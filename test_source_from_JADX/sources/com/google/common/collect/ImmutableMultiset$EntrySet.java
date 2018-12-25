package com.google.common.collect;

import com.google.common.collect.Multiset.Entry;

final class ImmutableMultiset$EntrySet extends Indexed<Entry<E>> {
    private static final long serialVersionUID = 0;
    final /* synthetic */ ImmutableMultiset this$0;

    private ImmutableMultiset$EntrySet(ImmutableMultiset immutableMultiset) {
        this.this$0 = immutableMultiset;
    }

    boolean isPartialView() {
        return this.this$0.isPartialView();
    }

    Entry<E> get(int index) {
        return this.this$0.getEntry(index);
    }

    public int size() {
        return this.this$0.elementSet().size();
    }

    public boolean contains(Object o) {
        boolean z = false;
        if (!(o instanceof Entry)) {
            return false;
        }
        Entry<?> entry = (Entry) o;
        if (entry.getCount() <= 0) {
            return false;
        }
        if (this.this$0.count(entry.getElement()) == entry.getCount()) {
            z = true;
        }
        return z;
    }

    public int hashCode() {
        return this.this$0.hashCode();
    }

    Object writeReplace() {
        return new ImmutableMultiset$EntrySetSerializedForm(this.this$0);
    }
}
