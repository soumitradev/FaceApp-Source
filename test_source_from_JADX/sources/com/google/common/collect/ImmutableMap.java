package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;

@GwtCompatible(emulated = true, serializable = true)
public abstract class ImmutableMap<K, V> implements Map<K, V>, Serializable {
    static final Entry<?, ?>[] EMPTY_ENTRY_ARRAY = new Entry[0];
    private transient ImmutableSet<Entry<K, V>> entrySet;
    private transient ImmutableSet<K> keySet;
    private transient ImmutableSetMultimap<K, V> multimapView;
    private transient ImmutableCollection<V> values;

    abstract ImmutableSet<Entry<K, V>> createEntrySet();

    public abstract V get(@Nullable Object obj);

    abstract boolean isPartialView();

    public static <K, V> ImmutableMap<K, V> of() {
        return ImmutableBiMap.of();
    }

    public static <K, V> ImmutableMap<K, V> of(K k1, V v1) {
        return ImmutableBiMap.of(k1, v1);
    }

    public static <K, V> ImmutableMap<K, V> of(K k1, V v1, K k2, V v2) {
        return RegularImmutableMap.fromEntries(entryOf(k1, v1), entryOf(k2, v2));
    }

    public static <K, V> ImmutableMap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3) {
        return RegularImmutableMap.fromEntries(entryOf(k1, v1), entryOf(k2, v2), entryOf(k3, v3));
    }

    public static <K, V> ImmutableMap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4) {
        return RegularImmutableMap.fromEntries(entryOf(k1, v1), entryOf(k2, v2), entryOf(k3, v3), entryOf(k4, v4));
    }

    public static <K, V> ImmutableMap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
        return RegularImmutableMap.fromEntries(entryOf(k1, v1), entryOf(k2, v2), entryOf(k3, v3), entryOf(k4, v4), entryOf(k5, v5));
    }

    static <K, V> ImmutableMapEntry<K, V> entryOf(K key, V value) {
        return new ImmutableMapEntry(key, value);
    }

    public static <K, V> ImmutableMap$Builder<K, V> builder() {
        return new ImmutableMap$Builder();
    }

    static void checkNoConflict(boolean safe, String conflictDescription, Entry<?, ?> entry1, Entry<?, ?> entry2) {
        if (!safe) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Multiple entries with same ");
            stringBuilder.append(conflictDescription);
            stringBuilder.append(": ");
            stringBuilder.append(entry1);
            stringBuilder.append(" and ");
            stringBuilder.append(entry2);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
    }

    public static <K, V> ImmutableMap<K, V> copyOf(Map<? extends K, ? extends V> map) {
        if ((map instanceof ImmutableMap) && !(map instanceof ImmutableSortedMap)) {
            ImmutableMap<K, V> kvMap = (ImmutableMap) map;
            if (!kvMap.isPartialView()) {
                return kvMap;
            }
        } else if (map instanceof EnumMap) {
            return copyOfEnumMap((EnumMap) map);
        }
        return copyOf(map.entrySet());
    }

    @Beta
    public static <K, V> ImmutableMap<K, V> copyOf(Iterable<? extends Entry<? extends K, ? extends V>> entries) {
        Entry[] entryArray = (Entry[]) Iterables.toArray((Iterable) entries, EMPTY_ENTRY_ARRAY);
        switch (entryArray.length) {
            case 0:
                return of();
            case 1:
                Entry<K, V> onlyEntry = entryArray[null];
                return of(onlyEntry.getKey(), onlyEntry.getValue());
            default:
                return RegularImmutableMap.fromEntries(entryArray);
        }
    }

    private static <K extends Enum<K>, V> ImmutableMap<K, V> copyOfEnumMap(EnumMap<K, ? extends V> original) {
        EnumMap<K, V> copy = new EnumMap(original);
        for (Entry<?, ?> entry : copy.entrySet()) {
            CollectPreconditions.checkEntryNotNull(entry.getKey(), entry.getValue());
        }
        return ImmutableEnumMap.asImmutable(copy);
    }

    ImmutableMap() {
    }

    @Deprecated
    public final V put(K k, V v) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final V remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final void putAll(Map<? extends K, ? extends V> map) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final void clear() {
        throw new UnsupportedOperationException();
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean containsKey(@Nullable Object key) {
        return get(key) != null;
    }

    public boolean containsValue(@Nullable Object value) {
        return values().contains(value);
    }

    public ImmutableSet<Entry<K, V>> entrySet() {
        ImmutableSet<Entry<K, V>> result = this.entrySet;
        if (result != null) {
            return result;
        }
        ImmutableSet<Entry<K, V>> createEntrySet = createEntrySet();
        this.entrySet = createEntrySet;
        return createEntrySet;
    }

    public ImmutableSet<K> keySet() {
        ImmutableSet<K> result = this.keySet;
        if (result != null) {
            return result;
        }
        ImmutableSet<K> createKeySet = createKeySet();
        this.keySet = createKeySet;
        return createKeySet;
    }

    ImmutableSet<K> createKeySet() {
        return isEmpty() ? ImmutableSet.of() : new ImmutableMapKeySet(this);
    }

    UnmodifiableIterator<K> keyIterator() {
        return new ImmutableMap$1(this, entrySet().iterator());
    }

    public ImmutableCollection<V> values() {
        ImmutableCollection<V> result = this.values;
        if (result != null) {
            return result;
        }
        ImmutableCollection<V> immutableMapValues = new ImmutableMapValues(this);
        this.values = immutableMapValues;
        return immutableMapValues;
    }

    @Beta
    public ImmutableSetMultimap<K, V> asMultimap() {
        if (isEmpty()) {
            return ImmutableSetMultimap.of();
        }
        ImmutableSetMultimap<K, V> immutableSetMultimap;
        ImmutableSetMultimap<K, V> result = this.multimapView;
        if (result == null) {
            immutableSetMultimap = new ImmutableSetMultimap(new ImmutableMap$MapViewOfValuesAsSingletonSets(this, null), size(), null);
            this.multimapView = immutableSetMultimap;
        } else {
            immutableSetMultimap = result;
        }
        return immutableSetMultimap;
    }

    public boolean equals(@Nullable Object object) {
        return Maps.equalsImpl(this, object);
    }

    public int hashCode() {
        return Sets.hashCodeImpl(entrySet());
    }

    boolean isHashCodeFast() {
        return false;
    }

    public String toString() {
        return Maps.toStringImpl(this);
    }

    Object writeReplace() {
        return new ImmutableMap$SerializedForm(this);
    }
}
