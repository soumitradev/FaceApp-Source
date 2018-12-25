package com.thoughtworks.xstream.converters.reflection;

import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.DataHolder;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.reflection.ReflectionProvider.Visitor;
import com.thoughtworks.xstream.core.ClassLoaderReference;
import com.thoughtworks.xstream.core.JVM;
import com.thoughtworks.xstream.core.util.CustomObjectInputStream;
import com.thoughtworks.xstream.core.util.CustomObjectOutputStream;
import com.thoughtworks.xstream.core.util.CustomObjectOutputStream.StreamCallback;
import com.thoughtworks.xstream.core.util.HierarchicalStreams;
import com.thoughtworks.xstream.io.ExtendedHierarchicalStreamWriterHelper;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputValidation;
import java.io.ObjectStreamClass;
import java.io.ObjectStreamField;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import name.antonsmirnov.firmata.FormatHelper;

public class SerializableConverter extends AbstractReflectionConverter {
    private static final String ATTRIBUTE_CLASS = "class";
    private static final String ATTRIBUTE_NAME = "name";
    private static final String ATTRIBUTE_SERIALIZATION = "serialization";
    private static final String ATTRIBUTE_VALUE_CUSTOM = "custom";
    private static final String ELEMENT_DEFAULT = "default";
    private static final String ELEMENT_FIELD = "field";
    private static final String ELEMENT_FIELDS = "fields";
    private static final String ELEMENT_NULL = "null";
    private static final String ELEMENT_UNSERIALIZABLE_PARENTS = "unserializable-parents";
    private final ClassLoaderReference classLoaderReference;

    private static class UnserializableParentsReflectionProvider extends ReflectionProviderWrapper {
        public UnserializableParentsReflectionProvider(ReflectionProvider reflectionProvider) {
            super(reflectionProvider);
        }

        public void visitSerializableFields(Object object, final Visitor visitor) {
            this.wrapped.visitSerializableFields(object, new Visitor() {
                public void visit(String name, Class type, Class definedIn, Object value) {
                    if (!Serializable.class.isAssignableFrom(definedIn)) {
                        visitor.visit(name, type, definedIn, value);
                    }
                }
            });
        }
    }

    public SerializableConverter(Mapper mapper, ReflectionProvider reflectionProvider, ClassLoaderReference classLoaderReference) {
        super(mapper, new UnserializableParentsReflectionProvider(reflectionProvider));
        this.classLoaderReference = classLoaderReference;
    }

    public SerializableConverter(Mapper mapper, ReflectionProvider reflectionProvider, ClassLoader classLoader) {
        this(mapper, reflectionProvider, new ClassLoaderReference(classLoader));
    }

    public SerializableConverter(Mapper mapper, ReflectionProvider reflectionProvider) {
        this(mapper, new UnserializableParentsReflectionProvider(reflectionProvider), new ClassLoaderReference(null));
    }

    public boolean canConvert(Class type) {
        return JVM.canCreateDerivedObjectOutputStream() && isSerializable(type);
    }

    private boolean isSerializable(Class type) {
        if (type == null || !Serializable.class.isAssignableFrom(type) || type.isInterface() || (!this.serializationMethodInvoker.supportsReadObject(type, true) && !this.serializationMethodInvoker.supportsWriteObject(type, true))) {
            return false;
        }
        for (Class isAssignableFrom : hierarchyFor(type)) {
            if (!Serializable.class.isAssignableFrom(isAssignableFrom)) {
                return canAccess(type);
            }
        }
        return true;
    }

    public void doMarshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
        Object obj = source;
        HierarchicalStreamWriter hierarchicalStreamWriter = writer;
        MarshallingContext marshallingContext = context;
        String attributeName = this.mapper.aliasForSystemAttribute(ATTRIBUTE_SERIALIZATION);
        if (attributeName != null) {
            hierarchicalStreamWriter.addAttribute(attributeName, ATTRIBUTE_VALUE_CUSTOM);
        }
        Class[] currentType = new Class[1];
        boolean[] writtenClassWrapper = new boolean[]{false};
        final HierarchicalStreamWriter hierarchicalStreamWriter2 = hierarchicalStreamWriter;
        final MarshallingContext marshallingContext2 = marshallingContext;
        final Class[] clsArr = currentType;
        final Object obj2 = obj;
        final boolean[] zArr = writtenClassWrapper;
        C20621 callback = new StreamCallback() {
            public void writeToStream(Object object) {
                if (object == null) {
                    hierarchicalStreamWriter2.startNode(SerializableConverter.ELEMENT_NULL);
                    hierarchicalStreamWriter2.endNode();
                    return;
                }
                ExtendedHierarchicalStreamWriterHelper.startNode(hierarchicalStreamWriter2, SerializableConverter.this.mapper.serializedClass(object.getClass()), object.getClass());
                marshallingContext2.convertAnother(object);
                hierarchicalStreamWriter2.endNode();
            }

            public void writeFieldsToStream(Map fields) {
                ObjectStreamClass objectStreamClass = ObjectStreamClass.lookup(clsArr[0]);
                hierarchicalStreamWriter2.startNode("default");
                for (String name : fields.keySet()) {
                    if (SerializableConverter.this.mapper.shouldSerializeMember(clsArr[0], name)) {
                        ObjectStreamField field = objectStreamClass.getField(name);
                        Object value = fields.get(name);
                        if (field == null) {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("Class ");
                            stringBuilder.append(value.getClass().getName());
                            stringBuilder.append(" may not write a field named '");
                            stringBuilder.append(name);
                            stringBuilder.append(FormatHelper.QUOTE);
                            throw new ObjectAccessException(stringBuilder.toString());
                        } else if (value != null) {
                            ExtendedHierarchicalStreamWriterHelper.startNode(hierarchicalStreamWriter2, SerializableConverter.this.mapper.serializedMember(obj2.getClass(), name), value.getClass());
                            if (!(field.getType() == value.getClass() || field.getType().isPrimitive())) {
                                String attributeName = SerializableConverter.this.mapper.aliasForSystemAttribute(SerializableConverter.ATTRIBUTE_CLASS);
                                if (attributeName != null) {
                                    hierarchicalStreamWriter2.addAttribute(attributeName, SerializableConverter.this.mapper.serializedClass(value.getClass()));
                                }
                            }
                            marshallingContext2.convertAnother(value);
                            hierarchicalStreamWriter2.endNode();
                        }
                    }
                }
                hierarchicalStreamWriter2.endNode();
            }

            public void defaultWriteObject() {
                ObjectStreamClass objectStreamClass = ObjectStreamClass.lookup(clsArr[0]);
                if (objectStreamClass != null) {
                    ObjectStreamField[] fields = objectStreamClass.getFields();
                    boolean writtenDefaultFields = false;
                    for (ObjectStreamField field : fields) {
                        Object value = SerializableConverter.this.readField(field, clsArr[0], obj2);
                        if (value != null) {
                            if (!zArr[0]) {
                                hierarchicalStreamWriter2.startNode(SerializableConverter.this.mapper.serializedClass(clsArr[0]));
                                zArr[0] = true;
                            }
                            if (!writtenDefaultFields) {
                                hierarchicalStreamWriter2.startNode("default");
                                writtenDefaultFields = true;
                            }
                            if (SerializableConverter.this.mapper.shouldSerializeMember(clsArr[0], field.getName())) {
                                Class actualType = value.getClass();
                                ExtendedHierarchicalStreamWriterHelper.startNode(hierarchicalStreamWriter2, SerializableConverter.this.mapper.serializedMember(obj2.getClass(), field.getName()), actualType);
                                if (!actualType.equals(SerializableConverter.this.mapper.defaultImplementationOf(field.getType()))) {
                                    String attributeName = SerializableConverter.this.mapper.aliasForSystemAttribute(SerializableConverter.ATTRIBUTE_CLASS);
                                    if (attributeName != null) {
                                        hierarchicalStreamWriter2.addAttribute(attributeName, SerializableConverter.this.mapper.serializedClass(actualType));
                                    }
                                }
                                marshallingContext2.convertAnother(value);
                                hierarchicalStreamWriter2.endNode();
                            }
                        }
                    }
                    if (zArr[0] && !writtenDefaultFields) {
                        hierarchicalStreamWriter2.startNode("default");
                        hierarchicalStreamWriter2.endNode();
                    } else if (writtenDefaultFields) {
                        hierarchicalStreamWriter2.endNode();
                    }
                }
            }

            public void flush() {
                hierarchicalStreamWriter2.flush();
            }

            public void close() {
                throw new UnsupportedOperationException("Objects are not allowed to call ObjectOutputStream.close() from writeObject()");
            }
        };
        boolean mustHandleUnserializableParent = false;
        try {
            for (Class cls : hierarchyFor(source.getClass())) {
                currentType[0] = cls;
                if (Serializable.class.isAssignableFrom(currentType[0])) {
                    if (mustHandleUnserializableParent) {
                        marshalUnserializableParent(hierarchicalStreamWriter, marshallingContext, obj);
                        mustHandleUnserializableParent = false;
                    }
                    String classAttributeName;
                    if (r8.serializationMethodInvoker.supportsWriteObject(currentType[0], false)) {
                        writtenClassWrapper[0] = true;
                        hierarchicalStreamWriter.startNode(r8.mapper.serializedClass(currentType[0]));
                        if (currentType[0] != r8.mapper.defaultImplementationOf(currentType[0])) {
                            classAttributeName = r8.mapper.aliasForSystemAttribute(ATTRIBUTE_CLASS);
                            if (classAttributeName != null) {
                                hierarchicalStreamWriter.addAttribute(classAttributeName, currentType[0].getName());
                            }
                        }
                        CustomObjectOutputStream objectOutputStream = CustomObjectOutputStream.getInstance(marshallingContext, callback);
                        r8.serializationMethodInvoker.callWriteObject(currentType[0], obj, objectOutputStream);
                        objectOutputStream.popCallback();
                        writer.endNode();
                    } else if (r8.serializationMethodInvoker.supportsReadObject(currentType[0], false)) {
                        writtenClassWrapper[0] = true;
                        hierarchicalStreamWriter.startNode(r8.mapper.serializedClass(currentType[0]));
                        if (currentType[0] != r8.mapper.defaultImplementationOf(currentType[0])) {
                            classAttributeName = r8.mapper.aliasForSystemAttribute(ATTRIBUTE_CLASS);
                            if (classAttributeName != null) {
                                hierarchicalStreamWriter.addAttribute(classAttributeName, currentType[0].getName());
                            }
                        }
                        callback.defaultWriteObject();
                        writer.endNode();
                    } else {
                        writtenClassWrapper[0] = false;
                        callback.defaultWriteObject();
                        if (writtenClassWrapper[0]) {
                            writer.endNode();
                        }
                    }
                } else {
                    mustHandleUnserializableParent = true;
                }
            }
        } catch (IOException e) {
            throw new ObjectAccessException("Could not call defaultWriteObject()", e);
        }
    }

    protected void marshalUnserializableParent(HierarchicalStreamWriter writer, MarshallingContext context, Object replacedSource) {
        writer.startNode(ELEMENT_UNSERIALIZABLE_PARENTS);
        super.doMarshal(replacedSource, writer, context);
        writer.endNode();
    }

    private Object readField(ObjectStreamField field, Class type, Object instance) {
        StringBuilder stringBuilder;
        try {
            Field javaField = type.getDeclaredField(field.getName());
            if (!javaField.isAccessible()) {
                javaField.setAccessible(true);
            }
            return javaField.get(instance);
        } catch (IllegalArgumentException e) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Could not get field ");
            stringBuilder.append(field.getClass());
            stringBuilder.append(".");
            stringBuilder.append(field.getName());
            throw new ObjectAccessException(stringBuilder.toString(), e);
        } catch (IllegalAccessException e2) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Could not get field ");
            stringBuilder.append(field.getClass());
            stringBuilder.append(".");
            stringBuilder.append(field.getName());
            throw new ObjectAccessException(stringBuilder.toString(), e2);
        } catch (NoSuchFieldException e3) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Could not get field ");
            stringBuilder.append(field.getClass());
            stringBuilder.append(".");
            stringBuilder.append(field.getName());
            throw new ObjectAccessException(stringBuilder.toString(), e3);
        } catch (SecurityException e4) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Could not get field ");
            stringBuilder.append(field.getClass());
            stringBuilder.append(".");
            stringBuilder.append(field.getName());
            throw new ObjectAccessException(stringBuilder.toString(), e4);
        }
    }

    protected List hierarchyFor(Class type) {
        List result = new ArrayList();
        while (type != Object.class && type != null) {
            result.add(type);
            type = type.getSuperclass();
        }
        Collections.reverse(result);
        return result;
    }

    public Object doUnmarshal(Object result, HierarchicalStreamReader reader, UnmarshallingContext context) {
        Class[] currentType = new Class[1];
        String attributeName = this.mapper.aliasForSystemAttribute(ATTRIBUTE_SERIALIZATION);
        if (attributeName == null || ATTRIBUTE_VALUE_CUSTOM.equals(reader.getAttribute(attributeName))) {
            final HierarchicalStreamReader hierarchicalStreamReader = reader;
            final UnmarshallingContext unmarshallingContext = context;
            final Object obj = result;
            final Class[] clsArr = currentType;
            CustomObjectInputStream.StreamCallback c20632 = new CustomObjectInputStream.StreamCallback() {
                public Object readFromStream() {
                    hierarchicalStreamReader.moveDown();
                    Object value = unmarshallingContext.convertAnother(obj, HierarchicalStreams.readClassType(hierarchicalStreamReader, SerializableConverter.this.mapper));
                    hierarchicalStreamReader.moveUp();
                    return value;
                }

                public Map readFieldsFromStream() {
                    Map fields = new HashMap();
                    hierarchicalStreamReader.moveDown();
                    if (hierarchicalStreamReader.getNodeName().equals("fields")) {
                        while (hierarchicalStreamReader.hasMoreChildren()) {
                            hierarchicalStreamReader.moveDown();
                            if (hierarchicalStreamReader.getNodeName().equals(SerializableConverter.ELEMENT_FIELD)) {
                                fields.put(hierarchicalStreamReader.getAttribute("name"), unmarshallingContext.convertAnother(obj, SerializableConverter.this.mapper.realClass(hierarchicalStreamReader.getAttribute(SerializableConverter.ATTRIBUTE_CLASS))));
                                hierarchicalStreamReader.moveUp();
                            } else {
                                throw new ConversionException("Expected <field/> element inside <field/>");
                            }
                        }
                    } else if (hierarchicalStreamReader.getNodeName().equals("default")) {
                        ObjectStreamClass objectStreamClass = ObjectStreamClass.lookup(clsArr[0]);
                        while (hierarchicalStreamReader.hasMoreChildren()) {
                            hierarchicalStreamReader.moveDown();
                            String name = SerializableConverter.this.mapper.realMember(clsArr[0], hierarchicalStreamReader.getNodeName());
                            if (SerializableConverter.this.mapper.shouldSerializeMember(clsArr[0], name)) {
                                Class type;
                                String classAttribute = HierarchicalStreams.readClassAttribute(hierarchicalStreamReader, SerializableConverter.this.mapper);
                                if (classAttribute != null) {
                                    type = SerializableConverter.this.mapper.realClass(classAttribute);
                                } else {
                                    type = objectStreamClass.getField(name);
                                    if (type == null) {
                                        throw new MissingFieldException(clsArr[0].getName(), name);
                                    }
                                    type = type.getType();
                                }
                                fields.put(name, unmarshallingContext.convertAnother(obj, type));
                            }
                            hierarchicalStreamReader.moveUp();
                        }
                    } else {
                        throw new ConversionException("Expected <fields/> or <default/> element when calling ObjectInputStream.readFields()");
                    }
                    hierarchicalStreamReader.moveUp();
                    return fields;
                }

                public void defaultReadObject() {
                    if (hierarchicalStreamReader.hasMoreChildren()) {
                        hierarchicalStreamReader.moveDown();
                        if (hierarchicalStreamReader.getNodeName().equals("default")) {
                            while (hierarchicalStreamReader.hasMoreChildren()) {
                                hierarchicalStreamReader.moveDown();
                                String fieldName = SerializableConverter.this.mapper.realMember(clsArr[0], hierarchicalStreamReader.getNodeName());
                                if (SerializableConverter.this.mapper.shouldSerializeMember(clsArr[0], fieldName)) {
                                    Class type;
                                    String classAttribute = HierarchicalStreams.readClassAttribute(hierarchicalStreamReader, SerializableConverter.this.mapper);
                                    if (classAttribute != null) {
                                        type = SerializableConverter.this.mapper.realClass(classAttribute);
                                    } else {
                                        type = SerializableConverter.this.mapper.defaultImplementationOf(SerializableConverter.this.reflectionProvider.getFieldType(obj, fieldName, clsArr[0]));
                                    }
                                    SerializableConverter.this.reflectionProvider.writeField(obj, fieldName, unmarshallingContext.convertAnother(obj, type), clsArr[0]);
                                }
                                hierarchicalStreamReader.moveUp();
                            }
                            hierarchicalStreamReader.moveUp();
                            return;
                        }
                        throw new ConversionException("Expected <default/> element in readObject() stream");
                    }
                }

                public void registerValidation(final ObjectInputValidation validation, int priority) {
                    unmarshallingContext.addCompletionCallback(new Runnable() {
                        public void run() {
                            try {
                                validation.validateObject();
                            } catch (InvalidObjectException e) {
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append("Cannot validate object : ");
                                stringBuilder.append(e.getMessage());
                                throw new ObjectAccessException(stringBuilder.toString(), e);
                            }
                        }
                    }, priority);
                }

                public void close() {
                    throw new UnsupportedOperationException("Objects are not allowed to call ObjectInputStream.close() from readObject()");
                }
            };
            while (reader.hasMoreChildren()) {
                reader.moveDown();
                String nodeName = reader.getNodeName();
                if (nodeName.equals(ELEMENT_UNSERIALIZABLE_PARENTS)) {
                    super.doUnmarshal(result, reader, context);
                } else {
                    String classAttribute = HierarchicalStreams.readClassAttribute(reader, this.mapper);
                    if (classAttribute == null) {
                        currentType[0] = this.mapper.defaultImplementationOf(this.mapper.realClass(nodeName));
                    } else {
                        currentType[0] = this.mapper.realClass(classAttribute);
                    }
                    if (this.serializationMethodInvoker.supportsReadObject(currentType[0], false)) {
                        CustomObjectInputStream objectInputStream = CustomObjectInputStream.getInstance((DataHolder) context, c20632, this.classLoaderReference);
                        this.serializationMethodInvoker.callReadObject(currentType[0], result, objectInputStream);
                        objectInputStream.popCallback();
                    } else {
                        try {
                            c20632.defaultReadObject();
                        } catch (IOException e) {
                            throw new ObjectAccessException("Could not call defaultWriteObject()", e);
                        }
                    }
                }
                reader.moveUp();
            }
            return result;
        }
        throw new ConversionException("Cannot deserialize object with new readObject()/writeObject() methods");
    }

    protected void doMarshalConditionally(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
        if (isSerializable(source.getClass())) {
            doMarshal(source, writer, context);
        } else {
            super.doMarshal(source, writer, context);
        }
    }

    protected Object doUnmarshalConditionally(Object result, HierarchicalStreamReader reader, UnmarshallingContext context) {
        return isSerializable(result.getClass()) ? doUnmarshal(result, reader, context) : super.doUnmarshal(result, reader, context);
    }
}
