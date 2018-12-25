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
class Booleans$BooleanArrayAsList extends AbstractList<Boolean> implements RandomAccess, Serializable {
    private static final long serialVersionUID = 0;
    final boolean[] array;
    final int end;
    final int start;

    Booleans$BooleanArrayAsList(boolean[] array) {
        this(array, 0, array.length);
    }

    Booleans$BooleanArrayAsList(boolean[] array, int start, int end) {
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

    public Boolean get(int index) {
        Preconditions.checkElementIndex(index, size());
        return Boolean.valueOf(this.array[this.start + index]);
    }

    public boolean contains(Object target) {
        return (target instanceof Boolean) && Booleans.access$000(this.array, ((Boolean) target).booleanValue(), this.start, this.end) != -1;
    }

    public int indexOf(Object target) {
        if (target instanceof Boolean) {
            int i = Booleans.access$000(this.array, ((Boolean) target).booleanValue(), this.start, this.end);
            if (i >= 0) {
                return i - this.start;
            }
        }
        return -1;
    }

    public int lastIndexOf(Object target) {
        if (target instanceof Boolean) {
            int i = Booleans.access$100(this.array, ((Boolean) target).booleanValue(), this.start, this.end);
            if (i >= 0) {
                return i - this.start;
            }
        }
        return -1;
    }

    public Boolean set(int index, Boolean element) {
        Preconditions.checkElementIndex(index, size());
        boolean oldValue = this.array[this.start + index];
        this.array[this.start + index] = ((Boolean) Preconditions.checkNotNull(element)).booleanValue();
        return Boolean.valueOf(oldValue);
    }

    public List<Boolean> subList(int fromIndex, int toIndex) {
        Preconditions.checkPositionIndexes(fromIndex, toIndex, size());
        if (fromIndex == toIndex) {
            return Collections.emptyList();
        }
        return new Booleans$BooleanArrayAsList(this.array, this.start + fromIndex, this.start + toIndex);
    }

    public boolean equals(@Nullable Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof Booleans$BooleanArrayAsList)) {
            return super.equals(object);
        }
        Booleans$BooleanArrayAsList that = (Booleans$BooleanArrayAsList) object;
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
            result = (result * 31) + Booleans.hashCode(this.array[i]);
        }
        return result;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder(size() * 7);
        builder.append(this.array[this.start] ? "[true" : "[false");
        int i = this.start;
        while (true) {
            i++;
            if (i < this.end) {
                builder.append(this.array[i] ? ", true" : ", false");
            } else {
                builder.append(Constants.REMIX_URL_SUFIX_INDICATOR);
                return builder.toString();
            }
        }
    }

    boolean[] toBooleanArray() {
        int size = size();
        boolean[] result = new boolean[size];
        System.arraycopy(this.array, this.start, result, 0, size);
        return result;
    }
}
