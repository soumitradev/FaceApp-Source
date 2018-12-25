package com.google.android.gms.internal;

import java.io.IOException;

public final class zzbs extends zzflm<zzbs> {
    public zzbr[] zza;
    public zzbp zzb;
    public String zzc;

    public zzbs() {
        this.zza = zzbr.zzb();
        this.zzb = null;
        this.zzc = "";
        this.zzax = null;
        this.zzay = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzbs)) {
            return false;
        }
        zzbs zzbs = (zzbs) obj;
        if (!zzflq.zza(this.zza, zzbs.zza)) {
            return false;
        }
        if (this.zzb == null) {
            if (zzbs.zzb != null) {
                return false;
            }
        } else if (!this.zzb.equals(zzbs.zzb)) {
            return false;
        }
        if (this.zzc == null) {
            if (zzbs.zzc != null) {
                return false;
            }
        } else if (!this.zzc.equals(zzbs.zzc)) {
            return false;
        }
        if (this.zzax != null) {
            if (!this.zzax.zzb()) {
                return this.zzax.equals(zzbs.zzax);
            }
        }
        return zzbs.zzax == null || zzbs.zzax.zzb();
    }

    public final int hashCode() {
        int hashCode = ((getClass().getName().hashCode() + 527) * 31) + zzflq.zza(this.zza);
        zzbp zzbp = this.zzb;
        int i = 0;
        hashCode = ((((hashCode * 31) + (zzbp == null ? 0 : zzbp.hashCode())) * 31) + (this.zzc == null ? 0 : this.zzc.hashCode())) * 31;
        if (this.zzax != null) {
            if (!this.zzax.zzb()) {
                i = this.zzax.hashCode();
            }
        }
        return hashCode + i;
    }

    protected final int zza() {
        int zza = super.zza();
        if (this.zza != null && this.zza.length > 0) {
            for (zzfls zzfls : this.zza) {
                if (zzfls != null) {
                    zza += zzflk.zzb(1, zzfls);
                }
            }
        }
        if (this.zzb != null) {
            zza += zzflk.zzb(2, this.zzb);
        }
        return (this.zzc == null || this.zzc.equals("")) ? zza : zza + zzflk.zzb(3, this.zzc);
    }

    public final /* synthetic */ zzfls zza(zzflj zzflj) throws IOException {
        while (true) {
            int zza = zzflj.zza();
            if (zza == 0) {
                return this;
            }
            if (zza == 10) {
                zza = zzflv.zza(zzflj, 10);
                int length = this.zza == null ? 0 : this.zza.length;
                Object obj = new zzbr[(zza + length)];
                if (length != 0) {
                    System.arraycopy(this.zza, 0, obj, 0, length);
                }
                while (length < obj.length - 1) {
                    obj[length] = new zzbr();
                    zzflj.zza(obj[length]);
                    zzflj.zza();
                    length++;
                }
                obj[length] = new zzbr();
                zzflj.zza(obj[length]);
                this.zza = obj;
            } else if (zza == 18) {
                if (this.zzb == null) {
                    this.zzb = new zzbp();
                }
                zzflj.zza(this.zzb);
            } else if (zza == 26) {
                this.zzc = zzflj.zze();
            } else if (!super.zza(zzflj, zza)) {
                return this;
            }
        }
    }

    public final void zza(zzflk zzflk) throws IOException {
        if (this.zza != null && this.zza.length > 0) {
            for (zzfls zzfls : this.zza) {
                if (zzfls != null) {
                    zzflk.zza(1, zzfls);
                }
            }
        }
        if (this.zzb != null) {
            zzflk.zza(2, this.zzb);
        }
        if (!(this.zzc == null || this.zzc.equals(""))) {
            zzflk.zza(3, this.zzc);
        }
        super.zza(zzflk);
    }
}
