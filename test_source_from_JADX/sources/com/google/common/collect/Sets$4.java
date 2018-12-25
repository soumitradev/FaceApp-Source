package com.google.common.collect;

import java.util.Iterator;
import java.util.Set;

class Sets$4 extends Sets$SetView<E> {
    final /* synthetic */ Set val$set1;
    final /* synthetic */ Set val$set2;

    Sets$4(Set set, Set set2) {
        this.val$set1 = set;
        this.val$set2 = set2;
        super();
    }

    public Iterator<E> iterator() {
        final Iterator<? extends E> itr1 = this.val$set1.iterator();
        final Iterator<? extends E> itr2 = this.val$set2.iterator();
        return new AbstractIterator<E>() {
            public E computeNext() {
                while (itr1.hasNext()) {
                    E elem1 = itr1.next();
                    if (!Sets$4.this.val$set2.contains(elem1)) {
                        return elem1;
                    }
                }
                while (itr2.hasNext()) {
                    elem1 = itr2.next();
                    if (!Sets$4.this.val$set1.contains(elem1)) {
                        return elem1;
                    }
                }
                return endOfData();
            }
        };
    }

    public int size() {
        return Iterators.size(iterator());
    }

    public boolean isEmpty() {
        return this.val$set1.equals(this.val$set2);
    }

    public boolean contains(Object element) {
        return this.val$set1.contains(element) ^ this.val$set2.contains(element);
    }
}
