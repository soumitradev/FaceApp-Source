package com.badlogic.gdx.utils;

import com.badlogic.gdx.math.MathUtils;
import java.util.Arrays;
import org.catrobat.catroid.common.Constants;

public class CharArray {
    public char[] items;
    public boolean ordered;
    public int size;

    public CharArray() {
        this(true, 16);
    }

    public CharArray(int capacity) {
        this(true, capacity);
    }

    public CharArray(boolean ordered, int capacity) {
        this.ordered = ordered;
        this.items = new char[capacity];
    }

    public CharArray(CharArray array) {
        this.ordered = array.ordered;
        this.size = array.size;
        this.items = new char[this.size];
        System.arraycopy(array.items, 0, this.items, 0, this.size);
    }

    public CharArray(char[] array) {
        this(true, array, 0, array.length);
    }

    public CharArray(boolean ordered, char[] array, int startIndex, int count) {
        this(ordered, count);
        this.size = count;
        System.arraycopy(array, startIndex, this.items, 0, count);
    }

    public void add(char value) {
        char[] items = this.items;
        if (this.size == items.length) {
            items = resize(Math.max(8, (int) (((float) this.size) * 1.75f)));
        }
        int i = this.size;
        this.size = i + 1;
        items[i] = value;
    }

    public void addAll(CharArray array) {
        addAll(array, 0, array.size);
    }

    public void addAll(CharArray array, int offset, int length) {
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

    public void addAll(char... array) {
        addAll(array, 0, array.length);
    }

    public void addAll(char[] array, int offset, int length) {
        char[] items = this.items;
        int sizeNeeded = this.size + length;
        if (sizeNeeded > items.length) {
            items = resize(Math.max(8, (int) (((float) sizeNeeded) * 1.75f)));
        }
        System.arraycopy(array, offset, items, this.size, length);
        this.size += length;
    }

    public char get(int index) {
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

    public void set(int index, char value) {
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

    public void incr(int index, char value) {
        if (index >= this.size) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("index can't be >= size: ");
            stringBuilder.append(index);
            stringBuilder.append(" >= ");
            stringBuilder.append(this.size);
            throw new IndexOutOfBoundsException(stringBuilder.toString());
        }
        char[] cArr = this.items;
        cArr[index] = (char) (cArr[index] + value);
    }

    public void mul(int index, char value) {
        if (index >= this.size) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("index can't be >= size: ");
            stringBuilder.append(index);
            stringBuilder.append(" >= ");
            stringBuilder.append(this.size);
            throw new IndexOutOfBoundsException(stringBuilder.toString());
        }
        char[] cArr = this.items;
        cArr[index] = (char) (cArr[index] * value);
    }

    public void insert(int index, char value) {
        if (index > this.size) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("index can't be > size: ");
            stringBuilder.append(index);
            stringBuilder.append(" > ");
            stringBuilder.append(this.size);
            throw new IndexOutOfBoundsException(stringBuilder.toString());
        }
        char[] items = this.items;
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
            char[] items = this.items;
            char firstValue = items[first];
            items[first] = items[second];
            items[second] = firstValue;
        }
    }

    public boolean contains(char value) {
        int i = this.size - 1;
        char[] items = this.items;
        while (i >= 0) {
            int i2 = i - 1;
            if (items[i] == value) {
                return true;
            }
            i = i2;
        }
        return false;
    }

    public int indexOf(char value) {
        char[] items = this.items;
        int n = this.size;
        for (int i = 0; i < n; i++) {
            if (items[i] == value) {
                return i;
            }
        }
        return -1;
    }

    public int lastIndexOf(char value) {
        char[] items = this.items;
        for (int i = this.size - 1; i >= 0; i--) {
            if (items[i] == value) {
                return i;
            }
        }
        return -1;
    }

    public boolean removeValue(char value) {
        char[] items = this.items;
        int n = this.size;
        for (int i = 0; i < n; i++) {
            if (items[i] == value) {
                removeIndex(i);
                return true;
            }
        }
        return false;
    }

    public char removeIndex(int index) {
        if (index >= this.size) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("index can't be >= size: ");
            stringBuilder.append(index);
            stringBuilder.append(" >= ");
            stringBuilder.append(this.size);
            throw new IndexOutOfBoundsException(stringBuilder.toString());
        }
        char[] items = this.items;
        char value = items[index];
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
            char[] items = this.items;
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

    public boolean removeAll(CharArray array) {
        int size = this.size;
        int startSize = size;
        char[] items = this.items;
        int i = 0;
        int n = array.size;
        while (true) {
            int ii = 0;
            if (i >= n) {
                break;
            }
            char item = array.get(i);
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

    public char pop() {
        char[] cArr = this.items;
        int i = this.size - 1;
        this.size = i;
        return cArr[i];
    }

    public char peek() {
        return this.items[this.size - 1];
    }

    public char first() {
        if (this.size != 0) {
            return this.items[0];
        }
        throw new IllegalStateException("Array is empty.");
    }

    public void clear() {
        this.size = 0;
    }

    public char[] shrink() {
        if (this.items.length != this.size) {
            resize(this.size);
        }
        return this.items;
    }

    public char[] ensureCapacity(int additionalCapacity) {
        int sizeNeeded = this.size + additionalCapacity;
        if (sizeNeeded > this.items.length) {
            resize(Math.max(8, sizeNeeded));
        }
        return this.items;
    }

    protected char[] resize(int newSize) {
        char[] newItems = new char[newSize];
        System.arraycopy(this.items, 0, newItems, 0, Math.min(this.size, newItems.length));
        this.items = newItems;
        return newItems;
    }

    public void sort() {
        Arrays.sort(this.items, 0, this.size);
    }

    public void reverse() {
        char[] items = this.items;
        int lastIndex = this.size - 1;
        int n = this.size / 2;
        for (int i = 0; i < n; i++) {
            int ii = lastIndex - i;
            char temp = items[i];
            items[i] = items[ii];
            items[ii] = temp;
        }
    }

    public void shuffle() {
        char[] items = this.items;
        for (int i = this.size - 1; i >= 0; i--) {
            int ii = MathUtils.random(i);
            char temp = items[i];
            items[i] = items[ii];
            items[ii] = temp;
        }
    }

    public void truncate(int newSize) {
        if (this.size > newSize) {
            this.size = newSize;
        }
    }

    public char random() {
        if (this.size == 0) {
            return '\u0000';
        }
        return this.items[MathUtils.random(0, this.size - 1)];
    }

    public char[] toArray() {
        char[] array = new char[this.size];
        System.arraycopy(this.items, 0, array, 0, this.size);
        return array;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof CharArray)) {
            return false;
        }
        CharArray array = (CharArray) object;
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
        char[] items = this.items;
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
        char[] items = this.items;
        StringBuilder buffer = new StringBuilder(32);
        buffer.append(items[0]);
        for (int i = 1; i < this.size; i++) {
            buffer.append(separator);
            buffer.append(items[i]);
        }
        return buffer.toString();
    }

    public static CharArray with(char... array) {
        return new CharArray(array);
    }
}
