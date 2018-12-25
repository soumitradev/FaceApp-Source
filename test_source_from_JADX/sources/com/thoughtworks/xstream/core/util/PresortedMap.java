package com.thoughtworks.xstream.core.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;

public class PresortedMap implements SortedMap {
    private final Comparator comparator;
    private final ArraySet set;

    private static class ArraySet extends ArrayList implements Set {
        private ArraySet() {
        }
    }

    private static class ArraySetComparator implements Comparator {
        private Entry[] array;
        private final ArrayList list;

        ArraySetComparator(ArrayList list) {
            this.list = list;
        }

        public int compare(Object object1, Object object2) {
            int i;
            int i2 = 0;
            if (this.array == null || this.list.size() != this.array.length) {
                Entry[] a = new Entry[this.list.size()];
                if (this.array != null) {
                    System.arraycopy(this.array, 0, a, 0, this.array.length);
                }
                i = this.array == null ? 0 : this.array.length;
                while (i < this.list.size()) {
                    a[i] = (Entry) this.list.get(i);
                    i++;
                }
                this.array = a;
            }
            int idx1 = Integer.MAX_VALUE;
            i = Integer.MAX_VALUE;
            while (i2 < this.array.length && (idx1 >= Integer.MAX_VALUE || i >= Integer.MAX_VALUE)) {
                if (idx1 == Integer.MAX_VALUE && object1 == this.array[i2].getKey()) {
                    idx1 = i2;
                }
                if (i == Integer.MAX_VALUE && object2 == this.array[i2].getKey()) {
                    i = i2;
                }
                i2++;
            }
            return idx1 - i;
        }
    }

    public PresortedMap() {
        this(null, new ArraySet());
    }

    public PresortedMap(Comparator comparator) {
        this(comparator, new ArraySet());
    }

    private PresortedMap(Comparator comparator, ArraySet set) {
        this.comparator = comparator != null ? comparator : new ArraySetComparator(set);
        this.set = set;
    }

    public Comparator comparator() {
        return this.comparator;
    }

    public Set entrySet() {
        return this.set;
    }

    public Object firstKey() {
        throw new UnsupportedOperationException();
    }

    public SortedMap headMap(Object toKey) {
        throw new UnsupportedOperationException();
    }

    public Set keySet() {
        Set keySet = new ArraySet();
        Iterator iterator = this.set.iterator();
        while (iterator.hasNext()) {
            keySet.add(((Entry) iterator.next()).getKey());
        }
        return keySet;
    }

    public Object lastKey() {
        throw new UnsupportedOperationException();
    }

    public SortedMap subMap(Object fromKey, Object toKey) {
        throw new UnsupportedOperationException();
    }

    public SortedMap tailMap(Object fromKey) {
        throw new UnsupportedOperationException();
    }

    public Collection values() {
        Set values = new ArraySet();
        Iterator iterator = this.set.iterator();
        while (iterator.hasNext()) {
            values.add(((Entry) iterator.next()).getValue());
        }
        return values;
    }

    public void clear() {
        throw new UnsupportedOperationException();
    }

    public boolean containsKey(Object key) {
        return false;
    }

    public boolean containsValue(Object value) {
        throw new UnsupportedOperationException();
    }

    public Object get(Object key) {
        throw new UnsupportedOperationException();
    }

    public boolean isEmpty() {
        return this.set.isEmpty();
    }

    public Object put(final Object key, final Object value) {
        this.set.add(new Entry() {
            public Object getKey() {
                return key;
            }

            public Object getValue() {
                return value;
            }

            public Object setValue(Object value) {
                throw new UnsupportedOperationException();
            }
        });
        return null;
    }

    public void putAll(Map m) {
        for (Object add : m.entrySet()) {
            this.set.add(add);
        }
    }

    public Object remove(Object key) {
        throw new UnsupportedOperationException();
    }

    public int size() {
        return this.set.size();
    }
}
