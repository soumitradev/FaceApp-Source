package com.google.android.gms.common.api.internal;

import com.google.android.gms.internal.zzcyw;

final class zzcx implements Runnable {
    private /* synthetic */ zzcyw zza;
    private /* synthetic */ zzcv zzb;

    zzcx(zzcv zzcv, zzcyw zzcyw) {
        this.zzb = zzcv;
        this.zza = zzcyw;
    }

    public final void run() {
        this.zzb.zzb(this.zza);
    }
}
