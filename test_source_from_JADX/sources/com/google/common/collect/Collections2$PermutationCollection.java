package com.google.common.collect;

import com.google.common.math.IntMath;
import java.util.AbstractCollection;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;

final class Collections2$PermutationCollection<E> extends AbstractCollection<List<E>> {
    final ImmutableList<E> inputList;

    Collections2$PermutationCollection(ImmutableList<E> input) {
        this.inputList = input;
    }

    public int size() {
        return IntMath.factorial(this.inputList.size());
    }

    public boolean isEmpty() {
        return false;
    }

    public Iterator<List<E>> iterator() {
        return new Collections2$PermutationIterator(this.inputList);
    }

    public boolean contains(@Nullable Object obj) {
        if (!(obj instanceof List)) {
            return false;
        }
        return Collections2.access$100(this.inputList, (List) obj);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("permutations(");
        stringBuilder.append(this.inputList);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}
