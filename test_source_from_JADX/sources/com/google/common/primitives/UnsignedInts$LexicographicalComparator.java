package com.google.common.primitives;

import java.util.Comparator;

enum UnsignedInts$LexicographicalComparator implements Comparator<int[]> {
    INSTANCE;

    public int compare(int[] left, int[] right) {
        int minLength = Math.min(left.length, right.length);
        for (int i = 0; i < minLength; i++) {
            if (left[i] != right[i]) {
                return UnsignedInts.compare(left[i], right[i]);
            }
        }
        return left.length - right.length;
    }
}
