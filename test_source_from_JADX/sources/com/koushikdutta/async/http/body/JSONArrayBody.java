package com.koushikdutta.async.http.body;

import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.DataSink;
import com.koushikdutta.async.Util;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.async.http.AsyncHttpRequest;
import com.koushikdutta.async.parser.JSONArrayParser;
import org.json.JSONArray;

public class JSONArrayBody implements AsyncHttpRequestBody<JSONArray> {
    public static final String CONTENT_TYPE = "application/json";
    JSONArray json;
    byte[] mBodyBytes;

    public JSONArrayBody(JSONArray json) {
        this();
        this.json = json;
    }

    public void parse(DataEmitter emitter, final CompletedCallback completed) {
        new JSONArrayParser().parse(emitter).setCallback(new FutureCallback<JSONArray>() {
            public void onCompleted(Exception e, JSONArray result) {
                JSONArrayBody.this.json = result;
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

    public JSONArray get() {
        return this.json;
    }
}
