package com.google.android.gms.internal;

final class zzcla implements Runnable {
    private /* synthetic */ zzcix zza;
    private /* synthetic */ String zzb;
    private /* synthetic */ zzcko zzc;

    zzcla(zzcko zzcko, zzcix zzcix, String str) {
        this.zzc = zzcko;
        this.zza = zzcix;
        this.zzb = str;
    }

    public final void run() {
        this.zzc.zza.zzag();
        this.zzc.zza.zza(this.zza, this.zzb);
    }
}
