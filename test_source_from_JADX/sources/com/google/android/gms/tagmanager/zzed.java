package com.google.android.gms.tagmanager;

final class zzed implements zzfx {
    private /* synthetic */ zzec zza;

    zzed(zzec zzec) {
        this.zza = zzec;
    }

    public final void zza(zzbx zzbx) {
        this.zza.zza(zzbx.zza());
    }

    public final void zzb(zzbx zzbx) {
        this.zza.zza(zzbx.zza());
        long zza = zzbx.zza();
        StringBuilder stringBuilder = new StringBuilder(57);
        stringBuilder.append("Permanent failure dispatching hitId: ");
        stringBuilder.append(zza);
        zzdj.zze(stringBuilder.toString());
    }

    public final void zzc(zzbx zzbx) {
        long zzb = zzbx.zzb();
        if (zzb == 0) {
            this.zza.zza(zzbx.zza(), this.zza.zzh.zza());
            return;
        }
        if (zzb + 14400000 < this.zza.zzh.zza()) {
            this.zza.zza(zzbx.zza());
            zzb = zzbx.zza();
            StringBuilder stringBuilder = new StringBuilder(47);
            stringBuilder.append("Giving up on failed hitId: ");
            stringBuilder.append(zzb);
            zzdj.zze(stringBuilder.toString());
        }
    }
}
