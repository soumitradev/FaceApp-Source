package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMapEntrySet.RegularEntrySet;
import com.google.firebase.analytics.FirebaseAnalytics$Param;
import java.util.Map.Entry;
import javax.annotation.Nullable;

@GwtCompatible(emulated = true, serializable = true)
class RegularImmutableBiMap<K, V> extends ImmutableBiMap<K, V> {
    static final RegularImmutableBiMap<Object, Object> EMPTY = new RegularImmutableBiMap(null, null, ImmutableMap.EMPTY_ENTRY_ARRAY, 0, 0);
    static final double MAX_LOAD_FACTOR = 1.2d;
    private final transient Entry<K, V>[] entries;
    private final transient int hashCode;
    private transient ImmutableBiMap<V, K> inverse;
    private final transient ImmutableMapEntry<K, V>[] keyTable;
    private final transient int mask;
    private final transient ImmutableMapEntry<K, V>[] valueTable;

    static <K, V> RegularImmutableBiMap<K, V> fromEntries(Entry<K, V>... entries) {
        return fromEntryArray(entries.length, entries);
    }

    static <K, V> RegularImmutableBiMap<K, V> fromEntryArray(int n, Entry<K, V>[] entryArray) {
        Entry<K, V>[] entries;
        int i = n;
        Entry<K, V>[] entryArr = entryArray;
        Preconditions.checkPositionIndex(i, entryArr.length);
        int tableSize = Hashing.closedTableSize(i, 858993459);
        int mask = tableSize - 1;
        ImmutableMapEntry<K, V>[] keyTable = ImmutableMapEntry.createEntryArray(tableSize);
        ImmutableMapEntry<K, V>[] valueTable = ImmutableMapEntry.createEntryArray(tableSize);
        if (i == entryArr.length) {
            entries = entryArr;
        } else {
            entries = ImmutableMapEntry.createEntryArray(n);
        }
        Entry<K, V>[] entries2 = entries;
        int hashCode = 0;
        int i2 = 0;
        while (i2 < i) {
            ImmutableMapEntry<K, V> immutableMapEntry;
            Entry<K, V> entry = entryArr[i2];
            K key = entry.getKey();
            V value = entry.getValue();
            CollectPreconditions.checkEntryNotNull(key, value);
            int keyHash = key.hashCode();
            int valueHash = value.hashCode();
            int keyBucket = Hashing.smear(keyHash) & mask;
            int valueBucket = Hashing.smear(valueHash) & mask;
            ImmutableMapEntry<K, V> nextInKeyBucket = keyTable[keyBucket];
            RegularImmutableMap.checkNoConflictInKeyBucket(key, entry, nextInKeyBucket);
            ImmutableMapEntry<K, V> nextInValueBucket = valueTable[valueBucket];
            checkNoConflictInValueBucket(value, entry, nextInValueBucket);
            if (nextInValueBucket == null && nextInKeyBucket == null) {
                boolean reusable = (entry instanceof ImmutableMapEntry) && ((ImmutableMapEntry) entry).isReusable();
                if (reusable) {
                    boolean z = reusable;
                    immutableMapEntry = (ImmutableMapEntry) entry;
                } else {
                    immutableMapEntry = new ImmutableMapEntry(key, value);
                }
            } else {
                immutableMapEntry = new NonTerminalImmutableBiMapEntry(key, value, nextInKeyBucket, nextInValueBucket);
            }
            keyTable[keyBucket] = immutableMapEntry;
            valueTable[valueBucket] = immutableMapEntry;
            entries2[i2] = immutableMapEntry;
            hashCode += keyHash ^ valueHash;
            i2++;
            i = n;
            entryArr = entryArray;
        }
        return new RegularImmutableBiMap(keyTable, valueTable, entries2, mask, hashCode);
    }

    private RegularImmutableBiMap(ImmutableMapEntry<K, V>[] keyTable, ImmutableMapEntry<K, V>[] valueTable, Entry<K, V>[] entries, int mask, int hashCode) {
        this.keyTable = keyTable;
        this.valueTable = valueTable;
        this.entries = entries;
        this.mask = mask;
        this.hashCode = hashCode;
    }

    private static void checkNoConflictInValueBucket(Object value, Entry<?, ?> entry, @Nullable ImmutableMapEntry<?, ?> valueBucketHead) {
        while (valueBucketHead != null) {
            ImmutableMap.checkNoConflict(value.equals(valueBucketHead.getValue()) ^ 1, FirebaseAnalytics$Param.VALUE, entry, valueBucketHead);
            valueBucketHead = valueBucketHead.getNextInValueBucket();
        }
    }

    @Nullable
    public V get(@Nullable Object key) {
        return this.keyTable == null ? null : RegularImmutableMap.get(key, this.keyTable, this.mask);
    }

    ImmutableSet<Entry<K, V>> createEntrySet() {
        return isEmpty() ? ImmutableSet.of() : new RegularEntrySet(this, this.entries);
    }

    boolean isHashCodeFast() {
        return true;
    }

    public int hashCode() {
        return this.hashCode;
    }

    boolean isPartialView() {
        return false;
    }

    public int size() {
        return this.entries.length;
    }

    public ImmutableBiMap<V, K> inverse() {
        if (isEmpty()) {
            return ImmutableBiMap.of();
        }
        ImmutableBiMap<V, K> regularImmutableBiMap$Inverse;
        ImmutableBiMap<V, K> result = this.inverse;
        if (result == null) {
            regularImmutableBiMap$Inverse = new RegularImmutableBiMap$Inverse(this, null);
            this.inverse = regularImmutableBiMap$Inverse;
        } else {
            regularImmutableBiMap$Inverse = result;
        }
        return regularImmutableBiMap$Inverse;
    }
}
