package com.google.android.gms.internal;

import java.io.IOException;

public final class zzcnu extends zzflm<zzcnu> {
    public Integer zza;
    public Boolean zzb;
    public String zzc;
    public String zzd;
    public String zze;

    public zzcnu() {
        this.zza = null;
        this.zzb = null;
        this.zzc = null;
        this.zzd = null;
        this.zze = null;
        this.zzax = null;
        this.zzay = -1;
    }

    private final zzcnu zzb(zzflj zzflj) throws IOException {
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
                            this.zza = Integer.valueOf(zzh);
                            break;
                        default:
                            StringBuilder stringBuilder = new StringBuilder(46);
                            stringBuilder.append(zzh);
                            stringBuilder.append(" is not a valid enum ComparisonType");
                            throw new IllegalArgumentException(stringBuilder.toString());
                    }
                } catch (IllegalArgumentException e) {
                    zzflj.zze(zzflj.zzm());
                    zza(zzflj, zza);
                }
            } else if (zza == 16) {
                this.zzb = Boolean.valueOf(zzflj.zzd());
            } else if (zza == 26) {
                this.zzc = zzflj.zze();
            } else if (zza == 34) {
                this.zzd = zzflj.zze();
            } else if (zza == 42) {
                this.zze = zzflj.zze();
            } else if (!super.zza(zzflj, zza)) {
                return this;
            }
        }
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzcnu)) {
            return false;
        }
        zzcnu zzcnu = (zzcnu) obj;
        if (this.zza == null) {
            if (zzcnu.zza != null) {
                return false;
            }
        } else if (!this.zza.equals(zzcnu.zza)) {
            return false;
        }
        if (this.zzb == null) {
            if (zzcnu.zzb != null) {
                return false;
            }
        } else if (!this.zzb.equals(zzcnu.zzb)) {
            return false;
        }
        if (this.zzc == null) {
            if (zzcnu.zzc != null) {
                return false;
            }
        } else if (!this.zzc.equals(zzcnu.zzc)) {
            return false;
        }
        if (this.zzd == null) {
            if (zzcnu.zzd != null) {
                return false;
            }
        } else if (!this.zzd.equals(zzcnu.zzd)) {
            return false;
        }
        if (this.zze == null) {
            if (zzcnu.zze != null) {
                return false;
            }
        } else if (!this.zze.equals(zzcnu.zze)) {
            return false;
        }
        if (this.zzax != null) {
            if (!this.zzax.zzb()) {
                return this.zzax.equals(zzcnu.zzax);
            }
        }
        return zzcnu.zzax == null || zzcnu.zzax.zzb();
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = (((((((((((getClass().getName().hashCode() + 527) * 31) + (this.zza == null ? 0 : this.zza.intValue())) * 31) + (this.zzb == null ? 0 : this.zzb.hashCode())) * 31) + (this.zzc == null ? 0 : this.zzc.hashCode())) * 31) + (this.zzd == null ? 0 : this.zzd.hashCode())) * 31) + (this.zze == null ? 0 : this.zze.hashCode())) * 31;
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
            this.zzb.booleanValue();
            zza += zzflk.zzb(2) + 1;
        }
        if (this.zzc != null) {
            zza += zzflk.zzb(3, this.zzc);
        }
        if (this.zzd != null) {
            zza += zzflk.zzb(4, this.zzd);
        }
        return this.zze != null ? zza + zzflk.zzb(5, this.zze) : zza;
    }

    public final /* synthetic */ zzfls zza(zzflj zzflj) throws IOException {
        return zzb(zzflj);
    }

    public final void zza(zzflk zzflk) throws IOException {
        if (this.zza != null) {
            zzflk.zza(1, this.zza.intValue());
        }
        if (this.zzb != null) {
            zzflk.zza(2, this.zzb.booleanValue());
        }
        if (this.zzc != null) {
            zzflk.zza(3, this.zzc);
        }
        if (this.zzd != null) {
            zzflk.zza(4, this.zzd);
        }
        if (this.zze != null) {
            zzflk.zza(5, this.zze);
        }
        super.zza(zzflk);
    }
}
