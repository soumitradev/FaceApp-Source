package com.google.android.gms.internal;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public final class zzfiw<K, V> extends LinkedHashMap<K, V> {
    private static final zzfiw zzb;
    private boolean zza = true;

    static {
        zzfiw zzfiw = new zzfiw();
        zzb = zzfiw;
        zzfiw.zza = false;
    }

    private zzfiw() {
    }

    private zzfiw(Map<K, V> map) {
        super(map);
    }

    private static int zza(Object obj) {
        if (obj instanceof byte[]) {
            return zzfhz.zza((byte[]) obj);
        }
        if (!(obj instanceof zzfia)) {
            return obj.hashCode();
        }
        throw new UnsupportedOperationException();
    }

    public static <K, V> zzfiw<K, V> zza() {
        return zzb;
    }

    private final void zze() {
        if (!this.zza) {
            throw new UnsupportedOperationException();
        }
    }

    public final void clear() {
        zze();
        super.clear();
    }

    public final Set<Entry<K, V>> entrySet() {
        return isEmpty() ? Collections.emptySet() : super.entrySet();
    }

    public final boolean equals(Object obj) {
        if (obj instanceof Map) {
            obj = (Map) obj;
            if (this != obj) {
                if (size() == obj.size()) {
                    for (Entry entry : entrySet()) {
                        if (obj.containsKey(entry.getKey())) {
                            boolean equals;
                            Object value = entry.getValue();
                            Object obj2 = obj.get(entry.getKey());
                            if ((value instanceof byte[]) && (obj2 instanceof byte[])) {
                                equals = Arrays.equals((byte[]) value, (byte[]) obj2);
                                continue;
                            } else {
                                equals = value.equals(obj2);
                                continue;
                            }
                            if (!equals) {
                            }
                        }
                    }
                }
                obj = null;
                if (obj != null) {
                }
            }
            obj = 1;
            return obj != null;
        }
    }

    public final int hashCode() {
        int i = 0;
        for (Entry entry : entrySet()) {
            i += zza(entry.getValue()) ^ zza(entry.getKey());
        }
        return i;
    }

    public final V put(K k, V v) {
        zze();
        zzfhz.zza((Object) k);
        zzfhz.zza((Object) v);
        return super.put(k, v);
    }

    public final void putAll(Map<? extends K, ? extends V> map) {
        zze();
        for (Object next : map.keySet()) {
            zzfhz.zza(next);
            zzfhz.zza(map.get(next));
        }
        super.putAll(map);
    }

    public final V remove(Object obj) {
        zze();
        return super.remove(obj);
    }

    public final void zza(zzfiw<K, V> zzfiw) {
        zze();
        if (!zzfiw.isEmpty()) {
            putAll(zzfiw);
        }
    }

    public final zzfiw<K, V> zzb() {
        return isEmpty() ? new zzfiw() : new zzfiw(this);
    }

    public final void zzc() {
        this.zza = false;
    }

    public final boolean zzd() {
        return this.zza;
    }
}
