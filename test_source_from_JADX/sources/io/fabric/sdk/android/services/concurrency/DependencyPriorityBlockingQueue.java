package io.fabric.sdk.android.services.concurrency;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class DependencyPriorityBlockingQueue<E extends Dependency & Task & PriorityProvider> extends PriorityBlockingQueue<E> {
    static final int PEEK = 1;
    static final int POLL = 2;
    static final int POLL_WITH_TIMEOUT = 3;
    static final int TAKE = 0;
    final Queue<E> blockedQueue = new LinkedList();
    private final ReentrantLock lock = new ReentrantLock();

    public E take() throws InterruptedException {
        return get(0, null, null);
    }

    public E peek() {
        try {
            return get(1, null, null);
        } catch (InterruptedException e) {
            return null;
        }
    }

    public E poll(long timeout, TimeUnit unit) throws InterruptedException {
        return get(3, Long.valueOf(timeout), unit);
    }

    public E poll() {
        try {
            return get(2, null, null);
        } catch (InterruptedException e) {
            return null;
        }
    }

    public int size() {
        try {
            this.lock.lock();
            int size = this.blockedQueue.size() + super.size();
            return size;
        } finally {
            this.lock.unlock();
        }
    }

    public <T> T[] toArray(T[] a) {
        try {
            this.lock.lock();
            T[] concatenate = concatenate(super.toArray(a), this.blockedQueue.toArray(a));
            return concatenate;
        } finally {
            this.lock.unlock();
        }
    }

    public Object[] toArray() {
        try {
            this.lock.lock();
            Object[] concatenate = concatenate(super.toArray(), this.blockedQueue.toArray());
            return concatenate;
        } finally {
            this.lock.unlock();
        }
    }

    public int drainTo(Collection<? super E> c) {
        try {
            this.lock.lock();
            int numberOfItems = super.drainTo(c) + this.blockedQueue.size();
            while (!this.blockedQueue.isEmpty()) {
                c.add(this.blockedQueue.poll());
            }
            return numberOfItems;
        } finally {
            this.lock.unlock();
        }
    }

    public int drainTo(Collection<? super E> c, int maxElements) {
        try {
            this.lock.lock();
            int numberOfItems = super.drainTo(c, maxElements);
            while (!this.blockedQueue.isEmpty() && numberOfItems <= maxElements) {
                c.add(this.blockedQueue.poll());
                numberOfItems++;
            }
            this.lock.unlock();
            return numberOfItems;
        } catch (Throwable th) {
            this.lock.unlock();
        }
    }

    public boolean contains(Object o) {
        try {
            boolean z;
            this.lock.lock();
            if (!super.contains(o)) {
                if (!this.blockedQueue.contains(o)) {
                    z = false;
                    this.lock.unlock();
                    return z;
                }
            }
            z = true;
            this.lock.unlock();
            return z;
        } catch (Throwable th) {
            this.lock.unlock();
        }
    }

    public void clear() {
        try {
            this.lock.lock();
            this.blockedQueue.clear();
            super.clear();
        } finally {
            this.lock.unlock();
        }
    }

    public boolean remove(Object o) {
        try {
            boolean z;
            this.lock.lock();
            if (!super.remove(o)) {
                if (!this.blockedQueue.remove(o)) {
                    z = false;
                    this.lock.unlock();
                    return z;
                }
            }
            z = true;
            this.lock.unlock();
            return z;
        } catch (Throwable th) {
            this.lock.unlock();
        }
    }

    public boolean removeAll(Collection<?> collection) {
        try {
            this.lock.lock();
            boolean removeAll = super.removeAll(collection) | this.blockedQueue.removeAll(collection);
            return removeAll;
        } finally {
            this.lock.unlock();
        }
    }

    E performOperation(int operation, Long time, TimeUnit unit) throws InterruptedException {
        E value;
        switch (operation) {
            case 0:
                value = (Dependency) super.take();
                break;
            case 1:
                value = (Dependency) super.peek();
                break;
            case 2:
                value = (Dependency) super.poll();
                break;
            case 3:
                value = (Dependency) super.poll(time.longValue(), unit);
                break;
            default:
                return null;
        }
        return value;
    }

    boolean offerBlockedResult(int operation, E result) {
        try {
            this.lock.lock();
            if (operation == 1) {
                super.remove(result);
            }
            boolean offer = this.blockedQueue.offer(result);
            return offer;
        } finally {
            this.lock.unlock();
        }
    }

    E get(int operation, Long time, TimeUnit unit) throws InterruptedException {
        E result;
        while (true) {
            E performOperation = performOperation(operation, time, unit);
            result = performOperation;
            if (performOperation == null) {
                break;
            } else if (canProcess(result)) {
                break;
            } else {
                offerBlockedResult(operation, result);
            }
        }
        return result;
    }

    boolean canProcess(E result) {
        return result.areDependenciesMet();
    }

    public void recycleBlockedQueue() {
        try {
            this.lock.lock();
            Iterator<E> iterator = this.blockedQueue.iterator();
            while (iterator.hasNext()) {
                Dependency blockedItem = (Dependency) iterator.next();
                if (canProcess(blockedItem)) {
                    super.offer(blockedItem);
                    iterator.remove();
                }
            }
        } finally {
            this.lock.unlock();
        }
    }

    <T> T[] concatenate(T[] arr1, T[] arr2) {
        int arr1Len = arr1.length;
        int arr2Len = arr2.length;
        Object[] C = (Object[]) Array.newInstance(arr1.getClass().getComponentType(), arr1Len + arr2Len);
        System.arraycopy(arr1, 0, C, 0, arr1Len);
        System.arraycopy(arr2, 0, C, arr1Len, arr2Len);
        return C;
    }
}
