package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.j2objc.annotations.Weak;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import javax.annotation.concurrent.GuardedBy;

@Beta
public final class Monitor {
    @GuardedBy("lock")
    private Guard activeGuards;
    private final boolean fair;
    private final ReentrantLock lock;

    @Beta
    public static abstract class Guard {
        final Condition condition;
        @Weak
        final Monitor monitor;
        @GuardedBy("monitor.lock")
        Guard next;
        @GuardedBy("monitor.lock")
        int waiterCount = 0;

        public abstract boolean isSatisfied();

        protected Guard(Monitor monitor) {
            this.monitor = (Monitor) Preconditions.checkNotNull(monitor, "monitor");
            this.condition = monitor.lock.newCondition();
        }
    }

    public Monitor() {
        this(false);
    }

    public Monitor(boolean fair) {
        this.activeGuards = null;
        this.fair = fair;
        this.lock = new ReentrantLock(fair);
    }

    public void enter() {
        this.lock.lock();
    }

    public void enterInterruptibly() throws InterruptedException {
        this.lock.lockInterruptibly();
    }

    public boolean enter(long time, TimeUnit unit) {
        long startTime;
        long timeoutNanos = toSafeNanos(time, unit);
        ReentrantLock lock = this.lock;
        if (!this.fair && lock.tryLock()) {
            return true;
        }
        boolean interrupted = Thread.interrupted();
        long remainingNanos;
        try {
            boolean tryLock;
            startTime = System.nanoTime();
            remainingNanos = timeoutNanos;
            while (true) {
                tryLock = lock.tryLock(remainingNanos, TimeUnit.NANOSECONDS);
                break;
            }
            if (interrupted) {
                Thread.currentThread().interrupt();
            }
            return tryLock;
        } catch (InterruptedException e) {
            interrupted = true;
            remainingNanos = remainingNanos(startTime, timeoutNanos);
        } catch (Throwable th) {
            if (interrupted) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public boolean enterInterruptibly(long time, TimeUnit unit) throws InterruptedException {
        return this.lock.tryLock(time, unit);
    }

    public boolean tryEnter() {
        return this.lock.tryLock();
    }

    public void enterWhen(Guard guard) throws InterruptedException {
        if (guard.monitor != this) {
            throw new IllegalMonitorStateException();
        }
        ReentrantLock lock = this.lock;
        boolean signalBeforeWaiting = lock.isHeldByCurrentThread();
        lock.lockInterruptibly();
        boolean satisfied = false;
        try {
            if (!guard.isSatisfied()) {
                await(guard, signalBeforeWaiting);
            }
            satisfied = true;
        } finally {
            if (!satisfied) {
                leave();
            }
        }
    }

    public void enterWhenUninterruptibly(Guard guard) {
        if (guard.monitor != this) {
            throw new IllegalMonitorStateException();
        }
        ReentrantLock lock = this.lock;
        boolean signalBeforeWaiting = lock.isHeldByCurrentThread();
        lock.lock();
        boolean satisfied = false;
        try {
            if (!guard.isSatisfied()) {
                awaitUninterruptibly(guard, signalBeforeWaiting);
            }
            satisfied = true;
        } finally {
            if (!satisfied) {
                leave();
            }
        }
    }

    public boolean enterWhen(Guard guard, long time, TimeUnit unit) throws InterruptedException {
        boolean threw;
        Monitor monitor = this;
        Guard guard2 = guard;
        long timeoutNanos = toSafeNanos(time, unit);
        if (guard2.monitor != monitor) {
            throw new IllegalMonitorStateException();
        }
        boolean z;
        ReentrantLock lock = monitor.lock;
        boolean reentrant = lock.isHeldByCurrentThread();
        long startTime = 0;
        if (!monitor.fair) {
            if (Thread.interrupted()) {
                throw new InterruptedException();
            } else if (lock.tryLock()) {
                boolean satisfied;
                long j = time;
                TimeUnit timeUnit = unit;
                z = true;
                threw = true;
                if (!guard.isSatisfied()) {
                    if (awaitNanos(guard2, startTime != 0 ? timeoutNanos : remainingNanos(startTime, timeoutNanos), reentrant)) {
                        z = false;
                    }
                }
                satisfied = z;
                if (!satisfied) {
                    if (false && !reentrant) {
                        try {
                            signalNextWaiter();
                        } catch (Throwable th) {
                            lock.unlock();
                        }
                    }
                    lock.unlock();
                }
                return satisfied;
            }
        }
        startTime = initNanoTime(timeoutNanos);
        if (!lock.tryLock(time, unit)) {
            return false;
        }
        z = true;
        threw = true;
        try {
            if (guard.isSatisfied()) {
                if (startTime != 0) {
                }
                if (awaitNanos(guard2, startTime != 0 ? timeoutNanos : remainingNanos(startTime, timeoutNanos), reentrant)) {
                    z = false;
                }
            }
            satisfied = z;
            if (satisfied) {
                signalNextWaiter();
                lock.unlock();
            }
            return satisfied;
        } catch (Throwable th2) {
            lock.unlock();
        }
    }

    public boolean enterWhenUninterruptibly(Guard guard, long time, TimeUnit unit) {
        Throwable th;
        Throwable th2;
        Monitor monitor = this;
        Guard guard2 = guard;
        long timeoutNanos = toSafeNanos(time, unit);
        if (guard2.monitor != monitor) {
            throw new IllegalMonitorStateException();
        }
        ReentrantLock lock = monitor.lock;
        long startTime = 0;
        boolean signalBeforeWaiting = lock.isHeldByCurrentThread();
        boolean interrupted = Thread.interrupted();
        try {
            boolean interrupted2;
            boolean satisfied;
            if (monitor.fair || !lock.tryLock()) {
                startTime = initNanoTime(timeoutNanos);
                interrupted2 = interrupted;
                long remainingNanos = timeoutNanos;
                while (true) {
                    try {
                        break;
                    } catch (InterruptedException e) {
                        InterruptedException interrupt = e;
                        interrupted2 = true;
                        remainingNanos = remainingNanos(startTime, timeoutNanos);
                    } catch (Throwable th3) {
                        th = th3;
                        interrupted = true;
                    }
                }
                if (lock.tryLock(remainingNanos, TimeUnit.NANOSECONDS)) {
                    interrupted = interrupted2;
                } else {
                    if (interrupted2) {
                        Thread.currentThread().interrupt();
                    }
                    return false;
                }
            }
            while (true) {
                try {
                    break;
                } catch (InterruptedException e2) {
                    interrupted2 = startTime;
                    InterruptedException interrupt2 = e2;
                    interrupted = true;
                    signalBeforeWaiting = false;
                    startTime = interrupted2;
                } catch (Throwable th4) {
                    th = th4;
                    th2 = th;
                    if (interrupted) {
                        Thread.currentThread().interrupt();
                    }
                    throw th2;
                }
            }
            if (guard.isSatisfied()) {
                satisfied = true;
            } else {
                long remainingNanos2;
                if (startTime == 0) {
                    startTime = initNanoTime(timeoutNanos);
                    remainingNanos2 = timeoutNanos;
                } else {
                    remainingNanos2 = remainingNanos(startTime, timeoutNanos);
                }
                satisfied = awaitNanos(guard2, remainingNanos2, signalBeforeWaiting);
            }
            if (!satisfied) {
                lock.unlock();
            }
            if (interrupted) {
                Thread.currentThread().interrupt();
            }
            return satisfied;
        } catch (Throwable th5) {
            th = th5;
            th2 = th;
            if (interrupted) {
                Thread.currentThread().interrupt();
            }
            throw th2;
        }
    }

    public boolean enterIf(Guard guard) {
        if (guard.monitor != this) {
            throw new IllegalMonitorStateException();
        }
        ReentrantLock lock = this.lock;
        lock.lock();
        boolean satisfied = false;
        try {
            boolean isSatisfied = guard.isSatisfied();
            satisfied = isSatisfied;
            return isSatisfied;
        } finally {
            if (!satisfied) {
                lock.unlock();
            }
        }
    }

    public boolean enterIfInterruptibly(Guard guard) throws InterruptedException {
        if (guard.monitor != this) {
            throw new IllegalMonitorStateException();
        }
        ReentrantLock lock = this.lock;
        lock.lockInterruptibly();
        boolean satisfied = false;
        try {
            boolean isSatisfied = guard.isSatisfied();
            satisfied = isSatisfied;
            return isSatisfied;
        } finally {
            if (!satisfied) {
                lock.unlock();
            }
        }
    }

    public boolean enterIf(Guard guard, long time, TimeUnit unit) {
        if (guard.monitor != this) {
            throw new IllegalMonitorStateException();
        } else if (!enter(time, unit)) {
            return false;
        } else {
            boolean satisfied = false;
            try {
                boolean isSatisfied = guard.isSatisfied();
                satisfied = isSatisfied;
                return isSatisfied;
            } finally {
                if (!satisfied) {
                    this.lock.unlock();
                }
            }
        }
    }

    public boolean enterIfInterruptibly(Guard guard, long time, TimeUnit unit) throws InterruptedException {
        if (guard.monitor != this) {
            throw new IllegalMonitorStateException();
        }
        ReentrantLock lock = this.lock;
        if (!lock.tryLock(time, unit)) {
            return false;
        }
        boolean satisfied = false;
        try {
            boolean isSatisfied = guard.isSatisfied();
            satisfied = isSatisfied;
            return isSatisfied;
        } finally {
            if (!satisfied) {
                lock.unlock();
            }
        }
    }

    public boolean tryEnterIf(Guard guard) {
        if (guard.monitor != this) {
            throw new IllegalMonitorStateException();
        }
        ReentrantLock lock = this.lock;
        if (!lock.tryLock()) {
            return false;
        }
        boolean satisfied = false;
        try {
            boolean isSatisfied = guard.isSatisfied();
            satisfied = isSatisfied;
            return isSatisfied;
        } finally {
            if (!satisfied) {
                lock.unlock();
            }
        }
    }

    public void waitFor(Guard guard) throws InterruptedException {
        if (((guard.monitor == this ? 1 : 0) & this.lock.isHeldByCurrentThread()) == 0) {
            throw new IllegalMonitorStateException();
        } else if (!guard.isSatisfied()) {
            await(guard, true);
        }
    }

    public void waitForUninterruptibly(Guard guard) {
        if (((guard.monitor == this ? 1 : 0) & this.lock.isHeldByCurrentThread()) == 0) {
            throw new IllegalMonitorStateException();
        } else if (!guard.isSatisfied()) {
            awaitUninterruptibly(guard, true);
        }
    }

    public boolean waitFor(Guard guard, long time, TimeUnit unit) throws InterruptedException {
        long timeoutNanos = toSafeNanos(time, unit);
        if (((guard.monitor == this ? 1 : 0) & this.lock.isHeldByCurrentThread()) == 0) {
            throw new IllegalMonitorStateException();
        } else if (guard.isSatisfied()) {
            return true;
        } else {
            if (!Thread.interrupted()) {
                return awaitNanos(guard, timeoutNanos, true);
            }
            throw new InterruptedException();
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean waitForUninterruptibly(com.google.common.util.concurrent.Monitor.Guard r16, long r17, java.util.concurrent.TimeUnit r19) {
        /*
        r15 = this;
        r1 = r15;
        r2 = r16;
        r3 = toSafeNanos(r17, r19);
        r5 = r2.monitor;
        r6 = 1;
        if (r5 != r1) goto L_0x000e;
    L_0x000c:
        r5 = 1;
        goto L_0x000f;
    L_0x000e:
        r5 = 0;
    L_0x000f:
        r7 = r1.lock;
        r7 = r7.isHeldByCurrentThread();
        r5 = r5 & r7;
        if (r5 != 0) goto L_0x001e;
    L_0x0018:
        r5 = new java.lang.IllegalMonitorStateException;
        r5.<init>();
        throw r5;
    L_0x001e:
        r5 = r16.isSatisfied();
        if (r5 == 0) goto L_0x0025;
    L_0x0024:
        return r6;
    L_0x0025:
        r5 = 1;
        r7 = initNanoTime(r3);
        r9 = java.lang.Thread.interrupted();
        r11 = r9;
        r9 = r3;
    L_0x0030:
        r12 = r1.awaitNanos(r2, r9, r5);	 Catch:{ InterruptedException -> 0x0042 }
        if (r11 == 0) goto L_0x003d;
    L_0x0036:
        r6 = java.lang.Thread.currentThread();
        r6.interrupt();
    L_0x003d:
        return r12;
    L_0x003e:
        r0 = move-exception;
        r6 = r5;
        r5 = r0;
        goto L_0x005d;
    L_0x0042:
        r0 = move-exception;
        r12 = r0;
        r11 = 1;
        r13 = r16.isSatisfied();	 Catch:{ all -> 0x003e }
        if (r13 == 0) goto L_0x0056;
        if (r11 == 0) goto L_0x0055;
    L_0x004e:
        r13 = java.lang.Thread.currentThread();
        r13.interrupt();
    L_0x0055:
        return r6;
    L_0x0056:
        r5 = 0;
        r13 = remainingNanos(r7, r3);	 Catch:{ all -> 0x003e }
        r9 = r13;
        goto L_0x0030;
    L_0x005d:
        if (r11 == 0) goto L_0x0066;
    L_0x005f:
        r9 = java.lang.Thread.currentThread();
        r9.interrupt();
    L_0x0066:
        throw r5;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.util.concurrent.Monitor.waitForUninterruptibly(com.google.common.util.concurrent.Monitor$Guard, long, java.util.concurrent.TimeUnit):boolean");
    }

    public void leave() {
        ReentrantLock lock = this.lock;
        try {
            if (lock.getHoldCount() == 1) {
                signalNextWaiter();
            }
            lock.unlock();
        } catch (Throwable th) {
            lock.unlock();
        }
    }

    public boolean isFair() {
        return this.fair;
    }

    public boolean isOccupied() {
        return this.lock.isLocked();
    }

    public boolean isOccupiedByCurrentThread() {
        return this.lock.isHeldByCurrentThread();
    }

    public int getOccupiedDepth() {
        return this.lock.getHoldCount();
    }

    public int getQueueLength() {
        return this.lock.getQueueLength();
    }

    public boolean hasQueuedThreads() {
        return this.lock.hasQueuedThreads();
    }

    public boolean hasQueuedThread(Thread thread) {
        return this.lock.hasQueuedThread(thread);
    }

    public boolean hasWaiters(Guard guard) {
        return getWaitQueueLength(guard) > 0;
    }

    public int getWaitQueueLength(Guard guard) {
        if (guard.monitor != this) {
            throw new IllegalMonitorStateException();
        }
        this.lock.lock();
        try {
            int i = guard.waiterCount;
            return i;
        } finally {
            this.lock.unlock();
        }
    }

    private static long toSafeNanos(long time, TimeUnit unit) {
        long timeoutNanos = unit.toNanos(time);
        if (timeoutNanos <= 0) {
            return 0;
        }
        return timeoutNanos > 6917529027641081853L ? 6917529027641081853L : timeoutNanos;
    }

    private static long initNanoTime(long timeoutNanos) {
        if (timeoutNanos <= 0) {
            return 0;
        }
        long startTime = System.nanoTime();
        return startTime == 0 ? 1 : startTime;
    }

    private static long remainingNanos(long startTime, long timeoutNanos) {
        return timeoutNanos <= 0 ? 0 : timeoutNanos - (System.nanoTime() - startTime);
    }

    @GuardedBy("lock")
    private void signalNextWaiter() {
        for (Guard guard = this.activeGuards; guard != null; guard = guard.next) {
            if (isSatisfied(guard)) {
                guard.condition.signal();
                return;
            }
        }
    }

    @GuardedBy("lock")
    private boolean isSatisfied(Guard guard) {
        try {
            return guard.isSatisfied();
        } catch (Throwable throwable) {
            signalAllWaiters();
            RuntimeException propagate = Throwables.propagate(throwable);
        }
    }

    @GuardedBy("lock")
    private void signalAllWaiters() {
        for (Guard guard = this.activeGuards; guard != null; guard = guard.next) {
            guard.condition.signalAll();
        }
    }

    @GuardedBy("lock")
    private void beginWaitingFor(Guard guard) {
        int waiters = guard.waiterCount;
        guard.waiterCount = waiters + 1;
        if (waiters == 0) {
            guard.next = this.activeGuards;
            this.activeGuards = guard;
        }
    }

    @GuardedBy("lock")
    private void endWaitingFor(Guard guard) {
        int waiters = guard.waiterCount - 1;
        guard.waiterCount = waiters;
        if (waiters == 0) {
            Guard p = this.activeGuards;
            Guard pred = null;
            while (p != guard) {
                pred = p;
                p = p.next;
            }
            if (pred == null) {
                this.activeGuards = p.next;
            } else {
                pred.next = p.next;
            }
            p.next = null;
        }
    }

    @GuardedBy("lock")
    private void await(Guard guard, boolean signalBeforeWaiting) throws InterruptedException {
        if (signalBeforeWaiting) {
            signalNextWaiter();
        }
        beginWaitingFor(guard);
        while (true) {
            try {
                guard.condition.await();
                if (guard.isSatisfied()) {
                    break;
                }
            } finally {
                endWaitingFor(guard);
            }
        }
    }

    @GuardedBy("lock")
    private void awaitUninterruptibly(Guard guard, boolean signalBeforeWaiting) {
        if (signalBeforeWaiting) {
            signalNextWaiter();
        }
        beginWaitingFor(guard);
        while (true) {
            try {
                guard.condition.awaitUninterruptibly();
                if (guard.isSatisfied()) {
                    break;
                }
            } finally {
                endWaitingFor(guard);
            }
        }
    }

    @GuardedBy("lock")
    private boolean awaitNanos(Guard guard, long nanos, boolean signalBeforeWaiting) throws InterruptedException {
        long nanos2 = nanos;
        boolean firstTime = true;
        while (nanos2 > 0) {
            if (firstTime) {
                if (signalBeforeWaiting) {
                    try {
                        signalNextWaiter();
                    } catch (Throwable th) {
                        if (!firstTime) {
                            endWaitingFor(guard);
                        }
                    }
                }
                beginWaitingFor(guard);
                firstTime = false;
            }
            nanos2 = guard.condition.awaitNanos(nanos2);
            if (guard.isSatisfied()) {
                if (!firstTime) {
                    endWaitingFor(guard);
                }
                return true;
            }
        }
        if (!firstTime) {
            endWaitingFor(guard);
        }
        return false;
    }
}
