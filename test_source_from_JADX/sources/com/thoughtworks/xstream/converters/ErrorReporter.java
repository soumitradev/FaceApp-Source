package com.thoughtworks.xstream.converters;

public interface ErrorReporter {
    void appendErrors(ErrorWriter errorWriter);
}
