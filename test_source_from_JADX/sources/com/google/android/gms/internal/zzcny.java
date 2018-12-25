package com.google.android.gms.internal;

import java.io.IOException;

public final class zzcny extends zzflm<zzcny> {
    public Long zza;
    public String zzb;
    public zzcnz[] zzc;
    public zzcnx[] zzd;
    public zzcnr[] zze;
    private Integer zzf;

    public zzcny() {
        this.zza = null;
        this.zzb = null;
        this.zzf = null;
        this.zzc = zzcnz.zzb();
        this.zzd = zzcnx.zzb();
        this.zze = zzcnr.zzb();
        this.zzax = null;
        this.zzay = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzcny)) {
            return false;
        }
        zzcny zzcny = (zzcny) obj;
        if (this.zza == null) {
            if (zzcny.zza != null) {
                return false;
            }
        } else if (!this.zza.equals(zzcny.zza)) {
            return false;
        }
        if (this.zzb == null) {
            if (zzcny.zzb != null) {
                return false;
            }
        } else if (!this.zzb.equals(zzcny.zzb)) {
            return false;
        }
        if (this.zzf == null) {
            if (zzcny.zzf != null) {
                return false;
            }
        } else if (!this.zzf.equals(zzcny.zzf)) {
            return false;
        }
        if (!zzflq.zza(this.zzc, zzcny.zzc) || !zzflq.zza(this.zzd, zzcny.zzd) || !zzflq.zza(this.zze, zzcny.zze)) {
            return false;
        }
        if (this.zzax != null) {
            if (!this.zzax.zzb()) {
                return this.zzax.equals(zzcny.zzax);
            }
        }
        return zzcny.zzax == null || zzcny.zzax.zzb();
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = (((((((((((((getClass().getName().hashCode() + 527) * 31) + (this.zza == null ? 0 : this.zza.hashCode())) * 31) + (this.zzb == null ? 0 : this.zzb.hashCode())) * 31) + (this.zzf == null ? 0 : this.zzf.hashCode())) * 31) + zzflq.zza(this.zzc)) * 31) + zzflq.zza(this.zzd)) * 31) + zzflq.zza(this.zze)) * 31;
        if (this.zzax != null) {
            if (!this.zzax.zzb()) {
                i = this.zzax.hashCode();
            }
        }
        return hashCode + i;
    }

    protected final int zza() {
        int i;
        int zza = super.zza();
        if (this.zza != null) {
            zza += zzflk.zze(1, this.zza.longValue());
        }
        if (this.zzb != null) {
            zza += zzflk.zzb(2, this.zzb);
        }
        if (this.zzf != null) {
            zza += zzflk.zzb(3, this.zzf.intValue());
        }
        if (this.zzc != null && this.zzc.length > 0) {
            i = zza;
            for (zzfls zzfls : this.zzc) {
                if (zzfls != null) {
                    i += zzflk.zzb(4, zzfls);
                }
            }
            zza = i;
        }
        if (this.zzd != null && this.zzd.length > 0) {
            i = zza;
            for (zzfls zzfls2 : this.zzd) {
                if (zzfls2 != null) {
                    i += zzflk.zzb(5, zzfls2);
                }
            }
            zza = i;
        }
        if (this.zze != null && this.zze.length > 0) {
            for (zzfls zzfls3 : this.zze) {
                if (zzfls3 != null) {
                    zza += zzflk.zzb(6, zzfls3);
                }
            }
        }
        return zza;
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
            } else if (zza == 24) {
                this.zzf = Integer.valueOf(zzflj.zzh());
            } else if (zza == 34) {
                zza = zzflv.zza(zzflj, 34);
                r1 = this.zzc == null ? 0 : this.zzc.length;
                r0 = new zzcnz[(zza + r1)];
                if (r1 != 0) {
                    System.arraycopy(this.zzc, 0, r0, 0, r1);
                }
                while (r1 < r0.length - 1) {
                    r0[r1] = new zzcnz();
                    zzflj.zza(r0[r1]);
                    zzflj.zza();
                    r1++;
                }
                r0[r1] = new zzcnz();
                zzflj.zza(r0[r1]);
                this.zzc = r0;
            } else if (zza == 42) {
                zza = zzflv.zza(zzflj, 42);
                r1 = this.zzd == null ? 0 : this.zzd.length;
                r0 = new zzcnx[(zza + r1)];
                if (r1 != 0) {
                    System.arraycopy(this.zzd, 0, r0, 0, r1);
                }
                while (r1 < r0.length - 1) {
                    r0[r1] = new zzcnx();
                    zzflj.zza(r0[r1]);
                    zzflj.zza();
                    r1++;
                }
                r0[r1] = new zzcnx();
                zzflj.zza(r0[r1]);
                this.zzd = r0;
            } else if (zza == 50) {
                zza = zzflv.zza(zzflj, 50);
                r1 = this.zze == null ? 0 : this.zze.length;
                r0 = new zzcnr[(zza + r1)];
                if (r1 != 0) {
                    System.arraycopy(this.zze, 0, r0, 0, r1);
                }
                while (r1 < r0.length - 1) {
                    r0[r1] = new zzcnr();
                    zzflj.zza(r0[r1]);
                    zzflj.zza();
                    r1++;
                }
                r0[r1] = new zzcnr();
                zzflj.zza(r0[r1]);
                this.zze = r0;
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
        if (this.zzf != null) {
            zzflk.zza(3, this.zzf.intValue());
        }
        if (this.zzc != null && this.zzc.length > 0) {
            for (zzfls zzfls : this.zzc) {
                if (zzfls != null) {
                    zzflk.zza(4, zzfls);
                }
            }
        }
        if (this.zzd != null && this.zzd.length > 0) {
            for (zzfls zzfls2 : this.zzd) {
                if (zzfls2 != null) {
                    zzflk.zza(5, zzfls2);
                }
            }
        }
        if (this.zze != null && this.zze.length > 0) {
            for (zzfls zzfls3 : this.zze) {
                if (zzfls3 != null) {
                    zzflk.zza(6, zzfls3);
                }
            }
        }
        super.zza(zzflk);
    }
}
