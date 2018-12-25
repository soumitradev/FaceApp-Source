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
import java.util.regex.Pattern;
import javax.annotation.CheckForNull;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;

@GwtCompatible(emulated = true)
@CheckReturnValue
public final class Doubles {
    public static final int BYTES = 8;
    @GwtIncompatible("regular expressions")
    static final Pattern FLOATING_POINT_PATTERN = fpPattern();

    private Doubles() {
    }

    public static int hashCode(double value) {
        return Double.valueOf(value).hashCode();
    }

    public static int compare(double a, double b) {
        return Double.compare(a, b);
    }

    public static boolean isFinite(double value) {
        int i = 0;
        int i2 = Double.NEGATIVE_INFINITY < value ? 1 : 0;
        if (value < Double.POSITIVE_INFINITY) {
            i = 1;
        }
        return i & i2;
    }

    public static boolean contains(double[] array, double target) {
        for (double value : array) {
            if (value == target) {
                return true;
            }
        }
        return false;
    }

    public static int indexOf(double[] array, double target) {
        return indexOf(array, target, 0, array.length);
    }

    private static int indexOf(double[] array, double target, int start, int end) {
        for (int i = start; i < end; i++) {
            if (array[i] == target) {
                return i;
            }
        }
        return -1;
    }

    public static int indexOf(double[] array, double[] target) {
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

    public static int lastIndexOf(double[] array, double target) {
        return lastIndexOf(array, target, 0, array.length);
    }

    private static int lastIndexOf(double[] array, double target, int start, int end) {
        for (int i = end - 1; i >= start; i--) {
            if (array[i] == target) {
                return i;
            }
        }
        return -1;
    }

    public static double min(double... array) {
        Preconditions.checkArgument(array.length > 0);
        double min = array[0];
        for (int i = 1; i < array.length; i++) {
            min = Math.min(min, array[i]);
        }
        return min;
    }

    public static double max(double... array) {
        Preconditions.checkArgument(array.length > 0);
        double max = array[0];
        for (int i = 1; i < array.length; i++) {
            max = Math.max(max, array[i]);
        }
        return max;
    }

    public static double[] concat(double[]... arrays) {
        int length = 0;
        for (double[] array : arrays) {
            length += array.length;
        }
        double[] result = new double[length];
        int pos = 0;
        for (double[] array2 : arrays) {
            System.arraycopy(array2, 0, result, pos, array2.length);
            pos += array2.length;
        }
        return result;
    }

    @Beta
    public static Converter<String, Double> stringConverter() {
        return Doubles$DoubleConverter.INSTANCE;
    }

    public static double[] ensureCapacity(double[] array, int minLength, int padding) {
        Preconditions.checkArgument(minLength >= 0, "Invalid minLength: %s", new Object[]{Integer.valueOf(minLength)});
        Preconditions.checkArgument(padding >= 0, "Invalid padding: %s", new Object[]{Integer.valueOf(padding)});
        return array.length < minLength ? copyOf(array, minLength + padding) : array;
    }

    private static double[] copyOf(double[] original, int length) {
        double[] copy = new double[length];
        System.arraycopy(original, 0, copy, 0, Math.min(original.length, length));
        return copy;
    }

    public static String join(String separator, double... array) {
        Preconditions.checkNotNull(separator);
        if (array.length == 0) {
            return "";
        }
        StringBuilder builder = new StringBuilder(array.length * 12);
        builder.append(array[0]);
        for (int i = 1; i < array.length; i++) {
            builder.append(separator);
            builder.append(array[i]);
        }
        return builder.toString();
    }

    public static Comparator<double[]> lexicographicalComparator() {
        return Doubles$LexicographicalComparator.INSTANCE;
    }

    public static double[] toArray(Collection<? extends Number> collection) {
        if (collection instanceof Doubles$DoubleArrayAsList) {
            return ((Doubles$DoubleArrayAsList) collection).toDoubleArray();
        }
        Object[] boxedArray = collection.toArray();
        int len = boxedArray.length;
        double[] array = new double[len];
        for (int i = 0; i < len; i++) {
            array[i] = ((Number) Preconditions.checkNotNull(boxedArray[i])).doubleValue();
        }
        return array;
    }

    public static List<Double> asList(double... backingArray) {
        if (backingArray.length == 0) {
            return Collections.emptyList();
        }
        return new Doubles$DoubleArrayAsList(backingArray);
    }

    @GwtIncompatible("regular expressions")
    private static Pattern fpPattern() {
        String completeDec = new StringBuilder();
        completeDec.append("(?:\\d++(?:\\.\\d*+)?|\\.\\d++)");
        completeDec.append("(?:[eE][+-]?\\d++)?[fFdD]?");
        completeDec = completeDec.toString();
        String completeHex = new StringBuilder();
        completeHex.append("0[xX]");
        completeHex.append("(?:\\p{XDigit}++(?:\\.\\p{XDigit}*+)?|\\.\\p{XDigit}++)");
        completeHex.append("[pP][+-]?\\d++[fFdD]?");
        completeHex = completeHex.toString();
        String fpPattern = new StringBuilder();
        fpPattern.append("[+-]?(?:NaN|Infinity|");
        fpPattern.append(completeDec);
        fpPattern.append("|");
        fpPattern.append(completeHex);
        fpPattern.append(")");
        return Pattern.compile(fpPattern.toString());
    }

    @GwtIncompatible("regular expressions")
    @CheckForNull
    @Nullable
    @Beta
    public static Double tryParse(String string) {
        if (FLOATING_POINT_PATTERN.matcher(string).matches()) {
            try {
                return Double.valueOf(Double.parseDouble(string));
            } catch (NumberFormatException e) {
            }
        }
        return null;
    }
}
