package com.google.android.gms.internal;

import java.io.IOException;

public final class zzcnr extends zzflm<zzcnr> {
    private static volatile zzcnr[] zzd;
    public Integer zza;
    public zzcnv[] zzb;
    public zzcns[] zzc;

    public zzcnr() {
        this.zza = null;
        this.zzb = zzcnv.zzb();
        this.zzc = zzcns.zzb();
        this.zzax = null;
        this.zzay = -1;
    }

    public static zzcnr[] zzb() {
        if (zzd == null) {
            synchronized (zzflq.zzb) {
                if (zzd == null) {
                    zzd = new zzcnr[0];
                }
            }
        }
        return zzd;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzcnr)) {
            return false;
        }
        zzcnr zzcnr = (zzcnr) obj;
        if (this.zza == null) {
            if (zzcnr.zza != null) {
                return false;
            }
        } else if (!this.zza.equals(zzcnr.zza)) {
            return false;
        }
        if (!zzflq.zza(this.zzb, zzcnr.zzb) || !zzflq.zza(this.zzc, zzcnr.zzc)) {
            return false;
        }
        if (this.zzax != null) {
            if (!this.zzax.zzb()) {
                return this.zzax.equals(zzcnr.zzax);
            }
        }
        return zzcnr.zzax == null || zzcnr.zzax.zzb();
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = (((((((getClass().getName().hashCode() + 527) * 31) + (this.zza == null ? 0 : this.zza.hashCode())) * 31) + zzflq.zza(this.zzb)) * 31) + zzflq.zza(this.zzc)) * 31;
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
        if (this.zzb != null && this.zzb.length > 0) {
            int i = zza;
            for (zzfls zzfls : this.zzb) {
                if (zzfls != null) {
                    i += zzflk.zzb(2, zzfls);
                }
            }
            zza = i;
        }
        if (this.zzc != null && this.zzc.length > 0) {
            for (zzfls zzfls2 : this.zzc) {
                if (zzfls2 != null) {
                    zza += zzflk.zzb(3, zzfls2);
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
                this.zza = Integer.valueOf(zzflj.zzh());
            } else if (zza == 18) {
                zza = zzflv.zza(zzflj, 18);
                r1 = this.zzb == null ? 0 : this.zzb.length;
                r0 = new zzcnv[(zza + r1)];
                if (r1 != 0) {
                    System.arraycopy(this.zzb, 0, r0, 0, r1);
                }
                while (r1 < r0.length - 1) {
                    r0[r1] = new zzcnv();
                    zzflj.zza(r0[r1]);
                    zzflj.zza();
                    r1++;
                }
                r0[r1] = new zzcnv();
                zzflj.zza(r0[r1]);
                this.zzb = r0;
            } else if (zza == 26) {
                zza = zzflv.zza(zzflj, 26);
                r1 = this.zzc == null ? 0 : this.zzc.length;
                r0 = new zzcns[(zza + r1)];
                if (r1 != 0) {
                    System.arraycopy(this.zzc, 0, r0, 0, r1);
                }
                while (r1 < r0.length - 1) {
                    r0[r1] = new zzcns();
                    zzflj.zza(r0[r1]);
                    zzflj.zza();
                    r1++;
                }
                r0[r1] = new zzcns();
                zzflj.zza(r0[r1]);
                this.zzc = r0;
            } else if (!super.zza(zzflj, zza)) {
                return this;
            }
        }
    }

    public final void zza(zzflk zzflk) throws IOException {
        if (this.zza != null) {
            zzflk.zza(1, this.zza.intValue());
        }
        if (this.zzb != null && this.zzb.length > 0) {
            for (zzfls zzfls : this.zzb) {
                if (zzfls != null) {
                    zzflk.zza(2, zzfls);
                }
            }
        }
        if (this.zzc != null && this.zzc.length > 0) {
            for (zzfls zzfls2 : this.zzc) {
                if (zzfls2 != null) {
                    zzflk.zza(3, zzfls2);
                }
            }
        }
        super.zza(zzflk);
    }
}
