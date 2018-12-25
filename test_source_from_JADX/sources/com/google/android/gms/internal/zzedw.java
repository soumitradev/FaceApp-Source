package com.google.android.gms.internal;

import java.util.Iterator;
import java.util.Map.Entry;

final class zzedw<T> implements Iterator<T> {
    private Iterator<Entry<T, Void>> zza;

    public zzedw(Iterator<Entry<T, Void>> it) {
        this.zza = it;
    }

    public final boolean hasNext() {
        return this.zza.hasNext();
    }

    public final T next() {
        return ((Entry) this.zza.next()).getKey();
    }

    public final void remove() {
        this.zza.remove();
    }
}
