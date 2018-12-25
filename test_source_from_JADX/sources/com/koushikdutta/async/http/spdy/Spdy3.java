package com.koushikdutta.async.http.spdy;

import android.os.Build.VERSION;
import android.support.v4.internal.view.SupportMenu;
import android.support.v4.view.ViewCompat;
import com.koushikdutta.async.BufferedDataSink;
import com.koushikdutta.async.ByteBufferList;
import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.DataEmitterReader;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.callback.DataCallback;
import com.koushikdutta.async.http.Protocol;
import com.koushikdutta.async.http.spdy.FrameReader.Handler;
import com.koushikdutta.async.util.Charsets;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.ProtocolException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;
import java.util.Locale;
import java.util.zip.Deflater;

final class Spdy3 implements Variant {
    static final byte[] DICTIONARY;
    static final int FLAG_FIN = 1;
    static final int FLAG_UNIDIRECTIONAL = 2;
    static final int TYPE_DATA = 0;
    static final int TYPE_GOAWAY = 7;
    static final int TYPE_HEADERS = 8;
    static final int TYPE_PING = 6;
    static final int TYPE_RST_STREAM = 3;
    static final int TYPE_SETTINGS = 4;
    static final int TYPE_SYN_REPLY = 2;
    static final int TYPE_SYN_STREAM = 1;
    static final int TYPE_WINDOW_UPDATE = 9;
    static final int VERSION = 3;

    static final class Reader implements FrameReader {
        private final boolean client;
        private final DataEmitter emitter;
        private final ByteBufferList emptyList = new ByteBufferList();
        int flags;
        private final Handler handler;
        private final HeaderReader headerReader = new HeaderReader();
        boolean inFinished;
        int length;
        private final DataCallback onDataFrame = new C11943();
        private final DataCallback onFrame = new C11932();
        private final DataCallback onFullFrame = new C11954();
        ByteBufferList partial = new ByteBufferList();
        private final DataEmitterReader reader;
        int streamId;
        int w1;
        int w2;

        /* renamed from: com.koushikdutta.async.http.spdy.Spdy3$Reader$1 */
        class C11921 implements CompletedCallback {
            C11921() {
            }

            public void onCompleted(Exception ex) {
            }
        }

        /* renamed from: com.koushikdutta.async.http.spdy.Spdy3$Reader$2 */
        class C11932 implements DataCallback {
            C11932() {
            }

            public void onDataAvailable(DataEmitter emitter, ByteBufferList bb) {
                bb.order(ByteOrder.BIG_ENDIAN);
                Reader.this.w1 = bb.getInt();
                Reader.this.w2 = bb.getInt();
                boolean z = false;
                boolean control = (Reader.this.w1 & Integer.MIN_VALUE) != 0;
                Reader.this.flags = (Reader.this.w2 & ViewCompat.MEASURED_STATE_MASK) >>> 24;
                Reader.this.length = Reader.this.w2 & ViewCompat.MEASURED_SIZE_MASK;
                if (control) {
                    Reader.this.reader.read(Reader.this.length, Reader.this.onFullFrame);
                    return;
                }
                Reader.this.streamId = Reader.this.w1 & Integer.MAX_VALUE;
                Reader reader = Reader.this;
                if ((Reader.this.flags & 1) != 0) {
                    z = true;
                }
                reader.inFinished = z;
                emitter.setDataCallback(Reader.this.onDataFrame);
                if (Reader.this.length == 0) {
                    Reader.this.onDataFrame.onDataAvailable(emitter, Reader.this.emptyList);
                }
            }
        }

        /* renamed from: com.koushikdutta.async.http.spdy.Spdy3$Reader$3 */
        class C11943 implements DataCallback {
            C11943() {
            }

            public void onDataAvailable(DataEmitter emitter, ByteBufferList bb) {
                int toRead = Math.min(bb.remaining(), Reader.this.length);
                if (toRead < bb.remaining()) {
                    bb.get(Reader.this.partial, toRead);
                    bb = Reader.this.partial;
                }
                Reader reader = Reader.this;
                reader.length -= toRead;
                Handler access$400 = Reader.this.handler;
                boolean z = Reader.this.length == 0 && Reader.this.inFinished;
                access$400.data(z, Reader.this.streamId, bb);
                if (Reader.this.length == 0) {
                    Reader.this.parseFrameHeader();
                }
            }
        }

        /* renamed from: com.koushikdutta.async.http.spdy.Spdy3$Reader$4 */
        class C11954 implements DataCallback {
            C11954() {
            }

            public void onDataAvailable(DataEmitter emitter, ByteBufferList bb) {
                bb.order(ByteOrder.BIG_ENDIAN);
                int version = (Reader.this.w1 & 2147418112) >>> 16;
                int type = Reader.this.w1 & SupportMenu.USER_MASK;
                if (version != 3) {
                    try {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("version != 3: ");
                        stringBuilder.append(version);
                        throw new ProtocolException(stringBuilder.toString());
                    } catch (IOException e) {
                        Reader.this.handler.error(e);
                        return;
                    }
                }
                switch (type) {
                    case 1:
                        Reader.this.readSynStream(bb, Reader.this.flags, Reader.this.length);
                        break;
                    case 2:
                        Reader.this.readSynReply(bb, Reader.this.flags, Reader.this.length);
                        break;
                    case 3:
                        Reader.this.readRstStream(bb, Reader.this.flags, Reader.this.length);
                        break;
                    case 4:
                        Reader.this.readSettings(bb, Reader.this.flags, Reader.this.length);
                        break;
                    case 6:
                        Reader.this.readPing(bb, Reader.this.flags, Reader.this.length);
                        break;
                    case 7:
                        Reader.this.readGoAway(bb, Reader.this.flags, Reader.this.length);
                        break;
                    case 8:
                        Reader.this.readHeaders(bb, Reader.this.flags, Reader.this.length);
                        break;
                    case 9:
                        Reader.this.readWindowUpdate(bb, Reader.this.flags, Reader.this.length);
                        break;
                    default:
                        bb.recycle();
                        break;
                }
                Reader.this.parseFrameHeader();
            }
        }

        Reader(DataEmitter emitter, Handler handler, boolean client) {
            this.emitter = emitter;
            this.handler = handler;
            this.client = client;
            emitter.setEndCallback(new C11921());
            this.reader = new DataEmitterReader();
            parseFrameHeader();
        }

        private void parseFrameHeader() {
            this.emitter.setDataCallback(this.reader);
            this.reader.read(8, this.onFrame);
        }

        private void readSynStream(ByteBufferList source, int flags, int length) throws IOException {
            int streamId = source.getInt() & Integer.MAX_VALUE;
            int associatedStreamId = Integer.MAX_VALUE & source.getInt();
            source.getShort();
            List<Header> headerBlock = this.headerReader.readHeader(source, length - 10);
            boolean outFinished = false;
            boolean inFinished = (flags & 1) != 0;
            if ((flags & 2) != 0) {
                outFinished = true;
            }
            this.handler.headers(outFinished, inFinished, streamId, associatedStreamId, headerBlock, HeadersMode.SPDY_SYN_STREAM);
        }

        private void readSynReply(ByteBufferList source, int flags, int length) throws IOException {
            this.handler.headers(false, (flags & 1) != 0, Integer.MAX_VALUE & source.getInt(), -1, this.headerReader.readHeader(source, length - 4), HeadersMode.SPDY_REPLY);
        }

        private void readRstStream(ByteBufferList source, int flags, int length) throws IOException {
            if (length != 8) {
                throw ioException("TYPE_RST_STREAM length: %d != 8", Integer.valueOf(length));
            }
            int streamId = source.getInt() & Integer.MAX_VALUE;
            ErrorCode errorCode = ErrorCode.fromSpdy3Rst(source.getInt());
            if (errorCode == null) {
                throw ioException("TYPE_RST_STREAM unexpected error code: %d", Integer.valueOf(errorCodeInt));
            } else {
                this.handler.rstStream(streamId, errorCode);
            }
        }

        private void readHeaders(ByteBufferList source, int flags, int length) throws IOException {
            int streamId = Integer.MAX_VALUE & source.getInt();
            this.handler.headers(false, false, streamId, -1, this.headerReader.readHeader(source, length - 4), HeadersMode.SPDY_HEADERS);
        }

        private void readWindowUpdate(ByteBufferList source, int flags, int length) throws IOException {
            if (length != 8) {
                throw ioException("TYPE_WINDOW_UPDATE length: %d != 8", Integer.valueOf(length));
            }
            int streamId = source.getInt() & Integer.MAX_VALUE;
            long increment = (long) (Integer.MAX_VALUE & source.getInt());
            if (increment == 0) {
                throw ioException("windowSizeIncrement was 0", Long.valueOf(increment));
            } else {
                this.handler.windowUpdate(streamId, increment);
            }
        }

        private void readPing(ByteBufferList source, int flags, int length) throws IOException {
            boolean ack = true;
            if (length != 4) {
                throw ioException("TYPE_PING length: %d != 4", Integer.valueOf(length));
            }
            int id = source.getInt();
            if (this.client != ((id & 1) == 1)) {
                ack = false;
            }
            this.handler.ping(ack, id, 0);
        }

        private void readGoAway(ByteBufferList source, int flags, int length) throws IOException {
            if (length != 8) {
                throw ioException("TYPE_GOAWAY length: %d != 8", Integer.valueOf(length));
            }
            int lastGoodStreamId = source.getInt() & Integer.MAX_VALUE;
            ErrorCode errorCode = ErrorCode.fromSpdyGoAway(source.getInt());
            if (errorCode == null) {
                throw ioException("TYPE_GOAWAY unexpected error code: %d", Integer.valueOf(errorCodeInt));
            } else {
                this.handler.goAway(lastGoodStreamId, errorCode, ByteString.EMPTY);
            }
        }

        private void readSettings(ByteBufferList source, int flags, int length) throws IOException {
            int numberOfEntries = source.getInt();
            boolean clearPrevious = false;
            if (length != (numberOfEntries * 8) + 4) {
                throw ioException("TYPE_SETTINGS length: %d != 4 + 8 * %d", Integer.valueOf(length), Integer.valueOf(numberOfEntries));
            }
            Settings settings = new Settings();
            for (int i = 0; i < numberOfEntries; i++) {
                int w1 = source.getInt();
                int id = ViewCompat.MEASURED_SIZE_MASK & w1;
                settings.set(id, (ViewCompat.MEASURED_STATE_MASK & w1) >>> 24, source.getInt());
            }
            if ((flags & 1) != 0) {
                clearPrevious = true;
            }
            this.handler.settings(clearPrevious, settings);
        }

        private static IOException ioException(String message, Object... args) throws IOException {
            throw new IOException(String.format(Locale.ENGLISH, message, args));
        }
    }

    static final class Writer implements FrameWriter {
        private final boolean client;
        private boolean closed;
        ByteBufferList dataList = new ByteBufferList();
        private final Deflater deflater = new Deflater();
        private ByteBufferList frameHeader = new ByteBufferList();
        ByteBufferList headerBlockList = new ByteBufferList();
        private final BufferedDataSink sink;

        Writer(BufferedDataSink sink, boolean client) {
            this.sink = sink;
            this.client = client;
            this.deflater.setDictionary(Spdy3.DICTIONARY);
        }

        public void ackSettings() {
        }

        public void pushPromise(int streamId, int promisedStreamId, List<Header> list) throws IOException {
        }

        public synchronized void connectionPreface() {
        }

        public synchronized void synStream(boolean outFinished, boolean inFinished, int streamId, int associatedStreamId, List<Header> headerBlock) throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            }
            ByteBufferList headerBlockBuffer = writeNameValueBlockToBuffer(headerBlock);
            int length = headerBlockBuffer.remaining() + 10;
            int flags = (inFinished ? 2 : 0) | outFinished;
            ByteBuffer sink = ByteBufferList.obtain(256).order(ByteOrder.BIG_ENDIAN);
            sink.putInt(-2147287040 | (SupportMenu.USER_MASK & 1));
            sink.putInt(((flags & 255) << 24) | (ViewCompat.MEASURED_SIZE_MASK & length));
            sink.putInt(streamId & Integer.MAX_VALUE);
            sink.putInt(Integer.MAX_VALUE & associatedStreamId);
            sink.putShort((short) ((((0 & 7) << 13) | ((0 & 31) << 8)) | (0 & 255)));
            sink.flip();
            this.sink.write(this.frameHeader.add(sink).add(headerBlockBuffer));
        }

        public synchronized void synReply(boolean outFinished, int streamId, List<Header> headerBlock) throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            }
            ByteBufferList headerBlockBuffer = writeNameValueBlockToBuffer(headerBlock);
            boolean flags = outFinished;
            int length = headerBlockBuffer.remaining() + 4;
            ByteBuffer sink = ByteBufferList.obtain(256).order(ByteOrder.BIG_ENDIAN);
            sink.putInt(-2147287040 | (SupportMenu.USER_MASK & 2));
            sink.putInt(((flags & 255) << 24) | (ViewCompat.MEASURED_SIZE_MASK & length));
            sink.putInt(Integer.MAX_VALUE & streamId);
            sink.flip();
            this.sink.write(this.frameHeader.add(sink).add(headerBlockBuffer));
        }

        public synchronized void headers(int streamId, List<Header> headerBlock) throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            }
            ByteBufferList headerBlockBuffer = writeNameValueBlockToBuffer(headerBlock);
            int length = headerBlockBuffer.remaining() + 4;
            ByteBuffer sink = ByteBufferList.obtain(256).order(ByteOrder.BIG_ENDIAN);
            sink.putInt(-2147287040 | (SupportMenu.USER_MASK & 8));
            sink.putInt(((0 & 255) << 24) | (ViewCompat.MEASURED_SIZE_MASK & length));
            sink.putInt(Integer.MAX_VALUE & streamId);
            sink.flip();
            this.sink.write(this.frameHeader.add(sink).add(headerBlockBuffer));
        }

        public synchronized void rstStream(int streamId, ErrorCode errorCode) throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            } else if (errorCode.spdyRstCode == -1) {
                throw new IllegalArgumentException();
            } else {
                ByteBuffer sink = ByteBufferList.obtain(256).order(ByteOrder.BIG_ENDIAN);
                sink.putInt(-2147287040 | (SupportMenu.USER_MASK & 3));
                sink.putInt(((0 & 255) << 24) | (ViewCompat.MEASURED_SIZE_MASK & 8));
                sink.putInt(Integer.MAX_VALUE & streamId);
                sink.putInt(errorCode.spdyRstCode);
                sink.flip();
                this.sink.write(this.frameHeader.addAll(sink));
            }
        }

        public synchronized void data(boolean outFinished, int streamId, ByteBufferList source) throws IOException {
            sendDataFrame(streamId, outFinished, source);
        }

        void sendDataFrame(int streamId, int flags, ByteBufferList buffer) throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            }
            int byteCount = buffer.remaining();
            if (((long) byteCount) > 16777215) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("FRAME_TOO_LARGE max size is 16Mib: ");
                stringBuilder.append(byteCount);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            ByteBuffer sink = ByteBufferList.obtain(256).order(ByteOrder.BIG_ENDIAN);
            sink.putInt(Integer.MAX_VALUE & streamId);
            sink.putInt(((flags & 255) << 24) | (ViewCompat.MEASURED_SIZE_MASK & byteCount));
            sink.flip();
            this.dataList.add(sink).add(buffer);
            this.sink.write(this.dataList);
        }

        private ByteBufferList writeNameValueBlockToBuffer(List<Header> headerBlock) throws IOException {
            if (this.headerBlockList.hasRemaining()) {
                throw new IllegalStateException();
            }
            ByteBuffer headerBlockOut = ByteBufferList.obtain(8192).order(ByteOrder.BIG_ENDIAN);
            headerBlockOut.putInt(headerBlock.size());
            int size = headerBlock.size();
            for (int i = 0; i < size; i++) {
                ByteString name = ((Header) headerBlock.get(i)).name;
                headerBlockOut.putInt(name.size());
                headerBlockOut.put(name.toByteArray());
                ByteString value = ((Header) headerBlock.get(i)).value;
                headerBlockOut.putInt(value.size());
                headerBlockOut.put(value.toByteArray());
                if (headerBlockOut.remaining() < headerBlockOut.capacity() / 2) {
                    ByteBuffer newOut = ByteBufferList.obtain(headerBlockOut.capacity() * 2).order(ByteOrder.BIG_ENDIAN);
                    headerBlockOut.flip();
                    newOut.put(headerBlockOut);
                    ByteBufferList.reclaim(headerBlockOut);
                    headerBlockOut = newOut;
                }
            }
            headerBlockOut.flip();
            this.deflater.setInput(headerBlockOut.array(), 0, headerBlockOut.remaining());
            while (!this.deflater.needsInput()) {
                ByteBuffer deflated = ByteBufferList.obtain(headerBlockOut.capacity()).order(ByteOrder.BIG_ENDIAN);
                if (VERSION.SDK_INT >= 19) {
                    size = this.deflater.deflate(deflated.array(), 0, deflated.capacity(), 2);
                } else {
                    size = this.deflater.deflate(deflated.array(), 0, deflated.capacity());
                }
                deflated.limit(size);
                this.headerBlockList.add(deflated);
            }
            ByteBufferList.reclaim(headerBlockOut);
            return this.headerBlockList;
        }

        public synchronized void settings(Settings settings) throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            }
            int size = settings.size();
            int length = (size * 8) + 4;
            ByteBuffer sink = ByteBufferList.obtain(256).order(ByteOrder.BIG_ENDIAN);
            sink.putInt(-2147287040 | (SupportMenu.USER_MASK & 4));
            sink.putInt(((0 & 255) << 24) | (length & ViewCompat.MEASURED_SIZE_MASK));
            sink.putInt(size);
            for (int i = 0; i <= 10; i++) {
                if (settings.isSet(i)) {
                    sink.putInt(((settings.flags(i) & 255) << 24) | (i & ViewCompat.MEASURED_SIZE_MASK));
                    sink.putInt(settings.get(i));
                }
            }
            sink.flip();
            this.sink.write(this.frameHeader.addAll(sink));
        }

        public synchronized void ping(boolean reply, int payload1, int payload2) throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            }
            if (reply != (this.client != ((payload1 & 1) == 1))) {
                throw new IllegalArgumentException("payload != reply");
            }
            ByteBuffer sink = ByteBufferList.obtain(256).order(ByteOrder.BIG_ENDIAN);
            sink.putInt(-2147287040 | (SupportMenu.USER_MASK & 6));
            sink.putInt(((0 & 255) << 24) | (ViewCompat.MEASURED_SIZE_MASK & 4));
            sink.putInt(payload1);
            sink.flip();
            this.sink.write(this.frameHeader.addAll(sink));
        }

        public synchronized void goAway(int lastGoodStreamId, ErrorCode errorCode, byte[] ignored) throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            } else if (errorCode.spdyGoAwayCode == -1) {
                throw new IllegalArgumentException("errorCode.spdyGoAwayCode == -1");
            } else {
                ByteBuffer sink = ByteBufferList.obtain(256).order(ByteOrder.BIG_ENDIAN);
                sink.putInt(-2147287040 | (SupportMenu.USER_MASK & 7));
                sink.putInt(((0 & 255) << 24) | (ViewCompat.MEASURED_SIZE_MASK & 8));
                sink.putInt(lastGoodStreamId);
                sink.putInt(errorCode.spdyGoAwayCode);
                sink.flip();
                this.sink.write(this.frameHeader.addAll(sink));
            }
        }

        public synchronized void windowUpdate(int streamId, long increment) throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            }
            if (increment != 0) {
                if (increment <= 2147483647L) {
                    ByteBuffer sink = ByteBufferList.obtain(256).order(ByteOrder.BIG_ENDIAN);
                    sink.putInt(-2147287040 | (SupportMenu.USER_MASK & 9));
                    sink.putInt(((0 & 255) << 24) | (ViewCompat.MEASURED_SIZE_MASK & 8));
                    sink.putInt(streamId);
                    sink.putInt((int) increment);
                    sink.flip();
                    this.sink.write(this.frameHeader.addAll(sink));
                }
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("windowSizeIncrement must be between 1 and 0x7fffffff: ");
            stringBuilder.append(increment);
            throw new IllegalArgumentException(stringBuilder.toString());
        }

        public synchronized void close() throws IOException {
            this.closed = true;
        }
    }

    Spdy3() {
    }

    public Protocol getProtocol() {
        return Protocol.SPDY_3;
    }

    static {
        try {
            DICTIONARY = "\u0000\u0000\u0000\u0007options\u0000\u0000\u0000\u0004head\u0000\u0000\u0000\u0004post\u0000\u0000\u0000\u0003put\u0000\u0000\u0000\u0006delete\u0000\u0000\u0000\u0005trace\u0000\u0000\u0000\u0006accept\u0000\u0000\u0000\u000eaccept-charset\u0000\u0000\u0000\u000faccept-encoding\u0000\u0000\u0000\u000faccept-language\u0000\u0000\u0000\raccept-ranges\u0000\u0000\u0000\u0003age\u0000\u0000\u0000\u0005allow\u0000\u0000\u0000\rauthorization\u0000\u0000\u0000\rcache-control\u0000\u0000\u0000\nconnection\u0000\u0000\u0000\fcontent-base\u0000\u0000\u0000\u0010content-encoding\u0000\u0000\u0000\u0010content-language\u0000\u0000\u0000\u000econtent-length\u0000\u0000\u0000\u0010content-location\u0000\u0000\u0000\u000bcontent-md5\u0000\u0000\u0000\rcontent-range\u0000\u0000\u0000\fcontent-type\u0000\u0000\u0000\u0004date\u0000\u0000\u0000\u0004etag\u0000\u0000\u0000\u0006expect\u0000\u0000\u0000\u0007expires\u0000\u0000\u0000\u0004from\u0000\u0000\u0000\u0004host\u0000\u0000\u0000\bif-match\u0000\u0000\u0000\u0011if-modified-since\u0000\u0000\u0000\rif-none-match\u0000\u0000\u0000\bif-range\u0000\u0000\u0000\u0013if-unmodified-since\u0000\u0000\u0000\rlast-modified\u0000\u0000\u0000\blocation\u0000\u0000\u0000\fmax-forwards\u0000\u0000\u0000\u0006pragma\u0000\u0000\u0000\u0012proxy-authenticate\u0000\u0000\u0000\u0013proxy-authorization\u0000\u0000\u0000\u0005range\u0000\u0000\u0000\u0007referer\u0000\u0000\u0000\u000bretry-after\u0000\u0000\u0000\u0006server\u0000\u0000\u0000\u0002te\u0000\u0000\u0000\u0007trailer\u0000\u0000\u0000\u0011transfer-encoding\u0000\u0000\u0000\u0007upgrade\u0000\u0000\u0000\nuser-agent\u0000\u0000\u0000\u0004vary\u0000\u0000\u0000\u0003via\u0000\u0000\u0000\u0007warning\u0000\u0000\u0000\u0010www-authenticate\u0000\u0000\u0000\u0006method\u0000\u0000\u0000\u0003get\u0000\u0000\u0000\u0006status\u0000\u0000\u0000\u0006200 OK\u0000\u0000\u0000\u0007version\u0000\u0000\u0000\bHTTP/1.1\u0000\u0000\u0000\u0003url\u0000\u0000\u0000\u0006public\u0000\u0000\u0000\nset-cookie\u0000\u0000\u0000\nkeep-alive\u0000\u0000\u0000\u0006origin100101201202205206300302303304305306307402405406407408409410411412413414415416417502504505203 Non-Authoritative Information204 No Content301 Moved Permanently400 Bad Request401 Unauthorized403 Forbidden404 Not Found500 Internal Server Error501 Not Implemented503 Service UnavailableJan Feb Mar Apr May Jun Jul Aug Sept Oct Nov Dec 00:00:00 Mon, Tue, Wed, Thu, Fri, Sat, Sun, GMTchunked,text/html,image/png,image/jpg,image/gif,application/xml,application/xhtml+xml,text/plain,text/javascript,publicprivatemax-age=gzip,deflate,sdchcharset=utf-8charset=iso-8859-1,utf-,*,enq=0.".getBytes(Charsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError();
        }
    }

    public FrameReader newReader(DataEmitter source, Handler handler, boolean client) {
        return new Reader(source, handler, client);
    }

    public FrameWriter newWriter(BufferedDataSink sink, boolean client) {
        return new Writer(sink, client);
    }

    public int maxFrameSize() {
        return 16383;
    }
}
