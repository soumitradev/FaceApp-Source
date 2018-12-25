package com.google.common.collect;

import com.google.common.collect.Multiset.Entry;
import javax.annotation.Nullable;

abstract class Multisets$EntrySet<E> extends Sets$ImprovedAbstractSet<Entry<E>> {
    abstract Multiset<E> multiset();

    Multisets$EntrySet() {
    }

    public boolean contains(@Nullable Object o) {
        boolean z = false;
        if (!(o instanceof Entry)) {
            return false;
        }
        Entry<?> entry = (Entry) o;
        if (entry.getCount() <= 0) {
            return false;
        }
        if (multiset().count(entry.getElement()) == entry.getCount()) {
            z = true;
        }
        return z;
    }

    public boolean remove(Object object) {
        if (object instanceof Entry) {
            Entry<?> entry = (Entry) object;
            Object element = entry.getElement();
            int entryCount = entry.getCount();
            if (entryCount != 0) {
                return multiset().setCount(element, entryCount, 0);
            }
        }
        return false;
    }

    public void clear() {
        multiset().clear();
    }
}
