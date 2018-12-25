package com.google.common.collect;

import java.util.Iterator;
import java.util.Map.Entry;

class Maps$6 extends UnmodifiableIterator<Entry<K, V>> {
    final /* synthetic */ Iterator val$entryIterator;

    Maps$6(Iterator it) {
        this.val$entryIterator = it;
    }

    public boolean hasNext() {
        return this.val$entryIterator.hasNext();
    }

    public Entry<K, V> next() {
        return Maps.unmodifiableEntry((Entry) this.val$entryIterator.next());
    }
}
