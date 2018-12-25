package com.google.android.gms.internal;

import java.io.IOException;

public final class zzbk extends zzflm<zzbk> {
    private int zza;
    private int zzb;
    private int zzc;

    public zzbk() {
        this.zza = 1;
        this.zzb = 0;
        this.zzc = 0;
        this.zzax = null;
        this.zzay = -1;
    }

    private final zzbk zzb(zzflj zzflj) throws IOException {
        while (true) {
            int zza = zzflj.zza();
            if (zza == 0) {
                return this;
            }
            if (zza == 8) {
                try {
                    int zzh = zzflj.zzh();
                    switch (zzh) {
                        case 1:
                        case 2:
                        case 3:
                            this.zza = zzh;
                            break;
                        default:
                            StringBuilder stringBuilder = new StringBuilder(42);
                            stringBuilder.append(zzh);
                            stringBuilder.append(" is not a valid enum CacheLevel");
                            throw new IllegalArgumentException(stringBuilder.toString());
                    }
                } catch (IllegalArgumentException e) {
                    zzflj.zze(zzflj.zzm());
                    zza(zzflj, zza);
                }
            } else if (zza == 16) {
                this.zzb = zzflj.zzh();
            } else if (zza == 24) {
                this.zzc = zzflj.zzh();
            } else if (!super.zza(zzflj, zza)) {
                return this;
            }
        }
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzbk)) {
            return false;
        }
        zzbk zzbk = (zzbk) obj;
        if (this.zza != zzbk.zza || this.zzb != zzbk.zzb || this.zzc != zzbk.zzc) {
            return false;
        }
        if (this.zzax != null) {
            if (!this.zzax.zzb()) {
                return this.zzax.equals(zzbk.zzax);
            }
        }
        return zzbk.zzax == null || zzbk.zzax.zzb();
    }

    public final int hashCode() {
        int hashCode;
        int hashCode2 = (((((((getClass().getName().hashCode() + 527) * 31) + this.zza) * 31) + this.zzb) * 31) + this.zzc) * 31;
        if (this.zzax != null) {
            if (!this.zzax.zzb()) {
                hashCode = this.zzax.hashCode();
                return hashCode2 + hashCode;
            }
        }
        hashCode = 0;
        return hashCode2 + hashCode;
    }

    protected final int zza() {
        int zza = super.zza();
        if (this.zza != 1) {
            zza += zzflk.zzb(1, this.zza);
        }
        if (this.zzb != 0) {
            zza += zzflk.zzb(2, this.zzb);
        }
        return this.zzc != 0 ? zza + zzflk.zzb(3, this.zzc) : zza;
    }

    public final /* synthetic */ zzfls zza(zzflj zzflj) throws IOException {
        return zzb(zzflj);
    }

    public final void zza(zzflk zzflk) throws IOException {
        if (this.zza != 1) {
            zzflk.zza(1, this.zza);
        }
        if (this.zzb != 0) {
            zzflk.zza(2, this.zzb);
        }
        if (this.zzc != 0) {
            zzflk.zza(3, this.zzc);
        }
        super.zza(zzflk);
    }
}
