package com.google.android.gms.flags.impl;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzccq;

@Hide
public final class zzj {
    private static SharedPreferences zza = null;

    public static SharedPreferences zza(Context context) throws Exception {
        SharedPreferences sharedPreferences;
        synchronized (SharedPreferences.class) {
            if (zza == null) {
                zza = (SharedPreferences) zzccq.zza(new zzk(context));
            }
            sharedPreferences = zza;
        }
        return sharedPreferences;
    }
}
