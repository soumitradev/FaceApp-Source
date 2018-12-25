package com.koushikdutta.async.http.body;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class StringPart extends StreamPart {
    String value;

    public StringPart(String name, String value) {
        super(name, (long) value.getBytes().length, null);
        this.value = value;
    }

    protected InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(this.value.getBytes());
    }
}
