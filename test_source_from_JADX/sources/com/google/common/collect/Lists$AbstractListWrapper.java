package com.google.common.collect;

import com.google.common.base.Preconditions;
import java.util.AbstractList;
import java.util.Collection;
import java.util.List;

class Lists$AbstractListWrapper<E> extends AbstractList<E> {
    final List<E> backingList;

    Lists$AbstractListWrapper(List<E> backingList) {
        this.backingList = (List) Preconditions.checkNotNull(backingList);
    }

    public void add(int index, E element) {
        this.backingList.add(index, element);
    }

    public boolean addAll(int index, Collection<? extends E> c) {
        return this.backingList.addAll(index, c);
    }

    public E get(int index) {
        return this.backingList.get(index);
    }

    public E remove(int index) {
        return this.backingList.remove(index);
    }

    public E set(int index, E element) {
        return this.backingList.set(index, element);
    }

    public boolean contains(Object o) {
        return this.backingList.contains(o);
    }

    public int size() {
        return this.backingList.size();
    }
}
