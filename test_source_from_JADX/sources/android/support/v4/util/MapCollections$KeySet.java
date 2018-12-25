package android.support.v4.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

final class MapCollections$KeySet implements Set<K> {
    final /* synthetic */ MapCollections this$0;

    MapCollections$KeySet(MapCollections this$0) {
        this.this$0 = this$0;
    }

    public boolean add(K k) {
        throw new UnsupportedOperationException();
    }

    public boolean addAll(Collection<? extends K> collection) {
        throw new UnsupportedOperationException();
    }

    public void clear() {
        this.this$0.colClear();
    }

    public boolean contains(Object object) {
        return this.this$0.colIndexOfKey(object) >= 0;
    }

    public boolean containsAll(Collection<?> collection) {
        return MapCollections.containsAllHelper(this.this$0.colGetMap(), collection);
    }

    public boolean isEmpty() {
        return this.this$0.colGetSize() == 0;
    }

    public Iterator<K> iterator() {
        return new MapCollections$ArrayIterator(this.this$0, 0);
    }

    public boolean remove(Object object) {
        int index = this.this$0.colIndexOfKey(object);
        if (index < 0) {
            return false;
        }
        this.this$0.colRemoveAt(index);
        return true;
    }

    public boolean removeAll(Collection<?> collection) {
        return MapCollections.removeAllHelper(this.this$0.colGetMap(), collection);
    }

    public boolean retainAll(Collection<?> collection) {
        return MapCollections.retainAllHelper(this.this$0.colGetMap(), collection);
    }

    public int size() {
        return this.this$0.colGetSize();
    }

    public Object[] toArray() {
        return this.this$0.toArrayHelper(0);
    }

    public <T> T[] toArray(T[] array) {
        return this.this$0.toArrayHelper(array, 0);
    }

    public boolean equals(Object object) {
        return MapCollections.equalsSetHelper(this, object);
    }

    public int hashCode() {
        int result = 0;
        for (int i = this.this$0.colGetSize() - 1; i >= 0; i--) {
            int i2 = 0;
            Object obj = this.this$0.colGetEntry(i, 0);
            if (obj != null) {
                i2 = obj.hashCode();
            }
            result += i2;
        }
        return result;
    }
}
