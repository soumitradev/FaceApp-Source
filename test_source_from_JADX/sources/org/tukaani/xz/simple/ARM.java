package org.tukaani.xz.simple;

public final class ARM implements SimpleFilter {
    private final boolean isEncoder;
    private int pos;

    public ARM(boolean z, int i) {
        this.isEncoder = z;
        this.pos = i + 8;
    }

    public int code(byte[] bArr, int i, int i2) {
        i2 = (i2 + i) - 4;
        int i3 = i;
        while (i3 <= i2) {
            if ((bArr[i3 + 3] & 255) == 235) {
                int i4 = i3 + 2;
                int i5 = i3 + 1;
                int i6 = ((((bArr[i4] & 255) << 16) | ((bArr[i5] & 255) << 8)) | (bArr[i3] & 255)) << 2;
                i6 = (this.isEncoder ? i6 + ((this.pos + i3) - i) : i6 - ((this.pos + i3) - i)) >>> 2;
                bArr[i4] = (byte) (i6 >>> 16);
                bArr[i5] = (byte) (i6 >>> 8);
                bArr[i3] = (byte) i6;
            }
            i3 += 4;
        }
        i3 -= i;
        this.pos += i3;
        return i3;
    }
}
