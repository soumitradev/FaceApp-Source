package com.google.common.primitives;

import java.util.Comparator;

enum Shorts$LexicographicalComparator implements Comparator<short[]> {
    INSTANCE;

    public int compare(short[] left, short[] right) {
        int minLength = Math.min(left.length, right.length);
        for (int i = 0; i < minLength; i++) {
            int result = Shorts.compare(left[i], right[i]);
            if (result != 0) {
                return result;
            }
        }
        return left.length - right.length;
    }
}
