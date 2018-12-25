package com.google.common.collect;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import javax.annotation.Nullable;

class MapConstraints$ConstrainedAsMapEntries<K, V> extends ForwardingSet<Entry<K, Collection<V>>> {
    private final MapConstraint<? super K, ? super V> constraint;
    private final Set<Entry<K, Collection<V>>> entries;

    MapConstraints$ConstrainedAsMapEntries(Set<Entry<K, Collection<V>>> entries, MapConstraint<? super K, ? super V> constraint) {
        this.entries = entries;
        this.constraint = constraint;
    }

    protected Set<Entry<K, Collection<V>>> delegate() {
        return this.entries;
    }

    public Iterator<Entry<K, Collection<V>>> iterator() {
        return new TransformedIterator<Entry<K, Collection<V>>, Entry<K, Collection<V>>>(this.entries.iterator()) {
            Entry<K, Collection<V>> transform(Entry<K, Collection<V>> from) {
                return MapConstraints.access$700(from, MapConstraints$ConstrainedAsMapEntries.this.constraint);
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

    public boolean equals(@Nullable Object object) {
        return standardEquals(object);
    }

    public int hashCode() {
        return standardHashCode();
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
