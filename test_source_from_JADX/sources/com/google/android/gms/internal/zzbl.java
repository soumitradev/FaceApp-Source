package com.google.android.gms.internal;

import java.io.IOException;

public final class zzbl extends zzflm<zzbl> {
    private static volatile zzbl[] zzb;
    public int[] zza;
    private int zzc;
    private int zzd;
    private boolean zze;
    private boolean zzf;

    public zzbl() {
        this.zza = zzflv.zza;
        this.zzc = 0;
        this.zzd = 0;
        this.zze = false;
        this.zzf = false;
        this.zzax = null;
        this.zzay = -1;
    }

    public static zzbl[] zzb() {
        if (zzb == null) {
            synchronized (zzflq.zzb) {
                if (zzb == null) {
                    zzb = new zzbl[0];
                }
            }
        }
        return zzb;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzbl)) {
            return false;
        }
        zzbl zzbl = (zzbl) obj;
        if (!zzflq.zza(this.zza, zzbl.zza) || this.zzc != zzbl.zzc || this.zzd != zzbl.zzd || this.zze != zzbl.zze || this.zzf != zzbl.zzf) {
            return false;
        }
        if (this.zzax != null) {
            if (!this.zzax.zzb()) {
                return this.zzax.equals(zzbl.zzax);
            }
        }
        return zzbl.zzax == null || zzbl.zzax.zzb();
    }

    public final int hashCode() {
        int hashCode;
        int i = 1237;
        int hashCode2 = (((((((((getClass().getName().hashCode() + 527) * 31) + zzflq.zza(this.zza)) * 31) + this.zzc) * 31) + this.zzd) * 31) + (this.zze ? 1231 : 1237)) * 31;
        if (this.zzf) {
            i = 1231;
        }
        hashCode2 = (hashCode2 + i) * 31;
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
        if (this.zzf) {
            zza += zzflk.zzb(1) + 1;
        }
        zza += zzflk.zzb(2, this.zzc);
        if (this.zza != null && this.zza.length > 0) {
            int i = 0;
            for (int zza2 : this.zza) {
                i += zzflk.zza(zza2);
            }
            zza = (zza + i) + (this.zza.length * 1);
        }
        if (this.zzd != 0) {
            zza += zzflk.zzb(4, this.zzd);
        }
        return this.zze ? zza + (zzflk.zzb(6) + 1) : zza;
    }

    public final /* synthetic */ zzfls zza(zzflj zzflj) throws IOException {
        while (true) {
            int zza = zzflj.zza();
            if (zza == 0) {
                return this;
            }
            if (zza == 8) {
                this.zzf = zzflj.zzd();
            } else if (zza == 16) {
                this.zzc = zzflj.zzh();
            } else if (zza == 24) {
                zza = zzflv.zza(zzflj, 24);
                r1 = this.zza == null ? 0 : this.zza.length;
                Object obj = new int[(zza + r1)];
                if (r1 != 0) {
                    System.arraycopy(this.zza, 0, obj, 0, r1);
                }
                while (r1 < obj.length - 1) {
                    obj[r1] = zzflj.zzh();
                    zzflj.zza();
                    r1++;
                }
                obj[r1] = zzflj.zzh();
                this.zza = obj;
            } else if (zza == 26) {
                zza = zzflj.zzc(zzflj.zzh());
                r1 = zzflj.zzm();
                int i = 0;
                while (zzflj.zzl() > 0) {
                    zzflj.zzh();
                    i++;
                }
                zzflj.zze(r1);
                r1 = this.zza == null ? 0 : this.zza.length;
                Object obj2 = new int[(i + r1)];
                if (r1 != 0) {
                    System.arraycopy(this.zza, 0, obj2, 0, r1);
                }
                while (r1 < obj2.length) {
                    obj2[r1] = zzflj.zzh();
                    r1++;
                }
                this.zza = obj2;
                zzflj.zzd(zza);
            } else if (zza == 32) {
                this.zzd = zzflj.zzh();
            } else if (zza == 48) {
                this.zze = zzflj.zzd();
            } else if (!super.zza(zzflj, zza)) {
                return this;
            }
        }
    }

    public final void zza(zzflk zzflk) throws IOException {
        if (this.zzf) {
            zzflk.zza(1, this.zzf);
        }
        zzflk.zza(2, this.zzc);
        if (this.zza != null && this.zza.length > 0) {
            for (int zza : this.zza) {
                zzflk.zza(3, zza);
            }
        }
        if (this.zzd != 0) {
            zzflk.zza(4, this.zzd);
        }
        if (this.zze) {
            zzflk.zza(6, this.zze);
        }
        super.zza(zzflk);
    }
}
