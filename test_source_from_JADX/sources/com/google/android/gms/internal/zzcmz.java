package com.google.android.gms.internal;

import android.content.Intent;

final /* synthetic */ class zzcmz implements Runnable {
    private final zzcmy zza;
    private final int zzb;
    private final zzcjj zzc;
    private final Intent zzd;

    zzcmz(zzcmy zzcmy, int i, zzcjj zzcjj, Intent intent) {
        this.zza = zzcmy;
        this.zzb = i;
        this.zzc = zzcjj;
        this.zzd = intent;
    }

    public final void run() {
        this.zza.zza(this.zzb, this.zzc, this.zzd);
    }
}
