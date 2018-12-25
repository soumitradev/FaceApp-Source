package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import javax.annotation.Nullable;

class Collections2$FilteredCollection<E> extends AbstractCollection<E> {
    final Predicate<? super E> predicate;
    final Collection<E> unfiltered;

    Collections2$FilteredCollection(Collection<E> unfiltered, Predicate<? super E> predicate) {
        this.unfiltered = unfiltered;
        this.predicate = predicate;
    }

    Collections2$FilteredCollection<E> createCombined(Predicate<? super E> newPredicate) {
        return new Collections2$FilteredCollection(this.unfiltered, Predicates.and(this.predicate, newPredicate));
    }

    public boolean add(E element) {
        Preconditions.checkArgument(this.predicate.apply(element));
        return this.unfiltered.add(element);
    }

    public boolean addAll(Collection<? extends E> collection) {
        for (E element : collection) {
            Preconditions.checkArgument(this.predicate.apply(element));
        }
        return this.unfiltered.addAll(collection);
    }

    public void clear() {
        Iterables.removeIf(this.unfiltered, this.predicate);
    }

    public boolean contains(@Nullable Object element) {
        if (!Collections2.safeContains(this.unfiltered, element)) {
            return false;
        }
        return this.predicate.apply(element);
    }

    public boolean containsAll(Collection<?> collection) {
        return Collections2.containsAllImpl(this, collection);
    }

    public boolean isEmpty() {
        return Iterables.any(this.unfiltered, this.predicate) ^ 1;
    }

    public Iterator<E> iterator() {
        return Iterators.filter(this.unfiltered.iterator(), this.predicate);
    }

    public boolean remove(Object element) {
        return contains(element) && this.unfiltered.remove(element);
    }

    public boolean removeAll(Collection<?> collection) {
        return Iterables.removeIf(this.unfiltered, Predicates.and(this.predicate, Predicates.in(collection)));
    }

    public boolean retainAll(Collection<?> collection) {
        return Iterables.removeIf(this.unfiltered, Predicates.and(this.predicate, Predicates.not(Predicates.in(collection))));
    }

    public int size() {
        return Iterators.size(iterator());
    }

    public Object[] toArray() {
        return Lists.newArrayList(iterator()).toArray();
    }

    public <T> T[] toArray(T[] array) {
        return Lists.newArrayList(iterator()).toArray(array);
    }
}
