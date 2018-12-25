package com.koushikdutta.async.http;

import android.text.TextUtils;

public class BasicNameValuePair implements NameValuePair, Cloneable {
    private final String name;
    private final String value;

    public BasicNameValuePair(String name, String value) {
        if (name == null) {
            throw new IllegalArgumentException("Name may not be null");
        }
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return this.name;
    }

    public String getValue() {
        return this.value;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.name);
        stringBuilder.append("=");
        stringBuilder.append(this.value);
        return stringBuilder.toString();
    }

    public boolean equals(Object object) {
        boolean z = false;
        if (object == null) {
            return false;
        }
        if (this == object) {
            return true;
        }
        if (!(object instanceof NameValuePair)) {
            return false;
        }
        BasicNameValuePair that = (BasicNameValuePair) object;
        if (this.name.equals(that.name) && TextUtils.equals(this.value, that.value)) {
            z = true;
        }
        return z;
    }

    public int hashCode() {
        return this.name.hashCode() ^ this.value.hashCode();
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
