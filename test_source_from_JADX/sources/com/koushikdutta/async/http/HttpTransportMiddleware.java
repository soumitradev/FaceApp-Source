package com.koushikdutta.async.http;

import android.text.TextUtils;
import com.koushikdutta.async.AsyncSocket;
import com.koushikdutta.async.BufferedDataSink;
import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.DataSink;
import com.koushikdutta.async.LineEmitter;
import com.koushikdutta.async.LineEmitter.StringCallback;
import com.koushikdutta.async.Util;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.http.AsyncHttpClientMiddleware.OnExchangeHeaderData;
import com.koushikdutta.async.http.AsyncHttpClientMiddleware.OnRequestSentData;
import com.koushikdutta.async.http.body.AsyncHttpRequestBody;
import com.koushikdutta.async.http.filter.ChunkedOutputFilter;
import java.io.IOException;
import name.antonsmirnov.firmata.FormatHelper;

public class HttpTransportMiddleware extends SimpleMiddleware {
    public boolean exchangeHeaders(final OnExchangeHeaderData data) {
        Protocol p = Protocol.get(data.protocol);
        if (p != null && p != Protocol.HTTP_1_0 && p != Protocol.HTTP_1_1) {
            return super.exchangeHeaders(data);
        }
        BufferedDataSink bsink;
        DataSink headerSink;
        AsyncHttpRequest request = data.request;
        AsyncHttpRequestBody requestBody = data.request.getBody();
        if (requestBody != null) {
            if (requestBody.length() >= 0) {
                request.getHeaders().set("Content-Length", String.valueOf(requestBody.length()));
                data.response.sink(data.socket);
            } else if ("close".equals(request.getHeaders().get("Connection"))) {
                data.response.sink(data.socket);
            } else {
                request.getHeaders().set("Transfer-Encoding", "Chunked");
                data.response.sink(new ChunkedOutputFilter(data.socket));
            }
        }
        String rs = request.getHeaders().toPrefixString(request.getRequestLine().toString());
        byte[] rsBytes = rs.getBytes();
        boolean waitForBody = requestBody != null && requestBody.length() >= 0 && requestBody.length() + rsBytes.length < 1024;
        if (waitForBody) {
            bsink = new BufferedDataSink(data.response.sink());
            bsink.forceBuffering(true);
            data.response.sink(bsink);
            headerSink = bsink;
        } else {
            bsink = null;
            headerSink = data.socket;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n");
        stringBuilder.append(rs);
        request.logv(stringBuilder.toString());
        final CompletedCallback sentCallback = data.sendHeadersCallback;
        Util.writeAll(headerSink, rsBytes, new CompletedCallback() {
            public void onCompleted(Exception ex) {
                Util.end(sentCallback, ex);
                if (bsink != null) {
                    bsink.forceBuffering(false);
                    bsink.setMaxBuffer(0);
                }
            }
        });
        StringCallback headerCallback = new StringCallback() {
            Headers mRawHeaders = new Headers();
            String statusLine;

            public void onStringAvailable(String s) {
                try {
                    s = s.trim();
                    if (this.statusLine == null) {
                        this.statusLine = s;
                    } else if (TextUtils.isEmpty(s)) {
                        String[] parts = this.statusLine.split(FormatHelper.SPACE, 3);
                        if (parts.length < 2) {
                            throw new Exception(new IOException("Not HTTP"));
                        }
                        data.response.headers(this.mRawHeaders);
                        String protocol = parts[0];
                        data.response.protocol(protocol);
                        data.response.code(Integer.parseInt(parts[1]));
                        data.response.message(parts.length == 3 ? parts[2] : "");
                        data.receiveHeadersCallback.onCompleted(null);
                        AsyncSocket socket = data.response.socket();
                        if (socket != null) {
                            DataEmitter emitter;
                            if ("HEAD".equalsIgnoreCase(data.request.getMethod())) {
                                emitter = EndEmitter.create(socket.getServer(), null);
                            } else {
                                emitter = HttpUtil.getBodyDecoder(socket, Protocol.get(protocol), this.mRawHeaders, false);
                            }
                            data.response.emitter(emitter);
                        }
                    } else {
                        this.mRawHeaders.addLine(s);
                    }
                } catch (Exception ex) {
                    data.receiveHeadersCallback.onCompleted(ex);
                }
            }
        };
        LineEmitter liner = new LineEmitter();
        data.socket.setDataCallback(liner);
        liner.setLineCallback(headerCallback);
        return true;
    }

    public void onRequestSent(OnRequestSentData data) {
        Protocol p = Protocol.get(data.protocol);
        if ((p == null || p == Protocol.HTTP_1_0 || p == Protocol.HTTP_1_1) && (data.response.sink() instanceof ChunkedOutputFilter)) {
            data.response.sink().end();
        }
    }
}
