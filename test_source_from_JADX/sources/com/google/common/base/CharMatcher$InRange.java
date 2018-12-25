package com.google.common.base;

import com.google.common.annotations.GwtIncompatible;
import java.util.BitSet;

final class CharMatcher$InRange extends CharMatcher$FastMatcher {
    private final char endInclusive;
    private final char startInclusive;

    CharMatcher$InRange(char startInclusive, char endInclusive) {
        Preconditions.checkArgument(endInclusive >= startInclusive);
        this.startInclusive = startInclusive;
        this.endInclusive = endInclusive;
    }

    public boolean matches(char c) {
        return this.startInclusive <= c && c <= this.endInclusive;
    }

    @GwtIncompatible("java.util.BitSet")
    void setBits(BitSet table) {
        table.set(this.startInclusive, this.endInclusive + 1);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CharMatcher.inRange('");
        stringBuilder.append(CharMatcher.access$100(this.startInclusive));
        stringBuilder.append("', '");
        stringBuilder.append(CharMatcher.access$100(this.endInclusive));
        stringBuilder.append("')");
        return stringBuilder.toString();
    }
}
