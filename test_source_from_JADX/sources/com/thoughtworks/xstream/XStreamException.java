package com.thoughtworks.xstream;

import com.thoughtworks.xstream.core.BaseException;

public class XStreamException extends BaseException {
    private Throwable cause;

    protected XStreamException() {
        this("", null);
    }

    public XStreamException(String message) {
        this(message, null);
    }

    public XStreamException(Throwable cause) {
        this("", cause);
    }

    public XStreamException(String message, Throwable cause) {
        String str;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(message);
        if (cause == null) {
            str = "";
        } else {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append(" : ");
            stringBuilder2.append(cause.getMessage());
            str = stringBuilder2.toString();
        }
        stringBuilder.append(str);
        super(stringBuilder.toString());
        this.cause = cause;
    }

    public Throwable getCause() {
        return this.cause;
    }
}
