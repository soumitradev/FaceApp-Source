package com.google.common.collect;

import java.util.Map.Entry;
import java.util.Set;

class MapConstraints$ConstrainedSetMultimap<K, V> extends MapConstraints$ConstrainedMultimap<K, V> implements SetMultimap<K, V> {
    MapConstraints$ConstrainedSetMultimap(SetMultimap<K, V> delegate, MapConstraint<? super K, ? super V> constraint) {
        super(delegate, constraint);
    }

    public Set<V> get(K key) {
        return (Set) super.get(key);
    }

    public Set<Entry<K, V>> entries() {
        return (Set) super.entries();
    }

    public Set<V> removeAll(Object key) {
        return (Set) super.removeAll(key);
    }

    public Set<V> replaceValues(K key, Iterable<? extends V> values) {
        return (Set) super.replaceValues(key, values);
    }
}
