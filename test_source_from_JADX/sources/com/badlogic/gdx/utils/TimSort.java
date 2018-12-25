package com.badlogic.gdx.utils;

import java.util.Arrays;
import java.util.Comparator;

class TimSort<T> {
    private static final boolean DEBUG = false;
    private static final int INITIAL_TMP_STORAGE_LENGTH = 256;
    private static final int MIN_GALLOP = 7;
    private static final int MIN_MERGE = 32;
    /* renamed from: a */
    private T[] f96a;
    /* renamed from: c */
    private Comparator<? super T> f97c;
    private int minGallop;
    private final int[] runBase;
    private final int[] runLen;
    private int stackSize;
    private T[] tmp;
    private int tmpCount;

    TimSort() {
        this.minGallop = 7;
        this.stackSize = 0;
        this.tmp = new Object[256];
        this.runBase = new int[40];
        this.runLen = new int[40];
    }

    public void doSort(T[] a, Comparator<T> c, int lo, int hi) {
        this.stackSize = 0;
        rangeCheck(a.length, lo, hi);
        int nRemaining = hi - lo;
        if (nRemaining >= 2) {
            if (nRemaining < 32) {
                binarySort(a, lo, hi, lo + countRunAndMakeAscending(a, lo, hi, c), c);
                return;
            }
            this.f96a = a;
            this.f97c = c;
            this.tmpCount = 0;
            int minRun = minRunLength(nRemaining);
            do {
                int runLen = countRunAndMakeAscending(a, lo, hi, c);
                if (runLen < minRun) {
                    int force = nRemaining <= minRun ? nRemaining : minRun;
                    binarySort(a, lo, lo + force, lo + runLen, c);
                    runLen = force;
                }
                pushRun(lo, runLen);
                mergeCollapse();
                lo += runLen;
                nRemaining -= runLen;
            } while (nRemaining != 0);
            mergeForceCollapse();
            this.f96a = null;
            this.f97c = null;
            T[] tmp = this.tmp;
            int n = this.tmpCount;
            for (int i = 0; i < n; i++) {
                tmp[i] = null;
            }
        }
    }

    private TimSort(T[] a, Comparator<? super T> c) {
        this.minGallop = 7;
        this.stackSize = 0;
        this.f96a = a;
        this.f97c = c;
        int len = a.length;
        this.tmp = (Object[]) new Object[(len < 512 ? len >>> 1 : 256)];
        int stackLen = len < 120 ? 5 : len < 1542 ? 10 : len < 119151 ? 19 : 40;
        this.runBase = new int[stackLen];
        this.runLen = new int[stackLen];
    }

    static <T> void sort(T[] a, Comparator<? super T> c) {
        sort(a, 0, a.length, c);
    }

    static <T> void sort(T[] a, int lo, int hi, Comparator<? super T> c) {
        if (c == null) {
            Arrays.sort(a, lo, hi);
            return;
        }
        rangeCheck(a.length, lo, hi);
        int nRemaining = hi - lo;
        if (nRemaining >= 2) {
            if (nRemaining < 32) {
                binarySort(a, lo, hi, lo + countRunAndMakeAscending(a, lo, hi, c), c);
                return;
            }
            TimSort<T> ts = new TimSort(a, c);
            int minRun = minRunLength(nRemaining);
            do {
                int runLen = countRunAndMakeAscending(a, lo, hi, c);
                if (runLen < minRun) {
                    int force = nRemaining <= minRun ? nRemaining : minRun;
                    binarySort(a, lo, lo + force, lo + runLen, c);
                    runLen = force;
                }
                ts.pushRun(lo, runLen);
                ts.mergeCollapse();
                lo += runLen;
                nRemaining -= runLen;
            } while (nRemaining != 0);
            ts.mergeForceCollapse();
        }
    }

    private static <T> void binarySort(T[] a, int lo, int hi, int start, Comparator<? super T> c) {
        if (start == lo) {
            start++;
        }
        while (start < hi) {
            int left;
            T pivot = a[start];
            int left2 = lo;
            int right = start;
            while (left2 < right) {
                left = (left2 + right) >>> 1;
                if (c.compare(pivot, a[left]) < 0) {
                    right = left;
                } else {
                    left2 = left + 1;
                }
            }
            left = start - left2;
            switch (left) {
                case 1:
                    break;
                case 2:
                    a[left2 + 2] = a[left2 + 1];
                    break;
                default:
                    System.arraycopy(a, left2, a, left2 + 1, left);
                    continue;
            }
            a[left2 + 1] = a[left2];
            a[left2] = pivot;
            start++;
        }
    }

    private static <T> int countRunAndMakeAscending(T[] a, int lo, int hi, Comparator<? super T> c) {
        int runHi = lo + 1;
        if (runHi == hi) {
            return 1;
        }
        int runHi2 = runHi + 1;
        if (c.compare(a[runHi], a[lo]) < 0) {
            while (runHi2 < hi && c.compare(a[runHi2], a[runHi2 - 1]) < 0) {
                runHi2++;
            }
            reverseRange(a, lo, runHi2);
        } else {
            while (runHi2 < hi && c.compare(a[runHi2], a[runHi2 - 1]) >= 0) {
                runHi2++;
            }
        }
        return runHi2 - lo;
    }

    private static void reverseRange(Object[] a, int lo, int hi) {
        hi--;
        while (lo < hi) {
            Object t = a[lo];
            int lo2 = lo + 1;
            a[lo] = a[hi];
            lo = hi - 1;
            a[hi] = t;
            hi = lo;
            lo = lo2;
        }
    }

    private static int minRunLength(int n) {
        int r = 0;
        while (n >= 32) {
            r |= n & 1;
            n >>= 1;
        }
        return n + r;
    }

    private void pushRun(int runBase, int runLen) {
        this.runBase[this.stackSize] = runBase;
        this.runLen[this.stackSize] = runLen;
        this.stackSize++;
    }

    private void mergeCollapse() {
        while (this.stackSize > 1) {
            int n = this.stackSize - 2;
            if ((n < 1 || this.runLen[n - 1] > this.runLen[n] + this.runLen[n + 1]) && (n < 2 || this.runLen[n - 2] > this.runLen[n] + this.runLen[n - 1])) {
                if (this.runLen[n] > this.runLen[n + 1]) {
                    return;
                }
            } else if (this.runLen[n - 1] < this.runLen[n + 1]) {
                n--;
            }
            mergeAt(n);
        }
    }

    private void mergeForceCollapse() {
        while (this.stackSize > 1) {
            int n = this.stackSize - 2;
            if (n > 0 && this.runLen[n - 1] < this.runLen[n + 1]) {
                n--;
            }
            mergeAt(n);
        }
    }

    private void mergeAt(int i) {
        int i2 = i;
        int base1 = this.runBase[i2];
        int len1 = this.runLen[i2];
        int base2 = this.runBase[i2 + 1];
        int len2 = this.runLen[i2 + 1];
        this.runLen[i2] = len1 + len2;
        if (i2 == this.stackSize - 3) {
            r0.runBase[i2 + 1] = r0.runBase[i2 + 2];
            r0.runLen[i2 + 1] = r0.runLen[i2 + 2];
        }
        r0.stackSize--;
        int k = gallopRight(r0.f96a[base2], r0.f96a, base1, len1, 0, r0.f97c);
        base1 += k;
        len1 -= k;
        if (len1 != 0) {
            int base22 = base2;
            int len22 = gallopLeft(r0.f96a[(base1 + len1) - 1], r0.f96a, base2, len2, len2 - 1, r0.f97c);
            if (len22 != 0) {
                if (len1 <= len22) {
                    mergeLo(base1, len1, base22, len22);
                } else {
                    mergeHi(base1, len1, base22, len22);
                }
            }
        }
    }

    private static <T> int gallopLeft(T key, T[] a, int base, int len, int hint, Comparator<? super T> c) {
        int maxOfs;
        int lastOfs = 0;
        int ofs = 1;
        if (c.compare(key, a[base + hint]) > 0) {
            maxOfs = len - hint;
            while (ofs < maxOfs && c.compare(key, a[(base + hint) + ofs]) > 0) {
                lastOfs = ofs;
                ofs = (ofs << 1) + 1;
                if (ofs <= 0) {
                    ofs = maxOfs;
                }
            }
            if (ofs > maxOfs) {
                ofs = maxOfs;
            }
            lastOfs += hint;
            ofs += hint;
        } else {
            maxOfs = hint + 1;
            while (ofs < maxOfs && c.compare(key, a[(base + hint) - ofs]) <= 0) {
                lastOfs = ofs;
                ofs = (ofs << 1) + 1;
                if (ofs <= 0) {
                    ofs = maxOfs;
                }
            }
            if (ofs > maxOfs) {
                ofs = maxOfs;
            }
            int tmp = lastOfs;
            lastOfs = hint - ofs;
            ofs = hint - tmp;
        }
        lastOfs++;
        while (lastOfs < ofs) {
            maxOfs = ((ofs - lastOfs) >>> 1) + lastOfs;
            if (c.compare(key, a[base + maxOfs]) > 0) {
                lastOfs = maxOfs + 1;
            } else {
                ofs = maxOfs;
            }
        }
        return ofs;
    }

    private static <T> int gallopRight(T key, T[] a, int base, int len, int hint, Comparator<? super T> c) {
        int maxOfs;
        int ofs = 1;
        int lastOfs = 0;
        if (c.compare(key, a[base + hint]) < 0) {
            maxOfs = hint + 1;
            while (ofs < maxOfs && c.compare(key, a[(base + hint) - ofs]) < 0) {
                lastOfs = ofs;
                ofs = (ofs << 1) + 1;
                if (ofs <= 0) {
                    ofs = maxOfs;
                }
            }
            if (ofs > maxOfs) {
                ofs = maxOfs;
            }
            int tmp = lastOfs;
            lastOfs = hint - ofs;
            ofs = hint - tmp;
        } else {
            maxOfs = len - hint;
            while (ofs < maxOfs && c.compare(key, a[(base + hint) + ofs]) >= 0) {
                lastOfs = ofs;
                ofs = (ofs << 1) + 1;
                if (ofs <= 0) {
                    ofs = maxOfs;
                }
            }
            if (ofs > maxOfs) {
                ofs = maxOfs;
            }
            lastOfs += hint;
            ofs += hint;
        }
        lastOfs++;
        while (lastOfs < ofs) {
            maxOfs = ((ofs - lastOfs) >>> 1) + lastOfs;
            if (c.compare(key, a[base + maxOfs]) < 0) {
                ofs = maxOfs;
            } else {
                lastOfs = maxOfs + 1;
            }
        }
        return ofs;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void mergeLo(int r21, int r22, int r23, int r24) {
        /*
        r20 = this;
        r0 = r20;
        r1 = r22;
        r7 = r0.f96a;
        r8 = r0.ensureCapacity(r1);
        r9 = 0;
        r10 = r21;
        java.lang.System.arraycopy(r7, r10, r8, r9, r1);
        r2 = 0;
        r3 = r23;
        r4 = r10;
        r5 = r4 + 1;
        r6 = r3 + 1;
        r3 = r7[r3];
        r7[r4] = r3;
        r3 = r24 + -1;
        if (r3 != 0) goto L_0x0024;
    L_0x0020:
        java.lang.System.arraycopy(r8, r2, r7, r5, r1);
        return;
    L_0x0024:
        r11 = 1;
        if (r1 != r11) goto L_0x0031;
    L_0x0027:
        java.lang.System.arraycopy(r7, r6, r7, r5, r3);
        r4 = r5 + r3;
        r9 = r8[r2];
        r7[r4] = r9;
        return;
    L_0x0031:
        r12 = r0.f97c;
        r4 = r0.minGallop;
    L_0x0035:
        r13 = 0;
        r14 = r1;
        r1 = 0;
    L_0x0038:
        r15 = r7[r6];
        r9 = r8[r2];
        r9 = r12.compare(r15, r9);
        if (r9 >= 0) goto L_0x005b;
    L_0x0042:
        r9 = r5 + 1;
        r15 = r6 + 1;
        r6 = r7[r6];
        r7[r5] = r6;
        r1 = r1 + 1;
        r5 = 0;
        r3 = r3 + -1;
        if (r3 != 0) goto L_0x0057;
    L_0x0051:
        r11 = r4;
        r6 = r9;
        r9 = r15;
    L_0x0054:
        r10 = 1;
        goto L_0x00f4;
    L_0x0057:
        r13 = r5;
        r5 = r9;
        r6 = r15;
        goto L_0x0076;
    L_0x005b:
        r9 = r5 + 1;
        r15 = r2 + 1;
        r2 = r8[r2];
        r7[r5] = r2;
        r13 = r13 + 1;
        r1 = 0;
        r14 = r14 + -1;
        if (r14 != r11) goto L_0x0074;
    L_0x006a:
        r11 = r4;
        r2 = r15;
        r10 = 1;
        r19 = r9;
        r9 = r6;
        r6 = r19;
        goto L_0x00f4;
    L_0x0074:
        r5 = r9;
        r2 = r15;
    L_0x0076:
        r9 = r13 | r1;
        if (r9 < r4) goto L_0x013f;
    L_0x007a:
        r17 = r1;
        r16 = r3;
        r18 = r4;
        r9 = r6;
        r15 = r13;
        r13 = r2;
        r6 = r5;
    L_0x0084:
        r1 = r7[r9];
        r5 = 0;
        r2 = r8;
        r3 = r13;
        r4 = r14;
        r11 = r6;
        r6 = r12;
        r15 = gallopRight(r1, r2, r3, r4, r5, r6);
        if (r15 == 0) goto L_0x00a6;
    L_0x0092:
        java.lang.System.arraycopy(r8, r13, r7, r11, r15);
        r1 = r11 + r15;
        r2 = r13 + r15;
        r14 = r14 - r15;
        r3 = 1;
        if (r14 > r3) goto L_0x00a4;
        r6 = r1;
        r3 = r16;
        r11 = r18;
        goto L_0x0054;
    L_0x00a4:
        r11 = r1;
        r13 = r2;
    L_0x00a6:
        r6 = r11 + 1;
        r5 = r9 + 1;
        r1 = r7[r9];
        r7[r11] = r1;
        r9 = r16 + -1;
        if (r9 != 0) goto L_0x00b9;
    L_0x00b2:
        r3 = r9;
        r2 = r13;
        r11 = r18;
        r10 = 1;
        r9 = r5;
        goto L_0x00f4;
    L_0x00b9:
        r1 = r8[r13];
        r11 = 0;
        r2 = r7;
        r3 = r5;
        r4 = r9;
        r10 = r5;
        r5 = r11;
        r11 = r6;
        r6 = r12;
        r1 = gallopLeft(r1, r2, r3, r4, r5, r6);
        if (r1 == 0) goto L_0x00e0;
    L_0x00c9:
        java.lang.System.arraycopy(r7, r10, r7, r11, r1);
        r2 = r11 + r1;
        r6 = r10 + r1;
        r3 = r9 - r1;
        if (r3 != 0) goto L_0x00db;
    L_0x00d4:
        r9 = r6;
        r11 = r18;
        r10 = 1;
        r6 = r2;
        r2 = r13;
        goto L_0x00f4;
    L_0x00db:
        r11 = r2;
        r16 = r3;
        r9 = r6;
        goto L_0x00e3;
    L_0x00e0:
        r16 = r9;
        r9 = r10;
    L_0x00e3:
        r6 = r11 + 1;
        r2 = r13 + 1;
        r3 = r8[r13];
        r7[r11] = r3;
        r14 = r14 + -1;
        r10 = 1;
        if (r14 != r10) goto L_0x0115;
    L_0x00f0:
        r3 = r16;
        r11 = r18;
    L_0x00f4:
        if (r11 >= r10) goto L_0x00f8;
    L_0x00f6:
        r1 = 1;
        goto L_0x00f9;
    L_0x00f8:
        r1 = r11;
    L_0x00f9:
        r0.minGallop = r1;
        if (r14 != r10) goto L_0x0107;
    L_0x00fd:
        java.lang.System.arraycopy(r7, r9, r7, r6, r3);
        r1 = r6 + r3;
        r4 = r8[r2];
        r7[r1] = r4;
        goto L_0x0114;
    L_0x0107:
        if (r14 != 0) goto L_0x0111;
    L_0x0109:
        r1 = new java.lang.IllegalArgumentException;
        r4 = "Comparison method violates its general contract!";
        r1.<init>(r4);
        throw r1;
    L_0x0111:
        java.lang.System.arraycopy(r8, r2, r7, r6, r14);
    L_0x0114:
        return;
    L_0x0115:
        r18 = r18 + -1;
        r3 = 7;
        if (r15 < r3) goto L_0x011c;
    L_0x011a:
        r4 = 1;
        goto L_0x011d;
    L_0x011c:
        r4 = 0;
    L_0x011d:
        if (r1 < r3) goto L_0x0121;
    L_0x011f:
        r3 = 1;
        goto L_0x0122;
    L_0x0121:
        r3 = 0;
    L_0x0122:
        r3 = r3 | r4;
        if (r3 != 0) goto L_0x0137;
    L_0x0125:
        if (r18 >= 0) goto L_0x0129;
    L_0x0127:
        r18 = 0;
    L_0x0129:
        r4 = r18 + 2;
        r5 = r6;
        r6 = r9;
        r1 = r14;
        r3 = r16;
        r9 = 0;
        r10 = r21;
        r11 = 1;
        goto L_0x0035;
    L_0x0137:
        r17 = r1;
        r13 = r2;
        r10 = r21;
        r11 = 1;
        goto L_0x0084;
    L_0x013f:
        r9 = 0;
        goto L_0x0038;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.badlogic.gdx.utils.TimSort.mergeLo(int, int, int, int):void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void mergeHi(int r22, int r23, int r24, int r25) {
        /*
        r21 = this;
        r0 = r21;
        r2 = r24;
        r3 = r25;
        r9 = r0.f96a;
        r10 = r0.ensureCapacity(r3);
        r11 = 0;
        java.lang.System.arraycopy(r9, r2, r10, r11, r3);
        r4 = r22 + r23;
        r12 = 1;
        r4 = r4 - r12;
        r5 = r3 + -1;
        r6 = r2 + r3;
        r6 = r6 - r12;
        r7 = r6 + -1;
        r8 = r4 + -1;
        r4 = r9[r4];
        r9[r6] = r4;
        r1 = r23 + -1;
        if (r1 != 0) goto L_0x002d;
    L_0x0025:
        r4 = r3 + -1;
        r4 = r7 - r4;
        java.lang.System.arraycopy(r10, r11, r9, r4, r3);
        return;
    L_0x002d:
        if (r3 != r12) goto L_0x003d;
    L_0x002f:
        r7 = r7 - r1;
        r8 = r8 - r1;
        r4 = r8 + 1;
        r6 = r7 + 1;
        java.lang.System.arraycopy(r9, r4, r9, r6, r1);
        r4 = r10[r5];
        r9[r7] = r4;
        return;
    L_0x003d:
        r13 = r0.f97c;
        r4 = r0.minGallop;
    L_0x0041:
        r6 = 0;
        r14 = r3;
        r3 = r1;
        r1 = 0;
    L_0x0045:
        r11 = r10[r5];
        r12 = r9[r8];
        r11 = r13.compare(r11, r12);
        if (r11 >= 0) goto L_0x0066;
    L_0x004f:
        r11 = r7 + -1;
        r12 = r8 + -1;
        r8 = r9[r8];
        r9[r7] = r8;
        r6 = r6 + 1;
        r1 = 0;
        r3 = r3 + -1;
        if (r3 != 0) goto L_0x0063;
    L_0x005e:
        r1 = r3;
        r15 = r12;
    L_0x0060:
        r12 = r4;
        goto L_0x010c;
    L_0x0063:
        r7 = r11;
        r8 = r12;
        goto L_0x007d;
    L_0x0066:
        r11 = r7 + -1;
        r12 = r5 + -1;
        r5 = r10[r5];
        r9[r7] = r5;
        r1 = r1 + 1;
        r5 = 0;
        r14 = r14 + -1;
        r6 = 1;
        if (r14 != r6) goto L_0x007a;
    L_0x0076:
        r1 = r3;
        r15 = r8;
        r5 = r12;
        goto L_0x0060;
    L_0x007a:
        r6 = r5;
        r7 = r11;
        r5 = r12;
    L_0x007d:
        r11 = r6 | r1;
        if (r11 < r4) goto L_0x015f;
    L_0x0081:
        r17 = r1;
        r1 = r3;
        r18 = r4;
        r11 = r5;
        r12 = r6;
        r15 = r8;
        r16 = r14;
        r14 = r7;
    L_0x008c:
        r3 = r10[r11];
        r7 = r1 + -1;
        r4 = r9;
        r5 = r22;
        r6 = r1;
        r8 = r13;
        r3 = gallopRight(r3, r4, r5, r6, r7, r8);
        r12 = r1 - r3;
        if (r12 == 0) goto L_0x00b6;
    L_0x009d:
        r3 = r14 - r12;
        r8 = r15 - r12;
        r1 = r1 - r12;
        r4 = r8 + 1;
        r5 = r3 + 1;
        java.lang.System.arraycopy(r9, r4, r9, r5, r12);
        if (r1 != 0) goto L_0x00b4;
    L_0x00ab:
        r15 = r8;
        r5 = r11;
        r14 = r16;
        r12 = r18;
        r11 = r3;
        goto L_0x010c;
    L_0x00b4:
        r14 = r3;
        r15 = r8;
    L_0x00b6:
        r19 = r14 + -1;
        r20 = r11 + -1;
        r3 = r10[r11];
        r9[r14] = r3;
        r11 = r16 + -1;
        r3 = 1;
        if (r11 != r3) goto L_0x00cb;
    L_0x00c3:
        r14 = r11;
        r12 = r18;
        r11 = r19;
        r5 = r20;
        goto L_0x010c;
    L_0x00cb:
        r3 = r9[r15];
        r5 = 0;
        r7 = r11 + -1;
        r4 = r10;
        r6 = r11;
        r8 = r13;
        r3 = gallopLeft(r3, r4, r5, r6, r7, r8);
        r3 = r11 - r3;
        if (r3 == 0) goto L_0x00f4;
    L_0x00db:
        r4 = r19 - r3;
        r5 = r20 - r3;
        r14 = r11 - r3;
        r6 = r5 + 1;
        r7 = r4 + 1;
        java.lang.System.arraycopy(r10, r6, r9, r7, r3);
        r6 = 1;
        if (r14 > r6) goto L_0x00f0;
        r11 = r4;
    L_0x00ed:
        r12 = r18;
        goto L_0x010c;
    L_0x00f0:
        r11 = r5;
        r16 = r14;
        goto L_0x00fa;
    L_0x00f4:
        r16 = r11;
        r4 = r19;
        r11 = r20;
    L_0x00fa:
        r14 = r4 + -1;
        r5 = r15 + -1;
        r6 = r9[r15];
        r9[r4] = r6;
        r1 = r1 + -1;
        if (r1 != 0) goto L_0x0137;
    L_0x0106:
        r15 = r5;
        r5 = r11;
        r11 = r14;
        r14 = r16;
        goto L_0x00ed;
    L_0x010c:
        r4 = 1;
        if (r12 >= r4) goto L_0x0111;
    L_0x010f:
        r3 = 1;
        goto L_0x0112;
    L_0x0111:
        r3 = r12;
    L_0x0112:
        r0.minGallop = r3;
        if (r14 != r4) goto L_0x0124;
    L_0x0116:
        r11 = r11 - r1;
        r15 = r15 - r1;
        r3 = r15 + 1;
        r4 = r11 + 1;
        java.lang.System.arraycopy(r9, r3, r9, r4, r1);
        r3 = r10[r5];
        r9[r11] = r3;
        goto L_0x0136;
    L_0x0124:
        if (r14 != 0) goto L_0x012e;
    L_0x0126:
        r3 = new java.lang.IllegalArgumentException;
        r4 = "Comparison method violates its general contract!";
        r3.<init>(r4);
        throw r3;
    L_0x012e:
        r3 = r14 + -1;
        r3 = r11 - r3;
        r6 = 0;
        java.lang.System.arraycopy(r10, r6, r9, r3, r14);
    L_0x0136:
        return;
    L_0x0137:
        r4 = 1;
        r6 = 0;
        r18 = r18 + -1;
        r7 = 7;
        if (r12 < r7) goto L_0x0140;
    L_0x013e:
        r8 = 1;
        goto L_0x0141;
    L_0x0140:
        r8 = 0;
    L_0x0141:
        if (r3 < r7) goto L_0x0145;
    L_0x0143:
        r7 = 1;
        goto L_0x0146;
    L_0x0145:
        r7 = 0;
    L_0x0146:
        r7 = r7 | r8;
        if (r7 != 0) goto L_0x015a;
    L_0x0149:
        if (r18 >= 0) goto L_0x014d;
    L_0x014b:
        r18 = 0;
    L_0x014d:
        r3 = r18 + 2;
        r4 = r3;
        r8 = r5;
        r5 = r11;
        r7 = r14;
        r3 = r16;
        r11 = 0;
        r12 = 1;
        goto L_0x0041;
    L_0x015a:
        r17 = r3;
        r15 = r5;
        goto L_0x008c;
    L_0x015f:
        r11 = 0;
        r12 = 1;
        goto L_0x0045;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.badlogic.gdx.utils.TimSort.mergeHi(int, int, int, int):void");
    }

    private T[] ensureCapacity(int minCapacity) {
        this.tmpCount = Math.max(this.tmpCount, minCapacity);
        if (this.tmp.length < minCapacity) {
            int newSize = minCapacity;
            newSize |= newSize >> 1;
            newSize |= newSize >> 2;
            newSize |= newSize >> 4;
            newSize |= newSize >> 8;
            newSize = (newSize | (newSize >> 16)) + 1;
            if (newSize < 0) {
                newSize = minCapacity;
            } else {
                newSize = Math.min(newSize, this.f96a.length >>> 1);
            }
            this.tmp = (Object[]) new Object[newSize];
        }
        return this.tmp;
    }

    private static void rangeCheck(int arrayLen, int fromIndex, int toIndex) {
        if (fromIndex > toIndex) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("fromIndex(");
            stringBuilder.append(fromIndex);
            stringBuilder.append(") > toIndex(");
            stringBuilder.append(toIndex);
            stringBuilder.append(")");
            throw new IllegalArgumentException(stringBuilder.toString());
        } else if (fromIndex < 0) {
            throw new ArrayIndexOutOfBoundsException(fromIndex);
        } else if (toIndex > arrayLen) {
            throw new ArrayIndexOutOfBoundsException(toIndex);
        }
    }
}
