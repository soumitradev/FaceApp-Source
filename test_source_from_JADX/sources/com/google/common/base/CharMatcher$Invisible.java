package com.google.common.base;

final class CharMatcher$Invisible extends CharMatcher$RangesMatcher {
    static final CharMatcher$Invisible INSTANCE = new CharMatcher$Invisible();
    private static final String RANGE_ENDS = "  ­؄؜۝܏ ᠎‏ ⁤⁦⁧⁨⁩⁯　﻿￹￻";
    private static final String RANGE_STARTS = "\u0000­؀؜۝܏ ᠎   ⁦⁧⁨⁩⁪　?﻿￹￺";

    private CharMatcher$Invisible() {
        super("CharMatcher.invisible()", RANGE_STARTS.toCharArray(), RANGE_ENDS.toCharArray());
    }
}
