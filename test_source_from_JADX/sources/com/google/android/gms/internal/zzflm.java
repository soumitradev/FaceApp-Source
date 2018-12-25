package com.google.android.gms.internal;

import java.io.IOException;

public abstract class zzflm<M extends zzflm<M>> extends zzfls {
    protected zzflo zzax;

    public /* synthetic */ Object clone() throws CloneNotSupportedException {
        return zzc();
    }

    protected int zza() {
        if (this.zzax == null) {
            return 0;
        }
        int i = 0;
        for (int i2 = 0; i2 < this.zzax.zza(); i2++) {
            i += this.zzax.zzb(i2).zza();
        }
        return i;
    }

    public final <T> T zza(zzfln<M, T> zzfln) {
        if (this.zzax == null) {
            return null;
        }
        zzflp zza = this.zzax.zza(zzfln.zzb >>> 3);
        return zza == null ? null : zza.zza((zzfln) zzfln);
    }

    public void zza(zzflk zzflk) throws IOException {
        if (this.zzax != null) {
            for (int i = 0; i < this.zzax.zza(); i++) {
                this.zzax.zzb(i).zza(zzflk);
            }
        }
    }

    protected final boolean zza(zzflj zzflj, int i) throws IOException {
        int zzm = zzflj.zzm();
        if (!zzflj.zzb(i)) {
            return false;
        }
        int i2 = i >>> 3;
        zzflu zzflu = new zzflu(i, zzflj.zza(zzm, zzflj.zzm() - zzm));
        zzflp zzflp = null;
        if (this.zzax == null) {
            this.zzax = new zzflo();
        } else {
            zzflp = this.zzax.zza(i2);
        }
        if (zzflp == null) {
            zzflp = new zzflp();
            this.zzax.zza(i2, zzflp);
        }
        zzflp.zza(zzflu);
        return true;
    }

    public M zzc() throws CloneNotSupportedException {
        zzflm zzflm = (zzflm) super.zzd();
        zzflq.zza(this, zzflm);
        return zzflm;
    }

    public /* synthetic */ zzfls zzd() throws CloneNotSupportedException {
        return (zzflm) clone();
    }
}
