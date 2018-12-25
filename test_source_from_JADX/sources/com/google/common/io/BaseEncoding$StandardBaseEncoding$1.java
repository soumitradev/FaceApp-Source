package com.google.common.io;

import com.google.common.io.BaseEncoding.StandardBaseEncoding;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

class BaseEncoding$StandardBaseEncoding$1 extends OutputStream {
    int bitBuffer = 0;
    int bitBufferLength = 0;
    final /* synthetic */ StandardBaseEncoding this$0;
    final /* synthetic */ Writer val$out;
    int writtenChars = 0;

    BaseEncoding$StandardBaseEncoding$1(StandardBaseEncoding standardBaseEncoding, Writer writer) {
        this.this$0 = standardBaseEncoding;
        this.val$out = writer;
    }

    public void write(int b) throws IOException {
        this.bitBuffer <<= 8;
        this.bitBuffer |= b & 255;
        this.bitBufferLength += 8;
        while (this.bitBufferLength >= this.this$0.alphabet.bitsPerChar) {
            this.val$out.write(this.this$0.alphabet.encode((this.bitBuffer >> (this.bitBufferLength - this.this$0.alphabet.bitsPerChar)) & this.this$0.alphabet.mask));
            this.writtenChars++;
            this.bitBufferLength -= this.this$0.alphabet.bitsPerChar;
        }
    }

    public void flush() throws IOException {
        this.val$out.flush();
    }

    public void close() throws IOException {
        if (this.bitBufferLength > 0) {
            this.val$out.write(this.this$0.alphabet.encode((this.bitBuffer << (this.this$0.alphabet.bitsPerChar - this.bitBufferLength)) & this.this$0.alphabet.mask));
            this.writtenChars++;
            if (this.this$0.paddingChar != null) {
                while (this.writtenChars % this.this$0.alphabet.charsPerChunk != 0) {
                    this.val$out.write(this.this$0.paddingChar.charValue());
                    this.writtenChars++;
                }
            }
        }
        this.val$out.close();
    }
}
