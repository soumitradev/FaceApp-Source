package com.google.android.gms.internal;

public final class zzeec<K, V> extends zzeed<K, V> {
    zzeec(K k, V v) {
        super(k, v, zzedy.zza(), zzedy.zza());
    }

    zzeec(K k, V v, zzedz<K, V> zzedz, zzedz<K, V> zzedz2) {
        super(k, v, zzedz, zzedz2);
    }

    protected final int zza() {
        return zzeea.zza;
    }

    protected final zzeed<K, V> zza(K k, V v, zzedz<K, V> zzedz, zzedz<K, V> zzedz2) {
        Object zze;
        Object zzf;
        zzedz zzg;
        zzedz zzh;
        if (k == null) {
            zze = zze();
        }
        if (v == null) {
            zzf = zzf();
        }
        if (zzedz == null) {
            zzg = zzg();
        }
        if (zzedz2 == null) {
            zzh = zzh();
        }
        return new zzeec(zze, zzf, zzg, zzh);
    }

    public final boolean zzb() {
        return true;
    }

    public final int zzc() {
        return (zzg().zzc() + 1) + zzh().zzc();
    }
}
