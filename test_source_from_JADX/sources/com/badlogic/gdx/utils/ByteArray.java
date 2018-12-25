package com.badlogic.gdx.utils;

import com.badlogic.gdx.math.MathUtils;
import java.util.Arrays;
import org.catrobat.catroid.common.Constants;

public class ByteArray {
    public byte[] items;
    public boolean ordered;
    public int size;

    public ByteArray() {
        this(true, 16);
    }

    public ByteArray(int capacity) {
        this(true, capacity);
    }

    public ByteArray(boolean ordered, int capacity) {
        this.ordered = ordered;
        this.items = new byte[capacity];
    }

    public ByteArray(ByteArray array) {
        this.ordered = array.ordered;
        this.size = array.size;
        this.items = new byte[this.size];
        System.arraycopy(array.items, 0, this.items, 0, this.size);
    }

    public ByteArray(byte[] array) {
        this(true, array, 0, array.length);
    }

    public ByteArray(boolean ordered, byte[] array, int startIndex, int count) {
        this(ordered, count);
        this.size = count;
        System.arraycopy(array, startIndex, this.items, 0, count);
    }

    public void add(byte value) {
        byte[] items = this.items;
        if (this.size == items.length) {
            items = resize(Math.max(8, (int) (((float) this.size) * 1.75f)));
        }
        int i = this.size;
        this.size = i + 1;
        items[i] = value;
    }

    public void addAll(ByteArray array) {
        addAll(array, 0, array.size);
    }

    public void addAll(ByteArray array, int offset, int length) {
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

    public void addAll(byte... array) {
        addAll(array, 0, array.length);
    }

    public void addAll(byte[] array, int offset, int length) {
        byte[] items = this.items;
        int sizeNeeded = this.size + length;
        if (sizeNeeded > items.length) {
            items = resize(Math.max(8, (int) (((float) sizeNeeded) * 1.75f)));
        }
        System.arraycopy(array, offset, items, this.size, length);
        this.size += length;
    }

    public byte get(int index) {
        if (index < this.size) {
            return this.items[index];
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("index can't be >= size: ");
        stringBuilder.append(index);
        stringBuilder.append(" >= ");
        stringBuilder.append(this.size);
        throw new IndexOutOfBoundsException(stringBuilder.toString());
    }

    public void set(int index, byte value) {
        if (index >= this.size) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("index can't be >= size: ");
            stringBuilder.append(index);
            stringBuilder.append(" >= ");
            stringBuilder.append(this.size);
            throw new IndexOutOfBoundsException(stringBuilder.toString());
        }
        this.items[index] = value;
    }

    public void incr(int index, byte value) {
        if (index >= this.size) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("index can't be >= size: ");
            stringBuilder.append(index);
            stringBuilder.append(" >= ");
            stringBuilder.append(this.size);
            throw new IndexOutOfBoundsException(stringBuilder.toString());
        }
        byte[] bArr = this.items;
        bArr[index] = (byte) (bArr[index] + value);
    }

    public void mul(int index, byte value) {
        if (index >= this.size) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("index can't be >= size: ");
            stringBuilder.append(index);
            stringBuilder.append(" >= ");
            stringBuilder.append(this.size);
            throw new IndexOutOfBoundsException(stringBuilder.toString());
        }
        byte[] bArr = this.items;
        bArr[index] = (byte) (bArr[index] * value);
    }

    public void insert(int index, byte value) {
        if (index > this.size) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("index can't be > size: ");
            stringBuilder.append(index);
            stringBuilder.append(" > ");
            stringBuilder.append(this.size);
            throw new IndexOutOfBoundsException(stringBuilder.toString());
        }
        byte[] items = this.items;
        if (this.size == items.length) {
            items = resize(Math.max(8, (int) (((float) this.size) * 1.75f)));
        }
        if (this.ordered) {
            System.arraycopy(items, index, items, index + 1, this.size - index);
        } else {
            items[this.size] = items[index];
        }
        this.size++;
        items[index] = value;
    }

    public void swap(int first, int second) {
        StringBuilder stringBuilder;
        if (first >= this.size) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("first can't be >= size: ");
            stringBuilder.append(first);
            stringBuilder.append(" >= ");
            stringBuilder.append(this.size);
            throw new IndexOutOfBoundsException(stringBuilder.toString());
        } else if (second >= this.size) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("second can't be >= size: ");
            stringBuilder.append(second);
            stringBuilder.append(" >= ");
            stringBuilder.append(this.size);
            throw new IndexOutOfBoundsException(stringBuilder.toString());
        } else {
            byte[] items = this.items;
            byte firstValue = items[first];
            items[first] = items[second];
            items[second] = firstValue;
        }
    }

    public boolean contains(byte value) {
        int i = this.size - 1;
        byte[] items = this.items;
        while (i >= 0) {
            int i2 = i - 1;
            if (items[i] == value) {
                return true;
            }
            i = i2;
        }
        return false;
    }

    public int indexOf(byte value) {
        byte[] items = this.items;
        int n = this.size;
        for (int i = 0; i < n; i++) {
            if (items[i] == value) {
                return i;
            }
        }
        return -1;
    }

    public int lastIndexOf(byte value) {
        byte[] items = this.items;
        for (int i = this.size - 1; i >= 0; i--) {
            if (items[i] == value) {
                return i;
            }
        }
        return -1;
    }

    public boolean removeValue(byte value) {
        byte[] items = this.items;
        int n = this.size;
        for (int i = 0; i < n; i++) {
            if (items[i] == value) {
                removeIndex(i);
                return true;
            }
        }
        return false;
    }

    public int removeIndex(int index) {
        if (index >= this.size) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("index can't be >= size: ");
            stringBuilder.append(index);
            stringBuilder.append(" >= ");
            stringBuilder.append(this.size);
            throw new IndexOutOfBoundsException(stringBuilder.toString());
        }
        byte[] items = this.items;
        int value = items[index];
        this.size--;
        if (this.ordered) {
            System.arraycopy(items, index + 1, items, index, this.size - index);
        } else {
            items[index] = items[this.size];
        }
        return value;
    }

    public void removeRange(int start, int end) {
        StringBuilder stringBuilder;
        if (end >= this.size) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("end can't be >= size: ");
            stringBuilder.append(end);
            stringBuilder.append(" >= ");
            stringBuilder.append(this.size);
            throw new IndexOutOfBoundsException(stringBuilder.toString());
        } else if (start > end) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("start can't be > end: ");
            stringBuilder.append(start);
            stringBuilder.append(" > ");
            stringBuilder.append(end);
            throw new IndexOutOfBoundsException(stringBuilder.toString());
        } else {
            byte[] items = this.items;
            int count = (end - start) + 1;
            if (this.ordered) {
                System.arraycopy(items, start + count, items, start, this.size - (start + count));
            } else {
                int lastIndex = this.size - 1;
                for (int i = 0; i < count; i++) {
                    items[start + i] = items[lastIndex - i];
                }
            }
            this.size -= count;
        }
    }

    public boolean removeAll(ByteArray array) {
        int size = this.size;
        int startSize = size;
        byte[] items = this.items;
        int i = 0;
        int n = array.size;
        while (true) {
            int ii = 0;
            if (i >= n) {
                break;
            }
            byte item = array.get(i);
            while (ii < size) {
                if (item == items[ii]) {
                    removeIndex(ii);
                    size--;
                    break;
                }
                ii++;
            }
            i++;
        }
        if (size != startSize) {
            return true;
        }
        return false;
    }

    public byte pop() {
        byte[] bArr = this.items;
        int i = this.size - 1;
        this.size = i;
        return bArr[i];
    }

    public byte peek() {
        return this.items[this.size - 1];
    }

    public byte first() {
        if (this.size != 0) {
            return this.items[0];
        }
        throw new IllegalStateException("Array is empty.");
    }

    public void clear() {
        this.size = 0;
    }

    public byte[] shrink() {
        if (this.items.length != this.size) {
            resize(this.size);
        }
        return this.items;
    }

    public byte[] ensureCapacity(int additionalCapacity) {
        int sizeNeeded = this.size + additionalCapacity;
        if (sizeNeeded > this.items.length) {
            resize(Math.max(8, sizeNeeded));
        }
        return this.items;
    }

    protected byte[] resize(int newSize) {
        byte[] newItems = new byte[newSize];
        System.arraycopy(this.items, 0, newItems, 0, Math.min(this.size, newItems.length));
        this.items = newItems;
        return newItems;
    }

    public void sort() {
        Arrays.sort(this.items, 0, this.size);
    }

    public void reverse() {
        byte[] items = this.items;
        int lastIndex = this.size - 1;
        int n = this.size / 2;
        for (int i = 0; i < n; i++) {
            int ii = lastIndex - i;
            byte temp = items[i];
            items[i] = items[ii];
            items[ii] = temp;
        }
    }

    public void shuffle() {
        byte[] items = this.items;
        for (int i = this.size - 1; i >= 0; i--) {
            int ii = MathUtils.random(i);
            byte temp = items[i];
            items[i] = items[ii];
            items[ii] = temp;
        }
    }

    public void truncate(int newSize) {
        if (this.size > newSize) {
            this.size = newSize;
        }
    }

    public byte random() {
        if (this.size == 0) {
            return (byte) 0;
        }
        return this.items[MathUtils.random(0, this.size - 1)];
    }

    public byte[] toArray() {
        byte[] array = new byte[this.size];
        System.arraycopy(this.items, 0, array, 0, this.size);
        return array;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof ByteArray)) {
            return false;
        }
        ByteArray array = (ByteArray) object;
        int n = this.size;
        if (n != array.size) {
            return false;
        }
        for (int i = 0; i < n; i++) {
            if (this.items[i] != array.items[i]) {
                return false;
            }
        }
        return true;
    }

    public String toString() {
        if (this.size == 0) {
            return "[]";
        }
        byte[] items = this.items;
        StringBuilder buffer = new StringBuilder(32);
        buffer.append((char) Constants.REMIX_URL_PREFIX_INDICATOR);
        buffer.append(items[0]);
        for (int i = 1; i < this.size; i++) {
            buffer.append(", ");
            buffer.append(items[i]);
        }
        buffer.append((char) Constants.REMIX_URL_SUFIX_INDICATOR);
        return buffer.toString();
    }

    public String toString(String separator) {
        if (this.size == 0) {
            return "";
        }
        byte[] items = this.items;
        StringBuilder buffer = new StringBuilder(32);
        buffer.append(items[0]);
        for (int i = 1; i < this.size; i++) {
            buffer.append(separator);
            buffer.append(items[i]);
        }
        return buffer.toString();
    }

    public static ByteArray with(byte... array) {
        return new ByteArray(array);
    }
}
