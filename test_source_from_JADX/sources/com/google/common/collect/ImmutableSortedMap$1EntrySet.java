package com.google.common.collect;

import java.util.Map.Entry;

class ImmutableSortedMap$1EntrySet extends ImmutableMapEntrySet<K, V> {
    final /* synthetic */ ImmutableSortedMap this$0;

    /* renamed from: com.google.common.collect.ImmutableSortedMap$1EntrySet$1 */
    class C13441 extends ImmutableAsList<Entry<K, V>> {
        C13441() {
        }

        public Entry<K, V> get(int index) {
            return Maps.immutableEntry(ImmutableSortedMap.access$200(ImmutableSortedMap$1EntrySet.this.this$0).asList().get(index), ImmutableSortedMap.access$300(ImmutableSortedMap$1EntrySet.this.this$0).get(index));
        }

        ImmutableCollection<Entry<K, V>> delegateCollection() {
            return ImmutableSortedMap$1EntrySet.this;
        }
    }

    ImmutableSortedMap$1EntrySet(ImmutableSortedMap immutableSortedMap) {
        this.this$0 = immutableSortedMap;
    }

    public UnmodifiableIterator<Entry<K, V>> iterator() {
        return asList().iterator();
    }

    ImmutableList<Entry<K, V>> createAsList() {
        return new C13441();
    }

    ImmutableMap<K, V> map() {
        return this.this$0;
    }
}
