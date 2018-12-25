package com.google.android.gms.internal;

import java.io.IOException;

public final class zzcob extends zzflm<zzcob> {
    private static volatile zzcob[] zzf;
    public zzcoc[] zza;
    public String zzb;
    public Long zzc;
    public Long zzd;
    public Integer zze;

    public zzcob() {
        this.zza = zzcoc.zzb();
        this.zzb = null;
        this.zzc = null;
        this.zzd = null;
        this.zze = null;
        this.zzax = null;
        this.zzay = -1;
    }

    public static zzcob[] zzb() {
        if (zzf == null) {
            synchronized (zzflq.zzb) {
                if (zzf == null) {
                    zzf = new zzcob[0];
                }
            }
        }
        return zzf;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzcob)) {
            return false;
        }
        zzcob zzcob = (zzcob) obj;
        if (!zzflq.zza(this.zza, zzcob.zza)) {
            return false;
        }
        if (this.zzb == null) {
            if (zzcob.zzb != null) {
                return false;
            }
        } else if (!this.zzb.equals(zzcob.zzb)) {
            return false;
        }
        if (this.zzc == null) {
            if (zzcob.zzc != null) {
                return false;
            }
        } else if (!this.zzc.equals(zzcob.zzc)) {
            return false;
        }
        if (this.zzd == null) {
            if (zzcob.zzd != null) {
                return false;
            }
        } else if (!this.zzd.equals(zzcob.zzd)) {
            return false;
        }
        if (this.zze == null) {
            if (zzcob.zze != null) {
                return false;
            }
        } else if (!this.zze.equals(zzcob.zze)) {
            return false;
        }
        if (this.zzax != null) {
            if (!this.zzax.zzb()) {
                return this.zzax.equals(zzcob.zzax);
            }
        }
        return zzcob.zzax == null || zzcob.zzax.zzb();
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = (((((((((((getClass().getName().hashCode() + 527) * 31) + zzflq.zza(this.zza)) * 31) + (this.zzb == null ? 0 : this.zzb.hashCode())) * 31) + (this.zzc == null ? 0 : this.zzc.hashCode())) * 31) + (this.zzd == null ? 0 : this.zzd.hashCode())) * 31) + (this.zze == null ? 0 : this.zze.hashCode())) * 31;
        if (this.zzax != null) {
            if (!this.zzax.zzb()) {
                i = this.zzax.hashCode();
            }
        }
        return hashCode + i;
    }

    protected final int zza() {
        int zza = super.zza();
        if (this.zza != null && this.zza.length > 0) {
            for (zzfls zzfls : this.zza) {
                if (zzfls != null) {
                    zza += zzflk.zzb(1, zzfls);
                }
            }
        }
        if (this.zzb != null) {
            zza += zzflk.zzb(2, this.zzb);
        }
        if (this.zzc != null) {
            zza += zzflk.zze(3, this.zzc.longValue());
        }
        if (this.zzd != null) {
            zza += zzflk.zze(4, this.zzd.longValue());
        }
        return this.zze != null ? zza + zzflk.zzb(5, this.zze.intValue()) : zza;
    }

    public final /* synthetic */ zzfls zza(zzflj zzflj) throws IOException {
        while (true) {
            int zza = zzflj.zza();
            if (zza == 0) {
                return this;
            }
            if (zza == 10) {
                zza = zzflv.zza(zzflj, 10);
                int length = this.zza == null ? 0 : this.zza.length;
                Object obj = new zzcoc[(zza + length)];
                if (length != 0) {
                    System.arraycopy(this.zza, 0, obj, 0, length);
                }
                while (length < obj.length - 1) {
                    obj[length] = new zzcoc();
                    zzflj.zza(obj[length]);
                    zzflj.zza();
                    length++;
                }
                obj[length] = new zzcoc();
                zzflj.zza(obj[length]);
                this.zza = obj;
            } else if (zza == 18) {
                this.zzb = zzflj.zze();
            } else if (zza == 24) {
                this.zzc = Long.valueOf(zzflj.zzi());
            } else if (zza == 32) {
                this.zzd = Long.valueOf(zzflj.zzi());
            } else if (zza == 40) {
                this.zze = Integer.valueOf(zzflj.zzh());
            } else if (!super.zza(zzflj, zza)) {
                return this;
            }
        }
    }

    public final void zza(zzflk zzflk) throws IOException {
        if (this.zza != null && this.zza.length > 0) {
            for (zzfls zzfls : this.zza) {
                if (zzfls != null) {
                    zzflk.zza(1, zzfls);
                }
            }
        }
        if (this.zzb != null) {
            zzflk.zza(2, this.zzb);
        }
        if (this.zzc != null) {
            zzflk.zzb(3, this.zzc.longValue());
        }
        if (this.zzd != null) {
            zzflk.zzb(4, this.zzd.longValue());
        }
        if (this.zze != null) {
            zzflk.zza(5, this.zze.intValue());
        }
        super.zza(zzflk);
    }
}
