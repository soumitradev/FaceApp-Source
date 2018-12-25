package com.google.common.collect;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.HashBiMap.BiEntry;
import java.io.Serializable;
import java.util.AbstractMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.annotation.Nullable;

final class HashBiMap$Inverse extends AbstractMap<V, K> implements BiMap<V, K>, Serializable {
    final /* synthetic */ HashBiMap this$0;

    /* renamed from: com.google.common.collect.HashBiMap$Inverse$1 */
    class C12691 extends Maps$EntrySet<V, K> {

        /* renamed from: com.google.common.collect.HashBiMap$Inverse$1$1 */
        class C09211 extends HashBiMap$Itr<Entry<V, K>> {

            /* renamed from: com.google.common.collect.HashBiMap$Inverse$1$1$InverseEntry */
            class InverseEntry extends AbstractMapEntry<V, K> {
                BiEntry<K, V> delegate;

                InverseEntry(BiEntry<K, V> entry) {
                    this.delegate = entry;
                }

                public V getKey() {
                    return this.delegate.value;
                }

                public K getValue() {
                    return this.delegate.key;
                }

                public K setValue(K key) {
                    K oldKey = this.delegate.key;
                    int keyHash = Hashing.smearedHash(key);
                    if (keyHash == this.delegate.keyHash && Objects.equal(key, oldKey)) {
                        return key;
                    }
                    Preconditions.checkArgument(HashBiMap.access$300(HashBiMap$Inverse.this.this$0, key, keyHash) == null, "value already present: %s", key);
                    HashBiMap.access$200(HashBiMap$Inverse.this.this$0, this.delegate);
                    BiEntry<K, V> newEntry = new BiEntry(key, keyHash, this.delegate.value, this.delegate.valueHash);
                    this.delegate = newEntry;
                    HashBiMap.access$500(HashBiMap$Inverse.this.this$0, newEntry, null);
                    C09211.this.expectedModCount = HashBiMap.access$100(HashBiMap$Inverse.this.this$0);
                    return oldKey;
                }
            }

            C09211() {
                super(HashBiMap$Inverse.this.this$0);
            }

            Entry<V, K> output(BiEntry<K, V> entry) {
                return new InverseEntry(entry);
            }
        }

        C12691() {
        }

        Map<V, K> map() {
            return HashBiMap$Inverse.this;
        }

        public Iterator<Entry<V, K>> iterator() {
            return new C09211();
        }
    }

    /* renamed from: com.google.common.collect.HashBiMap$Inverse$InverseKeySet */
    private final class InverseKeySet extends Maps$KeySet<V, K> {

        /* renamed from: com.google.common.collect.HashBiMap$Inverse$InverseKeySet$1 */
        class C09221 extends HashBiMap$Itr<V> {
            C09221() {
                super(HashBiMap$Inverse.this.this$0);
            }

            V output(BiEntry<K, V> entry) {
                return entry.value;
            }
        }

        InverseKeySet() {
            super(HashBiMap$Inverse.this);
        }

        public boolean remove(@Nullable Object o) {
            BiEntry<K, V> entry = HashBiMap.access$400(HashBiMap$Inverse.this.this$0, o, Hashing.smearedHash(o));
            if (entry == null) {
                return false;
            }
            HashBiMap.access$200(HashBiMap$Inverse.this.this$0, entry);
            return true;
        }

        public Iterator<V> iterator() {
            return new C09221();
        }
    }

    private HashBiMap$Inverse(HashBiMap hashBiMap) {
        this.this$0 = hashBiMap;
    }

    BiMap<K, V> forward() {
        return this.this$0;
    }

    public int size() {
        return HashBiMap.access$700(this.this$0);
    }

    public void clear() {
        forward().clear();
    }

    public boolean containsKey(@Nullable Object value) {
        return forward().containsValue(value);
    }

    public K get(@Nullable Object value) {
        return Maps.keyOrNull(HashBiMap.access$400(this.this$0, value, Hashing.smearedHash(value)));
    }

    public K put(@Nullable V value, @Nullable K key) {
        return HashBiMap.access$800(this.this$0, value, key, false);
    }

    public K forcePut(@Nullable V value, @Nullable K key) {
        return HashBiMap.access$800(this.this$0, value, key, true);
    }

    public K remove(@Nullable Object value) {
        BiEntry<K, V> entry = HashBiMap.access$400(this.this$0, value, Hashing.smearedHash(value));
        if (entry == null) {
            return null;
        }
        HashBiMap.access$200(this.this$0, entry);
        entry.prevInKeyInsertionOrder = null;
        entry.nextInKeyInsertionOrder = null;
        return entry.key;
    }

    public BiMap<K, V> inverse() {
        return forward();
    }

    public Set<V> keySet() {
        return new InverseKeySet();
    }

    public Set<K> values() {
        return forward().keySet();
    }

    public Set<Entry<V, K>> entrySet() {
        return new C12691();
    }

    Object writeReplace() {
        return new HashBiMap$InverseSerializedForm(this.this$0);
    }
}
