package com.squareup.okhttp.internal.http;

import com.squareup.okhttp.Connection;
import com.squareup.okhttp.ConnectionPool;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.Headers.Builder;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.internal.Internal;
import com.squareup.okhttp.internal.Util;
import java.io.EOFException;
import java.io.IOException;
import java.net.ProtocolException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import okio.Sink;
import okio.Source;
import okio.Timeout;

public final class HttpConnection {
    private static final int ON_IDLE_CLOSE = 2;
    private static final int ON_IDLE_HOLD = 0;
    private static final int ON_IDLE_POOL = 1;
    private static final int STATE_CLOSED = 6;
    private static final int STATE_IDLE = 0;
    private static final int STATE_OPEN_REQUEST_BODY = 1;
    private static final int STATE_OPEN_RESPONSE_BODY = 4;
    private static final int STATE_READING_RESPONSE_BODY = 5;
    private static final int STATE_READ_RESPONSE_HEADERS = 3;
    private static final int STATE_WRITING_REQUEST_BODY = 2;
    private final Connection connection;
    private int onIdle = 0;
    private final ConnectionPool pool;
    private final BufferedSink sink;
    private final Socket socket;
    private final BufferedSource source;
    private int state = 0;

    private abstract class AbstractSource implements Source {
        protected boolean closed;

        private AbstractSource() {
        }

        public Timeout timeout() {
            return HttpConnection.this.source.timeout();
        }

        protected final void endOfInput(boolean recyclable) throws IOException {
            if (HttpConnection.this.state != 5) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("state: ");
                stringBuilder.append(HttpConnection.this.state);
                throw new IllegalStateException(stringBuilder.toString());
            }
            HttpConnection.this.state = 0;
            if (recyclable && HttpConnection.this.onIdle == 1) {
                HttpConnection.this.onIdle = 0;
                Internal.instance.recycle(HttpConnection.this.pool, HttpConnection.this.connection);
            } else if (HttpConnection.this.onIdle == 2) {
                HttpConnection.this.state = 6;
                HttpConnection.this.connection.getSocket().close();
            }
        }

        protected final void unexpectedEndOfInput() {
            Util.closeQuietly(HttpConnection.this.connection.getSocket());
            HttpConnection.this.state = 6;
        }
    }

    private final class ChunkedSink implements Sink {
        private boolean closed;

        private ChunkedSink() {
        }

        public Timeout timeout() {
            return HttpConnection.this.sink.timeout();
        }

        public void write(Buffer source, long byteCount) throws IOException {
            if (this.closed) {
                throw new IllegalStateException("closed");
            } else if (byteCount != 0) {
                HttpConnection.this.sink.writeHexadecimalUnsignedLong(byteCount);
                HttpConnection.this.sink.writeUtf8("\r\n");
                HttpConnection.this.sink.write(source, byteCount);
                HttpConnection.this.sink.writeUtf8("\r\n");
            }
        }

        public synchronized void flush() throws IOException {
            if (!this.closed) {
                HttpConnection.this.sink.flush();
            }
        }

        public synchronized void close() throws IOException {
            if (!this.closed) {
                this.closed = true;
                HttpConnection.this.sink.writeUtf8("0\r\n\r\n");
                HttpConnection.this.state = 3;
            }
        }
    }

    private final class FixedLengthSink implements Sink {
        private long bytesRemaining;
        private boolean closed;

        private FixedLengthSink(long bytesRemaining) {
            this.bytesRemaining = bytesRemaining;
        }

        public Timeout timeout() {
            return HttpConnection.this.sink.timeout();
        }

        public void write(Buffer source, long byteCount) throws IOException {
            if (this.closed) {
                throw new IllegalStateException("closed");
            }
            Util.checkOffsetAndCount(source.size(), 0, byteCount);
            if (byteCount > this.bytesRemaining) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("expected ");
                stringBuilder.append(this.bytesRemaining);
                stringBuilder.append(" bytes but received ");
                stringBuilder.append(byteCount);
                throw new ProtocolException(stringBuilder.toString());
            }
            HttpConnection.this.sink.write(source, byteCount);
            this.bytesRemaining -= byteCount;
        }

        public void flush() throws IOException {
            if (!this.closed) {
                HttpConnection.this.sink.flush();
            }
        }

        public void close() throws IOException {
            if (!this.closed) {
                this.closed = true;
                if (this.bytesRemaining > 0) {
                    throw new ProtocolException("unexpected end of stream");
                }
                HttpConnection.this.state = 3;
            }
        }
    }

    private class ChunkedSource extends AbstractSource {
        private static final long NO_CHUNK_YET = -1;
        private long bytesRemainingInChunk = -1;
        private boolean hasMoreChunks = true;
        private final HttpEngine httpEngine;

        ChunkedSource(HttpEngine httpEngine) throws IOException {
            super();
            this.httpEngine = httpEngine;
        }

        public long read(Buffer sink, long byteCount) throws IOException {
            if (byteCount < 0) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("byteCount < 0: ");
                stringBuilder.append(byteCount);
                throw new IllegalArgumentException(stringBuilder.toString());
            } else if (this.closed) {
                throw new IllegalStateException("closed");
            } else if (!this.hasMoreChunks) {
                return -1;
            } else {
                if (this.bytesRemainingInChunk == 0 || this.bytesRemainingInChunk == -1) {
                    readChunkSize();
                    if (!this.hasMoreChunks) {
                        return -1;
                    }
                }
                long read = HttpConnection.this.source.read(sink, Math.min(byteCount, this.bytesRemainingInChunk));
                if (read == -1) {
                    unexpectedEndOfInput();
                    throw new IOException("unexpected end of stream");
                }
                this.bytesRemainingInChunk -= read;
                return read;
            }
        }

        private void readChunkSize() throws IOException {
            if (this.bytesRemainingInChunk != -1) {
                HttpConnection.this.source.readUtf8LineStrict();
            }
            try {
                this.bytesRemainingInChunk = HttpConnection.this.source.readHexadecimalUnsignedLong();
                String extensions = HttpConnection.this.source.readUtf8LineStrict().trim();
                if (this.bytesRemainingInChunk >= 0) {
                    if (extensions.isEmpty() || extensions.startsWith(";")) {
                        if (this.bytesRemainingInChunk == 0) {
                            this.hasMoreChunks = false;
                            extensions = new Builder();
                            HttpConnection.this.readHeaders(extensions);
                            this.httpEngine.receiveHeaders(extensions.build());
                            endOfInput(true);
                            return;
                        }
                        return;
                    }
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("expected chunk size and optional extensions but was \"");
                stringBuilder.append(this.bytesRemainingInChunk);
                stringBuilder.append(extensions);
                stringBuilder.append("\"");
                throw new ProtocolException(stringBuilder.toString());
            } catch (NumberFormatException e) {
                throw new ProtocolException(e.getMessage());
            }
        }

        public void close() throws IOException {
            if (!this.closed) {
                if (this.hasMoreChunks && !Util.discard(this, 100, TimeUnit.MILLISECONDS)) {
                    unexpectedEndOfInput();
                }
                this.closed = true;
            }
        }
    }

    private class FixedLengthSource extends AbstractSource {
        private long bytesRemaining;

        public FixedLengthSource(long length) throws IOException {
            super();
            this.bytesRemaining = length;
            if (this.bytesRemaining == 0) {
                endOfInput(true);
            }
        }

        public long read(Buffer sink, long byteCount) throws IOException {
            if (byteCount < 0) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("byteCount < 0: ");
                stringBuilder.append(byteCount);
                throw new IllegalArgumentException(stringBuilder.toString());
            } else if (this.closed) {
                throw new IllegalStateException("closed");
            } else if (this.bytesRemaining == 0) {
                return -1;
            } else {
                long read = HttpConnection.this.source.read(sink, Math.min(this.bytesRemaining, byteCount));
                if (read == -1) {
                    unexpectedEndOfInput();
                    throw new ProtocolException("unexpected end of stream");
                }
                this.bytesRemaining -= read;
                if (this.bytesRemaining == 0) {
                    endOfInput(true);
                }
                return read;
            }
        }

        public void close() throws IOException {
            if (!this.closed) {
                if (!(this.bytesRemaining == 0 || Util.discard(this, 100, TimeUnit.MILLISECONDS))) {
                    unexpectedEndOfInput();
                }
                this.closed = true;
            }
        }
    }

    private class UnknownLengthSource extends AbstractSource {
        private boolean inputExhausted;

        private UnknownLengthSource() {
            super();
        }

        public long read(Buffer sink, long byteCount) throws IOException {
            if (byteCount < 0) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("byteCount < 0: ");
                stringBuilder.append(byteCount);
                throw new IllegalArgumentException(stringBuilder.toString());
            } else if (this.closed) {
                throw new IllegalStateException("closed");
            } else if (this.inputExhausted) {
                return -1;
            } else {
                long read = HttpConnection.this.source.read(sink, byteCount);
                if (read != -1) {
                    return read;
                }
                this.inputExhausted = true;
                endOfInput(false);
                return -1;
            }
        }

        public void close() throws IOException {
            if (!this.closed) {
                if (!this.inputExhausted) {
                    unexpectedEndOfInput();
                }
                this.closed = true;
            }
        }
    }

    public HttpConnection(ConnectionPool pool, Connection connection, Socket socket) throws IOException {
        this.pool = pool;
        this.connection = connection;
        this.socket = socket;
        this.source = Okio.buffer(Okio.source(socket));
        this.sink = Okio.buffer(Okio.sink(socket));
    }

    public void setTimeouts(int readTimeoutMillis, int writeTimeoutMillis) {
        if (readTimeoutMillis != 0) {
            this.source.timeout().timeout((long) readTimeoutMillis, TimeUnit.MILLISECONDS);
        }
        if (writeTimeoutMillis != 0) {
            this.sink.timeout().timeout((long) writeTimeoutMillis, TimeUnit.MILLISECONDS);
        }
    }

    public void poolOnIdle() {
        this.onIdle = 1;
        if (this.state == 0) {
            this.onIdle = 0;
            Internal.instance.recycle(this.pool, this.connection);
        }
    }

    public void closeOnIdle() throws IOException {
        this.onIdle = 2;
        if (this.state == 0) {
            this.state = 6;
            this.connection.getSocket().close();
        }
    }

    public boolean isClosed() {
        return this.state == 6;
    }

    public void closeIfOwnedBy(Object owner) throws IOException {
        Internal.instance.closeIfOwnedBy(this.connection, owner);
    }

    public void flush() throws IOException {
        this.sink.flush();
    }

    public long bufferSize() {
        return this.source.buffer().size();
    }

    public boolean isReadable() {
        int readTimeout;
        try {
            readTimeout = this.socket.getSoTimeout();
            this.socket.setSoTimeout(1);
            if (this.source.exhausted()) {
                this.socket.setSoTimeout(readTimeout);
                return false;
            }
            this.socket.setSoTimeout(readTimeout);
            return true;
        } catch (SocketTimeoutException e) {
            return true;
        } catch (IOException e2) {
            return false;
        } catch (Throwable th) {
            this.socket.setSoTimeout(readTimeout);
        }
    }

    public void writeRequest(Headers headers, String requestLine) throws IOException {
        if (this.state != 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("state: ");
            stringBuilder.append(this.state);
            throw new IllegalStateException(stringBuilder.toString());
        }
        this.sink.writeUtf8(requestLine).writeUtf8("\r\n");
        int size = headers.size();
        for (int i = 0; i < size; i++) {
            this.sink.writeUtf8(headers.name(i)).writeUtf8(": ").writeUtf8(headers.value(i)).writeUtf8("\r\n");
        }
        this.sink.writeUtf8("\r\n");
        this.state = 1;
    }

    public Response.Builder readResponse() throws IOException {
        if (this.state == 1 || this.state == 3) {
            while (true) {
                try {
                    StatusLine statusLine = StatusLine.parse(this.source.readUtf8LineStrict());
                    Response.Builder responseBuilder = new Response.Builder().protocol(statusLine.protocol).code(statusLine.code).message(statusLine.message);
                    Builder headersBuilder = new Builder();
                    readHeaders(headersBuilder);
                    headersBuilder.add(OkHeaders.SELECTED_PROTOCOL, statusLine.protocol.toString());
                    responseBuilder.headers(headersBuilder.build());
                    if (statusLine.code != 100) {
                        this.state = 4;
                        return responseBuilder;
                    }
                } catch (EOFException e) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("unexpected end of stream on ");
                    stringBuilder.append(this.connection);
                    stringBuilder.append(" (recycle count=");
                    stringBuilder.append(Internal.instance.recycleCount(this.connection));
                    stringBuilder.append(")");
                    IOException exception = new IOException(stringBuilder.toString());
                    exception.initCause(e);
                    throw exception;
                }
            }
        }
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("state: ");
        stringBuilder2.append(this.state);
        throw new IllegalStateException(stringBuilder2.toString());
    }

    public void readHeaders(Builder builder) throws IOException {
        while (true) {
            String readUtf8LineStrict = this.source.readUtf8LineStrict();
            String line = readUtf8LineStrict;
            if (readUtf8LineStrict.length() != 0) {
                Internal.instance.addLenient(builder, line);
            } else {
                return;
            }
        }
    }

    public Sink newChunkedSink() {
        if (this.state != 1) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("state: ");
            stringBuilder.append(this.state);
            throw new IllegalStateException(stringBuilder.toString());
        }
        this.state = 2;
        return new ChunkedSink();
    }

    public Sink newFixedLengthSink(long contentLength) {
        if (this.state != 1) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("state: ");
            stringBuilder.append(this.state);
            throw new IllegalStateException(stringBuilder.toString());
        }
        this.state = 2;
        return new FixedLengthSink(contentLength);
    }

    public void writeRequestBody(RetryableSink requestBody) throws IOException {
        if (this.state != 1) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("state: ");
            stringBuilder.append(this.state);
            throw new IllegalStateException(stringBuilder.toString());
        }
        this.state = 3;
        requestBody.writeToSocket(this.sink);
    }

    public Source newFixedLengthSource(long length) throws IOException {
        if (this.state != 4) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("state: ");
            stringBuilder.append(this.state);
            throw new IllegalStateException(stringBuilder.toString());
        }
        this.state = 5;
        return new FixedLengthSource(length);
    }

    public Source newChunkedSource(HttpEngine httpEngine) throws IOException {
        if (this.state != 4) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("state: ");
            stringBuilder.append(this.state);
            throw new IllegalStateException(stringBuilder.toString());
        }
        this.state = 5;
        return new ChunkedSource(httpEngine);
    }

    public Source newUnknownLengthSource() throws IOException {
        if (this.state != 4) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("state: ");
            stringBuilder.append(this.state);
            throw new IllegalStateException(stringBuilder.toString());
        }
        this.state = 5;
        return new UnknownLengthSource();
    }
}
