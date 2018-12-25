package com.google.common.collect;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

class StandardTable$ColumnMap extends Maps$ViewCachingAbstractMap<C, Map<R, V>> {
    final /* synthetic */ StandardTable this$0;

    /* renamed from: com.google.common.collect.StandardTable$ColumnMap$ColumnMapValues */
    private class ColumnMapValues extends Maps$Values<C, Map<R, V>> {
        ColumnMapValues() {
            super(StandardTable$ColumnMap.this);
        }

        public boolean remove(Object obj) {
            for (Entry<C, Map<R, V>> entry : StandardTable$ColumnMap.this.entrySet()) {
                if (((Map) entry.getValue()).equals(obj)) {
                    StandardTable.access$900(StandardTable$ColumnMap.this.this$0, entry.getKey());
                    return true;
                }
            }
            return false;
        }

        public boolean removeAll(Collection<?> c) {
            Preconditions.checkNotNull(c);
            boolean changed = false;
            Iterator i$ = Lists.newArrayList(StandardTable$ColumnMap.this.this$0.columnKeySet().iterator()).iterator();
            while (i$.hasNext()) {
                C columnKey = i$.next();
                if (c.contains(StandardTable$ColumnMap.this.this$0.column(columnKey))) {
                    StandardTable.access$900(StandardTable$ColumnMap.this.this$0, columnKey);
                    changed = true;
                }
            }
            return changed;
        }

        public boolean retainAll(Collection<?> c) {
            Preconditions.checkNotNull(c);
            boolean changed = false;
            Iterator i$ = Lists.newArrayList(StandardTable$ColumnMap.this.this$0.columnKeySet().iterator()).iterator();
            while (i$.hasNext()) {
                C columnKey = i$.next();
                if (!c.contains(StandardTable$ColumnMap.this.this$0.column(columnKey))) {
                    StandardTable.access$900(StandardTable$ColumnMap.this.this$0, columnKey);
                    changed = true;
                }
            }
            return changed;
        }
    }

    /* renamed from: com.google.common.collect.StandardTable$ColumnMap$ColumnMapEntrySet */
    class ColumnMapEntrySet extends StandardTable$TableSet<Entry<C, Map<R, V>>> {

        /* renamed from: com.google.common.collect.StandardTable$ColumnMap$ColumnMapEntrySet$1 */
        class C09691 implements Function<C, Map<R, V>> {
            C09691() {
            }

            public Map<R, V> apply(C columnKey) {
                return StandardTable$ColumnMap.this.this$0.column(columnKey);
            }
        }

        ColumnMapEntrySet() {
            super(StandardTable$ColumnMap.this.this$0);
        }

        public Iterator<Entry<C, Map<R, V>>> iterator() {
            return Maps.asMapEntryIterator(StandardTable$ColumnMap.this.this$0.columnKeySet(), new C09691());
        }

        public int size() {
            return StandardTable$ColumnMap.this.this$0.columnKeySet().size();
        }

        public boolean contains(Object obj) {
            if (obj instanceof Entry) {
                Entry<?, ?> entry = (Entry) obj;
                if (StandardTable$ColumnMap.this.this$0.containsColumn(entry.getKey())) {
                    return StandardTable$ColumnMap.this.get(entry.getKey()).equals(entry.getValue());
                }
            }
            return false;
        }

        public boolean remove(Object obj) {
            if (!contains(obj)) {
                return false;
            }
            StandardTable.access$900(StandardTable$ColumnMap.this.this$0, ((Entry) obj).getKey());
            return true;
        }

        public boolean removeAll(Collection<?> c) {
            Preconditions.checkNotNull(c);
            return Sets.removeAllImpl(this, c.iterator());
        }

        public boolean retainAll(Collection<?> c) {
            Preconditions.checkNotNull(c);
            boolean changed = false;
            Iterator i$ = Lists.newArrayList(StandardTable$ColumnMap.this.this$0.columnKeySet().iterator()).iterator();
            while (i$.hasNext()) {
                C columnKey = i$.next();
                if (!c.contains(Maps.immutableEntry(columnKey, StandardTable$ColumnMap.this.this$0.column(columnKey)))) {
                    StandardTable.access$900(StandardTable$ColumnMap.this.this$0, columnKey);
                    changed = true;
                }
            }
            return changed;
        }
    }

    private StandardTable$ColumnMap(StandardTable standardTable) {
        this.this$0 = standardTable;
    }

    public Map<R, V> get(Object key) {
        return this.this$0.containsColumn(key) ? this.this$0.column(key) : null;
    }

    public boolean containsKey(Object key) {
        return this.this$0.containsColumn(key);
    }

    public Map<R, V> remove(Object key) {
        return this.this$0.containsColumn(key) ? StandardTable.access$900(this.this$0, key) : null;
    }

    public Set<Entry<C, Map<R, V>>> createEntrySet() {
        return new ColumnMapEntrySet();
    }

    public Set<C> keySet() {
        return this.this$0.columnKeySet();
    }

    Collection<Map<R, V>> createValues() {
        return new ColumnMapValues();
    }
}
