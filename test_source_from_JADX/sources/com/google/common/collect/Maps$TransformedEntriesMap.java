package com.google.common.collect;

import com.google.common.base.Preconditions;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

class Maps$TransformedEntriesMap<K, V1, V2> extends Maps$IteratorBasedAbstractMap<K, V2> {
    final Map<K, V1> fromMap;
    final Maps$EntryTransformer<? super K, ? super V1, V2> transformer;

    Maps$TransformedEntriesMap(Map<K, V1> fromMap, Maps$EntryTransformer<? super K, ? super V1, V2> transformer) {
        this.fromMap = (Map) Preconditions.checkNotNull(fromMap);
        this.transformer = (Maps$EntryTransformer) Preconditions.checkNotNull(transformer);
    }

    public int size() {
        return this.fromMap.size();
    }

    public boolean containsKey(Object key) {
        return this.fromMap.containsKey(key);
    }

    public V2 get(Object key) {
        V1 value = this.fromMap.get(key);
        if (value == null) {
            if (!this.fromMap.containsKey(key)) {
                return null;
            }
        }
        return this.transformer.transformEntry(key, value);
    }

    public V2 remove(Object key) {
        return this.fromMap.containsKey(key) ? this.transformer.transformEntry(key, this.fromMap.remove(key)) : null;
    }

    public void clear() {
        this.fromMap.clear();
    }

    public Set<K> keySet() {
        return this.fromMap.keySet();
    }

    Iterator<Entry<K, V2>> entryIterator() {
        return Iterators.transform(this.fromMap.entrySet().iterator(), Maps.asEntryToEntryFunction(this.transformer));
    }

    public Collection<V2> values() {
        return new Maps$Values(this);
    }
}
