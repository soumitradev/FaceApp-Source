package com.google.android.gms.common.internal;

import com.google.android.gms.internal.zzbgl;

public abstract class DowngradeableSafeParcel extends zzbgl implements ReflectedParcelable {
    private static final Object zza = new Object();
    private static ClassLoader zzb = null;
    private static Integer zzc = null;
    private boolean zzd = false;

    @Hide
    protected static Integer o_() {
        synchronized (zza) {
        }
        return null;
    }

    @Hide
    protected static boolean zza(String str) {
        zzb();
        return true;
    }

    @Hide
    private static ClassLoader zzb() {
        synchronized (zza) {
        }
        return null;
    }
}
