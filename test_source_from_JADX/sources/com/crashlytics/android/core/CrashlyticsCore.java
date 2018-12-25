package com.crashlytics.android.core;

import android.content.Context;
import android.util.Log;
import com.crashlytics.android.core.internal.CrashEventDataProvider;
import com.crashlytics.android.core.internal.models.SessionEventData;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.Logger;
import io.fabric.sdk.android.services.common.ApiKey;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.common.ExecutorUtils;
import io.fabric.sdk.android.services.common.IdManager;
import io.fabric.sdk.android.services.concurrency.DependsOn;
import io.fabric.sdk.android.services.concurrency.PriorityCallable;
import io.fabric.sdk.android.services.concurrency.Task;
import io.fabric.sdk.android.services.concurrency.UnmetDependencyException;
import io.fabric.sdk.android.services.network.DefaultHttpRequestFactory;
import io.fabric.sdk.android.services.network.HttpMethod;
import io.fabric.sdk.android.services.network.HttpRequest;
import io.fabric.sdk.android.services.network.HttpRequestFactory;
import io.fabric.sdk.android.services.persistence.FileStore;
import io.fabric.sdk.android.services.persistence.FileStoreImpl;
import io.fabric.sdk.android.services.persistence.PreferenceStore;
import io.fabric.sdk.android.services.persistence.PreferenceStoreImpl;
import io.fabric.sdk.android.services.settings.Settings;
import io.fabric.sdk.android.services.settings.SettingsData;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javax.net.ssl.HttpsURLConnection;
import name.antonsmirnov.firmata.FormatHelper;

@DependsOn({CrashEventDataProvider.class})
public class CrashlyticsCore extends Kit<Void> {
    static final float CLS_DEFAULT_PROCESS_DELAY = 1.0f;
    static final String CRASHLYTICS_REQUIRE_BUILD_ID = "com.crashlytics.RequireBuildId";
    static final boolean CRASHLYTICS_REQUIRE_BUILD_ID_DEFAULT = true;
    static final String CRASH_MARKER_FILE_NAME = "crash_marker";
    static final int DEFAULT_MAIN_HANDLER_TIMEOUT_SEC = 4;
    private static final String INITIALIZATION_MARKER_FILE_NAME = "initialization_marker";
    static final int MAX_ATTRIBUTES = 64;
    static final int MAX_ATTRIBUTE_SIZE = 1024;
    private static final String MISSING_BUILD_ID_MSG = "This app relies on Crashlytics. Please sign up for access at https://fabric.io/sign_up,\ninstall an Android build tool and ask a team member to invite you to this app's organization.";
    private static final String PREFERENCE_STORE_NAME = "com.crashlytics.android.core.CrashlyticsCore";
    public static final String TAG = "CrashlyticsCore";
    private final ConcurrentHashMap<String, String> attributes;
    private CrashlyticsBackgroundWorker backgroundWorker;
    private CrashlyticsController controller;
    private CrashlyticsFileMarker crashMarker;
    private float delay;
    private boolean disabled;
    private CrashEventDataProvider externalCrashEventDataProvider;
    private HttpRequestFactory httpRequestFactory;
    private CrashlyticsFileMarker initializationMarker;
    private CrashlyticsListener listener;
    private final PinningInfoProvider pinningInfo;
    private final long startTime;
    private String userEmail;
    private String userId;
    private String userName;

    public static class Builder {
        private float delay = -1.0f;
        private boolean disabled = false;
        private CrashlyticsListener listener;
        private PinningInfoProvider pinningInfoProvider;

        public Builder delay(float delay) {
            if (delay <= 0.0f) {
                throw new IllegalArgumentException("delay must be greater than 0");
            } else if (this.delay > 0.0f) {
                throw new IllegalStateException("delay already set.");
            } else {
                this.delay = delay;
                return this;
            }
        }

        public Builder listener(CrashlyticsListener listener) {
            if (listener == null) {
                throw new IllegalArgumentException("listener must not be null.");
            } else if (this.listener != null) {
                throw new IllegalStateException("listener already set.");
            } else {
                this.listener = listener;
                return this;
            }
        }

        @Deprecated
        public Builder pinningInfo(PinningInfoProvider pinningInfoProvider) {
            if (pinningInfoProvider == null) {
                throw new IllegalArgumentException("pinningInfoProvider must not be null.");
            } else if (this.pinningInfoProvider != null) {
                throw new IllegalStateException("pinningInfoProvider already set.");
            } else {
                this.pinningInfoProvider = pinningInfoProvider;
                return this;
            }
        }

        public Builder disabled(boolean isDisabled) {
            this.disabled = isDisabled;
            return this;
        }

        public CrashlyticsCore build() {
            if (this.delay < 0.0f) {
                this.delay = 1.0f;
            }
            return new CrashlyticsCore(this.delay, this.listener, this.pinningInfoProvider, this.disabled);
        }
    }

    public CrashlyticsCore() {
        this(1.0f, null, null, false);
    }

    CrashlyticsCore(float delay, CrashlyticsListener listener, PinningInfoProvider pinningInfo, boolean disabled) {
        this(delay, listener, pinningInfo, disabled, ExecutorUtils.buildSingleThreadExecutorService("Crashlytics Exception Handler"));
    }

    CrashlyticsCore(float delay, CrashlyticsListener listener, PinningInfoProvider pinningInfo, boolean disabled, ExecutorService crashHandlerExecutor) {
        this.userId = null;
        this.userEmail = null;
        this.userName = null;
        this.delay = delay;
        this.listener = listener != null ? listener : new CrashlyticsCore$NoOpListener(null);
        this.pinningInfo = pinningInfo;
        this.disabled = disabled;
        this.backgroundWorker = new CrashlyticsBackgroundWorker(crashHandlerExecutor);
        this.attributes = new ConcurrentHashMap();
        this.startTime = System.currentTimeMillis();
    }

    protected boolean onPreExecute() {
        return onPreExecute(super.getContext());
    }

    boolean onPreExecute(Context context) {
        Exception e;
        boolean z;
        String str;
        CrashlyticsController crashlyticsController;
        Context context2 = context;
        if (this.disabled) {
            return false;
        }
        String apiKey = new ApiKey().getValue(context2);
        if (apiKey == null) {
            return false;
        }
        String buildId = CommonUtils.resolveBuildId(context);
        boolean requiresBuildId = CommonUtils.getBooleanResourceValue(context2, CRASHLYTICS_REQUIRE_BUILD_ID, true);
        if (isBuildIdValid(buildId, requiresBuildId)) {
            try {
                CrashlyticsPinningInfoProvider crashlyticsPinningInfoProvider;
                Logger logger = Fabric.getLogger();
                String str2 = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Initializing Crashlytics ");
                stringBuilder.append(getVersion());
                logger.i(str2, stringBuilder.toString());
                FileStore fileStore = new FileStoreImpl(r10);
                r10.crashMarker = new CrashlyticsFileMarker(CRASH_MARKER_FILE_NAME, fileStore);
                r10.initializationMarker = new CrashlyticsFileMarker(INITIALIZATION_MARKER_FILE_NAME, fileStore);
                PreferenceStore preferenceStore = new PreferenceStoreImpl(getContext(), PREFERENCE_STORE_NAME);
                PreferenceManager preferenceManager = PreferenceManager.create(preferenceStore, r10);
                if (r10.pinningInfo != null) {
                    try {
                        crashlyticsPinningInfoProvider = new CrashlyticsPinningInfoProvider(r10.pinningInfo);
                    } catch (Exception e2) {
                        e = e2;
                        z = requiresBuildId;
                        str = apiKey;
                        crashlyticsController = null;
                        Fabric.getLogger().e(TAG, "Crashlytics was not started due to an exception during initialization", e);
                        r10.controller = crashlyticsController;
                        return false;
                    }
                }
                crashlyticsPinningInfoProvider = null;
                CrashlyticsPinningInfoProvider infoProvider = crashlyticsPinningInfoProvider;
                r10.httpRequestFactory = new DefaultHttpRequestFactory(Fabric.getLogger());
                r10.httpRequestFactory.setPinningInfoProvider(infoProvider);
                IdManager idManager = getIdManager();
                AppData appData = AppData.create(context2, idManager, apiKey, buildId);
                UnityVersionProvider unityVersionProvider = new ManifestUnityVersionProvider(context2, appData.packageName);
                Logger logger2 = Fabric.getLogger();
                String str3 = TAG;
                StringBuilder stringBuilder2 = new StringBuilder();
                IdManager idManager2 = idManager;
                stringBuilder2.append("Installer package name is: ");
                stringBuilder2.append(appData.installerPackageName);
                logger2.d(str3, stringBuilder2.toString());
                AppData appData2 = appData;
                CrashlyticsController crashlyticsController2 = crashlyticsController2;
                crashlyticsController = null;
                try {
                    r10.controller = new CrashlyticsController(r10, r10.backgroundWorker, r10.httpRequestFactory, idManager2, preferenceManager, fileStore, appData2, unityVersionProvider);
                    boolean initializeSynchronously = didPreviousInitializationFail();
                    checkForPreviousCrash();
                    r10.controller.enableExceptionHandling(Thread.getDefaultUncaughtExceptionHandler());
                    if (initializeSynchronously && CommonUtils.canTryConnection(context)) {
                        Fabric.getLogger().d(TAG, "Crashlytics did not finish previous background initialization. Initializing synchronously.");
                        finishInitSynchronously();
                        return false;
                    }
                    Fabric.getLogger().d(TAG, "Exception handling initialization successful");
                    return true;
                } catch (Exception e22) {
                    e = e22;
                    Fabric.getLogger().e(TAG, "Crashlytics was not started due to an exception during initialization", e);
                    r10.controller = crashlyticsController;
                    return false;
                }
            } catch (Exception e222) {
                z = requiresBuildId;
                str = apiKey;
                crashlyticsController = null;
                e = e222;
                Fabric.getLogger().e(TAG, "Crashlytics was not started due to an exception during initialization", e);
                r10.controller = crashlyticsController;
                return false;
            }
        }
        throw new UnmetDependencyException(MISSING_BUILD_ID_MSG);
    }

    protected Void doInBackground() {
        markInitializationStarted();
        SessionEventData crashEventData = getExternalCrashEventData();
        if (crashEventData != null) {
            this.controller.writeExternalCrashEvent(crashEventData);
        }
        this.controller.cleanInvalidTempFiles();
        try {
            SettingsData settingsData = Settings.getInstance().awaitSettingsData();
            if (settingsData == null) {
                Fabric.getLogger().w(TAG, "Received null settings, skipping report submission!");
                markInitializationComplete();
                return null;
            } else if (settingsData.featuresData.collectReports) {
                if (!this.controller.finalizeSessions(settingsData.sessionData)) {
                    Fabric.getLogger().d(TAG, "Could not finalize previous sessions.");
                }
                this.controller.submitAllReports(this.delay, settingsData);
                markInitializationComplete();
                return null;
            } else {
                Fabric.getLogger().d(TAG, "Collection of crash reports disabled in Crashlytics settings.");
                markInitializationComplete();
                return null;
            }
        } catch (Exception e) {
            Fabric.getLogger().e(TAG, "Crashlytics encountered a problem during asynchronous initialization.", e);
        } catch (Throwable th) {
            markInitializationComplete();
        }
    }

    public String getIdentifier() {
        return "com.crashlytics.sdk.android.crashlytics-core";
    }

    public String getVersion() {
        return "2.3.17.dev";
    }

    public static CrashlyticsCore getInstance() {
        return (CrashlyticsCore) Fabric.getKit(CrashlyticsCore.class);
    }

    public PinningInfoProvider getPinningInfoProvider() {
        return !this.disabled ? this.pinningInfo : null;
    }

    public void logException(Throwable throwable) {
        if (this.disabled || !ensureFabricWithCalled("prior to logging exceptions.")) {
            return;
        }
        if (throwable == null) {
            Fabric.getLogger().log(5, TAG, "Crashlytics is ignoring a request to log a null exception.");
        } else {
            this.controller.writeNonFatalException(Thread.currentThread(), throwable);
        }
    }

    public void log(String msg) {
        doLog(3, TAG, msg);
    }

    private void doLog(int priority, String tag, String msg) {
        if (!this.disabled && ensureFabricWithCalled("prior to logging messages.")) {
            this.controller.writeToLog(System.currentTimeMillis() - this.startTime, formatLogMessage(priority, tag, msg));
        }
    }

    public void log(int priority, String tag, String msg) {
        doLog(priority, tag, msg);
        Logger logger = Fabric.getLogger();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(tag);
        String stringBuilder2 = stringBuilder.toString();
        StringBuilder stringBuilder3 = new StringBuilder();
        stringBuilder3.append("");
        stringBuilder3.append(msg);
        logger.log(priority, stringBuilder2, stringBuilder3.toString(), true);
    }

    public void setUserIdentifier(String identifier) {
        if (!this.disabled && ensureFabricWithCalled("prior to setting user data.")) {
            this.userId = sanitizeAttribute(identifier);
            this.controller.cacheUserData(this.userId, this.userName, this.userEmail);
        }
    }

    public void setUserName(String name) {
        if (!this.disabled && ensureFabricWithCalled("prior to setting user data.")) {
            this.userName = sanitizeAttribute(name);
            this.controller.cacheUserData(this.userId, this.userName, this.userEmail);
        }
    }

    public void setUserEmail(String email) {
        if (!this.disabled && ensureFabricWithCalled("prior to setting user data.")) {
            this.userEmail = sanitizeAttribute(email);
            this.controller.cacheUserData(this.userId, this.userName, this.userEmail);
        }
    }

    public void setString(String key, String value) {
        if (this.disabled || !ensureFabricWithCalled("prior to setting keys.")) {
            return;
        }
        if (key == null) {
            Context context = getContext();
            if (context == null || !CommonUtils.isAppDebuggable(context)) {
                Fabric.getLogger().e(TAG, "Attempting to set custom attribute with null key, ignoring.", null);
                return;
            }
            throw new IllegalArgumentException("Custom attribute key must not be null.");
        }
        key = sanitizeAttribute(key);
        if (this.attributes.size() < 64 || this.attributes.containsKey(key)) {
            this.attributes.put(key, value == null ? "" : sanitizeAttribute(value));
            this.controller.cacheKeyData(this.attributes);
            return;
        }
        Fabric.getLogger().d(TAG, "Exceeded maximum number of custom attributes (64)");
    }

    public void setBool(String key, boolean value) {
        setString(key, Boolean.toString(value));
    }

    public void setDouble(String key, double value) {
        setString(key, Double.toString(value));
    }

    public void setFloat(String key, float value) {
        setString(key, Float.toString(value));
    }

    public void setInt(String key, int value) {
        setString(key, Integer.toString(value));
    }

    public void setLong(String key, long value) {
        setString(key, Long.toString(value));
    }

    public void crash() {
        new CrashTest().indexOutOfBounds();
    }

    public boolean verifyPinning(URL url) {
        try {
            return internalVerifyPinning(url);
        } catch (Exception e) {
            Fabric.getLogger().e(TAG, "Could not verify SSL pinning", e);
            return false;
        }
    }

    @Deprecated
    public synchronized void setListener(CrashlyticsListener listener) {
        Fabric.getLogger().w(TAG, "Use of setListener is deprecated.");
        if (listener == null) {
            throw new IllegalArgumentException("listener must not be null.");
        }
        this.listener = listener;
    }

    Map<String, String> getAttributes() {
        return Collections.unmodifiableMap(this.attributes);
    }

    CrashlyticsController getController() {
        return this.controller;
    }

    String getUserIdentifier() {
        return getIdManager().canCollectUserIds() ? this.userId : null;
    }

    String getUserEmail() {
        return getIdManager().canCollectUserIds() ? this.userEmail : null;
    }

    String getUserName() {
        return getIdManager().canCollectUserIds() ? this.userName : null;
    }

    private void finishInitSynchronously() {
        PriorityCallable<Void> callable = new CrashlyticsCore$1(this);
        for (Task task : getDependencies()) {
            callable.addDependency(task);
        }
        Future<Void> future = getFabric().getExecutorService().submit(callable);
        Fabric.getLogger().d(TAG, "Crashlytics detected incomplete initialization on previous app launch. Will initialize synchronously.");
        try {
            future.get(4, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Fabric.getLogger().e(TAG, "Crashlytics was interrupted during initialization.", e);
        } catch (ExecutionException e2) {
            Fabric.getLogger().e(TAG, "Problem encountered during Crashlytics initialization.", e2);
        } catch (TimeoutException e3) {
            Fabric.getLogger().e(TAG, "Crashlytics timed out during initialization.", e3);
        }
    }

    void markInitializationStarted() {
        this.backgroundWorker.submitAndWait(new CrashlyticsCore$2(this));
    }

    void markInitializationComplete() {
        this.backgroundWorker.submit(new CrashlyticsCore$3(this));
    }

    boolean didPreviousInitializationFail() {
        return ((Boolean) this.backgroundWorker.submitAndWait(new CrashlyticsCore$4(this))).booleanValue();
    }

    void setExternalCrashEventDataProvider(CrashEventDataProvider provider) {
        this.externalCrashEventDataProvider = provider;
    }

    SessionEventData getExternalCrashEventData() {
        if (this.externalCrashEventDataProvider != null) {
            return this.externalCrashEventDataProvider.getCrashEventData();
        }
        return null;
    }

    boolean internalVerifyPinning(URL url) {
        if (getPinningInfoProvider() == null) {
            return false;
        }
        HttpRequest httpRequest = this.httpRequestFactory.buildHttpRequest(HttpMethod.GET, url.toString());
        ((HttpsURLConnection) httpRequest.getConnection()).setInstanceFollowRedirects(false);
        httpRequest.code();
        return true;
    }

    private void checkForPreviousCrash() {
        if (Boolean.TRUE.equals((Boolean) this.backgroundWorker.submitAndWait(new CrashlyticsCore$CrashMarkerCheck(this.crashMarker)))) {
            try {
                this.listener.crashlyticsDidDetectCrashDuringPreviousExecution();
            } catch (Exception e) {
                Fabric.getLogger().e(TAG, "Exception thrown by CrashlyticsListener while notifying of previous crash.", e);
            }
        }
    }

    void createCrashMarker() {
        this.crashMarker.create();
    }

    private static String formatLogMessage(int priority, String tag, String msg) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(CommonUtils.logPriorityToString(priority));
        stringBuilder.append("/");
        stringBuilder.append(tag);
        stringBuilder.append(FormatHelper.SPACE);
        stringBuilder.append(msg);
        return stringBuilder.toString();
    }

    private static boolean ensureFabricWithCalled(String msg) {
        CrashlyticsCore instance = getInstance();
        if (instance != null) {
            if (instance.controller != null) {
                return true;
            }
        }
        Logger logger = Fabric.getLogger();
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Crashlytics must be initialized by calling Fabric.with(Context) ");
        stringBuilder.append(msg);
        logger.e(str, stringBuilder.toString(), null);
        return false;
    }

    private static String sanitizeAttribute(String input) {
        if (input == null) {
            return input;
        }
        input = input.trim();
        if (input.length() > 1024) {
            return input.substring(0, 1024);
        }
        return input;
    }

    static boolean isBuildIdValid(String buildId, boolean requiresBuildId) {
        if (!requiresBuildId) {
            Fabric.getLogger().d(TAG, "Configured not to require a build ID.");
            return true;
        } else if (!CommonUtils.isNullOrEmpty(buildId)) {
            return true;
        } else {
            Log.e(TAG, ".");
            Log.e(TAG, ".     |  | ");
            Log.e(TAG, ".     |  |");
            Log.e(TAG, ".     |  |");
            Log.e(TAG, ".   \\ |  | /");
            Log.e(TAG, ".    \\    /");
            Log.e(TAG, ".     \\  /");
            Log.e(TAG, ".      \\/");
            Log.e(TAG, ".");
            Log.e(TAG, MISSING_BUILD_ID_MSG);
            Log.e(TAG, ".");
            Log.e(TAG, ".      /\\");
            Log.e(TAG, ".     /  \\");
            Log.e(TAG, ".    /    \\");
            Log.e(TAG, ".   / |  | \\");
            Log.e(TAG, ".     |  |");
            Log.e(TAG, ".     |  |");
            Log.e(TAG, ".     |  |");
            Log.e(TAG, ".");
            return false;
        }
    }
}
