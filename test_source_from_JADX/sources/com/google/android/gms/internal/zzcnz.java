package com.google.android.gms.internal;

import java.io.IOException;

public final class zzcnz extends zzflm<zzcnz> {
    private static volatile zzcnz[] zzc;
    public String zza;
    public String zzb;

    public zzcnz() {
        this.zza = null;
        this.zzb = null;
        this.zzax = null;
        this.zzay = -1;
    }

    public static zzcnz[] zzb() {
        if (zzc == null) {
            synchronized (zzflq.zzb) {
                if (zzc == null) {
                    zzc = new zzcnz[0];
                }
            }
        }
        return zzc;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzcnz)) {
            return false;
        }
        zzcnz zzcnz = (zzcnz) obj;
        if (this.zza == null) {
            if (zzcnz.zza != null) {
                return false;
            }
        } else if (!this.zza.equals(zzcnz.zza)) {
            return false;
        }
        if (this.zzb == null) {
            if (zzcnz.zzb != null) {
                return false;
            }
        } else if (!this.zzb.equals(zzcnz.zzb)) {
            return false;
        }
        if (this.zzax != null) {
            if (!this.zzax.zzb()) {
                return this.zzax.equals(zzcnz.zzax);
            }
        }
        return zzcnz.zzax == null || zzcnz.zzax.zzb();
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = (((((getClass().getName().hashCode() + 527) * 31) + (this.zza == null ? 0 : this.zza.hashCode())) * 31) + (this.zzb == null ? 0 : this.zzb.hashCode())) * 31;
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
        return this.zzb != null ? zza + zzflk.zzb(2, this.zzb) : zza;
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
        super.zza(zzflk);
    }
}
