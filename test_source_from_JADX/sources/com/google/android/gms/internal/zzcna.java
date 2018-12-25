package com.google.android.gms.internal;

import android.app.job.JobParameters;

final /* synthetic */ class zzcna implements Runnable {
    private final zzcmy zza;
    private final zzcjj zzb;
    private final JobParameters zzc;

    zzcna(zzcmy zzcmy, zzcjj zzcjj, JobParameters jobParameters) {
        this.zza = zzcmy;
        this.zzb = zzcjj;
        this.zzc = jobParameters;
    }

    public final void run() {
        this.zza.zza(this.zzb, this.zzc);
    }
}
