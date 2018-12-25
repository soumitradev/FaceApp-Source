package com.google.android.gms.common.api.internal;

import java.lang.ref.WeakReference;

final class zzbg extends zzby {
    private WeakReference<zzba> zza;

    zzbg(zzba zzba) {
        this.zza = new WeakReference(zzba);
    }

    public final void zza() {
        zzba zzba = (zzba) this.zza.get();
        if (zzba != null) {
            zzba.zzj();
        }
    }
}
