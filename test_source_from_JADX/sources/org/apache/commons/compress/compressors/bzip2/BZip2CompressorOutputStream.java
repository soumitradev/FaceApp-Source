package org.apache.commons.compress.compressors.bzip2;

import android.support.v4.view.InputDeviceCompat;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Array;
import org.apache.commons.compress.compressors.CompressorOutputStream;

public class BZip2CompressorOutputStream extends CompressorOutputStream implements BZip2Constants {
    private static final int GREATER_ICOST = 15;
    private static final int LESSER_ICOST = 0;
    public static final int MAX_BLOCKSIZE = 9;
    public static final int MIN_BLOCKSIZE = 1;
    private final int allowableBlockSize;
    private int blockCRC;
    private final int blockSize100k;
    private BlockSort blockSorter;
    private int bsBuff;
    private int bsLive;
    private int combinedCRC;
    private final CRC crc;
    private int currentChar;
    private Data data;
    private int last;
    private int nInUse;
    private int nMTF;
    private OutputStream out;
    private int runLength;

    static final class Data {
        final byte[] block;
        final int[] fmap;
        final byte[] generateMTFValues_yy = new byte[256];
        final int[] heap = new int[260];
        final boolean[] inUse = new boolean[256];
        final int[] mtfFreq = new int[258];
        int origPtr;
        final int[] parent = new int[GL20.GL_GREATER];
        final byte[] selector = new byte[BZip2Constants.MAX_SELECTORS];
        final byte[] selectorMtf = new byte[BZip2Constants.MAX_SELECTORS];
        final byte[] sendMTFValues2_pos = new byte[6];
        final int[][] sendMTFValues_code = ((int[][]) Array.newInstance(int.class, new int[]{6, 258}));
        final short[] sendMTFValues_cost = new short[6];
        final int[] sendMTFValues_fave = new int[6];
        final byte[][] sendMTFValues_len = ((byte[][]) Array.newInstance(byte.class, new int[]{6, 258}));
        final int[][] sendMTFValues_rfreq = ((int[][]) Array.newInstance(int.class, new int[]{6, 258}));
        final boolean[] sentMTFValues4_inUse16 = new boolean[16];
        final char[] sfmap;
        final byte[] unseqToSeq = new byte[256];
        final int[] weight = new int[GL20.GL_GREATER];

        Data(int blockSize100k) {
            int n = BZip2Constants.BASEBLOCKSIZE * blockSize100k;
            this.block = new byte[((n + 1) + 20)];
            this.fmap = new int[n];
            this.sfmap = new char[(n * 2)];
        }
    }

    private static void hbMakeCodeLengths(byte[] len, int[] freq, Data dat, int alphaSize, int maxLen) {
        int i;
        Data data = dat;
        int i2 = alphaSize;
        int[] heap = data.heap;
        int[] weight = data.weight;
        int[] parent = data.parent;
        int i3 = i2;
        while (true) {
            int i4 = -1;
            i3--;
            i = 1;
            if (i3 < 0) {
                break;
            }
            i4 = i3 + 1;
            if (freq[i3] != 0) {
                i = freq[i3];
            }
            weight[i4] = i << 8;
        }
        boolean tooLong = true;
        while (tooLong) {
            int i5;
            int i6;
            tooLong = false;
            int nNodes = i2;
            heap[0] = 0;
            weight[0] = 0;
            parent[0] = -2;
            int nHeap = 0;
            for (i5 = 1; i5 <= i2; i5++) {
                parent[i5] = i4;
                nHeap++;
                heap[nHeap] = i5;
                int zz = nHeap;
                int tmp = heap[zz];
                while (weight[tmp] < weight[heap[zz >> 1]]) {
                    heap[zz] = heap[zz >> 1];
                    zz >>= 1;
                }
                heap[zz] = tmp;
            }
            while (nHeap > i) {
                int nHeap2;
                int tmp2;
                int nHeap3;
                i5 = heap[i];
                heap[i] = heap[nHeap];
                zz = nHeap - 1;
                tmp = 1;
                int tmp3 = heap[i];
                while (true) {
                    nHeap = tmp << 1;
                    if (nHeap > zz) {
                        break;
                    }
                    if (nHeap < zz && weight[heap[nHeap + 1]] < weight[heap[nHeap]]) {
                        nHeap++;
                    }
                    if (weight[tmp3] < weight[heap[nHeap]]) {
                        break;
                    }
                    heap[tmp] = heap[nHeap];
                    tmp = nHeap;
                    data = dat;
                    i = 1;
                    heap[tmp] = tmp3;
                    i4 = heap[i];
                    heap[i] = heap[zz];
                    nHeap2 = zz - 1;
                    zz = 1;
                    tmp2 = heap[i];
                    while (true) {
                        nHeap = zz << 1;
                        if (nHeap > nHeap2) {
                            break;
                        }
                        if (nHeap < nHeap2 && weight[heap[nHeap + 1]] < weight[heap[nHeap]]) {
                            nHeap++;
                        }
                        if (weight[tmp2] < weight[heap[nHeap]]) {
                            break;
                        }
                        heap[zz] = heap[nHeap];
                        zz = nHeap;
                        data = dat;
                        heap[zz] = tmp2;
                        nNodes++;
                        parent[i4] = nNodes;
                        parent[i5] = nNodes;
                        tmp = weight[i5];
                        tmp3 = weight[i4];
                        weight[nNodes] = (((tmp & 255) <= (tmp3 & 255) ? tmp & 255 : tmp3 & 255) + 1) | ((tmp & InputDeviceCompat.SOURCE_ANY) + (tmp3 & InputDeviceCompat.SOURCE_ANY));
                        parent[nNodes] = -1;
                        nHeap3 = nHeap2 + 1;
                        heap[nHeap3] = nNodes;
                        zz = nHeap3;
                        i = heap[zz];
                        nHeap2 = weight[i];
                        while (nHeap2 < weight[heap[zz >> 1]]) {
                            heap[zz] = heap[zz >> 1];
                            zz >>= 1;
                        }
                        heap[zz] = i;
                        nHeap = nHeap3;
                        nHeap3 = dat;
                        i = 1;
                    }
                    heap[zz] = tmp2;
                    nNodes++;
                    parent[i4] = nNodes;
                    parent[i5] = nNodes;
                    tmp = weight[i5];
                    tmp3 = weight[i4];
                    if ((tmp & 255) <= (tmp3 & 255)) {
                    }
                    weight[nNodes] = (((tmp & 255) <= (tmp3 & 255) ? tmp & 255 : tmp3 & 255) + 1) | ((tmp & InputDeviceCompat.SOURCE_ANY) + (tmp3 & InputDeviceCompat.SOURCE_ANY));
                    parent[nNodes] = -1;
                    nHeap3 = nHeap2 + 1;
                    heap[nHeap3] = nNodes;
                    zz = nHeap3;
                    i = heap[zz];
                    nHeap2 = weight[i];
                    while (nHeap2 < weight[heap[zz >> 1]]) {
                        heap[zz] = heap[zz >> 1];
                        zz >>= 1;
                    }
                    heap[zz] = i;
                    nHeap = nHeap3;
                    nHeap3 = dat;
                    i = 1;
                }
                heap[tmp] = tmp3;
                i4 = heap[i];
                heap[i] = heap[zz];
                nHeap2 = zz - 1;
                zz = 1;
                tmp2 = heap[i];
                while (true) {
                    nHeap = zz << 1;
                    if (nHeap > nHeap2) {
                        break;
                    }
                    nHeap++;
                    if (weight[tmp2] < weight[heap[nHeap]]) {
                        break;
                    }
                    heap[zz] = heap[nHeap];
                    zz = nHeap;
                    data = dat;
                    heap[zz] = tmp2;
                    nNodes++;
                    parent[i4] = nNodes;
                    parent[i5] = nNodes;
                    tmp = weight[i5];
                    tmp3 = weight[i4];
                    if ((tmp & 255) <= (tmp3 & 255)) {
                    }
                    weight[nNodes] = (((tmp & 255) <= (tmp3 & 255) ? tmp & 255 : tmp3 & 255) + 1) | ((tmp & InputDeviceCompat.SOURCE_ANY) + (tmp3 & InputDeviceCompat.SOURCE_ANY));
                    parent[nNodes] = -1;
                    nHeap3 = nHeap2 + 1;
                    heap[nHeap3] = nNodes;
                    zz = nHeap3;
                    i = heap[zz];
                    nHeap2 = weight[i];
                    while (nHeap2 < weight[heap[zz >> 1]]) {
                        heap[zz] = heap[zz >> 1];
                        zz >>= 1;
                    }
                    heap[zz] = i;
                    nHeap = nHeap3;
                    nHeap3 = dat;
                    i = 1;
                }
                heap[zz] = tmp2;
                nNodes++;
                parent[i4] = nNodes;
                parent[i5] = nNodes;
                tmp = weight[i5];
                tmp3 = weight[i4];
                if ((tmp & 255) <= (tmp3 & 255)) {
                }
                weight[nNodes] = (((tmp & 255) <= (tmp3 & 255) ? tmp & 255 : tmp3 & 255) + 1) | ((tmp & InputDeviceCompat.SOURCE_ANY) + (tmp3 & InputDeviceCompat.SOURCE_ANY));
                parent[nNodes] = -1;
                nHeap3 = nHeap2 + 1;
                heap[nHeap3] = nNodes;
                zz = nHeap3;
                i = heap[zz];
                nHeap2 = weight[i];
                while (nHeap2 < weight[heap[zz >> 1]]) {
                    heap[zz] = heap[zz >> 1];
                    zz >>= 1;
                }
                heap[zz] = i;
                nHeap = nHeap3;
                nHeap3 = dat;
                i = 1;
            }
            for (i6 = 1; i6 <= i2; i6++) {
                i4 = 0;
                nHeap3 = i6;
                while (true) {
                    i = parent[nHeap3];
                    i5 = i;
                    if (i < 0) {
                        break;
                    }
                    nHeap3 = i5;
                    i4++;
                }
                len[i6 - 1] = (byte) i4;
                if (i4 > maxLen) {
                    tooLong = true;
                }
            }
            i = maxLen;
            if (tooLong) {
                for (i6 = 1; i6 < i2; i6++) {
                    weight[i6] = (((weight[i6] >> 8) >> 1) + 1) << 8;
                }
            }
            data = dat;
            i4 = -1;
            i = 1;
        }
        i = maxLen;
    }

    public static int chooseBlockSize(long inputLength) {
        return inputLength > 0 ? (int) Math.min((inputLength / 132000) + 1, 9) : 9;
    }

    public BZip2CompressorOutputStream(OutputStream out) throws IOException {
        this(out, 9);
    }

    public BZip2CompressorOutputStream(OutputStream out, int blockSize) throws IOException {
        this.crc = new CRC();
        this.currentChar = -1;
        this.runLength = 0;
        StringBuilder stringBuilder;
        if (blockSize < 1) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("blockSize(");
            stringBuilder.append(blockSize);
            stringBuilder.append(") < 1");
            throw new IllegalArgumentException(stringBuilder.toString());
        } else if (blockSize > 9) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("blockSize(");
            stringBuilder.append(blockSize);
            stringBuilder.append(") > 9");
            throw new IllegalArgumentException(stringBuilder.toString());
        } else {
            this.blockSize100k = blockSize;
            this.out = out;
            this.allowableBlockSize = (this.blockSize100k * BZip2Constants.BASEBLOCKSIZE) - 20;
            init();
        }
    }

    public void write(int b) throws IOException {
        if (this.out != null) {
            write0(b);
            return;
        }
        throw new IOException("closed");
    }

    private void writeRun() throws IOException {
        int lastShadow = this.last;
        if (lastShadow < this.allowableBlockSize) {
            int currentCharShadow = this.currentChar;
            Data dataShadow = this.data;
            dataShadow.inUse[currentCharShadow] = true;
            byte ch = (byte) currentCharShadow;
            int runLengthShadow = this.runLength;
            this.crc.updateCRC(currentCharShadow, runLengthShadow);
            byte[] block;
            switch (runLengthShadow) {
                case 1:
                    dataShadow.block[lastShadow + 2] = ch;
                    this.last = lastShadow + 1;
                    break;
                case 2:
                    dataShadow.block[lastShadow + 2] = ch;
                    dataShadow.block[lastShadow + 3] = ch;
                    this.last = lastShadow + 2;
                    break;
                case 3:
                    block = dataShadow.block;
                    block[lastShadow + 2] = ch;
                    block[lastShadow + 3] = ch;
                    block[lastShadow + 4] = ch;
                    this.last = lastShadow + 3;
                    break;
                default:
                    runLengthShadow -= 4;
                    dataShadow.inUse[runLengthShadow] = true;
                    block = dataShadow.block;
                    block[lastShadow + 2] = ch;
                    block[lastShadow + 3] = ch;
                    block[lastShadow + 4] = ch;
                    block[lastShadow + 5] = ch;
                    block[lastShadow + 6] = (byte) runLengthShadow;
                    this.last = lastShadow + 5;
                    break;
            }
            return;
        }
        endBlock();
        initBlock();
        writeRun();
    }

    protected void finalize() throws Throwable {
        finish();
        super.finalize();
    }

    public void finish() throws IOException {
        if (this.out != null) {
            try {
                if (this.runLength > 0) {
                    writeRun();
                }
                this.currentChar = -1;
                endBlock();
                endCompression();
            } finally {
                this.out = null;
                this.data = null;
                this.blockSorter = null;
            }
        }
    }

    public void close() throws IOException {
        if (this.out != null) {
            OutputStream outShadow = this.out;
            finish();
            outShadow.close();
        }
    }

    public void flush() throws IOException {
        OutputStream outShadow = this.out;
        if (outShadow != null) {
            outShadow.flush();
        }
    }

    private void init() throws IOException {
        bsPutUByte(66);
        bsPutUByte(90);
        this.data = new Data(this.blockSize100k);
        this.blockSorter = new BlockSort(this.data);
        bsPutUByte(104);
        bsPutUByte(this.blockSize100k + 48);
        this.combinedCRC = 0;
        initBlock();
    }

    private void initBlock() {
        this.crc.initialiseCRC();
        this.last = -1;
        boolean[] inUse = this.data.inUse;
        int i = 256;
        while (true) {
            i--;
            if (i >= 0) {
                inUse[i] = false;
            } else {
                return;
            }
        }
    }

    private void endBlock() throws IOException {
        this.blockCRC = this.crc.getFinalCRC();
        this.combinedCRC = (this.combinedCRC << 1) | (this.combinedCRC >>> 31);
        this.combinedCRC ^= this.blockCRC;
        if (this.last != -1) {
            blockSort();
            bsPutUByte(49);
            bsPutUByte(65);
            bsPutUByte(89);
            bsPutUByte(38);
            bsPutUByte(83);
            bsPutUByte(89);
            bsPutInt(this.blockCRC);
            bsW(1, 0);
            moveToFrontCodeAndSend();
        }
    }

    private void endCompression() throws IOException {
        bsPutUByte(23);
        bsPutUByte(114);
        bsPutUByte(69);
        bsPutUByte(56);
        bsPutUByte(80);
        bsPutUByte(144);
        bsPutInt(this.combinedCRC);
        bsFinishedWithStream();
    }

    public final int getBlockSize() {
        return this.blockSize100k;
    }

    public void write(byte[] buf, int offs, int len) throws IOException {
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
        } else if (offs + len > buf.length) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("offs(");
            stringBuilder.append(offs);
            stringBuilder.append(") + len(");
            stringBuilder.append(len);
            stringBuilder.append(") > buf.length(");
            stringBuilder.append(buf.length);
            stringBuilder.append(").");
            throw new IndexOutOfBoundsException(stringBuilder.toString());
        } else if (this.out == null) {
            throw new IOException("stream closed");
        } else {
            int hi = offs + len;
            while (offs < hi) {
                int offs2 = offs + 1;
                write0(buf[offs]);
                offs = offs2;
            }
        }
    }

    private void write0(int b) throws IOException {
        if (this.currentChar != -1) {
            b &= 255;
            if (this.currentChar == b) {
                int i = this.runLength + 1;
                this.runLength = i;
                if (i > Keys.F11) {
                    writeRun();
                    this.currentChar = -1;
                    this.runLength = 0;
                    return;
                }
                return;
            }
            writeRun();
            this.runLength = 1;
            this.currentChar = b;
            return;
        }
        this.currentChar = b & 255;
        this.runLength++;
    }

    private static void hbAssignCodes(int[] code, byte[] length, int minLen, int maxLen, int alphaSize) {
        int vec = 0;
        for (int n = minLen; n <= maxLen; n++) {
            for (int i = 0; i < alphaSize; i++) {
                if ((length[i] & 255) == n) {
                    code[i] = vec;
                    vec++;
                }
            }
            vec <<= 1;
        }
    }

    private void bsFinishedWithStream() throws IOException {
        while (this.bsLive > 0) {
            this.out.write(this.bsBuff >> 24);
            this.bsBuff <<= 8;
            this.bsLive -= 8;
        }
    }

    private void bsW(int n, int v) throws IOException {
        OutputStream outShadow = this.out;
        int bsLiveShadow = this.bsLive;
        int bsBuffShadow = this.bsBuff;
        while (bsLiveShadow >= 8) {
            outShadow.write(bsBuffShadow >> 24);
            bsBuffShadow <<= 8;
            bsLiveShadow -= 8;
        }
        this.bsBuff = (v << ((32 - bsLiveShadow) - n)) | bsBuffShadow;
        this.bsLive = bsLiveShadow + n;
    }

    private void bsPutUByte(int c) throws IOException {
        bsW(8, c);
    }

    private void bsPutInt(int u) throws IOException {
        bsW(8, (u >> 24) & 255);
        bsW(8, (u >> 16) & 255);
        bsW(8, (u >> 8) & 255);
        bsW(8, u & 255);
    }

    private void sendMTFValues() throws IOException {
        byte[][] len = this.data.sendMTFValues_len;
        int nGroups = 2;
        int alphaSize = this.nInUse + 2;
        int t = 6;
        while (true) {
            t--;
            if (t < 0) {
                break;
            }
            byte[] len_t = len[t];
            int v = alphaSize;
            while (true) {
                v--;
                if (v < 0) {
                    break;
                }
                len_t[v] = (byte) 15;
            }
        }
        if (this.nMTF >= 200) {
            nGroups = this.nMTF < SettingsJsonConstants.ANALYTICS_FLUSH_INTERVAL_SECS_DEFAULT ? 3 : this.nMTF < 1200 ? 4 : this.nMTF < 2400 ? 5 : 6;
        }
        sendMTFValues0(nGroups, alphaSize);
        int nSelectors = sendMTFValues1(nGroups, alphaSize);
        sendMTFValues2(nGroups, nSelectors);
        sendMTFValues3(nGroups, alphaSize);
        sendMTFValues4();
        sendMTFValues5(nGroups, nSelectors);
        sendMTFValues6(nGroups, alphaSize);
        sendMTFValues7();
    }

    private void sendMTFValues0(int nGroups, int alphaSize) {
        byte[][] len = this.data.sendMTFValues_len;
        int[] mtfFreq = this.data.mtfFreq;
        int gs = 0;
        int remF = this.nMTF;
        int nPart = nGroups;
        while (nPart > 0) {
            int tFreq = remF / nPart;
            int ge = gs - 1;
            int aFreq = 0;
            int a = alphaSize - 1;
            while (aFreq < tFreq && ge < a) {
                ge++;
                aFreq += mtfFreq[ge];
            }
            if (!(ge <= gs || nPart == nGroups || nPart == 1 || (1 & (nGroups - nPart)) == 0)) {
                aFreq -= mtfFreq[ge];
                ge--;
            }
            byte[] len_np = len[nPart - 1];
            int v = alphaSize;
            while (true) {
                v--;
                if (v < 0) {
                    break;
                } else if (v < gs || v > ge) {
                    len_np[v] = (byte) 15;
                } else {
                    len_np[v] = (byte) 0;
                }
            }
            gs = ge + 1;
            remF -= aFreq;
            nPart--;
        }
    }

    private int sendMTFValues1(int nGroups, int alphaSize) {
        int i = nGroups;
        Data dataShadow = this.data;
        int[][] rfreq = dataShadow.sendMTFValues_rfreq;
        int[] fave = dataShadow.sendMTFValues_fave;
        short[] cost = dataShadow.sendMTFValues_cost;
        char[] sfmap = dataShadow.sfmap;
        byte[] selector = dataShadow.selector;
        byte[][] len = dataShadow.sendMTFValues_len;
        int i2 = 0;
        byte[] len_0 = len[0];
        byte[] len_1 = len[1];
        byte[] len_2 = len[2];
        byte[] len_3 = len[3];
        int i3 = 4;
        byte[] len_4 = len[4];
        byte[] len_5 = len[5];
        int nMTFShadow = this.nMTF;
        int nSelectors = 0;
        int iter = 0;
        while (true) {
            int iter2 = iter;
            Data dataShadow2;
            byte[] len_02;
            short[] cost2;
            int i4;
            if (iter2 < i3) {
                BZip2CompressorOutputStream bZip2CompressorOutputStream;
                iter = i;
                while (true) {
                    iter--;
                    if (iter < 0) {
                        break;
                    }
                    fave[iter] = i2;
                    int[] rfreqt = rfreq[iter];
                    int i5 = alphaSize;
                    while (true) {
                        i5--;
                        if (i5 < 0) {
                            break;
                        }
                        rfreqt[i5] = i2;
                    }
                }
                nSelectors = 0;
                iter = 0;
                while (true) {
                    i2 = iter;
                    if (i2 >= bZip2CompressorOutputStream.nMTF) {
                        break;
                    }
                    int gs;
                    int t;
                    int cost_t;
                    dataShadow2 = dataShadow;
                    dataShadow = Math.min((i2 + 50) - 1, nMTFShadow - 1);
                    if (i != 6) {
                        gs = i2;
                        t = i;
                        while (true) {
                            t--;
                            if (t < 0) {
                                break;
                            }
                            cost[t] = (short) 0;
                        }
                        t = gs;
                        while (true) {
                            i2 = t;
                            if (i2 > dataShadow) {
                                break;
                            }
                            t = sfmap[i2];
                            int t2 = i;
                            while (true) {
                                t2--;
                                if (t2 < 0) {
                                    break;
                                }
                                cost[t2] = (short) (cost[t2] + (len[t2][t] & 255));
                            }
                            t = i2 + 1;
                        }
                    } else {
                        short cost5 = (short) 0;
                        short cost4 = (short) 0;
                        short cost3 = (short) 0;
                        short cost22 = (short) 0;
                        short cost1 = (short) 0;
                        short cost0 = (short) 0;
                        i3 = i2;
                        while (i3 <= dataShadow) {
                            int icv = sfmap[i3];
                            i3++;
                            cost5 = (short) (cost5 + (len_5[icv] & 255));
                            i2 = i2;
                            cost0 = (short) (cost0 + (len_0[icv] & 255));
                            cost1 = (short) (cost1 + (len_1[icv] & 255));
                            cost22 = (short) (cost22 + (len_2[icv] & 255));
                            cost3 = (short) (cost3 + (len_3[icv] & 255));
                            cost4 = (short) (cost4 + (len_4[icv] & 255));
                        }
                        gs = i2;
                        cost[0] = cost0;
                        cost[1] = cost1;
                        cost[2] = cost22;
                        cost[3] = cost3;
                        cost[4] = cost4;
                        cost[5] = cost5;
                    }
                    i2 = -1;
                    i3 = i;
                    t = 999999999;
                    while (true) {
                        int bc = t;
                        i3--;
                        if (i3 < 0) {
                            break;
                        }
                        len_02 = len_0;
                        cost_t = cost[i3];
                        cost2 = cost;
                        cost = bc;
                        if (cost_t < cost) {
                            cost = cost_t;
                            i2 = i3;
                        }
                        t = cost;
                        len_0 = len_02;
                        cost = cost2;
                    }
                    cost2 = cost;
                    len_02 = len_0;
                    fave[i2] = fave[i2] + 1;
                    selector[nSelectors] = (byte) i2;
                    nSelectors++;
                    int[] rfreq_bt = rfreq[i2];
                    for (cost_t = gs; cost_t <= dataShadow; cost_t++) {
                        char c = sfmap[cost_t];
                        rfreq_bt[c] = rfreq_bt[c] + 1;
                    }
                    iter = dataShadow + 1;
                    dataShadow = dataShadow2;
                    len_0 = len_02;
                    cost = cost2;
                }
                dataShadow2 = dataShadow;
                cost2 = cost;
                len_02 = len_0;
                int t3 = 0;
                while (t3 < i) {
                    hbMakeCodeLengths(len[t3], rfreq[t3], bZip2CompressorOutputStream.data, alphaSize, 20);
                    t3++;
                    bZip2CompressorOutputStream = this;
                }
                i4 = alphaSize;
                iter = iter2 + 1;
                dataShadow = dataShadow2;
                len_0 = len_02;
                cost = cost2;
                bZip2CompressorOutputStream = this;
                i2 = 0;
                i3 = 4;
            } else {
                i4 = alphaSize;
                dataShadow2 = dataShadow;
                cost2 = cost;
                len_02 = len_0;
                return nSelectors;
            }
        }
    }

    private void sendMTFValues2(int nGroups, int nSelectors) {
        Data dataShadow = this.data;
        byte[] pos = dataShadow.sendMTFValues2_pos;
        int i = nGroups;
        while (true) {
            i--;
            if (i < 0) {
                break;
            }
            pos[i] = (byte) i;
        }
        for (int i2 = 0; i2 < nSelectors; i2++) {
            byte ll_i = dataShadow.selector[i2];
            byte tmp = pos[0];
            int j = 0;
            while (ll_i != tmp) {
                j++;
                byte tmp2 = tmp;
                tmp = pos[j];
                pos[j] = tmp2;
            }
            pos[0] = tmp;
            dataShadow.selectorMtf[i2] = (byte) j;
        }
    }

    private void sendMTFValues3(int nGroups, int alphaSize) {
        int[][] code = this.data.sendMTFValues_code;
        byte[][] len = this.data.sendMTFValues_len;
        for (int t = 0; t < nGroups; t++) {
            int maxLen = 0;
            byte[] len_t = len[t];
            int minLen = 32;
            int i = alphaSize;
            while (true) {
                i--;
                if (i < 0) {
                    break;
                }
                int l = len_t[i] & 255;
                if (l > maxLen) {
                    maxLen = l;
                }
                if (l < minLen) {
                    minLen = l;
                }
            }
            hbAssignCodes(code[t], len[t], minLen, maxLen, alphaSize);
        }
    }

    private void sendMTFValues4() throws IOException {
        int j;
        boolean[] inUse = this.data.inUse;
        boolean[] inUse16 = this.data.sentMTFValues4_inUse16;
        int i = 16;
        while (true) {
            i--;
            if (i < 0) {
                break;
            }
            inUse16[i] = false;
            int i16 = i * 16;
            j = 16;
            while (true) {
                j--;
                if (j < 0) {
                    break;
                } else if (inUse[i16 + j]) {
                    inUse16[i] = true;
                }
            }
        }
        for (i = 0; i < 16; i++) {
            bsW(1, inUse16[i]);
        }
        OutputStream outShadow = this.out;
        j = this.bsLive;
        int bsBuffShadow = this.bsBuff;
        int bsLiveShadow = j;
        for (j = 0; j < 16; j++) {
            if (inUse16[j]) {
                int i162 = j * 16;
                int bsBuffShadow2 = bsBuffShadow;
                for (bsBuffShadow = 0; bsBuffShadow < 16; bsBuffShadow++) {
                    while (bsLiveShadow >= 8) {
                        outShadow.write(bsBuffShadow2 >> 24);
                        bsBuffShadow2 <<= 8;
                        bsLiveShadow -= 8;
                    }
                    if (inUse[i162 + bsBuffShadow]) {
                        bsBuffShadow2 |= 1 << ((32 - bsLiveShadow) - 1);
                    }
                    bsLiveShadow++;
                }
                bsBuffShadow = bsBuffShadow2;
            }
        }
        this.bsBuff = bsBuffShadow;
        this.bsLive = bsLiveShadow;
    }

    private void sendMTFValues5(int nGroups, int nSelectors) throws IOException {
        bsW(3, nGroups);
        bsW(15, nSelectors);
        OutputStream outShadow = this.out;
        byte[] selectorMtf = this.data.selectorMtf;
        int bsLiveShadow = this.bsLive;
        int bsBuffShadow = this.bsBuff;
        for (int i = 0; i < nSelectors; i++) {
            int hj = selectorMtf[i] & 255;
            for (int j = 0; j < hj; j++) {
                while (bsLiveShadow >= 8) {
                    outShadow.write(bsBuffShadow >> 24);
                    bsBuffShadow <<= 8;
                    bsLiveShadow -= 8;
                }
                bsBuffShadow |= 1 << ((32 - bsLiveShadow) - 1);
                bsLiveShadow++;
            }
            while (bsLiveShadow >= 8) {
                outShadow.write(bsBuffShadow >> 24);
                bsBuffShadow <<= 8;
                bsLiveShadow -= 8;
            }
            bsLiveShadow++;
        }
        this.bsBuff = bsBuffShadow;
        this.bsLive = bsLiveShadow;
    }

    private void sendMTFValues6(int nGroups, int alphaSize) throws IOException {
        byte[][] len = this.data.sendMTFValues_len;
        OutputStream outShadow = this.out;
        int bsLiveShadow = this.bsLive;
        int bsBuffShadow = this.bsBuff;
        int bsLiveShadow2 = bsLiveShadow;
        bsLiveShadow = 0;
        while (bsLiveShadow < nGroups) {
            byte[] len_t = len[bsLiveShadow];
            int curr = len_t[0] & 255;
            while (bsLiveShadow2 >= 8) {
                outShadow.write(bsBuffShadow >> 24);
                bsBuffShadow <<= 8;
                bsLiveShadow2 -= 8;
            }
            bsLiveShadow2 += 5;
            int bsBuffShadow2 = bsBuffShadow | (curr << ((32 - bsLiveShadow2) - 5));
            for (bsBuffShadow = 0; bsBuffShadow < alphaSize; bsBuffShadow++) {
                int lti = len_t[bsBuffShadow] & 255;
                while (curr < lti) {
                    while (bsLiveShadow2 >= 8) {
                        outShadow.write(bsBuffShadow2 >> 24);
                        bsBuffShadow2 <<= 8;
                        bsLiveShadow2 -= 8;
                    }
                    bsBuffShadow2 |= 2 << ((32 - bsLiveShadow2) - 2);
                    bsLiveShadow2 += 2;
                    curr++;
                }
                while (curr > lti) {
                    while (bsLiveShadow2 >= 8) {
                        outShadow.write(bsBuffShadow2 >> 24);
                        bsBuffShadow2 <<= 8;
                        bsLiveShadow2 -= 8;
                    }
                    bsBuffShadow2 |= 3 << ((32 - bsLiveShadow2) - 2);
                    bsLiveShadow2 += 2;
                    curr--;
                }
                while (bsLiveShadow2 >= 8) {
                    outShadow.write(bsBuffShadow2 >> 24);
                    bsBuffShadow2 <<= 8;
                    bsLiveShadow2 -= 8;
                }
                bsLiveShadow2++;
            }
            bsLiveShadow++;
            bsBuffShadow = bsBuffShadow2;
        }
        int i = alphaSize;
        r0.bsBuff = bsBuffShadow;
        r0.bsLive = bsLiveShadow2;
    }

    private void sendMTFValues7() throws IOException {
        Data dataShadow = this.data;
        byte[][] len = dataShadow.sendMTFValues_len;
        int[][] code = dataShadow.sendMTFValues_code;
        OutputStream outShadow = this.out;
        byte[] selector = dataShadow.selector;
        char[] sfmap = dataShadow.sfmap;
        int nMTFShadow = this.nMTF;
        int selCtr = 0;
        int bsLiveShadow = this.bsLive;
        int bsBuffShadow = this.bsBuff;
        int gs = 0;
        while (gs < nMTFShadow) {
            int ge = Math.min((gs + 50) - 1, nMTFShadow - 1);
            int selector_selCtr = selector[selCtr] & 255;
            int[] code_selCtr = code[selector_selCtr];
            byte[] len_selCtr = len[selector_selCtr];
            while (gs <= ge) {
                Data dataShadow2;
                int sfmap_i = sfmap[gs];
                while (true) {
                    dataShadow2 = dataShadow;
                    if (bsLiveShadow < 8) {
                        break;
                    }
                    outShadow.write(bsBuffShadow >> 24);
                    bsBuffShadow <<= 8;
                    bsLiveShadow -= 8;
                    dataShadow = dataShadow2;
                }
                dataShadow = len_selCtr[sfmap_i] & 255;
                bsBuffShadow |= code_selCtr[sfmap_i] << ((32 - bsLiveShadow) - dataShadow);
                bsLiveShadow += dataShadow;
                gs++;
                dataShadow = dataShadow2;
            }
            gs = ge + 1;
            selCtr++;
        }
        r0.bsBuff = bsBuffShadow;
        r0.bsLive = bsLiveShadow;
    }

    private void moveToFrontCodeAndSend() throws IOException {
        bsW(24, this.data.origPtr);
        generateMTFValues();
        sendMTFValues();
    }

    private void blockSort() {
        this.blockSorter.blockSort(this.data, this.last);
    }

    private void generateMTFValues() {
        int i;
        int i2;
        int lastShadow = this.last;
        Data dataShadow = this.data;
        boolean[] inUse = dataShadow.inUse;
        byte[] block = dataShadow.block;
        int[] fmap = dataShadow.fmap;
        char[] sfmap = dataShadow.sfmap;
        int[] mtfFreq = dataShadow.mtfFreq;
        byte[] unseqToSeq = dataShadow.unseqToSeq;
        byte[] yy = dataShadow.generateMTFValues_yy;
        int nInUseShadow = 0;
        for (i = 0; i < 256; i++) {
            if (inUse[i]) {
                unseqToSeq[i] = (byte) nInUseShadow;
                nInUseShadow++;
            }
        }
        r0.nInUse = nInUseShadow;
        i = nInUseShadow + 1;
        for (i2 = i; i2 >= 0; i2--) {
            mtfFreq[i2] = 0;
        }
        i2 = nInUseShadow;
        while (true) {
            i2--;
            if (i2 < 0) {
                break;
            }
            yy[i2] = (byte) i2;
        }
        int zPend = 0;
        int wr = 0;
        i2 = 0;
        while (i2 <= lastShadow) {
            byte ll_i = unseqToSeq[block[fmap[i2]] & 255];
            int lastShadow2 = lastShadow;
            byte lastShadow3 = yy[0];
            int j = 0;
            while (ll_i != lastShadow3) {
                j++;
                byte tmp2 = lastShadow3;
                lastShadow3 = yy[j];
                yy[j] = tmp2;
            }
            yy[0] = lastShadow3;
            if (j == 0) {
                zPend++;
            } else {
                byte tmp;
                if (zPend > 0) {
                    zPend--;
                    while (true) {
                        if ((zPend & 1) == 0) {
                            sfmap[wr] = '\u0000';
                            wr++;
                            mtfFreq[0] = mtfFreq[0] + 1;
                        } else {
                            sfmap[wr] = '\u0001';
                            wr++;
                            mtfFreq[1] = mtfFreq[1] + 1;
                        }
                        tmp = lastShadow3;
                        if (zPend < 2) {
                            break;
                        }
                        zPend = (zPend - 2) >> 1;
                        lastShadow3 = tmp;
                    }
                    zPend = 0;
                } else {
                    tmp = lastShadow3;
                }
                sfmap[wr] = (char) (j + 1);
                wr++;
                lastShadow = j + 1;
                mtfFreq[lastShadow] = mtfFreq[lastShadow] + 1;
            }
            i2++;
            lastShadow = lastShadow2;
        }
        if (zPend > 0) {
            zPend--;
            while (true) {
                if ((zPend & 1) == 0) {
                    sfmap[wr] = '\u0000';
                    wr++;
                    mtfFreq[0] = mtfFreq[0] + 1;
                } else {
                    sfmap[wr] = '\u0001';
                    wr++;
                    mtfFreq[1] = mtfFreq[1] + 1;
                }
                if (zPend < 2) {
                    break;
                }
                zPend = (zPend - 2) >> 1;
            }
        }
        sfmap[wr] = (char) i;
        mtfFreq[i] = mtfFreq[i] + 1;
        r0.nMTF = wr + 1;
    }
}
