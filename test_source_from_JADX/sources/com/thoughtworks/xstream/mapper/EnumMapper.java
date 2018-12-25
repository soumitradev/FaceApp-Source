package com.thoughtworks.xstream.mapper;

import com.thoughtworks.xstream.converters.ConverterLookup;
import com.thoughtworks.xstream.converters.SingleValueConverter;
import com.thoughtworks.xstream.converters.enums.EnumSingleValueConverter;
import com.thoughtworks.xstream.core.Caching;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public class EnumMapper extends MapperWrapper implements Caching {
    private transient AttributeMapper attributeMapper;
    private transient Map<Class, SingleValueConverter> enumConverterMap;

    @Deprecated
    public EnumMapper(Mapper wrapped, ConverterLookup lookup) {
        super(wrapped);
        readResolve();
    }

    public EnumMapper(Mapper wrapped) {
        super(wrapped);
        readResolve();
    }

    public String serializedClass(Class type) {
        if (type == null) {
            return super.serializedClass(type);
        }
        if (Enum.class.isAssignableFrom(type) && type.getSuperclass() != Enum.class) {
            return super.serializedClass(type.getSuperclass());
        }
        if (EnumSet.class.isAssignableFrom(type)) {
            return super.serializedClass(EnumSet.class);
        }
        return super.serializedClass(type);
    }

    public boolean isImmutableValueType(Class type) {
        if (!Enum.class.isAssignableFrom(type)) {
            if (!super.isImmutableValueType(type)) {
                return false;
            }
        }
        return true;
    }

    public SingleValueConverter getConverterFromItemType(String fieldName, Class type, Class definedIn) {
        SingleValueConverter converter = getLocalConverter(fieldName, type, definedIn);
        return converter == null ? super.getConverterFromItemType(fieldName, type, definedIn) : converter;
    }

    public SingleValueConverter getConverterFromAttribute(Class definedIn, String attribute, Class type) {
        SingleValueConverter converter = getLocalConverter(attribute, type, definedIn);
        return converter == null ? super.getConverterFromAttribute(definedIn, attribute, type) : converter;
    }

    private SingleValueConverter getLocalConverter(String fieldName, Class type, Class definedIn) {
        if (this.attributeMapper == null || !Enum.class.isAssignableFrom(type) || !this.attributeMapper.shouldLookForSingleValueConverter(fieldName, type, definedIn)) {
            return null;
        }
        SingleValueConverter singleValueConverter;
        synchronized (this.enumConverterMap) {
            singleValueConverter = (SingleValueConverter) this.enumConverterMap.get(type);
            if (singleValueConverter == null) {
                singleValueConverter = super.getConverterFromItemType(fieldName, type, definedIn);
                if (singleValueConverter == null) {
                    singleValueConverter = new EnumSingleValueConverter(type);
                }
                this.enumConverterMap.put(type, singleValueConverter);
            }
        }
        return singleValueConverter;
    }

    public void flushCache() {
        if (this.enumConverterMap.size() > 0) {
            synchronized (this.enumConverterMap) {
                this.enumConverterMap.clear();
            }
        }
    }

    private Object readResolve() {
        this.enumConverterMap = new HashMap();
        this.attributeMapper = (AttributeMapper) lookupMapperOfType(AttributeMapper.class);
        return this;
    }
}
