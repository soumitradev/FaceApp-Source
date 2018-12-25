package com.google.android.gms.internal;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

class zzfjy<K extends Comparable<K>, V> extends AbstractMap<K, V> {
    private final int zza;
    private List<zzfkd> zzb;
    private Map<K, V> zzc;
    private boolean zzd;
    private volatile zzfkf zze;
    private Map<K, V> zzf;

    private zzfjy(int i) {
        this.zza = i;
        this.zzb = Collections.emptyList();
        this.zzc = Collections.emptyMap();
        this.zzf = Collections.emptyMap();
    }

    private final int zza(K k) {
        int compareTo;
        int size = this.zzb.size() - 1;
        if (size >= 0) {
            compareTo = k.compareTo((Comparable) ((zzfkd) this.zzb.get(size)).getKey());
            if (compareTo > 0) {
                return -(size + 2);
            }
            if (compareTo == 0) {
                return size;
            }
        }
        compareTo = 0;
        while (compareTo <= size) {
            int i = (compareTo + size) / 2;
            int compareTo2 = k.compareTo((Comparable) ((zzfkd) this.zzb.get(i)).getKey());
            if (compareTo2 < 0) {
                size = i - 1;
            } else if (compareTo2 <= 0) {
                return i;
            } else {
                compareTo = i + 1;
            }
        }
        return -(compareTo + 1);
    }

    static <FieldDescriptorType extends zzfhs<FieldDescriptorType>> zzfjy<FieldDescriptorType, Object> zza(int i) {
        return new zzfjz(i);
    }

    private final V zzc(int i) {
        zze();
        V value = ((zzfkd) this.zzb.remove(i)).getValue();
        if (!this.zzc.isEmpty()) {
            Iterator it = zzf().entrySet().iterator();
            this.zzb.add(new zzfkd(this, (Entry) it.next()));
            it.remove();
        }
        return value;
    }

    private final void zze() {
        if (this.zzd) {
            throw new UnsupportedOperationException();
        }
    }

    private final SortedMap<K, V> zzf() {
        zze();
        if (this.zzc.isEmpty() && !(this.zzc instanceof TreeMap)) {
            this.zzc = new TreeMap();
            this.zzf = ((TreeMap) this.zzc).descendingMap();
        }
        return (SortedMap) this.zzc;
    }

    public void clear() {
        zze();
        if (!this.zzb.isEmpty()) {
            this.zzb.clear();
        }
        if (!this.zzc.isEmpty()) {
            this.zzc.clear();
        }
    }

    public boolean containsKey(Object obj) {
        Comparable comparable = (Comparable) obj;
        if (zza(comparable) < 0) {
            if (!this.zzc.containsKey(comparable)) {
                return false;
            }
        }
        return true;
    }

    public Set<Entry<K, V>> entrySet() {
        if (this.zze == null) {
            this.zze = new zzfkf();
        }
        return this.zze;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzfjy)) {
            return super.equals(obj);
        }
        zzfjy zzfjy = (zzfjy) obj;
        int size = size();
        if (size != zzfjy.size()) {
            return false;
        }
        int zzc = zzc();
        if (zzc != zzfjy.zzc()) {
            return entrySet().equals(zzfjy.entrySet());
        }
        for (int i = 0; i < zzc; i++) {
            if (!zzb(i).equals(zzfjy.zzb(i))) {
                return false;
            }
        }
        return zzc != size ? this.zzc.equals(zzfjy.zzc) : true;
    }

    public V get(Object obj) {
        Comparable comparable = (Comparable) obj;
        int zza = zza(comparable);
        return zza >= 0 ? ((zzfkd) this.zzb.get(zza)).getValue() : this.zzc.get(comparable);
    }

    public int hashCode() {
        int i = 0;
        for (int i2 = 0; i2 < zzc(); i2++) {
            i += ((zzfkd) this.zzb.get(i2)).hashCode();
        }
        return this.zzc.size() > 0 ? i + this.zzc.hashCode() : i;
    }

    public /* synthetic */ Object put(Object obj, Object obj2) {
        return zza((Comparable) obj, obj2);
    }

    public V remove(Object obj) {
        zze();
        Comparable comparable = (Comparable) obj;
        int zza = zza(comparable);
        return zza >= 0 ? zzc(zza) : this.zzc.isEmpty() ? null : this.zzc.remove(comparable);
    }

    public int size() {
        return this.zzb.size() + this.zzc.size();
    }

    public final V zza(K k, V v) {
        zze();
        int zza = zza((Comparable) k);
        if (zza >= 0) {
            return ((zzfkd) this.zzb.get(zza)).setValue(v);
        }
        zze();
        if (this.zzb.isEmpty() && !(this.zzb instanceof ArrayList)) {
            this.zzb = new ArrayList(this.zza);
        }
        zza = -(zza + 1);
        if (zza >= this.zza) {
            return zzf().put(k, v);
        }
        if (this.zzb.size() == this.zza) {
            zzfkd zzfkd = (zzfkd) this.zzb.remove(this.zza - 1);
            zzf().put((Comparable) zzfkd.getKey(), zzfkd.getValue());
        }
        this.zzb.add(zza, new zzfkd(this, k, v));
        return null;
    }

    public void zza() {
        if (!this.zzd) {
            this.zzc = this.zzc.isEmpty() ? Collections.emptyMap() : Collections.unmodifiableMap(this.zzc);
            this.zzf = this.zzf.isEmpty() ? Collections.emptyMap() : Collections.unmodifiableMap(this.zzf);
            this.zzd = true;
        }
    }

    public final Entry<K, V> zzb(int i) {
        return (Entry) this.zzb.get(i);
    }

    public final boolean zzb() {
        return this.zzd;
    }

    public final int zzc() {
        return this.zzb.size();
    }

    public final Iterable<Entry<K, V>> zzd() {
        return this.zzc.isEmpty() ? zzfka.zza() : this.zzc.entrySet();
    }
}
