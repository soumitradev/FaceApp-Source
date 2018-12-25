package com.google.android.gms.internal;

final class zzclt implements Runnable {
    private /* synthetic */ String zza;
    private /* synthetic */ String zzb;
    private /* synthetic */ Object zzc;
    private /* synthetic */ long zzd;
    private /* synthetic */ zzclk zze;

    zzclt(zzclk zzclk, String str, String str2, Object obj, long j) {
        this.zze = zzclk;
        this.zza = str;
        this.zzb = str2;
        this.zzc = obj;
        this.zzd = j;
    }

    public final void run() {
        this.zze.zza(this.zza, this.zzb, this.zzc, this.zzd);
    }
}
