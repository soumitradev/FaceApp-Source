package org.tukaani.xz;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.tukaani.xz.check.Check;
import org.tukaani.xz.common.DecoderUtil;
import org.tukaani.xz.common.StreamFlags;
import org.tukaani.xz.index.IndexHash;

public class SingleXZInputStream extends InputStream {
    private BlockInputStream blockDecoder;
    private Check check;
    private boolean endReached;
    private IOException exception;
    private InputStream in;
    private final IndexHash indexHash;
    private int memoryLimit;
    private StreamFlags streamHeaderFlags;
    private final byte[] tempBuf;

    public SingleXZInputStream(InputStream inputStream) throws IOException {
        this.blockDecoder = null;
        this.indexHash = new IndexHash();
        this.endReached = false;
        this.exception = null;
        this.tempBuf = new byte[1];
        initialize(inputStream, -1);
    }

    public SingleXZInputStream(InputStream inputStream, int i) throws IOException {
        this.blockDecoder = null;
        this.indexHash = new IndexHash();
        this.endReached = false;
        this.exception = null;
        this.tempBuf = new byte[1];
        initialize(inputStream, i);
    }

    SingleXZInputStream(InputStream inputStream, int i, byte[] bArr) throws IOException {
        this.blockDecoder = null;
        this.indexHash = new IndexHash();
        this.endReached = false;
        this.exception = null;
        this.tempBuf = new byte[1];
        initialize(inputStream, i, bArr);
    }

    private void initialize(InputStream inputStream, int i) throws IOException {
        byte[] bArr = new byte[12];
        new DataInputStream(inputStream).readFully(bArr);
        initialize(inputStream, i, bArr);
    }

    private void initialize(InputStream inputStream, int i, byte[] bArr) throws IOException {
        this.in = inputStream;
        this.memoryLimit = i;
        this.streamHeaderFlags = DecoderUtil.decodeStreamHeader(bArr);
        this.check = Check.getInstance(this.streamHeaderFlags.checkType);
    }

    private void validateStreamFooter() throws IOException {
        byte[] bArr = new byte[12];
        new DataInputStream(this.in).readFully(bArr);
        StreamFlags decodeStreamFooter = DecoderUtil.decodeStreamFooter(bArr);
        if (DecoderUtil.areStreamFlagsEqual(this.streamHeaderFlags, decodeStreamFooter)) {
            if (this.indexHash.getIndexSize() == decodeStreamFooter.backwardSize) {
                return;
            }
        }
        throw new CorruptedInputException("XZ Stream Footer does not match Stream Header");
    }

    public int available() throws IOException {
        if (this.in == null) {
            throw new XZIOException("Stream closed");
        } else if (this.exception == null) {
            return this.blockDecoder == null ? 0 : this.blockDecoder.available();
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

    public String getCheckName() {
        return this.check.getName();
    }

    public int getCheckType() {
        return this.streamHeaderFlags.checkType;
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
                                if (this.blockDecoder == null) {
                                    try {
                                        this.blockDecoder = new BlockInputStream(this.in, this.check, this.memoryLimit, -1, -1);
                                    } catch (IndexIndicatorException e) {
                                        this.indexHash.validate(this.in);
                                        validateStreamFooter();
                                        this.endReached = true;
                                        return i3 > 0 ? i3 : -1;
                                    }
                                }
                                int read = this.blockDecoder.read(bArr, i, i2);
                                if (read > 0) {
                                    i3 += read;
                                    i += read;
                                    i2 -= read;
                                } else if (read == -1) {
                                    this.indexHash.add(this.blockDecoder.getUnpaddedSize(), this.blockDecoder.getUncompressedSize());
                                    this.blockDecoder = null;
                                }
                            } catch (IOException e2) {
                                this.exception = e2;
                                if (i3 == 0) {
                                    throw e2;
                                }
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
