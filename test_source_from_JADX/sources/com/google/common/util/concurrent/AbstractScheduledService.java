package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.util.concurrent.Service.Listener;
import com.google.common.util.concurrent.Service.State;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;
import javax.annotation.concurrent.GuardedBy;
import name.antonsmirnov.firmata.FormatHelper;

@Beta
public abstract class AbstractScheduledService implements Service {
    private static final Logger logger = Logger.getLogger(AbstractScheduledService.class.getName());
    private final AbstractService delegate = new ServiceDelegate();

    public static abstract class Scheduler {
        abstract Future<?> schedule(AbstractService abstractService, ScheduledExecutorService scheduledExecutorService, Runnable runnable);

        public static Scheduler newFixedDelaySchedule(long initialDelay, long delay, TimeUnit unit) {
            Preconditions.checkNotNull(unit);
            Preconditions.checkArgument(delay > 0, "delay must be > 0, found %s", Long.valueOf(delay));
            final long j = initialDelay;
            final long j2 = delay;
            final TimeUnit timeUnit = unit;
            return new Scheduler() {
                public Future<?> schedule(AbstractService service, ScheduledExecutorService executor, Runnable task) {
                    return executor.scheduleWithFixedDelay(task, j, j2, timeUnit);
                }
            };
        }

        public static Scheduler newFixedRateSchedule(long initialDelay, long period, TimeUnit unit) {
            Preconditions.checkNotNull(unit);
            Preconditions.checkArgument(period > 0, "period must be > 0, found %s", Long.valueOf(period));
            final long j = initialDelay;
            final long j2 = period;
            final TimeUnit timeUnit = unit;
            return new Scheduler() {
                public Future<?> schedule(AbstractService service, ScheduledExecutorService executor, Runnable task) {
                    return executor.scheduleAtFixedRate(task, j, j2, timeUnit);
                }
            };
        }

        private Scheduler() {
        }
    }

    @Beta
    public static abstract class CustomScheduler extends Scheduler {

        @Beta
        protected static final class Schedule {
            private final long delay;
            private final TimeUnit unit;

            public Schedule(long delay, TimeUnit unit) {
                this.delay = delay;
                this.unit = (TimeUnit) Preconditions.checkNotNull(unit);
            }
        }

        private class ReschedulableCallable extends ForwardingFuture<Void> implements Callable<Void> {
            @GuardedBy("lock")
            private Future<Void> currentFuture;
            private final ScheduledExecutorService executor;
            private final ReentrantLock lock = new ReentrantLock();
            private final AbstractService service;
            private final Runnable wrappedRunnable;

            ReschedulableCallable(AbstractService service, ScheduledExecutorService executor, Runnable runnable) {
                this.wrappedRunnable = runnable;
                this.executor = executor;
                this.service = service;
            }

            public Void call() throws Exception {
                this.wrappedRunnable.run();
                reschedule();
                return null;
            }

            public void reschedule() {
                try {
                    Schedule schedule = CustomScheduler.this.getNextSchedule();
                    Throwable scheduleFailure = null;
                    this.lock.lock();
                    try {
                        if (this.currentFuture == null || !this.currentFuture.isCancelled()) {
                            this.currentFuture = this.executor.schedule(this, schedule.delay, schedule.unit);
                        }
                    } catch (Throwable th) {
                        this.lock.unlock();
                    }
                    this.lock.unlock();
                    if (scheduleFailure != null) {
                        this.service.notifyFailed(scheduleFailure);
                    }
                } catch (Throwable t) {
                    this.service.notifyFailed(t);
                }
            }

            public boolean cancel(boolean mayInterruptIfRunning) {
                this.lock.lock();
                try {
                    boolean cancel = this.currentFuture.cancel(mayInterruptIfRunning);
                    return cancel;
                } finally {
                    this.lock.unlock();
                }
            }

            public boolean isCancelled() {
                this.lock.lock();
                try {
                    boolean isCancelled = this.currentFuture.isCancelled();
                    return isCancelled;
                } finally {
                    this.lock.unlock();
                }
            }

            protected Future<Void> delegate() {
                throw new UnsupportedOperationException("Only cancel and isCancelled is supported by this future");
            }
        }

        protected abstract Schedule getNextSchedule() throws Exception;

        public CustomScheduler() {
            super();
        }

        final Future<?> schedule(AbstractService service, ScheduledExecutorService executor, Runnable runnable) {
            ReschedulableCallable task = new ReschedulableCallable(service, executor, runnable);
            task.reschedule();
            return task;
        }
    }

    private final class ServiceDelegate extends AbstractService {
        private volatile ScheduledExecutorService executorService;
        private final ReentrantLock lock;
        private volatile Future<?> runningTask;
        private final Runnable task;

        /* renamed from: com.google.common.util.concurrent.AbstractScheduledService$ServiceDelegate$2 */
        class C05892 implements Runnable {
            C05892() {
            }

            public void run() {
                ServiceDelegate.this.lock.lock();
                try {
                    AbstractScheduledService.this.startUp();
                    ServiceDelegate.this.runningTask = AbstractScheduledService.this.scheduler().schedule(AbstractScheduledService.this.delegate, ServiceDelegate.this.executorService, ServiceDelegate.this.task);
                    ServiceDelegate.this.notifyStarted();
                } catch (Throwable th) {
                    ServiceDelegate.this.lock.unlock();
                }
                ServiceDelegate.this.lock.unlock();
            }
        }

        /* renamed from: com.google.common.util.concurrent.AbstractScheduledService$ServiceDelegate$3 */
        class C05903 implements Runnable {
            C05903() {
            }

            public void run() {
                try {
                    ServiceDelegate.this.lock.lock();
                    if (ServiceDelegate.this.state() != State.STOPPING) {
                        ServiceDelegate.this.lock.unlock();
                        return;
                    }
                    AbstractScheduledService.this.shutDown();
                    ServiceDelegate.this.lock.unlock();
                    ServiceDelegate.this.notifyStopped();
                } catch (Throwable t) {
                    ServiceDelegate.this.notifyFailed(t);
                }
            }
        }

        class Task implements Runnable {
            Task() {
            }

            public void run() {
                ServiceDelegate.this.lock.lock();
                try {
                    if (ServiceDelegate.this.runningTask.isCancelled()) {
                        ServiceDelegate.this.lock.unlock();
                        return;
                    }
                    AbstractScheduledService.this.runOneIteration();
                    ServiceDelegate.this.lock.unlock();
                } catch (Throwable th) {
                    ServiceDelegate.this.lock.unlock();
                }
            }
        }

        /* renamed from: com.google.common.util.concurrent.AbstractScheduledService$ServiceDelegate$1 */
        class C10131 implements Supplier<String> {
            C10131() {
            }

            public String get() {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(AbstractScheduledService.this.serviceName());
                stringBuilder.append(FormatHelper.SPACE);
                stringBuilder.append(ServiceDelegate.this.state());
                return stringBuilder.toString();
            }
        }

        private ServiceDelegate() {
            this.lock = new ReentrantLock();
            this.task = new Task();
        }

        protected final void doStart() {
            this.executorService = MoreExecutors.renamingDecorator(AbstractScheduledService.this.executor(), new C10131());
            this.executorService.execute(new C05892());
        }

        protected final void doStop() {
            this.runningTask.cancel(false);
            this.executorService.execute(new C05903());
        }

        public String toString() {
            return AbstractScheduledService.this.toString();
        }
    }

    protected abstract void runOneIteration() throws Exception;

    protected abstract Scheduler scheduler();

    protected AbstractScheduledService() {
    }

    protected void startUp() throws Exception {
    }

    protected void shutDown() throws Exception {
    }

    protected ScheduledExecutorService executor() {
        final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {
            public Thread newThread(Runnable runnable) {
                return MoreExecutors.newThread(AbstractScheduledService.this.serviceName(), runnable);
            }
        });
        addListener(new Listener() {
            public void terminated(State from) {
                executor.shutdown();
            }

            public void failed(State from, Throwable failure) {
                executor.shutdown();
            }
        }, MoreExecutors.directExecutor());
        return executor;
    }

    protected String serviceName() {
        return getClass().getSimpleName();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(serviceName());
        stringBuilder.append(" [");
        stringBuilder.append(state());
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public final boolean isRunning() {
        return this.delegate.isRunning();
    }

    public final State state() {
        return this.delegate.state();
    }

    public final void addListener(Listener listener, Executor executor) {
        this.delegate.addListener(listener, executor);
    }

    public final Throwable failureCause() {
        return this.delegate.failureCause();
    }

    public final Service startAsync() {
        this.delegate.startAsync();
        return this;
    }

    public final Service stopAsync() {
        this.delegate.stopAsync();
        return this;
    }

    public final void awaitRunning() {
        this.delegate.awaitRunning();
    }

    public final void awaitRunning(long timeout, TimeUnit unit) throws TimeoutException {
        this.delegate.awaitRunning(timeout, unit);
    }

    public final void awaitTerminated() {
        this.delegate.awaitTerminated();
    }

    public final void awaitTerminated(long timeout, TimeUnit unit) throws TimeoutException {
        this.delegate.awaitTerminated(timeout, unit);
    }
}
