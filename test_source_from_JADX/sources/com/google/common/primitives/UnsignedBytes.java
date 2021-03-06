package com.google.common.primitives;

import com.google.common.annotations.Beta;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.primitives.UnsignedBytes$LexicographicalComparatorHolder.PureJavaComparator;
import java.util.Comparator;
import javax.annotation.CheckReturnValue;

public final class UnsignedBytes {
    public static final byte MAX_POWER_OF_TWO = Byte.MIN_VALUE;
    public static final byte MAX_VALUE = (byte) -1;
    private static final int UNSIGNED_MASK = 255;

    private UnsignedBytes() {
    }

    @CheckReturnValue
    public static int toInt(byte value) {
        return value & 255;
    }

    public static byte checkedCast(long value) {
        if ((value >> 8) == 0) {
            return (byte) ((int) value);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Out of range: ");
        stringBuilder.append(value);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public static byte saturatedCast(long value) {
        if (value > ((long) toInt((byte) -1))) {
            return (byte) -1;
        }
        if (value < 0) {
            return (byte) 0;
        }
        return (byte) ((int) value);
    }

    @CheckReturnValue
    public static int compare(byte a, byte b) {
        return toInt(a) - toInt(b);
    }

    @CheckReturnValue
    public static byte min(byte... array) {
        int i = 1;
        Preconditions.checkArgument(array.length > 0);
        int min = toInt(array[0]);
        while (true) {
            int i2 = i;
            if (i2 >= array.length) {
                return (byte) min;
            }
            i = toInt(array[i2]);
            if (i < min) {
                min = i;
            }
            i = i2 + 1;
        }
    }

    @CheckReturnValue
    public static byte max(byte... array) {
        int i = 1;
        Preconditions.checkArgument(array.length > 0);
        int max = toInt(array[0]);
        while (true) {
            int i2 = i;
            if (i2 >= array.length) {
                return (byte) max;
            }
            i = toInt(array[i2]);
            if (i > max) {
                max = i;
            }
            i = i2 + 1;
        }
    }

    @CheckReturnValue
    @Beta
    public static String toString(byte x) {
        return toString(x, 10);
    }

    @CheckReturnValue
    @Beta
    public static String toString(byte x, int radix) {
        boolean z = radix >= 2 && radix <= 36;
        Preconditions.checkArgument(z, "radix (%s) must be between Character.MIN_RADIX and Character.MAX_RADIX", new Object[]{Integer.valueOf(radix)});
        return Integer.toString(toInt(x), radix);
    }

    @Beta
    public static byte parseUnsignedByte(String string) {
        return parseUnsignedByte(string, 10);
    }

    @Beta
    public static byte parseUnsignedByte(String string, int radix) {
        int parse = Integer.parseInt((String) Preconditions.checkNotNull(string), radix);
        if ((parse >> 8) == 0) {
            return (byte) parse;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("out of range: ");
        stringBuilder.append(parse);
        throw new NumberFormatException(stringBuilder.toString());
    }

    @CheckReturnValue
    public static String join(String separator, byte... array) {
        Preconditions.checkNotNull(separator);
        if (array.length == 0) {
            return "";
        }
        StringBuilder builder = new StringBuilder(array.length * (separator.length() + 3));
        builder.append(toInt(array[0]));
        for (int i = 1; i < array.length; i++) {
            builder.append(separator);
            builder.append(toString(array[i]));
        }
        return builder.toString();
    }

    @CheckReturnValue
    public static Comparator<byte[]> lexicographicalComparator() {
        return UnsignedBytes$LexicographicalComparatorHolder.BEST_COMPARATOR;
    }

    @VisibleForTesting
    static Comparator<byte[]> lexicographicalComparatorJavaImpl() {
        return PureJavaComparator.INSTANCE;
    }
}
