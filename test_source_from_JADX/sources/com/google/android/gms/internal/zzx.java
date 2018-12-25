package com.google.android.gms.internal;

public final class zzx<T> {
    public final T zza;
    public final zzc zzb;
    public final zzae zzc;
    public boolean zzd;

    private zzx(zzae zzae) {
        this.zzd = false;
        this.zza = null;
        this.zzb = null;
        this.zzc = zzae;
    }

    private zzx(T t, zzc zzc) {
        this.zzd = false;
        this.zza = t;
        this.zzb = zzc;
        this.zzc = null;
    }

    public static <T> zzx<T> zza(zzae zzae) {
        return new zzx(zzae);
    }

    public static <T> zzx<T> zza(T t, zzc zzc) {
        return new zzx(t, zzc);
    }
}
