package com.google.android.gms.internal;

import java.io.IOException;

public final class zzcoc extends zzflm<zzcoc> {
    private static volatile zzcoc[] zze;
    public String zza;
    public String zzb;
    public Long zzc;
    public Double zzd;
    private Float zzf;

    public zzcoc() {
        this.zza = null;
        this.zzb = null;
        this.zzc = null;
        this.zzf = null;
        this.zzd = null;
        this.zzax = null;
        this.zzay = -1;
    }

    public static zzcoc[] zzb() {
        if (zze == null) {
            synchronized (zzflq.zzb) {
                if (zze == null) {
                    zze = new zzcoc[0];
                }
            }
        }
        return zze;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzcoc)) {
            return false;
        }
        zzcoc zzcoc = (zzcoc) obj;
        if (this.zza == null) {
            if (zzcoc.zza != null) {
                return false;
            }
        } else if (!this.zza.equals(zzcoc.zza)) {
            return false;
        }
        if (this.zzb == null) {
            if (zzcoc.zzb != null) {
                return false;
            }
        } else if (!this.zzb.equals(zzcoc.zzb)) {
            return false;
        }
        if (this.zzc == null) {
            if (zzcoc.zzc != null) {
                return false;
            }
        } else if (!this.zzc.equals(zzcoc.zzc)) {
            return false;
        }
        if (this.zzf == null) {
            if (zzcoc.zzf != null) {
                return false;
            }
        } else if (!this.zzf.equals(zzcoc.zzf)) {
            return false;
        }
        if (this.zzd == null) {
            if (zzcoc.zzd != null) {
                return false;
            }
        } else if (!this.zzd.equals(zzcoc.zzd)) {
            return false;
        }
        if (this.zzax != null) {
            if (!this.zzax.zzb()) {
                return this.zzax.equals(zzcoc.zzax);
            }
        }
        return zzcoc.zzax == null || zzcoc.zzax.zzb();
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = (((((((((((getClass().getName().hashCode() + 527) * 31) + (this.zza == null ? 0 : this.zza.hashCode())) * 31) + (this.zzb == null ? 0 : this.zzb.hashCode())) * 31) + (this.zzc == null ? 0 : this.zzc.hashCode())) * 31) + (this.zzf == null ? 0 : this.zzf.hashCode())) * 31) + (this.zzd == null ? 0 : this.zzd.hashCode())) * 31;
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
            zza += zzflk.zzb(1, this.zza);
        }
        if (this.zzb != null) {
            zza += zzflk.zzb(2, this.zzb);
        }
        if (this.zzc != null) {
            zza += zzflk.zze(3, this.zzc.longValue());
        }
        if (this.zzf != null) {
            this.zzf.floatValue();
            zza += zzflk.zzb(4) + 4;
        }
        if (this.zzd == null) {
            return zza;
        }
        this.zzd.doubleValue();
        return zza + (zzflk.zzb(5) + 8);
    }

    public final /* synthetic */ zzfls zza(zzflj zzflj) throws IOException {
        while (true) {
            int zza = zzflj.zza();
            if (zza == 0) {
                return this;
            }
            if (zza == 10) {
                this.zza = zzflj.zze();
            } else if (zza == 18) {
                this.zzb = zzflj.zze();
            } else if (zza == 24) {
                this.zzc = Long.valueOf(zzflj.zzi());
            } else if (zza == 37) {
                this.zzf = Float.valueOf(Float.intBitsToFloat(zzflj.zzj()));
            } else if (zza == 41) {
                this.zzd = Double.valueOf(Double.longBitsToDouble(zzflj.zzk()));
            } else if (!super.zza(zzflj, zza)) {
                return this;
            }
        }
    }

    public final void zza(zzflk zzflk) throws IOException {
        if (this.zza != null) {
            zzflk.zza(1, this.zza);
        }
        if (this.zzb != null) {
            zzflk.zza(2, this.zzb);
        }
        if (this.zzc != null) {
            zzflk.zzb(3, this.zzc.longValue());
        }
        if (this.zzf != null) {
            zzflk.zza(4, this.zzf.floatValue());
        }
        if (this.zzd != null) {
            zzflk.zza(5, this.zzd.doubleValue());
        }
        super.zza(zzflk);
    }
}
