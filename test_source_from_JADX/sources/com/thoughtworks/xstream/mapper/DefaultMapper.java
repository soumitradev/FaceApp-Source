package com.thoughtworks.xstream.mapper;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.SingleValueConverter;
import com.thoughtworks.xstream.core.ClassLoaderReference;
import com.thoughtworks.xstream.core.util.Primitives;
import com.thoughtworks.xstream.mapper.Mapper.ImplicitCollectionMapping;
import org.catrobat.catroid.common.Constants;

public class DefaultMapper implements Mapper {
    private static String XSTREAM_PACKAGE_ROOT;
    private final ClassLoaderReference classLoaderReference;

    static {
        String packageName = DefaultMapper.class.getName();
        int idx = packageName.indexOf(".xstream.");
        XSTREAM_PACKAGE_ROOT = idx > 0 ? packageName.substring(0, idx + 9) : ".N/A";
    }

    public DefaultMapper(ClassLoaderReference classLoaderReference) {
        this.classLoaderReference = classLoaderReference;
    }

    public DefaultMapper(ClassLoader classLoader) {
        this(new ClassLoaderReference(classLoader));
    }

    public String serializedClass(Class type) {
        return type.getName();
    }

    public Class realClass(String elementName) {
        Class resultingClass = Primitives.primitiveType(elementName);
        if (resultingClass != null) {
            return resultingClass;
        }
        boolean initialize = true;
        try {
            ClassLoader classLoader;
            if (elementName.startsWith(XSTREAM_PACKAGE_ROOT)) {
                classLoader = DefaultMapper.class.getClassLoader();
            } else {
                classLoader = this.classLoaderReference.getReference();
                boolean z = false;
                if (elementName.charAt(0) == Constants.REMIX_URL_PREFIX_INDICATOR) {
                    z = true;
                }
                initialize = z;
            }
            return Class.forName(elementName, initialize, classLoader);
        } catch (ClassNotFoundException e) {
            throw new CannotResolveClassException(elementName);
        }
    }

    public Class defaultImplementationOf(Class type) {
        return type;
    }

    public String aliasForAttribute(String attribute) {
        return attribute;
    }

    public String attributeForAlias(String alias) {
        return alias;
    }

    public String aliasForSystemAttribute(String attribute) {
        return attribute;
    }

    public boolean isImmutableValueType(Class type) {
        return false;
    }

    public String getFieldNameForItemTypeAndName(Class definedIn, Class itemType, String itemFieldName) {
        return null;
    }

    public Class getItemTypeForItemFieldName(Class definedIn, String itemFieldName) {
        return null;
    }

    public ImplicitCollectionMapping getImplicitCollectionDefForFieldName(Class itemType, String fieldName) {
        return null;
    }

    public boolean shouldSerializeMember(Class definedIn, String fieldName) {
        return true;
    }

    public String lookupName(Class type) {
        return serializedClass(type);
    }

    public Class lookupType(String elementName) {
        return realClass(elementName);
    }

    public String serializedMember(Class type, String memberName) {
        return memberName;
    }

    public String realMember(Class type, String serialized) {
        return serialized;
    }

    public SingleValueConverter getConverterFromAttribute(String name) {
        return null;
    }

    public SingleValueConverter getConverterFromItemType(String fieldName, Class type) {
        return null;
    }

    public SingleValueConverter getConverterFromItemType(Class type) {
        return null;
    }

    public SingleValueConverter getConverterFromItemType(String fieldName, Class type, Class definedIn) {
        return null;
    }

    public Converter getLocalConverter(Class definedIn, String fieldName) {
        return null;
    }

    public Mapper lookupMapperOfType(Class type) {
        return null;
    }

    public String aliasForAttribute(Class definedIn, String fieldName) {
        return fieldName;
    }

    public String attributeForAlias(Class definedIn, String alias) {
        return alias;
    }

    public SingleValueConverter getConverterFromAttribute(Class definedIn, String attribute) {
        return null;
    }

    public SingleValueConverter getConverterFromAttribute(Class definedIn, String attribute, Class type) {
        return null;
    }
}
