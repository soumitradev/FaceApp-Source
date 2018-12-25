package com.koushikdutta.async.http.body;

import com.koushikdutta.async.ByteBufferList;
import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.DataSink;
import com.koushikdutta.async.Util;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.callback.DataCallback;
import com.koushikdutta.async.http.AsyncHttpRequest;
import com.koushikdutta.async.http.Multimap;
import com.koushikdutta.async.http.NameValuePair;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import kotlin.text.Typography;

public class UrlEncodedFormBody implements AsyncHttpRequestBody<Multimap> {
    public static final String CONTENT_TYPE = "application/x-www-form-urlencoded";
    private byte[] mBodyBytes;
    private Multimap mParameters;

    public UrlEncodedFormBody(Multimap parameters) {
        this.mParameters = parameters;
    }

    public UrlEncodedFormBody(List<NameValuePair> parameters) {
        this.mParameters = new Multimap((List) parameters);
    }

    private void buildData() {
        boolean first = true;
        StringBuilder b = new StringBuilder();
        try {
            Iterator it = this.mParameters.iterator();
            while (it.hasNext()) {
                NameValuePair pair = (NameValuePair) it.next();
                if (pair.getValue() != null) {
                    if (!first) {
                        b.append(Typography.amp);
                    }
                    first = false;
                    b.append(URLEncoder.encode(pair.getName(), "UTF-8"));
                    b.append('=');
                    b.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
                }
            }
            this.mBodyBytes = b.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError(e);
        }
    }

    public void write(AsyncHttpRequest request, DataSink response, CompletedCallback completed) {
        if (this.mBodyBytes == null) {
            buildData();
        }
        Util.writeAll(response, this.mBodyBytes, completed);
    }

    public String getContentType() {
        return "application/x-www-form-urlencoded; charset=utf-8";
    }

    public void parse(DataEmitter emitter, final CompletedCallback completed) {
        final ByteBufferList data = new ByteBufferList();
        emitter.setDataCallback(new DataCallback() {
            public void onDataAvailable(DataEmitter emitter, ByteBufferList bb) {
                bb.get(data);
            }
        });
        emitter.setEndCallback(new CompletedCallback() {
            public void onCompleted(Exception ex) {
                if (ex != null) {
                    completed.onCompleted(ex);
                    return;
                }
                try {
                    UrlEncodedFormBody.this.mParameters = Multimap.parseUrlEncoded(data.readString());
                    completed.onCompleted(null);
                } catch (Exception e) {
                    completed.onCompleted(e);
                }
            }
        });
    }

    public boolean readFullyOnRequest() {
        return true;
    }

    public int length() {
        if (this.mBodyBytes == null) {
            buildData();
        }
        return this.mBodyBytes.length;
    }

    public Multimap get() {
        return this.mParameters;
    }
}
