package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.SortedLists.KeyAbsentBehavior;
import com.google.common.collect.SortedLists.KeyPresentBehavior;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import javax.annotation.Nullable;

@GwtCompatible(emulated = true, serializable = true)
final class RegularImmutableSortedSet<E> extends ImmutableSortedSet<E> {
    private final transient ImmutableList<E> elements;

    RegularImmutableSortedSet(ImmutableList<E> elements, Comparator<? super E> comparator) {
        super(comparator);
        this.elements = elements;
    }

    public UnmodifiableIterator<E> iterator() {
        return this.elements.iterator();
    }

    @GwtIncompatible("NavigableSet")
    public UnmodifiableIterator<E> descendingIterator() {
        return this.elements.reverse().iterator();
    }

    public int size() {
        return this.elements.size();
    }

    public boolean contains(@Nullable Object o) {
        boolean z = false;
        if (o != null) {
            try {
                if (unsafeBinarySearch(o) >= 0) {
                    z = true;
                }
            } catch (ClassCastException e) {
                return false;
            }
        }
        return z;
    }

    public boolean containsAll(Collection<?> targets) {
        if (targets instanceof Multiset) {
            targets = ((Multiset) targets).elementSet();
        }
        if (SortedIterables.hasSameComparator(comparator(), targets)) {
            if (targets.size() > 1) {
                PeekingIterator<E> thisIterator = Iterators.peekingIterator(iterator());
                Iterator<?> thatIterator = targets.iterator();
                Object target = thatIterator.next();
                while (thisIterator.hasNext()) {
                    try {
                        int cmp = unsafeCompare(thisIterator.peek(), target);
                        if (cmp < 0) {
                            thisIterator.next();
                        } else if (cmp == 0) {
                            if (!thatIterator.hasNext()) {
                                return true;
                            }
                            target = thatIterator.next();
                        } else if (cmp > 0) {
                            return false;
                        }
                    } catch (NullPointerException e) {
                        return false;
                    } catch (ClassCastException e2) {
                        return false;
                    }
                }
                return false;
            }
        }
        return super.containsAll(targets);
    }

    private int unsafeBinarySearch(Object key) throws ClassCastException {
        return Collections.binarySearch(this.elements, key, unsafeComparator());
    }

    boolean isPartialView() {
        return this.elements.isPartialView();
    }

    int copyIntoArray(Object[] dst, int offset) {
        return this.elements.copyIntoArray(dst, offset);
    }

    public boolean equals(@Nullable Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof Set)) {
            return false;
        }
        Set<?> that = (Set) object;
        if (size() != that.size()) {
            return false;
        }
        if (isEmpty()) {
            return true;
        }
        if (!SortedIterables.hasSameComparator(this.comparator, that)) {
            return containsAll(that);
        }
        Iterator<?> otherIterator = that.iterator();
        try {
            Iterator<E> iterator = iterator();
            while (iterator.hasNext()) {
                Object element = iterator.next();
                Object otherElement = otherIterator.next();
                if (otherElement != null) {
                    if (unsafeCompare(element, otherElement) != 0) {
                    }
                }
                return false;
            }
            return true;
        } catch (ClassCastException e) {
            return false;
        } catch (NoSuchElementException e2) {
            return false;
        }
    }

    public E first() {
        if (!isEmpty()) {
            return this.elements.get(0);
        }
        throw new NoSuchElementException();
    }

    public E last() {
        if (!isEmpty()) {
            return this.elements.get(size() - 1);
        }
        throw new NoSuchElementException();
    }

    public E lower(E element) {
        int index = headIndex(element, false) - 1;
        return index == -1 ? null : this.elements.get(index);
    }

    public E floor(E element) {
        int index = headIndex(element, true) - 1;
        return index == -1 ? null : this.elements.get(index);
    }

    public E ceiling(E element) {
        int index = tailIndex(element, 1);
        return index == size() ? null : this.elements.get(index);
    }

    public E higher(E element) {
        int index = tailIndex(element, 0);
        return index == size() ? null : this.elements.get(index);
    }

    ImmutableSortedSet<E> headSetImpl(E toElement, boolean inclusive) {
        return getSubSet(0, headIndex(toElement, inclusive));
    }

    int headIndex(E toElement, boolean inclusive) {
        return SortedLists.binarySearch(this.elements, Preconditions.checkNotNull(toElement), comparator(), inclusive ? KeyPresentBehavior.FIRST_AFTER : KeyPresentBehavior.FIRST_PRESENT, KeyAbsentBehavior.NEXT_HIGHER);
    }

    ImmutableSortedSet<E> subSetImpl(E fromElement, boolean fromInclusive, E toElement, boolean toInclusive) {
        return tailSetImpl(fromElement, fromInclusive).headSetImpl(toElement, toInclusive);
    }

    ImmutableSortedSet<E> tailSetImpl(E fromElement, boolean inclusive) {
        return getSubSet(tailIndex(fromElement, inclusive), size());
    }

    int tailIndex(E fromElement, boolean inclusive) {
        return SortedLists.binarySearch(this.elements, Preconditions.checkNotNull(fromElement), comparator(), inclusive ? KeyPresentBehavior.FIRST_PRESENT : KeyPresentBehavior.FIRST_AFTER, KeyAbsentBehavior.NEXT_HIGHER);
    }

    Comparator<Object> unsafeComparator() {
        return this.comparator;
    }

    RegularImmutableSortedSet<E> getSubSet(int newFromIndex, int newToIndex) {
        if (newFromIndex == 0 && newToIndex == size()) {
            return this;
        }
        if (newFromIndex < newToIndex) {
            return new RegularImmutableSortedSet(this.elements.subList(newFromIndex, newToIndex), this.comparator);
        }
        return emptySet(this.comparator);
    }

    int indexOf(@Nullable Object target) {
        int i = -1;
        if (target == null) {
            return -1;
        }
        try {
            int position = SortedLists.binarySearch(this.elements, target, unsafeComparator(), KeyPresentBehavior.ANY_PRESENT, KeyAbsentBehavior.INVERTED_INSERTION_INDEX);
            if (position >= 0) {
                i = position;
            }
            return i;
        } catch (ClassCastException e) {
            return -1;
        }
    }

    ImmutableList<E> createAsList() {
        return size() <= 1 ? this.elements : new ImmutableSortedAsList(this, this.elements);
    }

    ImmutableSortedSet<E> createDescendingSet() {
        Ordering<E> reversedOrder = Ordering.from(this.comparator).reverse();
        return isEmpty() ? emptySet(reversedOrder) : new RegularImmutableSortedSet(this.elements.reverse(), reversedOrder);
    }
}
