package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.primitives.Ints;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;

@GwtCompatible(emulated = true)
public final class Lists {
    private Lists() {
    }

    @GwtCompatible(serializable = true)
    public static <E> ArrayList<E> newArrayList() {
        return new ArrayList();
    }

    @GwtCompatible(serializable = true)
    public static <E> ArrayList<E> newArrayList(E... elements) {
        Preconditions.checkNotNull(elements);
        ArrayList<E> list = new ArrayList(computeArrayListCapacity(elements.length));
        Collections.addAll(list, elements);
        return list;
    }

    @VisibleForTesting
    static int computeArrayListCapacity(int arraySize) {
        CollectPreconditions.checkNonnegative(arraySize, "arraySize");
        return Ints.saturatedCast((((long) arraySize) + 5) + ((long) (arraySize / 10)));
    }

    @GwtCompatible(serializable = true)
    public static <E> ArrayList<E> newArrayList(Iterable<? extends E> elements) {
        Preconditions.checkNotNull(elements);
        return elements instanceof Collection ? new ArrayList(Collections2.cast(elements)) : newArrayList(elements.iterator());
    }

    @GwtCompatible(serializable = true)
    public static <E> ArrayList<E> newArrayList(Iterator<? extends E> elements) {
        ArrayList<E> list = newArrayList();
        Iterators.addAll(list, elements);
        return list;
    }

    @GwtCompatible(serializable = true)
    public static <E> ArrayList<E> newArrayListWithCapacity(int initialArraySize) {
        CollectPreconditions.checkNonnegative(initialArraySize, "initialArraySize");
        return new ArrayList(initialArraySize);
    }

    @GwtCompatible(serializable = true)
    public static <E> ArrayList<E> newArrayListWithExpectedSize(int estimatedSize) {
        return new ArrayList(computeArrayListCapacity(estimatedSize));
    }

    @GwtCompatible(serializable = true)
    public static <E> LinkedList<E> newLinkedList() {
        return new LinkedList();
    }

    @GwtCompatible(serializable = true)
    public static <E> LinkedList<E> newLinkedList(Iterable<? extends E> elements) {
        LinkedList<E> list = newLinkedList();
        Iterables.addAll(list, elements);
        return list;
    }

    @GwtIncompatible("CopyOnWriteArrayList")
    public static <E> CopyOnWriteArrayList<E> newCopyOnWriteArrayList() {
        return new CopyOnWriteArrayList();
    }

    @GwtIncompatible("CopyOnWriteArrayList")
    public static <E> CopyOnWriteArrayList<E> newCopyOnWriteArrayList(Iterable<? extends E> elements) {
        return new CopyOnWriteArrayList(elements instanceof Collection ? Collections2.cast(elements) : newArrayList((Iterable) elements));
    }

    public static <E> List<E> asList(@Nullable E first, E[] rest) {
        return new Lists$OnePlusArrayList(first, rest);
    }

    public static <E> List<E> asList(@Nullable E first, @Nullable E second, E[] rest) {
        return new Lists$TwoPlusArrayList(first, second, rest);
    }

    public static <B> List<List<B>> cartesianProduct(List<? extends List<? extends B>> lists) {
        return CartesianList.create(lists);
    }

    public static <B> List<List<B>> cartesianProduct(List<? extends B>... lists) {
        return cartesianProduct(Arrays.asList(lists));
    }

    @CheckReturnValue
    public static <F, T> List<T> transform(List<F> fromList, Function<? super F, ? extends T> function) {
        return fromList instanceof RandomAccess ? new Lists$TransformingRandomAccessList(fromList, function) : new Lists$TransformingSequentialList(fromList, function);
    }

    public static <T> List<List<T>> partition(List<T> list, int size) {
        Preconditions.checkNotNull(list);
        Preconditions.checkArgument(size > 0);
        return list instanceof RandomAccess ? new Lists$RandomAccessPartition(list, size) : new Lists$Partition(list, size);
    }

    @Beta
    public static ImmutableList<Character> charactersOf(String string) {
        return new Lists$StringAsImmutableList((String) Preconditions.checkNotNull(string));
    }

    @Beta
    public static List<Character> charactersOf(CharSequence sequence) {
        return new Lists$CharSequenceAsList((CharSequence) Preconditions.checkNotNull(sequence));
    }

    @CheckReturnValue
    public static <T> List<T> reverse(List<T> list) {
        if (list instanceof ImmutableList) {
            return ((ImmutableList) list).reverse();
        }
        if (list instanceof Lists$ReverseList) {
            return ((Lists$ReverseList) list).getForwardList();
        }
        if (list instanceof RandomAccess) {
            return new Lists$RandomAccessReverseList(list);
        }
        return new Lists$ReverseList(list);
    }

    static int hashCodeImpl(List<?> list) {
        int hashCode = 1;
        for (Object o : list) {
            hashCode = (((hashCode * 31) + (o == null ? 0 : o.hashCode())) ^ -1) ^ -1;
        }
        return hashCode;
    }

    static boolean equalsImpl(List<?> thisList, @Nullable Object other) {
        if (other == Preconditions.checkNotNull(thisList)) {
            return true;
        }
        if (!(other instanceof List)) {
            return false;
        }
        List<?> otherList = (List) other;
        int size = thisList.size();
        if (size != otherList.size()) {
            return false;
        }
        if (!(thisList instanceof RandomAccess) || !(otherList instanceof RandomAccess)) {
            return Iterators.elementsEqual(thisList.iterator(), otherList.iterator());
        }
        for (int i = 0; i < size; i++) {
            if (!Objects.equal(thisList.get(i), otherList.get(i))) {
                return false;
            }
        }
        return true;
    }

    static <E> boolean addAllImpl(List<E> list, int index, Iterable<? extends E> elements) {
        boolean changed = false;
        ListIterator<E> listIterator = list.listIterator(index);
        for (E e : elements) {
            listIterator.add(e);
            changed = true;
        }
        return changed;
    }

    static int indexOfImpl(List<?> list, @Nullable Object element) {
        if (list instanceof RandomAccess) {
            return indexOfRandomAccess(list, element);
        }
        ListIterator<?> listIterator = list.listIterator();
        while (listIterator.hasNext()) {
            if (Objects.equal(element, listIterator.next())) {
                return listIterator.previousIndex();
            }
        }
        return -1;
    }

    private static int indexOfRandomAccess(List<?> list, @Nullable Object element) {
        int size = list.size();
        int i = 0;
        if (element == null) {
            while (i < size) {
                if (list.get(i) == null) {
                    return i;
                }
                i++;
            }
        } else {
            while (i < size) {
                if (element.equals(list.get(i))) {
                    return i;
                }
                i++;
            }
        }
        return -1;
    }

    static int lastIndexOfImpl(List<?> list, @Nullable Object element) {
        if (list instanceof RandomAccess) {
            return lastIndexOfRandomAccess(list, element);
        }
        ListIterator<?> listIterator = list.listIterator(list.size());
        while (listIterator.hasPrevious()) {
            if (Objects.equal(element, listIterator.previous())) {
                return listIterator.nextIndex();
            }
        }
        return -1;
    }

    private static int lastIndexOfRandomAccess(List<?> list, @Nullable Object element) {
        int i;
        if (element == null) {
            for (i = list.size() - 1; i >= 0; i--) {
                if (list.get(i) == null) {
                    return i;
                }
            }
        } else {
            for (i = list.size() - 1; i >= 0; i--) {
                if (element.equals(list.get(i))) {
                    return i;
                }
            }
        }
        return -1;
    }

    static <E> ListIterator<E> listIteratorImpl(List<E> list, int index) {
        return new Lists$AbstractListWrapper(list).listIterator(index);
    }

    static <E> List<E> subListImpl(List<E> list, int fromIndex, int toIndex) {
        List<E> wrapper;
        if (list instanceof RandomAccess) {
            wrapper = new Lists$1(list);
        } else {
            wrapper = new Lists$2(list);
        }
        return wrapper.subList(fromIndex, toIndex);
    }

    static <T> List<T> cast(Iterable<T> iterable) {
        return (List) iterable;
    }
}
