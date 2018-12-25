package com.google.android.gms.internal;

import java.lang.Thread.UncaughtExceptionHandler;

final class zzarl implements UncaughtExceptionHandler {
    private /* synthetic */ zzark zza;

    zzarl(zzark zzark) {
        this.zza = zzark;
    }

    public final void uncaughtException(Thread thread, Throwable th) {
        zzarh zzf = this.zza.zzf();
        if (zzf != null) {
            zzf.zze("Job execution failed", th);
        }
    }
}
