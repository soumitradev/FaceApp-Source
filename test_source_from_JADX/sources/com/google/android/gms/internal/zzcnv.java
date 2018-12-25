package com.google.android.gms.internal;

import java.io.IOException;

public final class zzcnv extends zzflm<zzcnv> {
    private static volatile zzcnv[] zzd;
    public Integer zza;
    public String zzb;
    public zzcnt zzc;

    public zzcnv() {
        this.zza = null;
        this.zzb = null;
        this.zzc = null;
        this.zzax = null;
        this.zzay = -1;
    }

    public static zzcnv[] zzb() {
        if (zzd == null) {
            synchronized (zzflq.zzb) {
                if (zzd == null) {
                    zzd = new zzcnv[0];
                }
            }
        }
        return zzd;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzcnv)) {
            return false;
        }
        zzcnv zzcnv = (zzcnv) obj;
        if (this.zza == null) {
            if (zzcnv.zza != null) {
                return false;
            }
        } else if (!this.zza.equals(zzcnv.zza)) {
            return false;
        }
        if (this.zzb == null) {
            if (zzcnv.zzb != null) {
                return false;
            }
        } else if (!this.zzb.equals(zzcnv.zzb)) {
            return false;
        }
        if (this.zzc == null) {
            if (zzcnv.zzc != null) {
                return false;
            }
        } else if (!this.zzc.equals(zzcnv.zzc)) {
            return false;
        }
        if (this.zzax != null) {
            if (!this.zzax.zzb()) {
                return this.zzax.equals(zzcnv.zzax);
            }
        }
        return zzcnv.zzax == null || zzcnv.zzax.zzb();
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = ((((getClass().getName().hashCode() + 527) * 31) + (this.zza == null ? 0 : this.zza.hashCode())) * 31) + (this.zzb == null ? 0 : this.zzb.hashCode());
        zzcnt zzcnt = this.zzc;
        hashCode = ((hashCode * 31) + (zzcnt == null ? 0 : zzcnt.hashCode())) * 31;
        if (this.zzax != null) {
            if (!this.zzax.zzb()) {
                i = this.zzax.hashCode();
            }
        }
        return hashCode + i;
    }

    protected final int zza() {
        int zza = super.zza();
        if (this.zza != null) {
            zza += zzflk.zzb(1, this.zza.intValue());
        }
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
            if (zza == 8) {
                this.zza = Integer.valueOf(zzflj.zzh());
            } else if (zza == 18) {
                this.zzb = zzflj.zze();
            } else if (zza == 26) {
                if (this.zzc == null) {
                    this.zzc = new zzcnt();
                }
                zzflj.zza(this.zzc);
            } else if (!super.zza(zzflj, zza)) {
                return this;
            }
        }
    }

    public final void zza(zzflk zzflk) throws IOException {
        if (this.zza != null) {
            zzflk.zza(1, this.zza.intValue());
        }
        if (this.zzb != null) {
            zzflk.zza(2, this.zzb);
        }
        if (this.zzc != null) {
            zzflk.zza(3, this.zzc);
        }
        super.zza(zzflk);
    }
}
