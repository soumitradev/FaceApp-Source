package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Objects;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.annotation.Nullable;

@GwtCompatible(emulated = true)
public final class HashBiMap<K, V> extends Maps$IteratorBasedAbstractMap<K, V> implements BiMap<K, V>, Serializable {
    private static final double LOAD_FACTOR = 1.0d;
    @GwtIncompatible("Not needed in emulated source")
    private static final long serialVersionUID = 0;
    private transient BiEntry<K, V> firstInKeyInsertionOrder;
    private transient BiEntry<K, V>[] hashTableKToV;
    private transient BiEntry<K, V>[] hashTableVToK;
    private transient BiMap<V, K> inverse;
    private transient BiEntry<K, V> lastInKeyInsertionOrder;
    private transient int mask;
    private transient int modCount;
    private transient int size;

    private static final class BiEntry<K, V> extends ImmutableEntry<K, V> {
        final int keyHash;
        @Nullable
        BiEntry<K, V> nextInKToVBucket;
        @Nullable
        BiEntry<K, V> nextInKeyInsertionOrder;
        @Nullable
        BiEntry<K, V> nextInVToKBucket;
        @Nullable
        BiEntry<K, V> prevInKeyInsertionOrder;
        final int valueHash;

        BiEntry(K key, int keyHash, V value, int valueHash) {
            super(key, value);
            this.keyHash = keyHash;
            this.valueHash = valueHash;
        }
    }

    public /* bridge */ /* synthetic */ Set entrySet() {
        return super.entrySet();
    }

    public static <K, V> HashBiMap<K, V> create() {
        return create(16);
    }

    public static <K, V> HashBiMap<K, V> create(int expectedSize) {
        return new HashBiMap(expectedSize);
    }

    public static <K, V> HashBiMap<K, V> create(Map<? extends K, ? extends V> map) {
        HashBiMap<K, V> bimap = create(map.size());
        bimap.putAll(map);
        return bimap;
    }

    private HashBiMap(int expectedSize) {
        init(expectedSize);
    }

    private void init(int expectedSize) {
        CollectPreconditions.checkNonnegative(expectedSize, "expectedSize");
        int tableSize = Hashing.closedTableSize(expectedSize, 0);
        this.hashTableKToV = createTable(tableSize);
        this.hashTableVToK = createTable(tableSize);
        this.firstInKeyInsertionOrder = null;
        this.lastInKeyInsertionOrder = null;
        this.size = 0;
        this.mask = tableSize - 1;
        this.modCount = 0;
    }

    private void delete(BiEntry<K, V> entry) {
        BiEntry<K, V> bucketEntry;
        int keyBucket = entry.keyHash & this.mask;
        BiEntry<K, V> prevBucketEntry = null;
        for (bucketEntry = this.hashTableKToV[keyBucket]; bucketEntry != entry; bucketEntry = bucketEntry.nextInKToVBucket) {
            prevBucketEntry = bucketEntry;
        }
        if (prevBucketEntry == null) {
            this.hashTableKToV[keyBucket] = entry.nextInKToVBucket;
        } else {
            prevBucketEntry.nextInKToVBucket = entry.nextInKToVBucket;
        }
        int valueBucket = this.mask & entry.valueHash;
        prevBucketEntry = null;
        for (bucketEntry = this.hashTableVToK[valueBucket]; bucketEntry != entry; bucketEntry = bucketEntry.nextInVToKBucket) {
            prevBucketEntry = bucketEntry;
        }
        if (prevBucketEntry == null) {
            this.hashTableVToK[valueBucket] = entry.nextInVToKBucket;
        } else {
            prevBucketEntry.nextInVToKBucket = entry.nextInVToKBucket;
        }
        if (entry.prevInKeyInsertionOrder == null) {
            this.firstInKeyInsertionOrder = entry.nextInKeyInsertionOrder;
        } else {
            entry.prevInKeyInsertionOrder.nextInKeyInsertionOrder = entry.nextInKeyInsertionOrder;
        }
        if (entry.nextInKeyInsertionOrder == null) {
            this.lastInKeyInsertionOrder = entry.prevInKeyInsertionOrder;
        } else {
            entry.nextInKeyInsertionOrder.prevInKeyInsertionOrder = entry.prevInKeyInsertionOrder;
        }
        this.size--;
        this.modCount++;
    }

    private void insert(BiEntry<K, V> entry, @Nullable BiEntry<K, V> oldEntryForKey) {
        int keyBucket = entry.keyHash & this.mask;
        entry.nextInKToVBucket = this.hashTableKToV[keyBucket];
        this.hashTableKToV[keyBucket] = entry;
        int valueBucket = entry.valueHash & this.mask;
        entry.nextInVToKBucket = this.hashTableVToK[valueBucket];
        this.hashTableVToK[valueBucket] = entry;
        if (oldEntryForKey == null) {
            entry.prevInKeyInsertionOrder = this.lastInKeyInsertionOrder;
            entry.nextInKeyInsertionOrder = null;
            if (this.lastInKeyInsertionOrder == null) {
                this.firstInKeyInsertionOrder = entry;
            } else {
                this.lastInKeyInsertionOrder.nextInKeyInsertionOrder = entry;
            }
            this.lastInKeyInsertionOrder = entry;
        } else {
            entry.prevInKeyInsertionOrder = oldEntryForKey.prevInKeyInsertionOrder;
            if (entry.prevInKeyInsertionOrder == null) {
                this.firstInKeyInsertionOrder = entry;
            } else {
                entry.prevInKeyInsertionOrder.nextInKeyInsertionOrder = entry;
            }
            entry.nextInKeyInsertionOrder = oldEntryForKey.nextInKeyInsertionOrder;
            if (entry.nextInKeyInsertionOrder == null) {
                this.lastInKeyInsertionOrder = entry;
            } else {
                entry.nextInKeyInsertionOrder.prevInKeyInsertionOrder = entry;
            }
        }
        this.size++;
        this.modCount++;
    }

    private BiEntry<K, V> seekByKey(@Nullable Object key, int keyHash) {
        BiEntry<K, V> entry = this.hashTableKToV[this.mask & keyHash];
        while (entry != null) {
            if (keyHash == entry.keyHash && Objects.equal(key, entry.key)) {
                return entry;
            }
            entry = entry.nextInKToVBucket;
        }
        return null;
    }

    private BiEntry<K, V> seekByValue(@Nullable Object value, int valueHash) {
        BiEntry<K, V> entry = this.hashTableVToK[this.mask & valueHash];
        while (entry != null) {
            if (valueHash == entry.valueHash && Objects.equal(value, entry.value)) {
                return entry;
            }
            entry = entry.nextInVToKBucket;
        }
        return null;
    }

    public boolean containsKey(@Nullable Object key) {
        return seekByKey(key, Hashing.smearedHash(key)) != null;
    }

    public boolean containsValue(@Nullable Object value) {
        return seekByValue(value, Hashing.smearedHash(value)) != null;
    }

    @Nullable
    public V get(@Nullable Object key) {
        return Maps.valueOrNull(seekByKey(key, Hashing.smearedHash(key)));
    }

    public V put(@Nullable K key, @Nullable V value) {
        return put(key, value, false);
    }

    public V forcePut(@Nullable K key, @Nullable V value) {
        return put(key, value, true);
    }

    private V put(@Nullable K key, @Nullable V value, boolean force) {
        int keyHash = Hashing.smearedHash(key);
        int valueHash = Hashing.smearedHash(value);
        BiEntry<K, V> oldEntryForKey = seekByKey(key, keyHash);
        if (oldEntryForKey != null && valueHash == oldEntryForKey.valueHash && Objects.equal(value, oldEntryForKey.value)) {
            return value;
        }
        BiEntry<K, V> oldEntryForValue = seekByValue(value, valueHash);
        if (oldEntryForValue != null) {
            if (force) {
                delete(oldEntryForValue);
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("value already present: ");
                stringBuilder.append(value);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
        }
        BiEntry<K, V> newEntry = new BiEntry(key, keyHash, value, valueHash);
        if (oldEntryForKey != null) {
            delete(oldEntryForKey);
            insert(newEntry, oldEntryForKey);
            oldEntryForKey.prevInKeyInsertionOrder = null;
            oldEntryForKey.nextInKeyInsertionOrder = null;
            rehashIfNecessary();
            return oldEntryForKey.value;
        }
        insert(newEntry, null);
        rehashIfNecessary();
        return null;
    }

    @Nullable
    private K putInverse(@Nullable V value, @Nullable K key, boolean force) {
        int valueHash = Hashing.smearedHash(value);
        int keyHash = Hashing.smearedHash(key);
        BiEntry<K, V> oldEntryForValue = seekByValue(value, valueHash);
        if (oldEntryForValue != null && keyHash == oldEntryForValue.keyHash && Objects.equal(key, oldEntryForValue.key)) {
            return key;
        }
        BiEntry<K, V> oldEntryForKey = seekByKey(key, keyHash);
        if (oldEntryForKey != null) {
            if (force) {
                delete(oldEntryForKey);
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("value already present: ");
                stringBuilder.append(key);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
        }
        if (oldEntryForValue != null) {
            delete(oldEntryForValue);
        }
        insert(new BiEntry(key, keyHash, value, valueHash), oldEntryForKey);
        if (oldEntryForKey != null) {
            oldEntryForKey.prevInKeyInsertionOrder = null;
            oldEntryForKey.nextInKeyInsertionOrder = null;
        }
        rehashIfNecessary();
        return Maps.keyOrNull(oldEntryForValue);
    }

    private void rehashIfNecessary() {
        BiEntry<K, V>[] oldKToV = this.hashTableKToV;
        if (Hashing.needsResizing(this.size, oldKToV.length, 1.0d)) {
            int newTableSize = oldKToV.length * 2;
            this.hashTableKToV = createTable(newTableSize);
            this.hashTableVToK = createTable(newTableSize);
            this.mask = newTableSize - 1;
            this.size = 0;
            for (BiEntry<K, V> entry = this.firstInKeyInsertionOrder; entry != null; entry = entry.nextInKeyInsertionOrder) {
                insert(entry, entry);
            }
            this.modCount++;
        }
    }

    private BiEntry<K, V>[] createTable(int length) {
        return new BiEntry[length];
    }

    public V remove(@Nullable Object key) {
        BiEntry<K, V> entry = seekByKey(key, Hashing.smearedHash(key));
        if (entry == null) {
            return null;
        }
        delete(entry);
        entry.prevInKeyInsertionOrder = null;
        entry.nextInKeyInsertionOrder = null;
        return entry.value;
    }

    public void clear() {
        this.size = 0;
        Arrays.fill(this.hashTableKToV, null);
        Arrays.fill(this.hashTableVToK, null);
        this.firstInKeyInsertionOrder = null;
        this.lastInKeyInsertionOrder = null;
        this.modCount++;
    }

    public int size() {
        return this.size;
    }

    public Set<K> keySet() {
        return new HashBiMap$KeySet(this);
    }

    public Set<V> values() {
        return inverse().keySet();
    }

    Iterator<Entry<K, V>> entryIterator() {
        return new HashBiMap$1(this);
    }

    public BiMap<V, K> inverse() {
        if (this.inverse != null) {
            return this.inverse;
        }
        BiMap<V, K> hashBiMap$Inverse = new HashBiMap$Inverse(this, null);
        this.inverse = hashBiMap$Inverse;
        return hashBiMap$Inverse;
    }

    @GwtIncompatible("java.io.ObjectOutputStream")
    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        Serialization.writeMap(this, stream);
    }

    @GwtIncompatible("java.io.ObjectInputStream")
    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        init(16);
        Serialization.populateMap(this, stream, Serialization.readCount(stream));
    }
}
