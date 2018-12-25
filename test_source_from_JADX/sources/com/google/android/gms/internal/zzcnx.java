package com.google.android.gms.internal;

import java.io.IOException;

public final class zzcnx extends zzflm<zzcnx> {
    private static volatile zzcnx[] zze;
    public String zza;
    public Boolean zzb;
    public Boolean zzc;
    public Integer zzd;

    public zzcnx() {
        this.zza = null;
        this.zzb = null;
        this.zzc = null;
        this.zzd = null;
        this.zzax = null;
        this.zzay = -1;
    }

    public static zzcnx[] zzb() {
        if (zze == null) {
            synchronized (zzflq.zzb) {
                if (zze == null) {
                    zze = new zzcnx[0];
                }
            }
        }
        return zze;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzcnx)) {
            return false;
        }
        zzcnx zzcnx = (zzcnx) obj;
        if (this.zza == null) {
            if (zzcnx.zza != null) {
                return false;
            }
        } else if (!this.zza.equals(zzcnx.zza)) {
            return false;
        }
        if (this.zzb == null) {
            if (zzcnx.zzb != null) {
                return false;
            }
        } else if (!this.zzb.equals(zzcnx.zzb)) {
            return false;
        }
        if (this.zzc == null) {
            if (zzcnx.zzc != null) {
                return false;
            }
        } else if (!this.zzc.equals(zzcnx.zzc)) {
            return false;
        }
        if (this.zzd == null) {
            if (zzcnx.zzd != null) {
                return false;
            }
        } else if (!this.zzd.equals(zzcnx.zzd)) {
            return false;
        }
        if (this.zzax != null) {
            if (!this.zzax.zzb()) {
                return this.zzax.equals(zzcnx.zzax);
            }
        }
        return zzcnx.zzax == null || zzcnx.zzax.zzb();
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = (((((((((getClass().getName().hashCode() + 527) * 31) + (this.zza == null ? 0 : this.zza.hashCode())) * 31) + (this.zzb == null ? 0 : this.zzb.hashCode())) * 31) + (this.zzc == null ? 0 : this.zzc.hashCode())) * 31) + (this.zzd == null ? 0 : this.zzd.hashCode())) * 31;
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
            this.zzb.booleanValue();
            zza += zzflk.zzb(2) + 1;
        }
        if (this.zzc != null) {
            this.zzc.booleanValue();
            zza += zzflk.zzb(3) + 1;
        }
        return this.zzd != null ? zza + zzflk.zzb(4, this.zzd.intValue()) : zza;
    }

    public final /* synthetic */ zzfls zza(zzflj zzflj) throws IOException {
        while (true) {
            int zza = zzflj.zza();
            if (zza == 0) {
                return this;
            }
            if (zza == 10) {
                this.zza = zzflj.zze();
            } else if (zza == 16) {
                this.zzb = Boolean.valueOf(zzflj.zzd());
            } else if (zza == 24) {
                this.zzc = Boolean.valueOf(zzflj.zzd());
            } else if (zza == 32) {
                this.zzd = Integer.valueOf(zzflj.zzh());
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
            zzflk.zza(2, this.zzb.booleanValue());
        }
        if (this.zzc != null) {
            zzflk.zza(3, this.zzc.booleanValue());
        }
        if (this.zzd != null) {
            zzflk.zza(4, this.zzd.intValue());
        }
        super.zza(zzflk);
    }
}
