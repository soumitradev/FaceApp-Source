package com.koushikdutta.async.http.server;

import com.koushikdutta.async.AsyncSocket;
import com.koushikdutta.async.DataSink;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.http.AsyncHttpResponse;
import com.koushikdutta.async.http.Headers;
import java.io.File;
import java.io.InputStream;
import org.json.JSONObject;

public interface AsyncHttpServerResponse extends DataSink, CompletedCallback {
    int code();

    AsyncHttpServerResponse code(int i);

    void end();

    Headers getHeaders();

    AsyncSocket getSocket();

    void onCompleted(Exception exception);

    void proxy(AsyncHttpResponse asyncHttpResponse);

    void redirect(String str);

    void send(String str);

    void send(String str, String str2);

    void send(String str, byte[] bArr);

    void send(JSONObject jSONObject);

    void sendFile(File file);

    void sendStream(InputStream inputStream, long j);

    void setContentType(String str);

    void writeHead();
}
