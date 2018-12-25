package com.thoughtworks.xstream.mapper;

import com.thoughtworks.xstream.InitializationException;
import com.thoughtworks.xstream.mapper.Mapper.Null;
import java.util.HashMap;
import java.util.Map;

public class DefaultImplementationsMapper extends MapperWrapper {
    private transient Map implToType = new HashMap();
    private final Map typeToImpl = new HashMap();

    public DefaultImplementationsMapper(Mapper wrapped) {
        super(wrapped);
        addDefaults();
    }

    protected void addDefaults() {
        addDefaultImplementation(null, Null.class);
        addDefaultImplementation(Boolean.class, Boolean.TYPE);
        addDefaultImplementation(Character.class, Character.TYPE);
        addDefaultImplementation(Integer.class, Integer.TYPE);
        addDefaultImplementation(Float.class, Float.TYPE);
        addDefaultImplementation(Double.class, Double.TYPE);
        addDefaultImplementation(Short.class, Short.TYPE);
        addDefaultImplementation(Byte.class, Byte.TYPE);
        addDefaultImplementation(Long.class, Long.TYPE);
    }

    public void addDefaultImplementation(Class defaultImplementation, Class ofType) {
        if (defaultImplementation == null || !defaultImplementation.isInterface()) {
            this.typeToImpl.put(ofType, defaultImplementation);
            this.implToType.put(defaultImplementation, ofType);
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Default implementation is not a concrete class: ");
        stringBuilder.append(defaultImplementation.getName());
        throw new InitializationException(stringBuilder.toString());
    }

    public String serializedClass(Class type) {
        Class baseType = (Class) this.implToType.get(type);
        return baseType == null ? super.serializedClass(type) : super.serializedClass(baseType);
    }

    public Class defaultImplementationOf(Class type) {
        if (this.typeToImpl.containsKey(type)) {
            return (Class) this.typeToImpl.get(type);
        }
        return super.defaultImplementationOf(type);
    }

    private Object readResolve() {
        this.implToType = new HashMap();
        for (Object type : this.typeToImpl.keySet()) {
            this.implToType.put(this.typeToImpl.get(type), type);
        }
        return this;
    }
}
