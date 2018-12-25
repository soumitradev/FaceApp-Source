package com.google.android.gms.internal;

import java.util.Map.Entry;

final class zzfii<K> implements Entry<K, Object> {
    private Entry<K, zzfig> zza;

    private zzfii(Entry<K, zzfig> entry) {
        this.zza = entry;
    }

    public final K getKey() {
        return this.zza.getKey();
    }

    public final Object getValue() {
        return ((zzfig) this.zza.getValue()) == null ? null : zzfig.zza();
    }

    public final Object setValue(Object obj) {
        if (obj instanceof zzfjc) {
            return ((zzfig) this.zza.getValue()).zza((zzfjc) obj);
        }
        throw new IllegalArgumentException("LazyField now only used for MessageSet, and the value of MessageSet must be an instance of MessageLite");
    }

    public final zzfig zza() {
        return (zzfig) this.zza.getValue();
    }
}
