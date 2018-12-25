package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;
import org.catrobat.catroid.common.Constants;

@GwtCompatible
@CheckReturnValue
public final class Collections2 {
    static final Joiner STANDARD_JOINER = Joiner.on(", ").useForNull("null");

    private Collections2() {
    }

    @CheckReturnValue
    public static <E> Collection<E> filter(Collection<E> unfiltered, Predicate<? super E> predicate) {
        if (unfiltered instanceof Collections2$FilteredCollection) {
            return ((Collections2$FilteredCollection) unfiltered).createCombined(predicate);
        }
        return new Collections2$FilteredCollection((Collection) Preconditions.checkNotNull(unfiltered), (Predicate) Preconditions.checkNotNull(predicate));
    }

    static boolean safeContains(Collection<?> collection, @Nullable Object object) {
        Preconditions.checkNotNull(collection);
        try {
            return collection.contains(object);
        } catch (ClassCastException e) {
            return false;
        } catch (NullPointerException e2) {
            return false;
        }
    }

    static boolean safeRemove(Collection<?> collection, @Nullable Object object) {
        Preconditions.checkNotNull(collection);
        try {
            return collection.remove(object);
        } catch (ClassCastException e) {
            return false;
        } catch (NullPointerException e2) {
            return false;
        }
    }

    public static <F, T> Collection<T> transform(Collection<F> fromCollection, Function<? super F, T> function) {
        return new Collections2$TransformedCollection(fromCollection, function);
    }

    static boolean containsAllImpl(Collection<?> self, Collection<?> c) {
        return Iterables.all(c, Predicates.in(self));
    }

    static String toStringImpl(Collection<?> collection) {
        StringBuilder sb = newStringBuilderForCollection(collection.size()).append(Constants.REMIX_URL_PREFIX_INDICATOR);
        STANDARD_JOINER.appendTo(sb, Iterables.transform(collection, new Collections2$1(collection)));
        sb.append(Constants.REMIX_URL_SUFIX_INDICATOR);
        return sb.toString();
    }

    static StringBuilder newStringBuilderForCollection(int size) {
        CollectPreconditions.checkNonnegative(size, "size");
        return new StringBuilder((int) Math.min(((long) size) * 8, 1073741824));
    }

    static <T> Collection<T> cast(Iterable<T> iterable) {
        return (Collection) iterable;
    }

    @Beta
    public static <E extends Comparable<? super E>> Collection<List<E>> orderedPermutations(Iterable<E> elements) {
        return orderedPermutations(elements, Ordering.natural());
    }

    @Beta
    public static <E> Collection<List<E>> orderedPermutations(Iterable<E> elements, Comparator<? super E> comparator) {
        return new Collections2$OrderedPermutationCollection(elements, comparator);
    }

    @Beta
    public static <E> Collection<List<E>> permutations(Collection<E> elements) {
        return new Collections2$PermutationCollection(ImmutableList.copyOf((Collection) elements));
    }

    private static boolean isPermutation(List<?> first, List<?> second) {
        if (first.size() != second.size()) {
            return false;
        }
        return HashMultiset.create(first).equals(HashMultiset.create(second));
    }

    private static boolean isPositiveInt(long n) {
        return n >= 0 && n <= 2147483647L;
    }
}
