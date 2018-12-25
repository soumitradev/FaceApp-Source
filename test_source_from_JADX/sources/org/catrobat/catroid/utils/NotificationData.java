package org.catrobat.catroid.utils;

import android.app.Notification.Builder;
import android.app.PendingIntent;
import android.content.Context;

public class NotificationData {
    private Builder notificationBuilder;
    private int notificationIcon;
    private String notificationTextDone;
    private String notificationTextWorking;
    private String notificationTitlePrefixDone;
    private String notificationTitlePrefixWorking;
    private PendingIntent pendingIntent;
    private String programName;

    public NotificationData(Context context, PendingIntent pendingIntent, int notificationIcon, String programName, int notificationTitlePrefixWorkingStringId, int notificationTitlePrefixDoneStringId, int notificationTextWorkingStringId, int notificationTextDoneStringId) {
        this.pendingIntent = pendingIntent;
        this.notificationIcon = notificationIcon;
        this.programName = programName;
        this.notificationTitlePrefixWorking = context.getString(notificationTitlePrefixWorkingStringId);
        this.notificationTitlePrefixDone = context.getString(notificationTitlePrefixDoneStringId);
        this.notificationTextWorking = context.getString(notificationTextWorkingStringId);
        this.notificationTextDone = context.getString(notificationTextDoneStringId);
    }

    public PendingIntent getPendingIntent() {
        return this.pendingIntent;
    }

    public NotificationData setPendingIntent(PendingIntent pendingIntent) {
        this.pendingIntent = pendingIntent;
        return this;
    }

    public int getNotificationIcon() {
        return this.notificationIcon;
    }

    public String getProgramName() {
        return this.programName;
    }

    public NotificationData setProgramName(String programName) {
        this.programName = programName;
        return this;
    }

    public String getNotificationTitleWorking() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.notificationTitlePrefixWorking);
        stringBuilder.append(this.programName);
        return stringBuilder.toString();
    }

    public String getNotificationTitleDone() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.notificationTitlePrefixDone);
        stringBuilder.append(this.programName);
        return stringBuilder.toString();
    }

    public String getNotificationTextWorking() {
        return this.notificationTextWorking;
    }

    public String getNotificationTextDone() {
        return this.notificationTextDone;
    }

    public void setNotificationTextDone(String newTextDone) {
        this.notificationTextDone = newTextDone;
    }

    public Builder getNotificationBuilder() {
        return this.notificationBuilder;
    }

    public NotificationData setNotificationBuilder(Builder notificationBuilder) {
        this.notificationBuilder = notificationBuilder;
        return this;
    }
}
