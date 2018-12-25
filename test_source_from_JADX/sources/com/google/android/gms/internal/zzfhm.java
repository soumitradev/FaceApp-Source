package com.google.android.gms.internal;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class zzfhm {
    static final zzfhm zza = new zzfhm(true);
    private static volatile boolean zzb = false;
    private static final Class<?> zzc = zzb();
    private final Map<Object, Object> zzd;

    zzfhm() {
        this.zzd = new HashMap();
    }

    private zzfhm(boolean z) {
        this.zzd = Collections.emptyMap();
    }

    public static zzfhm zza() {
        return zzfhl.zza();
    }

    private static Class<?> zzb() {
        try {
            return Class.forName("com.google.protobuf.Extension");
        } catch (ClassNotFoundException e) {
            return null;
        }
    }
}
