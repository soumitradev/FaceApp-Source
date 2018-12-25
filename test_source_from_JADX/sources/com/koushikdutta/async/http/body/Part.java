package com.koushikdutta.async.http.body;

import com.koushikdutta.async.DataSink;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.http.Headers;
import com.koushikdutta.async.http.Multimap;
import com.koushikdutta.async.http.NameValuePair;
import java.io.File;
import java.util.List;
import java.util.Locale;

public class Part {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final String CONTENT_DISPOSITION = "Content-Disposition";
    private long length = -1;
    Multimap mContentDisposition;
    Headers mHeaders;

    public Part(Headers headers) {
        this.mHeaders = headers;
        this.mContentDisposition = Multimap.parseSemicolonDelimited(this.mHeaders.get("Content-Disposition"));
    }

    public String getName() {
        return this.mContentDisposition.getString("name");
    }

    public Part(String name, long length, List<NameValuePair> contentDisposition) {
        this.length = length;
        this.mHeaders = new Headers();
        StringBuilder builder = new StringBuilder(String.format(Locale.ENGLISH, "form-data; name=\"%s\"", new Object[]{name}));
        if (contentDisposition != null) {
            for (NameValuePair pair : contentDisposition) {
                builder.append(String.format(Locale.ENGLISH, "; %s=\"%s\"", new Object[]{pair.getName(), pair.getValue()}));
            }
        }
        this.mHeaders.set("Content-Disposition", builder.toString());
        this.mContentDisposition = Multimap.parseSemicolonDelimited(this.mHeaders.get("Content-Disposition"));
    }

    public Headers getRawHeaders() {
        return this.mHeaders;
    }

    public String getContentType() {
        return this.mHeaders.get("Content-Type");
    }

    public void setContentType(String contentType) {
        this.mHeaders.set("Content-Type", contentType);
    }

    public String getFilename() {
        String file = this.mContentDisposition.getString("filename");
        if (file == null) {
            return null;
        }
        return new File(file).getName();
    }

    public boolean isFile() {
        return this.mContentDisposition.containsKey("filename");
    }

    public long length() {
        return this.length;
    }

    public void write(DataSink sink, CompletedCallback callback) {
    }
}
