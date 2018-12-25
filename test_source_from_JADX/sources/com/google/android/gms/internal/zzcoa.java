package com.google.android.gms.internal;

import java.io.IOException;

public final class zzcoa extends zzflm<zzcoa> {
    private static volatile zzcoa[] zze;
    public Integer zza;
    public zzcof zzb;
    public zzcof zzc;
    public Boolean zzd;

    public zzcoa() {
        this.zza = null;
        this.zzb = null;
        this.zzc = null;
        this.zzd = null;
        this.zzax = null;
        this.zzay = -1;
    }

    public static zzcoa[] zzb() {
        if (zze == null) {
            synchronized (zzflq.zzb) {
                if (zze == null) {
                    zze = new zzcoa[0];
                }
            }
        }
        return zze;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzcoa)) {
            return false;
        }
        zzcoa zzcoa = (zzcoa) obj;
        if (this.zza == null) {
            if (zzcoa.zza != null) {
                return false;
            }
        } else if (!this.zza.equals(zzcoa.zza)) {
            return false;
        }
        if (this.zzb == null) {
            if (zzcoa.zzb != null) {
                return false;
            }
        } else if (!this.zzb.equals(zzcoa.zzb)) {
            return false;
        }
        if (this.zzc == null) {
            if (zzcoa.zzc != null) {
                return false;
            }
        } else if (!this.zzc.equals(zzcoa.zzc)) {
            return false;
        }
        if (this.zzd == null) {
            if (zzcoa.zzd != null) {
                return false;
            }
        } else if (!this.zzd.equals(zzcoa.zzd)) {
            return false;
        }
        if (this.zzax != null) {
            if (!this.zzax.zzb()) {
                return this.zzax.equals(zzcoa.zzax);
            }
        }
        return zzcoa.zzax == null || zzcoa.zzax.zzb();
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = ((getClass().getName().hashCode() + 527) * 31) + (this.zza == null ? 0 : this.zza.hashCode());
        zzcof zzcof = this.zzb;
        hashCode = (hashCode * 31) + (zzcof == null ? 0 : zzcof.hashCode());
        zzcof = this.zzc;
        hashCode = ((((hashCode * 31) + (zzcof == null ? 0 : zzcof.hashCode())) * 31) + (this.zzd == null ? 0 : this.zzd.hashCode())) * 31;
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
        if (this.zzc != null) {
            zza += zzflk.zzb(3, this.zzc);
        }
        if (this.zzd == null) {
            return zza;
        }
        this.zzd.booleanValue();
        return zza + (zzflk.zzb(4) + 1);
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
                        this.zzb = new zzcof();
                    }
                    zzfls = this.zzb;
                } else if (zza == 26) {
                    if (this.zzc == null) {
                        this.zzc = new zzcof();
                    }
                    zzfls = this.zzc;
                } else if (zza == 32) {
                    this.zzd = Boolean.valueOf(zzflj.zzd());
                } else if (!super.zza(zzflj, zza)) {
                    return this;
                }
                zzflj.zza(zzfls);
            } else {
                this.zza = Integer.valueOf(zzflj.zzh());
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
        if (this.zzd != null) {
            zzflk.zza(4, this.zzd.booleanValue());
        }
        super.zza(zzflk);
    }
}
