package org.tukaani.xz.lzma;

import java.io.IOException;
import org.tukaani.xz.lz.LZDecoder;
import org.tukaani.xz.rangecoder.RangeDecoder;

public final class LZMADecoder extends LZMACoder {
    private final LiteralDecoder literalDecoder;
    private final LZDecoder lz;
    private final LengthDecoder matchLenDecoder = new LengthDecoder();
    private final RangeDecoder rc;
    private final LengthDecoder repLenDecoder = new LengthDecoder();

    private class LengthDecoder extends LengthCoder {
        private LengthDecoder() {
            super();
        }

        int decode(int i) throws IOException {
            return LZMADecoder.this.rc.decodeBit(this.choice, 0) == 0 ? LZMADecoder.this.rc.decodeBitTree(this.low[i]) + 2 : LZMADecoder.this.rc.decodeBit(this.choice, 1) == 0 ? (LZMADecoder.this.rc.decodeBitTree(this.mid[i]) + 2) + 8 : ((LZMADecoder.this.rc.decodeBitTree(this.high) + 2) + 8) + 8;
        }
    }

    private class LiteralDecoder extends LiteralCoder {
        LiteralSubdecoder[] subdecoders;

        private class LiteralSubdecoder extends LiteralSubcoder {
            private LiteralSubdecoder() {
                super();
            }

            void decode() throws IOException {
                int i = 1;
                if (LZMADecoder.this.state.isLiteral()) {
                    do {
                        i = LZMADecoder.this.rc.decodeBit(this.probs, i) | (i << 1);
                    } while (i < 256);
                } else {
                    int i2 = LZMADecoder.this.lz.getByte(LZMADecoder.this.reps[0]);
                    int i3 = 256;
                    int i4 = 1;
                    do {
                        i2 <<= 1;
                        int i5 = i2 & i3;
                        int decodeBit = LZMADecoder.this.rc.decodeBit(this.probs, (i3 + i5) + i4);
                        i4 = (i4 << 1) | decodeBit;
                        i3 &= (i5 ^ -1) ^ (0 - decodeBit);
                    } while (i4 < 256);
                    i = i4;
                }
                LZMADecoder.this.lz.putByte((byte) i);
                LZMADecoder.this.state.updateLiteral();
            }
        }

        LiteralDecoder(int i, int i2) {
            super(i, i2);
            this.subdecoders = new LiteralSubdecoder[(1 << (i + i2))];
            for (int i3 = 0; i3 < this.subdecoders.length; i3++) {
                this.subdecoders[i3] = new LiteralSubdecoder();
            }
        }

        void decode() throws IOException {
            this.subdecoders[getSubcoderIndex(LZMADecoder.this.lz.getByte(0), LZMADecoder.this.lz.getPos())].decode();
        }

        void reset() {
            for (LiteralSubdecoder reset : this.subdecoders) {
                reset.reset();
            }
        }
    }

    public LZMADecoder(LZDecoder lZDecoder, RangeDecoder rangeDecoder, int i, int i2, int i3) {
        super(i3);
        this.lz = lZDecoder;
        this.rc = rangeDecoder;
        this.literalDecoder = new LiteralDecoder(i, i2);
        reset();
    }

    private int decodeMatch(int i) throws IOException {
        this.state.updateMatch();
        this.reps[3] = this.reps[2];
        this.reps[2] = this.reps[1];
        this.reps[1] = this.reps[0];
        i = this.matchLenDecoder.decode(i);
        int decodeBitTree = this.rc.decodeBitTree(this.distSlots[LZMACoder.getDistState(i)]);
        if (decodeBitTree < 4) {
            this.reps[0] = decodeBitTree;
            return i;
        }
        int i2 = (decodeBitTree >> 1) - 1;
        this.reps[0] = (2 | (decodeBitTree & 1)) << i2;
        if (decodeBitTree < 14) {
            int[] iArr = this.reps;
            iArr[0] = this.rc.decodeReverseBitTree(this.distSpecial[decodeBitTree - 4]) | iArr[0];
            return i;
        }
        int[] iArr2 = this.reps;
        iArr2[0] = (this.rc.decodeDirectBits(i2 - 4) << 4) | iArr2[0];
        iArr2 = this.reps;
        iArr2[0] = iArr2[0] | this.rc.decodeReverseBitTree(this.distAlign);
        return i;
    }

    private int decodeRepMatch(int i) throws IOException {
        if (this.rc.decodeBit(this.isRep0, this.state.get()) != 0) {
            int i2;
            if (this.rc.decodeBit(this.isRep1, this.state.get()) == 0) {
                i2 = this.reps[1];
            } else {
                if (this.rc.decodeBit(this.isRep2, this.state.get()) == 0) {
                    i2 = this.reps[2];
                } else {
                    i2 = this.reps[3];
                    this.reps[3] = this.reps[2];
                }
                this.reps[2] = this.reps[1];
            }
            this.reps[1] = this.reps[0];
            this.reps[0] = i2;
        } else if (this.rc.decodeBit(this.isRep0Long[this.state.get()], i) == 0) {
            this.state.updateShortRep();
            return 1;
        }
        this.state.updateLongRep();
        return this.repLenDecoder.decode(i);
    }

    public void decode() throws IOException {
        this.lz.repeatPending();
        while (this.lz.hasSpace()) {
            int pos = this.lz.getPos() & this.posMask;
            if (this.rc.decodeBit(this.isMatch[this.state.get()], pos) == 0) {
                this.literalDecoder.decode();
            } else {
                this.lz.repeat(this.reps[0], this.rc.decodeBit(this.isRep, this.state.get()) == 0 ? decodeMatch(pos) : decodeRepMatch(pos));
            }
        }
        this.rc.normalize();
    }

    public boolean endMarkerDetected() {
        return this.reps[0] == -1;
    }

    public void reset() {
        super.reset();
        this.literalDecoder.reset();
        this.matchLenDecoder.reset();
        this.repLenDecoder.reset();
    }
}
