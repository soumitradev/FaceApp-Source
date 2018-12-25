package com.google.common.collect;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.HashBiMap.BiEntry;
import java.util.Map.Entry;

class HashBiMap$1 extends HashBiMap$Itr<Entry<K, V>> {
    final /* synthetic */ HashBiMap this$0;

    /* renamed from: com.google.common.collect.HashBiMap$1$MapEntry */
    class MapEntry extends AbstractMapEntry<K, V> {
        BiEntry<K, V> delegate;

        MapEntry(BiEntry<K, V> entry) {
            this.delegate = entry;
        }

        public K getKey() {
            return this.delegate.key;
        }

        public V getValue() {
            return this.delegate.value;
        }

        public V setValue(V value) {
            V oldValue = this.delegate.value;
            int valueHash = Hashing.smearedHash(value);
            if (valueHash == this.delegate.valueHash && Objects.equal(value, oldValue)) {
                return value;
            }
            Preconditions.checkArgument(HashBiMap.access$400(HashBiMap$1.this.this$0, value, valueHash) == null, "value already present: %s", value);
            HashBiMap.access$200(HashBiMap$1.this.this$0, this.delegate);
            BiEntry<K, V> newEntry = new BiEntry(this.delegate.key, this.delegate.keyHash, value, valueHash);
            HashBiMap.access$500(HashBiMap$1.this.this$0, newEntry, this.delegate);
            this.delegate.prevInKeyInsertionOrder = null;
            this.delegate.nextInKeyInsertionOrder = null;
            HashBiMap$1.this.expectedModCount = HashBiMap.access$100(HashBiMap$1.this.this$0);
            if (HashBiMap$1.this.toRemove == this.delegate) {
                HashBiMap$1.this.toRemove = newEntry;
            }
            this.delegate = newEntry;
            return oldValue;
        }
    }

    HashBiMap$1(HashBiMap hashBiMap) {
        this.this$0 = hashBiMap;
        super(hashBiMap);
    }

    Entry<K, V> output(BiEntry<K, V> entry) {
        return new MapEntry(entry);
    }
}
