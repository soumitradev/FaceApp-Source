package com.google.android.gms.measurement;

import com.facebook.AccessToken;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzcno;
import com.google.firebase.analytics.FirebaseAnalytics.UserProperty;

@Hide
@KeepForSdk
public final class AppMeasurement$UserProperty extends UserProperty {
    @Hide
    @KeepForSdk
    public static final String FIREBASE_LAST_NOTIFICATION = "_ln";
    @Hide
    public static final String[] zza = new String[]{"firebase_last_notification", "first_open_time", "first_visit_time", "last_deep_link_referrer", AccessToken.USER_ID_KEY, "first_open_after_install", "lifetime_user_engagement"};
    @Hide
    public static final String[] zzb = new String[]{FIREBASE_LAST_NOTIFICATION, "_fot", "_fvt", "_ldl", "_id", "_fi", "_lte"};

    private AppMeasurement$UserProperty() {
    }

    public static String zza(String str) {
        return zzcno.zza(str, zza, zzb);
    }
}
