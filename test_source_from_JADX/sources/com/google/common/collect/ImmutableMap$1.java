package com.google.common.collect;

import java.util.Map.Entry;

class ImmutableMap$1 extends UnmodifiableIterator<K> {
    final /* synthetic */ ImmutableMap this$0;
    final /* synthetic */ UnmodifiableIterator val$entryIterator;

    ImmutableMap$1(ImmutableMap immutableMap, UnmodifiableIterator unmodifiableIterator) {
        this.this$0 = immutableMap;
        this.val$entryIterator = unmodifiableIterator;
    }

    public boolean hasNext() {
        return this.val$entryIterator.hasNext();
    }

    public K next() {
        return ((Entry) this.val$entryIterator.next()).getKey();
    }
}
