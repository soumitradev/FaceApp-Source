package com.google.common.collect;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

final class Collections2$OrderedPermutationIterator<E> extends AbstractIterator<List<E>> {
    final Comparator<? super E> comparator;
    List<E> nextPermutation;

    Collections2$OrderedPermutationIterator(List<E> list, Comparator<? super E> comparator) {
        this.nextPermutation = Lists.newArrayList(list);
        this.comparator = comparator;
    }

    protected List<E> computeNext() {
        if (this.nextPermutation == null) {
            return (List) endOfData();
        }
        ImmutableList<E> next = ImmutableList.copyOf(this.nextPermutation);
        calculateNextPermutation();
        return next;
    }

    void calculateNextPermutation() {
        int j = findNextJ();
        if (j == -1) {
            this.nextPermutation = null;
            return;
        }
        Collections.swap(this.nextPermutation, j, findNextL(j));
        Collections.reverse(this.nextPermutation.subList(j + 1, this.nextPermutation.size()));
    }

    int findNextJ() {
        for (int k = this.nextPermutation.size() - 2; k >= 0; k--) {
            if (this.comparator.compare(this.nextPermutation.get(k), this.nextPermutation.get(k + 1)) < 0) {
                return k;
            }
        }
        return -1;
    }

    int findNextL(int j) {
        E ak = this.nextPermutation.get(j);
        for (int l = this.nextPermutation.size() - 1; l > j; l--) {
            if (this.comparator.compare(ak, this.nextPermutation.get(l)) < 0) {
                return l;
            }
        }
        throw new AssertionError("this statement should be unreachable");
    }
}
