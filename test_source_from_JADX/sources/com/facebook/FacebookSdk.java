package com.facebook;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.AsyncTask;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Base64;
import android.util.Log;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.internal.AppEventsLoggerUtility;
import com.facebook.internal.AppEventsLoggerUtility.GraphAPIActivityType;
import com.facebook.internal.AttributionIdentifiers;
import com.facebook.internal.LockOnGetVariable;
import com.facebook.internal.ServerProtocol;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import com.facebook.internal.WebDialog;
import io.fabric.sdk.android.services.common.CommonUtils;
import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.json.JSONException;
import org.json.JSONObject;

public final class FacebookSdk {
    public static final String APPLICATION_ID_PROPERTY = "com.facebook.sdk.ApplicationId";
    public static final String APPLICATION_NAME_PROPERTY = "com.facebook.sdk.ApplicationName";
    private static final String ATTRIBUTION_PREFERENCES = "com.facebook.sdk.attributionTracking";
    static final String CALLBACK_OFFSET_CHANGED_AFTER_INIT = "The callback request code offset can't be updated once the SDK is initialized.";
    static final String CALLBACK_OFFSET_NEGATIVE = "The callback request code offset can't be negative.";
    public static final String CLIENT_TOKEN_PROPERTY = "com.facebook.sdk.ClientToken";
    private static final int DEFAULT_CORE_POOL_SIZE = 5;
    private static final int DEFAULT_KEEP_ALIVE = 1;
    private static final int DEFAULT_MAXIMUM_POOL_SIZE = 128;
    private static final ThreadFactory DEFAULT_THREAD_FACTORY = new C04011();
    private static final BlockingQueue<Runnable> DEFAULT_WORK_QUEUE = new LinkedBlockingQueue(10);
    private static final String FACEBOOK_COM = "facebook.com";
    private static final Object LOCK = new Object();
    private static final int MAX_REQUEST_CODE_RANGE = 100;
    private static final String PUBLISH_ACTIVITY_PATH = "%s/activities";
    private static final String TAG = FacebookSdk.class.getCanonicalName();
    public static final String WEB_DIALOG_THEME = "com.facebook.sdk.WebDialogTheme";
    private static volatile String appClientToken;
    private static Context applicationContext;
    private static volatile String applicationId;
    private static volatile String applicationName;
    private static LockOnGetVariable<File> cacheDir;
    private static int callbackRequestCodeOffset = 64206;
    private static volatile Executor executor;
    private static volatile String facebookDomain = FACEBOOK_COM;
    private static volatile boolean isDebugEnabled = false;
    private static boolean isLegacyTokenUpgradeSupported = false;
    private static final HashSet<LoggingBehavior> loggingBehaviors = new HashSet(Arrays.asList(new LoggingBehavior[]{LoggingBehavior.DEVELOPER_ERRORS}));
    private static AtomicLong onProgressThreshold = new AtomicLong(PlaybackStateCompat.ACTION_PREPARE_FROM_SEARCH);
    private static Boolean sdkInitialized = Boolean.valueOf(false);
    private static volatile int webDialogTheme;

    /* renamed from: com.facebook.FacebookSdk$1 */
    static class C04011 implements ThreadFactory {
        private final AtomicInteger counter = new AtomicInteger(0);

        C04011() {
        }

        public Thread newThread(Runnable runnable) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("FacebookSdk #");
            stringBuilder.append(this.counter.incrementAndGet());
            return new Thread(runnable, stringBuilder.toString());
        }
    }

    /* renamed from: com.facebook.FacebookSdk$2 */
    static class C04022 implements Callable<File> {
        C04022() {
        }

        public File call() throws Exception {
            return FacebookSdk.applicationContext.getCacheDir();
        }
    }

    public interface InitializeCallback {
        void onInitialized();
    }

    public static synchronized void sdkInitialize(Context applicationContext, int callbackRequestCodeOffset) {
        synchronized (FacebookSdk.class) {
            sdkInitialize(applicationContext, callbackRequestCodeOffset, null);
        }
    }

    public static synchronized void sdkInitialize(Context applicationContext, int callbackRequestCodeOffset, InitializeCallback callback) {
        synchronized (FacebookSdk.class) {
            if (sdkInitialized.booleanValue() && callbackRequestCodeOffset != callbackRequestCodeOffset) {
                throw new FacebookException(CALLBACK_OFFSET_CHANGED_AFTER_INIT);
            } else if (callbackRequestCodeOffset < 0) {
                throw new FacebookException(CALLBACK_OFFSET_NEGATIVE);
            } else {
                callbackRequestCodeOffset = callbackRequestCodeOffset;
                sdkInitialize(applicationContext, callback);
            }
        }
    }

    public static synchronized void sdkInitialize(Context applicationContext) {
        synchronized (FacebookSdk.class) {
            sdkInitialize(applicationContext, null);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized void sdkInitialize(final android.content.Context r3, final com.facebook.FacebookSdk.InitializeCallback r4) {
        /*
        r0 = com.facebook.FacebookSdk.class;
        monitor-enter(r0);
        r1 = sdkInitialized;	 Catch:{ all -> 0x005e }
        r1 = r1.booleanValue();	 Catch:{ all -> 0x005e }
        if (r1 == 0) goto L_0x0012;
    L_0x000b:
        if (r4 == 0) goto L_0x0010;
    L_0x000d:
        r4.onInitialized();	 Catch:{ all -> 0x005e }
    L_0x0010:
        monitor-exit(r0);
        return;
    L_0x0012:
        r1 = "applicationContext";
        com.facebook.internal.Validate.notNull(r3, r1);	 Catch:{ all -> 0x005e }
        r1 = 0;
        com.facebook.internal.Validate.hasFacebookActivity(r3, r1);	 Catch:{ all -> 0x005e }
        com.facebook.internal.Validate.hasInternetPermissions(r3, r1);	 Catch:{ all -> 0x005e }
        r1 = r3.getApplicationContext();	 Catch:{ all -> 0x005e }
        applicationContext = r1;	 Catch:{ all -> 0x005e }
        r1 = applicationContext;	 Catch:{ all -> 0x005e }
        loadDefaultsFromMetadata(r1);	 Catch:{ all -> 0x005e }
        r1 = 1;
        r1 = java.lang.Boolean.valueOf(r1);	 Catch:{ all -> 0x005e }
        sdkInitialized = r1;	 Catch:{ all -> 0x005e }
        r1 = applicationContext;	 Catch:{ all -> 0x005e }
        r2 = applicationId;	 Catch:{ all -> 0x005e }
        com.facebook.internal.Utility.loadAppSettingsAsync(r1, r2);	 Catch:{ all -> 0x005e }
        com.facebook.internal.NativeProtocol.updateAllAvailableProtocolVersionsAsync();	 Catch:{ all -> 0x005e }
        r1 = applicationContext;	 Catch:{ all -> 0x005e }
        com.facebook.internal.BoltsMeasurementEventListener.getInstance(r1);	 Catch:{ all -> 0x005e }
        r1 = new com.facebook.internal.LockOnGetVariable;	 Catch:{ all -> 0x005e }
        r2 = new com.facebook.FacebookSdk$2;	 Catch:{ all -> 0x005e }
        r2.<init>();	 Catch:{ all -> 0x005e }
        r1.<init>(r2);	 Catch:{ all -> 0x005e }
        cacheDir = r1;	 Catch:{ all -> 0x005e }
        r1 = new java.util.concurrent.FutureTask;	 Catch:{ all -> 0x005e }
        r2 = new com.facebook.FacebookSdk$3;	 Catch:{ all -> 0x005e }
        r2.<init>(r4, r3);	 Catch:{ all -> 0x005e }
        r1.<init>(r2);	 Catch:{ all -> 0x005e }
        r2 = getExecutor();	 Catch:{ all -> 0x005e }
        r2.execute(r1);	 Catch:{ all -> 0x005e }
        monitor-exit(r0);
        return;
    L_0x005e:
        r3 = move-exception;
        monitor-exit(r0);
        throw r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.FacebookSdk.sdkInitialize(android.content.Context, com.facebook.FacebookSdk$InitializeCallback):void");
    }

    public static synchronized boolean isInitialized() {
        boolean booleanValue;
        synchronized (FacebookSdk.class) {
            booleanValue = sdkInitialized.booleanValue();
        }
        return booleanValue;
    }

    public static Set<LoggingBehavior> getLoggingBehaviors() {
        Set<LoggingBehavior> unmodifiableSet;
        synchronized (loggingBehaviors) {
            unmodifiableSet = Collections.unmodifiableSet(new HashSet(loggingBehaviors));
        }
        return unmodifiableSet;
    }

    public static void addLoggingBehavior(LoggingBehavior behavior) {
        synchronized (loggingBehaviors) {
            loggingBehaviors.add(behavior);
            updateGraphDebugBehavior();
        }
    }

    public static void removeLoggingBehavior(LoggingBehavior behavior) {
        synchronized (loggingBehaviors) {
            loggingBehaviors.remove(behavior);
        }
    }

    public static void clearLoggingBehaviors() {
        synchronized (loggingBehaviors) {
            loggingBehaviors.clear();
        }
    }

    public static boolean isLoggingBehaviorEnabled(LoggingBehavior behavior) {
        boolean z;
        synchronized (loggingBehaviors) {
            z = isDebugEnabled() && loggingBehaviors.contains(behavior);
        }
        return z;
    }

    public static boolean isDebugEnabled() {
        return isDebugEnabled;
    }

    public static void setIsDebugEnabled(boolean enabled) {
        isDebugEnabled = enabled;
    }

    public static boolean isLegacyTokenUpgradeSupported() {
        return isLegacyTokenUpgradeSupported;
    }

    private static void updateGraphDebugBehavior() {
        if (loggingBehaviors.contains(LoggingBehavior.GRAPH_API_DEBUG_INFO) && !loggingBehaviors.contains(LoggingBehavior.GRAPH_API_DEBUG_WARNING)) {
            loggingBehaviors.add(LoggingBehavior.GRAPH_API_DEBUG_WARNING);
        }
    }

    public static void setLegacyTokenUpgradeSupported(boolean supported) {
        isLegacyTokenUpgradeSupported = supported;
    }

    public static Executor getExecutor() {
        synchronized (LOCK) {
            if (executor == null) {
                executor = AsyncTask.THREAD_POOL_EXECUTOR;
            }
        }
        return executor;
    }

    public static void setExecutor(Executor executor) {
        Validate.notNull(executor, "executor");
        synchronized (LOCK) {
            executor = executor;
        }
    }

    public static String getFacebookDomain() {
        return facebookDomain;
    }

    public static void setFacebookDomain(String facebookDomain) {
        Log.w(TAG, "WARNING: Calling setFacebookDomain from non-DEBUG code.");
        facebookDomain = facebookDomain;
    }

    public static Context getApplicationContext() {
        Validate.sdkInitialized();
        return applicationContext;
    }

    public static void publishInstallAsync(Context context, final String applicationId) {
        final Context applicationContext = context.getApplicationContext();
        getExecutor().execute(new Runnable() {
            public void run() {
                FacebookSdk.publishInstallAndWaitForResponse(applicationContext, applicationId);
            }
        });
    }

    static GraphResponse publishInstallAndWaitForResponse(Context context, String applicationId) {
        Context context2 = context;
        String str = applicationId;
        if (context2 != null) {
            if (str != null) {
                try {
                    AttributionIdentifiers identifiers = AttributionIdentifiers.getAttributionIdentifiers(context);
                    SharedPreferences preferences = context2.getSharedPreferences(ATTRIBUTION_PREFERENCES, 0);
                    String pingKey = new StringBuilder();
                    pingKey.append(str);
                    pingKey.append("ping");
                    pingKey = pingKey.toString();
                    String jsonKey = new StringBuilder();
                    jsonKey.append(str);
                    jsonKey.append("json");
                    jsonKey = jsonKey.toString();
                    long lastPing = preferences.getLong(pingKey, 0);
                    String lastResponseJSON = preferences.getString(jsonKey, null);
                    JSONObject publishParams = AppEventsLoggerUtility.getJSONObjectForGraphAPICall(GraphAPIActivityType.MOBILE_INSTALL_EVENT, identifiers, AppEventsLogger.getAnonymousAppDeviceGUID(context), getLimitEventAndDataUsage(context), context2);
                    GraphRequest publishRequest = GraphRequest.newPostRequest(null, String.format(PUBLISH_ACTIVITY_PATH, new Object[]{str}), publishParams, null);
                    if (lastPing != 0) {
                        JSONObject graphObject = null;
                        if (lastResponseJSON != null) {
                            try {
                                graphObject = new JSONObject(lastResponseJSON);
                            } catch (JSONException e) {
                            }
                        }
                        JSONObject graphObject2 = graphObject;
                        if (graphObject2 != null) {
                            return new GraphResponse(null, null, null, graphObject2);
                        }
                        return (GraphResponse) GraphResponse.createResponsesFromString(ServerProtocol.DIALOG_RETURN_SCOPES_TRUE, null, new GraphRequestBatch(publishRequest)).get(0);
                    }
                    GraphResponse publishResponse = publishRequest.executeAndWait();
                    Editor editor = preferences.edit();
                    editor.putLong(pingKey, System.currentTimeMillis());
                    if (publishResponse.getJSONObject() != null) {
                        editor.putString(jsonKey, publishResponse.getJSONObject().toString());
                    }
                    editor.apply();
                    return publishResponse;
                } catch (Throwable e2) {
                    throw new FacebookException("An error occurred while publishing install.", e2);
                } catch (Exception e3) {
                    Exception e4 = e3;
                    Utility.logd("Facebook-publish", e4);
                    return new GraphResponse(null, null, new FacebookRequestError(null, e4));
                }
            }
        }
        throw new IllegalArgumentException("Both context and applicationId must be non-null");
    }

    public static String getSdkVersion() {
        return FacebookSdkVersion.BUILD;
    }

    public static boolean getLimitEventAndDataUsage(Context context) {
        Validate.sdkInitialized();
        return context.getSharedPreferences(AppEventsLogger.APP_EVENT_PREFERENCES, 0).getBoolean("limitEventUsage", false);
    }

    public static void setLimitEventAndDataUsage(Context context, boolean limitEventUsage) {
        context.getSharedPreferences(AppEventsLogger.APP_EVENT_PREFERENCES, 0).edit().putBoolean("limitEventUsage", limitEventUsage).apply();
    }

    public static long getOnProgressThreshold() {
        Validate.sdkInitialized();
        return onProgressThreshold.get();
    }

    public static void setOnProgressThreshold(long threshold) {
        onProgressThreshold.set(threshold);
    }

    static void loadDefaultsFromMetadata(Context context) {
        if (context != null) {
            try {
                ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
                if (ai != null) {
                    if (ai.metaData != null) {
                        if (applicationId == null) {
                            String appId = ai.metaData.get(APPLICATION_ID_PROPERTY);
                            if (appId instanceof String) {
                                String appIdString = appId;
                                if (appIdString.toLowerCase(Locale.ROOT).startsWith("fb")) {
                                    applicationId = appIdString.substring(2);
                                } else {
                                    applicationId = appIdString;
                                }
                            } else if (appId instanceof Integer) {
                                throw new FacebookException("App Ids cannot be directly placed in the manifest.They must be prefixed by 'fb' or be placed in the string resource file.");
                            }
                        }
                        if (applicationName == null) {
                            applicationName = ai.metaData.getString(APPLICATION_NAME_PROPERTY);
                        }
                        if (appClientToken == null) {
                            appClientToken = ai.metaData.getString(CLIENT_TOKEN_PROPERTY);
                        }
                        if (webDialogTheme == 0) {
                            setWebDialogTheme(ai.metaData.getInt(WEB_DIALOG_THEME));
                        }
                    }
                }
            } catch (NameNotFoundException e) {
            }
        }
    }

    public static String getApplicationSignature(Context context) {
        Validate.sdkInitialized();
        if (context == null) {
            return null;
        }
        PackageManager packageManager = context.getPackageManager();
        if (packageManager == null) {
            return null;
        }
        try {
            PackageInfo pInfo = packageManager.getPackageInfo(context.getPackageName(), 64);
            Signature[] signatures = pInfo.signatures;
            if (signatures != null) {
                if (signatures.length != 0) {
                    try {
                        MessageDigest md = MessageDigest.getInstance(CommonUtils.SHA1_INSTANCE);
                        md.update(pInfo.signatures[0].toByteArray());
                        return Base64.encodeToString(md.digest(), 9);
                    } catch (NoSuchAlgorithmException e) {
                        return null;
                    }
                }
            }
            return null;
        } catch (NameNotFoundException e2) {
            return null;
        }
    }

    public static String getApplicationId() {
        Validate.sdkInitialized();
        return applicationId;
    }

    public static void setApplicationId(String applicationId) {
        applicationId = applicationId;
    }

    public static String getApplicationName() {
        Validate.sdkInitialized();
        return applicationName;
    }

    public static void setApplicationName(String applicationName) {
        applicationName = applicationName;
    }

    public static String getClientToken() {
        Validate.sdkInitialized();
        return appClientToken;
    }

    public static void setClientToken(String clientToken) {
        appClientToken = clientToken;
    }

    public static int getWebDialogTheme() {
        Validate.sdkInitialized();
        return webDialogTheme;
    }

    public static void setWebDialogTheme(int theme) {
        webDialogTheme = theme != 0 ? theme : WebDialog.DEFAULT_THEME;
    }

    public static File getCacheDir() {
        Validate.sdkInitialized();
        return (File) cacheDir.getValue();
    }

    public static void setCacheDir(File cacheDir) {
        cacheDir = new LockOnGetVariable((Object) cacheDir);
    }

    public static int getCallbackRequestCodeOffset() {
        Validate.sdkInitialized();
        return callbackRequestCodeOffset;
    }

    public static boolean isFacebookRequestCode(int requestCode) {
        return requestCode >= callbackRequestCodeOffset && requestCode < callbackRequestCodeOffset + 100;
    }
}
