package com.google.common.base;

final class CharMatcher$JavaIsoControl extends CharMatcher$NamedFastMatcher {
    static final CharMatcher$JavaIsoControl INSTANCE = new CharMatcher$JavaIsoControl();

    private CharMatcher$JavaIsoControl() {
        super("CharMatcher.javaIsoControl()");
    }

    public boolean matches(char c) {
        if (c > '\u001f') {
            if (c < Ascii.MAX || c > '') {
                return false;
            }
        }
        return true;
    }
}
