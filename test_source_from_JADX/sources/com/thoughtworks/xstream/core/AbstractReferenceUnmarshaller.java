package com.thoughtworks.xstream.core;

import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.ConverterLookup;
import com.thoughtworks.xstream.core.util.FastStack;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.mapper.Mapper;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractReferenceUnmarshaller extends TreeUnmarshaller {
    private static final Object NULL = new Object();
    private FastStack parentStack = new FastStack(16);
    private Map values = new HashMap();

    protected abstract Object getCurrentReferenceKey();

    protected abstract Object getReferenceKey(String str);

    public AbstractReferenceUnmarshaller(Object root, HierarchicalStreamReader reader, ConverterLookup converterLookup, Mapper mapper) {
        super(root, reader, converterLookup, mapper);
    }

    protected Object convert(Object parent, Class type, Converter converter) {
        if (this.parentStack.size() > 0) {
            Object parentReferenceKey = this.parentStack.peek();
            if (!(parentReferenceKey == null || this.values.containsKey(parentReferenceKey))) {
                this.values.put(parentReferenceKey, parent);
            }
        }
        String attributeName = getMapper().aliasForSystemAttribute("reference");
        Object result = null;
        String reference = attributeName == null ? null : this.reader.getAttribute(attributeName);
        Object cache;
        if (reference != null) {
            cache = this.values.get(getReferenceKey(reference));
            if (cache == null) {
                ConversionException ex = new ConversionException("Invalid reference");
                ex.add("reference", reference);
                throw ex;
            }
            if (cache != NULL) {
                result = cache;
            }
            return result;
        }
        result = getCurrentReferenceKey();
        this.parentStack.push(result);
        cache = super.convert(parent, type, converter);
        if (result != null) {
            this.values.put(result, cache == null ? NULL : cache);
        }
        this.parentStack.popSilently();
        return cache;
    }
}
