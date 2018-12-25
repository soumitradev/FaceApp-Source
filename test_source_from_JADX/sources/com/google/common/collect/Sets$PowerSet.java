package com.google.common.collect;

import com.google.common.base.Preconditions;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Set;
import javax.annotation.Nullable;

final class Sets$PowerSet<E> extends AbstractSet<Set<E>> {
    final ImmutableMap<E, Integer> inputSet;

    Sets$PowerSet(Set<E> input) {
        this.inputSet = Maps.indexMap(input);
        Preconditions.checkArgument(this.inputSet.size() <= 30, "Too many elements to create power set: %s > 30", Integer.valueOf(this.inputSet.size()));
    }

    public int size() {
        return 1 << this.inputSet.size();
    }

    public boolean isEmpty() {
        return false;
    }

    public Iterator<Set<E>> iterator() {
        return new AbstractIndexedListIterator<Set<E>>(size()) {
            protected Set<E> get(int setBits) {
                return new Sets$SubSet(Sets$PowerSet.this.inputSet, setBits);
            }
        };
    }

    public boolean contains(@Nullable Object obj) {
        if (!(obj instanceof Set)) {
            return false;
        }
        return this.inputSet.keySet().containsAll((Set) obj);
    }

    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof Sets$PowerSet)) {
            return super.equals(obj);
        }
        return this.inputSet.equals(((Sets$PowerSet) obj).inputSet);
    }

    public int hashCode() {
        return this.inputSet.keySet().hashCode() << (this.inputSet.size() - 1);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("powerSet(");
        stringBuilder.append(this.inputSet);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}
