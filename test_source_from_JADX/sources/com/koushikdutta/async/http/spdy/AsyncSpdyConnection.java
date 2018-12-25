package com.koushikdutta.async.http.spdy;

import com.koushikdutta.async.AsyncServer;
import com.koushikdutta.async.AsyncSocket;
import com.koushikdutta.async.BufferedDataSink;
import com.koushikdutta.async.ByteBufferList;
import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.DataSink;
import com.koushikdutta.async.Util;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.callback.DataCallback;
import com.koushikdutta.async.callback.WritableCallback;
import com.koushikdutta.async.future.SimpleFuture;
import com.koushikdutta.async.http.Protocol;
import com.koushikdutta.async.http.spdy.FrameReader.Handler;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class AsyncSpdyConnection implements Handler {
    private static final int OKHTTP_CLIENT_WINDOW_SIZE = 16777216;
    BufferedDataSink bufferedSocket;
    long bytesLeftInWriteWindow;
    boolean client;
    private int lastGoodStreamId;
    private int nextPingId;
    private int nextStreamId;
    final Settings okHttpSettings;
    Settings peerSettings;
    private Map<Integer, Ping> pings;
    Protocol protocol;
    FrameReader reader;
    private boolean receivedInitialPeerSettings;
    boolean shutdown;
    AsyncSocket socket;
    Hashtable<Integer, SpdySocket> sockets = new Hashtable();
    int totalWindowRead;
    Variant variant;
    FrameWriter writer;

    public class SpdySocket implements AsyncSocket {
        long bytesLeftInWriteWindow = ((long) AsyncSpdyConnection.this.peerSettings.getInitialWindowSize(65536));
        CompletedCallback closedCallback;
        DataCallback dataCallback;
        CompletedCallback endCallback;
        SimpleFuture<List<Header>> headers = new SimpleFuture();
        final int id;
        boolean isOpen = true;
        boolean paused;
        ByteBufferList pending = new ByteBufferList();
        int totalWindowRead;
        WritableCallback writable;
        ByteBufferList writing = new ByteBufferList();

        public AsyncSpdyConnection getConnection() {
            return AsyncSpdyConnection.this;
        }

        public SimpleFuture<List<Header>> headers() {
            return this.headers;
        }

        void updateWindowRead(int length) {
            this.totalWindowRead += length;
            if (this.totalWindowRead >= AsyncSpdyConnection.this.okHttpSettings.getInitialWindowSize(65536) / 2) {
                try {
                    AsyncSpdyConnection.this.writer.windowUpdate(this.id, (long) this.totalWindowRead);
                    this.totalWindowRead = 0;
                } catch (IOException e) {
                    throw new AssertionError(e);
                }
            }
            AsyncSpdyConnection.this.updateWindowRead(length);
        }

        public SpdySocket(int id, boolean outFinished, boolean inFinished, List<Header> list) {
            this.id = id;
        }

        public boolean isLocallyInitiated() {
            if (AsyncSpdyConnection.this.client == ((this.id & 1) == 1)) {
                return true;
            }
            return false;
        }

        public void addBytesToWriteWindow(long delta) {
            long prev = this.bytesLeftInWriteWindow;
            this.bytesLeftInWriteWindow += delta;
            if (this.bytesLeftInWriteWindow > 0 && prev <= 0) {
                Util.writable(this.writable);
            }
        }

        public AsyncServer getServer() {
            return AsyncSpdyConnection.this.socket.getServer();
        }

        public void setDataCallback(DataCallback callback) {
            this.dataCallback = callback;
        }

        public DataCallback getDataCallback() {
            return this.dataCallback;
        }

        public boolean isChunked() {
            return false;
        }

        public void pause() {
            this.paused = true;
        }

        public void resume() {
            this.paused = false;
        }

        public void close() {
            this.isOpen = false;
        }

        public boolean isPaused() {
            return this.paused;
        }

        public void setEndCallback(CompletedCallback callback) {
            this.endCallback = callback;
        }

        public CompletedCallback getEndCallback() {
            return this.endCallback;
        }

        public String charset() {
            return null;
        }

        public void write(ByteBufferList bb) {
            int canWrite = Math.min(bb.remaining(), (int) Math.min(this.bytesLeftInWriteWindow, AsyncSpdyConnection.this.bytesLeftInWriteWindow));
            if (canWrite != 0) {
                if (canWrite < bb.remaining()) {
                    if (this.writing.hasRemaining()) {
                        throw new AssertionError("wtf");
                    }
                    bb.get(this.writing, canWrite);
                    bb = this.writing;
                }
                try {
                    AsyncSpdyConnection.this.writer.data(false, this.id, bb);
                    this.bytesLeftInWriteWindow -= (long) canWrite;
                } catch (IOException e) {
                    throw new AssertionError(e);
                }
            }
        }

        public void setWriteableCallback(WritableCallback handler) {
            this.writable = handler;
        }

        public WritableCallback getWriteableCallback() {
            return this.writable;
        }

        public boolean isOpen() {
            return this.isOpen;
        }

        public void end() {
            try {
                AsyncSpdyConnection.this.writer.data(true, this.id, this.writing);
            } catch (IOException e) {
                throw new AssertionError(e);
            }
        }

        public void setClosedCallback(CompletedCallback handler) {
            this.closedCallback = handler;
        }

        public CompletedCallback getClosedCallback() {
            return this.closedCallback;
        }

        public void receiveHeaders(List<Header> headers, HeadersMode headerMode) {
            this.headers.setComplete((Object) headers);
        }
    }

    public SpdySocket newStream(List<Header> requestHeaders, boolean out, boolean in) {
        return newStream(0, requestHeaders, out, in);
    }

    private SpdySocket newStream(int associatedStreamId, List<Header> requestHeaders, boolean out, boolean in) {
        List<Header> list;
        int i = associatedStreamId;
        boolean outFinished = out ^ 1;
        boolean inFinished = in ^ 1;
        if (this.shutdown) {
            return null;
        }
        int streamId = r7.nextStreamId;
        r7.nextStreamId += 2;
        SpdySocket socket = new SpdySocket(streamId, outFinished, inFinished, requestHeaders);
        if (socket.isOpen()) {
            r7.sockets.put(Integer.valueOf(streamId), socket);
        }
        if (i == 0) {
            try {
                try {
                    r7.writer.synStream(outFinished, inFinished, streamId, i, requestHeaders);
                    list = requestHeaders;
                } catch (IOException e) {
                    e = e;
                    list = requestHeaders;
                    e = e;
                    throw new AssertionError(e);
                }
            } catch (IOException e2) {
                IOException e22;
                IOException e3;
                int i2 = streamId;
                list = requestHeaders;
                e3 = e22;
                throw new AssertionError(e3);
            }
        }
        i2 = streamId;
        if (r7.client) {
            throw new IllegalArgumentException("client streams shouldn't have associated stream IDs");
        }
        try {
            r7.writer.pushPromise(i, i2, requestHeaders);
        } catch (IOException e4) {
            e22 = e4;
            e3 = e22;
            throw new AssertionError(e3);
        }
        return socket;
    }

    void updateWindowRead(int length) {
        this.totalWindowRead += length;
        if (this.totalWindowRead >= this.okHttpSettings.getInitialWindowSize(65536) / 2) {
            try {
                this.writer.windowUpdate(0, (long) this.totalWindowRead);
                this.totalWindowRead = 0;
            } catch (IOException e) {
                throw new AssertionError(e);
            }
        }
    }

    public AsyncSpdyConnection(AsyncSocket socket, Protocol protocol) {
        int i = 1;
        this.client = true;
        this.okHttpSettings = new Settings();
        this.peerSettings = new Settings();
        this.receivedInitialPeerSettings = false;
        this.protocol = protocol;
        this.socket = socket;
        this.bufferedSocket = new BufferedDataSink(socket);
        if (protocol == Protocol.SPDY_3) {
            this.variant = new Spdy3();
        } else if (protocol == Protocol.HTTP_2) {
            this.variant = new Http20Draft13();
        }
        this.reader = this.variant.newReader(socket, this, true);
        this.writer = this.variant.newWriter(this.bufferedSocket, true);
        this.nextStreamId = 1 != null ? 1 : 2;
        if (1 != null && protocol == Protocol.HTTP_2) {
            this.nextStreamId += 2;
        }
        if (1 == null) {
            i = 2;
        }
        this.nextPingId = i;
        if (1 != null) {
            this.okHttpSettings.set(7, 0, 16777216);
        }
    }

    public void sendConnectionPreface() throws IOException {
        this.writer.connectionPreface();
        this.writer.settings(this.okHttpSettings);
        int windowSize = this.okHttpSettings.getInitialWindowSize(65536);
        if (windowSize != 65536) {
            this.writer.windowUpdate(0, (long) (windowSize - 65536));
        }
    }

    private boolean pushedStream(int streamId) {
        return this.protocol == Protocol.HTTP_2 && streamId != 0 && (streamId & 1) == 0;
    }

    public void data(boolean inFinished, int streamId, ByteBufferList source) {
        if (pushedStream(streamId)) {
            throw new AssertionError("push");
        }
        DataEmitter socket = (SpdySocket) this.sockets.get(Integer.valueOf(streamId));
        if (socket == null) {
            try {
                this.writer.rstStream(streamId, ErrorCode.INVALID_STREAM);
                source.recycle();
                return;
            } catch (IOException e) {
                throw new AssertionError(e);
            }
        }
        int length = source.remaining();
        source.get(socket.pending);
        socket.updateWindowRead(length);
        Util.emitAllData(socket, socket.pending);
        if (inFinished) {
            this.sockets.remove(Integer.valueOf(streamId));
            socket.close();
            Util.end(socket, null);
        }
    }

    public void headers(boolean outFinished, boolean inFinished, int streamId, int associatedStreamId, List<Header> headerBlock, HeadersMode headersMode) {
        if (pushedStream(streamId)) {
            throw new AssertionError("push");
        } else if (!this.shutdown) {
            DataEmitter socket = (SpdySocket) this.sockets.get(Integer.valueOf(streamId));
            if (socket == null) {
                if (headersMode.failIfStreamAbsent()) {
                    try {
                        this.writer.rstStream(streamId, ErrorCode.INVALID_STREAM);
                    } catch (IOException e) {
                        throw new AssertionError(e);
                    }
                } else if (streamId > this.lastGoodStreamId && streamId % 2 != this.nextStreamId % 2) {
                    throw new AssertionError("unexpected receive stream");
                }
            } else if (headersMode.failIfStreamPresent()) {
                try {
                    this.writer.rstStream(streamId, ErrorCode.INVALID_STREAM);
                    this.sockets.remove(Integer.valueOf(streamId));
                } catch (IOException e2) {
                    throw new AssertionError(e2);
                }
            } else {
                socket.receiveHeaders(headerBlock, headersMode);
                if (inFinished) {
                    this.sockets.remove(Integer.valueOf(streamId));
                    Util.end(socket, null);
                }
            }
        }
    }

    public void rstStream(int streamId, ErrorCode errorCode) {
        if (pushedStream(streamId)) {
            throw new AssertionError("push");
        }
        DataEmitter rstStream = (SpdySocket) this.sockets.remove(Integer.valueOf(streamId));
        if (rstStream != null) {
            Util.end(rstStream, new IOException(errorCode.toString()));
        }
    }

    public void settings(boolean clearPrevious, Settings settings) {
        long delta = 0;
        int priorWriteWindowSize = this.peerSettings.getInitialWindowSize(65536);
        if (clearPrevious) {
            this.peerSettings.clear();
        }
        this.peerSettings.merge(settings);
        try {
            this.writer.ackSettings();
            int peerInitialWindowSize = this.peerSettings.getInitialWindowSize(65536);
            if (!(peerInitialWindowSize == -1 || peerInitialWindowSize == priorWriteWindowSize)) {
                delta = (long) (peerInitialWindowSize - priorWriteWindowSize);
                if (!this.receivedInitialPeerSettings) {
                    addBytesToWriteWindow(delta);
                    this.receivedInitialPeerSettings = true;
                }
            }
            for (SpdySocket socket : this.sockets.values()) {
                socket.addBytesToWriteWindow(delta);
            }
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    void addBytesToWriteWindow(long delta) {
        this.bytesLeftInWriteWindow += delta;
        for (DataSink socket : this.sockets.values()) {
            Util.writable(socket);
        }
    }

    public void ackSettings() {
        try {
            this.writer.ackSettings();
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    private void writePing(boolean reply, int payload1, int payload2, Ping ping) throws IOException {
        if (ping != null) {
            ping.send();
        }
        this.writer.ping(reply, payload1, payload2);
    }

    private synchronized Ping removePing(int id) {
        return this.pings != null ? (Ping) this.pings.remove(Integer.valueOf(id)) : null;
    }

    public void ping(boolean ack, int payload1, int payload2) {
        if (ack) {
            Ping ping = removePing(payload1);
            if (ping != null) {
                ping.receive();
            }
            return;
        }
        try {
            writePing(true, payload1, payload2, null);
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    public void goAway(int lastGoodStreamId, ErrorCode errorCode, ByteString debugData) {
        this.shutdown = true;
        Iterator<Entry<Integer, SpdySocket>> i = this.sockets.entrySet().iterator();
        while (i.hasNext()) {
            Entry<Integer, SpdySocket> entry = (Entry) i.next();
            if (((Integer) entry.getKey()).intValue() > lastGoodStreamId && ((SpdySocket) entry.getValue()).isLocallyInitiated()) {
                Util.end((DataEmitter) entry.getValue(), new IOException(ErrorCode.REFUSED_STREAM.toString()));
                i.remove();
            }
        }
    }

    public void windowUpdate(int streamId, long windowSizeIncrement) {
        if (streamId == 0) {
            addBytesToWriteWindow(windowSizeIncrement);
            return;
        }
        SpdySocket socket = (SpdySocket) this.sockets.get(Integer.valueOf(streamId));
        if (socket != null) {
            socket.addBytesToWriteWindow(windowSizeIncrement);
        }
    }

    public void priority(int streamId, int streamDependency, int weight, boolean exclusive) {
    }

    public void pushPromise(int streamId, int promisedStreamId, List<Header> list) {
        throw new AssertionError("pushPromise");
    }

    public void alternateService(int streamId, String origin, ByteString protocol, String host, int port, long maxAge) {
    }

    public void error(Exception e) {
        this.socket.close();
        Iterator<Entry<Integer, SpdySocket>> i = this.sockets.entrySet().iterator();
        while (i.hasNext()) {
            Util.end((DataEmitter) ((Entry) i.next()).getValue(), e);
            i.remove();
        }
    }
}
