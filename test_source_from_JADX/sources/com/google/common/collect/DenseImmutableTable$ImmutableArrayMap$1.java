package com.google.common.collect;

import java.util.Map.Entry;

class DenseImmutableTable$ImmutableArrayMap$1 extends AbstractIterator<Entry<K, V>> {
    private int index = -1;
    private final int maxIndex = this.this$0.keyToIndex().size();
    final /* synthetic */ DenseImmutableTable$ImmutableArrayMap this$0;

    DenseImmutableTable$ImmutableArrayMap$1(DenseImmutableTable$ImmutableArrayMap denseImmutableTable$ImmutableArrayMap) {
        this.this$0 = denseImmutableTable$ImmutableArrayMap;
    }

    protected Entry<K, V> computeNext() {
        int i = this.index;
        while (true) {
            this.index = i + 1;
            if (this.index >= this.maxIndex) {
                return (Entry) endOfData();
            }
            V value = this.this$0.getValue(this.index);
            if (value != null) {
                return Maps.immutableEntry(this.this$0.getKey(this.index), value);
            }
            i = this.index;
        }
    }
}
