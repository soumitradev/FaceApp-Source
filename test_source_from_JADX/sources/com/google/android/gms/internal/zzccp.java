package com.google.android.gms.internal;

import com.google.android.gms.common.internal.Hide;

@Hide
public final class zzccp {
    private static zzccp zza;
    private final zzcck zzb = new zzcck();
    private final zzccl zzc = new zzccl();

    static {
        zzccp zzccp = new zzccp();
        synchronized (zzccp.class) {
            zza = zzccp;
        }
    }

    private zzccp() {
    }

    public static zzcck zza() {
        return zzc().zzb;
    }

    public static zzccl zzb() {
        return zzc().zzc;
    }

    private static zzccp zzc() {
        zzccp zzccp;
        synchronized (zzccp.class) {
            zzccp = zza;
        }
        return zzccp;
    }
}
