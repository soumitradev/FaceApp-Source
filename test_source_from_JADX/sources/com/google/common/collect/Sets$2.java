package com.google.common.collect;

import com.google.common.base.Predicate;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

class Sets$2 extends Sets$SetView<E> {
    final /* synthetic */ Predicate val$inSet2;
    final /* synthetic */ Set val$set1;
    final /* synthetic */ Set val$set2;

    Sets$2(Set set, Predicate predicate, Set set2) {
        this.val$set1 = set;
        this.val$inSet2 = predicate;
        this.val$set2 = set2;
        super();
    }

    public Iterator<E> iterator() {
        return Iterators.filter(this.val$set1.iterator(), this.val$inSet2);
    }

    public int size() {
        return Iterators.size(iterator());
    }

    public boolean isEmpty() {
        return iterator().hasNext() ^ 1;
    }

    public boolean contains(Object object) {
        return this.val$set1.contains(object) && this.val$set2.contains(object);
    }

    public boolean containsAll(Collection<?> collection) {
        return this.val$set1.containsAll(collection) && this.val$set2.containsAll(collection);
    }
}
