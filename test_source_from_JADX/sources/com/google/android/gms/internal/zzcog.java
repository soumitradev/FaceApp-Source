package com.google.android.gms.internal;

import java.io.IOException;

public final class zzcog extends zzflm<zzcog> {
    private static volatile zzcog[] zzf;
    public Long zza;
    public String zzb;
    public String zzc;
    public Long zzd;
    public Double zze;
    private Float zzg;

    public zzcog() {
        this.zza = null;
        this.zzb = null;
        this.zzc = null;
        this.zzd = null;
        this.zzg = null;
        this.zze = null;
        this.zzax = null;
        this.zzay = -1;
    }

    public static zzcog[] zzb() {
        if (zzf == null) {
            synchronized (zzflq.zzb) {
                if (zzf == null) {
                    zzf = new zzcog[0];
                }
            }
        }
        return zzf;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzcog)) {
            return false;
        }
        zzcog zzcog = (zzcog) obj;
        if (this.zza == null) {
            if (zzcog.zza != null) {
                return false;
            }
        } else if (!this.zza.equals(zzcog.zza)) {
            return false;
        }
        if (this.zzb == null) {
            if (zzcog.zzb != null) {
                return false;
            }
        } else if (!this.zzb.equals(zzcog.zzb)) {
            return false;
        }
        if (this.zzc == null) {
            if (zzcog.zzc != null) {
                return false;
            }
        } else if (!this.zzc.equals(zzcog.zzc)) {
            return false;
        }
        if (this.zzd == null) {
            if (zzcog.zzd != null) {
                return false;
            }
        } else if (!this.zzd.equals(zzcog.zzd)) {
            return false;
        }
        if (this.zzg == null) {
            if (zzcog.zzg != null) {
                return false;
            }
        } else if (!this.zzg.equals(zzcog.zzg)) {
            return false;
        }
        if (this.zze == null) {
            if (zzcog.zze != null) {
                return false;
            }
        } else if (!this.zze.equals(zzcog.zze)) {
            return false;
        }
        if (this.zzax != null) {
            if (!this.zzax.zzb()) {
                return this.zzax.equals(zzcog.zzax);
            }
        }
        return zzcog.zzax == null || zzcog.zzax.zzb();
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = (((((((((((((getClass().getName().hashCode() + 527) * 31) + (this.zza == null ? 0 : this.zza.hashCode())) * 31) + (this.zzb == null ? 0 : this.zzb.hashCode())) * 31) + (this.zzc == null ? 0 : this.zzc.hashCode())) * 31) + (this.zzd == null ? 0 : this.zzd.hashCode())) * 31) + (this.zzg == null ? 0 : this.zzg.hashCode())) * 31) + (this.zze == null ? 0 : this.zze.hashCode())) * 31;
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
            zza += zzflk.zze(1, this.zza.longValue());
        }
        if (this.zzb != null) {
            zza += zzflk.zzb(2, this.zzb);
        }
        if (this.zzc != null) {
            zza += zzflk.zzb(3, this.zzc);
        }
        if (this.zzd != null) {
            zza += zzflk.zze(4, this.zzd.longValue());
        }
        if (this.zzg != null) {
            this.zzg.floatValue();
            zza += zzflk.zzb(5) + 4;
        }
        if (this.zze == null) {
            return zza;
        }
        this.zze.doubleValue();
        return zza + (zzflk.zzb(6) + 8);
    }

    public final /* synthetic */ zzfls zza(zzflj zzflj) throws IOException {
        while (true) {
            int zza = zzflj.zza();
            if (zza == 0) {
                return this;
            }
            if (zza == 8) {
                this.zza = Long.valueOf(zzflj.zzi());
            } else if (zza == 18) {
                this.zzb = zzflj.zze();
            } else if (zza == 26) {
                this.zzc = zzflj.zze();
            } else if (zza == 32) {
                this.zzd = Long.valueOf(zzflj.zzi());
            } else if (zza == 45) {
                this.zzg = Float.valueOf(Float.intBitsToFloat(zzflj.zzj()));
            } else if (zza == 49) {
                this.zze = Double.valueOf(Double.longBitsToDouble(zzflj.zzk()));
            } else if (!super.zza(zzflj, zza)) {
                return this;
            }
        }
    }

    public final void zza(zzflk zzflk) throws IOException {
        if (this.zza != null) {
            zzflk.zzb(1, this.zza.longValue());
        }
        if (this.zzb != null) {
            zzflk.zza(2, this.zzb);
        }
        if (this.zzc != null) {
            zzflk.zza(3, this.zzc);
        }
        if (this.zzd != null) {
            zzflk.zzb(4, this.zzd.longValue());
        }
        if (this.zzg != null) {
            zzflk.zza(5, this.zzg.floatValue());
        }
        if (this.zze != null) {
            zzflk.zza(6, this.zze.doubleValue());
        }
        super.zza(zzflk);
    }
}
