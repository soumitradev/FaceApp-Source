package com.google.common.collect;

import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.util.AbstractList;
import java.util.RandomAccess;
import javax.annotation.Nullable;

class Lists$TwoPlusArrayList<E> extends AbstractList<E> implements Serializable, RandomAccess {
    private static final long serialVersionUID = 0;
    final E first;
    final E[] rest;
    final E second;

    Lists$TwoPlusArrayList(@Nullable E first, @Nullable E second, E[] rest) {
        this.first = first;
        this.second = second;
        this.rest = (Object[]) Preconditions.checkNotNull(rest);
    }

    public int size() {
        return this.rest.length + 2;
    }

    public E get(int index) {
        switch (index) {
            case 0:
                return this.first;
            case 1:
                return this.second;
            default:
                Preconditions.checkElementIndex(index, size());
                return this.rest[index - 2];
        }
    }
}
