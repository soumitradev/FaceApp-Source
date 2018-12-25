package com.google.common.hash;

import com.google.common.base.Preconditions;
import java.io.Serializable;

final class HashCode$BytesHashCode extends HashCode implements Serializable {
    private static final long serialVersionUID = 0;
    final byte[] bytes;

    HashCode$BytesHashCode(byte[] bytes) {
        this.bytes = (byte[]) Preconditions.checkNotNull(bytes);
    }

    public int bits() {
        return this.bytes.length * 8;
    }

    public byte[] asBytes() {
        return (byte[]) this.bytes.clone();
    }

    public int asInt() {
        Preconditions.checkState(this.bytes.length >= 4, "HashCode#asInt() requires >= 4 bytes (it only has %s bytes).", Integer.valueOf(this.bytes.length));
        return (((this.bytes[0] & 255) | ((this.bytes[1] & 255) << 8)) | ((this.bytes[2] & 255) << 16)) | ((this.bytes[3] & 255) << 24);
    }

    public long asLong() {
        Preconditions.checkState(this.bytes.length >= 8, "HashCode#asLong() requires >= 8 bytes (it only has %s bytes).", Integer.valueOf(this.bytes.length));
        return padToLong();
    }

    public long padToLong() {
        long retVal = (long) (this.bytes[0] & 255);
        int i = 1;
        while (i < Math.min(this.bytes.length, 8)) {
            i++;
            retVal |= (((long) this.bytes[i]) & 255) << (i * 8);
        }
        return retVal;
    }

    void writeBytesToImpl(byte[] dest, int offset, int maxLength) {
        System.arraycopy(this.bytes, 0, dest, offset, maxLength);
    }

    byte[] getBytesInternal() {
        return this.bytes;
    }

    boolean equalsSameBits(HashCode that) {
        if (this.bytes.length != that.getBytesInternal().length) {
            return false;
        }
        boolean areEqual = true;
        for (int i = 0; i < this.bytes.length; i++) {
            areEqual &= this.bytes[i] == that.getBytesInternal()[i] ? 1 : 0;
        }
        return areEqual;
    }
}
