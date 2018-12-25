package com.google.common.base;

final class CharMatcher$Ascii extends CharMatcher$NamedFastMatcher {
    static final CharMatcher$Ascii INSTANCE = new CharMatcher$Ascii();

    CharMatcher$Ascii() {
        super("CharMatcher.ascii()");
    }

    public boolean matches(char c) {
        return c <= Ascii.MAX;
    }
}
