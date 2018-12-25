package com.facebook.appevents;

class AppEventsLogger$2 implements Runnable {
    final /* synthetic */ long val$eventTime;
    final /* synthetic */ AppEventsLogger val$logger;

    AppEventsLogger$2(AppEventsLogger appEventsLogger, long j) {
        this.val$logger = appEventsLogger;
        this.val$eventTime = j;
    }

    public void run() {
        AppEventsLogger.access$100(this.val$logger, this.val$eventTime);
    }
}
