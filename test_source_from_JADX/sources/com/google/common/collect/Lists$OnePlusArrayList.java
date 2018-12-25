package com.google.common.collect;

import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.util.AbstractList;
import java.util.RandomAccess;
import javax.annotation.Nullable;

class Lists$OnePlusArrayList<E> extends AbstractList<E> implements Serializable, RandomAccess {
    private static final long serialVersionUID = 0;
    final E first;
    final E[] rest;

    Lists$OnePlusArrayList(@Nullable E first, E[] rest) {
        this.first = first;
        this.rest = (Object[]) Preconditions.checkNotNull(rest);
    }

    public int size() {
        return this.rest.length + 1;
    }

    public E get(int index) {
        Preconditions.checkElementIndex(index, size());
        return index == 0 ? this.first : this.rest[index - 1];
    }
}
