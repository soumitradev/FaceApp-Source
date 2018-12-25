package com.google.android.gms.internal;

final class zzcjk implements Runnable {
    private /* synthetic */ int zza;
    private /* synthetic */ String zzb;
    private /* synthetic */ Object zzc;
    private /* synthetic */ Object zzd;
    private /* synthetic */ Object zze;
    private /* synthetic */ zzcjj zzf;

    zzcjk(zzcjj zzcjj, int i, String str, Object obj, Object obj2, Object obj3) {
        this.zzf = zzcjj;
        this.zza = i;
        this.zzb = str;
        this.zzc = obj;
        this.zzd = obj2;
        this.zze = obj3;
    }

    public final void run() {
        zzcli zze = this.zzf.zzp.zze();
        if (zze.zzap()) {
            char c;
            if (this.zzf.zza == '\u0000') {
                zzcjj zzcjj;
                if (this.zzf.zzv().zzw()) {
                    zzcjj = this.zzf;
                    c = 'C';
                } else {
                    zzcjj = this.zzf;
                    c = 'c';
                }
                zzcjj.zza = c;
            }
            if (this.zzf.zzb < 0) {
                this.zzf.zzb = 12210;
            }
            char charAt = "01VDIWEA?".charAt(this.zza);
            c = this.zzf.zza;
            long zzb = this.zzf.zzb;
            String zza = zzcjj.zza(true, this.zzb, this.zzc, this.zzd, this.zze);
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(zza).length() + 24);
            stringBuilder.append("2");
            stringBuilder.append(charAt);
            stringBuilder.append(c);
            stringBuilder.append(zzb);
            stringBuilder.append(":");
            stringBuilder.append(zza);
            String stringBuilder2 = stringBuilder.toString();
            if (stringBuilder2.length() > 1024) {
                stringBuilder2 = this.zzb.substring(0, 1024);
            }
            zze.zzb.zza(stringBuilder2, 1);
            return;
        }
        this.zzf.zza(6, "Persisted config not initialized. Not logging error/warn");
    }
}
