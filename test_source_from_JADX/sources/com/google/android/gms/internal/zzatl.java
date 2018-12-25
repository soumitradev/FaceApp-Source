package com.google.android.gms.internal;

import android.annotation.TargetApi;
import android.app.job.JobParameters;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.RequiresPermission;
import com.facebook.internal.NativeProtocol;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbq;

public final class zzatl<T extends Context & zzato> {
    private static Boolean zzc;
    private final Handler zza = new Handler();
    private final T zzb;

    @Hide
    public zzatl(T t) {
        zzbq.zza(t);
        this.zzb = t;
    }

    private final void zza(Integer num, JobParameters jobParameters) {
        zzark zza = zzark.zza(this.zzb);
        zza.zzh().zza(new zzatm(this, num, zza, zza.zze(), jobParameters));
    }

    @Hide
    public static boolean zza(Context context) {
        zzbq.zza(context);
        if (zzc != null) {
            return zzc.booleanValue();
        }
        boolean zza = zzatt.zza(context, "com.google.android.gms.analytics.AnalyticsService");
        zzc = Boolean.valueOf(zza);
        return zza;
    }

    @Hide
    @RequiresPermission(allOf = {"android.permission.INTERNET", "android.permission.ACCESS_NETWORK_STATE"})
    public final int zza(Intent intent, int i, int i2) {
        try {
            synchronized (zzatk.zza) {
                zzcyz zzcyz = zzatk.zzb;
                if (zzcyz != null && zzcyz.zzb()) {
                    zzcyz.zza();
                }
            }
        } catch (SecurityException e) {
        }
        zzarh zze = zzark.zza(this.zzb).zze();
        if (intent == null) {
            zze.zze("AnalyticsService started with null intent");
            return 2;
        }
        String action = intent.getAction();
        zze.zza("Local AnalyticsService called. startId, action", Integer.valueOf(i2), action);
        if ("com.google.android.gms.analytics.ANALYTICS_DISPATCH".equals(action)) {
            zza(Integer.valueOf(i2), null);
        }
        return 2;
    }

    @Hide
    @RequiresPermission(allOf = {"android.permission.INTERNET", "android.permission.ACCESS_NETWORK_STATE"})
    public final void zza() {
        zzark.zza(this.zzb).zze().zzb("Local AnalyticsService is starting up");
    }

    @TargetApi(24)
    public final boolean zza(JobParameters jobParameters) {
        zzarh zze = zzark.zza(this.zzb).zze();
        String string = jobParameters.getExtras().getString(NativeProtocol.WEB_DIALOG_ACTION);
        zze.zza("Local AnalyticsJobService called. action", string);
        if ("com.google.android.gms.analytics.ANALYTICS_DISPATCH".equals(string)) {
            zza(null, jobParameters);
        }
        return true;
    }

    @Hide
    @RequiresPermission(allOf = {"android.permission.INTERNET", "android.permission.ACCESS_NETWORK_STATE"})
    public final void zzb() {
        zzark.zza(this.zzb).zze().zzb("Local AnalyticsService is shutting down");
    }
}
