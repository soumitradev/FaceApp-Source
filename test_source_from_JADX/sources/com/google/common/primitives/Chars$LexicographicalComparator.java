package com.google.common.primitives;

import java.util.Comparator;

enum Chars$LexicographicalComparator implements Comparator<char[]> {
    INSTANCE;

    public int compare(char[] left, char[] right) {
        int minLength = Math.min(left.length, right.length);
        for (int i = 0; i < minLength; i++) {
            int result = Chars.compare(left[i], right[i]);
            if (result != 0) {
                return result;
            }
        }
        return left.length - right.length;
    }
}
