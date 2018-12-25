package com.koushikdutta.async;

import com.koushikdutta.async.callback.DataCallback;
import com.koushikdutta.async.util.StreamUtility;
import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileDataEmitter extends DataEmitterBase {
    DataCallback callback;
    FileChannel channel;
    File file;
    boolean paused;
    ByteBufferList pending = new ByteBufferList();
    Runnable pumper = new C06411();
    AsyncServer server;

    /* renamed from: com.koushikdutta.async.FileDataEmitter$1 */
    class C06411 implements Runnable {
        C06411() {
        }

        public void run() {
            try {
                if (FileDataEmitter.this.channel == null) {
                    FileDataEmitter.this.channel = new FileInputStream(FileDataEmitter.this.file).getChannel();
                }
                if (!FileDataEmitter.this.pending.isEmpty()) {
                    Util.emitAllData(FileDataEmitter.this, FileDataEmitter.this.pending);
                    if (!FileDataEmitter.this.pending.isEmpty()) {
                        return;
                    }
                }
                do {
                    ByteBuffer b = ByteBufferList.obtain(8192);
                    if (-1 != FileDataEmitter.this.channel.read(b)) {
                        b.flip();
                        FileDataEmitter.this.pending.add(b);
                        Util.emitAllData(FileDataEmitter.this, FileDataEmitter.this.pending);
                        if (FileDataEmitter.this.pending.remaining() != 0) {
                            break;
                        }
                    } else {
                        FileDataEmitter.this.report(null);
                        return;
                    }
                } while (!FileDataEmitter.this.isPaused());
            } catch (Exception e) {
                FileDataEmitter.this.report(e);
            }
        }
    }

    public FileDataEmitter(AsyncServer server, File file) {
        this.server = server;
        this.file = file;
        this.paused = server.isAffinityThread() ^ 1;
        if (!this.paused) {
            doResume();
        }
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

    protected void report(Exception e) {
        StreamUtility.closeQuietly(this.channel);
        super.report(e);
    }

    private void doResume() {
        this.server.post(this.pumper);
    }

    public boolean isPaused() {
        return this.paused;
    }

    public AsyncServer getServer() {
        return this.server;
    }

    public void close() {
        try {
            this.channel.close();
        } catch (Exception e) {
        }
    }
}
