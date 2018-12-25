package org.tukaani.xz.simple;

import android.support.v4.media.session.PlaybackStateCompat;

public final class IA64 implements SimpleFilter {
    private static final int[] BRANCH_TABLE = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 4, 6, 6, 0, 0, 7, 7, 4, 4, 0, 0, 4, 4, 0, 0};
    private final boolean isEncoder;
    private int pos;

    public IA64(boolean z, int i) {
        this.isEncoder = z;
        this.pos = i;
    }

    public int code(byte[] bArr, int i, int i2) {
        IA64 ia64 = this;
        int i3 = (i + i2) - 16;
        int i4 = i;
        while (i4 <= i3) {
            int i5 = BRANCH_TABLE[bArr[i4] & 31];
            int i6 = 0;
            int i7 = 5;
            while (i6 < 3) {
                int i8;
                int i9;
                if (((i5 >>> i6) & 1) == 0) {
                    i8 = i4;
                    i9 = i7;
                } else {
                    int i10 = i7 >>> 3;
                    int i11 = i7 & 7;
                    long j = 0;
                    int i12 = 0;
                    while (i12 < 6) {
                        i12++;
                        i4 = i4;
                        j |= (((long) bArr[(i4 + i10) + i12]) & 255) << (i12 * 8);
                    }
                    i8 = i4;
                    long j2 = j >>> i11;
                    if (((j2 >>> 37) & 15) == 5) {
                        if (((j2 >>> 9) & 7) == 0) {
                            i9 = i7;
                            int i13 = (((int) ((j2 >>> 13) & 1048575)) | ((((int) (j2 >>> 36)) & 1) << 20)) << 4;
                            long j3 = j2 & -77309403137L;
                            j2 = (long) ((ia64.isEncoder ? i13 + ((ia64.pos + i8) - i) : i13 - ((ia64.pos + i8) - i)) >>> 4);
                            long j4 = (j & ((long) ((1 << i11) - 1))) | (((j3 | ((j2 & 1048575) << 13)) | ((j2 & PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED) << 16)) << i11);
                            for (i4 = 0; i4 < 6; i4++) {
                                bArr[(i8 + i10) + i4] = (byte) ((int) (j4 >>> (i4 * 8)));
                            }
                        }
                    }
                    i9 = i7;
                }
                i6++;
                i7 = i9 + 41;
                i4 = i8;
            }
            i4 += 16;
        }
        i4 -= i;
        ia64.pos += i4;
        return i4;
    }
}
