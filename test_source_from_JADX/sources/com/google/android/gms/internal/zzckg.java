package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzbq;
import java.lang.Thread.UncaughtExceptionHandler;

final class zzckg implements UncaughtExceptionHandler {
    private final String zza;
    private /* synthetic */ zzcke zzb;

    public zzckg(zzcke zzcke, String str) {
        this.zzb = zzcke;
        zzbq.zza(str);
        this.zza = str;
    }

    public final synchronized void uncaughtException(Thread thread, Throwable th) {
        this.zzb.zzt().zzy().zza(this.zza, th);
    }
}
