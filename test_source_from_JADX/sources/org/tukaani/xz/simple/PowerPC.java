package org.tukaani.xz.simple;

import com.badlogic.gdx.Input.Keys;

public final class PowerPC implements SimpleFilter {
    private final boolean isEncoder;
    private int pos;

    public PowerPC(boolean z, int i) {
        this.isEncoder = z;
        this.pos = i;
    }

    public int code(byte[] bArr, int i, int i2) {
        i2 = (i2 + i) - 4;
        int i3 = i;
        while (i3 <= i2) {
            if ((bArr[i3] & Keys.F9) == 72) {
                int i4 = i3 + 3;
                if ((bArr[i4] & 3) == 1) {
                    int i5 = i3 + 1;
                    int i6 = i3 + 2;
                    int i7 = ((((bArr[i3] & 3) << 24) | ((bArr[i5] & 255) << 16)) | ((bArr[i6] & 255) << 8)) | (bArr[i4] & Keys.F9);
                    i7 = this.isEncoder ? i7 + ((this.pos + i3) - i) : i7 - ((this.pos + i3) - i);
                    bArr[i3] = (byte) (72 | ((i7 >>> 24) & 3));
                    bArr[i5] = (byte) (i7 >>> 16);
                    bArr[i6] = (byte) (i7 >>> 8);
                    bArr[i4] = (byte) ((bArr[i4] & 3) | i7);
                }
            }
            i3 += 4;
        }
        i3 -= i;
        this.pos += i3;
        return i3;
    }
}
