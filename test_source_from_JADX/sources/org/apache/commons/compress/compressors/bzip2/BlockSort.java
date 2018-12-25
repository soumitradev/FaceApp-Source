package org.apache.commons.compress.compressors.bzip2;

import android.support.v4.media.session.PlaybackStateCompat;
import com.facebook.internal.NativeProtocol;
import java.util.BitSet;
import name.antonsmirnov.firmata.message.ReportFirmwareVersionMessage;

class BlockSort {
    private static final int CLEARMASK = -2097153;
    private static final int DEPTH_THRESH = 10;
    private static final int FALLBACK_QSORT_SMALL_THRESH = 10;
    private static final int FALLBACK_QSORT_STACK_SIZE = 100;
    private static final int[] INCS = new int[]{1, 4, 13, 40, ReportFirmwareVersionMessage.COMMAND, 364, 1093, 3280, 9841, 29524, 88573, 265720, 797161, 2391484};
    private static final int QSORT_STACK_SIZE = 1000;
    private static final int SETMASK = 2097152;
    private static final int SMALL_THRESH = 20;
    private static final int STACK_SIZE = 1000;
    private static final int WORK_FACTOR = 30;
    private int[] eclass;
    private boolean firstAttempt;
    private final int[] ftab = new int[NativeProtocol.MESSAGE_GET_ACCESS_TOKEN_REPLY];
    private final boolean[] mainSort_bigDone = new boolean[256];
    private final int[] mainSort_copy = new int[256];
    private final int[] mainSort_runningOrder = new int[256];
    private final char[] quadrant;
    private final int[] stack_dd = new int[1000];
    private final int[] stack_hh = new int[1000];
    private final int[] stack_ll = new int[1000];
    private int workDone;
    private int workLimit;

    BlockSort(Data data) {
        this.quadrant = data.sfmap;
    }

    void blockSort(Data data, int last) {
        this.workLimit = last * 30;
        int i = 0;
        this.workDone = 0;
        this.firstAttempt = true;
        if (last + 1 < 10000) {
            fallbackSort(data, last);
        } else {
            mainSort(data, last);
            if (this.firstAttempt && this.workDone > this.workLimit) {
                fallbackSort(data, last);
            }
        }
        int[] fmap = data.fmap;
        data.origPtr = -1;
        while (i <= last) {
            if (fmap[i] == 0) {
                data.origPtr = i;
                return;
            }
            i++;
        }
    }

    final void fallbackSort(Data data, int last) {
        int i;
        int i2 = 0;
        data.block[0] = data.block[last + 1];
        fallbackSort(data.fmap, data.block, last + 1);
        for (i = 0; i < last + 1; i++) {
            int[] iArr = data.fmap;
            iArr[i] = iArr[i] - 1;
        }
        while (true) {
            i = i2;
            if (i >= last + 1) {
                return;
            }
            if (data.fmap[i] == -1) {
                data.fmap[i] = last;
                return;
            }
            i2 = i + 1;
        }
    }

    private void fallbackSimpleSort(int[] fmap, int[] eclass, int lo, int hi) {
        if (lo != hi) {
            int i;
            int tmp;
            int ec_tmp;
            int j;
            if (hi - lo > 3) {
                for (i = hi - 4; i >= lo; i--) {
                    tmp = fmap[i];
                    ec_tmp = eclass[tmp];
                    j = i + 4;
                    while (j <= hi && ec_tmp > eclass[fmap[j]]) {
                        fmap[j - 4] = fmap[j];
                        j += 4;
                    }
                    fmap[j - 4] = tmp;
                }
            }
            for (i = hi - 1; i >= lo; i--) {
                tmp = fmap[i];
                ec_tmp = eclass[tmp];
                j = i + 1;
                while (j <= hi && ec_tmp > eclass[fmap[j]]) {
                    fmap[j - 1] = fmap[j];
                    j++;
                }
                fmap[j - 1] = tmp;
            }
        }
    }

    private void fswap(int[] fmap, int zz1, int zz2) {
        int zztmp = fmap[zz1];
        fmap[zz1] = fmap[zz2];
        fmap[zz2] = zztmp;
    }

    private void fvswap(int[] fmap, int yyp1, int yyp2, int yyn) {
        while (yyn > 0) {
            fswap(fmap, yyp1, yyp2);
            yyp1++;
            yyp2++;
            yyn--;
        }
    }

    private int fmin(int a, int b) {
        return a < b ? a : b;
    }

    private void fpush(int sp, int lz, int hz) {
        this.stack_ll[sp] = lz;
        this.stack_hh[sp] = hz;
    }

    private int[] fpop(int sp) {
        return new int[]{this.stack_ll[sp], this.stack_hh[sp]};
    }

    private void fallbackQSort3(int[] fmap, int[] eclass, int loSt, int hiSt) {
        int[] iArr = fmap;
        int[] iArr2 = eclass;
        long r = 0;
        int sp = 0 + 1;
        fpush(0, loSt, hiSt);
        while (sp > 0) {
            int sp2 = sp - 1;
            int[] s = fpop(sp2);
            int lo = s[0];
            int hi = s[1];
            if (hi - lo < 10) {
                fallbackSimpleSort(iArr, iArr2, lo, hi);
                sp = sp2;
            } else {
                int unLo;
                int gtHi;
                int ltLo;
                int i;
                int n;
                long med;
                int i2;
                int i3;
                int n2;
                int m;
                long j = ((7621 * r) + 1) % PlaybackStateCompat.ACTION_PREPARE_FROM_MEDIA_ID;
                long r3 = j % 3;
                if (r3 == 0) {
                    r = (long) iArr2[iArr[lo]];
                } else if (r3 == 1) {
                    r = (long) iArr2[iArr[(lo + hi) >>> 1]];
                } else {
                    r = (long) iArr2[iArr[hi]];
                    unLo = lo;
                    gtHi = hi;
                    ltLo = lo;
                    sp = hi;
                    while (true) {
                        if (unLo <= sp) {
                            i = sp;
                        } else {
                            i = sp;
                            n = iArr2[iArr[unLo]] - ((int) r);
                            if (n == 0) {
                                fswap(iArr, unLo, ltLo);
                                ltLo++;
                                unLo++;
                                sp = i;
                            } else if (n > 0) {
                                med = r;
                                unLo++;
                                sp = i;
                                iArr2 = eclass;
                                i2 = loSt;
                            }
                        }
                        i2 = gtHi;
                        sp = i;
                        while (unLo <= sp) {
                            gtHi = iArr2[iArr[sp]] - ((int) r);
                            if (gtHi != 0) {
                                fswap(iArr, sp, i2);
                                i2--;
                                sp--;
                            } else if (gtHi < 0) {
                                break;
                            } else {
                                med = r;
                                i3 = i2;
                                sp--;
                            }
                            iArr2 = eclass;
                        }
                        if (unLo > sp) {
                            break;
                        }
                        med = r;
                        i3 = i2;
                        fswap(iArr, unLo, sp);
                        unLo++;
                        sp--;
                        gtHi = i3;
                        iArr2 = eclass;
                        i2 = loSt;
                    }
                    if (i2 < ltLo) {
                        med = r;
                        n2 = fmin(ltLo - lo, unLo - ltLo);
                        fvswap(iArr, lo, unLo - n2, n2);
                        r = fmin(hi - i2, i2 - sp);
                        fvswap(iArr, sp + 1, (hi - r) + 1, r);
                        n2 = ((lo + unLo) - ltLo) - 1;
                        m = (hi - (i2 - sp)) + 1;
                        if (n2 - lo <= hi - m) {
                            r = sp2 + 1;
                            fpush(sp2, lo, n2);
                            sp2 = r + 1;
                            fpush(r, m, hi);
                        } else {
                            r = sp2 + 1;
                            fpush(sp2, m, hi);
                            sp2 = r + 1;
                            fpush(r, lo, n2);
                        }
                    }
                    sp = sp2;
                    r = j;
                    iArr2 = eclass;
                    i2 = loSt;
                }
                unLo = lo;
                gtHi = hi;
                ltLo = lo;
                sp = hi;
                while (true) {
                    if (unLo <= sp) {
                        i = sp;
                        n = iArr2[iArr[unLo]] - ((int) r);
                        if (n == 0) {
                            fswap(iArr, unLo, ltLo);
                            ltLo++;
                            unLo++;
                            sp = i;
                        } else if (n > 0) {
                            med = r;
                            unLo++;
                            sp = i;
                            iArr2 = eclass;
                            i2 = loSt;
                        }
                    } else {
                        i = sp;
                    }
                    i2 = gtHi;
                    sp = i;
                    while (unLo <= sp) {
                        gtHi = iArr2[iArr[sp]] - ((int) r);
                        if (gtHi != 0) {
                            if (gtHi < 0) {
                                break;
                            }
                            med = r;
                            i3 = i2;
                            sp--;
                        } else {
                            fswap(iArr, sp, i2);
                            i2--;
                            sp--;
                        }
                        iArr2 = eclass;
                    }
                    if (unLo > sp) {
                        break;
                    }
                    med = r;
                    i3 = i2;
                    fswap(iArr, unLo, sp);
                    unLo++;
                    sp--;
                    gtHi = i3;
                    iArr2 = eclass;
                    i2 = loSt;
                }
                if (i2 < ltLo) {
                    med = r;
                    n2 = fmin(ltLo - lo, unLo - ltLo);
                    fvswap(iArr, lo, unLo - n2, n2);
                    r = fmin(hi - i2, i2 - sp);
                    fvswap(iArr, sp + 1, (hi - r) + 1, r);
                    n2 = ((lo + unLo) - ltLo) - 1;
                    m = (hi - (i2 - sp)) + 1;
                    if (n2 - lo <= hi - m) {
                        r = sp2 + 1;
                        fpush(sp2, m, hi);
                        sp2 = r + 1;
                        fpush(r, lo, n2);
                    } else {
                        r = sp2 + 1;
                        fpush(sp2, lo, n2);
                        sp2 = r + 1;
                        fpush(r, m, hi);
                    }
                }
                sp = sp2;
                r = j;
                iArr2 = eclass;
                i2 = loSt;
            }
        }
    }

    private int[] getEclass() {
        if (this.eclass != null) {
            return this.eclass;
        }
        int[] iArr = new int[(this.quadrant.length / 2)];
        this.eclass = iArr;
        return iArr;
    }

    final void fallbackSort(int[] fmap, byte[] block, int nblock) {
        int i;
        int i2;
        int[] iArr = fmap;
        int i3 = nblock;
        int[] ftab = new int[257];
        int[] eclass = getEclass();
        for (i = 0; i < i3; i++) {
            eclass[i] = 0;
        }
        int i4 = 0;
        while (true) {
            i = 1;
            if (i4 >= i3) {
                break;
            }
            int i5 = block[i4] & 255;
            ftab[i5] = ftab[i5] + 1;
            i4++;
        }
        for (i4 = 1; i4 < 257; i4++) {
            ftab[i4] = ftab[i4] + ftab[i4 - 1];
        }
        for (i2 = 0; i2 < i3; i2++) {
            i4 = block[i2] & 255;
            i5 = ftab[i4] - 1;
            ftab[i4] = i5;
            iArr[i5] = i2;
        }
        BitSet bhtab = new BitSet(i3 + 64);
        for (i2 = 0; i2 < 256; i2++) {
            bhtab.set(ftab[i2]);
        }
        i2 = 0;
        while (i2 < 32) {
            bhtab.set((i2 * 2) + i3);
            bhtab.clear(((i2 * 2) + i3) + 1);
            i2++;
        }
        i2 = 1;
        int nNotDone;
        do {
            int i6;
            int j = 0;
            for (i6 = 0; i6 < i3; i6++) {
                if (bhtab.get(i6)) {
                    j = i6;
                }
                nNotDone = iArr[i6] - i2;
                if (nNotDone < 0) {
                    nNotDone += i3;
                }
                eclass[nNotDone] = j;
            }
            nNotDone = 0;
            int r = -1;
            while (true) {
                int k = bhtab.nextClearBit(r + 1);
                int l = k - 1;
                if (l >= i3) {
                    break;
                }
                r = bhtab.nextSetBit(k + 1) - 1;
                if (r >= i3) {
                    break;
                } else if (r > l) {
                    nNotDone += (r - l) + i;
                    fallbackQSort3(iArr, eclass, l, r);
                    i6 = l;
                    i = -1;
                    while (i6 <= r) {
                        int cc1 = eclass[iArr[i6]];
                        if (i != cc1) {
                            bhtab.set(i6);
                            i = cc1;
                        }
                        i6++;
                        iArr = fmap;
                    }
                    i = 1;
                } else {
                    BlockSort blockSort = this;
                }
                i2 *= 2;
                if (i2 <= i3) {
                    return;
                }
            }
            i2 *= 2;
            if (i2 <= i3) {
                return;
            }
        } while (nNotDone != 0);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean mainSimpleSort(org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream.Data r28, int r29, int r30, int r31, int r32) {
        /*
        r27 = this;
        r0 = r27;
        r1 = r28;
        r3 = r30;
        r5 = r3 - r29;
        r6 = 1;
        r5 = r5 + r6;
        r8 = 2;
        if (r5 >= r8) goto L_0x001a;
    L_0x000d:
        r8 = r0.firstAttempt;
        if (r8 == 0) goto L_0x0018;
    L_0x0011:
        r8 = r0.workDone;
        r9 = r0.workLimit;
        if (r8 <= r9) goto L_0x0018;
    L_0x0017:
        goto L_0x0019;
    L_0x0018:
        r6 = 0;
    L_0x0019:
        return r6;
    L_0x001a:
        r8 = 0;
    L_0x001b:
        r9 = INCS;
        r9 = r9[r8];
        if (r9 >= r5) goto L_0x0024;
    L_0x0021:
        r8 = r8 + 1;
        goto L_0x001b;
    L_0x0024:
        r9 = r1.fmap;
        r10 = r0.quadrant;
        r11 = r1.block;
        r12 = r32 + 1;
        r13 = r0.firstAttempt;
        r14 = r0.workLimit;
        r7 = r0.workDone;
    L_0x0032:
        r8 = r8 + -1;
        if (r8 < 0) goto L_0x01fb;
    L_0x0036:
        r16 = INCS;
        r16 = r16[r8];
        r17 = r29 + r16;
        r1 = r17 + -1;
        r17 = r29 + r16;
    L_0x0040:
        r18 = r17;
        r6 = r18;
        if (r6 > r3) goto L_0x01f5;
    L_0x0046:
        r17 = 3;
    L_0x0048:
        if (r6 > r3) goto L_0x01e5;
    L_0x004a:
        r17 = r17 + -1;
        if (r17 < 0) goto L_0x01e5;
    L_0x004e:
        r18 = r9[r6];
        r19 = r18 + r31;
        r20 = r6;
        r21 = 0;
        r22 = r7;
        r7 = 0;
    L_0x0059:
        if (r21 == 0) goto L_0x0068;
    L_0x005b:
        r9[r20] = r7;
        r2 = r20 - r16;
        r20 = r2;
        if (r2 > r1) goto L_0x006b;
        r25 = r1;
        goto L_0x01db;
    L_0x0068:
        r2 = 1;
        r21 = r2;
    L_0x006b:
        r2 = r20 - r16;
        r7 = r9[r2];
        r2 = r7 + r31;
        r23 = r19;
        r24 = r2 + 1;
        r25 = r1;
        r1 = r11[r24];
        r24 = r23 + 1;
        r4 = r11[r24];
        if (r1 != r4) goto L_0x01c8;
    L_0x007f:
        r1 = r2 + 2;
        r1 = r11[r1];
        r4 = r23 + 2;
        r4 = r11[r4];
        if (r1 != r4) goto L_0x01b9;
    L_0x0089:
        r1 = r2 + 3;
        r1 = r11[r1];
        r4 = r23 + 3;
        r4 = r11[r4];
        if (r1 != r4) goto L_0x01aa;
    L_0x0093:
        r1 = r2 + 4;
        r1 = r11[r1];
        r4 = r23 + 4;
        r4 = r11[r4];
        if (r1 != r4) goto L_0x019b;
    L_0x009d:
        r1 = r2 + 5;
        r1 = r11[r1];
        r4 = r23 + 5;
        r4 = r11[r4];
        if (r1 != r4) goto L_0x018c;
    L_0x00a7:
        r2 = r2 + 6;
        r1 = r11[r2];
        r23 = r23 + 6;
        r4 = r11[r23];
        if (r1 != r4) goto L_0x0181;
    L_0x00b1:
        r1 = r32;
    L_0x00b3:
        if (r1 <= 0) goto L_0x01db;
    L_0x00b5:
        r1 = r1 + -4;
        r4 = r2 + 1;
        r4 = r11[r4];
        r24 = r23 + 1;
        r26 = r1;
        r1 = r11[r24];
        if (r4 != r1) goto L_0x0172;
    L_0x00c3:
        r1 = r10[r2];
        r4 = r10[r23];
        if (r1 != r4) goto L_0x016b;
    L_0x00c9:
        r1 = r2 + 2;
        r1 = r11[r1];
        r4 = r23 + 2;
        r4 = r11[r4];
        if (r1 != r4) goto L_0x015b;
    L_0x00d3:
        r1 = r2 + 1;
        r1 = r10[r1];
        r4 = r23 + 1;
        r4 = r10[r4];
        if (r1 != r4) goto L_0x014f;
    L_0x00dd:
        r1 = r2 + 3;
        r1 = r11[r1];
        r4 = r23 + 3;
        r4 = r11[r4];
        if (r1 != r4) goto L_0x013f;
    L_0x00e7:
        r1 = r2 + 2;
        r1 = r10[r1];
        r4 = r23 + 2;
        r4 = r10[r4];
        if (r1 != r4) goto L_0x0133;
    L_0x00f1:
        r1 = r2 + 4;
        r1 = r11[r1];
        r4 = r23 + 4;
        r4 = r11[r4];
        if (r1 != r4) goto L_0x0123;
    L_0x00fb:
        r1 = r2 + 3;
        r1 = r10[r1];
        r4 = r23 + 3;
        r4 = r10[r4];
        if (r1 != r4) goto L_0x0117;
    L_0x0105:
        r2 = r2 + 4;
        if (r2 < r12) goto L_0x010a;
    L_0x0109:
        r2 = r2 - r12;
    L_0x010a:
        r1 = r23 + 4;
        if (r1 < r12) goto L_0x010f;
    L_0x010e:
        r1 = r1 - r12;
    L_0x010f:
        r23 = r1;
        r22 = r22 + 1;
        r1 = r26;
        goto L_0x00b3;
    L_0x0117:
        r1 = r2 + 3;
        r1 = r10[r1];
        r4 = r23 + 3;
        r4 = r10[r4];
        if (r1 <= r4) goto L_0x01db;
    L_0x0121:
        goto L_0x01d7;
    L_0x0123:
        r1 = r2 + 4;
        r1 = r11[r1];
        r1 = r1 & 255;
        r4 = r23 + 4;
        r4 = r11[r4];
        r4 = r4 & 255;
        if (r1 <= r4) goto L_0x01db;
    L_0x0131:
        goto L_0x01d7;
    L_0x0133:
        r1 = r2 + 2;
        r1 = r10[r1];
        r4 = r23 + 2;
        r4 = r10[r4];
        if (r1 <= r4) goto L_0x01db;
    L_0x013d:
        goto L_0x01d7;
    L_0x013f:
        r1 = r2 + 3;
        r1 = r11[r1];
        r1 = r1 & 255;
        r4 = r23 + 3;
        r4 = r11[r4];
        r4 = r4 & 255;
        if (r1 <= r4) goto L_0x01db;
    L_0x014d:
        goto L_0x01d7;
    L_0x014f:
        r1 = r2 + 1;
        r1 = r10[r1];
        r4 = r23 + 1;
        r4 = r10[r4];
        if (r1 <= r4) goto L_0x01db;
    L_0x0159:
        goto L_0x01d7;
    L_0x015b:
        r1 = r2 + 2;
        r1 = r11[r1];
        r1 = r1 & 255;
        r4 = r23 + 2;
        r4 = r11[r4];
        r4 = r4 & 255;
        if (r1 <= r4) goto L_0x01db;
    L_0x0169:
        goto L_0x01d7;
    L_0x016b:
        r1 = r10[r2];
        r4 = r10[r23];
        if (r1 <= r4) goto L_0x01db;
    L_0x0171:
        goto L_0x01d7;
    L_0x0172:
        r1 = r2 + 1;
        r1 = r11[r1];
        r1 = r1 & 255;
        r4 = r23 + 1;
        r4 = r11[r4];
        r4 = r4 & 255;
        if (r1 <= r4) goto L_0x01db;
    L_0x0180:
        goto L_0x01d7;
    L_0x0181:
        r1 = r11[r2];
        r1 = r1 & 255;
        r4 = r11[r23];
        r4 = r4 & 255;
        if (r1 <= r4) goto L_0x01db;
    L_0x018b:
        goto L_0x01d7;
    L_0x018c:
        r1 = r2 + 5;
        r1 = r11[r1];
        r1 = r1 & 255;
        r4 = r23 + 5;
        r4 = r11[r4];
        r4 = r4 & 255;
        if (r1 <= r4) goto L_0x01db;
    L_0x019a:
        goto L_0x01d7;
    L_0x019b:
        r1 = r2 + 4;
        r1 = r11[r1];
        r1 = r1 & 255;
        r4 = r23 + 4;
        r4 = r11[r4];
        r4 = r4 & 255;
        if (r1 <= r4) goto L_0x01db;
    L_0x01a9:
        goto L_0x01d7;
    L_0x01aa:
        r1 = r2 + 3;
        r1 = r11[r1];
        r1 = r1 & 255;
        r4 = r23 + 3;
        r4 = r11[r4];
        r4 = r4 & 255;
        if (r1 <= r4) goto L_0x01db;
    L_0x01b8:
        goto L_0x01d7;
    L_0x01b9:
        r1 = r2 + 2;
        r1 = r11[r1];
        r1 = r1 & 255;
        r4 = r23 + 2;
        r4 = r11[r4];
        r4 = r4 & 255;
        if (r1 <= r4) goto L_0x01db;
    L_0x01c7:
        goto L_0x01d7;
    L_0x01c8:
        r1 = r2 + 1;
        r1 = r11[r1];
        r1 = r1 & 255;
        r4 = r23 + 1;
        r4 = r11[r4];
        r4 = r4 & 255;
        if (r1 <= r4) goto L_0x01db;
    L_0x01d7:
        r1 = r25;
        goto L_0x0059;
    L_0x01db:
        r9[r20] = r18;
        r6 = r6 + 1;
        r7 = r22;
        r1 = r25;
        goto L_0x0048;
    L_0x01e5:
        r25 = r1;
        if (r13 == 0) goto L_0x01ee;
    L_0x01e9:
        if (r6 > r3) goto L_0x01ee;
    L_0x01eb:
        if (r7 <= r14) goto L_0x01ee;
    L_0x01ed:
        goto L_0x01fb;
    L_0x01ee:
        r17 = r6;
        r1 = r25;
        r6 = 1;
        goto L_0x0040;
        r1 = r28;
        r6 = 1;
        goto L_0x0032;
    L_0x01fb:
        r0.workDone = r7;
        if (r13 == 0) goto L_0x0203;
    L_0x01ff:
        if (r7 <= r14) goto L_0x0203;
    L_0x0201:
        r15 = 1;
        goto L_0x0204;
    L_0x0203:
        r15 = 0;
    L_0x0204:
        return r15;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.compress.compressors.bzip2.BlockSort.mainSimpleSort(org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream$Data, int, int, int, int):boolean");
    }

    private static void vswap(int[] fmap, int p1, int p2, int n) {
        n += p1;
        while (p1 < n) {
            int t = fmap[p1];
            int p12 = p1 + 1;
            fmap[p1] = fmap[p2];
            p1 = p2 + 1;
            fmap[p2] = t;
            p2 = p1;
            p1 = p12;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static byte med3(byte r1, byte r2, byte r3) {
        /*
        if (r1 >= r2) goto L_0x0008;
    L_0x0002:
        if (r2 >= r3) goto L_0x0005;
    L_0x0004:
        goto L_0x000a;
    L_0x0005:
        if (r1 >= r3) goto L_0x0010;
    L_0x0007:
        goto L_0x000e;
    L_0x0008:
        if (r2 <= r3) goto L_0x000c;
    L_0x000a:
        r0 = r2;
        goto L_0x0011;
    L_0x000c:
        if (r1 <= r3) goto L_0x0010;
    L_0x000e:
        r0 = r3;
        goto L_0x0011;
    L_0x0010:
        r0 = r1;
    L_0x0011:
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.compress.compressors.bzip2.BlockSort.med3(byte, byte, byte):byte");
    }

    private void mainQSort3(Data dataShadow, int loSt, int hiSt, int dSt, int last) {
        Data data = dataShadow;
        int[] stack_ll = this.stack_ll;
        int[] stack_hh = this.stack_hh;
        int[] stack_dd = this.stack_dd;
        int[] fmap = data.fmap;
        byte[] block = data.block;
        stack_ll[0] = loSt;
        stack_hh[0] = hiSt;
        stack_dd[0] = dSt;
        int sp = 1;
        while (true) {
            int sp2 = sp - 1;
            if (sp2 >= 0) {
                int unHi;
                int lo = stack_ll[sp2];
                int hi = stack_hh[sp2];
                int i = stack_dd[sp2];
                if (hi - lo >= 20) {
                    if (i <= 10) {
                        int temp;
                        int n;
                        sp = i + 1;
                        int med = med3(block[fmap[lo] + sp], block[fmap[hi] + sp], block[fmap[(lo + hi) >>> 1] + sp]) & 255;
                        unHi = hi;
                        int ltLo = lo;
                        int unLo = lo;
                        int gtHi = hi;
                        while (true) {
                            int gtHi2;
                            if (unLo <= unHi) {
                                gtHi2 = gtHi;
                                gtHi = (block[fmap[unLo] + sp] & 255) - med;
                                if (gtHi == 0) {
                                    temp = fmap[unLo];
                                    int unLo2 = unLo + 1;
                                    fmap[unLo] = fmap[ltLo];
                                    unLo = ltLo + 1;
                                    fmap[ltLo] = temp;
                                    ltLo = unLo;
                                    unLo = unLo2;
                                } else if (gtHi < 0) {
                                    unLo++;
                                }
                                gtHi = gtHi2;
                            } else {
                                gtHi2 = gtHi;
                            }
                            gtHi = gtHi2;
                            while (unLo <= unHi) {
                                n = (block[fmap[unHi] + sp] & 255) - med;
                                if (n != 0) {
                                    if (n <= 0) {
                                        break;
                                    }
                                    unHi--;
                                } else {
                                    temp = fmap[unHi];
                                    gtHi2 = unHi - 1;
                                    fmap[unHi] = fmap[gtHi];
                                    unHi = gtHi - 1;
                                    fmap[gtHi] = temp;
                                    gtHi = unHi;
                                    unHi = gtHi2;
                                }
                            }
                            if (unLo > unHi) {
                                break;
                            }
                            n = fmap[unLo];
                            temp = unLo + 1;
                            fmap[unLo] = fmap[unHi];
                            unLo = unHi - 1;
                            fmap[unHi] = n;
                            unHi = unLo;
                            unLo = temp;
                        }
                        if (gtHi < ltLo) {
                            stack_ll[sp2] = lo;
                            stack_hh[sp2] = hi;
                            stack_dd[sp2] = sp;
                            sp2++;
                        } else {
                            med = ltLo - lo < unLo - ltLo ? ltLo - lo : unLo - ltLo;
                            vswap(fmap, lo, unLo - med, med);
                            med = hi - gtHi < gtHi - unHi ? hi - gtHi : gtHi - unHi;
                            vswap(fmap, unLo, (hi - med) + 1, med);
                            n = ((lo + unLo) - ltLo) - 1;
                            temp = (hi - (gtHi - unHi)) + 1;
                            stack_ll[sp2] = lo;
                            stack_hh[sp2] = n;
                            stack_dd[sp2] = i;
                            sp2++;
                            stack_ll[sp2] = n + 1;
                            stack_hh[sp2] = temp - 1;
                            stack_dd[sp2] = sp;
                            sp2++;
                            stack_ll[sp2] = temp;
                            stack_hh[sp2] = hi;
                            stack_dd[sp2] = i;
                            sp2++;
                        }
                        sp = sp2;
                    }
                }
                unHi = i;
                if (!mainSimpleSort(data, lo, hi, i, last)) {
                    sp = sp2;
                } else {
                    return;
                }
            }
            return;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    final void mainSort(org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream.Data r33, int r34) {
        /*
        r32 = this;
        r6 = r32;
        r7 = r33;
        r8 = r34;
        r9 = r6.mainSort_runningOrder;
        r10 = r6.mainSort_copy;
        r11 = r6.mainSort_bigDone;
        r12 = r6.ftab;
        r13 = r7.block;
        r14 = r7.fmap;
        r15 = r6.quadrant;
        r5 = r6.workLimit;
        r4 = r6.firstAttempt;
        r0 = 65537; // 0x10001 float:9.1837E-41 double:3.23796E-319;
    L_0x001b:
        r0 = r0 + -1;
        r16 = 0;
        if (r0 < 0) goto L_0x0024;
    L_0x0021:
        r12[r0] = r16;
        goto L_0x001b;
    L_0x0024:
        r0 = 0;
    L_0x0025:
        r3 = 20;
        r2 = 1;
        if (r0 >= r3) goto L_0x003a;
    L_0x002a:
        r1 = r8 + r0;
        r1 = r1 + 2;
        r3 = r8 + 1;
        r3 = r0 % r3;
        r3 = r3 + r2;
        r2 = r13[r3];
        r13[r1] = r2;
        r0 = r0 + 1;
        goto L_0x0025;
    L_0x003a:
        r0 = r8 + 20;
        r0 = r0 + r2;
    L_0x003d:
        r0 = r0 + -1;
        if (r0 < 0) goto L_0x0044;
    L_0x0041:
        r15[r0] = r16;
        goto L_0x003d;
    L_0x0044:
        r0 = r8 + 1;
        r0 = r13[r0];
        r13[r16] = r0;
        r0 = r13[r16];
        r1 = 255; // 0xff float:3.57E-43 double:1.26E-321;
        r0 = r0 & r1;
        r17 = r0;
        r0 = 0;
    L_0x0052:
        if (r0 > r8) goto L_0x006a;
    L_0x0054:
        r18 = r0 + 1;
        r3 = r13[r18];
        r3 = r3 & r1;
        r18 = r17 << 8;
        r18 = r18 + r3;
        r20 = r12[r18];
        r20 = r20 + 1;
        r12[r18] = r20;
        r17 = r3;
        r0 = r0 + 1;
        r3 = 20;
        goto L_0x0052;
    L_0x006a:
        r0 = 1;
    L_0x006b:
        r3 = 65536; // 0x10000 float:9.18355E-41 double:3.2379E-319;
        if (r0 > r3) goto L_0x007c;
    L_0x006f:
        r3 = r12[r0];
        r18 = r0 + -1;
        r18 = r12[r18];
        r3 = r3 + r18;
        r12[r0] = r3;
        r0 = r0 + 1;
        goto L_0x006b;
    L_0x007c:
        r0 = r13[r2];
        r0 = r0 & r1;
        r3 = r0;
        r0 = 0;
    L_0x0081:
        if (r0 >= r8) goto L_0x009b;
    L_0x0083:
        r17 = r0 + 2;
        r2 = r13[r17];
        r2 = r2 & r1;
        r17 = r3 << 8;
        r17 = r17 + r2;
        r18 = r12[r17];
        r20 = 1;
        r18 = r18 + -1;
        r12[r17] = r18;
        r14[r18] = r0;
        r3 = r2;
        r0 = r0 + 1;
        r2 = 1;
        goto L_0x0081;
    L_0x009b:
        r0 = r8 + 1;
        r0 = r13[r0];
        r0 = r0 & r1;
        r0 = r0 << 8;
        r22 = r3;
        r2 = 1;
        r3 = r13[r2];
        r3 = r3 & r1;
        r0 = r0 + r3;
        r3 = r12[r0];
        r3 = r3 - r2;
        r12[r0] = r3;
        r14[r3] = r8;
        r17 = 256; // 0x100 float:3.59E-43 double:1.265E-321;
        r0 = 256; // 0x100 float:3.59E-43 double:1.265E-321;
    L_0x00b4:
        r0 = r0 + -1;
        if (r0 < 0) goto L_0x00bd;
    L_0x00b8:
        r11[r0] = r16;
        r9[r0] = r0;
        goto L_0x00b4;
    L_0x00bd:
        r0 = 364; // 0x16c float:5.1E-43 double:1.8E-321;
    L_0x00bf:
        r2 = 1;
        if (r0 == r2) goto L_0x010d;
    L_0x00c2:
        r0 = r0 / 3;
        r2 = r0;
    L_0x00c5:
        if (r2 > r1) goto L_0x00bf;
    L_0x00c7:
        r3 = r9[r2];
        r18 = r3 + 1;
        r18 = r18 << 8;
        r18 = r12[r18];
        r20 = r3 << 8;
        r20 = r12[r20];
        r1 = r18 - r20;
        r24 = r4;
        r4 = r0 + -1;
        r18 = r2;
        r20 = r18 - r0;
        r20 = r9[r20];
    L_0x00df:
        r25 = r20 + 1;
        r25 = r25 << 8;
        r25 = r12[r25];
        r26 = r20 << 8;
        r26 = r12[r26];
        r27 = r5;
        r5 = r25 - r26;
        if (r5 <= r1) goto L_0x0102;
    L_0x00ef:
        r9[r18] = r20;
        r5 = r18 - r0;
        if (r5 > r4) goto L_0x00f9;
        r18 = r5;
        goto L_0x0102;
    L_0x00f9:
        r18 = r5 - r0;
        r20 = r9[r18];
        r18 = r5;
        r5 = r27;
        goto L_0x00df;
    L_0x0102:
        r9[r18] = r3;
        r2 = r2 + 1;
        r4 = r24;
        r5 = r27;
        r1 = 255; // 0xff float:3.57E-43 double:1.26E-321;
        goto L_0x00c5;
    L_0x010d:
        r24 = r4;
        r27 = r5;
        r0 = 0;
    L_0x0112:
        r5 = r0;
        r1 = 255; // 0xff float:3.57E-43 double:1.26E-321;
        if (r5 > r1) goto L_0x0227;
    L_0x0117:
        r18 = r9[r5];
        r0 = 0;
    L_0x011a:
        r4 = r0;
        r3 = 2097152; // 0x200000 float:2.938736E-39 double:1.0361308E-317;
        r0 = -2097153; // 0xffffffffffdfffff float:NaN double:NaN;
        if (r4 > r1) goto L_0x0187;
    L_0x0122:
        r2 = r18 << 8;
        r20 = r2 + r4;
        r23 = r12[r20];
        r2 = r23 & r3;
        if (r2 == r3) goto L_0x0172;
    L_0x012c:
        r2 = r23 & r0;
        r25 = r20 + 1;
        r25 = r12[r25];
        r0 = r25 & r0;
        r21 = 1;
        r0 = r0 + -1;
        if (r0 <= r2) goto L_0x015e;
    L_0x013a:
        r25 = 2;
        r26 = r0;
        r0 = r6;
        r1 = r7;
        r21 = r2;
        r28 = 1;
        r7 = 20;
        r19 = 2097152; // 0x200000 float:2.938736E-39 double:1.0361308E-317;
        r3 = r26;
        r29 = r4;
        r4 = r25;
        r30 = r9;
        r7 = r27;
        r9 = r5;
        r5 = r8;
        r0.mainQSort3(r1, r2, r3, r4, r5);
        if (r24 == 0) goto L_0x016d;
    L_0x0159:
        r0 = r6.workDone;
        if (r0 <= r7) goto L_0x016d;
    L_0x015d:
        return;
    L_0x015e:
        r26 = r0;
        r21 = r2;
        r29 = r4;
        r30 = r9;
        r7 = r27;
        r19 = 2097152; // 0x200000 float:2.938736E-39 double:1.0361308E-317;
        r28 = 1;
        r9 = r5;
    L_0x016d:
        r0 = r23 | r19;
        r12[r20] = r0;
        goto L_0x017b;
    L_0x0172:
        r29 = r4;
        r30 = r9;
        r7 = r27;
        r28 = 1;
        r9 = r5;
    L_0x017b:
        r0 = r29 + 1;
        r27 = r7;
        r5 = r9;
        r9 = r30;
        r1 = 255; // 0xff float:3.57E-43 double:1.26E-321;
        r7 = r33;
        goto L_0x011a;
    L_0x0187:
        r30 = r9;
        r7 = r27;
        r19 = 2097152; // 0x200000 float:2.938736E-39 double:1.0361308E-317;
        r28 = 1;
        r9 = r5;
        r1 = 0;
    L_0x0191:
        r2 = 255; // 0xff float:3.57E-43 double:1.26E-321;
        if (r1 > r2) goto L_0x01a1;
    L_0x0195:
        r3 = r1 << 8;
        r3 = r3 + r18;
        r3 = r12[r3];
        r3 = r3 & r0;
        r10[r1] = r3;
        r1 = r1 + 1;
        goto L_0x0191;
    L_0x01a1:
        r1 = r18 << 8;
        r1 = r12[r1];
        r1 = r1 & r0;
        r3 = r18 + 1;
        r3 = r3 << 8;
        r3 = r12[r3];
        r3 = r3 & r0;
    L_0x01ad:
        if (r1 >= r3) goto L_0x01ce;
    L_0x01af:
        r4 = r14[r1];
        r5 = r13[r4];
        r5 = r5 & r2;
        r20 = r11[r5];
        if (r20 != 0) goto L_0x01c9;
    L_0x01b8:
        r20 = r10[r5];
        if (r4 != 0) goto L_0x01bf;
    L_0x01bc:
        r21 = r8;
        goto L_0x01c1;
    L_0x01bf:
        r21 = r4 + -1;
    L_0x01c1:
        r14[r20] = r21;
        r20 = r10[r5];
        r20 = r20 + 1;
        r10[r5] = r20;
    L_0x01c9:
        r1 = r1 + 1;
        r22 = r5;
        goto L_0x01ad;
    L_0x01ce:
        r1 = 256; // 0x100 float:3.59E-43 double:1.265E-321;
    L_0x01d0:
        r1 = r1 + -1;
        if (r1 < 0) goto L_0x01df;
    L_0x01d4:
        r3 = r1 << 8;
        r3 = r3 + r18;
        r4 = r12[r3];
        r4 = r4 | r19;
        r12[r3] = r4;
        goto L_0x01d0;
    L_0x01df:
        r11[r18] = r28;
        if (r9 >= r2) goto L_0x021a;
    L_0x01e3:
        r1 = r18 << 8;
        r1 = r12[r1];
        r1 = r1 & r0;
        r3 = r18 + 1;
        r3 = r3 << 8;
        r3 = r12[r3];
        r0 = r0 & r3;
        r0 = r0 - r1;
        r3 = 0;
    L_0x01f1:
        r4 = r0 >> r3;
        r5 = 65534; // 0xfffe float:9.1833E-41 double:3.2378E-319;
        if (r4 <= r5) goto L_0x01fb;
    L_0x01f8:
        r3 = r3 + 1;
        goto L_0x01f1;
    L_0x01fb:
        r4 = 0;
    L_0x01fc:
        if (r4 >= r0) goto L_0x021a;
    L_0x01fe:
        r5 = r1 + r4;
        r5 = r14[r5];
        r2 = r4 >> r3;
        r2 = (char) r2;
        r15[r5] = r2;
        r31 = r0;
        r0 = 20;
        if (r5 >= r0) goto L_0x0213;
    L_0x020d:
        r19 = r5 + r8;
        r19 = r19 + 1;
        r15[r19] = r2;
    L_0x0213:
        r4 = r4 + 1;
        r0 = r31;
        r2 = 255; // 0xff float:3.57E-43 double:1.26E-321;
        goto L_0x01fc;
    L_0x021a:
        r0 = 20;
        r1 = r9 + 1;
        r0 = r1;
        r27 = r7;
        r9 = r30;
        r7 = r33;
        goto L_0x0112;
    L_0x0227:
        r30 = r9;
        r7 = r27;
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.compress.compressors.bzip2.BlockSort.mainSort(org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream$Data, int):void");
    }
}
