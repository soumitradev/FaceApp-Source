package ar.com.hjg.pngj.pixels;

import ar.com.hjg.pngj.PngjOutputException;
import java.io.OutputStream;
import java.util.zip.Deflater;

public class CompressorStreamLz4 extends CompressorStream {
    private static final int MAX_BUFFER_SIZE = 16000;
    private byte[] buf;
    private final int buffer_size;
    private int inbuf;
    private final DeflaterEstimatorLz4 lz4;

    public CompressorStreamLz4(OutputStream os, int maxBlockLen, long totalLen) {
        super(os, maxBlockLen, totalLen);
        this.inbuf = 0;
        this.lz4 = new DeflaterEstimatorLz4();
        long j = 16000;
        if (totalLen <= 16000) {
            j = totalLen;
        }
        this.buffer_size = (int) j;
    }

    public CompressorStreamLz4(OutputStream os, int maxBlockLen, long totalLen, Deflater def) {
        this(os, maxBlockLen, totalLen);
    }

    public CompressorStreamLz4(OutputStream os, int maxBlockLen, long totalLen, int deflaterCompLevel, int deflaterStrategy) {
        this(os, maxBlockLen, totalLen);
    }

    public void mywrite(byte[] b, int off, int len) {
        if (len != 0) {
            if (!this.done) {
                if (!this.closed) {
                    this.bytesIn += (long) len;
                    while (len > 0) {
                        if (this.inbuf != 0 || (len < MAX_BUFFER_SIZE && this.bytesIn != this.totalbytes)) {
                            if (this.buf == null) {
                                this.buf = new byte[this.buffer_size];
                            }
                            int len1 = this.inbuf + len <= this.buffer_size ? len : this.buffer_size - this.inbuf;
                            if (len1 > 0) {
                                System.arraycopy(b, off, this.buf, this.inbuf, len1);
                            }
                            this.inbuf += len1;
                            len -= len1;
                            off += len1;
                            if (this.inbuf == this.buffer_size) {
                                compressFromBuffer();
                            }
                        } else {
                            this.bytesOut += (long) this.lz4.compressEstim(b, off, len);
                            len = 0;
                        }
                    }
                    return;
                }
            }
            throw new PngjOutputException("write beyond end of stream");
        }
    }

    void compressFromBuffer() {
        if (this.inbuf > 0) {
            this.bytesOut += (long) this.lz4.compressEstim(this.buf, 0, this.inbuf);
            this.inbuf = 0;
        }
    }

    public void done() {
        if (!this.done) {
            compressFromBuffer();
            this.done = true;
            flush();
        }
    }

    public void close() {
        done();
        if (!this.closed) {
            super.close();
            this.buf = null;
        }
    }

    public void reset() {
        done();
        super.reset();
    }
}
