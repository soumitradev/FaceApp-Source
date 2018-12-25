package com.google.android.gms.internal;

final class zzclg implements Runnable {
    private /* synthetic */ String zza;
    private /* synthetic */ String zzb;
    private /* synthetic */ String zzc;
    private /* synthetic */ long zzd;
    private /* synthetic */ zzcko zze;

    zzclg(zzcko zzcko, String str, String str2, String str3, long j) {
        this.zze = zzcko;
        this.zza = str;
        this.zzb = str2;
        this.zzc = str3;
        this.zzd = j;
    }

    public final void run() {
        if (this.zza == null) {
            this.zze.zza.zzv().zza(this.zzb, null);
            return;
        }
        zzclz zzclz = new zzclz();
        zzclz.zza = this.zzc;
        zzclz.zzb = this.zza;
        zzclz.zzc = this.zzd;
        this.zze.zza.zzv().zza(this.zzb, zzclz);
    }
}
