package com.google.common.base;

final class CharMatcher$SingleWidth extends CharMatcher$RangesMatcher {
    static final CharMatcher$SingleWidth INSTANCE = new CharMatcher$SingleWidth();

    private CharMatcher$SingleWidth() {
        super("CharMatcher.singleWidth()", "\u0000־א׳؀ݐ฀Ḁ℀ﭐﹰ｡".toCharArray(), "ӹ־ת״ۿݿ๿₯℺﷿﻿ￜ".toCharArray());
    }
}
