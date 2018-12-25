package com.thoughtworks.xstream.mapper;

import net.sf.cglib.proxy.Enhancer;

public class CGLIBMapper extends MapperWrapper {
    private static String DEFAULT_NAMING_MARKER = "$$EnhancerByCGLIB$$";
    private final String alias;

    public interface Marker {
    }

    public CGLIBMapper(Mapper wrapped) {
        this(wrapped, "CGLIB-enhanced-proxy");
    }

    public CGLIBMapper(Mapper wrapped, String alias) {
        super(wrapped);
        this.alias = alias;
    }

    public String serializedClass(Class type) {
        String serializedName = super.serializedClass(type);
        if (type == null) {
            return serializedName;
        }
        String typeName = type.getName();
        String str = (typeName.equals(serializedName) && typeName.indexOf(DEFAULT_NAMING_MARKER) > 0 && Enhancer.isEnhanced(type)) ? this.alias : serializedName;
        return str;
    }

    public Class realClass(String elementName) {
        return elementName.equals(this.alias) ? Marker.class : super.realClass(elementName);
    }
}
