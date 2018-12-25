package com.koushikdutta.async.callback;

import com.koushikdutta.async.ByteBufferList;
import com.koushikdutta.async.DataEmitter;

public interface DataCallback {

    public static class NullDataCallback implements DataCallback {
        public void onDataAvailable(DataEmitter emitter, ByteBufferList bb) {
            bb.recycle();
        }
    }

    void onDataAvailable(DataEmitter dataEmitter, ByteBufferList byteBufferList);
}
