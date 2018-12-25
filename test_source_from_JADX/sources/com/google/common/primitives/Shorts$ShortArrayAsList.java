package com.google.common.primitives;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.util.AbstractList;
import java.util.Collections;
import java.util.List;
import java.util.RandomAccess;
import org.catrobat.catroid.common.Constants;

@GwtCompatible
class Shorts$ShortArrayAsList extends AbstractList<Short> implements RandomAccess, Serializable {
    private static final long serialVersionUID = 0;
    final short[] array;
    final int end;
    final int start;

    Shorts$ShortArrayAsList(short[] array) {
        this(array, 0, array.length);
    }

    Shorts$ShortArrayAsList(short[] array, int start, int end) {
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

    public Short get(int index) {
        Preconditions.checkElementIndex(index, size());
        return Short.valueOf(this.array[this.start + index]);
    }

    public boolean contains(Object target) {
        return (target instanceof Short) && Shorts.access$000(this.array, ((Short) target).shortValue(), this.start, this.end) != -1;
    }

    public int indexOf(Object target) {
        if (target instanceof Short) {
            int i = Shorts.access$000(this.array, ((Short) target).shortValue(), this.start, this.end);
            if (i >= 0) {
                return i - this.start;
            }
        }
        return -1;
    }

    public int lastIndexOf(Object target) {
        if (target instanceof Short) {
            int i = Shorts.access$100(this.array, ((Short) target).shortValue(), this.start, this.end);
            if (i >= 0) {
                return i - this.start;
            }
        }
        return -1;
    }

    public Short set(int index, Short element) {
        Preconditions.checkElementIndex(index, size());
        short oldValue = this.array[this.start + index];
        this.array[this.start + index] = ((Short) Preconditions.checkNotNull(element)).shortValue();
        return Short.valueOf(oldValue);
    }

    public List<Short> subList(int fromIndex, int toIndex) {
        Preconditions.checkPositionIndexes(fromIndex, toIndex, size());
        if (fromIndex == toIndex) {
            return Collections.emptyList();
        }
        return new Shorts$ShortArrayAsList(this.array, this.start + fromIndex, this.start + toIndex);
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof Shorts$ShortArrayAsList)) {
            return super.equals(object);
        }
        Shorts$ShortArrayAsList that = (Shorts$ShortArrayAsList) object;
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
            result = (result * 31) + Shorts.hashCode(this.array[i]);
        }
        return result;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder(size() * 6);
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

    short[] toShortArray() {
        int size = size();
        short[] result = new short[size];
        System.arraycopy(this.array, this.start, result, 0, size);
        return result;
    }
}
