package com.thoughtworks.xstream.converters;

import java.util.Iterator;

public interface DataHolder {
    Object get(Object obj);

    Iterator keys();

    void put(Object obj, Object obj2);
}
