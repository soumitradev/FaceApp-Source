package com.google.common.collect;

import java.util.Collection;
import java.util.Map.Entry;

class MapConstraints$2 extends ForwardingMapEntry<K, Collection<V>> {
    final /* synthetic */ MapConstraint val$constraint;
    final /* synthetic */ Entry val$entry;

    /* renamed from: com.google.common.collect.MapConstraints$2$1 */
    class C09281 implements Constraint<V> {
        C09281() {
        }

        public V checkElement(V value) {
            MapConstraints$2.this.val$constraint.checkKeyValue(MapConstraints$2.this.getKey(), value);
            return value;
        }
    }

    MapConstraints$2(Entry entry, MapConstraint mapConstraint) {
        this.val$entry = entry;
        this.val$constraint = mapConstraint;
    }

    protected Entry<K, Collection<V>> delegate() {
        return this.val$entry;
    }

    public Collection<V> getValue() {
        return Constraints.constrainedTypePreservingCollection((Collection) this.val$entry.getValue(), new C09281());
    }
}
