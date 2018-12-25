package ar.com.hjg.pngj.pixels;

import ar.com.hjg.pngj.PngjOutputException;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public abstract class CompressorStream extends FilterOutputStream {
    protected int block = -1;
    public final int blockLen;
    protected long bytesIn = 0;
    protected long bytesOut = 0;
    boolean closed = false;
    protected boolean done = false;
    private byte[] firstBytes;
    protected OutputStream os;
    protected boolean storeFirstByte = false;
    public final long totalbytes;

    public abstract void done();

    protected abstract void mywrite(byte[] bArr, int i, int i2);

    public CompressorStream(OutputStream os, int blockLen, long totalbytes) {
        super(os);
        if (blockLen < 0) {
            blockLen = 4096;
        }
        if (totalbytes < 0) {
            totalbytes = Long.MAX_VALUE;
        }
        if (blockLen >= 1) {
            if (totalbytes >= 1) {
                this.os = os;
                this.blockLen = blockLen;
                this.totalbytes = totalbytes;
                return;
            }
        }
        throw new RuntimeException(" maxBlockLen or totalLen invalid");
    }

    public void close() {
        done();
        this.closed = true;
    }

    public final void write(byte[] b, int off, int len) {
        this.block++;
        if (len <= this.blockLen) {
            mywrite(b, off, len);
            if (this.storeFirstByte && this.block < this.firstBytes.length) {
                this.firstBytes[this.block] = b[off];
            }
        } else {
            while (len > 0) {
                mywrite(b, off, this.blockLen);
                off += this.blockLen;
                len -= this.blockLen;
            }
        }
        if (this.bytesIn >= this.totalbytes) {
            done();
        }
    }

    public final void write(byte[] b) {
        write(b, 0, b.length);
    }

    public void write(int b) throws IOException {
        throw new PngjOutputException("should not be used");
    }

    public void reset() {
        reset(this.os);
    }

    public void reset(OutputStream os) {
        if (this.closed) {
            throw new PngjOutputException("cannot reset, discarded object");
        }
        done();
        this.bytesIn = 0;
        this.bytesOut = 0;
        this.block = -1;
        this.done = false;
        this.os = os;
    }

    public final double getCompressionRatio() {
        return this.bytesOut == 0 ? 1.0d : ((double) this.bytesOut) / ((double) this.bytesIn);
    }

    public final long getBytesRaw() {
        return this.bytesIn;
    }

    public final long getBytesCompressed() {
        return this.bytesOut;
    }

    public OutputStream getOs() {
        return this.os;
    }

    public void flush() {
        if (this.os != null) {
            try {
                this.os.flush();
            } catch (Throwable e) {
                throw new PngjOutputException(e);
            }
        }
    }

    public boolean isClosed() {
        return this.closed;
    }

    public boolean isDone() {
        return this.done;
    }

    public byte[] getFirstBytes() {
        return this.firstBytes;
    }

    public void setStoreFirstByte(boolean storeFirstByte, int nblocks) {
        this.storeFirstByte = storeFirstByte;
        if (!this.storeFirstByte) {
            this.firstBytes = null;
        } else if (this.firstBytes == null || this.firstBytes.length < nblocks) {
            this.firstBytes = new byte[nblocks];
        }
    }
}
