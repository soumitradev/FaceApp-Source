package com.google.common.collect;

import com.google.common.base.Preconditions;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

class MapConstraints$ConstrainedMap<K, V> extends ForwardingMap<K, V> {
    final MapConstraint<? super K, ? super V> constraint;
    private final Map<K, V> delegate;
    private transient Set<Entry<K, V>> entrySet;

    MapConstraints$ConstrainedMap(Map<K, V> delegate, MapConstraint<? super K, ? super V> constraint) {
        this.delegate = (Map) Preconditions.checkNotNull(delegate);
        this.constraint = (MapConstraint) Preconditions.checkNotNull(constraint);
    }

    protected Map<K, V> delegate() {
        return this.delegate;
    }

    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> result = this.entrySet;
        if (result != null) {
            return result;
        }
        Set<Entry<K, V>> access$000 = MapConstraints.access$000(this.delegate.entrySet(), this.constraint);
        result = access$000;
        this.entrySet = access$000;
        return result;
    }

    public V put(K key, V value) {
        this.constraint.checkKeyValue(key, value);
        return this.delegate.put(key, value);
    }

    public void putAll(Map<? extends K, ? extends V> map) {
        this.delegate.putAll(MapConstraints.access$100(map, this.constraint));
    }
}
