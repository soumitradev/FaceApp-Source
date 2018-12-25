package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import java.util.AbstractMap;
import java.util.Collection;
import java.util.Map.Entry;
import java.util.Set;

@GwtCompatible
abstract class Maps$ViewCachingAbstractMap<K, V> extends AbstractMap<K, V> {
    private transient Set<Entry<K, V>> entrySet;
    private transient Set<K> keySet;
    private transient Collection<V> values;

    abstract Set<Entry<K, V>> createEntrySet();

    Maps$ViewCachingAbstractMap() {
    }

    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> result = this.entrySet;
        if (result != null) {
            return result;
        }
        Set<Entry<K, V>> createEntrySet = createEntrySet();
        this.entrySet = createEntrySet;
        return createEntrySet;
    }

    public Set<K> keySet() {
        Set<K> result = this.keySet;
        if (result != null) {
            return result;
        }
        Set<K> createKeySet = createKeySet();
        this.keySet = createKeySet;
        return createKeySet;
    }

    Set<K> createKeySet() {
        return new Maps$KeySet(this);
    }

    public Collection<V> values() {
        Collection<V> result = this.values;
        if (result != null) {
            return result;
        }
        Collection<V> createValues = createValues();
        this.values = createValues;
        return createValues;
    }

    Collection<V> createValues() {
        return new Maps$Values(this);
    }
}
