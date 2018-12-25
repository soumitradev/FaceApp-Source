package com.google.common.collect;

import java.util.Set;
import javax.annotation.Nullable;

class MapConstraints$ConstrainedBiMap<K, V> extends MapConstraints$ConstrainedMap<K, V> implements BiMap<K, V> {
    volatile BiMap<V, K> inverse;

    MapConstraints$ConstrainedBiMap(BiMap<K, V> delegate, @Nullable BiMap<V, K> inverse, MapConstraint<? super K, ? super V> constraint) {
        super(delegate, constraint);
        this.inverse = inverse;
    }

    protected BiMap<K, V> delegate() {
        return (BiMap) super.delegate();
    }

    public V forcePut(K key, V value) {
        this.constraint.checkKeyValue(key, value);
        return delegate().forcePut(key, value);
    }

    public BiMap<V, K> inverse() {
        if (this.inverse == null) {
            this.inverse = new MapConstraints$ConstrainedBiMap(delegate().inverse(), this, new MapConstraints$InverseConstraint(this.constraint));
        }
        return this.inverse;
    }

    public Set<V> values() {
        return delegate().values();
    }
}
