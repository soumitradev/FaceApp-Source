package com.google.android.gms.internal;

import java.io.IOException;

public final class zzcof extends zzflm<zzcof> {
    public long[] zza;
    public long[] zzb;

    public zzcof() {
        this.zza = zzflv.zzb;
        this.zzb = zzflv.zzb;
        this.zzax = null;
        this.zzay = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzcof)) {
            return false;
        }
        zzcof zzcof = (zzcof) obj;
        if (!zzflq.zza(this.zza, zzcof.zza) || !zzflq.zza(this.zzb, zzcof.zzb)) {
            return false;
        }
        if (this.zzax != null) {
            if (!this.zzax.zzb()) {
                return this.zzax.equals(zzcof.zzax);
            }
        }
        return zzcof.zzax == null || zzcof.zzax.zzb();
    }

    public final int hashCode() {
        int hashCode;
        int hashCode2 = (((((getClass().getName().hashCode() + 527) * 31) + zzflq.zza(this.zza)) * 31) + zzflq.zza(this.zzb)) * 31;
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
        int i;
        int zza = super.zza();
        if (this.zza != null && this.zza.length > 0) {
            int i2 = 0;
            for (long zza2 : this.zza) {
                i2 += zzflk.zza(zza2);
            }
            zza = (zza + i2) + (this.zza.length * 1);
        }
        if (this.zzb == null || this.zzb.length <= 0) {
            return zza;
        }
        i = 0;
        for (long zza3 : this.zzb) {
            i += zzflk.zza(zza3);
        }
        return (zza + i) + (this.zzb.length * 1);
    }

    public final /* synthetic */ zzfls zza(zzflj zzflj) throws IOException {
        while (true) {
            int zza = zzflj.zza();
            if (zza == 0) {
                return this;
            }
            int zzm;
            Object obj;
            if (zza != 8) {
                int i;
                Object obj2;
                if (zza == 10) {
                    zza = zzflj.zzc(zzflj.zzh());
                    zzm = zzflj.zzm();
                    i = 0;
                    while (zzflj.zzl() > 0) {
                        zzflj.zzi();
                        i++;
                    }
                    zzflj.zze(zzm);
                    zzm = this.zza == null ? 0 : this.zza.length;
                    obj2 = new long[(i + zzm)];
                    if (zzm != 0) {
                        System.arraycopy(this.zza, 0, obj2, 0, zzm);
                    }
                    while (zzm < obj2.length) {
                        obj2[zzm] = zzflj.zzi();
                        zzm++;
                    }
                    this.zza = obj2;
                } else if (zza == 16) {
                    zza = zzflv.zza(zzflj, 16);
                    zzm = this.zzb == null ? 0 : this.zzb.length;
                    obj = new long[(zza + zzm)];
                    if (zzm != 0) {
                        System.arraycopy(this.zzb, 0, obj, 0, zzm);
                    }
                    while (zzm < obj.length - 1) {
                        obj[zzm] = zzflj.zzi();
                        zzflj.zza();
                        zzm++;
                    }
                    obj[zzm] = zzflj.zzi();
                    this.zzb = obj;
                } else if (zza == 18) {
                    zza = zzflj.zzc(zzflj.zzh());
                    zzm = zzflj.zzm();
                    i = 0;
                    while (zzflj.zzl() > 0) {
                        zzflj.zzi();
                        i++;
                    }
                    zzflj.zze(zzm);
                    zzm = this.zzb == null ? 0 : this.zzb.length;
                    obj2 = new long[(i + zzm)];
                    if (zzm != 0) {
                        System.arraycopy(this.zzb, 0, obj2, 0, zzm);
                    }
                    while (zzm < obj2.length) {
                        obj2[zzm] = zzflj.zzi();
                        zzm++;
                    }
                    this.zzb = obj2;
                } else if (!super.zza(zzflj, zza)) {
                    return this;
                }
                zzflj.zzd(zza);
            } else {
                zza = zzflv.zza(zzflj, 8);
                zzm = this.zza == null ? 0 : this.zza.length;
                obj = new long[(zza + zzm)];
                if (zzm != 0) {
                    System.arraycopy(this.zza, 0, obj, 0, zzm);
                }
                while (zzm < obj.length - 1) {
                    obj[zzm] = zzflj.zzi();
                    zzflj.zza();
                    zzm++;
                }
                obj[zzm] = zzflj.zzi();
                this.zza = obj;
            }
        }
    }

    public final void zza(zzflk zzflk) throws IOException {
        if (this.zza != null && this.zza.length > 0) {
            for (long zza : this.zza) {
                zzflk.zza(1, zza);
            }
        }
        if (this.zzb != null && this.zzb.length > 0) {
            for (long zza2 : this.zzb) {
                zzflk.zza(2, zza2);
            }
        }
        super.zza(zzflk);
    }
}
