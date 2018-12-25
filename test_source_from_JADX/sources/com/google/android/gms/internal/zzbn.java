package com.google.android.gms.internal;

import java.io.IOException;

public final class zzbn extends zzflm<zzbn> {
    public zzbt[] zza;
    public zzbt[] zzb;
    public zzbm[] zzc;

    public zzbn() {
        this.zza = zzbt.zzb();
        this.zzb = zzbt.zzb();
        this.zzc = zzbm.zzb();
        this.zzax = null;
        this.zzay = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzbn)) {
            return false;
        }
        zzbn zzbn = (zzbn) obj;
        if (!zzflq.zza(this.zza, zzbn.zza) || !zzflq.zza(this.zzb, zzbn.zzb) || !zzflq.zza(this.zzc, zzbn.zzc)) {
            return false;
        }
        if (this.zzax != null) {
            if (!this.zzax.zzb()) {
                return this.zzax.equals(zzbn.zzax);
            }
        }
        return zzbn.zzax == null || zzbn.zzax.zzb();
    }

    public final int hashCode() {
        int hashCode;
        int hashCode2 = (((((((getClass().getName().hashCode() + 527) * 31) + zzflq.zza(this.zza)) * 31) + zzflq.zza(this.zzb)) * 31) + zzflq.zza(this.zzc)) * 31;
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
            i = zza;
            for (zzfls zzfls : this.zza) {
                if (zzfls != null) {
                    i += zzflk.zzb(1, zzfls);
                }
            }
            zza = i;
        }
        if (this.zzb != null && this.zzb.length > 0) {
            i = zza;
            for (zzfls zzfls2 : this.zzb) {
                if (zzfls2 != null) {
                    i += zzflk.zzb(2, zzfls2);
                }
            }
            zza = i;
        }
        if (this.zzc != null && this.zzc.length > 0) {
            for (zzfls zzfls3 : this.zzc) {
                if (zzfls3 != null) {
                    zza += zzflk.zzb(3, zzfls3);
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
            int length;
            Object obj;
            if (zza == 10) {
                zza = zzflv.zza(zzflj, 10);
                length = this.zza == null ? 0 : this.zza.length;
                obj = new zzbt[(zza + length)];
                if (length != 0) {
                    System.arraycopy(this.zza, 0, obj, 0, length);
                }
                while (length < obj.length - 1) {
                    obj[length] = new zzbt();
                    zzflj.zza(obj[length]);
                    zzflj.zza();
                    length++;
                }
                obj[length] = new zzbt();
                zzflj.zza(obj[length]);
                this.zza = obj;
            } else if (zza == 18) {
                zza = zzflv.zza(zzflj, 18);
                length = this.zzb == null ? 0 : this.zzb.length;
                obj = new zzbt[(zza + length)];
                if (length != 0) {
                    System.arraycopy(this.zzb, 0, obj, 0, length);
                }
                while (length < obj.length - 1) {
                    obj[length] = new zzbt();
                    zzflj.zza(obj[length]);
                    zzflj.zza();
                    length++;
                }
                obj[length] = new zzbt();
                zzflj.zza(obj[length]);
                this.zzb = obj;
            } else if (zza == 26) {
                zza = zzflv.zza(zzflj, 26);
                length = this.zzc == null ? 0 : this.zzc.length;
                obj = new zzbm[(zza + length)];
                if (length != 0) {
                    System.arraycopy(this.zzc, 0, obj, 0, length);
                }
                while (length < obj.length - 1) {
                    obj[length] = new zzbm();
                    zzflj.zza(obj[length]);
                    zzflj.zza();
                    length++;
                }
                obj[length] = new zzbm();
                zzflj.zza(obj[length]);
                this.zzc = obj;
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
        if (this.zzb != null && this.zzb.length > 0) {
            for (zzfls zzfls2 : this.zzb) {
                if (zzfls2 != null) {
                    zzflk.zza(2, zzfls2);
                }
            }
        }
        if (this.zzc != null && this.zzc.length > 0) {
            for (zzfls zzfls3 : this.zzc) {
                if (zzfls3 != null) {
                    zzflk.zza(3, zzfls3);
                }
            }
        }
        super.zza(zzflk);
    }
}
