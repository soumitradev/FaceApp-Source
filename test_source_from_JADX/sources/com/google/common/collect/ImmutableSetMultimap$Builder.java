package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.base.Preconditions;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map.Entry;

public final class ImmutableSetMultimap$Builder<K, V> extends ImmutableMultimap$Builder<K, V> {
    public ImmutableSetMultimap$Builder() {
        super(MultimapBuilder.linkedHashKeys().linkedHashSetValues().build());
    }

    public ImmutableSetMultimap$Builder<K, V> put(K key, V value) {
        this.builderMultimap.put(Preconditions.checkNotNull(key), Preconditions.checkNotNull(value));
        return this;
    }

    public ImmutableSetMultimap$Builder<K, V> put(Entry<? extends K, ? extends V> entry) {
        this.builderMultimap.put(Preconditions.checkNotNull(entry.getKey()), Preconditions.checkNotNull(entry.getValue()));
        return this;
    }

    @Beta
    public ImmutableSetMultimap$Builder<K, V> putAll(Iterable<? extends Entry<? extends K, ? extends V>> entries) {
        super.putAll((Iterable) entries);
        return this;
    }

    public ImmutableSetMultimap$Builder<K, V> putAll(K key, Iterable<? extends V> values) {
        Collection<V> collection = this.builderMultimap.get(Preconditions.checkNotNull(key));
        for (V value : values) {
            collection.add(Preconditions.checkNotNull(value));
        }
        return this;
    }

    public ImmutableSetMultimap$Builder<K, V> putAll(K key, V... values) {
        return putAll((Object) key, Arrays.asList(values));
    }

    public ImmutableSetMultimap$Builder<K, V> putAll(Multimap<? extends K, ? extends V> multimap) {
        for (Entry<? extends K, ? extends Collection<? extends V>> entry : multimap.asMap().entrySet()) {
            putAll(entry.getKey(), (Iterable) entry.getValue());
        }
        return this;
    }

    public ImmutableSetMultimap$Builder<K, V> orderKeysBy(Comparator<? super K> keyComparator) {
        this.keyComparator = (Comparator) Preconditions.checkNotNull(keyComparator);
        return this;
    }

    public ImmutableSetMultimap$Builder<K, V> orderValuesBy(Comparator<? super V> valueComparator) {
        super.orderValuesBy(valueComparator);
        return this;
    }

    public ImmutableSetMultimap<K, V> build() {
        if (this.keyComparator != null) {
            Multimap<K, V> sortedCopy = MultimapBuilder.linkedHashKeys().linkedHashSetValues().build();
            for (Entry<K, Collection<V>> entry : Ordering.from(this.keyComparator).onKeys().immutableSortedCopy(this.builderMultimap.asMap().entrySet())) {
                sortedCopy.putAll(entry.getKey(), (Iterable) entry.getValue());
            }
            this.builderMultimap = sortedCopy;
        }
        return ImmutableSetMultimap.access$000(this.builderMultimap, this.valueComparator);
    }
}
