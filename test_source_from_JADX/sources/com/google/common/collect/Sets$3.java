package com.google.common.collect;

import com.google.common.base.Predicate;
import java.util.Iterator;
import java.util.Set;

class Sets$3 extends Sets$SetView<E> {
    final /* synthetic */ Predicate val$notInSet2;
    final /* synthetic */ Set val$set1;
    final /* synthetic */ Set val$set2;

    Sets$3(Set set, Predicate predicate, Set set2) {
        this.val$set1 = set;
        this.val$notInSet2 = predicate;
        this.val$set2 = set2;
        super();
    }

    public Iterator<E> iterator() {
        return Iterators.filter(this.val$set1.iterator(), this.val$notInSet2);
    }

    public int size() {
        return Iterators.size(iterator());
    }

    public boolean isEmpty() {
        return this.val$set2.containsAll(this.val$set1);
    }

    public boolean contains(Object element) {
        return this.val$set1.contains(element) && !this.val$set2.contains(element);
    }
}
