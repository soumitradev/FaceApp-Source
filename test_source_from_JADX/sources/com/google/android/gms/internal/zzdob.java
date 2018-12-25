package com.google.android.gms.internal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build.VERSION;

public final class zzdob<T> {
    private static final Object zza = new Object();
    @SuppressLint({"StaticFieldLeak"})
    private static Context zzb = null;
    private static boolean zzc = false;
    private static Boolean zzd = null;

    public static void zza(Context context) {
        synchronized (zza) {
            if (VERSION.SDK_INT < 24 || !context.isDeviceProtectedStorage()) {
                Context applicationContext = context.getApplicationContext();
                if (applicationContext != null) {
                    context = applicationContext;
                }
            }
            if (zzb != context) {
                zzd = null;
            }
            zzb = context;
        }
        zzc = false;
    }

    public static void zzb(Context context) {
        if (zzb == null) {
            zza(context);
        }
    }
}
