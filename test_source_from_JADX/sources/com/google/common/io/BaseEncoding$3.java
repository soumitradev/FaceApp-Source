package com.google.common.io;

import com.google.common.base.CharMatcher;
import java.io.IOException;
import java.io.Reader;

class BaseEncoding$3 extends Reader {
    final /* synthetic */ Reader val$delegate;
    final /* synthetic */ CharMatcher val$toIgnore;

    BaseEncoding$3(Reader reader, CharMatcher charMatcher) {
        this.val$delegate = reader;
        this.val$toIgnore = charMatcher;
    }

    public int read() throws IOException {
        int readChar;
        do {
            readChar = this.val$delegate.read();
            if (readChar == -1) {
                break;
            }
        } while (this.val$toIgnore.matches((char) readChar));
        return readChar;
    }

    public int read(char[] cbuf, int off, int len) throws IOException {
        throw new UnsupportedOperationException();
    }

    public void close() throws IOException {
        this.val$delegate.close();
    }
}
