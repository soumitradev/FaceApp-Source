package com.google.common.base;

final class CharMatcher$JavaLetterOrDigit extends CharMatcher {
    static final CharMatcher$JavaLetterOrDigit INSTANCE = new CharMatcher$JavaLetterOrDigit();

    private CharMatcher$JavaLetterOrDigit() {
    }

    public /* bridge */ /* synthetic */ boolean apply(Object x0) {
        return super.apply((Character) x0);
    }

    public boolean matches(char c) {
        return Character.isLetterOrDigit(c);
    }

    public String toString() {
        return "CharMatcher.javaLetterOrDigit()";
    }
}
