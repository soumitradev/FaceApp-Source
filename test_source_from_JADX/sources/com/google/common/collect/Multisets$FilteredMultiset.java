package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.Multiset.Entry;
import java.util.Iterator;
import java.util.Set;
import javax.annotation.Nullable;

final class Multisets$FilteredMultiset<E> extends AbstractMultiset<E> {
    final Predicate<? super E> predicate;
    final Multiset<E> unfiltered;

    /* renamed from: com.google.common.collect.Multisets$FilteredMultiset$1 */
    class C09571 implements Predicate<Entry<E>> {
        C09571() {
        }

        public boolean apply(Entry<E> entry) {
            return Multisets$FilteredMultiset.this.predicate.apply(entry.getElement());
        }
    }

    Multisets$FilteredMultiset(Multiset<E> unfiltered, Predicate<? super E> predicate) {
        this.unfiltered = (Multiset) Preconditions.checkNotNull(unfiltered);
        this.predicate = (Predicate) Preconditions.checkNotNull(predicate);
    }

    public UnmodifiableIterator<E> iterator() {
        return Iterators.filter(this.unfiltered.iterator(), this.predicate);
    }

    Set<E> createElementSet() {
        return Sets.filter(this.unfiltered.elementSet(), this.predicate);
    }

    Set<Entry<E>> createEntrySet() {
        return Sets.filter(this.unfiltered.entrySet(), new C09571());
    }

    Iterator<Entry<E>> entryIterator() {
        throw new AssertionError("should never be called");
    }

    int distinctElements() {
        return elementSet().size();
    }

    public int count(@Nullable Object element) {
        int count = this.unfiltered.count(element);
        int i = 0;
        if (count <= 0) {
            return 0;
        }
        if (this.predicate.apply(element)) {
            i = count;
        }
        return i;
    }

    public int add(@Nullable E element, int occurrences) {
        Preconditions.checkArgument(this.predicate.apply(element), "Element %s does not match predicate %s", element, this.predicate);
        return this.unfiltered.add(element, occurrences);
    }

    public int remove(@Nullable Object element, int occurrences) {
        CollectPreconditions.checkNonnegative(occurrences, "occurrences");
        if (occurrences == 0) {
            return count(element);
        }
        return contains(element) ? this.unfiltered.remove(element, occurrences) : 0;
    }

    public void clear() {
        elementSet().clear();
    }
}
