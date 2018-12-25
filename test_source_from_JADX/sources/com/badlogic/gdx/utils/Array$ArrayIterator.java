package com.badlogic.gdx.utils;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Array$ArrayIterator<T> implements Iterator<T>, Iterable<T> {
    private final boolean allowRemove;
    private final Array<T> array;
    int index;
    boolean valid;

    public Array$ArrayIterator(Array<T> array) {
        this(array, true);
    }

    public Array$ArrayIterator(Array<T> array, boolean allowRemove) {
        this.valid = true;
        this.array = array;
        this.allowRemove = allowRemove;
    }

    public boolean hasNext() {
        if (this.valid) {
            return this.index < this.array.size;
        } else {
            throw new GdxRuntimeException("#iterator() cannot be used nested.");
        }
    }

    public T next() {
        if (this.index >= this.array.size) {
            throw new NoSuchElementException(String.valueOf(this.index));
        } else if (this.valid) {
            Object[] objArr = this.array.items;
            int i = this.index;
            this.index = i + 1;
            return objArr[i];
        } else {
            throw new GdxRuntimeException("#iterator() cannot be used nested.");
        }
    }

    public void remove() {
        if (this.allowRemove) {
            this.index--;
            this.array.removeIndex(this.index);
            return;
        }
        throw new GdxRuntimeException("Remove not allowed.");
    }

    public void reset() {
        this.index = 0;
    }

    public Iterator<T> iterator() {
        return this;
    }
}
