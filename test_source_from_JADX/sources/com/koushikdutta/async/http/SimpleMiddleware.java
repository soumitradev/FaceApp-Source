package com.koushikdutta.async.http;

import com.koushikdutta.async.future.Cancellable;
import com.koushikdutta.async.http.AsyncHttpClientMiddleware.GetSocketData;
import com.koushikdutta.async.http.AsyncHttpClientMiddleware.OnBodyDataOnRequestSentData;
import com.koushikdutta.async.http.AsyncHttpClientMiddleware.OnExchangeHeaderData;
import com.koushikdutta.async.http.AsyncHttpClientMiddleware.OnHeadersReceivedDataOnRequestSentData;
import com.koushikdutta.async.http.AsyncHttpClientMiddleware.OnRequestData;
import com.koushikdutta.async.http.AsyncHttpClientMiddleware.OnRequestSentData;
import com.koushikdutta.async.http.AsyncHttpClientMiddleware.OnResponseCompleteDataOnRequestSentData;

public class SimpleMiddleware implements AsyncHttpClientMiddleware {
    public void onRequest(OnRequestData data) {
    }

    public Cancellable getSocket(GetSocketData data) {
        return null;
    }

    public boolean exchangeHeaders(OnExchangeHeaderData data) {
        return false;
    }

    public void onRequestSent(OnRequestSentData data) {
    }

    public void onHeadersReceived(OnHeadersReceivedDataOnRequestSentData data) {
    }

    public void onBodyDecoder(OnBodyDataOnRequestSentData data) {
    }

    public void onResponseComplete(OnResponseCompleteDataOnRequestSentData data) {
    }
}
