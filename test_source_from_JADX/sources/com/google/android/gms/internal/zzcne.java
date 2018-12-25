package com.google.android.gms.internal;

import android.os.Bundle;
import android.support.annotation.WorkerThread;

final class zzcne extends zzcip {
    private /* synthetic */ zzcnd zza;

    zzcne(zzcnd zzcnd, zzckj zzckj) {
        this.zza = zzcnd;
        super(zzckj);
    }

    @WorkerThread
    public final void zza() {
        zzclh zzclh = this.zza;
        zzclh.zzc();
        zzclh.zzt().zzae().zza("Session started, time", Long.valueOf(zzclh.zzk().zzb()));
        zzclh.zzu().zzl.zza(false);
        zzclh.zzf().zza("auto", "_s", new Bundle());
        zzclh.zzu().zzm.zza(zzclh.zzk().zza());
    }
}
