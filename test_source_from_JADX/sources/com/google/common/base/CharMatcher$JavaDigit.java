package com.google.common.base;

final class CharMatcher$JavaDigit extends CharMatcher {
    static final CharMatcher$JavaDigit INSTANCE = new CharMatcher$JavaDigit();

    private CharMatcher$JavaDigit() {
    }

    public /* bridge */ /* synthetic */ boolean apply(Object x0) {
        return super.apply((Character) x0);
    }

    public boolean matches(char c) {
        return Character.isDigit(c);
    }

    public String toString() {
        return "CharMatcher.javaDigit()";
    }
}
