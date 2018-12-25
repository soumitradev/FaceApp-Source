package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.math.IntMath;
import java.math.RoundingMode;
import java.util.AbstractList;
import java.util.List;

class Lists$Partition<T> extends AbstractList<List<T>> {
    final List<T> list;
    final int size;

    Lists$Partition(List<T> list, int size) {
        this.list = list;
        this.size = size;
    }

    public List<T> get(int index) {
        Preconditions.checkElementIndex(index, size());
        int start = this.size * index;
        return this.list.subList(start, Math.min(this.size + start, this.list.size()));
    }

    public int size() {
        return IntMath.divide(this.list.size(), this.size, RoundingMode.CEILING);
    }

    public boolean isEmpty() {
        return this.list.isEmpty();
    }
}
