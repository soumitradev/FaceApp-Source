package com.google.common.hash;

import java.io.Serializable;

final class HashCode$LongHashCode extends HashCode implements Serializable {
    private static final long serialVersionUID = 0;
    final long hash;

    HashCode$LongHashCode(long hash) {
        this.hash = hash;
    }

    public int bits() {
        return 64;
    }

    public byte[] asBytes() {
        return new byte[]{(byte) ((int) this.hash), (byte) ((int) (this.hash >> 8)), (byte) ((int) (this.hash >> 16)), (byte) ((int) (this.hash >> 24)), (byte) ((int) (this.hash >> 32)), (byte) ((int) (this.hash >> 40)), (byte) ((int) (this.hash >> 48)), (byte) ((int) (this.hash >> 56))};
    }

    public int asInt() {
        return (int) this.hash;
    }

    public long asLong() {
        return this.hash;
    }

    public long padToLong() {
        return this.hash;
    }

    void writeBytesToImpl(byte[] dest, int offset, int maxLength) {
        for (int i = 0; i < maxLength; i++) {
            dest[offset + i] = (byte) ((int) (this.hash >> (i * 8)));
        }
    }

    boolean equalsSameBits(HashCode that) {
        return this.hash == that.asLong();
    }
}
