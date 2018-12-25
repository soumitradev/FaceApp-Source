package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.common.util.zzs;

public final class zzbif {
    private static Context zza;
    private static Boolean zzb;

    public static synchronized boolean zza(Context context) {
        synchronized (zzbif.class) {
            Context applicationContext = context.getApplicationContext();
            boolean booleanValue;
            if (zza == null || zzb == null || zza != applicationContext) {
                Boolean valueOf;
                zzb = null;
                if (zzs.zzi()) {
                    valueOf = Boolean.valueOf(applicationContext.getPackageManager().isInstantApp());
                } else {
                    try {
                        context.getClassLoader().loadClass("com.google.android.instantapps.supervisor.InstantAppsRuntime");
                        zzb = Boolean.valueOf(true);
                    } catch (ClassNotFoundException e) {
                        valueOf = Boolean.valueOf(false);
                    }
                    zza = applicationContext;
                    booleanValue = zzb.booleanValue();
                    return booleanValue;
                }
                zzb = valueOf;
                zza = applicationContext;
                booleanValue = zzb.booleanValue();
                return booleanValue;
            }
            booleanValue = zzb.booleanValue();
            return booleanValue;
        }
    }
}
