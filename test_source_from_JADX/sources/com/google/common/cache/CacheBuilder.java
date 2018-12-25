package com.google.common.cache;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Ascii;
import com.google.common.base.Equivalence;
import com.google.common.base.MoreObjects;
import com.google.common.base.MoreObjects.ToStringHelper;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.base.Ticker;
import com.google.common.cache.AbstractCache.StatsCounter;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.CheckReturnValue;

@GwtCompatible(emulated = true)
public final class CacheBuilder<K, V> {
    static final Supplier<StatsCounter> CACHE_STATS_COUNTER = new CacheBuilder$2();
    private static final int DEFAULT_CONCURRENCY_LEVEL = 4;
    private static final int DEFAULT_EXPIRATION_NANOS = 0;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    private static final int DEFAULT_REFRESH_NANOS = 0;
    static final CacheStats EMPTY_STATS = new CacheStats(0, 0, 0, 0, 0, 0);
    static final Supplier<? extends StatsCounter> NULL_STATS_COUNTER = Suppliers.ofInstance(new CacheBuilder$1());
    static final Ticker NULL_TICKER = new CacheBuilder$3();
    static final int UNSET_INT = -1;
    private static final Logger logger = Logger.getLogger(CacheBuilder.class.getName());
    int concurrencyLevel = -1;
    long expireAfterAccessNanos = -1;
    long expireAfterWriteNanos = -1;
    int initialCapacity = -1;
    Equivalence<Object> keyEquivalence;
    LocalCache$Strength keyStrength;
    long maximumSize = -1;
    long maximumWeight = -1;
    long refreshNanos = -1;
    RemovalListener<? super K, ? super V> removalListener;
    Supplier<? extends StatsCounter> statsCounterSupplier = NULL_STATS_COUNTER;
    boolean strictParsing = true;
    Ticker ticker;
    Equivalence<Object> valueEquivalence;
    LocalCache$Strength valueStrength;
    Weigher<? super K, ? super V> weigher;

    CacheBuilder() {
    }

    public static CacheBuilder<Object, Object> newBuilder() {
        return new CacheBuilder();
    }

    @GwtIncompatible("To be supported")
    public static CacheBuilder<Object, Object> from(CacheBuilderSpec spec) {
        return spec.toCacheBuilder().lenientParsing();
    }

    @GwtIncompatible("To be supported")
    public static CacheBuilder<Object, Object> from(String spec) {
        return from(CacheBuilderSpec.parse(spec));
    }

    @GwtIncompatible("To be supported")
    CacheBuilder<K, V> lenientParsing() {
        this.strictParsing = false;
        return this;
    }

    @GwtIncompatible("To be supported")
    CacheBuilder<K, V> keyEquivalence(Equivalence<Object> equivalence) {
        Preconditions.checkState(this.keyEquivalence == null, "key equivalence was already set to %s", new Object[]{this.keyEquivalence});
        this.keyEquivalence = (Equivalence) Preconditions.checkNotNull(equivalence);
        return this;
    }

    Equivalence<Object> getKeyEquivalence() {
        return (Equivalence) MoreObjects.firstNonNull(this.keyEquivalence, getKeyStrength().defaultEquivalence());
    }

    @GwtIncompatible("To be supported")
    CacheBuilder<K, V> valueEquivalence(Equivalence<Object> equivalence) {
        Preconditions.checkState(this.valueEquivalence == null, "value equivalence was already set to %s", new Object[]{this.valueEquivalence});
        this.valueEquivalence = (Equivalence) Preconditions.checkNotNull(equivalence);
        return this;
    }

    Equivalence<Object> getValueEquivalence() {
        return (Equivalence) MoreObjects.firstNonNull(this.valueEquivalence, getValueStrength().defaultEquivalence());
    }

    public CacheBuilder<K, V> initialCapacity(int initialCapacity) {
        boolean z = false;
        Preconditions.checkState(this.initialCapacity == -1, "initial capacity was already set to %s", new Object[]{Integer.valueOf(this.initialCapacity)});
        if (initialCapacity >= 0) {
            z = true;
        }
        Preconditions.checkArgument(z);
        this.initialCapacity = initialCapacity;
        return this;
    }

    int getInitialCapacity() {
        return this.initialCapacity == -1 ? 16 : this.initialCapacity;
    }

    public CacheBuilder<K, V> concurrencyLevel(int concurrencyLevel) {
        boolean z = false;
        Preconditions.checkState(this.concurrencyLevel == -1, "concurrency level was already set to %s", new Object[]{Integer.valueOf(this.concurrencyLevel)});
        if (concurrencyLevel > 0) {
            z = true;
        }
        Preconditions.checkArgument(z);
        this.concurrencyLevel = concurrencyLevel;
        return this;
    }

    int getConcurrencyLevel() {
        return this.concurrencyLevel == -1 ? 4 : this.concurrencyLevel;
    }

    public CacheBuilder<K, V> maximumSize(long size) {
        boolean z = false;
        Preconditions.checkState(this.maximumSize == -1, "maximum size was already set to %s", new Object[]{Long.valueOf(this.maximumSize)});
        Preconditions.checkState(this.maximumWeight == -1, "maximum weight was already set to %s", new Object[]{Long.valueOf(this.maximumWeight)});
        Preconditions.checkState(this.weigher == null, "maximum size can not be combined with weigher");
        if (size >= 0) {
            z = true;
        }
        Preconditions.checkArgument(z, "maximum size must not be negative");
        this.maximumSize = size;
        return this;
    }

    @GwtIncompatible("To be supported")
    public CacheBuilder<K, V> maximumWeight(long weight) {
        boolean z = false;
        Preconditions.checkState(this.maximumWeight == -1, "maximum weight was already set to %s", new Object[]{Long.valueOf(this.maximumWeight)});
        Preconditions.checkState(this.maximumSize == -1, "maximum size was already set to %s", new Object[]{Long.valueOf(this.maximumSize)});
        this.maximumWeight = weight;
        if (weight >= 0) {
            z = true;
        }
        Preconditions.checkArgument(z, "maximum weight must not be negative");
        return this;
    }

    @GwtIncompatible("To be supported")
    public <K1 extends K, V1 extends V> CacheBuilder<K1, V1> weigher(Weigher<? super K1, ? super V1> weigher) {
        Preconditions.checkState(this.weigher == null);
        if (this.strictParsing) {
            Preconditions.checkState(this.maximumSize == -1, "weigher can not be combined with maximum size", new Object[]{Long.valueOf(this.maximumSize)});
        }
        this.weigher = (Weigher) Preconditions.checkNotNull(weigher);
        return me;
    }

    long getMaximumWeight() {
        if (this.expireAfterWriteNanos != 0) {
            if (this.expireAfterAccessNanos != 0) {
                return this.weigher == null ? this.maximumSize : this.maximumWeight;
            }
        }
        return 0;
    }

    <K1 extends K, V1 extends V> Weigher<K1, V1> getWeigher() {
        return (Weigher) MoreObjects.firstNonNull(this.weigher, CacheBuilder$OneWeigher.INSTANCE);
    }

    @GwtIncompatible("java.lang.ref.WeakReference")
    public CacheBuilder<K, V> weakKeys() {
        return setKeyStrength(LocalCache$Strength.WEAK);
    }

    CacheBuilder<K, V> setKeyStrength(LocalCache$Strength strength) {
        Preconditions.checkState(this.keyStrength == null, "Key strength was already set to %s", new Object[]{this.keyStrength});
        this.keyStrength = (LocalCache$Strength) Preconditions.checkNotNull(strength);
        return this;
    }

    LocalCache$Strength getKeyStrength() {
        return (LocalCache$Strength) MoreObjects.firstNonNull(this.keyStrength, LocalCache$Strength.STRONG);
    }

    @GwtIncompatible("java.lang.ref.WeakReference")
    public CacheBuilder<K, V> weakValues() {
        return setValueStrength(LocalCache$Strength.WEAK);
    }

    @GwtIncompatible("java.lang.ref.SoftReference")
    public CacheBuilder<K, V> softValues() {
        return setValueStrength(LocalCache$Strength.SOFT);
    }

    CacheBuilder<K, V> setValueStrength(LocalCache$Strength strength) {
        Preconditions.checkState(this.valueStrength == null, "Value strength was already set to %s", new Object[]{this.valueStrength});
        this.valueStrength = (LocalCache$Strength) Preconditions.checkNotNull(strength);
        return this;
    }

    LocalCache$Strength getValueStrength() {
        return (LocalCache$Strength) MoreObjects.firstNonNull(this.valueStrength, LocalCache$Strength.STRONG);
    }

    public CacheBuilder<K, V> expireAfterWrite(long duration, TimeUnit unit) {
        Preconditions.checkState(this.expireAfterWriteNanos == -1, "expireAfterWrite was already set to %s ns", new Object[]{Long.valueOf(this.expireAfterWriteNanos)});
        Preconditions.checkArgument(duration >= 0, "duration cannot be negative: %s %s", new Object[]{Long.valueOf(duration), unit});
        this.expireAfterWriteNanos = unit.toNanos(duration);
        return this;
    }

    long getExpireAfterWriteNanos() {
        return this.expireAfterWriteNanos == -1 ? 0 : this.expireAfterWriteNanos;
    }

    public CacheBuilder<K, V> expireAfterAccess(long duration, TimeUnit unit) {
        Preconditions.checkState(this.expireAfterAccessNanos == -1, "expireAfterAccess was already set to %s ns", new Object[]{Long.valueOf(this.expireAfterAccessNanos)});
        Preconditions.checkArgument(duration >= 0, "duration cannot be negative: %s %s", new Object[]{Long.valueOf(duration), unit});
        this.expireAfterAccessNanos = unit.toNanos(duration);
        return this;
    }

    long getExpireAfterAccessNanos() {
        return this.expireAfterAccessNanos == -1 ? 0 : this.expireAfterAccessNanos;
    }

    @GwtIncompatible("To be supported (synchronously).")
    public CacheBuilder<K, V> refreshAfterWrite(long duration, TimeUnit unit) {
        Preconditions.checkNotNull(unit);
        Preconditions.checkState(this.refreshNanos == -1, "refresh was already set to %s ns", new Object[]{Long.valueOf(this.refreshNanos)});
        Preconditions.checkArgument(duration > 0, "duration must be positive: %s %s", new Object[]{Long.valueOf(duration), unit});
        this.refreshNanos = unit.toNanos(duration);
        return this;
    }

    long getRefreshNanos() {
        return this.refreshNanos == -1 ? 0 : this.refreshNanos;
    }

    public CacheBuilder<K, V> ticker(Ticker ticker) {
        Preconditions.checkState(this.ticker == null);
        this.ticker = (Ticker) Preconditions.checkNotNull(ticker);
        return this;
    }

    Ticker getTicker(boolean recordsTime) {
        if (this.ticker != null) {
            return this.ticker;
        }
        return recordsTime ? Ticker.systemTicker() : NULL_TICKER;
    }

    @CheckReturnValue
    public <K1 extends K, V1 extends V> CacheBuilder<K1, V1> removalListener(RemovalListener<? super K1, ? super V1> listener) {
        Preconditions.checkState(this.removalListener == null);
        this.removalListener = (RemovalListener) Preconditions.checkNotNull(listener);
        return me;
    }

    <K1 extends K, V1 extends V> RemovalListener<K1, V1> getRemovalListener() {
        return (RemovalListener) MoreObjects.firstNonNull(this.removalListener, CacheBuilder$NullListener.INSTANCE);
    }

    public CacheBuilder<K, V> recordStats() {
        this.statsCounterSupplier = CACHE_STATS_COUNTER;
        return this;
    }

    boolean isRecordingStats() {
        return this.statsCounterSupplier == CACHE_STATS_COUNTER;
    }

    Supplier<? extends StatsCounter> getStatsCounterSupplier() {
        return this.statsCounterSupplier;
    }

    public <K1 extends K, V1 extends V> LoadingCache<K1, V1> build(CacheLoader<? super K1, V1> loader) {
        checkWeightWithWeigher();
        return new LocalCache$LocalLoadingCache(this, loader);
    }

    public <K1 extends K, V1 extends V> Cache<K1, V1> build() {
        checkWeightWithWeigher();
        checkNonLoadingCache();
        return new LocalManualCache(this);
    }

    private void checkNonLoadingCache() {
        Preconditions.checkState(this.refreshNanos == -1, "refreshAfterWrite requires a LoadingCache");
    }

    private void checkWeightWithWeigher() {
        boolean z = false;
        if (this.weigher == null) {
            if (this.maximumWeight == -1) {
                z = true;
            }
            Preconditions.checkState(z, "maximumWeight requires weigher");
        } else if (this.strictParsing) {
            if (this.maximumWeight != -1) {
                z = true;
            }
            Preconditions.checkState(z, "weigher requires maximumWeight");
        } else if (this.maximumWeight == -1) {
            logger.log(Level.WARNING, "ignoring weigher specified without maximumWeight");
        }
    }

    public String toString() {
        ToStringHelper s = MoreObjects.toStringHelper((Object) this);
        if (this.initialCapacity != -1) {
            s.add("initialCapacity", this.initialCapacity);
        }
        if (this.concurrencyLevel != -1) {
            s.add("concurrencyLevel", this.concurrencyLevel);
        }
        if (this.maximumSize != -1) {
            s.add("maximumSize", this.maximumSize);
        }
        if (this.maximumWeight != -1) {
            s.add("maximumWeight", this.maximumWeight);
        }
        if (this.expireAfterWriteNanos != -1) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.expireAfterWriteNanos);
            stringBuilder.append("ns");
            s.add("expireAfterWrite", stringBuilder.toString());
        }
        if (this.expireAfterAccessNanos != -1) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(this.expireAfterAccessNanos);
            stringBuilder.append("ns");
            s.add("expireAfterAccess", stringBuilder.toString());
        }
        if (this.keyStrength != null) {
            s.add("keyStrength", Ascii.toLowerCase(this.keyStrength.toString()));
        }
        if (this.valueStrength != null) {
            s.add("valueStrength", Ascii.toLowerCase(this.valueStrength.toString()));
        }
        if (this.keyEquivalence != null) {
            s.addValue((Object) "keyEquivalence");
        }
        if (this.valueEquivalence != null) {
            s.addValue((Object) "valueEquivalence");
        }
        if (this.removalListener != null) {
            s.addValue((Object) "removalListener");
        }
        return s.toString();
    }
}
