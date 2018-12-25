package com.google.android.gms.internal;

final class zzbdu implements Runnable {
    private /* synthetic */ zzbdp zza;
    private /* synthetic */ zzbdd zzb;

    zzbdu(zzbdr zzbdr, zzbdp zzbdp, zzbdd zzbdd) {
        this.zza = zzbdp;
        this.zzb = zzbdd;
    }

    public final void run() {
        this.zza.zza(this.zzb);
    }
}
