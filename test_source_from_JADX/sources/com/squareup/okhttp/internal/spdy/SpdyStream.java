package com.squareup.okhttp.internal.spdy;

import java.io.EOFException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.ArrayList;
import java.util.List;
import okio.AsyncTimeout;
import okio.Buffer;
import okio.BufferedSource;
import okio.Sink;
import okio.Source;
import okio.Timeout;

public final class SpdyStream {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    long bytesLeftInWriteWindow;
    private final SpdyConnection connection;
    private ErrorCode errorCode = null;
    private final int id;
    private final SpdyTimeout readTimeout = new SpdyTimeout();
    private final List<Header> requestHeaders;
    private List<Header> responseHeaders;
    final SpdyDataSink sink;
    private final SpdyDataSource source;
    long unacknowledgedBytesRead = 0;
    private final SpdyTimeout writeTimeout = new SpdyTimeout();

    final class SpdyDataSink implements Sink {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private static final long EMIT_BUFFER_SIZE = 16384;
        private boolean closed;
        private boolean finished;
        private final Buffer sendBuffer = new Buffer();

        static {
            Class cls = SpdyStream.class;
        }

        SpdyDataSink() {
        }

        public void write(Buffer source, long byteCount) throws IOException {
            this.sendBuffer.write(source, byteCount);
            while (this.sendBuffer.size() >= 16384) {
                emitDataFrame(false);
            }
        }

        private void emitDataFrame(boolean outFinished) throws IOException {
            long toWrite;
            synchronized (SpdyStream.this) {
                SpdyStream.this.writeTimeout.enter();
                while (SpdyStream.this.bytesLeftInWriteWindow <= 0 && !this.finished && !this.closed && SpdyStream.this.errorCode == null) {
                    try {
                        SpdyStream.this.waitForIo();
                    } finally {
                        SpdyStream.this.writeTimeout.exitAndThrowIfTimedOut();
                    }
                }
                SpdyStream.this.checkOutNotClosed();
                toWrite = Math.min(SpdyStream.this.bytesLeftInWriteWindow, this.sendBuffer.size());
                SpdyStream spdyStream = SpdyStream.this;
                spdyStream.bytesLeftInWriteWindow -= toWrite;
            }
            SpdyConnection access$500 = SpdyStream.this.connection;
            int access$600 = SpdyStream.this.id;
            boolean z = outFinished && toWrite == this.sendBuffer.size();
            access$500.writeData(access$600, z, this.sendBuffer, toWrite);
        }

        public void flush() throws IOException {
            synchronized (SpdyStream.this) {
                SpdyStream.this.checkOutNotClosed();
            }
            while (this.sendBuffer.size() > 0) {
                emitDataFrame(false);
            }
            SpdyStream.this.connection.flush();
        }

        public Timeout timeout() {
            return SpdyStream.this.writeTimeout;
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void close() throws java.io.IOException {
            /*
            r8 = this;
            r0 = com.squareup.okhttp.internal.spdy.SpdyStream.this;
            monitor-enter(r0);
            r1 = r8.closed;	 Catch:{ all -> 0x0059 }
            if (r1 == 0) goto L_0x000a;
        L_0x0008:
            monitor-exit(r0);	 Catch:{ all -> 0x0059 }
            return;
        L_0x000a:
            monitor-exit(r0);	 Catch:{ all -> 0x0059 }
            r0 = com.squareup.okhttp.internal.spdy.SpdyStream.this;
            r0 = r0.sink;
            r0 = r0.finished;
            r1 = 1;
            if (r0 != 0) goto L_0x0041;
        L_0x0014:
            r0 = r8.sendBuffer;
            r2 = r0.size();
            r4 = 0;
            r0 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1));
            if (r0 <= 0) goto L_0x002e;
        L_0x0020:
            r0 = r8.sendBuffer;
            r2 = r0.size();
            r0 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1));
            if (r0 <= 0) goto L_0x0041;
        L_0x002a:
            r8.emitDataFrame(r1);
            goto L_0x0020;
        L_0x002e:
            r0 = com.squareup.okhttp.internal.spdy.SpdyStream.this;
            r2 = r0.connection;
            r0 = com.squareup.okhttp.internal.spdy.SpdyStream.this;
            r3 = r0.id;
            r4 = 1;
            r5 = 0;
            r6 = 0;
            r2.writeData(r3, r4, r5, r6);
        L_0x0041:
            r2 = com.squareup.okhttp.internal.spdy.SpdyStream.this;
            monitor-enter(r2);
            r8.closed = r1;	 Catch:{ all -> 0x0056 }
            monitor-exit(r2);	 Catch:{ all -> 0x0056 }
            r0 = com.squareup.okhttp.internal.spdy.SpdyStream.this;
            r0 = r0.connection;
            r0.flush();
            r0 = com.squareup.okhttp.internal.spdy.SpdyStream.this;
            r0.cancelStreamIfNecessary();
            return;
        L_0x0056:
            r0 = move-exception;
            monitor-exit(r2);	 Catch:{ all -> 0x0056 }
            throw r0;
        L_0x0059:
            r1 = move-exception;
            monitor-exit(r0);	 Catch:{ all -> 0x0059 }
            throw r1;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.spdy.SpdyStream.SpdyDataSink.close():void");
        }
    }

    private final class SpdyDataSource implements Source {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private boolean closed;
        private boolean finished;
        private final long maxByteCount;
        private final Buffer readBuffer;
        private final Buffer receiveBuffer;

        static {
            Class cls = SpdyStream.class;
        }

        private SpdyDataSource(long maxByteCount) {
            this.receiveBuffer = new Buffer();
            this.readBuffer = new Buffer();
            this.maxByteCount = maxByteCount;
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public long read(okio.Buffer r17, long r18) throws java.io.IOException {
            /*
            r16 = this;
            r1 = r16;
            r2 = r18;
            r4 = 0;
            r6 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1));
            if (r6 >= 0) goto L_0x0021;
        L_0x000a:
            r4 = new java.lang.IllegalArgumentException;
            r5 = new java.lang.StringBuilder;
            r5.<init>();
            r6 = "byteCount < 0: ";
            r5.append(r6);
            r5.append(r2);
            r5 = r5.toString();
            r4.<init>(r5);
            throw r4;
        L_0x0021:
            r6 = com.squareup.okhttp.internal.spdy.SpdyStream.this;
            monitor-enter(r6);
            r16.waitUntilReadable();	 Catch:{ all -> 0x00d5 }
            r16.checkNotClosed();	 Catch:{ all -> 0x00d5 }
            r7 = r1.readBuffer;	 Catch:{ all -> 0x00d5 }
            r7 = r7.size();	 Catch:{ all -> 0x00d5 }
            r9 = (r7 > r4 ? 1 : (r7 == r4 ? 0 : -1));
            if (r9 != 0) goto L_0x0038;
        L_0x0034:
            r7 = -1;
            monitor-exit(r6);	 Catch:{ all -> 0x00d5 }
            return r7;
        L_0x0038:
            r7 = r1.readBuffer;	 Catch:{ all -> 0x00d5 }
            r8 = r1.readBuffer;	 Catch:{ all -> 0x00d5 }
            r8 = r8.size();	 Catch:{ all -> 0x00d5 }
            r8 = java.lang.Math.min(r2, r8);	 Catch:{ all -> 0x00d5 }
            r10 = r17;
            r7 = r7.read(r10, r8);	 Catch:{ all -> 0x00d3 }
            r9 = com.squareup.okhttp.internal.spdy.SpdyStream.this;	 Catch:{ all -> 0x00dc }
            r11 = r9.unacknowledgedBytesRead;	 Catch:{ all -> 0x00dc }
            r13 = 0;
            r13 = r11 + r7;
            r9.unacknowledgedBytesRead = r13;	 Catch:{ all -> 0x00dc }
            r9 = com.squareup.okhttp.internal.spdy.SpdyStream.this;	 Catch:{ all -> 0x00dc }
            r11 = r9.unacknowledgedBytesRead;	 Catch:{ all -> 0x00dc }
            r9 = com.squareup.okhttp.internal.spdy.SpdyStream.this;	 Catch:{ all -> 0x00dc }
            r9 = r9.connection;	 Catch:{ all -> 0x00dc }
            r9 = r9.okHttpSettings;	 Catch:{ all -> 0x00dc }
            r13 = 65536; // 0x10000 float:9.18355E-41 double:3.2379E-319;
            r9 = r9.getInitialWindowSize(r13);	 Catch:{ all -> 0x00dc }
            r9 = r9 / 2;
            r14 = (long) r9;	 Catch:{ all -> 0x00dc }
            r9 = (r11 > r14 ? 1 : (r11 == r14 ? 0 : -1));
            if (r9 < 0) goto L_0x0083;
        L_0x006c:
            r9 = com.squareup.okhttp.internal.spdy.SpdyStream.this;	 Catch:{ all -> 0x00dc }
            r9 = r9.connection;	 Catch:{ all -> 0x00dc }
            r11 = com.squareup.okhttp.internal.spdy.SpdyStream.this;	 Catch:{ all -> 0x00dc }
            r11 = r11.id;	 Catch:{ all -> 0x00dc }
            r12 = com.squareup.okhttp.internal.spdy.SpdyStream.this;	 Catch:{ all -> 0x00dc }
            r14 = r12.unacknowledgedBytesRead;	 Catch:{ all -> 0x00dc }
            r9.writeWindowUpdateLater(r11, r14);	 Catch:{ all -> 0x00dc }
            r9 = com.squareup.okhttp.internal.spdy.SpdyStream.this;	 Catch:{ all -> 0x00dc }
            r9.unacknowledgedBytesRead = r4;	 Catch:{ all -> 0x00dc }
        L_0x0083:
            monitor-exit(r6);	 Catch:{ all -> 0x00dc }
            r6 = com.squareup.okhttp.internal.spdy.SpdyStream.this;
            r9 = r6.connection;
            monitor-enter(r9);
            r6 = com.squareup.okhttp.internal.spdy.SpdyStream.this;	 Catch:{ all -> 0x00cf }
            r6 = r6.connection;	 Catch:{ all -> 0x00cf }
            r11 = r6.unacknowledgedBytesRead;	 Catch:{ all -> 0x00cf }
            r14 = 0;
            r14 = r11 + r7;
            r6.unacknowledgedBytesRead = r14;	 Catch:{ all -> 0x00cf }
            r6 = com.squareup.okhttp.internal.spdy.SpdyStream.this;	 Catch:{ all -> 0x00cf }
            r6 = r6.connection;	 Catch:{ all -> 0x00cf }
            r11 = r6.unacknowledgedBytesRead;	 Catch:{ all -> 0x00cf }
            r6 = com.squareup.okhttp.internal.spdy.SpdyStream.this;	 Catch:{ all -> 0x00cf }
            r6 = r6.connection;	 Catch:{ all -> 0x00cf }
            r6 = r6.okHttpSettings;	 Catch:{ all -> 0x00cf }
            r6 = r6.getInitialWindowSize(r13);	 Catch:{ all -> 0x00cf }
            r6 = r6 / 2;
            r13 = (long) r6;	 Catch:{ all -> 0x00cf }
            r6 = (r11 > r13 ? 1 : (r11 == r13 ? 0 : -1));
            if (r6 < 0) goto L_0x00cd;
        L_0x00b3:
            r6 = com.squareup.okhttp.internal.spdy.SpdyStream.this;	 Catch:{ all -> 0x00cf }
            r6 = r6.connection;	 Catch:{ all -> 0x00cf }
            r11 = 0;
            r12 = com.squareup.okhttp.internal.spdy.SpdyStream.this;	 Catch:{ all -> 0x00cf }
            r12 = r12.connection;	 Catch:{ all -> 0x00cf }
            r12 = r12.unacknowledgedBytesRead;	 Catch:{ all -> 0x00cf }
            r6.writeWindowUpdateLater(r11, r12);	 Catch:{ all -> 0x00cf }
            r6 = com.squareup.okhttp.internal.spdy.SpdyStream.this;	 Catch:{ all -> 0x00cf }
            r6 = r6.connection;	 Catch:{ all -> 0x00cf }
            r6.unacknowledgedBytesRead = r4;	 Catch:{ all -> 0x00cf }
        L_0x00cd:
            monitor-exit(r9);	 Catch:{ all -> 0x00cf }
            return r7;
        L_0x00cf:
            r0 = move-exception;
            r4 = r0;
            monitor-exit(r9);	 Catch:{ all -> 0x00cf }
            throw r4;
        L_0x00d3:
            r0 = move-exception;
            goto L_0x00d8;
        L_0x00d5:
            r0 = move-exception;
            r10 = r17;
        L_0x00d8:
            r7 = r4;
        L_0x00d9:
            r4 = r0;
            monitor-exit(r6);	 Catch:{ all -> 0x00dc }
            throw r4;
        L_0x00dc:
            r0 = move-exception;
            goto L_0x00d9;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.spdy.SpdyStream.SpdyDataSource.read(okio.Buffer, long):long");
        }

        private void waitUntilReadable() throws IOException {
            SpdyStream.this.readTimeout.enter();
            while (this.readBuffer.size() == 0 && !this.finished && !this.closed && SpdyStream.this.errorCode == null) {
                try {
                    SpdyStream.this.waitForIo();
                } catch (Throwable th) {
                    SpdyStream.this.readTimeout.exitAndThrowIfTimedOut();
                }
            }
            SpdyStream.this.readTimeout.exitAndThrowIfTimedOut();
        }

        void receive(BufferedSource in, long byteCount) throws IOException {
            while (byteCount > 0) {
                synchronized (SpdyStream.this) {
                    boolean finished = this.finished;
                    boolean z = true;
                    boolean flowControlError = byteCount + this.readBuffer.size() > this.maxByteCount;
                }
                if (flowControlError) {
                    in.skip(byteCount);
                    SpdyStream.this.closeLater(ErrorCode.FLOW_CONTROL_ERROR);
                    return;
                } else if (finished) {
                    in.skip(byteCount);
                    return;
                } else {
                    long read = in.read(this.receiveBuffer, byteCount);
                    if (read == -1) {
                        throw new EOFException();
                    }
                    long byteCount2 = byteCount - read;
                    synchronized (SpdyStream.this) {
                        if (this.readBuffer.size() != 0) {
                            z = false;
                        }
                        boolean wasEmpty = z;
                        this.readBuffer.writeAll(this.receiveBuffer);
                        if (wasEmpty) {
                            SpdyStream.this.notifyAll();
                        }
                    }
                    byteCount = byteCount2;
                }
            }
        }

        public Timeout timeout() {
            return SpdyStream.this.readTimeout;
        }

        public void close() throws IOException {
            synchronized (SpdyStream.this) {
                this.closed = true;
                this.readBuffer.clear();
                SpdyStream.this.notifyAll();
            }
            SpdyStream.this.cancelStreamIfNecessary();
        }

        private void checkNotClosed() throws IOException {
            if (this.closed) {
                throw new IOException("stream closed");
            } else if (SpdyStream.this.errorCode != null) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("stream was reset: ");
                stringBuilder.append(SpdyStream.this.errorCode);
                throw new IOException(stringBuilder.toString());
            }
        }
    }

    class SpdyTimeout extends AsyncTimeout {
        SpdyTimeout() {
        }

        protected void timedOut() {
            SpdyStream.this.closeLater(ErrorCode.CANCEL);
        }

        public void exitAndThrowIfTimedOut() throws InterruptedIOException {
            if (exit()) {
                throw new InterruptedIOException("timeout");
            }
        }
    }

    SpdyStream(int id, SpdyConnection connection, boolean outFinished, boolean inFinished, List<Header> requestHeaders) {
        if (connection == null) {
            throw new NullPointerException("connection == null");
        } else if (requestHeaders == null) {
            throw new NullPointerException("requestHeaders == null");
        } else {
            this.id = id;
            this.connection = connection;
            this.bytesLeftInWriteWindow = (long) connection.peerSettings.getInitialWindowSize(65536);
            this.source = new SpdyDataSource((long) connection.okHttpSettings.getInitialWindowSize(65536));
            this.sink = new SpdyDataSink();
            this.source.finished = inFinished;
            this.sink.finished = outFinished;
            this.requestHeaders = requestHeaders;
        }
    }

    public int getId() {
        return this.id;
    }

    public synchronized boolean isOpen() {
        if (this.errorCode != null) {
            return false;
        }
        if ((this.source.finished || this.source.closed) && ((this.sink.finished || this.sink.closed) && this.responseHeaders != null)) {
            return false;
        }
        return true;
    }

    public boolean isLocallyInitiated() {
        if (this.connection.client == ((this.id & 1) == 1)) {
            return true;
        }
        return false;
    }

    public SpdyConnection getConnection() {
        return this.connection;
    }

    public List<Header> getRequestHeaders() {
        return this.requestHeaders;
    }

    public synchronized List<Header> getResponseHeaders() throws IOException {
        this.readTimeout.enter();
        while (this.responseHeaders == null && this.errorCode == null) {
            try {
                waitForIo();
            } finally {
                this.readTimeout.exitAndThrowIfTimedOut();
            }
        }
        if (this.responseHeaders != null) {
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("stream was reset: ");
            stringBuilder.append(this.errorCode);
            throw new IOException(stringBuilder.toString());
        }
        return this.responseHeaders;
    }

    public synchronized ErrorCode getErrorCode() {
        return this.errorCode;
    }

    public void reply(List<Header> responseHeaders, boolean out) throws IOException {
        boolean outFinished = false;
        synchronized (this) {
            if (responseHeaders == null) {
                throw new NullPointerException("responseHeaders == null");
            } else if (this.responseHeaders != null) {
                throw new IllegalStateException("reply already sent");
            } else {
                this.responseHeaders = responseHeaders;
                if (!out) {
                    this.sink.finished = true;
                    outFinished = true;
                }
            }
        }
        this.connection.writeSynReply(this.id, outFinished, responseHeaders);
        if (outFinished) {
            this.connection.flush();
        }
    }

    public Timeout readTimeout() {
        return this.readTimeout;
    }

    public Timeout writeTimeout() {
        return this.writeTimeout;
    }

    public Source getSource() {
        return this.source;
    }

    public Sink getSink() {
        synchronized (this) {
            if (this.responseHeaders != null || isLocallyInitiated()) {
            } else {
                throw new IllegalStateException("reply before requesting the sink");
            }
        }
        return this.sink;
    }

    public void close(ErrorCode rstStatusCode) throws IOException {
        if (closeInternal(rstStatusCode)) {
            this.connection.writeSynReset(this.id, rstStatusCode);
        }
    }

    public void closeLater(ErrorCode errorCode) {
        if (closeInternal(errorCode)) {
            this.connection.writeSynResetLater(this.id, errorCode);
        }
    }

    private boolean closeInternal(ErrorCode errorCode) {
        synchronized (this) {
            if (this.errorCode != null) {
                return false;
            } else if (this.source.finished && this.sink.finished) {
                return false;
            } else {
                this.errorCode = errorCode;
                notifyAll();
                this.connection.removeStream(this.id);
                return true;
            }
        }
    }

    void receiveHeaders(List<Header> headers, HeadersMode headersMode) {
        ErrorCode errorCode = null;
        boolean open = true;
        synchronized (this) {
            if (this.responseHeaders == null) {
                if (headersMode.failIfHeadersAbsent()) {
                    errorCode = ErrorCode.PROTOCOL_ERROR;
                } else {
                    this.responseHeaders = headers;
                    open = isOpen();
                    notifyAll();
                }
            } else if (headersMode.failIfHeadersPresent()) {
                errorCode = ErrorCode.STREAM_IN_USE;
            } else {
                List<Header> newHeaders = new ArrayList();
                newHeaders.addAll(this.responseHeaders);
                newHeaders.addAll(headers);
                this.responseHeaders = newHeaders;
            }
        }
        if (errorCode != null) {
            closeLater(errorCode);
        } else if (!open) {
            this.connection.removeStream(this.id);
        }
    }

    void receiveData(BufferedSource in, int length) throws IOException {
        this.source.receive(in, (long) length);
    }

    void receiveFin() {
        synchronized (this) {
            this.source.finished = true;
            boolean open = isOpen();
            notifyAll();
        }
        if (!open) {
            this.connection.removeStream(this.id);
        }
    }

    synchronized void receiveRstStream(ErrorCode errorCode) {
        if (this.errorCode == null) {
            this.errorCode = errorCode;
            notifyAll();
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void cancelStreamIfNecessary() throws java.io.IOException {
        /*
        r5 = this;
        monitor-enter(r5);
        r0 = 0;
        r1 = r5.source;	 Catch:{ all -> 0x0043 }
        r1 = r1.finished;	 Catch:{ all -> 0x0043 }
        if (r1 != 0) goto L_0x0025;
    L_0x000b:
        r1 = r5.source;	 Catch:{ all -> 0x0043 }
        r1 = r1.closed;	 Catch:{ all -> 0x0043 }
        if (r1 == 0) goto L_0x0025;
    L_0x0013:
        r1 = r5.sink;	 Catch:{ all -> 0x0043 }
        r1 = r1.finished;	 Catch:{ all -> 0x0043 }
        if (r1 != 0) goto L_0x0023;
    L_0x001b:
        r1 = r5.sink;	 Catch:{ all -> 0x0043 }
        r1 = r1.closed;	 Catch:{ all -> 0x0043 }
        if (r1 == 0) goto L_0x0025;
    L_0x0023:
        r1 = 1;
        goto L_0x0026;
    L_0x0025:
        r1 = 0;
    L_0x0026:
        r2 = r5.isOpen();	 Catch:{ all -> 0x003e }
        r0 = r2;
        monitor-exit(r5);	 Catch:{ all -> 0x003e }
        if (r1 == 0) goto L_0x0034;
    L_0x002e:
        r2 = com.squareup.okhttp.internal.spdy.ErrorCode.CANCEL;
        r5.close(r2);
        goto L_0x003d;
    L_0x0034:
        if (r0 != 0) goto L_0x003d;
    L_0x0036:
        r2 = r5.connection;
        r3 = r5.id;
        r2.removeStream(r3);
    L_0x003d:
        return;
    L_0x003e:
        r2 = move-exception;
        r4 = r2;
        r2 = r1;
        r1 = r4;
        goto L_0x0045;
    L_0x0043:
        r1 = move-exception;
        r2 = 0;
    L_0x0045:
        monitor-exit(r5);	 Catch:{ all -> 0x0047 }
        throw r1;
    L_0x0047:
        r1 = move-exception;
        goto L_0x0045;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.spdy.SpdyStream.cancelStreamIfNecessary():void");
    }

    void addBytesToWriteWindow(long delta) {
        this.bytesLeftInWriteWindow += delta;
        if (delta > 0) {
            notifyAll();
        }
    }

    private void checkOutNotClosed() throws IOException {
        if (this.sink.closed) {
            throw new IOException("stream closed");
        } else if (this.sink.finished) {
            throw new IOException("stream finished");
        } else if (this.errorCode != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("stream was reset: ");
            stringBuilder.append(this.errorCode);
            throw new IOException(stringBuilder.toString());
        }
    }

    private void waitForIo() throws InterruptedIOException {
        try {
            wait();
        } catch (InterruptedException e) {
            throw new InterruptedIOException();
        }
    }
}
