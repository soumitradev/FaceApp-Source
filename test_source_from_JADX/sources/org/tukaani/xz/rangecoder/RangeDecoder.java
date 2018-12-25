package org.tukaani.xz.rangecoder;

import java.io.IOException;

public abstract class RangeDecoder extends RangeCoder {
    int code = 0;
    int range = 0;

    public int decodeBit(short[] sArr, int i) throws IOException {
        normalize();
        short s = sArr[i];
        int i2 = (this.range >>> 11) * s;
        if ((this.code ^ Integer.MIN_VALUE) < (Integer.MIN_VALUE ^ i2)) {
            this.range = i2;
            sArr[i] = (short) (s + ((2048 - s) >>> 5));
            return 0;
        }
        this.range -= i2;
        this.code -= i2;
        sArr[i] = (short) (s - (s >>> 5));
        return 1;
    }

    public int decodeBitTree(short[] sArr) throws IOException {
        int i = 1;
        do {
            i = decodeBit(sArr, i) | (i << 1);
        } while (i < sArr.length);
        return i - sArr.length;
    }

    public int decodeDirectBits(int i) throws IOException {
        int i2 = 0;
        do {
            normalize();
            this.range >>>= 1;
            int i3 = (this.code - this.range) >>> 31;
            this.code -= this.range & (i3 - 1);
            i2 = (i2 << 1) | (1 - i3);
            i--;
        } while (i != 0);
        return i2;
    }

    public int decodeReverseBitTree(short[] sArr) throws IOException {
        int i = 1;
        int i2 = 0;
        int i3 = 0;
        while (true) {
            int decodeBit = decodeBit(sArr, i);
            i = (i << 1) | decodeBit;
            int i4 = i3 + 1;
            i2 |= decodeBit << i3;
            if (i >= sArr.length) {
                return i2;
            }
            i3 = i4;
        }
    }

    public abstract void normalize() throws IOException;
}
