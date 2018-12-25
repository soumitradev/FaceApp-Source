package com.google.common.collect;

import com.google.common.base.Predicate;
import java.util.Comparator;
import java.util.SortedSet;

class Sets$FilteredSortedSet<E> extends Sets$FilteredSet<E> implements SortedSet<E> {
    Sets$FilteredSortedSet(SortedSet<E> unfiltered, Predicate<? super E> predicate) {
        super(unfiltered, predicate);
    }

    public Comparator<? super E> comparator() {
        return ((SortedSet) this.unfiltered).comparator();
    }

    public SortedSet<E> subSet(E fromElement, E toElement) {
        return new Sets$FilteredSortedSet(((SortedSet) this.unfiltered).subSet(fromElement, toElement), this.predicate);
    }

    public SortedSet<E> headSet(E toElement) {
        return new Sets$FilteredSortedSet(((SortedSet) this.unfiltered).headSet(toElement), this.predicate);
    }

    public SortedSet<E> tailSet(E fromElement) {
        return new Sets$FilteredSortedSet(((SortedSet) this.unfiltered).tailSet(fromElement), this.predicate);
    }

    public E first() {
        return iterator().next();
    }

    public E last() {
        SortedSet<E> sortedUnfiltered = (SortedSet) this.unfiltered;
        while (true) {
            E element = sortedUnfiltered.last();
            if (this.predicate.apply(element)) {
                return element;
            }
            sortedUnfiltered = sortedUnfiltered.headSet(element);
        }
    }
}
