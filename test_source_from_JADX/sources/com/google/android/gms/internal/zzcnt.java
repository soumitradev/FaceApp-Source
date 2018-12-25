package com.google.android.gms.internal;

import java.io.IOException;

public final class zzcnt extends zzflm<zzcnt> {
    private static volatile zzcnt[] zze;
    public zzcnw zza;
    public zzcnu zzb;
    public Boolean zzc;
    public String zzd;

    public zzcnt() {
        this.zza = null;
        this.zzb = null;
        this.zzc = null;
        this.zzd = null;
        this.zzax = null;
        this.zzay = -1;
    }

    public static zzcnt[] zzb() {
        if (zze == null) {
            synchronized (zzflq.zzb) {
                if (zze == null) {
                    zze = new zzcnt[0];
                }
            }
        }
        return zze;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzcnt)) {
            return false;
        }
        zzcnt zzcnt = (zzcnt) obj;
        if (this.zza == null) {
            if (zzcnt.zza != null) {
                return false;
            }
        } else if (!this.zza.equals(zzcnt.zza)) {
            return false;
        }
        if (this.zzb == null) {
            if (zzcnt.zzb != null) {
                return false;
            }
        } else if (!this.zzb.equals(zzcnt.zzb)) {
            return false;
        }
        if (this.zzc == null) {
            if (zzcnt.zzc != null) {
                return false;
            }
        } else if (!this.zzc.equals(zzcnt.zzc)) {
            return false;
        }
        if (this.zzd == null) {
            if (zzcnt.zzd != null) {
                return false;
            }
        } else if (!this.zzd.equals(zzcnt.zzd)) {
            return false;
        }
        if (this.zzax != null) {
            if (!this.zzax.zzb()) {
                return this.zzax.equals(zzcnt.zzax);
            }
        }
        return zzcnt.zzax == null || zzcnt.zzax.zzb();
    }

    public final int hashCode() {
        int hashCode = getClass().getName().hashCode() + 527;
        zzcnw zzcnw = this.zza;
        int i = 0;
        hashCode = (hashCode * 31) + (zzcnw == null ? 0 : zzcnw.hashCode());
        zzcnu zzcnu = this.zzb;
        hashCode = ((((((hashCode * 31) + (zzcnu == null ? 0 : zzcnu.hashCode())) * 31) + (this.zzc == null ? 0 : this.zzc.hashCode())) * 31) + (this.zzd == null ? 0 : this.zzd.hashCode())) * 31;
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
            this.zzc.booleanValue();
            zza += zzflk.zzb(3) + 1;
        }
        return this.zzd != null ? zza + zzflk.zzb(4, this.zzd) : zza;
    }

    public final /* synthetic */ zzfls zza(zzflj zzflj) throws IOException {
        while (true) {
            int zza = zzflj.zza();
            if (zza == 0) {
                return this;
            }
            zzfls zzfls;
            if (zza == 10) {
                if (this.zza == null) {
                    this.zza = new zzcnw();
                }
                zzfls = this.zza;
            } else if (zza == 18) {
                if (this.zzb == null) {
                    this.zzb = new zzcnu();
                }
                zzfls = this.zzb;
            } else if (zza == 24) {
                this.zzc = Boolean.valueOf(zzflj.zzd());
            } else if (zza == 34) {
                this.zzd = zzflj.zze();
            } else if (!super.zza(zzflj, zza)) {
                return this;
            }
            zzflj.zza(zzfls);
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
            zzflk.zza(3, this.zzc.booleanValue());
        }
        if (this.zzd != null) {
            zzflk.zza(4, this.zzd);
        }
        super.zza(zzflk);
    }
}
