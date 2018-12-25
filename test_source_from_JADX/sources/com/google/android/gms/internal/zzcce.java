package com.google.android.gms.internal;

import com.google.android.gms.common.internal.Hide;

@Hide
public abstract class zzcce<T> {
    private final int zza;
    private final String zzb;
    private final T zzc;

    private zzcce(int i, String str, T t) {
        this.zza = i;
        this.zzb = str;
        this.zzc = t;
        zzccp.zza().zza(this);
    }

    @Hide
    public static zzccg zza(int i, String str, Boolean bool) {
        return new zzccg(0, str, bool);
    }

    @Hide
    public static zzcch zza(int i, String str, int i2) {
        return new zzcch(0, str, Integer.valueOf(i2));
    }

    @Hide
    public static zzcci zza(int i, String str, long j) {
        return new zzcci(0, str, Long.valueOf(j));
    }

    @Hide
    protected abstract T zza(zzccm zzccm);

    public final String zza() {
        return this.zzb;
    }

    public final T zzb() {
        return this.zzc;
    }

    @Hide
    public final int zzc() {
        return this.zza;
    }
}
