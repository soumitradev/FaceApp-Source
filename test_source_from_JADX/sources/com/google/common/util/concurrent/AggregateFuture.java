package com.google.common.util.concurrent;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableCollection;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;

@GwtCompatible
abstract class AggregateFuture<InputT, OutputT> extends TrustedFuture<OutputT> {
    private static final Logger logger = Logger.getLogger(AggregateFuture.class.getName());
    private RunningState runningState;

    abstract class RunningState extends AggregateFutureState implements Runnable {
        private final boolean allMustSucceed;
        private final boolean collectsValues;
        private ImmutableCollection<? extends ListenableFuture<? extends InputT>> futures;

        abstract void collectOneValue(boolean z, int i, @Nullable InputT inputT);

        abstract void handleAllCompleted();

        RunningState(ImmutableCollection<? extends ListenableFuture<? extends InputT>> futures, boolean allMustSucceed, boolean collectsValues) {
            super(futures.size());
            this.futures = (ImmutableCollection) Preconditions.checkNotNull(futures);
            this.allMustSucceed = allMustSucceed;
            this.collectsValues = collectsValues;
        }

        public final void run() {
            decrementCountAndMaybeComplete();
        }

        private void init() {
            if (this.futures.isEmpty()) {
                handleAllCompleted();
                return;
            }
            if (this.allMustSucceed) {
                int index = 0;
                Iterator i$ = this.futures.iterator();
                while (i$.hasNext()) {
                    final ListenableFuture<? extends InputT> listenable = (ListenableFuture) i$.next();
                    int i = index + 1;
                    listenable.addListener(new Runnable() {
                        public void run() {
                            try {
                                RunningState.this.handleOneInputDone(index, listenable);
                            } finally {
                                RunningState.this.decrementCountAndMaybeComplete();
                            }
                        }
                    }, MoreExecutors.directExecutor());
                    index = i;
                }
            } else {
                Iterator i$2 = this.futures.iterator();
                while (i$2.hasNext()) {
                    ((ListenableFuture) i$2.next()).addListener(this, MoreExecutors.directExecutor());
                }
            }
        }

        private void handleException(Throwable throwable) {
            Preconditions.checkNotNull(throwable);
            boolean completedWithFailure = false;
            boolean firstTimeSeeingThisException = true;
            if (this.allMustSucceed) {
                completedWithFailure = AggregateFuture.this.setException(throwable);
                if (completedWithFailure) {
                    releaseResourcesAfterFailure();
                } else {
                    firstTimeSeeingThisException = AggregateFuture.addCausalChain(getOrInitSeenExceptions(), throwable);
                }
            }
            if (((throwable instanceof Error) | ((this.allMustSucceed & (!completedWithFailure ? 1 : 0)) & firstTimeSeeingThisException)) != 0) {
                AggregateFuture.logger.log(Level.SEVERE, throwable instanceof Error ? "Input Future failed with Error" : "Got more than one input Future failure. Logging failures after the first", throwable);
            }
        }

        final void addInitialException(Set<Throwable> seen) {
            if (!AggregateFuture.this.isCancelled()) {
                AggregateFuture.addCausalChain(seen, AggregateFuture.this.trustedGetException());
            }
        }

        private void handleOneInputDone(int index, Future<? extends InputT> future) {
            boolean z;
            InputT result;
            if (!this.allMustSucceed && AggregateFuture.this.isDone()) {
                if (!AggregateFuture.this.isCancelled()) {
                    z = false;
                    Preconditions.checkState(z, "Future was done before all dependencies completed");
                    Preconditions.checkState(future.isDone(), "Tried to set value from future which is not done");
                    if (this.allMustSucceed) {
                        if (this.collectsValues && !future.isCancelled()) {
                            collectOneValue(this.allMustSucceed, index, Uninterruptibles.getUninterruptibly(future));
                        }
                    } else if (future.isCancelled()) {
                        result = Uninterruptibles.getUninterruptibly(future);
                        if (this.collectsValues) {
                            collectOneValue(this.allMustSucceed, index, result);
                        }
                    } else {
                        super.cancel(false);
                    }
                }
            }
            z = true;
            Preconditions.checkState(z, "Future was done before all dependencies completed");
            try {
                Preconditions.checkState(future.isDone(), "Tried to set value from future which is not done");
                if (this.allMustSucceed) {
                    collectOneValue(this.allMustSucceed, index, Uninterruptibles.getUninterruptibly(future));
                } else if (future.isCancelled()) {
                    result = Uninterruptibles.getUninterruptibly(future);
                    if (this.collectsValues) {
                        collectOneValue(this.allMustSucceed, index, result);
                    }
                } else {
                    super.cancel(false);
                }
            } catch (ExecutionException e) {
                handleException(e.getCause());
            } catch (Throwable t) {
                handleException(t);
            }
        }

        private void decrementCountAndMaybeComplete() {
            int newRemaining = decrementRemainingAndGet();
            Preconditions.checkState(newRemaining >= 0, "Less than 0 remaining futures");
            if (newRemaining == 0) {
                processCompleted();
            }
        }

        private void processCompleted() {
            if ((this.collectsValues & (this.allMustSucceed ^ 1)) != 0) {
                int i = 0;
                Iterator i$ = this.futures.iterator();
                while (i$.hasNext()) {
                    int i2 = i + 1;
                    handleOneInputDone(i, (ListenableFuture) i$.next());
                    i = i2;
                }
            }
            handleAllCompleted();
        }

        void releaseResourcesAfterFailure() {
            this.futures = null;
        }

        void interruptTask() {
        }
    }

    AggregateFuture() {
    }

    final void done() {
        super.done();
        this.runningState = null;
    }

    public final boolean cancel(boolean mayInterruptIfRunning) {
        RunningState localRunningState = this.runningState;
        ImmutableCollection<? extends ListenableFuture<? extends InputT>> futures = localRunningState != null ? localRunningState.futures : null;
        boolean cancelled = super.cancel(mayInterruptIfRunning);
        if (((futures != null ? 1 : 0) & cancelled) != 0) {
            Iterator i$ = futures.iterator();
            while (i$.hasNext()) {
                ((ListenableFuture) i$.next()).cancel(mayInterruptIfRunning);
            }
        }
        return cancelled;
    }

    @GwtIncompatible("Interruption not supported")
    protected final void interruptTask() {
        RunningState localRunningState = this.runningState;
        if (localRunningState != null) {
            localRunningState.interruptTask();
        }
    }

    final void init(RunningState runningState) {
        this.runningState = runningState;
        runningState.init();
    }

    private static boolean addCausalChain(Set<Throwable> seen, Throwable t) {
        while (t != null) {
            if (!seen.add(t)) {
                return false;
            }
            t = t.getCause();
        }
        return true;
    }
}
