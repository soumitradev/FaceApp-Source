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
class Longs$LongArrayAsList extends AbstractList<Long> implements RandomAccess, Serializable {
    private static final long serialVersionUID = 0;
    final long[] array;
    final int end;
    final int start;

    Longs$LongArrayAsList(long[] array) {
        this(array, 0, array.length);
    }

    Longs$LongArrayAsList(long[] array, int start, int end) {
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

    public Long get(int index) {
        Preconditions.checkElementIndex(index, size());
        return Long.valueOf(this.array[this.start + index]);
    }

    public boolean contains(Object target) {
        return (target instanceof Long) && Longs.access$000(this.array, ((Long) target).longValue(), this.start, this.end) != -1;
    }

    public int indexOf(Object target) {
        if (target instanceof Long) {
            int i = Longs.access$000(this.array, ((Long) target).longValue(), this.start, this.end);
            if (i >= 0) {
                return i - this.start;
            }
        }
        return -1;
    }

    public int lastIndexOf(Object target) {
        if (target instanceof Long) {
            int i = Longs.access$100(this.array, ((Long) target).longValue(), this.start, this.end);
            if (i >= 0) {
                return i - this.start;
            }
        }
        return -1;
    }

    public Long set(int index, Long element) {
        Preconditions.checkElementIndex(index, size());
        long oldValue = this.array[this.start + index];
        this.array[this.start + index] = ((Long) Preconditions.checkNotNull(element)).longValue();
        return Long.valueOf(oldValue);
    }

    public List<Long> subList(int fromIndex, int toIndex) {
        Preconditions.checkPositionIndexes(fromIndex, toIndex, size());
        if (fromIndex == toIndex) {
            return Collections.emptyList();
        }
        return new Longs$LongArrayAsList(this.array, this.start + fromIndex, this.start + toIndex);
    }

    public boolean equals(@Nullable Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof Longs$LongArrayAsList)) {
            return super.equals(object);
        }
        Longs$LongArrayAsList that = (Longs$LongArrayAsList) object;
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
            result = (result * 31) + Longs.hashCode(this.array[i]);
        }
        return result;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder(size() * 10);
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

    long[] toLongArray() {
        int size = size();
        long[] result = new long[size];
        System.arraycopy(this.array, this.start, result, 0, size);
        return result;
    }
}
