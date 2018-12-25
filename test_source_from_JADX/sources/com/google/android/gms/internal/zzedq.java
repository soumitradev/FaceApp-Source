package com.google.android.gms.internal;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Map.Entry;
import org.catrobat.catroid.common.Constants;

public abstract class zzedq<K, V> implements Iterable<Entry<K, V>> {
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzedq)) {
            return false;
        }
        zzedq zzedq = (zzedq) obj;
        if (!zzf().equals(zzedq.zzf()) || zzc() != zzedq.zzc()) {
            return false;
        }
        Iterator it = iterator();
        Iterator it2 = zzedq.iterator();
        while (it.hasNext()) {
            if (!((Entry) it.next()).equals(it2.next())) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int hashCode = zzf().hashCode();
        Iterator it = iterator();
        while (it.hasNext()) {
            hashCode = (hashCode * 31) + ((Entry) it.next()).hashCode();
        }
        return hashCode;
    }

    public abstract Iterator<Entry<K, V>> iterator();

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getClass().getSimpleName());
        stringBuilder.append("{");
        Iterator it = iterator();
        Object obj = 1;
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            if (obj != null) {
                obj = null;
            } else {
                stringBuilder.append(", ");
            }
            stringBuilder.append(Constants.OPENING_BRACE);
            stringBuilder.append(entry.getKey());
            stringBuilder.append("=>");
            stringBuilder.append(entry.getValue());
            stringBuilder.append(")");
        }
        stringBuilder.append("};");
        return stringBuilder.toString();
    }

    public abstract zzedq<K, V> zza(K k, V v);

    public abstract K zza();

    public abstract void zza(zzeeb<K, V> zzeeb);

    public abstract boolean zza(K k);

    public abstract K zzb();

    public abstract V zzb(K k);

    public abstract int zzc();

    public abstract zzedq<K, V> zzc(K k);

    public abstract Iterator<Entry<K, V>> zzd(K k);

    public abstract boolean zzd();

    public abstract K zze(K k);

    public abstract Iterator<Entry<K, V>> zze();

    public abstract int zzf(K k);

    public abstract Comparator<K> zzf();
}
