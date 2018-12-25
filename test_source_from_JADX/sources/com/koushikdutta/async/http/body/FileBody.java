package com.koushikdutta.async.http.body;

import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.DataSink;
import com.koushikdutta.async.Util;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.http.AsyncHttpRequest;
import java.io.File;

public class FileBody implements AsyncHttpRequestBody<File> {
    public static final String CONTENT_TYPE = "application/binary";
    String contentType = "application/binary";
    File file;

    public FileBody(File file) {
        this.file = file;
    }

    public FileBody(File file, String contentType) {
        this.file = file;
        this.contentType = contentType;
    }

    public void write(AsyncHttpRequest request, DataSink sink, CompletedCallback completed) {
        Util.pump(this.file, sink, completed);
    }

    public void parse(DataEmitter emitter, CompletedCallback completed) {
        throw new AssertionError("not implemented");
    }

    public String getContentType() {
        return this.contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public boolean readFullyOnRequest() {
        throw new AssertionError("not implemented");
    }

    public int length() {
        return (int) this.file.length();
    }

    public File get() {
        return this.file;
    }
}
