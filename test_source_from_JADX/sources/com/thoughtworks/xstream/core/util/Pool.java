package com.thoughtworks.xstream.core.util;

public class Pool {
    private final Factory factory;
    private final int initialPoolSize;
    private final int maxPoolSize;
    private transient Object mutex = new Object();
    private transient int nextAvailable;
    private transient Object[] pool;

    public interface Factory {
        Object newInstance();
    }

    public Pool(int initialPoolSize, int maxPoolSize, Factory factory) {
        this.initialPoolSize = initialPoolSize;
        this.maxPoolSize = maxPoolSize;
        this.factory = factory;
    }

    public Object fetchFromPool() {
        Object result;
        synchronized (this.mutex) {
            if (this.pool == null) {
                this.pool = new Object[this.maxPoolSize];
                this.nextAvailable = this.initialPoolSize;
                while (this.nextAvailable > 0) {
                    putInPool(this.factory.newInstance());
                }
            }
            while (this.nextAvailable == this.maxPoolSize) {
                try {
                    this.mutex.wait();
                } catch (InterruptedException e) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Interrupted whilst waiting for a free item in the pool : ");
                    stringBuilder.append(e.getMessage());
                    throw new RuntimeException(stringBuilder.toString());
                }
            }
            Object[] objArr = this.pool;
            int i = this.nextAvailable;
            this.nextAvailable = i + 1;
            result = objArr[i];
            if (result == null) {
                result = this.factory.newInstance();
                putInPool(result);
                this.nextAvailable++;
            }
        }
        return result;
    }

    protected void putInPool(Object object) {
        synchronized (this.mutex) {
            Object[] objArr = this.pool;
            int i = this.nextAvailable - 1;
            this.nextAvailable = i;
            objArr[i] = object;
            this.mutex.notify();
        }
    }

    private Object readResolve() {
        this.mutex = new Object();
        return this;
    }
}
