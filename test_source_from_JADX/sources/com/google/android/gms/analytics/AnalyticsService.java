package com.google.android.gms.analytics;

import android.app.Service;
import android.app.job.JobParameters;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.RequiresPermission;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzatl;
import com.google.android.gms.internal.zzato;

public final class AnalyticsService extends Service implements zzato {
    private zzatl<AnalyticsService> zza;

    private final zzatl<AnalyticsService> zza() {
        if (this.zza == null) {
            this.zza = new zzatl(this);
        }
        return this.zza;
    }

    public final boolean callServiceStopSelfResult(int i) {
        return stopSelfResult(i);
    }

    public final IBinder onBind(Intent intent) {
        zza();
        return null;
    }

    @Hide
    @RequiresPermission(allOf = {"android.permission.INTERNET", "android.permission.ACCESS_NETWORK_STATE"})
    public final void onCreate() {
        super.onCreate();
        zza().zza();
    }

    @Hide
    @RequiresPermission(allOf = {"android.permission.INTERNET", "android.permission.ACCESS_NETWORK_STATE"})
    public final void onDestroy() {
        zza().zzb();
        super.onDestroy();
    }

    @Hide
    @RequiresPermission(allOf = {"android.permission.INTERNET", "android.permission.ACCESS_NETWORK_STATE"})
    public final int onStartCommand(Intent intent, int i, int i2) {
        return zza().zza(intent, i, i2);
    }

    @Hide
    public final void zza(JobParameters jobParameters, boolean z) {
        throw new UnsupportedOperationException();
    }
}
