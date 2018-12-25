package com.google.common.hash;

import com.google.common.annotations.Beta;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.hash.BloomFilterStrategies.BitArray;
import com.google.common.primitives.SignedBytes;
import com.google.common.primitives.UnsignedBytes;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;
import org.catrobat.catroid.common.BrickValues;

@Beta
public final class BloomFilter<T> implements Predicate<T>, Serializable {
    private final BitArray bits;
    private final Funnel<? super T> funnel;
    private final int numHashFunctions;
    private final BloomFilter$Strategy strategy;

    private BloomFilter(BitArray bits, int numHashFunctions, Funnel<? super T> funnel, BloomFilter$Strategy strategy) {
        Preconditions.checkArgument(numHashFunctions > 0, "numHashFunctions (%s) must be > 0", new Object[]{Integer.valueOf(numHashFunctions)});
        Preconditions.checkArgument(numHashFunctions <= 255, "numHashFunctions (%s) must be <= 255", new Object[]{Integer.valueOf(numHashFunctions)});
        this.bits = (BitArray) Preconditions.checkNotNull(bits);
        this.numHashFunctions = numHashFunctions;
        this.funnel = (Funnel) Preconditions.checkNotNull(funnel);
        this.strategy = (BloomFilter$Strategy) Preconditions.checkNotNull(strategy);
    }

    @CheckReturnValue
    public BloomFilter<T> copy() {
        return new BloomFilter(this.bits.copy(), this.numHashFunctions, this.funnel, this.strategy);
    }

    @CheckReturnValue
    public boolean mightContain(T object) {
        return this.strategy.mightContain(object, this.funnel, this.numHashFunctions, this.bits);
    }

    @CheckReturnValue
    @Deprecated
    public boolean apply(T input) {
        return mightContain(input);
    }

    public boolean put(T object) {
        return this.strategy.put(object, this.funnel, this.numHashFunctions, this.bits);
    }

    @CheckReturnValue
    public double expectedFpp() {
        return Math.pow(((double) this.bits.bitCount()) / ((double) bitSize()), (double) this.numHashFunctions);
    }

    @VisibleForTesting
    long bitSize() {
        return this.bits.bitSize();
    }

    @CheckReturnValue
    public boolean isCompatible(BloomFilter<T> that) {
        Preconditions.checkNotNull(that);
        return this != that && this.numHashFunctions == that.numHashFunctions && bitSize() == that.bitSize() && this.strategy.equals(that.strategy) && this.funnel.equals(that.funnel);
    }

    public void putAll(BloomFilter<T> that) {
        Preconditions.checkNotNull(that);
        Preconditions.checkArgument(this != that, "Cannot combine a BloomFilter with itself.");
        Preconditions.checkArgument(this.numHashFunctions == that.numHashFunctions, "BloomFilters must have the same number of hash functions (%s != %s)", new Object[]{Integer.valueOf(this.numHashFunctions), Integer.valueOf(that.numHashFunctions)});
        Preconditions.checkArgument(bitSize() == that.bitSize(), "BloomFilters must have the same size underlying bit arrays (%s != %s)", new Object[]{Long.valueOf(bitSize()), Long.valueOf(that.bitSize())});
        Preconditions.checkArgument(this.strategy.equals(that.strategy), "BloomFilters must have equal strategies (%s != %s)", new Object[]{this.strategy, that.strategy});
        Preconditions.checkArgument(this.funnel.equals(that.funnel), "BloomFilters must have equal funnels (%s != %s)", new Object[]{this.funnel, that.funnel});
        this.bits.putAll(that.bits);
    }

    public boolean equals(@Nullable Object object) {
        boolean z = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof BloomFilter)) {
            return false;
        }
        BloomFilter<?> that = (BloomFilter) object;
        if (this.numHashFunctions != that.numHashFunctions || !this.funnel.equals(that.funnel) || !this.bits.equals(that.bits) || !this.strategy.equals(that.strategy)) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return Objects.hashCode(Integer.valueOf(this.numHashFunctions), this.funnel, this.strategy, this.bits);
    }

    @CheckReturnValue
    public static <T> BloomFilter<T> create(Funnel<? super T> funnel, int expectedInsertions, double fpp) {
        return create((Funnel) funnel, (long) expectedInsertions, fpp);
    }

    @CheckReturnValue
    public static <T> BloomFilter<T> create(Funnel<? super T> funnel, long expectedInsertions, double fpp) {
        return create(funnel, expectedInsertions, fpp, BloomFilterStrategies.MURMUR128_MITZ_64);
    }

    @VisibleForTesting
    static <T> BloomFilter<T> create(Funnel<? super T> funnel, long expectedInsertions, double fpp, BloomFilter$Strategy strategy) {
        Preconditions.checkNotNull(funnel);
        Preconditions.checkArgument(expectedInsertions >= 0, "Expected insertions (%s) must be >= 0", new Object[]{Long.valueOf(expectedInsertions)});
        Preconditions.checkArgument(fpp > BrickValues.SET_COLOR_TO, "False positive probability (%s) must be > 0.0", new Object[]{Double.valueOf(fpp)});
        Preconditions.checkArgument(fpp < 1.0d, "False positive probability (%s) must be < 1.0", new Object[]{Double.valueOf(fpp)});
        Preconditions.checkNotNull(strategy);
        if (expectedInsertions == 0) {
            expectedInsertions = 1;
        }
        long numBits = optimalNumOfBits(expectedInsertions, fpp);
        try {
            return new BloomFilter(new BitArray(numBits), optimalNumOfHashFunctions(expectedInsertions, numBits), funnel, strategy);
        } catch (IllegalArgumentException e) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Could not create BloomFilter of ");
            stringBuilder.append(numBits);
            stringBuilder.append(" bits");
            throw new IllegalArgumentException(stringBuilder.toString(), e);
        }
    }

    @CheckReturnValue
    public static <T> BloomFilter<T> create(Funnel<? super T> funnel, int expectedInsertions) {
        return create((Funnel) funnel, (long) expectedInsertions);
    }

    @CheckReturnValue
    public static <T> BloomFilter<T> create(Funnel<? super T> funnel, long expectedInsertions) {
        return create((Funnel) funnel, expectedInsertions, 0.03d);
    }

    @VisibleForTesting
    static int optimalNumOfHashFunctions(long n, long m) {
        return Math.max(1, (int) Math.round((((double) m) / ((double) n)) * Math.log(2.0d)));
    }

    @VisibleForTesting
    static long optimalNumOfBits(long n, double p) {
        if (p == BrickValues.SET_COLOR_TO) {
            p = Double.MIN_VALUE;
        }
        return (long) ((((double) (-n)) * Math.log(p)) / (Math.log(2.0d) * Math.log(2.0d)));
    }

    private Object writeReplace() {
        return new BloomFilter$SerialForm(this);
    }

    public void writeTo(OutputStream out) throws IOException {
        DataOutputStream dout = new DataOutputStream(out);
        dout.writeByte(SignedBytes.checkedCast((long) this.strategy.ordinal()));
        dout.writeByte(UnsignedBytes.checkedCast((long) this.numHashFunctions));
        dout.writeInt(this.bits.data.length);
        for (long value : this.bits.data) {
            dout.writeLong(value);
        }
    }

    @CheckReturnValue
    public static <T> BloomFilter<T> readFrom(InputStream in, Funnel<T> funnel) throws IOException {
        Preconditions.checkNotNull(in, "InputStream");
        Preconditions.checkNotNull(funnel, "Funnel");
        try {
            DataInputStream din = new DataInputStream(in);
            byte strategyOrdinal = din.readByte();
            int numHashFunctions = UnsignedBytes.toInt(din.readByte());
            int dataLength = din.readInt();
            BloomFilter$Strategy strategy = BloomFilterStrategies.values()[strategyOrdinal];
            long[] data = new long[dataLength];
            for (int i = 0; i < data.length; i++) {
                data[i] = din.readLong();
            }
            return new BloomFilter(new BitArray(data), numHashFunctions, funnel, strategy);
        } catch (RuntimeException e) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unable to deserialize BloomFilter from InputStream. strategyOrdinal: ");
            stringBuilder.append(-1);
            stringBuilder.append(" numHashFunctions: ");
            stringBuilder.append(-1);
            stringBuilder.append(" dataLength: ");
            stringBuilder.append(-1);
            IOException ioException = new IOException(stringBuilder.toString());
            ioException.initCause(e);
            throw ioException;
        }
    }
}
