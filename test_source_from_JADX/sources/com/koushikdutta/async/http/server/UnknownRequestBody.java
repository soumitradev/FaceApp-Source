package com.koushikdutta.async.http.server;

import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.DataSink;
import com.koushikdutta.async.Util;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.callback.DataCallback;
import com.koushikdutta.async.callback.DataCallback.NullDataCallback;
import com.koushikdutta.async.http.AsyncHttpRequest;
import com.koushikdutta.async.http.body.AsyncHttpRequestBody;

public class UnknownRequestBody implements AsyncHttpRequestBody<Void> {
    DataEmitter emitter;
    int length = -1;
    private String mContentType;

    public UnknownRequestBody(String contentType) {
        this.mContentType = contentType;
    }

    public UnknownRequestBody(DataEmitter emitter, String contentType, int length) {
        this.mContentType = contentType;
        this.emitter = emitter;
        this.length = length;
    }

    public void write(AsyncHttpRequest request, DataSink sink, CompletedCallback completed) {
        Util.pump(this.emitter, sink, completed);
        if (this.emitter.isPaused()) {
            this.emitter.resume();
        }
    }

    public String getContentType() {
        return this.mContentType;
    }

    public boolean readFullyOnRequest() {
        return false;
    }

    public int length() {
        return this.length;
    }

    public Void get() {
        return null;
    }

    @Deprecated
    public void setCallbacks(DataCallback callback, CompletedCallback endCallback) {
        this.emitter.setEndCallback(endCallback);
        this.emitter.setDataCallback(callback);
    }

    public DataEmitter getEmitter() {
        return this.emitter;
    }

    public void parse(DataEmitter emitter, CompletedCallback completed) {
        this.emitter = emitter;
        emitter.setEndCallback(completed);
        emitter.setDataCallback(new NullDataCallback());
    }
}
