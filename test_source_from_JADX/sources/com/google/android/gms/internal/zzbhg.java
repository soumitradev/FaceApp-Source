package com.google.android.gms.internal;

import com.google.android.gms.common.internal.Hide;

@Hide
public final class zzbhg {
    private static zzbhi zza;

    @Hide
    public static synchronized zzbhi zza() {
        zzbhi zzbhi;
        synchronized (zzbhg.class) {
            if (zza == null) {
                zza = new zzbhh();
            }
            zzbhi = zza;
        }
        return zzbhi;
    }
}
