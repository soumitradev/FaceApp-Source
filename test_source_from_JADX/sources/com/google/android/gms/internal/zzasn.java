package com.google.android.gms.internal;

import android.os.Handler;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbq;

@Hide
abstract class zzasn {
    private static volatile Handler zzb;
    private final zzark zza;
    private final Runnable zzc = new zzaso(this);
    private volatile long zzd;

    zzasn(zzark zzark) {
        zzbq.zza(zzark);
        this.zza = zzark;
    }

    private final Handler zze() {
        if (zzb != null) {
            return zzb;
        }
        Handler handler;
        synchronized (zzasn.class) {
            if (zzb == null) {
                zzb = new Handler(this.zza.zza().getMainLooper());
            }
            handler = zzb;
        }
        return handler;
    }

    public abstract void zza();

    public final void zza(long j) {
        zzd();
        if (j >= 0) {
            this.zzd = this.zza.zzc().zza();
            if (!zze().postDelayed(this.zzc, j)) {
                this.zza.zze().zze("Failed to schedule delayed post. time", Long.valueOf(j));
            }
        }
    }

    public final long zzb() {
        return this.zzd == 0 ? 0 : Math.abs(this.zza.zzc().zza() - this.zzd);
    }

    public final void zzb(long j) {
        if (zzc()) {
            long j2 = 0;
            if (j < 0) {
                zzd();
                return;
            }
            long abs = j - Math.abs(this.zza.zzc().zza() - this.zzd);
            if (abs >= 0) {
                j2 = abs;
            }
            zze().removeCallbacks(this.zzc);
            if (!zze().postDelayed(this.zzc, j2)) {
                this.zza.zze().zze("Failed to adjust delayed post. time", Long.valueOf(j2));
            }
        }
    }

    public final boolean zzc() {
        return this.zzd != 0;
    }

    public final void zzd() {
        this.zzd = 0;
        zze().removeCallbacks(this.zzc);
    }
}
