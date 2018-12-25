package org.apache.commons.compress.compressors.bzip2;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import kotlin.text.Typography;
import org.apache.commons.compress.compressors.CompressorInputStream;
import org.billthefarmer.mididriver.GeneralMidiConstants;

public class BZip2CompressorInputStream extends CompressorInputStream implements BZip2Constants {
    private static final int EOF = 0;
    private static final int NO_RAND_PART_A_STATE = 5;
    private static final int NO_RAND_PART_B_STATE = 6;
    private static final int NO_RAND_PART_C_STATE = 7;
    private static final int RAND_PART_A_STATE = 2;
    private static final int RAND_PART_B_STATE = 3;
    private static final int RAND_PART_C_STATE = 4;
    private static final int START_BLOCK_STATE = 1;
    private boolean blockRandomised;
    private int blockSize100k;
    private int bsBuff;
    private int bsLive;
    private int computedBlockCRC;
    private int computedCombinedCRC;
    private final CRC crc;
    private int currentState;
    private Data data;
    private final boolean decompressConcatenated;
    private InputStream in;
    private int last;
    private int nInUse;
    private int origPtr;
    private int storedBlockCRC;
    private int storedCombinedCRC;
    private int su_ch2;
    private int su_chPrev;
    private int su_count;
    private int su_i2;
    private int su_j2;
    private int su_rNToGo;
    private int su_rTPos;
    private int su_tPos;
    private char su_z;

    private static final class Data {
        final int[][] base = ((int[][]) Array.newInstance(int.class, new int[]{6, 258}));
        final int[] cftab = new int[257];
        final char[] getAndMoveToFrontDecode_yy = new char[256];
        final boolean[] inUse = new boolean[256];
        final int[][] limit = ((int[][]) Array.newInstance(int.class, new int[]{6, 258}));
        byte[] ll8;
        final int[] minLens = new int[6];
        final int[][] perm = ((int[][]) Array.newInstance(int.class, new int[]{6, 258}));
        final byte[] recvDecodingTables_pos = new byte[6];
        final byte[] selector = new byte[BZip2Constants.MAX_SELECTORS];
        final byte[] selectorMtf = new byte[BZip2Constants.MAX_SELECTORS];
        final byte[] seqToUnseq = new byte[256];
        final char[][] temp_charArray2d = ((char[][]) Array.newInstance(char.class, new int[]{6, 258}));
        int[] tt;
        final int[] unzftab = new int[256];

        Data(int blockSize100k) {
            this.ll8 = new byte[(BZip2Constants.BASEBLOCKSIZE * blockSize100k)];
        }

        int[] initTT(int length) {
            int[] ttShadow = this.tt;
            if (ttShadow != null && ttShadow.length >= length) {
                return ttShadow;
            }
            int[] iArr = new int[length];
            ttShadow = iArr;
            this.tt = iArr;
            return ttShadow;
        }
    }

    public BZip2CompressorInputStream(InputStream in) throws IOException {
        this(in, false);
    }

    public BZip2CompressorInputStream(InputStream in, boolean decompressConcatenated) throws IOException {
        this.crc = new CRC();
        this.currentState = 1;
        this.in = in;
        this.decompressConcatenated = decompressConcatenated;
        init(true);
        initBlock();
    }

    public int read() throws IOException {
        if (this.in != null) {
            int r = read0();
            count(r < 0 ? -1 : 1);
            return r;
        }
        throw new IOException("stream closed");
    }

    public int read(byte[] dest, int offs, int len) throws IOException {
        StringBuilder stringBuilder;
        if (offs < 0) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("offs(");
            stringBuilder.append(offs);
            stringBuilder.append(") < 0.");
            throw new IndexOutOfBoundsException(stringBuilder.toString());
        } else if (len < 0) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("len(");
            stringBuilder.append(len);
            stringBuilder.append(") < 0.");
            throw new IndexOutOfBoundsException(stringBuilder.toString());
        } else if (offs + len > dest.length) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("offs(");
            stringBuilder.append(offs);
            stringBuilder.append(") + len(");
            stringBuilder.append(len);
            stringBuilder.append(") > dest.length(");
            stringBuilder.append(dest.length);
            stringBuilder.append(").");
            throw new IndexOutOfBoundsException(stringBuilder.toString());
        } else if (this.in == null) {
            throw new IOException("stream closed");
        } else {
            int hi = offs + len;
            int destOffs = offs;
            while (destOffs < hi) {
                int read0 = read0();
                int b = read0;
                if (read0 < 0) {
                    break;
                }
                read0 = destOffs + 1;
                dest[destOffs] = (byte) b;
                count(1);
                destOffs = read0;
            }
            return destOffs == offs ? -1 : destOffs - offs;
        }
    }

    private void makeMaps() {
        boolean[] inUse = this.data.inUse;
        byte[] seqToUnseq = this.data.seqToUnseq;
        int nInUseShadow = 0;
        for (int i = 0; i < 256; i++) {
            if (inUse[i]) {
                int nInUseShadow2 = nInUseShadow + 1;
                seqToUnseq[nInUseShadow] = (byte) i;
                nInUseShadow = nInUseShadow2;
            }
        }
        this.nInUse = nInUseShadow;
    }

    private int read0() throws IOException {
        switch (this.currentState) {
            case 0:
                return -1;
            case 1:
                return setupBlock();
            case 2:
                throw new IllegalStateException();
            case 3:
                return setupRandPartB();
            case 4:
                return setupRandPartC();
            case 5:
                throw new IllegalStateException();
            case 6:
                return setupNoRandPartB();
            case 7:
                return setupNoRandPartC();
            default:
                throw new IllegalStateException();
        }
    }

    private boolean init(boolean isFirstStream) throws IOException {
        if (this.in == null) {
            throw new IOException("No InputStream");
        }
        int magic0 = this.in.read();
        if (magic0 == -1 && !isFirstStream) {
            return false;
        }
        int magic1 = this.in.read();
        int magic2 = this.in.read();
        if (magic0 == 66 && magic1 == 90) {
            if (magic2 == 104) {
                int blockSize = this.in.read();
                if (blockSize >= 49) {
                    if (blockSize <= 57) {
                        this.blockSize100k = blockSize - 48;
                        this.bsLive = 0;
                        this.computedCombinedCRC = 0;
                        return true;
                    }
                }
                throw new IOException("BZip2 block size is invalid");
            }
        }
        throw new IOException(isFirstStream ? "Stream is not in the BZip2 format" : "Garbage after a valid BZip2 stream");
    }

    private void initBlock() throws IOException {
        do {
            char magic0 = bsGetUByte();
            char magic1 = bsGetUByte();
            char magic2 = bsGetUByte();
            char magic3 = bsGetUByte();
            char magic4 = bsGetUByte();
            char magic5 = bsGetUByte();
            if (magic0 == '\u0017' && magic1 == 'r' && magic2 == 'E' && magic3 == '8' && magic4 == 'P') {
                if (magic5 != '') {
                }
            }
            boolean z = false;
            if (magic0 == '1' && magic1 == 'A' && magic2 == 'Y' && magic3 == Typography.amp && magic4 == 'S') {
                if (magic5 == 'Y') {
                    this.storedBlockCRC = bsGetInt();
                    if (bsR(1) == 1) {
                        z = true;
                    }
                    this.blockRandomised = z;
                    if (this.data == null) {
                        this.data = new Data(this.blockSize100k);
                    }
                    getAndMoveToFrontDecode();
                    this.crc.initialiseCRC();
                    this.currentState = 1;
                    return;
                }
            }
            this.currentState = 0;
            throw new IOException("bad block header");
        } while (!complete());
    }

    private void endBlock() throws IOException {
        this.computedBlockCRC = this.crc.getFinalCRC();
        if (this.storedBlockCRC != this.computedBlockCRC) {
            this.computedCombinedCRC = (this.storedCombinedCRC << 1) | (this.storedCombinedCRC >>> 31);
            this.computedCombinedCRC ^= this.storedBlockCRC;
            throw new IOException("BZip2 CRC error");
        }
        this.computedCombinedCRC = (this.computedCombinedCRC << 1) | (this.computedCombinedCRC >>> 31);
        this.computedCombinedCRC ^= this.computedBlockCRC;
    }

    private boolean complete() throws IOException {
        this.storedCombinedCRC = bsGetInt();
        this.currentState = 0;
        this.data = null;
        if (this.storedCombinedCRC != this.computedCombinedCRC) {
            throw new IOException("BZip2 CRC error");
        }
        if (this.decompressConcatenated) {
            if (init(false)) {
                return false;
            }
        }
        return true;
    }

    public void close() throws IOException {
        InputStream inShadow = this.in;
        if (inShadow != null) {
            try {
                if (inShadow != System.in) {
                    inShadow.close();
                }
                this.data = null;
                this.in = null;
            } catch (Throwable th) {
                this.data = null;
                this.in = null;
            }
        }
    }

    private int bsR(int n) throws IOException {
        int bsLiveShadow = this.bsLive;
        int bsBuffShadow = this.bsBuff;
        if (bsLiveShadow < n) {
            InputStream inShadow = this.in;
            do {
                int thech = inShadow.read();
                if (thech < 0) {
                    throw new IOException("unexpected end of stream");
                }
                bsBuffShadow = (bsBuffShadow << 8) | thech;
                bsLiveShadow += 8;
            } while (bsLiveShadow < n);
            this.bsBuff = bsBuffShadow;
        }
        this.bsLive = bsLiveShadow - n;
        return (bsBuffShadow >> (bsLiveShadow - n)) & ((1 << n) - 1);
    }

    private boolean bsGetBit() throws IOException {
        int bsLiveShadow = this.bsLive;
        int bsBuffShadow = this.bsBuff;
        if (bsLiveShadow < 1) {
            int thech = this.in.read();
            if (thech < 0) {
                throw new IOException("unexpected end of stream");
            }
            bsBuffShadow = (bsBuffShadow << 8) | thech;
            bsLiveShadow += 8;
            this.bsBuff = bsBuffShadow;
        }
        this.bsLive = bsLiveShadow - 1;
        if (((bsBuffShadow >> (bsLiveShadow - 1)) & 1) != 0) {
            return true;
        }
        return false;
    }

    private char bsGetUByte() throws IOException {
        return (char) bsR(8);
    }

    private int bsGetInt() throws IOException {
        return bsR(8) | (((((bsR(8) << 8) | bsR(8)) << 8) | bsR(8)) << 8);
    }

    private static void hbCreateDecodeTables(int[] limit, int[] base, int[] perm, char[] length, int minLen, int maxLen, int alphaSize) {
        char i = minLen;
        int pp = 0;
        while (i <= maxLen) {
            int pp2 = pp;
            for (pp = 0; pp < alphaSize; pp++) {
                if (length[pp] == i) {
                    int pp3 = pp2 + 1;
                    perm[pp2] = pp;
                    pp2 = pp3;
                }
            }
            i++;
            pp = pp2;
        }
        int i2 = 23;
        while (true) {
            i2--;
            if (i2 <= 0) {
                break;
            }
            base[i2] = 0;
            limit[i2] = 0;
        }
        for (i2 = 0; i2 < alphaSize; i2++) {
            pp2 = length[i2] + 1;
            base[pp2] = base[pp2] + 1;
        }
        int b = base[0];
        for (i2 = 1; i2 < 23; i2++) {
            b += base[i2];
            base[i2] = b;
        }
        pp = minLen;
        b = 0;
        i2 = base[pp];
        while (pp <= maxLen) {
            pp2 = base[pp + 1];
            b += pp2 - i2;
            i2 = pp2;
            limit[pp] = b - 1;
            b <<= 1;
            pp++;
        }
        for (pp = minLen + 1; pp <= maxLen; pp++) {
            base[pp] = ((limit[pp - 1] + 1) << 1) - base[pp];
        }
    }

    private void recvDecodingTables() throws IOException {
        int i;
        int i16;
        int j;
        Data dataShadow = this.data;
        boolean[] inUse = dataShadow.inUse;
        byte[] pos = dataShadow.recvDecodingTables_pos;
        byte[] selector = dataShadow.selector;
        byte[] selectorMtf = dataShadow.selectorMtf;
        int inUse16 = 0;
        for (i = 0; i < 16; i++) {
            if (bsGetBit()) {
                inUse16 |= 1 << i;
            }
        }
        i = 256;
        while (true) {
            i--;
            if (i < 0) {
                break;
            }
            inUse[i] = false;
        }
        for (i = 0; i < 16; i++) {
            if (((1 << i) & inUse16) != 0) {
                i16 = i << 4;
                for (j = 0; j < 16; j++) {
                    if (bsGetBit()) {
                        inUse[i16 + j] = true;
                    }
                }
            }
        }
        makeMaps();
        i = r0.nInUse + 2;
        int nGroups = bsR(3);
        i16 = bsR(15);
        for (j = 0; j < i16; j++) {
            int j2 = 0;
            while (bsGetBit()) {
                j2++;
            }
            selectorMtf[j] = (byte) j2;
        }
        j = nGroups;
        while (true) {
            j--;
            if (j < 0) {
                break;
            }
            pos[j] = (byte) j;
        }
        for (j = 0; j < i16; j++) {
            j2 = selectorMtf[j] & 255;
            byte tmp = pos[j2];
            while (j2 > 0) {
                pos[j2] = pos[j2 - 1];
                j2--;
            }
            pos[0] = tmp;
            selector[j] = tmp;
        }
        char[][] len = dataShadow.temp_charArray2d;
        for (j2 = 0; j2 < nGroups; j2++) {
            int curr = bsR(5);
            char[] len_t = len[j2];
            int curr2 = curr;
            curr = 0;
            while (curr < i) {
                int curr3 = curr2;
                while (bsGetBit()) {
                    curr3 += bsGetBit() ? -1 : 1;
                }
                len_t[curr] = (char) curr3;
                curr++;
                curr2 = curr3;
            }
        }
        createHuffmanDecodingTables(i, nGroups);
    }

    private void createHuffmanDecodingTables(int alphaSize, int nGroups) {
        Data dataShadow = this.data;
        char[][] len = dataShadow.temp_charArray2d;
        int[] minLens = dataShadow.minLens;
        int[][] limit = dataShadow.limit;
        int[][] base = dataShadow.base;
        int[][] perm = dataShadow.perm;
        for (int t = 0; t < nGroups; t++) {
            int maxLen = 0;
            char[] len_t = len[t];
            int minLen = 32;
            int i = alphaSize;
            while (true) {
                i--;
                if (i < 0) {
                    break;
                }
                int minLen2 = len_t[i];
                if (minLen2 > maxLen) {
                    maxLen = minLen2;
                }
                if (minLen2 < minLen) {
                    minLen = minLen2;
                }
            }
            int minLen3 = minLen;
            hbCreateDecodeTables(limit[t], base[t], perm[t], len[t], minLen3, maxLen, alphaSize);
            minLens[t] = minLen3;
        }
    }

    private void getAndMoveToFrontDecode() throws IOException {
        BZip2CompressorInputStream bZip2CompressorInputStream;
        this.origPtr = bsR(24);
        recvDecodingTables();
        InputStream inShadow = this.in;
        Data dataShadow = this.data;
        byte[] ll8 = dataShadow.ll8;
        int[] unzftab = dataShadow.unzftab;
        byte[] selector = dataShadow.selector;
        byte[] seqToUnseq = dataShadow.seqToUnseq;
        char[] yy = dataShadow.getAndMoveToFrontDecode_yy;
        int[] minLens = dataShadow.minLens;
        int[][] limit = dataShadow.limit;
        int[][] base = dataShadow.base;
        int[][] perm = dataShadow.perm;
        int limitLast = this.blockSize100k * BZip2Constants.BASEBLOCKSIZE;
        int i = 256;
        while (true) {
            i--;
            if (i < 0) {
                break;
            }
            yy[i] = (char) i;
            unzftab[i] = 0;
        }
        int eob = bZip2CompressorInputStream.nInUse + 1;
        int nextSym = getAndMoveToFrontDecode0(0);
        int bsBuffShadow = bZip2CompressorInputStream.bsBuff;
        int bsLiveShadow = bZip2CompressorInputStream.bsLive;
        int zt = selector[0] & 255;
        int[] base_zt = base[zt];
        int[] limit_zt = limit[zt];
        int[] perm_zt = perm[zt];
        int minLens_zt = minLens[zt];
        int bsBuffShadow2 = bsBuffShadow;
        bsBuffShadow = 49;
        int bsLiveShadow2 = bsLiveShadow;
        zt = nextSym;
        nextSym = 0;
        i = -1;
        while (true) {
            bsLiveShadow = minLens_zt;
            int eob2;
            int nextSym2;
            int i2;
            byte[] selector2;
            int[] minLens2;
            int[][] limit2;
            if (zt != eob) {
                InputStream inShadow2;
                if (zt != 0) {
                    eob2 = eob;
                    if (zt == 1) {
                        nextSym2 = zt;
                        i2 = bsLiveShadow2;
                    } else {
                        i++;
                        if (i >= limitLast) {
                            i2 = bsLiveShadow2;
                            throw new IOException("block overrun");
                        }
                        int lastShadow;
                        i2 = bsLiveShadow2;
                        char tmp = yy[zt - 1];
                        bsLiveShadow2 = seqToUnseq[tmp] & 255;
                        unzftab[bsLiveShadow2] = unzftab[bsLiveShadow2] + 1;
                        ll8[i] = seqToUnseq[tmp];
                        if (zt <= 16) {
                            bsLiveShadow2 = zt - 1;
                            while (bsLiveShadow2 > 0) {
                                minLens_zt = bsLiveShadow2 - 1;
                                yy[bsLiveShadow2] = yy[minLens_zt];
                                bsLiveShadow2 = minLens_zt;
                            }
                            nextSym2 = zt;
                            lastShadow = i;
                            i = 0;
                        } else {
                            lastShadow = i;
                            i = 0;
                            System.arraycopy(yy, 0, yy, 1, zt - 1);
                        }
                        yy[i] = tmp;
                        if (bsBuffShadow == 0) {
                            nextSym++;
                            i = selector[nextSym] & 255;
                            int[] base_zt2 = base[i];
                            int[] limit_zt2 = limit[i];
                            base_zt = base_zt2;
                            limit_zt = limit_zt2;
                            perm_zt = perm[i];
                            minLens_zt = minLens[i];
                            bsBuffShadow = 49;
                        } else {
                            bsBuffShadow--;
                            minLens_zt = bsLiveShadow;
                        }
                        zt = minLens_zt;
                        i = i2;
                        while (i < zt) {
                            bsLiveShadow2 = inShadow.read();
                            if (bsLiveShadow2 >= 0) {
                                bsBuffShadow2 = (bsBuffShadow2 << 8) | bsLiveShadow2;
                                i += 8;
                            } else {
                                throw new IOException("unexpected end of stream");
                            }
                        }
                        eob = (bsBuffShadow2 >> (i - zt)) & ((1 << zt) - 1);
                        bsLiveShadow2 = i - zt;
                        while (eob > limit_zt[zt]) {
                            zt++;
                            while (bsLiveShadow2 < 1) {
                                i = inShadow.read();
                                if (i >= 0) {
                                    bsBuffShadow2 = (bsBuffShadow2 << 8) | i;
                                    bsLiveShadow2 += 8;
                                } else {
                                    throw new IOException("unexpected end of stream");
                                }
                            }
                            bsLiveShadow2--;
                            eob = (eob << 1) | ((bsBuffShadow2 >> bsLiveShadow2) & 1);
                            zt = zt;
                        }
                        zt = perm_zt[eob - base_zt[zt]];
                        eob = eob2;
                        i = lastShadow;
                    }
                } else {
                    nextSym2 = zt;
                    eob2 = eob;
                    i2 = bsLiveShadow2;
                }
                bsLiveShadow2 = -1;
                minLens_zt = bsLiveShadow;
                eob = nextSym2;
                zt = 1;
                while (true) {
                    int zt2;
                    if (eob != 0) {
                        if (eob != 1) {
                            break;
                        }
                        bsLiveShadow2 += zt << 1;
                    } else {
                        bsLiveShadow2 += zt;
                    }
                    if (bsBuffShadow == 0) {
                        nextSym++;
                        int groupPos = 49;
                        zt2 = selector[nextSym] & 255;
                        base_zt = base[zt2];
                        limit_zt = limit[zt2];
                        perm_zt = perm[zt2];
                        minLens_zt = minLens[zt2];
                        int i3 = zt2;
                        bsBuffShadow = groupPos;
                    } else {
                        bsBuffShadow--;
                    }
                    selector2 = selector;
                    zt2 = minLens_zt;
                    int bsLiveShadow3 = i2;
                    while (bsLiveShadow3 < zt2) {
                        bsLiveShadow = inShadow.read();
                        if (bsLiveShadow >= 0) {
                            bsBuffShadow2 = (bsBuffShadow2 << 8) | bsLiveShadow;
                            bsLiveShadow3 += 8;
                        } else {
                            throw new IOException("unexpected end of stream");
                        }
                    }
                    minLens2 = minLens;
                    limit2 = limit;
                    int zvec = (bsBuffShadow2 >> (bsLiveShadow3 - zt2)) & ((1 << zt2) - 1);
                    i2 = bsLiveShadow3 - zt2;
                    while (zvec > limit_zt[zt2]) {
                        zt2++;
                        bsLiveShadow3 = i2;
                        while (bsLiveShadow3 < 1) {
                            int thech = inShadow.read();
                            if (thech >= 0) {
                                bsBuffShadow2 = (bsBuffShadow2 << 8) | thech;
                                bsLiveShadow3 += 8;
                            } else {
                                throw new IOException("unexpected end of stream");
                            }
                        }
                        i2 = bsLiveShadow3 - 1;
                        zvec = (zvec << 1) | ((bsBuffShadow2 >> i2) & 1);
                        zt2 = zt2;
                        inShadow = inShadow;
                    }
                    inShadow2 = inShadow;
                    eob = perm_zt[zvec - base_zt[zt2]];
                    zt <<= 1;
                    selector = selector2;
                    minLens = minLens2;
                    limit = limit2;
                    inShadow = inShadow2;
                    bZip2CompressorInputStream = this;
                }
                inShadow2 = inShadow;
                selector2 = selector;
                minLens2 = minLens;
                limit2 = limit;
                inShadow = seqToUnseq[yy[0]];
                zt = inShadow & 255;
                unzftab[zt] = unzftab[zt] + (bsLiveShadow2 + 1);
                while (true) {
                    zt = bsLiveShadow2 - 1;
                    if (bsLiveShadow2 < 0) {
                        break;
                    }
                    i++;
                    ll8[i] = inShadow;
                    bsLiveShadow2 = zt;
                }
                if (i >= limitLast) {
                    throw new IOException("block overrun");
                }
                zt = eob;
                eob = eob2;
                bsLiveShadow2 = i2;
                selector = selector2;
                minLens = minLens2;
                limit = limit2;
                inShadow = inShadow2;
                bZip2CompressorInputStream = this;
            } else {
                nextSym2 = zt;
                selector2 = selector;
                minLens2 = minLens;
                limit2 = limit;
                eob2 = eob;
                i2 = bsLiveShadow2;
                this.last = i;
                this.bsLive = bsLiveShadow2;
                this.bsBuff = bsBuffShadow2;
                return;
            }
        }
    }

    private int getAndMoveToFrontDecode0(int groupNo) throws IOException {
        InputStream inShadow = this.in;
        Data dataShadow = this.data;
        int zt = dataShadow.selector[groupNo] & 255;
        int[] limit_zt = dataShadow.limit[zt];
        int zn = dataShadow.minLens[zt];
        int zvec = bsR(zn);
        int bsLiveShadow = this.bsLive;
        int bsBuffShadow = this.bsBuff;
        while (zvec > limit_zt[zn]) {
            zn++;
            while (bsLiveShadow < 1) {
                int thech = inShadow.read();
                if (thech >= 0) {
                    bsBuffShadow = (bsBuffShadow << 8) | thech;
                    bsLiveShadow += 8;
                } else {
                    throw new IOException("unexpected end of stream");
                }
            }
            bsLiveShadow--;
            zvec = (zvec << 1) | (1 & (bsBuffShadow >> bsLiveShadow));
        }
        this.bsLive = bsLiveShadow;
        this.bsBuff = bsBuffShadow;
        return dataShadow.perm[zt][zvec - dataShadow.base[zt][zn]];
    }

    private int setupBlock() throws IOException {
        if (this.currentState != 0) {
            if (this.data != null) {
                int i;
                int[] cftab = this.data.cftab;
                int[] tt = this.data.initTT(this.last + 1);
                byte[] ll8 = this.data.ll8;
                cftab[0] = 0;
                System.arraycopy(this.data.unzftab, 0, cftab, 1, 256);
                int c = cftab[0];
                for (i = 1; i <= 256; i++) {
                    c += cftab[i];
                    cftab[i] = c;
                }
                c = this.last;
                for (i = 0; i <= c; i++) {
                    int i2 = ll8[i] & 255;
                    int i3 = cftab[i2];
                    cftab[i2] = i3 + 1;
                    tt[i3] = i;
                }
                if (this.origPtr >= 0) {
                    if (this.origPtr < tt.length) {
                        this.su_tPos = tt[this.origPtr];
                        this.su_count = 0;
                        this.su_i2 = 0;
                        this.su_ch2 = 256;
                        if (!this.blockRandomised) {
                            return setupNoRandPartA();
                        }
                        this.su_rNToGo = 0;
                        this.su_rTPos = 0;
                        return setupRandPartA();
                    }
                }
                throw new IOException("stream corrupted");
            }
        }
        return -1;
    }

    private int setupRandPartA() throws IOException {
        if (this.su_i2 <= this.last) {
            int i;
            this.su_chPrev = this.su_ch2;
            int su_ch2Shadow = this.data.ll8[this.su_tPos] & 255;
            this.su_tPos = this.data.tt[this.su_tPos];
            int i2 = 0;
            if (this.su_rNToGo == 0) {
                this.su_rNToGo = Rand.rNums(this.su_rTPos) - 1;
                i = this.su_rTPos + 1;
                this.su_rTPos = i;
                if (i == 512) {
                    this.su_rTPos = 0;
                }
            } else {
                this.su_rNToGo--;
            }
            if (this.su_rNToGo == 1) {
                i2 = 1;
            }
            i = su_ch2Shadow ^ i2;
            su_ch2Shadow = i;
            this.su_ch2 = i;
            this.su_i2++;
            this.currentState = 3;
            this.crc.updateCRC(su_ch2Shadow);
            return su_ch2Shadow;
        }
        endBlock();
        initBlock();
        return setupBlock();
    }

    private int setupNoRandPartA() throws IOException {
        if (this.su_i2 <= this.last) {
            this.su_chPrev = this.su_ch2;
            int su_ch2Shadow = this.data.ll8[this.su_tPos] & 255;
            this.su_ch2 = su_ch2Shadow;
            this.su_tPos = this.data.tt[this.su_tPos];
            this.su_i2++;
            this.currentState = 6;
            this.crc.updateCRC(su_ch2Shadow);
            return su_ch2Shadow;
        }
        this.currentState = 5;
        endBlock();
        initBlock();
        return setupBlock();
    }

    private int setupRandPartB() throws IOException {
        if (this.su_ch2 != this.su_chPrev) {
            this.currentState = 2;
            this.su_count = 1;
            return setupRandPartA();
        }
        int i = this.su_count + 1;
        this.su_count = i;
        if (i >= 4) {
            this.su_z = (char) (this.data.ll8[this.su_tPos] & 255);
            this.su_tPos = this.data.tt[this.su_tPos];
            if (this.su_rNToGo == 0) {
                this.su_rNToGo = Rand.rNums(this.su_rTPos) - 1;
                i = this.su_rTPos + 1;
                this.su_rTPos = i;
                if (i == 512) {
                    this.su_rTPos = 0;
                }
            } else {
                this.su_rNToGo--;
            }
            this.su_j2 = 0;
            this.currentState = 4;
            if (this.su_rNToGo == 1) {
                this.su_z = (char) (this.su_z ^ 1);
            }
            return setupRandPartC();
        }
        this.currentState = 2;
        return setupRandPartA();
    }

    private int setupRandPartC() throws IOException {
        if (this.su_j2 < this.su_z) {
            this.crc.updateCRC(this.su_ch2);
            this.su_j2++;
            return this.su_ch2;
        }
        this.currentState = 2;
        this.su_i2++;
        this.su_count = 0;
        return setupRandPartA();
    }

    private int setupNoRandPartB() throws IOException {
        if (this.su_ch2 != this.su_chPrev) {
            this.su_count = 1;
            return setupNoRandPartA();
        }
        int i = this.su_count + 1;
        this.su_count = i;
        if (i < 4) {
            return setupNoRandPartA();
        }
        this.su_z = (char) (this.data.ll8[this.su_tPos] & 255);
        this.su_tPos = this.data.tt[this.su_tPos];
        this.su_j2 = 0;
        return setupNoRandPartC();
    }

    private int setupNoRandPartC() throws IOException {
        if (this.su_j2 < this.su_z) {
            int su_ch2Shadow = this.su_ch2;
            this.crc.updateCRC(su_ch2Shadow);
            this.su_j2++;
            this.currentState = 7;
            return su_ch2Shadow;
        }
        this.su_i2++;
        this.su_count = 0;
        return setupNoRandPartA();
    }

    public static boolean matches(byte[] signature, int length) {
        if (length >= 3 && signature[0] == GeneralMidiConstants.TENOR_SAX && signature[1] == GeneralMidiConstants.PAD_2_POLYSYNTH && signature[2] == GeneralMidiConstants.SIT_R) {
            return true;
        }
        return false;
    }
}
