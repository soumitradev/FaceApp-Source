package com.google.android.gms.internal;

import android.support.annotation.WorkerThread;
import com.google.android.gms.common.internal.zzbq;
import java.util.List;
import java.util.Map;

@WorkerThread
final class zzcjq implements Runnable {
    private final zzcjp zza;
    private final int zzb;
    private final Throwable zzc;
    private final byte[] zzd;
    private final String zze;
    private final Map<String, List<String>> zzf;

    private zzcjq(String str, zzcjp zzcjp, int i, Throwable th, byte[] bArr, Map<String, List<String>> map) {
        zzbq.zza(zzcjp);
        this.zza = zzcjp;
        this.zzb = i;
        this.zzc = th;
        this.zzd = bArr;
        this.zze = str;
        this.zzf = map;
    }

    public final void run() {
        this.zza.zza(this.zze, this.zzb, this.zzc, this.zzd, this.zzf);
    }
}
