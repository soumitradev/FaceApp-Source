package com.koushikdutta.async;

import com.koushikdutta.async.callback.DataCallback;

public class DataEmitterReader implements DataCallback {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    ByteBufferList mPendingData = new ByteBufferList();
    DataCallback mPendingRead;
    int mPendingReadLength;

    public void read(int count, DataCallback callback) {
        this.mPendingReadLength = count;
        this.mPendingRead = callback;
        this.mPendingData.recycle();
    }

    private boolean handlePendingData(DataEmitter emitter) {
        if (this.mPendingReadLength > this.mPendingData.remaining()) {
            return false;
        }
        DataCallback pendingRead = this.mPendingRead;
        this.mPendingRead = null;
        pendingRead.onDataAvailable(emitter, this.mPendingData);
        return true;
    }

    public void onDataAvailable(DataEmitter emitter, ByteBufferList bb) {
        do {
            bb.get(this.mPendingData, Math.min(bb.remaining(), this.mPendingReadLength - this.mPendingData.remaining()));
            bb.remaining();
            if (!handlePendingData(emitter)) {
                break;
            }
        } while (this.mPendingRead != null);
        bb.remaining();
    }
}
