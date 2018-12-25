package com.google.common.collect;

import com.google.common.base.Predicate;
import java.util.Set;
import javax.annotation.Nullable;

class Sets$FilteredSet<E> extends Collections2$FilteredCollection<E> implements Set<E> {
    Sets$FilteredSet(Set<E> unfiltered, Predicate<? super E> predicate) {
        super(unfiltered, predicate);
    }

    public boolean equals(@Nullable Object object) {
        return Sets.equalsImpl(this, object);
    }

    public int hashCode() {
        return Sets.hashCodeImpl(this);
    }
}
