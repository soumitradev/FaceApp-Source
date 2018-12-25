package com.google.common.base;

import com.google.common.annotations.GwtIncompatible;
import java.util.BitSet;

final class CharMatcher$And extends CharMatcher {
    final CharMatcher first;
    final CharMatcher second;

    public /* bridge */ /* synthetic */ boolean apply(Object x0) {
        return super.apply((Character) x0);
    }

    CharMatcher$And(CharMatcher a, CharMatcher b) {
        this.first = (CharMatcher) Preconditions.checkNotNull(a);
        this.second = (CharMatcher) Preconditions.checkNotNull(b);
    }

    public boolean matches(char c) {
        return this.first.matches(c) && this.second.matches(c);
    }

    @GwtIncompatible("java.util.BitSet")
    void setBits(BitSet table) {
        BitSet tmp1 = new BitSet();
        this.first.setBits(tmp1);
        BitSet tmp2 = new BitSet();
        this.second.setBits(tmp2);
        tmp1.and(tmp2);
        table.or(tmp1);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CharMatcher.and(");
        stringBuilder.append(this.first);
        stringBuilder.append(", ");
        stringBuilder.append(this.second);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}
