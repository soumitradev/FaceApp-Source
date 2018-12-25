package com.google.common.collect;

import com.google.common.annotations.GwtIncompatible;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.SortedSet;

@GwtIncompatible("NavigableSet")
class Sets$DescendingSet<E> extends ForwardingNavigableSet<E> {
    private final NavigableSet<E> forward;

    Sets$DescendingSet(NavigableSet<E> forward) {
        this.forward = forward;
    }

    protected NavigableSet<E> delegate() {
        return this.forward;
    }

    public E lower(E e) {
        return this.forward.higher(e);
    }

    public E floor(E e) {
        return this.forward.ceiling(e);
    }

    public E ceiling(E e) {
        return this.forward.floor(e);
    }

    public E higher(E e) {
        return this.forward.lower(e);
    }

    public E pollFirst() {
        return this.forward.pollLast();
    }

    public E pollLast() {
        return this.forward.pollFirst();
    }

    public NavigableSet<E> descendingSet() {
        return this.forward;
    }

    public Iterator<E> descendingIterator() {
        return this.forward.iterator();
    }

    public NavigableSet<E> subSet(E fromElement, boolean fromInclusive, E toElement, boolean toInclusive) {
        return this.forward.subSet(toElement, toInclusive, fromElement, fromInclusive).descendingSet();
    }

    public NavigableSet<E> headSet(E toElement, boolean inclusive) {
        return this.forward.tailSet(toElement, inclusive).descendingSet();
    }

    public NavigableSet<E> tailSet(E fromElement, boolean inclusive) {
        return this.forward.headSet(fromElement, inclusive).descendingSet();
    }

    public Comparator<? super E> comparator() {
        Comparator<? super E> forwardComparator = this.forward.comparator();
        if (forwardComparator == null) {
            return Ordering.natural().reverse();
        }
        return reverse(forwardComparator);
    }

    private static <T> Ordering<T> reverse(Comparator<T> forward) {
        return Ordering.from(forward).reverse();
    }

    public E first() {
        return this.forward.last();
    }

    public SortedSet<E> headSet(E toElement) {
        return standardHeadSet(toElement);
    }

    public E last() {
        return this.forward.first();
    }

    public SortedSet<E> subSet(E fromElement, E toElement) {
        return standardSubSet(fromElement, toElement);
    }

    public SortedSet<E> tailSet(E fromElement) {
        return standardTailSet(fromElement);
    }

    public Iterator<E> iterator() {
        return this.forward.descendingIterator();
    }

    public Object[] toArray() {
        return standardToArray();
    }

    public <T> T[] toArray(T[] array) {
        return standardToArray(array);
    }

    public String toString() {
        return standardToString();
    }
}
