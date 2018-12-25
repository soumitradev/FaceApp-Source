package com.badlogic.gdx.utils.compression.lz;

import android.support.v4.internal.view.SupportMenu;
import java.io.IOException;

public class BinTree extends InWindow {
    private static final int[] CrcTable = new int[256];
    static final int kBT2HashSize = 65536;
    static final int kEmptyHashValue = 0;
    static final int kHash2Size = 1024;
    static final int kHash3Offset = 1024;
    static final int kHash3Size = 65536;
    static final int kMaxValForNormalize = 1073741823;
    static final int kStartMaxLen = 1;
    boolean HASH_ARRAY = true;
    int _cutValue = 255;
    int _cyclicBufferPos;
    int _cyclicBufferSize = 0;
    int[] _hash;
    int _hashMask;
    int _hashSizeSum = 0;
    int _matchMaxLen;
    int[] _son;
    int kFixHashSize = 66560;
    int kMinMatchCheck = 4;
    int kNumHashDirectBytes = 0;

    public void SetType(int numHashBytes) {
        this.HASH_ARRAY = numHashBytes > 2;
        if (this.HASH_ARRAY) {
            this.kNumHashDirectBytes = 0;
            this.kMinMatchCheck = 4;
            this.kFixHashSize = 66560;
            return;
        }
        this.kNumHashDirectBytes = 2;
        this.kMinMatchCheck = 3;
        this.kFixHashSize = 0;
    }

    public void Init() throws IOException {
        super.Init();
        for (int i = 0; i < this._hashSizeSum; i++) {
            this._hash[i] = 0;
        }
        this._cyclicBufferPos = 0;
        ReduceOffsets(-1);
    }

    public void MovePos() throws IOException {
        int i = this._cyclicBufferPos + 1;
        this._cyclicBufferPos = i;
        if (i >= this._cyclicBufferSize) {
            this._cyclicBufferPos = 0;
        }
        super.MovePos();
        if (this._pos == kMaxValForNormalize) {
            Normalize();
        }
    }

    public boolean Create(int historySize, int keepAddBufferBefore, int matchMaxLen, int keepAddBufferAfter) {
        if (historySize > 1073741567) {
            return false;
        }
        this._cutValue = (matchMaxLen >> 1) + 16;
        super.Create(historySize + keepAddBufferBefore, matchMaxLen + keepAddBufferAfter, ((((historySize + keepAddBufferBefore) + matchMaxLen) + keepAddBufferAfter) / 2) + 256);
        this._matchMaxLen = matchMaxLen;
        int cyclicBufferSize = historySize + 1;
        if (this._cyclicBufferSize != cyclicBufferSize) {
            this._cyclicBufferSize = cyclicBufferSize;
            this._son = new int[(cyclicBufferSize * 2)];
        }
        int hs = 65536;
        if (this.HASH_ARRAY) {
            hs = historySize - 1;
            hs |= hs >> 1;
            hs |= hs >> 2;
            hs |= hs >> 4;
            hs = ((hs | (hs >> 8)) >> 1) | SupportMenu.USER_MASK;
            if (hs > 16777216) {
                hs >>= 1;
            }
            this._hashMask = hs;
            hs = (hs + 1) + this.kFixHashSize;
        }
        if (hs != this._hashSizeSum) {
            this._hashSizeSum = hs;
            this._hash = new int[hs];
        }
        return true;
    }

    public int GetMatches(int[] distances) throws IOException {
        int lenLimit;
        int temp;
        int curMatch2;
        int curMatch3;
        int offset;
        int i;
        int maxLen;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int count;
        int[] iArr;
        if (this._pos + this._matchMaxLen <= this._streamPos) {
            lenLimit = r0._matchMaxLen;
        } else {
            lenLimit = r0._streamPos - r0._pos;
            if (lenLimit < r0.kMinMatchCheck) {
                MovePos();
                return 0;
            }
        }
        int i7 = 0;
        int matchMinPos = r0._pos > r0._cyclicBufferSize ? r0._pos - r0._cyclicBufferSize : 0;
        int cur = r0._bufferOffset + r0._pos;
        int maxLen2 = 1;
        int hash2Value = 0;
        int hash3Value = 0;
        if (r0.HASH_ARRAY) {
            temp = CrcTable[r0._bufferBase[cur] & 255] ^ (r0._bufferBase[cur + 1] & 255);
            hash2Value = temp & 1023;
            temp ^= (r0._bufferBase[cur + 2] & 255) << 8;
            hash3Value = temp & SupportMenu.USER_MASK;
            temp = ((CrcTable[r0._bufferBase[cur + 3] & 255] << 5) ^ temp) & r0._hashMask;
        } else {
            temp = (r0._bufferBase[cur] & 255) ^ ((r0._bufferBase[cur + 1] & 255) << 8);
        }
        int curMatch = r0._hash[r0.kFixHashSize + temp];
        if (r0.HASH_ARRAY) {
            curMatch2 = r0._hash[hash2Value];
            curMatch3 = r0._hash[hash3Value + 1024];
            r0._hash[hash2Value] = r0._pos;
            r0._hash[hash3Value + 1024] = r0._pos;
            if (curMatch2 > matchMinPos && r0._bufferBase[r0._bufferOffset + curMatch2] == r0._bufferBase[cur]) {
                offset = 0 + 1;
                maxLen2 = 2;
                distances[0] = 2;
                i7 = offset + 1;
                distances[offset] = (r0._pos - curMatch2) - 1;
            }
            if (curMatch3 > matchMinPos && r0._bufferBase[r0._bufferOffset + curMatch3] == r0._bufferBase[cur]) {
                if (curMatch3 == curMatch2) {
                    i7 -= 2;
                }
                offset = i7 + 1;
                maxLen2 = 3;
                distances[i7] = 3;
                i7 = offset + 1;
                distances[offset] = (r0._pos - curMatch3) - 1;
                curMatch2 = curMatch3;
            }
            if (i7 != 0 && curMatch2 == curMatch) {
                i7 -= 2;
                maxLen2 = 1;
            }
        }
        r0._hash[r0.kFixHashSize + temp] = r0._pos;
        offset = (r0._cyclicBufferPos << 1) + 1;
        curMatch2 = r0._cyclicBufferPos << 1;
        curMatch3 = r0.kNumHashDirectBytes;
        int len1 = curMatch3;
        if (r0.kNumHashDirectBytes == 0 || curMatch <= matchMinPos) {
            i = offset;
            maxLen = maxLen2;
        } else {
            i = offset;
            maxLen = maxLen2;
            if (r0._bufferBase[(r0._bufferOffset + curMatch) + r0.kNumHashDirectBytes] != r0._bufferBase[r0.kNumHashDirectBytes + cur]) {
                offset = i7 + 1;
                maxLen2 = r0.kNumHashDirectBytes;
                i2 = maxLen2;
                distances[i7] = maxLen2;
                i7 = offset + 1;
                distances[offset] = (r0._pos - curMatch) - 1;
                offset = r0._cutValue;
                while (curMatch > matchMinPos) {
                    maxLen2 = offset - 1;
                    if (offset == 0) {
                        i3 = matchMinPos;
                        i4 = hash2Value;
                        i5 = hash3Value;
                        i6 = temp;
                        break;
                    }
                    offset = r0._pos - curMatch;
                    i3 = matchMinPos;
                    if (offset > r0._cyclicBufferPos) {
                        matchMinPos = r0._cyclicBufferPos - offset;
                        count = maxLen2;
                    } else {
                        count = maxLen2;
                        matchMinPos = (r0._cyclicBufferPos - offset) + r0._cyclicBufferSize;
                    }
                    matchMinPos <<= 1;
                    maxLen2 = r0._bufferOffset + curMatch;
                    maxLen = Math.min(curMatch3, len1);
                    i4 = hash2Value;
                    i5 = hash3Value;
                    if (r0._bufferBase[maxLen2 + maxLen] == r0._bufferBase[cur + maxLen]) {
                        while (true) {
                            hash2Value = maxLen + 1;
                            if (hash2Value == lenLimit) {
                                break;
                            }
                            i6 = temp;
                            if (r0._bufferBase[maxLen2 + hash2Value] != r0._bufferBase[cur + hash2Value]) {
                                break;
                            }
                            maxLen = hash2Value;
                            temp = i6;
                            if (i2 < hash2Value) {
                                hash3Value = i7 + 1;
                                i2 = hash2Value;
                                distances[i7] = hash2Value;
                                i7 = hash3Value + 1;
                                distances[hash3Value] = offset - 1;
                                if (hash2Value == lenLimit) {
                                    r0._son[curMatch2] = r0._son[matchMinPos];
                                    r0._son[i] = r0._son[matchMinPos + 1];
                                    break;
                                }
                            }
                        }
                        i6 = temp;
                        if (i2 < hash2Value) {
                            hash3Value = i7 + 1;
                            i2 = hash2Value;
                            distances[i7] = hash2Value;
                            i7 = hash3Value + 1;
                            distances[hash3Value] = offset - 1;
                            if (hash2Value == lenLimit) {
                                r0._son[curMatch2] = r0._son[matchMinPos];
                                r0._son[i] = r0._son[matchMinPos + 1];
                                break;
                            }
                        }
                    }
                    i6 = temp;
                    hash2Value = maxLen;
                    if ((r0._bufferBase[maxLen2 + hash2Value] & 255) >= (r0._bufferBase[cur + hash2Value] & 255)) {
                        r0._son[curMatch2] = curMatch;
                        hash3Value = matchMinPos + 1;
                        temp = r0._son[hash3Value];
                        curMatch2 = hash3Value;
                        len1 = hash2Value;
                    } else {
                        r0._son[i] = curMatch;
                        hash3Value = matchMinPos;
                        temp = r0._son[hash3Value];
                        curMatch3 = hash2Value;
                        i = hash3Value;
                    }
                    curMatch = temp;
                    matchMinPos = i3;
                    offset = count;
                    hash2Value = i4;
                    hash3Value = i5;
                    temp = i6;
                }
                i4 = hash2Value;
                i5 = hash3Value;
                i6 = temp;
                maxLen2 = offset;
                iArr = r0._son;
                r0._son[curMatch2] = 0;
                iArr[i] = 0;
                count = maxLen2;
                MovePos();
                return i7;
            }
        }
        i2 = maxLen;
        offset = r0._cutValue;
        while (curMatch > matchMinPos) {
            maxLen2 = offset - 1;
            if (offset == 0) {
                i3 = matchMinPos;
                i4 = hash2Value;
                i5 = hash3Value;
                i6 = temp;
                break;
            }
            offset = r0._pos - curMatch;
            i3 = matchMinPos;
            if (offset > r0._cyclicBufferPos) {
                count = maxLen2;
                matchMinPos = (r0._cyclicBufferPos - offset) + r0._cyclicBufferSize;
            } else {
                matchMinPos = r0._cyclicBufferPos - offset;
                count = maxLen2;
            }
            matchMinPos <<= 1;
            maxLen2 = r0._bufferOffset + curMatch;
            maxLen = Math.min(curMatch3, len1);
            i4 = hash2Value;
            i5 = hash3Value;
            if (r0._bufferBase[maxLen2 + maxLen] == r0._bufferBase[cur + maxLen]) {
                while (true) {
                    hash2Value = maxLen + 1;
                    if (hash2Value == lenLimit) {
                        i6 = temp;
                        if (r0._bufferBase[maxLen2 + hash2Value] != r0._bufferBase[cur + hash2Value]) {
                            break;
                        }
                        maxLen = hash2Value;
                        temp = i6;
                    } else {
                        break;
                    }
                    if (i2 < hash2Value) {
                        hash3Value = i7 + 1;
                        i2 = hash2Value;
                        distances[i7] = hash2Value;
                        i7 = hash3Value + 1;
                        distances[hash3Value] = offset - 1;
                        if (hash2Value == lenLimit) {
                            r0._son[curMatch2] = r0._son[matchMinPos];
                            r0._son[i] = r0._son[matchMinPos + 1];
                            break;
                        }
                    }
                }
                i6 = temp;
                if (i2 < hash2Value) {
                    hash3Value = i7 + 1;
                    i2 = hash2Value;
                    distances[i7] = hash2Value;
                    i7 = hash3Value + 1;
                    distances[hash3Value] = offset - 1;
                    if (hash2Value == lenLimit) {
                        r0._son[curMatch2] = r0._son[matchMinPos];
                        r0._son[i] = r0._son[matchMinPos + 1];
                        break;
                    }
                }
            }
            i6 = temp;
            hash2Value = maxLen;
            if ((r0._bufferBase[maxLen2 + hash2Value] & 255) >= (r0._bufferBase[cur + hash2Value] & 255)) {
                r0._son[i] = curMatch;
                hash3Value = matchMinPos;
                temp = r0._son[hash3Value];
                curMatch3 = hash2Value;
                i = hash3Value;
            } else {
                r0._son[curMatch2] = curMatch;
                hash3Value = matchMinPos + 1;
                temp = r0._son[hash3Value];
                curMatch2 = hash3Value;
                len1 = hash2Value;
            }
            curMatch = temp;
            matchMinPos = i3;
            offset = count;
            hash2Value = i4;
            hash3Value = i5;
            temp = i6;
        }
        i4 = hash2Value;
        i5 = hash3Value;
        i6 = temp;
        maxLen2 = offset;
        iArr = r0._son;
        r0._son[curMatch2] = 0;
        iArr[i] = 0;
        count = maxLen2;
        MovePos();
        return i7;
    }

    public void Skip(int num) throws IOException {
        BinTree binTree = this;
        int num2 = num;
        do {
            int lenLimit;
            int temp;
            int count;
            int i;
            if (binTree._pos + binTree._matchMaxLen <= binTree._streamPos) {
                lenLimit = binTree._matchMaxLen;
            } else {
                lenLimit = binTree._streamPos - binTree._pos;
                if (lenLimit < binTree.kMinMatchCheck) {
                    MovePos();
                    num2--;
                }
            }
            int matchMinPos = binTree._pos > binTree._cyclicBufferSize ? binTree._pos - binTree._cyclicBufferSize : 0;
            int cur = binTree._bufferOffset + binTree._pos;
            if (binTree.HASH_ARRAY) {
                temp = CrcTable[binTree._bufferBase[cur] & 255] ^ (binTree._bufferBase[cur + 1] & 255);
                binTree._hash[temp & 1023] = binTree._pos;
                temp ^= (binTree._bufferBase[cur + 2] & 255) << 8;
                binTree._hash[(SupportMenu.USER_MASK & temp) + 1024] = binTree._pos;
                temp = ((CrcTable[binTree._bufferBase[cur + 3] & 255] << 5) ^ temp) & binTree._hashMask;
            } else {
                temp = (binTree._bufferBase[cur] & 255) ^ ((binTree._bufferBase[cur + 1] & 255) << 8);
            }
            int curMatch = binTree._hash[binTree.kFixHashSize + temp];
            binTree._hash[binTree.kFixHashSize + temp] = binTree._pos;
            int ptr0 = (binTree._cyclicBufferPos << 1) + 1;
            int ptr1 = binTree._cyclicBufferPos << 1;
            int len0 = binTree.kNumHashDirectBytes;
            int len1 = len0;
            int count2 = binTree._cutValue;
            while (curMatch > matchMinPos) {
                count = count2 - 1;
                if (count2 == 0) {
                    int i2 = matchMinPos;
                    i = temp;
                    break;
                }
                count2 = binTree._pos - curMatch;
                int cyclicPos = (count2 <= binTree._cyclicBufferPos ? binTree._cyclicBufferPos - count2 : (binTree._cyclicBufferPos - count2) + binTree._cyclicBufferSize) << 1;
                int pby1 = binTree._bufferOffset + curMatch;
                int len = Math.min(len0, len1);
                i2 = matchMinPos;
                if (binTree._bufferBase[pby1 + len] == binTree._bufferBase[cur + len]) {
                    while (true) {
                        matchMinPos = len + 1;
                        if (matchMinPos == lenLimit) {
                            break;
                        }
                        i = temp;
                        if (binTree._bufferBase[pby1 + matchMinPos] != binTree._bufferBase[cur + matchMinPos]) {
                            break;
                        }
                        len = matchMinPos;
                        temp = i;
                        if (matchMinPos == lenLimit) {
                            binTree._son[ptr1] = binTree._son[cyclicPos];
                            binTree._son[ptr0] = binTree._son[cyclicPos + 1];
                            break;
                        }
                        len = matchMinPos;
                    }
                    i = temp;
                    if (matchMinPos == lenLimit) {
                        binTree._son[ptr1] = binTree._son[cyclicPos];
                        binTree._son[ptr0] = binTree._son[cyclicPos + 1];
                        break;
                    }
                    len = matchMinPos;
                } else {
                    i = temp;
                }
                if ((binTree._bufferBase[pby1 + len] & 255) < (binTree._bufferBase[cur + len] & 255)) {
                    binTree._son[ptr1] = curMatch;
                    matchMinPos = cyclicPos + 1;
                    ptr1 = matchMinPos;
                    curMatch = binTree._son[matchMinPos];
                    len1 = len;
                } else {
                    binTree._son[ptr0] = curMatch;
                    matchMinPos = cyclicPos;
                    ptr0 = matchMinPos;
                    curMatch = binTree._son[matchMinPos];
                    len0 = len;
                }
                count2 = count;
                matchMinPos = i2;
                temp = i;
            }
            i = temp;
            count = count2;
            int[] iArr = binTree._son;
            binTree._son[ptr1] = 0;
            iArr[ptr0] = 0;
            MovePos();
            num2--;
        } while (num2 != 0);
    }

    void NormalizeLinks(int[] items, int numItems, int subValue) {
        for (int i = 0; i < numItems; i++) {
            int value = items[i];
            if (value <= subValue) {
                value = 0;
            } else {
                value -= subValue;
            }
            items[i] = value;
        }
    }

    void Normalize() {
        int subValue = this._pos - this._cyclicBufferSize;
        NormalizeLinks(this._son, this._cyclicBufferSize * 2, subValue);
        NormalizeLinks(this._hash, this._hashSizeSum, subValue);
        ReduceOffsets(subValue);
    }

    public void SetCutValue(int cutValue) {
        this._cutValue = cutValue;
    }

    static {
        for (int i = 0; i < 256; i++) {
            int r = i;
            for (int j = 0; j < 8; j++) {
                if ((r & 1) != 0) {
                    r = (r >>> 1) ^ -306674912;
                } else {
                    r >>>= 1;
                }
            }
            CrcTable[i] = r;
        }
    }
}
