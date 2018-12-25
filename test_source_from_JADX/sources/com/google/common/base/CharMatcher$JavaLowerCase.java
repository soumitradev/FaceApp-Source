package com.google.common.base;

final class CharMatcher$JavaLowerCase extends CharMatcher {
    static final CharMatcher$JavaLowerCase INSTANCE = new CharMatcher$JavaLowerCase();

    private CharMatcher$JavaLowerCase() {
    }

    public /* bridge */ /* synthetic */ boolean apply(Object x0) {
        return super.apply((Character) x0);
    }

    public boolean matches(char c) {
        return Character.isLowerCase(c);
    }

    public String toString() {
        return "CharMatcher.javaLowerCase()";
    }
}
