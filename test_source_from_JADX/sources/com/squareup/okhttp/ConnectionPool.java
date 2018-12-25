package com.squareup.okhttp;

import com.squareup.okhttp.internal.Platform;
import com.squareup.okhttp.internal.Util;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public final class ConnectionPool {
    private static final long DEFAULT_KEEP_ALIVE_DURATION_MS = 300000;
    private static final ConnectionPool systemDefault;
    private final LinkedList<Connection> connections = new LinkedList();
    private final Runnable connectionsCleanupRunnable = new ConnectionPool$1(this);
    private Executor executor = new ThreadPoolExecutor(0, 1, 60, TimeUnit.SECONDS, new LinkedBlockingQueue(), Util.threadFactory("OkHttp ConnectionPool", true));
    private final long keepAliveDurationNs;
    private final int maxIdleConnections;

    static {
        String keepAlive = System.getProperty("http.keepAlive");
        String keepAliveDuration = System.getProperty("http.keepAliveDuration");
        String maxIdleConnections = System.getProperty("http.maxConnections");
        long keepAliveDurationMs = keepAliveDuration != null ? Long.parseLong(keepAliveDuration) : DEFAULT_KEEP_ALIVE_DURATION_MS;
        if (keepAlive != null && !Boolean.parseBoolean(keepAlive)) {
            systemDefault = new ConnectionPool(0, keepAliveDurationMs);
        } else if (maxIdleConnections != null) {
            systemDefault = new ConnectionPool(Integer.parseInt(maxIdleConnections), keepAliveDurationMs);
        } else {
            systemDefault = new ConnectionPool(5, keepAliveDurationMs);
        }
    }

    public ConnectionPool(int maxIdleConnections, long keepAliveDurationMs) {
        this.maxIdleConnections = maxIdleConnections;
        this.keepAliveDurationNs = (keepAliveDurationMs * 1000) * 1000;
    }

    public static ConnectionPool getDefault() {
        return systemDefault;
    }

    public synchronized int getConnectionCount() {
        return this.connections.size();
    }

    @Deprecated
    public synchronized int getSpdyConnectionCount() {
        return getMultiplexedConnectionCount();
    }

    public synchronized int getMultiplexedConnectionCount() {
        int total;
        total = 0;
        Iterator it = this.connections.iterator();
        while (it.hasNext()) {
            if (((Connection) it.next()).isSpdy()) {
                total++;
            }
        }
        return total;
    }

    public synchronized int getHttpConnectionCount() {
        return this.connections.size() - getMultiplexedConnectionCount();
    }

    public synchronized Connection get(Address address) {
        Connection foundConnection;
        foundConnection = null;
        ListIterator<Connection> i = this.connections.listIterator(this.connections.size());
        while (i.hasPrevious()) {
            Connection connection = (Connection) i.previous();
            if (connection.getRoute().getAddress().equals(address) && connection.isAlive()) {
                if (System.nanoTime() - connection.getIdleStartTimeNs() < this.keepAliveDurationNs) {
                    i.remove();
                    if (!connection.isSpdy()) {
                        try {
                            Platform.get().tagSocket(connection.getSocket());
                        } catch (SocketException e) {
                            Util.closeQuietly(connection.getSocket());
                            Platform platform = Platform.get();
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("Unable to tagSocket(): ");
                            stringBuilder.append(e);
                            platform.logW(stringBuilder.toString());
                        }
                    }
                    foundConnection = connection;
                    break;
                }
            }
        }
        if (foundConnection != null && foundConnection.isSpdy()) {
            this.connections.addFirst(foundConnection);
        }
        return foundConnection;
    }

    void recycle(Connection connection) {
        if (connection.isSpdy() || !connection.clearOwner()) {
            return;
        }
        if (connection.isAlive()) {
            try {
                Platform.get().untagSocket(connection.getSocket());
                synchronized (this) {
                    addConnection(connection);
                    connection.incrementRecycleCount();
                    connection.resetIdleStartTime();
                }
                return;
            } catch (SocketException e) {
                Platform platform = Platform.get();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unable to untagSocket(): ");
                stringBuilder.append(e);
                platform.logW(stringBuilder.toString());
                Util.closeQuietly(connection.getSocket());
                return;
            }
        }
        Util.closeQuietly(connection.getSocket());
    }

    private void addConnection(Connection connection) {
        boolean empty = this.connections.isEmpty();
        this.connections.addFirst(connection);
        if (empty) {
            this.executor.execute(this.connectionsCleanupRunnable);
        } else {
            notifyAll();
        }
    }

    void share(Connection connection) {
        if (!connection.isSpdy()) {
            throw new IllegalArgumentException();
        } else if (connection.isAlive()) {
            synchronized (this) {
                addConnection(connection);
            }
        }
    }

    public void evictAll() {
        ArrayList toEvict;
        synchronized (this) {
            toEvict = new ArrayList(this.connections);
            this.connections.clear();
            notifyAll();
        }
        int size = toEvict.size();
        for (int i = 0; i < size; i++) {
            Util.closeQuietly(((Connection) toEvict.get(i)).getSocket());
        }
    }

    private void runCleanupUntilPoolIsEmpty() {
        do {
        } while (performCleanup());
    }

    boolean performCleanup() {
        int size;
        synchronized (this) {
            int idleConnectionCount;
            if (this.connections.isEmpty()) {
                return false;
            }
            evictableConnections = new ArrayList();
            idleConnectionCount = 0;
            long now = System.nanoTime();
            long nanosUntilNextEviction = this.keepAliveDurationNs;
            ListIterator<Connection> i = this.connections.listIterator(this.connections.size());
            while (i.hasPrevious()) {
                Connection connection = (Connection) i.previous();
                long nanosUntilEviction = (connection.getIdleStartTimeNs() + this.keepAliveDurationNs) - now;
                if (nanosUntilEviction > 0) {
                    if (connection.isAlive()) {
                        if (connection.isIdle()) {
                            idleConnectionCount++;
                            nanosUntilNextEviction = Math.min(nanosUntilNextEviction, nanosUntilEviction);
                        }
                    }
                }
                i.remove();
                evictableConnections.add(connection);
            }
            i = this.connections.listIterator(this.connections.size());
            while (i.hasPrevious() && idleConnectionCount > this.maxIdleConnections) {
                connection = (Connection) i.previous();
                if (connection.isIdle()) {
                    evictableConnections.add(connection);
                    i.remove();
                    idleConnectionCount--;
                }
            }
            if (evictableConnections.isEmpty()) {
                try {
                    long millisUntilNextEviction = nanosUntilNextEviction / 1000000;
                    wait(millisUntilNextEviction, (int) (nanosUntilNextEviction - (1000000 * millisUntilNextEviction)));
                    return true;
                } catch (InterruptedException e) {
                    size = evictableConnections.size();
                    for (idleConnectionCount = 0; idleConnectionCount < size; idleConnectionCount++) {
                        ArrayList evictableConnections;
                        Util.closeQuietly(((Connection) evictableConnections.get(idleConnectionCount)).getSocket());
                    }
                    return true;
                }
            }
        }
    }

    void replaceCleanupExecutorForTests(Executor cleanupExecutor) {
        this.executor = cleanupExecutor;
    }

    synchronized List<Connection> getConnections() {
        return new ArrayList(this.connections);
    }
}
