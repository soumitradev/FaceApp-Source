package com.google.android.gms.internal;

import com.google.android.gms.internal.zzfhu.zzg;

final class zzfhi implements zzfli {
    private final zzfhg zza;

    private zzfhi(zzfhg zzfhg) {
        this.zza = (zzfhg) zzfhz.zza(zzfhg, "output");
        this.zza.zza = this;
    }

    public static zzfhi zza(zzfhg zzfhg) {
        return zzfhg.zza != null ? zzfhg.zza : new zzfhi(zzfhg);
    }

    public final int zza() {
        return zzg.zzl;
    }

    public final void zza(int i, Object obj) {
        try {
            if (obj instanceof zzfgs) {
                this.zza.zzb(i, (zzfgs) obj);
            } else {
                this.zza.zzb(i, (zzfjc) obj);
            }
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
