package com.google.android.gms.internal;

import android.content.Context;

public final class zzbih {
    private static zzbih zzb = new zzbih();
    private zzbig zza = null;

    public static zzbig zza(Context context) {
        return zzb.zzb(context);
    }

    private final synchronized zzbig zzb(Context context) {
        if (this.zza == null) {
            if (context.getApplicationContext() != null) {
                context = context.getApplicationContext();
            }
            this.zza = new zzbig(context);
        }
        return this.zza;
    }
}
