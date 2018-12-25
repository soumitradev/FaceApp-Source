package com.google.android.gms.common.api.internal;

import android.os.IBinder;
import android.os.IBinder.DeathRecipient;
import com.google.android.gms.common.api.zze;
import java.lang.ref.WeakReference;
import java.util.NoSuchElementException;

final class zzdm implements DeathRecipient, zzdn {
    private final WeakReference<BasePendingResult<?>> zza;
    private final WeakReference<zze> zzb;
    private final WeakReference<IBinder> zzc;

    private zzdm(BasePendingResult<?> basePendingResult, zze zze, IBinder iBinder) {
        this.zzb = new WeakReference(zze);
        this.zza = new WeakReference(basePendingResult);
        this.zzc = new WeakReference(iBinder);
    }

    private final void zza() {
        BasePendingResult basePendingResult = (BasePendingResult) this.zza.get();
        zze zze = (zze) this.zzb.get();
        if (!(zze == null || basePendingResult == null)) {
            zze.zza(basePendingResult.zzb().intValue());
        }
        IBinder iBinder = (IBinder) this.zzc.get();
        if (iBinder != null) {
            try {
                iBinder.unlinkToDeath(this, 0);
            } catch (NoSuchElementException e) {
            }
        }
    }

    public final void binderDied() {
        zza();
    }

    public final void zza(BasePendingResult<?> basePendingResult) {
        zza();
    }
}
