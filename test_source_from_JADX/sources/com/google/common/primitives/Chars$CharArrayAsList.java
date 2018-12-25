package com.google.common.primitives;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.util.AbstractList;
import java.util.Collections;
import java.util.List;
import java.util.RandomAccess;
import javax.annotation.Nullable;
import org.catrobat.catroid.common.Constants;

@GwtCompatible
class Chars$CharArrayAsList extends AbstractList<Character> implements RandomAccess, Serializable {
    private static final long serialVersionUID = 0;
    final char[] array;
    final int end;
    final int start;

    Chars$CharArrayAsList(char[] array) {
        this(array, 0, array.length);
    }

    Chars$CharArrayAsList(char[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    public int size() {
        return this.end - this.start;
    }

    public boolean isEmpty() {
        return false;
    }

    public Character get(int index) {
        Preconditions.checkElementIndex(index, size());
        return Character.valueOf(this.array[this.start + index]);
    }

    public boolean contains(Object target) {
        return (target instanceof Character) && Chars.access$000(this.array, ((Character) target).charValue(), this.start, this.end) != -1;
    }

    public int indexOf(Object target) {
        if (target instanceof Character) {
            int i = Chars.access$000(this.array, ((Character) target).charValue(), this.start, this.end);
            if (i >= 0) {
                return i - this.start;
            }
        }
        return -1;
    }

    public int lastIndexOf(Object target) {
        if (target instanceof Character) {
            int i = Chars.access$100(this.array, ((Character) target).charValue(), this.start, this.end);
            if (i >= 0) {
                return i - this.start;
            }
        }
        return -1;
    }

    public Character set(int index, Character element) {
        Preconditions.checkElementIndex(index, size());
        char oldValue = this.array[this.start + index];
        this.array[this.start + index] = ((Character) Preconditions.checkNotNull(element)).charValue();
        return Character.valueOf(oldValue);
    }

    public List<Character> subList(int fromIndex, int toIndex) {
        Preconditions.checkPositionIndexes(fromIndex, toIndex, size());
        if (fromIndex == toIndex) {
            return Collections.emptyList();
        }
        return new Chars$CharArrayAsList(this.array, this.start + fromIndex, this.start + toIndex);
    }

    public boolean equals(@Nullable Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof Chars$CharArrayAsList)) {
            return super.equals(object);
        }
        Chars$CharArrayAsList that = (Chars$CharArrayAsList) object;
        int size = size();
        if (that.size() != size) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (this.array[this.start + i] != that.array[that.start + i]) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int result = 1;
        for (int i = this.start; i < this.end; i++) {
            result = (result * 31) + Chars.hashCode(this.array[i]);
        }
        return result;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder(size() * 3);
        builder.append(Constants.REMIX_URL_PREFIX_INDICATOR);
        builder.append(this.array[this.start]);
        int i = this.start;
        while (true) {
            i++;
            if (i < this.end) {
                builder.append(", ");
                builder.append(this.array[i]);
            } else {
                builder.append(Constants.REMIX_URL_SUFIX_INDICATOR);
                return builder.toString();
            }
        }
    }

    char[] toCharArray() {
        int size = size();
        char[] result = new char[size];
        System.arraycopy(this.array, this.start, result, 0, size);
        return result;
    }
}
