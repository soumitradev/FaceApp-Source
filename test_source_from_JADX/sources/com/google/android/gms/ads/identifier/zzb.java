package com.google.android.gms.ads.identifier;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.zzs;

@Hide
public final class zzb {
    private SharedPreferences zza;

    @Hide
    public zzb(Context context) {
        try {
            context = zzs.getRemoteContext(context);
            this.zza = context == null ? null : context.getSharedPreferences("google_ads_flags", 0);
        } catch (Throwable th) {
            Log.w("GmscoreFlag", "Error while getting SharedPreferences ", th);
            this.zza = null;
        }
    }

    final float zza(String str, float f) {
        try {
            return this.zza == null ? 0.0f : this.zza.getFloat(str, 0.0f);
        } catch (Throwable th) {
            Log.w("GmscoreFlag", "Error while reading from SharedPreferences ", th);
            return 0.0f;
        }
    }

    final String zza(String str, String str2) {
        try {
            return this.zza == null ? str2 : this.zza.getString(str, str2);
        } catch (Throwable th) {
            Log.w("GmscoreFlag", "Error while reading from SharedPreferences ", th);
            return str2;
        }
    }

    public final boolean zza(String str, boolean z) {
        try {
            return this.zza == null ? false : this.zza.getBoolean(str, false);
        } catch (Throwable th) {
            Log.w("GmscoreFlag", "Error while reading from SharedPreferences ", th);
            return false;
        }
    }
}
