package com.google.common.collect;

import java.util.Iterator;
import java.util.Map.Entry;
import javax.annotation.Nullable;

final class ImmutableMap$MapViewOfValuesAsSingletonSets extends ImmutableMap$IteratorBasedImmutableMap<K, ImmutableSet<V>> {
    final /* synthetic */ ImmutableMap this$0;

    private ImmutableMap$MapViewOfValuesAsSingletonSets(ImmutableMap immutableMap) {
        this.this$0 = immutableMap;
    }

    public int size() {
        return this.this$0.size();
    }

    public ImmutableSet<K> keySet() {
        return this.this$0.keySet();
    }

    public boolean containsKey(@Nullable Object key) {
        return this.this$0.containsKey(key);
    }

    public ImmutableSet<V> get(@Nullable Object key) {
        V outerValue = this.this$0.get(key);
        return outerValue == null ? null : ImmutableSet.of(outerValue);
    }

    boolean isPartialView() {
        return this.this$0.isPartialView();
    }

    public int hashCode() {
        return this.this$0.hashCode();
    }

    boolean isHashCodeFast() {
        return this.this$0.isHashCodeFast();
    }

    UnmodifiableIterator<Entry<K, ImmutableSet<V>>> entryIterator() {
        final Iterator<Entry<K, V>> backingIterator = this.this$0.entrySet().iterator();
        return new UnmodifiableIterator<Entry<K, ImmutableSet<V>>>() {
            public boolean hasNext() {
                return backingIterator.hasNext();
            }

            public Entry<K, ImmutableSet<V>> next() {
                final Entry<K, V> backingEntry = (Entry) backingIterator.next();
                return new AbstractMapEntry<K, ImmutableSet<V>>() {
                    public K getKey() {
                        return backingEntry.getKey();
                    }

                    public ImmutableSet<V> getValue() {
                        return ImmutableSet.of(backingEntry.getValue());
                    }
                };
            }
        };
    }
}
