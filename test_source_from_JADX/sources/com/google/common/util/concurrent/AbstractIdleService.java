package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.base.Supplier;
import com.google.common.util.concurrent.Service.Listener;
import com.google.common.util.concurrent.Service.State;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import name.antonsmirnov.firmata.FormatHelper;

@Beta
public abstract class AbstractIdleService implements Service {
    private final Service delegate = new DelegateService();
    private final Supplier<String> threadNameSupplier = new ThreadNameSupplier();

    /* renamed from: com.google.common.util.concurrent.AbstractIdleService$1 */
    class C05861 implements Executor {
        C05861() {
        }

        public void execute(Runnable command) {
            MoreExecutors.newThread((String) AbstractIdleService.this.threadNameSupplier.get(), command).start();
        }
    }

    private final class ThreadNameSupplier implements Supplier<String> {
        private ThreadNameSupplier() {
        }

        public String get() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(AbstractIdleService.this.serviceName());
            stringBuilder.append(FormatHelper.SPACE);
            stringBuilder.append(AbstractIdleService.this.state());
            return stringBuilder.toString();
        }
    }

    private final class DelegateService extends AbstractService {

        /* renamed from: com.google.common.util.concurrent.AbstractIdleService$DelegateService$1 */
        class C05871 implements Runnable {
            C05871() {
            }

            public void run() {
                try {
                    AbstractIdleService.this.startUp();
                    DelegateService.this.notifyStarted();
                } catch (Throwable t) {
                    DelegateService.this.notifyFailed(t);
                }
            }
        }

        /* renamed from: com.google.common.util.concurrent.AbstractIdleService$DelegateService$2 */
        class C05882 implements Runnable {
            C05882() {
            }

            public void run() {
                try {
                    AbstractIdleService.this.shutDown();
                    DelegateService.this.notifyStopped();
                } catch (Throwable t) {
                    DelegateService.this.notifyFailed(t);
                }
            }
        }

        private DelegateService() {
        }

        protected final void doStart() {
            MoreExecutors.renamingDecorator(AbstractIdleService.this.executor(), AbstractIdleService.this.threadNameSupplier).execute(new C05871());
        }

        protected final void doStop() {
            MoreExecutors.renamingDecorator(AbstractIdleService.this.executor(), AbstractIdleService.this.threadNameSupplier).execute(new C05882());
        }

        public String toString() {
            return AbstractIdleService.this.toString();
        }
    }

    protected abstract void shutDown() throws Exception;

    protected abstract void startUp() throws Exception;

    protected AbstractIdleService() {
    }

    protected Executor executor() {
        return new C05861();
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

    protected String serviceName() {
        return getClass().getSimpleName();
    }
}
