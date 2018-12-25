package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Multiset.Entry;
import com.google.common.collect.Multisets.ImmutableEntry;
import com.google.common.primitives.Ints;
import java.util.Collection;
import javax.annotation.Nullable;

@GwtCompatible(serializable = true)
class RegularImmutableMultiset<E> extends ImmutableMultiset<E> {
    static final RegularImmutableMultiset<Object> EMPTY = new RegularImmutableMultiset(ImmutableList.of());
    private transient ImmutableSet<E> elementSet;
    private final transient ImmutableEntry<E>[] entries;
    private final transient int hashCode;
    private final transient ImmutableEntry<E>[] hashTable;
    private final transient int size;

    private final class ElementSet extends Indexed<E> {
        private ElementSet() {
        }

        E get(int index) {
            return RegularImmutableMultiset.this.entries[index].getElement();
        }

        public boolean contains(@Nullable Object object) {
            return RegularImmutableMultiset.this.contains(object);
        }

        boolean isPartialView() {
            return true;
        }

        public int size() {
            return RegularImmutableMultiset.this.entries.length;
        }
    }

    private static final class NonTerminalEntry<E> extends ImmutableEntry<E> {
        private final ImmutableEntry<E> nextInBucket;

        NonTerminalEntry(E element, int count, ImmutableEntry<E> nextInBucket) {
            super(element, count);
            this.nextInBucket = nextInBucket;
        }

        public ImmutableEntry<E> nextInBucket() {
            return this.nextInBucket;
        }
    }

    RegularImmutableMultiset(Collection<? extends Entry<? extends E>> entries) {
        RegularImmutableMultiset regularImmutableMultiset = this;
        int distinct = entries.size();
        ImmutableEntry<E>[] entryArray = new ImmutableEntry[distinct];
        if (distinct == 0) {
            regularImmutableMultiset.entries = entryArray;
            regularImmutableMultiset.hashTable = null;
            regularImmutableMultiset.size = 0;
            regularImmutableMultiset.hashCode = 0;
            regularImmutableMultiset.elementSet = ImmutableSet.of();
            int i = distinct;
            return;
        }
        int tableSize = Hashing.closedTableSize(distinct, 0);
        int mask = tableSize - 1;
        ImmutableEntry<E>[] hashTable = new ImmutableEntry[tableSize];
        int index = 0;
        int hashCode = 0;
        long size = 0;
        for (Entry<? extends E> entry : entries) {
            E element = Preconditions.checkNotNull(entry.getElement());
            int count = entry.getCount();
            int hash = element.hashCode();
            int bucket = Hashing.smear(hash) & mask;
            ImmutableEntry<E> bucketHead = hashTable[bucket];
            if (bucketHead == null) {
                i = distinct;
                boolean distinct2 = (entry instanceof ImmutableEntry) != 0 && (entry instanceof NonTerminalEntry) == 0;
                boolean z;
                if (distinct2) {
                    z = distinct2;
                    distinct = (ImmutableEntry) entry;
                } else {
                    z = distinct2;
                    distinct = new ImmutableEntry(element, count);
                }
            } else {
                i = distinct;
                distinct = new NonTerminalEntry(element, count, bucketHead);
            }
            hashCode += hash ^ count;
            int index2 = index + 1;
            entryArray[index] = distinct;
            hashTable[bucket] = distinct;
            size += (long) count;
            distinct = i;
            index = index2;
            tableSize = tableSize;
        }
        int i2 = tableSize;
        regularImmutableMultiset.entries = entryArray;
        regularImmutableMultiset.hashTable = hashTable;
        regularImmutableMultiset.size = Ints.saturatedCast(size);
        regularImmutableMultiset.hashCode = hashCode;
    }

    boolean isPartialView() {
        return false;
    }

    public int count(@Nullable Object element) {
        ImmutableEntry<E>[] hashTable = this.hashTable;
        if (element != null) {
            if (hashTable != null) {
                for (ImmutableEntry<E> entry = hashTable[Hashing.smearedHash(element) & (hashTable.length - 1)]; entry != null; entry = entry.nextInBucket()) {
                    if (Objects.equal(element, entry.getElement())) {
                        return entry.getCount();
                    }
                }
                return 0;
            }
        }
        return 0;
    }

    public int size() {
        return this.size;
    }

    public ImmutableSet<E> elementSet() {
        ImmutableSet<E> result = this.elementSet;
        if (result != null) {
            return result;
        }
        ImmutableSet<E> elementSet = new ElementSet();
        this.elementSet = elementSet;
        return elementSet;
    }

    Entry<E> getEntry(int index) {
        return this.entries[index];
    }

    public int hashCode() {
        return this.hashCode;
    }
}
