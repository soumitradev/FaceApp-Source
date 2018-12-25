package org.tukaani.xz.lz;

final class BT4 extends LZEncoder {
    private int cyclicPos = -1;
    private final int cyclicSize;
    private final int depthLimit;
    private final Hash234 hash;
    private int lzPos;
    private final Matches matches;
    private final int[] tree;

    BT4(int i, int i2, int i3, int i4, int i5, int i6) {
        super(i, i2, i3, i4, i5);
        this.cyclicSize = i + 1;
        this.lzPos = this.cyclicSize;
        this.hash = new Hash234(i);
        this.tree = new int[(this.cyclicSize * 2)];
        this.matches = new Matches(i4 - 1);
        if (i6 <= 0) {
            i6 = (i4 / 2) + 16;
        }
        this.depthLimit = i6;
    }

    static int getMemoryUsage(int i) {
        return (Hash234.getMemoryUsage(i) + (i / 128)) + 10;
    }

    private int movePos() {
        int movePos = movePos(this.niceLen, 4);
        if (movePos != 0) {
            int i = this.lzPos + 1;
            this.lzPos = i;
            if (i == Integer.MAX_VALUE) {
                int i2 = Integer.MAX_VALUE - this.cyclicSize;
                this.hash.normalize(i2);
                LZEncoder.normalize(this.tree, i2);
                this.lzPos -= i2;
            }
            i = this.cyclicPos + 1;
            this.cyclicPos = i;
            if (i == this.cyclicSize) {
                this.cyclicPos = 0;
            }
        }
        return movePos;
    }

    private void skip(int i, int i2) {
        int i3 = this.depthLimit;
        int i4 = (this.cyclicPos << 1) + 1;
        int i5 = this.cyclicPos << 1;
        int i6 = 0;
        int i7 = 0;
        while (true) {
            int i8 = this.lzPos - i2;
            int i9 = i3 - 1;
            if (i3 == 0) {
                break;
            } else if (i8 >= this.cyclicSize) {
                break;
            } else {
                i3 = ((this.cyclicPos - i8) + (i8 > this.cyclicPos ? this.cyclicSize : 0)) << 1;
                int min = Math.min(i6, i7);
                if (this.buf[(this.readPos + min) - i8] == this.buf[this.readPos + min]) {
                    do {
                        min++;
                        if (min == i) {
                            this.tree[i5] = this.tree[i3];
                            this.tree[i4] = this.tree[i3 + 1];
                            return;
                        }
                    } while (this.buf[(this.readPos + min) - i8] == this.buf[this.readPos + min]);
                }
                if ((this.buf[(this.readPos + min) - i8] & 255) < (this.buf[this.readPos + min] & 255)) {
                    this.tree[i5] = i2;
                    i3++;
                    i2 = this.tree[i3];
                    i5 = i3;
                    i7 = min;
                } else {
                    this.tree[i4] = i2;
                    i2 = this.tree[i3];
                    i4 = i3;
                    i6 = min;
                }
                i3 = i9;
            }
        }
        this.tree[i4] = 0;
        this.tree[i5] = 0;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public org.tukaani.xz.lz.Matches getMatches() {
        /*
        r17 = this;
        r0 = r17;
        r1 = r0.matches;
        r2 = 0;
        r1.count = r2;
        r1 = r0.matchLenMax;
        r3 = r0.niceLen;
        r4 = r17.movePos();
        if (r4 >= r1) goto L_0x001a;
    L_0x0011:
        if (r4 != 0) goto L_0x0016;
    L_0x0013:
        r1 = r0.matches;
        return r1;
    L_0x0016:
        if (r3 <= r4) goto L_0x001b;
    L_0x0018:
        r3 = r4;
        goto L_0x001b;
    L_0x001a:
        r4 = r1;
    L_0x001b:
        r1 = r0.hash;
        r5 = r0.buf;
        r6 = r0.readPos;
        r1.calcHashes(r5, r6);
        r1 = r0.lzPos;
        r5 = r0.hash;
        r5 = r5.getHash2Pos();
        r1 = r1 - r5;
        r5 = r0.lzPos;
        r6 = r0.hash;
        r6 = r6.getHash3Pos();
        r5 = r5 - r6;
        r6 = r0.hash;
        r6 = r6.getHash4Pos();
        r7 = r0.hash;
        r8 = r0.lzPos;
        r7.updateTables(r8);
        r7 = r0.cyclicSize;
        r8 = 2;
        r9 = 1;
        if (r1 >= r7) goto L_0x006b;
    L_0x0049:
        r7 = r0.buf;
        r10 = r0.readPos;
        r10 = r10 - r1;
        r7 = r7[r10];
        r10 = r0.buf;
        r11 = r0.readPos;
        r10 = r10[r11];
        if (r7 != r10) goto L_0x006b;
    L_0x0058:
        r7 = r0.matches;
        r7 = r7.len;
        r7[r2] = r8;
        r7 = r0.matches;
        r7 = r7.dist;
        r10 = r1 + -1;
        r7[r2] = r10;
        r7 = r0.matches;
        r7.count = r9;
        goto L_0x006c;
    L_0x006b:
        r8 = 0;
    L_0x006c:
        r7 = 3;
        if (r1 == r5) goto L_0x0094;
    L_0x006f:
        r10 = r0.cyclicSize;
        if (r5 >= r10) goto L_0x0094;
    L_0x0073:
        r10 = r0.buf;
        r11 = r0.readPos;
        r11 = r11 - r5;
        r10 = r10[r11];
        r11 = r0.buf;
        r12 = r0.readPos;
        r11 = r11[r12];
        if (r10 != r11) goto L_0x0094;
    L_0x0082:
        r1 = r0.matches;
        r1 = r1.dist;
        r8 = r0.matches;
        r10 = r8.count;
        r11 = r10 + 1;
        r8.count = r11;
        r8 = r5 + -1;
        r1[r10] = r8;
        r1 = r5;
        r8 = 3;
    L_0x0094:
        r5 = r0.matches;
        r5 = r5.count;
        if (r5 <= 0) goto L_0x00c3;
    L_0x009a:
        if (r8 >= r4) goto L_0x00b0;
    L_0x009c:
        r5 = r0.buf;
        r10 = r0.readPos;
        r10 = r10 + r8;
        r10 = r10 - r1;
        r5 = r5[r10];
        r10 = r0.buf;
        r11 = r0.readPos;
        r11 = r11 + r8;
        r10 = r10[r11];
        if (r5 != r10) goto L_0x00b0;
    L_0x00ad:
        r8 = r8 + 1;
        goto L_0x009a;
    L_0x00b0:
        r1 = r0.matches;
        r1 = r1.len;
        r5 = r0.matches;
        r5 = r5.count;
        r5 = r5 - r9;
        r1[r5] = r8;
        if (r8 < r3) goto L_0x00c3;
    L_0x00bd:
        r0.skip(r3, r6);
    L_0x00c0:
        r1 = r0.matches;
        return r1;
    L_0x00c3:
        if (r8 >= r7) goto L_0x00c6;
    L_0x00c5:
        goto L_0x00c7;
    L_0x00c6:
        r7 = r8;
    L_0x00c7:
        r1 = r0.depthLimit;
        r5 = r0.cyclicPos;
        r5 = r5 << r9;
        r5 = r5 + r9;
        r8 = r0.cyclicPos;
        r8 = r8 << r9;
        r11 = r7;
        r7 = 0;
        r10 = 0;
    L_0x00d3:
        r12 = r0.lzPos;
        r12 = r12 - r6;
        r13 = r1 + -1;
        if (r1 == 0) goto L_0x0182;
    L_0x00da:
        r1 = r0.cyclicSize;
        if (r12 < r1) goto L_0x00e0;
    L_0x00de:
        goto L_0x0182;
    L_0x00e0:
        r1 = r0.cyclicPos;
        r1 = r1 - r12;
        r14 = r0.cyclicPos;
        if (r12 <= r14) goto L_0x00ea;
    L_0x00e7:
        r14 = r0.cyclicSize;
        goto L_0x00eb;
    L_0x00ea:
        r14 = 0;
    L_0x00eb:
        r1 = r1 + r14;
        r1 = r1 << r9;
        r14 = java.lang.Math.min(r7, r10);
        r15 = r0.buf;
        r2 = r0.readPos;
        r2 = r2 + r14;
        r2 = r2 - r12;
        r2 = r15[r2];
        r15 = r0.buf;
        r9 = r0.readPos;
        r9 = r9 + r14;
        r9 = r15[r9];
        if (r2 != r9) goto L_0x014e;
    L_0x0102:
        r2 = 1;
        r14 = r14 + r2;
        if (r14 >= r4) goto L_0x0117;
    L_0x0106:
        r2 = r0.buf;
        r9 = r0.readPos;
        r9 = r9 + r14;
        r9 = r9 - r12;
        r2 = r2[r9];
        r9 = r0.buf;
        r15 = r0.readPos;
        r15 = r15 + r14;
        r9 = r9[r15];
        if (r2 == r9) goto L_0x0102;
    L_0x0117:
        if (r14 <= r11) goto L_0x014e;
    L_0x0119:
        r2 = r0.matches;
        r2 = r2.len;
        r9 = r0.matches;
        r9 = r9.count;
        r2[r9] = r14;
        r2 = r0.matches;
        r2 = r2.dist;
        r9 = r0.matches;
        r9 = r9.count;
        r11 = r12 + -1;
        r2[r9] = r11;
        r2 = r0.matches;
        r9 = r2.count;
        r15 = 1;
        r9 = r9 + r15;
        r2.count = r9;
        if (r14 < r3) goto L_0x014c;
    L_0x0139:
        r2 = r0.tree;
        r3 = r0.tree;
        r3 = r3[r1];
        r2[r8] = r3;
        r2 = r0.tree;
        r3 = r0.tree;
        r1 = r1 + r15;
        r1 = r3[r1];
        r2[r5] = r1;
        goto L_0x00c0;
    L_0x014c:
        r11 = r14;
        goto L_0x014f;
    L_0x014e:
        r15 = 1;
    L_0x014f:
        r2 = r0.buf;
        r9 = r0.readPos;
        r9 = r9 + r14;
        r9 = r9 - r12;
        r2 = r2[r9];
        r2 = r2 & 255;
        r9 = r0.buf;
        r12 = r0.readPos;
        r12 = r12 + r14;
        r9 = r9[r12];
        r9 = r9 & 255;
        if (r2 >= r9) goto L_0x0172;
    L_0x0164:
        r2 = r0.tree;
        r2[r8] = r6;
        r1 = r1 + 1;
        r2 = r0.tree;
        r2 = r2[r1];
        r8 = r1;
        r6 = r2;
        r10 = r14;
        goto L_0x017d;
    L_0x0172:
        r2 = r0.tree;
        r2[r5] = r6;
        r2 = r0.tree;
        r2 = r2[r1];
        r5 = r1;
        r6 = r2;
        r7 = r14;
    L_0x017d:
        r1 = r13;
        r2 = 0;
        r9 = 1;
        goto L_0x00d3;
    L_0x0182:
        r1 = r0.tree;
        r2 = 0;
        r1[r5] = r2;
        r1 = r0.tree;
        r1[r8] = r2;
        goto L_0x00c0;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.tukaani.xz.lz.BT4.getMatches():org.tukaani.xz.lz.Matches");
    }

    public void skip(int i) {
        while (true) {
            int i2 = i - 1;
            if (i > 0) {
                i = this.niceLen;
                int movePos = movePos();
                if (movePos < i) {
                    i = movePos == 0 ? i2 : movePos;
                }
                this.hash.calcHashes(this.buf, this.readPos);
                movePos = this.hash.getHash4Pos();
                this.hash.updateTables(this.lzPos);
                skip(i, movePos);
            } else {
                return;
            }
        }
    }
}
