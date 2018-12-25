package com.google.common.hash;

import com.google.common.primitives.UnsignedInts;
import java.io.Serializable;

final class HashCode$IntHashCode extends HashCode implements Serializable {
    private static final long serialVersionUID = 0;
    final int hash;

    HashCode$IntHashCode(int hash) {
        this.hash = hash;
    }

    public int bits() {
        return 32;
    }

    public byte[] asBytes() {
        return new byte[]{(byte) this.hash, (byte) (this.hash >> 8), (byte) (this.hash >> 16), (byte) (this.hash >> 24)};
    }

    public int asInt() {
        return this.hash;
    }

    public long asLong() {
        throw new IllegalStateException("this HashCode only has 32 bits; cannot create a long");
    }

    public long padToLong() {
        return UnsignedInts.toLong(this.hash);
    }

    void writeBytesToImpl(byte[] dest, int offset, int maxLength) {
        for (int i = 0; i < maxLength; i++) {
            dest[offset + i] = (byte) (this.hash >> (i * 8));
        }
    }

    boolean equalsSameBits(HashCode that) {
        return this.hash == that.asInt();
    }
}
