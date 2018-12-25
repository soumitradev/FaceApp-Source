package org.tukaani.xz;

import java.io.IOException;
import org.tukaani.xz.delta.DeltaEncoder;

class DeltaOutputStream extends FinishableOutputStream {
    private static final int FILTER_BUF_SIZE = 4096;
    private final DeltaEncoder delta;
    private IOException exception = null;
    private final byte[] filterBuf = new byte[4096];
    private boolean finished = false;
    private FinishableOutputStream out;
    private final byte[] tempBuf = new byte[1];

    DeltaOutputStream(FinishableOutputStream finishableOutputStream, DeltaOptions deltaOptions) {
        this.out = finishableOutputStream;
        this.delta = new DeltaEncoder(deltaOptions.getDistance());
    }

    static int getMemoryUsage() {
        return 5;
    }

    public void close() throws IOException {
        if (this.out != null) {
            try {
                this.out.close();
            } catch (IOException e) {
                if (this.exception == null) {
                    this.exception = e;
                }
            }
            this.out = null;
        }
        if (this.exception != null) {
            throw this.exception;
        }
    }

    public void finish() throws IOException {
        if (!this.finished) {
            if (this.exception != null) {
                throw this.exception;
            }
            try {
                this.out.finish();
                this.finished = true;
            } catch (IOException e) {
                this.exception = e;
                throw e;
            }
        }
    }

    public void flush() throws IOException {
        if (this.exception != null) {
            throw this.exception;
        } else if (this.finished) {
            throw new XZIOException("Stream finished or closed");
        } else {
            try {
                this.out.flush();
            } catch (IOException e) {
                this.exception = e;
                throw e;
            }
        }
    }

    public void write(int i) throws IOException {
        this.tempBuf[0] = (byte) i;
        write(this.tempBuf, 0, 1);
    }

    public void write(byte[] bArr, int i, int i2) throws IOException {
        if (i >= 0 && i2 >= 0) {
            int i3 = i + i2;
            if (i3 >= 0) {
                if (i3 <= bArr.length) {
                    if (this.exception != null) {
                        throw this.exception;
                    } else if (this.finished) {
                        throw new XZIOException("Stream finished");
                    } else {
                        while (i2 > 4096) {
                            try {
                                this.delta.encode(bArr, i, 4096, this.filterBuf);
                                this.out.write(this.filterBuf);
                                i += 4096;
                                i2 -= 4096;
                            } catch (IOException e) {
                                this.exception = e;
                                throw e;
                            }
                        }
                        this.delta.encode(bArr, i, i2, this.filterBuf);
                        this.out.write(this.filterBuf, 0, i2);
                        return;
                    }
                }
            }
        }
        throw new IndexOutOfBoundsException();
    }
}
