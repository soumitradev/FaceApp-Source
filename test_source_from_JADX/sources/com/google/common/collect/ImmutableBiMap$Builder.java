package com.google.common.collect;

import com.google.common.annotations.Beta;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;

public final class ImmutableBiMap$Builder<K, V> extends ImmutableMap$Builder<K, V> {
    ImmutableBiMap$Builder(int size) {
        super(size);
    }

    public ImmutableBiMap$Builder<K, V> put(K key, V value) {
        super.put(key, value);
        return this;
    }

    public ImmutableBiMap$Builder<K, V> put(Entry<? extends K, ? extends V> entry) {
        super.put(entry);
        return this;
    }

    public ImmutableBiMap$Builder<K, V> putAll(Map<? extends K, ? extends V> map) {
        super.putAll((Map) map);
        return this;
    }

    @Beta
    public ImmutableBiMap$Builder<K, V> putAll(Iterable<? extends Entry<? extends K, ? extends V>> entries) {
        super.putAll((Iterable) entries);
        return this;
    }

    @Beta
    public ImmutableBiMap$Builder<K, V> orderEntriesByValue(Comparator<? super V> valueComparator) {
        super.orderEntriesByValue(valueComparator);
        return this;
    }

    public ImmutableBiMap<K, V> build() {
        boolean z = false;
        switch (this.size) {
            case 0:
                return ImmutableBiMap.of();
            case 1:
                return ImmutableBiMap.of(this.entries[0].getKey(), this.entries[0].getValue());
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
                return RegularImmutableBiMap.fromEntryArray(this.size, this.entries);
        }
    }
}
