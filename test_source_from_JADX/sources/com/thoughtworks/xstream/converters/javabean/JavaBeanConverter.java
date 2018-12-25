package com.thoughtworks.xstream.converters.javabean;

import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.javabean.JavaBeanProvider.Visitor;
import com.thoughtworks.xstream.converters.reflection.MissingFieldException;
import com.thoughtworks.xstream.core.util.FastField;
import com.thoughtworks.xstream.io.ExtendedHierarchicalStreamWriterHelper;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;
import java.util.HashSet;
import java.util.Set;

public class JavaBeanConverter implements Converter {
    protected final JavaBeanProvider beanProvider;
    private String classAttributeIdentifier;
    protected final Mapper mapper;
    private final Class type;

    /* renamed from: com.thoughtworks.xstream.converters.javabean.JavaBeanConverter$2 */
    class C16722 extends HashSet {
        C16722() {
        }

        public boolean add(Object e) {
            if (super.add(e)) {
                return true;
            }
            throw new DuplicatePropertyException(((FastField) e).getName());
        }
    }

    public static class DuplicateFieldException extends ConversionException {
        public DuplicateFieldException(String msg) {
            super(msg);
        }
    }

    public static class DuplicatePropertyException extends ConversionException {
        public DuplicatePropertyException(String msg) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Duplicate property ");
            stringBuilder.append(msg);
            super(stringBuilder.toString());
            add("property", msg);
        }
    }

    public JavaBeanConverter(Mapper mapper) {
        this(mapper, (Class) null);
    }

    public JavaBeanConverter(Mapper mapper, Class type) {
        this(mapper, new BeanProvider(), type);
    }

    public JavaBeanConverter(Mapper mapper, JavaBeanProvider beanProvider) {
        this(mapper, beanProvider, null);
    }

    public JavaBeanConverter(Mapper mapper, JavaBeanProvider beanProvider, Class type) {
        this.mapper = mapper;
        this.beanProvider = beanProvider;
        this.type = type;
    }

    public JavaBeanConverter(Mapper mapper, String classAttributeIdentifier) {
        this(mapper, new BeanProvider());
        this.classAttributeIdentifier = classAttributeIdentifier;
    }

    public boolean canConvert(Class type) {
        return (this.type == null || this.type == type) && this.beanProvider.canInstantiate(type);
    }

    public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
        final String classAttributeName = this.classAttributeIdentifier != null ? this.classAttributeIdentifier : this.mapper.aliasForSystemAttribute("class");
        final Object obj = source;
        final HierarchicalStreamWriter hierarchicalStreamWriter = writer;
        final MarshallingContext marshallingContext = context;
        this.beanProvider.visitSerializableProperties(source, new Visitor() {
            public boolean shouldVisit(String name, Class definedIn) {
                return JavaBeanConverter.this.mapper.shouldSerializeMember(definedIn, name);
            }

            public void visit(String propertyName, Class fieldType, Class definedIn, Object newObj) {
                if (newObj != null) {
                    writeField(propertyName, fieldType, newObj, definedIn);
                }
            }

            private void writeField(String propertyName, Class fieldType, Object newObj, Class definedIn) {
                Class actualType = newObj.getClass();
                Class defaultType = JavaBeanConverter.this.mapper.defaultImplementationOf(fieldType);
                ExtendedHierarchicalStreamWriterHelper.startNode(hierarchicalStreamWriter, JavaBeanConverter.this.mapper.serializedMember(obj.getClass(), propertyName), actualType);
                if (!(actualType.equals(defaultType) || classAttributeName == null)) {
                    hierarchicalStreamWriter.addAttribute(classAttributeName, JavaBeanConverter.this.mapper.serializedClass(actualType));
                }
                marshallingContext.convertAnother(newObj);
                hierarchicalStreamWriter.endNode();
            }
        });
    }

    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        Object result = instantiateNewInstance(context);
        Set seenProperties = new C16722();
        Class resultType = result.getClass();
        while (reader.hasMoreChildren()) {
            reader.moveDown();
            String propertyName = this.mapper.realMember(resultType, reader.getNodeName());
            if (this.mapper.shouldSerializeMember(resultType, propertyName)) {
                if (this.beanProvider.propertyDefinedInClass(propertyName, resultType)) {
                    this.beanProvider.writeProperty(result, propertyName, context.convertAnother(result, determineType(reader, result, propertyName)));
                    seenProperties.add(new FastField(resultType, propertyName));
                } else {
                    throw new MissingFieldException(resultType.getName(), propertyName);
                }
            }
            reader.moveUp();
        }
        return result;
    }

    private Object instantiateNewInstance(UnmarshallingContext context) {
        Object result = context.currentObject();
        if (result == null) {
            return this.beanProvider.newInstance(context.getRequiredType());
        }
        return result;
    }

    private Class determineType(HierarchicalStreamReader reader, Object result, String fieldName) {
        String classAttributeName = this.classAttributeIdentifier != null ? this.classAttributeIdentifier : this.mapper.aliasForSystemAttribute("class");
        String classAttribute = classAttributeName == null ? null : reader.getAttribute(classAttributeName);
        if (classAttribute != null) {
            return this.mapper.realClass(classAttribute);
        }
        return this.mapper.defaultImplementationOf(this.beanProvider.getPropertyType(result, fieldName));
    }
}
