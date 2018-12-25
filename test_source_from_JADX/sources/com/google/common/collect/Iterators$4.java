package com.google.common.collect;

import java.util.Iterator;
import java.util.NoSuchElementException;

class Iterators$4 implements Iterator<T> {
    Iterator<T> iterator = Iterators.emptyModifiableIterator();
    final /* synthetic */ Iterable val$iterable;

    Iterators$4(Iterable iterable) {
        this.val$iterable = iterable;
    }

    public boolean hasNext() {
        if (!this.iterator.hasNext()) {
            if (!this.val$iterable.iterator().hasNext()) {
                return false;
            }
        }
        return true;
    }

    public T next() {
        if (!this.iterator.hasNext()) {
            this.iterator = this.val$iterable.iterator();
            if (!this.iterator.hasNext()) {
                throw new NoSuchElementException();
            }
        }
        return this.iterator.next();
    }

    public void remove() {
        this.iterator.remove();
    }
}
