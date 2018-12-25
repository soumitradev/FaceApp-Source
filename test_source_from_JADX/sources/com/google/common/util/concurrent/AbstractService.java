package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.Monitor.Guard;
import com.google.common.util.concurrent.Service.Listener;
import com.google.common.util.concurrent.Service.State;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javax.annotation.concurrent.GuardedBy;

@Beta
public abstract class AbstractService implements Service {
    private static final Callback<Listener> RUNNING_CALLBACK = new Callback<Listener>("running()") {
        void call(Listener listener) {
            listener.running();
        }
    };
    private static final Callback<Listener> STARTING_CALLBACK = new Callback<Listener>("starting()") {
        void call(Listener listener) {
            listener.starting();
        }
    };
    private static final Callback<Listener> STOPPING_FROM_RUNNING_CALLBACK = stoppingCallback(State.RUNNING);
    private static final Callback<Listener> STOPPING_FROM_STARTING_CALLBACK = stoppingCallback(State.STARTING);
    private static final Callback<Listener> TERMINATED_FROM_NEW_CALLBACK = terminatedCallback(State.NEW);
    private static final Callback<Listener> TERMINATED_FROM_RUNNING_CALLBACK = terminatedCallback(State.RUNNING);
    private static final Callback<Listener> TERMINATED_FROM_STOPPING_CALLBACK = terminatedCallback(State.STOPPING);
    private final Guard hasReachedRunning = new HasReachedRunningGuard();
    private final Guard isStartable = new IsStartableGuard();
    private final Guard isStoppable = new IsStoppableGuard();
    private final Guard isStopped = new IsStoppedGuard();
    @GuardedBy("monitor")
    private final List<ListenerCallQueue<Listener>> listeners = Collections.synchronizedList(new ArrayList());
    private final Monitor monitor = new Monitor();
    @GuardedBy("monitor")
    private volatile AbstractService$StateSnapshot snapshot = new AbstractService$StateSnapshot(State.NEW);

    private final class HasReachedRunningGuard extends Guard {
        HasReachedRunningGuard() {
            super(AbstractService.this.monitor);
        }

        public boolean isSatisfied() {
            return AbstractService.this.state().compareTo(State.RUNNING) >= 0;
        }
    }

    private final class IsStartableGuard extends Guard {
        IsStartableGuard() {
            super(AbstractService.this.monitor);
        }

        public boolean isSatisfied() {
            return AbstractService.this.state() == State.NEW;
        }
    }

    private final class IsStoppableGuard extends Guard {
        IsStoppableGuard() {
            super(AbstractService.this.monitor);
        }

        public boolean isSatisfied() {
            return AbstractService.this.state().compareTo(State.RUNNING) <= 0;
        }
    }

    private final class IsStoppedGuard extends Guard {
        IsStoppedGuard() {
            super(AbstractService.this.monitor);
        }

        public boolean isSatisfied() {
            return AbstractService.this.state().isTerminal();
        }
    }

    protected abstract void doStart();

    protected abstract void doStop();

    private static Callback<Listener> terminatedCallback(final State from) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("terminated({from = ");
        stringBuilder.append(from);
        stringBuilder.append("})");
        return new Callback<Listener>(stringBuilder.toString()) {
            void call(Listener listener) {
                listener.terminated(from);
            }
        };
    }

    private static Callback<Listener> stoppingCallback(final State from) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("stopping({from = ");
        stringBuilder.append(from);
        stringBuilder.append("})");
        return new Callback<Listener>(stringBuilder.toString()) {
            void call(Listener listener) {
                listener.stopping(from);
            }
        };
    }

    protected AbstractService() {
    }

    public final Service startAsync() {
        if (this.monitor.enterIf(this.isStartable)) {
            try {
                this.snapshot = new AbstractService$StateSnapshot(State.STARTING);
                starting();
                doStart();
            } catch (Throwable th) {
                this.monitor.leave();
                executeListeners();
            }
            this.monitor.leave();
            executeListeners();
            return this;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Service ");
        stringBuilder.append(this);
        stringBuilder.append(" has already been started");
        throw new IllegalStateException(stringBuilder.toString());
    }

    public final Service stopAsync() {
        if (this.monitor.enterIf(this.isStoppable)) {
            try {
                State previous = state();
                StringBuilder stringBuilder;
                switch (previous) {
                    case NEW:
                        this.snapshot = new AbstractService$StateSnapshot(State.TERMINATED);
                        terminated(State.NEW);
                        break;
                    case STARTING:
                        this.snapshot = new AbstractService$StateSnapshot(State.STARTING, true, null);
                        stopping(State.STARTING);
                        break;
                    case RUNNING:
                        this.snapshot = new AbstractService$StateSnapshot(State.STOPPING);
                        stopping(State.RUNNING);
                        doStop();
                        break;
                    case STOPPING:
                    case TERMINATED:
                    case FAILED:
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("isStoppable is incorrectly implemented, saw: ");
                        stringBuilder.append(previous);
                        throw new AssertionError(stringBuilder.toString());
                    default:
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("Unexpected state: ");
                        stringBuilder.append(previous);
                        throw new AssertionError(stringBuilder.toString());
                }
            } catch (Throwable th) {
                this.monitor.leave();
                executeListeners();
            }
            this.monitor.leave();
            executeListeners();
        }
        return this;
    }

    public final void awaitRunning() {
        this.monitor.enterWhenUninterruptibly(this.hasReachedRunning);
        try {
            checkCurrentState(State.RUNNING);
        } finally {
            this.monitor.leave();
        }
    }

    public final void awaitRunning(long timeout, TimeUnit unit) throws TimeoutException {
        if (this.monitor.enterWhenUninterruptibly(this.hasReachedRunning, timeout, unit)) {
            try {
                checkCurrentState(State.RUNNING);
            } finally {
                this.monitor.leave();
            }
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Timed out waiting for ");
            stringBuilder.append(this);
            stringBuilder.append(" to reach the RUNNING state.");
            throw new TimeoutException(stringBuilder.toString());
        }
    }

    public final void awaitTerminated() {
        this.monitor.enterWhenUninterruptibly(this.isStopped);
        try {
            checkCurrentState(State.TERMINATED);
        } finally {
            this.monitor.leave();
        }
    }

    public final void awaitTerminated(long timeout, TimeUnit unit) throws TimeoutException {
        if (this.monitor.enterWhenUninterruptibly(this.isStopped, timeout, unit)) {
            try {
                checkCurrentState(State.TERMINATED);
            } finally {
                this.monitor.leave();
            }
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Timed out waiting for ");
            stringBuilder.append(this);
            stringBuilder.append(" to reach a terminal state. ");
            stringBuilder.append("Current state: ");
            stringBuilder.append(state());
            throw new TimeoutException(stringBuilder.toString());
        }
    }

    @GuardedBy("monitor")
    private void checkCurrentState(State expected) {
        State actual = state();
        if (actual == expected) {
            return;
        }
        if (actual == State.FAILED) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Expected the service to be ");
            stringBuilder.append(expected);
            stringBuilder.append(", but the service has FAILED");
            throw new IllegalStateException(stringBuilder.toString(), failureCause());
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append("Expected the service to be ");
        stringBuilder.append(expected);
        stringBuilder.append(", but was ");
        stringBuilder.append(actual);
        throw new IllegalStateException(stringBuilder.toString());
    }

    protected final void notifyStarted() {
        this.monitor.enter();
        try {
            if (this.snapshot.state != State.STARTING) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Cannot notifyStarted() when the service is ");
                stringBuilder.append(this.snapshot.state);
                IllegalStateException failure = new IllegalStateException(stringBuilder.toString());
                notifyFailed(failure);
                throw failure;
            }
            if (this.snapshot.shutdownWhenStartupFinishes) {
                this.snapshot = new AbstractService$StateSnapshot(State.STOPPING);
                doStop();
            } else {
                this.snapshot = new AbstractService$StateSnapshot(State.RUNNING);
                running();
            }
            this.monitor.leave();
            executeListeners();
        } catch (Throwable th) {
            this.monitor.leave();
            executeListeners();
        }
    }

    protected final void notifyStopped() {
        this.monitor.enter();
        try {
            State previous = this.snapshot.state;
            if (previous == State.STOPPING || previous == State.RUNNING) {
                this.snapshot = new AbstractService$StateSnapshot(State.TERMINATED);
                terminated(previous);
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Cannot notifyStopped() when the service is ");
            stringBuilder.append(previous);
            IllegalStateException failure = new IllegalStateException(stringBuilder.toString());
            notifyFailed(failure);
            throw failure;
        } finally {
            this.monitor.leave();
            executeListeners();
        }
    }

    protected final void notifyFailed(Throwable cause) {
        Preconditions.checkNotNull(cause);
        this.monitor.enter();
        try {
            State previous = state();
            StringBuilder stringBuilder;
            switch (previous) {
                case NEW:
                case TERMINATED:
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("Failed while in state:");
                    stringBuilder.append(previous);
                    throw new IllegalStateException(stringBuilder.toString(), cause);
                case STARTING:
                case RUNNING:
                case STOPPING:
                    this.snapshot = new AbstractService$StateSnapshot(State.FAILED, false, cause);
                    failed(previous, cause);
                    break;
                case FAILED:
                    break;
                default:
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("Unexpected state: ");
                    stringBuilder.append(previous);
                    throw new AssertionError(stringBuilder.toString());
            }
            this.monitor.leave();
            executeListeners();
        } catch (Throwable th) {
            this.monitor.leave();
            executeListeners();
        }
    }

    public final boolean isRunning() {
        return state() == State.RUNNING;
    }

    public final State state() {
        return this.snapshot.externalState();
    }

    public final Throwable failureCause() {
        return this.snapshot.failureCause();
    }

    public final void addListener(Listener listener, Executor executor) {
        Preconditions.checkNotNull(listener, "listener");
        Preconditions.checkNotNull(executor, "executor");
        this.monitor.enter();
        try {
            if (!state().isTerminal()) {
                this.listeners.add(new ListenerCallQueue(listener, executor));
            }
            this.monitor.leave();
        } catch (Throwable th) {
            this.monitor.leave();
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getClass().getSimpleName());
        stringBuilder.append(" [");
        stringBuilder.append(state());
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    private void executeListeners() {
        if (!this.monitor.isOccupiedByCurrentThread()) {
            for (int i = 0; i < this.listeners.size(); i++) {
                ((ListenerCallQueue) this.listeners.get(i)).execute();
            }
        }
    }

    @GuardedBy("monitor")
    private void starting() {
        STARTING_CALLBACK.enqueueOn(this.listeners);
    }

    @GuardedBy("monitor")
    private void running() {
        RUNNING_CALLBACK.enqueueOn(this.listeners);
    }

    @GuardedBy("monitor")
    private void stopping(State from) {
        if (from == State.STARTING) {
            STOPPING_FROM_STARTING_CALLBACK.enqueueOn(this.listeners);
        } else if (from == State.RUNNING) {
            STOPPING_FROM_RUNNING_CALLBACK.enqueueOn(this.listeners);
        } else {
            throw new AssertionError();
        }
    }

    @GuardedBy("monitor")
    private void terminated(State from) {
        int i = C05916.$SwitchMap$com$google$common$util$concurrent$Service$State[from.ordinal()];
        if (i != 1) {
            switch (i) {
                case 3:
                    TERMINATED_FROM_RUNNING_CALLBACK.enqueueOn(this.listeners);
                    return;
                case 4:
                    TERMINATED_FROM_STOPPING_CALLBACK.enqueueOn(this.listeners);
                    return;
                default:
                    throw new AssertionError();
            }
        }
        TERMINATED_FROM_NEW_CALLBACK.enqueueOn(this.listeners);
    }

    @GuardedBy("monitor")
    private void failed(final State from, final Throwable cause) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("failed({from = ");
        stringBuilder.append(from);
        stringBuilder.append(", cause = ");
        stringBuilder.append(cause);
        stringBuilder.append("})");
        new Callback<Listener>(stringBuilder.toString()) {
            void call(Listener listener) {
                listener.failed(from, cause);
            }
        }.enqueueOn(this.listeners);
    }
}
