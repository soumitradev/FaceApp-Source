package com.google.common.primitives;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Converter;
import com.google.common.base.Preconditions;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.annotation.CheckForNull;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;

@GwtCompatible
@CheckReturnValue
public final class Longs {
    public static final int BYTES = 8;
    public static final long MAX_POWER_OF_TWO = 4611686018427387904L;
    private static final byte[] asciiDigits = createAsciiDigits();

    private Longs() {
    }

    public static int hashCode(long value) {
        return (int) (value ^ (value >>> 32));
    }

    public static int compare(long a, long b) {
        if (a < b) {
            return -1;
        }
        return a > b ? 1 : 0;
    }

    public static boolean contains(long[] array, long target) {
        for (long value : array) {
            if (value == target) {
                return true;
            }
        }
        return false;
    }

    public static int indexOf(long[] array, long target) {
        return indexOf(array, target, 0, array.length);
    }

    private static int indexOf(long[] array, long target, int start, int end) {
        for (int i = start; i < end; i++) {
            if (array[i] == target) {
                return i;
            }
        }
        return -1;
    }

    public static int indexOf(long[] array, long[] target) {
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

    public static int lastIndexOf(long[] array, long target) {
        return lastIndexOf(array, target, 0, array.length);
    }

    private static int lastIndexOf(long[] array, long target, int start, int end) {
        for (int i = end - 1; i >= start; i--) {
            if (array[i] == target) {
                return i;
            }
        }
        return -1;
    }

    public static long min(long... array) {
        Preconditions.checkArgument(array.length > 0);
        long min = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] < min) {
                min = array[i];
            }
        }
        return min;
    }

    public static long max(long... array) {
        Preconditions.checkArgument(array.length > 0);
        long max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }
        return max;
    }

    public static long[] concat(long[]... arrays) {
        int length = 0;
        for (long[] array : arrays) {
            length += array.length;
        }
        long[] result = new long[length];
        int pos = 0;
        for (long[] array2 : arrays) {
            System.arraycopy(array2, 0, result, pos, array2.length);
            pos += array2.length;
        }
        return result;
    }

    public static byte[] toByteArray(long value) {
        byte[] result = new byte[8];
        for (int i = 7; i >= 0; i--) {
            result[i] = (byte) ((int) (value & 255));
            value >>= 8;
        }
        return result;
    }

    public static long fromByteArray(byte[] bytes) {
        Preconditions.checkArgument(bytes.length >= 8, "array too small: %s < %s", new Object[]{Integer.valueOf(bytes.length), Integer.valueOf(8)});
        return fromBytes(bytes[0], bytes[1], bytes[2], bytes[3], bytes[4], bytes[5], bytes[6], bytes[7]);
    }

    public static long fromBytes(byte b1, byte b2, byte b3, byte b4, byte b5, byte b6, byte b7, byte b8) {
        return ((((((((((long) b1) & 255) << 56) | ((((long) b2) & 255) << 48)) | ((((long) b3) & 255) << 40)) | ((((long) b4) & 255) << 32)) | ((((long) b5) & 255) << 24)) | ((((long) b6) & 255) << 16)) | ((((long) b7) & 255) << 8)) | (((long) b8) & 255);
    }

    private static byte[] createAsciiDigits() {
        byte[] result = new byte[128];
        Arrays.fill(result, (byte) -1);
        for (int i = 0; i <= 9; i++) {
            result[i + 48] = (byte) i;
        }
        for (int i2 = 0; i2 <= 26; i2++) {
            result[i2 + 65] = (byte) (i2 + 10);
            result[i2 + 97] = (byte) (i2 + 10);
        }
        return result;
    }

    private static int digit(char c) {
        return c < '' ? asciiDigits[c] : -1;
    }

    @CheckForNull
    @Nullable
    @Beta
    public static Long tryParse(String string) {
        return tryParse(string, 10);
    }

    @CheckForNull
    @Nullable
    @Beta
    public static Long tryParse(String string, int radix) {
        String str = string;
        int i = radix;
        if (((String) Preconditions.checkNotNull(string)).isEmpty()) {
            return null;
        }
        if (i >= 2) {
            if (i <= 36) {
                int index = 0;
                boolean negative = str.charAt(0) == '-';
                if (negative) {
                    index = 1;
                }
                if (index == string.length()) {
                    return null;
                }
                int index2 = index + 1;
                index = digit(str.charAt(index));
                if (index >= 0) {
                    if (index < i) {
                        long accum = (long) (-index);
                        long cap = Long.MIN_VALUE / ((long) i);
                        while (index2 < string.length()) {
                            int index3 = index2 + 1;
                            index = digit(str.charAt(index2));
                            if (index >= 0 && index < i) {
                                if (accum >= cap) {
                                    accum *= (long) i;
                                    if (accum < ((long) index) - Long.MIN_VALUE) {
                                        return null;
                                    }
                                    index2 = index3;
                                    accum -= (long) index;
                                }
                            }
                            return null;
                        }
                        if (negative) {
                            return Long.valueOf(accum);
                        }
                        if (accum == Long.MIN_VALUE) {
                            return null;
                        }
                        return Long.valueOf(-accum);
                    }
                }
                return null;
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("radix must be between MIN_RADIX and MAX_RADIX but was ");
        stringBuilder.append(i);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    @Beta
    public static Converter<String, Long> stringConverter() {
        return Longs$LongConverter.INSTANCE;
    }

    public static long[] ensureCapacity(long[] array, int minLength, int padding) {
        Preconditions.checkArgument(minLength >= 0, "Invalid minLength: %s", new Object[]{Integer.valueOf(minLength)});
        Preconditions.checkArgument(padding >= 0, "Invalid padding: %s", new Object[]{Integer.valueOf(padding)});
        return array.length < minLength ? copyOf(array, minLength + padding) : array;
    }

    private static long[] copyOf(long[] original, int length) {
        long[] copy = new long[length];
        System.arraycopy(original, 0, copy, 0, Math.min(original.length, length));
        return copy;
    }

    public static String join(String separator, long... array) {
        Preconditions.checkNotNull(separator);
        if (array.length == 0) {
            return "";
        }
        StringBuilder builder = new StringBuilder(array.length * 10);
        builder.append(array[0]);
        for (int i = 1; i < array.length; i++) {
            builder.append(separator);
            builder.append(array[i]);
        }
        return builder.toString();
    }

    public static Comparator<long[]> lexicographicalComparator() {
        return Longs$LexicographicalComparator.INSTANCE;
    }

    public static long[] toArray(Collection<? extends Number> collection) {
        if (collection instanceof Longs$LongArrayAsList) {
            return ((Longs$LongArrayAsList) collection).toLongArray();
        }
        Object[] boxedArray = collection.toArray();
        int len = boxedArray.length;
        long[] array = new long[len];
        for (int i = 0; i < len; i++) {
            array[i] = ((Number) Preconditions.checkNotNull(boxedArray[i])).longValue();
        }
        return array;
    }

    public static List<Long> asList(long... backingArray) {
        if (backingArray.length == 0) {
            return Collections.emptyList();
        }
        return new Longs$LongArrayAsList(backingArray);
    }
}
