package com.google.common.collect;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import javax.annotation.Nullable;

final class Sets$SubSet<E> extends AbstractSet<E> {
    private final ImmutableMap<E, Integer> inputSet;
    private final int mask;

    /* renamed from: com.google.common.collect.Sets$SubSet$1 */
    class C09601 extends UnmodifiableIterator<E> {
        final ImmutableList<E> elements = Sets$SubSet.this.inputSet.keySet().asList();
        int remainingSetBits = Sets$SubSet.this.mask;

        C09601() {
        }

        public boolean hasNext() {
            return this.remainingSetBits != 0;
        }

        public E next() {
            int index = Integer.numberOfTrailingZeros(this.remainingSetBits);
            if (index == 32) {
                throw new NoSuchElementException();
            }
            this.remainingSetBits &= (1 << index) ^ -1;
            return this.elements.get(index);
        }
    }

    Sets$SubSet(ImmutableMap<E, Integer> inputSet, int mask) {
        this.inputSet = inputSet;
        this.mask = mask;
    }

    public Iterator<E> iterator() {
        return new C09601();
    }

    public int size() {
        return Integer.bitCount(this.mask);
    }

    public boolean contains(@Nullable Object o) {
        Integer index = (Integer) this.inputSet.get(o);
        return (index == null || (this.mask & (1 << index.intValue())) == 0) ? false : true;
    }
}
