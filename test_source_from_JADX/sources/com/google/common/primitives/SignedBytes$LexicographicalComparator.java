package com.google.common.primitives;

import java.util.Comparator;

enum SignedBytes$LexicographicalComparator implements Comparator<byte[]> {
    INSTANCE;

    public int compare(byte[] left, byte[] right) {
        int minLength = Math.min(left.length, right.length);
        for (int i = 0; i < minLength; i++) {
            int result = SignedBytes.compare(left[i], right[i]);
            if (result != 0) {
                return result;
            }
        }
        return left.length - right.length;
    }
}
