package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.common.internal.Hide;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Hide
public final class zzdkq {
    private static Integer zza = Integer.valueOf(0);
    private static Integer zzb = Integer.valueOf(1);
    private final Context zzc;
    private final ExecutorService zzd;

    public zzdkq(Context context) {
        this(context, Executors.newSingleThreadExecutor());
    }

    private zzdkq(Context context, ExecutorService executorService) {
        this.zzc = context;
        this.zzd = executorService;
    }
}
