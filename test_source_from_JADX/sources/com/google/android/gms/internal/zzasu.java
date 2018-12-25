package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzbq;

public final class zzasu<V> {
    private final V zza;
    private final zzbfx<V> zzb;

    private zzasu(zzbfx<V> zzbfx, V v) {
        zzbq.zza(zzbfx);
        this.zzb = zzbfx;
        this.zza = v;
    }

    static zzasu<Float> zza(String str, float f, float f2) {
        return new zzasu(zzbfx.zza(str, Float.valueOf(0.5f)), Float.valueOf(0.5f));
    }

    static zzasu<Integer> zza(String str, int i, int i2) {
        return new zzasu(zzbfx.zza(str, Integer.valueOf(i2)), Integer.valueOf(i));
    }

    static zzasu<Long> zza(String str, long j, long j2) {
        return new zzasu(zzbfx.zza(str, Long.valueOf(j2)), Long.valueOf(j));
    }

    static zzasu<String> zza(String str, String str2, String str3) {
        return new zzasu(zzbfx.zza(str, str3), str2);
    }

    static zzasu<Boolean> zza(String str, boolean z, boolean z2) {
        return new zzasu(zzbfx.zza(str, z2), Boolean.valueOf(z));
    }

    public final V zza() {
        return this.zza;
    }
}
