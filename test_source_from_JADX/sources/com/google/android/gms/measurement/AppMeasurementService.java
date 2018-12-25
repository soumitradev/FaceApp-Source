package com.google.android.gms.measurement;

import android.app.Service;
import android.app.job.JobParameters;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.MainThread;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzcmy;
import com.google.android.gms.internal.zzcnc;

public final class AppMeasurementService extends Service implements zzcnc {
    private zzcmy<AppMeasurementService> zza;

    private final zzcmy<AppMeasurementService> zza() {
        if (this.zza == null) {
            this.zza = new zzcmy(this);
        }
        return this.zza;
    }

    @MainThread
    public final IBinder onBind(Intent intent) {
        return zza().zza(intent);
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

    @MainThread
    public final int onStartCommand(Intent intent, int i, int i2) {
        return zza().zza(intent, i, i2);
    }

    @MainThread
    public final boolean onUnbind(Intent intent) {
        return zza().zzb(intent);
    }

    @Hide
    public final void zza(JobParameters jobParameters, boolean z) {
        throw new UnsupportedOperationException();
    }

    @Hide
    public final void zza(Intent intent) {
        AppMeasurementReceiver.completeWakefulIntent(intent);
    }

    @Hide
    public final boolean zza(int i) {
        return stopSelfResult(i);
    }
}
