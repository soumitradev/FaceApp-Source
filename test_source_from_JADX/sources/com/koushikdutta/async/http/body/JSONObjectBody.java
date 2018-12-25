package com.koushikdutta.async.http.body;

import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.DataSink;
import com.koushikdutta.async.Util;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.async.http.AsyncHttpRequest;
import com.koushikdutta.async.parser.JSONObjectParser;
import org.json.JSONObject;

public class JSONObjectBody implements AsyncHttpRequestBody<JSONObject> {
    public static final String CONTENT_TYPE = "application/json";
    JSONObject json;
    byte[] mBodyBytes;

    public JSONObjectBody(JSONObject json) {
        this();
        this.json = json;
    }

    public void parse(DataEmitter emitter, final CompletedCallback completed) {
        new JSONObjectParser().parse(emitter).setCallback(new FutureCallback<JSONObject>() {
            public void onCompleted(Exception e, JSONObject result) {
                JSONObjectBody.this.json = result;
                completed.onCompleted(e);
            }
        });
    }

    public void write(AsyncHttpRequest request, DataSink sink, CompletedCallback completed) {
        Util.writeAll(sink, this.mBodyBytes, completed);
    }

    public String getContentType() {
        return "application/json";
    }

    public boolean readFullyOnRequest() {
        return true;
    }

    public int length() {
        this.mBodyBytes = this.json.toString().getBytes();
        return this.mBodyBytes.length;
    }

    public JSONObject get() {
        return this.json;
    }
}
