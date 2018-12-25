package org.apache.commons.compress.archivers.zip;

import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.compress.compressors.p000z._internal_.InternalLZWInputStream;

class UnshrinkingInputStream extends InternalLZWInputStream {
    private static final int MAX_CODE_SIZE = 13;
    private static final int MAX_TABLE_SIZE = 8192;
    private final boolean[] isUsed = new boolean[this.prefixes.length];

    public UnshrinkingInputStream(InputStream inputStream) throws IOException {
        super(inputStream);
        setClearCode(this.codeSize);
        initializeTables(13);
        for (int i = 0; i < 256; i++) {
            this.isUsed[i] = true;
        }
        this.tableSize = this.clearCode + 1;
    }

    protected int addEntry(int previousCode, byte character) throws IOException {
        while (this.tableSize < 8192 && this.isUsed[this.tableSize]) {
            this.tableSize++;
        }
        int idx = addEntry(previousCode, character, 8192);
        if (idx >= 0) {
            this.isUsed[idx] = true;
        }
        return idx;
    }

    private void partialClear() {
        boolean[] isParent = new boolean[8192];
        int i = 0;
        while (i < this.isUsed.length) {
            if (this.isUsed[i] && this.prefixes[i] != -1) {
                isParent[this.prefixes[i]] = true;
            }
            i++;
        }
        for (i = this.clearCode + 1; i < isParent.length; i++) {
            if (!isParent[i]) {
                this.isUsed[i] = false;
                this.prefixes[i] = -1;
            }
        }
    }

    protected int decompressNextSymbol() throws IOException {
        int code = readNextCode();
        if (code < 0) {
            return -1;
        }
        if (code == this.clearCode) {
            int subCode = readNextCode();
            if (subCode < 0) {
                throw new IOException("Unexpected EOF;");
            }
            if (subCode == 1) {
                if (this.codeSize < 13) {
                    this.codeSize++;
                } else {
                    throw new IOException("Attempt to increase code size beyond maximum");
                }
            } else if (subCode == 2) {
                partialClear();
                this.tableSize = this.clearCode + 1;
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Invalid clear code subcode ");
                stringBuilder.append(subCode);
                throw new IOException(stringBuilder.toString());
            }
            return 0;
        }
        boolean addedUnfinishedEntry = false;
        int effectiveCode = code;
        if (!this.isUsed[code]) {
            effectiveCode = addRepeatOfPreviousCode();
            addedUnfinishedEntry = true;
        }
        return expandCodeToOutputStack(effectiveCode, addedUnfinishedEntry);
    }
}
