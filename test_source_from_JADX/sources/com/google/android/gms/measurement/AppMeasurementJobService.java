package com.google.android.gms.measurement;

import android.annotation.TargetApi;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.support.annotation.MainThread;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzcmy;
import com.google.android.gms.internal.zzcnc;

@TargetApi(24)
public final class AppMeasurementJobService extends JobService implements zzcnc {
    private zzcmy<AppMeasurementJobService> zza;

    private final zzcmy<AppMeasurementJobService> zza() {
        if (this.zza == null) {
            this.zza = new zzcmy(this);
        }
        return this.zza;
    }

    @MainThread
    public final void onCreate() {
        super.onCreate();
        zza().zza();
    }

    @MainThread
    public final void onDestroy() {
        zza().zzb();
        super.onDestroy();
    }

    @MainThread
    public final void onRebind(Intent intent) {
        zza().zzc(intent);
    }

    public final boolean onStartJob(JobParameters jobParameters) {
        return zza().zza(jobParameters);
    }

    public final boolean onStopJob(JobParameters jobParameters) {
        return false;
    }

    @MainThread
    public final boolean onUnbind(Intent intent) {
        return zza().zzb(intent);
    }

    @Hide
    @TargetApi(24)
    public final void zza(JobParameters jobParameters, boolean z) {
        jobFinished(jobParameters, false);
    }

    @Hide
    public final void zza(Intent intent) {
    }

    @Hide
    public final boolean zza(int i) {
        throw new UnsupportedOperationException();
    }
}
