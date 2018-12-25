package com.google.android.gms.internal;

import java.io.IOException;

public final class zzbo extends zzflm<zzbo> {
    private static volatile zzbo[] zzc;
    public int zza;
    public int zzb;

    public zzbo() {
        this.zza = 0;
        this.zzb = 0;
        this.zzax = null;
        this.zzay = -1;
    }

    public static zzbo[] zzb() {
        if (zzc == null) {
            synchronized (zzflq.zzb) {
                if (zzc == null) {
                    zzc = new zzbo[0];
                }
            }
        }
        return zzc;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzbo)) {
            return false;
        }
        zzbo zzbo = (zzbo) obj;
        if (this.zza != zzbo.zza || this.zzb != zzbo.zzb) {
            return false;
        }
        if (this.zzax != null) {
            if (!this.zzax.zzb()) {
                return this.zzax.equals(zzbo.zzax);
            }
        }
        return zzbo.zzax == null || zzbo.zzax.zzb();
    }

    public final int hashCode() {
        int hashCode;
        int hashCode2 = (((((getClass().getName().hashCode() + 527) * 31) + this.zza) * 31) + this.zzb) * 31;
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
        return (super.zza() + zzflk.zzb(1, this.zza)) + zzflk.zzb(2, this.zzb);
    }

    public final /* synthetic */ zzfls zza(zzflj zzflj) throws IOException {
        while (true) {
            int zza = zzflj.zza();
            if (zza == 0) {
                return this;
            }
            if (zza == 8) {
                this.zza = zzflj.zzh();
            } else if (zza == 16) {
                this.zzb = zzflj.zzh();
            } else if (!super.zza(zzflj, zza)) {
                return this;
            }
        }
    }

    public final void zza(zzflk zzflk) throws IOException {
        zzflk.zza(1, this.zza);
        zzflk.zza(2, this.zzb);
        super.zza(zzflk);
    }
}
