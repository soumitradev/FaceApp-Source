package com.google.android.gms.internal;

import java.io.IOException;

public final class zzfms extends zzflm<zzfms> implements Cloneable {
    private static volatile zzfms[] zza;
    private String zzb;
    private String zzc;

    public zzfms() {
        this.zzb = "";
        this.zzc = "";
        this.zzax = null;
        this.zzay = -1;
    }

    public static zzfms[] zzb() {
        if (zza == null) {
            synchronized (zzflq.zzb) {
                if (zza == null) {
                    zza = new zzfms[0];
                }
            }
        }
        return zza;
    }

    private zzfms zzg() {
        try {
            return (zzfms) super.zzc();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public final /* synthetic */ Object clone() throws CloneNotSupportedException {
        return zzg();
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzfms)) {
            return false;
        }
        zzfms zzfms = (zzfms) obj;
        if (this.zzb == null) {
            if (zzfms.zzb != null) {
                return false;
            }
        } else if (!this.zzb.equals(zzfms.zzb)) {
            return false;
        }
        if (this.zzc == null) {
            if (zzfms.zzc != null) {
                return false;
            }
        } else if (!this.zzc.equals(zzfms.zzc)) {
            return false;
        }
        if (this.zzax != null) {
            if (!this.zzax.zzb()) {
                return this.zzax.equals(zzfms.zzax);
            }
        }
        return zzfms.zzax == null || zzfms.zzax.zzb();
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = (((((getClass().getName().hashCode() + 527) * 31) + (this.zzb == null ? 0 : this.zzb.hashCode())) * 31) + (this.zzc == null ? 0 : this.zzc.hashCode())) * 31;
        if (this.zzax != null) {
            if (!this.zzax.zzb()) {
                i = this.zzax.hashCode();
            }
        }
        return hashCode + i;
    }

    protected final int zza() {
        int zza = super.zza();
        if (!(this.zzb == null || this.zzb.equals(""))) {
            zza += zzflk.zzb(1, this.zzb);
        }
        return (this.zzc == null || this.zzc.equals("")) ? zza : zza + zzflk.zzb(2, this.zzc);
    }

    public final /* synthetic */ zzfls zza(zzflj zzflj) throws IOException {
        while (true) {
            int zza = zzflj.zza();
            if (zza == 0) {
                return this;
            }
            if (zza == 10) {
                this.zzb = zzflj.zze();
            } else if (zza == 18) {
                this.zzc = zzflj.zze();
            } else if (!super.zza(zzflj, zza)) {
                return this;
            }
        }
    }

    public final void zza(zzflk zzflk) throws IOException {
        if (!(this.zzb == null || this.zzb.equals(""))) {
            zzflk.zza(1, this.zzb);
        }
        if (!(this.zzc == null || this.zzc.equals(""))) {
            zzflk.zza(2, this.zzc);
        }
        super.zza(zzflk);
    }

    public final /* synthetic */ zzflm zzc() throws CloneNotSupportedException {
        return (zzfms) clone();
    }

    public final /* synthetic */ zzfls zzd() throws CloneNotSupportedException {
        return (zzfms) clone();
    }
}
