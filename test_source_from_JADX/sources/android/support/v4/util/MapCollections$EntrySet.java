package android.support.v4.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

final class MapCollections$EntrySet implements Set<Entry<K, V>> {
    final /* synthetic */ MapCollections this$0;

    MapCollections$EntrySet(MapCollections this$0) {
        this.this$0 = this$0;
    }

    public boolean add(Entry<K, V> entry) {
        throw new UnsupportedOperationException();
    }

    public boolean addAll(Collection<? extends Entry<K, V>> collection) {
        int oldSize = this.this$0.colGetSize();
        for (Entry<K, V> entry : collection) {
            this.this$0.colPut(entry.getKey(), entry.getValue());
        }
        return oldSize != this.this$0.colGetSize();
    }

    public void clear() {
        this.this$0.colClear();
    }

    public boolean contains(Object o) {
        if (!(o instanceof Entry)) {
            return false;
        }
        Entry<?, ?> e = (Entry) o;
        int index = this.this$0.colIndexOfKey(e.getKey());
        if (index < 0) {
            return false;
        }
        return ContainerHelpers.equal(this.this$0.colGetEntry(index, 1), e.getValue());
    }

    public boolean containsAll(Collection<?> collection) {
        for (Object contains : collection) {
            if (!contains(contains)) {
                return false;
            }
        }
        return true;
    }

    public boolean isEmpty() {
        return this.this$0.colGetSize() == 0;
    }

    public Iterator<Entry<K, V>> iterator() {
        return new MapCollections$MapIterator(this.this$0);
    }

    public boolean remove(Object object) {
        throw new UnsupportedOperationException();
    }

    public boolean removeAll(Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    public boolean retainAll(Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    public int size() {
        return this.this$0.colGetSize();
    }

    public Object[] toArray() {
        throw new UnsupportedOperationException();
    }

    public <T> T[] toArray(T[] tArr) {
        throw new UnsupportedOperationException();
    }

    public boolean equals(Object object) {
        return MapCollections.equalsSetHelper(this, object);
    }

    public int hashCode() {
        int result = 0;
        for (int i = this.this$0.colGetSize() - 1; i >= 0; i--) {
            int i2 = 0;
            Object key = this.this$0.colGetEntry(i, 0);
            Object value = this.this$0.colGetEntry(i, 1);
            int hashCode = key == null ? 0 : key.hashCode();
            if (value != null) {
                i2 = value.hashCode();
            }
            result += i2 ^ hashCode;
        }
        return result;
    }
}
