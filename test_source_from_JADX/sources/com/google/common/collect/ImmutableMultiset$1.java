package com.google.common.collect;

import com.google.common.collect.Multiset.Entry;
import java.util.Iterator;

class ImmutableMultiset$1 extends UnmodifiableIterator<E> {
    E element;
    int remaining;
    final /* synthetic */ ImmutableMultiset this$0;
    final /* synthetic */ Iterator val$entryIterator;

    ImmutableMultiset$1(ImmutableMultiset immutableMultiset, Iterator it) {
        this.this$0 = immutableMultiset;
        this.val$entryIterator = it;
    }

    public boolean hasNext() {
        if (this.remaining <= 0) {
            if (!this.val$entryIterator.hasNext()) {
                return false;
            }
        }
        return true;
    }

    public E next() {
        if (this.remaining <= 0) {
            Entry<E> entry = (Entry) this.val$entryIterator.next();
            this.element = entry.getElement();
            this.remaining = entry.getCount();
        }
        this.remaining--;
        return this.element;
    }
}
