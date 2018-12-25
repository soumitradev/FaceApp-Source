package com.google.common.collect;

import java.util.Map.Entry;

class Maps$5 extends AbstractMapEntry<K, V> {
    final /* synthetic */ Entry val$entry;

    Maps$5(Entry entry) {
        this.val$entry = entry;
    }

    public K getKey() {
        return this.val$entry.getKey();
    }

    public V getValue() {
        return this.val$entry.getValue();
    }
}
