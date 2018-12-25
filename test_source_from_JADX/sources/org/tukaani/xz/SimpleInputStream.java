package org.tukaani.xz;

import java.io.IOException;
import java.io.InputStream;
import org.tukaani.xz.simple.SimpleFilter;

class SimpleInputStream extends InputStream {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int FILTER_BUF_SIZE = 4096;
    static /* synthetic */ Class class$org$tukaani$xz$SimpleInputStream = class$("org.tukaani.xz.SimpleInputStream");
    private boolean endReached = false;
    private IOException exception = null;
    private final byte[] filterBuf = new byte[4096];
    private int filtered = 0;
    private InputStream in;
    private int pos = 0;
    private final SimpleFilter simpleFilter;
    private final byte[] tempBuf = new byte[1];
    private int unfiltered = 0;

    static {
        if (class$org$tukaani$xz$SimpleInputStream == null) {
        } else {
            Class cls = class$org$tukaani$xz$SimpleInputStream;
        }
    }

    SimpleInputStream(InputStream inputStream, SimpleFilter simpleFilter) {
        if (inputStream == null) {
            throw new NullPointerException();
        }
        this.in = inputStream;
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

    public int available() throws IOException {
        if (this.in == null) {
            throw new XZIOException("Stream closed");
        } else if (this.exception == null) {
            return this.filtered;
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
        if (i >= 0 && i2 >= 0) {
            int i3 = i + i2;
            if (i3 >= 0) {
                if (i3 <= bArr.length) {
                    if (i2 == 0) {
                        return 0;
                    }
                    if (this.in == null) {
                        throw new XZIOException("Stream closed");
                    } else if (this.exception != null) {
                        throw this.exception;
                    } else {
                        int i4 = 0;
                        while (true) {
                            try {
                                int min = Math.min(this.filtered, i2);
                                System.arraycopy(this.filterBuf, this.pos, bArr, i, min);
                                this.pos += min;
                                this.filtered -= min;
                                i += min;
                                i2 -= min;
                                i4 += min;
                                if ((this.pos + this.filtered) + this.unfiltered == 4096) {
                                    System.arraycopy(this.filterBuf, this.pos, this.filterBuf, 0, this.filtered + this.unfiltered);
                                    this.pos = 0;
                                }
                                if (i2 == 0) {
                                    break;
                                } else if (this.endReached) {
                                    break;
                                } else {
                                    int read = this.in.read(this.filterBuf, (this.pos + this.filtered) + this.unfiltered, 4096 - ((this.pos + this.filtered) + this.unfiltered));
                                    if (read == -1) {
                                        this.endReached = true;
                                        this.filtered = this.unfiltered;
                                        this.unfiltered = 0;
                                    } else {
                                        this.unfiltered += read;
                                        this.filtered = this.simpleFilter.code(this.filterBuf, this.pos, this.unfiltered);
                                        this.unfiltered -= this.filtered;
                                    }
                                }
                            } catch (IOException e) {
                                this.exception = e;
                                throw e;
                            }
                        }
                        return i4 > 0 ? i4 : -1;
                    }
                }
            }
        }
        throw new IndexOutOfBoundsException();
    }
}
