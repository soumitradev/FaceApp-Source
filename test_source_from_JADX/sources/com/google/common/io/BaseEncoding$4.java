package com.google.common.io;

import java.io.IOException;

class BaseEncoding$4 implements Appendable {
    int charsUntilSeparator = this.val$afterEveryChars;
    final /* synthetic */ int val$afterEveryChars;
    final /* synthetic */ Appendable val$delegate;
    final /* synthetic */ String val$separator;

    BaseEncoding$4(int i, Appendable appendable, String str) {
        this.val$afterEveryChars = i;
        this.val$delegate = appendable;
        this.val$separator = str;
    }

    public Appendable append(char c) throws IOException {
        if (this.charsUntilSeparator == 0) {
            this.val$delegate.append(this.val$separator);
            this.charsUntilSeparator = this.val$afterEveryChars;
        }
        this.val$delegate.append(c);
        this.charsUntilSeparator--;
        return this;
    }

    public Appendable append(CharSequence chars, int off, int len) throws IOException {
        throw new UnsupportedOperationException();
    }

    public Appendable append(CharSequence chars) throws IOException {
        throw new UnsupportedOperationException();
    }
}
