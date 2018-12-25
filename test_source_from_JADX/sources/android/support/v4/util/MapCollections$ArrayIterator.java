package android.support.v4.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

final class MapCollections$ArrayIterator<T> implements Iterator<T> {
    boolean mCanRemove = false;
    int mIndex;
    final int mOffset;
    int mSize;
    final /* synthetic */ MapCollections this$0;

    MapCollections$ArrayIterator(MapCollections this$0, int offset) {
        this.this$0 = this$0;
        this.mOffset = offset;
        this.mSize = this$0.colGetSize();
    }

    public boolean hasNext() {
        return this.mIndex < this.mSize;
    }

    public T next() {
        if (hasNext()) {
            Object res = this.this$0.colGetEntry(this.mIndex, this.mOffset);
            this.mIndex++;
            this.mCanRemove = true;
            return res;
        }
        throw new NoSuchElementException();
    }

    public void remove() {
        if (this.mCanRemove) {
            this.mIndex--;
            this.mSize--;
            this.mCanRemove = false;
            this.this$0.colRemoveAt(this.mIndex);
            return;
        }
        throw new IllegalStateException();
    }
}
