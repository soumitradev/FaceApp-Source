package com.google.common.collect;

import java.util.List;
import java.util.ListIterator;

class Lists$1 extends Lists$RandomAccessListWrapper<E> {
    private static final long serialVersionUID = 0;

    Lists$1(List x0) {
        super(x0);
    }

    public ListIterator<E> listIterator(int index) {
        return this.backingList.listIterator(index);
    }
}
