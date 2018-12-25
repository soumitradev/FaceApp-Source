package com.koushikdutta.async.stream;

import com.koushikdutta.async.AsyncServer;
import com.koushikdutta.async.ByteBufferList;
import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.Util;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.callback.DataCallback;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class InputStreamDataEmitter implements DataEmitter {
    DataCallback callback;
    CompletedCallback endCallback;
    InputStream inputStream;
    int mToAlloc = 0;
    boolean paused;
    ByteBufferList pending = new ByteBufferList();
    Runnable pumper = new C06682();
    AsyncServer server;

    /* renamed from: com.koushikdutta.async.stream.InputStreamDataEmitter$2 */
    class C06682 implements Runnable {

        /* renamed from: com.koushikdutta.async.stream.InputStreamDataEmitter$2$1 */
        class C06661 implements Runnable {
            C06661() {
            }

            public void run() {
                Util.emitAllData(InputStreamDataEmitter.this, InputStreamDataEmitter.this.pending);
            }
        }

        /* renamed from: com.koushikdutta.async.stream.InputStreamDataEmitter$2$2 */
        class C06672 implements Runnable {
            C06672() {
            }

            public void run() {
                Util.emitAllData(InputStreamDataEmitter.this, InputStreamDataEmitter.this.pending);
            }
        }

        C06682() {
        }

        public void run() {
            try {
                if (!InputStreamDataEmitter.this.pending.isEmpty()) {
                    InputStreamDataEmitter.this.getServer().run(new C06661());
                    if (!InputStreamDataEmitter.this.pending.isEmpty()) {
                        return;
                    }
                }
                do {
                    ByteBuffer b = ByteBufferList.obtain(Math.min(Math.max(InputStreamDataEmitter.this.mToAlloc, 4096), 262144));
                    int read = InputStreamDataEmitter.this.inputStream.read(b.array());
                    int read2 = read;
                    if (-1 != read) {
                        InputStreamDataEmitter.this.mToAlloc = read2 * 2;
                        b.limit(read2);
                        InputStreamDataEmitter.this.pending.add(b);
                        InputStreamDataEmitter.this.getServer().run(new C06672());
                        if (InputStreamDataEmitter.this.pending.remaining() != 0) {
                            break;
                        }
                    } else {
                        InputStreamDataEmitter.this.report(null);
                        return;
                    }
                } while (!InputStreamDataEmitter.this.isPaused());
            } catch (Exception e) {
                InputStreamDataEmitter.this.report(e);
            }
        }
    }

    public InputStreamDataEmitter(AsyncServer server, InputStream inputStream) {
        this.server = server;
        this.inputStream = inputStream;
        doResume();
    }

    public void setDataCallback(DataCallback callback) {
        this.callback = callback;
    }

    public DataCallback getDataCallback() {
        return this.callback;
    }

    public boolean isChunked() {
        return false;
    }

    public void pause() {
        this.paused = true;
    }

    public void resume() {
        this.paused = false;
        doResume();
    }

    private void report(final Exception e) {
        getServer().post(new Runnable() {
            public void run() {
                Exception ex = e;
                try {
                    InputStreamDataEmitter.this.inputStream.close();
                } catch (Exception e) {
                    ex = e;
                }
                if (InputStreamDataEmitter.this.endCallback != null) {
                    InputStreamDataEmitter.this.endCallback.onCompleted(ex);
                }
            }
        });
    }

    private void doResume() {
        new Thread(this.pumper).start();
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

    public AsyncServer getServer() {
        return this.server;
    }

    public void close() {
        report(null);
        try {
            this.inputStream.close();
        } catch (Exception e) {
        }
    }

    public String charset() {
        return null;
    }
}
