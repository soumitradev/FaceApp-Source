package com.koushikdutta.async;

import com.koushikdutta.async.DataTrackingEmitter.DataTracker;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.callback.DataCallback;
import com.koushikdutta.async.wrapper.DataEmitterWrapper;

public class FilteredDataEmitter extends DataEmitterBase implements DataEmitter, DataCallback, DataEmitterWrapper, DataTrackingEmitter {
    boolean closed;
    private DataEmitter mEmitter;
    private int totalRead;
    private DataTracker tracker;

    /* renamed from: com.koushikdutta.async.FilteredDataEmitter$1 */
    class C10901 implements CompletedCallback {
        C10901() {
        }

        public void onCompleted(Exception ex) {
            FilteredDataEmitter.this.report(ex);
        }
    }

    public DataEmitter getDataEmitter() {
        return this.mEmitter;
    }

    public void setDataEmitter(DataEmitter emitter) {
        if (this.mEmitter != null) {
            this.mEmitter.setDataCallback(null);
        }
        this.mEmitter = emitter;
        this.mEmitter.setDataCallback(this);
        this.mEmitter.setEndCallback(new C10901());
    }

    public int getBytesRead() {
        return this.totalRead;
    }

    public DataTracker getDataTracker() {
        return this.tracker;
    }

    public void setDataTracker(DataTracker tracker) {
        this.tracker = tracker;
    }

    public void onDataAvailable(DataEmitter emitter, ByteBufferList bb) {
        if (this.closed) {
            bb.recycle();
            return;
        }
        if (bb != null) {
            this.totalRead += bb.remaining();
        }
        Util.emitAllData(this, bb);
        if (bb != null) {
            this.totalRead -= bb.remaining();
        }
        if (!(this.tracker == null || bb == null)) {
            this.tracker.onData(this.totalRead);
        }
    }

    public boolean isChunked() {
        return this.mEmitter.isChunked();
    }

    public void pause() {
        this.mEmitter.pause();
    }

    public void resume() {
        this.mEmitter.resume();
    }

    public boolean isPaused() {
        return this.mEmitter.isPaused();
    }

    public AsyncServer getServer() {
        return this.mEmitter.getServer();
    }

    public void close() {
        this.closed = true;
        if (this.mEmitter != null) {
            this.mEmitter.close();
        }
    }

    public String charset() {
        if (this.mEmitter == null) {
            return null;
        }
        return this.mEmitter.charset();
    }
}
