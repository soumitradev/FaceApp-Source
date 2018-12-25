package com.badlogic.gdx.utils;

import com.badlogic.gdx.math.MathUtils;
import com.google.common.primitives.Ints;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ObjectSet<T> implements Iterable<T> {
    private static final int PRIME1 = -1105259343;
    private static final int PRIME2 = -1262997959;
    private static final int PRIME3 = -825114047;
    int capacity;
    private int hashShift;
    private ObjectSetIterator iterator1;
    private ObjectSetIterator iterator2;
    T[] keyTable;
    private float loadFactor;
    private int mask;
    private int pushIterations;
    public int size;
    private int stashCapacity;
    int stashSize;
    private int threshold;

    public static class ObjectSetIterator<K> implements Iterable<K>, Iterator<K> {
        int currentIndex;
        public boolean hasNext;
        int nextIndex;
        final ObjectSet<K> set;
        boolean valid = true;

        public ObjectSetIterator(ObjectSet<K> set) {
            this.set = set;
            reset();
        }

        public void reset() {
            this.currentIndex = -1;
            this.nextIndex = -1;
            findNextIndex();
        }

        void findNextIndex() {
            this.hasNext = false;
            K[] keyTable = this.set.keyTable;
            int n = this.set.capacity + this.set.stashSize;
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
            if (this.currentIndex >= this.set.capacity) {
                this.set.removeStashIndex(this.currentIndex);
                this.nextIndex = this.currentIndex - 1;
                findNextIndex();
            } else {
                this.set.keyTable[this.currentIndex] = null;
            }
            this.currentIndex = -1;
            ObjectSet objectSet = this.set;
            objectSet.size--;
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
                K key = this.set.keyTable[this.nextIndex];
                this.currentIndex = this.nextIndex;
                findNextIndex();
                return key;
            } else {
                throw new GdxRuntimeException("#iterator() cannot be used nested.");
            }
        }

        public ObjectSetIterator<K> iterator() {
            return this;
        }

        public Array<K> toArray(Array<K> array) {
            while (this.hasNext) {
                array.add(next());
            }
            return array;
        }

        public Array<K> toArray() {
            return toArray(new Array(true, this.set.size));
        }
    }

    public ObjectSet() {
        this(32, 0.8f);
    }

    public ObjectSet(int initialCapacity) {
        this(initialCapacity, 0.8f);
    }

    public ObjectSet(int initialCapacity, float loadFactor) {
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
        }
    }

    public ObjectSet(ObjectSet set) {
        this(set.capacity, set.loadFactor);
        this.stashSize = set.stashSize;
        System.arraycopy(set.keyTable, 0, this.keyTable, 0, set.keyTable.length);
        this.size = set.size;
    }

    public boolean add(T key) {
        ObjectSet objectSet = this;
        T t = key;
        if (t == null) {
            throw new IllegalArgumentException("key cannot be null.");
        }
        T[] keyTable = objectSet.keyTable;
        int hashCode = key.hashCode();
        int index1 = hashCode & objectSet.mask;
        T key1 = keyTable[index1];
        if (t.equals(key1)) {
            return false;
        }
        int index2 = hash2(hashCode);
        T key2 = keyTable[index2];
        if (t.equals(key2)) {
            return false;
        }
        int index3 = hash3(hashCode);
        T key3 = keyTable[index3];
        if (t.equals(key3)) {
            return false;
        }
        int i = objectSet.capacity;
        int n = objectSet.stashSize + i;
        while (i < n) {
            if (t.equals(keyTable[i])) {
                return false;
            }
            i++;
        }
        if (key1 == null) {
            keyTable[index1] = t;
            i = objectSet.size;
            objectSet.size = i + 1;
            if (i >= objectSet.threshold) {
                resize(objectSet.capacity << 1);
            }
            return true;
        } else if (key2 == null) {
            keyTable[index2] = t;
            i = objectSet.size;
            objectSet.size = i + 1;
            if (i >= objectSet.threshold) {
                resize(objectSet.capacity << 1);
            }
            return true;
        } else if (key3 == null) {
            keyTable[index3] = t;
            i = objectSet.size;
            objectSet.size = i + 1;
            if (i >= objectSet.threshold) {
                resize(objectSet.capacity << 1);
            }
            return true;
        } else {
            push(t, index1, key1, index2, key2, index3, key3);
            return true;
        }
    }

    public void addAll(Array<? extends T> array) {
        addAll((Array) array, 0, array.size);
    }

    public void addAll(Array<? extends T> array, int offset, int length) {
        if (offset + length > array.size) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("offset + length must be <= size: ");
            stringBuilder.append(offset);
            stringBuilder.append(" + ");
            stringBuilder.append(length);
            stringBuilder.append(" <= ");
            stringBuilder.append(array.size);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        addAll(array.items, offset, length);
    }

    public void addAll(T... array) {
        addAll((Object[]) array, 0, array.length);
    }

    public void addAll(T[] array, int offset, int length) {
        ensureCapacity(length);
        int i = offset;
        int n = i + length;
        while (i < n) {
            add(array[i]);
            i++;
        }
    }

    public void addAll(ObjectSet<T> set) {
        ensureCapacity(set.size);
        Iterator i$ = set.iterator();
        while (i$.hasNext()) {
            add(i$.next());
        }
    }

    private void addResize(T key) {
        int hashCode = key.hashCode();
        int index1 = hashCode & this.mask;
        T key1 = this.keyTable[index1];
        int i;
        if (key1 == null) {
            r8.keyTable[index1] = key;
            i = r8.size;
            r8.size = i + 1;
            if (i >= r8.threshold) {
                resize(r8.capacity << 1);
            }
            return;
        }
        int index2 = hash2(hashCode);
        T key2 = r8.keyTable[index2];
        if (key2 == null) {
            r8.keyTable[index2] = key;
            i = r8.size;
            r8.size = i + 1;
            if (i >= r8.threshold) {
                resize(r8.capacity << 1);
            }
            return;
        }
        int index3 = hash3(hashCode);
        T key3 = r8.keyTable[index3];
        if (key3 == null) {
            r8.keyTable[index3] = key;
            i = r8.size;
            r8.size = i + 1;
            if (i >= r8.threshold) {
                resize(r8.capacity << 1);
            }
            return;
        }
        push(key, index1, key1, index2, key2, index3, key3);
    }

    private void push(T insertKey, int index1, T key1, int index2, T key2, int index3, T key3) {
        T[] keyTable = this.keyTable;
        int mask = this.mask;
        int i = 0;
        int pushIterations = this.pushIterations;
        while (true) {
            T evictedKey;
            switch (MathUtils.random(2)) {
                case 0:
                    evictedKey = key1;
                    keyTable[index1] = insertKey;
                    break;
                case 1:
                    evictedKey = key2;
                    keyTable[index2] = insertKey;
                    break;
                default:
                    evictedKey = key3;
                    keyTable[index3] = insertKey;
                    break;
            }
            int hashCode = evictedKey.hashCode();
            index1 = hashCode & mask;
            key1 = keyTable[index1];
            if (key1 == null) {
                int i2;
                keyTable[index1] = evictedKey;
                i2 = this.size;
                this.size = i2 + 1;
                if (i2 >= this.threshold) {
                    resize(this.capacity << 1);
                }
                return;
            }
            index2 = hash2(hashCode);
            key2 = keyTable[index2];
            if (key2 == null) {
                keyTable[index2] = evictedKey;
                i2 = this.size;
                this.size = i2 + 1;
                if (i2 >= this.threshold) {
                    resize(this.capacity << 1);
                }
                return;
            }
            index3 = hash3(hashCode);
            key3 = keyTable[index3];
            if (key3 == null) {
                keyTable[index3] = evictedKey;
                i2 = this.size;
                this.size = i2 + 1;
                if (i2 >= this.threshold) {
                    resize(this.capacity << 1);
                }
                return;
            }
            i++;
            if (i == pushIterations) {
                addStash(evictedKey);
                return;
            }
            insertKey = evictedKey;
        }
    }

    private void addStash(T key) {
        if (this.stashSize == this.stashCapacity) {
            resize(this.capacity << 1);
            add(key);
            return;
        }
        this.keyTable[this.capacity + this.stashSize] = key;
        this.stashSize++;
        this.size++;
    }

    public boolean remove(T key) {
        int hashCode = key.hashCode();
        int index = this.mask & hashCode;
        if (key.equals(this.keyTable[index])) {
            this.keyTable[index] = null;
            this.size--;
            return true;
        }
        index = hash2(hashCode);
        if (key.equals(this.keyTable[index])) {
            this.keyTable[index] = null;
            this.size--;
            return true;
        }
        index = hash3(hashCode);
        if (!key.equals(this.keyTable[index])) {
            return removeStash(key);
        }
        this.keyTable[index] = null;
        this.size--;
        return true;
    }

    boolean removeStash(T key) {
        T[] keyTable = this.keyTable;
        int i = this.capacity;
        int n = this.stashSize + i;
        while (i < n) {
            if (key.equals(keyTable[i])) {
                removeStashIndex(i);
                this.size--;
                return true;
            }
            i++;
        }
        return false;
    }

    void removeStashIndex(int index) {
        this.stashSize--;
        int lastIndex = this.capacity + this.stashSize;
        if (index < lastIndex) {
            this.keyTable[index] = this.keyTable[lastIndex];
        }
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
            T[] keyTable = this.keyTable;
            int i = this.capacity + this.stashSize;
            while (true) {
                int i2 = i - 1;
                if (i > 0) {
                    keyTable[i2] = null;
                    i = i2;
                } else {
                    this.size = 0;
                    this.stashSize = 0;
                    return;
                }
            }
        }
    }

    public boolean contains(T key) {
        int hashCode = key.hashCode();
        if (!key.equals(this.keyTable[this.mask & hashCode])) {
            if (!key.equals(this.keyTable[hash2(hashCode)])) {
                if (!key.equals(this.keyTable[hash3(hashCode)])) {
                    return containsKeyStash(key);
                }
            }
        }
        return true;
    }

    private boolean containsKeyStash(T key) {
        T[] keyTable = this.keyTable;
        int i = this.capacity;
        int n = this.stashSize + i;
        while (i < n) {
            if (key.equals(keyTable[i])) {
                return true;
            }
            i++;
        }
        return false;
    }

    public T first() {
        T[] keyTable = this.keyTable;
        int n = this.capacity + this.stashSize;
        for (int i = 0; i < n; i++) {
            if (keyTable[i] != null) {
                return keyTable[i];
            }
        }
        throw new IllegalStateException("ObjectSet is empty.");
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
        T[] oldKeyTable = this.keyTable;
        this.keyTable = new Object[(this.stashCapacity + newSize)];
        int oldSize = this.size;
        int i = 0;
        this.size = 0;
        this.stashSize = 0;
        if (oldSize > 0) {
            while (i < oldEndIndex) {
                T key = oldKeyTable[i];
                if (key != null) {
                    addResize(key);
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

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('{');
        stringBuilder.append(toString(", "));
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    public String toString(String separator) {
        if (this.size == 0) {
            return "";
        }
        Object key;
        StringBuilder buffer = new StringBuilder(32);
        T[] keyTable = this.keyTable;
        int i = keyTable.length;
        while (true) {
            int i2 = i - 1;
            if (i <= 0) {
                break;
            }
            key = keyTable[i2];
            if (key != null) {
                break;
            }
            i = i2;
        }
        buffer.append(key);
        while (true) {
            i = i2 - 1;
            if (i2 <= 0) {
                return buffer.toString();
            }
            Object key2 = keyTable[i];
            if (key2 != null) {
                buffer.append(separator);
                buffer.append(key2);
            }
            i2 = i;
        }
    }

    public ObjectSetIterator<T> iterator() {
        if (this.iterator1 == null) {
            this.iterator1 = new ObjectSetIterator(this);
            this.iterator2 = new ObjectSetIterator(this);
        }
        if (this.iterator1.valid) {
            this.iterator2.reset();
            this.iterator2.valid = true;
            this.iterator1.valid = false;
            return this.iterator2;
        }
        this.iterator1.reset();
        this.iterator1.valid = true;
        this.iterator2.valid = false;
        return this.iterator1;
    }

    public static <T> ObjectSet<T> with(T... array) {
        ObjectSet set = new ObjectSet();
        set.addAll((Object[]) array);
        return set;
    }
}
