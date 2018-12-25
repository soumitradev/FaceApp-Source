package org.tukaani.xz.lz;

import java.io.DataInputStream;
import java.io.IOException;
import org.tukaani.xz.CorruptedInputException;

public final class LZDecoder {
    private final byte[] buf;
    private int full = 0;
    private int limit = 0;
    private int pendingDist = 0;
    private int pendingLen = 0;
    private int pos = 0;
    private int start = 0;

    public LZDecoder(int i, byte[] bArr) {
        this.buf = new byte[i];
        if (bArr != null) {
            this.pos = Math.min(bArr.length, i);
            this.full = this.pos;
            this.start = this.pos;
            System.arraycopy(bArr, bArr.length - this.pos, this.buf, 0, this.pos);
        }
    }

    public void copyUncompressed(DataInputStream dataInputStream, int i) throws IOException {
        i = Math.min(this.buf.length - this.pos, i);
        dataInputStream.readFully(this.buf, this.pos, i);
        this.pos += i;
        if (this.full < this.pos) {
            this.full = this.pos;
        }
    }

    public int flush(byte[] bArr, int i) {
        int i2 = this.pos - this.start;
        if (this.pos == this.buf.length) {
            this.pos = 0;
        }
        System.arraycopy(this.buf, this.start, bArr, i, i2);
        this.start = this.pos;
        return i2;
    }

    public int getByte(int i) {
        int i2 = (this.pos - i) - 1;
        if (i >= this.pos) {
            i2 += this.buf.length;
        }
        return this.buf[i2] & 255;
    }

    public int getPos() {
        return this.pos;
    }

    public boolean hasPending() {
        return this.pendingLen > 0;
    }

    public boolean hasSpace() {
        return this.pos < this.limit;
    }

    public void putByte(byte b) {
        byte[] bArr = this.buf;
        int i = this.pos;
        this.pos = i + 1;
        bArr[i] = b;
        if (this.full < this.pos) {
            this.full = this.pos;
        }
    }

    public void repeat(int i, int i2) throws IOException {
        if (i >= 0) {
            if (i < this.full) {
                int min = Math.min(this.limit - this.pos, i2);
                this.pendingLen = i2 - min;
                this.pendingDist = i;
                i2 = (this.pos - i) - 1;
                if (i >= this.pos) {
                    i2 += this.buf.length;
                }
                do {
                    byte[] bArr = this.buf;
                    int i3 = this.pos;
                    this.pos = i3 + 1;
                    int i4 = i2 + 1;
                    bArr[i3] = this.buf[i2];
                    i2 = i4 == this.buf.length ? 0 : i4;
                    min--;
                } while (min > 0);
                if (this.full < this.pos) {
                    this.full = this.pos;
                    return;
                }
                return;
            }
        }
        throw new CorruptedInputException();
    }

    public void repeatPending() throws IOException {
        if (this.pendingLen > 0) {
            repeat(this.pendingDist, this.pendingLen);
        }
    }

    public void reset() {
        this.start = 0;
        this.pos = 0;
        this.full = 0;
        this.limit = 0;
        this.buf[this.buf.length - 1] = (byte) 0;
    }

    public void setLimit(int i) {
        if (this.buf.length - this.pos <= i) {
            this.limit = this.buf.length;
        } else {
            this.limit = this.pos + i;
        }
    }
}
