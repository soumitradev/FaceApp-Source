package com.google.common.collect;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map.Entry;

class ImmutableRangeMap$SerializedForm<K extends Comparable<?>, V> implements Serializable {
    private static final long serialVersionUID = 0;
    private final ImmutableMap<Range<K>, V> mapOfRanges;

    ImmutableRangeMap$SerializedForm(ImmutableMap<Range<K>, V> mapOfRanges) {
        this.mapOfRanges = mapOfRanges;
    }

    Object readResolve() {
        if (this.mapOfRanges.isEmpty()) {
            return ImmutableRangeMap.of();
        }
        return createRangeMap();
    }

    Object createRangeMap() {
        ImmutableRangeMap$Builder<K, V> builder = new ImmutableRangeMap$Builder();
        Iterator i$ = this.mapOfRanges.entrySet().iterator();
        while (i$.hasNext()) {
            Entry<Range<K>, V> entry = (Entry) i$.next();
            builder.put((Range) entry.getKey(), entry.getValue());
        }
        return builder.build();
    }
}
