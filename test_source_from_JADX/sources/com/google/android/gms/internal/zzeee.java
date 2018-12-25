package com.google.android.gms.internal;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public final class zzeee<K, V> extends zzedq<K, V> {
    private zzedz<K, V> zza;
    private Comparator<K> zzb;

    private zzeee(zzedz<K, V> zzedz, Comparator<K> comparator) {
        this.zza = zzedz;
        this.zzb = comparator;
    }

    public static <A, B> zzeee<A, B> zza(Map<A, B> map, Comparator<A> comparator) {
        return zzeeg.zza(new ArrayList(map.keySet()), map, zzedr.zza(), comparator);
    }

    private final zzedz<K, V> zzg(K k) {
        zzedz<K, V> zzedz = this.zza;
        while (!zzedz.zzd()) {
            int compare = this.zzb.compare(k, zzedz.zze());
            if (compare < 0) {
                zzedz = zzedz.zzg();
            } else if (compare == 0) {
                return zzedz;
            } else {
                zzedz = zzedz.zzh();
            }
        }
        return null;
    }

    public final Iterator<Entry<K, V>> iterator() {
        return new zzedu(this.zza, null, this.zzb, false);
    }

    public final zzedq<K, V> zza(K k, V v) {
        return new zzeee(this.zza.zza(k, v, this.zzb).zza(null, null, zzeea.zzb, null, null), this.zzb);
    }

    public final K zza() {
        return this.zza.zzi().zze();
    }

    public final void zza(zzeeb<K, V> zzeeb) {
        this.zza.zza(zzeeb);
    }

    public final boolean zza(K k) {
        return zzg(k) != null;
    }

    public final K zzb() {
        return this.zza.zzj().zze();
    }

    public final V zzb(K k) {
        zzedz zzg = zzg(k);
        return zzg != null ? zzg.zzf() : null;
    }

    public final int zzc() {
        return this.zza.zzc();
    }

    public final zzedq<K, V> zzc(K k) {
        return !zza((Object) k) ? this : new zzeee(this.zza.zza(k, this.zzb).zza(null, null, zzeea.zzb, null, null), this.zzb);
    }

    public final Iterator<Entry<K, V>> zzd(K k) {
        return new zzedu(this.zza, k, this.zzb, false);
    }

    public final boolean zzd() {
        return this.zza.zzd();
    }

    public final K zze(K k) {
        zzedz zzedz = this.zza;
        zzedz zzedz2 = null;
        while (!zzedz.zzd()) {
            int compare = this.zzb.compare(k, zzedz.zze());
            if (compare == 0) {
                if (zzedz.zzg().zzd()) {
                    return zzedz2 != null ? zzedz2.zze() : null;
                } else {
                    zzedz zzg = zzedz.zzg();
                    while (!zzg.zzh().zzd()) {
                        zzg = zzg.zzh();
                    }
                    return zzg.zze();
                }
            } else if (compare < 0) {
                zzedz = zzedz.zzg();
            } else {
                zzedz2 = zzedz;
                zzedz = zzedz.zzh();
            }
        }
        String valueOf = String.valueOf(k);
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 50);
        stringBuilder.append("Couldn't find predecessor key of non-present key: ");
        stringBuilder.append(valueOf);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public final Iterator<Entry<K, V>> zze() {
        return new zzedu(this.zza, null, this.zzb, true);
    }

    public final int zzf(K k) {
        zzedz zzedz = this.zza;
        int i = 0;
        while (!zzedz.zzd()) {
            int compare = this.zzb.compare(k, zzedz.zze());
            if (compare == 0) {
                return i + zzedz.zzg().zzc();
            }
            if (compare < 0) {
                zzedz = zzedz.zzg();
            } else {
                i += zzedz.zzg().zzc() + 1;
                zzedz = zzedz.zzh();
            }
        }
        return -1;
    }

    public final Comparator<K> zzf() {
        return this.zzb;
    }
}
