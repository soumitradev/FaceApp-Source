package com.thoughtworks.xstream.converters.enums;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class EnumConverter implements Converter {
    public boolean canConvert(Class type) {
        if (!type.isEnum()) {
            if (!Enum.class.isAssignableFrom(type)) {
                return false;
            }
        }
        return true;
    }

    public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
        writer.setValue(((Enum) source).name());
    }

    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        Class type = context.getRequiredType();
        if (type.getSuperclass() != Enum.class) {
            type = type.getSuperclass();
        }
        String name = reader.getValue();
        try {
            return Enum.valueOf(type, name);
        } catch (IllegalArgumentException e) {
            for (Enum c : (Enum[]) type.getEnumConstants()) {
                if (c.name().equalsIgnoreCase(name)) {
                    return c;
                }
            }
            throw e;
        }
    }
}
