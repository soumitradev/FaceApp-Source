package com.google.common.collect;

import com.google.common.base.Function;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

class StandardTable$RowMap extends Maps$ViewCachingAbstractMap<R, Map<C, V>> {
    final /* synthetic */ StandardTable this$0;

    /* renamed from: com.google.common.collect.StandardTable$RowMap$EntrySet */
    class EntrySet extends StandardTable$TableSet<Entry<R, Map<C, V>>> {

        /* renamed from: com.google.common.collect.StandardTable$RowMap$EntrySet$1 */
        class C09701 implements Function<R, Map<C, V>> {
            C09701() {
            }

            public Map<C, V> apply(R rowKey) {
                return StandardTable$RowMap.this.this$0.row(rowKey);
            }
        }

        EntrySet() {
            super(StandardTable$RowMap.this.this$0);
        }

        public Iterator<Entry<R, Map<C, V>>> iterator() {
            return Maps.asMapEntryIterator(StandardTable$RowMap.this.this$0.backingMap.keySet(), new C09701());
        }

        public int size() {
            return StandardTable$RowMap.this.this$0.backingMap.size();
        }

        public boolean contains(Object obj) {
            boolean z = false;
            if (!(obj instanceof Entry)) {
                return false;
            }
            Entry<?, ?> entry = (Entry) obj;
            if (entry.getKey() != null && (entry.getValue() instanceof Map) && Collections2.safeContains(StandardTable$RowMap.this.this$0.backingMap.entrySet(), entry)) {
                z = true;
            }
            return z;
        }

        public boolean remove(Object obj) {
            boolean z = false;
            if (!(obj instanceof Entry)) {
                return false;
            }
            Entry<?, ?> entry = (Entry) obj;
            if (entry.getKey() != null && (entry.getValue() instanceof Map) && StandardTable$RowMap.this.this$0.backingMap.entrySet().remove(entry)) {
                z = true;
            }
            return z;
        }
    }

    StandardTable$RowMap(StandardTable standardTable) {
        this.this$0 = standardTable;
    }

    public boolean containsKey(Object key) {
        return this.this$0.containsRow(key);
    }

    public Map<C, V> get(Object key) {
        return this.this$0.containsRow(key) ? this.this$0.row(key) : null;
    }

    public Map<C, V> remove(Object key) {
        return key == null ? null : (Map) this.this$0.backingMap.remove(key);
    }

    protected Set<Entry<R, Map<C, V>>> createEntrySet() {
        return new EntrySet();
    }
}
