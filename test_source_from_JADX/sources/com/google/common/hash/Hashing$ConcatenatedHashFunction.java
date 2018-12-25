package com.google.common.hash;

import com.google.common.base.Preconditions;
import java.util.Arrays;
import javax.annotation.Nullable;

final class Hashing$ConcatenatedHashFunction extends AbstractCompositeHashFunction {
    private final int bits;

    private Hashing$ConcatenatedHashFunction(HashFunction... functions) {
        super(functions);
        int bitSum = 0;
        for (HashFunction function : functions) {
            bitSum += function.bits();
            Preconditions.checkArgument(function.bits() % 8 == 0, "the number of bits (%s) in hashFunction (%s) must be divisible by 8", Integer.valueOf(function.bits()), function);
        }
        this.bits = bitSum;
    }

    HashCode makeHash(Hasher[] hashers) {
        byte[] bytes = new byte[(this.bits / 8)];
        int i = 0;
        for (Hasher hasher : hashers) {
            HashCode newHash = hasher.hash();
            i += newHash.writeBytesTo(bytes, i, newHash.bits() / 8);
        }
        return HashCode.fromBytesNoCopy(bytes);
    }

    public int bits() {
        return this.bits;
    }

    public boolean equals(@Nullable Object object) {
        if (!(object instanceof Hashing$ConcatenatedHashFunction)) {
            return false;
        }
        return Arrays.equals(this.functions, ((Hashing$ConcatenatedHashFunction) object).functions);
    }

    public int hashCode() {
        return (Arrays.hashCode(this.functions) * 31) + this.bits;
    }
}
