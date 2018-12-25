package javax.jmdns.impl;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jmdns.impl.constants.DNSState;
import javax.jmdns.impl.tasks.DNSTask;

public interface DNSStatefulObject {

    public static final class DNSStatefulObjectSemaphore {
        private static Logger logger = Logger.getLogger(DNSStatefulObjectSemaphore.class.getName());
        private final String _name;
        private final ConcurrentMap<Thread, Semaphore> _semaphores = new ConcurrentHashMap(50);

        public DNSStatefulObjectSemaphore(String name) {
            this._name = name;
        }

        public void waitForEvent(long timeout) {
            Thread thread = Thread.currentThread();
            if (((Semaphore) this._semaphores.get(thread)) == null) {
                Semaphore semaphore = new Semaphore(1, true);
                semaphore.drainPermits();
                this._semaphores.putIfAbsent(thread, semaphore);
            }
            try {
                ((Semaphore) this._semaphores.get(thread)).tryAcquire(timeout, TimeUnit.MILLISECONDS);
            } catch (InterruptedException exception) {
                logger.log(Level.FINER, "Exception ", exception);
            }
        }

        public void signalEvent() {
            Collection<Semaphore> semaphores = this._semaphores.values();
            for (Semaphore semaphore : semaphores) {
                semaphore.release();
                semaphores.remove(semaphore);
            }
        }

        public String toString() {
            StringBuilder aLog = new StringBuilder(1000);
            aLog.append("Semaphore: ");
            aLog.append(this._name);
            if (this._semaphores.size() == 0) {
                aLog.append(" no semaphores.");
            } else {
                aLog.append(" semaphores:\n");
                for (Thread thread : this._semaphores.keySet()) {
                    aLog.append("\tThread: ");
                    aLog.append(thread.getName());
                    aLog.append(' ');
                    aLog.append(this._semaphores.get(thread));
                    aLog.append('\n');
                }
            }
            return aLog.toString();
        }
    }

    public static class DefaultImplementation extends ReentrantLock implements DNSStatefulObject {
        private static Logger logger = Logger.getLogger(DefaultImplementation.class.getName());
        private static final long serialVersionUID = -3264781576883412227L;
        private final DNSStatefulObjectSemaphore _announcing = new DNSStatefulObjectSemaphore("Announce");
        private final DNSStatefulObjectSemaphore _canceling = new DNSStatefulObjectSemaphore("Cancel");
        private volatile JmDNSImpl _dns = null;
        protected volatile DNSState _state = DNSState.PROBING_1;
        protected volatile DNSTask _task = null;

        public JmDNSImpl getDns() {
            return this._dns;
        }

        protected void setDns(JmDNSImpl dns) {
            this._dns = dns;
        }

        public void associateWithTask(DNSTask task, DNSState state) {
            if (this._task == null && this._state == state) {
                lock();
                try {
                    if (this._task == null && this._state == state) {
                        setTask(task);
                    }
                    unlock();
                } catch (Throwable th) {
                    unlock();
                }
            }
        }

        public void removeAssociationWithTask(DNSTask task) {
            if (this._task == task) {
                lock();
                try {
                    if (this._task == task) {
                        setTask(null);
                    }
                    unlock();
                } catch (Throwable th) {
                    unlock();
                }
            }
        }

        public boolean isAssociatedWithTask(DNSTask task, DNSState state) {
            lock();
            try {
                boolean z = this._task == task && this._state == state;
                unlock();
                return z;
            } catch (Throwable th) {
                unlock();
            }
        }

        protected void setTask(DNSTask task) {
            this._task = task;
        }

        protected void setState(DNSState state) {
            lock();
            try {
                this._state = state;
                if (isAnnounced()) {
                    this._announcing.signalEvent();
                }
                if (isCanceled()) {
                    this._canceling.signalEvent();
                    this._announcing.signalEvent();
                }
                unlock();
            } catch (Throwable th) {
                unlock();
            }
        }

        public boolean advanceState(DNSTask task) {
            if (this._task == task) {
                lock();
                try {
                    if (this._task == task) {
                        setState(this._state.advance());
                    } else {
                        Logger logger = logger;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Trying to advance state whhen not the owner. owner: ");
                        stringBuilder.append(this._task);
                        stringBuilder.append(" perpetrator: ");
                        stringBuilder.append(task);
                        logger.warning(stringBuilder.toString());
                    }
                    unlock();
                } catch (Throwable th) {
                    unlock();
                }
            }
            return true;
        }

        public boolean revertState() {
            if (!willCancel()) {
                lock();
                try {
                    if (!willCancel()) {
                        setState(this._state.revert());
                        setTask(null);
                    }
                    unlock();
                } catch (Throwable th) {
                    unlock();
                }
            }
            return true;
        }

        public boolean cancelState() {
            boolean result = false;
            if (!willCancel()) {
                lock();
                try {
                    if (!willCancel()) {
                        setState(DNSState.CANCELING_1);
                        setTask(null);
                        result = true;
                    }
                    unlock();
                } catch (Throwable th) {
                    unlock();
                }
            }
            return result;
        }

        public boolean closeState() {
            boolean result = false;
            if (!willClose()) {
                lock();
                try {
                    if (!willClose()) {
                        setState(DNSState.CLOSING);
                        setTask(null);
                        result = true;
                    }
                    unlock();
                } catch (Throwable th) {
                    unlock();
                }
            }
            return result;
        }

        public boolean recoverState() {
            lock();
            try {
                setState(DNSState.PROBING_1);
                setTask(null);
                return false;
            } finally {
                unlock();
            }
        }

        public boolean isProbing() {
            return this._state.isProbing();
        }

        public boolean isAnnouncing() {
            return this._state.isAnnouncing();
        }

        public boolean isAnnounced() {
            return this._state.isAnnounced();
        }

        public boolean isCanceling() {
            return this._state.isCanceling();
        }

        public boolean isCanceled() {
            return this._state.isCanceled();
        }

        public boolean isClosing() {
            return this._state.isClosing();
        }

        public boolean isClosed() {
            return this._state.isClosed();
        }

        private boolean willCancel() {
            if (!this._state.isCanceled()) {
                if (!this._state.isCanceling()) {
                    return false;
                }
            }
            return true;
        }

        private boolean willClose() {
            if (!this._state.isClosed()) {
                if (!this._state.isClosing()) {
                    return false;
                }
            }
            return true;
        }

        public boolean waitForAnnounced(long timeout) {
            if (!(isAnnounced() || willCancel())) {
                this._announcing.waitForEvent(timeout);
            }
            if (!isAnnounced()) {
                Logger logger;
                StringBuilder stringBuilder;
                if (!willCancel()) {
                    if (!willClose()) {
                        logger = logger;
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("Wait for announced timed out: ");
                        stringBuilder.append(this);
                        logger.warning(stringBuilder.toString());
                    }
                }
                logger = logger;
                stringBuilder = new StringBuilder();
                stringBuilder.append("Wait for announced cancelled: ");
                stringBuilder.append(this);
                logger.fine(stringBuilder.toString());
            }
            return isAnnounced();
        }

        public boolean waitForCanceled(long timeout) {
            if (!isCanceled()) {
                this._canceling.waitForEvent(timeout);
            }
            if (!(isCanceled() || willClose())) {
                Logger logger = logger;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Wait for canceled timed out: ");
                stringBuilder.append(this);
                logger.warning(stringBuilder.toString());
            }
            return isCanceled();
        }

        public String toString() {
            String stringBuilder;
            StringBuilder stringBuilder2 = new StringBuilder();
            if (this._dns != null) {
                StringBuilder stringBuilder3 = new StringBuilder();
                stringBuilder3.append("DNS: ");
                stringBuilder3.append(this._dns.getName());
                stringBuilder = stringBuilder3.toString();
            } else {
                stringBuilder = "NO DNS";
            }
            stringBuilder2.append(stringBuilder);
            stringBuilder2.append(" state: ");
            stringBuilder2.append(this._state);
            stringBuilder2.append(" task: ");
            stringBuilder2.append(this._task);
            return stringBuilder2.toString();
        }
    }

    boolean advanceState(DNSTask dNSTask);

    void associateWithTask(DNSTask dNSTask, DNSState dNSState);

    boolean cancelState();

    boolean closeState();

    JmDNSImpl getDns();

    boolean isAnnounced();

    boolean isAnnouncing();

    boolean isAssociatedWithTask(DNSTask dNSTask, DNSState dNSState);

    boolean isCanceled();

    boolean isCanceling();

    boolean isClosed();

    boolean isClosing();

    boolean isProbing();

    boolean recoverState();

    void removeAssociationWithTask(DNSTask dNSTask);

    boolean revertState();

    boolean waitForAnnounced(long j);

    boolean waitForCanceled(long j);
}
