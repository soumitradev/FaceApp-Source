package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.base.Preconditions;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;

public class ImmutableMultimap$Builder<K, V> {
    Multimap<K, V> builderMultimap;
    Comparator<? super K> keyComparator;
    Comparator<? super V> valueComparator;

    public ImmutableMultimap$Builder() {
        this(MultimapBuilder.linkedHashKeys().arrayListValues().build());
    }

    ImmutableMultimap$Builder(Multimap<K, V> builderMultimap) {
        this.builderMultimap = builderMultimap;
    }

    public ImmutableMultimap$Builder<K, V> put(K key, V value) {
        CollectPreconditions.checkEntryNotNull(key, value);
        this.builderMultimap.put(key, value);
        return this;
    }

    public ImmutableMultimap$Builder<K, V> put(Entry<? extends K, ? extends V> entry) {
        return put(entry.getKey(), entry.getValue());
    }

    @Beta
    public ImmutableMultimap$Builder<K, V> putAll(Iterable<? extends Entry<? extends K, ? extends V>> entries) {
        for (Entry<? extends K, ? extends V> entry : entries) {
            put(entry);
        }
        return this;
    }

    public ImmutableMultimap$Builder<K, V> putAll(K key, Iterable<? extends V> values) {
        if (key == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("null key in entry: null=");
            stringBuilder.append(Iterables.toString(values));
            throw new NullPointerException(stringBuilder.toString());
        }
        Collection<V> valueList = this.builderMultimap.get(key);
        for (V value : values) {
            CollectPreconditions.checkEntryNotNull(key, value);
            valueList.add(value);
        }
        return this;
    }

    public ImmutableMultimap$Builder<K, V> putAll(K key, V... values) {
        return putAll((Object) key, Arrays.asList(values));
    }

    public ImmutableMultimap$Builder<K, V> putAll(Multimap<? extends K, ? extends V> multimap) {
        for (Entry<? extends K, ? extends Collection<? extends V>> entry : multimap.asMap().entrySet()) {
            putAll(entry.getKey(), (Iterable) entry.getValue());
        }
        return this;
    }

    public ImmutableMultimap$Builder<K, V> orderKeysBy(Comparator<? super K> keyComparator) {
        this.keyComparator = (Comparator) Preconditions.checkNotNull(keyComparator);
        return this;
    }

    public ImmutableMultimap$Builder<K, V> orderValuesBy(Comparator<? super V> valueComparator) {
        this.valueComparator = (Comparator) Preconditions.checkNotNull(valueComparator);
        return this;
    }

    public ImmutableMultimap<K, V> build() {
        if (this.valueComparator != null) {
            for (Collection<V> values : this.builderMultimap.asMap().values()) {
                Collections.sort((List) values, this.valueComparator);
            }
        }
        if (this.keyComparator != null) {
            Multimap<K, V> sortedCopy = MultimapBuilder.linkedHashKeys().arrayListValues().build();
            for (Entry<K, Collection<V>> entry : Ordering.from(this.keyComparator).onKeys().immutableSortedCopy(this.builderMultimap.asMap().entrySet())) {
                sortedCopy.putAll(entry.getKey(), (Iterable) entry.getValue());
            }
            this.builderMultimap = sortedCopy;
        }
        return ImmutableMultimap.copyOf(this.builderMultimap);
    }
}
