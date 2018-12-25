package com.google.common.primitives;

import java.util.Comparator;

enum UnsignedLongs$LexicographicalComparator implements Comparator<long[]> {
    INSTANCE;

    public int compare(long[] left, long[] right) {
        int minLength = Math.min(left.length, right.length);
        for (int i = 0; i < minLength; i++) {
            if (left[i] != right[i]) {
                return UnsignedLongs.compare(left[i], right[i]);
            }
        }
        return left.length - right.length;
    }
}
