package org.tukaani.xz.simple;

import com.badlogic.gdx.Input.Keys;

public final class X86 implements SimpleFilter {
    private static final boolean[] MASK_TO_ALLOWED_STATUS = new boolean[]{true, true, true, false, true, false, false, false};
    private static final int[] MASK_TO_BIT_NUMBER = new int[]{0, 1, 2, 2, 3, 3, 3, 3};
    private final boolean isEncoder;
    private int pos;
    private int prevMask = 0;

    public X86(boolean z, int i) {
        this.isEncoder = z;
        this.pos = i + 5;
    }

    private static boolean test86MSByte(byte b) {
        int i = b & 255;
        if (i != 0) {
            if (i != 255) {
                return false;
            }
        }
        return true;
    }

    public int code(byte[] bArr, int i, int i2) {
        int i3;
        i2 = (i2 + i) - 5;
        int i4 = i - 1;
        int i5 = i;
        while (true) {
            i3 = 0;
            if (i5 > i2) {
                break;
            }
            if ((bArr[i5] & Keys.F11) == 232) {
                i4 = i5 - i4;
                if ((i4 & -4) != 0) {
                    this.prevMask = 0;
                } else {
                    this.prevMask = (this.prevMask << (i4 - 1)) & 7;
                    if (this.prevMask != 0) {
                        if (MASK_TO_ALLOWED_STATUS[this.prevMask]) {
                            if (test86MSByte(bArr[(i5 + 4) - MASK_TO_BIT_NUMBER[this.prevMask]])) {
                            }
                        }
                        this.prevMask = (this.prevMask << 1) | 1;
                        i4 = i5;
                    }
                }
                i4 = i5 + 4;
                if (test86MSByte(bArr[i4])) {
                    i3 = i5 + 1;
                    int i6 = i5 + 2;
                    int i7 = i5 + 3;
                    int i8 = (((bArr[i3] & 255) | ((bArr[i6] & 255) << 8)) | ((bArr[i7] & 255) << 16)) | ((bArr[i4] & 255) << 24);
                    while (true) {
                        i8 = this.isEncoder ? i8 + ((this.pos + i5) - i) : i8 - ((this.pos + i5) - i);
                        if (this.prevMask == 0) {
                            break;
                        }
                        int i9 = MASK_TO_BIT_NUMBER[this.prevMask] * 8;
                        if (!test86MSByte((byte) (i8 >>> (24 - i9)))) {
                            break;
                        }
                        i8 ^= (1 << (32 - i9)) - 1;
                    }
                    bArr[i3] = (byte) i8;
                    bArr[i6] = (byte) (i8 >>> 8);
                    bArr[i7] = (byte) (i8 >>> 16);
                    bArr[i4] = (byte) ((((i8 >>> 24) & 1) - 1) ^ -1);
                    int i10 = i4;
                    i4 = i5;
                    i5 = i10;
                }
                this.prevMask = (this.prevMask << 1) | 1;
                i4 = i5;
            }
            i5++;
        }
        int i11 = i5 - i4;
        if ((i11 & -4) == 0) {
            i3 = this.prevMask << (i11 - 1);
        }
        this.prevMask = i3;
        i5 -= i;
        this.pos += i5;
        return i5;
    }
}
