package com.badlogic.gdx.utils;

import com.badlogic.gdx.math.MathUtils;
import com.google.common.primitives.Ints;
import java.util.NoSuchElementException;

public class IntSet {
    private static final int EMPTY = 0;
    private static final int PRIME1 = -1105259343;
    private static final int PRIME2 = -1262997959;
    private static final int PRIME3 = -825114047;
    int capacity;
    boolean hasZeroValue;
    private int hashShift;
    private IntSetIterator iterator1;
    private IntSetIterator iterator2;
    int[] keyTable;
    private float loadFactor;
    private int mask;
    private int pushIterations;
    public int size;
    private int stashCapacity;
    int stashSize;
    private int threshold;

    public static class IntSetIterator {
        static final int INDEX_ILLEGAL = -2;
        static final int INDEX_ZERO = -1;
        int currentIndex;
        public boolean hasNext;
        int nextIndex;
        final IntSet set;
        boolean valid = true;

        public IntSetIterator(IntSet set) {
            this.set = set;
            reset();
        }

        public void reset() {
            this.currentIndex = -2;
            this.nextIndex = -1;
            if (this.set.hasZeroValue) {
                this.hasNext = true;
            } else {
                findNextIndex();
            }
        }

        void findNextIndex() {
            this.hasNext = false;
            int[] keyTable = this.set.keyTable;
            int n = this.set.capacity + this.set.stashSize;
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
            if (this.currentIndex == -1 && this.set.hasZeroValue) {
                this.set.hasZeroValue = false;
            } else if (this.currentIndex < 0) {
                throw new IllegalStateException("next must be called before remove.");
            } else if (this.currentIndex >= this.set.capacity) {
                this.set.removeStashIndex(this.currentIndex);
                this.nextIndex = this.currentIndex - 1;
                findNextIndex();
            } else {
                this.set.keyTable[this.currentIndex] = 0;
            }
            this.currentIndex = -2;
            IntSet intSet = this.set;
            intSet.size--;
        }

        public int next() {
            if (!this.hasNext) {
                throw new NoSuchElementException();
            } else if (this.valid) {
                int key = this.nextIndex == -1 ? 0 : this.set.keyTable[this.nextIndex];
                this.currentIndex = this.nextIndex;
                findNextIndex();
                return key;
            } else {
                throw new GdxRuntimeException("#iterator() cannot be used nested.");
            }
        }

        public IntArray toArray() {
            IntArray array = new IntArray(true, this.set.size);
            while (this.hasNext) {
                array.add(next());
            }
            return array;
        }
    }

    public IntSet() {
        this(32, 0.8f);
    }

    public IntSet(int initialCapacity) {
        this(initialCapacity, 0.8f);
    }

    public IntSet(int initialCapacity, float loadFactor) {
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
        }
    }

    public IntSet(IntSet map) {
        this(map.capacity, map.loadFactor);
        this.stashSize = map.stashSize;
        System.arraycopy(map.keyTable, 0, this.keyTable, 0, map.keyTable.length);
        this.size = map.size;
        this.hasZeroValue = map.hasZeroValue;
    }

    public boolean add(int key) {
        IntSet intSet = this;
        int i = key;
        if (i != 0) {
            int[] keyTable = intSet.keyTable;
            int index1 = i & intSet.mask;
            int key1 = keyTable[index1];
            if (key1 == i) {
                return false;
            }
            int index2 = hash2(key);
            int key2 = keyTable[index2];
            if (key2 == i) {
                return false;
            }
            int index3 = hash3(key);
            int key3 = keyTable[index3];
            if (key3 == i) {
                return false;
            }
            int i2 = intSet.capacity;
            int n = intSet.stashSize + i2;
            while (i2 < n) {
                if (keyTable[i2] == i) {
                    return false;
                }
                i2++;
            }
            int i3;
            if (key1 == 0) {
                keyTable[index1] = i;
                i3 = intSet.size;
                intSet.size = i3 + 1;
                if (i3 >= intSet.threshold) {
                    resize(intSet.capacity << 1);
                }
                return true;
            } else if (key2 == 0) {
                keyTable[index2] = i;
                i3 = intSet.size;
                intSet.size = i3 + 1;
                if (i3 >= intSet.threshold) {
                    resize(intSet.capacity << 1);
                }
                return true;
            } else if (key3 == 0) {
                keyTable[index3] = i;
                i3 = intSet.size;
                intSet.size = i3 + 1;
                if (i3 >= intSet.threshold) {
                    resize(intSet.capacity << 1);
                }
                return true;
            } else {
                push(i, index1, key1, index2, key2, index3, key3);
                return true;
            }
        } else if (intSet.hasZeroValue) {
            return false;
        } else {
            intSet.hasZeroValue = true;
            intSet.size++;
            return true;
        }
    }

    public void addAll(IntArray array) {
        addAll(array, 0, array.size);
    }

    public void addAll(IntArray array, int offset, int length) {
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

    public void addAll(int... array) {
        addAll(array, 0, array.length);
    }

    public void addAll(int[] array, int offset, int length) {
        ensureCapacity(length);
        int i = offset;
        int n = i + length;
        while (i < n) {
            add(array[i]);
            i++;
        }
    }

    public void addAll(IntSet set) {
        ensureCapacity(set.size);
        IntSetIterator iterator = set.iterator();
        while (iterator.hasNext) {
            add(iterator.next());
        }
    }

    private void addResize(int key) {
        IntSet intSet = this;
        if (key == 0) {
            intSet.hasZeroValue = true;
            return;
        }
        int index1 = key & intSet.mask;
        int key1 = intSet.keyTable[index1];
        if (key1 == 0) {
            int i;
            intSet.keyTable[index1] = key;
            i = intSet.size;
            intSet.size = i + 1;
            if (i >= intSet.threshold) {
                resize(intSet.capacity << 1);
            }
            return;
        }
        int index2 = hash2(key);
        int key2 = intSet.keyTable[index2];
        if (key2 == 0) {
            intSet.keyTable[index2] = key;
            i = intSet.size;
            intSet.size = i + 1;
            if (i >= intSet.threshold) {
                resize(intSet.capacity << 1);
            }
            return;
        }
        int index3 = hash3(key);
        int key3 = intSet.keyTable[index3];
        if (key3 == 0) {
            intSet.keyTable[index3] = key;
            i = intSet.size;
            intSet.size = i + 1;
            if (i >= intSet.threshold) {
                resize(intSet.capacity << 1);
            }
            return;
        }
        push(key, index1, key1, index2, key2, index3, key3);
    }

    private void push(int insertKey, int index1, int key1, int index2, int key2, int index3, int key3) {
        int[] keyTable = this.keyTable;
        int mask = this.mask;
        int i = 0;
        int pushIterations = this.pushIterations;
        while (true) {
            int evictedKey;
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
            index1 = evictedKey & mask;
            key1 = keyTable[index1];
            if (key1 == 0) {
                int i2;
                keyTable[index1] = evictedKey;
                i2 = this.size;
                this.size = i2 + 1;
                if (i2 >= this.threshold) {
                    resize(this.capacity << 1);
                }
                return;
            }
            index2 = hash2(evictedKey);
            key2 = keyTable[index2];
            if (key2 == 0) {
                keyTable[index2] = evictedKey;
                i2 = this.size;
                this.size = i2 + 1;
                if (i2 >= this.threshold) {
                    resize(this.capacity << 1);
                }
                return;
            }
            index3 = hash3(evictedKey);
            key3 = keyTable[index3];
            if (key3 == 0) {
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

    private void addStash(int key) {
        if (this.stashSize == this.stashCapacity) {
            resize(this.capacity << 1);
            add(key);
            return;
        }
        this.keyTable[this.capacity + this.stashSize] = key;
        this.stashSize++;
        this.size++;
    }

    public boolean remove(int key) {
        if (key != 0) {
            int index = this.mask & key;
            if (this.keyTable[index] == key) {
                this.keyTable[index] = 0;
                this.size--;
                return true;
            }
            index = hash2(key);
            if (this.keyTable[index] == key) {
                this.keyTable[index] = 0;
                this.size--;
                return true;
            }
            index = hash3(key);
            if (this.keyTable[index] != key) {
                return removeStash(key);
            }
            this.keyTable[index] = 0;
            this.size--;
            return true;
        } else if (!this.hasZeroValue) {
            return false;
        } else {
            this.hasZeroValue = false;
            this.size--;
            return true;
        }
    }

    boolean removeStash(int key) {
        int[] keyTable = this.keyTable;
        int i = this.capacity;
        int n = this.stashSize + i;
        while (i < n) {
            if (keyTable[i] == key) {
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

    public boolean contains(int key) {
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
            if (keyTable[i] == key) {
                return true;
            }
            i++;
        }
        return false;
    }

    public int first() {
        if (this.hasZeroValue) {
            return 0;
        }
        int[] keyTable = this.keyTable;
        int n = this.capacity + this.stashSize;
        for (int i = 0; i < n; i++) {
            if (keyTable[i] != 0) {
                return keyTable[i];
            }
        }
        throw new IllegalStateException("IntSet is empty.");
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
        this.keyTable = new int[(this.stashCapacity + newSize)];
        int oldSize = this.size;
        this.size = this.hasZeroValue;
        int i = 0;
        this.stashSize = 0;
        if (oldSize > 0) {
            while (i < oldEndIndex) {
                int key = oldKeyTable[i];
                if (key != 0) {
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
        r5 = this;
        r0 = r5.size;
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
        r1 = r5.keyTable;
        r2 = r1.length;
        r3 = r5.hasZeroValue;
        if (r3 == 0) goto L_0x0020;
    L_0x001a:
        r3 = "0";
        r0.append(r3);
        goto L_0x002f;
    L_0x0020:
        r3 = r2 + -1;
        if (r2 <= 0) goto L_0x002e;
    L_0x0024:
        r2 = r1[r3];
        if (r2 != 0) goto L_0x002a;
    L_0x0028:
        r2 = r3;
        goto L_0x0020;
    L_0x002a:
        r0.append(r2);
    L_0x002e:
        r2 = r3;
    L_0x002f:
        r3 = r2 + -1;
        if (r2 <= 0) goto L_0x0041;
    L_0x0033:
        r2 = r1[r3];
        if (r2 != 0) goto L_0x0038;
    L_0x0037:
        goto L_0x002e;
    L_0x0038:
        r4 = ", ";
        r0.append(r4);
        r0.append(r2);
        goto L_0x002e;
    L_0x0041:
        r2 = 93;
        r0.append(r2);
        r2 = r0.toString();
        return r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.badlogic.gdx.utils.IntSet.toString():java.lang.String");
    }

    public IntSetIterator iterator() {
        if (this.iterator1 == null) {
            this.iterator1 = new IntSetIterator(this);
            this.iterator2 = new IntSetIterator(this);
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

    public static IntSet with(int... array) {
        IntSet set = new IntSet();
        set.addAll(array);
        return set;
    }
}
