package com.google.common.collect;

import com.google.common.collect.TreeRangeMap$SubRangeMap.SubRangeMapAsMap;
import com.google.common.collect.TreeRangeMap.SubRangeMap;
import java.util.Iterator;
import java.util.Map.Entry;

class TreeRangeMap$SubRangeMap$1 extends SubRangeMapAsMap {
    final /* synthetic */ SubRangeMap this$1;

    TreeRangeMap$SubRangeMap$1(SubRangeMap subRangeMap) {
        this.this$1 = subRangeMap;
        super(subRangeMap);
    }

    Iterator<Entry<Range<K>, V>> entryIterator() {
        if (SubRangeMap.access$200(this.this$1).isEmpty()) {
            return Iterators.emptyIterator();
        }
        final Iterator<TreeRangeMap$RangeMapEntry<K, V>> backingItr = TreeRangeMap.access$000(this.this$1.this$0).headMap(SubRangeMap.access$200(this.this$1).upperBound, false).descendingMap().values().iterator();
        return new AbstractIterator<Entry<Range<K>, V>>() {
            protected Entry<Range<K>, V> computeNext() {
                if (!backingItr.hasNext()) {
                    return (Entry) endOfData();
                }
                TreeRangeMap$RangeMapEntry<K, V> entry = (TreeRangeMap$RangeMapEntry) backingItr.next();
                if (entry.getUpperBound().compareTo(SubRangeMap.access$200(TreeRangeMap$SubRangeMap$1.this.this$1).lowerBound) <= 0) {
                    return (Entry) endOfData();
                }
                return Maps.immutableEntry(entry.getKey().intersection(SubRangeMap.access$200(TreeRangeMap$SubRangeMap$1.this.this$1)), entry.getValue());
            }
        };
    }
}
