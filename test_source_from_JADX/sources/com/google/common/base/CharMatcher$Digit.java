package com.google.common.base;

final class CharMatcher$Digit extends CharMatcher$RangesMatcher {
    static final CharMatcher$Digit INSTANCE = new CharMatcher$Digit();
    private static final String ZEROES = "0٠۰߀०০੦૦୦௦౦೦൦๐໐༠၀႐០᠐᥆᧐᭐᮰᱀᱐꘠꣐꤀꩐０";

    private static char[] zeroes() {
        return ZEROES.toCharArray();
    }

    private static char[] nines() {
        char[] nines = new char[ZEROES.length()];
        for (int i = 0; i < ZEROES.length(); i++) {
            nines[i] = (char) (ZEROES.charAt(i) + 9);
        }
        return nines;
    }

    private CharMatcher$Digit() {
        super("CharMatcher.digit()", zeroes(), nines());
    }
}
