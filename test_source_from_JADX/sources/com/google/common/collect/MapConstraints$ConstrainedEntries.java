package com.google.common.collect;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map.Entry;

class MapConstraints$ConstrainedEntries<K, V> extends ForwardingCollection<Entry<K, V>> {
    final MapConstraint<? super K, ? super V> constraint;
    final Collection<Entry<K, V>> entries;

    MapConstraints$ConstrainedEntries(Collection<Entry<K, V>> entries, MapConstraint<? super K, ? super V> constraint) {
        this.entries = entries;
        this.constraint = constraint;
    }

    protected Collection<Entry<K, V>> delegate() {
        return this.entries;
    }

    public Iterator<Entry<K, V>> iterator() {
        return new TransformedIterator<Entry<K, V>, Entry<K, V>>(this.entries.iterator()) {
            Entry<K, V> transform(Entry<K, V> from) {
                return MapConstraints.access$500(from, MapConstraints$ConstrainedEntries.this.constraint);
            }
        };
    }

    public Object[] toArray() {
        return standardToArray();
    }

    public <T> T[] toArray(T[] array) {
        return standardToArray(array);
    }

    public boolean contains(Object o) {
        return Maps.containsEntryImpl(delegate(), o);
    }

    public boolean containsAll(Collection<?> c) {
        return standardContainsAll(c);
    }

    public boolean remove(Object o) {
        return Maps.removeEntryImpl(delegate(), o);
    }

    public boolean removeAll(Collection<?> c) {
        return standardRemoveAll(c);
    }

    public boolean retainAll(Collection<?> c) {
        return standardRetainAll(c);
    }
}
