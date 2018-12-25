package com.badlogic.gdx.utils;

import java.util.Iterator;

public class Array$ArrayIterable<T> implements Iterable<T> {
    private final boolean allowRemove;
    private final Array<T> array;
    private Array$ArrayIterator iterator1;
    private Array$ArrayIterator iterator2;

    public Array$ArrayIterable(Array<T> array) {
        this(array, true);
    }

    public Array$ArrayIterable(Array<T> array, boolean allowRemove) {
        this.array = array;
        this.allowRemove = allowRemove;
    }

    public Iterator<T> iterator() {
        if (this.iterator1 == null) {
            this.iterator1 = new Array$ArrayIterator(this.array, this.allowRemove);
            this.iterator2 = new Array$ArrayIterator(this.array, this.allowRemove);
        }
        if (this.iterator1.valid) {
            this.iterator2.index = 0;
            this.iterator2.valid = true;
            this.iterator1.valid = false;
            return this.iterator2;
        }
        this.iterator1.index = 0;
        this.iterator1.valid = true;
        this.iterator2.valid = false;
        return this.iterator1;
    }
}
