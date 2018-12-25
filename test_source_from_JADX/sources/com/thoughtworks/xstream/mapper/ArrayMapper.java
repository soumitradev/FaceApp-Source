package com.thoughtworks.xstream.mapper;

import com.thoughtworks.xstream.core.util.Primitives;
import org.catrobat.catroid.common.Constants;

public class ArrayMapper extends MapperWrapper {
    public ArrayMapper(Mapper wrapped) {
        super(wrapped);
    }

    public String serializedClass(Class type) {
        StringBuffer arraySuffix = new StringBuffer();
        String name = null;
        while (type.isArray()) {
            name = super.serializedClass(type);
            if (!type.getName().equals(name)) {
                break;
            }
            type = type.getComponentType();
            arraySuffix.append("-array");
            name = null;
        }
        if (name == null) {
            name = boxedTypeName(type);
        }
        if (name == null) {
            name = super.serializedClass(type);
        }
        if (arraySuffix.length() <= 0) {
            return name;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(name);
        stringBuilder.append(arraySuffix);
        return stringBuilder.toString();
    }

    public Class realClass(String elementName) {
        String elementName2 = elementName;
        int dimensions = 0;
        while (elementName2.endsWith("-array")) {
            elementName2 = elementName2.substring(0, elementName2.length() - 6);
            dimensions++;
        }
        if (dimensions <= 0) {
            return super.realClass(elementName2);
        }
        Class componentType = Primitives.primitiveType(elementName2);
        if (componentType == null) {
            componentType = super.realClass(elementName2);
        }
        while (componentType.isArray()) {
            componentType = componentType.getComponentType();
            dimensions++;
        }
        return super.realClass(arrayType(dimensions, componentType));
    }

    private String arrayType(int dimensions, Class componentType) {
        StringBuffer className = new StringBuffer();
        for (int i = 0; i < dimensions; i++) {
            className.append(Constants.REMIX_URL_PREFIX_INDICATOR);
        }
        if (componentType.isPrimitive()) {
            className.append(Primitives.representingChar(componentType));
            return className.toString();
        }
        className.append('L');
        className.append(componentType.getName());
        className.append(Constants.REMIX_URL_REPLACE_SEPARATOR);
        return className.toString();
    }

    private String boxedTypeName(Class type) {
        return Primitives.isBoxed(type) ? type.getName() : null;
    }
}
