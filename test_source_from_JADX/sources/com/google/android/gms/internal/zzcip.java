package com.google.android.gms.internal;

import android.os.Handler;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbq;

@Hide
abstract class zzcip {
    private static volatile Handler zzb;
    private final zzckj zza;
    private final Runnable zzc;
    private volatile long zzd;
    private boolean zze = true;

    zzcip(zzckj zzckj) {
        zzbq.zza(zzckj);
        this.zza = zzckj;
        this.zzc = new zzciq(this, zzckj);
    }

    private final Handler zzd() {
        if (zzb != null) {
            return zzb;
        }
        Handler handler;
        synchronized (zzcip.class) {
            if (zzb == null) {
                zzb = new Handler(this.zza.zzt().getMainLooper());
            }
            handler = zzb;
        }
        return handler;
    }

    public abstract void zza();

    public final void zza(long j) {
        zzc();
        if (j >= 0) {
            this.zzd = this.zza.zzu().zza();
            if (!zzd().postDelayed(this.zzc, j)) {
                this.zza.zzf().zzy().zza("Failed to schedule delayed post. time", Long.valueOf(j));
            }
        }
    }

    public final boolean zzb() {
        return this.zzd != 0;
    }

    public final void zzc() {
        this.zzd = 0;
        zzd().removeCallbacks(this.zzc);
    }
}
