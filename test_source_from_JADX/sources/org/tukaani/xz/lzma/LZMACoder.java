package org.tukaani.xz.lzma;

import com.badlogic.gdx.graphics.GL20;
import java.lang.reflect.Array;
import org.tukaani.xz.rangecoder.RangeCoder;

abstract class LZMACoder {
    static final int ALIGN_BITS = 4;
    static final int ALIGN_MASK = 15;
    static final int ALIGN_SIZE = 16;
    static final int DIST_MODEL_END = 14;
    static final int DIST_MODEL_START = 4;
    static final int DIST_SLOTS = 64;
    static final int DIST_STATES = 4;
    static final int FULL_DISTANCES = 128;
    static final int MATCH_LEN_MAX = 273;
    static final int MATCH_LEN_MIN = 2;
    static final int POS_STATES_MAX = 16;
    static final int REPS = 4;
    final short[] distAlign = new short[16];
    final short[][] distSlots = ((short[][]) Array.newInstance(short.class, new int[]{4, 64}));
    final short[][] distSpecial = new short[][]{new short[2], new short[2], new short[4], new short[4], new short[8], new short[8], new short[16], new short[16], new short[32], new short[32]};
    final short[][] isMatch = ((short[][]) Array.newInstance(short.class, new int[]{12, 16}));
    final short[] isRep = new short[12];
    final short[] isRep0 = new short[12];
    final short[][] isRep0Long = ((short[][]) Array.newInstance(short.class, new int[]{12, 16}));
    final short[] isRep1 = new short[12];
    final short[] isRep2 = new short[12];
    final int posMask;
    final int[] reps = new int[4];
    final State state = new State();

    abstract class LengthCoder {
        static final int HIGH_SYMBOLS = 256;
        static final int LOW_SYMBOLS = 8;
        static final int MID_SYMBOLS = 8;
        final short[] choice = new short[2];
        final short[] high = new short[256];
        final short[][] low = ((short[][]) Array.newInstance(short.class, new int[]{16, 8}));
        final short[][] mid = ((short[][]) Array.newInstance(short.class, new int[]{16, 8}));

        LengthCoder() {
        }

        void reset() {
            RangeCoder.initProbs(this.choice);
            for (short[] initProbs : this.low) {
                RangeCoder.initProbs(initProbs);
            }
            for (int i = 0; i < this.low.length; i++) {
                RangeCoder.initProbs(this.mid[i]);
            }
            RangeCoder.initProbs(this.high);
        }
    }

    abstract class LiteralCoder {
        private final int lc;
        private final int literalPosMask;

        abstract class LiteralSubcoder {
            final short[] probs = new short[GL20.GL_SRC_COLOR];

            LiteralSubcoder() {
            }

            void reset() {
                RangeCoder.initProbs(this.probs);
            }
        }

        LiteralCoder(int i, int i2) {
            this.lc = i;
            this.literalPosMask = (1 << i2) - 1;
        }

        final int getSubcoderIndex(int i, int i2) {
            return (i >> (8 - this.lc)) + ((i2 & this.literalPosMask) << this.lc);
        }
    }

    LZMACoder(int i) {
        this.posMask = (1 << i) - 1;
    }

    static final int getDistState(int i) {
        return i < 6 ? i - 2 : 3;
    }

    void reset() {
        int i = 0;
        this.reps[0] = 0;
        this.reps[1] = 0;
        this.reps[2] = 0;
        this.reps[3] = 0;
        this.state.reset();
        for (short[] initProbs : this.isMatch) {
            RangeCoder.initProbs(initProbs);
        }
        RangeCoder.initProbs(this.isRep);
        RangeCoder.initProbs(this.isRep0);
        RangeCoder.initProbs(this.isRep1);
        RangeCoder.initProbs(this.isRep2);
        for (short[] initProbs2 : this.isRep0Long) {
            RangeCoder.initProbs(initProbs2);
        }
        for (short[] initProbs22 : this.distSlots) {
            RangeCoder.initProbs(initProbs22);
        }
        while (i < this.distSpecial.length) {
            RangeCoder.initProbs(this.distSpecial[i]);
            i++;
        }
        RangeCoder.initProbs(this.distAlign);
    }
}
