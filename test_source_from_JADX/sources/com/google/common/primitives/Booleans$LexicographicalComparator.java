package com.google.common.primitives;

import java.util.Comparator;

enum Booleans$LexicographicalComparator implements Comparator<boolean[]> {
    INSTANCE;

    public int compare(boolean[] left, boolean[] right) {
        int minLength = Math.min(left.length, right.length);
        for (int i = 0; i < minLength; i++) {
            int result = Booleans.compare(left[i], right[i]);
            if (result != 0) {
                return result;
            }
        }
        return left.length - right.length;
    }
}
