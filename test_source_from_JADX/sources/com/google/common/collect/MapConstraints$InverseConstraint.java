package com.google.common.collect;

import com.google.common.base.Preconditions;

class MapConstraints$InverseConstraint<K, V> implements MapConstraint<K, V> {
    final MapConstraint<? super V, ? super K> constraint;

    public MapConstraints$InverseConstraint(MapConstraint<? super V, ? super K> constraint) {
        this.constraint = (MapConstraint) Preconditions.checkNotNull(constraint);
    }

    public void checkKeyValue(K key, V value) {
        this.constraint.checkKeyValue(value, key);
    }
}
