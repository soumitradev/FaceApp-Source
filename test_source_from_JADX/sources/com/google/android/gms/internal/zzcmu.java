package com.google.android.gms.internal;

import android.content.ComponentName;

final class zzcmu implements Runnable {
    private /* synthetic */ ComponentName zza;
    private /* synthetic */ zzcms zzb;

    zzcmu(zzcms zzcms, ComponentName componentName) {
        this.zzb = zzcms;
        this.zza = componentName;
    }

    public final void run() {
        this.zzb.zza.zza(this.zza);
    }
}
