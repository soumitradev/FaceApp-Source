package com.google.common.collect;

import java.util.Iterator;
import java.util.Map.Entry;
import javax.annotation.Nullable;

final class TreeRangeMap$AsMapOfRanges extends Maps$IteratorBasedAbstractMap<Range<K>, V> {
    final Iterable<Entry<Range<K>, V>> entryIterable;
    final /* synthetic */ TreeRangeMap this$0;

    TreeRangeMap$AsMapOfRanges(TreeRangeMap treeRangeMap, Iterable<TreeRangeMap$RangeMapEntry<K, V>> entryIterable) {
        this.this$0 = treeRangeMap;
        this.entryIterable = entryIterable;
    }

    public boolean containsKey(@Nullable Object key) {
        return get(key) != null;
    }

    public V get(@Nullable Object key) {
        if (key instanceof Range) {
            Range<?> range = (Range) key;
            TreeRangeMap$RangeMapEntry<K, V> rangeMapEntry = (TreeRangeMap$RangeMapEntry) TreeRangeMap.access$000(this.this$0).get(range.lowerBound);
            if (rangeMapEntry != null && rangeMapEntry.getKey().equals(range)) {
                return rangeMapEntry.getValue();
            }
        }
        return null;
    }

    public int size() {
        return TreeRangeMap.access$000(this.this$0).size();
    }

    Iterator<Entry<Range<K>, V>> entryIterator() {
        return this.entryIterable.iterator();
    }
}
