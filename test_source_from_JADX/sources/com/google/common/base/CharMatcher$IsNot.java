package com.google.common.base;

import com.google.common.annotations.GwtIncompatible;
import java.util.BitSet;

final class CharMatcher$IsNot extends CharMatcher$FastMatcher {
    private final char match;

    CharMatcher$IsNot(char match) {
        this.match = match;
    }

    public boolean matches(char c) {
        return c != this.match;
    }

    public CharMatcher and(CharMatcher other) {
        return other.matches(this.match) ? super.and(other) : other;
    }

    public CharMatcher or(CharMatcher other) {
        return other.matches(this.match) ? any() : this;
    }

    @GwtIncompatible("java.util.BitSet")
    void setBits(BitSet table) {
        table.set(0, this.match);
        table.set(this.match + 1, 65536);
    }

    public CharMatcher negate() {
        return is(this.match);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CharMatcher.isNot('");
        stringBuilder.append(CharMatcher.access$100(this.match));
        stringBuilder.append("')");
        return stringBuilder.toString();
    }
}
