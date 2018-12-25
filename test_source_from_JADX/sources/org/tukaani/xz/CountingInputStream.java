package org.tukaani.xz;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

class CountingInputStream extends FilterInputStream {
    private long size = 0;

    public CountingInputStream(InputStream inputStream) {
        super(inputStream);
    }

    public long getSize() {
        return this.size;
    }

    public int read() throws IOException {
        int read = this.in.read();
        if (read != -1 && this.size >= 0) {
            this.size++;
        }
        return read;
    }

    public int read(byte[] bArr, int i, int i2) throws IOException {
        int read = this.in.read(bArr, i, i2);
        if (read > 0 && this.size >= 0) {
            this.size += (long) read;
        }
        return read;
    }
}
