package com.google.common.hash;

import com.google.common.annotations.Beta;
import com.google.common.base.Preconditions;
import com.google.common.primitives.Ints;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;

@Beta
public abstract class HashCode {
    private static final char[] hexDigits = "0123456789abcdef".toCharArray();

    @CheckReturnValue
    public abstract byte[] asBytes();

    @CheckReturnValue
    public abstract int asInt();

    @CheckReturnValue
    public abstract long asLong();

    @CheckReturnValue
    public abstract int bits();

    abstract boolean equalsSameBits(HashCode hashCode);

    @CheckReturnValue
    public abstract long padToLong();

    abstract void writeBytesToImpl(byte[] bArr, int i, int i2);

    HashCode() {
    }

    public int writeBytesTo(byte[] dest, int offset, int maxLength) {
        maxLength = Ints.min(maxLength, bits() / 8);
        Preconditions.checkPositionIndexes(offset, offset + maxLength, dest.length);
        writeBytesToImpl(dest, offset, maxLength);
        return maxLength;
    }

    byte[] getBytesInternal() {
        return asBytes();
    }

    @CheckReturnValue
    public static HashCode fromInt(int hash) {
        return new HashCode$IntHashCode(hash);
    }

    @CheckReturnValue
    public static HashCode fromLong(long hash) {
        return new HashCode$LongHashCode(hash);
    }

    @CheckReturnValue
    public static HashCode fromBytes(byte[] bytes) {
        boolean z = true;
        if (bytes.length < 1) {
            z = false;
        }
        Preconditions.checkArgument(z, "A HashCode must contain at least 1 byte.");
        return fromBytesNoCopy((byte[]) bytes.clone());
    }

    static HashCode fromBytesNoCopy(byte[] bytes) {
        return new HashCode$BytesHashCode(bytes);
    }

    @CheckReturnValue
    public static HashCode fromString(String string) {
        int i = 0;
        Preconditions.checkArgument(string.length() >= 2, "input string (%s) must have at least 2 characters", new Object[]{string});
        Preconditions.checkArgument(string.length() % 2 == 0, "input string (%s) must have an even number of characters", new Object[]{string});
        byte[] bytes = new byte[(string.length() / 2)];
        while (i < string.length()) {
            bytes[i / 2] = (byte) ((decode(string.charAt(i)) << 4) + decode(string.charAt(i + 1)));
            i += 2;
        }
        return fromBytesNoCopy(bytes);
    }

    private static int decode(char ch) {
        if (ch >= '0' && ch <= '9') {
            return ch - 48;
        }
        if (ch >= 'a' && ch <= 'f') {
            return (ch - 97) + 10;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Illegal hexadecimal character: ");
        stringBuilder.append(ch);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public final boolean equals(@Nullable Object object) {
        boolean z = false;
        if (!(object instanceof HashCode)) {
            return false;
        }
        HashCode that = (HashCode) object;
        if (bits() == that.bits() && equalsSameBits(that)) {
            z = true;
        }
        return z;
    }

    public final int hashCode() {
        if (bits() >= 32) {
            return asInt();
        }
        byte[] bytes = getBytesInternal();
        int val = bytes[0] & 255;
        for (int i = 1; i < bytes.length; i++) {
            val |= (bytes[i] & 255) << (i * 8);
        }
        return val;
    }

    public final String toString() {
        byte[] bytes = getBytesInternal();
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            sb.append(hexDigits[(b >> 4) & 15]);
            sb.append(hexDigits[b & 15]);
        }
        return sb.toString();
    }
}
