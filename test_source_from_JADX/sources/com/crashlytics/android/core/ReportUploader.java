package com.crashlytics.android.core;

import com.facebook.appevents.AppEventsConstants;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Logger;
import io.fabric.sdk.android.services.common.BackgroundPriorityRunnable;
import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

class ReportUploader {
    static final Map<String, String> HEADER_INVALID_CLS_FILE = Collections.singletonMap("X-CRASHLYTICS-INVALID-SESSION", AppEventsConstants.EVENT_PARAM_VALUE_YES);
    private static final short[] RETRY_INTERVALS = new short[]{(short) 10, (short) 20, (short) 30, (short) 60, (short) 120, (short) 300};
    private final String apiKey;
    private final CreateReportSpiCall createReportCall;
    private final Object fileAccessLock = new Object();
    private final HandlingExceptionCheck handlingExceptionCheck;
    private final ReportFilesProvider reportFilesProvider;
    private Thread uploadThread;

    interface HandlingExceptionCheck {
        boolean isHandlingException();
    }

    interface ReportFilesProvider {
        File[] getCompleteSessionFiles();

        File[] getInvalidSessionFiles();
    }

    interface SendCheck {
        boolean canSendReports();
    }

    static final class AlwaysSendCheck implements SendCheck {
        AlwaysSendCheck() {
        }

        public boolean canSendReports() {
            return true;
        }
    }

    private class Worker extends BackgroundPriorityRunnable {
        private final float delay;
        private final SendCheck sendCheck;

        Worker(float delay, SendCheck sendCheck) {
            this.delay = delay;
            this.sendCheck = sendCheck;
        }

        public void onRun() {
            try {
                attemptUploadWithRetry();
            } catch (Exception e) {
                Fabric.getLogger().e(CrashlyticsCore.TAG, "An unexpected error occurred while attempting to upload crash reports.", e);
            }
            ReportUploader.this.uploadThread = null;
        }

        private void attemptUploadWithRetry() {
            Logger logger = Fabric.getLogger();
            String str = CrashlyticsCore.TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Starting report processing in ");
            stringBuilder.append(this.delay);
            stringBuilder.append(" second(s)...");
            logger.d(str, stringBuilder.toString());
            if (this.delay > 0.0f) {
                try {
                    Thread.sleep((long) (this.delay * 1000.0f));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
            List<Report> reports = ReportUploader.this.findReports();
            if (!ReportUploader.this.handlingExceptionCheck.isHandlingException()) {
                if (reports.isEmpty() || this.sendCheck.canSendReports()) {
                    int retryCount = 0;
                    while (!reports.isEmpty() && !ReportUploader.this.handlingExceptionCheck.isHandlingException()) {
                        Logger logger2 = Fabric.getLogger();
                        String str2 = CrashlyticsCore.TAG;
                        StringBuilder stringBuilder2 = new StringBuilder();
                        stringBuilder2.append("Attempting to send ");
                        stringBuilder2.append(reports.size());
                        stringBuilder2.append(" report(s)");
                        logger2.d(str2, stringBuilder2.toString());
                        for (Report report : reports) {
                            ReportUploader.this.forceUpload(report);
                        }
                        reports = ReportUploader.this.findReports();
                        if (!reports.isEmpty()) {
                            int retryCount2 = retryCount + 1;
                            long interval = (long) ReportUploader.RETRY_INTERVALS[Math.min(retryCount, ReportUploader.RETRY_INTERVALS.length - 1)];
                            Logger logger3 = Fabric.getLogger();
                            String str3 = CrashlyticsCore.TAG;
                            StringBuilder stringBuilder3 = new StringBuilder();
                            stringBuilder3.append("Report submisson: scheduling delayed retry in ");
                            stringBuilder3.append(interval);
                            stringBuilder3.append(" seconds");
                            logger3.d(str3, stringBuilder3.toString());
                            try {
                                Thread.sleep(1000 * interval);
                                retryCount = retryCount2;
                            } catch (InterruptedException e2) {
                                Thread.currentThread().interrupt();
                                return;
                            }
                        }
                    }
                    return;
                }
                Logger logger4 = Fabric.getLogger();
                String str4 = CrashlyticsCore.TAG;
                StringBuilder stringBuilder4 = new StringBuilder();
                stringBuilder4.append("User declined to send. Removing ");
                stringBuilder4.append(reports.size());
                stringBuilder4.append(" Report(s).");
                logger4.d(str4, stringBuilder4.toString());
                for (Report report2 : reports) {
                    report2.remove();
                }
            }
        }
    }

    public ReportUploader(String apiKey, CreateReportSpiCall createReportCall, ReportFilesProvider reportFilesProvider, HandlingExceptionCheck handlingExceptionCheck) {
        if (createReportCall == null) {
            throw new IllegalArgumentException("createReportCall must not be null.");
        }
        this.createReportCall = createReportCall;
        this.apiKey = apiKey;
        this.reportFilesProvider = reportFilesProvider;
        this.handlingExceptionCheck = handlingExceptionCheck;
    }

    public synchronized void uploadReports(float delay, SendCheck sendCheck) {
        if (this.uploadThread != null) {
            Fabric.getLogger().d(CrashlyticsCore.TAG, "Report upload has already been started.");
            return;
        }
        this.uploadThread = new Thread(new Worker(delay, sendCheck), "Crashlytics Report Uploader");
        this.uploadThread.start();
    }

    boolean isUploading() {
        return this.uploadThread != null;
    }

    boolean forceUpload(Report report) {
        boolean removed = false;
        synchronized (this.fileAccessLock) {
            try {
                boolean sent = this.createReportCall.invoke(new CreateReportRequest(this.apiKey, report));
                Logger logger = Fabric.getLogger();
                String str = CrashlyticsCore.TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Crashlytics report upload ");
                stringBuilder.append(sent ? "complete: " : "FAILED: ");
                stringBuilder.append(report.getIdentifier());
                logger.i(str, stringBuilder.toString());
                if (sent) {
                    report.remove();
                    removed = true;
                }
            } catch (Exception e) {
                Logger logger2 = Fabric.getLogger();
                String str2 = CrashlyticsCore.TAG;
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("Error occurred sending report ");
                stringBuilder2.append(report);
                logger2.e(str2, stringBuilder2.toString(), e);
            }
        }
        return removed;
    }

    List<Report> findReports() {
        Fabric.getLogger().d(CrashlyticsCore.TAG, "Checking for crash reports...");
        synchronized (this.fileAccessLock) {
            File[] clsFiles = this.reportFilesProvider.getCompleteSessionFiles();
            File[] invalidClsFiles = this.reportFilesProvider.getInvalidSessionFiles();
        }
        List<Report> reports = new LinkedList();
        if (clsFiles != null) {
            for (File file : clsFiles) {
                Logger logger = Fabric.getLogger();
                String str = CrashlyticsCore.TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Found crash report ");
                stringBuilder.append(file.getPath());
                logger.d(str, stringBuilder.toString());
                reports.add(new SessionReport(file));
            }
        }
        Map<String, List<File>> invalidSessionFiles = new HashMap();
        if (invalidClsFiles != null) {
            for (File file2 : invalidClsFiles) {
                String sessionId;
                sessionId = CrashlyticsController.getSessionIdFromSessionFile(file2);
                if (!invalidSessionFiles.containsKey(sessionId)) {
                    invalidSessionFiles.put(sessionId, new LinkedList());
                }
                ((List) invalidSessionFiles.get(sessionId)).add(file2);
            }
        }
        for (String key : invalidSessionFiles.keySet()) {
            Logger logger2 = Fabric.getLogger();
            sessionId = CrashlyticsCore.TAG;
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("Found invalid session: ");
            stringBuilder2.append(key);
            logger2.d(sessionId, stringBuilder2.toString());
            List<File> invalidFiles = (List) invalidSessionFiles.get(key);
            reports.add(new InvalidSessionReport(key, (File[]) invalidFiles.toArray(new File[invalidFiles.size()])));
        }
        if (reports.isEmpty()) {
            Fabric.getLogger().d(CrashlyticsCore.TAG, "No reports found.");
        }
        return reports;
    }
}
