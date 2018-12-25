package com.thoughtworks.xstream.security;

import com.thoughtworks.xstream.XStreamException;

public class ForbiddenClassException extends XStreamException {
    public ForbiddenClassException(Class type) {
        super(type == null ? "null" : type.getName());
    }
}
