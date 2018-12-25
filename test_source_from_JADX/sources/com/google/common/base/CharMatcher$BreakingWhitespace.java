package com.google.common.base;

final class CharMatcher$BreakingWhitespace extends CharMatcher {
    static final CharMatcher INSTANCE = new CharMatcher$BreakingWhitespace();

    private CharMatcher$BreakingWhitespace() {
    }

    public /* bridge */ /* synthetic */ boolean apply(Object x0) {
        return super.apply((Character) x0);
    }

    public boolean matches(char c) {
        boolean z = true;
        if (!(c == ' ' || c == '' || c == ' ')) {
            if (c == ' ') {
                return false;
            }
            if (!(c == ' ' || c == '　')) {
                switch (c) {
                    case '\t':
                    case '\n':
                    case '\u000b':
                    case '\f':
                    case '\r':
                        break;
                    default:
                        switch (c) {
                            case ' ':
                            case ' ':
                                break;
                            default:
                                if (c < ' ' || c > ' ') {
                                    z = false;
                                }
                                return z;
                        }
                }
            }
        }
        return true;
    }

    public String toString() {
        return "CharMatcher.breakingWhitespace()";
    }
}
