package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import org.catrobat.catroid.common.Constants;

@GwtCompatible(emulated = true, serializable = true)
final class SingletonImmutableSet<E> extends ImmutableSet<E> {
    private transient int cachedHashCode;
    final transient E element;

    SingletonImmutableSet(E element) {
        this.element = Preconditions.checkNotNull(element);
    }

    SingletonImmutableSet(E element, int hashCode) {
        this.element = element;
        this.cachedHashCode = hashCode;
    }

    public int size() {
        return 1;
    }

    public boolean contains(Object target) {
        return this.element.equals(target);
    }

    public UnmodifiableIterator<E> iterator() {
        return Iterators.singletonIterator(this.element);
    }

    boolean isPartialView() {
        return false;
    }

    int copyIntoArray(Object[] dst, int offset) {
        dst[offset] = this.element;
        return offset + 1;
    }

    public final int hashCode() {
        int code = this.cachedHashCode;
        if (code != 0) {
            return code;
        }
        int hashCode = this.element.hashCode();
        code = hashCode;
        this.cachedHashCode = hashCode;
        return code;
    }

    boolean isHashCodeFast() {
        return this.cachedHashCode != 0;
    }

    public String toString() {
        String elementToString = this.element.toString();
        StringBuilder stringBuilder = new StringBuilder(elementToString.length() + 2);
        stringBuilder.append(Constants.REMIX_URL_PREFIX_INDICATOR);
        stringBuilder.append(elementToString);
        stringBuilder.append(Constants.REMIX_URL_SUFIX_INDICATOR);
        return stringBuilder.toString();
    }
}
