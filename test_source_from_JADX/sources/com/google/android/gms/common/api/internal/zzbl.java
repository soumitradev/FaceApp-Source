package com.google.android.gms.common.api.internal;

import com.google.android.gms.internal.zzbid;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class zzbl {
    private static final ExecutorService zza = Executors.newFixedThreadPool(2, new zzbid("GAC_Executor"));

    public static ExecutorService zza() {
        return zza;
    }
}
