package org.tukaani.xz.lz;

import java.io.IOException;
import java.io.OutputStream;

public abstract class LZEncoder {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int MF_BT4 = 20;
    public static final int MF_HC4 = 4;
    static /* synthetic */ Class class$org$tukaani$xz$lz$LZEncoder = class$("org.tukaani.xz.lz.LZEncoder");
    final byte[] buf;
    private boolean finishing = false;
    private final int keepSizeAfter;
    private final int keepSizeBefore;
    final int matchLenMax;
    final int niceLen;
    private int pendingSize = 0;
    private int readLimit = -1;
    int readPos = -1;
    private int writePos = 0;

    static {
        if (class$org$tukaani$xz$lz$LZEncoder == null) {
        } else {
            Class cls = class$org$tukaani$xz$lz$LZEncoder;
        }
    }

    LZEncoder(int i, int i2, int i3, int i4, int i5) {
        this.buf = new byte[getBufSize(i, i2, i3, i5)];
        this.keepSizeBefore = i2 + i;
        this.keepSizeAfter = i3 + i5;
        this.matchLenMax = i5;
        this.niceLen = i4;
    }

    static /* synthetic */ Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (Throwable e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    private static int getBufSize(int i, int i2, int i3, int i4) {
        return ((i2 + i) + (i3 + i4)) + Math.min((i / 2) + 262144, 536870912);
    }

    public static LZEncoder getInstance(int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        if (i6 == 4) {
            return new HC4(i, i2, i3, i4, i5, i7);
        }
        if (i6 == 20) {
            return new BT4(i, i2, i3, i4, i5, i7);
        }
        throw new IllegalArgumentException();
    }

    public static int getMemoryUsage(int i, int i2, int i3, int i4, int i5) {
        i2 = (getBufSize(i, i2, i3, i4) / 1024) + 10;
        if (i5 == 4) {
            i = HC4.getMemoryUsage(i);
        } else if (i5 != 20) {
            throw new IllegalArgumentException();
        } else {
            i = BT4.getMemoryUsage(i);
        }
        return i2 + i;
    }

    private void moveWindow() {
        int i = ((this.readPos + 1) - this.keepSizeBefore) & -16;
        System.arraycopy(this.buf, i, this.buf, 0, this.writePos - i);
        this.readPos -= i;
        this.readLimit -= i;
        this.writePos -= i;
    }

    static void normalize(int[] iArr, int i) {
        for (int i2 = 0; i2 < iArr.length; i2++) {
            if (iArr[i2] <= i) {
                iArr[i2] = 0;
            } else {
                iArr[i2] = iArr[i2] - i;
            }
        }
    }

    private void processPendingBytes() {
        if (this.pendingSize > 0 && this.readPos < this.readLimit) {
            this.readPos -= this.pendingSize;
            int i = this.pendingSize;
            this.pendingSize = 0;
            skip(i);
        }
    }

    public void copyUncompressed(OutputStream outputStream, int i, int i2) throws IOException {
        outputStream.write(this.buf, (this.readPos + 1) - i, i2);
    }

    public int fillWindow(byte[] bArr, int i, int i2) {
        if (this.readPos >= this.buf.length - this.keepSizeAfter) {
            moveWindow();
        }
        if (i2 > this.buf.length - this.writePos) {
            i2 = this.buf.length - this.writePos;
        }
        System.arraycopy(bArr, i, this.buf, this.writePos, i2);
        this.writePos += i2;
        if (this.writePos >= this.keepSizeAfter) {
            this.readLimit = this.writePos - this.keepSizeAfter;
        }
        processPendingBytes();
        return i2;
    }

    public int getAvail() {
        return this.writePos - this.readPos;
    }

    public int getByte(int i) {
        return this.buf[this.readPos - i] & 255;
    }

    public int getByte(int i, int i2) {
        return this.buf[(this.readPos + i) - i2] & 255;
    }

    public int getMatchLen(int i, int i2) {
        int i3 = (this.readPos - i) - 1;
        i = 0;
        while (i < i2 && this.buf[this.readPos + i] == this.buf[i3 + i]) {
            i++;
        }
        return i;
    }

    public int getMatchLen(int i, int i2, int i3) {
        int i4 = this.readPos + i;
        i = (i4 - i2) - 1;
        i2 = 0;
        while (i2 < i3 && this.buf[i4 + i2] == this.buf[i + i2]) {
            i2++;
        }
        return i2;
    }

    public abstract Matches getMatches();

    public int getPos() {
        return this.readPos;
    }

    public boolean hasEnoughData(int i) {
        return this.readPos - i < this.readLimit;
    }

    public boolean isStarted() {
        return this.readPos != -1;
    }

    int movePos(int i, int i2) {
        this.readPos++;
        int i3 = this.writePos - this.readPos;
        if (i3 >= i) {
            return i3;
        }
        if (i3 >= i2 && this.finishing) {
            return i3;
        }
        this.pendingSize++;
        return 0;
    }

    public void setFinishing() {
        this.readLimit = this.writePos - 1;
        this.finishing = true;
        processPendingBytes();
    }

    public void setFlushing() {
        this.readLimit = this.writePos - 1;
        processPendingBytes();
    }

    public void setPresetDict(int i, byte[] bArr) {
        if (bArr != null) {
            i = Math.min(bArr.length, i);
            System.arraycopy(bArr, bArr.length - i, this.buf, 0, i);
            this.writePos += i;
            skip(i);
        }
    }

    public abstract void skip(int i);

    public boolean verifyMatches(Matches matches) {
        int min = Math.min(getAvail(), this.matchLenMax);
        for (int i = 0; i < matches.count; i++) {
            if (getMatchLen(matches.dist[i], min) != matches.len[i]) {
                return false;
            }
        }
        return true;
    }
}
