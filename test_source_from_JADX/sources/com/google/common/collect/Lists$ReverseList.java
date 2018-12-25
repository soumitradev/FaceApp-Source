package com.google.common.collect;

import com.google.common.base.Preconditions;
import java.util.AbstractList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import javax.annotation.Nullable;

class Lists$ReverseList<T> extends AbstractList<T> {
    private final List<T> forwardList;

    Lists$ReverseList(List<T> forwardList) {
        this.forwardList = (List) Preconditions.checkNotNull(forwardList);
    }

    List<T> getForwardList() {
        return this.forwardList;
    }

    private int reverseIndex(int index) {
        int size = size();
        Preconditions.checkElementIndex(index, size);
        return (size - 1) - index;
    }

    private int reversePosition(int index) {
        int size = size();
        Preconditions.checkPositionIndex(index, size);
        return size - index;
    }

    public void add(int index, @Nullable T element) {
        this.forwardList.add(reversePosition(index), element);
    }

    public void clear() {
        this.forwardList.clear();
    }

    public T remove(int index) {
        return this.forwardList.remove(reverseIndex(index));
    }

    protected void removeRange(int fromIndex, int toIndex) {
        subList(fromIndex, toIndex).clear();
    }

    public T set(int index, @Nullable T element) {
        return this.forwardList.set(reverseIndex(index), element);
    }

    public T get(int index) {
        return this.forwardList.get(reverseIndex(index));
    }

    public int size() {
        return this.forwardList.size();
    }

    public List<T> subList(int fromIndex, int toIndex) {
        Preconditions.checkPositionIndexes(fromIndex, toIndex, size());
        return Lists.reverse(this.forwardList.subList(reversePosition(toIndex), reversePosition(fromIndex)));
    }

    public Iterator<T> iterator() {
        return listIterator();
    }

    public ListIterator<T> listIterator(int index) {
        final ListIterator<T> forwardIterator = this.forwardList.listIterator(reversePosition(index));
        return new ListIterator<T>() {
            boolean canRemoveOrSet;

            public void add(T e) {
                forwardIterator.add(e);
                forwardIterator.previous();
                this.canRemoveOrSet = false;
            }

            public boolean hasNext() {
                return forwardIterator.hasPrevious();
            }

            public boolean hasPrevious() {
                return forwardIterator.hasNext();
            }

            public T next() {
                if (hasNext()) {
                    this.canRemoveOrSet = true;
                    return forwardIterator.previous();
                }
                throw new NoSuchElementException();
            }

            public int nextIndex() {
                return Lists$ReverseList.this.reversePosition(forwardIterator.nextIndex());
            }

            public T previous() {
                if (hasPrevious()) {
                    this.canRemoveOrSet = true;
                    return forwardIterator.next();
                }
                throw new NoSuchElementException();
            }

            public int previousIndex() {
                return nextIndex() - 1;
            }

            public void remove() {
                CollectPreconditions.checkRemove(this.canRemoveOrSet);
                forwardIterator.remove();
                this.canRemoveOrSet = false;
            }

            public void set(T e) {
                Preconditions.checkState(this.canRemoveOrSet);
                forwardIterator.set(e);
            }
        };
    }
}
