package com.google.common.base;

import com.google.common.annotations.GwtIncompatible;
import java.util.Arrays;
import java.util.BitSet;

final class CharMatcher$AnyOf extends CharMatcher {
    private final char[] chars;

    public /* bridge */ /* synthetic */ boolean apply(Object x0) {
        return super.apply((Character) x0);
    }

    public CharMatcher$AnyOf(CharSequence chars) {
        this.chars = chars.toString().toCharArray();
        Arrays.sort(this.chars);
    }

    public boolean matches(char c) {
        return Arrays.binarySearch(this.chars, c) >= 0;
    }

    @GwtIncompatible("java.util.BitSet")
    void setBits(BitSet table) {
        for (char c : this.chars) {
            table.set(c);
        }
    }

    public String toString() {
        StringBuilder description = new StringBuilder("CharMatcher.anyOf(\"");
        for (char c : this.chars) {
            description.append(CharMatcher.access$100(c));
        }
        description.append("\")");
        return description.toString();
    }
}
