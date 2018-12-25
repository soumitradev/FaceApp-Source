package org.tukaani.xz.lzma;

import android.support.v4.internal.view.SupportMenu;
import android.support.v4.view.ViewCompat;
import java.lang.reflect.Array;
import org.tukaani.xz.lz.LZEncoder;
import org.tukaani.xz.lz.Matches;
import org.tukaani.xz.rangecoder.RangeEncoder;

public abstract class LZMAEncoder extends LZMACoder {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int ALIGN_PRICE_UPDATE_INTERVAL = 16;
    private static final int DIST_PRICE_UPDATE_INTERVAL = 128;
    private static final int LZMA2_COMPRESSED_LIMIT = 65510;
    private static final int LZMA2_UNCOMPRESSED_LIMIT = 2096879;
    public static final int MODE_FAST = 1;
    public static final int MODE_NORMAL = 2;
    static /* synthetic */ Class class$org$tukaani$xz$lzma$LZMAEncoder = class$("org.tukaani.xz.lzma.LZMAEncoder");
    private int alignPriceCount = 0;
    private final int[] alignPrices = new int[16];
    int back = 0;
    private int distPriceCount = 0;
    private final int[][] distSlotPrices;
    private final int distSlotPricesSize;
    private final int[][] fullDistPrices = ((int[][]) Array.newInstance(int.class, new int[]{4, 128}));
    final LiteralEncoder literalEncoder;
    final LZEncoder lz;
    final LengthEncoder matchLenEncoder;
    final int niceLen;
    private final RangeEncoder rc;
    int readAhead = -1;
    final LengthEncoder repLenEncoder;
    private int uncompressedSize = 0;

    class LengthEncoder extends LengthCoder {
        private static final int PRICE_UPDATE_INTERVAL = 32;
        private final int[] counters;
        private final int[][] prices;

        LengthEncoder(int i, int i2) {
            super();
            i = 1 << i;
            this.counters = new int[i];
            this.prices = (int[][]) Array.newInstance(int.class, new int[]{i, Math.max((i2 - 2) + 1, 16)});
        }

        private void updatePrices(int i) {
            int bitPrice = RangeEncoder.getBitPrice(this.choice[0], 0);
            int i2 = 0;
            while (i2 < 8) {
                this.prices[i][i2] = RangeEncoder.getBitTreePrice(this.low[i], i2) + bitPrice;
                i2++;
            }
            bitPrice = RangeEncoder.getBitPrice(this.choice[0], 1);
            int bitPrice2 = RangeEncoder.getBitPrice(this.choice[1], 0);
            while (i2 < 16) {
                this.prices[i][i2] = (bitPrice + bitPrice2) + RangeEncoder.getBitTreePrice(this.mid[i], i2 - 8);
                i2++;
            }
            bitPrice2 = RangeEncoder.getBitPrice(this.choice[1], 1);
            while (i2 < this.prices[i].length) {
                this.prices[i][i2] = (bitPrice + bitPrice2) + RangeEncoder.getBitTreePrice(this.high, (i2 - 8) - 8);
                i2++;
            }
        }

        void encode(int i, int i2) {
            RangeEncoder access$200;
            short[] sArr;
            int[] iArr;
            i -= 2;
            if (i < 8) {
                LZMAEncoder.this.rc.encodeBit(this.choice, 0, 0);
                access$200 = LZMAEncoder.this.rc;
                sArr = this.low[i2];
            } else {
                LZMAEncoder.this.rc.encodeBit(this.choice, 0, 1);
                i -= 8;
                if (i < 8) {
                    LZMAEncoder.this.rc.encodeBit(this.choice, 1, 0);
                    access$200 = LZMAEncoder.this.rc;
                    sArr = this.mid[i2];
                } else {
                    LZMAEncoder.this.rc.encodeBit(this.choice, 1, 1);
                    LZMAEncoder.this.rc.encodeBitTree(this.high, i - 8);
                    iArr = this.counters;
                    iArr[i2] = iArr[i2] - 1;
                }
            }
            access$200.encodeBitTree(sArr, i);
            iArr = this.counters;
            iArr[i2] = iArr[i2] - 1;
        }

        int getPrice(int i, int i2) {
            return this.prices[i2][i - 2];
        }

        void reset() {
            super.reset();
            for (int i = 0; i < this.counters.length; i++) {
                this.counters[i] = 0;
            }
        }

        void updatePrices() {
            for (int i = 0; i < this.counters.length; i++) {
                if (this.counters[i] <= 0) {
                    this.counters[i] = 32;
                    updatePrices(i);
                }
            }
        }
    }

    class LiteralEncoder extends LiteralCoder {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        LiteralSubencoder[] subencoders;

        private class LiteralSubencoder extends LiteralSubcoder {
            private LiteralSubencoder() {
                super();
            }

            void encode() {
                int i = 256;
                int i2 = LZMAEncoder.this.lz.getByte(LZMAEncoder.this.readAhead) | 256;
                if (LZMAEncoder.this.state.isLiteral()) {
                    do {
                        LZMAEncoder.this.rc.encodeBit(this.probs, i2 >>> 8, (i2 >>> 7) & 1);
                        i2 <<= 1;
                    } while (i2 < 65536);
                } else {
                    int i3 = LZMAEncoder.this.lz.getByte((LZMAEncoder.this.reps[0] + 1) + LZMAEncoder.this.readAhead);
                    do {
                        i3 <<= 1;
                        LZMAEncoder.this.rc.encodeBit(this.probs, ((i3 & i) + i) + (i2 >>> 8), (i2 >>> 7) & 1);
                        i2 <<= 1;
                        i &= (i3 ^ i2) ^ -1;
                    } while (i2 < 65536);
                }
                LZMAEncoder.this.state.updateLiteral();
            }

            int getMatchedPrice(int i, int i2) {
                int i3 = 256;
                i |= 256;
                int i4 = 0;
                do {
                    i2 <<= 1;
                    int i5 = (i >>> 7) & 1;
                    i4 += RangeEncoder.getBitPrice(this.probs[((i2 & i3) + i3) + (i >>> 8)], i5);
                    i <<= 1;
                    i3 &= (i2 ^ i) ^ -1;
                } while (i < 65536);
                return i4;
            }

            int getNormalPrice(int i) {
                i |= 256;
                int i2 = 0;
                do {
                    int i3 = (i >>> 7) & 1;
                    i2 += RangeEncoder.getBitPrice(this.probs[i >>> 8], i3);
                    i <<= 1;
                } while (i < 65536);
                return i2;
            }
        }

        static {
            if (LZMAEncoder.class$org$tukaani$xz$lzma$LZMAEncoder == null) {
                LZMAEncoder.class$org$tukaani$xz$lzma$LZMAEncoder = LZMAEncoder.class$("org.tukaani.xz.lzma.LZMAEncoder");
            } else {
                Class cls = LZMAEncoder.class$org$tukaani$xz$lzma$LZMAEncoder;
            }
        }

        LiteralEncoder(int i, int i2) {
            super(i, i2);
            this.subencoders = new LiteralSubencoder[(1 << (i + i2))];
            for (int i3 = 0; i3 < this.subencoders.length; i3++) {
                this.subencoders[i3] = new LiteralSubencoder();
            }
        }

        void encode() {
            this.subencoders[getSubcoderIndex(LZMAEncoder.this.lz.getByte(LZMAEncoder.this.readAhead + 1), LZMAEncoder.this.lz.getPos() - LZMAEncoder.this.readAhead)].encode();
        }

        void encodeInit() {
            this.subencoders[0].encode();
        }

        int getPrice(int i, int i2, int i3, int i4, State state) {
            int bitPrice = RangeEncoder.getBitPrice(LZMAEncoder.this.isMatch[state.get()][LZMAEncoder.this.posMask & i4], 0);
            i3 = getSubcoderIndex(i3, i4);
            return bitPrice + (state.isLiteral() ? this.subencoders[i3].getNormalPrice(i) : this.subencoders[i3].getMatchedPrice(i, i2));
        }

        void reset() {
            for (LiteralSubencoder reset : this.subencoders) {
                reset.reset();
            }
        }
    }

    static {
        if (class$org$tukaani$xz$lzma$LZMAEncoder == null) {
        } else {
            Class cls = class$org$tukaani$xz$lzma$LZMAEncoder;
        }
    }

    LZMAEncoder(RangeEncoder rangeEncoder, LZEncoder lZEncoder, int i, int i2, int i3, int i4, int i5) {
        super(i3);
        this.rc = rangeEncoder;
        this.lz = lZEncoder;
        this.niceLen = i5;
        this.literalEncoder = new LiteralEncoder(i, i2);
        this.matchLenEncoder = new LengthEncoder(i3, i5);
        this.repLenEncoder = new LengthEncoder(i3, i5);
        this.distSlotPricesSize = getDistSlot(i4 - 1) + 1;
        this.distSlotPrices = (int[][]) Array.newInstance(int.class, new int[]{4, this.distSlotPricesSize});
        reset();
    }

    static /* synthetic */ Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (Throwable e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    private boolean encodeInit() {
        if (!this.lz.hasEnoughData(0)) {
            return false;
        }
        skip(1);
        this.rc.encodeBit(this.isMatch[this.state.get()], 0, 0);
        this.literalEncoder.encodeInit();
        this.readAhead--;
        this.uncompressedSize++;
        return true;
    }

    private void encodeMatch(int i, int i2, int i3) {
        this.state.updateMatch();
        this.matchLenEncoder.encode(i2, i3);
        i3 = getDistSlot(i);
        this.rc.encodeBitTree(this.distSlots[LZMACoder.getDistState(i2)], i3);
        if (i3 >= 4) {
            int i4 = (i3 >>> 1) - 1;
            int i5 = i - (((i3 & 1) | 2) << i4);
            if (i3 < 14) {
                this.rc.encodeReverseBitTree(this.distSpecial[i3 - 4], i5);
            } else {
                this.rc.encodeDirectBits(i5 >>> 4, i4 - 4);
                this.rc.encodeReverseBitTree(this.distAlign, i5 & 15);
                this.alignPriceCount--;
            }
        }
        this.reps[3] = this.reps[2];
        this.reps[2] = this.reps[1];
        this.reps[1] = this.reps[0];
        this.reps[0] = i;
        this.distPriceCount--;
    }

    private void encodeRepMatch(int i, int i2, int i3) {
        int i4 = 0;
        if (i == 0) {
            this.rc.encodeBit(this.isRep0, this.state.get(), 0);
            RangeEncoder rangeEncoder = this.rc;
            short[] sArr = this.isRep0Long[this.state.get()];
            if (i2 != 1) {
                i4 = 1;
            }
            rangeEncoder.encodeBit(sArr, i3, i4);
        } else {
            int i5 = this.reps[i];
            this.rc.encodeBit(this.isRep0, this.state.get(), 1);
            if (i == 1) {
                this.rc.encodeBit(this.isRep1, this.state.get(), 0);
            } else {
                this.rc.encodeBit(this.isRep1, this.state.get(), 1);
                this.rc.encodeBit(this.isRep2, this.state.get(), i - 2);
                if (i == 3) {
                    this.reps[3] = this.reps[2];
                }
                this.reps[2] = this.reps[1];
            }
            this.reps[1] = this.reps[0];
            this.reps[0] = i5;
        }
        if (i2 == 1) {
            this.state.updateShortRep();
            return;
        }
        this.repLenEncoder.encode(i2, i3);
        this.state.updateLongRep();
    }

    private boolean encodeSymbol() {
        if (!this.lz.hasEnoughData(this.readAhead + 1)) {
            return false;
        }
        int nextSymbol = getNextSymbol();
        int pos = (this.lz.getPos() - this.readAhead) & this.posMask;
        if (this.back == -1) {
            this.rc.encodeBit(this.isMatch[this.state.get()], pos, 0);
            this.literalEncoder.encode();
        } else {
            this.rc.encodeBit(this.isMatch[this.state.get()], pos, 1);
            if (this.back < 4) {
                this.rc.encodeBit(this.isRep, this.state.get(), 1);
                encodeRepMatch(this.back, nextSymbol, pos);
            } else {
                this.rc.encodeBit(this.isRep, this.state.get(), 0);
                encodeMatch(this.back - 4, nextSymbol, pos);
            }
        }
        this.readAhead -= nextSymbol;
        this.uncompressedSize += nextSymbol;
        return true;
    }

    public static int getDistSlot(int i) {
        if (i <= 4) {
            return i;
        }
        int i2;
        int i3;
        if ((SupportMenu.CATEGORY_MASK & i) == 0) {
            i2 = i << 16;
            i3 = 15;
        } else {
            i2 = i;
            i3 = 31;
        }
        if ((ViewCompat.MEASURED_STATE_MASK & i2) == 0) {
            i2 <<= 8;
            i3 -= 8;
        }
        if ((-268435456 & i2) == 0) {
            i2 <<= 4;
            i3 -= 4;
        }
        if ((-1073741824 & i2) == 0) {
            i2 <<= 2;
            i3 -= 2;
        }
        if ((i2 & Integer.MIN_VALUE) == 0) {
            i3--;
        }
        return (i3 << 1) + ((i >>> (i3 - 1)) & 1);
    }

    public static LZMAEncoder getInstance(RangeEncoder rangeEncoder, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
        switch (i4) {
            case 1:
                return new LZMAEncoderFast(rangeEncoder, i, i2, i3, i5, i6, i7, i8, i9);
            case 2:
                return new LZMAEncoderNormal(rangeEncoder, i, i2, i3, i5, i6, i7, i8, i9);
            default:
                throw new IllegalArgumentException();
        }
    }

    public static int getMemoryUsage(int i, int i2, int i3, int i4) {
        switch (i) {
            case 1:
                i = LZMAEncoderFast.getMemoryUsage(i2, i3, i4);
                break;
            case 2:
                i = LZMAEncoderNormal.getMemoryUsage(i2, i3, i4);
                break;
            default:
                throw new IllegalArgumentException();
        }
        return i + 80;
    }

    private void updateAlignPrices() {
        this.alignPriceCount = 16;
        for (int i = 0; i < 16; i++) {
            this.alignPrices[i] = RangeEncoder.getReverseBitTreePrice(this.distAlign, i);
        }
    }

    private void updateDistPrices() {
        int i;
        this.distPriceCount = 128;
        int i2 = 0;
        while (true) {
            int i3 = 14;
            if (i2 >= 4) {
                break;
            }
            for (i = 0; i < this.distSlotPricesSize; i++) {
                this.distSlotPrices[i2][i] = RangeEncoder.getBitTreePrice(this.distSlots[i2], i);
            }
            while (i3 < this.distSlotPricesSize) {
                i = ((i3 >>> 1) - 1) - 4;
                int[] iArr = this.distSlotPrices[i2];
                iArr[i3] = iArr[i3] + RangeEncoder.getDirectBitsPrice(i);
                i3++;
            }
            for (i3 = 0; i3 < 4; i3++) {
                this.fullDistPrices[i2][i3] = this.distSlotPrices[i2][i3];
            }
            i2++;
        }
        i2 = 4;
        i = 4;
        while (i2 < 14) {
            int i4 = ((i2 & 1) | 2) << ((i2 >>> 1) - 1);
            int i5 = i2 - 4;
            int length = this.distSpecial[i5].length;
            int i6 = i;
            for (i = 0; i < length; i++) {
                int reverseBitTreePrice = RangeEncoder.getReverseBitTreePrice(this.distSpecial[i5], i6 - i4);
                for (int i7 = 0; i7 < 4; i7++) {
                    this.fullDistPrices[i7][i6] = this.distSlotPrices[i7][i2] + reverseBitTreePrice;
                }
                i6++;
            }
            i2++;
            i = i6;
        }
    }

    public boolean encodeForLZMA2() {
        if (!this.lz.isStarted() && !encodeInit()) {
            return false;
        }
        while (this.uncompressedSize <= LZMA2_UNCOMPRESSED_LIMIT && this.rc.getPendingSize() <= LZMA2_COMPRESSED_LIMIT) {
            if (!encodeSymbol()) {
                return false;
            }
        }
        return true;
    }

    int getAnyMatchPrice(State state, int i) {
        return RangeEncoder.getBitPrice(this.isMatch[state.get()][i], 1);
    }

    int getAnyRepPrice(int i, State state) {
        return i + RangeEncoder.getBitPrice(this.isRep[state.get()], 1);
    }

    public LZEncoder getLZEncoder() {
        return this.lz;
    }

    int getLongRepAndLenPrice(int i, int i2, State state, int i3) {
        return getLongRepPrice(getAnyRepPrice(getAnyMatchPrice(state, i3), state), i, state, i3) + this.repLenEncoder.getPrice(i2, i3);
    }

    int getLongRepPrice(int i, int i2, State state, int i3) {
        if (i2 == 0) {
            i2 = RangeEncoder.getBitPrice(this.isRep0[state.get()], 0) + RangeEncoder.getBitPrice(this.isRep0Long[state.get()][i3], 1);
        } else {
            i += RangeEncoder.getBitPrice(this.isRep0[state.get()], 1);
            if (i2 != 1) {
                return i + (RangeEncoder.getBitPrice(this.isRep1[state.get()], 1) + RangeEncoder.getBitPrice(this.isRep2[state.get()], i2 - 2));
            }
            i2 = RangeEncoder.getBitPrice(this.isRep1[state.get()], 0);
        }
        return i + i2;
    }

    int getMatchAndLenPrice(int i, int i2, int i3, int i4) {
        i += this.matchLenEncoder.getPrice(i3, i4);
        i3 = LZMACoder.getDistState(i3);
        if (i2 < 128) {
            return i + this.fullDistPrices[i3][i2];
        }
        return i + (this.distSlotPrices[i3][getDistSlot(i2)] + this.alignPrices[i2 & 15]);
    }

    Matches getMatches() {
        this.readAhead++;
        return this.lz.getMatches();
    }

    abstract int getNextSymbol();

    int getNormalMatchPrice(int i, State state) {
        return i + RangeEncoder.getBitPrice(this.isRep[state.get()], 0);
    }

    int getShortRepPrice(int i, State state, int i2) {
        return (i + RangeEncoder.getBitPrice(this.isRep0[state.get()], 0)) + RangeEncoder.getBitPrice(this.isRep0Long[state.get()][i2], 0);
    }

    public int getUncompressedSize() {
        return this.uncompressedSize;
    }

    public void reset() {
        super.reset();
        this.literalEncoder.reset();
        this.matchLenEncoder.reset();
        this.repLenEncoder.reset();
        this.distPriceCount = 0;
        this.alignPriceCount = 0;
        this.uncompressedSize += this.readAhead + 1;
        this.readAhead = -1;
    }

    public void resetUncompressedSize() {
        this.uncompressedSize = 0;
    }

    void skip(int i) {
        this.readAhead += i;
        this.lz.skip(i);
    }

    void updatePrices() {
        if (this.distPriceCount <= 0) {
            updateDistPrices();
        }
        if (this.alignPriceCount <= 0) {
            updateAlignPrices();
        }
        this.matchLenEncoder.updatePrices();
        this.repLenEncoder.updatePrices();
    }
}
