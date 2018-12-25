package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.NoSuchElementException;
import javax.annotation.Nullable;

@GwtIncompatible("NavigableMap")
@Beta
public final class TreeRangeMap<K extends Comparable, V> implements RangeMap<K, V> {
    private static final RangeMap EMPTY_SUB_RANGE_MAP = new C00671();
    private final NavigableMap<Cut<K>, TreeRangeMap$RangeMapEntry<K, V>> entriesByLowerBound = Maps.newTreeMap();

    /* renamed from: com.google.common.collect.TreeRangeMap$1 */
    static class C00671 implements RangeMap {
        C00671() {
        }

        @Nullable
        public Object get(Comparable key) {
            return null;
        }

        @Nullable
        public Entry<Range, Object> getEntry(Comparable key) {
            return null;
        }

        public Range span() {
            throw new NoSuchElementException();
        }

        public void put(Range range, Object value) {
            Preconditions.checkNotNull(range);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Cannot insert range ");
            stringBuilder.append(range);
            stringBuilder.append(" into an empty subRangeMap");
            throw new IllegalArgumentException(stringBuilder.toString());
        }

        public void putAll(RangeMap rangeMap) {
            if (!rangeMap.asMapOfRanges().isEmpty()) {
                throw new IllegalArgumentException("Cannot putAll(nonEmptyRangeMap) into an empty subRangeMap");
            }
        }

        public void clear() {
        }

        public void remove(Range range) {
            Preconditions.checkNotNull(range);
        }

        public Map<Range, Object> asMapOfRanges() {
            return Collections.emptyMap();
        }

        public Map<Range, Object> asDescendingMapOfRanges() {
            return Collections.emptyMap();
        }

        public RangeMap subRangeMap(Range range) {
            Preconditions.checkNotNull(range);
            return this;
        }
    }

    private class SubRangeMap implements RangeMap<K, V> {
        private final Range<K> subRange;

        SubRangeMap(Range<K> subRange) {
            this.subRange = subRange;
        }

        @Nullable
        public V get(K key) {
            return this.subRange.contains(key) ? TreeRangeMap.this.get(key) : null;
        }

        @Nullable
        public Entry<Range<K>, V> getEntry(K key) {
            if (this.subRange.contains(key)) {
                Entry<Range<K>, V> entry = TreeRangeMap.this.getEntry(key);
                if (entry != null) {
                    return Maps.immutableEntry(((Range) entry.getKey()).intersection(this.subRange), entry.getValue());
                }
            }
            return null;
        }

        public Range<K> span() {
            Entry<Cut<K>, TreeRangeMap$RangeMapEntry<K, V>> lowerEntry = TreeRangeMap.this.entriesByLowerBound.floorEntry(this.subRange.lowerBound);
            Cut<K> lowerBound;
            if (lowerEntry == null || ((TreeRangeMap$RangeMapEntry) lowerEntry.getValue()).getUpperBound().compareTo(this.subRange.lowerBound) <= 0) {
                lowerBound = (Cut) TreeRangeMap.this.entriesByLowerBound.ceilingKey(this.subRange.lowerBound);
                if (lowerBound != null) {
                    if (lowerBound.compareTo(this.subRange.upperBound) >= 0) {
                    }
                }
                throw new NoSuchElementException();
            }
            lowerBound = this.subRange.lowerBound;
            Entry<Cut<K>, TreeRangeMap$RangeMapEntry<K, V>> upperEntry = TreeRangeMap.this.entriesByLowerBound.lowerEntry(this.subRange.upperBound);
            if (upperEntry == null) {
                throw new NoSuchElementException();
            }
            Cut<K> upperBound;
            if (((TreeRangeMap$RangeMapEntry) upperEntry.getValue()).getUpperBound().compareTo(this.subRange.upperBound) >= 0) {
                upperBound = this.subRange.upperBound;
            } else {
                upperBound = ((TreeRangeMap$RangeMapEntry) upperEntry.getValue()).getUpperBound();
            }
            return Range.create(lowerBound, upperBound);
        }

        public void put(Range<K> range, V value) {
            Preconditions.checkArgument(this.subRange.encloses(range), "Cannot put range %s into a subRangeMap(%s)", new Object[]{range, this.subRange});
            TreeRangeMap.this.put(range, value);
        }

        public void putAll(RangeMap<K, V> rangeMap) {
            if (!rangeMap.asMapOfRanges().isEmpty()) {
                Object[] objArr = new Object[]{rangeMap.span(), this.subRange};
                Preconditions.checkArgument(this.subRange.encloses(rangeMap.span()), "Cannot putAll rangeMap with span %s into a subRangeMap(%s)", objArr);
                TreeRangeMap.this.putAll(rangeMap);
            }
        }

        public void clear() {
            TreeRangeMap.this.remove(this.subRange);
        }

        public void remove(Range<K> range) {
            if (range.isConnected(this.subRange)) {
                TreeRangeMap.this.remove(range.intersection(this.subRange));
            }
        }

        public RangeMap<K, V> subRangeMap(Range<K> range) {
            if (range.isConnected(this.subRange)) {
                return TreeRangeMap.this.subRangeMap(range.intersection(this.subRange));
            }
            return TreeRangeMap.this.emptySubRangeMap();
        }

        public Map<Range<K>, V> asMapOfRanges() {
            return new TreeRangeMap$SubRangeMap$SubRangeMapAsMap(this);
        }

        public Map<Range<K>, V> asDescendingMapOfRanges() {
            return new TreeRangeMap$SubRangeMap$1(this);
        }

        public boolean equals(@Nullable Object o) {
            if (!(o instanceof RangeMap)) {
                return false;
            }
            return asMapOfRanges().equals(((RangeMap) o).asMapOfRanges());
        }

        public int hashCode() {
            return asMapOfRanges().hashCode();
        }

        public String toString() {
            return asMapOfRanges().toString();
        }
    }

    public static <K extends Comparable, V> TreeRangeMap<K, V> create() {
        return new TreeRangeMap();
    }

    private TreeRangeMap() {
    }

    @Nullable
    public V get(K key) {
        Entry<Range<K>, V> entry = getEntry(key);
        return entry == null ? null : entry.getValue();
    }

    @Nullable
    public Entry<Range<K>, V> getEntry(K key) {
        Entry<Cut<K>, TreeRangeMap$RangeMapEntry<K, V>> mapEntry = this.entriesByLowerBound.floorEntry(Cut.belowValue(key));
        if (mapEntry == null || !((TreeRangeMap$RangeMapEntry) mapEntry.getValue()).contains(key)) {
            return null;
        }
        return (Entry) mapEntry.getValue();
    }

    public void put(Range<K> range, V value) {
        if (!range.isEmpty()) {
            Preconditions.checkNotNull(value);
            remove(range);
            this.entriesByLowerBound.put(range.lowerBound, new TreeRangeMap$RangeMapEntry(range, value));
        }
    }

    public void putAll(RangeMap<K, V> rangeMap) {
        for (Entry<Range<K>, V> entry : rangeMap.asMapOfRanges().entrySet()) {
            put((Range) entry.getKey(), entry.getValue());
        }
    }

    public void clear() {
        this.entriesByLowerBound.clear();
    }

    public Range<K> span() {
        Entry<Cut<K>, TreeRangeMap$RangeMapEntry<K, V>> firstEntry = this.entriesByLowerBound.firstEntry();
        Entry<Cut<K>, TreeRangeMap$RangeMapEntry<K, V>> lastEntry = this.entriesByLowerBound.lastEntry();
        if (firstEntry != null) {
            return Range.create(((TreeRangeMap$RangeMapEntry) firstEntry.getValue()).getKey().lowerBound, ((TreeRangeMap$RangeMapEntry) lastEntry.getValue()).getKey().upperBound);
        }
        throw new NoSuchElementException();
    }

    private void putRangeMapEntry(Cut<K> lowerBound, Cut<K> upperBound, V value) {
        this.entriesByLowerBound.put(lowerBound, new TreeRangeMap$RangeMapEntry(lowerBound, upperBound, value));
    }

    public void remove(Range<K> rangeToRemove) {
        if (!rangeToRemove.isEmpty()) {
            Entry<Cut<K>, TreeRangeMap$RangeMapEntry<K, V>> mapEntryBelowToTruncate = this.entriesByLowerBound.lowerEntry(rangeToRemove.lowerBound);
            if (mapEntryBelowToTruncate != null) {
                TreeRangeMap$RangeMapEntry<K, V> rangeMapEntry = (TreeRangeMap$RangeMapEntry) mapEntryBelowToTruncate.getValue();
                if (rangeMapEntry.getUpperBound().compareTo(rangeToRemove.lowerBound) > 0) {
                    if (rangeMapEntry.getUpperBound().compareTo(rangeToRemove.upperBound) > 0) {
                        putRangeMapEntry(rangeToRemove.upperBound, rangeMapEntry.getUpperBound(), ((TreeRangeMap$RangeMapEntry) mapEntryBelowToTruncate.getValue()).getValue());
                    }
                    putRangeMapEntry(rangeMapEntry.getLowerBound(), rangeToRemove.lowerBound, ((TreeRangeMap$RangeMapEntry) mapEntryBelowToTruncate.getValue()).getValue());
                }
            }
            Entry<Cut<K>, TreeRangeMap$RangeMapEntry<K, V>> mapEntryAboveToTruncate = this.entriesByLowerBound.lowerEntry(rangeToRemove.upperBound);
            if (mapEntryAboveToTruncate != null) {
                TreeRangeMap$RangeMapEntry<K, V> rangeMapEntry2 = (TreeRangeMap$RangeMapEntry) mapEntryAboveToTruncate.getValue();
                if (rangeMapEntry2.getUpperBound().compareTo(rangeToRemove.upperBound) > 0) {
                    putRangeMapEntry(rangeToRemove.upperBound, rangeMapEntry2.getUpperBound(), ((TreeRangeMap$RangeMapEntry) mapEntryAboveToTruncate.getValue()).getValue());
                    this.entriesByLowerBound.remove(rangeToRemove.lowerBound);
                }
            }
            this.entriesByLowerBound.subMap(rangeToRemove.lowerBound, rangeToRemove.upperBound).clear();
        }
    }

    public Map<Range<K>, V> asMapOfRanges() {
        return new TreeRangeMap$AsMapOfRanges(this, this.entriesByLowerBound.values());
    }

    public Map<Range<K>, V> asDescendingMapOfRanges() {
        return new TreeRangeMap$AsMapOfRanges(this, this.entriesByLowerBound.descendingMap().values());
    }

    public RangeMap<K, V> subRangeMap(Range<K> subRange) {
        if (subRange.equals(Range.all())) {
            return this;
        }
        return new SubRangeMap(subRange);
    }

    private RangeMap<K, V> emptySubRangeMap() {
        return EMPTY_SUB_RANGE_MAP;
    }

    public boolean equals(@Nullable Object o) {
        if (!(o instanceof RangeMap)) {
            return false;
        }
        return asMapOfRanges().equals(((RangeMap) o).asMapOfRanges());
    }

    public int hashCode() {
        return asMapOfRanges().hashCode();
    }

    public String toString() {
        return this.entriesByLowerBound.values().toString();
    }
}
