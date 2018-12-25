package com.google.android.gms.internal;

public final class zzedx<K, V> extends zzeed<K, V> {
    private int zza = -1;

    zzedx(K k, V v, zzedz<K, V> zzedz, zzedz<K, V> zzedz2) {
        super(k, v, zzedz, zzedz2);
    }

    protected final int zza() {
        return zzeea.zzb;
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
        return new zzedx(zze, zzf, zzg, zzh);
    }

    final void zza(zzedz<K, V> zzedz) {
        if (this.zza != -1) {
            throw new IllegalStateException("Can't set left after using size");
        }
        super.zza((zzedz) zzedz);
    }

    public final boolean zzb() {
        return false;
    }

    public final int zzc() {
        if (this.zza == -1) {
            this.zza = (zzg().zzc() + 1) + zzh().zzc();
        }
        return this.zza;
    }
}
