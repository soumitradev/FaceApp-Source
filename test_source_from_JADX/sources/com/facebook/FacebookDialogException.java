package com.facebook;

public class FacebookDialogException extends FacebookException {
    static final long serialVersionUID = 1;
    private int errorCode;
    private String failingUrl;

    public FacebookDialogException(String message, int errorCode, String failingUrl) {
        super(message);
        this.errorCode = errorCode;
        this.failingUrl = failingUrl;
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public String getFailingUrl() {
        return this.failingUrl;
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{FacebookDialogException: ");
        stringBuilder.append("errorCode: ");
        stringBuilder.append(getErrorCode());
        stringBuilder.append(", message: ");
        stringBuilder.append(getMessage());
        stringBuilder.append(", url: ");
        stringBuilder.append(getFailingUrl());
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}
