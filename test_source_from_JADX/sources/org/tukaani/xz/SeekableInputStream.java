package org.tukaani.xz;

import java.io.IOException;
import java.io.InputStream;

public abstract class SeekableInputStream extends InputStream {
    public abstract long length() throws IOException;

    public abstract long position() throws IOException;

    public abstract void seek(long j) throws IOException;

    public long skip(long j) throws IOException {
        if (j <= 0) {
            return 0;
        }
        long length = length();
        long position = position();
        if (position >= length) {
            return 0;
        }
        long j2 = length - position;
        if (j2 < j) {
            j = j2;
        }
        seek(position + j);
        return j;
    }
}
