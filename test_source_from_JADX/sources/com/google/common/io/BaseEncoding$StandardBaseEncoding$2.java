package com.google.common.io;

import com.google.common.base.CharMatcher;
import com.google.common.io.BaseEncoding.StandardBaseEncoding;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

class BaseEncoding$StandardBaseEncoding$2 extends InputStream {
    int bitBuffer = 0;
    int bitBufferLength = 0;
    boolean hitPadding = false;
    final CharMatcher paddingMatcher = this.this$0.padding();
    int readChars = 0;
    final /* synthetic */ StandardBaseEncoding this$0;
    final /* synthetic */ Reader val$reader;

    BaseEncoding$StandardBaseEncoding$2(StandardBaseEncoding standardBaseEncoding, Reader reader) {
        this.this$0 = standardBaseEncoding;
        this.val$reader = reader;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int read() throws java.io.IOException {
        /*
        r5 = this;
    L_0x0000:
        r0 = r5.val$reader;
        r0 = r0.read();
        r1 = -1;
        if (r0 != r1) goto L_0x0033;
    L_0x0009:
        r2 = r5.hitPadding;
        if (r2 != 0) goto L_0x0032;
    L_0x000d:
        r2 = r5.this$0;
        r2 = r2.alphabet;
        r3 = r5.readChars;
        r2 = r2.isValidPaddingStartPosition(r3);
        if (r2 != 0) goto L_0x0032;
    L_0x0019:
        r1 = new com.google.common.io.BaseEncoding$DecodingException;
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r3 = "Invalid input length ";
        r2.append(r3);
        r3 = r5.readChars;
        r2.append(r3);
        r2 = r2.toString();
        r1.<init>(r2);
        throw r1;
    L_0x0032:
        return r1;
    L_0x0033:
        r1 = r5.readChars;
        r2 = 1;
        r1 = r1 + r2;
        r5.readChars = r1;
        r1 = (char) r0;
        r3 = r5.paddingMatcher;
        r3 = r3.matches(r1);
        if (r3 == 0) goto L_0x0073;
    L_0x0042:
        r3 = r5.hitPadding;
        if (r3 != 0) goto L_0x0070;
    L_0x0046:
        r3 = r5.readChars;
        if (r3 == r2) goto L_0x0057;
    L_0x004a:
        r3 = r5.this$0;
        r3 = r3.alphabet;
        r4 = r5.readChars;
        r4 = r4 - r2;
        r3 = r3.isValidPaddingStartPosition(r4);
        if (r3 != 0) goto L_0x0070;
    L_0x0057:
        r2 = new com.google.common.io.BaseEncoding$DecodingException;
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r4 = "Padding cannot start at index ";
        r3.append(r4);
        r4 = r5.readChars;
        r3.append(r4);
        r3 = r3.toString();
        r2.<init>(r3);
        throw r2;
    L_0x0070:
        r5.hitPadding = r2;
        goto L_0x00ce;
    L_0x0073:
        r2 = r5.hitPadding;
        if (r2 == 0) goto L_0x0098;
    L_0x0077:
        r2 = new com.google.common.io.BaseEncoding$DecodingException;
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r4 = "Expected padding character but found '";
        r3.append(r4);
        r3.append(r1);
        r4 = "' at index ";
        r3.append(r4);
        r4 = r5.readChars;
        r3.append(r4);
        r3 = r3.toString();
        r2.<init>(r3);
        throw r2;
    L_0x0098:
        r2 = r5.bitBuffer;
        r3 = r5.this$0;
        r3 = r3.alphabet;
        r3 = r3.bitsPerChar;
        r2 = r2 << r3;
        r5.bitBuffer = r2;
        r2 = r5.bitBuffer;
        r3 = r5.this$0;
        r3 = r3.alphabet;
        r3 = r3.decode(r1);
        r2 = r2 | r3;
        r5.bitBuffer = r2;
        r2 = r5.bitBufferLength;
        r3 = r5.this$0;
        r3 = r3.alphabet;
        r3 = r3.bitsPerChar;
        r2 = r2 + r3;
        r5.bitBufferLength = r2;
        r2 = r5.bitBufferLength;
        r3 = 8;
        if (r2 < r3) goto L_0x00ce;
    L_0x00c1:
        r2 = r5.bitBufferLength;
        r2 = r2 - r3;
        r5.bitBufferLength = r2;
        r2 = r5.bitBuffer;
        r3 = r5.bitBufferLength;
        r2 = r2 >> r3;
        r2 = r2 & 255;
        return r2;
    L_0x00ce:
        goto L_0x0000;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.io.BaseEncoding$StandardBaseEncoding$2.read():int");
    }

    public void close() throws IOException {
        this.val$reader.close();
    }
}
