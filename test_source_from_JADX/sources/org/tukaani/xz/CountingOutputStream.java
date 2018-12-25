package org.tukaani.xz;

import java.io.IOException;
import java.io.OutputStream;

class CountingOutputStream extends FinishableOutputStream {
    private final OutputStream out;
    private long size = 0;

    public CountingOutputStream(OutputStream outputStream) {
        this.out = outputStream;
    }

    public void close() throws IOException {
        this.out.close();
    }

    public void flush() throws IOException {
        this.out.flush();
    }

    public long getSize() {
        return this.size;
    }

    public void write(int i) throws IOException {
        this.out.write(i);
        if (this.size >= 0) {
            this.size++;
        }
    }

    public void write(byte[] bArr, int i, int i2) throws IOException {
        this.out.write(bArr, i, i2);
        if (this.size >= 0) {
            this.size += (long) i2;
        }
    }
}
