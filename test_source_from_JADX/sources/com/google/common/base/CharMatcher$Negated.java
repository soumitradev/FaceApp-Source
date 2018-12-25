package com.google.common.base;

import com.google.common.annotations.GwtIncompatible;
import java.util.BitSet;

class CharMatcher$Negated extends CharMatcher {
    final CharMatcher original;

    public /* bridge */ /* synthetic */ boolean apply(Object x0) {
        return super.apply((Character) x0);
    }

    CharMatcher$Negated(CharMatcher original) {
        this.original = (CharMatcher) Preconditions.checkNotNull(original);
    }

    public boolean matches(char c) {
        return this.original.matches(c) ^ 1;
    }

    public boolean matchesAllOf(CharSequence sequence) {
        return this.original.matchesNoneOf(sequence);
    }

    public boolean matchesNoneOf(CharSequence sequence) {
        return this.original.matchesAllOf(sequence);
    }

    public int countIn(CharSequence sequence) {
        return sequence.length() - this.original.countIn(sequence);
    }

    @GwtIncompatible("java.util.BitSet")
    void setBits(BitSet table) {
        BitSet tmp = new BitSet();
        this.original.setBits(tmp);
        tmp.flip(0, 65536);
        table.or(tmp);
    }

    public CharMatcher negate() {
        return this.original;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.original);
        stringBuilder.append(".negate()");
        return stringBuilder.toString();
    }
}
