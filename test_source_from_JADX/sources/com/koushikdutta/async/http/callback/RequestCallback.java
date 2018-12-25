package com.koushikdutta.async.http.callback;

import com.koushikdutta.async.callback.ResultCallback;
import com.koushikdutta.async.http.AsyncHttpResponse;

public interface RequestCallback<T> extends ResultCallback<AsyncHttpResponse, T> {
    void onConnect(AsyncHttpResponse asyncHttpResponse);

    void onProgress(AsyncHttpResponse asyncHttpResponse, long j, long j2);
}
