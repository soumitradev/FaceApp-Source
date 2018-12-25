package com.google.common.collect;

import com.google.common.collect.HashBiMap.BiEntry;
import java.util.Iterator;
import javax.annotation.Nullable;

final class HashBiMap$KeySet extends Maps$KeySet<K, V> {
    final /* synthetic */ HashBiMap this$0;

    /* renamed from: com.google.common.collect.HashBiMap$KeySet$1 */
    class C09231 extends HashBiMap$Itr<K> {
        C09231() {
            super(HashBiMap$KeySet.this.this$0);
        }

        K output(BiEntry<K, V> entry) {
            return entry.key;
        }
    }

    HashBiMap$KeySet(HashBiMap hashBiMap) {
        this.this$0 = hashBiMap;
        super(hashBiMap);
    }

    public Iterator<K> iterator() {
        return new C09231();
    }

    public boolean remove(@Nullable Object o) {
        BiEntry<K, V> entry = HashBiMap.access$300(this.this$0, o, Hashing.smearedHash(o));
        if (entry == null) {
            return false;
        }
        HashBiMap.access$200(this.this$0, entry);
        entry.prevInKeyInsertionOrder = null;
        entry.nextInKeyInsertionOrder = null;
        return true;
    }
}
