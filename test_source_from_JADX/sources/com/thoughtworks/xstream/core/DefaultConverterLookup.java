package com.thoughtworks.xstream.core;

import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.ConverterLookup;
import com.thoughtworks.xstream.converters.ConverterRegistry;
import com.thoughtworks.xstream.core.util.PrioritizedList;
import com.thoughtworks.xstream.mapper.Mapper;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;

public class DefaultConverterLookup implements ConverterLookup, ConverterRegistry, Caching {
    private final PrioritizedList converters = new PrioritizedList();
    private transient Map typeToConverterMap;

    public DefaultConverterLookup() {
        readResolve();
    }

    public DefaultConverterLookup(Mapper mapper) {
    }

    public Converter lookupConverterForType(Class type) {
        Converter cachedConverter = (Converter) this.typeToConverterMap.get(type);
        if (cachedConverter != null) {
            return cachedConverter;
        }
        Iterator iterator = this.converters.iterator();
        while (iterator.hasNext()) {
            Converter converter = (Converter) iterator.next();
            if (converter.canConvert(type)) {
                this.typeToConverterMap.put(type, converter);
                return converter;
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("No converter specified for ");
        stringBuilder.append(type);
        throw new ConversionException(stringBuilder.toString());
    }

    public void registerConverter(Converter converter, int priority) {
        this.converters.add(converter, priority);
        Iterator iter = this.typeToConverterMap.keySet().iterator();
        while (iter.hasNext()) {
            if (converter.canConvert((Class) iter.next())) {
                iter.remove();
            }
        }
    }

    public void flushCache() {
        this.typeToConverterMap.clear();
        Iterator iterator = this.converters.iterator();
        while (iterator.hasNext()) {
            Converter converter = (Converter) iterator.next();
            if (converter instanceof Caching) {
                ((Caching) converter).flushCache();
            }
        }
    }

    private Object readResolve() {
        this.typeToConverterMap = Collections.synchronizedMap(new WeakHashMap());
        return this;
    }
}
