package com.google.common.io;

import com.google.common.base.Preconditions;
import com.google.common.io.BaseEncoding.StandardBaseEncoding;
import java.io.IOException;
import javax.annotation.Nullable;

final class BaseEncoding$Base64Encoding extends StandardBaseEncoding {
    BaseEncoding$Base64Encoding(String name, String alphabetChars, @Nullable Character paddingChar) {
        this(new BaseEncoding$Alphabet(name, alphabetChars.toCharArray()), paddingChar);
    }

    private BaseEncoding$Base64Encoding(BaseEncoding$Alphabet alphabet, @Nullable Character paddingChar) {
        super(alphabet, paddingChar);
        Preconditions.checkArgument(alphabet.chars.length == 64);
    }

    void encodeTo(Appendable target, byte[] bytes, int off, int len) throws IOException {
        Preconditions.checkNotNull(target);
        Preconditions.checkPositionIndexes(off, off + len, bytes.length);
        int i = off;
        int remaining = len;
        while (remaining >= 3) {
            int i2 = i + 1;
            int i3 = i2 + 1;
            i = ((bytes[i] & 255) << 16) | ((bytes[i2] & 255) << 8);
            i2 = i3 + 1;
            i |= bytes[i3] & 255;
            target.append(this.alphabet.encode(i >>> 18));
            target.append(this.alphabet.encode((i >>> 12) & 63));
            target.append(this.alphabet.encode((i >>> 6) & 63));
            target.append(this.alphabet.encode(i & 63));
            remaining -= 3;
            i = i2;
        }
        if (i < off + len) {
            encodeChunkTo(target, bytes, i, (off + len) - i);
        }
    }

    int decodeTo(byte[] target, CharSequence chars) throws BaseEncoding$DecodingException {
        Preconditions.checkNotNull(target);
        chars = padding().trimTrailingFrom(chars);
        if (this.alphabet.isValidPaddingStartPosition(chars.length())) {
            int bytesWritten = 0;
            int i = 0;
            while (i < chars.length()) {
                int i2 = i + 1;
                int i3 = i2 + 1;
                i = (this.alphabet.decode(chars.charAt(i)) << 18) | (this.alphabet.decode(chars.charAt(i2)) << 12);
                int i4 = bytesWritten + 1;
                target[bytesWritten] = (byte) (i >>> 16);
                if (i3 < chars.length()) {
                    i2 = i3 + 1;
                    bytesWritten = (this.alphabet.decode(chars.charAt(i3)) << 6) | i;
                    i = i4 + 1;
                    target[i4] = (byte) ((bytesWritten >>> 8) & 255);
                    if (i2 < chars.length()) {
                        i3 = i2 + 1;
                        i4 = i + 1;
                        target[i] = (byte) ((bytesWritten | this.alphabet.decode(chars.charAt(i2))) & 255);
                    } else {
                        bytesWritten = i;
                        i = i2;
                    }
                }
                bytesWritten = i4;
                i = i3;
            }
            return bytesWritten;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid input length ");
        stringBuilder.append(chars.length());
        throw new BaseEncoding$DecodingException(stringBuilder.toString());
    }

    BaseEncoding newInstance(BaseEncoding$Alphabet alphabet, @Nullable Character paddingChar) {
        return new BaseEncoding$Base64Encoding(alphabet, paddingChar);
    }
}
