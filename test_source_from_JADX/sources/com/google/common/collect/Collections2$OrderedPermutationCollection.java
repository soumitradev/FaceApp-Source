package com.google.common.collect;

import com.google.common.math.LongMath;
import java.util.AbstractCollection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;

final class Collections2$OrderedPermutationCollection<E> extends AbstractCollection<List<E>> {
    final Comparator<? super E> comparator;
    final ImmutableList<E> inputList;
    final int size;

    Collections2$OrderedPermutationCollection(Iterable<E> input, Comparator<? super E> comparator) {
        this.inputList = Ordering.from(comparator).immutableSortedCopy(input);
        this.comparator = comparator;
        this.size = calculateSize(this.inputList, comparator);
    }

    private static <E> int calculateSize(List<E> sortedInputList, Comparator<? super E> comparator) {
        int n = 1;
        long permutations = 1;
        int r = 1;
        while (n < sortedInputList.size()) {
            if (comparator.compare(sortedInputList.get(n - 1), sortedInputList.get(n)) < 0) {
                permutations *= LongMath.binomial(n, r);
                r = 0;
                if (!Collections2.access$000(permutations)) {
                    return Integer.MAX_VALUE;
                }
            }
            n++;
            r++;
        }
        permutations *= LongMath.binomial(n, r);
        if (Collections2.access$000(permutations)) {
            return (int) permutations;
        }
        return Integer.MAX_VALUE;
    }

    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        return false;
    }

    public Iterator<List<E>> iterator() {
        return new Collections2$OrderedPermutationIterator(this.inputList, this.comparator);
    }

    public boolean contains(@Nullable Object obj) {
        if (!(obj instanceof List)) {
            return false;
        }
        return Collections2.access$100(this.inputList, (List) obj);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("orderedPermutationCollection(");
        stringBuilder.append(this.inputList);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}
