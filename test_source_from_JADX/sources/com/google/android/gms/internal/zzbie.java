package com.google.android.gms.internal;

import android.os.Process;

final class zzbie implements Runnable {
    private final Runnable zza;
    private final int zzb;

    public zzbie(Runnable runnable, int i) {
        this.zza = runnable;
        this.zzb = i;
    }

    public final void run() {
        Process.setThreadPriority(this.zzb);
        this.zza.run();
    }
}
