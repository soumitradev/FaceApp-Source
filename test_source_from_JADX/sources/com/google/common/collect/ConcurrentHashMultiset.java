package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.collect.Multiset.Entry;
import com.google.common.math.IntMath;
import com.google.common.primitives.Ints;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.Nullable;

public final class ConcurrentHashMultiset<E> extends AbstractMultiset<E> implements Serializable {
    private static final long serialVersionUID = 1;
    private final transient ConcurrentMap<E, AtomicInteger> countMap;

    private static class FieldSettersHolder {
        static final FieldSetter<ConcurrentHashMultiset> COUNT_MAP_FIELD_SETTER = Serialization.getFieldSetter(ConcurrentHashMultiset.class, "countMap");

        private FieldSettersHolder() {
        }
    }

    /* renamed from: com.google.common.collect.ConcurrentHashMultiset$2 */
    class C12652 extends AbstractIterator<Entry<E>> {
        private Iterator<Map.Entry<E, AtomicInteger>> mapEntries = ConcurrentHashMultiset.this.countMap.entrySet().iterator();

        C12652() {
        }

        protected Entry<E> computeNext() {
            while (this.mapEntries.hasNext()) {
                Map.Entry<E, AtomicInteger> mapEntry = (Map.Entry) this.mapEntries.next();
                int count = ((AtomicInteger) mapEntry.getValue()).get();
                if (count != 0) {
                    return Multisets.immutableEntry(mapEntry.getKey(), count);
                }
            }
            return (Entry) endOfData();
        }
    }

    private class EntrySet extends EntrySet {
        private EntrySet() {
            super();
        }

        ConcurrentHashMultiset<E> multiset() {
            return ConcurrentHashMultiset.this;
        }

        public Object[] toArray() {
            return snapshot().toArray();
        }

        public <T> T[] toArray(T[] array) {
            return snapshot().toArray(array);
        }

        private List<Entry<E>> snapshot() {
            List<Entry<E>> list = Lists.newArrayListWithExpectedSize(size());
            Iterators.addAll(list, iterator());
            return list;
        }
    }

    public /* bridge */ /* synthetic */ boolean add(Object x0) {
        return super.add(x0);
    }

    public /* bridge */ /* synthetic */ boolean addAll(Collection x0) {
        return super.addAll(x0);
    }

    public /* bridge */ /* synthetic */ boolean contains(Object x0) {
        return super.contains(x0);
    }

    public /* bridge */ /* synthetic */ Set elementSet() {
        return super.elementSet();
    }

    public /* bridge */ /* synthetic */ Set entrySet() {
        return super.entrySet();
    }

    public /* bridge */ /* synthetic */ boolean equals(Object x0) {
        return super.equals(x0);
    }

    public /* bridge */ /* synthetic */ int hashCode() {
        return super.hashCode();
    }

    public /* bridge */ /* synthetic */ Iterator iterator() {
        return super.iterator();
    }

    public /* bridge */ /* synthetic */ boolean remove(Object x0) {
        return super.remove(x0);
    }

    public /* bridge */ /* synthetic */ boolean removeAll(Collection x0) {
        return super.removeAll(x0);
    }

    public /* bridge */ /* synthetic */ boolean retainAll(Collection x0) {
        return super.retainAll(x0);
    }

    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }

    public static <E> ConcurrentHashMultiset<E> create() {
        return new ConcurrentHashMultiset(new ConcurrentHashMap());
    }

    public static <E> ConcurrentHashMultiset<E> create(Iterable<? extends E> elements) {
        ConcurrentHashMultiset<E> multiset = create();
        Iterables.addAll(multiset, elements);
        return multiset;
    }

    @Beta
    public static <E> ConcurrentHashMultiset<E> create(MapMaker mapMaker) {
        return new ConcurrentHashMultiset(mapMaker.makeMap());
    }

    @VisibleForTesting
    ConcurrentHashMultiset(ConcurrentMap<E, AtomicInteger> countMap) {
        Preconditions.checkArgument(countMap.isEmpty());
        this.countMap = countMap;
    }

    public int count(@Nullable Object element) {
        AtomicInteger existingCounter = (AtomicInteger) Maps.safeGet(this.countMap, element);
        return existingCounter == null ? 0 : existingCounter.get();
    }

    public int size() {
        long sum = 0;
        for (AtomicInteger value : this.countMap.values()) {
            sum += (long) value.get();
        }
        return Ints.saturatedCast(sum);
    }

    public Object[] toArray() {
        return snapshot().toArray();
    }

    public <T> T[] toArray(T[] array) {
        return snapshot().toArray(array);
    }

    private List<E> snapshot() {
        List<E> list = Lists.newArrayListWithExpectedSize(size());
        for (Entry<E> entry : entrySet()) {
            E element = entry.getElement();
            for (int i = entry.getCount(); i > 0; i--) {
                list.add(element);
            }
        }
        return list;
    }

    public int add(E element, int occurrences) {
        Preconditions.checkNotNull(element);
        if (occurrences == 0) {
            return count(element);
        }
        CollectPreconditions.checkPositive(occurrences, "occurences");
        while (true) {
            AtomicInteger existingCounter = (AtomicInteger) Maps.safeGet(this.countMap, element);
            if (existingCounter == null) {
                existingCounter = (AtomicInteger) this.countMap.putIfAbsent(element, new AtomicInteger(occurrences));
                if (existingCounter == null) {
                    return 0;
                }
            }
            while (true) {
                int oldValue = existingCounter.get();
                if (oldValue == 0) {
                    break;
                }
                try {
                    if (existingCounter.compareAndSet(oldValue, IntMath.checkedAdd(oldValue, occurrences))) {
                        return oldValue;
                    }
                } catch (ArithmeticException e) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Overflow adding ");
                    stringBuilder.append(occurrences);
                    stringBuilder.append(" occurrences to a count of ");
                    stringBuilder.append(oldValue);
                    throw new IllegalArgumentException(stringBuilder.toString());
                }
            }
            AtomicInteger newCounter = new AtomicInteger(occurrences);
            if (this.countMap.putIfAbsent(element, newCounter) == null) {
                break;
            } else if (this.countMap.replace(element, existingCounter, newCounter)) {
                break;
            }
        }
        return 0;
    }

    public int remove(@Nullable Object element, int occurrences) {
        if (occurrences == 0) {
            return count(element);
        }
        CollectPreconditions.checkPositive(occurrences, "occurences");
        AtomicInteger existingCounter = (AtomicInteger) Maps.safeGet(this.countMap, element);
        if (existingCounter == null) {
            return 0;
        }
        int oldValue;
        while (true) {
            oldValue = existingCounter.get();
            if (oldValue == 0) {
                return 0;
            }
            int newValue = Math.max(0, oldValue - occurrences);
            if (existingCounter.compareAndSet(oldValue, newValue)) {
                break;
            }
        }
        if (newValue == 0) {
            this.countMap.remove(element, existingCounter);
        }
        return oldValue;
    }

    public boolean removeExactly(@Nullable Object element, int occurrences) {
        if (occurrences == 0) {
            return true;
        }
        CollectPreconditions.checkPositive(occurrences, "occurences");
        AtomicInteger existingCounter = (AtomicInteger) Maps.safeGet(this.countMap, element);
        if (existingCounter == null) {
            return false;
        }
        while (true) {
            int oldValue = existingCounter.get();
            if (oldValue < occurrences) {
                return false;
            }
            int newValue = oldValue - occurrences;
            if (existingCounter.compareAndSet(oldValue, newValue)) {
                break;
            }
        }
        if (newValue == 0) {
            this.countMap.remove(element, existingCounter);
        }
        return true;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int setCount(E r6, int r7) {
        /*
        r5 = this;
        com.google.common.base.Preconditions.checkNotNull(r6);
        r0 = "count";
        com.google.common.collect.CollectPreconditions.checkNonnegative(r7, r0);
    L_0x0008:
        r0 = r5.countMap;
        r0 = com.google.common.collect.Maps.safeGet(r0, r6);
        r0 = (java.util.concurrent.atomic.AtomicInteger) r0;
        r1 = 0;
        if (r0 != 0) goto L_0x0027;
    L_0x0013:
        if (r7 != 0) goto L_0x0016;
    L_0x0015:
        return r1;
    L_0x0016:
        r2 = r5.countMap;
        r3 = new java.util.concurrent.atomic.AtomicInteger;
        r3.<init>(r7);
        r2 = r2.putIfAbsent(r6, r3);
        r0 = r2;
        r0 = (java.util.concurrent.atomic.AtomicInteger) r0;
        if (r0 != 0) goto L_0x0027;
    L_0x0026:
        return r1;
    L_0x0027:
        r2 = r0.get();
        if (r2 != 0) goto L_0x0049;
    L_0x002d:
        if (r7 != 0) goto L_0x0030;
    L_0x002f:
        return r1;
    L_0x0030:
        r3 = new java.util.concurrent.atomic.AtomicInteger;
        r3.<init>(r7);
        r4 = r5.countMap;
        r4 = r4.putIfAbsent(r6, r3);
        if (r4 == 0) goto L_0x0048;
    L_0x003d:
        r4 = r5.countMap;
        r4 = r4.replace(r6, r0, r3);
        if (r4 == 0) goto L_0x0046;
    L_0x0045:
        goto L_0x0048;
        goto L_0x0008;
    L_0x0048:
        return r1;
    L_0x0049:
        r3 = r0.compareAndSet(r2, r7);
        if (r3 == 0) goto L_0x0057;
    L_0x004f:
        if (r7 != 0) goto L_0x0056;
    L_0x0051:
        r1 = r5.countMap;
        r1.remove(r6, r0);
    L_0x0056:
        return r2;
    L_0x0057:
        goto L_0x0027;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.ConcurrentHashMultiset.setCount(java.lang.Object, int):int");
    }

    public boolean setCount(E element, int expectedOldCount, int newCount) {
        Preconditions.checkNotNull(element);
        CollectPreconditions.checkNonnegative(expectedOldCount, "oldCount");
        CollectPreconditions.checkNonnegative(newCount, "newCount");
        AtomicInteger existingCounter = (AtomicInteger) Maps.safeGet(this.countMap, element);
        boolean z = false;
        if (existingCounter != null) {
            int oldValue = existingCounter.get();
            if (oldValue == expectedOldCount) {
                if (oldValue == 0) {
                    if (newCount == 0) {
                        this.countMap.remove(element, existingCounter);
                        return true;
                    }
                    AtomicInteger newCounter = new AtomicInteger(newCount);
                    if (this.countMap.putIfAbsent(element, newCounter) != null) {
                        if (!this.countMap.replace(element, existingCounter, newCounter)) {
                            return z;
                        }
                    }
                    z = true;
                    return z;
                } else if (existingCounter.compareAndSet(oldValue, newCount)) {
                    if (newCount == 0) {
                        this.countMap.remove(element, existingCounter);
                    }
                    return true;
                }
            }
            return false;
        } else if (expectedOldCount != 0) {
            return false;
        } else {
            if (newCount == 0) {
                return true;
            }
            if (this.countMap.putIfAbsent(element, new AtomicInteger(newCount)) == null) {
                z = true;
            }
            return z;
        }
    }

    Set<E> createElementSet() {
        final Set<E> delegate = this.countMap.keySet();
        return new ForwardingSet<E>() {
            protected Set<E> delegate() {
                return delegate;
            }

            public boolean contains(@Nullable Object object) {
                return object != null && Collections2.safeContains(delegate, object);
            }

            public boolean containsAll(Collection<?> collection) {
                return standardContainsAll(collection);
            }

            public boolean remove(Object object) {
                return object != null && Collections2.safeRemove(delegate, object);
            }

            public boolean removeAll(Collection<?> c) {
                return standardRemoveAll(c);
            }
        };
    }

    public Set<Entry<E>> createEntrySet() {
        return new EntrySet();
    }

    int distinctElements() {
        return this.countMap.size();
    }

    public boolean isEmpty() {
        return this.countMap.isEmpty();
    }

    Iterator<Entry<E>> entryIterator() {
        final Iterator<Entry<E>> readOnlyIterator = new C12652();
        return new ForwardingIterator<Entry<E>>() {
            private Entry<E> last;

            protected Iterator<Entry<E>> delegate() {
                return readOnlyIterator;
            }

            public Entry<E> next() {
                this.last = (Entry) super.next();
                return this.last;
            }

            public void remove() {
                CollectPreconditions.checkRemove(this.last != null);
                ConcurrentHashMultiset.this.setCount(this.last.getElement(), 0);
                this.last = null;
            }
        };
    }

    public void clear() {
        this.countMap.clear();
    }

    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        stream.writeObject(this.countMap);
    }

    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        FieldSettersHolder.COUNT_MAP_FIELD_SETTER.set((Object) this, (ConcurrentMap) stream.readObject());
    }
}
