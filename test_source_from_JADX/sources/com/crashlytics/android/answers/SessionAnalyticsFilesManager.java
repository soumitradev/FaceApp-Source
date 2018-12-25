package com.crashlytics.android.answers;

import android.content.Context;
import io.fabric.sdk.android.services.common.CurrentTimeProvider;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import io.fabric.sdk.android.services.events.EventsStorage;
import io.fabric.sdk.android.services.settings.AnalyticsSettingsData;
import java.io.IOException;
import java.util.UUID;

class SessionAnalyticsFilesManager extends EventsFilesManager<SessionEvent> {
    private static final String SESSION_ANALYTICS_TO_SEND_FILE_EXTENSION = ".tap";
    private static final String SESSION_ANALYTICS_TO_SEND_FILE_PREFIX = "sa";
    private AnalyticsSettingsData analyticsSettingsData;

    SessionAnalyticsFilesManager(Context context, SessionEventTransform transform, CurrentTimeProvider currentTimeProvider, EventsStorage eventStorage) throws IOException {
        super(context, transform, currentTimeProvider, eventStorage, 100);
    }

    protected String generateUniqueRollOverFileName() {
        UUID targetUUIDComponent = UUID.randomUUID();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(SESSION_ANALYTICS_TO_SEND_FILE_PREFIX);
        stringBuilder.append(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
        stringBuilder.append(targetUUIDComponent.toString());
        stringBuilder.append(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
        stringBuilder.append(this.currentTimeProvider.getCurrentTimeMillis());
        stringBuilder.append(SESSION_ANALYTICS_TO_SEND_FILE_EXTENSION);
        return stringBuilder.toString();
    }

    protected int getMaxFilesToKeep() {
        return this.analyticsSettingsData == null ? super.getMaxFilesToKeep() : this.analyticsSettingsData.maxPendingSendFileCount;
    }

    protected int getMaxByteSizePerFile() {
        return this.analyticsSettingsData == null ? super.getMaxByteSizePerFile() : this.analyticsSettingsData.maxByteSizePerFile;
    }

    void setAnalyticsSettingsData(AnalyticsSettingsData analyticsSettingsData) {
        this.analyticsSettingsData = analyticsSettingsData;
    }
}
