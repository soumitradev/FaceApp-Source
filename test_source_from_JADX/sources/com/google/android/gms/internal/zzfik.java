package com.google.android.gms.internal;

public class zzfik {
    private static final zzfhm zza = zzfhm.zza();
    private zzfgs zzb;
    private volatile zzfjc zzc;
    private volatile zzfgs zzd;

    private zzfjc zzb(zzfjc zzfjc) {
        if (this.zzc == null) {
            synchronized (this) {
                if (this.zzc == null) {
                    try {
                        this.zzc = zzfjc;
                        this.zzd = zzfgs.zza;
                    } catch (zzfie e) {
                        this.zzc = zzfjc;
                        this.zzd = zzfgs.zza;
                    }
                }
            }
        }
        return this.zzc;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzfik)) {
            return false;
        }
        zzfik zzfik = (zzfik) obj;
        zzfjc zzfjc = this.zzc;
        zzfjc zzfjc2 = zzfik.zzc;
        return (zzfjc == null && zzfjc2 == null) ? zzc().equals(zzfik.zzc()) : (zzfjc == null || zzfjc2 == null) ? zzfjc != null ? zzfjc.equals(zzfik.zzb(zzfjc.zzw())) : zzb(zzfjc2.zzw()).equals(zzfjc2) : zzfjc.equals(zzfjc2);
    }

    public int hashCode() {
        return 1;
    }

    public final zzfjc zza(zzfjc zzfjc) {
        zzfjc zzfjc2 = this.zzc;
        this.zzb = null;
        this.zzd = null;
        this.zzc = zzfjc;
        return zzfjc2;
    }

    public final int zzb() {
        return this.zzd != null ? this.zzd.zza() : this.zzc != null ? this.zzc.zza() : 0;
    }

    public final zzfgs zzc() {
        if (this.zzd != null) {
            return this.zzd;
        }
        synchronized (this) {
            if (this.zzd != null) {
                zzfgs zzfgs = this.zzd;
                return zzfgs;
            }
            this.zzd = this.zzc == null ? zzfgs.zza : this.zzc.zzp();
            zzfgs = this.zzd;
            return zzfgs;
        }
    }
}
