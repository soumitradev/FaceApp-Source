package com.google.android.gms.measurement;

import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzcno;
import com.google.firebase.analytics.FirebaseAnalytics$Param;

@Hide
@KeepForSdk
public final class AppMeasurement$Param extends FirebaseAnalytics$Param {
    @Hide
    @KeepForSdk
    public static final String FATAL = "fatal";
    @Hide
    @KeepForSdk
    public static final String TIMESTAMP = "timestamp";
    @Hide
    @KeepForSdk
    public static final String TYPE = "type";
    @Hide
    public static final String[] zza = new String[]{"firebase_conversion", "engagement_time_msec", "exposure_time", "ad_event_id", "ad_unit_id", "firebase_error", "firebase_error_value", "firebase_error_length", "firebase_event_origin", "firebase_screen", "firebase_screen_class", "firebase_screen_id", "firebase_previous_screen", "firebase_previous_class", "firebase_previous_id", "message_device_time", "message_id", "message_name", "message_time", "previous_app_version", "previous_os_version", "topic", "update_with_analytics", "previous_first_open_count", "system_app", "system_app_update", "previous_install_count", "ga_event_id", "ga_extra_params_ct", "ga_group_name", "ga_list_length", "ga_index", "ga_event_name"};
    @Hide
    public static final String[] zzb = new String[]{"_c", "_et", "_xt", "_aeid", "_ai", "_err", "_ev", "_el", "_o", "_sn", "_sc", "_si", "_pn", "_pc", "_pi", "_ndt", "_nmid", "_nmn", "_nmt", "_pv", "_po", "_nt", "_uwa", "_pfo", "_sys", "_sysu", "_pin", "_eid", "_epc", "_gn", "_ll", "_i", "_en"};

    private AppMeasurement$Param() {
    }

    public static String zza(String str) {
        return zzcno.zza(str, zza, zzb);
    }
}
