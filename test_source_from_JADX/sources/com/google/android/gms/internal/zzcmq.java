package com.google.android.gms.internal;

final class zzcmq implements Runnable {
    private /* synthetic */ boolean zza;
    private /* synthetic */ zzcnl zzb;
    private /* synthetic */ zzcif zzc;
    private /* synthetic */ zzcme zzd;

    zzcmq(zzcme zzcme, boolean z, zzcnl zzcnl, zzcif zzcif) {
        this.zzd = zzcme;
        this.zza = z;
        this.zzb = zzcnl;
        this.zzc = zzcif;
    }

    public final void run() {
        zzcjb zzd = this.zzd.zzb;
        if (zzd == null) {
            this.zzd.zzt().zzy().zza("Discarding data. Failed to set user attribute");
            return;
        }
        this.zzd.zza(zzd, this.zza ? null : this.zzb, this.zzc);
        this.zzd.zzaf();
    }
}
