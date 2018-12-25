package com.google.android.gms.tagmanager;

final class zzav implements Runnable {
    private /* synthetic */ zzaq zza;
    private /* synthetic */ zzat zzb;

    zzav(zzat zzat, zzaq zzaq) {
        this.zzb = zzat;
        this.zza = zzaq;
    }

    public final void run() {
        this.zza.zza(this.zzb.zzb());
    }
}
