package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Set;
import javax.annotation.Nullable;

@GwtCompatible(emulated = true, serializable = true)
public final class LinkedHashMultimap<K, V> extends AbstractSetMultimap<K, V> {
    private static final int DEFAULT_KEY_CAPACITY = 16;
    private static final int DEFAULT_VALUE_SET_CAPACITY = 2;
    @VisibleForTesting
    static final double VALUE_SET_LOAD_FACTOR = 1.0d;
    @GwtIncompatible("java serialization not supported")
    private static final long serialVersionUID = 1;
    private transient LinkedHashMultimap$ValueEntry<K, V> multimapHeaderEntry;
    @VisibleForTesting
    transient int valueSetCapacity = 2;

    /* renamed from: com.google.common.collect.LinkedHashMultimap$1 */
    class C05471 implements Iterator<Entry<K, V>> {
        LinkedHashMultimap$ValueEntry<K, V> nextEntry = LinkedHashMultimap.this.multimapHeaderEntry.successorInMultimap;
        LinkedHashMultimap$ValueEntry<K, V> toRemove;

        C05471() {
        }

        public boolean hasNext() {
            return this.nextEntry != LinkedHashMultimap.this.multimapHeaderEntry;
        }

        public Entry<K, V> next() {
            if (hasNext()) {
                LinkedHashMultimap$ValueEntry<K, V> result = this.nextEntry;
                this.toRemove = result;
                this.nextEntry = this.nextEntry.successorInMultimap;
                return result;
            }
            throw new NoSuchElementException();
        }

        public void remove() {
            CollectPreconditions.checkRemove(this.toRemove != null);
            LinkedHashMultimap.this.remove(this.toRemove.getKey(), this.toRemove.getValue());
            this.toRemove = null;
        }
    }

    private interface ValueSetLink<K, V> {
        ValueSetLink<K, V> getPredecessorInValueSet();

        ValueSetLink<K, V> getSuccessorInValueSet();

        void setPredecessorInValueSet(ValueSetLink<K, V> valueSetLink);

        void setSuccessorInValueSet(ValueSetLink<K, V> valueSetLink);
    }

    @VisibleForTesting
    final class ValueSet extends Sets$ImprovedAbstractSet<V> implements ValueSetLink<K, V> {
        private ValueSetLink<K, V> firstEntry;
        @VisibleForTesting
        LinkedHashMultimap$ValueEntry<K, V>[] hashTable;
        private final K key;
        private ValueSetLink<K, V> lastEntry;
        private int modCount = 0;
        private int size = 0;

        /* renamed from: com.google.common.collect.LinkedHashMultimap$ValueSet$1 */
        class C05481 implements Iterator<V> {
            int expectedModCount = ValueSet.this.modCount;
            ValueSetLink<K, V> nextEntry = ValueSet.this.firstEntry;
            LinkedHashMultimap$ValueEntry<K, V> toRemove;

            C05481() {
            }

            private void checkForComodification() {
                if (ValueSet.this.modCount != this.expectedModCount) {
                    throw new ConcurrentModificationException();
                }
            }

            public boolean hasNext() {
                checkForComodification();
                return this.nextEntry != ValueSet.this;
            }

            public V next() {
                if (hasNext()) {
                    LinkedHashMultimap$ValueEntry<K, V> entry = this.nextEntry;
                    V result = entry.getValue();
                    this.toRemove = entry;
                    this.nextEntry = entry.getSuccessorInValueSet();
                    return result;
                }
                throw new NoSuchElementException();
            }

            public void remove() {
                checkForComodification();
                CollectPreconditions.checkRemove(this.toRemove != null);
                ValueSet.this.remove(this.toRemove.getValue());
                this.expectedModCount = ValueSet.this.modCount;
                this.toRemove = null;
            }
        }

        ValueSet(K key, int expectedValues) {
            this.key = key;
            this.firstEntry = this;
            this.lastEntry = this;
            this.hashTable = new LinkedHashMultimap$ValueEntry[Hashing.closedTableSize(expectedValues, 1.0d)];
        }

        private int mask() {
            return this.hashTable.length - 1;
        }

        public ValueSetLink<K, V> getPredecessorInValueSet() {
            return this.lastEntry;
        }

        public ValueSetLink<K, V> getSuccessorInValueSet() {
            return this.firstEntry;
        }

        public void setPredecessorInValueSet(ValueSetLink<K, V> entry) {
            this.lastEntry = entry;
        }

        public void setSuccessorInValueSet(ValueSetLink<K, V> entry) {
            this.firstEntry = entry;
        }

        public Iterator<V> iterator() {
            return new C05481();
        }

        public int size() {
            return this.size;
        }

        public boolean contains(@Nullable Object o) {
            int smearedHash = Hashing.smearedHash(o);
            for (LinkedHashMultimap$ValueEntry<K, V> entry = this.hashTable[mask() & smearedHash]; entry != null; entry = entry.nextInValueBucket) {
                if (entry.matchesValue(o, smearedHash)) {
                    return true;
                }
            }
            return false;
        }

        public boolean add(@Nullable V value) {
            LinkedHashMultimap$ValueEntry<K, V> entry;
            int smearedHash = Hashing.smearedHash(value);
            int bucket = mask() & smearedHash;
            LinkedHashMultimap$ValueEntry<K, V> rowHead = this.hashTable[bucket];
            for (entry = rowHead; entry != null; entry = entry.nextInValueBucket) {
                if (entry.matchesValue(value, smearedHash)) {
                    return false;
                }
            }
            entry = new LinkedHashMultimap$ValueEntry(this.key, value, smearedHash, rowHead);
            LinkedHashMultimap.succeedsInValueSet(this.lastEntry, entry);
            LinkedHashMultimap.succeedsInValueSet(entry, this);
            LinkedHashMultimap.succeedsInMultimap(LinkedHashMultimap.this.multimapHeaderEntry.getPredecessorInMultimap(), entry);
            LinkedHashMultimap.succeedsInMultimap(entry, LinkedHashMultimap.this.multimapHeaderEntry);
            this.hashTable[bucket] = entry;
            this.size++;
            this.modCount++;
            rehashIfNecessary();
            return true;
        }

        private void rehashIfNecessary() {
            if (Hashing.needsResizing(this.size, this.hashTable.length, 1.0d)) {
                LinkedHashMultimap$ValueEntry<K, V>[] hashTable = new LinkedHashMultimap$ValueEntry[(this.hashTable.length * 2)];
                this.hashTable = hashTable;
                int mask = hashTable.length - 1;
                for (LinkedHashMultimap$ValueEntry<K, V> entry = this.firstEntry; entry != this; entry = entry.getSuccessorInValueSet()) {
                    LinkedHashMultimap$ValueEntry<K, V> valueEntry = entry;
                    int bucket = valueEntry.smearedValueHash & mask;
                    valueEntry.nextInValueBucket = hashTable[bucket];
                    hashTable[bucket] = valueEntry;
                }
            }
        }

        public boolean remove(@Nullable Object o) {
            int smearedHash = Hashing.smearedHash(o);
            int bucket = mask() & smearedHash;
            LinkedHashMultimap$ValueEntry<K, V> prev = null;
            for (LinkedHashMultimap$ValueEntry<K, V> entry = this.hashTable[bucket]; entry != null; entry = entry.nextInValueBucket) {
                if (entry.matchesValue(o, smearedHash)) {
                    if (prev == null) {
                        this.hashTable[bucket] = entry.nextInValueBucket;
                    } else {
                        prev.nextInValueBucket = entry.nextInValueBucket;
                    }
                    LinkedHashMultimap.deleteFromValueSet(entry);
                    LinkedHashMultimap.deleteFromMultimap(entry);
                    this.size--;
                    this.modCount++;
                    return true;
                }
                prev = entry;
            }
            return false;
        }

        public void clear() {
            Arrays.fill(this.hashTable, null);
            this.size = 0;
            for (LinkedHashMultimap$ValueEntry<K, V> entry = this.firstEntry; entry != this; entry = entry.getSuccessorInValueSet()) {
                LinkedHashMultimap.deleteFromMultimap(entry);
            }
            LinkedHashMultimap.succeedsInValueSet(this, this);
            this.modCount++;
        }
    }

    public /* bridge */ /* synthetic */ Map asMap() {
        return super.asMap();
    }

    public /* bridge */ /* synthetic */ boolean containsEntry(Object x0, Object x1) {
        return super.containsEntry(x0, x1);
    }

    public /* bridge */ /* synthetic */ boolean containsKey(Object x0) {
        return super.containsKey(x0);
    }

    public /* bridge */ /* synthetic */ boolean containsValue(Object x0) {
        return super.containsValue(x0);
    }

    public /* bridge */ /* synthetic */ boolean equals(Object x0) {
        return super.equals(x0);
    }

    public /* bridge */ /* synthetic */ Set get(Object x0) {
        return super.get(x0);
    }

    public /* bridge */ /* synthetic */ int hashCode() {
        return super.hashCode();
    }

    public /* bridge */ /* synthetic */ boolean isEmpty() {
        return super.isEmpty();
    }

    public /* bridge */ /* synthetic */ Set keySet() {
        return super.keySet();
    }

    public /* bridge */ /* synthetic */ Multiset keys() {
        return super.keys();
    }

    public /* bridge */ /* synthetic */ boolean put(Object x0, Object x1) {
        return super.put(x0, x1);
    }

    public /* bridge */ /* synthetic */ boolean putAll(Multimap x0) {
        return super.putAll(x0);
    }

    public /* bridge */ /* synthetic */ boolean putAll(Object x0, Iterable x1) {
        return super.putAll(x0, x1);
    }

    public /* bridge */ /* synthetic */ boolean remove(Object x0, Object x1) {
        return super.remove(x0, x1);
    }

    public /* bridge */ /* synthetic */ Set removeAll(Object x0) {
        return super.removeAll(x0);
    }

    public /* bridge */ /* synthetic */ int size() {
        return super.size();
    }

    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }

    public static <K, V> LinkedHashMultimap<K, V> create() {
        return new LinkedHashMultimap(16, 2);
    }

    public static <K, V> LinkedHashMultimap<K, V> create(int expectedKeys, int expectedValuesPerKey) {
        return new LinkedHashMultimap(Maps.capacity(expectedKeys), Maps.capacity(expectedValuesPerKey));
    }

    public static <K, V> LinkedHashMultimap<K, V> create(Multimap<? extends K, ? extends V> multimap) {
        LinkedHashMultimap<K, V> result = create(multimap.keySet().size(), 2);
        result.putAll(multimap);
        return result;
    }

    private static <K, V> void succeedsInValueSet(ValueSetLink<K, V> pred, ValueSetLink<K, V> succ) {
        pred.setSuccessorInValueSet(succ);
        succ.setPredecessorInValueSet(pred);
    }

    private static <K, V> void succeedsInMultimap(LinkedHashMultimap$ValueEntry<K, V> pred, LinkedHashMultimap$ValueEntry<K, V> succ) {
        pred.setSuccessorInMultimap(succ);
        succ.setPredecessorInMultimap(pred);
    }

    private static <K, V> void deleteFromValueSet(ValueSetLink<K, V> entry) {
        succeedsInValueSet(entry.getPredecessorInValueSet(), entry.getSuccessorInValueSet());
    }

    private static <K, V> void deleteFromMultimap(LinkedHashMultimap$ValueEntry<K, V> entry) {
        succeedsInMultimap(entry.getPredecessorInMultimap(), entry.getSuccessorInMultimap());
    }

    private LinkedHashMultimap(int keyCapacity, int valueSetCapacity) {
        super(new LinkedHashMap(keyCapacity));
        CollectPreconditions.checkNonnegative(valueSetCapacity, "expectedValuesPerKey");
        this.valueSetCapacity = valueSetCapacity;
        this.multimapHeaderEntry = new LinkedHashMultimap$ValueEntry(null, null, 0, null);
        succeedsInMultimap(this.multimapHeaderEntry, this.multimapHeaderEntry);
    }

    Set<V> createCollection() {
        return new LinkedHashSet(this.valueSetCapacity);
    }

    Collection<V> createCollection(K key) {
        return new ValueSet(key, this.valueSetCapacity);
    }

    public Set<V> replaceValues(@Nullable K key, Iterable<? extends V> values) {
        return super.replaceValues((Object) key, (Iterable) values);
    }

    public Set<Entry<K, V>> entries() {
        return super.entries();
    }

    public Collection<V> values() {
        return super.values();
    }

    Iterator<Entry<K, V>> entryIterator() {
        return new C05471();
    }

    Iterator<V> valueIterator() {
        return Maps.valueIterator(entryIterator());
    }

    public void clear() {
        super.clear();
        succeedsInMultimap(this.multimapHeaderEntry, this.multimapHeaderEntry);
    }

    @GwtIncompatible("java.io.ObjectOutputStream")
    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        stream.writeInt(keySet().size());
        for (K key : keySet()) {
            stream.writeObject(key);
        }
        stream.writeInt(size());
        for (Entry<K, V> entry : entries()) {
            stream.writeObject(entry.getKey());
            stream.writeObject(entry.getValue());
        }
    }

    @GwtIncompatible("java.io.ObjectInputStream")
    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        int i;
        stream.defaultReadObject();
        int i2 = 0;
        this.multimapHeaderEntry = new LinkedHashMultimap$ValueEntry(null, null, 0, null);
        succeedsInMultimap(this.multimapHeaderEntry, this.multimapHeaderEntry);
        this.valueSetCapacity = 2;
        int distinctKeys = stream.readInt();
        Map<K, Collection<V>> map = new LinkedHashMap();
        for (i = 0; i < distinctKeys; i++) {
            K key = stream.readObject();
            map.put(key, createCollection(key));
        }
        i = stream.readInt();
        while (i2 < i) {
            key = stream.readObject();
            ((Collection) map.get(key)).add(stream.readObject());
            i2++;
        }
        setMap(map);
    }
}
