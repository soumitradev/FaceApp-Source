package com.google.common.collect;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.util.AbstractList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;

class Lists$TransformingRandomAccessList<F, T> extends AbstractList<T> implements RandomAccess, Serializable {
    private static final long serialVersionUID = 0;
    final List<F> fromList;
    final Function<? super F, ? extends T> function;

    Lists$TransformingRandomAccessList(List<F> fromList, Function<? super F, ? extends T> function) {
        this.fromList = (List) Preconditions.checkNotNull(fromList);
        this.function = (Function) Preconditions.checkNotNull(function);
    }

    public void clear() {
        this.fromList.clear();
    }

    public T get(int index) {
        return this.function.apply(this.fromList.get(index));
    }

    public Iterator<T> iterator() {
        return listIterator();
    }

    public ListIterator<T> listIterator(int index) {
        return new TransformedListIterator<F, T>(this.fromList.listIterator(index)) {
            T transform(F from) {
                return Lists$TransformingRandomAccessList.this.function.apply(from);
            }
        };
    }

    public boolean isEmpty() {
        return this.fromList.isEmpty();
    }

    public T remove(int index) {
        return this.function.apply(this.fromList.remove(index));
    }

    public int size() {
        return this.fromList.size();
    }
}
