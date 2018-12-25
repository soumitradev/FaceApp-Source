package com.google.common.base;

final class CharMatcher$JavaUpperCase extends CharMatcher {
    static final CharMatcher$JavaUpperCase INSTANCE = new CharMatcher$JavaUpperCase();

    private CharMatcher$JavaUpperCase() {
    }

    public /* bridge */ /* synthetic */ boolean apply(Object x0) {
        return super.apply((Character) x0);
    }

    public boolean matches(char c) {
        return Character.isUpperCase(c);
    }

    public String toString() {
        return "CharMatcher.javaUpperCase()";
    }
}
