package com.google.android.gms.tagmanager;

final class zzcb implements Runnable {
    private /* synthetic */ zzbz zza;
    private /* synthetic */ long zzb;
    private /* synthetic */ String zzc;
    private /* synthetic */ zzca zzd;

    zzcb(zzca zzca, zzbz zzbz, long j, String str) {
        this.zzd = zzca;
        this.zza = zzbz;
        this.zzb = j;
        this.zzc = str;
    }

    public final void run() {
        if (this.zzd.zze == null) {
            zzfo zzc = zzfo.zzc();
            zzc.zza(this.zzd.zzf, this.zza);
            this.zzd.zze = zzc.zzd();
        }
        this.zzd.zze.zza(this.zzb, this.zzc);
    }
}
