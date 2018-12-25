package com.google.android.gms.internal;

public final class zzcja<V> {
    private final V zza;
    private final V zzb;
    private final String zzc;

    private zzcja(String str, V v, V v2) {
        this.zza = v;
        this.zzb = v2;
        this.zzc = str;
    }

    static zzcja<Integer> zza(String str, int i, int i2) {
        zzcja<Integer> zzcja = new zzcja(str, Integer.valueOf(i), Integer.valueOf(i2));
        zzciz.zza.add(zzcja);
        return zzcja;
    }

    static zzcja<Long> zza(String str, long j, long j2) {
        zzcja<Long> zzcja = new zzcja(str, Long.valueOf(j), Long.valueOf(j2));
        zzciz.zzb.add(zzcja);
        return zzcja;
    }

    static zzcja<String> zza(String str, String str2, String str3) {
        zzcja<String> zzcja = new zzcja(str, str2, str3);
        zzciz.zzd.add(zzcja);
        return zzcja;
    }

    static zzcja<Boolean> zza(String str, boolean z, boolean z2) {
        zzcja<Boolean> zzcja = new zzcja(str, Boolean.valueOf(false), Boolean.valueOf(false));
        zzciz.zzc.add(zzcja);
        return zzcja;
    }

    public final V zza(V v) {
        return v != null ? v : this.zza;
    }

    public final String zza() {
        return this.zzc;
    }

    public final V zzb() {
        return this.zza;
    }
}
