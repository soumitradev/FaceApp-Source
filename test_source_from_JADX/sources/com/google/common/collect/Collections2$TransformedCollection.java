package com.google.common.collect;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;

class Collections2$TransformedCollection<F, T> extends AbstractCollection<T> {
    final Collection<F> fromCollection;
    final Function<? super F, ? extends T> function;

    Collections2$TransformedCollection(Collection<F> fromCollection, Function<? super F, ? extends T> function) {
        this.fromCollection = (Collection) Preconditions.checkNotNull(fromCollection);
        this.function = (Function) Preconditions.checkNotNull(function);
    }

    public void clear() {
        this.fromCollection.clear();
    }

    public boolean isEmpty() {
        return this.fromCollection.isEmpty();
    }

    public Iterator<T> iterator() {
        return Iterators.transform(this.fromCollection.iterator(), this.function);
    }

    public int size() {
        return this.fromCollection.size();
    }
}
