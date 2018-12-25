package com.google.android.gms.internal;

import android.annotation.TargetApi;
import android.app.job.JobParameters;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.IBinder;
import android.support.annotation.MainThread;
import com.facebook.internal.NativeProtocol;
import com.google.android.gms.common.internal.zzbq;

public final class zzcmy<T extends Context & zzcnc> {
    private final T zza;

    public zzcmy(T t) {
        zzbq.zza(t);
        this.zza = t;
    }

    private final void zza(Runnable runnable) {
        zzckj zza = zzckj.zza(this.zza);
        zza.zzf();
        zza.zzh().zza(new zzcnb(this, zza, runnable));
    }

    public static boolean zza(Context context, boolean z) {
        zzbq.zza(context);
        return zzcno.zza(context, VERSION.SDK_INT >= 24 ? "com.google.android.gms.measurement.AppMeasurementJobService" : "com.google.android.gms.measurement.AppMeasurementService");
    }

    private final zzcjj zzc() {
        return zzckj.zza(this.zza).zzf();
    }

    @MainThread
    public final int zza(Intent intent, int i, int i2) {
        zzcjj zzf = zzckj.zza(this.zza).zzf();
        if (intent == null) {
            zzf.zzaa().zza("AppMeasurementService started with null intent");
            return 2;
        }
        String action = intent.getAction();
        zzf.zzae().zza("Local AppMeasurementService called. startId, action", Integer.valueOf(i2), action);
        if ("com.google.android.gms.measurement.UPLOAD".equals(action)) {
            zza(new zzcmz(this, i2, zzf, intent));
        }
        return 2;
    }

    @MainThread
    public final IBinder zza(Intent intent) {
        if (intent == null) {
            zzc().zzy().zza("onBind called with null intent");
            return null;
        }
        String action = intent.getAction();
        if ("com.google.android.gms.measurement.START".equals(action)) {
            return new zzcko(zzckj.zza(this.zza));
        }
        zzc().zzaa().zza("onBind received unknown action", action);
        return null;
    }

    @MainThread
    public final void zza() {
        zzckj.zza(this.zza).zzf().zzae().zza("Local AppMeasurementService is starting up");
    }

    final /* synthetic */ void zza(int i, zzcjj zzcjj, Intent intent) {
        if (((zzcnc) this.zza).zza(i)) {
            zzcjj.zzae().zza("Local AppMeasurementService processed last upload request. StartId", Integer.valueOf(i));
            zzc().zzae().zza("Completed wakeful intent.");
            ((zzcnc) this.zza).zza(intent);
        }
    }

    final /* synthetic */ void zza(zzcjj zzcjj, JobParameters jobParameters) {
        zzcjj.zzae().zza("AppMeasurementJobService processed last upload request.");
        ((zzcnc) this.zza).zza(jobParameters, false);
    }

    @TargetApi(24)
    @MainThread
    public final boolean zza(JobParameters jobParameters) {
        zzcjj zzf = zzckj.zza(this.zza).zzf();
        String string = jobParameters.getExtras().getString(NativeProtocol.WEB_DIALOG_ACTION);
        zzf.zzae().zza("Local AppMeasurementJobService called. action", string);
        if ("com.google.android.gms.measurement.UPLOAD".equals(string)) {
            zza(new zzcna(this, zzf, jobParameters));
        }
        return true;
    }

    @MainThread
    public final void zzb() {
        zzckj.zza(this.zza).zzf().zzae().zza("Local AppMeasurementService is shutting down");
    }

    @MainThread
    public final boolean zzb(Intent intent) {
        if (intent == null) {
            zzc().zzy().zza("onUnbind called with null intent");
            return true;
        }
        zzc().zzae().zza("onUnbind called for intent. action", intent.getAction());
        return true;
    }

    @MainThread
    public final void zzc(Intent intent) {
        if (intent == null) {
            zzc().zzy().zza("onRebind called with null intent");
            return;
        }
        zzc().zzae().zza("onRebind called. action", intent.getAction());
    }
}
