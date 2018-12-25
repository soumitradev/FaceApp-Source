package com.google.common.io;

import com.google.common.base.Ascii;
import com.google.common.base.CharMatcher;
import com.google.common.base.Preconditions;
import com.google.common.math.IntMath;
import java.math.RoundingMode;
import java.util.Arrays;

final class BaseEncoding$Alphabet extends CharMatcher {
    final int bitsPerChar;
    final int bytesPerChunk;
    private final char[] chars;
    final int charsPerChunk;
    private final byte[] decodabet;
    final int mask;
    private final String name;
    private final boolean[] validPadding;

    BaseEncoding$Alphabet(String name, char[] chars) {
        this.name = (String) Preconditions.checkNotNull(name);
        this.chars = (char[]) Preconditions.checkNotNull(chars);
        try {
            this.bitsPerChar = IntMath.log2(chars.length, RoundingMode.UNNECESSARY);
            int gcd = Math.min(8, Integer.lowestOneBit(this.bitsPerChar));
            this.charsPerChunk = 8 / gcd;
            this.bytesPerChunk = this.bitsPerChar / gcd;
            this.mask = chars.length - 1;
            byte[] decodabet = new byte[128];
            Arrays.fill(decodabet, (byte) -1);
            int i = 0;
            for (int i2 = 0; i2 < chars.length; i2++) {
                char c = chars[i2];
                Preconditions.checkArgument(CharMatcher.ASCII.matches(c), "Non-ASCII character: %s", Character.valueOf(c));
                Preconditions.checkArgument(decodabet[c] == (byte) -1, "Duplicate character: %s", Character.valueOf(c));
                decodabet[c] = (byte) i2;
            }
            this.decodabet = decodabet;
            boolean[] validPadding = new boolean[this.charsPerChunk];
            while (i < this.bytesPerChunk) {
                validPadding[IntMath.divide(i * 8, this.bitsPerChar, RoundingMode.CEILING)] = true;
                i++;
            }
            this.validPadding = validPadding;
        } catch (ArithmeticException e) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Illegal alphabet length ");
            stringBuilder.append(chars.length);
            throw new IllegalArgumentException(stringBuilder.toString(), e);
        }
    }

    char encode(int bits) {
        return this.chars[bits];
    }

    boolean isValidPaddingStartPosition(int index) {
        return this.validPadding[index % this.charsPerChunk];
    }

    int decode(char ch) throws BaseEncoding$DecodingException {
        Object stringBuilder;
        if (ch <= Ascii.MAX) {
            if (this.decodabet[ch] != (byte) -1) {
                return this.decodabet[ch];
            }
        }
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("Unrecognized character: ");
        if (CharMatcher.INVISIBLE.matches(ch)) {
            StringBuilder stringBuilder3 = new StringBuilder();
            stringBuilder3.append("0x");
            stringBuilder3.append(Integer.toHexString(ch));
            stringBuilder = stringBuilder3.toString();
        } else {
            stringBuilder = Character.valueOf(ch);
        }
        stringBuilder2.append(stringBuilder);
        throw new BaseEncoding$DecodingException(stringBuilder2.toString());
    }

    private boolean hasLowerCase() {
        for (char c : this.chars) {
            if (Ascii.isLowerCase(c)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasUpperCase() {
        for (char c : this.chars) {
            if (Ascii.isUpperCase(c)) {
                return true;
            }
        }
        return false;
    }

    BaseEncoding$Alphabet upperCase() {
        if (!hasLowerCase()) {
            return this;
        }
        Preconditions.checkState(hasUpperCase() ^ 1, "Cannot call upperCase() on a mixed-case alphabet");
        char[] upperCased = new char[this.chars.length];
        for (int i = 0; i < this.chars.length; i++) {
            upperCased[i] = Ascii.toUpperCase(this.chars[i]);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.name);
        stringBuilder.append(".upperCase()");
        return new BaseEncoding$Alphabet(stringBuilder.toString(), upperCased);
    }

    BaseEncoding$Alphabet lowerCase() {
        if (!hasUpperCase()) {
            return this;
        }
        Preconditions.checkState(hasLowerCase() ^ 1, "Cannot call lowerCase() on a mixed-case alphabet");
        char[] lowerCased = new char[this.chars.length];
        for (int i = 0; i < this.chars.length; i++) {
            lowerCased[i] = Ascii.toLowerCase(this.chars[i]);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.name);
        stringBuilder.append(".lowerCase()");
        return new BaseEncoding$Alphabet(stringBuilder.toString(), lowerCased);
    }

    public boolean matches(char c) {
        return CharMatcher.ASCII.matches(c) && this.decodabet[c] != (byte) -1;
    }

    public String toString() {
        return this.name;
    }
}
