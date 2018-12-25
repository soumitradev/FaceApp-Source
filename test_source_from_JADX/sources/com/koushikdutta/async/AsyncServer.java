package com.koushikdutta.async;

import android.os.Build.VERSION;
import android.os.Handler;
import android.util.Log;
import com.facebook.internal.ServerProtocol;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.callback.ConnectCallback;
import com.koushikdutta.async.callback.ListenCallback;
import com.koushikdutta.async.future.Cancellable;
import com.koushikdutta.async.future.Future;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.async.future.SimpleFuture;
import com.koushikdutta.async.future.TransformFuture;
import com.koushikdutta.async.util.StreamUtility;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class AsyncServer {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final String LOGTAG = "NIO";
    private static final long QUEUE_EMPTY = Long.MAX_VALUE;
    private static final Comparator<InetAddress> ipSorter = new C06358();
    static AsyncServer mInstance = new AsyncServer();
    static final WeakHashMap<Thread, AsyncServer> mServers = new WeakHashMap();
    private static ExecutorService synchronousResolverWorkers = newSynchronousWorkers("AsyncServer-resolver-");
    private static ExecutorService synchronousWorkers = newSynchronousWorkers("AsyncServer-worker-");
    Thread mAffinity;
    String mName;
    PriorityQueue<Scheduled> mQueue;
    private SelectorWrapper mSelector;
    int postCounter;

    /* renamed from: com.koushikdutta.async.AsyncServer$8 */
    static class C06358 implements Comparator<InetAddress> {
        C06358() {
        }

        public int compare(InetAddress lhs, InetAddress rhs) {
            if ((lhs instanceof Inet4Address) && (rhs instanceof Inet4Address)) {
                return 0;
            }
            if ((lhs instanceof Inet6Address) && (rhs instanceof Inet6Address)) {
                return 0;
            }
            if ((lhs instanceof Inet4Address) && (rhs instanceof Inet6Address)) {
                return -1;
            }
            return 1;
        }
    }

    private static class AsyncSelectorException extends IOException {
        public AsyncSelectorException(Exception e) {
            super(e);
        }
    }

    private static class NamedThreadFactory implements ThreadFactory {
        private final ThreadGroup group;
        private final String namePrefix;
        private final AtomicInteger threadNumber = new AtomicInteger(1);

        NamedThreadFactory(String namePrefix) {
            ThreadGroup threadGroup;
            SecurityManager s = System.getSecurityManager();
            if (s != null) {
                threadGroup = s.getThreadGroup();
            } else {
                threadGroup = Thread.currentThread().getThreadGroup();
            }
            this.group = threadGroup;
            this.namePrefix = namePrefix;
        }

        public Thread newThread(Runnable r) {
            ThreadGroup threadGroup = this.group;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.namePrefix);
            stringBuilder.append(this.threadNumber.getAndIncrement());
            Thread t = new Thread(threadGroup, r, stringBuilder.toString(), 0);
            if (t.isDaemon()) {
                t.setDaemon(false);
            }
            if (t.getPriority() != 5) {
                t.setPriority(5);
            }
            return t;
        }
    }

    private static class ObjectHolder<T> {
        T held;

        private ObjectHolder() {
        }
    }

    private static class RunnableWrapper implements Runnable {
        Handler handler;
        boolean hasRun;
        Runnable runnable;
        ThreadQueue threadQueue;

        private RunnableWrapper() {
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
            r3 = this;
            monitor-enter(r3);
            r0 = r3.hasRun;	 Catch:{ all -> 0x0035 }
            if (r0 == 0) goto L_0x0007;
        L_0x0005:
            monitor-exit(r3);	 Catch:{ all -> 0x0035 }
            return;
        L_0x0007:
            r0 = 1;
            r3.hasRun = r0;	 Catch:{ all -> 0x0035 }
            monitor-exit(r3);	 Catch:{ all -> 0x0035 }
            r0 = 0;
            r1 = r3.runnable;	 Catch:{ all -> 0x0023 }
            r1.run();	 Catch:{ all -> 0x0023 }
            r1 = r3.threadQueue;
            r1.remove(r3);
            r1 = r3.handler;
            r1.removeCallbacks(r3);
            r3.threadQueue = r0;
            r3.handler = r0;
            r3.runnable = r0;
            return;
        L_0x0023:
            r1 = move-exception;
            r2 = r3.threadQueue;
            r2.remove(r3);
            r2 = r3.handler;
            r2.removeCallbacks(r3);
            r3.threadQueue = r0;
            r3.handler = r0;
            r3.runnable = r0;
            throw r1;
        L_0x0035:
            r0 = move-exception;
            monitor-exit(r3);	 Catch:{ all -> 0x0035 }
            throw r0;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.koushikdutta.async.AsyncServer.RunnableWrapper.run():void");
        }
    }

    private static class Scheduled {
        public Runnable runnable;
        public long time;

        public Scheduled(Runnable runnable, long time) {
            this.runnable = runnable;
            this.time = time;
        }
    }

    static class Scheduler implements Comparator<Scheduled> {
        public static Scheduler INSTANCE = new Scheduler();

        private Scheduler() {
        }

        public int compare(Scheduled s1, Scheduled s2) {
            if (s1.time == s2.time) {
                return 0;
            }
            if (s1.time > s2.time) {
                return 1;
            }
            return -1;
        }
    }

    private class ConnectFuture extends SimpleFuture<AsyncNetworkSocket> {
        ConnectCallback callback;
        SocketChannel socket;

        private ConnectFuture() {
        }

        protected void cancelCleanup() {
            super.cancelCleanup();
            try {
                if (this.socket != null) {
                    this.socket.close();
                }
            } catch (IOException e) {
            }
        }
    }

    private static void runLoop(com.koushikdutta.async.AsyncServer r17, com.koushikdutta.async.SelectorWrapper r18, java.util.PriorityQueue<com.koushikdutta.async.AsyncServer.Scheduled> r19) throws com.koushikdutta.async.AsyncServer.AsyncSelectorException {
        /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.fixSplitterBlock(BlockFinish.java:63)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:34)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:282)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:200)
	at jadx.api.JadxDecompiler$$Lambda$8/1115170891.run(Unknown Source)
*/
        /*
        r1 = r17;
        r2 = 1;
        r3 = r19;
        r4 = lockAndRunQueue(r1, r3);
        monitor-enter(r17);	 Catch:{ Exception -> 0x0151 }
        r6 = r18.selectNow();	 Catch:{ all -> 0x0146, Exception -> 0x014d }
        r7 = 9223372036854775807; // 0x7fffffffffffffff float:NaN double:NaN;	 Catch:{ all -> 0x0146, Exception -> 0x014d }
        if (r6 != 0) goto L_0x0025;	 Catch:{ all -> 0x0146, Exception -> 0x014d }
    L_0x0015:
        r9 = r18.keys();	 Catch:{ all -> 0x0146, Exception -> 0x014d }
        r9 = r9.size();	 Catch:{ all -> 0x0146, Exception -> 0x014d }
        if (r9 != 0) goto L_0x0026;	 Catch:{ all -> 0x0146, Exception -> 0x014d }
    L_0x001f:
        r9 = (r4 > r7 ? 1 : (r4 == r7 ? 0 : -1));	 Catch:{ all -> 0x0146, Exception -> 0x014d }
        if (r9 != 0) goto L_0x0026;	 Catch:{ all -> 0x0146, Exception -> 0x014d }
    L_0x0023:
        monitor-exit(r17);	 Catch:{ all -> 0x0146, Exception -> 0x014d }
        return;	 Catch:{ all -> 0x0146, Exception -> 0x014d }
    L_0x0025:
        r2 = 0;	 Catch:{ all -> 0x0146, Exception -> 0x014d }
    L_0x0026:
        monitor-exit(r17);	 Catch:{ all -> 0x0146, Exception -> 0x014d }
        if (r2 == 0) goto L_0x003a;
    L_0x0029:
        r6 = (r4 > r7 ? 1 : (r4 == r7 ? 0 : -1));
        if (r6 != 0) goto L_0x0031;
    L_0x002d:
        r18.select();	 Catch:{ Exception -> 0x0151 }
        goto L_0x003a;
    L_0x0031:
        r6 = r18;
        r6.select(r4);	 Catch:{ Exception -> 0x0037 }
        goto L_0x003c;
    L_0x0037:
        r0 = move-exception;
        goto L_0x0154;
    L_0x003a:
        r6 = r18;
    L_0x003c:
        r7 = r18.selectedKeys();
        r8 = r7.iterator();
    L_0x0044:
        r9 = r8.hasNext();
        if (r9 == 0) goto L_0x0142;
    L_0x004a:
        r9 = r8.next();
        r9 = (java.nio.channels.SelectionKey) r9;
        r10 = r9.isAcceptable();	 Catch:{ Exception -> 0x010d, CancelledKeyException -> 0x013f }
        r11 = 0;	 Catch:{ Exception -> 0x010d, CancelledKeyException -> 0x013f }
        r12 = 0;	 Catch:{ Exception -> 0x010d, CancelledKeyException -> 0x013f }
        r13 = 1;	 Catch:{ Exception -> 0x010d, CancelledKeyException -> 0x013f }
        if (r10 == 0) goto L_0x00a8;	 Catch:{ Exception -> 0x010d, CancelledKeyException -> 0x013f }
    L_0x0059:
        r10 = r9.channel();	 Catch:{ Exception -> 0x010d, CancelledKeyException -> 0x013f }
        r10 = (java.nio.channels.ServerSocketChannel) r10;	 Catch:{ Exception -> 0x010d, CancelledKeyException -> 0x013f }
        r14 = 0;
        r15 = r10.accept();	 Catch:{ IOException -> 0x0097 }
        r14 = r15;	 Catch:{ IOException -> 0x0097 }
        if (r14 != 0) goto L_0x0069;	 Catch:{ IOException -> 0x0097 }
    L_0x0068:
        goto L_0x0044;	 Catch:{ IOException -> 0x0097 }
    L_0x0069:
        r14.configureBlocking(r11);	 Catch:{ IOException -> 0x0097 }
        r15 = r18.getSelector();	 Catch:{ IOException -> 0x0097 }
        r15 = r14.register(r15, r13);	 Catch:{ IOException -> 0x0097 }
        r12 = r15;	 Catch:{ IOException -> 0x0097 }
        r15 = r9.attachment();	 Catch:{ IOException -> 0x0097 }
        r15 = (com.koushikdutta.async.callback.ListenCallback) r15;	 Catch:{ IOException -> 0x0097 }
        r11 = new com.koushikdutta.async.AsyncNetworkSocket;	 Catch:{ IOException -> 0x0097 }
        r11.<init>();	 Catch:{ IOException -> 0x0097 }
        r13 = r14.socket();	 Catch:{ IOException -> 0x0097 }
        r13 = r13.getRemoteSocketAddress();	 Catch:{ IOException -> 0x0097 }
        r13 = (java.net.InetSocketAddress) r13;	 Catch:{ IOException -> 0x0097 }
        r11.attach(r14, r13);	 Catch:{ IOException -> 0x0097 }
        r11.setup(r1, r12);	 Catch:{ IOException -> 0x0097 }
        r12.attach(r11);	 Catch:{ IOException -> 0x0097 }
        r15.onAccepted(r11);	 Catch:{ IOException -> 0x0097 }
        goto L_0x00a7;
    L_0x0097:
        r0 = move-exception;
        r11 = r0;
        r13 = 1;
        r13 = new java.io.Closeable[r13];	 Catch:{ Exception -> 0x010d, CancelledKeyException -> 0x013f }
        r15 = 0;	 Catch:{ Exception -> 0x010d, CancelledKeyException -> 0x013f }
        r13[r15] = r14;	 Catch:{ Exception -> 0x010d, CancelledKeyException -> 0x013f }
        com.koushikdutta.async.util.StreamUtility.closeQuietly(r13);	 Catch:{ Exception -> 0x010d, CancelledKeyException -> 0x013f }
        if (r12 == 0) goto L_0x00a7;	 Catch:{ Exception -> 0x010d, CancelledKeyException -> 0x013f }
    L_0x00a4:
        r12.cancel();	 Catch:{ Exception -> 0x010d, CancelledKeyException -> 0x013f }
    L_0x00a7:
        goto L_0x010c;	 Catch:{ Exception -> 0x010d, CancelledKeyException -> 0x013f }
    L_0x00a8:
        r10 = r9.isReadable();	 Catch:{ Exception -> 0x010d, CancelledKeyException -> 0x013f }
        if (r10 == 0) goto L_0x00bc;	 Catch:{ Exception -> 0x010d, CancelledKeyException -> 0x013f }
    L_0x00ae:
        r10 = r9.attachment();	 Catch:{ Exception -> 0x010d, CancelledKeyException -> 0x013f }
        r10 = (com.koushikdutta.async.AsyncNetworkSocket) r10;	 Catch:{ Exception -> 0x010d, CancelledKeyException -> 0x013f }
        r11 = r10.onReadable();	 Catch:{ Exception -> 0x010d, CancelledKeyException -> 0x013f }
        r1.onDataReceived(r11);	 Catch:{ Exception -> 0x010d, CancelledKeyException -> 0x013f }
        goto L_0x010c;	 Catch:{ Exception -> 0x010d, CancelledKeyException -> 0x013f }
    L_0x00bc:
        r10 = r9.isWritable();	 Catch:{ Exception -> 0x010d, CancelledKeyException -> 0x013f }
        if (r10 == 0) goto L_0x00cc;	 Catch:{ Exception -> 0x010d, CancelledKeyException -> 0x013f }
    L_0x00c2:
        r10 = r9.attachment();	 Catch:{ Exception -> 0x010d, CancelledKeyException -> 0x013f }
        r10 = (com.koushikdutta.async.AsyncNetworkSocket) r10;	 Catch:{ Exception -> 0x010d, CancelledKeyException -> 0x013f }
        r10.onDataWritable();	 Catch:{ Exception -> 0x010d, CancelledKeyException -> 0x013f }
        goto L_0x010c;	 Catch:{ Exception -> 0x010d, CancelledKeyException -> 0x013f }
    L_0x00cc:
        r10 = r9.isConnectable();	 Catch:{ Exception -> 0x010d, CancelledKeyException -> 0x013f }
        if (r10 == 0) goto L_0x0130;	 Catch:{ Exception -> 0x010d, CancelledKeyException -> 0x013f }
    L_0x00d2:
        r10 = r9.attachment();	 Catch:{ Exception -> 0x010d, CancelledKeyException -> 0x013f }
        r10 = (com.koushikdutta.async.AsyncServer.ConnectFuture) r10;	 Catch:{ Exception -> 0x010d, CancelledKeyException -> 0x013f }
        r11 = r9.channel();	 Catch:{ Exception -> 0x010d, CancelledKeyException -> 0x013f }
        r11 = (java.nio.channels.SocketChannel) r11;	 Catch:{ Exception -> 0x010d, CancelledKeyException -> 0x013f }
        r13 = 1;	 Catch:{ Exception -> 0x010d, CancelledKeyException -> 0x013f }
        r9.interestOps(r13);	 Catch:{ Exception -> 0x010d, CancelledKeyException -> 0x013f }
        r11.finishConnect();	 Catch:{ IOException -> 0x0115 }
        r13 = new com.koushikdutta.async.AsyncNetworkSocket;	 Catch:{ IOException -> 0x0115 }
        r13.<init>();	 Catch:{ IOException -> 0x0115 }
        r13.setup(r1, r9);	 Catch:{ IOException -> 0x0115 }
        r14 = r11.socket();	 Catch:{ IOException -> 0x0115 }
        r14 = r14.getRemoteSocketAddress();	 Catch:{ IOException -> 0x0115 }
        r14 = (java.net.InetSocketAddress) r14;	 Catch:{ IOException -> 0x0115 }
        r13.attach(r11, r14);	 Catch:{ IOException -> 0x0115 }
        r9.attach(r13);	 Catch:{ IOException -> 0x0115 }
        r14 = r10.setComplete(r13);	 Catch:{ Exception -> 0x010d, CancelledKeyException -> 0x013f }
        if (r14 == 0) goto L_0x010a;	 Catch:{ Exception -> 0x010d, CancelledKeyException -> 0x013f }
    L_0x0105:
        r14 = r10.callback;	 Catch:{ Exception -> 0x010d, CancelledKeyException -> 0x013f }
        r14.onConnectCompleted(r12, r13);	 Catch:{ Exception -> 0x010d, CancelledKeyException -> 0x013f }
    L_0x010c:
        goto L_0x0140;
    L_0x010d:
        r0 = move-exception;
        r12 = r0;
        r14 = new java.lang.RuntimeException;	 Catch:{ Exception -> 0x010d, CancelledKeyException -> 0x013f }
        r14.<init>(r12);	 Catch:{ Exception -> 0x010d, CancelledKeyException -> 0x013f }
        throw r14;	 Catch:{ Exception -> 0x010d, CancelledKeyException -> 0x013f }
    L_0x0115:
        r0 = move-exception;	 Catch:{ Exception -> 0x010d, CancelledKeyException -> 0x013f }
        r13 = r0;	 Catch:{ Exception -> 0x010d, CancelledKeyException -> 0x013f }
        r9.cancel();	 Catch:{ Exception -> 0x010d, CancelledKeyException -> 0x013f }
        r14 = 1;	 Catch:{ Exception -> 0x010d, CancelledKeyException -> 0x013f }
        r14 = new java.io.Closeable[r14];	 Catch:{ Exception -> 0x010d, CancelledKeyException -> 0x013f }
        r15 = 0;	 Catch:{ Exception -> 0x010d, CancelledKeyException -> 0x013f }
        r14[r15] = r11;	 Catch:{ Exception -> 0x010d, CancelledKeyException -> 0x013f }
        com.koushikdutta.async.util.StreamUtility.closeQuietly(r14);	 Catch:{ Exception -> 0x010d, CancelledKeyException -> 0x013f }
        r14 = r10.setComplete(r13);	 Catch:{ Exception -> 0x010d, CancelledKeyException -> 0x013f }
        if (r14 == 0) goto L_0x012e;	 Catch:{ Exception -> 0x010d, CancelledKeyException -> 0x013f }
    L_0x0129:
        r14 = r10.callback;	 Catch:{ Exception -> 0x010d, CancelledKeyException -> 0x013f }
        r14.onConnectCompleted(r13, r12);	 Catch:{ Exception -> 0x010d, CancelledKeyException -> 0x013f }
    L_0x012e:
        goto L_0x0044;	 Catch:{ Exception -> 0x010d, CancelledKeyException -> 0x013f }
    L_0x0130:
        r10 = "NIO";	 Catch:{ Exception -> 0x010d, CancelledKeyException -> 0x013f }
        r11 = "wtf";	 Catch:{ Exception -> 0x010d, CancelledKeyException -> 0x013f }
        android.util.Log.i(r10, r11);	 Catch:{ Exception -> 0x010d, CancelledKeyException -> 0x013f }
        r10 = new java.lang.RuntimeException;	 Catch:{ Exception -> 0x010d, CancelledKeyException -> 0x013f }
        r11 = "Unknown key state.";	 Catch:{ Exception -> 0x010d, CancelledKeyException -> 0x013f }
        r10.<init>(r11);	 Catch:{ Exception -> 0x010d, CancelledKeyException -> 0x013f }
        throw r10;	 Catch:{ Exception -> 0x010d, CancelledKeyException -> 0x013f }
    L_0x013f:
        r0 = move-exception;
    L_0x0140:
        goto L_0x0044;
    L_0x0142:
        r7.clear();
        return;
    L_0x0146:
        r0 = move-exception;
        r6 = r18;
        r7 = r2;
    L_0x014a:
        r2 = r0;
        monitor-exit(r17);	 Catch:{ all -> 0x014f }
        throw r2;	 Catch:{ all -> 0x0146, Exception -> 0x014d }
    L_0x014d:
        r0 = move-exception;
        goto L_0x0155;
    L_0x014f:
        r0 = move-exception;
        goto L_0x014a;
    L_0x0151:
        r0 = move-exception;
        r6 = r18;
    L_0x0154:
        r7 = r2;
    L_0x0155:
        r2 = r0;
        r8 = new com.koushikdutta.async.AsyncServer$AsyncSelectorException;
        r8.<init>(r2);
        throw r8;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.koushikdutta.async.AsyncServer.runLoop(com.koushikdutta.async.AsyncServer, com.koushikdutta.async.SelectorWrapper, java.util.PriorityQueue):void");
    }

    static {
        try {
            if (VERSION.SDK_INT <= 8) {
                System.setProperty("java.net.preferIPv4Stack", ServerProtocol.DIALOG_RETURN_SCOPES_TRUE);
                System.setProperty("java.net.preferIPv6Addresses", "false");
            }
        } catch (Throwable th) {
        }
    }

    public static void post(Handler handler, Runnable runnable) {
        Runnable wrapper = new RunnableWrapper();
        ThreadQueue threadQueue = ThreadQueue.getOrCreateThreadQueue(handler.getLooper().getThread());
        wrapper.threadQueue = threadQueue;
        wrapper.handler = handler;
        wrapper.runnable = runnable;
        threadQueue.add(wrapper);
        handler.post(wrapper);
        threadQueue.queueSemaphore.release();
    }

    public static AsyncServer getDefault() {
        return mInstance;
    }

    public boolean isRunning() {
        return this.mSelector != null;
    }

    public AsyncServer() {
        this(null);
    }

    public AsyncServer(String name) {
        this.postCounter = 0;
        this.mQueue = new PriorityQueue(1, Scheduler.INSTANCE);
        if (name == null) {
            name = "AsyncServer";
        }
        this.mName = name;
    }

    private void handleSocket(AsyncNetworkSocket handler) throws ClosedChannelException {
        SelectionKey ckey = handler.getChannel().register(this.mSelector.getSelector());
        ckey.attach(handler);
        handler.setup(this, ckey);
    }

    public void removeAllCallbacks(Object scheduled) {
        synchronized (this) {
            this.mQueue.remove(scheduled);
        }
    }

    private static void wakeup(final SelectorWrapper selector) {
        synchronousWorkers.execute(new Runnable() {
            public void run() {
                try {
                    selector.wakeupOnce();
                } catch (Exception e) {
                    Log.i(AsyncServer.LOGTAG, "Selector Exception? L Preview?");
                }
            }
        });
    }

    public Object postDelayed(Runnable runnable, long delay) {
        Scheduled s;
        synchronized (this) {
            long time = 0;
            if (delay > 0) {
                time = System.currentTimeMillis() + delay;
            } else if (delay == 0) {
                time = this.postCounter;
                this.postCounter = time + 1;
                time = (long) time;
            } else if (this.mQueue.size() > 0) {
                time = Math.min(0, ((Scheduled) this.mQueue.peek()).time - 1);
            }
            PriorityQueue priorityQueue = this.mQueue;
            Scheduled scheduled = new Scheduled(runnable, time);
            s = scheduled;
            priorityQueue.add(scheduled);
            if (this.mSelector == null) {
                run(true);
            }
            if (!isAffinityThread()) {
                wakeup(this.mSelector);
            }
        }
        return s;
    }

    public Object postImmediate(Runnable runnable) {
        if (Thread.currentThread() != getAffinity()) {
            return postDelayed(runnable, -1);
        }
        runnable.run();
        return null;
    }

    public Object post(Runnable runnable) {
        return postDelayed(runnable, 0);
    }

    public Object post(final CompletedCallback callback, final Exception e) {
        return post(new Runnable() {
            public void run() {
                callback.onCompleted(e);
            }
        });
    }

    public void run(final Runnable runnable) {
        if (Thread.currentThread() == this.mAffinity) {
            post(runnable);
            lockAndRunQueue(this, this.mQueue);
            return;
        }
        final Semaphore semaphore = new Semaphore(0);
        post(new Runnable() {
            public void run() {
                runnable.run();
                semaphore.release();
            }
        });
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            Log.e(LOGTAG, "run", e);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void stop() {
        /*
        r8 = this;
        monitor-enter(r8);
        r0 = r8.isAffinityThread();	 Catch:{ all -> 0x0050 }
        r1 = r8.mSelector;	 Catch:{ all -> 0x0050 }
        if (r1 != 0) goto L_0x000b;
    L_0x0009:
        monitor-exit(r8);	 Catch:{ all -> 0x0050 }
        return;
    L_0x000b:
        r2 = mServers;	 Catch:{ all -> 0x0050 }
        monitor-enter(r2);	 Catch:{ all -> 0x0050 }
        r3 = mServers;	 Catch:{ all -> 0x004d }
        r4 = r8.mAffinity;	 Catch:{ all -> 0x004d }
        r3.remove(r4);	 Catch:{ all -> 0x004d }
        monitor-exit(r2);	 Catch:{ all -> 0x004d }
        r2 = new java.util.concurrent.Semaphore;	 Catch:{ all -> 0x0050 }
        r3 = 0;
        r2.<init>(r3);	 Catch:{ all -> 0x0050 }
        r3 = r8.mQueue;	 Catch:{ all -> 0x0050 }
        r4 = new com.koushikdutta.async.AsyncServer$Scheduled;	 Catch:{ all -> 0x0050 }
        r5 = new com.koushikdutta.async.AsyncServer$4;	 Catch:{ all -> 0x0050 }
        r5.<init>(r1, r2);	 Catch:{ all -> 0x0050 }
        r6 = 0;
        r4.<init>(r5, r6);	 Catch:{ all -> 0x0050 }
        r3.add(r4);	 Catch:{ all -> 0x0050 }
        r1.wakeupOnce();	 Catch:{ all -> 0x0050 }
        shutdownKeys(r1);	 Catch:{ all -> 0x0050 }
        r3 = new java.util.PriorityQueue;	 Catch:{ all -> 0x0050 }
        r4 = 1;
        r5 = com.koushikdutta.async.AsyncServer.Scheduler.INSTANCE;	 Catch:{ all -> 0x0050 }
        r3.<init>(r4, r5);	 Catch:{ all -> 0x0050 }
        r8.mQueue = r3;	 Catch:{ all -> 0x0050 }
        r3 = 0;
        r8.mSelector = r3;	 Catch:{ all -> 0x0050 }
        r8.mAffinity = r3;	 Catch:{ all -> 0x0050 }
        monitor-exit(r8);	 Catch:{ all -> 0x0050 }
        if (r0 != 0) goto L_0x004b;
    L_0x0045:
        r2.acquire();	 Catch:{ Exception -> 0x0049 }
        goto L_0x004b;
    L_0x0049:
        r3 = move-exception;
        goto L_0x004c;
    L_0x004c:
        return;
    L_0x004d:
        r3 = move-exception;
        monitor-exit(r2);	 Catch:{ all -> 0x004d }
        throw r3;	 Catch:{ all -> 0x0050 }
    L_0x0050:
        r0 = move-exception;
        monitor-exit(r8);	 Catch:{ all -> 0x0050 }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.koushikdutta.async.AsyncServer.stop():void");
    }

    protected void onDataReceived(int transmitted) {
    }

    protected void onDataSent(int transmitted) {
    }

    public AsyncServerSocket listen(InetAddress host, int port, ListenCallback handler) {
        ObjectHolder<AsyncServerSocket> holder = new ObjectHolder();
        final InetAddress inetAddress = host;
        final int i = port;
        final ListenCallback listenCallback = handler;
        final ObjectHolder<AsyncServerSocket> objectHolder = holder;
        run(new Runnable() {
            public void run() {
                try {
                    InetSocketAddress isa;
                    ServerSocketChannel closeableServer = ServerSocketChannel.open();
                    ServerSocketChannelWrapper closeableWrapper = new ServerSocketChannelWrapper(closeableServer);
                    final ServerSocketChannel server = closeableServer;
                    final ServerSocketChannelWrapper wrapper = closeableWrapper;
                    if (inetAddress == null) {
                        isa = new InetSocketAddress(i);
                    } else {
                        isa = new InetSocketAddress(inetAddress, i);
                    }
                    server.socket().bind(isa);
                    final SelectionKey key = wrapper.register(AsyncServer.this.mSelector.getSelector());
                    key.attach(listenCallback);
                    ListenCallback listenCallback = listenCallback;
                    ObjectHolder objectHolder = objectHolder;
                    C10851 c10851 = new AsyncServerSocket() {
                        public int getLocalPort() {
                            return server.socket().getLocalPort();
                        }

                        public void stop() {
                            StreamUtility.closeQuietly(wrapper);
                            try {
                                key.cancel();
                            } catch (Exception e) {
                            }
                        }
                    };
                    objectHolder.held = c10851;
                    listenCallback.onListening(c10851);
                } catch (IOException e) {
                    Log.e(AsyncServer.LOGTAG, "wtf", e);
                    StreamUtility.closeQuietly(null, null);
                    listenCallback.onCompleted(e);
                }
            }
        });
        return (AsyncServerSocket) holder.held;
    }

    private ConnectFuture connectResolvedInetSocketAddress(final InetSocketAddress address, final ConnectCallback callback) {
        final ConnectFuture cancel = new ConnectFuture();
        post(new Runnable() {
            public void run() {
                if (!cancel.isCancelled()) {
                    cancel.callback = callback;
                    SelectionKey ckey = null;
                    SocketChannel socket = null;
                    try {
                        ConnectFuture connectFuture = cancel;
                        SocketChannel open = SocketChannel.open();
                        connectFuture.socket = open;
                        socket = open;
                        socket.configureBlocking(false);
                        ckey = socket.register(AsyncServer.this.mSelector.getSelector(), 8);
                        ckey.attach(cancel);
                        socket.connect(address);
                    } catch (Throwable e) {
                        if (ckey != null) {
                            ckey.cancel();
                        }
                        StreamUtility.closeQuietly(socket);
                        cancel.setComplete(new RuntimeException(e));
                    }
                }
            }
        });
        return cancel;
    }

    public Cancellable connectSocket(final InetSocketAddress remote, final ConnectCallback callback) {
        if (!remote.isUnresolved()) {
            return connectResolvedInetSocketAddress(remote, callback);
        }
        final SimpleFuture<AsyncNetworkSocket> ret = new SimpleFuture();
        Cancellable lookup = getByName(remote.getHostName());
        ret.setParent(lookup);
        lookup.setCallback(new FutureCallback<InetAddress>() {
            public void onCompleted(Exception e, InetAddress result) {
                if (e != null) {
                    callback.onConnectCompleted(e, null);
                    ret.setComplete(e);
                    return;
                }
                ret.setComplete(AsyncServer.this.connectResolvedInetSocketAddress(new InetSocketAddress(result, remote.getPort()), callback));
            }
        });
        return ret;
    }

    public Cancellable connectSocket(String host, int port, ConnectCallback callback) {
        return connectSocket(InetSocketAddress.createUnresolved(host, port), callback);
    }

    private static ExecutorService newSynchronousWorkers(String prefix) {
        return new ThreadPoolExecutor(1, 4, 10, TimeUnit.SECONDS, new LinkedBlockingQueue(), new NamedThreadFactory(prefix));
    }

    public Future<InetAddress[]> getAllByName(final String host) {
        final SimpleFuture<InetAddress[]> ret = new SimpleFuture();
        synchronousResolverWorkers.execute(new Runnable() {
            public void run() {
                try {
                    final InetAddress[] result = InetAddress.getAllByName(host);
                    Arrays.sort(result, AsyncServer.ipSorter);
                    if (result != null) {
                        if (result.length != 0) {
                            AsyncServer.this.post(new Runnable() {
                                public void run() {
                                    ret.setComplete(null, result);
                                }
                            });
                            return;
                        }
                    }
                    throw new HostnameResolutionException("no addresses for host");
                } catch (final Exception e) {
                    AsyncServer.this.post(new Runnable() {
                        public void run() {
                            ret.setComplete(e, null);
                        }
                    });
                }
            }
        });
        return ret;
    }

    public Future<InetAddress> getByName(String host) {
        return (Future) getAllByName(host).then(new TransformFuture<InetAddress, InetAddress[]>() {
            protected void transform(InetAddress[] result) throws Exception {
                setComplete(result[0]);
            }
        });
    }

    public AsyncDatagramSocket connectDatagram(String host, int port) throws IOException {
        DatagramChannel socket = DatagramChannel.open();
        AsyncDatagramSocket handler = new AsyncDatagramSocket();
        handler.attach(socket);
        final String str = host;
        final int i = port;
        final AsyncDatagramSocket asyncDatagramSocket = handler;
        final DatagramChannel datagramChannel = socket;
        run(new Runnable() {
            public void run() {
                try {
                    SocketAddress remote = new InetSocketAddress(str, i);
                    AsyncServer.this.handleSocket(asyncDatagramSocket);
                    datagramChannel.connect(remote);
                } catch (IOException e) {
                    Log.e(AsyncServer.LOGTAG, "Datagram error", e);
                    StreamUtility.closeQuietly(datagramChannel);
                }
            }
        });
        return handler;
    }

    public AsyncDatagramSocket openDatagram() throws IOException {
        return openDatagram(null, false);
    }

    public AsyncDatagramSocket openDatagram(SocketAddress address, boolean reuseAddress) throws IOException {
        DatagramChannel socket = DatagramChannel.open();
        AsyncDatagramSocket handler = new AsyncDatagramSocket();
        handler.attach(socket);
        final boolean z = reuseAddress;
        final DatagramChannel datagramChannel = socket;
        final SocketAddress socketAddress = address;
        final AsyncDatagramSocket asyncDatagramSocket = handler;
        run(new Runnable() {
            public void run() {
                try {
                    if (z) {
                        datagramChannel.socket().setReuseAddress(z);
                    }
                    datagramChannel.socket().bind(socketAddress);
                    AsyncServer.this.handleSocket(asyncDatagramSocket);
                } catch (IOException e) {
                    Log.e(AsyncServer.LOGTAG, "Datagram error", e);
                    StreamUtility.closeQuietly(datagramChannel);
                }
            }
        });
        return handler;
    }

    public AsyncDatagramSocket connectDatagram(final SocketAddress remote) throws IOException {
        final DatagramChannel socket = DatagramChannel.open();
        final AsyncDatagramSocket handler = new AsyncDatagramSocket();
        handler.attach(socket);
        run(new Runnable() {
            public void run() {
                try {
                    AsyncServer.this.handleSocket(handler);
                    socket.connect(remote);
                } catch (IOException e) {
                    StreamUtility.closeQuietly(socket);
                }
            }
        });
        return handler;
    }

    private boolean addMe() {
        synchronized (mServers) {
            if (((AsyncServer) mServers.get(this.mAffinity)) != null) {
                return false;
            }
            mServers.put(this.mAffinity, this);
            return true;
        }
    }

    public static AsyncServer getCurrentThreadServer() {
        return (AsyncServer) mServers.get(Thread.currentThread());
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void run(boolean r7) {
        /*
        r6 = this;
        r0 = 0;
        monitor-enter(r6);
        r1 = r6.mSelector;	 Catch:{ all -> 0x0076 }
        if (r1 == 0) goto L_0x0014;
    L_0x0006:
        r1 = "NIO";
        r2 = "Reentrant call";
        android.util.Log.i(r1, r2);	 Catch:{ all -> 0x0076 }
        r0 = 1;
        r1 = r6.mSelector;	 Catch:{ all -> 0x0076 }
        r2 = r6.mQueue;	 Catch:{ all -> 0x0076 }
        goto L_0x0056;
    L_0x0014:
        r1 = new com.koushikdutta.async.SelectorWrapper;	 Catch:{ IOException -> 0x0073 }
        r2 = java.nio.channels.spi.SelectorProvider.provider();	 Catch:{ IOException -> 0x0073 }
        r2 = r2.openSelector();	 Catch:{ IOException -> 0x0073 }
        r1.<init>(r2);	 Catch:{ IOException -> 0x0073 }
        r6.mSelector = r1;	 Catch:{ IOException -> 0x0073 }
        r2 = r6.mQueue;	 Catch:{ IOException -> 0x0073 }
        if (r7 == 0) goto L_0x0033;
    L_0x0029:
        r3 = new com.koushikdutta.async.AsyncServer$14;	 Catch:{ all -> 0x0076 }
        r4 = r6.mName;	 Catch:{ all -> 0x0076 }
        r3.<init>(r4, r1, r2);	 Catch:{ all -> 0x0076 }
        r6.mAffinity = r3;	 Catch:{ all -> 0x0076 }
        goto L_0x0039;
    L_0x0033:
        r3 = java.lang.Thread.currentThread();	 Catch:{ all -> 0x0076 }
        r6.mAffinity = r3;	 Catch:{ all -> 0x0076 }
    L_0x0039:
        r3 = r6.addMe();	 Catch:{ all -> 0x0076 }
        if (r3 != 0) goto L_0x004d;
    L_0x003f:
        r3 = r6.mSelector;	 Catch:{ Exception -> 0x0045 }
        r3.close();	 Catch:{ Exception -> 0x0045 }
        goto L_0x0046;
    L_0x0045:
        r3 = move-exception;
    L_0x0046:
        r3 = 0;
        r6.mSelector = r3;	 Catch:{ all -> 0x0076 }
        r6.mAffinity = r3;	 Catch:{ all -> 0x0076 }
        monitor-exit(r6);	 Catch:{ all -> 0x0076 }
        return;
    L_0x004d:
        if (r7 == 0) goto L_0x0056;
    L_0x004f:
        r3 = r6.mAffinity;	 Catch:{ all -> 0x0076 }
        r3.start();	 Catch:{ all -> 0x0076 }
        monitor-exit(r6);	 Catch:{ all -> 0x0076 }
        return;
    L_0x0056:
        monitor-exit(r6);	 Catch:{ all -> 0x0076 }
        if (r0 == 0) goto L_0x006f;
    L_0x0059:
        runLoop(r6, r1, r2);	 Catch:{ AsyncSelectorException -> 0x005d }
        goto L_0x006e;
    L_0x005d:
        r3 = move-exception;
        r4 = "NIO";
        r5 = "Selector closed";
        android.util.Log.i(r4, r5, r3);
        r4 = r1.getSelector();	 Catch:{ Exception -> 0x006d }
        r4.close();	 Catch:{ Exception -> 0x006d }
        goto L_0x006e;
    L_0x006d:
        r4 = move-exception;
    L_0x006e:
        return;
    L_0x006f:
        run(r6, r1, r2);
        return;
    L_0x0073:
        r1 = move-exception;
        monitor-exit(r6);	 Catch:{ all -> 0x0076 }
        return;
    L_0x0076:
        r1 = move-exception;
        monitor-exit(r6);	 Catch:{ all -> 0x0076 }
        throw r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.koushikdutta.async.AsyncServer.run(boolean):void");
    }

    private static void run(AsyncServer server, SelectorWrapper selector, PriorityQueue<Scheduled> queue) {
        while (true) {
            try {
                runLoop(server, selector, queue);
            } catch (AsyncSelectorException e) {
                Log.i(LOGTAG, "Selector exception, shutting down", e);
                try {
                    selector.getSelector().close();
                } catch (Exception e2) {
                }
            }
            synchronized (server) {
                if (!selector.isOpen() || (selector.keys().size() <= 0 && queue.size() <= 0)) {
                    shutdownEverything(selector);
                }
            }
        }
        shutdownEverything(selector);
        if (server.mSelector == selector) {
            server.mQueue = new PriorityQueue(1, Scheduler.INSTANCE);
            server.mSelector = null;
            server.mAffinity = null;
        }
        synchronized (mServers) {
            mServers.remove(Thread.currentThread());
        }
    }

    private static void shutdownKeys(SelectorWrapper selector) {
        try {
            for (SelectionKey key : selector.keys()) {
                StreamUtility.closeQuietly(key.channel());
                try {
                    ((SelectionKey) r0.next()).cancel();
                } catch (Exception e) {
                }
            }
        } catch (Exception e2) {
        }
    }

    private static void shutdownEverything(SelectorWrapper selector) {
        shutdownKeys(selector);
        try {
            selector.close();
        } catch (Exception e) {
        }
    }

    private static long lockAndRunQueue(AsyncServer server, PriorityQueue<Scheduled> queue) {
        long wait = Long.MAX_VALUE;
        while (true) {
            Scheduled run = null;
            synchronized (server) {
                long now = System.currentTimeMillis();
                if (queue.size() > 0) {
                    Scheduled s = (Scheduled) queue.remove();
                    if (s.time <= now) {
                        run = s;
                    } else {
                        wait = s.time - now;
                        queue.add(s);
                    }
                }
            }
            if (run == null) {
                server.postCounter = 0;
                return wait;
            }
            run.runnable.run();
        }
    }

    public void dump() {
        post(new Runnable() {
            public void run() {
                if (AsyncServer.this.mSelector == null) {
                    Log.i(AsyncServer.LOGTAG, "Server dump not possible. No selector?");
                    return;
                }
                String str = AsyncServer.LOGTAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Key Count: ");
                stringBuilder.append(AsyncServer.this.mSelector.keys().size());
                Log.i(str, stringBuilder.toString());
                for (SelectionKey key : AsyncServer.this.mSelector.keys()) {
                    String str2 = AsyncServer.LOGTAG;
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("Key: ");
                    stringBuilder2.append(key);
                    Log.i(str2, stringBuilder2.toString());
                }
            }
        });
    }

    public Thread getAffinity() {
        return this.mAffinity;
    }

    public boolean isAffinityThread() {
        return this.mAffinity == Thread.currentThread();
    }

    public boolean isAffinityThreadOrStopped() {
        Thread affinity = this.mAffinity;
        if (affinity != null) {
            if (affinity != Thread.currentThread()) {
                return false;
            }
        }
        return true;
    }
}
