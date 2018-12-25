package com.google.common.hash;

import com.google.common.annotations.Beta;
import com.google.common.base.Preconditions;
import com.pdrogfer.mididroid.event.meta.MetaEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.CheckReturnValue;

@CheckReturnValue
@Beta
public final class Hashing {
    private static final int GOOD_FAST_HASH_SEED = ((int) System.currentTimeMillis());

    public static HashFunction goodFastHash(int minimumBits) {
        int bits = checkPositiveAndMakeMultipleOf32(minimumBits);
        if (bits == 32) {
            return Hashing$Murmur3_32Holder.GOOD_FAST_HASH_FUNCTION_32;
        }
        if (bits <= 128) {
            return Hashing$Murmur3_128Holder.GOOD_FAST_HASH_FUNCTION_128;
        }
        int hashFunctionsNeeded = (bits + MetaEvent.SEQUENCER_SPECIFIC) / 128;
        HashFunction[] hashFunctions = new HashFunction[hashFunctionsNeeded];
        hashFunctions[0] = Hashing$Murmur3_128Holder.GOOD_FAST_HASH_FUNCTION_128;
        int seed = GOOD_FAST_HASH_SEED;
        for (int i = 1; i < hashFunctionsNeeded; i++) {
            seed += 1500450271;
            hashFunctions[i] = murmur3_128(seed);
        }
        return new Hashing$ConcatenatedHashFunction(hashFunctions, null);
    }

    public static HashFunction murmur3_32(int seed) {
        return new Murmur3_32HashFunction(seed);
    }

    public static HashFunction murmur3_32() {
        return Hashing$Murmur3_32Holder.MURMUR3_32;
    }

    public static HashFunction murmur3_128(int seed) {
        return new Murmur3_128HashFunction(seed);
    }

    public static HashFunction murmur3_128() {
        return Hashing$Murmur3_128Holder.MURMUR3_128;
    }

    public static HashFunction sipHash24() {
        return Hashing$SipHash24Holder.SIP_HASH_24;
    }

    public static HashFunction sipHash24(long k0, long k1) {
        return new SipHashFunction(2, 4, k0, k1);
    }

    public static HashFunction md5() {
        return Hashing$Md5Holder.MD5;
    }

    public static HashFunction sha1() {
        return Hashing$Sha1Holder.SHA_1;
    }

    public static HashFunction sha256() {
        return Hashing$Sha256Holder.SHA_256;
    }

    public static HashFunction sha384() {
        return Hashing$Sha384Holder.SHA_384;
    }

    public static HashFunction sha512() {
        return Hashing$Sha512Holder.SHA_512;
    }

    public static HashFunction crc32c() {
        return Hashing$Crc32cHolder.CRC_32_C;
    }

    public static HashFunction crc32() {
        return Hashing$Crc32Holder.CRC_32;
    }

    public static HashFunction adler32() {
        return Hashing$Adler32Holder.ADLER_32;
    }

    private static HashFunction checksumHashFunction(Hashing$ChecksumType type, String toString) {
        return new ChecksumHashFunction(type, Hashing$ChecksumType.access$300(type), toString);
    }

    public static int consistentHash(HashCode hashCode, int buckets) {
        return consistentHash(hashCode.padToLong(), buckets);
    }

    public static int consistentHash(long input, int buckets) {
        int candidate = 0;
        Preconditions.checkArgument(buckets > 0, "buckets must be positive: %s", new Object[]{Integer.valueOf(buckets)});
        Hashing$LinearCongruentialGenerator generator = new Hashing$LinearCongruentialGenerator(input);
        while (true) {
            int next = (int) (((double) (candidate + 1)) / generator.nextDouble());
            if (next < 0 || next >= buckets) {
                return candidate;
            }
            candidate = next;
        }
        return candidate;
    }

    public static HashCode combineOrdered(Iterable<HashCode> hashCodes) {
        Iterator<HashCode> iterator = hashCodes.iterator();
        Preconditions.checkArgument(iterator.hasNext(), "Must be at least 1 hash code to combine.");
        byte[] resultBytes = new byte[(((HashCode) iterator.next()).bits() / 8)];
        for (HashCode hashCode : hashCodes) {
            byte[] nextBytes = hashCode.asBytes();
            int i = 0;
            Preconditions.checkArgument(nextBytes.length == resultBytes.length, "All hashcodes must have the same bit length.");
            while (true) {
                int i2 = i;
                if (i2 >= nextBytes.length) {
                    break;
                }
                resultBytes[i2] = (byte) ((resultBytes[i2] * 37) ^ nextBytes[i2]);
                i = i2 + 1;
            }
        }
        return HashCode.fromBytesNoCopy(resultBytes);
    }

    public static HashCode combineUnordered(Iterable<HashCode> hashCodes) {
        Iterator<HashCode> iterator = hashCodes.iterator();
        Preconditions.checkArgument(iterator.hasNext(), "Must be at least 1 hash code to combine.");
        byte[] resultBytes = new byte[(((HashCode) iterator.next()).bits() / 8)];
        for (HashCode hashCode : hashCodes) {
            byte[] nextBytes = hashCode.asBytes();
            int i = 0;
            Preconditions.checkArgument(nextBytes.length == resultBytes.length, "All hashcodes must have the same bit length.");
            while (true) {
                int i2 = i;
                if (i2 >= nextBytes.length) {
                    break;
                }
                resultBytes[i2] = (byte) (resultBytes[i2] + nextBytes[i2]);
                i = i2 + 1;
            }
        }
        return HashCode.fromBytesNoCopy(resultBytes);
    }

    static int checkPositiveAndMakeMultipleOf32(int bits) {
        Preconditions.checkArgument(bits > 0, "Number of bits must be positive");
        return (bits + 31) & -32;
    }

    public static HashFunction concatenating(HashFunction first, HashFunction second, HashFunction... rest) {
        List<HashFunction> list = new ArrayList();
        list.add(first);
        list.add(second);
        for (HashFunction hashFunc : rest) {
            list.add(hashFunc);
        }
        return new Hashing$ConcatenatedHashFunction((HashFunction[]) list.toArray(new HashFunction[0]), null);
    }

    public static HashFunction concatenating(Iterable<HashFunction> hashFunctions) {
        Preconditions.checkNotNull(hashFunctions);
        List<HashFunction> list = new ArrayList();
        for (HashFunction hashFunction : hashFunctions) {
            list.add(hashFunction);
        }
        Preconditions.checkArgument(list.size() > 0, "number of hash functions (%s) must be > 0", new Object[]{Integer.valueOf(list.size())});
        return new Hashing$ConcatenatedHashFunction((HashFunction[]) list.toArray(new HashFunction[0]), null);
    }

    private Hashing() {
    }
}
