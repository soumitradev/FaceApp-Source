package com.crashlytics.android.answers;

import android.content.Context;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.Logger;
import io.fabric.sdk.android.services.common.ApiKey;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.events.FilesSender;
import io.fabric.sdk.android.services.events.TimeBasedFileRollOverRunnable;
import io.fabric.sdk.android.services.network.HttpRequestFactory;
import io.fabric.sdk.android.services.settings.AnalyticsSettingsData;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

class EnabledSessionAnalyticsManagerStrategy implements SessionAnalyticsManagerStrategy {
    static final int UNDEFINED_ROLLOVER_INTERVAL_SECONDS = -1;
    ApiKey apiKey = new ApiKey();
    private final Context context;
    boolean customEventsEnabled = true;
    EventFilter eventFilter = new KeepAllEventFilter();
    private final ScheduledExecutorService executorService;
    private final SessionAnalyticsFilesManager filesManager;
    FilesSender filesSender;
    private final HttpRequestFactory httpRequestFactory;
    private final Kit kit;
    final SessionEventMetadata metadata;
    boolean predefinedEventsEnabled = true;
    private final AtomicReference<ScheduledFuture<?>> rolloverFutureRef = new AtomicReference();
    volatile int rolloverIntervalSeconds = -1;

    public EnabledSessionAnalyticsManagerStrategy(Kit kit, Context context, ScheduledExecutorService executor, SessionAnalyticsFilesManager filesManager, HttpRequestFactory httpRequestFactory, SessionEventMetadata metadata) {
        this.kit = kit;
        this.context = context;
        this.executorService = executor;
        this.filesManager = filesManager;
        this.httpRequestFactory = httpRequestFactory;
        this.metadata = metadata;
    }

    public void setAnalyticsSettingsData(AnalyticsSettingsData analyticsSettingsData, String protocolAndHostOverride) {
        this.filesSender = AnswersRetryFilesSender.build(new SessionAnalyticsFilesSender(this.kit, protocolAndHostOverride, analyticsSettingsData.analyticsURL, this.httpRequestFactory, this.apiKey.getValue(this.context)));
        this.filesManager.setAnalyticsSettingsData(analyticsSettingsData);
        this.customEventsEnabled = analyticsSettingsData.trackCustomEvents;
        Logger logger = Fabric.getLogger();
        String str = Answers.TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Custom event tracking ");
        stringBuilder.append(this.customEventsEnabled ? "enabled" : "disabled");
        logger.d(str, stringBuilder.toString());
        this.predefinedEventsEnabled = analyticsSettingsData.trackPredefinedEvents;
        logger = Fabric.getLogger();
        str = Answers.TAG;
        stringBuilder = new StringBuilder();
        stringBuilder.append("Predefined event tracking ");
        stringBuilder.append(this.predefinedEventsEnabled ? "enabled" : "disabled");
        logger.d(str, stringBuilder.toString());
        if (analyticsSettingsData.samplingRate > 1) {
            Fabric.getLogger().d(Answers.TAG, "Event sampling enabled");
            this.eventFilter = new SamplingEventFilter(analyticsSettingsData.samplingRate);
        }
        this.rolloverIntervalSeconds = analyticsSettingsData.flushIntervalSeconds;
        scheduleTimeBasedFileRollOver(0, (long) this.rolloverIntervalSeconds);
    }

    public void processEvent(Builder builder) {
        SessionEvent event = builder.build(this.metadata);
        Logger logger;
        String str;
        StringBuilder stringBuilder;
        if (!this.customEventsEnabled && Type.CUSTOM.equals(event.type)) {
            logger = Fabric.getLogger();
            str = Answers.TAG;
            stringBuilder = new StringBuilder();
            stringBuilder.append("Custom events tracking disabled - skipping event: ");
            stringBuilder.append(event);
            logger.d(str, stringBuilder.toString());
        } else if (!this.predefinedEventsEnabled && Type.PREDEFINED.equals(event.type)) {
            logger = Fabric.getLogger();
            str = Answers.TAG;
            stringBuilder = new StringBuilder();
            stringBuilder.append("Predefined events tracking disabled - skipping event: ");
            stringBuilder.append(event);
            logger.d(str, stringBuilder.toString());
        } else if (this.eventFilter.skipEvent(event)) {
            logger = Fabric.getLogger();
            str = Answers.TAG;
            stringBuilder = new StringBuilder();
            stringBuilder.append("Skipping filtered event: ");
            stringBuilder.append(event);
            logger.d(str, stringBuilder.toString());
        } else {
            try {
                this.filesManager.writeEvent(event);
            } catch (IOException e) {
                Logger logger2 = Fabric.getLogger();
                String str2 = Answers.TAG;
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("Failed to write event: ");
                stringBuilder2.append(event);
                logger2.e(str2, stringBuilder2.toString(), e);
            }
            scheduleTimeBasedRollOverIfNeeded();
        }
    }

    public void scheduleTimeBasedRollOverIfNeeded() {
        if (this.rolloverIntervalSeconds != -1) {
            scheduleTimeBasedFileRollOver((long) this.rolloverIntervalSeconds, (long) this.rolloverIntervalSeconds);
        }
    }

    public void sendEvents() {
        if (this.filesSender == null) {
            CommonUtils.logControlled(this.context, "skipping files send because we don't yet know the target endpoint");
            return;
        }
        CommonUtils.logControlled(this.context, "Sending all files");
        int filesSent = 0;
        List<File> batch = this.filesManager.getBatchOfFilesToSend();
        while (batch.size() > 0) {
            try {
                CommonUtils.logControlled(this.context, String.format(Locale.US, "attempt to send batch of %d files", new Object[]{Integer.valueOf(batch.size())}));
                boolean cleanup = this.filesSender.send(batch);
                if (cleanup) {
                    filesSent += batch.size();
                    this.filesManager.deleteSentFiles(batch);
                }
                if (!cleanup) {
                    break;
                }
                batch = this.filesManager.getBatchOfFilesToSend();
            } catch (Exception e) {
                Context context = this.context;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Failed to send batch of analytics files to server: ");
                stringBuilder.append(e.getMessage());
                CommonUtils.logControlledError(context, stringBuilder.toString(), e);
            }
        }
        if (filesSent == 0) {
            this.filesManager.deleteOldestInRollOverIfOverMax();
        }
    }

    public void cancelTimeBasedFileRollOver() {
        if (this.rolloverFutureRef.get() != null) {
            CommonUtils.logControlled(this.context, "Cancelling time-based rollover because no events are currently being generated.");
            ((ScheduledFuture) this.rolloverFutureRef.get()).cancel(false);
            this.rolloverFutureRef.set(null);
        }
    }

    public void deleteAllEvents() {
        this.filesManager.deleteAllEventsFiles();
    }

    public boolean rollFileOver() {
        try {
            return this.filesManager.rollFileOver();
        } catch (IOException e) {
            CommonUtils.logControlledError(this.context, "Failed to roll file over.", e);
            return false;
        }
    }

    void scheduleTimeBasedFileRollOver(long initialDelaySecs, long frequencySecs) {
        if (this.rolloverFutureRef.get() == null) {
            Runnable rollOverRunnable = new TimeBasedFileRollOverRunnable(this.context, this);
            Context context = this.context;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Scheduling time based file roll over every ");
            stringBuilder.append(frequencySecs);
            stringBuilder.append(" seconds");
            CommonUtils.logControlled(context, stringBuilder.toString());
            try {
                this.rolloverFutureRef.set(this.executorService.scheduleAtFixedRate(rollOverRunnable, initialDelaySecs, frequencySecs, TimeUnit.SECONDS));
            } catch (RejectedExecutionException e) {
                CommonUtils.logControlledError(this.context, "Failed to schedule time based file roll over", e);
            }
        }
    }
}
