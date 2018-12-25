package org.tukaani.xz.lzma;

import org.tukaani.xz.lz.LZEncoder;
import org.tukaani.xz.lz.Matches;
import org.tukaani.xz.rangecoder.RangeEncoder;

final class LZMAEncoderFast extends LZMAEncoder {
    private static int EXTRA_SIZE_AFTER = 272;
    private static int EXTRA_SIZE_BEFORE = 1;
    private Matches matches = null;

    LZMAEncoderFast(RangeEncoder rangeEncoder, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        int i9 = i4;
        super(rangeEncoder, LZEncoder.getInstance(i9, Math.max(i5, EXTRA_SIZE_BEFORE), EXTRA_SIZE_AFTER, i6, 273, i7, i8), i, i2, i3, i4, i6);
    }

    private boolean changePair(int i, int i2) {
        return i < (i2 >>> 7);
    }

    static int getMemoryUsage(int i, int i2, int i3) {
        return LZEncoder.getMemoryUsage(i, Math.max(i2, EXTRA_SIZE_BEFORE), EXTRA_SIZE_AFTER, 273, i3);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    int getNextSymbol() {
        /*
        r11 = this;
        r0 = r11.readAhead;
        r1 = -1;
        if (r0 != r1) goto L_0x000b;
    L_0x0005:
        r0 = r11.getMatches();
        r11.matches = r0;
    L_0x000b:
        r11.back = r1;
        r0 = r11.lz;
        r0 = r0.getAvail();
        r1 = 273; // 0x111 float:3.83E-43 double:1.35E-321;
        r0 = java.lang.Math.min(r0, r1);
        r1 = 2;
        r2 = 1;
        if (r0 >= r1) goto L_0x001e;
    L_0x001d:
        return r2;
    L_0x001e:
        r3 = 0;
        r4 = 0;
        r5 = 0;
        r6 = 0;
    L_0x0022:
        r7 = 4;
        if (r4 >= r7) goto L_0x0045;
    L_0x0025:
        r7 = r11.lz;
        r8 = r11.reps;
        r8 = r8[r4];
        r7 = r7.getMatchLen(r8, r0);
        if (r7 >= r1) goto L_0x0032;
    L_0x0031:
        goto L_0x0042;
    L_0x0032:
        r8 = r11.niceLen;
        if (r7 < r8) goto L_0x003e;
    L_0x0036:
        r11.back = r4;
        r0 = r7 + -1;
        r11.skip(r0);
        return r7;
    L_0x003e:
        if (r7 <= r5) goto L_0x0042;
    L_0x0040:
        r6 = r4;
        r5 = r7;
    L_0x0042:
        r4 = r4 + 1;
        goto L_0x0022;
    L_0x0045:
        r4 = r11.matches;
        r4 = r4.count;
        if (r4 <= 0) goto L_0x00ba;
    L_0x004b:
        r4 = r11.matches;
        r4 = r4.len;
        r8 = r11.matches;
        r8 = r8.count;
        r8 = r8 - r2;
        r4 = r4[r8];
        r8 = r11.matches;
        r8 = r8.dist;
        r9 = r11.matches;
        r9 = r9.count;
        r9 = r9 - r2;
        r8 = r8[r9];
        r9 = r11.niceLen;
        if (r4 < r9) goto L_0x006e;
    L_0x0065:
        r8 = r8 + r7;
        r11.back = r8;
        r0 = r4 + -1;
    L_0x006a:
        r11.skip(r0);
        return r4;
    L_0x006e:
        r9 = r11.matches;
        r9 = r9.count;
        if (r9 <= r2) goto L_0x00b2;
    L_0x0074:
        r9 = r11.matches;
        r9 = r9.len;
        r10 = r11.matches;
        r10 = r10.count;
        r10 = r10 - r1;
        r9 = r9[r10];
        r9 = r9 + r2;
        if (r4 != r9) goto L_0x00b2;
    L_0x0082:
        r9 = r11.matches;
        r9 = r9.dist;
        r10 = r11.matches;
        r10 = r10.count;
        r10 = r10 - r1;
        r9 = r9[r10];
        r9 = r11.changePair(r9, r8);
        if (r9 != 0) goto L_0x0094;
    L_0x0093:
        goto L_0x00b2;
    L_0x0094:
        r4 = r11.matches;
        r8 = r4.count;
        r8 = r8 - r2;
        r4.count = r8;
        r4 = r11.matches;
        r4 = r4.len;
        r8 = r11.matches;
        r8 = r8.count;
        r8 = r8 - r2;
        r4 = r4[r8];
        r8 = r11.matches;
        r8 = r8.dist;
        r9 = r11.matches;
        r9 = r9.count;
        r9 = r9 - r2;
        r8 = r8[r9];
        goto L_0x006e;
    L_0x00b2:
        if (r4 != r1) goto L_0x00bc;
    L_0x00b4:
        r9 = 128; // 0x80 float:1.794E-43 double:6.32E-322;
        if (r8 < r9) goto L_0x00bc;
    L_0x00b8:
        r4 = 1;
        goto L_0x00bc;
    L_0x00ba:
        r4 = 0;
        r8 = 0;
    L_0x00bc:
        if (r5 < r1) goto L_0x00db;
    L_0x00be:
        r9 = r5 + 1;
        if (r9 >= r4) goto L_0x00d3;
    L_0x00c2:
        r9 = r5 + 2;
        if (r9 < r4) goto L_0x00ca;
    L_0x00c6:
        r9 = 512; // 0x200 float:7.175E-43 double:2.53E-321;
        if (r8 >= r9) goto L_0x00d3;
    L_0x00ca:
        r9 = r5 + 3;
        if (r9 < r4) goto L_0x00db;
    L_0x00ce:
        r9 = 32768; // 0x8000 float:4.5918E-41 double:1.61895E-319;
        if (r8 < r9) goto L_0x00db;
    L_0x00d3:
        r11.back = r6;
        r0 = r5 + -1;
        r11.skip(r0);
        return r5;
    L_0x00db:
        if (r4 < r1) goto L_0x013e;
    L_0x00dd:
        if (r0 > r1) goto L_0x00e0;
    L_0x00df:
        return r2;
    L_0x00e0:
        r0 = r11.getMatches();
        r11.matches = r0;
        r0 = r11.matches;
        r0 = r0.count;
        if (r0 <= 0) goto L_0x011f;
    L_0x00ec:
        r0 = r11.matches;
        r0 = r0.len;
        r5 = r11.matches;
        r5 = r5.count;
        r5 = r5 - r2;
        r0 = r0[r5];
        r5 = r11.matches;
        r5 = r5.dist;
        r6 = r11.matches;
        r6 = r6.count;
        r6 = r6 - r2;
        r5 = r5[r6];
        if (r0 < r4) goto L_0x0106;
    L_0x0104:
        if (r5 < r8) goto L_0x011e;
    L_0x0106:
        r6 = r4 + 1;
        if (r0 != r6) goto L_0x0110;
    L_0x010a:
        r9 = r11.changePair(r8, r5);
        if (r9 == 0) goto L_0x011e;
    L_0x0110:
        if (r0 > r6) goto L_0x011e;
    L_0x0112:
        r0 = r0 + r2;
        if (r0 < r4) goto L_0x011f;
    L_0x0115:
        r0 = 3;
        if (r4 < r0) goto L_0x011f;
    L_0x0118:
        r0 = r11.changePair(r5, r8);
        if (r0 == 0) goto L_0x011f;
    L_0x011e:
        return r2;
    L_0x011f:
        r0 = r4 + -1;
        r0 = java.lang.Math.max(r0, r1);
    L_0x0125:
        if (r3 >= r7) goto L_0x0137;
    L_0x0127:
        r1 = r11.lz;
        r5 = r11.reps;
        r5 = r5[r3];
        r1 = r1.getMatchLen(r5, r0);
        if (r1 != r0) goto L_0x0134;
    L_0x0133:
        return r2;
    L_0x0134:
        r3 = r3 + 1;
        goto L_0x0125;
    L_0x0137:
        r8 = r8 + r7;
        r11.back = r8;
        r0 = r4 + -2;
        goto L_0x006a;
    L_0x013e:
        return r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.tukaani.xz.lzma.LZMAEncoderFast.getNextSymbol():int");
    }
}
