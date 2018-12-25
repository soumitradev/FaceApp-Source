package com.google.common.collect;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map.Entry;

class Maps$UnmodifiableEntries<K, V> extends ForwardingCollection<Entry<K, V>> {
    private final Collection<Entry<K, V>> entries;

    Maps$UnmodifiableEntries(Collection<Entry<K, V>> entries) {
        this.entries = entries;
    }

    protected Collection<Entry<K, V>> delegate() {
        return this.entries;
    }

    public Iterator<Entry<K, V>> iterator() {
        return Maps.unmodifiableEntryIterator(this.entries.iterator());
    }

    public Object[] toArray() {
        return standardToArray();
    }

    public <T> T[] toArray(T[] array) {
        return standardToArray(array);
    }
}
