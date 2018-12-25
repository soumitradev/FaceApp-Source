package com.google.common.collect;

import com.google.common.collect.HashBiMap.BiEntry;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

abstract class HashBiMap$Itr<T> implements Iterator<T> {
    int expectedModCount = HashBiMap.access$100(this.this$0);
    BiEntry<K, V> next = HashBiMap.access$000(this.this$0);
    final /* synthetic */ HashBiMap this$0;
    BiEntry<K, V> toRemove = null;

    abstract T output(BiEntry<K, V> biEntry);

    HashBiMap$Itr(HashBiMap hashBiMap) {
        this.this$0 = hashBiMap;
    }

    public boolean hasNext() {
        if (HashBiMap.access$100(this.this$0) == this.expectedModCount) {
            return this.next != null;
        } else {
            throw new ConcurrentModificationException();
        }
    }

    public T next() {
        if (hasNext()) {
            BiEntry<K, V> entry = this.next;
            this.next = entry.nextInKeyInsertionOrder;
            this.toRemove = entry;
            return output(entry);
        }
        throw new NoSuchElementException();
    }

    public void remove() {
        if (HashBiMap.access$100(this.this$0) != this.expectedModCount) {
            throw new ConcurrentModificationException();
        }
        CollectPreconditions.checkRemove(this.toRemove != null);
        HashBiMap.access$200(this.this$0, this.toRemove);
        this.expectedModCount = HashBiMap.access$100(this.this$0);
        this.toRemove = null;
    }
}
