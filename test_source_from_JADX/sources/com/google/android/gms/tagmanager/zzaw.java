package com.google.android.gms.tagmanager;

final class zzaw implements Runnable {
    private /* synthetic */ String zza;
    private /* synthetic */ zzat zzb;

    zzaw(zzat zzat, String str) {
        this.zzb = zzat;
        this.zza = str;
    }

    public final void run() {
        this.zzb.zzb(this.zza);
    }
}
