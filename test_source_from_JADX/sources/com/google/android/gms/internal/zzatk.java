package com.google.android.gms.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.RequiresPermission;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbq;

public final class zzatk {
    static Object zza = new Object();
    static zzcyz zzb;
    private static Boolean zzc;

    @RequiresPermission(allOf = {"android.permission.INTERNET", "android.permission.ACCESS_NETWORK_STATE"})
    public static void zza(Context context, Intent intent) {
        zzarh zze = zzark.zza(context).zze();
        if (intent == null) {
            zze.zze("AnalyticsReceiver called with null intent");
            return;
        }
        String action = intent.getAction();
        zze.zza("Local AnalyticsReceiver got", action);
        if ("com.google.android.gms.analytics.ANALYTICS_DISPATCH".equals(action)) {
            boolean zza = zzatl.zza(context);
            Intent intent2 = new Intent("com.google.android.gms.analytics.ANALYTICS_DISPATCH");
            intent2.setComponent(new ComponentName(context, "com.google.android.gms.analytics.AnalyticsService"));
            intent2.setAction("com.google.android.gms.analytics.ANALYTICS_DISPATCH");
            synchronized (zza) {
                context.startService(intent2);
                if (zza) {
                    try {
                        if (zzb == null) {
                            zzcyz zzcyz = new zzcyz(context, 1, "Analytics WakeLock");
                            zzb = zzcyz;
                            zzcyz.zza(false);
                        }
                        zzb.zza(1000);
                    } catch (SecurityException e) {
                        zze.zze("Analytics service at risk of not starting. For more reliable analytics, add the WAKE_LOCK permission to your manifest. See http://goo.gl/8Rd3yj for instructions.");
                    }
                } else {
                    return;
                }
            }
        }
        return;
    }

    @Hide
    public static boolean zza(Context context) {
        zzbq.zza(context);
        if (zzc != null) {
            return zzc.booleanValue();
        }
        boolean zza = zzatt.zza(context, "com.google.android.gms.analytics.AnalyticsReceiver", false);
        zzc = Boolean.valueOf(zza);
        return zza;
    }
}
