package com.badlogic.gdx.utils;

import com.badlogic.gdx.math.MathUtils;
import com.google.common.primitives.Ints;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class IdentityMap<K, V> implements Iterable<Entry<K, V>> {
    private static final int PRIME1 = -1105259343;
    private static final int PRIME2 = -1262997959;
    private static final int PRIME3 = -825114047;
    int capacity;
    private Entries entries1;
    private Entries entries2;
    private int hashShift;
    K[] keyTable;
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

    public static class Entry<K, V> {
        public K key;
        public V value;

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.key);
            stringBuilder.append("=");
            stringBuilder.append(this.value);
            return stringBuilder.toString();
        }
    }

    private static abstract class MapIterator<K, V, I> implements Iterable<I>, Iterator<I> {
        int currentIndex;
        public boolean hasNext;
        final IdentityMap<K, V> map;
        int nextIndex;
        boolean valid = true;

        public MapIterator(IdentityMap<K, V> map) {
            this.map = map;
            reset();
        }

        public void reset() {
            this.currentIndex = -1;
            this.nextIndex = -1;
            findNextIndex();
        }

        void findNextIndex() {
            this.hasNext = false;
            K[] keyTable = this.map.keyTable;
            int n = this.map.capacity + this.map.stashSize;
            do {
                int i = this.nextIndex + 1;
                this.nextIndex = i;
                if (i >= n) {
                    return;
                }
            } while (keyTable[this.nextIndex] == null);
            this.hasNext = true;
        }

        public void remove() {
            if (this.currentIndex < 0) {
                throw new IllegalStateException("next must be called before remove.");
            }
            if (this.currentIndex >= this.map.capacity) {
                this.map.removeStashIndex(this.currentIndex);
                this.nextIndex = this.currentIndex - 1;
                findNextIndex();
            } else {
                this.map.keyTable[this.currentIndex] = null;
                this.map.valueTable[this.currentIndex] = null;
            }
            this.currentIndex = -1;
            IdentityMap identityMap = this.map;
            identityMap.size--;
        }
    }

    public static class Entries<K, V> extends MapIterator<K, V, Entry<K, V>> {
        private Entry<K, V> entry = new Entry();

        public /* bridge */ /* synthetic */ void remove() {
            super.remove();
        }

        public /* bridge */ /* synthetic */ void reset() {
            super.reset();
        }

        public Entries(IdentityMap<K, V> map) {
            super(map);
        }

        public Entry<K, V> next() {
            if (!this.hasNext) {
                throw new NoSuchElementException();
            } else if (this.valid) {
                K[] keyTable = this.map.keyTable;
                this.entry.key = keyTable[this.nextIndex];
                this.entry.value = this.map.valueTable[this.nextIndex];
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

        public Iterator<Entry<K, V>> iterator() {
            return this;
        }
    }

    public static class Keys<K> extends MapIterator<K, Object, K> {
        public /* bridge */ /* synthetic */ void remove() {
            super.remove();
        }

        public /* bridge */ /* synthetic */ void reset() {
            super.reset();
        }

        public Keys(IdentityMap<K, ?> map) {
            super(map);
        }

        public boolean hasNext() {
            if (this.valid) {
                return this.hasNext;
            }
            throw new GdxRuntimeException("#iterator() cannot be used nested.");
        }

        public K next() {
            if (!this.hasNext) {
                throw new NoSuchElementException();
            } else if (this.valid) {
                K key = this.map.keyTable[this.nextIndex];
                this.currentIndex = this.nextIndex;
                findNextIndex();
                return key;
            } else {
                throw new GdxRuntimeException("#iterator() cannot be used nested.");
            }
        }

        public Iterator<K> iterator() {
            return this;
        }

        public Array<K> toArray() {
            Array array = new Array(true, this.map.size);
            while (this.hasNext) {
                array.add(next());
            }
            return array;
        }
    }

    public static class Values<V> extends MapIterator<Object, V, V> {
        public /* bridge */ /* synthetic */ void remove() {
            super.remove();
        }

        public /* bridge */ /* synthetic */ void reset() {
            super.reset();
        }

        public Values(IdentityMap<?, V> map) {
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
                V value = this.map.valueTable[this.nextIndex];
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

        public void toArray(Array<V> array) {
            while (this.hasNext) {
                array.add(next());
            }
        }
    }

    public IdentityMap() {
        this(32, 0.8f);
    }

    public IdentityMap(int initialCapacity) {
        this(initialCapacity, 0.8f);
    }

    public IdentityMap(int initialCapacity, float loadFactor) {
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
            this.hashShift = 31 - Integer.numberOfTrailingZeros(this.capacity);
            this.stashCapacity = Math.max(3, ((int) Math.ceil(Math.log((double) this.capacity))) * 2);
            this.pushIterations = Math.max(Math.min(this.capacity, 8), ((int) Math.sqrt((double) this.capacity)) / 8);
            this.keyTable = new Object[(this.capacity + this.stashCapacity)];
            this.valueTable = new Object[this.keyTable.length];
        }
    }

    public IdentityMap(IdentityMap map) {
        this(map.capacity, map.loadFactor);
        this.stashSize = map.stashSize;
        System.arraycopy(map.keyTable, 0, this.keyTable, 0, map.keyTable.length);
        System.arraycopy(map.valueTable, 0, this.valueTable, 0, map.valueTable.length);
        this.size = map.size;
    }

    public V put(K key, V value) {
        IdentityMap identityMap = this;
        K k = key;
        if (k == null) {
            throw new IllegalArgumentException("key cannot be null.");
        }
        K[] keyTable = identityMap.keyTable;
        int hashCode = System.identityHashCode(key);
        int index1 = hashCode & identityMap.mask;
        K key1 = keyTable[index1];
        if (key1 == k) {
            V oldValue = identityMap.valueTable[index1];
            identityMap.valueTable[index1] = value;
            return oldValue;
        }
        int index2 = hash2(hashCode);
        K key2 = keyTable[index2];
        if (key2 == k) {
            oldValue = identityMap.valueTable[index2];
            identityMap.valueTable[index2] = value;
            return oldValue;
        }
        int index3 = hash3(hashCode);
        K key3 = keyTable[index3];
        if (key3 == k) {
            oldValue = identityMap.valueTable[index3];
            identityMap.valueTable[index3] = value;
            return oldValue;
        }
        int i = identityMap.capacity;
        int n = identityMap.stashSize + i;
        while (i < n) {
            if (keyTable[i] == k) {
                V oldValue2 = identityMap.valueTable[i];
                identityMap.valueTable[i] = value;
                return oldValue2;
            }
            i++;
        }
        if (key1 == null) {
            keyTable[index1] = k;
            identityMap.valueTable[index1] = value;
            i = identityMap.size;
            identityMap.size = i + 1;
            if (i >= identityMap.threshold) {
                resize(identityMap.capacity << 1);
            }
            return null;
        } else if (key2 == null) {
            keyTable[index2] = k;
            identityMap.valueTable[index2] = value;
            i = identityMap.size;
            identityMap.size = i + 1;
            if (i >= identityMap.threshold) {
                resize(identityMap.capacity << 1);
            }
            return null;
        } else if (key3 == null) {
            keyTable[index3] = k;
            identityMap.valueTable[index3] = value;
            i = identityMap.size;
            identityMap.size = i + 1;
            if (i >= identityMap.threshold) {
                resize(identityMap.capacity << 1);
            }
            return null;
        } else {
            push(k, value, index1, key1, index2, key2, index3, key3);
            return null;
        }
    }

    private void putResize(K key, V value) {
        int hashCode = System.identityHashCode(key);
        int index1 = hashCode & this.mask;
        K key1 = this.keyTable[index1];
        int i;
        if (key1 == null) {
            r9.keyTable[index1] = key;
            r9.valueTable[index1] = value;
            i = r9.size;
            r9.size = i + 1;
            if (i >= r9.threshold) {
                resize(r9.capacity << 1);
            }
            return;
        }
        int index2 = hash2(hashCode);
        K key2 = r9.keyTable[index2];
        if (key2 == null) {
            r9.keyTable[index2] = key;
            r9.valueTable[index2] = value;
            i = r9.size;
            r9.size = i + 1;
            if (i >= r9.threshold) {
                resize(r9.capacity << 1);
            }
            return;
        }
        int index3 = hash3(hashCode);
        K key3 = r9.keyTable[index3];
        if (key3 == null) {
            r9.keyTable[index3] = key;
            r9.valueTable[index3] = value;
            i = r9.size;
            r9.size = i + 1;
            if (i >= r9.threshold) {
                resize(r9.capacity << 1);
            }
            return;
        }
        push(key, value, index1, key1, index2, key2, index3, key3);
    }

    private void push(K insertKey, V insertValue, int index1, K key1, int index2, K key2, int index3, K key3) {
        K[] keyTable = this.keyTable;
        V[] valueTable = this.valueTable;
        int mask = this.mask;
        int pushIterations = this.pushIterations;
        K evictedKey = null;
        K insertKey2 = insertKey;
        V insertValue2 = insertValue;
        int index12 = index1;
        K key12 = key1;
        int index22 = index2;
        K key22 = key2;
        int index32 = index3;
        int i = 0;
        V evictedValue = null;
        K key32 = key3;
        while (true) {
            switch (MathUtils.random(2)) {
                case 0:
                    evictedKey = key12;
                    evictedValue = valueTable[index12];
                    keyTable[index12] = insertKey2;
                    valueTable[index12] = insertValue2;
                    break;
                case 1:
                    evictedKey = key22;
                    evictedValue = valueTable[index22];
                    keyTable[index22] = insertKey2;
                    valueTable[index22] = insertValue2;
                    break;
                default:
                    evictedKey = key32;
                    evictedValue = valueTable[index32];
                    keyTable[index32] = insertKey2;
                    valueTable[index32] = insertValue2;
                    break;
            }
            int hashCode = System.identityHashCode(evictedKey);
            index12 = hashCode & mask;
            key12 = keyTable[index12];
            if (key12 == null) {
                keyTable[index12] = evictedKey;
                valueTable[index12] = evictedValue;
                mask = r0.size;
                r0.size = mask + 1;
                if (mask >= r0.threshold) {
                    resize(r0.capacity << 1);
                }
                return;
            }
            int mask2 = mask;
            int i2 = index32;
            index22 = hash2(hashCode);
            key22 = keyTable[index22];
            if (key22 == null) {
                keyTable[index22] = evictedKey;
                valueTable[index22] = evictedValue;
                mask = r0.size;
                r0.size = mask + 1;
                if (mask >= r0.threshold) {
                    resize(r0.capacity << 1);
                }
                return;
            }
            index32 = hash3(hashCode);
            K key33 = keyTable[index32];
            if (key33 == null) {
                keyTable[index32] = evictedKey;
                valueTable[index32] = evictedValue;
                int i3 = r0.size;
                r0.size = i3 + 1;
                if (i3 >= r0.threshold) {
                    resize(r0.capacity << 1);
                }
                return;
            }
            K[] keyTable2 = keyTable;
            V[] valueTable2 = valueTable;
            i++;
            if (i == pushIterations) {
                putStash(evictedKey, evictedValue);
                return;
            }
            insertKey2 = evictedKey;
            insertValue2 = evictedValue;
            key32 = key33;
            mask = mask2;
            keyTable = keyTable2;
            valueTable = valueTable2;
        }
    }

    private void putStash(K key, V value) {
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

    public V get(K key) {
        int hashCode = System.identityHashCode(key);
        int index = this.mask & hashCode;
        if (key != this.keyTable[index]) {
            index = hash2(hashCode);
            if (key != this.keyTable[index]) {
                index = hash3(hashCode);
                if (key != this.keyTable[index]) {
                    return getStash(key, null);
                }
            }
        }
        return this.valueTable[index];
    }

    public V get(K key, V defaultValue) {
        int hashCode = System.identityHashCode(key);
        int index = this.mask & hashCode;
        if (key != this.keyTable[index]) {
            index = hash2(hashCode);
            if (key != this.keyTable[index]) {
                index = hash3(hashCode);
                if (key != this.keyTable[index]) {
                    return getStash(key, defaultValue);
                }
            }
        }
        return this.valueTable[index];
    }

    private V getStash(K key, V defaultValue) {
        K[] keyTable = this.keyTable;
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

    public V remove(K key) {
        int hashCode = System.identityHashCode(key);
        int index = this.mask & hashCode;
        if (this.keyTable[index] == key) {
            this.keyTable[index] = null;
            V oldValue = this.valueTable[index];
            this.valueTable[index] = null;
            this.size--;
            return oldValue;
        }
        index = hash2(hashCode);
        if (this.keyTable[index] == key) {
            this.keyTable[index] = null;
            oldValue = this.valueTable[index];
            this.valueTable[index] = null;
            this.size--;
            return oldValue;
        }
        index = hash3(hashCode);
        if (this.keyTable[index] != key) {
            return removeStash(key);
        }
        this.keyTable[index] = null;
        oldValue = this.valueTable[index];
        this.valueTable[index] = null;
        this.size--;
        return oldValue;
    }

    V removeStash(K key) {
        K[] keyTable = this.keyTable;
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
        this.size = 0;
        resize(maximumCapacity);
    }

    public void clear() {
        if (this.size != 0) {
            K[] keyTable = this.keyTable;
            V[] valueTable = this.valueTable;
            int i = this.capacity + this.stashSize;
            while (true) {
                int i2 = i - 1;
                if (i > 0) {
                    keyTable[i2] = null;
                    valueTable[i2] = null;
                    i = i2;
                } else {
                    this.size = 0;
                    this.stashSize = 0;
                    return;
                }
            }
        }
    }

    public boolean containsValue(Object value, boolean identity) {
        V[] valueTable = this.valueTable;
        int i;
        if (value != null) {
            int i2;
            if (!identity) {
                i2 = this.capacity + this.stashSize;
                while (true) {
                    i = i2 - 1;
                    if (i2 <= 0) {
                        break;
                    } else if (value.equals(valueTable[i])) {
                        return true;
                    } else {
                        i2 = i;
                    }
                }
            } else {
                i2 = this.capacity + this.stashSize;
                while (true) {
                    i = i2 - 1;
                    if (i2 <= 0) {
                        break;
                    } else if (valueTable[i] == value) {
                        return true;
                    } else {
                        i2 = i;
                    }
                }
            }
        } else {
            K[] keyTable = this.keyTable;
            i = this.capacity + this.stashSize;
            while (true) {
                int i3 = i - 1;
                if (i <= 0) {
                    break;
                } else if (keyTable[i3] != null && valueTable[i3] == null) {
                    return true;
                } else {
                    i = i3;
                }
            }
        }
        return false;
    }

    public boolean containsKey(K key) {
        int hashCode = System.identityHashCode(key);
        if (key != this.keyTable[this.mask & hashCode]) {
            if (key != this.keyTable[hash2(hashCode)]) {
                if (key != this.keyTable[hash3(hashCode)]) {
                    return containsKeyStash(key);
                }
            }
        }
        return true;
    }

    private boolean containsKeyStash(K key) {
        K[] keyTable = this.keyTable;
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

    public K findKey(Object value, boolean identity) {
        V[] valueTable = this.valueTable;
        int i;
        if (value != null) {
            int i2;
            if (!identity) {
                i2 = this.capacity + this.stashSize;
                while (true) {
                    i = i2 - 1;
                    if (i2 <= 0) {
                        break;
                    } else if (value.equals(valueTable[i])) {
                        return this.keyTable[i];
                    } else {
                        i2 = i;
                    }
                }
            } else {
                i2 = this.capacity + this.stashSize;
                while (true) {
                    i = i2 - 1;
                    if (i2 <= 0) {
                        break;
                    } else if (valueTable[i] == value) {
                        return this.keyTable[i];
                    } else {
                        i2 = i;
                    }
                }
            }
        } else {
            K[] keyTable = this.keyTable;
            i = this.capacity + this.stashSize;
            while (true) {
                int i3 = i - 1;
                if (i <= 0) {
                    break;
                } else if (keyTable[i3] != null && valueTable[i3] == null) {
                    return keyTable[i3];
                } else {
                    i = i3;
                }
            }
        }
        return null;
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
        this.hashShift = 31 - Integer.numberOfTrailingZeros(newSize);
        this.stashCapacity = Math.max(3, ((int) Math.ceil(Math.log((double) newSize))) * 2);
        this.pushIterations = Math.max(Math.min(newSize, 8), ((int) Math.sqrt((double) newSize)) / 8);
        K[] oldKeyTable = this.keyTable;
        V[] oldValueTable = this.valueTable;
        this.keyTable = new Object[(this.stashCapacity + newSize)];
        this.valueTable = new Object[(this.stashCapacity + newSize)];
        int oldSize = this.size;
        int i = 0;
        this.size = 0;
        this.stashSize = 0;
        if (oldSize > 0) {
            while (i < oldEndIndex) {
                K key = oldKeyTable[i];
                if (key != null) {
                    putResize(key, oldValueTable[i]);
                }
                i++;
            }
        }
    }

    private int hash2(int h) {
        h *= PRIME2;
        return ((h >>> this.hashShift) ^ h) & this.mask;
    }

    private int hash3(int h) {
        h *= PRIME3;
        return ((h >>> this.hashShift) ^ h) & this.mask;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String toString() {
        /*
        r7 = this;
        r0 = r7.size;
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
        r1 = r7.keyTable;
        r2 = r7.valueTable;
        r3 = r1.length;
    L_0x0018:
        r4 = r3 + -1;
        r5 = 61;
        if (r3 <= 0) goto L_0x0030;
    L_0x001e:
        r3 = r1[r4];
        if (r3 != 0) goto L_0x0024;
    L_0x0022:
        r3 = r4;
        goto L_0x0018;
    L_0x0024:
        r0.append(r3);
        r0.append(r5);
        r6 = r2[r4];
        r0.append(r6);
    L_0x0030:
        r3 = r4 + -1;
        if (r4 <= 0) goto L_0x004c;
    L_0x0034:
        r4 = r1[r3];
        if (r4 != 0) goto L_0x0039;
    L_0x0038:
        goto L_0x004a;
    L_0x0039:
        r6 = ", ";
        r0.append(r6);
        r0.append(r4);
        r0.append(r5);
        r6 = r2[r3];
        r0.append(r6);
    L_0x004a:
        r4 = r3;
        goto L_0x0030;
    L_0x004c:
        r4 = 93;
        r0.append(r4);
        r4 = r0.toString();
        return r4;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.badlogic.gdx.utils.IdentityMap.toString():java.lang.String");
    }

    public Iterator<Entry<K, V>> iterator() {
        return entries();
    }

    public Entries<K, V> entries() {
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

    public Keys<K> keys() {
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
