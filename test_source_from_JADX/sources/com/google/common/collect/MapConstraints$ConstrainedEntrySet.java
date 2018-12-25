package com.google.common.collect;

import java.util.Map.Entry;
import java.util.Set;
import javax.annotation.Nullable;

class MapConstraints$ConstrainedEntrySet<K, V> extends MapConstraints$ConstrainedEntries<K, V> implements Set<Entry<K, V>> {
    MapConstraints$ConstrainedEntrySet(Set<Entry<K, V>> entries, MapConstraint<? super K, ? super V> constraint) {
        super(entries, constraint);
    }

    public boolean equals(@Nullable Object object) {
        return Sets.equalsImpl(this, object);
    }

    public int hashCode() {
        return Sets.hashCodeImpl(this);
    }
}
