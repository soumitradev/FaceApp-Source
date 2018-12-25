package com.google.common.collect;

import java.util.Map.Entry;

class MapConstraints$1 extends ForwardingMapEntry<K, V> {
    final /* synthetic */ MapConstraint val$constraint;
    final /* synthetic */ Entry val$entry;

    MapConstraints$1(Entry entry, MapConstraint mapConstraint) {
        this.val$entry = entry;
        this.val$constraint = mapConstraint;
    }

    protected Entry<K, V> delegate() {
        return this.val$entry;
    }

    public V setValue(V value) {
        this.val$constraint.checkKeyValue(getKey(), value);
        return this.val$entry.setValue(value);
    }
}
