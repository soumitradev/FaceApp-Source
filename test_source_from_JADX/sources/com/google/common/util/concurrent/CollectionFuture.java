package com.google.common.util.concurrent;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;

@GwtCompatible
abstract class CollectionFuture<V, C> extends AggregateFuture<V, C> {

    abstract class CollectionFutureRunningState extends RunningState {
        private List<Optional<V>> values;

        abstract C combine(List<Optional<V>> list);

        CollectionFutureRunningState(ImmutableCollection<? extends ListenableFuture<? extends V>> futures, boolean allMustSucceed) {
            super(futures, allMustSucceed, true);
            this.values = (List) (futures.isEmpty() != null ? ImmutableList.of() : Lists.newArrayListWithCapacity(futures.size()));
            for (int i = 0; i < futures.size(); i++) {
                this.values.add(null);
            }
        }

        final void collectOneValue(boolean allMustSucceed, int index, @Nullable V returnValue) {
            List<Optional<V>> localValues = this.values;
            if (localValues != null) {
                localValues.set(index, Optional.fromNullable(returnValue));
                return;
            }
            boolean z;
            if (!allMustSucceed) {
                if (!CollectionFuture.this.isCancelled()) {
                    z = false;
                    Preconditions.checkState(z, "Future was done before all dependencies completed");
                }
            }
            z = true;
            Preconditions.checkState(z, "Future was done before all dependencies completed");
        }

        final void handleAllCompleted() {
            List<Optional<V>> localValues = this.values;
            if (localValues != null) {
                CollectionFuture.this.set(combine(localValues));
            } else {
                Preconditions.checkState(CollectionFuture.this.isDone());
            }
        }

        void releaseResourcesAfterFailure() {
            super.releaseResourcesAfterFailure();
            this.values = null;
        }
    }

    CollectionFuture() {
    }
}
