package com.facebook.appevents;

import android.content.Context;
import android.util.Log;
import com.facebook.FacebookSdk;
import com.facebook.LoggingBehavior;
import com.facebook.internal.Logger;
import com.facebook.internal.Utility;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

class AppEventsLogger$PersistedAppSessionInfo {
    private static final String PERSISTED_SESSION_INFO_FILENAME = "AppEventsLogger.persistedsessioninfo";
    private static final Runnable appSessionInfoFlushRunnable = new C04191();
    private static Map<AccessTokenAppIdPair, FacebookTimeSpentData> appSessionInfoMap;
    private static boolean hasChanges = false;
    private static boolean isLoaded = false;
    private static final Object staticLock = new Object();

    /* renamed from: com.facebook.appevents.AppEventsLogger$PersistedAppSessionInfo$1 */
    static class C04191 implements Runnable {
        C04191() {
        }

        public void run() {
            AppEventsLogger$PersistedAppSessionInfo.saveAppSessionInformation(FacebookSdk.getApplicationContext());
        }
    }

    AppEventsLogger$PersistedAppSessionInfo() {
    }

    private static void restoreAppSessionInformation(Context context) {
        ObjectInputStream ois = null;
        synchronized (staticLock) {
            if (!isLoaded) {
                try {
                    ois = new ObjectInputStream(context.openFileInput(PERSISTED_SESSION_INFO_FILENAME));
                    appSessionInfoMap = (HashMap) ois.readObject();
                    Logger.log(LoggingBehavior.APP_EVENTS, "AppEvents", "App session info loaded");
                    Utility.closeQuietly(ois);
                    context.deleteFile(PERSISTED_SESSION_INFO_FILENAME);
                    if (appSessionInfoMap == null) {
                        appSessionInfoMap = new HashMap();
                    }
                    isLoaded = true;
                } catch (FileNotFoundException e) {
                    Utility.closeQuietly(ois);
                    context.deleteFile(PERSISTED_SESSION_INFO_FILENAME);
                    if (appSessionInfoMap == null) {
                        appSessionInfoMap = new HashMap();
                    }
                    isLoaded = true;
                } catch (Exception e2) {
                    try {
                        String access$200 = AppEventsLogger.access$200();
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Got unexpected exception restoring app session info: ");
                        stringBuilder.append(e2.toString());
                        Log.w(access$200, stringBuilder.toString());
                        Utility.closeQuietly(ois);
                        context.deleteFile(PERSISTED_SESSION_INFO_FILENAME);
                        if (appSessionInfoMap == null) {
                            appSessionInfoMap = new HashMap();
                        }
                        isLoaded = true;
                    } catch (Throwable th) {
                        Utility.closeQuietly(ois);
                        context.deleteFile(PERSISTED_SESSION_INFO_FILENAME);
                        if (appSessionInfoMap == null) {
                            appSessionInfoMap = new HashMap();
                        }
                        isLoaded = true;
                        hasChanges = false;
                    }
                }
                hasChanges = false;
            }
        }
    }

    static void saveAppSessionInformation(Context context) {
        ObjectOutputStream oos = null;
        synchronized (staticLock) {
            if (hasChanges) {
                try {
                    oos = new ObjectOutputStream(new BufferedOutputStream(context.openFileOutput(PERSISTED_SESSION_INFO_FILENAME, 0)));
                    oos.writeObject(appSessionInfoMap);
                    hasChanges = false;
                    Logger.log(LoggingBehavior.APP_EVENTS, "AppEvents", "App session info saved");
                    Utility.closeQuietly(oos);
                } catch (Exception e) {
                    try {
                        String access$200 = AppEventsLogger.access$200();
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Got unexpected exception while writing app session info: ");
                        stringBuilder.append(e.toString());
                        Log.w(access$200, stringBuilder.toString());
                    } finally {
                        Utility.closeQuietly(oos);
                    }
                }
            }
        }
    }

    static void onResume(Context context, AccessTokenAppIdPair accessTokenAppId, AppEventsLogger logger, long eventTime, String sourceApplicationInfo) {
        synchronized (staticLock) {
            getTimeSpentData(context, accessTokenAppId).onResume(logger, eventTime, sourceApplicationInfo);
            onTimeSpentDataUpdate();
        }
    }

    static void onSuspend(Context context, AccessTokenAppIdPair accessTokenAppId, AppEventsLogger logger, long eventTime) {
        synchronized (staticLock) {
            getTimeSpentData(context, accessTokenAppId).onSuspend(logger, eventTime);
            onTimeSpentDataUpdate();
        }
    }

    private static FacebookTimeSpentData getTimeSpentData(Context context, AccessTokenAppIdPair accessTokenAppId) {
        restoreAppSessionInformation(context);
        FacebookTimeSpentData result = (FacebookTimeSpentData) appSessionInfoMap.get(accessTokenAppId);
        if (result != null) {
            return result;
        }
        result = new FacebookTimeSpentData();
        appSessionInfoMap.put(accessTokenAppId, result);
        return result;
    }

    private static void onTimeSpentDataUpdate() {
        if (!hasChanges) {
            hasChanges = true;
            AppEventsLogger.access$300().schedule(appSessionInfoFlushRunnable, 30, TimeUnit.SECONDS);
        }
    }
}
