package com.google.android.gms.analytics;

final class zzl implements Runnable {
    private /* synthetic */ zzg zza;
    private /* synthetic */ zzk zzb;

    zzl(zzk zzk, zzg zzg) {
        this.zzb = zzk;
        this.zza = zzg;
    }

    public final void run() {
        this.zza.zzh().zza(this.zza);
        for (zzn zza : this.zzb.zzc) {
            zza.zza(this.zza);
        }
        zzk.zzb(this.zza);
    }
}
