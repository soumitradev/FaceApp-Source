package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.ConnectionResult;

final class zzcw implements Runnable {
    private /* synthetic */ zzcv zza;

    zzcw(zzcv zzcv) {
        this.zza = zzcv;
    }

    public final void run() {
        this.zza.zzh.zzb(new ConnectionResult(4));
    }
}
