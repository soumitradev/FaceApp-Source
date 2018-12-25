package com.google.android.gms.internal;

import java.io.IOException;

public final class zzcod extends zzflm<zzcod> {
    public zzcoe[] zza;

    public zzcod() {
        this.zza = zzcoe.zzb();
        this.zzax = null;
        this.zzay = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzcod)) {
            return false;
        }
        zzcod zzcod = (zzcod) obj;
        if (!zzflq.zza(this.zza, zzcod.zza)) {
            return false;
        }
        if (this.zzax != null) {
            if (!this.zzax.zzb()) {
                return this.zzax.equals(zzcod.zzax);
            }
        }
        return zzcod.zzax == null || zzcod.zzax.zzb();
    }

    public final int hashCode() {
        int hashCode;
        int hashCode2 = (((getClass().getName().hashCode() + 527) * 31) + zzflq.zza(this.zza)) * 31;
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
        if (this.zza != null && this.zza.length > 0) {
            for (zzfls zzfls : this.zza) {
                if (zzfls != null) {
                    zza += zzflk.zzb(1, zzfls);
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
            if (zza == 10) {
                zza = zzflv.zza(zzflj, 10);
                int length = this.zza == null ? 0 : this.zza.length;
                Object obj = new zzcoe[(zza + length)];
                if (length != 0) {
                    System.arraycopy(this.zza, 0, obj, 0, length);
                }
                while (length < obj.length - 1) {
                    obj[length] = new zzcoe();
                    zzflj.zza(obj[length]);
                    zzflj.zza();
                    length++;
                }
                obj[length] = new zzcoe();
                zzflj.zza(obj[length]);
                this.zza = obj;
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
        super.zza(zzflk);
    }
}
