package com.google.common.io;

import com.google.common.base.Preconditions;
import com.google.common.io.BaseEncoding.StandardBaseEncoding;
import java.io.IOException;
import javax.annotation.Nullable;

final class BaseEncoding$Base16Encoding extends StandardBaseEncoding {
    final char[] encoding;

    BaseEncoding$Base16Encoding(String name, String alphabetChars) {
        this(new BaseEncoding$Alphabet(name, alphabetChars.toCharArray()));
    }

    private BaseEncoding$Base16Encoding(BaseEncoding$Alphabet alphabet) {
        super(alphabet, null);
        this.encoding = new char[512];
        int i = 0;
        Preconditions.checkArgument(alphabet.chars.length == 16);
        while (true) {
            int i2 = i;
            if (i2 < 256) {
                this.encoding[i2] = alphabet.encode(i2 >>> 4);
                this.encoding[i2 | 256] = alphabet.encode(i2 & 15);
                i = i2 + 1;
            } else {
                return;
            }
        }
    }

    void encodeTo(Appendable target, byte[] bytes, int off, int len) throws IOException {
        Preconditions.checkNotNull(target);
        Preconditions.checkPositionIndexes(off, off + len, bytes.length);
        for (int i = 0; i < len; i++) {
            int b = bytes[off + i] & 255;
            target.append(this.encoding[b]);
            target.append(this.encoding[b | 256]);
        }
    }

    int decodeTo(byte[] target, CharSequence chars) throws BaseEncoding$DecodingException {
        Preconditions.checkNotNull(target);
        if (chars.length() % 2 == 1) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid input length ");
            stringBuilder.append(chars.length());
            throw new BaseEncoding$DecodingException(stringBuilder.toString());
        }
        int bytesWritten = 0;
        int i = 0;
        while (i < chars.length()) {
            int bytesWritten2 = bytesWritten + 1;
            target[bytesWritten] = (byte) ((this.alphabet.decode(chars.charAt(i)) << 4) | this.alphabet.decode(chars.charAt(i + 1)));
            i += 2;
            bytesWritten = bytesWritten2;
        }
        return bytesWritten;
    }

    BaseEncoding newInstance(BaseEncoding$Alphabet alphabet, @Nullable Character paddingChar) {
        return new BaseEncoding$Base16Encoding(alphabet);
    }
}
