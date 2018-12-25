package com.google.common.collect;

import java.util.Iterator;

class Iterables$1 extends FluentIterable<T> {
    final /* synthetic */ Iterable val$iterable;

    Iterables$1(Iterable iterable) {
        this.val$iterable = iterable;
    }

    public Iterator<T> iterator() {
        return Iterators.cycle(this.val$iterable);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.val$iterable.toString());
        stringBuilder.append(" (cycled)");
        return stringBuilder.toString();
    }
}
