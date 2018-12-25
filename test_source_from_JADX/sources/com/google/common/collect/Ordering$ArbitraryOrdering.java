package com.google.common.collect;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Function;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@VisibleForTesting
class Ordering$ArbitraryOrdering extends Ordering<Object> {
    private Map<Object, Integer> uids = Platform.tryWeakKeys(new MapMaker()).makeComputingMap(new C09591());

    /* renamed from: com.google.common.collect.Ordering$ArbitraryOrdering$1 */
    class C09591 implements Function<Object, Integer> {
        final AtomicInteger counter = new AtomicInteger(0);

        C09591() {
        }

        public Integer apply(Object from) {
            return Integer.valueOf(this.counter.getAndIncrement());
        }
    }

    Ordering$ArbitraryOrdering() {
    }

    public int compare(Object left, Object right) {
        if (left == right) {
            return 0;
        }
        int i = -1;
        if (left == null) {
            return -1;
        }
        if (right == null) {
            return 1;
        }
        int leftCode = identityHashCode(left);
        int rightCode = identityHashCode(right);
        if (leftCode != rightCode) {
            if (leftCode >= rightCode) {
                i = 1;
            }
            return i;
        }
        i = ((Integer) this.uids.get(left)).compareTo((Integer) this.uids.get(right));
        if (i != 0) {
            return i;
        }
        throw new AssertionError();
    }

    public String toString() {
        return "Ordering.arbitrary()";
    }

    int identityHashCode(Object object) {
        return System.identityHashCode(object);
    }
}
