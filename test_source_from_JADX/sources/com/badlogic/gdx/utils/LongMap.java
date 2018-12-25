package com.badlogic.gdx.utils;

import com.badlogic.gdx.math.MathUtils;
import com.google.common.primitives.Ints;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class LongMap<V> implements Iterable<Entry<V>> {
    private static final int EMPTY = 0;
    private static final int PRIME1 = -1105259343;
    private static final int PRIME2 = -1262997959;
    private static final int PRIME3 = -825114047;
    int capacity;
    private Entries entries1;
    private Entries entries2;
    boolean hasZeroValue;
    private int hashShift;
    long[] keyTable;
    private Keys keys1;
    private Keys keys2;
    private float loadFactor;
    private int mask;
    private int pushIterations;
    public int size;
    private int stashCapacity;
    int stashSize;
    private int threshold;
    V[] valueTable;
    private Values values1;
    private Values values2;
    V zeroValue;

    public static class Entry<V> {
        public long key;
        public V value;

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.key);
            stringBuilder.append("=");
            stringBuilder.append(this.value);
            return stringBuilder.toString();
        }
    }

    private static class MapIterator<V> {
        static final int INDEX_ILLEGAL = -2;
        static final int INDEX_ZERO = -1;
        int currentIndex;
        public boolean hasNext;
        final LongMap<V> map;
        int nextIndex;
        boolean valid = true;

        public MapIterator(LongMap<V> map) {
            this.map = map;
            reset();
        }

        public void reset() {
            this.currentIndex = -2;
            this.nextIndex = -1;
            if (this.map.hasZeroValue) {
                this.hasNext = true;
            } else {
                findNextIndex();
            }
        }

        void findNextIndex() {
            this.hasNext = false;
            long[] keyTable = this.map.keyTable;
            int n = this.map.capacity + this.map.stashSize;
            do {
                int i = this.nextIndex + 1;
                this.nextIndex = i;
                if (i >= n) {
                    return;
                }
            } while (keyTable[this.nextIndex] == 0);
            this.hasNext = true;
        }

        public void remove() {
            if (this.currentIndex == -1 && this.map.hasZeroValue) {
                this.map.zeroValue = null;
                this.map.hasZeroValue = false;
            } else if (this.currentIndex < 0) {
                throw new IllegalStateException("next must be called before remove.");
            } else if (this.currentIndex >= this.map.capacity) {
                this.map.removeStashIndex(this.currentIndex);
                this.nextIndex = this.currentIndex - 1;
                findNextIndex();
            } else {
                this.map.keyTable[this.currentIndex] = 0;
                this.map.valueTable[this.currentIndex] = null;
            }
            this.currentIndex = -2;
            LongMap longMap = this.map;
            longMap.size--;
        }
    }

    public static class Entries<V> extends MapIterator<V> implements Iterable<Entry<V>>, Iterator<Entry<V>> {
        private Entry<V> entry = new Entry();

        public /* bridge */ /* synthetic */ void reset() {
            super.reset();
        }

        public Entries(LongMap map) {
            super(map);
        }

        public Entry<V> next() {
            if (!this.hasNext) {
                throw new NoSuchElementException();
            } else if (this.valid) {
                long[] keyTable = this.map.keyTable;
                if (this.nextIndex == -1) {
                    this.entry.key = 0;
                    this.entry.value = this.map.zeroValue;
                } else {
                    this.entry.key = keyTable[this.nextIndex];
                    this.entry.value = this.map.valueTable[this.nextIndex];
                }
                this.currentIndex = this.nextIndex;
                findNextIndex();
                return this.entry;
            } else {
                throw new GdxRuntimeException("#iterator() cannot be used nested.");
            }
        }

        public boolean hasNext() {
            if (this.valid) {
                return this.hasNext;
            }
            throw new GdxRuntimeException("#iterator() cannot be used nested.");
        }

        public Iterator<Entry<V>> iterator() {
            return this;
        }

        public void remove() {
            super.remove();
        }
    }

    public static class Keys extends MapIterator {
        public /* bridge */ /* synthetic */ void remove() {
            super.remove();
        }

        public /* bridge */ /* synthetic */ void reset() {
            super.reset();
        }

        public Keys(LongMap map) {
            super(map);
        }

        public long next() {
            if (!this.hasNext) {
                throw new NoSuchElementException();
            } else if (this.valid) {
                long key = this.nextIndex == -1 ? 0 : this.map.keyTable[this.nextIndex];
                this.currentIndex = this.nextIndex;
                findNextIndex();
                return key;
            } else {
                throw new GdxRuntimeException("#iterator() cannot be used nested.");
            }
        }

        public LongArray toArray() {
            LongArray array = new LongArray(true, this.map.size);
            while (this.hasNext) {
                array.add(next());
            }
            return array;
        }
    }

    public static class Values<V> extends MapIterator<V> implements Iterable<V>, Iterator<V> {
        public /* bridge */ /* synthetic */ void reset() {
            super.reset();
        }

        public Values(LongMap<V> map) {
            super(map);
        }

        public boolean hasNext() {
            if (this.valid) {
                return this.hasNext;
            }
            throw new GdxRuntimeException("#iterator() cannot be used nested.");
        }

        public V next() {
            if (!this.hasNext) {
                throw new NoSuchElementException();
            } else if (this.valid) {
                V value;
                if (this.nextIndex == -1) {
                    value = this.map.zeroValue;
                } else {
                    value = this.map.valueTable[this.nextIndex];
                }
                this.currentIndex = this.nextIndex;
                findNextIndex();
                return value;
            } else {
                throw new GdxRuntimeException("#iterator() cannot be used nested.");
            }
        }

        public Iterator<V> iterator() {
            return this;
        }

        public Array<V> toArray() {
            Array array = new Array(true, this.map.size);
            while (this.hasNext) {
                array.add(next());
            }
            return array;
        }

        public void remove() {
            super.remove();
        }
    }

    public LongMap() {
        this(32, 0.8f);
    }

    public LongMap(int initialCapacity) {
        this(initialCapacity, 0.8f);
    }

    public LongMap(int initialCapacity, float loadFactor) {
        StringBuilder stringBuilder;
        if (initialCapacity < 0) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("initialCapacity must be >= 0: ");
            stringBuilder.append(initialCapacity);
            throw new IllegalArgumentException(stringBuilder.toString());
        } else if (initialCapacity > Ints.MAX_POWER_OF_TWO) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("initialCapacity is too large: ");
            stringBuilder.append(initialCapacity);
            throw new IllegalArgumentException(stringBuilder.toString());
        } else {
            this.capacity = MathUtils.nextPowerOfTwo(initialCapacity);
            if (loadFactor <= 0.0f) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("loadFactor must be > 0: ");
                stringBuilder.append(loadFactor);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            this.loadFactor = loadFactor;
            this.threshold = (int) (((float) this.capacity) * loadFactor);
            this.mask = this.capacity - 1;
            this.hashShift = 63 - Long.numberOfTrailingZeros((long) this.capacity);
            this.stashCapacity = Math.max(3, ((int) Math.ceil(Math.log((double) this.capacity))) * 2);
            this.pushIterations = Math.max(Math.min(this.capacity, 8), ((int) Math.sqrt((double) this.capacity)) / 8);
            this.keyTable = new long[(this.capacity + this.stashCapacity)];
            this.valueTable = new Object[this.keyTable.length];
        }
    }

    public LongMap(LongMap<? extends V> map) {
        this(map.capacity, map.loadFactor);
        this.stashSize = map.stashSize;
        System.arraycopy(map.keyTable, 0, this.keyTable, 0, map.keyTable.length);
        System.arraycopy(map.valueTable, 0, this.valueTable, 0, map.valueTable.length);
        this.size = map.size;
        this.zeroValue = map.zeroValue;
        this.hasZeroValue = map.hasZeroValue;
    }

    public V put(long key, V value) {
        LongMap longMap = this;
        V v = value;
        if (key == 0) {
            V oldValue = longMap.zeroValue;
            longMap.zeroValue = v;
            if (!longMap.hasZeroValue) {
                longMap.hasZeroValue = true;
                longMap.size++;
            }
            return oldValue;
        }
        long[] keyTable = longMap.keyTable;
        int index1 = (int) (key & ((long) longMap.mask));
        long key1 = keyTable[index1];
        if (key1 == key) {
            oldValue = longMap.valueTable[index1];
            longMap.valueTable[index1] = v;
            return oldValue;
        }
        int index2 = hash2(key);
        long key2 = keyTable[index2];
        if (key2 == key) {
            oldValue = longMap.valueTable[index2];
            longMap.valueTable[index2] = v;
            return oldValue;
        }
        int index3 = hash3(key);
        long key3 = keyTable[index3];
        if (key3 == key) {
            oldValue = longMap.valueTable[index3];
            longMap.valueTable[index3] = v;
            return oldValue;
        }
        int i = longMap.capacity;
        int n = longMap.stashSize + i;
        while (i < n) {
            if (keyTable[i] == key) {
                oldValue = longMap.valueTable[i];
                longMap.valueTable[i] = v;
                return oldValue;
            }
            i++;
        }
        int i2;
        if (key1 == 0) {
            keyTable[index1] = key;
            longMap.valueTable[index1] = v;
            i2 = longMap.size;
            longMap.size = i2 + 1;
            if (i2 >= longMap.threshold) {
                resize(longMap.capacity << 1);
            }
            return null;
        } else if (key2 == 0) {
            keyTable[index2] = key;
            longMap.valueTable[index2] = v;
            i2 = longMap.size;
            longMap.size = i2 + 1;
            if (i2 >= longMap.threshold) {
                resize(longMap.capacity << 1);
            }
            return null;
        } else if (key3 == 0) {
            keyTable[index3] = key;
            longMap.valueTable[index3] = v;
            i2 = longMap.size;
            longMap.size = i2 + 1;
            if (i2 >= longMap.threshold) {
                resize(longMap.capacity << 1);
            }
            return null;
        } else {
            push(key, v, index1, key1, index2, key2, index3, key3);
            return null;
        }
    }

    public void putAll(LongMap<V> map) {
        Iterator i$ = map.entries().iterator();
        while (i$.hasNext()) {
            Entry<V> entry = (Entry) i$.next();
            put(entry.key, entry.value);
        }
    }

    private void putResize(long key, V value) {
        LongMap longMap = this;
        V v = value;
        if (key == 0) {
            longMap.zeroValue = v;
            longMap.hasZeroValue = true;
            return;
        }
        int index1 = (int) (key & ((long) longMap.mask));
        long key1 = longMap.keyTable[index1];
        if (key1 == 0) {
            int i;
            longMap.keyTable[index1] = key;
            longMap.valueTable[index1] = v;
            i = longMap.size;
            longMap.size = i + 1;
            if (i >= longMap.threshold) {
                resize(longMap.capacity << 1);
            }
            return;
        }
        int index2 = hash2(key);
        long key2 = longMap.keyTable[index2];
        if (key2 == 0) {
            longMap.keyTable[index2] = key;
            longMap.valueTable[index2] = v;
            i = longMap.size;
            longMap.size = i + 1;
            if (i >= longMap.threshold) {
                resize(longMap.capacity << 1);
            }
            return;
        }
        int index3 = hash3(key);
        long key3 = longMap.keyTable[index3];
        if (key3 == 0) {
            longMap.keyTable[index3] = key;
            longMap.valueTable[index3] = v;
            i = longMap.size;
            longMap.size = i + 1;
            if (i >= longMap.threshold) {
                resize(longMap.capacity << 1);
            }
            return;
        }
        push(key, v, index1, key1, index2, key2, index3, key3);
    }

    private void push(long insertKey, V insertValue, int index1, long key1, int index2, long key2, int index3, long key3) {
        long[] keyTable = this.keyTable;
        V[] valueTable = this.valueTable;
        int mask = this.mask;
        int pushIterations = this.pushIterations;
        long insertKey2 = insertKey;
        V insertValue2 = insertValue;
        int index12 = index1;
        long key12 = key1;
        int index22 = index2;
        long key22 = key2;
        int i = 0;
        V evictedValue = null;
        int index32 = index3;
        long key32 = key3;
        while (true) {
            long evictedKey;
            V evictedKey2;
            switch (MathUtils.random(2)) {
                case 0:
                    evictedKey = key12;
                    evictedKey2 = valueTable[index12];
                    keyTable[index12] = insertKey2;
                    valueTable[index12] = insertValue2;
                    break;
                case 1:
                    evictedKey = key22;
                    evictedKey2 = valueTable[index22];
                    keyTable[index22] = insertKey2;
                    valueTable[index22] = insertValue2;
                    break;
                default:
                    evictedKey = key32;
                    evictedKey2 = valueTable[index32];
                    keyTable[index32] = insertKey2;
                    valueTable[index32] = insertValue2;
                    break;
            }
            evictedValue = evictedKey2;
            long evictedKey3 = evictedKey;
            int mask2 = mask;
            mask = (int) (evictedKey3 & ((long) mask));
            key12 = keyTable[mask];
            if (key12 == 0) {
                keyTable[mask] = evictedKey3;
                valueTable[mask] = evictedValue;
                index32 = r0.size;
                r0.size = index32 + 1;
                if (index32 >= r0.threshold) {
                    resize(r0.capacity << 1);
                }
                return;
            }
            index22 = hash2(evictedKey3);
            key22 = keyTable[index22];
            if (key22 == 0) {
                keyTable[index22] = evictedKey3;
                valueTable[index22] = evictedValue;
                index32 = r0.size;
                r0.size = index32 + 1;
                if (index32 >= r0.threshold) {
                    resize(r0.capacity << 1);
                }
                return;
            }
            index32 = hash3(evictedKey3);
            key32 = keyTable[index32];
            int i2;
            if (key32 == 0) {
                keyTable[index32] = evictedKey3;
                valueTable[index32] = evictedValue;
                i2 = r0.size;
                r0.size = i2 + 1;
                if (i2 >= r0.threshold) {
                    resize(r0.capacity << 1);
                }
                return;
            }
            i2 = i + 1;
            if (i2 == pushIterations) {
                putStash(evictedKey3, evictedValue);
                return;
            }
            insertKey2 = evictedKey3;
            insertValue2 = evictedValue;
            index12 = mask;
            i = i2;
            mask = mask2;
        }
    }

    private void putStash(long key, V value) {
        if (this.stashSize == this.stashCapacity) {
            resize(this.capacity << 1);
            put(key, value);
            return;
        }
        int index = this.capacity + this.stashSize;
        this.keyTable[index] = key;
        this.valueTable[index] = value;
        this.stashSize++;
        this.size++;
    }

    public V get(long key) {
        if (key != 0) {
            int index = (int) (key & ((long) this.mask));
            if (this.keyTable[index] != key) {
                index = hash2(key);
                if (this.keyTable[index] != key) {
                    index = hash3(key);
                    if (this.keyTable[index] != key) {
                        return getStash(key, null);
                    }
                }
            }
            return this.valueTable[index];
        } else if (this.hasZeroValue) {
            return this.zeroValue;
        } else {
            return null;
        }
    }

    public V get(long key, V defaultValue) {
        if (key != 0) {
            int index = (int) (key & ((long) this.mask));
            if (this.keyTable[index] != key) {
                index = hash2(key);
                if (this.keyTable[index] != key) {
                    index = hash3(key);
                    if (this.keyTable[index] != key) {
                        return getStash(key, defaultValue);
                    }
                }
            }
            return this.valueTable[index];
        } else if (this.hasZeroValue) {
            return this.zeroValue;
        } else {
            return defaultValue;
        }
    }

    private V getStash(long key, V defaultValue) {
        long[] keyTable = this.keyTable;
        int i = this.capacity;
        int n = this.stashSize + i;
        while (i < n) {
            if (keyTable[i] == key) {
                return this.valueTable[i];
            }
            i++;
        }
        return defaultValue;
    }

    public V remove(long key) {
        V oldValue;
        if (key != 0) {
            int index = (int) (key & ((long) this.mask));
            if (this.keyTable[index] == key) {
                this.keyTable[index] = 0;
                oldValue = this.valueTable[index];
                this.valueTable[index] = null;
                this.size--;
                return oldValue;
            }
            index = hash2(key);
            if (this.keyTable[index] == key) {
                this.keyTable[index] = 0;
                oldValue = this.valueTable[index];
                this.valueTable[index] = null;
                this.size--;
                return oldValue;
            }
            index = hash3(key);
            if (this.keyTable[index] != key) {
                return removeStash(key);
            }
            this.keyTable[index] = 0;
            oldValue = this.valueTable[index];
            this.valueTable[index] = null;
            this.size--;
            return oldValue;
        } else if (!this.hasZeroValue) {
            return null;
        } else {
            oldValue = this.zeroValue;
            this.zeroValue = null;
            this.hasZeroValue = false;
            this.size--;
            return oldValue;
        }
    }

    V removeStash(long key) {
        long[] keyTable = this.keyTable;
        int i = this.capacity;
        int n = this.stashSize + i;
        while (i < n) {
            if (keyTable[i] == key) {
                V oldValue = this.valueTable[i];
                removeStashIndex(i);
                this.size--;
                return oldValue;
            }
            i++;
        }
        return null;
    }

    void removeStashIndex(int index) {
        this.stashSize--;
        int lastIndex = this.capacity + this.stashSize;
        if (index < lastIndex) {
            this.keyTable[index] = this.keyTable[lastIndex];
            this.valueTable[index] = this.valueTable[lastIndex];
            this.valueTable[lastIndex] = null;
            return;
        }
        this.valueTable[index] = null;
    }

    public void shrink(int maximumCapacity) {
        if (maximumCapacity < 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("maximumCapacity must be >= 0: ");
            stringBuilder.append(maximumCapacity);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        if (this.size > maximumCapacity) {
            maximumCapacity = this.size;
        }
        if (this.capacity > maximumCapacity) {
            resize(MathUtils.nextPowerOfTwo(maximumCapacity));
        }
    }

    public void clear(int maximumCapacity) {
        if (this.capacity <= maximumCapacity) {
            clear();
            return;
        }
        this.zeroValue = null;
        this.hasZeroValue = false;
        this.size = 0;
        resize(maximumCapacity);
    }

    public void clear() {
        if (this.size != 0) {
            long[] keyTable = this.keyTable;
            V[] valueTable = this.valueTable;
            int i = this.capacity + this.stashSize;
            while (true) {
                int i2 = i - 1;
                if (i > 0) {
                    keyTable[i2] = 0;
                    valueTable[i2] = null;
                    i = i2;
                } else {
                    this.size = 0;
                    this.stashSize = 0;
                    this.zeroValue = null;
                    this.hasZeroValue = false;
                    return;
                }
            }
        }
    }

    public boolean containsValue(Object value, boolean identity) {
        V[] valueTable = this.valueTable;
        int i;
        if (value == null) {
            if (!this.hasZeroValue || this.zeroValue != null) {
                long[] keyTable = this.keyTable;
                i = this.capacity + this.stashSize;
                while (true) {
                    int i2 = i - 1;
                    if (i <= 0) {
                        break;
                    } else if (keyTable[i2] != 0 && valueTable[i2] == null) {
                        return true;
                    } else {
                        i = i2;
                    }
                }
            } else {
                return true;
            }
        } else if (identity) {
            if (value != this.zeroValue) {
                i = this.capacity + this.stashSize;
                while (true) {
                    i = i - 1;
                    if (i <= 0) {
                        break;
                    } else if (valueTable[i] == value) {
                        return true;
                    } else {
                        i = i;
                    }
                }
            } else {
                return true;
            }
        } else if (!this.hasZeroValue || !value.equals(this.zeroValue)) {
            i = this.capacity + this.stashSize;
            while (true) {
                i = i - 1;
                if (i <= 0) {
                    break;
                } else if (value.equals(valueTable[i])) {
                    return true;
                } else {
                    i = i;
                }
            }
        } else {
            return true;
        }
        return false;
    }

    public boolean containsKey(long key) {
        if (key == 0) {
            return this.hasZeroValue;
        }
        if (this.keyTable[(int) (key & ((long) this.mask))] != key) {
            if (this.keyTable[hash2(key)] != key) {
                if (this.keyTable[hash3(key)] != key) {
                    return containsKeyStash(key);
                }
            }
        }
        return true;
    }

    private boolean containsKeyStash(long key) {
        long[] keyTable = this.keyTable;
        int i = this.capacity;
        int n = this.stashSize + i;
        while (i < n) {
            if (keyTable[i] == key) {
                return true;
            }
            i++;
        }
        return false;
    }

    public long findKey(Object value, boolean identity, long notFound) {
        V[] valueTable = this.valueTable;
        if (value == null) {
            if (!this.hasZeroValue || this.zeroValue != null) {
                long[] keyTable = this.keyTable;
                int i = this.capacity + this.stashSize;
                while (true) {
                    int i2 = i - 1;
                    if (i <= 0) {
                        break;
                    } else if (keyTable[i2] != 0 && valueTable[i2] == null) {
                        return keyTable[i2];
                    } else {
                        i = i2;
                    }
                }
            } else {
                return 0;
            }
        } else if (identity) {
            if (value != this.zeroValue) {
                i = this.capacity + this.stashSize;
                while (true) {
                    i = i - 1;
                    if (i <= 0) {
                        break;
                    } else if (valueTable[i] == value) {
                        return this.keyTable[i];
                    } else {
                        i = i;
                    }
                }
            } else {
                return 0;
            }
        } else if (!this.hasZeroValue || !value.equals(this.zeroValue)) {
            i = this.capacity + this.stashSize;
            while (true) {
                i = i - 1;
                if (i <= 0) {
                    break;
                } else if (value.equals(valueTable[i])) {
                    return this.keyTable[i];
                } else {
                    i = i;
                }
            }
        } else {
            return 0;
        }
        return notFound;
    }

    public void ensureCapacity(int additionalCapacity) {
        int sizeNeeded = this.size + additionalCapacity;
        if (sizeNeeded >= this.threshold) {
            resize(MathUtils.nextPowerOfTwo((int) (((float) sizeNeeded) / this.loadFactor)));
        }
    }

    private void resize(int newSize) {
        int oldEndIndex = this.capacity + this.stashSize;
        this.capacity = newSize;
        this.threshold = (int) (((float) newSize) * this.loadFactor);
        this.mask = newSize - 1;
        this.hashShift = 63 - Long.numberOfTrailingZeros((long) newSize);
        this.stashCapacity = Math.max(3, ((int) Math.ceil(Math.log((double) newSize))) * 2);
        this.pushIterations = Math.max(Math.min(newSize, 8), ((int) Math.sqrt((double) newSize)) / 8);
        long[] oldKeyTable = this.keyTable;
        V[] oldValueTable = this.valueTable;
        this.keyTable = new long[(this.stashCapacity + newSize)];
        this.valueTable = new Object[(this.stashCapacity + newSize)];
        int oldSize = this.size;
        this.size = this.hasZeroValue;
        int i = 0;
        this.stashSize = 0;
        if (oldSize > 0) {
            while (i < oldEndIndex) {
                long key = oldKeyTable[i];
                if (key != 0) {
                    putResize(key, oldValueTable[i]);
                }
                i++;
            }
        }
    }

    private int hash2(long h) {
        h *= -1262997959;
        return (int) ((h ^ (h >>> this.hashShift)) & ((long) this.mask));
    }

    private int hash3(long h) {
        h *= -825114047;
        return (int) ((h ^ (h >>> this.hashShift)) & ((long) this.mask));
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String toString() {
        /*
        r10 = this;
        r0 = r10.size;
        if (r0 != 0) goto L_0x0007;
    L_0x0004:
        r0 = "[]";
        return r0;
    L_0x0007:
        r0 = new com.badlogic.gdx.utils.StringBuilder;
        r1 = 32;
        r0.<init>(r1);
        r1 = 91;
        r0.append(r1);
        r1 = r10.keyTable;
        r2 = r10.valueTable;
        r3 = r1.length;
    L_0x0018:
        r4 = r3 + -1;
        r5 = 61;
        r6 = 0;
        if (r3 <= 0) goto L_0x0034;
    L_0x0020:
        r8 = r1[r4];
        r3 = (r8 > r6 ? 1 : (r8 == r6 ? 0 : -1));
        if (r3 != 0) goto L_0x0028;
    L_0x0026:
        r3 = r4;
        goto L_0x0018;
    L_0x0028:
        r0.append(r8);
        r0.append(r5);
        r3 = r2[r4];
        r0.append(r3);
    L_0x0034:
        r3 = r4 + -1;
        if (r4 <= 0) goto L_0x0052;
    L_0x0038:
        r8 = r1[r3];
        r4 = (r8 > r6 ? 1 : (r8 == r6 ? 0 : -1));
        if (r4 != 0) goto L_0x003f;
    L_0x003e:
        goto L_0x0050;
    L_0x003f:
        r4 = ", ";
        r0.append(r4);
        r0.append(r8);
        r0.append(r5);
        r4 = r2[r3];
        r0.append(r4);
    L_0x0050:
        r4 = r3;
        goto L_0x0034;
    L_0x0052:
        r4 = 93;
        r0.append(r4);
        r4 = r0.toString();
        return r4;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.badlogic.gdx.utils.LongMap.toString():java.lang.String");
    }

    public Iterator<Entry<V>> iterator() {
        return entries();
    }

    public Entries<V> entries() {
        if (this.entries1 == null) {
            this.entries1 = new Entries(this);
            this.entries2 = new Entries(this);
        }
        if (this.entries1.valid) {
            this.entries2.reset();
            this.entries2.valid = true;
            this.entries1.valid = false;
            return this.entries2;
        }
        this.entries1.reset();
        this.entries1.valid = true;
        this.entries2.valid = false;
        return this.entries1;
    }

    public Values<V> values() {
        if (this.values1 == null) {
            this.values1 = new Values(this);
            this.values2 = new Values(this);
        }
        if (this.values1.valid) {
            this.values2.reset();
            this.values2.valid = true;
            this.values1.valid = false;
            return this.values2;
        }
        this.values1.reset();
        this.values1.valid = true;
        this.values2.valid = false;
        return this.values1;
    }

    public Keys keys() {
        if (this.keys1 == null) {
            this.keys1 = new Keys(this);
            this.keys2 = new Keys(this);
        }
        if (this.keys1.valid) {
            this.keys2.reset();
            this.keys2.valid = true;
            this.keys1.valid = false;
            return this.keys2;
        }
        this.keys1.reset();
        this.keys1.valid = true;
        this.keys2.valid = false;
        return this.keys1;
    }
}
