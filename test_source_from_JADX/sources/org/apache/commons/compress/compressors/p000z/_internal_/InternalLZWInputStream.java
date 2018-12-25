package org.apache.commons.compress.compressors.p000z._internal_;

import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.compress.compressors.CompressorInputStream;

/* renamed from: org.apache.commons.compress.compressors.z._internal_.InternalLZWInputStream */
public abstract class InternalLZWInputStream extends CompressorInputStream {
    protected int bitsCached = 0;
    protected int bitsCachedSize = 0;
    protected byte[] characters;
    protected int clearCode = -1;
    protected int codeSize = 9;
    protected final InputStream in;
    private final byte[] oneByte = new byte[1];
    private byte[] outputStack;
    private int outputStackLocation;
    protected int[] prefixes;
    protected int previousCode = -1;
    protected int tableSize = 0;

    protected abstract int addEntry(int i, byte b) throws IOException;

    protected abstract int decompressNextSymbol() throws IOException;

    protected InternalLZWInputStream(InputStream inputStream) {
        this.in = inputStream;
    }

    public void close() throws IOException {
        this.in.close();
    }

    public int read() throws IOException {
        int ret = read(this.oneByte);
        if (ret < 0) {
            return ret;
        }
        return this.oneByte[0] & 255;
    }

    public int read(byte[] b, int off, int len) throws IOException {
        int bytesRead = readFromStack(b, off, len);
        while (len - bytesRead > 0) {
            int result = decompressNextSymbol();
            if (result >= 0) {
                bytesRead += readFromStack(b, off + bytesRead, len - bytesRead);
            } else if (bytesRead <= 0) {
                return result;
            } else {
                count(bytesRead);
                return bytesRead;
            }
        }
        count(bytesRead);
        return bytesRead;
    }

    protected void setClearCode(int codeSize) {
        this.clearCode = 1 << (codeSize - 1);
    }

    protected void initializeTables(int maxCodeSize) {
        int maxTableSize = 1 << maxCodeSize;
        this.prefixes = new int[maxTableSize];
        this.characters = new byte[maxTableSize];
        this.outputStack = new byte[maxTableSize];
        this.outputStackLocation = maxTableSize;
        for (int i = 0; i < 256; i++) {
            this.prefixes[i] = -1;
            this.characters[i] = (byte) i;
        }
    }

    protected int readNextCode() throws IOException {
        while (this.bitsCachedSize < this.codeSize) {
            int nextByte = this.in.read();
            if (nextByte < 0) {
                return nextByte;
            }
            this.bitsCached |= nextByte << this.bitsCachedSize;
            this.bitsCachedSize += 8;
        }
        int code = this.bitsCached & ((1 << this.codeSize) - 1);
        this.bitsCached >>>= this.codeSize;
        this.bitsCachedSize -= this.codeSize;
        return code;
    }

    protected int addEntry(int previousCode, byte character, int maxTableSize) {
        if (this.tableSize >= maxTableSize) {
            return -1;
        }
        int index = this.tableSize;
        this.prefixes[this.tableSize] = previousCode;
        this.characters[this.tableSize] = character;
        this.tableSize++;
        return index;
    }

    protected int addRepeatOfPreviousCode() throws IOException {
        if (this.previousCode == -1) {
            throw new IOException("The first code can't be a reference to its preceding code");
        }
        byte firstCharacter = (byte) 0;
        int last = this.previousCode;
        while (last >= 0) {
            firstCharacter = this.characters[last];
            last = this.prefixes[last];
        }
        return addEntry(this.previousCode, firstCharacter);
    }

    protected int expandCodeToOutputStack(int code, boolean addedUnfinishedEntry) throws IOException {
        int entry = code;
        while (entry >= 0) {
            byte[] bArr = this.outputStack;
            int i = this.outputStackLocation - 1;
            this.outputStackLocation = i;
            bArr[i] = this.characters[entry];
            entry = this.prefixes[entry];
        }
        if (!(this.previousCode == -1 || addedUnfinishedEntry)) {
            addEntry(this.previousCode, this.outputStack[this.outputStackLocation]);
        }
        this.previousCode = code;
        return this.outputStackLocation;
    }

    private int readFromStack(byte[] b, int off, int len) {
        int remainingInStack = this.outputStack.length - this.outputStackLocation;
        if (remainingInStack <= 0) {
            return 0;
        }
        int maxLength = Math.min(remainingInStack, len);
        System.arraycopy(this.outputStack, this.outputStackLocation, b, off, maxLength);
        this.outputStackLocation += maxLength;
        return maxLength;
    }
}
