package com.facebook.appevents;

import com.facebook.internal.Utility;
import java.util.HashSet;
import java.util.Set;

class AppEventsLogger$3 implements Runnable {
    AppEventsLogger$3() {
    }

    public void run() {
        Set<String> applicationIds = new HashSet();
        for (AccessTokenAppIdPair accessTokenAppId : AppEventQueue.getKeySet()) {
            applicationIds.add(accessTokenAppId.getApplicationId());
        }
        for (String applicationId : applicationIds) {
            Utility.queryAppSettings(applicationId, true);
        }
    }
}
