package com.google.common.primitives;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Converter;
import com.google.common.base.Preconditions;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.annotation.CheckReturnValue;
import kotlin.jvm.internal.ShortCompanionObject;

@GwtCompatible(emulated = true)
@CheckReturnValue
public final class Shorts {
    public static final int BYTES = 2;
    public static final short MAX_POWER_OF_TWO = (short) 16384;

    private Shorts() {
    }

    public static int hashCode(short value) {
        return value;
    }

    public static short checkedCast(long value) {
        short result = (short) ((int) value);
        if (((long) result) == value) {
            return result;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Out of range: ");
        stringBuilder.append(value);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public static short saturatedCast(long value) {
        if (value > 32767) {
            return ShortCompanionObject.MAX_VALUE;
        }
        if (value < -32768) {
            return ShortCompanionObject.MIN_VALUE;
        }
        return (short) ((int) value);
    }

    public static int compare(short a, short b) {
        return a - b;
    }

    public static boolean contains(short[] array, short target) {
        for (short value : array) {
            if (value == target) {
                return true;
            }
        }
        return false;
    }

    public static int indexOf(short[] array, short target) {
        return indexOf(array, target, 0, array.length);
    }

    private static int indexOf(short[] array, short target, int start, int end) {
        for (int i = start; i < end; i++) {
            if (array[i] == target) {
                return i;
            }
        }
        return -1;
    }

    public static int indexOf(short[] array, short[] target) {
        Preconditions.checkNotNull(array, "array");
        Preconditions.checkNotNull(target, "target");
        if (target.length == 0) {
            return 0;
        }
        int i = 0;
        while (i < (array.length - target.length) + 1) {
            int j = 0;
            while (j < target.length) {
                if (array[i + j] != target[j]) {
                    i++;
                } else {
                    j++;
                }
            }
            return i;
        }
        return -1;
    }

    public static int lastIndexOf(short[] array, short target) {
        return lastIndexOf(array, target, 0, array.length);
    }

    private static int lastIndexOf(short[] array, short target, int start, int end) {
        for (int i = end - 1; i >= start; i--) {
            if (array[i] == target) {
                return i;
            }
        }
        return -1;
    }

    public static short min(short... array) {
        int i = 1;
        Preconditions.checkArgument(array.length > 0);
        short min = array[0];
        while (true) {
            int i2 = i;
            if (i2 >= array.length) {
                return min;
            }
            if (array[i2] < min) {
                min = array[i2];
            }
            i = i2 + 1;
        }
    }

    public static short max(short... array) {
        int i = 1;
        Preconditions.checkArgument(array.length > 0);
        short max = array[0];
        while (true) {
            int i2 = i;
            if (i2 >= array.length) {
                return max;
            }
            if (array[i2] > max) {
                max = array[i2];
            }
            i = i2 + 1;
        }
    }

    public static short[] concat(short[]... arrays) {
        int length = 0;
        for (short[] array : arrays) {
            length += array.length;
        }
        short[] result = new short[length];
        int pos = 0;
        for (short[] array2 : arrays) {
            System.arraycopy(array2, 0, result, pos, array2.length);
            pos += array2.length;
        }
        return result;
    }

    @GwtIncompatible("doesn't work")
    public static byte[] toByteArray(short value) {
        return new byte[]{(byte) (value >> 8), (byte) value};
    }

    @GwtIncompatible("doesn't work")
    public static short fromByteArray(byte[] bytes) {
        Preconditions.checkArgument(bytes.length >= 2, "array too small: %s < %s", new Object[]{Integer.valueOf(bytes.length), Integer.valueOf(2)});
        return fromBytes(bytes[0], bytes[1]);
    }

    @GwtIncompatible("doesn't work")
    public static short fromBytes(byte b1, byte b2) {
        return (short) ((b1 << 8) | (b2 & 255));
    }

    @Beta
    public static Converter<String, Short> stringConverter() {
        return Shorts$ShortConverter.INSTANCE;
    }

    public static short[] ensureCapacity(short[] array, int minLength, int padding) {
        Preconditions.checkArgument(minLength >= 0, "Invalid minLength: %s", new Object[]{Integer.valueOf(minLength)});
        Preconditions.checkArgument(padding >= 0, "Invalid padding: %s", new Object[]{Integer.valueOf(padding)});
        return array.length < minLength ? copyOf(array, minLength + padding) : array;
    }

    private static short[] copyOf(short[] original, int length) {
        short[] copy = new short[length];
        System.arraycopy(original, 0, copy, 0, Math.min(original.length, length));
        return copy;
    }

    public static String join(String separator, short... array) {
        Preconditions.checkNotNull(separator);
        if (array.length == 0) {
            return "";
        }
        StringBuilder builder = new StringBuilder(array.length * 6);
        builder.append(array[0]);
        for (int i = 1; i < array.length; i++) {
            builder.append(separator);
            builder.append(array[i]);
        }
        return builder.toString();
    }

    public static Comparator<short[]> lexicographicalComparator() {
        return Shorts$LexicographicalComparator.INSTANCE;
    }

    public static short[] toArray(Collection<? extends Number> collection) {
        if (collection instanceof Shorts$ShortArrayAsList) {
            return ((Shorts$ShortArrayAsList) collection).toShortArray();
        }
        Object[] boxedArray = collection.toArray();
        int len = boxedArray.length;
        short[] array = new short[len];
        for (int i = 0; i < len; i++) {
            array[i] = ((Number) Preconditions.checkNotNull(boxedArray[i])).shortValue();
        }
        return array;
    }

    public static List<Short> asList(short... backingArray) {
        if (backingArray.length == 0) {
            return Collections.emptyList();
        }
        return new Shorts$ShortArrayAsList(backingArray);
    }
}
