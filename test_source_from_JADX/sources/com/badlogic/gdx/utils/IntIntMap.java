package com.badlogic.gdx.utils;

import com.badlogic.gdx.math.MathUtils;
import com.google.common.primitives.Ints;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class IntIntMap implements Iterable<Entry> {
    private static final int EMPTY = 0;
    private static final int PRIME1 = -1105259343;
    private static final int PRIME2 = -1262997959;
    private static final int PRIME3 = -825114047;
    int capacity;
    private Entries entries1;
    private Entries entries2;
    boolean hasZeroValue;
    private int hashShift;
    int[] keyTable;
    private Keys keys1;
    private Keys keys2;
    private float loadFactor;
    private int mask;
    private int pushIterations;
    public int size;
    private int stashCapacity;
    int stashSize;
    private int threshold;
    int[] valueTable;
    private Values values1;
    private Values values2;
    int zeroValue;

    public static class Entry {
        public int key;
        public int value;

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.key);
            stringBuilder.append("=");
            stringBuilder.append(this.value);
            return stringBuilder.toString();
        }
    }

    private static class MapIterator {
        static final int INDEX_ILLEGAL = -2;
        static final int INDEX_ZERO = -1;
        int currentIndex;
        public boolean hasNext;
        final IntIntMap map;
        int nextIndex;
        boolean valid = true;

        public MapIterator(IntIntMap map) {
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
            int[] keyTable = this.map.keyTable;
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
                this.map.hasZeroValue = false;
            } else if (this.currentIndex < 0) {
                throw new IllegalStateException("next must be called before remove.");
            } else if (this.currentIndex >= this.map.capacity) {
                this.map.removeStashIndex(this.currentIndex);
                this.nextIndex = this.currentIndex - 1;
                findNextIndex();
            } else {
                this.map.keyTable[this.currentIndex] = 0;
            }
            this.currentIndex = -2;
            IntIntMap intIntMap = this.map;
            intIntMap.size--;
        }
    }

    public static class Entries extends MapIterator implements Iterable<Entry>, Iterator<Entry> {
        private Entry entry = new Entry();

        public /* bridge */ /* synthetic */ void reset() {
            super.reset();
        }

        public Entries(IntIntMap map) {
            super(map);
        }

        public Entry next() {
            if (!this.hasNext) {
                throw new NoSuchElementException();
            } else if (this.valid) {
                int[] keyTable = this.map.keyTable;
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

        public Iterator<Entry> iterator() {
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

        public Keys(IntIntMap map) {
            super(map);
        }

        public boolean hasNext() {
            if (this.valid) {
                return this.hasNext;
            }
            throw new GdxRuntimeException("#iterator() cannot be used nested.");
        }

        public int next() {
            if (!this.hasNext) {
                throw new NoSuchElementException();
            } else if (this.valid) {
                int key = this.nextIndex == -1 ? 0 : this.map.keyTable[this.nextIndex];
                this.currentIndex = this.nextIndex;
                findNextIndex();
                return key;
            } else {
                throw new GdxRuntimeException("#iterator() cannot be used nested.");
            }
        }

        public IntArray toArray() {
            IntArray array = new IntArray(true, this.map.size);
            while (this.hasNext) {
                array.add(next());
            }
            return array;
        }
    }

    public static class Values extends MapIterator {
        public /* bridge */ /* synthetic */ void remove() {
            super.remove();
        }

        public /* bridge */ /* synthetic */ void reset() {
            super.reset();
        }

        public Values(IntIntMap map) {
            super(map);
        }

        public boolean hasNext() {
            if (this.valid) {
                return this.hasNext;
            }
            throw new GdxRuntimeException("#iterator() cannot be used nested.");
        }

        public int next() {
            if (!this.hasNext) {
                throw new NoSuchElementException();
            } else if (this.valid) {
                int value;
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

        public IntArray toArray() {
            IntArray array = new IntArray(true, this.map.size);
            while (this.hasNext) {
                array.add(next());
            }
            return array;
        }
    }

    public IntIntMap() {
        this(32, 0.8f);
    }

    public IntIntMap(int initialCapacity) {
        this(initialCapacity, 0.8f);
    }

    public IntIntMap(int initialCapacity, float loadFactor) {
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
            this.keyTable = new int[(this.capacity + this.stashCapacity)];
            this.valueTable = new int[this.keyTable.length];
        }
    }

    public IntIntMap(IntIntMap map) {
        this(map.capacity, map.loadFactor);
        this.stashSize = map.stashSize;
        System.arraycopy(map.keyTable, 0, this.keyTable, 0, map.keyTable.length);
        System.arraycopy(map.valueTable, 0, this.valueTable, 0, map.valueTable.length);
        this.size = map.size;
        this.zeroValue = map.zeroValue;
        this.hasZeroValue = map.hasZeroValue;
    }

    public void put(int key, int value) {
        IntIntMap intIntMap = this;
        int i = key;
        int i2 = value;
        if (i == 0) {
            intIntMap.zeroValue = i2;
            if (!intIntMap.hasZeroValue) {
                intIntMap.hasZeroValue = true;
                intIntMap.size++;
            }
            return;
        }
        int[] keyTable = intIntMap.keyTable;
        int index1 = i & intIntMap.mask;
        int key1 = keyTable[index1];
        if (i == key1) {
            intIntMap.valueTable[index1] = i2;
            return;
        }
        int index2 = hash2(key);
        int key2 = keyTable[index2];
        if (i == key2) {
            intIntMap.valueTable[index2] = i2;
            return;
        }
        int index3 = hash3(key);
        int key3 = keyTable[index3];
        if (i == key3) {
            intIntMap.valueTable[index3] = i2;
            return;
        }
        int i3 = intIntMap.capacity;
        int n = intIntMap.stashSize + i3;
        while (i3 < n) {
            if (i == keyTable[i3]) {
                intIntMap.valueTable[i3] = i2;
                return;
            }
            i3++;
        }
        if (key1 == 0) {
            keyTable[index1] = i;
            intIntMap.valueTable[index1] = i2;
            i3 = intIntMap.size;
            intIntMap.size = i3 + 1;
            if (i3 >= intIntMap.threshold) {
                resize(intIntMap.capacity << 1);
            }
        } else if (key2 == 0) {
            keyTable[index2] = i;
            intIntMap.valueTable[index2] = i2;
            i3 = intIntMap.size;
            intIntMap.size = i3 + 1;
            if (i3 >= intIntMap.threshold) {
                resize(intIntMap.capacity << 1);
            }
        } else if (key3 == 0) {
            keyTable[index3] = i;
            intIntMap.valueTable[index3] = i2;
            i3 = intIntMap.size;
            intIntMap.size = i3 + 1;
            if (i3 >= intIntMap.threshold) {
                resize(intIntMap.capacity << 1);
            }
        } else {
            push(i, i2, index1, key1, index2, key2, index3, key3);
        }
    }

    public void putAll(IntIntMap map) {
        Iterator i$ = map.entries().iterator();
        while (i$.hasNext()) {
            Entry entry = (Entry) i$.next();
            put(entry.key, entry.value);
        }
    }

    private void putResize(int key, int value) {
        IntIntMap intIntMap = this;
        int i = value;
        if (key == 0) {
            intIntMap.zeroValue = i;
            intIntMap.hasZeroValue = true;
            return;
        }
        int index1 = key & intIntMap.mask;
        int key1 = intIntMap.keyTable[index1];
        int i2;
        if (key1 == 0) {
            intIntMap.keyTable[index1] = key;
            intIntMap.valueTable[index1] = i;
            i2 = intIntMap.size;
            intIntMap.size = i2 + 1;
            if (i2 >= intIntMap.threshold) {
                resize(intIntMap.capacity << 1);
            }
            return;
        }
        int index2 = hash2(key);
        int key2 = intIntMap.keyTable[index2];
        if (key2 == 0) {
            intIntMap.keyTable[index2] = key;
            intIntMap.valueTable[index2] = i;
            i2 = intIntMap.size;
            intIntMap.size = i2 + 1;
            if (i2 >= intIntMap.threshold) {
                resize(intIntMap.capacity << 1);
            }
            return;
        }
        int index3 = hash3(key);
        int key3 = intIntMap.keyTable[index3];
        if (key3 == 0) {
            intIntMap.keyTable[index3] = key;
            intIntMap.valueTable[index3] = i;
            i2 = intIntMap.size;
            intIntMap.size = i2 + 1;
            if (i2 >= intIntMap.threshold) {
                resize(intIntMap.capacity << 1);
            }
            return;
        }
        push(key, i, index1, key1, index2, key2, index3, key3);
    }

    private void push(int insertKey, int insertValue, int index1, int key1, int index2, int key2, int index3, int key3) {
        int[] keyTable = this.keyTable;
        int[] valueTable = this.valueTable;
        int mask = this.mask;
        int pushIterations = this.pushIterations;
        int evictedKey = 0;
        int insertKey2 = insertKey;
        int insertValue2 = insertValue;
        int index12 = index1;
        int key12 = key1;
        int index22 = index2;
        int key22 = key2;
        int index32 = index3;
        int i = 0;
        int key32 = key3;
        while (true) {
            int evictedValue;
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
            index12 = evictedKey & mask;
            key12 = keyTable[index12];
            if (key12 == 0) {
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
            int i2 = key32;
            index22 = hash2(evictedKey);
            key22 = keyTable[index22];
            if (key22 == 0) {
                keyTable[index22] = evictedKey;
                valueTable[index22] = evictedValue;
                mask = r0.size;
                r0.size = mask + 1;
                if (mask >= r0.threshold) {
                    resize(r0.capacity << 1);
                }
                return;
            }
            index32 = hash3(evictedKey);
            key32 = keyTable[index32];
            if (key32 == 0) {
                keyTable[index32] = evictedKey;
                valueTable[index32] = evictedValue;
                mask = r0.size;
                r0.size = mask + 1;
                if (mask >= r0.threshold) {
                    resize(r0.capacity << 1);
                }
                return;
            }
            int[] keyTable2 = keyTable;
            i++;
            if (i == pushIterations) {
                putStash(evictedKey, evictedValue);
                return;
            }
            insertKey2 = evictedKey;
            insertValue2 = evictedValue;
            mask = mask2;
            keyTable = keyTable2;
        }
    }

    private void putStash(int key, int value) {
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

    public int get(int key, int defaultValue) {
        if (key != 0) {
            int index = this.mask & key;
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

    private int getStash(int key, int defaultValue) {
        int[] keyTable = this.keyTable;
        int i = this.capacity;
        int n = this.stashSize + i;
        while (i < n) {
            if (key == keyTable[i]) {
                return this.valueTable[i];
            }
            i++;
        }
        return defaultValue;
    }

    public int getAndIncrement(int key, int defaultValue, int increment) {
        int index;
        if (key != 0) {
            index = this.mask & key;
            if (key != this.keyTable[index]) {
                index = hash2(key);
                if (key != this.keyTable[index]) {
                    index = hash3(key);
                    if (key != this.keyTable[index]) {
                        return getAndIncrementStash(key, defaultValue, increment);
                    }
                }
            }
            int value = this.valueTable[index];
            this.valueTable[index] = value + increment;
            return value;
        } else if (this.hasZeroValue) {
            index = this.zeroValue;
            this.zeroValue += increment;
            return index;
        } else {
            this.hasZeroValue = true;
            this.zeroValue = defaultValue + increment;
            this.size++;
            return defaultValue;
        }
    }

    private int getAndIncrementStash(int key, int defaultValue, int increment) {
        int[] keyTable = this.keyTable;
        int i = this.capacity;
        int n = this.stashSize + i;
        while (i < n) {
            if (key == keyTable[i]) {
                int value = this.valueTable[i];
                this.valueTable[i] = value + increment;
                return value;
            }
            i++;
        }
        put(key, defaultValue + increment);
        return defaultValue;
    }

    public int remove(int key, int defaultValue) {
        if (key != 0) {
            int index = this.mask & key;
            int oldValue;
            if (key == this.keyTable[index]) {
                this.keyTable[index] = 0;
                oldValue = this.valueTable[index];
                this.size--;
                return oldValue;
            }
            index = hash2(key);
            if (key == this.keyTable[index]) {
                this.keyTable[index] = 0;
                oldValue = this.valueTable[index];
                this.size--;
                return oldValue;
            }
            index = hash3(key);
            if (key != this.keyTable[index]) {
                return removeStash(key, defaultValue);
            }
            this.keyTable[index] = 0;
            oldValue = this.valueTable[index];
            this.size--;
            return oldValue;
        } else if (!this.hasZeroValue) {
            return defaultValue;
        } else {
            this.hasZeroValue = false;
            this.size--;
            return this.zeroValue;
        }
    }

    int removeStash(int key, int defaultValue) {
        int[] keyTable = this.keyTable;
        int i = this.capacity;
        int n = this.stashSize + i;
        while (i < n) {
            if (key == keyTable[i]) {
                int oldValue = this.valueTable[i];
                removeStashIndex(i);
                this.size--;
                return oldValue;
            }
            i++;
        }
        return defaultValue;
    }

    void removeStashIndex(int index) {
        this.stashSize--;
        int lastIndex = this.capacity + this.stashSize;
        if (index < lastIndex) {
            this.keyTable[index] = this.keyTable[lastIndex];
            this.valueTable[index] = this.valueTable[lastIndex];
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
        this.hasZeroValue = false;
        this.size = 0;
        resize(maximumCapacity);
    }

    public void clear() {
        if (this.size != 0) {
            int[] keyTable = this.keyTable;
            int i = this.capacity + this.stashSize;
            while (true) {
                int i2 = i - 1;
                if (i > 0) {
                    keyTable[i2] = 0;
                    i = i2;
                } else {
                    this.size = 0;
                    this.stashSize = 0;
                    this.hasZeroValue = false;
                    return;
                }
            }
        }
    }

    public boolean containsValue(int value) {
        if (this.hasZeroValue && this.zeroValue == value) {
            return true;
        }
        int[] valueTable = this.valueTable;
        int i = this.capacity + this.stashSize;
        while (true) {
            int i2 = i - 1;
            if (i <= 0) {
                return false;
            }
            if (valueTable[i2] == value) {
                return true;
            }
            i = i2;
        }
    }

    public boolean containsKey(int key) {
        if (key == 0) {
            return this.hasZeroValue;
        }
        if (this.keyTable[this.mask & key] != key) {
            if (this.keyTable[hash2(key)] != key) {
                if (this.keyTable[hash3(key)] != key) {
                    return containsKeyStash(key);
                }
            }
        }
        return true;
    }

    private boolean containsKeyStash(int key) {
        int[] keyTable = this.keyTable;
        int i = this.capacity;
        int n = this.stashSize + i;
        while (i < n) {
            if (key == keyTable[i]) {
                return true;
            }
            i++;
        }
        return false;
    }

    public int findKey(int value, int notFound) {
        if (this.hasZeroValue && this.zeroValue == value) {
            return 0;
        }
        int[] valueTable = this.valueTable;
        int i = this.capacity + this.stashSize;
        while (true) {
            int i2 = i - 1;
            if (i <= 0) {
                return notFound;
            }
            if (valueTable[i2] == value) {
                return this.keyTable[i2];
            }
            i = i2;
        }
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
        int[] oldKeyTable = this.keyTable;
        int[] oldValueTable = this.valueTable;
        this.keyTable = new int[(this.stashCapacity + newSize)];
        this.valueTable = new int[(this.stashCapacity + newSize)];
        int oldSize = this.size;
        this.size = this.hasZeroValue;
        int i = 0;
        this.stashSize = 0;
        if (oldSize > 0) {
            while (i < oldEndIndex) {
                int key = oldKeyTable[i];
                if (key != 0) {
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

    public java.lang.String toString() {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:37)
	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:61)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:282)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:200)
	at jadx.api.JadxDecompiler$$Lambda$8/1115170891.run(Unknown Source)
*/
        /*
        r7 = this;
        r0 = r7.size;
        if (r0 != 0) goto L_0x0007;
    L_0x0004:
        r0 = "{}";
        return r0;
    L_0x0007:
        r0 = new com.badlogic.gdx.utils.StringBuilder;
        r1 = 32;
        r0.<init>(r1);
        r1 = 123; // 0x7b float:1.72E-43 double:6.1E-322;
        r0.append(r1);
        r1 = r7.keyTable;
        r2 = r7.valueTable;
        r3 = r1.length;
        r4 = r7.hasZeroValue;
        r5 = 61;
        if (r4 == 0) goto L_0x0029;
    L_0x001e:
        r4 = "0=";
        r0.append(r4);
        r4 = r7.zeroValue;
        r0.append(r4);
        goto L_0x0040;
    L_0x0029:
        r4 = r3 + -1;
        if (r3 <= 0) goto L_0x003f;
    L_0x002d:
        r3 = r1[r4];
        if (r3 != 0) goto L_0x0033;
    L_0x0031:
        r3 = r4;
        goto L_0x0029;
    L_0x0033:
        r0.append(r3);
        r0.append(r5);
        r6 = r2[r4];
        r0.append(r6);
    L_0x003f:
        r3 = r4;
    L_0x0040:
        r4 = r3 + -1;
        if (r3 <= 0) goto L_0x005a;
    L_0x0044:
        r3 = r1[r4];
        if (r3 != 0) goto L_0x0049;
    L_0x0048:
        goto L_0x003f;
    L_0x0049:
        r6 = ", ";
        r0.append(r6);
        r0.append(r3);
        r0.append(r5);
        r6 = r2[r4];
        r0.append(r6);
        goto L_0x003f;
    L_0x005a:
        r3 = 125; // 0x7d float:1.75E-43 double:6.2E-322;
        r0.append(r3);
        r3 = r0.toString();
        return r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.badlogic.gdx.utils.IntIntMap.toString():java.lang.String");
    }

    public Iterator<Entry> iterator() {
        return entries();
    }

    public Entries entries() {
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

    public Values values() {
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
