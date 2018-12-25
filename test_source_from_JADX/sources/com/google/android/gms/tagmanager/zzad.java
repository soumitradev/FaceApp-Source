package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzbp;
import com.google.android.gms.internal.zzbs;
import com.google.android.gms.internal.zzdkf;

final class zzad implements zzdi<zzdkf> {
    private /* synthetic */ zzy zza;

    private zzad(zzy zzy) {
        this.zza = zzy;
    }

    public final void zza() {
    }

    public final void zza(int i) {
        if (!this.zza.zzn) {
            this.zza.zza(0);
        }
    }

    public final /* synthetic */ void zza(Object obj) {
        zzbs zzbs;
        zzdkf zzdkf = (zzdkf) obj;
        if (zzdkf.zzc != null) {
            zzbs = zzdkf.zzc;
        } else {
            zzbp zzbp = zzdkf.zzb;
            zzbs zzbs2 = new zzbs();
            zzbs2.zzb = zzbp;
            zzbs2.zza = null;
            zzbs2.zzc = zzbp.zzh;
            zzbs = zzbs2;
        }
        this.zza.zza(zzbs, zzdkf.zza, true);
    }
}
