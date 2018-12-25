package com.koushikdutta.async;

import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.callback.DataCallback;

public abstract class DataEmitterBase implements DataEmitter {
    CompletedCallback endCallback;
    private boolean ended;
    DataCallback mDataCallback;

    protected void report(Exception e) {
        if (!this.ended) {
            this.ended = true;
            if (getEndCallback() != null) {
                getEndCallback().onCompleted(e);
            }
        }
    }

    public final void setEndCallback(CompletedCallback callback) {
        this.endCallback = callback;
    }

    public final CompletedCallback getEndCallback() {
        return this.endCallback;
    }

    public void setDataCallback(DataCallback callback) {
        this.mDataCallback = callback;
    }

    public DataCallback getDataCallback() {
        return this.mDataCallback;
    }

    public String charset() {
        return null;
    }
}
