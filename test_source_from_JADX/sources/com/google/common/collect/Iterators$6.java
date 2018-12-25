package com.google.common.collect;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

class Iterators$6 extends UnmodifiableIterator<List<T>> {
    final /* synthetic */ Iterator val$iterator;
    final /* synthetic */ boolean val$pad;
    final /* synthetic */ int val$size;

    Iterators$6(Iterator it, int i, boolean z) {
        this.val$iterator = it;
        this.val$size = i;
        this.val$pad = z;
    }

    public boolean hasNext() {
        return this.val$iterator.hasNext();
    }

    public List<T> next() {
        if (hasNext()) {
            Object[] array = new Object[this.val$size];
            int count = 0;
            while (count < this.val$size && this.val$iterator.hasNext()) {
                array[count] = this.val$iterator.next();
                count++;
            }
            for (int i = count; i < this.val$size; i++) {
                array[i] = null;
            }
            List<T> list = Collections.unmodifiableList(Arrays.asList(array));
            if (!this.val$pad) {
                if (count != this.val$size) {
                    return list.subList(0, count);
                }
            }
            return list;
        }
        throw new NoSuchElementException();
    }
}
