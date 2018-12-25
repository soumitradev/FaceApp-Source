package com.google.android.gms.internal;

import android.content.BroadcastReceiver.PendingResult;
import android.content.Context;
import android.os.Bundle;
import com.google.android.gms.measurement.AppMeasurement;

final class zzckb implements Runnable {
    private /* synthetic */ zzckj zza;
    private /* synthetic */ long zzb;
    private /* synthetic */ Bundle zzc;
    private /* synthetic */ Context zzd;
    private /* synthetic */ zzcjj zze;
    private /* synthetic */ PendingResult zzf;

    zzckb(zzcka zzcka, zzckj zzckj, long j, Bundle bundle, Context context, zzcjj zzcjj, PendingResult pendingResult) {
        this.zza = zzckj;
        this.zzb = j;
        this.zzc = bundle;
        this.zzd = context;
        this.zze = zzcjj;
        this.zzf = pendingResult;
    }

    public final void run() {
        long zza = this.zza.zze().zzh.zza();
        long j = this.zzb;
        if (zza > 0 && (j >= zza || j <= 0)) {
            j = zza - 1;
        }
        if (j > 0) {
            this.zzc.putLong("click_timestamp", j);
        }
        AppMeasurement.getInstance(this.zzd).logEventInternal("auto", "_cmp", this.zzc);
        this.zze.zzae().zza("Install campaign recorded");
        if (this.zzf != null) {
            this.zzf.finish();
        }
    }
}
