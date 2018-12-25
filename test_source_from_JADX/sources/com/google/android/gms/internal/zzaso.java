package com.google.android.gms.internal;

import android.os.Looper;

final class zzaso implements Runnable {
    private /* synthetic */ zzasn zza;

    zzaso(zzasn zzasn) {
        this.zza = zzasn;
    }

    public final void run() {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            this.zza.zza.zzg().zza((Runnable) this);
            return;
        }
        boolean zzc = this.zza.zzc();
        this.zza.zzd = 0;
        if (zzc) {
            this.zza.zza();
        }
    }
}
