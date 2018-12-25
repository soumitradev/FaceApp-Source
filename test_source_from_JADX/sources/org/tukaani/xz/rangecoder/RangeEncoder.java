package org.tukaani.xz.rangecoder;

import android.support.v4.internal.view.SupportMenu;
import android.support.v4.view.ViewCompat;
import java.io.IOException;
import java.io.OutputStream;

public final class RangeEncoder extends RangeCoder {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int BIT_PRICE_SHIFT_BITS = 4;
    private static final int MOVE_REDUCING_BITS = 4;
    static /* synthetic */ Class class$org$tukaani$xz$rangecoder$RangeEncoder = class$("org.tukaani.xz.rangecoder.RangeEncoder");
    private static final int[] prices = new int[128];
    private final byte[] buf;
    private int bufPos;
    private byte cache;
    private int cacheSize;
    private long low;
    private int range;

    static {
        if (class$org$tukaani$xz$rangecoder$RangeEncoder == null) {
        } else {
            Class cls = class$org$tukaani$xz$rangecoder$RangeEncoder;
        }
        for (int i = 8; i < 2048; i += 16) {
            int i2 = i;
            int i3 = 0;
            for (int i4 = 0; i4 < 4; i4++) {
                i2 *= i2;
                i3 <<= 1;
                while ((SupportMenu.CATEGORY_MASK & i2) != 0) {
                    i2 >>>= 1;
                    i3++;
                }
            }
            prices[i >> 4] = 161 - i3;
        }
    }

    public RangeEncoder(int i) {
        this.buf = new byte[i];
        reset();
    }

    static /* synthetic */ Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (Throwable e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    public static int getBitPrice(int i, int i2) {
        return prices[(i ^ ((-i2) & 2047)) >>> 4];
    }

    public static int getBitTreePrice(short[] sArr, int i) {
        i |= sArr.length;
        int i2 = 0;
        do {
            int i3 = i & 1;
            i >>>= 1;
            i2 += getBitPrice(sArr[i], i3);
        } while (i != 1);
        return i2;
    }

    public static int getDirectBitsPrice(int i) {
        return i << 4;
    }

    public static int getReverseBitTreePrice(short[] sArr, int i) {
        i |= sArr.length;
        int i2 = 0;
        int i3 = 1;
        do {
            int i4 = i & 1;
            i >>>= 1;
            i2 += getBitPrice(sArr[i3], i4);
            i3 = (i3 << 1) | i4;
        } while (i != 1);
        return i2;
    }

    private void shiftLow() {
        int i = (int) (this.low >>> 32);
        if (i != 0 || this.low < 4278190080L) {
            int i2 = this.cache;
            int i3;
            do {
                byte[] bArr = this.buf;
                int i4 = this.bufPos;
                this.bufPos = i4 + 1;
                bArr[i4] = (byte) (i2 + i);
                i2 = 255;
                i3 = this.cacheSize - 1;
                this.cacheSize = i3;
            } while (i3 != 0);
            this.cache = (byte) ((int) (this.low >>> 24));
        }
        this.cacheSize++;
        this.low = (this.low & 16777215) << 8;
    }

    public void encodeBit(short[] sArr, int i, int i2) {
        short s = sArr[i];
        int i3 = (this.range >>> 11) * s;
        if (i2 == 0) {
            this.range = i3;
            sArr[i] = (short) (s + ((2048 - s) >>> 5));
        } else {
            this.low += ((long) i3) & 4294967295L;
            this.range -= i3;
            sArr[i] = (short) (s - (s >>> 5));
        }
        if ((this.range & ViewCompat.MEASURED_STATE_MASK) == 0) {
            this.range <<= 8;
            shiftLow();
        }
    }

    public void encodeBitTree(short[] sArr, int i) {
        int length = sArr.length;
        int i2 = 1;
        do {
            length >>>= 1;
            int i3 = i & length;
            encodeBit(sArr, i2, i3);
            i2 <<= 1;
            if (i3 != 0) {
                i2 |= 1;
                continue;
            }
        } while (length != 1);
    }

    public void encodeDirectBits(int i, int i2) {
        do {
            this.range >>>= 1;
            i2--;
            this.low += (long) (this.range & (0 - ((i >>> i2) & 1)));
            if ((this.range & ViewCompat.MEASURED_STATE_MASK) == 0) {
                this.range <<= 8;
                shiftLow();
                continue;
            }
        } while (i2 != 0);
    }

    public void encodeReverseBitTree(short[] sArr, int i) {
        i |= sArr.length;
        int i2 = 1;
        do {
            int i3 = i & 1;
            i >>>= 1;
            encodeBit(sArr, i2, i3);
            i2 = (i2 << 1) | i3;
        } while (i != 1);
    }

    public int finish() {
        for (int i = 0; i < 5; i++) {
            shiftLow();
        }
        return this.bufPos;
    }

    public int getPendingSize() {
        return ((this.bufPos + this.cacheSize) + 5) - 1;
    }

    public void reset() {
        this.low = 0;
        this.range = -1;
        this.cache = (byte) 0;
        this.cacheSize = 1;
        this.bufPos = 0;
    }

    public void write(OutputStream outputStream) throws IOException {
        outputStream.write(this.buf, 0, this.bufPos);
    }
}
