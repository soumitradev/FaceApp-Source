package com.badlogic.gdx.utils.compression.lzma;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.compression.lz.OutWindow;
import com.badlogic.gdx.utils.compression.rangecoder.BitTreeDecoder;
import java.io.IOException;
import name.antonsmirnov.firmata.writer.ReportAnalogPinMessageWriter;

public class Decoder {
    int m_DictionarySize = -1;
    int m_DictionarySizeCheck = -1;
    short[] m_IsMatchDecoders = new short[ReportAnalogPinMessageWriter.COMMAND];
    short[] m_IsRep0LongDecoders = new short[ReportAnalogPinMessageWriter.COMMAND];
    short[] m_IsRepDecoders = new short[12];
    short[] m_IsRepG0Decoders = new short[12];
    short[] m_IsRepG1Decoders = new short[12];
    short[] m_IsRepG2Decoders = new short[12];
    LenDecoder m_LenDecoder = new LenDecoder();
    LiteralDecoder m_LiteralDecoder = new LiteralDecoder();
    OutWindow m_OutWindow = new OutWindow();
    BitTreeDecoder m_PosAlignDecoder = new BitTreeDecoder(4);
    short[] m_PosDecoders = new short[114];
    BitTreeDecoder[] m_PosSlotDecoder = new BitTreeDecoder[4];
    int m_PosStateMask;
    com.badlogic.gdx.utils.compression.rangecoder.Decoder m_RangeDecoder = new com.badlogic.gdx.utils.compression.rangecoder.Decoder();
    LenDecoder m_RepLenDecoder = new LenDecoder();

    class LenDecoder {
        short[] m_Choice = new short[2];
        BitTreeDecoder m_HighCoder = new BitTreeDecoder(8);
        BitTreeDecoder[] m_LowCoder = new BitTreeDecoder[16];
        BitTreeDecoder[] m_MidCoder = new BitTreeDecoder[16];
        int m_NumPosStates = null;

        LenDecoder() {
        }

        public void Create(int numPosStates) {
            while (this.m_NumPosStates < numPosStates) {
                this.m_LowCoder[this.m_NumPosStates] = new BitTreeDecoder(3);
                this.m_MidCoder[this.m_NumPosStates] = new BitTreeDecoder(3);
                this.m_NumPosStates++;
            }
        }

        public void Init() {
            com.badlogic.gdx.utils.compression.rangecoder.Decoder.InitBitModels(this.m_Choice);
            for (int posState = 0; posState < this.m_NumPosStates; posState++) {
                this.m_LowCoder[posState].Init();
                this.m_MidCoder[posState].Init();
            }
            this.m_HighCoder.Init();
        }

        public int Decode(com.badlogic.gdx.utils.compression.rangecoder.Decoder rangeDecoder, int posState) throws IOException {
            if (rangeDecoder.DecodeBit(this.m_Choice, 0) == 0) {
                return this.m_LowCoder[posState].Decode(rangeDecoder);
            }
            int symbol;
            if (rangeDecoder.DecodeBit(this.m_Choice, 1) == 0) {
                symbol = 8 + this.m_MidCoder[posState].Decode(rangeDecoder);
            } else {
                symbol = 8 + (this.m_HighCoder.Decode(rangeDecoder) + 8);
            }
            return symbol;
        }
    }

    class LiteralDecoder {
        Decoder2[] m_Coders;
        int m_NumPosBits;
        int m_NumPrevBits;
        int m_PosMask;

        class Decoder2 {
            short[] m_Decoders = new short[GL20.GL_SRC_COLOR];

            Decoder2() {
            }

            public void Init() {
                com.badlogic.gdx.utils.compression.rangecoder.Decoder.InitBitModels(this.m_Decoders);
            }

            public byte DecodeNormal(com.badlogic.gdx.utils.compression.rangecoder.Decoder rangeDecoder) throws IOException {
                int symbol = 1;
                do {
                    symbol = (symbol << 1) | rangeDecoder.DecodeBit(this.m_Decoders, symbol);
                } while (symbol < 256);
                return (byte) symbol;
            }

            public byte DecodeWithMatchByte(com.badlogic.gdx.utils.compression.rangecoder.Decoder rangeDecoder, byte matchByte) throws IOException {
                byte matchByte2 = matchByte;
                int symbol = 1;
                do {
                    int matchBit = (matchByte2 >> 7) & 1;
                    matchByte2 = (byte) (matchByte2 << 1);
                    int bit = rangeDecoder.DecodeBit(this.m_Decoders, ((matchBit + 1) << 8) + symbol);
                    symbol = (symbol << 1) | bit;
                    if (matchBit != bit) {
                        while (symbol < 256) {
                            symbol = (symbol << 1) | rangeDecoder.DecodeBit(this.m_Decoders, symbol);
                        }
                    }
                    return (byte) symbol;
                } while (symbol < 256);
                return (byte) symbol;
            }
        }

        LiteralDecoder() {
        }

        public void Create(int numPosBits, int numPrevBits) {
            if (this.m_Coders == null || this.m_NumPrevBits != numPrevBits || this.m_NumPosBits != numPosBits) {
                this.m_NumPosBits = numPosBits;
                this.m_PosMask = (1 << numPosBits) - 1;
                this.m_NumPrevBits = numPrevBits;
                int numStates = 1 << (this.m_NumPrevBits + this.m_NumPosBits);
                this.m_Coders = new Decoder2[numStates];
                for (int i = 0; i < numStates; i++) {
                    this.m_Coders[i] = new Decoder2();
                }
            }
        }

        public void Init() {
            int numStates = 1 << (this.m_NumPrevBits + this.m_NumPosBits);
            for (int i = 0; i < numStates; i++) {
                this.m_Coders[i].Init();
            }
        }

        Decoder2 GetDecoder(int pos, byte prevByte) {
            return this.m_Coders[((this.m_PosMask & pos) << this.m_NumPrevBits) + ((prevByte & 255) >>> (8 - this.m_NumPrevBits))];
        }
    }

    public Decoder() {
        for (int i = 0; i < 4; i++) {
            this.m_PosSlotDecoder[i] = new BitTreeDecoder(6);
        }
    }

    boolean SetDictionarySize(int dictionarySize) {
        if (dictionarySize < 0) {
            return false;
        }
        if (this.m_DictionarySize != dictionarySize) {
            this.m_DictionarySize = dictionarySize;
            this.m_DictionarySizeCheck = Math.max(this.m_DictionarySize, 1);
            this.m_OutWindow.Create(Math.max(this.m_DictionarySizeCheck, 4096));
        }
        return true;
    }

    boolean SetLcLpPb(int lc, int lp, int pb) {
        if (lc <= 8 && lp <= 4) {
            if (pb <= 4) {
                this.m_LiteralDecoder.Create(lp, lc);
                int numPosStates = 1 << pb;
                this.m_LenDecoder.Create(numPosStates);
                this.m_RepLenDecoder.Create(numPosStates);
                this.m_PosStateMask = numPosStates - 1;
                return true;
            }
        }
        return false;
    }

    void Init() throws IOException {
        int i = 0;
        this.m_OutWindow.Init(false);
        com.badlogic.gdx.utils.compression.rangecoder.Decoder.InitBitModels(this.m_IsMatchDecoders);
        com.badlogic.gdx.utils.compression.rangecoder.Decoder.InitBitModels(this.m_IsRep0LongDecoders);
        com.badlogic.gdx.utils.compression.rangecoder.Decoder.InitBitModels(this.m_IsRepDecoders);
        com.badlogic.gdx.utils.compression.rangecoder.Decoder.InitBitModels(this.m_IsRepG0Decoders);
        com.badlogic.gdx.utils.compression.rangecoder.Decoder.InitBitModels(this.m_IsRepG1Decoders);
        com.badlogic.gdx.utils.compression.rangecoder.Decoder.InitBitModels(this.m_IsRepG2Decoders);
        com.badlogic.gdx.utils.compression.rangecoder.Decoder.InitBitModels(this.m_PosDecoders);
        this.m_LiteralDecoder.Init();
        while (true) {
            int i2 = i;
            if (i2 < 4) {
                this.m_PosSlotDecoder[i2].Init();
                i = i2 + 1;
            } else {
                this.m_LenDecoder.Init();
                this.m_RepLenDecoder.Init();
                this.m_PosAlignDecoder.Init();
                this.m_RangeDecoder.Init();
                return;
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean Code(java.io.InputStream r23, java.io.OutputStream r24, long r25) throws java.io.IOException {
        /*
        r22 = this;
        r0 = r22;
        r3 = r0.m_RangeDecoder;
        r4 = r23;
        r3.SetStream(r4);
        r3 = r0.m_OutWindow;
        r5 = r24;
        r3.SetStream(r5);
        r22.Init();
        r3 = com.badlogic.gdx.utils.compression.lzma.Base.StateInit();
        r6 = 0;
        r7 = 0;
        r8 = 0;
        r9 = 0;
        r10 = 0;
        r13 = r7;
        r7 = r6;
        r6 = r3;
        r3 = 0;
    L_0x0021:
        r14 = 0;
        r16 = (r25 > r14 ? 1 : (r25 == r14 ? 0 : -1));
        if (r16 < 0) goto L_0x0030;
    L_0x0027:
        r15 = (r10 > r25 ? 1 : (r10 == r25 ? 0 : -1));
        if (r15 >= 0) goto L_0x002c;
    L_0x002b:
        goto L_0x0030;
    L_0x002c:
        r21 = r3;
        goto L_0x0135;
    L_0x0030:
        r15 = (int) r10;
        r12 = r0.m_PosStateMask;
        r12 = r12 & r15;
        r15 = r0.m_RangeDecoder;
        r14 = r0.m_IsMatchDecoders;
        r16 = r6 << 4;
        r1 = r16 + r12;
        r1 = r15.DecodeBit(r14, r1);
        if (r1 != 0) goto L_0x0078;
    L_0x0042:
        r1 = r0.m_LiteralDecoder;
        r2 = (int) r10;
        r1 = r1.GetDecoder(r2, r3);
        r2 = com.badlogic.gdx.utils.compression.lzma.Base.StateIsCharState(r6);
        if (r2 != 0) goto L_0x005c;
    L_0x004f:
        r2 = r0.m_RangeDecoder;
        r14 = r0.m_OutWindow;
        r14 = r14.GetByte(r7);
        r2 = r1.DecodeWithMatchByte(r2, r14);
    L_0x005b:
        goto L_0x0063;
    L_0x005c:
        r2 = r0.m_RangeDecoder;
        r2 = r1.DecodeNormal(r2);
        goto L_0x005b;
    L_0x0063:
        r3 = r0.m_OutWindow;
        r3.PutByte(r2);
        r3 = com.badlogic.gdx.utils.compression.lzma.Base.StateUpdateChar(r6);
        r14 = 1;
        r18 = r10 + r14;
        r1 = r2;
        r6 = r3;
        r10 = r18;
        r3 = 0;
        goto L_0x016b;
    L_0x0078:
        r1 = r0.m_RangeDecoder;
        r2 = r0.m_IsRepDecoders;
        r1 = r1.DecodeBit(r2, r6);
        r2 = 1;
        if (r1 != r2) goto L_0x00d2;
    L_0x0083:
        r1 = 0;
        r2 = r0.m_RangeDecoder;
        r14 = r0.m_IsRepG0Decoders;
        r2 = r2.DecodeBit(r14, r6);
        if (r2 != 0) goto L_0x00a1;
    L_0x008e:
        r2 = r0.m_RangeDecoder;
        r14 = r0.m_IsRep0LongDecoders;
        r15 = r6 << 4;
        r15 = r15 + r12;
        r2 = r2.DecodeBit(r14, r15);
        if (r2 != 0) goto L_0x00be;
    L_0x009b:
        r6 = com.badlogic.gdx.utils.compression.lzma.Base.StateUpdateShortRep(r6);
        r1 = 1;
        goto L_0x00be;
    L_0x00a1:
        r2 = r0.m_RangeDecoder;
        r14 = r0.m_IsRepG1Decoders;
        r2 = r2.DecodeBit(r14, r6);
        if (r2 != 0) goto L_0x00ad;
    L_0x00ab:
        r2 = r13;
        goto L_0x00bc;
    L_0x00ad:
        r2 = r0.m_RangeDecoder;
        r14 = r0.m_IsRepG2Decoders;
        r2 = r2.DecodeBit(r14, r6);
        if (r2 != 0) goto L_0x00b9;
    L_0x00b7:
        r2 = r8;
        goto L_0x00bb;
    L_0x00b9:
        r2 = r9;
        r9 = r8;
    L_0x00bb:
        r8 = r13;
    L_0x00bc:
        r13 = r7;
        r7 = r2;
    L_0x00be:
        if (r1 != 0) goto L_0x00ce;
    L_0x00c0:
        r2 = r0.m_RepLenDecoder;
        r14 = r0.m_RangeDecoder;
        r2 = r2.Decode(r14, r12);
        r1 = r2 + 2;
        r6 = com.badlogic.gdx.utils.compression.lzma.Base.StateUpdateRep(r6);
    L_0x00ce:
        r21 = r3;
        goto L_0x0151;
    L_0x00d2:
        r9 = r8;
        r8 = r13;
        r13 = r7;
        r1 = r0.m_LenDecoder;
        r2 = r0.m_RangeDecoder;
        r1 = r1.Decode(r2, r12);
        r1 = r1 + 2;
        r6 = com.badlogic.gdx.utils.compression.lzma.Base.StateUpdateMatch(r6);
        r2 = r0.m_PosSlotDecoder;
        r14 = com.badlogic.gdx.utils.compression.lzma.Base.GetLenToPosState(r1);
        r2 = r2[r14];
        r14 = r0.m_RangeDecoder;
        r2 = r2.Decode(r14);
        r14 = 4;
        if (r2 < r14) goto L_0x014c;
    L_0x00f4:
        r15 = r2 >> 1;
        r16 = 1;
        r15 = r15 + -1;
        r16 = r2 & 1;
        r16 = r16 | 2;
        r7 = r16 << r15;
        r14 = 14;
        if (r2 >= r14) goto L_0x0118;
    L_0x0104:
        r14 = r0.m_PosDecoders;
        r16 = r7 - r2;
        r20 = r1;
        r17 = 1;
        r1 = r16 + -1;
        r21 = r3;
        r3 = r0.m_RangeDecoder;
        r1 = com.badlogic.gdx.utils.compression.rangecoder.BitTreeDecoder.ReverseDecode(r14, r1, r3, r15);
        r7 = r7 + r1;
        goto L_0x0148;
    L_0x0118:
        r20 = r1;
        r21 = r3;
        r1 = r0.m_RangeDecoder;
        r3 = r15 + -4;
        r1 = r1.DecodeDirectBits(r3);
        r3 = 4;
        r1 = r1 << r3;
        r7 = r7 + r1;
        r1 = r0.m_PosAlignDecoder;
        r3 = r0.m_RangeDecoder;
        r1 = r1.ReverseDecode(r3);
        r7 = r7 + r1;
        if (r7 >= 0) goto L_0x0148;
    L_0x0132:
        r1 = -1;
        if (r7 != r1) goto L_0x0146;
    L_0x0135:
        r1 = r0.m_OutWindow;
        r1.Flush();
        r1 = r0.m_OutWindow;
        r1.ReleaseStream();
        r1 = r0.m_RangeDecoder;
        r1.ReleaseStream();
        r1 = 1;
        return r1;
    L_0x0146:
        r1 = 0;
        return r1;
        r1 = r20;
        goto L_0x0151;
    L_0x014c:
        r20 = r1;
        r21 = r3;
        r7 = r2;
    L_0x0151:
        r2 = (long) r7;
        r14 = (r2 > r10 ? 1 : (r2 == r10 ? 0 : -1));
        if (r14 >= 0) goto L_0x016e;
    L_0x0156:
        r2 = r0.m_DictionarySizeCheck;
        if (r7 < r2) goto L_0x015b;
    L_0x015a:
        goto L_0x016e;
    L_0x015b:
        r2 = r0.m_OutWindow;
        r2.CopyBlock(r7, r1);
        r2 = (long) r1;
        r14 = r10 + r2;
        r2 = r0.m_OutWindow;
        r3 = 0;
        r1 = r2.GetByte(r3);
        r10 = r14;
    L_0x016b:
        r3 = r1;
        goto L_0x0021;
    L_0x016e:
        r3 = 0;
        return r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.badlogic.gdx.utils.compression.lzma.Decoder.Code(java.io.InputStream, java.io.OutputStream, long):boolean");
    }

    public boolean SetDecoderProperties(byte[] properties) {
        if (properties.length < 5) {
            return false;
        }
        int val = properties[0] & 255;
        int lc = val % 9;
        int remainder = val / 9;
        int lp = remainder % 5;
        int pb = remainder / 5;
        int dictionarySize = 0;
        for (int i = 0; i < 4; i++) {
            dictionarySize += (properties[i + 1] & 255) << (i * 8);
        }
        if (SetLcLpPb(lc, lp, pb)) {
            return SetDictionarySize(dictionarySize);
        }
        return false;
    }
}
