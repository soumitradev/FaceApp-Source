package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.SortedLists.KeyAbsentBehavior;
import com.google.common.collect.SortedLists.KeyPresentBehavior;
import java.io.Serializable;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import javax.annotation.Nullable;

@GwtIncompatible("NavigableMap")
@Beta
public class ImmutableRangeMap<K extends Comparable<?>, V> implements RangeMap<K, V>, Serializable {
    private static final ImmutableRangeMap<Comparable<?>, Object> EMPTY = new ImmutableRangeMap(ImmutableList.of(), ImmutableList.of());
    private static final long serialVersionUID = 0;
    private final transient ImmutableList<Range<K>> ranges;
    private final transient ImmutableList<V> values;

    public static <K extends Comparable<?>, V> ImmutableRangeMap<K, V> of() {
        return EMPTY;
    }

    public static <K extends Comparable<?>, V> ImmutableRangeMap<K, V> of(Range<K> range, V value) {
        return new ImmutableRangeMap(ImmutableList.of(range), ImmutableList.of(value));
    }

    public static <K extends Comparable<?>, V> ImmutableRangeMap<K, V> copyOf(RangeMap<K, ? extends V> rangeMap) {
        if (rangeMap instanceof ImmutableRangeMap) {
            return (ImmutableRangeMap) rangeMap;
        }
        Map<Range<K>, ? extends V> map = rangeMap.asMapOfRanges();
        ImmutableList$Builder<Range<K>> rangesBuilder = new ImmutableList$Builder(map.size());
        ImmutableList$Builder<V> valuesBuilder = new ImmutableList$Builder(map.size());
        for (Entry<Range<K>, ? extends V> entry : map.entrySet()) {
            rangesBuilder.add(entry.getKey());
            valuesBuilder.add(entry.getValue());
        }
        return new ImmutableRangeMap(rangesBuilder.build(), valuesBuilder.build());
    }

    public static <K extends Comparable<?>, V> ImmutableRangeMap$Builder<K, V> builder() {
        return new ImmutableRangeMap$Builder();
    }

    ImmutableRangeMap(ImmutableList<Range<K>> ranges, ImmutableList<V> values) {
        this.ranges = ranges;
        this.values = values;
    }

    @Nullable
    public V get(K key) {
        int index = SortedLists.binarySearch(this.ranges, Range.lowerBoundFn(), Cut.belowValue(key), KeyPresentBehavior.ANY_PRESENT, KeyAbsentBehavior.NEXT_LOWER);
        V v = null;
        if (index == -1) {
            return null;
        }
        if (((Range) this.ranges.get(index)).contains(key)) {
            v = this.values.get(index);
        }
        return v;
    }

    @Nullable
    public Entry<Range<K>, V> getEntry(K key) {
        int index = SortedLists.binarySearch(this.ranges, Range.lowerBoundFn(), Cut.belowValue(key), KeyPresentBehavior.ANY_PRESENT, KeyAbsentBehavior.NEXT_LOWER);
        Entry<Range<K>, V> entry = null;
        if (index == -1) {
            return null;
        }
        Range<K> range = (Range) this.ranges.get(index);
        if (range.contains(key)) {
            entry = Maps.immutableEntry(range, this.values.get(index));
        }
        return entry;
    }

    public Range<K> span() {
        if (this.ranges.isEmpty()) {
            throw new NoSuchElementException();
        }
        return Range.create(((Range) this.ranges.get(0)).lowerBound, ((Range) this.ranges.get(this.ranges.size() - 1)).upperBound);
    }

    public void put(Range<K> range, V v) {
        throw new UnsupportedOperationException();
    }

    public void putAll(RangeMap<K, V> rangeMap) {
        throw new UnsupportedOperationException();
    }

    public void clear() {
        throw new UnsupportedOperationException();
    }

    public void remove(Range<K> range) {
        throw new UnsupportedOperationException();
    }

    public ImmutableMap<Range<K>, V> asMapOfRanges() {
        if (this.ranges.isEmpty()) {
            return ImmutableMap.of();
        }
        return new ImmutableSortedMap(new RegularImmutableSortedSet(this.ranges, Range.RANGE_LEX_ORDERING), this.values);
    }

    public ImmutableMap<Range<K>, V> asDescendingMapOfRanges() {
        if (this.ranges.isEmpty()) {
            return ImmutableMap.of();
        }
        return new ImmutableSortedMap(new RegularImmutableSortedSet(this.ranges.reverse(), Range.RANGE_LEX_ORDERING.reverse()), this.values.reverse());
    }

    public ImmutableRangeMap<K, V> subRangeMap(Range<K> range) {
        if (((Range) Preconditions.checkNotNull(range)).isEmpty()) {
            return of();
        }
        if (!this.ranges.isEmpty()) {
            if (!range.encloses(span())) {
                int lowerIndex = SortedLists.binarySearch(this.ranges, Range.upperBoundFn(), range.lowerBound, KeyPresentBehavior.FIRST_AFTER, KeyAbsentBehavior.NEXT_HIGHER);
                int upperIndex = SortedLists.binarySearch(this.ranges, Range.lowerBoundFn(), range.upperBound, KeyPresentBehavior.ANY_PRESENT, KeyAbsentBehavior.NEXT_HIGHER);
                if (lowerIndex >= upperIndex) {
                    return of();
                }
                int len = upperIndex - lowerIndex;
                return new ImmutableRangeMap$2(this, new ImmutableRangeMap$1(this, len, lowerIndex, range), this.values.subList(lowerIndex, upperIndex), range, this);
            }
        }
        return this;
    }

    public int hashCode() {
        return asMapOfRanges().hashCode();
    }

    public boolean equals(@Nullable Object o) {
        if (!(o instanceof RangeMap)) {
            return false;
        }
        return asMapOfRanges().equals(((RangeMap) o).asMapOfRanges());
    }

    public String toString() {
        return asMapOfRanges().toString();
    }

    Object writeReplace() {
        return new ImmutableRangeMap$SerializedForm(asMapOfRanges());
    }
}
