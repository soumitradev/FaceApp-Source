package com.google.common.base;

abstract class CharMatcher$FastMatcher extends CharMatcher {
    CharMatcher$FastMatcher() {
    }

    public /* bridge */ /* synthetic */ boolean apply(Object x0) {
        return super.apply((Character) x0);
    }

    public final CharMatcher precomputed() {
        return this;
    }

    public CharMatcher negate() {
        return new CharMatcher$NegatedFastMatcher(this);
    }
}
