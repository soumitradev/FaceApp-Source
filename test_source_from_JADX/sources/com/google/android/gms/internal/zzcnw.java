package com.google.android.gms.internal;

import java.io.IOException;

public final class zzcnw extends zzflm<zzcnw> {
    public Integer zza;
    public String zzb;
    public Boolean zzc;
    public String[] zzd;

    public zzcnw() {
        this.zza = null;
        this.zzb = null;
        this.zzc = null;
        this.zzd = zzflv.zzf;
        this.zzax = null;
        this.zzay = -1;
    }

    private final zzcnw zzb(zzflj zzflj) throws IOException {
        while (true) {
            int zza = zzflj.zza();
            if (zza == 0) {
                return this;
            }
            if (zza == 8) {
                try {
                    int zzh = zzflj.zzh();
                    switch (zzh) {
                        case 0:
                        case 1:
                        case 2:
                        case 3:
                        case 4:
                        case 5:
                        case 6:
                            this.zza = Integer.valueOf(zzh);
                            break;
                        default:
                            StringBuilder stringBuilder = new StringBuilder(41);
                            stringBuilder.append(zzh);
                            stringBuilder.append(" is not a valid enum MatchType");
                            throw new IllegalArgumentException(stringBuilder.toString());
                    }
                } catch (IllegalArgumentException e) {
                    zzflj.zze(zzflj.zzm());
                    zza(zzflj, zza);
                }
            } else if (zza == 18) {
                this.zzb = zzflj.zze();
            } else if (zza == 24) {
                this.zzc = Boolean.valueOf(zzflj.zzd());
            } else if (zza == 34) {
                zza = zzflv.zza(zzflj, 34);
                int length = this.zzd == null ? 0 : this.zzd.length;
                Object obj = new String[(zza + length)];
                if (length != 0) {
                    System.arraycopy(this.zzd, 0, obj, 0, length);
                }
                while (length < obj.length - 1) {
                    obj[length] = zzflj.zze();
                    zzflj.zza();
                    length++;
                }
                obj[length] = zzflj.zze();
                this.zzd = obj;
            } else if (!super.zza(zzflj, zza)) {
                return this;
            }
        }
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzcnw)) {
            return false;
        }
        zzcnw zzcnw = (zzcnw) obj;
        if (this.zza == null) {
            if (zzcnw.zza != null) {
                return false;
            }
        } else if (!this.zza.equals(zzcnw.zza)) {
            return false;
        }
        if (this.zzb == null) {
            if (zzcnw.zzb != null) {
                return false;
            }
        } else if (!this.zzb.equals(zzcnw.zzb)) {
            return false;
        }
        if (this.zzc == null) {
            if (zzcnw.zzc != null) {
                return false;
            }
        } else if (!this.zzc.equals(zzcnw.zzc)) {
            return false;
        }
        if (!zzflq.zza(this.zzd, zzcnw.zzd)) {
            return false;
        }
        if (this.zzax != null) {
            if (!this.zzax.zzb()) {
                return this.zzax.equals(zzcnw.zzax);
            }
        }
        return zzcnw.zzax == null || zzcnw.zzax.zzb();
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = (((((((((getClass().getName().hashCode() + 527) * 31) + (this.zza == null ? 0 : this.zza.intValue())) * 31) + (this.zzb == null ? 0 : this.zzb.hashCode())) * 31) + (this.zzc == null ? 0 : this.zzc.hashCode())) * 31) + zzflq.zza(this.zzd)) * 31;
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
            this.zzc.booleanValue();
            zza += zzflk.zzb(3) + 1;
        }
        if (this.zzd == null || this.zzd.length <= 0) {
            return zza;
        }
        int i = 0;
        int i2 = 0;
        for (String str : this.zzd) {
            if (str != null) {
                i2++;
                i += zzflk.zza(str);
            }
        }
        return (zza + i) + (i2 * 1);
    }

    public final /* synthetic */ zzfls zza(zzflj zzflj) throws IOException {
        return zzb(zzflj);
    }

    public final void zza(zzflk zzflk) throws IOException {
        if (this.zza != null) {
            zzflk.zza(1, this.zza.intValue());
        }
        if (this.zzb != null) {
            zzflk.zza(2, this.zzb);
        }
        if (this.zzc != null) {
            zzflk.zza(3, this.zzc.booleanValue());
        }
        if (this.zzd != null && this.zzd.length > 0) {
            for (String str : this.zzd) {
                if (str != null) {
                    zzflk.zza(4, str);
                }
            }
        }
        super.zza(zzflk);
    }
}
