package com.google.android.gms.internal;

import java.io.IOException;

public final class zzbm extends zzflm<zzbm> {
    private static volatile zzbm[] zzf;
    public String zza;
    public long zzb;
    public long zzc;
    public boolean zzd;
    public long zze;

    public zzbm() {
        this.zza = "";
        this.zzb = 0;
        this.zzc = 2147483647L;
        this.zzd = false;
        this.zze = 0;
        this.zzax = null;
        this.zzay = -1;
    }

    public static zzbm[] zzb() {
        if (zzf == null) {
            synchronized (zzflq.zzb) {
                if (zzf == null) {
                    zzf = new zzbm[0];
                }
            }
        }
        return zzf;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzbm)) {
            return false;
        }
        zzbm zzbm = (zzbm) obj;
        if (this.zza == null) {
            if (zzbm.zza != null) {
                return false;
            }
        } else if (!this.zza.equals(zzbm.zza)) {
            return false;
        }
        if (this.zzb != zzbm.zzb || this.zzc != zzbm.zzc || this.zzd != zzbm.zzd || this.zze != zzbm.zze) {
            return false;
        }
        if (this.zzax != null) {
            if (!this.zzax.zzb()) {
                return this.zzax.equals(zzbm.zzax);
            }
        }
        return zzbm.zzax == null || zzbm.zzax.zzb();
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = (((((((((((getClass().getName().hashCode() + 527) * 31) + (this.zza == null ? 0 : this.zza.hashCode())) * 31) + ((int) (this.zzb ^ (this.zzb >>> 32)))) * 31) + ((int) (this.zzc ^ (this.zzc >>> 32)))) * 31) + (this.zzd ? 1231 : 1237)) * 31) + ((int) (this.zze ^ (this.zze >>> 32)))) * 31;
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
        if (this.zzb != 0) {
            zza += zzflk.zze(2, this.zzb);
        }
        if (this.zzc != 2147483647L) {
            zza += zzflk.zze(3, this.zzc);
        }
        if (this.zzd) {
            zza += zzflk.zzb(4) + 1;
        }
        return this.zze != 0 ? zza + zzflk.zze(5, this.zze) : zza;
    }

    public final /* synthetic */ zzfls zza(zzflj zzflj) throws IOException {
        while (true) {
            int zza = zzflj.zza();
            if (zza == 0) {
                return this;
            }
            if (zza == 10) {
                this.zza = zzflj.zze();
            } else if (zza == 16) {
                this.zzb = zzflj.zzi();
            } else if (zza == 24) {
                this.zzc = zzflj.zzi();
            } else if (zza == 32) {
                this.zzd = zzflj.zzd();
            } else if (zza == 40) {
                this.zze = zzflj.zzi();
            } else if (!super.zza(zzflj, zza)) {
                return this;
            }
        }
    }

    public final void zza(zzflk zzflk) throws IOException {
        if (!(this.zza == null || this.zza.equals(""))) {
            zzflk.zza(1, this.zza);
        }
        if (this.zzb != 0) {
            zzflk.zzb(2, this.zzb);
        }
        if (this.zzc != 2147483647L) {
            zzflk.zzb(3, this.zzc);
        }
        if (this.zzd) {
            zzflk.zza(4, this.zzd);
        }
        if (this.zze != 0) {
            zzflk.zzb(5, this.zze);
        }
        super.zza(zzflk);
    }
}
