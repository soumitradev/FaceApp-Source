package com.google.android.gms.common.api.internal;

import android.os.Looper;
import android.support.annotation.NonNull;
import com.google.android.gms.common.internal.zzbq;

public final class zzci<L> {
    private final zzcj zza;
    private volatile L zzb;
    private final zzck<L> zzc;

    zzci(@NonNull Looper looper, @NonNull L l, @NonNull String str) {
        this.zza = new zzcj(this, looper);
        this.zzb = zzbq.zza(l, "Listener must not be null");
        this.zzc = new zzck(l, zzbq.zza(str));
    }

    public final void zza(zzcl<? super L> zzcl) {
        zzbq.zza(zzcl, "Notifier must not be null");
        this.zza.sendMessage(this.zza.obtainMessage(1, zzcl));
    }

    public final boolean zza() {
        return this.zzb != null;
    }

    public final void zzb() {
        this.zzb = null;
    }

    final void zzb(zzcl<? super L> zzcl) {
        Object obj = this.zzb;
        if (obj == null) {
            zzcl.zza();
            return;
        }
        try {
            zzcl.zza(obj);
        } catch (RuntimeException e) {
            zzcl.zza();
            throw e;
        }
    }

    @NonNull
    public final zzck<L> zzc() {
        return this.zzc;
    }
}
