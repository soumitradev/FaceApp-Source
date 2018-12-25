package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.MapMaker;
import com.google.common.collect.Maps;
import com.google.j2objc.annotations.Weak;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
@Beta
public class CycleDetectingLockFactory {
    private static final ThreadLocal<ArrayList<CycleDetectingLockFactory$LockGraphNode>> acquiredLocks = new C05961();
    private static final ConcurrentMap<Class<? extends Enum>, Map<? extends Enum, CycleDetectingLockFactory$LockGraphNode>> lockGraphNodesPerType = new MapMaker().weakKeys().makeMap();
    private static final Logger logger = Logger.getLogger(CycleDetectingLockFactory.class.getName());
    final Policy policy;

    /* renamed from: com.google.common.util.concurrent.CycleDetectingLockFactory$1 */
    static class C05961 extends ThreadLocal<ArrayList<CycleDetectingLockFactory$LockGraphNode>> {
        C05961() {
        }

        protected ArrayList<CycleDetectingLockFactory$LockGraphNode> initialValue() {
            return Lists.newArrayListWithCapacity(3);
        }
    }

    private interface CycleDetectingLock {
        CycleDetectingLockFactory$LockGraphNode getLockGraphNode();

        boolean isAcquiredByCurrentThread();
    }

    private class CycleDetectingReentrantReadLock extends ReadLock {
        @Weak
        final CycleDetectingReentrantReadWriteLock readWriteLock;

        CycleDetectingReentrantReadLock(CycleDetectingReentrantReadWriteLock readWriteLock) {
            super(readWriteLock);
            this.readWriteLock = readWriteLock;
        }

        public void lock() {
            CycleDetectingLockFactory.this.aboutToAcquire(this.readWriteLock);
            try {
                super.lock();
            } finally {
                CycleDetectingLockFactory.this.lockStateChanged(this.readWriteLock);
            }
        }

        public void lockInterruptibly() throws InterruptedException {
            CycleDetectingLockFactory.this.aboutToAcquire(this.readWriteLock);
            try {
                super.lockInterruptibly();
            } finally {
                CycleDetectingLockFactory.this.lockStateChanged(this.readWriteLock);
            }
        }

        public boolean tryLock() {
            CycleDetectingLockFactory.this.aboutToAcquire(this.readWriteLock);
            try {
                boolean tryLock = super.tryLock();
                return tryLock;
            } finally {
                CycleDetectingLockFactory.this.lockStateChanged(this.readWriteLock);
            }
        }

        public boolean tryLock(long timeout, TimeUnit unit) throws InterruptedException {
            CycleDetectingLockFactory.this.aboutToAcquire(this.readWriteLock);
            try {
                boolean tryLock = super.tryLock(timeout, unit);
                return tryLock;
            } finally {
                CycleDetectingLockFactory.this.lockStateChanged(this.readWriteLock);
            }
        }

        public void unlock() {
            try {
                super.unlock();
            } finally {
                CycleDetectingLockFactory.this.lockStateChanged(this.readWriteLock);
            }
        }
    }

    private class CycleDetectingReentrantWriteLock extends WriteLock {
        @Weak
        final CycleDetectingReentrantReadWriteLock readWriteLock;

        CycleDetectingReentrantWriteLock(CycleDetectingReentrantReadWriteLock readWriteLock) {
            super(readWriteLock);
            this.readWriteLock = readWriteLock;
        }

        public void lock() {
            CycleDetectingLockFactory.this.aboutToAcquire(this.readWriteLock);
            try {
                super.lock();
            } finally {
                CycleDetectingLockFactory.this.lockStateChanged(this.readWriteLock);
            }
        }

        public void lockInterruptibly() throws InterruptedException {
            CycleDetectingLockFactory.this.aboutToAcquire(this.readWriteLock);
            try {
                super.lockInterruptibly();
            } finally {
                CycleDetectingLockFactory.this.lockStateChanged(this.readWriteLock);
            }
        }

        public boolean tryLock() {
            CycleDetectingLockFactory.this.aboutToAcquire(this.readWriteLock);
            try {
                boolean tryLock = super.tryLock();
                return tryLock;
            } finally {
                CycleDetectingLockFactory.this.lockStateChanged(this.readWriteLock);
            }
        }

        public boolean tryLock(long timeout, TimeUnit unit) throws InterruptedException {
            CycleDetectingLockFactory.this.aboutToAcquire(this.readWriteLock);
            try {
                boolean tryLock = super.tryLock(timeout, unit);
                return tryLock;
            } finally {
                CycleDetectingLockFactory.this.lockStateChanged(this.readWriteLock);
            }
        }

        public void unlock() {
            try {
                super.unlock();
            } finally {
                CycleDetectingLockFactory.this.lockStateChanged(this.readWriteLock);
            }
        }
    }

    private static class ExampleStackTrace extends IllegalStateException {
        static final StackTraceElement[] EMPTY_STACK_TRACE = new StackTraceElement[0];
        static final Set<String> EXCLUDED_CLASS_NAMES = ImmutableSet.of(CycleDetectingLockFactory.class.getName(), ExampleStackTrace.class.getName(), CycleDetectingLockFactory$LockGraphNode.class.getName());

        ExampleStackTrace(CycleDetectingLockFactory$LockGraphNode node1, CycleDetectingLockFactory$LockGraphNode node2) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(node1.getLockName());
            stringBuilder.append(" -> ");
            stringBuilder.append(node2.getLockName());
            super(stringBuilder.toString());
            StackTraceElement[] origStackTrace = getStackTrace();
            int i = 0;
            int n = origStackTrace.length;
            while (i < n) {
                if (WithExplicitOrdering.class.getName().equals(origStackTrace[i].getClassName())) {
                    setStackTrace(EMPTY_STACK_TRACE);
                    return;
                } else if (EXCLUDED_CLASS_NAMES.contains(origStackTrace[i].getClassName())) {
                    i++;
                } else {
                    setStackTrace((StackTraceElement[]) Arrays.copyOfRange(origStackTrace, i, n));
                    return;
                }
            }
        }
    }

    @ThreadSafe
    @Beta
    public interface Policy {
        void handlePotentialDeadlock(PotentialDeadlockException potentialDeadlockException);
    }

    final class CycleDetectingReentrantLock extends ReentrantLock implements CycleDetectingLock {
        private final CycleDetectingLockFactory$LockGraphNode lockGraphNode;

        private CycleDetectingReentrantLock(CycleDetectingLockFactory$LockGraphNode lockGraphNode, boolean fair) {
            super(fair);
            this.lockGraphNode = (CycleDetectingLockFactory$LockGraphNode) Preconditions.checkNotNull(lockGraphNode);
        }

        public CycleDetectingLockFactory$LockGraphNode getLockGraphNode() {
            return this.lockGraphNode;
        }

        public boolean isAcquiredByCurrentThread() {
            return isHeldByCurrentThread();
        }

        public void lock() {
            CycleDetectingLockFactory.this.aboutToAcquire(this);
            try {
                super.lock();
            } finally {
                CycleDetectingLockFactory.this.lockStateChanged(this);
            }
        }

        public void lockInterruptibly() throws InterruptedException {
            CycleDetectingLockFactory.this.aboutToAcquire(this);
            try {
                super.lockInterruptibly();
            } finally {
                CycleDetectingLockFactory.this.lockStateChanged(this);
            }
        }

        public boolean tryLock() {
            CycleDetectingLockFactory.this.aboutToAcquire(this);
            try {
                boolean tryLock = super.tryLock();
                return tryLock;
            } finally {
                CycleDetectingLockFactory.this.lockStateChanged(this);
            }
        }

        public boolean tryLock(long timeout, TimeUnit unit) throws InterruptedException {
            CycleDetectingLockFactory.this.aboutToAcquire(this);
            try {
                boolean tryLock = super.tryLock(timeout, unit);
                return tryLock;
            } finally {
                CycleDetectingLockFactory.this.lockStateChanged(this);
            }
        }

        public void unlock() {
            try {
                super.unlock();
            } finally {
                CycleDetectingLockFactory.this.lockStateChanged(this);
            }
        }
    }

    final class CycleDetectingReentrantReadWriteLock extends ReentrantReadWriteLock implements CycleDetectingLock {
        private final CycleDetectingLockFactory$LockGraphNode lockGraphNode;
        private final CycleDetectingReentrantReadLock readLock;
        private final CycleDetectingReentrantWriteLock writeLock;

        private CycleDetectingReentrantReadWriteLock(CycleDetectingLockFactory$LockGraphNode lockGraphNode, boolean fair) {
            super(fair);
            this.readLock = new CycleDetectingReentrantReadLock(this);
            this.writeLock = new CycleDetectingReentrantWriteLock(this);
            this.lockGraphNode = (CycleDetectingLockFactory$LockGraphNode) Preconditions.checkNotNull(lockGraphNode);
        }

        public ReadLock readLock() {
            return this.readLock;
        }

        public WriteLock writeLock() {
            return this.writeLock;
        }

        public CycleDetectingLockFactory$LockGraphNode getLockGraphNode() {
            return this.lockGraphNode;
        }

        public boolean isAcquiredByCurrentThread() {
            if (!isWriteLockedByCurrentThread()) {
                if (getReadHoldCount() <= 0) {
                    return false;
                }
            }
            return true;
        }
    }

    @Beta
    public enum Policies implements Policy {
        THROW {
            public void handlePotentialDeadlock(PotentialDeadlockException e) {
                throw e;
            }
        },
        WARN {
            public void handlePotentialDeadlock(PotentialDeadlockException e) {
                CycleDetectingLockFactory.logger.log(Level.SEVERE, "Detected potential deadlock", e);
            }
        },
        DISABLED {
            public void handlePotentialDeadlock(PotentialDeadlockException e) {
            }
        }
    }

    @Beta
    public static final class PotentialDeadlockException extends ExampleStackTrace {
        private final ExampleStackTrace conflictingStackTrace;

        private PotentialDeadlockException(CycleDetectingLockFactory$LockGraphNode node1, CycleDetectingLockFactory$LockGraphNode node2, ExampleStackTrace conflictingStackTrace) {
            super(node1, node2);
            this.conflictingStackTrace = conflictingStackTrace;
            initCause(conflictingStackTrace);
        }

        public ExampleStackTrace getConflictingStackTrace() {
            return this.conflictingStackTrace;
        }

        public String getMessage() {
            StringBuilder message = new StringBuilder(super.getMessage());
            for (Throwable t = this.conflictingStackTrace; t != null; t = t.getCause()) {
                message.append(", ");
                message.append(t.getMessage());
            }
            return message.toString();
        }
    }

    @Beta
    public static final class WithExplicitOrdering<E extends Enum<E>> extends CycleDetectingLockFactory {
        private final Map<E, CycleDetectingLockFactory$LockGraphNode> lockGraphNodes;

        @VisibleForTesting
        WithExplicitOrdering(Policy policy, Map<E, CycleDetectingLockFactory$LockGraphNode> lockGraphNodes) {
            super(policy);
            this.lockGraphNodes = lockGraphNodes;
        }

        public ReentrantLock newReentrantLock(E rank) {
            return newReentrantLock(rank, false);
        }

        public ReentrantLock newReentrantLock(E rank, boolean fair) {
            return this.policy == Policies.DISABLED ? new ReentrantLock(fair) : new CycleDetectingReentrantLock((CycleDetectingLockFactory$LockGraphNode) this.lockGraphNodes.get(rank), fair);
        }

        public ReentrantReadWriteLock newReentrantReadWriteLock(E rank) {
            return newReentrantReadWriteLock(rank, false);
        }

        public ReentrantReadWriteLock newReentrantReadWriteLock(E rank, boolean fair) {
            return this.policy == Policies.DISABLED ? new ReentrantReadWriteLock(fair) : new CycleDetectingReentrantReadWriteLock((CycleDetectingLockFactory$LockGraphNode) this.lockGraphNodes.get(rank), fair);
        }
    }

    public static CycleDetectingLockFactory newInstance(Policy policy) {
        return new CycleDetectingLockFactory(policy);
    }

    public ReentrantLock newReentrantLock(String lockName) {
        return newReentrantLock(lockName, false);
    }

    public ReentrantLock newReentrantLock(String lockName, boolean fair) {
        return this.policy == Policies.DISABLED ? new ReentrantLock(fair) : new CycleDetectingReentrantLock(new CycleDetectingLockFactory$LockGraphNode(lockName), fair);
    }

    public ReentrantReadWriteLock newReentrantReadWriteLock(String lockName) {
        return newReentrantReadWriteLock(lockName, false);
    }

    public ReentrantReadWriteLock newReentrantReadWriteLock(String lockName, boolean fair) {
        return this.policy == Policies.DISABLED ? new ReentrantReadWriteLock(fair) : new CycleDetectingReentrantReadWriteLock(new CycleDetectingLockFactory$LockGraphNode(lockName), fair);
    }

    public static <E extends Enum<E>> WithExplicitOrdering<E> newInstanceWithExplicitOrdering(Class<E> enumClass, Policy policy) {
        Preconditions.checkNotNull(enumClass);
        Preconditions.checkNotNull(policy);
        return new WithExplicitOrdering(policy, getOrCreateNodes(enumClass));
    }

    private static Map<? extends Enum, CycleDetectingLockFactory$LockGraphNode> getOrCreateNodes(Class<? extends Enum> clazz) {
        Map<? extends Enum, CycleDetectingLockFactory$LockGraphNode> existing = (Map) lockGraphNodesPerType.get(clazz);
        if (existing != null) {
            return existing;
        }
        Map<? extends Enum, CycleDetectingLockFactory$LockGraphNode> created = createNodes(clazz);
        return (Map) MoreObjects.firstNonNull((Map) lockGraphNodesPerType.putIfAbsent(clazz, created), created);
    }

    @VisibleForTesting
    static <E extends Enum<E>> Map<E, CycleDetectingLockFactory$LockGraphNode> createNodes(Class<E> clazz) {
        int i;
        EnumMap<E, CycleDetectingLockFactory$LockGraphNode> map = Maps.newEnumMap(clazz);
        Enum[] keys = (Enum[]) clazz.getEnumConstants();
        int numKeys = keys.length;
        ArrayList<CycleDetectingLockFactory$LockGraphNode> nodes = Lists.newArrayListWithCapacity(numKeys);
        int i2 = 0;
        for (E key : keys) {
            CycleDetectingLockFactory$LockGraphNode node = new CycleDetectingLockFactory$LockGraphNode(getLockName(key));
            nodes.add(node);
            map.put(key, node);
        }
        for (i = 1; i < numKeys; i++) {
            ((CycleDetectingLockFactory$LockGraphNode) nodes.get(i)).checkAcquiredLocks(Policies.THROW, nodes.subList(0, i));
        }
        while (true) {
            i = i2;
            if (i >= numKeys - 1) {
                return Collections.unmodifiableMap(map);
            }
            ((CycleDetectingLockFactory$LockGraphNode) nodes.get(i)).checkAcquiredLocks(Policies.DISABLED, nodes.subList(i + 1, numKeys));
            i2 = i + 1;
        }
    }

    private static String getLockName(Enum<?> rank) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(rank.getDeclaringClass().getSimpleName());
        stringBuilder.append(".");
        stringBuilder.append(rank.name());
        return stringBuilder.toString();
    }

    private CycleDetectingLockFactory(Policy policy) {
        this.policy = (Policy) Preconditions.checkNotNull(policy);
    }

    private void aboutToAcquire(CycleDetectingLock lock) {
        if (!lock.isAcquiredByCurrentThread()) {
            ArrayList<CycleDetectingLockFactory$LockGraphNode> acquiredLockList = (ArrayList) acquiredLocks.get();
            CycleDetectingLockFactory$LockGraphNode node = lock.getLockGraphNode();
            node.checkAcquiredLocks(this.policy, acquiredLockList);
            acquiredLockList.add(node);
        }
    }

    private void lockStateChanged(CycleDetectingLock lock) {
        if (!lock.isAcquiredByCurrentThread()) {
            ArrayList<CycleDetectingLockFactory$LockGraphNode> acquiredLockList = (ArrayList) acquiredLocks.get();
            CycleDetectingLockFactory$LockGraphNode node = lock.getLockGraphNode();
            for (int i = acquiredLockList.size() - 1; i >= 0; i--) {
                if (acquiredLockList.get(i) == node) {
                    acquiredLockList.remove(i);
                    return;
                }
            }
        }
    }
}
