package com.google.common.util.concurrent;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;

final class Futures$ListFuture<V> extends CollectionFuture<V, List<V>> {

    /* renamed from: com.google.common.util.concurrent.Futures$ListFuture$ListFutureRunningState */
    private final class ListFutureRunningState extends CollectionFutureRunningState {
        ListFutureRunningState(ImmutableCollection<? extends ListenableFuture<? extends V>> futures, boolean allMustSucceed) {
            super(futures, allMustSucceed);
        }

        public List<V> combine(List<Optional<V>> values) {
            List<V> result = Lists.newArrayList();
            for (Optional<V> element : values) {
                result.add(element != null ? element.orNull() : null);
            }
            return Collections.unmodifiableList(result);
        }
    }

    Futures$ListFuture(ImmutableCollection<? extends ListenableFuture<? extends V>> futures, boolean allMustSucceed) {
        init(new ListFutureRunningState(futures, allMustSucceed));
    }
}
