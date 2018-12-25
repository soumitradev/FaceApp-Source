package com.koushikdutta.async;

import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.callback.DataCallback;
import com.koushikdutta.async.callback.WritableCallback;
import com.koushikdutta.async.util.Allocator;
import com.koushikdutta.async.util.StreamUtility;
import com.koushikdutta.async.wrapper.AsyncSocketWrapper;
import com.koushikdutta.async.wrapper.DataEmitterWrapper;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.ByteBuffer;

public class Util {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static boolean SUPRESS_DEBUG_EXCEPTIONS = false;

    public static void emitAllData(DataEmitter emitter, ByteBufferList list) {
        DataCallback handler = null;
        while (!emitter.isPaused()) {
            DataCallback dataCallback = emitter.getDataCallback();
            handler = dataCallback;
            if (dataCallback == null) {
                break;
            }
            int remaining = list.remaining();
            int remaining2 = remaining;
            if (remaining <= 0) {
                break;
            }
            handler.onDataAvailable(emitter, list);
            if (remaining2 == list.remaining() && handler == emitter.getDataCallback() && !emitter.isPaused()) {
                PrintStream printStream = System.out;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("handler: ");
                stringBuilder.append(handler);
                printStream.println(stringBuilder.toString());
                list.recycle();
                if (!SUPRESS_DEBUG_EXCEPTIONS) {
                    throw new RuntimeException("mDataHandler failed to consume data, yet remains the mDataHandler.");
                }
                return;
            }
        }
        if (list.remaining() != 0 && !emitter.isPaused()) {
            printStream = System.out;
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("handler: ");
            stringBuilder2.append(handler);
            printStream.println(stringBuilder2.toString());
            printStream = System.out;
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append("emitter: ");
            stringBuilder2.append(emitter);
            printStream.println(stringBuilder2.toString());
            list.recycle();
            if (!SUPRESS_DEBUG_EXCEPTIONS) {
                throw new RuntimeException("Not all data was consumed by Util.emitAllData");
            }
        }
    }

    public static void pump(InputStream is, DataSink ds, CompletedCallback callback) {
        pump(is, 2147483647L, ds, callback);
    }

    public static void pump(InputStream is, long max, DataSink ds, final CompletedCallback callback) {
        CompletedCallback wrapper = new CompletedCallback() {
            boolean reported;

            public void onCompleted(Exception ex) {
                if (!this.reported) {
                    this.reported = true;
                    callback.onCompleted(ex);
                }
            }
        };
        final DataSink dataSink = ds;
        final InputStream inputStream = is;
        final long j = max;
        final CompletedCallback completedCallback = wrapper;
        C11002 cb = new WritableCallback() {
            Allocator allocator = new Allocator();
            ByteBufferList pending = new ByteBufferList();
            int totalRead = null;

            private void cleanup() {
                dataSink.setClosedCallback(null);
                dataSink.setWriteableCallback(null);
                this.pending.recycle();
                StreamUtility.closeQuietly(inputStream);
            }

            public void onWriteable() {
                do {
                    try {
                        if (!this.pending.hasRemaining()) {
                            ByteBuffer b = this.allocator.allocate();
                            int read = inputStream.read(b.array(), 0, (int) Math.min(j - ((long) this.totalRead), (long) b.capacity()));
                            if (read != -1) {
                                if (((long) this.totalRead) != j) {
                                    this.allocator.track((long) read);
                                    this.totalRead += read;
                                    b.position(0);
                                    b.limit(read);
                                    this.pending.add(b);
                                }
                            }
                            cleanup();
                            completedCallback.onCompleted(null);
                            return;
                        }
                        dataSink.write(this.pending);
                    } catch (Exception e) {
                        cleanup();
                        completedCallback.onCompleted(e);
                    }
                } while (!this.pending.hasRemaining());
            }
        };
        ds.setWriteableCallback(cb);
        ds.setClosedCallback(wrapper);
        cb.onWriteable();
    }

    public static void pump(final DataEmitter emitter, final DataSink sink, final CompletedCallback callback) {
        emitter.setDataCallback(new DataCallback() {
            public void onDataAvailable(DataEmitter emitter, ByteBufferList bb) {
                sink.write(bb);
                if (bb.remaining() > 0) {
                    emitter.pause();
                }
            }
        });
        sink.setWriteableCallback(new WritableCallback() {
            public void onWriteable() {
                emitter.resume();
            }
        });
        final CompletedCallback wrapper = new CompletedCallback() {
            boolean reported;

            public void onCompleted(Exception ex) {
                if (!this.reported) {
                    this.reported = true;
                    emitter.setDataCallback(null);
                    emitter.setEndCallback(null);
                    sink.setClosedCallback(null);
                    sink.setWriteableCallback(null);
                    callback.onCompleted(ex);
                }
            }
        };
        emitter.setEndCallback(wrapper);
        sink.setClosedCallback(new CompletedCallback() {
            public void onCompleted(Exception ex) {
                if (ex == null) {
                    ex = new IOException("sink was closed before emitter ended");
                }
                wrapper.onCompleted(ex);
            }
        });
    }

    public static void stream(AsyncSocket s1, AsyncSocket s2, CompletedCallback callback) {
        pump((DataEmitter) s1, (DataSink) s2, callback);
        pump((DataEmitter) s2, (DataSink) s1, callback);
    }

    public static void pump(File file, DataSink ds, final CompletedCallback callback) {
        if (file != null) {
            if (ds != null) {
                try {
                    final InputStream is = new FileInputStream(file);
                    pump(is, ds, new CompletedCallback() {
                        public void onCompleted(Exception ex) {
                            try {
                                is.close();
                                callback.onCompleted(ex);
                            } catch (IOException e) {
                                callback.onCompleted(e);
                            }
                        }
                    });
                } catch (Exception e) {
                    callback.onCompleted(e);
                }
                return;
            }
        }
        callback.onCompleted(null);
    }

    public static void writeAll(final DataSink sink, final ByteBufferList bb, final CompletedCallback callback) {
        WritableCallback c11068 = new WritableCallback() {
            public void onWriteable() {
                sink.write(bb);
                if (bb.remaining() == 0 && callback != null) {
                    sink.setWriteableCallback(null);
                    callback.onCompleted(null);
                }
            }
        };
        WritableCallback wc = c11068;
        sink.setWriteableCallback(c11068);
        wc.onWriteable();
    }

    public static void writeAll(DataSink sink, byte[] bytes, CompletedCallback callback) {
        ByteBuffer bb = ByteBufferList.obtain(bytes.length);
        bb.put(bytes);
        bb.flip();
        ByteBufferList bbl = new ByteBufferList();
        bbl.add(bb);
        writeAll(sink, bbl, callback);
    }

    public static <T extends AsyncSocket> T getWrappedSocket(AsyncSocket socket, Class<T> wrappedClass) {
        if (wrappedClass.isInstance(socket)) {
            return socket;
        }
        while (socket instanceof AsyncSocketWrapper) {
            socket = ((AsyncSocketWrapper) socket).getSocket();
            if (wrappedClass.isInstance(socket)) {
                return socket;
            }
        }
        return null;
    }

    public static DataEmitter getWrappedDataEmitter(DataEmitter emitter, Class wrappedClass) {
        if (wrappedClass.isInstance(emitter)) {
            return emitter;
        }
        while (emitter instanceof DataEmitterWrapper) {
            emitter = ((AsyncSocketWrapper) emitter).getSocket();
            if (wrappedClass.isInstance(emitter)) {
                return emitter;
            }
        }
        return null;
    }

    public static void end(DataEmitter emitter, Exception e) {
        if (emitter != null) {
            end(emitter.getEndCallback(), e);
        }
    }

    public static void end(CompletedCallback end, Exception e) {
        if (end != null) {
            end.onCompleted(e);
        }
    }

    public static void writable(DataSink emitter) {
        if (emitter != null) {
            writable(emitter.getWriteableCallback());
        }
    }

    public static void writable(WritableCallback writable) {
        if (writable != null) {
            writable.onWriteable();
        }
    }
}
