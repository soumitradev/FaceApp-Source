package com.google.common.primitives;

import java.util.Comparator;

enum Longs$LexicographicalComparator implements Comparator<long[]> {
    INSTANCE;

    public int compare(long[] left, long[] right) {
        int minLength = Math.min(left.length, right.length);
        for (int i = 0; i < minLength; i++) {
            int result = Longs.compare(left[i], right[i]);
            if (result != 0) {
                return result;
            }
        }
        return left.length - right.length;
    }
}
