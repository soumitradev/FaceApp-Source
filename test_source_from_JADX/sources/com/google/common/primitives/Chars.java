package com.google.common.primitives;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.annotation.CheckReturnValue;

@GwtCompatible(emulated = true)
@CheckReturnValue
public final class Chars {
    public static final int BYTES = 2;

    private Chars() {
    }

    public static int hashCode(char value) {
        return value;
    }

    public static char checkedCast(long value) {
        char result = (char) ((int) value);
        if (((long) result) == value) {
            return result;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Out of range: ");
        stringBuilder.append(value);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public static char saturatedCast(long value) {
        if (value > 65535) {
            return '￿';
        }
        if (value < 0) {
            return '\u0000';
        }
        return (char) ((int) value);
    }

    public static int compare(char a, char b) {
        return a - b;
    }

    public static boolean contains(char[] array, char target) {
        for (char value : array) {
            if (value == target) {
                return true;
            }
        }
        return false;
    }

    public static int indexOf(char[] array, char target) {
        return indexOf(array, target, 0, array.length);
    }

    private static int indexOf(char[] array, char target, int start, int end) {
        for (int i = start; i < end; i++) {
            if (array[i] == target) {
                return i;
            }
        }
        return -1;
    }

    public static int indexOf(char[] array, char[] target) {
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

    public static int lastIndexOf(char[] array, char target) {
        return lastIndexOf(array, target, 0, array.length);
    }

    private static int lastIndexOf(char[] array, char target, int start, int end) {
        for (int i = end - 1; i >= start; i--) {
            if (array[i] == target) {
                return i;
            }
        }
        return -1;
    }

    public static char min(char... array) {
        int i = 1;
        Preconditions.checkArgument(array.length > 0);
        char min = array[0];
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

    public static char max(char... array) {
        int i = 1;
        Preconditions.checkArgument(array.length > 0);
        char max = array[0];
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

    public static char[] concat(char[]... arrays) {
        int length = 0;
        for (char[] array : arrays) {
            length += array.length;
        }
        char[] result = new char[length];
        int pos = 0;
        for (char[] array2 : arrays) {
            System.arraycopy(array2, 0, result, pos, array2.length);
            pos += array2.length;
        }
        return result;
    }

    @GwtIncompatible("doesn't work")
    public static byte[] toByteArray(char value) {
        return new byte[]{(byte) (value >> 8), (byte) value};
    }

    @GwtIncompatible("doesn't work")
    public static char fromByteArray(byte[] bytes) {
        Preconditions.checkArgument(bytes.length >= 2, "array too small: %s < %s", new Object[]{Integer.valueOf(bytes.length), Integer.valueOf(2)});
        return fromBytes(bytes[0], bytes[1]);
    }

    @GwtIncompatible("doesn't work")
    public static char fromBytes(byte b1, byte b2) {
        return (char) ((b1 << 8) | (b2 & 255));
    }

    public static char[] ensureCapacity(char[] array, int minLength, int padding) {
        Preconditions.checkArgument(minLength >= 0, "Invalid minLength: %s", new Object[]{Integer.valueOf(minLength)});
        Preconditions.checkArgument(padding >= 0, "Invalid padding: %s", new Object[]{Integer.valueOf(padding)});
        return array.length < minLength ? copyOf(array, minLength + padding) : array;
    }

    private static char[] copyOf(char[] original, int length) {
        char[] copy = new char[length];
        System.arraycopy(original, 0, copy, 0, Math.min(original.length, length));
        return copy;
    }

    public static String join(String separator, char... array) {
        Preconditions.checkNotNull(separator);
        int len = array.length;
        if (len == 0) {
            return "";
        }
        StringBuilder builder = new StringBuilder((separator.length() * (len - 1)) + len);
        builder.append(array[0]);
        for (int i = 1; i < len; i++) {
            builder.append(separator);
            builder.append(array[i]);
        }
        return builder.toString();
    }

    public static Comparator<char[]> lexicographicalComparator() {
        return Chars$LexicographicalComparator.INSTANCE;
    }

    public static char[] toArray(Collection<Character> collection) {
        if (collection instanceof Chars$CharArrayAsList) {
            return ((Chars$CharArrayAsList) collection).toCharArray();
        }
        Object[] boxedArray = collection.toArray();
        int len = boxedArray.length;
        char[] array = new char[len];
        for (int i = 0; i < len; i++) {
            array[i] = ((Character) Preconditions.checkNotNull(boxedArray[i])).charValue();
        }
        return array;
    }

    public static List<Character> asList(char... backingArray) {
        if (backingArray.length == 0) {
            return Collections.emptyList();
        }
        return new Chars$CharArrayAsList(backingArray);
    }
}
