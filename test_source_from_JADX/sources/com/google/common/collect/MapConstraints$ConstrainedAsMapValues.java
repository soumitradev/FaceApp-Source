package com.google.common.collect;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

class MapConstraints$ConstrainedAsMapValues<K, V> extends ForwardingCollection<Collection<V>> {
    final Collection<Collection<V>> delegate;
    final Set<Entry<K, Collection<V>>> entrySet;

    MapConstraints$ConstrainedAsMapValues(Collection<Collection<V>> delegate, Set<Entry<K, Collection<V>>> entrySet) {
        this.delegate = delegate;
        this.entrySet = entrySet;
    }

    protected Collection<Collection<V>> delegate() {
        return this.delegate;
    }

    public Iterator<Collection<V>> iterator() {
        final Iterator<Entry<K, Collection<V>>> iterator = this.entrySet.iterator();
        return new Iterator<Collection<V>>() {
            public boolean hasNext() {
                return iterator.hasNext();
            }

            public Collection<V> next() {
                return (Collection) ((Entry) iterator.next()).getValue();
            }

            public void remove() {
                iterator.remove();
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
        return standardContains(o);
    }

    public boolean containsAll(Collection<?> c) {
        return standardContainsAll(c);
    }

    public boolean remove(Object o) {
        return standardRemove(o);
    }

    public boolean removeAll(Collection<?> c) {
        return standardRemoveAll(c);
    }

    public boolean retainAll(Collection<?> c) {
        return standardRetainAll(c);
    }
}
