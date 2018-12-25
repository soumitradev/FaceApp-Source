package android.support.v4.util;

import java.util.Collection;
import java.util.Iterator;

final class MapCollections$ValuesCollection implements Collection<V> {
    final /* synthetic */ MapCollections this$0;

    MapCollections$ValuesCollection(MapCollections this$0) {
        this.this$0 = this$0;
    }

    public boolean add(V v) {
        throw new UnsupportedOperationException();
    }

    public boolean addAll(Collection<? extends V> collection) {
        throw new UnsupportedOperationException();
    }

    public void clear() {
        this.this$0.colClear();
    }

    public boolean contains(Object object) {
        return this.this$0.colIndexOfValue(object) >= 0;
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

    public Iterator<V> iterator() {
        return new MapCollections$ArrayIterator(this.this$0, 1);
    }

    public boolean remove(Object object) {
        int index = this.this$0.colIndexOfValue(object);
        if (index < 0) {
            return false;
        }
        this.this$0.colRemoveAt(index);
        return true;
    }

    public boolean removeAll(Collection<?> collection) {
        int N = this.this$0.colGetSize();
        boolean changed = false;
        int i = 0;
        while (i < N) {
            if (collection.contains(this.this$0.colGetEntry(i, 1))) {
                this.this$0.colRemoveAt(i);
                i--;
                N--;
                changed = true;
            }
            i++;
        }
        return changed;
    }

    public boolean retainAll(Collection<?> collection) {
        int N = this.this$0.colGetSize();
        boolean changed = false;
        int i = 0;
        while (i < N) {
            if (!collection.contains(this.this$0.colGetEntry(i, 1))) {
                this.this$0.colRemoveAt(i);
                i--;
                N--;
                changed = true;
            }
            i++;
        }
        return changed;
    }

    public int size() {
        return this.this$0.colGetSize();
    }

    public Object[] toArray() {
        return this.this$0.toArrayHelper(1);
    }

    public <T> T[] toArray(T[] array) {
        return this.this$0.toArrayHelper(array, 1);
    }
}
