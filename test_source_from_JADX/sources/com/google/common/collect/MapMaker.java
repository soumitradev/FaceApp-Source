package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Ascii;
import com.google.common.base.Equivalence;
import com.google.common.base.Function;
import com.google.common.base.MoreObjects;
import com.google.common.base.MoreObjects.ToStringHelper;
import com.google.common.base.Preconditions;
import com.google.common.base.Ticker;
import com.google.common.collect.MapMakerInternalMap.Strength;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

@GwtCompatible(emulated = true)
public final class MapMaker extends GenericMapMaker<Object, Object> {
    private static final int DEFAULT_CONCURRENCY_LEVEL = 4;
    private static final int DEFAULT_EXPIRATION_NANOS = 0;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    static final int UNSET_INT = -1;
    int concurrencyLevel = -1;
    long expireAfterAccessNanos = -1;
    long expireAfterWriteNanos = -1;
    int initialCapacity = -1;
    Equivalence<Object> keyEquivalence;
    Strength keyStrength;
    int maximumSize = -1;
    MapMaker$RemovalCause nullRemovalCause;
    Ticker ticker;
    boolean useCustomMap;
    Strength valueStrength;

    @GwtIncompatible("To be supported")
    MapMaker keyEquivalence(Equivalence<Object> equivalence) {
        Preconditions.checkState(this.keyEquivalence == null, "key equivalence was already set to %s", new Object[]{this.keyEquivalence});
        this.keyEquivalence = (Equivalence) Preconditions.checkNotNull(equivalence);
        this.useCustomMap = true;
        return this;
    }

    Equivalence<Object> getKeyEquivalence() {
        return (Equivalence) MoreObjects.firstNonNull(this.keyEquivalence, getKeyStrength().defaultEquivalence());
    }

    public MapMaker initialCapacity(int initialCapacity) {
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

    @Deprecated
    MapMaker maximumSize(int size) {
        boolean z = false;
        Preconditions.checkState(this.maximumSize == -1, "maximum size was already set to %s", new Object[]{Integer.valueOf(this.maximumSize)});
        if (size >= 0) {
            z = true;
        }
        Preconditions.checkArgument(z, "maximum size must not be negative");
        this.maximumSize = size;
        this.useCustomMap = true;
        if (this.maximumSize == 0) {
            this.nullRemovalCause = MapMaker$RemovalCause.SIZE;
        }
        return this;
    }

    public MapMaker concurrencyLevel(int concurrencyLevel) {
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

    @GwtIncompatible("java.lang.ref.WeakReference")
    public MapMaker weakKeys() {
        return setKeyStrength(Strength.WEAK);
    }

    MapMaker setKeyStrength(Strength strength) {
        boolean z = false;
        Preconditions.checkState(this.keyStrength == null, "Key strength was already set to %s", new Object[]{this.keyStrength});
        this.keyStrength = (Strength) Preconditions.checkNotNull(strength);
        if (this.keyStrength != Strength.SOFT) {
            z = true;
        }
        Preconditions.checkArgument(z, "Soft keys are not supported");
        if (strength != Strength.STRONG) {
            this.useCustomMap = true;
        }
        return this;
    }

    Strength getKeyStrength() {
        return (Strength) MoreObjects.firstNonNull(this.keyStrength, Strength.STRONG);
    }

    @GwtIncompatible("java.lang.ref.WeakReference")
    public MapMaker weakValues() {
        return setValueStrength(Strength.WEAK);
    }

    @GwtIncompatible("java.lang.ref.SoftReference")
    @Deprecated
    MapMaker softValues() {
        return setValueStrength(Strength.SOFT);
    }

    MapMaker setValueStrength(Strength strength) {
        Preconditions.checkState(this.valueStrength == null, "Value strength was already set to %s", new Object[]{this.valueStrength});
        this.valueStrength = (Strength) Preconditions.checkNotNull(strength);
        if (strength != Strength.STRONG) {
            this.useCustomMap = true;
        }
        return this;
    }

    Strength getValueStrength() {
        return (Strength) MoreObjects.firstNonNull(this.valueStrength, Strength.STRONG);
    }

    @Deprecated
    MapMaker expireAfterWrite(long duration, TimeUnit unit) {
        checkExpiration(duration, unit);
        this.expireAfterWriteNanos = unit.toNanos(duration);
        if (duration == 0 && this.nullRemovalCause == null) {
            this.nullRemovalCause = MapMaker$RemovalCause.EXPIRED;
        }
        this.useCustomMap = true;
        return this;
    }

    private void checkExpiration(long duration, TimeUnit unit) {
        Preconditions.checkState(this.expireAfterWriteNanos == -1, "expireAfterWrite was already set to %s ns", new Object[]{Long.valueOf(this.expireAfterWriteNanos)});
        Preconditions.checkState(this.expireAfterAccessNanos == -1, "expireAfterAccess was already set to %s ns", new Object[]{Long.valueOf(this.expireAfterAccessNanos)});
        Preconditions.checkArgument(duration >= 0, "duration cannot be negative: %s %s", new Object[]{Long.valueOf(duration), unit});
    }

    long getExpireAfterWriteNanos() {
        return this.expireAfterWriteNanos == -1 ? 0 : this.expireAfterWriteNanos;
    }

    @GwtIncompatible("To be supported")
    @Deprecated
    MapMaker expireAfterAccess(long duration, TimeUnit unit) {
        checkExpiration(duration, unit);
        this.expireAfterAccessNanos = unit.toNanos(duration);
        if (duration == 0 && this.nullRemovalCause == null) {
            this.nullRemovalCause = MapMaker$RemovalCause.EXPIRED;
        }
        this.useCustomMap = true;
        return this;
    }

    long getExpireAfterAccessNanos() {
        return this.expireAfterAccessNanos == -1 ? 0 : this.expireAfterAccessNanos;
    }

    Ticker getTicker() {
        return (Ticker) MoreObjects.firstNonNull(this.ticker, Ticker.systemTicker());
    }

    @GwtIncompatible("To be supported")
    @Deprecated
    <K, V> GenericMapMaker<K, V> removalListener(MapMaker$RemovalListener<K, V> listener) {
        Preconditions.checkState(this.removalListener == null);
        this.removalListener = (MapMaker$RemovalListener) Preconditions.checkNotNull(listener);
        this.useCustomMap = true;
        return me;
    }

    public <K, V> ConcurrentMap<K, V> makeMap() {
        if (!this.useCustomMap) {
            return new ConcurrentHashMap(getInitialCapacity(), 0.75f, getConcurrencyLevel());
        }
        return this.nullRemovalCause == null ? new MapMakerInternalMap(this) : new MapMaker$NullConcurrentMap(this);
    }

    @GwtIncompatible("MapMakerInternalMap")
    <K, V> MapMakerInternalMap<K, V> makeCustomMap() {
        return new MapMakerInternalMap(this);
    }

    @Deprecated
    <K, V> ConcurrentMap<K, V> makeComputingMap(Function<? super K, ? extends V> computingFunction) {
        return this.nullRemovalCause == null ? new MapMaker$ComputingMapAdapter(this, computingFunction) : new MapMaker$NullComputingConcurrentMap(this, computingFunction);
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
        if (this.removalListener != null) {
            s.addValue((Object) "removalListener");
        }
        return s.toString();
    }
}
