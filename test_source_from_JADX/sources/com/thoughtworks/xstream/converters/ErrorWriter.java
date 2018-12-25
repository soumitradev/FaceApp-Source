package com.thoughtworks.xstream.converters;

import java.util.Iterator;

public interface ErrorWriter {
    void add(String str, String str2);

    String get(String str);

    Iterator keys();

    void set(String str, String str2);
}
