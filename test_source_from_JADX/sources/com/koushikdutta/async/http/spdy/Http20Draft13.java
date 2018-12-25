package com.koushikdutta.async.http.spdy;

import android.support.v4.view.MotionEventCompat;
import com.koushikdutta.async.BufferedDataSink;
import com.koushikdutta.async.ByteBufferList;
import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.DataEmitterReader;
import com.koushikdutta.async.callback.DataCallback;
import com.koushikdutta.async.http.Protocol;
import com.koushikdutta.async.http.spdy.FrameReader.Handler;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

final class Http20Draft13 implements Variant {
    private static final ByteString CONNECTION_PREFACE = ByteString.encodeUtf8("PRI * HTTP/2.0\r\n\r\nSM\r\n\r\n");
    static final byte FLAG_ACK = (byte) 1;
    static final byte FLAG_COMPRESSED = (byte) 32;
    static final byte FLAG_END_HEADERS = (byte) 4;
    static final byte FLAG_END_PUSH_PROMISE = (byte) 4;
    static final byte FLAG_END_SEGMENT = (byte) 2;
    static final byte FLAG_END_STREAM = (byte) 1;
    static final byte FLAG_NONE = (byte) 0;
    static final byte FLAG_PADDED = (byte) 8;
    static final byte FLAG_PRIORITY = (byte) 32;
    static final int MAX_FRAME_SIZE = 16383;
    static final byte TYPE_CONTINUATION = (byte) 9;
    static final byte TYPE_DATA = (byte) 0;
    static final byte TYPE_GOAWAY = (byte) 7;
    static final byte TYPE_HEADERS = (byte) 1;
    static final byte TYPE_PING = (byte) 6;
    static final byte TYPE_PRIORITY = (byte) 2;
    static final byte TYPE_PUSH_PROMISE = (byte) 5;
    static final byte TYPE_RST_STREAM = (byte) 3;
    static final byte TYPE_SETTINGS = (byte) 4;
    static final byte TYPE_WINDOW_UPDATE = (byte) 8;
    private static final Logger logger = Logger.getLogger(Http20Draft13.class.getName());

    static final class FrameLogger {
        private static final String[] BINARY = new String[256];
        private static final String[] FLAGS = new String[64];
        private static final String[] TYPES = new String[]{"DATA", "HEADERS", "PRIORITY", "RST_STREAM", "SETTINGS", "PUSH_PROMISE", "PING", "GOAWAY", "WINDOW_UPDATE", "CONTINUATION"};

        FrameLogger() {
        }

        static String formatHeader(boolean inbound, int streamId, int length, byte type, byte flags) {
            String formattedType = type < TYPES.length ? TYPES[type] : String.format(Locale.ENGLISH, "0x%02x", new Object[]{Byte.valueOf(type)});
            String formattedFlags = formatFlags(type, flags);
            Locale locale = Locale.ENGLISH;
            String str = "%s 0x%08x %5d %-13s %s";
            Object[] objArr = new Object[5];
            objArr[0] = inbound ? "<<" : ">>";
            objArr[1] = Integer.valueOf(streamId);
            objArr[2] = Integer.valueOf(length);
            objArr[3] = formattedType;
            objArr[4] = formattedFlags;
            return String.format(locale, str, objArr);
        }

        static String formatFlags(byte type, byte flags) {
            if (flags == (byte) 0) {
                return "";
            }
            switch (type) {
                case (byte) 2:
                case (byte) 3:
                case (byte) 7:
                case (byte) 8:
                    return BINARY[flags];
                case (byte) 4:
                case (byte) 6:
                    return flags == (byte) 1 ? "ACK" : BINARY[flags];
                default:
                    String result;
                    if (flags < FLAGS.length) {
                        result = FLAGS[flags];
                    } else {
                        result = BINARY[flags];
                    }
                    if (type == (byte) 5 && (flags & 4) != 0) {
                        return result.replace("HEADERS", "PUSH_PROMISE");
                    }
                    if (type != (byte) 0 || (flags & 32) == 0) {
                        return result;
                    }
                    return result.replace("PRIORITY", "COMPRESSED");
            }
        }

        static {
            int i = 0;
            for (int i2 = 0; i2 < BINARY.length; i2++) {
                BINARY[i2] = String.format(Locale.ENGLISH, "%8s", new Object[]{Integer.toBinaryString(i2)}).replace(' ', '0');
            }
            FLAGS[0] = "";
            FLAGS[1] = "END_STREAM";
            FLAGS[2] = "END_SEGMENT";
            FLAGS[3] = "END_STREAM|END_SEGMENT";
            int[] prefixFlags = new int[]{1, 2, 3};
            FLAGS[8] = "PADDED";
            for (int prefixFlag : prefixFlags) {
                String[] strArr = FLAGS;
                int i3 = prefixFlag | 8;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(FLAGS[prefixFlag]);
                stringBuilder.append("|PADDED");
                strArr[i3] = stringBuilder.toString();
            }
            FLAGS[4] = "END_HEADERS";
            FLAGS[32] = "PRIORITY";
            FLAGS[36] = "END_HEADERS|PRIORITY";
            for (int i4 : new int[]{4, 32, 36}) {
                for (int i32 : prefixFlags) {
                    String[] strArr2 = FLAGS;
                    int i5 = i32 | i4;
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append(FLAGS[i32]);
                    stringBuilder2.append('|');
                    stringBuilder2.append(FLAGS[i4]);
                    strArr2[i5] = stringBuilder2.toString();
                    strArr2 = FLAGS;
                    i5 = (i32 | i4) | 8;
                    stringBuilder2 = new StringBuilder();
                    stringBuilder2.append(FLAGS[i32]);
                    stringBuilder2.append('|');
                    stringBuilder2.append(FLAGS[i4]);
                    stringBuilder2.append("|PADDED");
                    strArr2[i5] = stringBuilder2.toString();
                }
            }
            while (i < FLAGS.length) {
                if (FLAGS[i] == null) {
                    FLAGS[i] = BINARY[i];
                }
                i++;
            }
        }
    }

    static final class Reader implements FrameReader {
        private final boolean client;
        int continuingStreamId;
        private final DataEmitter emitter;
        byte flags;
        private final Handler handler;
        final Reader hpackReader;
        short length;
        private final DataCallback onFrame = new C11901();
        private final DataCallback onFullFrame = new C11912();
        byte pendingHeaderType;
        int promisedStreamId;
        private final DataEmitterReader reader;
        int streamId;
        byte type;
        int w1;
        int w2;

        /* renamed from: com.koushikdutta.async.http.spdy.Http20Draft13$Reader$1 */
        class C11901 implements DataCallback {
            C11901() {
            }

            public void onDataAvailable(DataEmitter emitter, ByteBufferList bb) {
                bb.order(ByteOrder.BIG_ENDIAN);
                Reader.this.w1 = bb.getInt();
                Reader.this.w2 = bb.getInt();
                Reader.this.length = (short) ((Reader.this.w1 & 1073676288) >> 16);
                Reader.this.type = (byte) ((Reader.this.w1 & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8);
                Reader.this.flags = (byte) (Reader.this.w1 & 255);
                Reader.this.streamId = Reader.this.w2 & Integer.MAX_VALUE;
                if (Http20Draft13.logger.isLoggable(Level.FINE)) {
                    Http20Draft13.logger.fine(FrameLogger.formatHeader(true, Reader.this.streamId, Reader.this.length, Reader.this.type, Reader.this.flags));
                }
                Reader.this.reader.read(Reader.this.length, Reader.this.onFullFrame);
            }
        }

        /* renamed from: com.koushikdutta.async.http.spdy.Http20Draft13$Reader$2 */
        class C11912 implements DataCallback {
            C11912() {
            }

            public void onDataAvailable(DataEmitter emitter, ByteBufferList bb) {
                try {
                    switch (Reader.this.type) {
                        case (byte) 0:
                            Reader.this.readData(bb, Reader.this.length, Reader.this.flags, Reader.this.streamId);
                            break;
                        case (byte) 1:
                            Reader.this.readHeaders(bb, Reader.this.length, Reader.this.flags, Reader.this.streamId);
                            break;
                        case (byte) 2:
                            Reader.this.readPriority(bb, Reader.this.length, Reader.this.flags, Reader.this.streamId);
                            break;
                        case (byte) 3:
                            Reader.this.readRstStream(bb, Reader.this.length, Reader.this.flags, Reader.this.streamId);
                            break;
                        case (byte) 4:
                            Reader.this.readSettings(bb, Reader.this.length, Reader.this.flags, Reader.this.streamId);
                            break;
                        case (byte) 5:
                            Reader.this.readPushPromise(bb, Reader.this.length, Reader.this.flags, Reader.this.streamId);
                            break;
                        case (byte) 6:
                            Reader.this.readPing(bb, Reader.this.length, Reader.this.flags, Reader.this.streamId);
                            break;
                        case (byte) 7:
                            Reader.this.readGoAway(bb, Reader.this.length, Reader.this.flags, Reader.this.streamId);
                            break;
                        case (byte) 8:
                            Reader.this.readWindowUpdate(bb, Reader.this.length, Reader.this.flags, Reader.this.streamId);
                            break;
                        case (byte) 9:
                            Reader.this.readContinuation(bb, Reader.this.length, Reader.this.flags, Reader.this.streamId);
                            break;
                        default:
                            bb.recycle();
                            break;
                    }
                    Reader.this.parseFrameHeader();
                } catch (IOException e) {
                    Reader.this.handler.error(e);
                }
            }
        }

        Reader(DataEmitter emitter, Handler handler, int headerTableSize, boolean client) {
            this.emitter = emitter;
            this.client = client;
            this.hpackReader = new Reader(headerTableSize);
            this.handler = handler;
            this.reader = new DataEmitterReader();
            parseFrameHeader();
        }

        private void parseFrameHeader() {
            this.emitter.setDataCallback(this.reader);
            this.reader.read(8, this.onFrame);
        }

        private void readHeaders(ByteBufferList source, short length, byte flags, int streamId) throws IOException {
            short padding = (short) 0;
            if (streamId == 0) {
                throw Http20Draft13.ioException("PROTOCOL_ERROR: TYPE_HEADERS streamId == 0", new Object[0]);
            }
            if ((flags & 8) != 0) {
                padding = (short) (source.get() & 255);
            }
            if ((flags & 32) != 0) {
                readPriority(source, streamId);
                length = (short) (length - 5);
            }
            length = Http20Draft13.lengthWithoutPadding(length, flags, padding);
            this.pendingHeaderType = this.type;
            readHeaderBlock(source, length, padding, flags, streamId);
        }

        private void readContinuation(ByteBufferList source, short length, byte flags, int streamId) throws IOException {
            if (streamId != this.continuingStreamId) {
                throw new IOException("continuation stream id mismatch");
            }
            readHeaderBlock(source, length, (short) 0, flags, streamId);
        }

        private void readHeaderBlock(ByteBufferList source, short length, short padding, byte flags, int streamId) throws IOException {
            source.skip(padding);
            this.hpackReader.refill(source);
            this.hpackReader.readHeaders();
            this.hpackReader.emitReferenceSet();
            if ((flags & 4) == 0) {
                this.continuingStreamId = streamId;
            } else if (this.pendingHeaderType == (byte) 1) {
                this.handler.headers(false, (flags & 1) != 0, streamId, -1, this.hpackReader.getAndReset(), HeadersMode.HTTP_20_HEADERS);
            } else if (this.pendingHeaderType == (byte) 5) {
                this.handler.pushPromise(streamId, this.promisedStreamId, this.hpackReader.getAndReset());
            } else {
                throw new AssertionError("unknown header type");
            }
        }

        private void readData(ByteBufferList source, short length, byte flags, int streamId) throws IOException {
            boolean gzipped = true;
            short padding = (short) 0;
            boolean inFinished = (flags & 1) != 0;
            if ((flags & 32) == 0) {
                gzipped = false;
            }
            if (gzipped) {
                throw Http20Draft13.ioException("PROTOCOL_ERROR: FLAG_COMPRESSED without SETTINGS_COMPRESS_DATA", new Object[0]);
            }
            if ((flags & 8) != 0) {
                padding = (short) (source.get() & 255);
            }
            length = Http20Draft13.lengthWithoutPadding(length, flags, padding);
            this.handler.data(inFinished, streamId, source);
            source.skip(padding);
        }

        private void readPriority(ByteBufferList source, short length, byte flags, int streamId) throws IOException {
            if (length != (short) 5) {
                throw Http20Draft13.ioException("TYPE_PRIORITY length: %d != 5", Short.valueOf(length));
            } else if (streamId == 0) {
                throw Http20Draft13.ioException("TYPE_PRIORITY streamId == 0", new Object[0]);
            } else {
                readPriority(source, streamId);
            }
        }

        private void readPriority(ByteBufferList source, int streamId) throws IOException {
            int w1 = source.getInt();
            this.handler.priority(streamId, Integer.MAX_VALUE & w1, (source.get() & 255) + 1, (Integer.MIN_VALUE & w1) != 0);
        }

        private void readRstStream(ByteBufferList source, short length, byte flags, int streamId) throws IOException {
            if (length != (short) 4) {
                throw Http20Draft13.ioException("TYPE_RST_STREAM length: %d != 4", Short.valueOf(length));
            } else if (streamId == 0) {
                throw Http20Draft13.ioException("TYPE_RST_STREAM streamId == 0", new Object[0]);
            } else {
                ErrorCode errorCode = ErrorCode.fromHttp2(source.getInt());
                if (errorCode == null) {
                    throw Http20Draft13.ioException("TYPE_RST_STREAM unexpected error code: %d", Integer.valueOf(errorCodeInt));
                } else {
                    this.handler.rstStream(streamId, errorCode);
                }
            }
        }

        private void readSettings(ByteBufferList source, short length, byte flags, int streamId) throws IOException {
            if (streamId != 0) {
                throw Http20Draft13.ioException("TYPE_SETTINGS streamId != 0", new Object[0]);
            } else if ((flags & 1) != 0) {
                if (length != (short) 0) {
                    throw Http20Draft13.ioException("FRAME_SIZE_ERROR ack frame should be empty!", new Object[0]);
                }
                this.handler.ackSettings();
            } else if (length % 6 != 0) {
                throw Http20Draft13.ioException("TYPE_SETTINGS length %% 6 != 0: %s", Short.valueOf(length));
            } else {
                Settings settings = new Settings();
                for (short i = (short) 0; i < length; i += 6) {
                    short id = source.getShort();
                    int value = source.getInt();
                    switch (id) {
                        case (short) 1:
                            break;
                        case (short) 2:
                            if (!(value == 0 || value == 1)) {
                                throw Http20Draft13.ioException("PROTOCOL_ERROR SETTINGS_ENABLE_PUSH != 0 or 1", new Object[0]);
                            }
                        case (short) 3:
                            id = (short) 4;
                            break;
                        case (short) 4:
                            id = (short) 7;
                            if (value >= 0) {
                                break;
                            }
                            throw Http20Draft13.ioException("PROTOCOL_ERROR SETTINGS_INITIAL_WINDOW_SIZE > 2^31 - 1", new Object[0]);
                        case (short) 5:
                            break;
                        default:
                            throw Http20Draft13.ioException("PROTOCOL_ERROR invalid settings id: %s", Short.valueOf(id));
                    }
                    settings.set(id, 0, value);
                }
                this.handler.settings(false, settings);
                if (settings.getHeaderTableSize() >= 0) {
                    this.hpackReader.maxHeaderTableByteCountSetting(settings.getHeaderTableSize());
                }
            }
        }

        private void readPushPromise(ByteBufferList source, short length, byte flags, int streamId) throws IOException {
            short padding = (short) 0;
            if (streamId == 0) {
                throw Http20Draft13.ioException("PROTOCOL_ERROR: TYPE_PUSH_PROMISE streamId == 0", new Object[0]);
            }
            if ((flags & 8) != 0) {
                padding = (short) (source.get() & 255);
            }
            this.promisedStreamId = source.getInt() & Integer.MAX_VALUE;
            length = Http20Draft13.lengthWithoutPadding((short) (length - 4), flags, padding);
            this.pendingHeaderType = (byte) 5;
            readHeaderBlock(source, length, padding, flags, streamId);
        }

        private void readPing(ByteBufferList source, short length, byte flags, int streamId) throws IOException {
            boolean ack = false;
            if (length != (short) 8) {
                throw Http20Draft13.ioException("TYPE_PING length != 8: %s", Short.valueOf(length));
            } else if (streamId != 0) {
                throw Http20Draft13.ioException("TYPE_PING streamId != 0", new Object[0]);
            } else {
                int payload1 = source.getInt();
                int payload2 = source.getInt();
                if ((flags & 1) != 0) {
                    ack = true;
                }
                this.handler.ping(ack, payload1, payload2);
            }
        }

        private void readGoAway(ByteBufferList source, short length, byte flags, int streamId) throws IOException {
            if (length < (short) 8) {
                throw Http20Draft13.ioException("TYPE_GOAWAY length < 8: %s", Short.valueOf(length));
            } else if (streamId != 0) {
                throw Http20Draft13.ioException("TYPE_GOAWAY streamId != 0", new Object[0]);
            } else {
                int lastStreamId = source.getInt();
                int opaqueDataLength = length - 8;
                ErrorCode errorCode = ErrorCode.fromHttp2(source.getInt());
                if (errorCode == null) {
                    throw Http20Draft13.ioException("TYPE_GOAWAY unexpected error code: %d", Integer.valueOf(errorCodeInt));
                }
                ByteString debugData = ByteString.EMPTY;
                if (opaqueDataLength > 0) {
                    debugData = ByteString.of(source.getBytes(opaqueDataLength));
                }
                this.handler.goAway(lastStreamId, errorCode, debugData);
            }
        }

        private void readWindowUpdate(ByteBufferList source, short length, byte flags, int streamId) throws IOException {
            if (length != (short) 4) {
                throw Http20Draft13.ioException("TYPE_WINDOW_UPDATE length !=4: %s", Short.valueOf(length));
            }
            long increment = ((long) source.getInt()) & 2147483647L;
            if (increment == 0) {
                throw Http20Draft13.ioException("windowSizeIncrement was 0", Long.valueOf(increment));
            } else {
                this.handler.windowUpdate(streamId, increment);
            }
        }
    }

    static final class Writer implements FrameWriter {
        private final boolean client;
        private boolean closed;
        private final ByteBufferList frameHeader = new ByteBufferList();
        private final Writer hpackWriter;
        private final BufferedDataSink sink;

        Writer(BufferedDataSink sink, boolean client) {
            this.sink = sink;
            this.client = client;
            this.hpackWriter = new Writer();
        }

        public synchronized void ackSettings() throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            }
            frameHeader(0, 0, (byte) 4, (byte) 1);
        }

        public synchronized void connectionPreface() throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            } else if (this.client) {
                if (Http20Draft13.logger.isLoggable(Level.FINE)) {
                    Http20Draft13.logger.fine(String.format(Locale.ENGLISH, ">> CONNECTION %s", new Object[]{Http20Draft13.CONNECTION_PREFACE.hex()}));
                }
                this.sink.write(new ByteBufferList(Http20Draft13.CONNECTION_PREFACE.toByteArray()));
            }
        }

        public synchronized void synStream(boolean outFinished, boolean inFinished, int streamId, int associatedStreamId, List<Header> headerBlock) throws IOException {
            if (inFinished) {
                throw new UnsupportedOperationException();
            } else if (this.closed) {
                throw new IOException("closed");
            } else {
                headers(outFinished, streamId, headerBlock);
            }
        }

        public synchronized void synReply(boolean outFinished, int streamId, List<Header> headerBlock) throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            }
            headers(outFinished, streamId, headerBlock);
        }

        public synchronized void headers(int streamId, List<Header> headerBlock) throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            }
            headers(false, streamId, headerBlock);
        }

        public synchronized void pushPromise(int streamId, int promisedStreamId, List<Header> requestHeaders) throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            }
            ByteBufferList hpackBuffer = this.hpackWriter.writeHeaders(requestHeaders);
            long byteCount = (long) hpackBuffer.remaining();
            int length = (int) Math.min(16379, byteCount);
            frameHeader(streamId, length + 4, (byte) 5, byteCount == ((long) length) ? (byte) 4 : (byte) 0);
            ByteBuffer sink = ByteBufferList.obtain(8192).order(ByteOrder.BIG_ENDIAN);
            sink.putInt(Integer.MAX_VALUE & promisedStreamId);
            sink.flip();
            this.frameHeader.add(sink);
            hpackBuffer.get(this.frameHeader, length);
            this.sink.write(this.frameHeader);
            if (byteCount > ((long) length)) {
                writeContinuationFrames(hpackBuffer, streamId);
            }
        }

        void headers(boolean outFinished, int streamId, List<Header> headerBlock) throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            }
            ByteBufferList hpackBuffer = this.hpackWriter.writeHeaders(headerBlock);
            long byteCount = (long) hpackBuffer.remaining();
            int length = (int) Math.min(16383, byteCount);
            byte flags = byteCount == ((long) length) ? (byte) 4 : (byte) 0;
            if (outFinished) {
                flags = (byte) (flags | 1);
            }
            frameHeader(streamId, length, (byte) 1, flags);
            hpackBuffer.get(this.frameHeader, length);
            this.sink.write(this.frameHeader);
            if (byteCount > ((long) length)) {
                writeContinuationFrames(hpackBuffer, streamId);
            }
        }

        private void writeContinuationFrames(ByteBufferList hpackBuffer, int streamId) throws IOException {
            while (hpackBuffer.hasRemaining()) {
                int length = Math.min(Http20Draft13.MAX_FRAME_SIZE, hpackBuffer.remaining());
                frameHeader(streamId, length, (byte) 9, hpackBuffer.remaining() - length == 0 ? (byte) 4 : (byte) 0);
                hpackBuffer.get(this.frameHeader, length);
                this.sink.write(this.frameHeader);
            }
        }

        public synchronized void rstStream(int streamId, ErrorCode errorCode) throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            } else if (errorCode.spdyRstCode == -1) {
                throw new IllegalArgumentException();
            } else {
                frameHeader(streamId, 4, (byte) 3, (byte) 0);
                ByteBuffer sink = ByteBufferList.obtain(8192).order(ByteOrder.BIG_ENDIAN);
                sink.putInt(errorCode.httpCode);
                sink.flip();
                this.sink.write(this.frameHeader.add(sink));
            }
        }

        public synchronized void data(boolean outFinished, int streamId, ByteBufferList source) throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            }
            byte flags = (byte) 0;
            if (outFinished) {
                flags = (byte) (0 | 1);
            }
            dataFrame(streamId, flags, source);
        }

        void dataFrame(int streamId, byte flags, ByteBufferList buffer) throws IOException {
            frameHeader(streamId, buffer.remaining(), (byte) 0, flags);
            this.sink.write(buffer);
        }

        public synchronized void settings(Settings settings) throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            }
            frameHeader(0, settings.size() * 6, (byte) 4, (byte) 0);
            ByteBuffer sink = ByteBufferList.obtain(8192).order(ByteOrder.BIG_ENDIAN);
            for (int i = 0; i < 10; i++) {
                if (settings.isSet(i)) {
                    int id = i;
                    if (id == 4) {
                        id = 3;
                    } else if (id == 7) {
                        id = 4;
                    }
                    sink.putShort((short) id);
                    sink.putInt(settings.get(i));
                }
            }
            sink.flip();
            this.sink.write(this.frameHeader.add(sink));
        }

        public synchronized void ping(boolean ack, int payload1, int payload2) throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            }
            frameHeader(0, 8, (byte) 6, ack);
            ByteBuffer sink = ByteBufferList.obtain(256).order(ByteOrder.BIG_ENDIAN);
            sink.putInt(payload1);
            sink.putInt(payload2);
            sink.flip();
            this.sink.write(this.frameHeader.add(sink));
        }

        public synchronized void goAway(int lastGoodStreamId, ErrorCode errorCode, byte[] debugData) throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            } else if (errorCode.httpCode == -1) {
                throw Http20Draft13.illegalArgument("errorCode.httpCode == -1", new Object[0]);
            } else {
                frameHeader(0, debugData.length + 8, (byte) 7, (byte) 0);
                ByteBuffer sink = ByteBufferList.obtain(256).order(ByteOrder.BIG_ENDIAN);
                sink.putInt(lastGoodStreamId);
                sink.putInt(errorCode.httpCode);
                sink.put(debugData);
                sink.flip();
                this.sink.write(this.frameHeader.add(sink));
            }
        }

        public synchronized void windowUpdate(int streamId, long windowSizeIncrement) throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            }
            if (windowSizeIncrement != 0) {
                if (windowSizeIncrement <= 2147483647L) {
                    frameHeader(streamId, 4, (byte) 8, (byte) 0);
                    ByteBuffer sink = ByteBufferList.obtain(256).order(ByteOrder.BIG_ENDIAN);
                    sink.putInt((int) windowSizeIncrement);
                    sink.flip();
                    this.sink.write(this.frameHeader.add(sink));
                }
            }
            throw Http20Draft13.illegalArgument("windowSizeIncrement == 0 || windowSizeIncrement > 0x7fffffffL: %s", Long.valueOf(windowSizeIncrement));
        }

        public synchronized void close() throws IOException {
            this.closed = true;
        }

        void frameHeader(int streamId, int length, byte type, byte flags) throws IOException {
            if (Http20Draft13.logger.isLoggable(Level.FINE)) {
                Http20Draft13.logger.fine(FrameLogger.formatHeader(false, streamId, length, type, flags));
            }
            if (length > Http20Draft13.MAX_FRAME_SIZE) {
                throw Http20Draft13.illegalArgument("FRAME_SIZE_ERROR length > %d: %d", Integer.valueOf(Http20Draft13.MAX_FRAME_SIZE), Integer.valueOf(length));
            } else if ((Integer.MIN_VALUE & streamId) != 0) {
                throw Http20Draft13.illegalArgument("reserved bit set: %s", Integer.valueOf(streamId));
            } else {
                ByteBuffer sink = ByteBufferList.obtain(256).order(ByteOrder.BIG_ENDIAN);
                sink.putInt((((length & Http20Draft13.MAX_FRAME_SIZE) << 16) | ((type & 255) << 8)) | (flags & 255));
                sink.putInt(Integer.MAX_VALUE & streamId);
                sink.flip();
                this.sink.write(this.frameHeader.add(sink));
            }
        }
    }

    Http20Draft13() {
    }

    public Protocol getProtocol() {
        return Protocol.HTTP_2;
    }

    public FrameReader newReader(DataEmitter source, Handler handler, boolean client) {
        return new Reader(source, handler, 4096, client);
    }

    public FrameWriter newWriter(BufferedDataSink sink, boolean client) {
        return new Writer(sink, client);
    }

    public int maxFrameSize() {
        return MAX_FRAME_SIZE;
    }

    private static IllegalArgumentException illegalArgument(String message, Object... args) {
        throw new IllegalArgumentException(String.format(Locale.ENGLISH, message, args));
    }

    private static IOException ioException(String message, Object... args) throws IOException {
        throw new IOException(String.format(Locale.ENGLISH, message, args));
    }

    private static short lengthWithoutPadding(short length, byte flags, short padding) throws IOException {
        if ((flags & 8) != 0) {
            length = (short) (length - 1);
        }
        if (padding <= length) {
            return (short) (length - padding);
        }
        throw ioException("PROTOCOL_ERROR padding %s > remaining length %s", Short.valueOf(padding), Short.valueOf(length));
    }
}
