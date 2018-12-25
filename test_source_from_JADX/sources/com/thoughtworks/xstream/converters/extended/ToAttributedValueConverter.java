package com.thoughtworks.xstream.converters.extended;

import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.ConverterLookup;
import com.thoughtworks.xstream.converters.ConverterMatcher;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.SingleValueConverter;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.reflection.AbstractReflectionConverter.DuplicateFieldException;
import com.thoughtworks.xstream.converters.reflection.ReflectionProvider;
import com.thoughtworks.xstream.converters.reflection.ReflectionProvider.Visitor;
import com.thoughtworks.xstream.core.JVM;
import com.thoughtworks.xstream.core.util.FastField;
import com.thoughtworks.xstream.core.util.HierarchicalStreams;
import com.thoughtworks.xstream.core.util.Primitives;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ToAttributedValueConverter implements Converter {
    private static final String STRUCTURE_MARKER = "";
    private final Mapper enumMapper;
    private final ConverterLookup lookup;
    private final Mapper mapper;
    private final ReflectionProvider reflectionProvider;
    private final Class type;
    private final Field valueField;

    public ToAttributedValueConverter(Class type, Mapper mapper, ReflectionProvider reflectionProvider, ConverterLookup lookup, String valueFieldName) {
        this(type, mapper, reflectionProvider, lookup, valueFieldName, null);
    }

    public ToAttributedValueConverter(Class type, Mapper mapper, ReflectionProvider reflectionProvider, ConverterLookup lookup, String valueFieldName, Class valueDefinedIn) {
        this.type = type;
        this.mapper = mapper;
        this.reflectionProvider = reflectionProvider;
        this.lookup = lookup;
        Mapper mapper2 = null;
        if (valueFieldName == null) {
            this.valueField = null;
        } else {
            Field field = null;
            try {
                field = (valueDefinedIn != null ? valueDefinedIn : type).getDeclaredField(valueFieldName);
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                this.valueField = field;
            } catch (NoSuchFieldException e) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(e.getMessage());
                stringBuilder.append(": ");
                stringBuilder.append(valueFieldName);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
        }
        if (JVM.is15()) {
            mapper2 = UseAttributeForEnumMapper.createEnumMapper(mapper);
        }
        this.enumMapper = mapper2;
    }

    public boolean canConvert(Class type) {
        return this.type == type;
    }

    public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
        HierarchicalStreamWriter hierarchicalStreamWriter = writer;
        Class sourceType = source.getClass();
        final Map defaultFieldDefinition = new HashMap();
        String[] tagValue = new String[1];
        Object[] realValue = new Object[1];
        Class[] fieldType = new Class[1];
        Class[] definingType = new Class[1];
        final Class cls = sourceType;
        final Class[] clsArr = definingType;
        final Class[] clsArr2 = fieldType;
        final Object[] objArr = realValue;
        C20561 c20561 = r0;
        final String[] strArr = tagValue;
        ReflectionProvider reflectionProvider = this.reflectionProvider;
        final HierarchicalStreamWriter hierarchicalStreamWriter2 = hierarchicalStreamWriter;
        C20561 c205612 = new Visitor() {
            public void visit(String fieldName, Class type, Class definedIn, Object value) {
                if (ToAttributedValueConverter.this.mapper.shouldSerializeMember(definedIn, fieldName)) {
                    FastField field = new FastField(definedIn, fieldName);
                    String alias = ToAttributedValueConverter.this.mapper.serializedMember(definedIn, fieldName);
                    if (!defaultFieldDefinition.containsKey(alias)) {
                        defaultFieldDefinition.put(alias, ToAttributedValueConverter.this.reflectionProvider.getField(cls, fieldName));
                    } else if (!ToAttributedValueConverter.this.fieldIsEqual(field)) {
                        ConversionException exception = new ConversionException("Cannot write attribute twice for object");
                        exception.add("alias", alias);
                        exception.add("type", cls.getName());
                        throw exception;
                    }
                    ConverterMatcher converter = UseAttributeForEnumMapper.isEnum(type) ? ToAttributedValueConverter.this.enumMapper.getConverterFromItemType(null, type, null) : ToAttributedValueConverter.this.mapper.getLocalConverter(definedIn, fieldName);
                    if (converter == null) {
                        converter = ToAttributedValueConverter.this.lookup.lookupConverterForType(type);
                    }
                    if (value != null) {
                        boolean isValueField = ToAttributedValueConverter.this.valueField != null && ToAttributedValueConverter.this.fieldIsEqual(field);
                        if (isValueField) {
                            clsArr[0] = definedIn;
                            clsArr2[0] = type;
                            objArr[0] = value;
                            strArr[0] = "";
                        }
                        if (converter instanceof SingleValueConverter) {
                            String str = ((SingleValueConverter) converter).toString(value);
                            if (isValueField) {
                                strArr[0] = str;
                            } else if (str != null) {
                                hierarchicalStreamWriter2.addAttribute(alias, str);
                            }
                        } else if (!isValueField) {
                            ConversionException exception2 = new ConversionException("Cannot write element as attribute");
                            exception2.add("alias", alias);
                            exception2.add("type", cls.getName());
                            throw exception2;
                        }
                    }
                }
            }
        };
        reflectionProvider.visitSerializableFields(source, c20561);
        if (tagValue[0] != null) {
            cls = realValue[0].getClass();
            Class defaultType = r9.mapper.defaultImplementationOf(fieldType[0]);
            if (!cls.equals(defaultType)) {
                String serializedClassName = r9.mapper.serializedClass(cls);
                if (!serializedClassName.equals(r9.mapper.serializedClass(defaultType))) {
                    String attributeName = r9.mapper.aliasForSystemAttribute("class");
                    if (attributeName != null) {
                        hierarchicalStreamWriter.addAttribute(attributeName, serializedClassName);
                    }
                }
            }
            if (tagValue[0] == "") {
                context.convertAnother(realValue[0]);
                return;
            }
            MarshallingContext marshallingContext = context;
            hierarchicalStreamWriter.setValue(tagValue[0]);
            return;
        }
        marshallingContext = context;
    }

    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        HierarchicalStreamReader hierarchicalStreamReader = reader;
        Object result = this.reflectionProvider.newInstance(context.getRequiredType());
        Class resultType = result.getClass();
        Set seenFields = new HashSet();
        Iterator it = reader.getAttributeNames();
        Set systemAttributes = new HashSet();
        systemAttributes.add(this.mapper.aliasForSystemAttribute("class"));
        while (true) {
            Field field = null;
            if (!it.hasNext()) {
                break;
            }
            String fieldName;
            String attrName = (String) it.next();
            if (!systemAttributes.contains(attrName)) {
                Class resultType2;
                fieldName = r0.mapper.realMember(resultType, attrName);
                Field field2 = r0.reflectionProvider.getFieldOrNull(resultType, fieldName);
                if (field2 != null) {
                    if (!Modifier.isTransient(field2.getModifiers())) {
                        Class type = field2.getType();
                        Class declaringClass = field2.getDeclaringClass();
                        ConverterMatcher converter = UseAttributeForEnumMapper.isEnum(type) ? r0.enumMapper.getConverterFromItemType(null, type, null) : r0.mapper.getLocalConverter(declaringClass, fieldName);
                        if (converter == null) {
                            converter = r0.lookup.lookupConverterForType(type);
                        }
                        if (!(converter instanceof SingleValueConverter)) {
                            ConversionException exception = new ConversionException("Cannot read field as a single value for object");
                            exception.add("field", fieldName);
                            exception.add("type", resultType.getName());
                            throw exception;
                        } else if (converter != null) {
                            Object value = ((SingleValueConverter) converter).fromString(hierarchicalStreamReader.getAttribute(attrName));
                            if (type.isPrimitive()) {
                                type = Primitives.box(type);
                            }
                            if (value == null || type.isAssignableFrom(value.getClass())) {
                                resultType2 = resultType;
                                r0.reflectionProvider.writeField(result, fieldName, value, declaringClass);
                                if (!seenFields.add(new FastField(declaringClass, fieldName))) {
                                    StringBuilder stringBuilder = new StringBuilder();
                                    stringBuilder.append(fieldName);
                                    stringBuilder.append(" [");
                                    stringBuilder.append(declaringClass.getName());
                                    stringBuilder.append("]");
                                    throw new DuplicateFieldException(stringBuilder.toString());
                                }
                                resultType = resultType2;
                            } else {
                                ConversionException exception2 = new ConversionException("Cannot assign object to type");
                                exception2.add("object type", value.getClass().getName());
                                exception2.add("target type", type.getName());
                                throw exception2;
                            }
                        }
                    }
                }
                resultType2 = resultType;
                resultType = resultType2;
            }
        }
        UnmarshallingContext unmarshallingContext;
        Iterator it2;
        if (r0.valueField != null) {
            resultType = r0.valueField.getDeclaringClass();
            attrName = r0.valueField.getName();
            if (attrName != null) {
                field = r0.reflectionProvider.getField(resultType, attrName);
            }
            if (attrName == null) {
                unmarshallingContext = context;
                it2 = it;
            } else if (field == null) {
                unmarshallingContext = context;
                it2 = it;
            } else {
                Class type2;
                fieldName = HierarchicalStreams.readClassAttribute(hierarchicalStreamReader, r0.mapper);
                if (fieldName != null) {
                    type2 = r0.mapper.realClass(fieldName);
                } else {
                    type2 = r0.mapper.defaultImplementationOf(r0.reflectionProvider.getFieldType(result, attrName, resultType));
                }
                Object value2 = context.convertAnother(result, type2, r0.mapper.getLocalConverter(field.getDeclaringClass(), field.getName()));
                Class definedType = r0.reflectionProvider.getFieldType(result, attrName, resultType);
                if (!definedType.isPrimitive()) {
                    type2 = definedType;
                }
                if (value2 == null || type2.isAssignableFrom(value2.getClass())) {
                    r0.reflectionProvider.writeField(result, attrName, value2, resultType);
                    if (!seenFields.add(new FastField(resultType, attrName))) {
                        stringBuilder = new StringBuilder();
                        stringBuilder.append(attrName);
                        stringBuilder.append(" [");
                        stringBuilder.append(resultType.getName());
                        stringBuilder.append("]");
                        throw new DuplicateFieldException(stringBuilder.toString());
                    }
                }
                exception2 = new ConversionException("Cannot assign object to type");
                exception2.add("object type", value2.getClass().getName());
                exception2.add("target type", type2.getName());
                throw exception2;
            }
            it = new ConversionException("Cannot assign value to field of type");
            it.add("element", reader.getNodeName());
            it.add("field", attrName);
            it.add("target type", context.getRequiredType().getName());
            throw it;
        }
        unmarshallingContext = context;
        it2 = it;
        return result;
    }

    private boolean fieldIsEqual(FastField field) {
        return this.valueField.getName().equals(field.getName()) && this.valueField.getDeclaringClass().getName().equals(field.getDeclaringClass());
    }
}
