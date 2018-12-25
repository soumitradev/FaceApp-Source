package com.crashlytics.android.core;

import android.app.Activity;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.core.LogFileManager.DirectoryProvider;
import com.crashlytics.android.core.internal.models.SessionEventData;
import com.facebook.appevents.AppEventsConstants;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.Logger;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.common.Crash.FatalException;
import io.fabric.sdk.android.services.common.Crash.LoggedException;
import io.fabric.sdk.android.services.common.DeliveryMechanism;
import io.fabric.sdk.android.services.common.IdManager;
import io.fabric.sdk.android.services.network.HttpRequestFactory;
import io.fabric.sdk.android.services.persistence.FileStore;
import io.fabric.sdk.android.services.settings.PromptSettingsData;
import io.fabric.sdk.android.services.settings.SessionSettingsData;
import io.fabric.sdk.android.services.settings.Settings;
import io.fabric.sdk.android.services.settings.SettingsData;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class CrashlyticsController {
    private static final int ANALYZER_VERSION = 1;
    static final FilenameFilter ANY_SESSION_FILENAME_FILTER = new C03834();
    private static final String COLLECT_CUSTOM_KEYS = "com.crashlytics.CollectCustomKeys";
    private static final String CRASHLYTICS_API_ENDPOINT = "com.crashlytics.ApiEndpoint";
    private static final String EVENT_TYPE_CRASH = "crash";
    private static final String EVENT_TYPE_LOGGED = "error";
    static final String FATAL_SESSION_DIR = "fatal-sessions";
    private static final String GENERATOR_FORMAT = "Crashlytics Android SDK/%s";
    private static final String[] INITIAL_SESSION_PART_TAGS = new String[]{SESSION_USER_TAG, SESSION_APP_TAG, SESSION_OS_TAG, SESSION_DEVICE_TAG};
    static final String INVALID_CLS_CACHE_DIR = "invalidClsFiles";
    static final Comparator<File> LARGEST_FILE_NAME_FIRST = new C03812();
    static final int MAX_INVALID_SESSIONS = 4;
    private static final int MAX_LOCAL_LOGGED_EXCEPTIONS = 64;
    static final int MAX_OPEN_SESSIONS = 8;
    static final int MAX_STACK_SIZE = 1024;
    static final String NONFATAL_SESSION_DIR = "nonfatal-sessions";
    static final int NUM_STACK_REPETITIONS_ALLOWED = 10;
    private static final Map<String, String> SEND_AT_CRASHTIME_HEADER = Collections.singletonMap("X-CRASHLYTICS-SEND-FLAGS", AppEventsConstants.EVENT_PARAM_VALUE_YES);
    static final String SESSION_APP_TAG = "SessionApp";
    static final String SESSION_BEGIN_TAG = "BeginSession";
    static final String SESSION_DEVICE_TAG = "SessionDevice";
    static final String SESSION_EVENT_MISSING_BINARY_IMGS_TAG = "SessionMissingBinaryImages";
    static final String SESSION_FATAL_TAG = "SessionCrash";
    static final FilenameFilter SESSION_FILE_FILTER = new C03801();
    private static final Pattern SESSION_FILE_PATTERN = Pattern.compile("([\\d|A-Z|a-z]{12}\\-[\\d|A-Z|a-z]{4}\\-[\\d|A-Z|a-z]{4}\\-[\\d|A-Z|a-z]{12}).+");
    private static final int SESSION_ID_LENGTH = 35;
    static final String SESSION_NON_FATAL_TAG = "SessionEvent";
    static final String SESSION_OS_TAG = "SessionOS";
    static final String SESSION_USER_TAG = "SessionUser";
    private static final boolean SHOULD_PROMPT_BEFORE_SENDING_REPORTS_DEFAULT = false;
    static final Comparator<File> SMALLEST_FILE_NAME_FIRST = new C03823();
    private final AppData appData;
    private final CrashlyticsBackgroundWorker backgroundWorker;
    private CrashlyticsUncaughtExceptionHandler crashHandler;
    private final CrashlyticsCore crashlyticsCore;
    private final DevicePowerStateListener devicePowerStateListener;
    private final AtomicInteger eventCounter = new AtomicInteger(0);
    private final FileStore fileStore;
    private final HandlingExceptionCheck handlingExceptionCheck;
    private final HttpRequestFactory httpRequestFactory;
    private final IdManager idManager;
    private final LogFileDirectoryProvider logFileDirectoryProvider;
    private final LogFileManager logFileManager;
    private final PreferenceManager preferenceManager;
    private final ReportFilesProvider reportFilesProvider;
    private final StackTraceTrimmingStrategy stackTraceTrimmingStrategy;
    private final String unityVersion;

    /* renamed from: com.crashlytics.android.core.CrashlyticsController$1 */
    static class C03801 implements FilenameFilter {
        C03801() {
        }

        public boolean accept(File dir, String filename) {
            return filename.length() == ClsFileOutputStream.SESSION_FILE_EXTENSION.length() + 35 && filename.endsWith(ClsFileOutputStream.SESSION_FILE_EXTENSION);
        }
    }

    /* renamed from: com.crashlytics.android.core.CrashlyticsController$2 */
    static class C03812 implements Comparator<File> {
        C03812() {
        }

        public int compare(File file1, File file2) {
            return file2.getName().compareTo(file1.getName());
        }
    }

    /* renamed from: com.crashlytics.android.core.CrashlyticsController$3 */
    static class C03823 implements Comparator<File> {
        C03823() {
        }

        public int compare(File file1, File file2) {
            return file1.getName().compareTo(file2.getName());
        }
    }

    /* renamed from: com.crashlytics.android.core.CrashlyticsController$4 */
    static class C03834 implements FilenameFilter {
        C03834() {
        }

        public boolean accept(File file, String filename) {
            return CrashlyticsController.SESSION_FILE_PATTERN.matcher(filename).matches();
        }
    }

    private static class AnySessionPartFileFilter implements FilenameFilter {
        private AnySessionPartFileFilter() {
        }

        public boolean accept(File file, String fileName) {
            return !CrashlyticsController.SESSION_FILE_FILTER.accept(file, fileName) && CrashlyticsController.SESSION_FILE_PATTERN.matcher(fileName).matches();
        }
    }

    static class FileNameContainsFilter implements FilenameFilter {
        private final String string;

        public FileNameContainsFilter(String s) {
            this.string = s;
        }

        public boolean accept(File dir, String filename) {
            return filename.contains(this.string) && !filename.endsWith(ClsFileOutputStream.IN_PROGRESS_SESSION_FILE_EXTENSION);
        }
    }

    static class InvalidPartFileFilter implements FilenameFilter {
        InvalidPartFileFilter() {
        }

        public boolean accept(File file, String fileName) {
            if (!ClsFileOutputStream.TEMP_FILENAME_FILTER.accept(file, fileName)) {
                if (!fileName.contains(CrashlyticsController.SESSION_EVENT_MISSING_BINARY_IMGS_TAG)) {
                    return false;
                }
            }
            return true;
        }
    }

    private static final class SendReportRunnable implements Runnable {
        private final Context context;
        private final Report report;
        private final ReportUploader reportUploader;

        public SendReportRunnable(Context context, Report report, ReportUploader reportUploader) {
            this.context = context;
            this.report = report;
            this.reportUploader = reportUploader;
        }

        public void run() {
            if (CommonUtils.canTryConnection(this.context)) {
                Fabric.getLogger().d(CrashlyticsCore.TAG, "Attempting to send crash report at time of crash...");
                this.reportUploader.forceUpload(this.report);
            }
        }
    }

    static class SessionPartFileFilter implements FilenameFilter {
        private final String sessionId;

        public SessionPartFileFilter(String sessionId) {
            this.sessionId = sessionId;
        }

        public boolean accept(File file, String fileName) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.sessionId);
            stringBuilder.append(ClsFileOutputStream.SESSION_FILE_EXTENSION);
            boolean z = false;
            if (fileName.equals(stringBuilder.toString())) {
                return false;
            }
            if (fileName.contains(this.sessionId) && !fileName.endsWith(ClsFileOutputStream.IN_PROGRESS_SESSION_FILE_EXTENSION)) {
                z = true;
            }
            return z;
        }
    }

    /* renamed from: com.crashlytics.android.core.CrashlyticsController$5 */
    class C08045 implements CrashListener {
        C08045() {
        }

        public void onUncaughtException(Thread thread, Throwable ex) {
            CrashlyticsController.this.handleUncaughtException(thread, ex);
        }
    }

    private static final class LogFileDirectoryProvider implements DirectoryProvider {
        private static final String LOG_FILES_DIR = "log-files";
        private final FileStore rootFileStore;

        public LogFileDirectoryProvider(FileStore rootFileStore) {
            this.rootFileStore = rootFileStore;
        }

        public File getLogFileDir() {
            File logFileDir = new File(this.rootFileStore.getFilesDir(), LOG_FILES_DIR);
            if (!logFileDir.exists()) {
                logFileDir.mkdirs();
            }
            return logFileDir;
        }
    }

    private static final class PrivacyDialogCheck implements SendCheck {
        private final Kit kit;
        private final PreferenceManager preferenceManager;
        private final PromptSettingsData promptData;

        /* renamed from: com.crashlytics.android.core.CrashlyticsController$PrivacyDialogCheck$1 */
        class C08051 implements AlwaysSendCallback {
            C08051() {
            }

            public void sendUserReportsWithoutPrompting(boolean send) {
                PrivacyDialogCheck.this.preferenceManager.setShouldAlwaysSendReports(send);
            }
        }

        public PrivacyDialogCheck(Kit kit, PreferenceManager preferenceManager, PromptSettingsData promptData) {
            this.kit = kit;
            this.preferenceManager = preferenceManager;
            this.promptData = promptData;
        }

        public boolean canSendReports() {
            Activity activity = this.kit.getFabric().getCurrentActivity();
            if (activity != null) {
                if (!activity.isFinishing()) {
                    final CrashPromptDialog dialog = CrashPromptDialog.create(activity, this.promptData, new C08051());
                    activity.runOnUiThread(new Runnable() {
                        public void run() {
                            dialog.show();
                        }
                    });
                    Fabric.getLogger().d(CrashlyticsCore.TAG, "Waiting for user opt-in.");
                    dialog.await();
                    return dialog.getOptIn();
                }
            }
            return true;
        }
    }

    private final class ReportUploaderFilesProvider implements ReportFilesProvider {
        private ReportUploaderFilesProvider() {
        }

        public File[] getCompleteSessionFiles() {
            return CrashlyticsController.this.listCompleteSessionFiles();
        }

        public File[] getInvalidSessionFiles() {
            return CrashlyticsController.this.getInvalidFilesDir().listFiles();
        }
    }

    private final class ReportUploaderHandlingExceptionCheck implements HandlingExceptionCheck {
        private ReportUploaderHandlingExceptionCheck() {
        }

        public boolean isHandlingException() {
            return CrashlyticsController.this.isHandlingException();
        }
    }

    CrashlyticsController(CrashlyticsCore crashlyticsCore, CrashlyticsBackgroundWorker backgroundWorker, HttpRequestFactory httpRequestFactory, IdManager idManager, PreferenceManager preferenceManager, FileStore fileStore, AppData appData, UnityVersionProvider unityVersionProvider) {
        this.crashlyticsCore = crashlyticsCore;
        this.backgroundWorker = backgroundWorker;
        this.httpRequestFactory = httpRequestFactory;
        this.idManager = idManager;
        this.preferenceManager = preferenceManager;
        this.fileStore = fileStore;
        this.appData = appData;
        this.unityVersion = unityVersionProvider.getUnityVersion();
        Context context = crashlyticsCore.getContext();
        this.logFileDirectoryProvider = new LogFileDirectoryProvider(fileStore);
        this.logFileManager = new LogFileManager(context, this.logFileDirectoryProvider);
        this.reportFilesProvider = new ReportUploaderFilesProvider();
        this.handlingExceptionCheck = new ReportUploaderHandlingExceptionCheck();
        this.devicePowerStateListener = new DevicePowerStateListener(context);
        this.stackTraceTrimmingStrategy = new MiddleOutFallbackStrategy(1024, new RemoveRepeatsStrategy(10));
    }

    void enableExceptionHandling(UncaughtExceptionHandler defaultHandler) {
        openSession();
        this.crashHandler = new CrashlyticsUncaughtExceptionHandler(new C08045(), defaultHandler);
        Thread.setDefaultUncaughtExceptionHandler(this.crashHandler);
    }

    synchronized void handleUncaughtException(final Thread thread, final Throwable ex) {
        Logger logger = Fabric.getLogger();
        String str = CrashlyticsCore.TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Crashlytics is handling uncaught exception \"");
        stringBuilder.append(ex);
        stringBuilder.append("\" from thread ");
        stringBuilder.append(thread.getName());
        logger.d(str, stringBuilder.toString());
        this.devicePowerStateListener.dispose();
        final Date time = new Date();
        this.backgroundWorker.submitAndWait(new Callable<Void>() {
            public Void call() throws Exception {
                CrashlyticsController.this.crashlyticsCore.createCrashMarker();
                CrashlyticsController.this.writeFatal(time, thread, ex);
                SettingsData settingsData = Settings.getInstance().awaitSettingsData();
                SessionSettingsData sessionSettings = settingsData != null ? settingsData.sessionData : null;
                CrashlyticsController.this.doCloseSessions(sessionSettings);
                CrashlyticsController.this.doOpenSession();
                if (sessionSettings != null) {
                    CrashlyticsController.this.trimSessionFiles(sessionSettings.maxCompleteSessionsCount);
                }
                if (!CrashlyticsController.this.shouldPromptUserBeforeSendingCrashReports(settingsData)) {
                    CrashlyticsController.this.sendSessionReports(settingsData);
                }
                return null;
            }
        });
    }

    void submitAllReports(float delay, SettingsData settingsData) {
        if (settingsData == null) {
            Fabric.getLogger().w(CrashlyticsCore.TAG, "Could not send reports. Settings are not available.");
            return;
        }
        new ReportUploader(this.appData.apiKey, getCreateReportSpiCall(settingsData.appData.reportsUrl), this.reportFilesProvider, this.handlingExceptionCheck).uploadReports(delay, shouldPromptUserBeforeSendingCrashReports(settingsData) ? new PrivacyDialogCheck(this.crashlyticsCore, this.preferenceManager, settingsData.promptData) : new AlwaysSendCheck());
    }

    void writeToLog(final long timestamp, final String msg) {
        this.backgroundWorker.submit(new Callable<Void>() {
            public Void call() throws Exception {
                if (!CrashlyticsController.this.isHandlingException()) {
                    CrashlyticsController.this.logFileManager.writeToLog(timestamp, msg);
                }
                return null;
            }
        });
    }

    void writeNonFatalException(final Thread thread, final Throwable ex) {
        final Date now = new Date();
        this.backgroundWorker.submit(new Runnable() {
            public void run() {
                if (!CrashlyticsController.this.isHandlingException()) {
                    CrashlyticsController.this.doWriteNonFatal(now, thread, ex);
                }
            }
        });
    }

    void cacheUserData(final String userId, final String userName, final String userEmail) {
        this.backgroundWorker.submit(new Callable<Void>() {
            public Void call() throws Exception {
                new MetaDataStore(CrashlyticsController.this.getFilesDir()).writeUserData(CrashlyticsController.this.getCurrentSessionId(), new UserMetaData(userId, userName, userEmail));
                return null;
            }
        });
    }

    void cacheKeyData(final Map<String, String> keyData) {
        this.backgroundWorker.submit(new Callable<Void>() {
            public Void call() throws Exception {
                new MetaDataStore(CrashlyticsController.this.getFilesDir()).writeKeyData(CrashlyticsController.this.getCurrentSessionId(), keyData);
                return null;
            }
        });
    }

    void openSession() {
        this.backgroundWorker.submit(new Callable<Void>() {
            public Void call() throws Exception {
                CrashlyticsController.this.doOpenSession();
                return null;
            }
        });
    }

    private String getCurrentSessionId() {
        File[] sessionBeginFiles = listSortedSessionBeginFiles();
        return sessionBeginFiles.length > 0 ? getSessionIdFromSessionFile(sessionBeginFiles[0]) : null;
    }

    private String getPreviousSessionId() {
        File[] sessionBeginFiles = listSortedSessionBeginFiles();
        return sessionBeginFiles.length > 1 ? getSessionIdFromSessionFile(sessionBeginFiles[1]) : null;
    }

    static String getSessionIdFromSessionFile(File sessionFile) {
        return sessionFile.getName().substring(0, 35);
    }

    boolean hasOpenSession() {
        return listSessionBeginFiles().length > 0;
    }

    boolean finalizeSessions(final SessionSettingsData sessionSettingsData) {
        return ((Boolean) this.backgroundWorker.submitAndWait(new Callable<Boolean>() {
            public Boolean call() throws Exception {
                if (CrashlyticsController.this.isHandlingException()) {
                    Fabric.getLogger().d(CrashlyticsCore.TAG, "Skipping session finalization because a crash has already occurred.");
                    return Boolean.FALSE;
                }
                Fabric.getLogger().d(CrashlyticsCore.TAG, "Finalizing previously open sessions.");
                CrashlyticsController.this.doCloseSessions(sessionSettingsData, true);
                Fabric.getLogger().d(CrashlyticsCore.TAG, "Closed all previously open sessions");
                return Boolean.TRUE;
            }
        })).booleanValue();
    }

    private void doOpenSession() throws Exception {
        Date startedAt = new Date();
        String sessionIdentifier = new CLSUUID(this.idManager).toString();
        Logger logger = Fabric.getLogger();
        String str = CrashlyticsCore.TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Opening a new session with ID ");
        stringBuilder.append(sessionIdentifier);
        logger.d(str, stringBuilder.toString());
        writeBeginSession(sessionIdentifier, startedAt);
        writeSessionApp(sessionIdentifier);
        writeSessionOS(sessionIdentifier);
        writeSessionDevice(sessionIdentifier);
        this.logFileManager.setCurrentSession(sessionIdentifier);
    }

    void doCloseSessions(SessionSettingsData sessionSettingsData) throws Exception {
        doCloseSessions(sessionSettingsData, false);
    }

    private void doCloseSessions(SessionSettingsData sessionSettingsData, boolean excludeCurrent) throws Exception {
        boolean offset = excludeCurrent;
        trimOpenSessions(offset + 8);
        File[] sessionBeginFiles = listSortedSessionBeginFiles();
        if (sessionBeginFiles.length <= offset) {
            Fabric.getLogger().d(CrashlyticsCore.TAG, "No open sessions to be closed.");
            return;
        }
        writeSessionUser(getSessionIdFromSessionFile(sessionBeginFiles[offset]));
        if (sessionSettingsData == null) {
            Fabric.getLogger().d(CrashlyticsCore.TAG, "Unable to close session. Settings are not loaded.");
        } else {
            closeOpenSessions(sessionBeginFiles, offset, sessionSettingsData.maxCustomExceptionEvents);
        }
    }

    private void closeOpenSessions(File[] sessionBeginFiles, int beginIndex, int maxLoggedExceptionsCount) {
        Fabric.getLogger().d(CrashlyticsCore.TAG, "Closing open sessions.");
        for (int i = beginIndex; i < sessionBeginFiles.length; i++) {
            File sessionBeginFile = sessionBeginFiles[i];
            String sessionIdentifier = getSessionIdFromSessionFile(sessionBeginFile);
            Logger logger = Fabric.getLogger();
            String str = CrashlyticsCore.TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Closing session: ");
            stringBuilder.append(sessionIdentifier);
            logger.d(str, stringBuilder.toString());
            writeSessionPartsToSessionFile(sessionBeginFile, sessionIdentifier, maxLoggedExceptionsCount);
        }
    }

    private void closeWithoutRenamingOrLog(ClsFileOutputStream fos) {
        if (fos != null) {
            try {
                fos.closeInProgressStream();
            } catch (IOException ex) {
                Fabric.getLogger().e(CrashlyticsCore.TAG, "Error closing session file stream in the presence of an exception", ex);
            }
        }
    }

    private void deleteSessionPartFilesFor(String sessionId) {
        for (File file : listSessionPartFilesFor(sessionId)) {
            file.delete();
        }
    }

    private File[] listSessionPartFilesFor(String sessionId) {
        return listFilesMatching(new SessionPartFileFilter(sessionId));
    }

    File[] listCompleteSessionFiles() {
        List<File> completeSessionFiles = new LinkedList();
        Collections.addAll(completeSessionFiles, listFilesMatching(getFatalSessionFilesDir(), SESSION_FILE_FILTER));
        Collections.addAll(completeSessionFiles, listFilesMatching(getNonFatalSessionFilesDir(), SESSION_FILE_FILTER));
        Collections.addAll(completeSessionFiles, listFilesMatching(getFilesDir(), SESSION_FILE_FILTER));
        return (File[]) completeSessionFiles.toArray(new File[completeSessionFiles.size()]);
    }

    File[] listSessionBeginFiles() {
        return listFilesMatching(new FileNameContainsFilter(SESSION_BEGIN_TAG));
    }

    private File[] listSortedSessionBeginFiles() {
        File[] sessionBeginFiles = listSessionBeginFiles();
        Arrays.sort(sessionBeginFiles, LARGEST_FILE_NAME_FIRST);
        return sessionBeginFiles;
    }

    private File[] listFilesMatching(FilenameFilter filter) {
        return listFilesMatching(getFilesDir(), filter);
    }

    private File[] listFilesMatching(File directory, FilenameFilter filter) {
        return ensureFileArrayNotNull(directory.listFiles(filter));
    }

    private File[] listFiles(File directory) {
        return ensureFileArrayNotNull(directory.listFiles());
    }

    private File[] ensureFileArrayNotNull(File[] files) {
        return files == null ? new File[0] : files;
    }

    private void trimSessionEventFiles(String sessionId, int limit) {
        File filesDir = getFilesDir();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(sessionId);
        stringBuilder.append(SESSION_NON_FATAL_TAG);
        Utils.capFileCount(filesDir, new FileNameContainsFilter(stringBuilder.toString()), limit, SMALLEST_FILE_NAME_FIRST);
    }

    void trimSessionFiles(int maxCompleteSessionsCount) {
        int remaining = maxCompleteSessionsCount;
        remaining -= Utils.capFileCount(getFatalSessionFilesDir(), remaining, SMALLEST_FILE_NAME_FIRST);
        Utils.capFileCount(getFilesDir(), SESSION_FILE_FILTER, remaining - Utils.capFileCount(getNonFatalSessionFilesDir(), remaining, SMALLEST_FILE_NAME_FIRST), SMALLEST_FILE_NAME_FIRST);
    }

    private void trimOpenSessions(int maxOpenSessionCount) {
        Set<String> sessionIdsToKeep = new HashSet();
        File[] beginSessionFiles = listSortedSessionBeginFiles();
        int count = Math.min(maxOpenSessionCount, beginSessionFiles.length);
        for (int i = 0; i < count; i++) {
            sessionIdsToKeep.add(getSessionIdFromSessionFile(beginSessionFiles[i]));
        }
        this.logFileManager.discardOldLogFiles(sessionIdsToKeep);
        retainSessions(listFilesMatching(new AnySessionPartFileFilter()), sessionIdsToKeep);
    }

    private void retainSessions(File[] files, Set<String> sessionIdsToKeep) {
        int length = files.length;
        int i = 0;
        while (i < length) {
            File sessionPartFile = files[i];
            String fileName = sessionPartFile.getName();
            Matcher matcher = SESSION_FILE_PATTERN.matcher(fileName);
            if (matcher.matches()) {
                if (!sessionIdsToKeep.contains(matcher.group(1))) {
                    Logger logger = Fabric.getLogger();
                    String str = CrashlyticsCore.TAG;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Trimming session file: ");
                    stringBuilder.append(fileName);
                    logger.d(str, stringBuilder.toString());
                    sessionPartFile.delete();
                }
                i++;
            } else {
                Logger logger2 = Fabric.getLogger();
                String str2 = CrashlyticsCore.TAG;
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("Deleting unknown file: ");
                stringBuilder2.append(fileName);
                logger2.d(str2, stringBuilder2.toString());
                sessionPartFile.delete();
                return;
            }
        }
    }

    private File[] getTrimmedNonFatalFiles(String sessionId, File[] nonFatalFiles, int maxLoggedExceptionsCount) {
        if (nonFatalFiles.length <= maxLoggedExceptionsCount) {
            return nonFatalFiles;
        }
        Fabric.getLogger().d(CrashlyticsCore.TAG, String.format(Locale.US, "Trimming down to %d logged exceptions.", new Object[]{Integer.valueOf(maxLoggedExceptionsCount)}));
        trimSessionEventFiles(sessionId, maxLoggedExceptionsCount);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(sessionId);
        stringBuilder.append(SESSION_NON_FATAL_TAG);
        return listFilesMatching(new FileNameContainsFilter(stringBuilder.toString()));
    }

    void cleanInvalidTempFiles() {
        this.backgroundWorker.submit(new Runnable() {
            public void run() {
                CrashlyticsController.this.doCleanInvalidTempFiles(CrashlyticsController.this.listFilesMatching(new InvalidPartFileFilter()));
            }
        });
    }

    void doCleanInvalidTempFiles(File[] invalidFiles) {
        final Set<String> invalidSessionIds = new HashSet();
        for (File invalidFile : invalidFiles) {
            Logger logger = Fabric.getLogger();
            String str = CrashlyticsCore.TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Found invalid session part file: ");
            stringBuilder.append(invalidFile);
            logger.d(str, stringBuilder.toString());
            invalidSessionIds.add(getSessionIdFromSessionFile(invalidFile));
        }
        if (!invalidSessionIds.isEmpty()) {
            File invalidFilesDir = getInvalidFilesDir();
            if (!invalidFilesDir.exists()) {
                invalidFilesDir.mkdir();
            }
            for (File sessionFile : listFilesMatching(new FilenameFilter() {
                public boolean accept(File dir, String filename) {
                    if (filename.length() < 35) {
                        return false;
                    }
                    return invalidSessionIds.contains(filename.substring(0, 35));
                }
            })) {
                Logger logger2 = Fabric.getLogger();
                String str2 = CrashlyticsCore.TAG;
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("Moving session file: ");
                stringBuilder2.append(sessionFile);
                logger2.d(str2, stringBuilder2.toString());
                if (!sessionFile.renameTo(new File(invalidFilesDir, sessionFile.getName()))) {
                    logger2 = Fabric.getLogger();
                    str2 = CrashlyticsCore.TAG;
                    stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("Could not move session file. Deleting ");
                    stringBuilder2.append(sessionFile);
                    logger2.d(str2, stringBuilder2.toString());
                    sessionFile.delete();
                }
            }
            trimInvalidSessionFiles();
        }
    }

    private void trimInvalidSessionFiles() {
        File invalidFilesDir = getInvalidFilesDir();
        if (invalidFilesDir.exists()) {
            File[] oldInvalidFiles = listFilesMatching(invalidFilesDir, new InvalidPartFileFilter());
            Arrays.sort(oldInvalidFiles, Collections.reverseOrder());
            Set<String> sessionIdsToKeep = new HashSet();
            for (int i = 0; i < oldInvalidFiles.length && sessionIdsToKeep.size() < 4; i++) {
                sessionIdsToKeep.add(getSessionIdFromSessionFile(oldInvalidFiles[i]));
            }
            retainSessions(listFiles(invalidFilesDir), sessionIdsToKeep);
        }
    }

    private void writeFatal(Date time, Thread thread, Throwable ex) {
        Exception e;
        Throwable th;
        ClsFileOutputStream fos = null;
        CodedOutputStream cos = null;
        try {
            String currentSessionId = getCurrentSessionId();
            if (currentSessionId == null) {
                Fabric.getLogger().e(CrashlyticsCore.TAG, "Tried to write a fatal exception while no session was open.", null);
                CommonUtils.flushOrLog(cos, "Failed to flush to session begin file.");
                CommonUtils.closeOrLog(null, "Failed to close fatal exception file output stream.");
                return;
            }
            recordFatalExceptionAnswersEvent(currentSessionId, ex.getClass().getName());
            File filesDir = getFilesDir();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(currentSessionId);
            stringBuilder.append(SESSION_FATAL_TAG);
            fos = new ClsFileOutputStream(filesDir, stringBuilder.toString());
            CodedOutputStream cos2 = CodedOutputStream.newInstance((OutputStream) fos);
            try {
                writeSessionEvent(cos2, time, thread, ex, "crash", true);
                CommonUtils.flushOrLog(cos2, "Failed to flush to session begin file.");
                CommonUtils.closeOrLog(fos, "Failed to close fatal exception file output stream.");
            } catch (Exception e2) {
                Exception exception = e2;
                cos = cos2;
                e = exception;
                try {
                    Fabric.getLogger().e(CrashlyticsCore.TAG, "An error occurred in the fatal exception logger", e);
                    CommonUtils.flushOrLog(cos, "Failed to flush to session begin file.");
                    CommonUtils.closeOrLog(fos, "Failed to close fatal exception file output stream.");
                } catch (Throwable th2) {
                    th = th2;
                    CommonUtils.flushOrLog(cos, "Failed to flush to session begin file.");
                    CommonUtils.closeOrLog(fos, "Failed to close fatal exception file output stream.");
                    throw th;
                }
            } catch (Throwable th3) {
                Throwable th4 = th3;
                cos = cos2;
                th = th4;
                CommonUtils.flushOrLog(cos, "Failed to flush to session begin file.");
                CommonUtils.closeOrLog(fos, "Failed to close fatal exception file output stream.");
                throw th;
            }
        } catch (Exception e3) {
            e = e3;
            Fabric.getLogger().e(CrashlyticsCore.TAG, "An error occurred in the fatal exception logger", e);
            CommonUtils.flushOrLog(cos, "Failed to flush to session begin file.");
            CommonUtils.closeOrLog(fos, "Failed to close fatal exception file output stream.");
        }
    }

    void writeExternalCrashEvent(final SessionEventData crashEventData) {
        this.backgroundWorker.submit(new Callable<Void>() {
            public Void call() throws Exception {
                if (!CrashlyticsController.this.isHandlingException()) {
                    CrashlyticsController.this.doWriteExternalCrashEvent(crashEventData);
                }
                return null;
            }
        });
    }

    private void doWriteExternalCrashEvent(SessionEventData crashEventData) throws IOException {
        ClsFileOutputStream fos = null;
        CodedOutputStream cos = null;
        try {
            String previousSessionId = getPreviousSessionId();
            if (previousSessionId == null) {
                Fabric.getLogger().e(CrashlyticsCore.TAG, "Tried to write a native crash while no session was open.", null);
                CommonUtils.flushOrLog(cos, "Failed to flush to session begin file.");
                CommonUtils.closeOrLog(null, "Failed to close fatal exception file output stream.");
                return;
            }
            r5 = new Object[2];
            boolean z = false;
            r5[0] = crashEventData.signal.code;
            r5[1] = crashEventData.signal.name;
            recordFatalExceptionAnswersEvent(previousSessionId, String.format(Locale.US, "<native-crash [%s (%s)]>", r5));
            if (crashEventData.binaryImages != null && crashEventData.binaryImages.length > 0) {
                z = true;
            }
            String fileTag = z ? SESSION_FATAL_TAG : SESSION_EVENT_MISSING_BINARY_IMGS_TAG;
            File filesDir = getFilesDir();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(previousSessionId);
            stringBuilder.append(fileTag);
            fos = new ClsFileOutputStream(filesDir, stringBuilder.toString());
            cos = CodedOutputStream.newInstance((OutputStream) fos);
            NativeCrashWriter.writeNativeCrash(crashEventData, new LogFileManager(this.crashlyticsCore.getContext(), this.logFileDirectoryProvider, previousSessionId), new MetaDataStore(getFilesDir()).readKeyData(previousSessionId), cos);
            CommonUtils.flushOrLog(cos, "Failed to flush to session begin file.");
            CommonUtils.closeOrLog(fos, "Failed to close fatal exception file output stream.");
        } catch (Exception e) {
            Fabric.getLogger().e(CrashlyticsCore.TAG, "An error occurred in the native crash logger", e);
        } catch (Throwable th) {
            CommonUtils.flushOrLog(cos, "Failed to flush to session begin file.");
            CommonUtils.closeOrLog(null, "Failed to close fatal exception file output stream.");
        }
    }

    private void doWriteNonFatal(Date time, Thread thread, Throwable ex) {
        Exception e;
        CodedOutputStream cos;
        Exception e2;
        ClsFileOutputStream clsFileOutputStream;
        Throwable th;
        Throwable th2;
        String currentSessionId = getCurrentSessionId();
        if (currentSessionId == null) {
            Fabric.getLogger().e(CrashlyticsCore.TAG, "Tried to write a non-fatal exception while no session was open.", null);
            return;
        }
        recordLoggedExceptionAnswersEvent(currentSessionId, ex.getClass().getName());
        ClsFileOutputStream fos = null;
        Throwable th3;
        try {
            OutputStream fos2;
            Logger logger = Fabric.getLogger();
            String str = CrashlyticsCore.TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Crashlytics is logging non-fatal exception \"");
            th3 = ex;
            try {
                stringBuilder.append(th3);
                stringBuilder.append("\" from thread ");
                stringBuilder.append(thread.getName());
                logger.d(str, stringBuilder.toString());
                String counterString = CommonUtils.padWithZerosToMaxIntWidth(r8.eventCounter.getAndIncrement());
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append(currentSessionId);
                stringBuilder2.append(SESSION_NON_FATAL_TAG);
                stringBuilder2.append(counterString);
                fos2 = new ClsFileOutputStream(getFilesDir(), stringBuilder2.toString());
            } catch (Exception e3) {
                e = e3;
                cos = null;
                e2 = e;
                try {
                    Fabric.getLogger().e(CrashlyticsCore.TAG, "An error occurred in the non-fatal exception logger", e2);
                    CommonUtils.flushOrLog(cos, "Failed to flush to non-fatal file.");
                    CommonUtils.closeOrLog(fos, "Failed to close non-fatal file output stream.");
                    clsFileOutputStream = fos;
                    trimSessionEventFiles(currentSessionId, 64);
                } catch (Throwable th4) {
                    th = th4;
                    th2 = th;
                    CommonUtils.flushOrLog(cos, "Failed to flush to non-fatal file.");
                    CommonUtils.closeOrLog(fos, "Failed to close non-fatal file output stream.");
                    throw th2;
                }
            } catch (Throwable th5) {
                th = th5;
                cos = null;
                th2 = th;
                CommonUtils.flushOrLog(cos, "Failed to flush to non-fatal file.");
                CommonUtils.closeOrLog(fos, "Failed to close non-fatal file output stream.");
                throw th2;
            }
            try {
                cos = CodedOutputStream.newInstance(fos2);
            } catch (Exception e4) {
                e = e4;
                cos = null;
                fos = fos2;
                e2 = e;
                Fabric.getLogger().e(CrashlyticsCore.TAG, "An error occurred in the non-fatal exception logger", e2);
                CommonUtils.flushOrLog(cos, "Failed to flush to non-fatal file.");
                CommonUtils.closeOrLog(fos, "Failed to close non-fatal file output stream.");
                clsFileOutputStream = fos;
                trimSessionEventFiles(currentSessionId, 64);
            } catch (Throwable th6) {
                th = th6;
                cos = null;
                fos = fos2;
                th2 = th;
                CommonUtils.flushOrLog(cos, "Failed to flush to non-fatal file.");
                CommonUtils.closeOrLog(fos, "Failed to close non-fatal file output stream.");
                throw th2;
            }
            try {
                writeSessionEvent(cos, time, thread, th3, "error", false);
                CommonUtils.flushOrLog(cos, "Failed to flush to non-fatal file.");
                CommonUtils.closeOrLog(fos2, "Failed to close non-fatal file output stream.");
            } catch (Exception e5) {
                e2 = e5;
                fos = fos2;
                Fabric.getLogger().e(CrashlyticsCore.TAG, "An error occurred in the non-fatal exception logger", e2);
                CommonUtils.flushOrLog(cos, "Failed to flush to non-fatal file.");
                CommonUtils.closeOrLog(fos, "Failed to close non-fatal file output stream.");
                clsFileOutputStream = fos;
                trimSessionEventFiles(currentSessionId, 64);
            } catch (Throwable th7) {
                th2 = th7;
                fos = fos2;
                CommonUtils.flushOrLog(cos, "Failed to flush to non-fatal file.");
                CommonUtils.closeOrLog(fos, "Failed to close non-fatal file output stream.");
                throw th2;
            }
        } catch (Exception e6) {
            e5 = e6;
            th3 = ex;
            cos = null;
            e2 = e5;
            Fabric.getLogger().e(CrashlyticsCore.TAG, "An error occurred in the non-fatal exception logger", e2);
            CommonUtils.flushOrLog(cos, "Failed to flush to non-fatal file.");
            CommonUtils.closeOrLog(fos, "Failed to close non-fatal file output stream.");
            clsFileOutputStream = fos;
            trimSessionEventFiles(currentSessionId, 64);
        } catch (Throwable th8) {
            th7 = th8;
            th3 = ex;
            cos = null;
            th2 = th7;
            CommonUtils.flushOrLog(cos, "Failed to flush to non-fatal file.");
            CommonUtils.closeOrLog(fos, "Failed to close non-fatal file output stream.");
            throw th2;
        }
        try {
            trimSessionEventFiles(currentSessionId, 64);
        } catch (Exception e52) {
            Fabric.getLogger().e(CrashlyticsCore.TAG, "An error occurred when trimming non-fatal files.", e52);
        }
    }

    private void writeBeginSession(String sessionId, Date startedAt) throws Exception {
        FileOutputStream fos = null;
        CodedOutputStream cos = null;
        try {
            File filesDir = getFilesDir();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(sessionId);
            stringBuilder.append(SESSION_BEGIN_TAG);
            fos = new ClsFileOutputStream(filesDir, stringBuilder.toString());
            cos = CodedOutputStream.newInstance((OutputStream) fos);
            SessionProtobufHelper.writeBeginSession(cos, sessionId, String.format(Locale.US, GENERATOR_FORMAT, new Object[]{this.crashlyticsCore.getVersion()}), startedAt.getTime() / 1000);
        } finally {
            CommonUtils.flushOrLog(cos, "Failed to flush to session begin file.");
            CommonUtils.closeOrLog(fos, "Failed to close begin session file.");
        }
    }

    private void writeSessionApp(String sessionId) throws Exception {
        FileOutputStream fos = null;
        CodedOutputStream cos = null;
        try {
            File filesDir = getFilesDir();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(sessionId);
            stringBuilder.append(SESSION_APP_TAG);
            fos = new ClsFileOutputStream(filesDir, stringBuilder.toString());
            cos = CodedOutputStream.newInstance((OutputStream) fos);
            CodedOutputStream codedOutputStream = cos;
            SessionProtobufHelper.writeSessionApp(codedOutputStream, this.idManager.getAppIdentifier(), this.appData.apiKey, this.appData.versionCode, this.appData.versionName, this.idManager.getAppInstallIdentifier(), DeliveryMechanism.determineFrom(this.appData.installerPackageName).getId(), this.unityVersion);
        } finally {
            CommonUtils.flushOrLog(cos, "Failed to flush to session app file.");
            CommonUtils.closeOrLog(fos, "Failed to close session app file.");
        }
    }

    private void writeSessionOS(String sessionId) throws Exception {
        FileOutputStream fos = null;
        CodedOutputStream cos = null;
        try {
            File filesDir = getFilesDir();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(sessionId);
            stringBuilder.append(SESSION_OS_TAG);
            fos = new ClsFileOutputStream(filesDir, stringBuilder.toString());
            cos = CodedOutputStream.newInstance((OutputStream) fos);
            SessionProtobufHelper.writeSessionOS(cos, CommonUtils.isRooted(this.crashlyticsCore.getContext()));
        } finally {
            CommonUtils.flushOrLog(cos, "Failed to flush to session OS file.");
            CommonUtils.closeOrLog(fos, "Failed to close session OS file.");
        }
    }

    private void writeSessionDevice(String sessionId) throws Exception {
        CrashlyticsController crashlyticsController = this;
        FileOutputStream fos = null;
        CodedOutputStream cos = null;
        try {
            File filesDir = getFilesDir();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(sessionId);
            stringBuilder.append(SESSION_DEVICE_TAG);
            fos = new ClsFileOutputStream(filesDir, stringBuilder.toString());
            cos = CodedOutputStream.newInstance((OutputStream) fos);
            Context context = crashlyticsController.crashlyticsCore.getContext();
            StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
            String clsDeviceId = crashlyticsController.idManager.getDeviceUUID();
            String clsDeviceId2 = clsDeviceId;
            long diskSpace = ((long) statFs.getBlockCount()) * ((long) statFs.getBlockSize());
            CodedOutputStream codedOutputStream = cos;
            long j = diskSpace;
            clsDeviceId = clsDeviceId2;
            SessionProtobufHelper.writeSessionDevice(codedOutputStream, clsDeviceId, CommonUtils.getCpuArchitectureInt(), Build.MODEL, Runtime.getRuntime().availableProcessors(), CommonUtils.getTotalRamInBytes(), j, CommonUtils.isEmulator(context), crashlyticsController.idManager.getDeviceIdentifiers(), CommonUtils.getDeviceState(context), Build.MANUFACTURER, Build.PRODUCT);
            CommonUtils.flushOrLog(cos, "Failed to flush session device info.");
            CommonUtils.closeOrLog(fos, "Failed to close session device file.");
        } catch (Throwable th) {
            FileOutputStream fos2 = fos;
            Throwable fos3 = th;
            CommonUtils.flushOrLog(cos, "Failed to flush session device info.");
            CommonUtils.closeOrLog(fos2, "Failed to close session device file.");
        }
    }

    private void writeSessionUser(String sessionId) throws Exception {
        FileOutputStream fos = null;
        CodedOutputStream cos = null;
        try {
            File filesDir = getFilesDir();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(sessionId);
            stringBuilder.append(SESSION_USER_TAG);
            fos = new ClsFileOutputStream(filesDir, stringBuilder.toString());
            cos = CodedOutputStream.newInstance((OutputStream) fos);
            UserMetaData userMetaData = getUserMetaData(sessionId);
            if (!userMetaData.isEmpty()) {
                SessionProtobufHelper.writeSessionUser(cos, userMetaData.id, userMetaData.name, userMetaData.email);
                CommonUtils.flushOrLog(cos, "Failed to flush session user file.");
                CommonUtils.closeOrLog(fos, "Failed to close session user file.");
            }
        } finally {
            CommonUtils.flushOrLog(cos, "Failed to flush session user file.");
            CommonUtils.closeOrLog(fos, "Failed to close session user file.");
        }
    }

    private void writeSessionEvent(CodedOutputStream cos, Date time, Thread thread, Throwable ex, String eventType, boolean includeAllThreads) throws Exception {
        Thread[] threads;
        TreeMap attributes;
        TrimmedThrowableData trimmedEx = new TrimmedThrowableData(ex, this.stackTraceTrimmingStrategy);
        Context context = this.crashlyticsCore.getContext();
        long eventTime = time.getTime() / 1000;
        Float batteryLevel = CommonUtils.getBatteryLevel(context);
        int batteryVelocity = CommonUtils.getBatteryVelocity(context, this.devicePowerStateListener.isPowerConnected());
        boolean proximityEnabled = CommonUtils.getProximitySensorEnabled(context);
        int orientation = context.getResources().getConfiguration().orientation;
        long usedRamBytes = CommonUtils.getTotalRamInBytes() - CommonUtils.calculateFreeRamInBytes(context);
        long diskUsedBytes = CommonUtils.calculateUsedDiskSpaceInBytes(Environment.getDataDirectory().getPath());
        RunningAppProcessInfo runningAppProcessInfo = CommonUtils.getAppProcessInfo(context.getPackageName(), context);
        List<StackTraceElement[]> stacks = new LinkedList();
        StackTraceElement[] exceptionStack = trimmedEx.stacktrace;
        String buildId = this.appData.buildId;
        String appIdentifier = this.idManager.getAppIdentifier();
        if (includeAllThreads) {
            Map<Thread, StackTraceElement[]> allStackTraces = Thread.getAllStackTraces();
            Thread[] threads2 = new Thread[allStackTraces.size()];
            int i = 0;
            for (Entry<Thread, StackTraceElement[]> entry : allStackTraces.entrySet()) {
                threads2[i] = (Thread) entry.getKey();
                stacks.add(r0.stackTraceTrimmingStrategy.getTrimmedStackTrace((StackTraceElement[]) entry.getValue()));
                i++;
            }
            threads = threads2;
        } else {
            threads = new Thread[0];
        }
        if (CommonUtils.getBooleanResourceValue(context, COLLECT_CUSTOM_KEYS, true)) {
            attributes = r0.crashlyticsCore.getAttributes();
            if (attributes != null && attributes.size() > 1) {
                attributes = new TreeMap(attributes);
            }
        } else {
            attributes = new TreeMap();
        }
        Map attributes2 = attributes;
        int orientation2 = orientation;
        SessionProtobufHelper.writeSessionEvent(cos, eventTime, eventType, trimmedEx, thread, exceptionStack, threads, stacks, attributes2, r0.logFileManager, runningAppProcessInfo, orientation2, appIdentifier, buildId, batteryLevel, batteryVelocity, proximityEnabled, usedRamBytes, diskUsedBytes);
    }

    private void writeSessionPartsToSessionFile(File sessionBeginFile, String sessionId, int maxLoggedExceptionsCount) {
        Logger logger;
        String str;
        StringBuilder stringBuilder;
        Logger logger2 = Fabric.getLogger();
        String str2 = CrashlyticsCore.TAG;
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("Collecting session parts for ID ");
        stringBuilder2.append(sessionId);
        logger2.d(str2, stringBuilder2.toString());
        StringBuilder stringBuilder3 = new StringBuilder();
        stringBuilder3.append(sessionId);
        stringBuilder3.append(SESSION_FATAL_TAG);
        File[] fatalFiles = listFilesMatching(new FileNameContainsFilter(stringBuilder3.toString()));
        boolean hasFatal = fatalFiles != null && fatalFiles.length > 0;
        Fabric.getLogger().d(CrashlyticsCore.TAG, String.format(Locale.US, "Session %s has fatal exception: %s", new Object[]{sessionId, Boolean.valueOf(hasFatal)}));
        StringBuilder stringBuilder4 = new StringBuilder();
        stringBuilder4.append(sessionId);
        stringBuilder4.append(SESSION_NON_FATAL_TAG);
        File[] nonFatalFiles = listFilesMatching(new FileNameContainsFilter(stringBuilder4.toString()));
        boolean hasNonFatal = nonFatalFiles != null && nonFatalFiles.length > 0;
        Fabric.getLogger().d(CrashlyticsCore.TAG, String.format(Locale.US, "Session %s has non-fatal exceptions: %s", new Object[]{sessionId, Boolean.valueOf(hasNonFatal)}));
        if (!hasFatal) {
            if (!hasNonFatal) {
                logger = Fabric.getLogger();
                str = CrashlyticsCore.TAG;
                stringBuilder = new StringBuilder();
                stringBuilder.append("No events present for session ID ");
                stringBuilder.append(sessionId);
                logger.d(str, stringBuilder.toString());
                logger = Fabric.getLogger();
                str = CrashlyticsCore.TAG;
                stringBuilder = new StringBuilder();
                stringBuilder.append("Removing session part files for ID ");
                stringBuilder.append(sessionId);
                logger.d(str, stringBuilder.toString());
                deleteSessionPartFilesFor(sessionId);
            }
        }
        synthesizeSessionFile(sessionBeginFile, sessionId, getTrimmedNonFatalFiles(sessionId, nonFatalFiles, maxLoggedExceptionsCount), hasFatal ? fatalFiles[0] : null);
        logger = Fabric.getLogger();
        str = CrashlyticsCore.TAG;
        stringBuilder = new StringBuilder();
        stringBuilder.append("Removing session part files for ID ");
        stringBuilder.append(sessionId);
        logger.d(str, stringBuilder.toString());
        deleteSessionPartFilesFor(sessionId);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void synthesizeSessionFile(java.io.File r12, java.lang.String r13, java.io.File[] r14, java.io.File r15) {
        /*
        r11 = this;
        r0 = 1;
        if (r15 == 0) goto L_0x0005;
    L_0x0003:
        r1 = 1;
        goto L_0x0006;
    L_0x0005:
        r1 = 0;
    L_0x0006:
        r2 = 0;
        if (r1 == 0) goto L_0x000e;
    L_0x0009:
        r3 = r11.getFatalSessionFilesDir();
        goto L_0x0012;
    L_0x000e:
        r3 = r11.getNonFatalSessionFilesDir();
    L_0x0012:
        r4 = r3.exists();
        if (r4 != 0) goto L_0x001b;
    L_0x0018:
        r3.mkdirs();
    L_0x001b:
        r4 = 0;
        r5 = 0;
        r6 = new com.crashlytics.android.core.ClsFileOutputStream;	 Catch:{ Exception -> 0x0082 }
        r6.<init>(r3, r13);	 Catch:{ Exception -> 0x0082 }
        r4 = r6;
        r6 = com.crashlytics.android.core.CodedOutputStream.newInstance(r4);	 Catch:{ Exception -> 0x0082 }
        r5 = r6;
        r6 = io.fabric.sdk.android.Fabric.getLogger();	 Catch:{ Exception -> 0x0082 }
        r7 = "CrashlyticsCore";
        r8 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0082 }
        r8.<init>();	 Catch:{ Exception -> 0x0082 }
        r9 = "Collecting SessionStart data for session ID ";
        r8.append(r9);	 Catch:{ Exception -> 0x0082 }
        r8.append(r13);	 Catch:{ Exception -> 0x0082 }
        r8 = r8.toString();	 Catch:{ Exception -> 0x0082 }
        r6.d(r7, r8);	 Catch:{ Exception -> 0x0082 }
        writeToCosFromFile(r5, r12);	 Catch:{ Exception -> 0x0082 }
        r6 = 4;
        r7 = new java.util.Date;	 Catch:{ Exception -> 0x0082 }
        r7.<init>();	 Catch:{ Exception -> 0x0082 }
        r7 = r7.getTime();	 Catch:{ Exception -> 0x0082 }
        r9 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r7 = r7 / r9;
        r5.writeUInt64(r6, r7);	 Catch:{ Exception -> 0x0082 }
        r6 = 5;
        r5.writeBool(r6, r1);	 Catch:{ Exception -> 0x0082 }
        r6 = 11;
        r5.writeUInt32(r6, r0);	 Catch:{ Exception -> 0x0082 }
        r0 = 12;
        r6 = 3;
        r5.writeEnum(r0, r6);	 Catch:{ Exception -> 0x0082 }
        r11.writeInitialPartsTo(r5, r13);	 Catch:{ Exception -> 0x0082 }
        writeNonFatalEventsTo(r5, r14, r13);	 Catch:{ Exception -> 0x0082 }
        if (r1 == 0) goto L_0x006f;
    L_0x006c:
        writeToCosFromFile(r5, r15);	 Catch:{ Exception -> 0x0082 }
    L_0x006f:
        r0 = "Error flushing session file stream";
        io.fabric.sdk.android.services.common.CommonUtils.flushOrLog(r5, r0);
        if (r2 == 0) goto L_0x007a;
    L_0x0076:
        r11.closeWithoutRenamingOrLog(r4);
        goto L_0x00a6;
    L_0x007a:
        r0 = "Failed to close CLS file";
        io.fabric.sdk.android.services.common.CommonUtils.closeOrLog(r4, r0);
        goto L_0x00a6;
    L_0x0080:
        r0 = move-exception;
        goto L_0x00a7;
    L_0x0082:
        r0 = move-exception;
        r6 = io.fabric.sdk.android.Fabric.getLogger();	 Catch:{ all -> 0x0080 }
        r7 = "CrashlyticsCore";
        r8 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0080 }
        r8.<init>();	 Catch:{ all -> 0x0080 }
        r9 = "Failed to write session file for session ID: ";
        r8.append(r9);	 Catch:{ all -> 0x0080 }
        r8.append(r13);	 Catch:{ all -> 0x0080 }
        r8 = r8.toString();	 Catch:{ all -> 0x0080 }
        r6.e(r7, r8, r0);	 Catch:{ all -> 0x0080 }
        r2 = 1;
        r0 = "Error flushing session file stream";
        io.fabric.sdk.android.services.common.CommonUtils.flushOrLog(r5, r0);
        if (r2 == 0) goto L_0x007a;
    L_0x00a5:
        goto L_0x0076;
    L_0x00a6:
        return;
    L_0x00a7:
        r6 = "Error flushing session file stream";
        io.fabric.sdk.android.services.common.CommonUtils.flushOrLog(r5, r6);
        if (r2 == 0) goto L_0x00b2;
    L_0x00ae:
        r11.closeWithoutRenamingOrLog(r4);
        goto L_0x00b7;
    L_0x00b2:
        r6 = "Failed to close CLS file";
        io.fabric.sdk.android.services.common.CommonUtils.closeOrLog(r4, r6);
    L_0x00b7:
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.crashlytics.android.core.CrashlyticsController.synthesizeSessionFile(java.io.File, java.lang.String, java.io.File[], java.io.File):void");
    }

    private static void writeNonFatalEventsTo(CodedOutputStream cos, File[] nonFatalFiles, String sessionId) {
        Arrays.sort(nonFatalFiles, CommonUtils.FILE_MODIFIED_COMPARATOR);
        for (File nonFatalFile : nonFatalFiles) {
            try {
                Fabric.getLogger().d(CrashlyticsCore.TAG, String.format(Locale.US, "Found Non Fatal for session ID %s in %s ", new Object[]{sessionId, nonFatalFile.getName()}));
                writeToCosFromFile(cos, nonFatalFile);
            } catch (Exception e) {
                Fabric.getLogger().e(CrashlyticsCore.TAG, "Error writting non-fatal to session.", e);
            }
        }
    }

    private void writeInitialPartsTo(CodedOutputStream cos, String sessionId) throws IOException {
        for (String tag : INITIAL_SESSION_PART_TAGS) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(sessionId);
            stringBuilder.append(tag);
            File[] sessionPartFiles = listFilesMatching(new FileNameContainsFilter(stringBuilder.toString()));
            Logger logger;
            String str;
            StringBuilder stringBuilder2;
            if (sessionPartFiles.length == 0) {
                logger = Fabric.getLogger();
                str = CrashlyticsCore.TAG;
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append("Can't find ");
                stringBuilder2.append(tag);
                stringBuilder2.append(" data for session ID ");
                stringBuilder2.append(sessionId);
                logger.e(str, stringBuilder2.toString(), null);
            } else {
                logger = Fabric.getLogger();
                str = CrashlyticsCore.TAG;
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append("Collecting ");
                stringBuilder2.append(tag);
                stringBuilder2.append(" data for session ID ");
                stringBuilder2.append(sessionId);
                logger.d(str, stringBuilder2.toString());
                writeToCosFromFile(cos, sessionPartFiles[0]);
            }
        }
    }

    private static void writeToCosFromFile(CodedOutputStream cos, File file) throws IOException {
        if (file.exists()) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(file);
                copyToCodedOutputStream(fis, cos, (int) file.length());
            } finally {
                CommonUtils.closeOrLog(fis, "Failed to close file input stream.");
            }
        } else {
            Logger logger = Fabric.getLogger();
            String str = CrashlyticsCore.TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Tried to include a file that doesn't exist: ");
            stringBuilder.append(file.getName());
            logger.e(str, stringBuilder.toString(), null);
        }
    }

    private static void copyToCodedOutputStream(InputStream inStream, CodedOutputStream cos, int bufferLength) throws IOException {
        byte[] buffer = new byte[bufferLength];
        int offset = 0;
        while (offset < buffer.length) {
            int read = inStream.read(buffer, offset, buffer.length - offset);
            int numRead = read;
            if (read < 0) {
                break;
            }
            offset += numRead;
        }
        cos.writeRawBytes(buffer);
    }

    private UserMetaData getUserMetaData(String sessionId) {
        if (isHandlingException()) {
            return new UserMetaData(this.crashlyticsCore.getUserIdentifier(), this.crashlyticsCore.getUserName(), this.crashlyticsCore.getUserEmail());
        }
        return new MetaDataStore(getFilesDir()).readUserData(sessionId);
    }

    boolean isHandlingException() {
        return this.crashHandler != null && this.crashHandler.isHandlingException();
    }

    File getFilesDir() {
        return this.fileStore.getFilesDir();
    }

    File getFatalSessionFilesDir() {
        return new File(getFilesDir(), FATAL_SESSION_DIR);
    }

    File getNonFatalSessionFilesDir() {
        return new File(getFilesDir(), NONFATAL_SESSION_DIR);
    }

    File getInvalidFilesDir() {
        return new File(getFilesDir(), INVALID_CLS_CACHE_DIR);
    }

    private boolean shouldPromptUserBeforeSendingCrashReports(SettingsData settingsData) {
        boolean z = false;
        if (settingsData == null) {
            return false;
        }
        if (settingsData.featuresData.promptEnabled && !this.preferenceManager.shouldAlwaysSendReports()) {
            z = true;
        }
        return z;
    }

    private CreateReportSpiCall getCreateReportSpiCall(String reportsUrl) {
        return new DefaultCreateReportSpiCall(this.crashlyticsCore, CommonUtils.getStringsFileValue(this.crashlyticsCore.getContext(), CRASHLYTICS_API_ENDPOINT), reportsUrl, this.httpRequestFactory);
    }

    private void sendSessionReports(SettingsData settingsData) {
        if (settingsData == null) {
            Fabric.getLogger().w(CrashlyticsCore.TAG, "Cannot send reports. Settings are unavailable.");
            return;
        }
        Context context = this.crashlyticsCore.getContext();
        ReportUploader reportUploader = new ReportUploader(this.appData.apiKey, getCreateReportSpiCall(settingsData.appData.reportsUrl), this.reportFilesProvider, this.handlingExceptionCheck);
        for (File finishedSessionFile : listCompleteSessionFiles()) {
            this.backgroundWorker.submit(new SendReportRunnable(context, new SessionReport(finishedSessionFile, SEND_AT_CRASHTIME_HEADER), reportUploader));
        }
    }

    private static void recordLoggedExceptionAnswersEvent(String sessionId, String exceptionName) {
        Answers answers = (Answers) Fabric.getKit(Answers.class);
        if (answers == null) {
            Fabric.getLogger().d(CrashlyticsCore.TAG, "Answers is not available");
        } else {
            answers.onException(new LoggedException(sessionId, exceptionName));
        }
    }

    private static void recordFatalExceptionAnswersEvent(String sessionId, String exceptionName) {
        Answers answers = (Answers) Fabric.getKit(Answers.class);
        if (answers == null) {
            Fabric.getLogger().d(CrashlyticsCore.TAG, "Answers is not available");
        } else {
            answers.onException(new FatalException(sessionId, exceptionName));
        }
    }
}
