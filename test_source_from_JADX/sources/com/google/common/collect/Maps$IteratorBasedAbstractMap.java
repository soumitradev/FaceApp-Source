package com.google.common.collect;

import java.util.AbstractMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

abstract class Maps$IteratorBasedAbstractMap<K, V> extends AbstractMap<K, V> {

    /* renamed from: com.google.common.collect.Maps$IteratorBasedAbstractMap$1 */
    class C12811 extends Maps$EntrySet<K, V> {
        C12811() {
        }

        Map<K, V> map() {
            return Maps$IteratorBasedAbstractMap.this;
        }

        public Iterator<Entry<K, V>> iterator() {
            return Maps$IteratorBasedAbstractMap.this.entryIterator();
        }
    }

    abstract Iterator<Entry<K, V>> entryIterator();

    public abstract int size();

    Maps$IteratorBasedAbstractMap() {
    }

    public Set<Entry<K, V>> entrySet() {
        return new C12811();
    }

    public void clear() {
        Iterators.clear(entryIterator());
    }
}
