package com.google.common.base;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import java.util.BitSet;

@VisibleForTesting
final class CharMatcher$Whitespace extends CharMatcher$NamedFastMatcher {
    static final CharMatcher$Whitespace INSTANCE = new CharMatcher$Whitespace();
    static final int MULTIPLIER = 1682554634;
    static final int SHIFT = Integer.numberOfLeadingZeros(TABLE.length() - 1);
    static final String TABLE = " 　\r   　 \u000b　   　 \t     \f 　 　　 \n 　";

    CharMatcher$Whitespace() {
        super("CharMatcher.whitespace()");
    }

    public boolean matches(char c) {
        return TABLE.charAt((MULTIPLIER * c) >>> SHIFT) == c;
    }

    @GwtIncompatible("java.util.BitSet")
    void setBits(BitSet table) {
        for (int i = 0; i < TABLE.length(); i++) {
            table.set(TABLE.charAt(i));
        }
    }
}
