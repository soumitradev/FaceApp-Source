package com.google.android.gms.common.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PowerManager;
import android.os.SystemClock;
import com.google.firebase.analytics.FirebaseAnalytics$Param;

public final class zzk {
    private static IntentFilter zza = new IntentFilter("android.intent.action.BATTERY_CHANGED");
    private static long zzb;
    private static float zzc = Float.NaN;

    @TargetApi(20)
    public static int zza(Context context) {
        if (context == null || context.getApplicationContext() == null) {
            return -1;
        }
        Intent registerReceiver = context.getApplicationContext().registerReceiver(null, zza);
        int i = 0;
        int i2 = ((registerReceiver == null ? 0 : registerReceiver.getIntExtra("plugged", 0)) & 7) != 0 ? 1 : 0;
        PowerManager powerManager = (PowerManager) context.getSystemService("power");
        if (powerManager == null) {
            return -1;
        }
        if (zzs.zzf() ? powerManager.isInteractive() : powerManager.isScreenOn()) {
            i = 1;
        }
        return (i << 1) | i2;
    }

    public static synchronized float zzb(Context context) {
        synchronized (zzk.class) {
            if (SystemClock.elapsedRealtime() - zzb >= 60000 || Float.isNaN(zzc)) {
                Intent registerReceiver = context.getApplicationContext().registerReceiver(null, zza);
                if (registerReceiver != null) {
                    zzc = ((float) registerReceiver.getIntExtra(FirebaseAnalytics$Param.LEVEL, -1)) / ((float) registerReceiver.getIntExtra("scale", -1));
                }
                zzb = SystemClock.elapsedRealtime();
                float f = zzc;
                return f;
            }
            f = zzc;
            return f;
        }
    }
}
