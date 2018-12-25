package com.badlogic.gdx.utils;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class JsonValue$JsonIterator implements Iterator<JsonValue>, Iterable<JsonValue> {
    JsonValue current;
    JsonValue entry = this.this$0.child;
    final /* synthetic */ JsonValue this$0;

    public JsonValue$JsonIterator(JsonValue jsonValue) {
        this.this$0 = jsonValue;
    }

    public boolean hasNext() {
        return this.entry != null;
    }

    public JsonValue next() {
        this.current = this.entry;
        if (this.current == null) {
            throw new NoSuchElementException();
        }
        this.entry = this.current.next;
        return this.current;
    }

    public void remove() {
        if (this.current.prev == null) {
            this.this$0.child = this.current.next;
            if (this.this$0.child != null) {
                this.this$0.child.prev = null;
            }
        } else {
            this.current.prev.next = this.current.next;
            if (this.current.next != null) {
                this.current.next.prev = this.current.prev;
            }
        }
        JsonValue jsonValue = this.this$0;
        jsonValue.size--;
    }

    public Iterator<JsonValue> iterator() {
        return this;
    }
}
