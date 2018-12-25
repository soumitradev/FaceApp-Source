package com.google.android.gms.internal;

import java.io.IOException;
import java.util.Arrays;

public final class zzfmq extends zzflm<zzfmq> implements Cloneable {
    private byte[] zza;
    private String zzb;
    private byte[][] zzc;
    private boolean zzd;

    public zzfmq() {
        this.zza = zzflv.zzh;
        this.zzb = "";
        this.zzc = zzflv.zzg;
        this.zzd = false;
        this.zzax = null;
        this.zzay = -1;
    }

    private zzfmq zzb() {
        try {
            zzfmq zzfmq = (zzfmq) super.zzc();
            if (this.zzc != null && this.zzc.length > 0) {
                zzfmq.zzc = (byte[][]) this.zzc.clone();
            }
            return zzfmq;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public final /* synthetic */ Object clone() throws CloneNotSupportedException {
        return zzb();
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzfmq)) {
            return false;
        }
        zzfmq zzfmq = (zzfmq) obj;
        if (!Arrays.equals(this.zza, zzfmq.zza)) {
            return false;
        }
        if (this.zzb == null) {
            if (zzfmq.zzb != null) {
                return false;
            }
        } else if (!this.zzb.equals(zzfmq.zzb)) {
            return false;
        }
        if (!zzflq.zza(this.zzc, zzfmq.zzc) || this.zzd != zzfmq.zzd) {
            return false;
        }
        if (this.zzax != null) {
            if (!this.zzax.zzb()) {
                return this.zzax.equals(zzfmq.zzax);
            }
        }
        return zzfmq.zzax == null || zzfmq.zzax.zzb();
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = (((((((((getClass().getName().hashCode() + 527) * 31) + Arrays.hashCode(this.zza)) * 31) + (this.zzb == null ? 0 : this.zzb.hashCode())) * 31) + zzflq.zza(this.zzc)) * 31) + (this.zzd ? 1231 : 1237)) * 31;
        if (this.zzax != null) {
            if (!this.zzax.zzb()) {
                i = this.zzax.hashCode();
            }
        }
        return hashCode + i;
    }

    protected final int zza() {
        int zza = super.zza();
        if (!Arrays.equals(this.zza, zzflv.zzh)) {
            zza += zzflk.zzb(1, this.zza);
        }
        if (this.zzc != null && this.zzc.length > 0) {
            int i = 0;
            int i2 = 0;
            for (byte[] bArr : this.zzc) {
                if (bArr != null) {
                    i2++;
                    i += zzflk.zzb(bArr);
                }
            }
            zza = (zza + i) + (i2 * 1);
        }
        if (this.zzd) {
            zza += zzflk.zzb(3) + 1;
        }
        return (this.zzb == null || this.zzb.equals("")) ? zza : zza + zzflk.zzb(4, this.zzb);
    }

    public final /* synthetic */ zzfls zza(zzflj zzflj) throws IOException {
        while (true) {
            int zza = zzflj.zza();
            if (zza == 0) {
                return this;
            }
            if (zza == 10) {
                this.zza = zzflj.zzf();
            } else if (zza == 18) {
                zza = zzflv.zza(zzflj, 18);
                int length = this.zzc == null ? 0 : this.zzc.length;
                Object obj = new byte[(zza + length)][];
                if (length != 0) {
                    System.arraycopy(this.zzc, 0, obj, 0, length);
                }
                while (length < obj.length - 1) {
                    obj[length] = zzflj.zzf();
                    zzflj.zza();
                    length++;
                }
                obj[length] = zzflj.zzf();
                this.zzc = obj;
            } else if (zza == 24) {
                this.zzd = zzflj.zzd();
            } else if (zza == 34) {
                this.zzb = zzflj.zze();
            } else if (!super.zza(zzflj, zza)) {
                return this;
            }
        }
    }

    public final void zza(zzflk zzflk) throws IOException {
        if (!Arrays.equals(this.zza, zzflv.zzh)) {
            zzflk.zza(1, this.zza);
        }
        if (this.zzc != null && this.zzc.length > 0) {
            for (byte[] bArr : this.zzc) {
                if (bArr != null) {
                    zzflk.zza(2, bArr);
                }
            }
        }
        if (this.zzd) {
            zzflk.zza(3, this.zzd);
        }
        if (!(this.zzb == null || this.zzb.equals(""))) {
            zzflk.zza(4, this.zzb);
        }
        super.zza(zzflk);
    }

    public final /* synthetic */ zzflm zzc() throws CloneNotSupportedException {
        return (zzfmq) clone();
    }

    public final /* synthetic */ zzfls zzd() throws CloneNotSupportedException {
        return (zzfmq) clone();
    }
}
