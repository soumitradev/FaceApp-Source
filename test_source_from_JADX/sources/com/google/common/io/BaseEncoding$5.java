package com.google.common.io;

import java.io.IOException;
import java.io.Writer;

class BaseEncoding$5 extends Writer {
    final /* synthetic */ Writer val$delegate;
    final /* synthetic */ Appendable val$seperatingAppendable;

    BaseEncoding$5(Appendable appendable, Writer writer) {
        this.val$seperatingAppendable = appendable;
        this.val$delegate = writer;
    }

    public void write(int c) throws IOException {
        this.val$seperatingAppendable.append((char) c);
    }

    public void write(char[] chars, int off, int len) throws IOException {
        throw new UnsupportedOperationException();
    }

    public void flush() throws IOException {
        this.val$delegate.flush();
    }

    public void close() throws IOException {
        this.val$delegate.close();
    }
}
