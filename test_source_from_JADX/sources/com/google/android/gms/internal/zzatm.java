package com.google.android.gms.internal;

import android.app.job.JobParameters;

final class zzatm implements zzasr {
    final /* synthetic */ Integer zza;
    final /* synthetic */ zzatd zzb;
    final /* synthetic */ JobParameters zzc;
    final /* synthetic */ zzatl zzd;
    private /* synthetic */ zzark zze;

    zzatm(zzatl zzatl, Integer num, zzark zzark, zzatd zzatd, JobParameters jobParameters) {
        this.zzd = zzatl;
        this.zza = num;
        this.zze = zzark;
        this.zzb = zzatd;
        this.zzc = jobParameters;
    }

    public final void zza(Throwable th) {
        this.zzd.zza.post(new zzatn(this));
    }
}
