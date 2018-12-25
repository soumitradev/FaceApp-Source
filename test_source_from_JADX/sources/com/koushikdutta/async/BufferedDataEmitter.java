package com.koushikdutta.async;

import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.callback.DataCallback;

public class BufferedDataEmitter implements DataEmitter {
    ByteBufferList mBuffers = new ByteBufferList();
    DataCallback mDataCallback;
    DataEmitter mEmitter;
    CompletedCallback mEndCallback;
    Exception mEndException;
    boolean mEnded = false;

    /* renamed from: com.koushikdutta.async.BufferedDataEmitter$1 */
    class C10871 implements DataCallback {
        C10871() {
        }

        public void onDataAvailable(DataEmitter emitter, ByteBufferList bb) {
            bb.get(BufferedDataEmitter.this.mBuffers);
            BufferedDataEmitter.this.onDataAvailable();
        }
    }

    /* renamed from: com.koushikdutta.async.BufferedDataEmitter$2 */
    class C10882 implements CompletedCallback {
        C10882() {
        }

        public void onCompleted(Exception ex) {
            BufferedDataEmitter.this.mEnded = true;
            BufferedDataEmitter.this.mEndException = ex;
            if (BufferedDataEmitter.this.mBuffers.remaining() == 0 && BufferedDataEmitter.this.mEndCallback != null) {
                BufferedDataEmitter.this.mEndCallback.onCompleted(ex);
            }
        }
    }

    public BufferedDataEmitter(DataEmitter emitter) {
        this.mEmitter = emitter;
        this.mEmitter.setDataCallback(new C10871());
        this.mEmitter.setEndCallback(new C10882());
    }

    public void close() {
        this.mEmitter.close();
    }

    public void onDataAvailable() {
        if (!(this.mDataCallback == null || isPaused() || this.mBuffers.remaining() <= 0)) {
            this.mDataCallback.onDataAvailable(this, this.mBuffers);
        }
        if (this.mEnded && !this.mBuffers.hasRemaining() && this.mEndCallback != null) {
            this.mEndCallback.onCompleted(this.mEndException);
        }
    }

    public void setDataCallback(DataCallback callback) {
        if (this.mDataCallback != null) {
            throw new RuntimeException("Buffered Data Emitter callback may only be set once");
        }
        this.mDataCallback = callback;
    }

    public DataCallback getDataCallback() {
        return this.mDataCallback;
    }

    public boolean isChunked() {
        return false;
    }

    public void pause() {
        this.mEmitter.pause();
    }

    public void resume() {
        this.mEmitter.resume();
        onDataAvailable();
    }

    public boolean isPaused() {
        return this.mEmitter.isPaused();
    }

    public void setEndCallback(CompletedCallback callback) {
        this.mEndCallback = callback;
    }

    public CompletedCallback getEndCallback() {
        return this.mEndCallback;
    }

    public AsyncServer getServer() {
        return this.mEmitter.getServer();
    }

    public String charset() {
        return this.mEmitter.charset();
    }
}
