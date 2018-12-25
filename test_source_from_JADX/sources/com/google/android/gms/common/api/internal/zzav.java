package com.google.android.gms.common.api.internal;

import android.support.annotation.BinderThread;
import com.google.android.gms.internal.zzcyo;
import com.google.android.gms.internal.zzcyw;
import java.lang.ref.WeakReference;

final class zzav extends zzcyo {
    private final WeakReference<zzao> zza;

    zzav(zzao zzao) {
        this.zza = new WeakReference(zzao);
    }

    @BinderThread
    public final void zza(zzcyw zzcyw) {
        zzao zzao = (zzao) this.zza.get();
        if (zzao != null) {
            zzao.zza.zza(new zzaw(this, zzao, zzao, zzcyw));
        }
    }
}
