package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzbq;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public final class zzbid implements ThreadFactory {
    private final String zza;
    private final int zzb;
    private final AtomicInteger zzc;
    private final ThreadFactory zzd;

    public zzbid(String str) {
        this(str, 0);
    }

    private zzbid(String str, int i) {
        this.zzc = new AtomicInteger();
        this.zzd = Executors.defaultThreadFactory();
        this.zza = (String) zzbq.zza(str, "Name must not be null");
        this.zzb = 0;
    }

    public final Thread newThread(Runnable runnable) {
        Thread newThread = this.zzd.newThread(new zzbie(runnable, 0));
        String str = this.zza;
        int andIncrement = this.zzc.getAndIncrement();
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(str).length() + 13);
        stringBuilder.append(str);
        stringBuilder.append("[");
        stringBuilder.append(andIncrement);
        stringBuilder.append("]");
        newThread.setName(stringBuilder.toString());
        return newThread;
    }
}
