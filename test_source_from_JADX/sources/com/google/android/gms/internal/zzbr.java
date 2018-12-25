package com.google.android.gms.internal;

import java.io.IOException;

public final class zzbr extends zzflm<zzbr> {
    private static volatile zzbr[] zzc;
    public String zza;
    public zzbn zzb;
    private zzbt zzd;

    public zzbr() {
        this.zza = "";
        this.zzd = null;
        this.zzb = null;
        this.zzax = null;
        this.zzay = -1;
    }

    public static zzbr[] zzb() {
        if (zzc == null) {
            synchronized (zzflq.zzb) {
                if (zzc == null) {
                    zzc = new zzbr[0];
                }
            }
        }
        return zzc;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzbr)) {
            return false;
        }
        zzbr zzbr = (zzbr) obj;
        if (this.zza == null) {
            if (zzbr.zza != null) {
                return false;
            }
        } else if (!this.zza.equals(zzbr.zza)) {
            return false;
        }
        if (this.zzd == null) {
            if (zzbr.zzd != null) {
                return false;
            }
        } else if (!this.zzd.equals(zzbr.zzd)) {
            return false;
        }
        if (this.zzb == null) {
            if (zzbr.zzb != null) {
                return false;
            }
        } else if (!this.zzb.equals(zzbr.zzb)) {
            return false;
        }
        if (this.zzax != null) {
            if (!this.zzax.zzb()) {
                return this.zzax.equals(zzbr.zzax);
            }
        }
        return zzbr.zzax == null || zzbr.zzax.zzb();
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = ((getClass().getName().hashCode() + 527) * 31) + (this.zza == null ? 0 : this.zza.hashCode());
        zzbt zzbt = this.zzd;
        hashCode = (hashCode * 31) + (zzbt == null ? 0 : zzbt.hashCode());
        zzbn zzbn = this.zzb;
        hashCode = ((hashCode * 31) + (zzbn == null ? 0 : zzbn.hashCode())) * 31;
        if (this.zzax != null) {
            if (!this.zzax.zzb()) {
                i = this.zzax.hashCode();
            }
        }
        return hashCode + i;
    }

    protected final int zza() {
        int zza = super.zza();
        if (!(this.zza == null || this.zza.equals(""))) {
            zza += zzflk.zzb(1, this.zza);
        }
        if (this.zzd != null) {
            zza += zzflk.zzb(2, this.zzd);
        }
        return this.zzb != null ? zza + zzflk.zzb(3, this.zzb) : zza;
    }

    public final /* synthetic */ zzfls zza(zzflj zzflj) throws IOException {
        while (true) {
            int zza = zzflj.zza();
            if (zza == 0) {
                return this;
            }
            if (zza != 10) {
                zzfls zzfls;
                if (zza == 18) {
                    if (this.zzd == null) {
                        this.zzd = new zzbt();
                    }
                    zzfls = this.zzd;
                } else if (zza == 26) {
                    if (this.zzb == null) {
                        this.zzb = new zzbn();
                    }
                    zzfls = this.zzb;
                } else if (!super.zza(zzflj, zza)) {
                    return this;
                }
                zzflj.zza(zzfls);
            } else {
                this.zza = zzflj.zze();
            }
        }
    }

    public final void zza(zzflk zzflk) throws IOException {
        if (!(this.zza == null || this.zza.equals(""))) {
            zzflk.zza(1, this.zza);
        }
        if (this.zzd != null) {
            zzflk.zza(2, this.zzd);
        }
        if (this.zzb != null) {
            zzflk.zza(3, this.zzb);
        }
        super.zza(zzflk);
    }
}
