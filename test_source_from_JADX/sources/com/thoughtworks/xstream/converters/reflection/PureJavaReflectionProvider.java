package com.thoughtworks.xstream.converters.reflection;

import com.thoughtworks.xstream.converters.reflection.ReflectionProvider.Visitor;
import com.thoughtworks.xstream.core.JVM;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;

public class PureJavaReflectionProvider implements ReflectionProvider {
    protected FieldDictionary fieldDictionary;
    private transient Map serializedDataCache;

    public PureJavaReflectionProvider() {
        this(new FieldDictionary(new ImmutableFieldKeySorter()));
    }

    public PureJavaReflectionProvider(FieldDictionary fieldDictionary) {
        this.fieldDictionary = fieldDictionary;
        init();
    }

    public Object newInstance(Class type) {
        StringBuilder stringBuilder;
        try {
            Constructor[] constructors = type.getDeclaredConstructors();
            for (Constructor constructor : constructors) {
                if (constructor.getParameterTypes().length == 0) {
                    if (!constructor.isAccessible()) {
                        constructor.setAccessible(true);
                    }
                    return constructor.newInstance(new Object[0]);
                }
            }
            if (Serializable.class.isAssignableFrom(type)) {
                return instantiateUsingSerialization(type);
            }
            stringBuilder = new StringBuilder();
            stringBuilder.append("Cannot construct ");
            stringBuilder.append(type.getName());
            stringBuilder.append(" as it does not have a no-args constructor");
            throw new ObjectAccessException(stringBuilder.toString());
        } catch (InstantiationException e) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Cannot construct ");
            stringBuilder.append(type.getName());
            throw new ObjectAccessException(stringBuilder.toString(), e);
        } catch (IllegalAccessException e2) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Cannot construct ");
            stringBuilder.append(type.getName());
            throw new ObjectAccessException(stringBuilder.toString(), e2);
        } catch (InvocationTargetException e3) {
            if (e3.getTargetException() instanceof RuntimeException) {
                throw ((RuntimeException) e3.getTargetException());
            } else if (e3.getTargetException() instanceof Error) {
                throw ((Error) e3.getTargetException());
            } else {
                stringBuilder = new StringBuilder();
                stringBuilder.append("Constructor for ");
                stringBuilder.append(type.getName());
                stringBuilder.append(" threw an exception");
                throw new ObjectAccessException(stringBuilder.toString(), e3.getTargetException());
            }
        }
    }

    private Object instantiateUsingSerialization(final Class type) {
        StringBuilder stringBuilder;
        try {
            Object readObject;
            synchronized (this.serializedDataCache) {
                byte[] data = (byte[]) this.serializedDataCache.get(type);
                if (data == null) {
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    DataOutputStream stream = new DataOutputStream(bytes);
                    stream.writeShort(-21267);
                    stream.writeShort(5);
                    stream.writeByte(115);
                    stream.writeByte(114);
                    stream.writeUTF(type.getName());
                    stream.writeLong(ObjectStreamClass.lookup(type).getSerialVersionUID());
                    stream.writeByte(2);
                    stream.writeShort(0);
                    stream.writeByte(120);
                    stream.writeByte(112);
                    data = bytes.toByteArray();
                    this.serializedDataCache.put(type, data);
                }
                readObject = new ObjectInputStream(new ByteArrayInputStream(data)) {
                    protected Class resolveClass(ObjectStreamClass desc) throws IOException, ClassNotFoundException {
                        return Class.forName(desc.getName(), false, type.getClassLoader());
                    }
                }.readObject();
            }
            return readObject;
        } catch (IOException e) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Cannot create ");
            stringBuilder.append(type.getName());
            stringBuilder.append(" by JDK serialization");
            throw new ObjectAccessException(stringBuilder.toString(), e);
        } catch (ClassNotFoundException e2) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Cannot find class ");
            stringBuilder.append(e2.getMessage());
            throw new ObjectAccessException(stringBuilder.toString(), e2);
        }
    }

    public void visitSerializableFields(Object object, Visitor visitor) {
        StringBuilder stringBuilder;
        Iterator iterator = this.fieldDictionary.fieldsFor(object.getClass());
        while (iterator.hasNext()) {
            Field field = (Field) iterator.next();
            if (fieldModifiersSupported(field)) {
                validateFieldAccess(field);
                try {
                    visitor.visit(field.getName(), field.getType(), field.getDeclaringClass(), field.get(object));
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
                }
            }
        }
    }

    public void writeField(Object object, String fieldName, Object value, Class definedIn) {
        StringBuilder stringBuilder;
        Field field = this.fieldDictionary.field(object.getClass(), fieldName, definedIn);
        validateFieldAccess(field);
        try {
            field.set(object, value);
        } catch (IllegalArgumentException e) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Could not set field ");
            stringBuilder.append(object.getClass());
            stringBuilder.append(".");
            stringBuilder.append(field.getName());
            throw new ObjectAccessException(stringBuilder.toString(), e);
        } catch (IllegalAccessException e2) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Could not set field ");
            stringBuilder.append(object.getClass());
            stringBuilder.append(".");
            stringBuilder.append(field.getName());
            throw new ObjectAccessException(stringBuilder.toString(), e2);
        }
    }

    public Class getFieldType(Object object, String fieldName, Class definedIn) {
        return this.fieldDictionary.field(object.getClass(), fieldName, definedIn).getType();
    }

    public boolean fieldDefinedInClass(String fieldName, Class type) {
        Field field = this.fieldDictionary.fieldOrNull(type, fieldName, null);
        return field != null && fieldModifiersSupported(field);
    }

    protected boolean fieldModifiersSupported(Field field) {
        int modifiers = field.getModifiers();
        return (Modifier.isStatic(modifiers) || Modifier.isTransient(modifiers)) ? false : true;
    }

    protected void validateFieldAccess(Field field) {
        if (!Modifier.isFinal(field.getModifiers())) {
            return;
        }
        if (!JVM.is15()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid final field ");
            stringBuilder.append(field.getDeclaringClass().getName());
            stringBuilder.append(".");
            stringBuilder.append(field.getName());
            throw new ObjectAccessException(stringBuilder.toString());
        } else if (!field.isAccessible()) {
            field.setAccessible(true);
        }
    }

    public Field getField(Class definedIn, String fieldName) {
        return this.fieldDictionary.field(definedIn, fieldName, null);
    }

    public Field getFieldOrNull(Class definedIn, String fieldName) {
        return this.fieldDictionary.fieldOrNull(definedIn, fieldName, null);
    }

    public void setFieldDictionary(FieldDictionary dictionary) {
        this.fieldDictionary = dictionary;
    }

    private Object readResolve() {
        init();
        return this;
    }

    protected void init() {
        this.serializedDataCache = new WeakHashMap();
    }
}
