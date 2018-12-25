package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.common.internal.zzbq;

public final class zzarm {
    private final Context zza;
    private final Context zzb;

    public zzarm(Context context) {
        zzbq.zza(context);
        context = context.getApplicationContext();
        zzbq.zza(context, "Application context can't be null");
        this.zza = context;
        this.zzb = context;
    }

    public final Context zza() {
        return this.zza;
    }

    public final Context zzb() {
        return this.zzb;
    }
}
