package com.google.common.collect;

import com.google.common.base.Preconditions;
import java.util.AbstractSet;
import java.util.Collection;

abstract class Sets$ImprovedAbstractSet<E> extends AbstractSet<E> {
    Sets$ImprovedAbstractSet() {
    }

    public boolean removeAll(Collection<?> c) {
        return Sets.removeAllImpl(this, c);
    }

    public boolean retainAll(Collection<?> c) {
        return super.retainAll((Collection) Preconditions.checkNotNull(c));
    }
}
