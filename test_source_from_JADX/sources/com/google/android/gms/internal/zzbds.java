package com.google.android.gms.internal;

final class zzbds implements Runnable {
    private /* synthetic */ zzbdp zza;
    private /* synthetic */ int zzb;

    zzbds(zzbdr zzbdr, zzbdp zzbdp, int i) {
        this.zza = zzbdp;
        this.zzb = i;
    }

    public final void run() {
        this.zza.zzg.onApplicationDisconnected(this.zzb);
    }
}
