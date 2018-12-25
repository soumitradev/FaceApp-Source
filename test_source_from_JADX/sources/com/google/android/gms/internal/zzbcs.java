package com.google.android.gms.internal;

import com.google.android.gms.common.api.Status;

final class zzbcs implements zzben {
    private /* synthetic */ zzbcl zza;
    private /* synthetic */ zzbcr zzb;

    zzbcs(zzbcr zzbcr, zzbcl zzbcl) {
        this.zzb = zzbcr;
        this.zza = zzbcl;
    }

    public final void zza(long j) {
        this.zzb.zza(zzbcr.zzb(new Status(2103)));
    }

    public final void zza(long j, int i, Object obj) {
        if (obj == null) {
            try {
                this.zzb.zza(new zzbcx(new Status(i, null, null), null, j, null));
                return;
            } catch (ClassCastException e) {
                this.zzb.zza(zzbcr.zzb(new Status(13)));
                return;
            }
        }
        zzbcz zzbcz = (zzbcz) obj;
        String str = zzbcz.zzj;
        if (i == 0 && str != null) {
            this.zzb.zza.zzu = str;
        }
        this.zzb.zza(new zzbcx(new Status(i, zzbcz.zzc, null), str, zzbcz.zzk, zzbcz.zzd));
    }
}
