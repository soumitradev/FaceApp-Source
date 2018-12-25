package com.google.common.collect;

import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

class MapConstraints$ConstrainedMultimap<K, V> extends ForwardingMultimap<K, V> implements Serializable {
    transient Map<K, Collection<V>> asMap;
    final MapConstraint<? super K, ? super V> constraint;
    final Multimap<K, V> delegate;
    transient Collection<Entry<K, V>> entries;

    public MapConstraints$ConstrainedMultimap(Multimap<K, V> delegate, MapConstraint<? super K, ? super V> constraint) {
        this.delegate = (Multimap) Preconditions.checkNotNull(delegate);
        this.constraint = (MapConstraint) Preconditions.checkNotNull(constraint);
    }

    protected Multimap<K, V> delegate() {
        return this.delegate;
    }

    public Map<K, Collection<V>> asMap() {
        Map<K, Collection<V>> result = this.asMap;
        if (result != null) {
            return result;
        }
        final Map<K, Collection<V>> asMapDelegate = this.delegate.asMap();
        Map<K, Collection<V>> anonymousClass1AsMap = new ForwardingMap<K, Collection<V>>() {
            Set<Entry<K, Collection<V>>> entrySet;
            Collection<Collection<V>> values;

            protected Map<K, Collection<V>> delegate() {
                return asMapDelegate;
            }

            public Set<Entry<K, Collection<V>>> entrySet() {
                Set<Entry<K, Collection<V>>> result = this.entrySet;
                if (result != null) {
                    return result;
                }
                Set<Entry<K, Collection<V>>> access$200 = MapConstraints.access$200(asMapDelegate.entrySet(), MapConstraints$ConstrainedMultimap.this.constraint);
                result = access$200;
                this.entrySet = access$200;
                return result;
            }

            public Collection<V> get(Object key) {
                Collection<V> collection = null;
                try {
                    Collection<V> collection2 = MapConstraints$ConstrainedMultimap.this.get(key);
                    if (!collection2.isEmpty()) {
                        collection = collection2;
                    }
                    return collection;
                } catch (ClassCastException e) {
                    return null;
                }
            }

            public Collection<Collection<V>> values() {
                Collection<Collection<V>> result = this.values;
                if (result != null) {
                    return result;
                }
                Collection<Collection<V>> mapConstraints$ConstrainedAsMapValues = new MapConstraints$ConstrainedAsMapValues(delegate().values(), entrySet());
                result = mapConstraints$ConstrainedAsMapValues;
                this.values = mapConstraints$ConstrainedAsMapValues;
                return result;
            }

            public boolean containsValue(Object o) {
                return values().contains(o);
            }
        };
        result = anonymousClass1AsMap;
        this.asMap = anonymousClass1AsMap;
        return result;
    }

    public Collection<Entry<K, V>> entries() {
        Collection<Entry<K, V>> result = this.entries;
        if (result != null) {
            return result;
        }
        Collection<Entry<K, V>> access$300 = MapConstraints.access$300(this.delegate.entries(), this.constraint);
        result = access$300;
        this.entries = access$300;
        return result;
    }

    public Collection<V> get(final K key) {
        return Constraints.constrainedTypePreservingCollection(this.delegate.get(key), new Constraint<V>() {
            public V checkElement(V value) {
                MapConstraints$ConstrainedMultimap.this.constraint.checkKeyValue(key, value);
                return value;
            }
        });
    }

    public boolean put(K key, V value) {
        this.constraint.checkKeyValue(key, value);
        return this.delegate.put(key, value);
    }

    public boolean putAll(K key, Iterable<? extends V> values) {
        return this.delegate.putAll(key, MapConstraints.access$400(key, values, this.constraint));
    }

    public boolean putAll(Multimap<? extends K, ? extends V> multimap) {
        boolean changed = false;
        for (Entry<? extends K, ? extends V> entry : multimap.entries()) {
            changed |= put(entry.getKey(), entry.getValue());
        }
        return changed;
    }

    public Collection<V> replaceValues(K key, Iterable<? extends V> values) {
        return this.delegate.replaceValues(key, MapConstraints.access$400(key, values, this.constraint));
    }
}
