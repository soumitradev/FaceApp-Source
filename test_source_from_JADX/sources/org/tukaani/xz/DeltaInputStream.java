package org.tukaani.xz;

import java.io.IOException;
import java.io.InputStream;
import org.tukaani.xz.delta.DeltaDecoder;

public class DeltaInputStream extends InputStream {
    public static final int DISTANCE_MAX = 256;
    public static final int DISTANCE_MIN = 1;
    private final DeltaDecoder delta;
    private IOException exception = null;
    private InputStream in;
    private final byte[] tempBuf = new byte[1];

    public DeltaInputStream(InputStream inputStream, int i) {
        if (inputStream == null) {
            throw new NullPointerException();
        }
        this.in = inputStream;
        this.delta = new DeltaDecoder(i);
    }

    public int available() throws IOException {
        if (this.in == null) {
            throw new XZIOException("Stream closed");
        } else if (this.exception == null) {
            return this.in.available();
        } else {
            throw this.exception;
        }
    }

    public void close() throws IOException {
        if (this.in != null) {
            try {
                this.in.close();
            } finally {
                this.in = null;
            }
        }
    }

    public int read() throws IOException {
        return read(this.tempBuf, 0, 1) == -1 ? -1 : this.tempBuf[0] & 255;
    }

    public int read(byte[] bArr, int i, int i2) throws IOException {
        if (i2 == 0) {
            return 0;
        }
        if (this.in == null) {
            throw new XZIOException("Stream closed");
        } else if (this.exception != null) {
            throw this.exception;
        } else {
            try {
                i2 = this.in.read(bArr, i, i2);
                if (i2 == -1) {
                    return -1;
                }
                this.delta.decode(bArr, i, i2);
                return i2;
            } catch (IOException e) {
                this.exception = e;
                throw e;
            }
        }
    }
}
