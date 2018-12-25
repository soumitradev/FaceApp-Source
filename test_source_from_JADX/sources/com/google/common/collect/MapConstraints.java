package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

@GwtCompatible
@Deprecated
@Beta
public final class MapConstraints {
    private MapConstraints() {
    }

    public static MapConstraint<Object, Object> notNull() {
        return MapConstraints$NotNullMapConstraint.INSTANCE;
    }

    public static <K, V> Map<K, V> constrainedMap(Map<K, V> map, MapConstraint<? super K, ? super V> constraint) {
        return new MapConstraints$ConstrainedMap(map, constraint);
    }

    public static <K, V> Multimap<K, V> constrainedMultimap(Multimap<K, V> multimap, MapConstraint<? super K, ? super V> constraint) {
        return new MapConstraints$ConstrainedMultimap(multimap, constraint);
    }

    public static <K, V> ListMultimap<K, V> constrainedListMultimap(ListMultimap<K, V> multimap, MapConstraint<? super K, ? super V> constraint) {
        return new MapConstraints$ConstrainedListMultimap(multimap, constraint);
    }

    public static <K, V> SetMultimap<K, V> constrainedSetMultimap(SetMultimap<K, V> multimap, MapConstraint<? super K, ? super V> constraint) {
        return new MapConstraints$ConstrainedSetMultimap(multimap, constraint);
    }

    public static <K, V> SortedSetMultimap<K, V> constrainedSortedSetMultimap(SortedSetMultimap<K, V> multimap, MapConstraint<? super K, ? super V> constraint) {
        return new MapConstraints$ConstrainedSortedSetMultimap(multimap, constraint);
    }

    private static <K, V> Entry<K, V> constrainedEntry(Entry<K, V> entry, MapConstraint<? super K, ? super V> constraint) {
        Preconditions.checkNotNull(entry);
        Preconditions.checkNotNull(constraint);
        return new MapConstraints$1(entry, constraint);
    }

    private static <K, V> Entry<K, Collection<V>> constrainedAsMapEntry(Entry<K, Collection<V>> entry, MapConstraint<? super K, ? super V> constraint) {
        Preconditions.checkNotNull(entry);
        Preconditions.checkNotNull(constraint);
        return new MapConstraints$2(entry, constraint);
    }

    private static <K, V> Set<Entry<K, Collection<V>>> constrainedAsMapEntries(Set<Entry<K, Collection<V>>> entries, MapConstraint<? super K, ? super V> constraint) {
        return new MapConstraints$ConstrainedAsMapEntries(entries, constraint);
    }

    private static <K, V> Collection<Entry<K, V>> constrainedEntries(Collection<Entry<K, V>> entries, MapConstraint<? super K, ? super V> constraint) {
        if (entries instanceof Set) {
            return constrainedEntrySet((Set) entries, constraint);
        }
        return new MapConstraints$ConstrainedEntries(entries, constraint);
    }

    private static <K, V> Set<Entry<K, V>> constrainedEntrySet(Set<Entry<K, V>> entries, MapConstraint<? super K, ? super V> constraint) {
        return new MapConstraints$ConstrainedEntrySet(entries, constraint);
    }

    public static <K, V> BiMap<K, V> constrainedBiMap(BiMap<K, V> map, MapConstraint<? super K, ? super V> constraint) {
        return new MapConstraints$ConstrainedBiMap(map, null, constraint);
    }

    private static <K, V> Collection<V> checkValues(K key, Iterable<? extends V> values, MapConstraint<? super K, ? super V> constraint) {
        Collection<V> copy = Lists.newArrayList((Iterable) values);
        for (V value : copy) {
            constraint.checkKeyValue(key, value);
        }
        return copy;
    }

    private static <K, V> Map<K, V> checkMap(Map<? extends K, ? extends V> map, MapConstraint<? super K, ? super V> constraint) {
        Map<K, V> copy = new LinkedHashMap(map);
        for (Entry<K, V> entry : copy.entrySet()) {
            constraint.checkKeyValue(entry.getKey(), entry.getValue());
        }
        return copy;
    }
}
