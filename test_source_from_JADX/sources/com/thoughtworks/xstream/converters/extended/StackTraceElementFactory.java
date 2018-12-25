package com.thoughtworks.xstream.converters.extended;

import com.thoughtworks.xstream.converters.ConversionException;
import java.lang.reflect.Field;

public class StackTraceElementFactory {
    public StackTraceElement nativeMethodElement(String declaringClass, String methodName) {
        return create(declaringClass, methodName, "Native Method", -2);
    }

    public StackTraceElement unknownSourceElement(String declaringClass, String methodName) {
        return create(declaringClass, methodName, "Unknown Source", -1);
    }

    public StackTraceElement element(String declaringClass, String methodName, String fileName) {
        return create(declaringClass, methodName, fileName, -1);
    }

    public StackTraceElement element(String declaringClass, String methodName, String fileName, int lineNumber) {
        return create(declaringClass, methodName, fileName, lineNumber);
    }

    protected StackTraceElement create(String declaringClass, String methodName, String fileName, int lineNumber) {
        StackTraceElement result = new Throwable().getStackTrace()[0];
        setField(result, "declaringClass", declaringClass);
        setField(result, "methodName", methodName);
        setField(result, "fileName", fileName);
        setField(result, "lineNumber", new Integer(lineNumber));
        return result;
    }

    private void setField(StackTraceElement element, String fieldName, Object value) {
        try {
            Field field = StackTraceElement.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(element, value);
        } catch (Throwable e) {
            throw new ConversionException(e);
        }
    }
}
