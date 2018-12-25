package org.apache.commons.compress.compressors.p000z;

import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.compress.compressors.p000z._internal_.InternalLZWInputStream;

/* renamed from: org.apache.commons.compress.compressors.z.ZCompressorInputStream */
public class ZCompressorInputStream extends InternalLZWInputStream {
    private static final int BLOCK_MODE_MASK = 128;
    private static final int MAGIC_1 = 31;
    private static final int MAGIC_2 = 157;
    private static final int MAX_CODE_SIZE_MASK = 31;
    private final boolean blockMode;
    private final int maxCodeSize;
    private long totalCodesRead = 0;

    public ZCompressorInputStream(InputStream inputStream) throws IOException {
        super(inputStream);
        int firstByte = this.in.read();
        int secondByte = this.in.read();
        int thirdByte = this.in.read();
        if (firstByte == 31 && secondByte == MAGIC_2) {
            if (thirdByte >= 0) {
                this.blockMode = (thirdByte & 128) != 0;
                this.maxCodeSize = thirdByte & 31;
                if (this.blockMode) {
                    setClearCode(this.codeSize);
                }
                initializeTables(this.maxCodeSize);
                clearEntries();
                return;
            }
        }
        throw new IOException("Input is not in .Z format");
    }

    private void clearEntries() {
        this.tableSize = 256;
        if (this.blockMode) {
            this.tableSize++;
        }
    }

    protected int readNextCode() throws IOException {
        int code = super.readNextCode();
        if (code >= 0) {
            this.totalCodesRead++;
        }
        return code;
    }

    private void reAlignReading() throws IOException {
        long codeReadsToThrowAway = 8 - (this.totalCodesRead % 8);
        if (codeReadsToThrowAway == 8) {
            codeReadsToThrowAway = 0;
        }
        for (long i = 0; i < codeReadsToThrowAway; i++) {
            readNextCode();
        }
        this.bitsCached = 0;
        this.bitsCachedSize = 0;
    }

    protected int addEntry(int previousCode, byte character) throws IOException {
        int maxTableSize = 1 << this.codeSize;
        int r = addEntry(previousCode, character, maxTableSize);
        if (this.tableSize == maxTableSize && this.codeSize < this.maxCodeSize) {
            reAlignReading();
            this.codeSize++;
        }
        return r;
    }

    protected int decompressNextSymbol() throws IOException {
        int code = readNextCode();
        if (code < 0) {
            return -1;
        }
        if (this.blockMode && code == this.clearCode) {
            clearEntries();
            reAlignReading();
            this.codeSize = 9;
            this.previousCode = -1;
            return 0;
        }
        boolean addedUnfinishedEntry = false;
        if (code == this.tableSize) {
            addRepeatOfPreviousCode();
            addedUnfinishedEntry = true;
        } else if (code > this.tableSize) {
            throw new IOException(String.format("Invalid %d bit code 0x%x", new Object[]{Integer.valueOf(this.codeSize), Integer.valueOf(code)}));
        }
        return expandCodeToOutputStack(code, addedUnfinishedEntry);
    }

    public static boolean matches(byte[] signature, int length) {
        return length > 3 && signature[0] == (byte) 31 && signature[1] == (byte) -99;
    }
}
