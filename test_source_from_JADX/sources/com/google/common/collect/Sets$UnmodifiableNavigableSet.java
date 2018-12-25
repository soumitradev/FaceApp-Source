package com.google.common.collect;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.SortedSet;

@GwtIncompatible("NavigableSet")
final class Sets$UnmodifiableNavigableSet<E> extends ForwardingSortedSet<E> implements NavigableSet<E>, Serializable {
    private static final long serialVersionUID = 0;
    private final NavigableSet<E> delegate;
    private transient Sets$UnmodifiableNavigableSet<E> descendingSet;

    Sets$UnmodifiableNavigableSet(NavigableSet<E> delegate) {
        this.delegate = (NavigableSet) Preconditions.checkNotNull(delegate);
    }

    protected SortedSet<E> delegate() {
        return Collections.unmodifiableSortedSet(this.delegate);
    }

    public E lower(E e) {
        return this.delegate.lower(e);
    }

    public E floor(E e) {
        return this.delegate.floor(e);
    }

    public E ceiling(E e) {
        return this.delegate.ceiling(e);
    }

    public E higher(E e) {
        return this.delegate.higher(e);
    }

    public E pollFirst() {
        throw new UnsupportedOperationException();
    }

    public E pollLast() {
        throw new UnsupportedOperationException();
    }

    public NavigableSet<E> descendingSet() {
        Sets$UnmodifiableNavigableSet<E> result = this.descendingSet;
        if (result != null) {
            return result;
        }
        Sets$UnmodifiableNavigableSet<E> sets$UnmodifiableNavigableSet = new Sets$UnmodifiableNavigableSet(this.delegate.descendingSet());
        this.descendingSet = sets$UnmodifiableNavigableSet;
        result = sets$UnmodifiableNavigableSet;
        result.descendingSet = this;
        return result;
    }

    public Iterator<E> descendingIterator() {
        return Iterators.unmodifiableIterator(this.delegate.descendingIterator());
    }

    public NavigableSet<E> subSet(E fromElement, boolean fromInclusive, E toElement, boolean toInclusive) {
        return Sets.unmodifiableNavigableSet(this.delegate.subSet(fromElement, fromInclusive, toElement, toInclusive));
    }

    public NavigableSet<E> headSet(E toElement, boolean inclusive) {
        return Sets.unmodifiableNavigableSet(this.delegate.headSet(toElement, inclusive));
    }

    public NavigableSet<E> tailSet(E fromElement, boolean inclusive) {
        return Sets.unmodifiableNavigableSet(this.delegate.tailSet(fromElement, inclusive));
    }
}
