package com.google.common.collect;

import com.google.common.annotations.Beta;
import java.util.Comparator;
import java.util.Map.Entry;

public final class ImmutableListMultimap$Builder<K, V> extends ImmutableMultimap$Builder<K, V> {
    public ImmutableListMultimap$Builder<K, V> put(K key, V value) {
        super.put(key, value);
        return this;
    }

    public ImmutableListMultimap$Builder<K, V> put(Entry<? extends K, ? extends V> entry) {
        super.put(entry);
        return this;
    }

    @Beta
    public ImmutableListMultimap$Builder<K, V> putAll(Iterable<? extends Entry<? extends K, ? extends V>> entries) {
        super.putAll((Iterable) entries);
        return this;
    }

    public ImmutableListMultimap$Builder<K, V> putAll(K key, Iterable<? extends V> values) {
        super.putAll((Object) key, (Iterable) values);
        return this;
    }

    public ImmutableListMultimap$Builder<K, V> putAll(K key, V... values) {
        super.putAll((Object) key, (Object[]) values);
        return this;
    }

    public ImmutableListMultimap$Builder<K, V> putAll(Multimap<? extends K, ? extends V> multimap) {
        super.putAll((Multimap) multimap);
        return this;
    }

    public ImmutableListMultimap$Builder<K, V> orderKeysBy(Comparator<? super K> keyComparator) {
        super.orderKeysBy(keyComparator);
        return this;
    }

    public ImmutableListMultimap$Builder<K, V> orderValuesBy(Comparator<? super V> valueComparator) {
        super.orderValuesBy(valueComparator);
        return this;
    }

    public ImmutableListMultimap<K, V> build() {
        return (ImmutableListMultimap) super.build();
    }
}
