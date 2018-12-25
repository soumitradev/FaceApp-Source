package org.tukaani.xz.lzma;

import org.apache.commons.compress.archivers.zip.UnixStat;
import org.tukaani.xz.lz.LZEncoder;
import org.tukaani.xz.lz.Matches;
import org.tukaani.xz.rangecoder.RangeEncoder;

final class LZMAEncoderNormal extends LZMAEncoder {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static int EXTRA_SIZE_AFTER = 4096;
    private static int EXTRA_SIZE_BEFORE = 4096;
    private static final int OPTS = 4096;
    static /* synthetic */ Class class$org$tukaani$xz$lzma$LZMAEncoderNormal = class$("org.tukaani.xz.lzma.LZMAEncoderNormal");
    private Matches matches;
    private final State nextState;
    private int optCur;
    private int optEnd;
    private final Optimum[] opts = new Optimum[4096];
    private final int[] repLens;

    static {
        if (class$org$tukaani$xz$lzma$LZMAEncoderNormal == null) {
        } else {
            Class cls = class$org$tukaani$xz$lzma$LZMAEncoderNormal;
        }
    }

    LZMAEncoderNormal(RangeEncoder rangeEncoder, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        int i9 = i4;
        super(rangeEncoder, LZEncoder.getInstance(i9, Math.max(i5, EXTRA_SIZE_BEFORE), EXTRA_SIZE_AFTER, i6, 273, i7, i8), i, i2, i3, i4, i6);
        i9 = 0;
        this.optCur = 0;
        this.optEnd = 0;
        this.repLens = new int[4];
        this.nextState = new State();
        while (i9 < 4096) {
            r8.opts[i9] = new Optimum();
            i9++;
        }
    }

    private void calc1BytePrices(int i, int i2, int i3, int i4) {
        Object obj;
        int i5 = this.lz.getByte(0);
        int i6 = this.lz.getByte(this.opts[this.optCur].reps[0] + 1);
        int price = this.opts[this.optCur].price + this.literalEncoder.getPrice(i5, i6, this.lz.getByte(1), i, this.opts[this.optCur].state);
        if (price < this.opts[this.optCur + 1].price) {
            this.opts[this.optCur + 1].set1(price, this.optCur, -1);
            obj = 1;
        } else {
            obj = null;
        }
        if (i6 == i5 && (this.opts[this.optCur + 1].optPrev == this.optCur || this.opts[this.optCur + 1].backPrev != 0)) {
            i2 = getShortRepPrice(i4, this.opts[this.optCur].state, i2);
            if (i2 <= this.opts[this.optCur + 1].price) {
                this.opts[this.optCur + 1].set1(i2, this.optCur, 0);
                obj = 1;
            }
        }
        if (obj == null && i6 != i5 && i3 > 2) {
            i3 = this.lz.getMatchLen(1, this.opts[this.optCur].reps[0], Math.min(this.niceLen, i3 - 1));
            if (i3 >= 2) {
                this.nextState.set(this.opts[this.optCur].state);
                this.nextState.updateLiteral();
                price += getLongRepAndLenPrice(0, i3, this.nextState, (i + 1) & this.posMask);
                i = (this.optCur + 1) + i3;
                while (this.optEnd < i) {
                    Optimum[] optimumArr = this.opts;
                    i3 = this.optEnd + 1;
                    this.optEnd = i3;
                    optimumArr[i3].reset();
                }
                if (price < this.opts[i].price) {
                    this.opts[i].set2(price, this.optCur, 0);
                }
            }
        }
    }

    private int calcLongRepPrices(int i, int i2, int i3, int i4) {
        int i5 = i2;
        int i6 = i3;
        int min = Math.min(i6, this.niceLen);
        int i7 = 2;
        for (int i8 = 0; i8 < 4; i8++) {
            int matchLen = r0.lz.getMatchLen(r0.opts[r0.optCur].reps[i8], min);
            if (matchLen < 2) {
                int i9 = i4;
            } else {
                int i10;
                int price;
                while (r0.optEnd < r0.optCur + matchLen) {
                    Optimum[] optimumArr = r0.opts;
                    i10 = r0.optEnd + 1;
                    r0.optEnd = i10;
                    optimumArr[i10].reset();
                }
                int longRepPrice = getLongRepPrice(i4, i8, r0.opts[r0.optCur].state, i5);
                for (i10 = matchLen; i10 >= 2; i10--) {
                    price = r0.repLenEncoder.getPrice(i10, i5) + longRepPrice;
                    if (price < r0.opts[r0.optCur + i10].price) {
                        r0.opts[r0.optCur + i10].set1(price, r0.optCur, i8);
                    }
                }
                if (i8 == 0) {
                    i7 = matchLen + 1;
                }
                int i11 = i7;
                i7 = r0.lz.getMatchLen(matchLen + 1, r0.opts[r0.optCur].reps[i8], Math.min(r0.niceLen, (i6 - matchLen) - 1));
                if (i7 >= 2) {
                    longRepPrice += r0.repLenEncoder.getPrice(matchLen, i5);
                    r0.nextState.set(r0.opts[r0.optCur].state);
                    r0.nextState.updateLongRep();
                    int i12 = r0.lz.getByte(matchLen, 0);
                    int i13 = r0.lz.getByte(0);
                    int i14 = r0.lz.getByte(matchLen, 1);
                    int i15 = i + matchLen;
                    longRepPrice += r0.literalEncoder.getPrice(i12, i13, i14, i15, r0.nextState);
                    r0.nextState.updateLiteral();
                    longRepPrice += getLongRepAndLenPrice(0, i7, r0.nextState, (i15 + 1) & r0.posMask);
                    i15 = ((r0.optCur + matchLen) + 1) + i7;
                    while (r0.optEnd < i15) {
                        Optimum[] optimumArr2 = r0.opts;
                        price = r0.optEnd + 1;
                        r0.optEnd = price;
                        optimumArr2[price].reset();
                    }
                    if (longRepPrice < r0.opts[i15].price) {
                        r0.opts[i15].set3(longRepPrice, r0.optCur, i8, matchLen, 0);
                    }
                }
                i7 = i11;
            }
        }
        return i7;
    }

    private void calcNormalMatchPrices(int i, int i2, int i3, int i4, int i5) {
        int i6 = i3;
        int i7 = i5;
        if (this.matches.len[this.matches.count - 1] > i6) {
            r0.matches.count = 0;
            while (r0.matches.len[r0.matches.count] < i6) {
                Matches matches = r0.matches;
                matches.count++;
            }
            int[] iArr = r0.matches.len;
            Matches matches2 = r0.matches;
            int i8 = matches2.count;
            matches2.count = i8 + 1;
            iArr[i8] = i6;
        }
        if (r0.matches.len[r0.matches.count - 1] >= i7) {
            int i9;
            while (r0.optEnd < r0.optCur + r0.matches.len[r0.matches.count - 1]) {
                Optimum[] optimumArr = r0.opts;
                i9 = r0.optEnd + 1;
                r0.optEnd = i9;
                optimumArr[i9].reset();
            }
            int normalMatchPrice = getNormalMatchPrice(i4, r0.opts[r0.optCur].state);
            i9 = 0;
            while (i7 > r0.matches.len[i9]) {
                i9++;
            }
            while (true) {
                i8 = r0.matches.dist[i9];
                int matchAndLenPrice = getMatchAndLenPrice(normalMatchPrice, i8, i7, i2);
                if (matchAndLenPrice < r0.opts[r0.optCur + i7].price) {
                    r0.opts[r0.optCur + i7].set1(matchAndLenPrice, r0.optCur, i8 + 4);
                }
                if (i7 == r0.matches.len[i9]) {
                    int matchLen = r0.lz.getMatchLen(i7 + 1, i8, Math.min(r0.niceLen, (i6 - i7) - 1));
                    if (matchLen >= 2) {
                        r0.nextState.set(r0.opts[r0.optCur].state);
                        r0.nextState.updateMatch();
                        int i10 = i + i7;
                        matchAndLenPrice += r0.literalEncoder.getPrice(r0.lz.getByte(i7, 0), r0.lz.getByte(0), r0.lz.getByte(i7, 1), i10, r0.nextState);
                        r0.nextState.updateLiteral();
                        matchAndLenPrice += getLongRepAndLenPrice(0, matchLen, r0.nextState, (i10 + 1) & r0.posMask);
                        i10 = ((r0.optCur + i7) + 1) + matchLen;
                        while (r0.optEnd < i10) {
                            Optimum[] optimumArr2 = r0.opts;
                            int i11 = r0.optEnd + 1;
                            r0.optEnd = i11;
                            optimumArr2[i11].reset();
                        }
                        if (matchAndLenPrice < r0.opts[i10].price) {
                            r0.opts[i10].set3(matchAndLenPrice, r0.optCur, i8 + 4, i7, 0);
                        }
                    }
                    i9++;
                    if (i9 == r0.matches.count) {
                        return;
                    }
                }
                i7++;
            }
        }
    }

    static /* synthetic */ Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (Throwable e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    private int convertOpts() {
        this.optEnd = this.optCur;
        int i = this.opts[this.optCur].optPrev;
        while (true) {
            Optimum optimum = this.opts[this.optCur];
            if (optimum.prev1IsLiteral) {
                this.opts[i].optPrev = this.optCur;
                this.opts[i].backPrev = -1;
                int i2 = i - 1;
                this.optCur = i;
                if (optimum.hasPrev2) {
                    this.opts[i2].optPrev = i2 + 1;
                    this.opts[i2].backPrev = optimum.backPrev2;
                    this.optCur = i2;
                    i = optimum.optPrev2;
                } else {
                    i = i2;
                }
            }
            int i3 = this.opts[i].optPrev;
            this.opts[i].optPrev = this.optCur;
            this.optCur = i;
            if (this.optCur <= 0) {
                this.optCur = this.opts[0].optPrev;
                this.back = this.opts[this.optCur].backPrev;
                return this.optCur;
            }
            i = i3;
        }
    }

    static int getMemoryUsage(int i, int i2, int i3) {
        return LZEncoder.getMemoryUsage(i, Math.max(i2, EXTRA_SIZE_BEFORE), EXTRA_SIZE_AFTER, 273, i3) + 256;
    }

    private void updateOptStateAndReps() {
        int i = this.opts[this.optCur].optPrev;
        if (this.opts[this.optCur].prev1IsLiteral) {
            i--;
            if (this.opts[this.optCur].hasPrev2) {
                this.opts[this.optCur].state.set(this.opts[this.opts[this.optCur].optPrev2].state);
                if (this.opts[this.optCur].backPrev2 < 4) {
                    this.opts[this.optCur].state.updateLongRep();
                } else {
                    this.opts[this.optCur].state.updateMatch();
                }
            } else {
                this.opts[this.optCur].state.set(this.opts[i].state);
            }
            this.opts[this.optCur].state.updateLiteral();
        } else {
            this.opts[this.optCur].state.set(this.opts[i].state);
        }
        int i2 = 1;
        if (i == this.optCur - 1) {
            if (this.opts[this.optCur].backPrev == 0) {
                this.opts[this.optCur].state.updateShortRep();
            } else {
                this.opts[this.optCur].state.updateLiteral();
            }
            System.arraycopy(this.opts[i].reps, 0, this.opts[this.optCur].reps, 0, 4);
            return;
        }
        int i3;
        Optimum optimum;
        if (this.opts[this.optCur].prev1IsLiteral && this.opts[this.optCur].hasPrev2) {
            i = this.opts[this.optCur].optPrev2;
            i3 = this.opts[this.optCur].backPrev2;
            optimum = this.opts[this.optCur];
        } else {
            i3 = this.opts[this.optCur].backPrev;
            if (i3 < 4) {
                optimum = this.opts[this.optCur];
            } else {
                this.opts[this.optCur].state.updateMatch();
                if (i3 >= 4) {
                    this.opts[this.optCur].reps[0] = this.opts[i].reps[i3];
                    while (i2 <= i3) {
                        this.opts[this.optCur].reps[i2] = this.opts[i].reps[i2 - 1];
                        i2++;
                    }
                    while (i2 < 4) {
                        this.opts[this.optCur].reps[i2] = this.opts[i].reps[i2];
                        i2++;
                    }
                } else {
                    this.opts[this.optCur].reps[0] = i3 - 4;
                    System.arraycopy(this.opts[i].reps, 0, this.opts[this.optCur].reps, 1, 3);
                }
            }
        }
        optimum.state.updateLongRep();
        if (i3 >= 4) {
            this.opts[this.optCur].reps[0] = i3 - 4;
            System.arraycopy(this.opts[i].reps, 0, this.opts[this.optCur].reps, 1, 3);
        } else {
            this.opts[this.optCur].reps[0] = this.opts[i].reps[i3];
            while (i2 <= i3) {
                this.opts[this.optCur].reps[i2] = this.opts[i].reps[i2 - 1];
                i2++;
            }
            while (i2 < 4) {
                this.opts[this.optCur].reps[i2] = this.opts[i].reps[i2];
                i2++;
            }
        }
    }

    int getNextSymbol() {
        if (this.optCur < this.optEnd) {
            int i = r6.opts[r6.optCur].optPrev - r6.optCur;
            r6.optCur = r6.opts[r6.optCur].optPrev;
            r6.back = r6.opts[r6.optCur].backPrev;
            return i;
        }
        r6.optCur = 0;
        r6.optEnd = 0;
        r6.back = -1;
        if (r6.readAhead == -1) {
            r6.matches = getMatches();
        }
        int min = Math.min(r6.lz.getAvail(), 273);
        if (min < 2) {
            return 1;
        }
        int i2;
        int i3 = 0;
        for (i2 = 0; i2 < 4; i2++) {
            r6.repLens[i2] = r6.lz.getMatchLen(r6.reps[i2], min);
            if (r6.repLens[i2] < 2) {
                r6.repLens[i2] = 0;
            } else if (r6.repLens[i2] > r6.repLens[i3]) {
                i3 = i2;
            }
        }
        if (r6.repLens[i3] >= r6.niceLen) {
            r6.back = i3;
            skip(r6.repLens[i3] - 1);
            return r6.repLens[i3];
        }
        if (r6.matches.count > 0) {
            min = r6.matches.len[r6.matches.count - 1];
            i2 = r6.matches.dist[r6.matches.count - 1];
            if (min >= r6.niceLen) {
                r6.back = i2 + 4;
                skip(min - 1);
                return min;
            }
        }
        min = 0;
        i2 = r6.lz.getByte(0);
        int i4 = r6.lz.getByte(r6.reps[0] + 1);
        if (min < 2 && i2 != i4 && r6.repLens[i3] < 2) {
            return 1;
        }
        int pos = r6.lz.getPos();
        int i5 = pos & r6.posMask;
        int i6 = r6.lz.getByte(1);
        int i7 = i5;
        r6.opts[1].set1(r6.literalEncoder.getPrice(i2, i4, i6, pos, r6.state), 0, -1);
        int anyMatchPrice = getAnyMatchPrice(r6.state, i7);
        int anyRepPrice = getAnyRepPrice(anyMatchPrice, r6.state);
        if (i4 == i2) {
            i2 = getShortRepPrice(anyRepPrice, r6.state, i7);
            if (i2 < r6.opts[1].price) {
                r6.opts[1].set1(i2, 0, 0);
            }
        }
        r6.optEnd = Math.max(min, r6.repLens[i3]);
        if (r6.optEnd < 2) {
            r6.back = r6.opts[1].backPrev;
            return 1;
        }
        updatePrices();
        r6.opts[0].state.set(r6.state);
        System.arraycopy(r6.reps, 0, r6.opts[0].reps, 0, 4);
        for (i2 = r6.optEnd; i2 >= 2; i2--) {
            r6.opts[i2].reset();
        }
        for (i2 = 0; i2 < 4; i2++) {
            i3 = r6.repLens[i2];
            if (i3 >= 2) {
                int longRepPrice = getLongRepPrice(anyRepPrice, i2, r6.state, i7);
                do {
                    i6 = r6.repLenEncoder.getPrice(i3, i7) + longRepPrice;
                    if (i6 < r6.opts[i3].price) {
                        r6.opts[i3].set1(i6, 0, i2);
                    }
                    i3--;
                } while (i3 >= 2);
            }
        }
        i2 = Math.max(r6.repLens[0] + 1, 2);
        if (i2 <= min) {
            min = getNormalMatchPrice(anyMatchPrice, r6.state);
            i3 = 0;
            while (i2 > r6.matches.len[i3]) {
                i3++;
            }
            while (true) {
                i4 = r6.matches.dist[i3];
                anyMatchPrice = getMatchAndLenPrice(min, i4, i2, i7);
                if (anyMatchPrice < r6.opts[i2].price) {
                    r6.opts[i2].set1(anyMatchPrice, 0, i4 + 4);
                }
                if (i2 == r6.matches.len[i3]) {
                    i3++;
                    if (i3 == r6.matches.count) {
                        break;
                    }
                }
                i2++;
            }
        }
        i = Math.min(r6.lz.getAvail(), UnixStat.PERM_MASK);
        while (true) {
            min = r6.optCur + 1;
            r6.optCur = min;
            if (min >= r6.optEnd) {
                break;
            }
            r6.matches = getMatches();
            if (r6.matches.count > 0 && r6.matches.len[r6.matches.count - 1] >= r6.niceLen) {
                break;
            }
            anyMatchPrice = i - 1;
            anyRepPrice = pos + 1;
            i2 = anyRepPrice & r6.posMask;
            updateOptStateAndReps();
            i7 = r6.opts[r6.optCur].price + getAnyMatchPrice(r6.opts[r6.optCur].state, i2);
            i = getAnyRepPrice(i7, r6.opts[r6.optCur].state);
            calc1BytePrices(anyRepPrice, i2, anyMatchPrice, i);
            if (anyMatchPrice >= 2) {
                i4 = calcLongRepPrices(anyRepPrice, i2, anyMatchPrice, i);
                if (r6.matches.count > 0) {
                    calcNormalMatchPrices(anyRepPrice, i2, anyMatchPrice, i7, i4);
                }
            }
            i = anyMatchPrice;
            pos = anyRepPrice;
        }
        return convertOpts();
    }

    public void reset() {
        this.optCur = 0;
        this.optEnd = 0;
        super.reset();
    }
}
