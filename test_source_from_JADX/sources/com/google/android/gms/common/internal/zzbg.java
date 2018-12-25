package com.google.android.gms.common.internal;

import android.support.annotation.Nullable;

@Hide
public final class zzbg {
    public static zzbi zza(Object obj) {
        return new zzbi(obj);
    }

    public static boolean zza(@Nullable Object obj, @Nullable Object obj2) {
        if (obj != obj2) {
            if (obj == null || !obj.equals(obj2)) {
                return false;
            }
        }
        return true;
    }
}
