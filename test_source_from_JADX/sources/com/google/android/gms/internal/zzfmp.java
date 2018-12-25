package com.google.android.gms.internal;

import java.io.IOException;

public final class zzfmp extends zzflm<zzfmp> implements Cloneable {
    private int zza;
    private String zzb;
    private String zzc;

    public zzfmp() {
        this.zza = 0;
        this.zzb = "";
        this.zzc = "";
        this.zzax = null;
        this.zzay = -1;
    }

    private zzfmp zzb() {
        try {
            return (zzfmp) super.zzc();
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
        if (!(obj instanceof zzfmp)) {
            return false;
        }
        zzfmp zzfmp = (zzfmp) obj;
        if (this.zza != zzfmp.zza) {
            return false;
        }
        if (this.zzb == null) {
            if (zzfmp.zzb != null) {
                return false;
            }
        } else if (!this.zzb.equals(zzfmp.zzb)) {
            return false;
        }
        if (this.zzc == null) {
            if (zzfmp.zzc != null) {
                return false;
            }
        } else if (!this.zzc.equals(zzfmp.zzc)) {
            return false;
        }
        if (this.zzax != null) {
            if (!this.zzax.zzb()) {
                return this.zzax.equals(zzfmp.zzax);
            }
        }
        return zzfmp.zzax == null || zzfmp.zzax.zzb();
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = (((((((getClass().getName().hashCode() + 527) * 31) + this.zza) * 31) + (this.zzb == null ? 0 : this.zzb.hashCode())) * 31) + (this.zzc == null ? 0 : this.zzc.hashCode())) * 31;
        if (this.zzax != null) {
            if (!this.zzax.zzb()) {
                i = this.zzax.hashCode();
            }
        }
        return hashCode + i;
    }

    protected final int zza() {
        int zza = super.zza();
        if (this.zza != 0) {
            zza += zzflk.zzb(1, this.zza);
        }
        if (!(this.zzb == null || this.zzb.equals(""))) {
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
            if (zza == 8) {
                this.zza = zzflj.zzc();
            } else if (zza == 18) {
                this.zzb = zzflj.zze();
            } else if (zza == 26) {
                this.zzc = zzflj.zze();
            } else if (!super.zza(zzflj, zza)) {
                return this;
            }
        }
    }

    public final void zza(zzflk zzflk) throws IOException {
        if (this.zza != 0) {
            zzflk.zza(1, this.zza);
        }
        if (!(this.zzb == null || this.zzb.equals(""))) {
            zzflk.zza(2, this.zzb);
        }
        if (!(this.zzc == null || this.zzc.equals(""))) {
            zzflk.zza(3, this.zzc);
        }
        super.zza(zzflk);
    }

    public final /* synthetic */ zzflm zzc() throws CloneNotSupportedException {
        return (zzfmp) clone();
    }

    public final /* synthetic */ zzfls zzd() throws CloneNotSupportedException {
        return (zzfmp) clone();
    }
}
