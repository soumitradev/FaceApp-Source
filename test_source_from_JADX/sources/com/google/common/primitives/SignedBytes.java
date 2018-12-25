package com.google.common.primitives;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import java.util.Comparator;
import javax.annotation.CheckReturnValue;

@GwtCompatible
@CheckReturnValue
public final class SignedBytes {
    public static final byte MAX_POWER_OF_TWO = (byte) 64;

    private SignedBytes() {
    }

    public static byte checkedCast(long value) {
        byte result = (byte) ((int) value);
        if (((long) result) == value) {
            return result;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Out of range: ");
        stringBuilder.append(value);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public static byte saturatedCast(long value) {
        if (value > 127) {
            return Byte.MAX_VALUE;
        }
        if (value < -128) {
            return Byte.MIN_VALUE;
        }
        return (byte) ((int) value);
    }

    public static int compare(byte a, byte b) {
        return a - b;
    }

    public static byte min(byte... array) {
        int i = 1;
        Preconditions.checkArgument(array.length > 0);
        byte min = array[0];
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

    public static byte max(byte... array) {
        int i = 1;
        Preconditions.checkArgument(array.length > 0);
        byte max = array[0];
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

    public static String join(String separator, byte... array) {
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

    public static Comparator<byte[]> lexicographicalComparator() {
        return SignedBytes$LexicographicalComparator.INSTANCE;
    }
}
