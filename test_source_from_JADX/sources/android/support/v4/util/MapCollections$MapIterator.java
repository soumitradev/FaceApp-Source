package android.support.v4.util;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.NoSuchElementException;

final class MapCollections$MapIterator implements Iterator<Entry<K, V>>, Entry<K, V> {
    int mEnd;
    boolean mEntryValid = false;
    int mIndex;
    final /* synthetic */ MapCollections this$0;

    MapCollections$MapIterator(MapCollections this$0) {
        this.this$0 = this$0;
        this.mEnd = this$0.colGetSize() - 1;
        this.mIndex = -1;
    }

    public boolean hasNext() {
        return this.mIndex < this.mEnd;
    }

    public Entry<K, V> next() {
        if (hasNext()) {
            this.mIndex++;
            this.mEntryValid = true;
            return this;
        }
        throw new NoSuchElementException();
    }

    public void remove() {
        if (this.mEntryValid) {
            this.this$0.colRemoveAt(this.mIndex);
            this.mIndex--;
            this.mEnd--;
            this.mEntryValid = false;
            return;
        }
        throw new IllegalStateException();
    }

    public K getKey() {
        if (this.mEntryValid) {
            return this.this$0.colGetEntry(this.mIndex, 0);
        }
        throw new IllegalStateException("This container does not support retaining Map.Entry objects");
    }

    public V getValue() {
        if (this.mEntryValid) {
            return this.this$0.colGetEntry(this.mIndex, 1);
        }
        throw new IllegalStateException("This container does not support retaining Map.Entry objects");
    }

    public V setValue(V object) {
        if (this.mEntryValid) {
            return this.this$0.colSetValue(this.mIndex, object);
        }
        throw new IllegalStateException("This container does not support retaining Map.Entry objects");
    }

    public boolean equals(Object o) {
        if (this.mEntryValid) {
            boolean z = false;
            if (!(o instanceof Entry)) {
                return false;
            }
            Entry<?, ?> e = (Entry) o;
            if (ContainerHelpers.equal(e.getKey(), this.this$0.colGetEntry(this.mIndex, 0)) && ContainerHelpers.equal(e.getValue(), this.this$0.colGetEntry(this.mIndex, 1))) {
                z = true;
            }
            return z;
        }
        throw new IllegalStateException("This container does not support retaining Map.Entry objects");
    }

    public int hashCode() {
        if (this.mEntryValid) {
            int i = 0;
            Object key = this.this$0.colGetEntry(this.mIndex, 0);
            Object value = this.this$0.colGetEntry(this.mIndex, 1);
            int hashCode = key == null ? 0 : key.hashCode();
            if (value != null) {
                i = value.hashCode();
            }
            return i ^ hashCode;
        }
        throw new IllegalStateException("This container does not support retaining Map.Entry objects");
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getKey());
        stringBuilder.append("=");
        stringBuilder.append(getValue());
        return stringBuilder.toString();
    }
}
