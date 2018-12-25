package com.google.android.gms.internal;

import java.io.IOException;

public final class zzdkf extends zzflm<zzdkf> {
    public long zza;
    public zzbp zzb;
    public zzbs zzc;

    public zzdkf() {
        this.zza = 0;
        this.zzb = null;
        this.zzc = null;
        this.zzax = null;
        this.zzay = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzdkf)) {
            return false;
        }
        zzdkf zzdkf = (zzdkf) obj;
        if (this.zza != zzdkf.zza) {
            return false;
        }
        if (this.zzb == null) {
            if (zzdkf.zzb != null) {
                return false;
            }
        } else if (!this.zzb.equals(zzdkf.zzb)) {
            return false;
        }
        if (this.zzc == null) {
            if (zzdkf.zzc != null) {
                return false;
            }
        } else if (!this.zzc.equals(zzdkf.zzc)) {
            return false;
        }
        if (this.zzax != null) {
            if (!this.zzax.zzb()) {
                return this.zzax.equals(zzdkf.zzax);
            }
        }
        return zzdkf.zzax == null || zzdkf.zzax.zzb();
    }

    public final int hashCode() {
        int hashCode = ((getClass().getName().hashCode() + 527) * 31) + ((int) (this.zza ^ (this.zza >>> 32)));
        zzbp zzbp = this.zzb;
        int i = 0;
        hashCode = (hashCode * 31) + (zzbp == null ? 0 : zzbp.hashCode());
        zzbs zzbs = this.zzc;
        hashCode = ((hashCode * 31) + (zzbs == null ? 0 : zzbs.hashCode())) * 31;
        if (this.zzax != null) {
            if (!this.zzax.zzb()) {
                i = this.zzax.hashCode();
            }
        }
        return hashCode + i;
    }

    protected final int zza() {
        int zza = super.zza() + zzflk.zze(1, this.zza);
        if (this.zzb != null) {
            zza += zzflk.zzb(2, this.zzb);
        }
        return this.zzc != null ? zza + zzflk.zzb(3, this.zzc) : zza;
    }

    public final /* synthetic */ zzfls zza(zzflj zzflj) throws IOException {
        while (true) {
            int zza = zzflj.zza();
            if (zza == 0) {
                return this;
            }
            if (zza != 8) {
                zzfls zzfls;
                if (zza == 18) {
                    if (this.zzb == null) {
                        this.zzb = new zzbp();
                    }
                    zzfls = this.zzb;
                } else if (zza == 26) {
                    if (this.zzc == null) {
                        this.zzc = new zzbs();
                    }
                    zzfls = this.zzc;
                } else if (!super.zza(zzflj, zza)) {
                    return this;
                }
                zzflj.zza(zzfls);
            } else {
                this.zza = zzflj.zzi();
            }
        }
    }

    public final void zza(zzflk zzflk) throws IOException {
        zzflk.zzb(1, this.zza);
        if (this.zzb != null) {
            zzflk.zza(2, this.zzb);
        }
        if (this.zzc != null) {
            zzflk.zza(3, this.zzc);
        }
        super.zza(zzflk);
    }
}
