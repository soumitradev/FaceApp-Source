package com.google.android.gms.common.util;

import android.os.SystemClock;

public final class zzi implements zze {
    private static zzi zza = new zzi();

    private zzi() {
    }

    public static zze zzd() {
        return zza;
    }

    public final long zza() {
        return System.currentTimeMillis();
    }

    public final long zzb() {
        return SystemClock.elapsedRealtime();
    }

    public final long zzc() {
        return System.nanoTime();
    }
}
