package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import org.catrobat.catroid.common.Constants;

@GwtCompatible(emulated = true, serializable = true)
final class SingletonImmutableList<E> extends ImmutableList<E> {
    final transient E element;

    SingletonImmutableList(E element) {
        this.element = Preconditions.checkNotNull(element);
    }

    public E get(int index) {
        Preconditions.checkElementIndex(index, 1);
        return this.element;
    }

    public UnmodifiableIterator<E> iterator() {
        return Iterators.singletonIterator(this.element);
    }

    public int size() {
        return 1;
    }

    public ImmutableList<E> subList(int fromIndex, int toIndex) {
        Preconditions.checkPositionIndexes(fromIndex, toIndex, 1);
        return fromIndex == toIndex ? ImmutableList.of() : this;
    }

    public String toString() {
        String elementToString = this.element.toString();
        StringBuilder stringBuilder = new StringBuilder(elementToString.length() + 2);
        stringBuilder.append(Constants.REMIX_URL_PREFIX_INDICATOR);
        stringBuilder.append(elementToString);
        stringBuilder.append(Constants.REMIX_URL_SUFIX_INDICATOR);
        return stringBuilder.toString();
    }

    boolean isPartialView() {
        return false;
    }
}
