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
import javax.annotation.CheckForNull;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;

@GwtCompatible(emulated = true)
@CheckReturnValue
public final class Ints {
    public static final int BYTES = 4;
    public static final int MAX_POWER_OF_TWO = 1073741824;

    private Ints() {
    }

    public static int hashCode(int value) {
        return value;
    }

    public static int checkedCast(long value) {
        int result = (int) value;
        if (((long) result) == value) {
            return result;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Out of range: ");
        stringBuilder.append(value);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public static int saturatedCast(long value) {
        if (value > 2147483647L) {
            return Integer.MAX_VALUE;
        }
        if (value < -2147483648L) {
            return Integer.MIN_VALUE;
        }
        return (int) value;
    }

    public static int compare(int a, int b) {
        if (a < b) {
            return -1;
        }
        return a > b ? 1 : 0;
    }

    public static boolean contains(int[] array, int target) {
        for (int value : array) {
            if (value == target) {
                return true;
            }
        }
        return false;
    }

    public static int indexOf(int[] array, int target) {
        return indexOf(array, target, 0, array.length);
    }

    private static int indexOf(int[] array, int target, int start, int end) {
        for (int i = start; i < end; i++) {
            if (array[i] == target) {
                return i;
            }
        }
        return -1;
    }

    public static int indexOf(int[] array, int[] target) {
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

    public static int lastIndexOf(int[] array, int target) {
        return lastIndexOf(array, target, 0, array.length);
    }

    private static int lastIndexOf(int[] array, int target, int start, int end) {
        for (int i = end - 1; i >= start; i--) {
            if (array[i] == target) {
                return i;
            }
        }
        return -1;
    }

    public static int min(int... array) {
        int i = 1;
        Preconditions.checkArgument(array.length > 0);
        int min = array[0];
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

    public static int max(int... array) {
        int i = 1;
        Preconditions.checkArgument(array.length > 0);
        int max = array[0];
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

    public static int[] concat(int[]... arrays) {
        int length = 0;
        for (int[] array : arrays) {
            length += array.length;
        }
        int[] result = new int[length];
        int pos = 0;
        for (int[] array2 : arrays) {
            System.arraycopy(array2, 0, result, pos, array2.length);
            pos += array2.length;
        }
        return result;
    }

    @GwtIncompatible("doesn't work")
    public static byte[] toByteArray(int value) {
        return new byte[]{(byte) (value >> 24), (byte) (value >> 16), (byte) (value >> 8), (byte) value};
    }

    @GwtIncompatible("doesn't work")
    public static int fromByteArray(byte[] bytes) {
        Preconditions.checkArgument(bytes.length >= 4, "array too small: %s < %s", new Object[]{Integer.valueOf(bytes.length), Integer.valueOf(4)});
        return fromBytes(bytes[0], bytes[1], bytes[2], bytes[3]);
    }

    @GwtIncompatible("doesn't work")
    public static int fromBytes(byte b1, byte b2, byte b3, byte b4) {
        return (((b1 << 24) | ((b2 & 255) << 16)) | ((b3 & 255) << 8)) | (b4 & 255);
    }

    @Beta
    public static Converter<String, Integer> stringConverter() {
        return Ints$IntConverter.INSTANCE;
    }

    public static int[] ensureCapacity(int[] array, int minLength, int padding) {
        Preconditions.checkArgument(minLength >= 0, "Invalid minLength: %s", new Object[]{Integer.valueOf(minLength)});
        Preconditions.checkArgument(padding >= 0, "Invalid padding: %s", new Object[]{Integer.valueOf(padding)});
        return array.length < minLength ? copyOf(array, minLength + padding) : array;
    }

    private static int[] copyOf(int[] original, int length) {
        int[] copy = new int[length];
        System.arraycopy(original, 0, copy, 0, Math.min(original.length, length));
        return copy;
    }

    public static String join(String separator, int... array) {
        Preconditions.checkNotNull(separator);
        if (array.length == 0) {
            return "";
        }
        StringBuilder builder = new StringBuilder(array.length * 5);
        builder.append(array[0]);
        for (int i = 1; i < array.length; i++) {
            builder.append(separator);
            builder.append(array[i]);
        }
        return builder.toString();
    }

    public static Comparator<int[]> lexicographicalComparator() {
        return Ints$LexicographicalComparator.INSTANCE;
    }

    public static int[] toArray(Collection<? extends Number> collection) {
        if (collection instanceof Ints$IntArrayAsList) {
            return ((Ints$IntArrayAsList) collection).toIntArray();
        }
        Object[] boxedArray = collection.toArray();
        int len = boxedArray.length;
        int[] array = new int[len];
        for (int i = 0; i < len; i++) {
            array[i] = ((Number) Preconditions.checkNotNull(boxedArray[i])).intValue();
        }
        return array;
    }

    public static List<Integer> asList(int... backingArray) {
        if (backingArray.length == 0) {
            return Collections.emptyList();
        }
        return new Ints$IntArrayAsList(backingArray);
    }

    @CheckForNull
    @Nullable
    @Beta
    public static Integer tryParse(String string) {
        return tryParse(string, 10);
    }

    @CheckForNull
    @Nullable
    @Beta
    public static Integer tryParse(String string, int radix) {
        Long result = Longs.tryParse(string, radix);
        if (result != null) {
            if (result.longValue() == ((long) result.intValue())) {
                return Integer.valueOf(result.intValue());
            }
        }
        return null;
    }
}
