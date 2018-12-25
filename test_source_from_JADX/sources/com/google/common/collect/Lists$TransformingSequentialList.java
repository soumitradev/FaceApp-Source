package com.google.common.collect;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.util.AbstractSequentialList;
import java.util.List;
import java.util.ListIterator;

class Lists$TransformingSequentialList<F, T> extends AbstractSequentialList<T> implements Serializable {
    private static final long serialVersionUID = 0;
    final List<F> fromList;
    final Function<? super F, ? extends T> function;

    Lists$TransformingSequentialList(List<F> fromList, Function<? super F, ? extends T> function) {
        this.fromList = (List) Preconditions.checkNotNull(fromList);
        this.function = (Function) Preconditions.checkNotNull(function);
    }

    public void clear() {
        this.fromList.clear();
    }

    public int size() {
        return this.fromList.size();
    }

    public ListIterator<T> listIterator(int index) {
        return new TransformedListIterator<F, T>(this.fromList.listIterator(index)) {
            T transform(F from) {
                return Lists$TransformingSequentialList.this.function.apply(from);
            }
        };
    }
}
