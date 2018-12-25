package com.squareup.okhttp.internal.spdy;

import android.support.v4.internal.view.SupportMenu;
import com.squareup.okhttp.Protocol;
import com.squareup.okhttp.internal.NamedRunnable;
import com.squareup.okhttp.internal.Util;
import com.squareup.okhttp.internal.spdy.FrameReader.Handler;
import java.io.Closeable;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import okio.Buffer;
import okio.BufferedSource;
import okio.ByteString;
import okio.Okio;

public final class SpdyConnection implements Closeable {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int OKHTTP_CLIENT_WINDOW_SIZE = 16777216;
    private static final ExecutorService executor = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS, new SynchronousQueue(), Util.threadFactory("OkHttp SpdyConnection", true));
    long bytesLeftInWriteWindow;
    final boolean client;
    private final Set<Integer> currentPushRequests;
    final FrameWriter frameWriter;
    private final IncomingStreamHandler handler;
    private final String hostName;
    private long idleStartTimeNs;
    private int lastGoodStreamId;
    private int nextPingId;
    private int nextStreamId;
    final Settings okHttpSettings;
    final Settings peerSettings;
    private Map<Integer, Ping> pings;
    final Protocol protocol;
    private final ExecutorService pushExecutor;
    private final PushObserver pushObserver;
    final Reader readerRunnable;
    private boolean receivedInitialPeerSettings;
    private boolean shutdown;
    final Socket socket;
    private final Map<Integer, SpdyStream> streams;
    long unacknowledgedBytesRead;
    final Variant variant;

    public static class Builder {
        private boolean client;
        private IncomingStreamHandler handler;
        private String hostName;
        private Protocol protocol;
        private PushObserver pushObserver;
        private Socket socket;

        public Builder(boolean client, Socket socket) throws IOException {
            this(((InetSocketAddress) socket.getRemoteSocketAddress()).getHostName(), client, socket);
        }

        public Builder(String hostName, boolean client, Socket socket) throws IOException {
            this.handler = IncomingStreamHandler.REFUSE_INCOMING_STREAMS;
            this.protocol = Protocol.SPDY_3;
            this.pushObserver = PushObserver.CANCEL;
            this.hostName = hostName;
            this.client = client;
            this.socket = socket;
        }

        public Builder handler(IncomingStreamHandler handler) {
            this.handler = handler;
            return this;
        }

        public Builder protocol(Protocol protocol) {
            this.protocol = protocol;
            return this;
        }

        public Builder pushObserver(PushObserver pushObserver) {
            this.pushObserver = pushObserver;
            return this;
        }

        public SpdyConnection build() throws IOException {
            return new SpdyConnection();
        }
    }

    class Reader extends NamedRunnable implements Handler {
        FrameReader frameReader;

        private Reader() {
            super("OkHttp %s", this$0.hostName);
        }

        protected void execute() {
            ErrorCode connectionErrorCode = ErrorCode.INTERNAL_ERROR;
            ErrorCode streamErrorCode = ErrorCode.INTERNAL_ERROR;
            try {
                this.frameReader = SpdyConnection.this.variant.newReader(Okio.buffer(Okio.source(SpdyConnection.this.socket)), SpdyConnection.this.client);
                if (!SpdyConnection.this.client) {
                    this.frameReader.readConnectionPreface();
                }
                while (this.frameReader.nextFrame(this)) {
                }
                connectionErrorCode = ErrorCode.NO_ERROR;
                streamErrorCode = ErrorCode.CANCEL;
            } catch (IOException e) {
                connectionErrorCode = ErrorCode.PROTOCOL_ERROR;
                streamErrorCode = ErrorCode.PROTOCOL_ERROR;
            } finally {
                try {
                    SpdyConnection.this.close(connectionErrorCode, streamErrorCode);
                } catch (IOException e2) {
                }
                Util.closeQuietly(this.frameReader);
            }
            Util.closeQuietly(this.frameReader);
        }

        public void data(boolean inFinished, int streamId, BufferedSource source, int length) throws IOException {
            if (SpdyConnection.this.pushedStream(streamId)) {
                SpdyConnection.this.pushDataLater(streamId, source, length, inFinished);
                return;
            }
            SpdyStream dataStream = SpdyConnection.this.getStream(streamId);
            if (dataStream == null) {
                SpdyConnection.this.writeSynResetLater(streamId, ErrorCode.INVALID_STREAM);
                source.skip((long) length);
                return;
            }
            dataStream.receiveData(source, length);
            if (inFinished) {
                dataStream.receiveFin();
            }
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void headers(boolean r16, boolean r17, int r18, int r19, java.util.List<com.squareup.okhttp.internal.spdy.Header> r20, com.squareup.okhttp.internal.spdy.HeadersMode r21) {
            /*
            r15 = this;
            r1 = r15;
            r8 = r17;
            r9 = r18;
            r10 = r20;
            r2 = com.squareup.okhttp.internal.spdy.SpdyConnection.this;
            r2 = r2.pushedStream(r9);
            if (r2 == 0) goto L_0x0015;
        L_0x000f:
            r2 = com.squareup.okhttp.internal.spdy.SpdyConnection.this;
            r2.pushHeadersLater(r9, r10, r8);
            return;
        L_0x0015:
            r11 = com.squareup.okhttp.internal.spdy.SpdyConnection.this;
            monitor-enter(r11);
            r2 = 0;
            r3 = com.squareup.okhttp.internal.spdy.SpdyConnection.this;	 Catch:{ all -> 0x00b6 }
            r3 = r3.shutdown;	 Catch:{ all -> 0x00b6 }
            if (r3 == 0) goto L_0x0023;
        L_0x0021:
            monitor-exit(r11);	 Catch:{ all -> 0x00b6 }
            return;
        L_0x0023:
            r3 = com.squareup.okhttp.internal.spdy.SpdyConnection.this;	 Catch:{ all -> 0x00b6 }
            r3 = r3.getStream(r9);	 Catch:{ all -> 0x00b6 }
            r12 = r3;
            if (r12 != 0) goto L_0x0099;
        L_0x002c:
            r2 = r21.failIfStreamAbsent();	 Catch:{ all -> 0x0095 }
            if (r2 == 0) goto L_0x003b;
        L_0x0032:
            r2 = com.squareup.okhttp.internal.spdy.SpdyConnection.this;	 Catch:{ all -> 0x0095 }
            r3 = com.squareup.okhttp.internal.spdy.ErrorCode.INVALID_STREAM;	 Catch:{ all -> 0x0095 }
            r2.writeSynResetLater(r9, r3);	 Catch:{ all -> 0x0095 }
            monitor-exit(r11);	 Catch:{ all -> 0x0095 }
            return;
        L_0x003b:
            r2 = com.squareup.okhttp.internal.spdy.SpdyConnection.this;	 Catch:{ all -> 0x0095 }
            r2 = r2.lastGoodStreamId;	 Catch:{ all -> 0x0095 }
            if (r9 > r2) goto L_0x0045;
        L_0x0043:
            monitor-exit(r11);	 Catch:{ all -> 0x0095 }
            return;
        L_0x0045:
            r2 = r9 % 2;
            r3 = com.squareup.okhttp.internal.spdy.SpdyConnection.this;	 Catch:{ all -> 0x0095 }
            r3 = r3.nextStreamId;	 Catch:{ all -> 0x0095 }
            r13 = 2;
            r3 = r3 % r13;
            if (r2 != r3) goto L_0x0053;
        L_0x0051:
            monitor-exit(r11);	 Catch:{ all -> 0x0095 }
            return;
        L_0x0053:
            r14 = new com.squareup.okhttp.internal.spdy.SpdyStream;	 Catch:{ all -> 0x0095 }
            r4 = com.squareup.okhttp.internal.spdy.SpdyConnection.this;	 Catch:{ all -> 0x0095 }
            r2 = r14;
            r3 = r9;
            r5 = r16;
            r6 = r8;
            r7 = r10;
            r2.<init>(r3, r4, r5, r6, r7);	 Catch:{ all -> 0x0095 }
            r2 = r14;
            r3 = com.squareup.okhttp.internal.spdy.SpdyConnection.this;	 Catch:{ all -> 0x0095 }
            r3.lastGoodStreamId = r9;	 Catch:{ all -> 0x0095 }
            r3 = com.squareup.okhttp.internal.spdy.SpdyConnection.this;	 Catch:{ all -> 0x0095 }
            r3 = r3.streams;	 Catch:{ all -> 0x0095 }
            r4 = java.lang.Integer.valueOf(r18);	 Catch:{ all -> 0x0095 }
            r3.put(r4, r2);	 Catch:{ all -> 0x0095 }
            r3 = com.squareup.okhttp.internal.spdy.SpdyConnection.executor;	 Catch:{ all -> 0x0095 }
            r4 = new com.squareup.okhttp.internal.spdy.SpdyConnection$Reader$1;	 Catch:{ all -> 0x0095 }
            r5 = "OkHttp %s stream %d";
            r6 = new java.lang.Object[r13];	 Catch:{ all -> 0x0095 }
            r7 = 0;
            r13 = com.squareup.okhttp.internal.spdy.SpdyConnection.this;	 Catch:{ all -> 0x0095 }
            r13 = r13.hostName;	 Catch:{ all -> 0x0095 }
            r6[r7] = r13;	 Catch:{ all -> 0x0095 }
            r7 = 1;
            r13 = java.lang.Integer.valueOf(r18);	 Catch:{ all -> 0x0095 }
            r6[r7] = r13;	 Catch:{ all -> 0x0095 }
            r4.<init>(r5, r6, r2);	 Catch:{ all -> 0x0095 }
            r3.execute(r4);	 Catch:{ all -> 0x0095 }
            monitor-exit(r11);	 Catch:{ all -> 0x0095 }
            return;
        L_0x0095:
            r0 = move-exception;
            r3 = r21;
            goto L_0x00ba;
        L_0x0099:
            monitor-exit(r11);	 Catch:{ all -> 0x0095 }
            r2 = r21.failIfStreamPresent();
            if (r2 == 0) goto L_0x00ab;
        L_0x00a0:
            r2 = com.squareup.okhttp.internal.spdy.ErrorCode.PROTOCOL_ERROR;
            r12.closeLater(r2);
            r2 = com.squareup.okhttp.internal.spdy.SpdyConnection.this;
            r2.removeStream(r9);
            return;
        L_0x00ab:
            r3 = r21;
            r12.receiveHeaders(r10, r3);
            if (r8 == 0) goto L_0x00b5;
        L_0x00b2:
            r12.receiveFin();
        L_0x00b5:
            return;
        L_0x00b6:
            r0 = move-exception;
            r3 = r21;
            r12 = r2;
        L_0x00ba:
            r2 = r0;
            monitor-exit(r11);	 Catch:{ all -> 0x00bd }
            throw r2;
        L_0x00bd:
            r0 = move-exception;
            goto L_0x00ba;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.spdy.SpdyConnection.Reader.headers(boolean, boolean, int, int, java.util.List, com.squareup.okhttp.internal.spdy.HeadersMode):void");
        }

        public void rstStream(int streamId, ErrorCode errorCode) {
            if (SpdyConnection.this.pushedStream(streamId)) {
                SpdyConnection.this.pushResetLater(streamId, errorCode);
                return;
            }
            SpdyStream rstStream = SpdyConnection.this.removeStream(streamId);
            if (rstStream != null) {
                rstStream.receiveRstStream(errorCode);
            }
        }

        public void settings(boolean clearPrevious, Settings newSettings) {
            long delta = 0;
            SpdyStream[] streamsToNotify = null;
            synchronized (SpdyConnection.this) {
                int priorWriteWindowSize = SpdyConnection.this.peerSettings.getInitialWindowSize(65536);
                if (clearPrevious) {
                    SpdyConnection.this.peerSettings.clear();
                }
                SpdyConnection.this.peerSettings.merge(newSettings);
                if (SpdyConnection.this.getProtocol() == Protocol.HTTP_2) {
                    ackSettingsLater(newSettings);
                }
                int peerInitialWindowSize = SpdyConnection.this.peerSettings.getInitialWindowSize(65536);
                if (!(peerInitialWindowSize == -1 || peerInitialWindowSize == priorWriteWindowSize)) {
                    delta = (long) (peerInitialWindowSize - priorWriteWindowSize);
                    if (!SpdyConnection.this.receivedInitialPeerSettings) {
                        SpdyConnection.this.addBytesToWriteWindow(delta);
                        SpdyConnection.this.receivedInitialPeerSettings = true;
                    }
                    if (!SpdyConnection.this.streams.isEmpty()) {
                        streamsToNotify = (SpdyStream[]) SpdyConnection.this.streams.values().toArray(new SpdyStream[SpdyConnection.this.streams.size()]);
                    }
                }
            }
            if (streamsToNotify != null && delta != 0) {
                for (SpdyStream stream : streamsToNotify) {
                    synchronized (stream) {
                        stream.addBytesToWriteWindow(delta);
                    }
                }
            }
        }

        private void ackSettingsLater(final Settings peerSettings) {
            SpdyConnection.executor.execute(new NamedRunnable("OkHttp %s ACK Settings", new Object[]{SpdyConnection.this.hostName}) {
                public void execute() {
                    try {
                        SpdyConnection.this.frameWriter.ackSettings(peerSettings);
                    } catch (IOException e) {
                    }
                }
            });
        }

        public void ackSettings() {
        }

        public void ping(boolean reply, int payload1, int payload2) {
            if (reply) {
                Ping ping = SpdyConnection.this.removePing(payload1);
                if (ping != null) {
                    ping.receive();
                }
                return;
            }
            SpdyConnection.this.writePingLater(true, payload1, payload2, null);
        }

        public void goAway(int lastGoodStreamId, ErrorCode errorCode, ByteString debugData) {
            debugData.size();
            synchronized (SpdyConnection.this) {
                SpdyStream[] streamsCopy = (SpdyStream[]) SpdyConnection.this.streams.values().toArray(new SpdyStream[SpdyConnection.this.streams.size()]);
                SpdyConnection.this.shutdown = true;
            }
            for (SpdyStream spdyStream : streamsCopy) {
                if (spdyStream.getId() > lastGoodStreamId && spdyStream.isLocallyInitiated()) {
                    spdyStream.receiveRstStream(ErrorCode.REFUSED_STREAM);
                    SpdyConnection.this.removeStream(spdyStream.getId());
                }
            }
        }

        public void windowUpdate(int streamId, long windowSizeIncrement) {
            if (streamId == 0) {
                synchronized (SpdyConnection.this) {
                    SpdyConnection spdyConnection = SpdyConnection.this;
                    spdyConnection.bytesLeftInWriteWindow += windowSizeIncrement;
                    SpdyConnection.this.notifyAll();
                }
                return;
            }
            SpdyStream stream = SpdyConnection.this.getStream(streamId);
            if (stream != null) {
                synchronized (stream) {
                    stream.addBytesToWriteWindow(windowSizeIncrement);
                }
            }
        }

        public void priority(int streamId, int streamDependency, int weight, boolean exclusive) {
        }

        public void pushPromise(int streamId, int promisedStreamId, List<Header> requestHeaders) {
            SpdyConnection.this.pushRequestLater(promisedStreamId, requestHeaders);
        }

        public void alternateService(int streamId, String origin, ByteString protocol, String host, int port, long maxAge) {
        }
    }

    private SpdyConnection(Builder builder) throws IOException {
        this.streams = new HashMap();
        this.idleStartTimeNs = System.nanoTime();
        this.unacknowledgedBytesRead = 0;
        this.okHttpSettings = new Settings();
        this.peerSettings = new Settings();
        this.receivedInitialPeerSettings = false;
        this.currentPushRequests = new LinkedHashSet();
        this.protocol = builder.protocol;
        this.pushObserver = builder.pushObserver;
        this.client = builder.client;
        this.handler = builder.handler;
        int i = 2;
        this.nextStreamId = builder.client ? 1 : 2;
        if (builder.client && this.protocol == Protocol.HTTP_2) {
            this.nextStreamId += 2;
        }
        if (builder.client) {
            i = 1;
        }
        this.nextPingId = i;
        if (builder.client) {
            this.okHttpSettings.set(7, 0, 16777216);
        }
        this.hostName = builder.hostName;
        if (this.protocol == Protocol.HTTP_2) {
            this.variant = new Http2();
            this.pushExecutor = new ThreadPoolExecutor(0, 1, 60, TimeUnit.SECONDS, new LinkedBlockingQueue(), Util.threadFactory(String.format("OkHttp %s Push Observer", new Object[]{this.hostName}), true));
            this.peerSettings.set(7, 0, SupportMenu.USER_MASK);
            this.peerSettings.set(5, 0, 16384);
        } else if (this.protocol == Protocol.SPDY_3) {
            this.variant = new Spdy3();
            this.pushExecutor = null;
        } else {
            throw new AssertionError(this.protocol);
        }
        this.bytesLeftInWriteWindow = (long) this.peerSettings.getInitialWindowSize(65536);
        this.socket = builder.socket;
        this.frameWriter = this.variant.newWriter(Okio.buffer(Okio.sink(builder.socket)), this.client);
        this.readerRunnable = new Reader();
        new Thread(this.readerRunnable).start();
    }

    public Protocol getProtocol() {
        return this.protocol;
    }

    public synchronized int openStreamCount() {
        return this.streams.size();
    }

    synchronized SpdyStream getStream(int id) {
        return (SpdyStream) this.streams.get(Integer.valueOf(id));
    }

    synchronized SpdyStream removeStream(int streamId) {
        SpdyStream stream;
        stream = (SpdyStream) this.streams.remove(Integer.valueOf(streamId));
        if (stream != null && this.streams.isEmpty()) {
            setIdle(true);
        }
        return stream;
    }

    private synchronized void setIdle(boolean value) {
        this.idleStartTimeNs = value ? System.nanoTime() : Long.MAX_VALUE;
    }

    public synchronized boolean isIdle() {
        return this.idleStartTimeNs != Long.MAX_VALUE;
    }

    public synchronized long getIdleStartTimeNs() {
        return this.idleStartTimeNs;
    }

    public SpdyStream pushStream(int associatedStreamId, List<Header> requestHeaders, boolean out) throws IOException {
        if (this.client) {
            throw new IllegalStateException("Client cannot push requests.");
        } else if (this.protocol == Protocol.HTTP_2) {
            return newStream(associatedStreamId, requestHeaders, out, false);
        } else {
            throw new IllegalStateException("protocol != HTTP_2");
        }
    }

    public SpdyStream newStream(List<Header> requestHeaders, boolean out, boolean in) throws IOException {
        return newStream(0, requestHeaders, out, in);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.squareup.okhttp.internal.spdy.SpdyStream newStream(int r17, java.util.List<com.squareup.okhttp.internal.spdy.Header> r18, boolean r19, boolean r20) throws java.io.IOException {
        /*
        r16 = this;
        r7 = r16;
        r14 = r17;
        r4 = r19 ^ 1;
        r5 = r20 ^ 1;
        r13 = r7.frameWriter;
        monitor-enter(r13);
        r8 = 0;
        r9 = 0;
        monitor-enter(r16);	 Catch:{ all -> 0x00ab }
        r1 = r7.shutdown;	 Catch:{ all -> 0x00a2 }
        if (r1 == 0) goto L_0x0021;
    L_0x0012:
        r1 = new java.io.IOException;	 Catch:{ all -> 0x001a }
        r2 = "shutdown";
        r1.<init>(r2);	 Catch:{ all -> 0x001a }
        throw r1;	 Catch:{ all -> 0x001a }
    L_0x001a:
        r0 = move-exception;
        r10 = r18;
        r1 = r0;
        r3 = r13;
        goto L_0x00a7;
    L_0x0021:
        r1 = r7.nextStreamId;	 Catch:{ all -> 0x00a2 }
        r12 = r1;
        r1 = r7.nextStreamId;	 Catch:{ all -> 0x009a }
        r1 = r1 + 2;
        r7.nextStreamId = r1;	 Catch:{ all -> 0x009a }
        r10 = new com.squareup.okhttp.internal.spdy.SpdyStream;	 Catch:{ all -> 0x009a }
        r1 = r10;
        r2 = r12;
        r3 = r7;
        r6 = r18;
        r1.<init>(r2, r3, r4, r5, r6);	 Catch:{ all -> 0x009a }
        r1 = r10;
        r2 = r1.isOpen();	 Catch:{ all -> 0x0091 }
        if (r2 == 0) goto L_0x004f;
    L_0x003b:
        r2 = r7.streams;	 Catch:{ all -> 0x0048 }
        r3 = java.lang.Integer.valueOf(r12);	 Catch:{ all -> 0x0048 }
        r2.put(r3, r1);	 Catch:{ all -> 0x0048 }
        r7.setIdle(r8);	 Catch:{ all -> 0x0048 }
        goto L_0x004f;
    L_0x0048:
        r0 = move-exception;
        r10 = r18;
        r9 = r1;
        r8 = r12;
        goto L_0x00a5;
    L_0x004f:
        monitor-exit(r16);	 Catch:{ all -> 0x0091 }
        if (r14 != 0) goto L_0x006b;
    L_0x0052:
        r8 = r7.frameWriter;	 Catch:{ all -> 0x0062 }
        r9 = r4;
        r10 = r5;
        r11 = r12;
        r2 = r12;
        r12 = r14;
        r3 = r13;
        r13 = r18;
        r8.synStream(r9, r10, r11, r12, r13);	 Catch:{ all -> 0x008b }
        r10 = r18;
        goto L_0x0080;
    L_0x0062:
        r0 = move-exception;
        r2 = r12;
        r3 = r13;
        r10 = r18;
        r9 = r1;
        r8 = r2;
        r1 = r0;
        goto L_0x00b0;
    L_0x006b:
        r2 = r12;
        r3 = r13;
        r6 = r7.client;	 Catch:{ all -> 0x008b }
        if (r6 == 0) goto L_0x0079;
    L_0x0071:
        r6 = new java.lang.IllegalArgumentException;	 Catch:{ all -> 0x008b }
        r8 = "client streams shouldn't have associated stream IDs";
        r6.<init>(r8);	 Catch:{ all -> 0x008b }
        throw r6;	 Catch:{ all -> 0x008b }
    L_0x0079:
        r6 = r7.frameWriter;	 Catch:{ all -> 0x008b }
        r10 = r18;
        r6.pushPromise(r14, r2, r10);	 Catch:{ all -> 0x0089 }
    L_0x0080:
        monitor-exit(r3);	 Catch:{ all -> 0x0089 }
        if (r19 != 0) goto L_0x0088;
    L_0x0083:
        r3 = r7.frameWriter;
        r3.flush();
    L_0x0088:
        return r1;
    L_0x0089:
        r0 = move-exception;
        goto L_0x008e;
    L_0x008b:
        r0 = move-exception;
        r10 = r18;
    L_0x008e:
        r9 = r1;
        r8 = r2;
        goto L_0x00af;
    L_0x0091:
        r0 = move-exception;
        r10 = r18;
        r2 = r12;
        r3 = r13;
        r9 = r1;
        r8 = r2;
        r1 = r0;
        goto L_0x00a1;
    L_0x009a:
        r0 = move-exception;
        r10 = r18;
        r2 = r12;
        r3 = r13;
        r1 = r0;
        r8 = r2;
    L_0x00a1:
        goto L_0x00a7;
    L_0x00a2:
        r0 = move-exception;
        r10 = r18;
    L_0x00a5:
        r3 = r13;
    L_0x00a6:
        r1 = r0;
    L_0x00a7:
        monitor-exit(r16);	 Catch:{ all -> 0x00a9 }
        throw r1;	 Catch:{ all -> 0x00b2 }
    L_0x00a9:
        r0 = move-exception;
        goto L_0x00a6;
    L_0x00ab:
        r0 = move-exception;
        r10 = r18;
        r3 = r13;
    L_0x00af:
        r1 = r0;
    L_0x00b0:
        monitor-exit(r3);	 Catch:{ all -> 0x00b2 }
        throw r1;
    L_0x00b2:
        r0 = move-exception;
        goto L_0x00af;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.spdy.SpdyConnection.newStream(int, java.util.List, boolean, boolean):com.squareup.okhttp.internal.spdy.SpdyStream");
    }

    void writeSynReply(int streamId, boolean outFinished, List<Header> alternating) throws IOException {
        this.frameWriter.synReply(outFinished, streamId, alternating);
    }

    public void writeData(int streamId, boolean outFinished, Buffer buffer, long byteCount) throws IOException {
        if (byteCount == 0) {
            this.frameWriter.data(outFinished, streamId, buffer, 0);
            return;
        }
        while (byteCount > 0) {
            int toWrite;
            synchronized (this) {
                while (this.bytesLeftInWriteWindow <= 0) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        throw new InterruptedIOException();
                    }
                }
                toWrite = Math.min((int) Math.min(byteCount, this.bytesLeftInWriteWindow), this.frameWriter.maxDataLength());
                this.bytesLeftInWriteWindow -= (long) toWrite;
            }
            long byteCount2 = byteCount - ((long) toWrite);
            FrameWriter frameWriter = this.frameWriter;
            boolean z = outFinished && byteCount2 == 0;
            frameWriter.data(z, streamId, buffer, toWrite);
            byteCount = byteCount2;
        }
    }

    void addBytesToWriteWindow(long delta) {
        this.bytesLeftInWriteWindow += delta;
        if (delta > 0) {
            notifyAll();
        }
    }

    void writeSynResetLater(int streamId, ErrorCode errorCode) {
        final int i = streamId;
        final ErrorCode errorCode2 = errorCode;
        executor.submit(new NamedRunnable("OkHttp %s stream %d", new Object[]{this.hostName, Integer.valueOf(streamId)}) {
            public void execute() {
                try {
                    SpdyConnection.this.writeSynReset(i, errorCode2);
                } catch (IOException e) {
                }
            }
        });
    }

    void writeSynReset(int streamId, ErrorCode statusCode) throws IOException {
        this.frameWriter.rstStream(streamId, statusCode);
    }

    void writeWindowUpdateLater(int streamId, long unacknowledgedBytesRead) {
        final int i = streamId;
        final long j = unacknowledgedBytesRead;
        executor.execute(new NamedRunnable("OkHttp Window Update %s stream %d", new Object[]{this.hostName, Integer.valueOf(streamId)}) {
            public void execute() {
                try {
                    SpdyConnection.this.frameWriter.windowUpdate(i, j);
                } catch (IOException e) {
                }
            }
        });
    }

    public Ping ping() throws IOException {
        Throwable th;
        Ping ping = new Ping();
        synchronized (this) {
            try {
                if (this.shutdown) {
                    throw new IOException("shutdown");
                }
                int pingId = this.nextPingId;
                try {
                    this.nextPingId += 2;
                    if (this.pings == null) {
                        this.pings = new HashMap();
                    }
                    this.pings.put(Integer.valueOf(pingId), ping);
                    writePing(false, pingId, 1330343787, ping);
                    return ping;
                } catch (Throwable th2) {
                    th = th2;
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private void writePingLater(boolean reply, int payload1, int payload2, Ping ping) {
        final boolean z = reply;
        final int i = payload1;
        final int i2 = payload2;
        final Ping ping2 = ping;
        executor.execute(new NamedRunnable("OkHttp %s ping %08x%08x", new Object[]{this.hostName, Integer.valueOf(payload1), Integer.valueOf(payload2)}) {
            public void execute() {
                try {
                    SpdyConnection.this.writePing(z, i, i2, ping2);
                } catch (IOException e) {
                }
            }
        });
    }

    private void writePing(boolean reply, int payload1, int payload2, Ping ping) throws IOException {
        synchronized (this.frameWriter) {
            if (ping != null) {
                ping.send();
            }
            this.frameWriter.ping(reply, payload1, payload2);
        }
    }

    private synchronized Ping removePing(int id) {
        return this.pings != null ? (Ping) this.pings.remove(Integer.valueOf(id)) : null;
    }

    public void flush() throws IOException {
        this.frameWriter.flush();
    }

    public void shutdown(ErrorCode statusCode) throws IOException {
        synchronized (this.frameWriter) {
            synchronized (this) {
                if (this.shutdown) {
                    return;
                }
                this.shutdown = true;
                int lastGoodStreamId = this.lastGoodStreamId;
                this.frameWriter.goAway(lastGoodStreamId, statusCode, Util.EMPTY_BYTE_ARRAY);
            }
        }
    }

    public void close() throws IOException {
        close(ErrorCode.NO_ERROR, ErrorCode.CANCEL);
    }

    private void close(ErrorCode connectionCode, ErrorCode streamCode) throws IOException {
        int length;
        IOException thrown = null;
        try {
            shutdown(connectionCode);
        } catch (IOException e) {
            thrown = e;
        }
        SpdyStream[] streamsToClose = null;
        Ping[] pingsToCancel = null;
        synchronized (this) {
            int i = 0;
            if (!this.streams.isEmpty()) {
                streamsToClose = (SpdyStream[]) this.streams.values().toArray(new SpdyStream[this.streams.size()]);
                this.streams.clear();
                setIdle(false);
            }
            if (this.pings != null) {
                pingsToCancel = (Ping[]) this.pings.values().toArray(new Ping[this.pings.size()]);
                this.pings = null;
            }
        }
        if (streamsToClose != null) {
            IOException thrown2 = thrown;
            for (SpdyStream stream : streamsToClose) {
                try {
                    stream.close(streamCode);
                } catch (IOException e2) {
                    if (thrown2 != null) {
                        thrown2 = e2;
                    }
                }
            }
            thrown = thrown2;
        }
        if (pingsToCancel != null) {
            length = pingsToCancel.length;
            while (i < length) {
                pingsToCancel[i].cancel();
                i++;
            }
        }
        try {
            this.frameWriter.close();
        } catch (IOException e3) {
            if (thrown == null) {
                thrown = e3;
            }
        }
        try {
            this.socket.close();
        } catch (IOException e32) {
            thrown = e32;
        }
        if (thrown != null) {
            throw thrown;
        }
    }

    public void sendConnectionPreface() throws IOException {
        this.frameWriter.connectionPreface();
        this.frameWriter.settings(this.okHttpSettings);
        int windowSize = this.okHttpSettings.getInitialWindowSize(65536);
        if (windowSize != 65536) {
            this.frameWriter.windowUpdate(0, (long) (windowSize - 65536));
        }
    }

    private boolean pushedStream(int streamId) {
        return this.protocol == Protocol.HTTP_2 && streamId != 0 && (streamId & 1) == 0;
    }

    private void pushRequestLater(int streamId, List<Header> requestHeaders) {
        synchronized (this) {
            if (this.currentPushRequests.contains(Integer.valueOf(streamId))) {
                writeSynResetLater(streamId, ErrorCode.PROTOCOL_ERROR);
                return;
            }
            this.currentPushRequests.add(Integer.valueOf(streamId));
            final int i = streamId;
            final List<Header> list = requestHeaders;
            this.pushExecutor.execute(new NamedRunnable("OkHttp %s Push Request[%s]", new Object[]{this.hostName, Integer.valueOf(streamId)}) {
                public void execute() {
                    if (SpdyConnection.this.pushObserver.onRequest(i, list)) {
                        try {
                            SpdyConnection.this.frameWriter.rstStream(i, ErrorCode.CANCEL);
                            synchronized (SpdyConnection.this) {
                                SpdyConnection.this.currentPushRequests.remove(Integer.valueOf(i));
                            }
                        } catch (IOException e) {
                        }
                    }
                }
            });
        }
    }

    private void pushHeadersLater(int streamId, List<Header> requestHeaders, boolean inFinished) {
        final int i = streamId;
        final List<Header> list = requestHeaders;
        final boolean z = inFinished;
        this.pushExecutor.execute(new NamedRunnable("OkHttp %s Push Headers[%s]", new Object[]{this.hostName, Integer.valueOf(streamId)}) {
            public void execute() {
                boolean cancel = SpdyConnection.this.pushObserver.onHeaders(i, list, z);
                if (cancel) {
                    try {
                        SpdyConnection.this.frameWriter.rstStream(i, ErrorCode.CANCEL);
                    } catch (IOException e) {
                        return;
                    }
                }
                if (cancel || z) {
                    synchronized (SpdyConnection.this) {
                        SpdyConnection.this.currentPushRequests.remove(Integer.valueOf(i));
                    }
                }
            }
        });
    }

    private void pushDataLater(int streamId, BufferedSource source, int byteCount, boolean inFinished) throws IOException {
        Buffer buffer = new Buffer();
        source.require((long) byteCount);
        source.read(buffer, (long) byteCount);
        if (buffer.size() != ((long) byteCount)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(buffer.size());
            stringBuilder.append(" != ");
            stringBuilder.append(byteCount);
            throw new IOException(stringBuilder.toString());
        }
        final int i = streamId;
        final Buffer buffer2 = buffer;
        final int i2 = byteCount;
        final boolean z = inFinished;
        this.pushExecutor.execute(new NamedRunnable("OkHttp %s Push Data[%s]", new Object[]{this.hostName, Integer.valueOf(streamId)}) {
            public void execute() {
                try {
                    boolean cancel = SpdyConnection.this.pushObserver.onData(i, buffer2, i2, z);
                    if (cancel) {
                        SpdyConnection.this.frameWriter.rstStream(i, ErrorCode.CANCEL);
                    }
                    if (cancel || z) {
                        synchronized (SpdyConnection.this) {
                            SpdyConnection.this.currentPushRequests.remove(Integer.valueOf(i));
                        }
                    }
                } catch (IOException e) {
                }
            }
        });
    }

    private void pushResetLater(int streamId, ErrorCode errorCode) {
        final int i = streamId;
        final ErrorCode errorCode2 = errorCode;
        this.pushExecutor.execute(new NamedRunnable("OkHttp %s Push Reset[%s]", new Object[]{this.hostName, Integer.valueOf(streamId)}) {
            public void execute() {
                SpdyConnection.this.pushObserver.onReset(i, errorCode2);
                synchronized (SpdyConnection.this) {
                    SpdyConnection.this.currentPushRequests.remove(Integer.valueOf(i));
                }
            }
        });
    }
}
