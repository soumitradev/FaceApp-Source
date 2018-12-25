package org.tukaani.xz;

import java.io.IOException;
import org.tukaani.xz.simple.SimpleFilter;

class SimpleOutputStream extends FinishableOutputStream {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int FILTER_BUF_SIZE = 4096;
    static /* synthetic */ Class class$org$tukaani$xz$SimpleOutputStream = class$("org.tukaani.xz.SimpleOutputStream");
    private IOException exception = null;
    private final byte[] filterBuf = new byte[4096];
    private boolean finished = false;
    private FinishableOutputStream out;
    private int pos = 0;
    private final SimpleFilter simpleFilter;
    private final byte[] tempBuf = new byte[1];
    private int unfiltered = 0;

    static {
        if (class$org$tukaani$xz$SimpleOutputStream == null) {
        } else {
            Class cls = class$org$tukaani$xz$SimpleOutputStream;
        }
    }

    SimpleOutputStream(FinishableOutputStream finishableOutputStream, SimpleFilter simpleFilter) {
        if (finishableOutputStream == null) {
            throw new NullPointerException();
        }
        this.out = finishableOutputStream;
        this.simpleFilter = simpleFilter;
    }

    static /* synthetic */ Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (Throwable e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    static int getMemoryUsage() {
        return 5;
    }

    private void writePending() throws IOException {
        if (this.exception != null) {
            throw this.exception;
        }
        try {
            this.out.write(this.filterBuf, this.pos, this.unfiltered);
            this.finished = true;
        } catch (IOException e) {
            this.exception = e;
            throw e;
        }
    }

    public void close() throws IOException {
        if (this.out != null) {
            if (!this.finished) {
                try {
                    writePending();
                } catch (IOException e) {
                }
            }
            try {
                this.out.close();
            } catch (IOException e2) {
                if (this.exception == null) {
                    this.exception = e2;
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
            writePending();
            try {
                this.out.finish();
            } catch (IOException e) {
                this.exception = e;
                throw e;
            }
        }
    }

    public void flush() throws IOException {
        throw new UnsupportedOptionsException("Flushing is not supported");
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
                        throw new XZIOException("Stream finished or closed");
                    } else {
                        while (i2 > 0) {
                            i3 = Math.min(i2, 4096 - (this.pos + this.unfiltered));
                            System.arraycopy(bArr, i, this.filterBuf, this.pos + this.unfiltered, i3);
                            i += i3;
                            i2 -= i3;
                            this.unfiltered += i3;
                            i3 = this.simpleFilter.code(this.filterBuf, this.pos, this.unfiltered);
                            this.unfiltered -= i3;
                            try {
                                this.out.write(this.filterBuf, this.pos, i3);
                                this.pos += i3;
                                if (this.pos + this.unfiltered == 4096) {
                                    System.arraycopy(this.filterBuf, this.pos, this.filterBuf, 0, this.unfiltered);
                                    this.pos = 0;
                                }
                            } catch (IOException e) {
                                this.exception = e;
                                throw e;
                            }
                        }
                        return;
                    }
                }
            }
        }
        throw new IndexOutOfBoundsException();
    }
}
