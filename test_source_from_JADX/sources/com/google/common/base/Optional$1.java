package com.google.common.base;

import java.util.Iterator;

class Optional$1 implements Iterable<T> {
    final /* synthetic */ Iterable val$optionals;

    /* renamed from: com.google.common.base.Optional$1$1 */
    class C08901 extends AbstractIterator<T> {
        private final Iterator<? extends Optional<? extends T>> iterator = ((Iterator) Preconditions.checkNotNull(Optional$1.this.val$optionals.iterator()));

        C08901() {
        }

        protected T computeNext() {
            while (this.iterator.hasNext()) {
                Optional<? extends T> optional = (Optional) this.iterator.next();
                if (optional.isPresent()) {
                    return optional.get();
                }
            }
            return endOfData();
        }
    }

    Optional$1(Iterable iterable) {
        this.val$optionals = iterable;
    }

    public Iterator<T> iterator() {
        return new C08901();
    }
}
