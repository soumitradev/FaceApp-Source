package com.google.android.gms.internal;

import java.util.Iterator;
import java.util.Map.Entry;

final class zzfij<K> implements Iterator<Entry<K, Object>> {
    private Iterator<Entry<K, Object>> zza;

    public zzfij(Iterator<Entry<K, Object>> it) {
        this.zza = it;
    }

    public final boolean hasNext() {
        return this.zza.hasNext();
    }

    public final /* synthetic */ Object next() {
        Entry entry = (Entry) this.zza.next();
        return entry.getValue() instanceof zzfig ? new zzfii(entry) : entry;
    }

    public final void remove() {
        this.zza.remove();
    }
}
