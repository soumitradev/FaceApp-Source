package com.google.android.gms.internal;

final class zzbdt implements Runnable {
    private /* synthetic */ zzbdp zza;
    private /* synthetic */ zzbdx zzb;

    zzbdt(zzbdr zzbdr, zzbdp zzbdp, zzbdx zzbdx) {
        this.zza = zzbdp;
        this.zzb = zzbdx;
    }

    public final void run() {
        this.zza.zza(this.zzb);
    }
}
