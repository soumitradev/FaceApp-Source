package com.badlogic.gdx.utils.compression.lzma;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.compression.ICodeProgress;
import com.badlogic.gdx.utils.compression.lz.BinTree;
import com.badlogic.gdx.utils.compression.rangecoder.BitTreeEncoder;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import name.antonsmirnov.firmata.writer.ReportAnalogPinMessageWriter;

public class Encoder {
    public static final int EMatchFinderTypeBT2 = 0;
    public static final int EMatchFinderTypeBT4 = 1;
    static byte[] g_FastPos = new byte[2048];
    static final int kDefaultDictionaryLogSize = 22;
    static final int kIfinityPrice = 268435455;
    static final int kNumFastBytesDefault = 32;
    public static final int kNumLenSpecSymbols = 16;
    static final int kNumOpts = 4096;
    public static final int kPropSize = 5;
    int _additionalOffset;
    int _alignPriceCount;
    int[] _alignPrices = new int[16];
    int _dictionarySize;
    int _dictionarySizePrev;
    int _distTableSize = 44;
    int[] _distancesPrices = new int[512];
    boolean _finished;
    InputStream _inStream;
    short[] _isMatch = new short[ReportAnalogPinMessageWriter.COMMAND];
    short[] _isRep = new short[12];
    short[] _isRep0Long = new short[ReportAnalogPinMessageWriter.COMMAND];
    short[] _isRepG0 = new short[12];
    short[] _isRepG1 = new short[12];
    short[] _isRepG2 = new short[12];
    LenPriceTableEncoder _lenEncoder = new LenPriceTableEncoder();
    LiteralEncoder _literalEncoder = new LiteralEncoder();
    int _longestMatchLength;
    boolean _longestMatchWasFound;
    int[] _matchDistances = new int[548];
    BinTree _matchFinder = null;
    int _matchFinderType;
    int _matchPriceCount;
    boolean _needReleaseMFStream;
    int _numDistancePairs;
    int _numFastBytes = 32;
    int _numFastBytesPrev;
    int _numLiteralContextBits;
    int _numLiteralPosStateBits;
    Optimal[] _optimum = new Optimal[4096];
    int _optimumCurrentIndex;
    int _optimumEndIndex;
    BitTreeEncoder _posAlignEncoder = new BitTreeEncoder(4);
    short[] _posEncoders = new short[114];
    BitTreeEncoder[] _posSlotEncoder = new BitTreeEncoder[4];
    int[] _posSlotPrices = new int[256];
    int _posStateBits = 2;
    int _posStateMask = 3;
    byte _previousByte;
    com.badlogic.gdx.utils.compression.rangecoder.Encoder _rangeEncoder = new com.badlogic.gdx.utils.compression.rangecoder.Encoder();
    int[] _repDistances = new int[4];
    LenPriceTableEncoder _repMatchLenEncoder = new LenPriceTableEncoder();
    int _state = Base.StateInit();
    boolean _writeEndMark;
    int backRes;
    boolean[] finished;
    long nowPos64;
    long[] processedInSize;
    long[] processedOutSize;
    byte[] properties;
    int[] repLens;
    int[] reps;
    int[] tempPrices;

    class LenEncoder {
        short[] _choice = new short[2];
        BitTreeEncoder _highCoder = new BitTreeEncoder(8);
        BitTreeEncoder[] _lowCoder = new BitTreeEncoder[16];
        BitTreeEncoder[] _midCoder = new BitTreeEncoder[16];

        public LenEncoder() {
            for (int posState = 0; posState < 16; posState++) {
                this._lowCoder[posState] = new BitTreeEncoder(3);
                this._midCoder[posState] = new BitTreeEncoder(3);
            }
        }

        public void Init(int numPosStates) {
            com.badlogic.gdx.utils.compression.rangecoder.Encoder.InitBitModels(this._choice);
            for (int posState = 0; posState < numPosStates; posState++) {
                this._lowCoder[posState].Init();
                this._midCoder[posState].Init();
            }
            this._highCoder.Init();
        }

        public void Encode(com.badlogic.gdx.utils.compression.rangecoder.Encoder rangeEncoder, int symbol, int posState) throws IOException {
            if (symbol < 8) {
                rangeEncoder.Encode(this._choice, 0, 0);
                this._lowCoder[posState].Encode(rangeEncoder, symbol);
                return;
            }
            symbol -= 8;
            rangeEncoder.Encode(this._choice, 0, 1);
            if (symbol < 8) {
                rangeEncoder.Encode(this._choice, 1, 0);
                this._midCoder[posState].Encode(rangeEncoder, symbol);
                return;
            }
            rangeEncoder.Encode(this._choice, 1, 1);
            this._highCoder.Encode(rangeEncoder, symbol - 8);
        }

        public void SetPrices(int posState, int numSymbols, int[] prices, int st) {
            int a0 = com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice0(this._choice[0]);
            int a1 = com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice1(this._choice[0]);
            int b0 = com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice0(this._choice[1]) + a1;
            int b1 = com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice1(this._choice[1]) + a1;
            int i = 0;
            while (i < 8) {
                if (i < numSymbols) {
                    prices[st + i] = this._lowCoder[posState].GetPrice(i) + a0;
                    i++;
                } else {
                    return;
                }
            }
            while (i < 16) {
                if (i < numSymbols) {
                    prices[st + i] = this._midCoder[posState].GetPrice(i - 8) + b0;
                    i++;
                } else {
                    return;
                }
            }
            while (i < numSymbols) {
                prices[st + i] = this._highCoder.GetPrice((i - 8) - 8) + b1;
                i++;
            }
        }
    }

    class LiteralEncoder {
        Encoder2[] m_Coders;
        int m_NumPosBits;
        int m_NumPrevBits;
        int m_PosMask;

        class Encoder2 {
            short[] m_Encoders = new short[GL20.GL_SRC_COLOR];

            Encoder2() {
            }

            public void Init() {
                com.badlogic.gdx.utils.compression.rangecoder.Encoder.InitBitModels(this.m_Encoders);
            }

            public void Encode(com.badlogic.gdx.utils.compression.rangecoder.Encoder rangeEncoder, byte symbol) throws IOException {
                int context = 1;
                for (int i = 7; i >= 0; i--) {
                    int bit = (symbol >> i) & 1;
                    rangeEncoder.Encode(this.m_Encoders, context, bit);
                    context = (context << 1) | bit;
                }
            }

            public void EncodeMatched(com.badlogic.gdx.utils.compression.rangecoder.Encoder rangeEncoder, byte matchByte, byte symbol) throws IOException {
                int context = 1;
                boolean same = true;
                for (int i = 7; i >= 0; i--) {
                    boolean z = true;
                    int bit = (symbol >> i) & 1;
                    int state = context;
                    if (same) {
                        int matchBit = (matchByte >> i) & 1;
                        state += (matchBit + 1) << 8;
                        if (matchBit != bit) {
                            z = false;
                        }
                        same = z;
                    }
                    rangeEncoder.Encode(this.m_Encoders, state, bit);
                    context = (context << 1) | bit;
                }
            }

            public int GetPrice(boolean matchMode, byte matchByte, byte symbol) {
                int matchBit;
                int price = 0;
                int context = 1;
                int i = 7;
                if (matchMode) {
                    while (i >= 0) {
                        matchBit = (matchByte >> i) & 1;
                        int bit = (symbol >> i) & 1;
                        price += com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice(this.m_Encoders[((matchBit + 1) << 8) + context], bit);
                        context = (context << 1) | bit;
                        if (matchBit != bit) {
                            i--;
                            break;
                        }
                        i--;
                    }
                }
                while (i >= 0) {
                    matchBit = (symbol >> i) & 1;
                    price += com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice(this.m_Encoders[context], matchBit);
                    context = (context << 1) | matchBit;
                    i--;
                }
                return price;
            }
        }

        LiteralEncoder() {
        }

        public void Create(int numPosBits, int numPrevBits) {
            if (this.m_Coders == null || this.m_NumPrevBits != numPrevBits || this.m_NumPosBits != numPosBits) {
                this.m_NumPosBits = numPosBits;
                this.m_PosMask = (1 << numPosBits) - 1;
                this.m_NumPrevBits = numPrevBits;
                int numStates = 1 << (this.m_NumPrevBits + this.m_NumPosBits);
                this.m_Coders = new Encoder2[numStates];
                for (int i = 0; i < numStates; i++) {
                    this.m_Coders[i] = new Encoder2();
                }
            }
        }

        public void Init() {
            int numStates = 1 << (this.m_NumPrevBits + this.m_NumPosBits);
            for (int i = 0; i < numStates; i++) {
                this.m_Coders[i].Init();
            }
        }

        public Encoder2 GetSubCoder(int pos, byte prevByte) {
            return this.m_Coders[((this.m_PosMask & pos) << this.m_NumPrevBits) + ((prevByte & 255) >>> (8 - this.m_NumPrevBits))];
        }
    }

    class Optimal {
        public int BackPrev;
        public int BackPrev2;
        public int Backs0;
        public int Backs1;
        public int Backs2;
        public int Backs3;
        public int PosPrev;
        public int PosPrev2;
        public boolean Prev1IsChar;
        public boolean Prev2;
        public int Price;
        public int State;

        Optimal() {
        }

        public void MakeAsChar() {
            this.BackPrev = -1;
            this.Prev1IsChar = false;
        }

        public void MakeAsShortRep() {
            this.BackPrev = 0;
            this.Prev1IsChar = false;
        }

        public boolean IsShortRep() {
            return this.BackPrev == 0;
        }
    }

    class LenPriceTableEncoder extends LenEncoder {
        int[] _counters = new int[16];
        int[] _prices = new int[GL20.GL_DONT_CARE];
        int _tableSize;

        LenPriceTableEncoder() {
            super();
        }

        public void SetTableSize(int tableSize) {
            this._tableSize = tableSize;
        }

        public int GetPrice(int symbol, int posState) {
            return this._prices[(posState * 272) + symbol];
        }

        void UpdateTable(int posState) {
            SetPrices(posState, this._tableSize, this._prices, posState * 272);
            this._counters[posState] = this._tableSize;
        }

        public void UpdateTables(int numPosStates) {
            for (int posState = 0; posState < numPosStates; posState++) {
                UpdateTable(posState);
            }
        }

        public void Encode(com.badlogic.gdx.utils.compression.rangecoder.Encoder rangeEncoder, int symbol, int posState) throws IOException {
            super.Encode(rangeEncoder, symbol, posState);
            int[] iArr = this._counters;
            int i = iArr[posState] - 1;
            iArr[posState] = i;
            if (i == 0) {
                UpdateTable(posState);
            }
        }
    }

    static {
        int c = 2;
        g_FastPos[0] = (byte) 0;
        g_FastPos[1] = (byte) 1;
        int slotFast = 2;
        while (slotFast < 22) {
            int k = 1 << ((slotFast >> 1) - 1);
            int c2 = c;
            c = 0;
            while (c < k) {
                g_FastPos[c2] = (byte) slotFast;
                c++;
                c2++;
            }
            slotFast++;
            c = c2;
        }
    }

    static int GetPosSlot(int pos) {
        if (pos < 2048) {
            return g_FastPos[pos];
        }
        if (pos < 2097152) {
            return g_FastPos[pos >> 10] + 20;
        }
        return g_FastPos[pos >> 20] + 40;
    }

    static int GetPosSlot2(int pos) {
        if (pos < 131072) {
            return g_FastPos[pos >> 6] + 12;
        }
        if (pos < 134217728) {
            return g_FastPos[pos >> 16] + 32;
        }
        return g_FastPos[pos >> 26] + 52;
    }

    void BaseInit() {
        this._state = Base.StateInit();
        this._previousByte = (byte) 0;
        for (int i = 0; i < 4; i++) {
            this._repDistances[i] = 0;
        }
    }

    void Create() {
        if (this._matchFinder == null) {
            BinTree bt = new BinTree();
            int numHashBytes = 4;
            if (this._matchFinderType == 0) {
                numHashBytes = 2;
            }
            bt.SetType(numHashBytes);
            this._matchFinder = bt;
        }
        this._literalEncoder.Create(this._numLiteralPosStateBits, this._numLiteralContextBits);
        if (this._dictionarySize != this._dictionarySizePrev || this._numFastBytesPrev != this._numFastBytes) {
            this._matchFinder.Create(this._dictionarySize, 4096, this._numFastBytes, 274);
            this._dictionarySizePrev = this._dictionarySize;
            this._numFastBytesPrev = this._numFastBytes;
        }
    }

    public Encoder() {
        int i = 0;
        this._numLiteralPosStateBits = 0;
        this._numLiteralContextBits = 3;
        this._dictionarySize = 4194304;
        this._dictionarySizePrev = -1;
        this._numFastBytesPrev = -1;
        this._matchFinderType = 1;
        this._writeEndMark = false;
        this._needReleaseMFStream = false;
        this.reps = new int[4];
        this.repLens = new int[4];
        this.processedInSize = new long[1];
        this.processedOutSize = new long[1];
        this.finished = new boolean[1];
        this.properties = new byte[5];
        this.tempPrices = new int[128];
        for (int i2 = 0; i2 < 4096; i2++) {
            this._optimum[i2] = new Optimal();
        }
        while (true) {
            int i3 = i;
            if (i3 < 4) {
                this._posSlotEncoder[i3] = new BitTreeEncoder(6);
                i = i3 + 1;
            } else {
                return;
            }
        }
    }

    void SetWriteEndMarkerMode(boolean writeEndMarker) {
        this._writeEndMark = writeEndMarker;
    }

    void Init() {
        BaseInit();
        this._rangeEncoder.Init();
        com.badlogic.gdx.utils.compression.rangecoder.Encoder.InitBitModels(this._isMatch);
        com.badlogic.gdx.utils.compression.rangecoder.Encoder.InitBitModels(this._isRep0Long);
        com.badlogic.gdx.utils.compression.rangecoder.Encoder.InitBitModels(this._isRep);
        com.badlogic.gdx.utils.compression.rangecoder.Encoder.InitBitModels(this._isRepG0);
        com.badlogic.gdx.utils.compression.rangecoder.Encoder.InitBitModels(this._isRepG1);
        com.badlogic.gdx.utils.compression.rangecoder.Encoder.InitBitModels(this._isRepG2);
        com.badlogic.gdx.utils.compression.rangecoder.Encoder.InitBitModels(this._posEncoders);
        this._literalEncoder.Init();
        for (int i = 0; i < 4; i++) {
            this._posSlotEncoder[i].Init();
        }
        this._lenEncoder.Init(1 << this._posStateBits);
        this._repMatchLenEncoder.Init(1 << this._posStateBits);
        this._posAlignEncoder.Init();
        this._longestMatchWasFound = false;
        this._optimumEndIndex = 0;
        this._optimumCurrentIndex = 0;
        this._additionalOffset = 0;
    }

    int ReadMatchDistances() throws IOException {
        int lenRes = 0;
        this._numDistancePairs = this._matchFinder.GetMatches(this._matchDistances);
        if (this._numDistancePairs > 0) {
            lenRes = this._matchDistances[this._numDistancePairs - 2];
            if (lenRes == this._numFastBytes) {
                lenRes += this._matchFinder.GetMatchLen(lenRes - 1, this._matchDistances[this._numDistancePairs - 1], 273 - lenRes);
            }
        }
        this._additionalOffset++;
        return lenRes;
    }

    void MovePos(int num) throws IOException {
        if (num > 0) {
            this._matchFinder.Skip(num);
            this._additionalOffset += num;
        }
    }

    int GetRepLen1Price(int state, int posState) {
        return com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice0(this._isRepG0[state]) + com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice0(this._isRep0Long[(state << 4) + posState]);
    }

    int GetPureRepPrice(int repIndex, int state, int posState) {
        if (repIndex == 0) {
            return com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice0(this._isRepG0[state]) + com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice1(this._isRep0Long[(state << 4) + posState]);
        }
        int price = com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice1(this._isRepG0[state]);
        if (repIndex == 1) {
            return price + com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice0(this._isRepG1[state]);
        }
        return (price + com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice1(this._isRepG1[state])) + com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice(this._isRepG2[state], repIndex - 2);
    }

    int GetRepPrice(int repIndex, int len, int state, int posState) {
        return GetPureRepPrice(repIndex, state, posState) + this._repMatchLenEncoder.GetPrice(len - 2, posState);
    }

    int GetPosLenPrice(int pos, int len, int posState) {
        int price;
        int lenToPosState = Base.GetLenToPosState(len);
        if (pos < 128) {
            price = this._distancesPrices[(lenToPosState * 128) + pos];
        } else {
            price = this._posSlotPrices[(lenToPosState << 6) + GetPosSlot2(pos)] + this._alignPrices[pos & 15];
        }
        return this._lenEncoder.GetPrice(len - 2, posState) + price;
    }

    int Backward(int cur) {
        this._optimumEndIndex = cur;
        int posMem = this._optimum[cur].PosPrev;
        int backMem = this._optimum[cur].BackPrev;
        do {
            if (this._optimum[cur].Prev1IsChar) {
                this._optimum[posMem].MakeAsChar();
                this._optimum[posMem].PosPrev = posMem - 1;
                if (this._optimum[cur].Prev2) {
                    this._optimum[posMem - 1].Prev1IsChar = false;
                    this._optimum[posMem - 1].PosPrev = this._optimum[cur].PosPrev2;
                    this._optimum[posMem - 1].BackPrev = this._optimum[cur].BackPrev2;
                }
            }
            int posPrev = posMem;
            int backCur = backMem;
            backMem = this._optimum[posPrev].BackPrev;
            posMem = this._optimum[posPrev].PosPrev;
            this._optimum[posPrev].BackPrev = backCur;
            this._optimum[posPrev].PosPrev = cur;
            cur = posPrev;
        } while (cur > 0);
        this.backRes = this._optimum[0].BackPrev;
        this._optimumCurrentIndex = this._optimum[0].PosPrev;
        return this._optimumCurrentIndex;
    }

    int GetOptimum(int position) throws IOException {
        int i = position;
        Encoder encoder;
        if (this._optimumEndIndex != this._optimumCurrentIndex) {
            int lenRes = encoder._optimum[encoder._optimumCurrentIndex].PosPrev - encoder._optimumCurrentIndex;
            encoder.backRes = encoder._optimum[encoder._optimumCurrentIndex].BackPrev;
            encoder._optimumCurrentIndex = encoder._optimum[encoder._optimumCurrentIndex].PosPrev;
            return lenRes;
        }
        int lenMain;
        encoder._optimumEndIndex = 0;
        encoder._optimumCurrentIndex = 0;
        if (encoder._longestMatchWasFound) {
            lenMain = encoder._longestMatchLength;
            encoder._longestMatchWasFound = false;
        } else {
            lenMain = ReadMatchDistances();
        }
        int numDistancePairs = encoder._numDistancePairs;
        int numAvailableBytes = encoder._matchFinder.GetNumAvailableBytes() + 1;
        if (numAvailableBytes < 2) {
            encoder.backRes = -1;
            return 1;
        }
        int i2;
        if (numAvailableBytes > 273) {
            numAvailableBytes = 273;
        }
        int repMaxIndex = 0;
        for (i2 = 0; i2 < 4; i2++) {
            encoder.reps[i2] = encoder._repDistances[i2];
            encoder.repLens[i2] = encoder._matchFinder.GetMatchLen(-1, encoder.reps[i2], 273);
            if (encoder.repLens[i2] > encoder.repLens[repMaxIndex]) {
                repMaxIndex = i2;
            }
        }
        if (encoder.repLens[repMaxIndex] >= encoder._numFastBytes) {
            encoder.backRes = repMaxIndex;
            lenRes = encoder.repLens[repMaxIndex];
            MovePos(lenRes - 1);
            return lenRes;
        } else if (lenMain >= encoder._numFastBytes) {
            encoder.backRes = encoder._matchDistances[numDistancePairs - 1] + 4;
            MovePos(lenMain - 1);
            return lenMain;
        } else {
            byte currentByte = encoder._matchFinder.GetIndexByte(-1);
            byte matchByte = encoder._matchFinder.GetIndexByte(((0 - encoder._repDistances[0]) - 1) - 1);
            if (lenMain >= 2 || currentByte == matchByte || encoder.repLens[repMaxIndex] >= 2) {
                int shortRepPrice;
                encoder._optimum[0].State = encoder._state;
                int posState = encoder._posStateMask & i;
                encoder._optimum[1].Price = com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice0(encoder._isMatch[(encoder._state << 4) + posState]) + encoder._literalEncoder.GetSubCoder(i, encoder._previousByte).GetPrice(Base.StateIsCharState(encoder._state) ^ true, matchByte, currentByte);
                encoder._optimum[1].MakeAsChar();
                lenRes = com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice1(encoder._isMatch[(encoder._state << 4) + posState]);
                int repMatchPrice = com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice1(encoder._isRep[encoder._state]) + lenRes;
                if (matchByte == currentByte) {
                    shortRepPrice = GetRepLen1Price(encoder._state, posState) + repMatchPrice;
                    if (shortRepPrice < encoder._optimum[1].Price) {
                        encoder._optimum[1].Price = shortRepPrice;
                        encoder._optimum[1].MakeAsShortRep();
                    }
                }
                shortRepPrice = lenMain >= encoder.repLens[repMaxIndex] ? lenMain : encoder.repLens[repMaxIndex];
                if (shortRepPrice < 2) {
                    encoder.backRes = encoder._optimum[1].BackPrev;
                    return 1;
                }
                int len;
                int matchPrice;
                int repLen;
                int numAvailableBytes2;
                int normalMatchPrice;
                encoder._optimum[1].PosPrev = 0;
                encoder._optimum[0].Backs0 = encoder.reps[0];
                encoder._optimum[0].Backs1 = encoder.reps[1];
                encoder._optimum[0].Backs2 = encoder.reps[2];
                encoder._optimum[0].Backs3 = encoder.reps[3];
                int len2 = shortRepPrice;
                while (true) {
                    len = len2 - 1;
                    encoder._optimum[len2].Price = kIfinityPrice;
                    len2 = 2;
                    if (len < 2) {
                        break;
                    }
                    matchPrice = lenRes;
                    len2 = len;
                    i = position;
                }
                i2 = 0;
                while (i2 < 4) {
                    repLen = encoder.repLens[i2];
                    if (repLen < len2) {
                        numAvailableBytes2 = numAvailableBytes;
                    } else {
                        len2 = GetPureRepPrice(i2, encoder._state, posState) + repMatchPrice;
                        while (true) {
                            numAvailableBytes2 = numAvailableBytes;
                            i = encoder._repMatchLenEncoder.GetPrice(repLen - 2, posState) + len2;
                            numAvailableBytes = encoder._optimum[repLen];
                            int price = len2;
                            if (i < numAvailableBytes.Price) {
                                numAvailableBytes.Price = i;
                                numAvailableBytes.PosPrev = 0;
                                numAvailableBytes.BackPrev = i2;
                                numAvailableBytes.Prev1IsChar = false;
                            }
                            repLen--;
                            if (repLen < 2) {
                                break;
                            }
                            numAvailableBytes = numAvailableBytes2;
                            len2 = price;
                            i = position;
                        }
                    }
                    i2++;
                    numAvailableBytes = numAvailableBytes2;
                    i = position;
                    len2 = 2;
                }
                i = com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice0(encoder._isRep[encoder._state]) + lenRes;
                numAvailableBytes = encoder.repLens[0] >= 2 ? encoder.repLens[0] + 1 : 2;
                if (numAvailableBytes <= lenMain) {
                    len2 = 0;
                    while (numAvailableBytes > encoder._matchDistances[len2]) {
                        len2 += 2;
                    }
                    while (true) {
                        repLen = encoder._matchDistances[len2 + 1];
                        len = GetPosLenPrice(repLen, numAvailableBytes, posState) + i;
                        normalMatchPrice = i;
                        i = encoder._optimum[numAvailableBytes];
                        matchPrice = lenRes;
                        if (len < i.Price) {
                            i.Price = len;
                            i.PosPrev = 0;
                            i.BackPrev = repLen + 4;
                            i.Prev1IsChar = false;
                        }
                        if (numAvailableBytes == encoder._matchDistances[len2]) {
                            len2 += 2;
                            if (len2 == numDistancePairs) {
                                break;
                            }
                        }
                        numAvailableBytes++;
                        i = normalMatchPrice;
                        lenRes = matchPrice;
                    }
                } else {
                    matchPrice = lenRes;
                }
                i = 0;
                int position2 = position;
                while (true) {
                    i++;
                    if (i == shortRepPrice) {
                        return encoder.Backward(i);
                    }
                    lenRes = ReadMatchDistances();
                    numDistancePairs = encoder._numDistancePairs;
                    if (lenRes >= encoder._numFastBytes) {
                        encoder._longestMatchLength = lenRes;
                        encoder._longestMatchWasFound = true;
                        return encoder.Backward(i);
                    }
                    int i3;
                    int i4;
                    int len3;
                    boolean nextIsChar;
                    len2 = position2 + 1;
                    repLen = encoder._optimum[i].PosPrev;
                    if (encoder._optimum[i].Prev1IsChar) {
                        repLen--;
                        if (encoder._optimum[i].Prev2) {
                            i3 = lenMain;
                            lenMain = encoder._optimum[encoder._optimum[i].PosPrev2].State;
                            i4 = numDistancePairs;
                            if (encoder._optimum[i].BackPrev2 < 4) {
                                lenMain = Base.StateUpdateRep(lenMain);
                            } else {
                                lenMain = Base.StateUpdateMatch(lenMain);
                            }
                        } else {
                            i3 = lenMain;
                            i4 = numDistancePairs;
                            lenMain = encoder._optimum[repLen].State;
                        }
                        lenMain = Base.StateUpdateChar(lenMain);
                    } else {
                        i3 = lenMain;
                        i4 = numDistancePairs;
                        lenMain = encoder._optimum[repLen].State;
                    }
                    if (repLen == i - 1) {
                        if (encoder._optimum[i].IsShortRep()) {
                            lenMain = Base.StateUpdateShortRep(lenMain);
                        } else {
                            lenMain = Base.StateUpdateChar(lenMain);
                        }
                        len3 = numAvailableBytes;
                    } else {
                        if (encoder._optimum[i].Prev1IsChar && encoder._optimum[i].Prev2) {
                            repLen = encoder._optimum[i].PosPrev2;
                            numDistancePairs = encoder._optimum[i].BackPrev2;
                            lenMain = Base.StateUpdateRep(lenMain);
                        } else {
                            numDistancePairs = encoder._optimum[i].BackPrev;
                            if (numDistancePairs < 4) {
                                lenMain = Base.StateUpdateRep(lenMain);
                            } else {
                                lenMain = Base.StateUpdateMatch(lenMain);
                            }
                        }
                        Optimal opt = encoder._optimum[repLen];
                        int state = lenMain;
                        int i5;
                        if (numDistancePairs >= 4) {
                            len3 = numAvailableBytes;
                            i5 = repMatchPrice;
                            encoder.reps[0] = numDistancePairs - 4;
                            encoder.reps[1] = opt.Backs0;
                            encoder.reps[2] = opt.Backs1;
                            encoder.reps[3] = opt.Backs2;
                        } else if (numDistancePairs == 0) {
                            len3 = numAvailableBytes;
                            encoder.reps[0] = opt.Backs0;
                            i5 = repMatchPrice;
                            encoder.reps[1] = opt.Backs1;
                            encoder.reps[2] = opt.Backs2;
                            encoder.reps[3] = opt.Backs3;
                        } else {
                            len3 = numAvailableBytes;
                            i5 = repMatchPrice;
                            if (numDistancePairs == 1) {
                                encoder.reps[0] = opt.Backs1;
                                encoder.reps[1] = opt.Backs0;
                                encoder.reps[2] = opt.Backs2;
                                encoder.reps[3] = opt.Backs3;
                            } else if (numDistancePairs == 2) {
                                encoder.reps[0] = opt.Backs2;
                                encoder.reps[1] = opt.Backs0;
                                encoder.reps[2] = opt.Backs1;
                                encoder.reps[3] = opt.Backs3;
                            } else {
                                encoder.reps[0] = opt.Backs3;
                                encoder.reps[1] = opt.Backs0;
                                encoder.reps[2] = opt.Backs1;
                                encoder.reps[3] = opt.Backs2;
                            }
                        }
                        lenMain = state;
                    }
                    encoder._optimum[i].State = lenMain;
                    encoder._optimum[i].Backs0 = encoder.reps[0];
                    encoder._optimum[i].Backs1 = encoder.reps[1];
                    encoder._optimum[i].Backs2 = encoder.reps[2];
                    encoder._optimum[i].Backs3 = encoder.reps[3];
                    numDistancePairs = encoder._optimum[i].Price;
                    currentByte = encoder._matchFinder.GetIndexByte(-1);
                    matchByte = encoder._matchFinder.GetIndexByte(((0 - encoder.reps[0]) - 1) - 1);
                    posState = len2 & encoder._posStateMask;
                    int lenEnd = shortRepPrice;
                    numAvailableBytes = (com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice0(encoder._isMatch[(lenMain << 4) + posState]) + numDistancePairs) + encoder._literalEncoder.GetSubCoder(len2, encoder._matchFinder.GetIndexByte(-2)).GetPrice(Base.StateIsCharState(lenMain) ^ true, matchByte, currentByte);
                    Optimal nextOptimum = encoder._optimum[i + 1];
                    boolean nextIsChar2 = false;
                    if (numAvailableBytes < nextOptimum.Price) {
                        nextOptimum.Price = numAvailableBytes;
                        nextOptimum.PosPrev = i;
                        nextOptimum.MakeAsChar();
                        nextIsChar2 = true;
                    }
                    matchPrice = numDistancePairs + com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice1(encoder._isMatch[(lenMain << 4) + posState]);
                    len = matchPrice + com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice1(encoder._isRep[lenMain]);
                    if (matchByte == currentByte) {
                        if (nextOptimum.PosPrev >= i || nextOptimum.BackPrev != 0) {
                            boolean curPrice = encoder.GetRepLen1Price(lenMain, posState) + len;
                            nextIsChar = nextIsChar2;
                            if (curPrice <= nextOptimum.Price) {
                                nextOptimum.Price = curPrice;
                                nextOptimum.PosPrev = i;
                                nextOptimum.MakeAsShortRep();
                                nextIsChar2 = true;
                            }
                        }
                        nextIsChar = nextIsChar2;
                    } else {
                        nextIsChar = nextIsChar2;
                    }
                    numDistancePairs = Math.min(4095 - i, encoder._matchFinder.GetNumAvailableBytes() + 1);
                    shortRepPrice = numDistancePairs;
                    if (shortRepPrice < 2) {
                        position2 = len2;
                        numAvailableBytes2 = shortRepPrice;
                        repMatchPrice = len;
                        lenMain = i3;
                        numDistancePairs = i4;
                        numAvailableBytes = len3;
                        shortRepPrice = lenEnd;
                    } else {
                        int i6;
                        int i7;
                        int lenTestTemp;
                        int numAvailableBytesFull;
                        int repMatchPrice2;
                        Optimal optimum;
                        int lenEnd2;
                        int i8;
                        int state2;
                        int startLen;
                        int posStateNext;
                        int curAndLenCharPrice;
                        int nextMatchPrice;
                        int lenEnd3;
                        int state22;
                        int posStateNext2;
                        int curAndLenPrice;
                        Optimal optimum2;
                        int i9;
                        int i10;
                        int i11;
                        int posStateNext3;
                        int lenEnd4;
                        int i12;
                        int i13;
                        int curAndLenCharPrice2;
                        int state23;
                        int i14;
                        Object obj;
                        Optimal optimal;
                        if (shortRepPrice > encoder._numFastBytes) {
                            repMatchPrice = encoder._numFastBytes;
                        } else {
                            repMatchPrice = shortRepPrice;
                        }
                        byte b;
                        int i15;
                        byte matchByte2;
                        if (nextIsChar || matchByte == currentByte) {
                            b = currentByte;
                            i6 = i2;
                            i7 = repMaxIndex;
                            i15 = repLen;
                            matchByte2 = matchByte;
                        } else {
                            b = currentByte;
                            shortRepPrice = Math.min(numDistancePairs - 1, encoder._numFastBytes);
                            i6 = i2;
                            i7 = repMaxIndex;
                            currentByte = encoder._matchFinder.GetMatchLen(0, encoder.reps[0], shortRepPrice);
                            if (currentByte >= (byte) 2) {
                                i2 = Base.StateUpdateChar(lenMain);
                                shortRepPrice = encoder._posStateMask & (len2 + 1);
                                repMaxIndex = (com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice1(encoder._isMatch[(i2 << 4) + shortRepPrice]) + numAvailableBytes) + com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice1(encoder._isRep[i2]);
                                numAvailableBytes = (i + 1) + currentByte;
                                repLen = lenEnd;
                                while (repLen < numAvailableBytes) {
                                    matchByte2 = matchByte;
                                    repLen++;
                                    int lenEnd5 = repLen;
                                    encoder._optimum[repLen].Price = kIfinityPrice;
                                    matchByte = matchByte2;
                                    repLen = lenEnd5;
                                }
                                matchByte2 = matchByte;
                                matchByte = repMaxIndex + encoder.GetRepPrice(0, currentByte, i2, shortRepPrice);
                                Optimal optimum3 = encoder._optimum[numAvailableBytes];
                                int offset = numAvailableBytes;
                                if (matchByte < optimum3.Price) {
                                    optimum3.Price = matchByte;
                                    optimum3.PosPrev = i + 1;
                                    optimum3.BackPrev = 0;
                                    optimum3.Prev1IsChar = true;
                                    optimum3.Prev2 = false;
                                }
                                shortRepPrice = 2;
                                numAvailableBytes = 0;
                                while (numAvailableBytes < 4) {
                                    lenTestTemp = encoder._matchFinder.GetMatchLen(-1, encoder.reps[numAvailableBytes], repMatchPrice);
                                    if (lenTestTemp >= 2) {
                                        numAvailableBytesFull = numDistancePairs;
                                        repMatchPrice2 = len;
                                    } else {
                                        i2 = lenTestTemp;
                                        while (true) {
                                            if (repLen >= i + i2) {
                                                repLen++;
                                                encoder._optimum[repLen].Price = kIfinityPrice;
                                            } else {
                                                repMaxIndex = encoder.GetRepPrice(numAvailableBytes, i2, lenMain, posState) + len;
                                                optimum = encoder._optimum[i + i2];
                                                lenEnd2 = repLen;
                                                if (repMaxIndex < optimum.Price) {
                                                    optimum.Price = repMaxIndex;
                                                    optimum.PosPrev = i;
                                                    optimum.BackPrev = numAvailableBytes;
                                                    optimum.Prev1IsChar = false;
                                                }
                                                i2--;
                                                if (i2 < 2) {
                                                    break;
                                                }
                                                i8 = lenTestTemp;
                                                repLen = lenEnd2;
                                            }
                                        }
                                        i2 = lenTestTemp;
                                        if (numAvailableBytes == 0) {
                                            shortRepPrice = i2 + 1;
                                        }
                                        if (i2 < numDistancePairs) {
                                            repMaxIndex = Math.min((numDistancePairs - 1) - i2, encoder._numFastBytes);
                                            repLen = encoder._matchFinder.GetMatchLen(i2, encoder.reps[numAvailableBytes], repMaxIndex);
                                            if (repLen >= 2) {
                                                state2 = Base.StateUpdateRep(lenMain);
                                                startLen = shortRepPrice;
                                                shortRepPrice = (len2 + i2) & encoder._posStateMask;
                                                posStateNext = shortRepPrice;
                                                numAvailableBytesFull = numDistancePairs;
                                                repMatchPrice2 = len;
                                                curAndLenCharPrice = ((len + encoder.GetRepPrice(numAvailableBytes, i2, lenMain, posState)) + com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice0(encoder._isMatch[(state2 << 4) + shortRepPrice])) + encoder._literalEncoder.GetSubCoder(len2 + i2, encoder._matchFinder.GetIndexByte((i2 - 1) - 1)).GetPrice(true, encoder._matchFinder.GetIndexByte((i2 - 1) - (encoder.reps[numAvailableBytes] + 1)), encoder._matchFinder.GetIndexByte(i2 - 1));
                                                numDistancePairs = Base.StateUpdateChar(state2);
                                                shortRepPrice = ((len2 + i2) + 1) & encoder._posStateMask;
                                                lenTestTemp = curAndLenCharPrice + com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice1(encoder._isMatch[(numDistancePairs << 4) + shortRepPrice]);
                                                repMaxIndex = com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice1(encoder._isRep[numDistancePairs]) + lenTestTemp;
                                                state2 = (i2 + 1) + repLen;
                                                len = lenEnd2;
                                                while (true) {
                                                    nextMatchPrice = lenTestTemp;
                                                    if (len < i + state2) {
                                                        break;
                                                    }
                                                    len++;
                                                    lenEnd3 = len;
                                                    encoder._optimum[len].Price = kIfinityPrice;
                                                    lenTestTemp = nextMatchPrice;
                                                    len = lenEnd3;
                                                }
                                                lenTestTemp = repMaxIndex + encoder.GetRepPrice(0, repLen, numDistancePairs, shortRepPrice);
                                                state22 = numDistancePairs;
                                                numDistancePairs = encoder._optimum[i + state2];
                                                posStateNext2 = shortRepPrice;
                                                if (lenTestTemp < numDistancePairs.Price) {
                                                    numDistancePairs.Price = lenTestTemp;
                                                    curAndLenPrice = lenTestTemp;
                                                    numDistancePairs.PosPrev = (i + i2) + 1;
                                                    numDistancePairs.BackPrev = 0;
                                                    numDistancePairs.Prev1IsChar = true;
                                                    numDistancePairs.Prev2 = true;
                                                    numDistancePairs.PosPrev2 = i;
                                                    numDistancePairs.BackPrev2 = numAvailableBytes;
                                                }
                                                repLen = len;
                                                shortRepPrice = startLen;
                                            }
                                        }
                                        numAvailableBytesFull = numDistancePairs;
                                        startLen = shortRepPrice;
                                        repMatchPrice2 = len;
                                        repLen = lenEnd2;
                                        shortRepPrice = startLen;
                                    }
                                    numAvailableBytes++;
                                    len = repMatchPrice2;
                                    numDistancePairs = numAvailableBytesFull;
                                }
                                numAvailableBytesFull = numDistancePairs;
                                repMatchPrice2 = len;
                                if (lenRes <= repMatchPrice) {
                                    lenRes = repMatchPrice;
                                    numDistancePairs = 0;
                                    while (lenRes > encoder._matchDistances[numDistancePairs]) {
                                        numDistancePairs += 2;
                                    }
                                    encoder._matchDistances[numDistancePairs] = lenRes;
                                    numDistancePairs += 2;
                                } else {
                                    numDistancePairs = i4;
                                }
                                if (lenRes < shortRepPrice) {
                                    numAvailableBytes = matchPrice + com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice0(encoder._isRep[lenMain]);
                                    while (repLen < i + lenRes) {
                                        repLen++;
                                        encoder._optimum[repLen].Price = kIfinityPrice;
                                    }
                                    lenRes = 0;
                                    while (shortRepPrice > encoder._matchDistances[lenRes]) {
                                        lenRes += 2;
                                    }
                                    while (true) {
                                        lenTestTemp = encoder._matchDistances[lenRes + 1];
                                        i2 = encoder.GetPosLenPrice(lenTestTemp, shortRepPrice, posState) + numAvailableBytes;
                                        optimum2 = encoder._optimum[i + shortRepPrice];
                                        if (i2 < optimum2.Price) {
                                            optimum2.Price = i2;
                                            optimum2.PosPrev = i;
                                            optimum2.BackPrev = lenTestTemp + 4;
                                            optimum2.Prev1IsChar = false;
                                        }
                                        if (shortRepPrice == encoder._matchDistances[lenRes]) {
                                            state2 = numAvailableBytesFull;
                                            if (shortRepPrice >= state2) {
                                                i9 = numAvailableBytes;
                                                numAvailableBytes = Math.min((state2 - 1) - shortRepPrice, encoder._numFastBytes);
                                                len = encoder._matchFinder.GetMatchLen(shortRepPrice, lenTestTemp, numAvailableBytes);
                                                if (len < 2) {
                                                    numAvailableBytes = Base.StateUpdateMatch(lenMain);
                                                    i10 = lenMain;
                                                    lenMain = (len2 + shortRepPrice) & encoder._posStateMask;
                                                    i11 = repMatchPrice;
                                                    posStateNext3 = lenMain;
                                                    lenEnd4 = repLen;
                                                    i12 = state2;
                                                    repMatchPrice = (com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice0(encoder._isMatch[(numAvailableBytes << 4) + lenMain]) + i2) + encoder._literalEncoder.GetSubCoder(len2 + shortRepPrice, encoder._matchFinder.GetIndexByte((shortRepPrice - 1) - 1)).GetPrice(true, encoder._matchFinder.GetIndexByte((shortRepPrice - (lenTestTemp + 1)) - 1), encoder._matchFinder.GetIndexByte(shortRepPrice - 1));
                                                    lenMain = Base.StateUpdateChar(numAvailableBytes);
                                                    numAvailableBytes = ((len2 + shortRepPrice) + 1) & encoder._posStateMask;
                                                    optimum2 = com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice1(encoder._isRep[lenMain]) + (com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice1(encoder._isMatch[(lenMain << 4) + numAvailableBytes]) + repMatchPrice);
                                                    repLen = (shortRepPrice + 1) + len;
                                                    state2 = lenEnd4;
                                                    while (true) {
                                                        i13 = len2;
                                                        if (state2 < i + repLen) {
                                                            break;
                                                        }
                                                        state2++;
                                                        curAndLenCharPrice2 = repMatchPrice;
                                                        encoder._optimum[state2].Price = kIfinityPrice;
                                                        len2 = i13;
                                                        repMatchPrice = curAndLenCharPrice2;
                                                    }
                                                    len2 = optimum2 + encoder.GetRepPrice(0, len, lenMain, numAvailableBytes);
                                                    repMatchPrice = encoder._optimum[i + repLen];
                                                    if (len2 < repMatchPrice.Price) {
                                                        repMatchPrice.Price = len2;
                                                        state23 = lenMain;
                                                        repMatchPrice.PosPrev = (i + shortRepPrice) + 1;
                                                        repMatchPrice.BackPrev = 0;
                                                        repMatchPrice.Prev1IsChar = true;
                                                        repMatchPrice.Prev2 = true;
                                                        repMatchPrice.PosPrev2 = i;
                                                        repMatchPrice.BackPrev2 = lenTestTemp + 4;
                                                    }
                                                    i14 = len2;
                                                    obj = repMatchPrice;
                                                    lenRes += 2;
                                                    if (lenRes != numDistancePairs) {
                                                        break;
                                                    }
                                                    repLen = state2;
                                                } else {
                                                    i10 = lenMain;
                                                    i13 = len2;
                                                    i11 = repMatchPrice;
                                                    i14 = i2;
                                                    optimal = optimum2;
                                                    lenEnd4 = repLen;
                                                    i12 = state2;
                                                }
                                            } else {
                                                i10 = lenMain;
                                                i9 = numAvailableBytes;
                                                i13 = len2;
                                                i11 = repMatchPrice;
                                                i14 = i2;
                                                optimal = optimum2;
                                                lenEnd4 = repLen;
                                                i12 = state2;
                                            }
                                            state2 = lenEnd4;
                                            lenRes += 2;
                                            if (lenRes != numDistancePairs) {
                                                break;
                                            }
                                            repLen = state2;
                                        } else {
                                            i10 = lenMain;
                                            i9 = numAvailableBytes;
                                            i13 = len2;
                                            i11 = repMatchPrice;
                                            lenEnd4 = repLen;
                                            i12 = numAvailableBytesFull;
                                        }
                                        shortRepPrice++;
                                        numAvailableBytes = i9;
                                        lenMain = i10;
                                        repMatchPrice = i11;
                                        numAvailableBytesFull = i12;
                                        len2 = i13;
                                        encoder = this;
                                    }
                                    shortRepPrice = state2;
                                    normalMatchPrice = i9;
                                } else {
                                    i13 = len2;
                                    i11 = repMatchPrice;
                                    shortRepPrice = repLen;
                                }
                                lenMain = i3;
                                numAvailableBytes = len3;
                                i2 = i6;
                                repMaxIndex = i7;
                                repMatchPrice = repMatchPrice2;
                                numAvailableBytes2 = i11;
                                position2 = i13;
                                encoder = this;
                            } else {
                                i15 = repLen;
                                matchByte2 = matchByte;
                            }
                        }
                        repLen = lenEnd;
                        shortRepPrice = 2;
                        numAvailableBytes = 0;
                        while (numAvailableBytes < 4) {
                            lenTestTemp = encoder._matchFinder.GetMatchLen(-1, encoder.reps[numAvailableBytes], repMatchPrice);
                            if (lenTestTemp >= 2) {
                                i2 = lenTestTemp;
                                while (true) {
                                    if (repLen >= i + i2) {
                                        repMaxIndex = encoder.GetRepPrice(numAvailableBytes, i2, lenMain, posState) + len;
                                        optimum = encoder._optimum[i + i2];
                                        lenEnd2 = repLen;
                                        if (repMaxIndex < optimum.Price) {
                                            optimum.Price = repMaxIndex;
                                            optimum.PosPrev = i;
                                            optimum.BackPrev = numAvailableBytes;
                                            optimum.Prev1IsChar = false;
                                        }
                                        i2--;
                                        if (i2 < 2) {
                                            break;
                                        }
                                        i8 = lenTestTemp;
                                        repLen = lenEnd2;
                                    } else {
                                        repLen++;
                                        encoder._optimum[repLen].Price = kIfinityPrice;
                                    }
                                }
                                i2 = lenTestTemp;
                                if (numAvailableBytes == 0) {
                                    shortRepPrice = i2 + 1;
                                }
                                if (i2 < numDistancePairs) {
                                    repMaxIndex = Math.min((numDistancePairs - 1) - i2, encoder._numFastBytes);
                                    repLen = encoder._matchFinder.GetMatchLen(i2, encoder.reps[numAvailableBytes], repMaxIndex);
                                    if (repLen >= 2) {
                                        state2 = Base.StateUpdateRep(lenMain);
                                        startLen = shortRepPrice;
                                        shortRepPrice = (len2 + i2) & encoder._posStateMask;
                                        posStateNext = shortRepPrice;
                                        numAvailableBytesFull = numDistancePairs;
                                        repMatchPrice2 = len;
                                        curAndLenCharPrice = ((len + encoder.GetRepPrice(numAvailableBytes, i2, lenMain, posState)) + com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice0(encoder._isMatch[(state2 << 4) + shortRepPrice])) + encoder._literalEncoder.GetSubCoder(len2 + i2, encoder._matchFinder.GetIndexByte((i2 - 1) - 1)).GetPrice(true, encoder._matchFinder.GetIndexByte((i2 - 1) - (encoder.reps[numAvailableBytes] + 1)), encoder._matchFinder.GetIndexByte(i2 - 1));
                                        numDistancePairs = Base.StateUpdateChar(state2);
                                        shortRepPrice = ((len2 + i2) + 1) & encoder._posStateMask;
                                        lenTestTemp = curAndLenCharPrice + com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice1(encoder._isMatch[(numDistancePairs << 4) + shortRepPrice]);
                                        repMaxIndex = com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice1(encoder._isRep[numDistancePairs]) + lenTestTemp;
                                        state2 = (i2 + 1) + repLen;
                                        len = lenEnd2;
                                        while (true) {
                                            nextMatchPrice = lenTestTemp;
                                            if (len < i + state2) {
                                                break;
                                            }
                                            len++;
                                            lenEnd3 = len;
                                            encoder._optimum[len].Price = kIfinityPrice;
                                            lenTestTemp = nextMatchPrice;
                                            len = lenEnd3;
                                        }
                                        lenTestTemp = repMaxIndex + encoder.GetRepPrice(0, repLen, numDistancePairs, shortRepPrice);
                                        state22 = numDistancePairs;
                                        numDistancePairs = encoder._optimum[i + state2];
                                        posStateNext2 = shortRepPrice;
                                        if (lenTestTemp < numDistancePairs.Price) {
                                            numDistancePairs.Price = lenTestTemp;
                                            curAndLenPrice = lenTestTemp;
                                            numDistancePairs.PosPrev = (i + i2) + 1;
                                            numDistancePairs.BackPrev = 0;
                                            numDistancePairs.Prev1IsChar = true;
                                            numDistancePairs.Prev2 = true;
                                            numDistancePairs.PosPrev2 = i;
                                            numDistancePairs.BackPrev2 = numAvailableBytes;
                                        }
                                        repLen = len;
                                        shortRepPrice = startLen;
                                    }
                                }
                                numAvailableBytesFull = numDistancePairs;
                                startLen = shortRepPrice;
                                repMatchPrice2 = len;
                                repLen = lenEnd2;
                                shortRepPrice = startLen;
                            } else {
                                numAvailableBytesFull = numDistancePairs;
                                repMatchPrice2 = len;
                            }
                            numAvailableBytes++;
                            len = repMatchPrice2;
                            numDistancePairs = numAvailableBytesFull;
                        }
                        numAvailableBytesFull = numDistancePairs;
                        repMatchPrice2 = len;
                        if (lenRes <= repMatchPrice) {
                            numDistancePairs = i4;
                        } else {
                            lenRes = repMatchPrice;
                            numDistancePairs = 0;
                            while (lenRes > encoder._matchDistances[numDistancePairs]) {
                                numDistancePairs += 2;
                            }
                            encoder._matchDistances[numDistancePairs] = lenRes;
                            numDistancePairs += 2;
                        }
                        if (lenRes < shortRepPrice) {
                            i13 = len2;
                            i11 = repMatchPrice;
                            shortRepPrice = repLen;
                        } else {
                            numAvailableBytes = matchPrice + com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice0(encoder._isRep[lenMain]);
                            while (repLen < i + lenRes) {
                                repLen++;
                                encoder._optimum[repLen].Price = kIfinityPrice;
                            }
                            lenRes = 0;
                            while (shortRepPrice > encoder._matchDistances[lenRes]) {
                                lenRes += 2;
                            }
                            while (true) {
                                lenTestTemp = encoder._matchDistances[lenRes + 1];
                                i2 = encoder.GetPosLenPrice(lenTestTemp, shortRepPrice, posState) + numAvailableBytes;
                                optimum2 = encoder._optimum[i + shortRepPrice];
                                if (i2 < optimum2.Price) {
                                    optimum2.Price = i2;
                                    optimum2.PosPrev = i;
                                    optimum2.BackPrev = lenTestTemp + 4;
                                    optimum2.Prev1IsChar = false;
                                }
                                if (shortRepPrice == encoder._matchDistances[lenRes]) {
                                    state2 = numAvailableBytesFull;
                                    if (shortRepPrice >= state2) {
                                        i10 = lenMain;
                                        i9 = numAvailableBytes;
                                        i13 = len2;
                                        i11 = repMatchPrice;
                                        i14 = i2;
                                        optimal = optimum2;
                                        lenEnd4 = repLen;
                                        i12 = state2;
                                    } else {
                                        i9 = numAvailableBytes;
                                        numAvailableBytes = Math.min((state2 - 1) - shortRepPrice, encoder._numFastBytes);
                                        len = encoder._matchFinder.GetMatchLen(shortRepPrice, lenTestTemp, numAvailableBytes);
                                        if (len < 2) {
                                            i10 = lenMain;
                                            i13 = len2;
                                            i11 = repMatchPrice;
                                            i14 = i2;
                                            optimal = optimum2;
                                            lenEnd4 = repLen;
                                            i12 = state2;
                                        } else {
                                            numAvailableBytes = Base.StateUpdateMatch(lenMain);
                                            i10 = lenMain;
                                            lenMain = (len2 + shortRepPrice) & encoder._posStateMask;
                                            i11 = repMatchPrice;
                                            posStateNext3 = lenMain;
                                            lenEnd4 = repLen;
                                            i12 = state2;
                                            repMatchPrice = (com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice0(encoder._isMatch[(numAvailableBytes << 4) + lenMain]) + i2) + encoder._literalEncoder.GetSubCoder(len2 + shortRepPrice, encoder._matchFinder.GetIndexByte((shortRepPrice - 1) - 1)).GetPrice(true, encoder._matchFinder.GetIndexByte((shortRepPrice - (lenTestTemp + 1)) - 1), encoder._matchFinder.GetIndexByte(shortRepPrice - 1));
                                            lenMain = Base.StateUpdateChar(numAvailableBytes);
                                            numAvailableBytes = ((len2 + shortRepPrice) + 1) & encoder._posStateMask;
                                            optimum2 = com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice1(encoder._isRep[lenMain]) + (com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice1(encoder._isMatch[(lenMain << 4) + numAvailableBytes]) + repMatchPrice);
                                            repLen = (shortRepPrice + 1) + len;
                                            state2 = lenEnd4;
                                            while (true) {
                                                i13 = len2;
                                                if (state2 < i + repLen) {
                                                    break;
                                                }
                                                state2++;
                                                curAndLenCharPrice2 = repMatchPrice;
                                                encoder._optimum[state2].Price = kIfinityPrice;
                                                len2 = i13;
                                                repMatchPrice = curAndLenCharPrice2;
                                            }
                                            len2 = optimum2 + encoder.GetRepPrice(0, len, lenMain, numAvailableBytes);
                                            repMatchPrice = encoder._optimum[i + repLen];
                                            if (len2 < repMatchPrice.Price) {
                                                repMatchPrice.Price = len2;
                                                state23 = lenMain;
                                                repMatchPrice.PosPrev = (i + shortRepPrice) + 1;
                                                repMatchPrice.BackPrev = 0;
                                                repMatchPrice.Prev1IsChar = true;
                                                repMatchPrice.Prev2 = true;
                                                repMatchPrice.PosPrev2 = i;
                                                repMatchPrice.BackPrev2 = lenTestTemp + 4;
                                            }
                                            i14 = len2;
                                            obj = repMatchPrice;
                                            lenRes += 2;
                                            if (lenRes != numDistancePairs) {
                                                break;
                                            }
                                            repLen = state2;
                                        }
                                    }
                                    state2 = lenEnd4;
                                    lenRes += 2;
                                    if (lenRes != numDistancePairs) {
                                        break;
                                    }
                                    repLen = state2;
                                } else {
                                    i10 = lenMain;
                                    i9 = numAvailableBytes;
                                    i13 = len2;
                                    i11 = repMatchPrice;
                                    lenEnd4 = repLen;
                                    i12 = numAvailableBytesFull;
                                }
                                shortRepPrice++;
                                numAvailableBytes = i9;
                                lenMain = i10;
                                repMatchPrice = i11;
                                numAvailableBytesFull = i12;
                                len2 = i13;
                                encoder = this;
                            }
                            shortRepPrice = state2;
                            normalMatchPrice = i9;
                        }
                        lenMain = i3;
                        numAvailableBytes = len3;
                        i2 = i6;
                        repMaxIndex = i7;
                        repMatchPrice = repMatchPrice2;
                        numAvailableBytes2 = i11;
                        position2 = i13;
                        encoder = this;
                    }
                }
            } else {
                encoder.backRes = -1;
                return 1;
            }
        }
    }

    boolean ChangePair(int smallDist, int bigDist) {
        return smallDist < (1 << (32 - 7)) && bigDist >= (smallDist << 7);
    }

    void WriteEndMarker(int posState) throws IOException {
        if (this._writeEndMark) {
            this._rangeEncoder.Encode(this._isMatch, (this._state << 4) + posState, 1);
            this._rangeEncoder.Encode(this._isRep, this._state, 0);
            this._state = Base.StateUpdateMatch(this._state);
            this._lenEncoder.Encode(this._rangeEncoder, 2 - 2, posState);
            this._posSlotEncoder[Base.GetLenToPosState(2)].Encode(this._rangeEncoder, 63);
            int posReduced = (1 << 30) - 1;
            this._rangeEncoder.EncodeDirectBits(posReduced >> 4, 30 - 4);
            this._posAlignEncoder.ReverseEncode(this._rangeEncoder, posReduced & 15);
        }
    }

    void Flush(int nowPos) throws IOException {
        ReleaseMFStream();
        WriteEndMarker(this._posStateMask & nowPos);
        this._rangeEncoder.FlushData();
        this._rangeEncoder.FlushStream();
    }

    public void CodeOneBlock(long[] inSize, long[] outSize, boolean[] finished) throws IOException {
        inSize[0] = 0;
        outSize[0] = 0;
        finished[0] = true;
        if (this._inStream != null) {
            r0._matchFinder.SetStream(r0._inStream);
            r0._matchFinder.Init();
            r0._needReleaseMFStream = true;
            r0._inStream = null;
        }
        if (!r0._finished) {
            r0._finished = true;
            long progressPosValuePrev = r0.nowPos64;
            int i = 4;
            if (r0.nowPos64 == 0) {
                if (r0._matchFinder.GetNumAvailableBytes() == 0) {
                    Flush((int) r0.nowPos64);
                    return;
                }
                ReadMatchDistances();
                r0._rangeEncoder.Encode(r0._isMatch, (r0._state << 4) + (((int) r0.nowPos64) & r0._posStateMask), 0);
                r0._state = Base.StateUpdateChar(r0._state);
                byte curByte = r0._matchFinder.GetIndexByte(0 - r0._additionalOffset);
                r0._literalEncoder.GetSubCoder((int) r0.nowPos64, r0._previousByte).Encode(r0._rangeEncoder, curByte);
                r0._previousByte = curByte;
                r0._additionalOffset--;
                r0.nowPos64++;
            }
            if (r0._matchFinder.GetNumAvailableBytes() == 0) {
                Flush((int) r0.nowPos64);
                return;
            }
            while (true) {
                int len = GetOptimum((int) r0.nowPos64);
                int pos = r0.backRes;
                int posState = ((int) r0.nowPos64) & r0._posStateMask;
                int complexState = (r0._state << i) + posState;
                int i2;
                int i3;
                if (len == 1 && pos == -1) {
                    r0._rangeEncoder.Encode(r0._isMatch, complexState, 0);
                    byte curByte2 = r0._matchFinder.GetIndexByte(0 - r0._additionalOffset);
                    Encoder2 subCoder = r0._literalEncoder.GetSubCoder((int) r0.nowPos64, r0._previousByte);
                    if (Base.StateIsCharState(r0._state)) {
                        subCoder.Encode(r0._rangeEncoder, curByte2);
                    } else {
                        subCoder.EncodeMatched(r0._rangeEncoder, r0._matchFinder.GetIndexByte(((0 - r0._repDistances[0]) - 1) - r0._additionalOffset), curByte2);
                    }
                    r0._previousByte = curByte2;
                    r0._state = Base.StateUpdateChar(r0._state);
                    i2 = posState;
                    i3 = complexState;
                } else {
                    r0._rangeEncoder.Encode(r0._isMatch, complexState, 1);
                    int distance;
                    if (pos < i) {
                        r0._rangeEncoder.Encode(r0._isRep, r0._state, 1);
                        if (pos == 0) {
                            r0._rangeEncoder.Encode(r0._isRepG0, r0._state, 0);
                            if (len == 1) {
                                r0._rangeEncoder.Encode(r0._isRep0Long, complexState, 0);
                            } else {
                                r0._rangeEncoder.Encode(r0._isRep0Long, complexState, 1);
                            }
                        } else {
                            r0._rangeEncoder.Encode(r0._isRepG0, r0._state, 1);
                            if (pos == 1) {
                                r0._rangeEncoder.Encode(r0._isRepG1, r0._state, 0);
                            } else {
                                r0._rangeEncoder.Encode(r0._isRepG1, r0._state, 1);
                                r0._rangeEncoder.Encode(r0._isRepG2, r0._state, pos - 2);
                            }
                        }
                        if (len == 1) {
                            r0._state = Base.StateUpdateShortRep(r0._state);
                        } else {
                            r0._repMatchLenEncoder.Encode(r0._rangeEncoder, len - 2, posState);
                            r0._state = Base.StateUpdateRep(r0._state);
                        }
                        distance = r0._repDistances[pos];
                        if (pos != 0) {
                            for (int i4 = pos; i4 >= 1; i4--) {
                                r0._repDistances[i4] = r0._repDistances[i4 - 1];
                            }
                            r0._repDistances[0] = distance;
                        }
                        i2 = posState;
                        i3 = complexState;
                    } else {
                        r0._rangeEncoder.Encode(r0._isRep, r0._state, 0);
                        r0._state = Base.StateUpdateMatch(r0._state);
                        r0._lenEncoder.Encode(r0._rangeEncoder, len - 2, posState);
                        pos -= 4;
                        distance = GetPosSlot(pos);
                        r0._posSlotEncoder[Base.GetLenToPosState(len)].Encode(r0._rangeEncoder, distance);
                        if (distance >= i) {
                            int footerBits = (distance >> 1) - 1;
                            int baseVal = ((distance & 1) | 2) << footerBits;
                            i = pos - baseVal;
                            if (distance < 14) {
                                BitTreeEncoder.ReverseEncode(r0._posEncoders, (baseVal - distance) - 1, r0._rangeEncoder, footerBits, i);
                                complexState = 1;
                            } else {
                                i3 = complexState;
                                r0._rangeEncoder.EncodeDirectBits(i >> 4, footerBits - 4);
                                r0._posAlignEncoder.ReverseEncode(r0._rangeEncoder, i & 15);
                                complexState = 1;
                                r0._alignPriceCount++;
                            }
                        } else {
                            i3 = complexState;
                            complexState = 1;
                        }
                        i = pos;
                        posState = 3;
                        for (complexState = 
/*
Method generation error in method: com.badlogic.gdx.utils.compression.lzma.Encoder.CodeOneBlock(long[], long[], boolean[]):void, dex: classes2.dex
jadx.core.utils.exceptions.CodegenException: Error generate insn: PHI: (r7_21 'complexState' int) = (r7_16 'complexState' int), (r7_19 'complexState' int), (r7_20 'complexState' int) binds: {(r7_19 'complexState' int)=B:55:0x01eb, (r7_20 'complexState' int)=B:56:0x0207, (r7_16 'complexState' int)=B:54:0x01da} in method: com.badlogic.gdx.utils.compression.lzma.Encoder.CodeOneBlock(long[], long[], boolean[]):void, dex: classes2.dex
	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:226)
	at jadx.core.codegen.RegionGen.makeLoop(RegionGen.java:184)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:61)
	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:87)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:53)
	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:93)
	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:128)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:57)
	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:87)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:53)
	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:93)
	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:128)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:57)
	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:87)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:53)
	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:93)
	at jadx.core.codegen.RegionGen.makeLoop(RegionGen.java:174)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:61)
	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:87)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:53)
	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:87)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:53)
	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:87)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:53)
	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:93)
	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:118)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:57)
	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:87)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:53)
	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:187)
	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:320)
	at jadx.core.codegen.ClassGen.addMethods(ClassGen.java:257)
	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:220)
	at jadx.core.codegen.ClassGen.addClassCode(ClassGen.java:110)
	at jadx.core.codegen.ClassGen.makeClass(ClassGen.java:75)
	at jadx.core.codegen.CodeGen.visit(CodeGen.java:12)
	at jadx.core.ProcessClass.process(ProcessClass.java:40)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:282)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:200)
	at jadx.api.JadxDecompiler$$Lambda$8/1115170891.run(Unknown Source)
Caused by: jadx.core.utils.exceptions.CodegenException: PHI can be used only in fallback mode
	at jadx.core.codegen.InsnGen.fallbackOnlyInsn(InsnGen.java:537)
	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:509)
	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:220)
	... 40 more

*/

                        void ReleaseMFStream() {
                            if (this._matchFinder != null && this._needReleaseMFStream) {
                                this._matchFinder.ReleaseStream();
                                this._needReleaseMFStream = false;
                            }
                        }

                        void SetOutStream(OutputStream outStream) {
                            this._rangeEncoder.SetStream(outStream);
                        }

                        void ReleaseOutStream() {
                            this._rangeEncoder.ReleaseStream();
                        }

                        void ReleaseStreams() {
                            ReleaseMFStream();
                            ReleaseOutStream();
                        }

                        void SetStreams(InputStream inStream, OutputStream outStream, long inSize, long outSize) {
                            this._inStream = inStream;
                            this._finished = false;
                            Create();
                            SetOutStream(outStream);
                            Init();
                            FillDistancesPrices();
                            FillAlignPrices();
                            this._lenEncoder.SetTableSize((this._numFastBytes + 1) - 2);
                            this._lenEncoder.UpdateTables(1 << this._posStateBits);
                            this._repMatchLenEncoder.SetTableSize((this._numFastBytes + 1) - 2);
                            this._repMatchLenEncoder.UpdateTables(1 << this._posStateBits);
                            this.nowPos64 = 0;
                        }

                        public void Code(InputStream inStream, OutputStream outStream, long inSize, long outSize, ICodeProgress progress) throws IOException {
                            this._needReleaseMFStream = false;
                            try {
                                SetStreams(inStream, outStream, inSize, outSize);
                                while (true) {
                                    CodeOneBlock(this.processedInSize, this.processedOutSize, this.finished);
                                    if (this.finished[0]) {
                                        break;
                                    } else if (progress != null) {
                                        progress.SetProgress(this.processedInSize[0], this.processedOutSize[0]);
                                    }
                                }
                            } finally {
                                ReleaseStreams();
                            }
                        }

                        public void WriteCoderProperties(OutputStream outStream) throws IOException {
                            this.properties[0] = (byte) ((((this._posStateBits * 5) + this._numLiteralPosStateBits) * 9) + this._numLiteralContextBits);
                            for (int i = 0; i < 4; i++) {
                                this.properties[i + 1] = (byte) (this._dictionarySize >> (i * 8));
                            }
                            outStream.write(this.properties, 0, 5);
                        }

                        void FillDistancesPrices() {
                            for (int i = 4; i < 128; i++) {
                                int posSlot = GetPosSlot(i);
                                int footerBits = (posSlot >> 1) - 1;
                                int baseVal = ((posSlot & 1) | 2) << footerBits;
                                this.tempPrices[i] = BitTreeEncoder.ReverseGetPrice(this._posEncoders, (baseVal - posSlot) - 1, footerBits, i - baseVal);
                            }
                            for (footerBits = 0; footerBits < 4; footerBits++) {
                                int posSlot2;
                                int i2;
                                BitTreeEncoder encoder = this._posSlotEncoder[footerBits];
                                int st = footerBits << 6;
                                for (posSlot2 = 0; posSlot2 < this._distTableSize; posSlot2++) {
                                    this._posSlotPrices[st + posSlot2] = encoder.GetPrice(posSlot2);
                                }
                                for (posSlot2 = 14; posSlot2 < this._distTableSize; posSlot2++) {
                                    int[] iArr = this._posSlotPrices;
                                    i2 = st + posSlot2;
                                    iArr[i2] = iArr[i2] + ((((posSlot2 >> 1) - 1) - 4) << 6);
                                }
                                int st2 = footerBits * 128;
                                i2 = 0;
                                while (i2 < 4) {
                                    this._distancesPrices[st2 + i2] = this._posSlotPrices[st + i2];
                                    i2++;
                                }
                                while (i2 < 128) {
                                    this._distancesPrices[st2 + i2] = this._posSlotPrices[GetPosSlot(i2) + st] + this.tempPrices[i2];
                                    i2++;
                                }
                            }
                            this._matchPriceCount = 0;
                        }

                        void FillAlignPrices() {
                            for (int i = 0; i < 16; i++) {
                                this._alignPrices[i] = this._posAlignEncoder.ReverseGetPrice(i);
                            }
                            this._alignPriceCount = 0;
                        }

                        public boolean SetAlgorithm(int algorithm) {
                            return true;
                        }

                        public boolean SetDictionarySize(int dictionarySize) {
                            int dicLogSize = 0;
                            if (dictionarySize >= 1) {
                                if (dictionarySize <= (1 << 29)) {
                                    this._dictionarySize = dictionarySize;
                                    while (dictionarySize > (1 << dicLogSize)) {
                                        dicLogSize++;
                                    }
                                    this._distTableSize = dicLogSize * 2;
                                    return true;
                                }
                            }
                            return false;
                        }

                        public boolean SetNumFastBytes(int numFastBytes) {
                            if (numFastBytes >= 5) {
                                if (numFastBytes <= 273) {
                                    this._numFastBytes = numFastBytes;
                                    return true;
                                }
                            }
                            return false;
                        }

                        public boolean SetMatchFinder(int matchFinderIndex) {
                            if (matchFinderIndex >= 0) {
                                if (matchFinderIndex <= 2) {
                                    int matchFinderIndexPrev = this._matchFinderType;
                                    this._matchFinderType = matchFinderIndex;
                                    if (!(this._matchFinder == null || matchFinderIndexPrev == this._matchFinderType)) {
                                        this._dictionarySizePrev = -1;
                                        this._matchFinder = null;
                                    }
                                    return true;
                                }
                            }
                            return false;
                        }

                        public boolean SetLcLpPb(int lc, int lp, int pb) {
                            if (lp >= 0 && lp <= 4 && lc >= 0 && lc <= 8 && pb >= 0) {
                                if (pb <= 4) {
                                    this._numLiteralPosStateBits = lp;
                                    this._numLiteralContextBits = lc;
                                    this._posStateBits = pb;
                                    this._posStateMask = (1 << this._posStateBits) - 1;
                                    return true;
                                }
                            }
                            return false;
                        }

                        public void SetEndMarkerMode(boolean endMarkerMode) {
                            this._writeEndMark = endMarkerMode;
                        }
                    }
