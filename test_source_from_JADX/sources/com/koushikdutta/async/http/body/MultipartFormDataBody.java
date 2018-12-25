package com.koushikdutta.async.http.body;

import com.koushikdutta.async.ByteBufferList;
import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.DataSink;
import com.koushikdutta.async.LineEmitter;
import com.koushikdutta.async.LineEmitter.StringCallback;
import com.koushikdutta.async.Util;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.callback.ContinuationCallback;
import com.koushikdutta.async.callback.DataCallback;
import com.koushikdutta.async.callback.DataCallback.NullDataCallback;
import com.koushikdutta.async.future.Continuation;
import com.koushikdutta.async.http.AsyncHttpRequest;
import com.koushikdutta.async.http.Headers;
import com.koushikdutta.async.http.Multimap;
import com.koushikdutta.async.http.server.BoundaryEmitter;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

public class MultipartFormDataBody extends BoundaryEmitter implements AsyncHttpRequestBody<Multimap> {
    public static final String CONTENT_TYPE = "multipart/form-data";
    String contentType = CONTENT_TYPE;
    Headers formData;
    ByteBufferList last;
    String lastName;
    LineEmitter liner;
    MultipartCallback mCallback;
    private ArrayList<Part> mParts;
    int totalToWrite;
    int written;

    public interface MultipartCallback {
        void onPart(Part part);
    }

    public void parse(DataEmitter emitter, CompletedCallback completed) {
        setDataEmitter(emitter);
        setEndCallback(completed);
    }

    void handleLast() {
        if (this.last != null) {
            if (this.formData == null) {
                this.formData = new Headers();
            }
            this.formData.add(this.lastName, this.last.peekString());
            this.lastName = null;
            this.last = null;
        }
    }

    public String getField(String name) {
        if (this.formData == null) {
            return null;
        }
        return this.formData.get(name);
    }

    protected void onBoundaryEnd() {
        super.onBoundaryEnd();
        handleLast();
    }

    protected void onBoundaryStart() {
        final Headers headers = new Headers();
        this.liner = new LineEmitter();
        this.liner.setLineCallback(new StringCallback() {

            /* renamed from: com.koushikdutta.async.http.body.MultipartFormDataBody$1$1 */
            class C11481 implements DataCallback {
                C11481() {
                }

                public void onDataAvailable(DataEmitter emitter, ByteBufferList bb) {
                    bb.get(MultipartFormDataBody.this.last);
                }
            }

            public void onStringAvailable(String s) {
                if ("\r".equals(s)) {
                    MultipartFormDataBody.this.handleLast();
                    MultipartFormDataBody.this.liner = null;
                    MultipartFormDataBody.this.setDataCallback(null);
                    Part part = new Part(headers);
                    if (MultipartFormDataBody.this.mCallback != null) {
                        MultipartFormDataBody.this.mCallback.onPart(part);
                    }
                    if (MultipartFormDataBody.this.getDataCallback() == null) {
                        if (part.isFile()) {
                            MultipartFormDataBody.this.setDataCallback(new NullDataCallback());
                            return;
                        }
                        MultipartFormDataBody.this.lastName = part.getName();
                        MultipartFormDataBody.this.last = new ByteBufferList();
                        MultipartFormDataBody.this.setDataCallback(new C11481());
                    }
                } else {
                    headers.addLine(s);
                }
            }
        });
        setDataCallback(this.liner);
    }

    public MultipartFormDataBody(String[] values) {
        for (String value : values) {
            String[] splits = value.split("=");
            if (splits.length == 2) {
                if ("boundary".equals(splits[0])) {
                    setBoundary(splits[1]);
                    return;
                }
            }
        }
        report(new Exception("No boundary found for multipart/form-data"));
    }

    public void setMultipartCallback(MultipartCallback callback) {
        this.mCallback = callback;
    }

    public MultipartCallback getMultipartCallback() {
        return this.mCallback;
    }

    public void write(AsyncHttpRequest request, final DataSink sink, final CompletedCallback completed) {
        if (this.mParts != null) {
            Continuation c = new Continuation(new CompletedCallback() {
                public void onCompleted(Exception ex) {
                    completed.onCompleted(ex);
                }
            });
            Iterator it = this.mParts.iterator();
            while (it.hasNext()) {
                final Part part = (Part) it.next();
                c.add(new ContinuationCallback() {
                    public void onContinue(Continuation continuation, CompletedCallback next) throws Exception {
                        byte[] bytes = part.getRawHeaders().toPrefixString(MultipartFormDataBody.this.getBoundaryStart()).getBytes();
                        Util.writeAll(sink, bytes, next);
                        MultipartFormDataBody multipartFormDataBody = MultipartFormDataBody.this;
                        multipartFormDataBody.written += bytes.length;
                    }
                }).add(new ContinuationCallback() {
                    public void onContinue(Continuation continuation, CompletedCallback next) throws Exception {
                        long partLength = part.length();
                        if (partLength >= 0) {
                            MultipartFormDataBody multipartFormDataBody = MultipartFormDataBody.this;
                            multipartFormDataBody.written = (int) (((long) multipartFormDataBody.written) + partLength);
                        }
                        part.write(sink, next);
                    }
                }).add(new ContinuationCallback() {
                    public void onContinue(Continuation continuation, CompletedCallback next) throws Exception {
                        byte[] bytes = "\r\n".getBytes();
                        Util.writeAll(sink, bytes, next);
                        MultipartFormDataBody multipartFormDataBody = MultipartFormDataBody.this;
                        multipartFormDataBody.written += bytes.length;
                    }
                });
            }
            c.add(new ContinuationCallback() {
                static final /* synthetic */ boolean $assertionsDisabled = false;

                static {
                    Class cls = MultipartFormDataBody.class;
                }

                public void onContinue(Continuation continuation, CompletedCallback next) throws Exception {
                    byte[] bytes = MultipartFormDataBody.this.getBoundaryEnd().getBytes();
                    Util.writeAll(sink, bytes, next);
                    MultipartFormDataBody multipartFormDataBody = MultipartFormDataBody.this;
                    multipartFormDataBody.written += bytes.length;
                }
            });
            c.start();
        }
    }

    public String getContentType() {
        StringBuilder stringBuilder;
        if (getBoundary() == null) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("----------------------------");
            stringBuilder.append(UUID.randomUUID().toString().replace("-", ""));
            setBoundary(stringBuilder.toString());
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append(this.contentType);
        stringBuilder.append("; boundary=");
        stringBuilder.append(getBoundary());
        return stringBuilder.toString();
    }

    public boolean readFullyOnRequest() {
        return false;
    }

    public int length() {
        if (getBoundary() == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("----------------------------");
            stringBuilder.append(UUID.randomUUID().toString().replace("-", ""));
            setBoundary(stringBuilder.toString());
        }
        int length = 0;
        Iterator it = this.mParts.iterator();
        while (it.hasNext()) {
            Part part = (Part) it.next();
            String partHeader = part.getRawHeaders().toPrefixString(getBoundaryStart());
            if (part.length() == -1) {
                return -1;
            }
            length = (int) (((long) length) + ((part.length() + ((long) partHeader.getBytes().length)) + ((long) "\r\n".length())));
        }
        length += getBoundaryEnd().getBytes().length;
        this.totalToWrite = length;
        return length;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void addFilePart(String name, File file) {
        addPart(new FilePart(name, file));
    }

    public void addStringPart(String name, String value) {
        addPart(new StringPart(name, value));
    }

    public void addPart(Part part) {
        if (this.mParts == null) {
            this.mParts = new ArrayList();
        }
        this.mParts.add(part);
    }

    public Multimap get() {
        return new Multimap(this.formData.getMultiMap());
    }
}
