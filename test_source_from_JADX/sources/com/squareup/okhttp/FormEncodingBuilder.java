package com.squareup.okhttp;

import com.squareup.okhttp.internal.Util;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import kotlin.text.Typography;

public final class FormEncodingBuilder {
    private static final MediaType CONTENT_TYPE = MediaType.parse("application/x-www-form-urlencoded");
    private final StringBuilder content = new StringBuilder();

    public FormEncodingBuilder add(String name, String value) {
        if (this.content.length() > 0) {
            this.content.append(Typography.amp);
        }
        try {
            StringBuilder stringBuilder = this.content;
            stringBuilder.append(URLEncoder.encode(name, "UTF-8"));
            stringBuilder.append('=');
            stringBuilder.append(URLEncoder.encode(value, "UTF-8"));
            return this;
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError(e);
        }
    }

    public RequestBody build() {
        if (this.content.length() == 0) {
            throw new IllegalStateException("Form encoded body must have at least one part.");
        }
        return RequestBody.create(CONTENT_TYPE, this.content.toString().getBytes(Util.UTF_8));
    }
}
