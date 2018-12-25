package com.google.android.gms.measurement;

import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzcno;
import com.google.firebase.analytics.FirebaseAnalytics.Event;

@Hide
@KeepForSdk
public final class AppMeasurement$Event extends Event {
    @Hide
    @KeepForSdk
    public static final String AD_REWARD = "_ar";
    @Hide
    @KeepForSdk
    public static final String APP_EXCEPTION = "_ae";
    @Hide
    public static final String[] zza = new String[]{"app_clear_data", "app_exception", "app_remove", "app_upgrade", "app_install", "app_update", "firebase_campaign", "error", "first_open", "first_visit", "in_app_purchase", "notification_dismiss", "notification_foreground", "notification_open", "notification_receive", "os_update", "session_start", "user_engagement", "ad_exposure", "adunit_exposure", "ad_query", "ad_activeview", "ad_impression", "ad_click", "ad_reward", "screen_view", "ga_extra_parameter"};
    @Hide
    public static final String[] zzb = new String[]{"_cd", APP_EXCEPTION, "_ui", "_ug", "_in", "_au", "_cmp", "_err", "_f", "_v", "_iap", "_nd", "_nf", "_no", "_nr", "_ou", "_s", "_e", "_xa", "_xu", "_aq", "_aa", "_ai", "_ac", AD_REWARD, "_vs", "_ep"};

    private AppMeasurement$Event() {
    }

    public static String zza(String str) {
        return zzcno.zza(str, zza, zzb);
    }
}
