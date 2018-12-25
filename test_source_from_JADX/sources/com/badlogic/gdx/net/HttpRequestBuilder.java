package com.badlogic.gdx.net;

import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Pools;
import java.io.InputStream;
import java.util.Map;

public class HttpRequestBuilder {
    public static String baseUrl = "";
    public static int defaultTimeout = 1000;
    public static Json json = new Json();
    private HttpRequest httpRequest;

    public HttpRequestBuilder newRequest() {
        if (this.httpRequest != null) {
            throw new IllegalStateException("A new request has already been started. Call HttpRequestBuilder.build() first.");
        }
        this.httpRequest = (HttpRequest) Pools.obtain(HttpRequest.class);
        this.httpRequest.setTimeOut(defaultTimeout);
        return this;
    }

    public HttpRequestBuilder method(String httpMethod) {
        validate();
        this.httpRequest.setMethod(httpMethod);
        return this;
    }

    public HttpRequestBuilder url(String url) {
        validate();
        HttpRequest httpRequest = this.httpRequest;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(baseUrl);
        stringBuilder.append(url);
        httpRequest.setUrl(stringBuilder.toString());
        return this;
    }

    public HttpRequestBuilder timeout(int timeOut) {
        validate();
        this.httpRequest.setTimeOut(timeOut);
        return this;
    }

    public HttpRequestBuilder followRedirects(boolean followRedirects) {
        validate();
        this.httpRequest.setFollowRedirects(followRedirects);
        return this;
    }

    public HttpRequestBuilder header(String name, String value) {
        validate();
        this.httpRequest.setHeader(name, value);
        return this;
    }

    public HttpRequestBuilder content(String content) {
        validate();
        this.httpRequest.setContent(content);
        return this;
    }

    public HttpRequestBuilder content(InputStream contentStream, long contentLength) {
        validate();
        this.httpRequest.setContent(contentStream, contentLength);
        return this;
    }

    public HttpRequestBuilder formEncodedContent(Map<String, String> content) {
        validate();
        this.httpRequest.setHeader("Content-Type", "application/x-www-form-urlencoded");
        this.httpRequest.setContent(HttpParametersUtils.convertHttpParameters(content));
        return this;
    }

    public HttpRequestBuilder jsonContent(Object content) {
        validate();
        this.httpRequest.setHeader("Content-Type", "application/json");
        this.httpRequest.setContent(json.toJson(content));
        return this;
    }

    public HttpRequestBuilder basicAuthentication(String username, String password) {
        validate();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Basic ");
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(username);
        stringBuilder2.append(":");
        stringBuilder2.append(password);
        stringBuilder.append(Base64Coder.encodeString(stringBuilder2.toString()));
        this.httpRequest.setHeader("Authorization", stringBuilder.toString());
        return this;
    }

    public HttpRequest build() {
        validate();
        HttpRequest request = this.httpRequest;
        this.httpRequest = null;
        return request;
    }

    private void validate() {
        if (this.httpRequest == null) {
            throw new IllegalStateException("A new request has not been started yet. Call HttpRequestBuilder.newRequest() first.");
        }
    }
}
