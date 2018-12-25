package com.koushikdutta.async.http.body;

import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.DataSink;
import com.koushikdutta.async.Util;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.async.http.AsyncHttpRequest;
import com.koushikdutta.async.parser.StringParser;

public class StringBody implements AsyncHttpRequestBody<String> {
    public static final String CONTENT_TYPE = "text/plain";
    byte[] mBodyBytes;
    String string;

    public StringBody(String string) {
        this();
        this.string = string;
    }

    public void parse(DataEmitter emitter, final CompletedCallback completed) {
        new StringParser().parse(emitter).setCallback(new FutureCallback<String>() {
            public void onCompleted(Exception e, String result) {
                StringBody.this.string = result;
                completed.onCompleted(e);
            }
        });
    }

    public void write(AsyncHttpRequest request, DataSink sink, CompletedCallback completed) {
        if (this.mBodyBytes == null) {
            this.mBodyBytes = this.string.getBytes();
        }
        Util.writeAll(sink, this.mBodyBytes, completed);
    }

    public String getContentType() {
        return CONTENT_TYPE;
    }

    public boolean readFullyOnRequest() {
        return true;
    }

    public int length() {
        if (this.mBodyBytes == null) {
            this.mBodyBytes = this.string.getBytes();
        }
        return this.mBodyBytes.length;
    }

    public String toString() {
        return this.string;
    }

    public String get() {
        return toString();
    }
}
