package com.thoughtworks.xstream.converters;

public interface ConverterMatcher {
    boolean canConvert(Class cls);
}
