package com.google.android.gms.internal;

import java.io.IOException;

public final class zzcns extends zzflm<zzcns> {
    private static volatile zzcns[] zze;
    public Integer zza;
    public String zzb;
    public zzcnt[] zzc;
    public zzcnu zzd;
    private Boolean zzf;

    public zzcns() {
        this.zza = null;
        this.zzb = null;
        this.zzc = zzcnt.zzb();
        this.zzf = null;
        this.zzd = null;
        this.zzax = null;
        this.zzay = -1;
    }

    public static zzcns[] zzb() {
        if (zze == null) {
            synchronized (zzflq.zzb) {
                if (zze == null) {
                    zze = new zzcns[0];
                }
            }
        }
        return zze;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzcns)) {
            return false;
        }
        zzcns zzcns = (zzcns) obj;
        if (this.zza == null) {
            if (zzcns.zza != null) {
                return false;
            }
        } else if (!this.zza.equals(zzcns.zza)) {
            return false;
        }
        if (this.zzb == null) {
            if (zzcns.zzb != null) {
                return false;
            }
        } else if (!this.zzb.equals(zzcns.zzb)) {
            return false;
        }
        if (!zzflq.zza(this.zzc, zzcns.zzc)) {
            return false;
        }
        if (this.zzf == null) {
            if (zzcns.zzf != null) {
                return false;
            }
        } else if (!this.zzf.equals(zzcns.zzf)) {
            return false;
        }
        if (this.zzd == null) {
            if (zzcns.zzd != null) {
                return false;
            }
        } else if (!this.zzd.equals(zzcns.zzd)) {
            return false;
        }
        if (this.zzax != null) {
            if (!this.zzax.zzb()) {
                return this.zzax.equals(zzcns.zzax);
            }
        }
        return zzcns.zzax == null || zzcns.zzax.zzb();
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = ((((((((getClass().getName().hashCode() + 527) * 31) + (this.zza == null ? 0 : this.zza.hashCode())) * 31) + (this.zzb == null ? 0 : this.zzb.hashCode())) * 31) + zzflq.zza(this.zzc)) * 31) + (this.zzf == null ? 0 : this.zzf.hashCode());
        zzcnu zzcnu = this.zzd;
        hashCode = ((hashCode * 31) + (zzcnu == null ? 0 : zzcnu.hashCode())) * 31;
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
        if (this.zzc != null && this.zzc.length > 0) {
            for (zzfls zzfls : this.zzc) {
                if (zzfls != null) {
                    zza += zzflk.zzb(3, zzfls);
                }
            }
        }
        if (this.zzf != null) {
            this.zzf.booleanValue();
            zza += zzflk.zzb(4) + 1;
        }
        return this.zzd != null ? zza + zzflk.zzb(5, this.zzd) : zza;
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
                zza = zzflv.zza(zzflj, 26);
                int length = this.zzc == null ? 0 : this.zzc.length;
                Object obj = new zzcnt[(zza + length)];
                if (length != 0) {
                    System.arraycopy(this.zzc, 0, obj, 0, length);
                }
                while (length < obj.length - 1) {
                    obj[length] = new zzcnt();
                    zzflj.zza(obj[length]);
                    zzflj.zza();
                    length++;
                }
                obj[length] = new zzcnt();
                zzflj.zza(obj[length]);
                this.zzc = obj;
            } else if (zza == 32) {
                this.zzf = Boolean.valueOf(zzflj.zzd());
            } else if (zza == 42) {
                if (this.zzd == null) {
                    this.zzd = new zzcnu();
                }
                zzflj.zza(this.zzd);
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
        if (this.zzc != null && this.zzc.length > 0) {
            for (zzfls zzfls : this.zzc) {
                if (zzfls != null) {
                    zzflk.zza(3, zzfls);
                }
            }
        }
        if (this.zzf != null) {
            zzflk.zza(4, this.zzf.booleanValue());
        }
        if (this.zzd != null) {
            zzflk.zza(5, this.zzd);
        }
        super.zza(zzflk);
    }
}
