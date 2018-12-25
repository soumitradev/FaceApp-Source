package com.badlogic.gdx.utils.compression.rangecoder;

import java.io.IOException;
import java.io.InputStream;

public class Decoder {
    static final int kBitModelTotal = 2048;
    static final int kNumBitModelTotalBits = 11;
    static final int kNumMoveBits = 5;
    static final int kTopMask = -16777216;
    int Code;
    int Range;
    InputStream Stream;

    public final void SetStream(InputStream stream) {
        this.Stream = stream;
    }

    public final void ReleaseStream() {
        this.Stream = null;
    }

    public final void Init() throws IOException {
        int i = 0;
        this.Code = 0;
        this.Range = -1;
        while (i < 5) {
            this.Code = (this.Code << 8) | this.Stream.read();
            i++;
        }
    }

    public final int DecodeDirectBits(int numTotalBits) throws IOException {
        int result = 0;
        for (int i = numTotalBits; i != 0; i--) {
            this.Range >>>= 1;
            int t = (this.Code - this.Range) >>> 31;
            this.Code -= this.Range & (t - 1);
            result = (result << 1) | (1 - t);
            if ((this.Range & -16777216) == 0) {
                this.Code = (this.Code << 8) | this.Stream.read();
                this.Range <<= 8;
            }
        }
        return result;
    }

    public int DecodeBit(short[] probs, int index) throws IOException {
        int prob = probs[index];
        int newBound = (this.Range >>> 11) * prob;
        if ((this.Code ^ Integer.MIN_VALUE) < (Integer.MIN_VALUE ^ newBound)) {
            this.Range = newBound;
            probs[index] = (short) (((2048 - prob) >>> 5) + prob);
            if ((this.Range & -16777216) == 0) {
                this.Code = (this.Code << 8) | this.Stream.read();
                this.Range <<= 8;
            }
            return 0;
        }
        this.Range -= newBound;
        this.Code -= newBound;
        probs[index] = (short) (prob - (prob >>> 5));
        if ((this.Range & -16777216) == 0) {
            this.Code = (this.Code << 8) | this.Stream.read();
            this.Range <<= 8;
        }
        return 1;
    }

    public static void InitBitModels(short[] probs) {
        for (int i = 0; i < probs.length; i++) {
            probs[i] = (short) 1024;
        }
    }
}
