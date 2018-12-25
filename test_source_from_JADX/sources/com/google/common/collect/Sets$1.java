package com.google.common.collect;

import com.google.common.collect.ImmutableSet.Builder;
import java.util.Iterator;
import java.util.Set;

class Sets$1 extends Sets$SetView<E> {
    final /* synthetic */ Set val$set1;
    final /* synthetic */ Set val$set2;
    final /* synthetic */ Set val$set2minus1;

    Sets$1(Set set, Set set2, Set set3) {
        this.val$set1 = set;
        this.val$set2minus1 = set2;
        this.val$set2 = set3;
        super();
    }

    public int size() {
        return this.val$set1.size() + this.val$set2minus1.size();
    }

    public boolean isEmpty() {
        return this.val$set1.isEmpty() && this.val$set2.isEmpty();
    }

    public Iterator<E> iterator() {
        return Iterators.unmodifiableIterator(Iterators.concat(this.val$set1.iterator(), this.val$set2minus1.iterator()));
    }

    public boolean contains(Object object) {
        if (!this.val$set1.contains(object)) {
            if (!this.val$set2.contains(object)) {
                return false;
            }
        }
        return true;
    }

    public <S extends Set<E>> S copyInto(S set) {
        set.addAll(this.val$set1);
        set.addAll(this.val$set2);
        return set;
    }

    public ImmutableSet<E> immutableCopy() {
        return new Builder().addAll(this.val$set1).addAll(this.val$set2).build();
    }
}
