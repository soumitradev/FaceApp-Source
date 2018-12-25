package bolts;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Task<TResult> {
    public static final ExecutorService BACKGROUND_EXECUTOR = BoltsExecutors.background();
    private static final Executor IMMEDIATE_EXECUTOR = BoltsExecutors.immediate();
    private static Task<?> TASK_CANCELLED = new Task(true);
    private static Task<Boolean> TASK_FALSE = new Task(Boolean.valueOf(false));
    private static Task<?> TASK_NULL = new Task(null);
    private static Task<Boolean> TASK_TRUE = new Task(Boolean.valueOf(true));
    public static final Executor UI_THREAD_EXECUTOR = AndroidExecutors.uiThread();
    private static volatile UnobservedExceptionHandler unobservedExceptionHandler;
    private boolean cancelled;
    private boolean complete;
    private List<Continuation<TResult, Void>> continuations = new ArrayList();
    private Exception error;
    private boolean errorHasBeenObserved;
    private final Object lock = new Object();
    private TResult result;
    private UnobservedErrorNotifier unobservedErrorNotifier;

    public interface UnobservedExceptionHandler {
        void unobservedException(Task<?> task, UnobservedTaskException unobservedTaskException);
    }

    /* renamed from: bolts.Task$3 */
    class C07453 implements Continuation<TResult, Task<Void>> {
        C07453() {
        }

        public Task<Void> then(Task<TResult> task) throws Exception {
            if (task.isCancelled()) {
                return Task.cancelled();
            }
            if (task.isFaulted()) {
                return Task.forError(task.getError());
            }
            return Task.forResult(null);
        }
    }

    public class TaskCompletionSource extends TaskCompletionSource<TResult> {
        TaskCompletionSource() {
        }
    }

    public static UnobservedExceptionHandler getUnobservedExceptionHandler() {
        return unobservedExceptionHandler;
    }

    public static void setUnobservedExceptionHandler(UnobservedExceptionHandler eh) {
        unobservedExceptionHandler = eh;
    }

    Task() {
    }

    private Task(TResult result) {
        trySetResult(result);
    }

    private Task(boolean cancelled) {
        if (cancelled) {
            trySetCancelled();
        } else {
            trySetResult(null);
        }
    }

    public static <TResult> TaskCompletionSource create() {
        Task<TResult> task = new Task();
        task.getClass();
        return new TaskCompletionSource();
    }

    public boolean isCompleted() {
        boolean z;
        synchronized (this.lock) {
            z = this.complete;
        }
        return z;
    }

    public boolean isCancelled() {
        boolean z;
        synchronized (this.lock) {
            z = this.cancelled;
        }
        return z;
    }

    public boolean isFaulted() {
        boolean z;
        synchronized (this.lock) {
            z = getError() != null;
        }
        return z;
    }

    public TResult getResult() {
        TResult tResult;
        synchronized (this.lock) {
            tResult = this.result;
        }
        return tResult;
    }

    public Exception getError() {
        Exception exception;
        synchronized (this.lock) {
            if (this.error != null) {
                this.errorHasBeenObserved = true;
                if (this.unobservedErrorNotifier != null) {
                    this.unobservedErrorNotifier.setObserved();
                    this.unobservedErrorNotifier = null;
                }
            }
            exception = this.error;
        }
        return exception;
    }

    public void waitForCompletion() throws InterruptedException {
        synchronized (this.lock) {
            if (!isCompleted()) {
                this.lock.wait();
            }
        }
    }

    public boolean waitForCompletion(long duration, TimeUnit timeUnit) throws InterruptedException {
        boolean isCompleted;
        synchronized (this.lock) {
            if (!isCompleted()) {
                this.lock.wait(timeUnit.toMillis(duration));
            }
            isCompleted = isCompleted();
        }
        return isCompleted;
    }

    public static <TResult> Task<TResult> forResult(TResult value) {
        if (value == null) {
            return TASK_NULL;
        }
        if (value instanceof Boolean) {
            return ((Boolean) value).booleanValue() ? TASK_TRUE : TASK_FALSE;
        }
        TaskCompletionSource<TResult> tcs = new TaskCompletionSource();
        tcs.setResult(value);
        return tcs.getTask();
    }

    public static <TResult> Task<TResult> forError(Exception error) {
        TaskCompletionSource<TResult> tcs = new TaskCompletionSource();
        tcs.setError(error);
        return tcs.getTask();
    }

    public static <TResult> Task<TResult> cancelled() {
        return TASK_CANCELLED;
    }

    public static Task<Void> delay(long delay) {
        return delay(delay, BoltsExecutors.scheduled(), null);
    }

    public static Task<Void> delay(long delay, CancellationToken cancellationToken) {
        return delay(delay, BoltsExecutors.scheduled(), cancellationToken);
    }

    static Task<Void> delay(long delay, ScheduledExecutorService executor, CancellationToken cancellationToken) {
        if (cancellationToken != null && cancellationToken.isCancellationRequested()) {
            return cancelled();
        }
        if (delay <= 0) {
            return forResult(null);
        }
        final TaskCompletionSource<Void> tcs = new TaskCompletionSource();
        final ScheduledFuture<?> scheduled = executor.schedule(new Runnable() {
            public void run() {
                tcs.trySetResult(null);
            }
        }, delay, TimeUnit.MILLISECONDS);
        if (cancellationToken != null) {
            cancellationToken.register(new Runnable() {
                public void run() {
                    scheduled.cancel(true);
                    tcs.trySetCancelled();
                }
            });
        }
        return tcs.getTask();
    }

    public <TOut> Task<TOut> cast() {
        return this;
    }

    public Task<Void> makeVoid() {
        return continueWithTask(new C07453());
    }

    public static <TResult> Task<TResult> callInBackground(Callable<TResult> callable) {
        return call(callable, BACKGROUND_EXECUTOR, null);
    }

    public static <TResult> Task<TResult> callInBackground(Callable<TResult> callable, CancellationToken ct) {
        return call(callable, BACKGROUND_EXECUTOR, ct);
    }

    public static <TResult> Task<TResult> call(Callable<TResult> callable, Executor executor) {
        return call(callable, executor, null);
    }

    public static <TResult> Task<TResult> call(final Callable<TResult> callable, Executor executor, final CancellationToken ct) {
        final TaskCompletionSource<TResult> tcs = new TaskCompletionSource();
        try {
            executor.execute(new Runnable() {
                public void run() {
                    if (ct == null || !ct.isCancellationRequested()) {
                        try {
                            tcs.setResult(callable.call());
                        } catch (CancellationException e) {
                            tcs.setCancelled();
                        } catch (Exception e2) {
                            tcs.setError(e2);
                        }
                        return;
                    }
                    tcs.setCancelled();
                }
            });
        } catch (Exception e) {
            tcs.setError(new ExecutorException(e));
        }
        return tcs.getTask();
    }

    public static <TResult> Task<TResult> call(Callable<TResult> callable) {
        return call(callable, IMMEDIATE_EXECUTOR, null);
    }

    public static <TResult> Task<TResult> call(Callable<TResult> callable, CancellationToken ct) {
        return call(callable, IMMEDIATE_EXECUTOR, ct);
    }

    public static <TResult> Task<Task<TResult>> whenAnyResult(Collection<? extends Task<TResult>> tasks) {
        if (tasks.size() == 0) {
            return forResult(null);
        }
        final TaskCompletionSource<Task<TResult>> firstCompleted = new TaskCompletionSource();
        final AtomicBoolean isAnyTaskComplete = new AtomicBoolean(false);
        for (Task<TResult> task : tasks) {
            task.continueWith(new Continuation<TResult, Void>() {
                public Void then(Task<TResult> task) {
                    if (isAnyTaskComplete.compareAndSet(false, true)) {
                        firstCompleted.setResult(task);
                    } else {
                        task.getError();
                    }
                    return null;
                }
            });
        }
        return firstCompleted.getTask();
    }

    public static Task<Task<?>> whenAny(Collection<? extends Task<?>> tasks) {
        if (tasks.size() == 0) {
            return forResult(null);
        }
        final TaskCompletionSource<Task<?>> firstCompleted = new TaskCompletionSource();
        final AtomicBoolean isAnyTaskComplete = new AtomicBoolean(false);
        for (Task<?> task : tasks) {
            task.continueWith(new Continuation<Object, Void>() {
                public Void then(Task<Object> task) {
                    if (isAnyTaskComplete.compareAndSet(false, true)) {
                        firstCompleted.setResult(task);
                    } else {
                        task.getError();
                    }
                    return null;
                }
            });
        }
        return firstCompleted.getTask();
    }

    public static <TResult> Task<List<TResult>> whenAllResult(final Collection<? extends Task<TResult>> tasks) {
        return whenAll(tasks).onSuccess(new Continuation<Void, List<TResult>>() {
            public List<TResult> then(Task<Void> task) throws Exception {
                if (tasks.size() == 0) {
                    return Collections.emptyList();
                }
                List<TResult> results = new ArrayList();
                for (Task<TResult> individualTask : tasks) {
                    results.add(individualTask.getResult());
                }
                return results;
            }
        });
    }

    public static Task<Void> whenAll(Collection<? extends Task<?>> tasks) {
        if (tasks.size() == 0) {
            return forResult(null);
        }
        TaskCompletionSource<Void> allFinished = new TaskCompletionSource();
        final ArrayList<Exception> causes = new ArrayList();
        final Object errorLock = new Object();
        final AtomicInteger count = new AtomicInteger(tasks.size());
        final AtomicBoolean isCancelled = new AtomicBoolean(false);
        Iterator i$ = tasks.iterator();
        while (true) {
            Iterator i$2 = i$;
            if (!i$2.hasNext()) {
                return allFinished.getTask();
            }
            final TaskCompletionSource<Void> taskCompletionSource = allFinished;
            ((Task) i$2.next()).continueWith(new Continuation<Object, Void>() {
                public Void then(Task<Object> task) {
                    if (task.isFaulted()) {
                        synchronized (errorLock) {
                            causes.add(task.getError());
                        }
                    }
                    if (task.isCancelled()) {
                        isCancelled.set(true);
                    }
                    if (count.decrementAndGet() == 0) {
                        if (causes.size() != 0) {
                            if (causes.size() == 1) {
                                taskCompletionSource.setError((Exception) causes.get(0));
                            } else {
                                taskCompletionSource.setError(new AggregateException(String.format("There were %d exceptions.", new Object[]{Integer.valueOf(causes.size())}), causes));
                            }
                        } else if (isCancelled.get()) {
                            taskCompletionSource.setCancelled();
                        } else {
                            taskCompletionSource.setResult(null);
                        }
                    }
                    return null;
                }
            });
            i$ = i$2;
        }
    }

    public Task<Void> continueWhile(Callable<Boolean> predicate, Continuation<Void, Task<Void>> continuation) {
        return continueWhile(predicate, continuation, IMMEDIATE_EXECUTOR, null);
    }

    public Task<Void> continueWhile(Callable<Boolean> predicate, Continuation<Void, Task<Void>> continuation, CancellationToken ct) {
        return continueWhile(predicate, continuation, IMMEDIATE_EXECUTOR, ct);
    }

    public Task<Void> continueWhile(Callable<Boolean> predicate, Continuation<Void, Task<Void>> continuation, Executor executor) {
        return continueWhile(predicate, continuation, executor, null);
    }

    public Task<Void> continueWhile(Callable<Boolean> predicate, Continuation<Void, Task<Void>> continuation, Executor executor, CancellationToken ct) {
        Capture<Continuation<Void, Task<Void>>> predicateContinuation = new Capture();
        final CancellationToken cancellationToken = ct;
        final Callable<Boolean> callable = predicate;
        final Continuation<Void, Task<Void>> continuation2 = continuation;
        final Executor executor2 = executor;
        final Capture<Continuation<Void, Task<Void>>> capture = predicateContinuation;
        predicateContinuation.set(new Continuation<Void, Task<Void>>() {
            public Task<Void> then(Task<Void> task) throws Exception {
                if (cancellationToken != null && cancellationToken.isCancellationRequested()) {
                    return Task.cancelled();
                }
                if (((Boolean) callable.call()).booleanValue()) {
                    return Task.forResult(null).onSuccessTask(continuation2, executor2).onSuccessTask((Continuation) capture.get(), executor2);
                }
                return Task.forResult(null);
            }
        });
        return makeVoid().continueWithTask((Continuation) predicateContinuation.get(), executor);
    }

    public <TContinuationResult> Task<TContinuationResult> continueWith(Continuation<TResult, TContinuationResult> continuation, Executor executor) {
        return continueWith(continuation, executor, null);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public <TContinuationResult> bolts.Task<TContinuationResult> continueWith(bolts.Continuation<TResult, TContinuationResult> r12, java.util.concurrent.Executor r13, bolts.CancellationToken r14) {
        /*
        r11 = this;
        r0 = new bolts.TaskCompletionSource;
        r0.<init>();
        r7 = r11.lock;
        monitor-enter(r7);
        r1 = r11.isCompleted();	 Catch:{ all -> 0x002a }
        r8 = r1;
        if (r8 != 0) goto L_0x001f;
    L_0x000f:
        r9 = r11.continuations;	 Catch:{ all -> 0x002e }
        r10 = new bolts.Task$10;	 Catch:{ all -> 0x002e }
        r1 = r10;
        r2 = r11;
        r3 = r0;
        r4 = r12;
        r5 = r13;
        r6 = r14;
        r1.<init>(r3, r4, r5, r6);	 Catch:{ all -> 0x002e }
        r9.add(r10);	 Catch:{ all -> 0x002e }
    L_0x001f:
        monitor-exit(r7);	 Catch:{ all -> 0x002e }
        if (r8 == 0) goto L_0x0025;
    L_0x0022:
        completeImmediately(r0, r12, r11, r13, r14);
    L_0x0025:
        r1 = r0.getTask();
        return r1;
    L_0x002a:
        r1 = move-exception;
        r8 = 0;
    L_0x002c:
        monitor-exit(r7);	 Catch:{ all -> 0x002e }
        throw r1;
    L_0x002e:
        r1 = move-exception;
        goto L_0x002c;
        */
        throw new UnsupportedOperationException("Method not decompiled: bolts.Task.continueWith(bolts.Continuation, java.util.concurrent.Executor, bolts.CancellationToken):bolts.Task<TContinuationResult>");
    }

    public <TContinuationResult> Task<TContinuationResult> continueWith(Continuation<TResult, TContinuationResult> continuation) {
        return continueWith(continuation, IMMEDIATE_EXECUTOR, null);
    }

    public <TContinuationResult> Task<TContinuationResult> continueWith(Continuation<TResult, TContinuationResult> continuation, CancellationToken ct) {
        return continueWith(continuation, IMMEDIATE_EXECUTOR, ct);
    }

    public <TContinuationResult> Task<TContinuationResult> continueWithTask(Continuation<TResult, Task<TContinuationResult>> continuation, Executor executor) {
        return continueWithTask(continuation, executor, null);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public <TContinuationResult> bolts.Task<TContinuationResult> continueWithTask(bolts.Continuation<TResult, bolts.Task<TContinuationResult>> r12, java.util.concurrent.Executor r13, bolts.CancellationToken r14) {
        /*
        r11 = this;
        r0 = new bolts.TaskCompletionSource;
        r0.<init>();
        r7 = r11.lock;
        monitor-enter(r7);
        r1 = r11.isCompleted();	 Catch:{ all -> 0x002a }
        r8 = r1;
        if (r8 != 0) goto L_0x001f;
    L_0x000f:
        r9 = r11.continuations;	 Catch:{ all -> 0x002e }
        r10 = new bolts.Task$11;	 Catch:{ all -> 0x002e }
        r1 = r10;
        r2 = r11;
        r3 = r0;
        r4 = r12;
        r5 = r13;
        r6 = r14;
        r1.<init>(r3, r4, r5, r6);	 Catch:{ all -> 0x002e }
        r9.add(r10);	 Catch:{ all -> 0x002e }
    L_0x001f:
        monitor-exit(r7);	 Catch:{ all -> 0x002e }
        if (r8 == 0) goto L_0x0025;
    L_0x0022:
        completeAfterTask(r0, r12, r11, r13, r14);
    L_0x0025:
        r1 = r0.getTask();
        return r1;
    L_0x002a:
        r1 = move-exception;
        r8 = 0;
    L_0x002c:
        monitor-exit(r7);	 Catch:{ all -> 0x002e }
        throw r1;
    L_0x002e:
        r1 = move-exception;
        goto L_0x002c;
        */
        throw new UnsupportedOperationException("Method not decompiled: bolts.Task.continueWithTask(bolts.Continuation, java.util.concurrent.Executor, bolts.CancellationToken):bolts.Task<TContinuationResult>");
    }

    public <TContinuationResult> Task<TContinuationResult> continueWithTask(Continuation<TResult, Task<TContinuationResult>> continuation) {
        return continueWithTask(continuation, IMMEDIATE_EXECUTOR, null);
    }

    public <TContinuationResult> Task<TContinuationResult> continueWithTask(Continuation<TResult, Task<TContinuationResult>> continuation, CancellationToken ct) {
        return continueWithTask(continuation, IMMEDIATE_EXECUTOR, ct);
    }

    public <TContinuationResult> Task<TContinuationResult> onSuccess(Continuation<TResult, TContinuationResult> continuation, Executor executor) {
        return onSuccess(continuation, executor, null);
    }

    public <TContinuationResult> Task<TContinuationResult> onSuccess(final Continuation<TResult, TContinuationResult> continuation, Executor executor, final CancellationToken ct) {
        return continueWithTask(new Continuation<TResult, Task<TContinuationResult>>() {
            public Task<TContinuationResult> then(Task<TResult> task) {
                if (ct != null && ct.isCancellationRequested()) {
                    return Task.cancelled();
                }
                if (task.isFaulted()) {
                    return Task.forError(task.getError());
                }
                if (task.isCancelled()) {
                    return Task.cancelled();
                }
                return task.continueWith(continuation);
            }
        }, executor);
    }

    public <TContinuationResult> Task<TContinuationResult> onSuccess(Continuation<TResult, TContinuationResult> continuation) {
        return onSuccess(continuation, IMMEDIATE_EXECUTOR, null);
    }

    public <TContinuationResult> Task<TContinuationResult> onSuccess(Continuation<TResult, TContinuationResult> continuation, CancellationToken ct) {
        return onSuccess(continuation, IMMEDIATE_EXECUTOR, ct);
    }

    public <TContinuationResult> Task<TContinuationResult> onSuccessTask(Continuation<TResult, Task<TContinuationResult>> continuation, Executor executor) {
        return onSuccessTask(continuation, executor, null);
    }

    public <TContinuationResult> Task<TContinuationResult> onSuccessTask(final Continuation<TResult, Task<TContinuationResult>> continuation, Executor executor, final CancellationToken ct) {
        return continueWithTask(new Continuation<TResult, Task<TContinuationResult>>() {
            public Task<TContinuationResult> then(Task<TResult> task) {
                if (ct != null && ct.isCancellationRequested()) {
                    return Task.cancelled();
                }
                if (task.isFaulted()) {
                    return Task.forError(task.getError());
                }
                if (task.isCancelled()) {
                    return Task.cancelled();
                }
                return task.continueWithTask(continuation);
            }
        }, executor);
    }

    public <TContinuationResult> Task<TContinuationResult> onSuccessTask(Continuation<TResult, Task<TContinuationResult>> continuation) {
        return onSuccessTask((Continuation) continuation, IMMEDIATE_EXECUTOR);
    }

    public <TContinuationResult> Task<TContinuationResult> onSuccessTask(Continuation<TResult, Task<TContinuationResult>> continuation, CancellationToken ct) {
        return onSuccessTask(continuation, IMMEDIATE_EXECUTOR, ct);
    }

    private static <TContinuationResult, TResult> void completeImmediately(final TaskCompletionSource<TContinuationResult> tcs, final Continuation<TResult, TContinuationResult> continuation, final Task<TResult> task, Executor executor, final CancellationToken ct) {
        try {
            executor.execute(new Runnable() {
                public void run() {
                    if (ct == null || !ct.isCancellationRequested()) {
                        try {
                            tcs.setResult(continuation.then(task));
                        } catch (CancellationException e) {
                            tcs.setCancelled();
                        } catch (Exception e2) {
                            tcs.setError(e2);
                        }
                        return;
                    }
                    tcs.setCancelled();
                }
            });
        } catch (Exception e) {
            tcs.setError(new ExecutorException(e));
        }
    }

    private static <TContinuationResult, TResult> void completeAfterTask(final TaskCompletionSource<TContinuationResult> tcs, final Continuation<TResult, Task<TContinuationResult>> continuation, final Task<TResult> task, Executor executor, final CancellationToken ct) {
        try {
            executor.execute(new Runnable() {

                /* renamed from: bolts.Task$15$1 */
                class C07441 implements Continuation<TContinuationResult, Void> {
                    C07441() {
                    }

                    public Void then(Task<TContinuationResult> task) {
                        if (ct == null || !ct.isCancellationRequested()) {
                            if (task.isCancelled()) {
                                tcs.setCancelled();
                            } else if (task.isFaulted()) {
                                tcs.setError(task.getError());
                            } else {
                                tcs.setResult(task.getResult());
                            }
                            return null;
                        }
                        tcs.setCancelled();
                        return null;
                    }
                }

                public void run() {
                    if (ct == null || !ct.isCancellationRequested()) {
                        try {
                            Task<TContinuationResult> result = (Task) continuation.then(task);
                            if (result == null) {
                                tcs.setResult(null);
                            } else {
                                result.continueWith(new C07441());
                            }
                        } catch (CancellationException e) {
                            tcs.setCancelled();
                        } catch (Exception e2) {
                            tcs.setError(e2);
                        }
                        return;
                    }
                    tcs.setCancelled();
                }
            });
        } catch (Exception e) {
            tcs.setError(new ExecutorException(e));
        }
    }

    private void runContinuations() {
        synchronized (this.lock) {
            for (Continuation<TResult, ?> continuation : this.continuations) {
                try {
                    continuation.then(this);
                } catch (RuntimeException e) {
                    throw e;
                } catch (Exception e2) {
                    throw new RuntimeException(e2);
                }
            }
            this.continuations = null;
        }
    }

    boolean trySetCancelled() {
        synchronized (this.lock) {
            if (this.complete) {
                return false;
            }
            this.complete = true;
            this.cancelled = true;
            this.lock.notifyAll();
            runContinuations();
            return true;
        }
    }

    boolean trySetResult(TResult result) {
        synchronized (this.lock) {
            if (this.complete) {
                return false;
            }
            this.complete = true;
            this.result = result;
            this.lock.notifyAll();
            runContinuations();
            return true;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    boolean trySetError(java.lang.Exception r4) {
        /*
        r3 = this;
        r0 = r3.lock;
        monitor-enter(r0);
        r1 = r3.complete;	 Catch:{ all -> 0x002c }
        r2 = 0;
        if (r1 == 0) goto L_0x000a;
    L_0x0008:
        monitor-exit(r0);	 Catch:{ all -> 0x002c }
        return r2;
    L_0x000a:
        r1 = 1;
        r3.complete = r1;	 Catch:{ all -> 0x002c }
        r3.error = r4;	 Catch:{ all -> 0x002c }
        r3.errorHasBeenObserved = r2;	 Catch:{ all -> 0x002c }
        r2 = r3.lock;	 Catch:{ all -> 0x002c }
        r2.notifyAll();	 Catch:{ all -> 0x002c }
        r3.runContinuations();	 Catch:{ all -> 0x002c }
        r2 = r3.errorHasBeenObserved;	 Catch:{ all -> 0x002c }
        if (r2 != 0) goto L_0x002a;
    L_0x001d:
        r2 = getUnobservedExceptionHandler();	 Catch:{ all -> 0x002c }
        if (r2 == 0) goto L_0x002a;
    L_0x0023:
        r2 = new bolts.UnobservedErrorNotifier;	 Catch:{ all -> 0x002c }
        r2.<init>(r3);	 Catch:{ all -> 0x002c }
        r3.unobservedErrorNotifier = r2;	 Catch:{ all -> 0x002c }
    L_0x002a:
        monitor-exit(r0);	 Catch:{ all -> 0x002c }
        return r1;
    L_0x002c:
        r1 = move-exception;
        monitor-exit(r0);	 Catch:{ all -> 0x002c }
        throw r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: bolts.Task.trySetError(java.lang.Exception):boolean");
    }
}
