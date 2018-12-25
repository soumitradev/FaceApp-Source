package android.support.v7.widget;

class ChildHelper$Bucket {
    static final int BITS_PER_WORD = 64;
    static final long LAST_BIT = Long.MIN_VALUE;
    long mData = 0;
    ChildHelper$Bucket mNext;

    ChildHelper$Bucket() {
    }

    void set(int index) {
        if (index >= 64) {
            ensureNext();
            this.mNext.set(index - 64);
            return;
        }
        this.mData |= 1 << index;
    }

    private void ensureNext() {
        if (this.mNext == null) {
            this.mNext = new ChildHelper$Bucket();
        }
    }

    void clear(int index) {
        if (index < 64) {
            this.mData &= (1 << index) ^ -1;
        } else if (this.mNext != null) {
            this.mNext.clear(index - 64);
        }
    }

    boolean get(int index) {
        if (index >= 64) {
            ensureNext();
            return this.mNext.get(index - 64);
        }
        return (this.mData & (1 << index)) != 0;
    }

    void reset() {
        this.mData = 0;
        if (this.mNext != null) {
            this.mNext.reset();
        }
    }

    void insert(int index, boolean value) {
        if (index >= 64) {
            ensureNext();
            this.mNext.insert(index - 64, value);
            return;
        }
        boolean lastBit = (this.mData & Long.MIN_VALUE) != 0;
        long mask = (1 << index) - 1;
        this.mData = (this.mData & mask) | ((this.mData & (mask ^ -1)) << 1);
        if (value) {
            set(index);
        } else {
            clear(index);
        }
        if (lastBit || this.mNext != null) {
            ensureNext();
            this.mNext.insert(0, lastBit);
        }
    }

    boolean remove(int index) {
        ChildHelper$Bucket childHelper$Bucket = this;
        int i = index;
        if (i >= 64) {
            ensureNext();
            return childHelper$Bucket.mNext.remove(i - 64);
        }
        long mask = 1 << i;
        boolean value = (childHelper$Bucket.mData & mask) != 0;
        childHelper$Bucket.mData &= mask ^ -1;
        long mask2 = mask - 1;
        childHelper$Bucket.mData = (childHelper$Bucket.mData & mask2) | Long.rotateRight(childHelper$Bucket.mData & (mask2 ^ -1), 1);
        if (childHelper$Bucket.mNext != null) {
            if (childHelper$Bucket.mNext.get(0)) {
                set(63);
            }
            childHelper$Bucket.mNext.remove(0);
        }
        return value;
    }

    int countOnesBefore(int index) {
        if (this.mNext == null) {
            if (index >= 64) {
                return Long.bitCount(this.mData);
            }
            return Long.bitCount(this.mData & ((1 << index) - 1));
        } else if (index < 64) {
            return Long.bitCount(this.mData & ((1 << index) - 1));
        } else {
            return this.mNext.countOnesBefore(index - 64) + Long.bitCount(this.mData);
        }
    }

    public String toString() {
        if (this.mNext == null) {
            return Long.toBinaryString(this.mData);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.mNext.toString());
        stringBuilder.append("xx");
        stringBuilder.append(Long.toBinaryString(this.mData));
        return stringBuilder.toString();
    }
}
