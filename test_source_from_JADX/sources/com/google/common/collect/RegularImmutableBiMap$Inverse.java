package com.google.common.collect;

import java.util.Map.Entry;
import javax.annotation.Nullable;

final class RegularImmutableBiMap$Inverse extends ImmutableBiMap<V, K> {
    final /* synthetic */ RegularImmutableBiMap this$0;

    /* renamed from: com.google.common.collect.RegularImmutableBiMap$Inverse$InverseEntrySet */
    final class InverseEntrySet extends ImmutableMapEntrySet<V, K> {

        /* renamed from: com.google.common.collect.RegularImmutableBiMap$Inverse$InverseEntrySet$1 */
        class C13461 extends ImmutableAsList<Entry<V, K>> {
            C13461() {
            }

            public Entry<V, K> get(int index) {
                Entry<K, V> entry = RegularImmutableBiMap.access$400(RegularImmutableBiMap$Inverse.this.this$0)[index];
                return Maps.immutableEntry(entry.getValue(), entry.getKey());
            }

            ImmutableCollection<Entry<V, K>> delegateCollection() {
                return InverseEntrySet.this;
            }
        }

        InverseEntrySet() {
        }

        ImmutableMap<V, K> map() {
            return RegularImmutableBiMap$Inverse.this;
        }

        boolean isHashCodeFast() {
            return true;
        }

        public int hashCode() {
            return RegularImmutableBiMap.access$300(RegularImmutableBiMap$Inverse.this.this$0);
        }

        public UnmodifiableIterator<Entry<V, K>> iterator() {
            return asList().iterator();
        }

        ImmutableList<Entry<V, K>> createAsList() {
            return new C13461();
        }
    }

    private RegularImmutableBiMap$Inverse(RegularImmutableBiMap regularImmutableBiMap) {
        this.this$0 = regularImmutableBiMap;
    }

    public int size() {
        return inverse().size();
    }

    public ImmutableBiMap<K, V> inverse() {
        return this.this$0;
    }

    public K get(@Nullable Object value) {
        if (value != null) {
            if (RegularImmutableBiMap.access$100(this.this$0) != null) {
                for (ImmutableMapEntry<K, V> entry = RegularImmutableBiMap.access$100(this.this$0)[Hashing.smear(value.hashCode()) & RegularImmutableBiMap.access$200(this.this$0)]; entry != null; entry = entry.getNextInValueBucket()) {
                    if (value.equals(entry.getValue())) {
                        return entry.getKey();
                    }
                }
                return null;
            }
        }
        return null;
    }

    ImmutableSet<Entry<V, K>> createEntrySet() {
        return new InverseEntrySet();
    }

    boolean isPartialView() {
        return false;
    }

    Object writeReplace() {
        return new RegularImmutableBiMap$InverseSerializedForm(this.this$0);
    }
}
