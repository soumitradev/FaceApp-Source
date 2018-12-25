package com.google.common.util.concurrent;

import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.Service.State;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

@Immutable
final class AbstractService$StateSnapshot {
    @Nullable
    final Throwable failure;
    final boolean shutdownWhenStartupFinishes;
    final State state;

    AbstractService$StateSnapshot(State internalState) {
        this(internalState, false, null);
    }

    AbstractService$StateSnapshot(State internalState, boolean shutdownWhenStartupFinishes, @Nullable Throwable failure) {
        boolean z;
        if (shutdownWhenStartupFinishes) {
            if (internalState != State.STARTING) {
                z = false;
                Preconditions.checkArgument(z, "shudownWhenStartupFinishes can only be set if state is STARTING. Got %s instead.", new Object[]{internalState});
                Preconditions.checkArgument(((failure == null ? 1 : 0) ^ (internalState != State.FAILED ? 1 : 0)) != 0, "A failure cause should be set if and only if the state is failed.  Got %s and %s instead.", new Object[]{internalState, failure});
                this.state = internalState;
                this.shutdownWhenStartupFinishes = shutdownWhenStartupFinishes;
                this.failure = failure;
            }
        }
        z = true;
        Preconditions.checkArgument(z, "shudownWhenStartupFinishes can only be set if state is STARTING. Got %s instead.", new Object[]{internalState});
        if (failure == null) {
        }
        if (internalState != State.FAILED) {
        }
        if (((failure == null ? 1 : 0) ^ (internalState != State.FAILED ? 1 : 0)) != 0) {
        }
        Preconditions.checkArgument(((failure == null ? 1 : 0) ^ (internalState != State.FAILED ? 1 : 0)) != 0, "A failure cause should be set if and only if the state is failed.  Got %s and %s instead.", new Object[]{internalState, failure});
        this.state = internalState;
        this.shutdownWhenStartupFinishes = shutdownWhenStartupFinishes;
        this.failure = failure;
    }

    State externalState() {
        if (this.shutdownWhenStartupFinishes && this.state == State.STARTING) {
            return State.STOPPING;
        }
        return this.state;
    }

    Throwable failureCause() {
        Preconditions.checkState(this.state == State.FAILED, "failureCause() is only valid if the service has failed, service is %s", new Object[]{this.state});
        return this.failure;
    }
}
