package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.base.Preconditions;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;

public class ImmutableMap$Builder<K, V> {
    ImmutableMapEntry<K, V>[] entries;
    boolean entriesUsed;
    int size;
    Comparator<? super V> valueComparator;

    public ImmutableMap$Builder() {
        this(4);
    }

    ImmutableMap$Builder(int initialCapacity) {
        this.entries = new ImmutableMapEntry[initialCapacity];
        this.size = 0;
        this.entriesUsed = false;
    }

    private void ensureCapacity(int minCapacity) {
        if (minCapacity > this.entries.length) {
            this.entries = (ImmutableMapEntry[]) ObjectArrays.arraysCopyOf(this.entries, ImmutableCollection$Builder.expandedCapacity(this.entries.length, minCapacity));
            this.entriesUsed = false;
        }
    }

    public ImmutableMap$Builder<K, V> put(K key, V value) {
        ensureCapacity(this.size + 1);
        ImmutableMapEntry<K, V> entry = ImmutableMap.entryOf(key, value);
        ImmutableMapEntry[] immutableMapEntryArr = this.entries;
        int i = this.size;
        this.size = i + 1;
        immutableMapEntryArr[i] = entry;
        return this;
    }

    public ImmutableMap$Builder<K, V> put(Entry<? extends K, ? extends V> entry) {
        return put(entry.getKey(), entry.getValue());
    }

    public ImmutableMap$Builder<K, V> putAll(Map<? extends K, ? extends V> map) {
        return putAll(map.entrySet());
    }

    @Beta
    public ImmutableMap$Builder<K, V> putAll(Iterable<? extends Entry<? extends K, ? extends V>> entries) {
        if (entries instanceof Collection) {
            ensureCapacity(this.size + ((Collection) entries).size());
        }
        for (Entry<? extends K, ? extends V> entry : entries) {
            put(entry);
        }
        return this;
    }

    @Beta
    public ImmutableMap$Builder<K, V> orderEntriesByValue(Comparator<? super V> valueComparator) {
        Preconditions.checkState(this.valueComparator == null, "valueComparator was already set");
        this.valueComparator = (Comparator) Preconditions.checkNotNull(valueComparator, "valueComparator");
        return this;
    }

    public ImmutableMap<K, V> build() {
        boolean z = false;
        switch (this.size) {
            case 0:
                return ImmutableMap.of();
            case 1:
                return ImmutableMap.of(this.entries[0].getKey(), this.entries[0].getValue());
            default:
                if (this.valueComparator != null) {
                    if (this.entriesUsed) {
                        this.entries = (ImmutableMapEntry[]) ObjectArrays.arraysCopyOf(this.entries, this.size);
                    }
                    Arrays.sort(this.entries, 0, this.size, Ordering.from(this.valueComparator).onResultOf(Maps.valueFunction()));
                }
                if (this.size == this.entries.length) {
                    z = true;
                }
                this.entriesUsed = z;
                return RegularImmutableMap.fromEntryArray(this.size, this.entries);
        }
    }
}
