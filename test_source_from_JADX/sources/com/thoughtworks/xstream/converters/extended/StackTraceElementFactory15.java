package com.thoughtworks.xstream.converters.extended;

class StackTraceElementFactory15 extends StackTraceElementFactory {
    StackTraceElementFactory15() {
    }

    protected StackTraceElement create(String declaringClass, String methodName, String fileName, int lineNumber) {
        return new StackTraceElement(declaringClass, methodName, fileName, lineNumber);
    }
}
