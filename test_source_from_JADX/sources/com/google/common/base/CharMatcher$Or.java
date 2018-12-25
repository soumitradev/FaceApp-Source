package com.google.common.base;

import com.google.common.annotations.GwtIncompatible;
import java.util.BitSet;

final class CharMatcher$Or extends CharMatcher {
    final CharMatcher first;
    final CharMatcher second;

    public /* bridge */ /* synthetic */ boolean apply(Object x0) {
        return super.apply((Character) x0);
    }

    CharMatcher$Or(CharMatcher a, CharMatcher b) {
        this.first = (CharMatcher) Preconditions.checkNotNull(a);
        this.second = (CharMatcher) Preconditions.checkNotNull(b);
    }

    @GwtIncompatible("java.util.BitSet")
    void setBits(BitSet table) {
        this.first.setBits(table);
        this.second.setBits(table);
    }

    public boolean matches(char c) {
        if (!this.first.matches(c)) {
            if (!this.second.matches(c)) {
                return false;
            }
        }
        return true;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CharMatcher.or(");
        stringBuilder.append(this.first);
        stringBuilder.append(", ");
        stringBuilder.append(this.second);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}
