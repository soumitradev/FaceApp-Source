package com.google.common.base;

import java.util.Arrays;

class CharMatcher$RangesMatcher extends CharMatcher {
    private final String description;
    private final char[] rangeEnds;
    private final char[] rangeStarts;

    public /* bridge */ /* synthetic */ boolean apply(Object x0) {
        return super.apply((Character) x0);
    }

    CharMatcher$RangesMatcher(String description, char[] rangeStarts, char[] rangeEnds) {
        this.description = description;
        this.rangeStarts = rangeStarts;
        this.rangeEnds = rangeEnds;
        Preconditions.checkArgument(rangeStarts.length == rangeEnds.length);
        for (int i = 0; i < rangeStarts.length; i++) {
            Preconditions.checkArgument(rangeStarts[i] <= rangeEnds[i]);
            if (i + 1 < rangeStarts.length) {
                Preconditions.checkArgument(rangeEnds[i] < rangeStarts[i + 1]);
            }
        }
    }

    public boolean matches(char c) {
        int index = Arrays.binarySearch(this.rangeStarts, c);
        boolean z = true;
        if (index >= 0) {
            return true;
        }
        int index2 = (index ^ -1) - 1;
        if (index2 < 0 || c > this.rangeEnds[index2]) {
            z = false;
        }
        return z;
    }

    public String toString() {
        return this.description;
    }
}
