package org.tukaani.xz;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import name.antonsmirnov.firmata.writer.AnalogMessageWriter;
import org.tukaani.xz.lz.LZDecoder;
import org.tukaani.xz.lzma.LZMADecoder;
import org.tukaani.xz.rangecoder.RangeDecoderFromStream;

public class LZMAInputStream extends InputStream {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int DICT_SIZE_MAX = 2147483632;
    static /* synthetic */ Class class$org$tukaani$xz$LZMAInputStream = class$("org.tukaani.xz.LZMAInputStream");
    private boolean endReached;
    private IOException exception;
    private InputStream in;
    private LZDecoder lz;
    private LZMADecoder lzma;
    private RangeDecoderFromStream rc;
    private long remainingSize;
    private final byte[] tempBuf;

    static {
        if (class$org$tukaani$xz$LZMAInputStream == null) {
        } else {
            Class cls = class$org$tukaani$xz$LZMAInputStream;
        }
    }

    public LZMAInputStream(InputStream inputStream) throws IOException {
        this(inputStream, -1);
    }

    public LZMAInputStream(InputStream inputStream, int i) throws IOException {
        int i2 = 0;
        this.endReached = false;
        this.tempBuf = new byte[1];
        this.exception = null;
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        byte readByte = dataInputStream.readByte();
        int i3 = 0;
        for (int i4 = 0; i4 < 4; i4++) {
            i3 |= dataInputStream.readUnsignedByte() << (i4 * 8);
        }
        long j = 0;
        while (i2 < 8) {
            i2++;
            j |= ((long) dataInputStream.readUnsignedByte()) << (i2 * 8);
        }
        i2 = getMemoryUsage(i3, readByte);
        if (i == -1 || i2 <= i) {
            initialize(inputStream, j, readByte, i3, null);
            return;
        }
        throw new MemoryLimitException(i2, i);
    }

    public LZMAInputStream(InputStream inputStream, long j, byte b, int i) throws IOException {
        this.endReached = false;
        this.tempBuf = new byte[1];
        this.exception = null;
        initialize(inputStream, j, b, i, null);
    }

    public LZMAInputStream(InputStream inputStream, long j, byte b, int i, byte[] bArr) throws IOException {
        this.endReached = false;
        this.tempBuf = new byte[1];
        this.exception = null;
        initialize(inputStream, j, b, i, bArr);
    }

    public LZMAInputStream(InputStream inputStream, long j, int i, int i2, int i3, int i4, byte[] bArr) throws IOException {
        this.endReached = false;
        this.tempBuf = new byte[1];
        this.exception = null;
        initialize(inputStream, j, i, i2, i3, i4, bArr);
    }

    static /* synthetic */ Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (Throwable e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    private static int getDictSize(int i) {
        if (i >= 0) {
            if (i <= 2147483632) {
                if (i < 4096) {
                    i = 4096;
                }
                return (i + 15) & -16;
            }
        }
        throw new IllegalArgumentException("LZMA dictionary is too big for this implementation");
    }

    public static int getMemoryUsage(int i, byte b) throws UnsupportedOptionsException, CorruptedInputException {
        if (i >= 0) {
            if (i <= 2147483632) {
                int i2 = b & 255;
                if (i2 > AnalogMessageWriter.COMMAND) {
                    throw new CorruptedInputException("Invalid LZMA properties byte");
                }
                i2 %= 45;
                int i3 = i2 / 9;
                return getMemoryUsage(i, i2 - (i3 * 9), i3);
            }
        }
        throw new UnsupportedOptionsException("LZMA dictionary is too big for this implementation");
    }

    public static int getMemoryUsage(int i, int i2, int i3) {
        if (i2 >= 0 && i2 <= 8 && i3 >= 0) {
            if (i3 <= 4) {
                return ((getDictSize(i) / 1024) + 10) + ((1536 << (i2 + i3)) / 1024);
            }
        }
        throw new IllegalArgumentException("Invalid lc or lp");
    }

    private void initialize(InputStream inputStream, long j, byte b, int i, byte[] bArr) throws IOException {
        if (j < -1) {
            throw new UnsupportedOptionsException("Uncompressed size is too big");
        }
        int i2 = b & 255;
        if (i2 > AnalogMessageWriter.COMMAND) {
            throw new CorruptedInputException("Invalid LZMA properties byte");
        }
        int i3 = i2 / 45;
        i2 -= (i3 * 9) * 5;
        int i4 = i2 / 9;
        int i5 = i2 - (i4 * 9);
        if (i >= 0) {
            if (i <= 2147483632) {
                initialize(inputStream, j, i5, i4, i3, i, bArr);
                return;
            }
        }
        throw new UnsupportedOptionsException("LZMA dictionary is too big for this implementation");
    }

    private void initialize(InputStream inputStream, long j, int i, int i2, int i3, int i4, byte[] bArr) throws IOException {
        if (j >= -1 && i >= 0 && i <= 8 && i2 >= 0 && i2 <= 4 && i3 >= 0) {
            if (i3 <= 4) {
                this.in = inputStream;
                i4 = getDictSize(i4);
                if (j >= 0 && ((long) i4) > j) {
                    i4 = getDictSize((int) j);
                }
                this.lz = new LZDecoder(getDictSize(i4), bArr);
                this.rc = new RangeDecoderFromStream(inputStream);
                this.lzma = new LZMADecoder(this.lz, this.rc, i, i2, i3);
                this.remainingSize = j;
                return;
            }
        }
        throw new IllegalArgumentException();
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
                    i3 = 0;
                    if (i2 == 0) {
                        return 0;
                    }
                    if (this.in == null) {
                        throw new XZIOException("Stream closed");
                    } else if (this.exception != null) {
                        throw this.exception;
                    } else if (this.endReached) {
                        return -1;
                    } else {
                        while (i2 > 0) {
                            try {
                                int i4 = (this.remainingSize < 0 || this.remainingSize >= ((long) i2)) ? i2 : (int) this.remainingSize;
                                this.lz.setLimit(i4);
                                this.lzma.decode();
                            } catch (CorruptedInputException e) {
                                if (this.remainingSize == -1) {
                                    if (this.lzma.endMarkerDetected()) {
                                        this.endReached = true;
                                        this.rc.normalize();
                                    }
                                }
                                throw e;
                            } catch (IOException e2) {
                                this.exception = e2;
                                throw e2;
                            }
                            int flush = this.lz.flush(bArr, i);
                            i += flush;
                            i2 -= flush;
                            i3 += flush;
                            if (this.remainingSize >= 0) {
                                this.remainingSize -= (long) flush;
                                if (this.remainingSize == 0) {
                                    this.endReached = true;
                                }
                            }
                            if (this.endReached) {
                                if (this.rc.isFinished()) {
                                    if (!this.lz.hasPending()) {
                                        if (i3 == 0) {
                                            i3 = -1;
                                        }
                                        return i3;
                                    }
                                }
                                throw new CorruptedInputException();
                            }
                        }
                        return i3;
                    }
                }
            }
        }
        throw new IndexOutOfBoundsException();
    }
}
