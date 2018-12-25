package com.thoughtworks.xstream.converters.reflection;

import name.antonsmirnov.firmata.FormatHelper;

public class MissingFieldException extends ObjectAccessException {
    private final String className;
    private final String fieldName;

    public MissingFieldException(String className, String fieldName) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("No field '");
        stringBuilder.append(fieldName);
        stringBuilder.append("' found in class '");
        stringBuilder.append(className);
        stringBuilder.append(FormatHelper.QUOTE);
        super(stringBuilder.toString());
        this.className = className;
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    protected String getClassName() {
        return this.className;
    }
}
