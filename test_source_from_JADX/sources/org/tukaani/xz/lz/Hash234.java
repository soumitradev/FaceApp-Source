package org.tukaani.xz.lz;

final class Hash234 extends CRC32Hash {
    private static final int HASH_2_MASK = 1023;
    private static final int HASH_2_SIZE = 1024;
    private static final int HASH_3_MASK = 65535;
    private static final int HASH_3_SIZE = 65536;
    private final int[] hash2Table = new int[1024];
    private int hash2Value = 0;
    private final int[] hash3Table = new int[65536];
    private int hash3Value = 0;
    private final int hash4Mask;
    private final int[] hash4Table;
    private int hash4Value = 0;

    Hash234(int i) {
        this.hash4Table = new int[getHash4Size(i)];
        this.hash4Mask = this.hash4Table.length - 1;
    }

    static int getHash4Size(int i) {
        i--;
        i |= i >>> 1;
        i |= i >>> 2;
        i |= i >>> 4;
        i = ((i | (i >>> 8)) >>> 1) | 65535;
        if (i > 16777216) {
            i >>>= 1;
        }
        return i + 1;
    }

    static int getMemoryUsage(int i) {
        return ((getHash4Size(i) + 66560) / 256) + 4;
    }

    void calcHashes(byte[] bArr, int i) {
        int i2 = crcTable[bArr[i] & 255] ^ (bArr[i + 1] & 255);
        this.hash2Value = i2 & HASH_2_MASK;
        i2 ^= (bArr[i + 2] & 255) << 8;
        this.hash3Value = 65535 & i2;
        this.hash4Value = ((crcTable[bArr[i + 3] & 255] << 5) ^ i2) & this.hash4Mask;
    }

    int getHash2Pos() {
        return this.hash2Table[this.hash2Value];
    }

    int getHash3Pos() {
        return this.hash3Table[this.hash3Value];
    }

    int getHash4Pos() {
        return this.hash4Table[this.hash4Value];
    }

    void normalize(int i) {
        LZEncoder.normalize(this.hash2Table, i);
        LZEncoder.normalize(this.hash3Table, i);
        LZEncoder.normalize(this.hash4Table, i);
    }

    void updateTables(int i) {
        this.hash2Table[this.hash2Value] = i;
        this.hash3Table[this.hash3Value] = i;
        this.hash4Table[this.hash4Value] = i;
    }
}
