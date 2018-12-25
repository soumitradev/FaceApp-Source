package com.google.android.gms.internal;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public final class zzedo<K, V> extends zzedq<K, V> {
    private final K[] zza;
    private final V[] zzb;
    private final Comparator<K> zzc;

    public zzedo(Comparator<K> comparator) {
        this.zza = new Object[0];
        this.zzb = new Object[0];
        this.zzc = comparator;
    }

    private zzedo(Comparator<K> comparator, K[] kArr, V[] vArr) {
        this.zza = kArr;
        this.zzb = vArr;
        this.zzc = comparator;
    }

    public static <A, B, C> zzedo<A, C> zza(List<A> list, Map<B, C> map, zzedt<A, B> zzedt, Comparator<A> comparator) {
        Collections.sort(list, comparator);
        int size = list.size();
        Object[] objArr = new Object[size];
        Object[] objArr2 = new Object[size];
        int i = 0;
        for (Object next : list) {
            objArr[i] = next;
            objArr2[i] = map.get(zzedt.zza(next));
            i++;
        }
        return new zzedo(comparator, objArr, objArr2);
    }

    private final Iterator<Entry<K, V>> zza(int i, boolean z) {
        return new zzedp(this, i, z);
    }

    private static <T> T[] zza(T[] tArr, int i) {
        int length = tArr.length - 1;
        Object obj = new Object[length];
        System.arraycopy(tArr, 0, obj, 0, i);
        System.arraycopy(tArr, i + 1, obj, i, length - i);
        return obj;
    }

    private static <T> T[] zza(T[] tArr, int i, T t) {
        int length = tArr.length + 1;
        Object obj = new Object[length];
        System.arraycopy(tArr, 0, obj, 0, i);
        obj[i] = t;
        System.arraycopy(tArr, i, obj, i + 1, (length - i) - 1);
        return obj;
    }

    private static <T> T[] zzb(T[] tArr, int i, T t) {
        int length = tArr.length;
        Object obj = new Object[length];
        System.arraycopy(tArr, 0, obj, 0, length);
        obj[i] = t;
        return obj;
    }

    private final int zzg(K k) {
        int i = 0;
        while (i < this.zza.length && this.zzc.compare(this.zza[i], k) < 0) {
            i++;
        }
        return i;
    }

    private final int zzh(K k) {
        int i = 0;
        for (Object compare : this.zza) {
            if (this.zzc.compare(k, compare) == 0) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public final Iterator<Entry<K, V>> iterator() {
        return zza(0, false);
    }

    public final zzedq<K, V> zza(K k, V v) {
        int zzh = zzh(k);
        if (zzh != -1) {
            if (this.zza[zzh] == k && this.zzb[zzh] == v) {
                return this;
            }
            return new zzedo(this.zzc, zzb(this.zza, zzh, k), zzb(this.zzb, zzh, v));
        } else if (this.zza.length > 25) {
            Map hashMap = new HashMap(this.zza.length + 1);
            for (int i = 0; i < this.zza.length; i++) {
                hashMap.put(this.zza[i], this.zzb[i]);
            }
            hashMap.put(k, v);
            return zzeee.zza(hashMap, this.zzc);
        } else {
            zzh = zzg(k);
            return new zzedo(this.zzc, zza(this.zza, zzh, k), zza(this.zzb, zzh, v));
        }
    }

    public final K zza() {
        return this.zza.length > 0 ? this.zza[0] : null;
    }

    public final void zza(zzeeb<K, V> zzeeb) {
        for (int i = 0; i < this.zza.length; i++) {
            zzeeb.zza(this.zza[i], this.zzb[i]);
        }
    }

    public final boolean zza(K k) {
        return zzh(k) != -1;
    }

    public final K zzb() {
        return this.zza.length > 0 ? this.zza[this.zza.length - 1] : null;
    }

    public final V zzb(K k) {
        int zzh = zzh(k);
        return zzh != -1 ? this.zzb[zzh] : null;
    }

    public final int zzc() {
        return this.zza.length;
    }

    public final zzedq<K, V> zzc(K k) {
        int zzh = zzh(k);
        if (zzh == -1) {
            return this;
        }
        return new zzedo(this.zzc, zza(this.zza, zzh), zza(this.zzb, zzh));
    }

    public final Iterator<Entry<K, V>> zzd(K k) {
        return zza(zzg(k), false);
    }

    public final boolean zzd() {
        return this.zza.length == 0;
    }

    public final K zze(K k) {
        int zzh = zzh(k);
        if (zzh != -1) {
            return zzh > 0 ? this.zza[zzh - 1] : null;
        } else {
            throw new IllegalArgumentException("Can't find predecessor of nonexistent key");
        }
    }

    public final Iterator<Entry<K, V>> zze() {
        return zza(this.zza.length - 1, true);
    }

    public final int zzf(K k) {
        return zzh(k);
    }

    public final Comparator<K> zzf() {
        return this.zzc;
    }
}
