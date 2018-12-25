package com.google.common.io;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.CharMatcher;
import com.google.common.base.Preconditions;
import com.google.common.math.IntMath;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.math.RoundingMode;

final class BaseEncoding$SeparatedBaseEncoding extends BaseEncoding {
    private final int afterEveryChars;
    private final BaseEncoding delegate;
    private final String separator;
    private final CharMatcher separatorChars;

    BaseEncoding$SeparatedBaseEncoding(BaseEncoding delegate, String separator, int afterEveryChars) {
        this.delegate = (BaseEncoding) Preconditions.checkNotNull(delegate);
        this.separator = (String) Preconditions.checkNotNull(separator);
        this.afterEveryChars = afterEveryChars;
        Preconditions.checkArgument(afterEveryChars > 0, "Cannot add a separator after every %s chars", Integer.valueOf(afterEveryChars));
        this.separatorChars = CharMatcher.anyOf(separator).precomputed();
    }

    CharMatcher padding() {
        return this.delegate.padding();
    }

    int maxEncodedSize(int bytes) {
        int unseparatedSize = this.delegate.maxEncodedSize(bytes);
        return (this.separator.length() * IntMath.divide(Math.max(0, unseparatedSize - 1), this.afterEveryChars, RoundingMode.FLOOR)) + unseparatedSize;
    }

    @GwtIncompatible("Writer,OutputStream")
    public OutputStream encodingStream(Writer output) {
        return this.delegate.encodingStream(separatingWriter(output, this.separator, this.afterEveryChars));
    }

    void encodeTo(Appendable target, byte[] bytes, int off, int len) throws IOException {
        this.delegate.encodeTo(separatingAppendable(target, this.separator, this.afterEveryChars), bytes, off, len);
    }

    int maxDecodedSize(int chars) {
        return this.delegate.maxDecodedSize(chars);
    }

    int decodeTo(byte[] target, CharSequence chars) throws BaseEncoding$DecodingException {
        return this.delegate.decodeTo(target, this.separatorChars.removeFrom(chars));
    }

    @GwtIncompatible("Reader,InputStream")
    public InputStream decodingStream(Reader reader) {
        return this.delegate.decodingStream(ignoringReader(reader, this.separatorChars));
    }

    public BaseEncoding omitPadding() {
        return this.delegate.omitPadding().withSeparator(this.separator, this.afterEveryChars);
    }

    public BaseEncoding withPadChar(char padChar) {
        return this.delegate.withPadChar(padChar).withSeparator(this.separator, this.afterEveryChars);
    }

    public BaseEncoding withSeparator(String separator, int afterEveryChars) {
        throw new UnsupportedOperationException("Already have a separator");
    }

    public BaseEncoding upperCase() {
        return this.delegate.upperCase().withSeparator(this.separator, this.afterEveryChars);
    }

    public BaseEncoding lowerCase() {
        return this.delegate.lowerCase().withSeparator(this.separator, this.afterEveryChars);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.delegate.toString());
        stringBuilder.append(".withSeparator(\"");
        stringBuilder.append(this.separator);
        stringBuilder.append("\", ");
        stringBuilder.append(this.afterEveryChars);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}
