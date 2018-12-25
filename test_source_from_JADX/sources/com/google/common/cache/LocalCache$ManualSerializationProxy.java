package com.google.common.cache;

import com.google.common.base.Equivalence;
import com.google.common.base.Ticker;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;

class LocalCache$ManualSerializationProxy<K, V> extends ForwardingCache<K, V> implements Serializable {
    private static final long serialVersionUID = 1;
    final int concurrencyLevel;
    transient Cache<K, V> delegate;
    final long expireAfterAccessNanos;
    final long expireAfterWriteNanos;
    final Equivalence<Object> keyEquivalence;
    final LocalCache$Strength keyStrength;
    final CacheLoader<? super K, V> loader;
    final long maxWeight;
    final RemovalListener<? super K, ? super V> removalListener;
    final Ticker ticker;
    final Equivalence<Object> valueEquivalence;
    final LocalCache$Strength valueStrength;
    final Weigher<K, V> weigher;

    LocalCache$ManualSerializationProxy(LocalCache<K, V> cache) {
        LocalCache<K, V> localCache = cache;
        this(localCache.keyStrength, localCache.valueStrength, localCache.keyEquivalence, localCache.valueEquivalence, localCache.expireAfterWriteNanos, localCache.expireAfterAccessNanos, localCache.maxWeight, localCache.weigher, localCache.concurrencyLevel, localCache.removalListener, localCache.ticker, localCache.defaultLoader);
    }

    private LocalCache$ManualSerializationProxy(LocalCache$Strength keyStrength, LocalCache$Strength valueStrength, Equivalence<Object> keyEquivalence, Equivalence<Object> valueEquivalence, long expireAfterWriteNanos, long expireAfterAccessNanos, long maxWeight, Weigher<K, V> weigher, int concurrencyLevel, RemovalListener<? super K, ? super V> removalListener, Ticker ticker, CacheLoader<? super K, V> loader) {
        Ticker ticker2;
        Ticker ticker3 = ticker;
        this.keyStrength = keyStrength;
        this.valueStrength = valueStrength;
        this.keyEquivalence = keyEquivalence;
        this.valueEquivalence = valueEquivalence;
        this.expireAfterWriteNanos = expireAfterWriteNanos;
        this.expireAfterAccessNanos = expireAfterAccessNanos;
        this.maxWeight = maxWeight;
        this.weigher = weigher;
        this.concurrencyLevel = concurrencyLevel;
        this.removalListener = removalListener;
        if (ticker3 != Ticker.systemTicker()) {
            if (ticker3 != CacheBuilder.NULL_TICKER) {
                ticker2 = ticker3;
                r0.ticker = ticker2;
                r0.loader = loader;
            }
        }
        ticker2 = null;
        r0.ticker = ticker2;
        r0.loader = loader;
    }

    CacheBuilder<K, V> recreateCacheBuilder() {
        CacheBuilder<K, V> builder = CacheBuilder.newBuilder().setKeyStrength(this.keyStrength).setValueStrength(this.valueStrength).keyEquivalence(this.keyEquivalence).valueEquivalence(this.valueEquivalence).concurrencyLevel(this.concurrencyLevel).removalListener(this.removalListener);
        builder.strictParsing = false;
        if (this.expireAfterWriteNanos > 0) {
            builder.expireAfterWrite(this.expireAfterWriteNanos, TimeUnit.NANOSECONDS);
        }
        if (this.expireAfterAccessNanos > 0) {
            builder.expireAfterAccess(this.expireAfterAccessNanos, TimeUnit.NANOSECONDS);
        }
        if (this.weigher != CacheBuilder$OneWeigher.INSTANCE) {
            builder.weigher(this.weigher);
            if (this.maxWeight != -1) {
                builder.maximumWeight(this.maxWeight);
            }
        } else if (this.maxWeight != -1) {
            builder.maximumSize(this.maxWeight);
        }
        if (this.ticker != null) {
            builder.ticker(this.ticker);
        }
        return builder;
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        this.delegate = recreateCacheBuilder().build();
    }

    private Object readResolve() {
        return this.delegate;
    }

    protected Cache<K, V> delegate() {
        return this.delegate;
    }
}