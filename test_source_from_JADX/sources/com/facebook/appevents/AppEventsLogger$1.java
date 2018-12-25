package com.facebook.appevents;

class AppEventsLogger$1 implements Runnable {
    final /* synthetic */ long val$eventTime;
    final /* synthetic */ AppEventsLogger val$logger;
    final /* synthetic */ String val$sourceApplicationInfo;

    AppEventsLogger$1(AppEventsLogger appEventsLogger, long j, String str) {
        this.val$logger = appEventsLogger;
        this.val$eventTime = j;
        this.val$sourceApplicationInfo = str;
    }

    public void run() {
        AppEventsLogger.access$000(this.val$logger, this.val$eventTime, this.val$sourceApplicationInfo);
    }
}
