package com.google.android.gms.internal;

final class zzcmc implements Runnable {
    private /* synthetic */ zzcmd zza;
    private /* synthetic */ zzcma zzb;

    zzcmc(zzcma zzcma, zzcmd zzcmd) {
        this.zzb = zzcma;
        this.zza = zzcmd;
    }

    public final void run() {
        this.zzb.zza(this.zza);
        this.zzb.zza = null;
        this.zzb.zzi().zza(null);
    }
}
