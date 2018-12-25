package com.google.common.base;

import com.google.common.annotations.GwtIncompatible;
import java.util.BitSet;

final class CharMatcher$IsEither extends CharMatcher$FastMatcher {
    private final char match1;
    private final char match2;

    CharMatcher$IsEither(char match1, char match2) {
        this.match1 = match1;
        this.match2 = match2;
    }

    public boolean matches(char c) {
        if (c != this.match1) {
            if (c != this.match2) {
                return false;
            }
        }
        return true;
    }

    @GwtIncompatible("java.util.BitSet")
    void setBits(BitSet table) {
        table.set(this.match1);
        table.set(this.match2);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CharMatcher.anyOf(\"");
        stringBuilder.append(CharMatcher.access$100(this.match1));
        stringBuilder.append(CharMatcher.access$100(this.match2));
        stringBuilder.append("\")");
        return stringBuilder.toString();
    }
}
