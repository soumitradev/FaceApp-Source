package com.badlogic.gdx.utils;

class ComparableTimSort {
    private static final boolean DEBUG = false;
    private static final int INITIAL_TMP_STORAGE_LENGTH = 256;
    private static final int MIN_GALLOP = 7;
    private static final int MIN_MERGE = 32;
    /* renamed from: a */
    private Object[] f93a;
    private int minGallop;
    private final int[] runBase;
    private final int[] runLen;
    private int stackSize;
    private Object[] tmp;

    ComparableTimSort() {
        this.minGallop = 7;
        this.stackSize = 0;
        this.tmp = new Object[256];
        this.runBase = new int[40];
        this.runLen = new int[40];
    }

    public void doSort(Object[] a, int lo, int hi) {
        this.stackSize = 0;
        rangeCheck(a.length, lo, hi);
        int nRemaining = hi - lo;
        if (nRemaining >= 2) {
            if (nRemaining < 32) {
                binarySort(a, lo, hi, lo + countRunAndMakeAscending(a, lo, hi));
                return;
            }
            this.f93a = a;
            int minRun = minRunLength(nRemaining);
            do {
                int runLen = countRunAndMakeAscending(a, lo, hi);
                if (runLen < minRun) {
                    int force = nRemaining <= minRun ? nRemaining : minRun;
                    binarySort(a, lo, lo + force, lo + runLen);
                    runLen = force;
                }
                pushRun(lo, runLen);
                mergeCollapse();
                lo += runLen;
                nRemaining -= runLen;
            } while (nRemaining != 0);
            mergeForceCollapse();
        }
    }

    private ComparableTimSort(Object[] a) {
        this.minGallop = 7;
        this.stackSize = 0;
        this.f93a = a;
        int len = a.length;
        this.tmp = new Object[(len < 512 ? len >>> 1 : 256)];
        int stackLen = len < 120 ? 5 : len < 1542 ? 10 : len < 119151 ? 19 : 40;
        this.runBase = new int[stackLen];
        this.runLen = new int[stackLen];
    }

    static void sort(Object[] a) {
        sort(a, 0, a.length);
    }

    static void sort(Object[] a, int lo, int hi) {
        rangeCheck(a.length, lo, hi);
        int nRemaining = hi - lo;
        if (nRemaining >= 2) {
            if (nRemaining < 32) {
                binarySort(a, lo, hi, lo + countRunAndMakeAscending(a, lo, hi));
                return;
            }
            ComparableTimSort ts = new ComparableTimSort(a);
            int minRun = minRunLength(nRemaining);
            do {
                int runLen = countRunAndMakeAscending(a, lo, hi);
                if (runLen < minRun) {
                    int force = nRemaining <= minRun ? nRemaining : minRun;
                    binarySort(a, lo, lo + force, lo + runLen);
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

    private static void binarySort(Object[] a, int lo, int hi, int start) {
        if (start == lo) {
            start++;
        }
        while (start < hi) {
            int left;
            Comparable<Object> pivot = a[start];
            int left2 = lo;
            int right = start;
            while (left2 < right) {
                left = (left2 + right) >>> 1;
                if (pivot.compareTo(a[left]) < 0) {
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

    private static int countRunAndMakeAscending(Object[] a, int lo, int hi) {
        int runHi = lo + 1;
        if (runHi == hi) {
            return 1;
        }
        int runHi2 = runHi + 1;
        if (((Comparable) a[runHi]).compareTo(a[lo]) < 0) {
            while (runHi2 < hi && ((Comparable) a[runHi2]).compareTo(a[runHi2 - 1]) < 0) {
                runHi2++;
            }
            reverseRange(a, lo, runHi2);
        } else {
            while (runHi2 < hi && ((Comparable) a[runHi2]).compareTo(a[runHi2 - 1]) >= 0) {
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
            if (n > 0 && this.runLen[n - 1] <= this.runLen[n] + this.runLen[n + 1]) {
                if (this.runLen[n - 1] < this.runLen[n + 1]) {
                    n--;
                }
                mergeAt(n);
            } else if (this.runLen[n] <= this.runLen[n + 1]) {
                mergeAt(n);
            } else {
                return;
            }
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
        int base1 = this.runBase[i];
        int len1 = this.runLen[i];
        int base2 = this.runBase[i + 1];
        int len2 = this.runLen[i + 1];
        this.runLen[i] = len1 + len2;
        if (i == this.stackSize - 3) {
            this.runBase[i + 1] = this.runBase[i + 2];
            this.runLen[i + 1] = this.runLen[i + 2];
        }
        this.stackSize--;
        int k = gallopRight((Comparable) this.f93a[base2], this.f93a, base1, len1, 0);
        base1 += k;
        len1 -= k;
        if (len1 != 0) {
            len2 = gallopLeft((Comparable) this.f93a[(base1 + len1) - 1], this.f93a, base2, len2, len2 - 1);
            if (len2 != 0) {
                if (len1 <= len2) {
                    mergeLo(base1, len1, base2, len2);
                } else {
                    mergeHi(base1, len1, base2, len2);
                }
            }
        }
    }

    private static int gallopLeft(Comparable<Object> key, Object[] a, int base, int len, int hint) {
        int maxOfs;
        int lastOfs = 0;
        int ofs = 1;
        if (key.compareTo(a[base + hint]) > 0) {
            maxOfs = len - hint;
            while (ofs < maxOfs && key.compareTo(a[(base + hint) + ofs]) > 0) {
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
            while (ofs < maxOfs && key.compareTo(a[(base + hint) - ofs]) <= 0) {
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
            if (key.compareTo(a[base + maxOfs]) > 0) {
                lastOfs = maxOfs + 1;
            } else {
                ofs = maxOfs;
            }
        }
        return ofs;
    }

    private static int gallopRight(Comparable<Object> key, Object[] a, int base, int len, int hint) {
        int maxOfs;
        int ofs = 1;
        int lastOfs = 0;
        if (key.compareTo(a[base + hint]) < 0) {
            maxOfs = hint + 1;
            while (ofs < maxOfs && key.compareTo(a[(base + hint) - ofs]) < 0) {
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
            while (ofs < maxOfs && key.compareTo(a[(base + hint) + ofs]) >= 0) {
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
            if (key.compareTo(a[base + maxOfs]) < 0) {
                ofs = maxOfs;
            } else {
                lastOfs = maxOfs + 1;
            }
        }
        return ofs;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void mergeLo(int r17, int r18, int r19, int r20) {
        /*
        r16 = this;
        r0 = r16;
        r1 = r18;
        r2 = r0.f93a;
        r3 = r0.ensureCapacity(r1);
        r4 = 0;
        r5 = r17;
        java.lang.System.arraycopy(r2, r5, r3, r4, r1);
        r6 = 0;
        r7 = r19;
        r8 = r5;
        r9 = r8 + 1;
        r10 = r7 + 1;
        r7 = r2[r7];
        r2[r8] = r7;
        r7 = r20 + -1;
        if (r7 != 0) goto L_0x0024;
    L_0x0020:
        java.lang.System.arraycopy(r3, r6, r2, r9, r1);
        return;
    L_0x0024:
        r8 = 1;
        if (r1 != r8) goto L_0x0031;
    L_0x0027:
        java.lang.System.arraycopy(r2, r10, r2, r9, r7);
        r4 = r9 + r7;
        r8 = r3[r6];
        r2[r4] = r8;
        return;
    L_0x0031:
        r11 = r0.minGallop;
    L_0x0033:
        r12 = 0;
        r13 = r1;
        r1 = 0;
    L_0x0036:
        r14 = r2[r10];
        r14 = (java.lang.Comparable) r14;
        r15 = r3[r6];
        r14 = r14.compareTo(r15);
        if (r14 >= 0) goto L_0x0058;
    L_0x0042:
        r14 = r9 + 1;
        r15 = r10 + 1;
        r10 = r2[r10];
        r2[r9] = r10;
        r1 = r1 + 1;
        r9 = 0;
        r7 = r7 + -1;
        if (r7 != 0) goto L_0x0054;
    L_0x0051:
        r10 = r15;
        goto L_0x00b4;
    L_0x0054:
        r12 = r9;
        r9 = r14;
        r10 = r15;
        goto L_0x006b;
    L_0x0058:
        r14 = r9 + 1;
        r15 = r6 + 1;
        r6 = r3[r6];
        r2[r9] = r6;
        r12 = r12 + 1;
        r1 = 0;
        r13 = r13 + -1;
        if (r13 != r8) goto L_0x0069;
    L_0x0067:
        r6 = r15;
        goto L_0x00b4;
    L_0x0069:
        r9 = r14;
        r6 = r15;
    L_0x006b:
        r14 = r12 | r1;
        if (r14 < r11) goto L_0x0036;
    L_0x006f:
        r14 = r2[r10];
        r14 = (java.lang.Comparable) r14;
        r12 = gallopRight(r14, r3, r6, r13, r4);
        if (r12 == 0) goto L_0x0084;
    L_0x0079:
        java.lang.System.arraycopy(r3, r6, r2, r9, r12);
        r14 = r9 + r12;
        r6 = r6 + r12;
        r13 = r13 - r12;
        if (r13 > r8) goto L_0x0083;
    L_0x0082:
        goto L_0x00b4;
    L_0x0083:
        r9 = r14;
    L_0x0084:
        r14 = r9 + 1;
        r15 = r10 + 1;
        r10 = r2[r10];
        r2[r9] = r10;
        r7 = r7 + -1;
        if (r7 != 0) goto L_0x0091;
    L_0x0090:
        goto L_0x0051;
    L_0x0091:
        r9 = r3[r6];
        r9 = (java.lang.Comparable) r9;
        r1 = gallopLeft(r9, r2, r15, r7, r4);
        if (r1 == 0) goto L_0x00a5;
    L_0x009b:
        java.lang.System.arraycopy(r2, r15, r2, r14, r1);
        r14 = r14 + r1;
        r10 = r15 + r1;
        r7 = r7 - r1;
        if (r7 != 0) goto L_0x00a6;
    L_0x00a4:
        goto L_0x00b4;
    L_0x00a5:
        r10 = r15;
    L_0x00a6:
        r9 = r14 + 1;
        r15 = r6 + 1;
        r6 = r3[r6];
        r2[r14] = r6;
        r13 = r13 + -1;
        if (r13 != r8) goto L_0x00d5;
    L_0x00b2:
        r14 = r9;
        goto L_0x0067;
    L_0x00b4:
        if (r11 >= r8) goto L_0x00b8;
    L_0x00b6:
        r1 = 1;
        goto L_0x00b9;
    L_0x00b8:
        r1 = r11;
    L_0x00b9:
        r0.minGallop = r1;
        if (r13 != r8) goto L_0x00c7;
    L_0x00bd:
        java.lang.System.arraycopy(r2, r10, r2, r14, r7);
        r1 = r14 + r7;
        r4 = r3[r6];
        r2[r1] = r4;
        goto L_0x00d4;
    L_0x00c7:
        if (r13 != 0) goto L_0x00d1;
    L_0x00c9:
        r1 = new java.lang.IllegalArgumentException;
        r4 = "Comparison method violates its general contract!";
        r1.<init>(r4);
        throw r1;
    L_0x00d1:
        java.lang.System.arraycopy(r3, r6, r2, r14, r13);
    L_0x00d4:
        return;
    L_0x00d5:
        r11 = r11 + -1;
        r6 = 7;
        if (r12 < r6) goto L_0x00dc;
    L_0x00da:
        r14 = 1;
        goto L_0x00dd;
    L_0x00dc:
        r14 = 0;
    L_0x00dd:
        if (r1 < r6) goto L_0x00e1;
    L_0x00df:
        r6 = 1;
        goto L_0x00e2;
    L_0x00e1:
        r6 = 0;
    L_0x00e2:
        r6 = r6 | r14;
        if (r6 != 0) goto L_0x00ef;
    L_0x00e5:
        if (r11 >= 0) goto L_0x00e8;
    L_0x00e7:
        r11 = 0;
    L_0x00e8:
        r11 = r11 + 2;
        r1 = r13;
        r6 = r15;
        goto L_0x0033;
    L_0x00ef:
        r6 = r15;
        goto L_0x006f;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.badlogic.gdx.utils.ComparableTimSort.mergeLo(int, int, int, int):void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void mergeHi(int r19, int r20, int r21, int r22) {
        /*
        r18 = this;
        r0 = r18;
        r1 = r19;
        r3 = r21;
        r4 = r22;
        r5 = r0.f93a;
        r6 = r0.ensureCapacity(r4);
        r7 = 0;
        java.lang.System.arraycopy(r5, r3, r6, r7, r4);
        r8 = r1 + r20;
        r9 = 1;
        r8 = r8 - r9;
        r10 = r4 + -1;
        r11 = r3 + r4;
        r11 = r11 - r9;
        r12 = r11 + -1;
        r13 = r8 + -1;
        r8 = r5[r8];
        r5[r11] = r8;
        r2 = r20 + -1;
        if (r2 != 0) goto L_0x002f;
    L_0x0027:
        r8 = r4 + -1;
        r8 = r12 - r8;
        java.lang.System.arraycopy(r6, r7, r5, r8, r4);
        return;
    L_0x002f:
        if (r4 != r9) goto L_0x003f;
    L_0x0031:
        r12 = r12 - r2;
        r13 = r13 - r2;
        r7 = r13 + 1;
        r8 = r12 + 1;
        java.lang.System.arraycopy(r5, r7, r5, r8, r2);
        r7 = r6[r10];
        r5[r12] = r7;
        return;
    L_0x003f:
        r8 = r0.minGallop;
    L_0x0041:
        r11 = 0;
        r14 = r4;
        r4 = r2;
        r2 = 0;
    L_0x0045:
        r15 = r6[r10];
        r15 = (java.lang.Comparable) r15;
        r7 = r5[r13];
        r7 = r15.compareTo(r7);
        if (r7 >= 0) goto L_0x0067;
    L_0x0051:
        r7 = r12 + -1;
        r15 = r13 + -1;
        r13 = r5[r13];
        r5[r12] = r13;
        r11 = r11 + 1;
        r2 = 0;
        r4 = r4 + -1;
        if (r4 != 0) goto L_0x0064;
    L_0x0060:
        r9 = r8;
        r13 = r15;
        goto L_0x00da;
    L_0x0064:
        r12 = r7;
        r13 = r15;
        goto L_0x007d;
    L_0x0067:
        r7 = r12 + -1;
        r15 = r10 + -1;
        r10 = r6[r10];
        r5[r12] = r10;
        r2 = r2 + 1;
        r10 = 0;
        r14 = r14 + -1;
        if (r14 != r9) goto L_0x007a;
    L_0x0076:
        r9 = r8;
        r10 = r15;
        goto L_0x00da;
    L_0x007a:
        r12 = r7;
        r11 = r10;
        r10 = r15;
    L_0x007d:
        r7 = r11 | r2;
        if (r7 < r8) goto L_0x012a;
    L_0x0081:
        r7 = r6[r10];
        r7 = (java.lang.Comparable) r7;
        r15 = r4 + -1;
        r7 = gallopRight(r7, r5, r1, r4, r15);
        r11 = r4 - r7;
        if (r11 == 0) goto L_0x009f;
    L_0x008f:
        r7 = r12 - r11;
        r13 = r13 - r11;
        r4 = r4 - r11;
        r12 = r13 + 1;
        r15 = r7 + 1;
        java.lang.System.arraycopy(r5, r12, r5, r15, r11);
        if (r4 != 0) goto L_0x009e;
    L_0x009c:
        r9 = r8;
        goto L_0x00da;
    L_0x009e:
        r12 = r7;
    L_0x009f:
        r7 = r12 + -1;
        r15 = r10 + -1;
        r10 = r6[r10];
        r5[r12] = r10;
        r14 = r14 + -1;
        if (r14 != r9) goto L_0x00ac;
    L_0x00ab:
        goto L_0x0076;
    L_0x00ac:
        r10 = r5[r13];
        r10 = (java.lang.Comparable) r10;
        r12 = r14 + -1;
        r9 = 0;
        r10 = gallopLeft(r10, r6, r9, r14, r12);
        r2 = r14 - r10;
        if (r2 == 0) goto L_0x00ca;
    L_0x00bb:
        r7 = r7 - r2;
        r10 = r15 - r2;
        r14 = r14 - r2;
        r9 = r10 + 1;
        r12 = r7 + 1;
        java.lang.System.arraycopy(r6, r9, r5, r12, r2);
        r9 = 1;
        if (r14 > r9) goto L_0x00cb;
    L_0x00c9:
        goto L_0x009c;
    L_0x00ca:
        r10 = r15;
    L_0x00cb:
        r12 = r7 + -1;
        r9 = r13 + -1;
        r13 = r5[r13];
        r5[r7] = r13;
        r4 = r4 + -1;
        if (r4 != 0) goto L_0x0105;
    L_0x00d7:
        r13 = r9;
        r7 = r12;
        goto L_0x009c;
    L_0x00da:
        r15 = 1;
        if (r9 >= r15) goto L_0x00df;
    L_0x00dd:
        r2 = 1;
        goto L_0x00e0;
    L_0x00df:
        r2 = r9;
    L_0x00e0:
        r0.minGallop = r2;
        if (r14 != r15) goto L_0x00f2;
    L_0x00e4:
        r7 = r7 - r4;
        r13 = r13 - r4;
        r2 = r13 + 1;
        r8 = r7 + 1;
        java.lang.System.arraycopy(r5, r2, r5, r8, r4);
        r2 = r6[r10];
        r5[r7] = r2;
        goto L_0x0104;
    L_0x00f2:
        if (r14 != 0) goto L_0x00fc;
    L_0x00f4:
        r2 = new java.lang.IllegalArgumentException;
        r8 = "Comparison method violates its general contract!";
        r2.<init>(r8);
        throw r2;
    L_0x00fc:
        r2 = r14 + -1;
        r2 = r7 - r2;
        r8 = 0;
        java.lang.System.arraycopy(r6, r8, r5, r2, r14);
    L_0x0104:
        return;
    L_0x0105:
        r7 = 0;
        r15 = 1;
        r8 = r8 + -1;
        r13 = 7;
        if (r11 < r13) goto L_0x010f;
    L_0x010c:
        r16 = 1;
        goto L_0x0111;
    L_0x010f:
        r16 = 0;
    L_0x0111:
        if (r2 < r13) goto L_0x0115;
    L_0x0113:
        r13 = 1;
        goto L_0x0116;
    L_0x0115:
        r13 = 0;
    L_0x0116:
        r13 = r16 | r13;
        if (r13 != 0) goto L_0x0126;
    L_0x011a:
        if (r8 >= 0) goto L_0x011d;
    L_0x011c:
        r8 = 0;
    L_0x011d:
        r8 = r8 + 2;
        r2 = r4;
        r13 = r9;
        r4 = r14;
        r9 = 1;
        goto L_0x0041;
    L_0x0126:
        r13 = r9;
        r9 = 1;
        goto L_0x0081;
    L_0x012a:
        r7 = 0;
        goto L_0x0045;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.badlogic.gdx.utils.ComparableTimSort.mergeHi(int, int, int, int):void");
    }

    private Object[] ensureCapacity(int minCapacity) {
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
                newSize = Math.min(newSize, this.f93a.length >>> 1);
            }
            this.tmp = new Object[newSize];
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
