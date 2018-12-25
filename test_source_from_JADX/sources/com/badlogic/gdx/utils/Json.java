package com.badlogic.gdx.utils;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonWriter.OutputType;
import com.badlogic.gdx.utils.ObjectMap.Entry;
import com.badlogic.gdx.utils.reflect.ArrayReflection;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.Constructor;
import com.badlogic.gdx.utils.reflect.Field;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.google.firebase.analytics.FirebaseAnalytics$Param;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.security.AccessControlException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Json {
    private static final boolean debug = false;
    private final ObjectMap<Class, Object[]> classToDefaultValues;
    private final ObjectMap<Class, Serializer> classToSerializer;
    private final ObjectMap<Class, String> classToTag;
    private Serializer defaultSerializer;
    private boolean enumNames;
    private final Object[] equals1;
    private final Object[] equals2;
    private boolean ignoreUnknownFields;
    private OutputType outputType;
    private boolean quoteLongValues;
    private final ObjectMap<String, Class> tagToClass;
    private String typeName;
    private final ObjectMap<Class, OrderedMap<String, FieldMetadata>> typeToFields;
    private boolean usePrototypes;
    private JsonWriter writer;

    private static class FieldMetadata {
        Class elementType;
        Field field;

        public FieldMetadata(Field field) {
            int index;
            this.field = field;
            if (!ClassReflection.isAssignableFrom(ObjectMap.class, field.getType())) {
                if (!ClassReflection.isAssignableFrom(Map.class, field.getType())) {
                    index = 0;
                    this.elementType = field.getElementType(index);
                }
            }
            index = 1;
            this.elementType = field.getElementType(index);
        }
    }

    public interface Serializable {
        void read(Json json, JsonValue jsonValue);

        void write(Json json);
    }

    public interface Serializer<T> {
        T read(Json json, JsonValue jsonValue, Class cls);

        void write(Json json, T t, Class cls);
    }

    public static abstract class ReadOnlySerializer<T> implements Serializer<T> {
        public abstract T read(Json json, JsonValue jsonValue, Class cls);

        public void write(Json json, T t, Class knownType) {
        }
    }

    public Json() {
        this.typeName = "class";
        this.usePrototypes = true;
        this.enumNames = true;
        this.typeToFields = new ObjectMap();
        this.tagToClass = new ObjectMap();
        this.classToTag = new ObjectMap();
        this.classToSerializer = new ObjectMap();
        this.classToDefaultValues = new ObjectMap();
        this.equals1 = new Object[]{null};
        this.equals2 = new Object[]{null};
        this.outputType = OutputType.minimal;
    }

    public Json(OutputType outputType) {
        this.typeName = "class";
        this.usePrototypes = true;
        this.enumNames = true;
        this.typeToFields = new ObjectMap();
        this.tagToClass = new ObjectMap();
        this.classToTag = new ObjectMap();
        this.classToSerializer = new ObjectMap();
        this.classToDefaultValues = new ObjectMap();
        this.equals1 = new Object[]{null};
        this.equals2 = new Object[]{null};
        this.outputType = outputType;
    }

    public void setIgnoreUnknownFields(boolean ignoreUnknownFields) {
        this.ignoreUnknownFields = ignoreUnknownFields;
    }

    public void setOutputType(OutputType outputType) {
        this.outputType = outputType;
    }

    public void setQuoteLongValues(boolean quoteLongValues) {
        this.quoteLongValues = quoteLongValues;
    }

    public void setEnumNames(boolean enumNames) {
        this.enumNames = enumNames;
    }

    public void addClassTag(String tag, Class type) {
        this.tagToClass.put(tag, type);
        this.classToTag.put(type, tag);
    }

    public Class getClass(String tag) {
        return (Class) this.tagToClass.get(tag);
    }

    public String getTag(Class type) {
        return (String) this.classToTag.get(type);
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public void setDefaultSerializer(Serializer defaultSerializer) {
        this.defaultSerializer = defaultSerializer;
    }

    public <T> void setSerializer(Class<T> type, Serializer<T> serializer) {
        this.classToSerializer.put(type, serializer);
    }

    public <T> Serializer<T> getSerializer(Class<T> type) {
        return (Serializer) this.classToSerializer.get(type);
    }

    public void setUsePrototypes(boolean usePrototypes) {
        this.usePrototypes = usePrototypes;
    }

    public void setElementType(Class type, String fieldName, Class elementType) {
        FieldMetadata metadata = (FieldMetadata) getFields(type).get(fieldName);
        if (metadata == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Field not found: ");
            stringBuilder.append(fieldName);
            stringBuilder.append(" (");
            stringBuilder.append(type.getName());
            stringBuilder.append(")");
            throw new SerializationException(stringBuilder.toString());
        }
        metadata.elementType = elementType;
    }

    private OrderedMap<String, FieldMetadata> getFields(Class type) {
        OrderedMap<String, FieldMetadata> fields = (OrderedMap) this.typeToFields.get(type);
        if (fields != null) {
            return fields;
        }
        Array<Class> classHierarchy = new Array();
        for (Class nextClass = type; nextClass != Object.class; nextClass = nextClass.getSuperclass()) {
            classHierarchy.add(nextClass);
        }
        ArrayList<Field> allFields = new ArrayList();
        for (int i = classHierarchy.size - 1; i >= 0; i--) {
            Collections.addAll(allFields, ClassReflection.getDeclaredFields((Class) classHierarchy.get(i)));
        }
        OrderedMap<String, FieldMetadata> nameToField = new OrderedMap();
        int n = allFields.size();
        for (int i2 = 0; i2 < n; i2++) {
            Field field = (Field) allFields.get(i2);
            if (!field.isTransient()) {
                if (!field.isStatic()) {
                    if (!field.isSynthetic()) {
                        if (!field.isAccessible()) {
                            try {
                                field.setAccessible(true);
                            } catch (AccessControlException e) {
                            }
                        }
                        nameToField.put(field.getName(), new FieldMetadata(field));
                    }
                }
            }
        }
        this.typeToFields.put(type, nameToField);
        return nameToField;
    }

    public String toJson(Object object) {
        return toJson(object, object == null ? null : object.getClass(), (Class) null);
    }

    public String toJson(Object object, Class knownType) {
        return toJson(object, knownType, (Class) null);
    }

    public String toJson(Object object, Class knownType, Class elementType) {
        Writer buffer = new StringWriter();
        toJson(object, knownType, elementType, buffer);
        return buffer.toString();
    }

    public void toJson(Object object, FileHandle file) {
        toJson(object, object == null ? null : object.getClass(), null, file);
    }

    public void toJson(Object object, Class knownType, FileHandle file) {
        toJson(object, knownType, null, file);
    }

    public void toJson(Object object, Class knownType, Class elementType, FileHandle file) {
        Writer writer = null;
        try {
            writer = file.writer(false, "UTF-8");
            toJson(object, knownType, elementType, writer);
            StreamUtils.closeQuietly(writer);
        } catch (Exception ex) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Error writing file: ");
            stringBuilder.append(file);
            throw new SerializationException(stringBuilder.toString(), ex);
        } catch (Throwable th) {
            StreamUtils.closeQuietly(writer);
        }
    }

    public void toJson(Object object, Writer writer) {
        toJson(object, object == null ? null : object.getClass(), null, writer);
    }

    public void toJson(Object object, Class knownType, Writer writer) {
        toJson(object, knownType, null, writer);
    }

    public void toJson(Object object, Class knownType, Class elementType, Writer writer) {
        setWriter(writer);
        try {
            writeValue(object, knownType, elementType);
        } finally {
            StreamUtils.closeQuietly(this.writer);
            this.writer = null;
        }
    }

    public void setWriter(Writer writer) {
        if (!(writer instanceof JsonWriter)) {
            writer = new JsonWriter(writer);
        }
        this.writer = (JsonWriter) writer;
        this.writer.setOutputType(this.outputType);
        this.writer.setQuoteLongValues(this.quoteLongValues);
    }

    public JsonWriter getWriter() {
        return this.writer;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void writeFields(java.lang.Object r12) {
        /*
        r11 = this;
        r0 = r12.getClass();
        r1 = r11.getDefaultValues(r0);
        r2 = r11.getFields(r0);
        r3 = 0;
        r4 = new com.badlogic.gdx.utils.OrderedMap$OrderedMapValues;
        r4.<init>(r2);
        r4 = r4.iterator();
    L_0x0016:
        r5 = r4.hasNext();
        if (r5 == 0) goto L_0x00ff;
    L_0x001c:
        r5 = r4.next();
        r5 = (com.badlogic.gdx.utils.Json.FieldMetadata) r5;
        r6 = r5.field;
        r7 = r6.get(r12);	 Catch:{ ReflectionException -> 0x00d0, SerializationException -> 0x00ac, Exception -> 0x0083 }
        if (r1 == 0) goto L_0x006f;
    L_0x002a:
        r8 = r3 + 1;
        r3 = r1[r3];	 Catch:{ ReflectionException -> 0x006d, SerializationException -> 0x006b, Exception -> 0x0069 }
        if (r7 != 0) goto L_0x0033;
    L_0x0030:
        if (r3 != 0) goto L_0x0033;
    L_0x0032:
        goto L_0x0065;
    L_0x0033:
        if (r7 == 0) goto L_0x0067;
    L_0x0035:
        if (r3 == 0) goto L_0x0067;
    L_0x0037:
        r9 = r7.equals(r3);	 Catch:{ ReflectionException -> 0x006d, SerializationException -> 0x006b, Exception -> 0x0069 }
        if (r9 == 0) goto L_0x003e;
    L_0x003d:
        goto L_0x0065;
    L_0x003e:
        r9 = r7.getClass();	 Catch:{ ReflectionException -> 0x006d, SerializationException -> 0x006b, Exception -> 0x0069 }
        r9 = r9.isArray();	 Catch:{ ReflectionException -> 0x006d, SerializationException -> 0x006b, Exception -> 0x0069 }
        if (r9 == 0) goto L_0x0067;
    L_0x0048:
        r9 = r3.getClass();	 Catch:{ ReflectionException -> 0x006d, SerializationException -> 0x006b, Exception -> 0x0069 }
        r9 = r9.isArray();	 Catch:{ ReflectionException -> 0x006d, SerializationException -> 0x006b, Exception -> 0x0069 }
        if (r9 == 0) goto L_0x0067;
    L_0x0052:
        r9 = r11.equals1;	 Catch:{ ReflectionException -> 0x006d, SerializationException -> 0x006b, Exception -> 0x0069 }
        r10 = 0;
        r9[r10] = r7;	 Catch:{ ReflectionException -> 0x006d, SerializationException -> 0x006b, Exception -> 0x0069 }
        r9 = r11.equals2;	 Catch:{ ReflectionException -> 0x006d, SerializationException -> 0x006b, Exception -> 0x0069 }
        r9[r10] = r3;	 Catch:{ ReflectionException -> 0x006d, SerializationException -> 0x006b, Exception -> 0x0069 }
        r9 = r11.equals1;	 Catch:{ ReflectionException -> 0x006d, SerializationException -> 0x006b, Exception -> 0x0069 }
        r10 = r11.equals2;	 Catch:{ ReflectionException -> 0x006d, SerializationException -> 0x006b, Exception -> 0x0069 }
        r9 = java.util.Arrays.deepEquals(r9, r10);	 Catch:{ ReflectionException -> 0x006d, SerializationException -> 0x006b, Exception -> 0x0069 }
        if (r9 == 0) goto L_0x0067;
    L_0x0065:
        r3 = r8;
        goto L_0x0016;
    L_0x0067:
        r3 = r8;
        goto L_0x006f;
    L_0x0069:
        r3 = move-exception;
        goto L_0x0086;
    L_0x006b:
        r3 = move-exception;
        goto L_0x00af;
    L_0x006d:
        r3 = move-exception;
        goto L_0x00d3;
    L_0x006f:
        r8 = r11.writer;	 Catch:{ ReflectionException -> 0x00d0, SerializationException -> 0x00ac, Exception -> 0x0083 }
        r9 = r6.getName();	 Catch:{ ReflectionException -> 0x00d0, SerializationException -> 0x00ac, Exception -> 0x0083 }
        r8.name(r9);	 Catch:{ ReflectionException -> 0x00d0, SerializationException -> 0x00ac, Exception -> 0x0083 }
        r8 = r6.getType();	 Catch:{ ReflectionException -> 0x00d0, SerializationException -> 0x00ac, Exception -> 0x0083 }
        r9 = r5.elementType;	 Catch:{ ReflectionException -> 0x00d0, SerializationException -> 0x00ac, Exception -> 0x0083 }
        r11.writeValue(r7, r8, r9);	 Catch:{ ReflectionException -> 0x00d0, SerializationException -> 0x00ac, Exception -> 0x0083 }
        goto L_0x0016;
    L_0x0083:
        r7 = move-exception;
        r8 = r3;
        r3 = r7;
    L_0x0086:
        r7 = new com.badlogic.gdx.utils.SerializationException;
        r7.<init>(r3);
        r9 = new java.lang.StringBuilder;
        r9.<init>();
        r9.append(r6);
        r10 = " (";
        r9.append(r10);
        r10 = r0.getName();
        r9.append(r10);
        r10 = ")";
        r9.append(r10);
        r9 = r9.toString();
        r7.addTrace(r9);
        throw r7;
    L_0x00ac:
        r7 = move-exception;
        r8 = r3;
        r3 = r7;
    L_0x00af:
        r7 = new java.lang.StringBuilder;
        r7.<init>();
        r7.append(r6);
        r9 = " (";
        r7.append(r9);
        r9 = r0.getName();
        r7.append(r9);
        r9 = ")";
        r7.append(r9);
        r7 = r7.toString();
        r3.addTrace(r7);
        throw r3;
    L_0x00d0:
        r7 = move-exception;
        r8 = r3;
        r3 = r7;
    L_0x00d3:
        r7 = new com.badlogic.gdx.utils.SerializationException;
        r9 = new java.lang.StringBuilder;
        r9.<init>();
        r10 = "Error accessing field: ";
        r9.append(r10);
        r10 = r6.getName();
        r9.append(r10);
        r10 = " (";
        r9.append(r10);
        r10 = r0.getName();
        r9.append(r10);
        r10 = ")";
        r9.append(r10);
        r9 = r9.toString();
        r7.<init>(r9, r3);
        throw r7;
    L_0x00ff:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.badlogic.gdx.utils.Json.writeFields(java.lang.Object):void");
    }

    private Object[] getDefaultValues(Class type) {
        StringBuilder stringBuilder;
        if (!this.usePrototypes) {
            return null;
        }
        if (this.classToDefaultValues.containsKey(type)) {
            return (Object[]) this.classToDefaultValues.get(type);
        }
        try {
            Object object = newInstance(type);
            ObjectMap<String, FieldMetadata> fields = getFields(type);
            Object[] values = new Object[fields.size];
            this.classToDefaultValues.put(type, values);
            int i = 0;
            Iterator i$ = fields.values().iterator();
            while (i$.hasNext()) {
                Field field = ((FieldMetadata) i$.next()).field;
                int i2 = i + 1;
                try {
                    values[i] = field.get(object);
                    i = i2;
                } catch (int i3) {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("Error accessing field: ");
                    stringBuilder.append(field.getName());
                    stringBuilder.append(" (");
                    stringBuilder.append(type.getName());
                    stringBuilder.append(")");
                    throw new SerializationException(stringBuilder.toString(), i3);
                } catch (SerializationException ex) {
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append(field);
                    stringBuilder2.append(" (");
                    stringBuilder2.append(type.getName());
                    stringBuilder2.append(")");
                    ex.addTrace(stringBuilder2.toString());
                    throw ex;
                } catch (Throwable runtimeEx) {
                    SerializationException ex2 = new SerializationException(runtimeEx);
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(field);
                    stringBuilder.append(" (");
                    stringBuilder.append(type.getName());
                    stringBuilder.append(")");
                    ex2.addTrace(stringBuilder.toString());
                    throw ex2;
                }
            }
            return values;
        } catch (Exception e) {
            this.classToDefaultValues.put(type, null);
            return null;
        }
    }

    public void writeField(Object object, String name) {
        writeField(object, name, name, null);
    }

    public void writeField(Object object, String name, Class elementType) {
        writeField(object, name, name, elementType);
    }

    public void writeField(Object object, String fieldName, String jsonName) {
        writeField(object, fieldName, jsonName, null);
    }

    public void writeField(Object object, String fieldName, String jsonName, Class elementType) {
        StringBuilder stringBuilder;
        Class type = object.getClass();
        FieldMetadata metadata = (FieldMetadata) getFields(type).get(fieldName);
        if (metadata == null) {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("Field not found: ");
            stringBuilder2.append(fieldName);
            stringBuilder2.append(" (");
            stringBuilder2.append(type.getName());
            stringBuilder2.append(")");
            throw new SerializationException(stringBuilder2.toString());
        }
        Field field = metadata.field;
        if (elementType == null) {
            elementType = metadata.elementType;
        }
        try {
            this.writer.name(jsonName);
            writeValue(field.get(object), field.getType(), elementType);
        } catch (ReflectionException ex) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Error accessing field: ");
            stringBuilder.append(field.getName());
            stringBuilder.append(" (");
            stringBuilder.append(type.getName());
            stringBuilder.append(")");
            throw new SerializationException(stringBuilder.toString(), ex);
        } catch (SerializationException ex2) {
            StringBuilder stringBuilder3 = new StringBuilder();
            stringBuilder3.append(field);
            stringBuilder3.append(" (");
            stringBuilder3.append(type.getName());
            stringBuilder3.append(")");
            ex2.addTrace(stringBuilder3.toString());
            throw ex2;
        } catch (Throwable runtimeEx) {
            SerializationException ex3 = new SerializationException(runtimeEx);
            stringBuilder = new StringBuilder();
            stringBuilder.append(field);
            stringBuilder.append(" (");
            stringBuilder.append(type.getName());
            stringBuilder.append(")");
            ex3.addTrace(stringBuilder.toString());
            throw ex3;
        }
    }

    public void writeValue(String name, Object value) {
        try {
            this.writer.name(name);
            if (value == null) {
                writeValue(value, null, null);
            } else {
                writeValue(value, value.getClass(), null);
            }
        } catch (Throwable ex) {
            throw new SerializationException(ex);
        }
    }

    public void writeValue(String name, Object value, Class knownType) {
        try {
            this.writer.name(name);
            writeValue(value, knownType, null);
        } catch (Throwable ex) {
            throw new SerializationException(ex);
        }
    }

    public void writeValue(String name, Object value, Class knownType, Class elementType) {
        try {
            this.writer.name(name);
            writeValue(value, knownType, elementType);
        } catch (Throwable ex) {
            throw new SerializationException(ex);
        }
    }

    public void writeValue(Object value) {
        if (value == null) {
            writeValue(value, null, null);
        } else {
            writeValue(value, value.getClass(), null);
        }
    }

    public void writeValue(Object value, Class knownType) {
        writeValue(value, knownType, null);
    }

    public void writeValue(Object value, Class knownType, Class elementType) {
        if (value == null) {
            try {
                this.writer.value(null);
                return;
            } catch (Throwable ex) {
                throw new SerializationException(ex);
            }
        }
        if (!((knownType != null && knownType.isPrimitive()) || knownType == String.class || knownType == Integer.class || knownType == Boolean.class || knownType == Float.class || knownType == Long.class || knownType == Double.class || knownType == Short.class || knownType == Byte.class)) {
            if (knownType != Character.class) {
                Class actualType = value.getClass();
                if (!(actualType.isPrimitive() || actualType == String.class || actualType == Integer.class || actualType == Boolean.class || actualType == Float.class || actualType == Long.class || actualType == Double.class || actualType == Short.class || actualType == Byte.class)) {
                    if (actualType != Character.class) {
                        if (value instanceof Serializable) {
                            writeObjectStart(actualType, knownType);
                            ((Serializable) value).write(this);
                            writeObjectEnd();
                            return;
                        }
                        Serializer serializer = (Serializer) this.classToSerializer.get(actualType);
                        if (serializer != null) {
                            serializer.write(this, value, knownType);
                            return;
                        } else if (value instanceof Array) {
                            if (knownType == null || actualType == knownType || actualType == Array.class) {
                                writeArrayStart();
                                Array array = (Array) value;
                                n = array.size;
                                for (i = 0; i < n; i++) {
                                    writeValue(array.get(i), elementType, null);
                                }
                                writeArrayEnd();
                                return;
                            }
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("Serialization of an Array other than the known type is not supported.\nKnown type: ");
                            stringBuilder.append(knownType);
                            stringBuilder.append("\nActual type: ");
                            stringBuilder.append(actualType);
                            throw new SerializationException(stringBuilder.toString());
                        } else if (value instanceof Collection) {
                            if (this.typeName == null || actualType == ArrayList.class || (knownType != null && knownType == actualType)) {
                                writeArrayStart();
                                for (Object item : (Collection) value) {
                                    writeValue(item, elementType, null);
                                }
                                writeArrayEnd();
                            } else {
                                writeObjectStart(actualType, knownType);
                                writeArrayStart("items");
                                for (Object item2 : (Collection) value) {
                                    writeValue(item2, elementType, null);
                                }
                                writeArrayEnd();
                                writeObjectEnd();
                            }
                            return;
                        } else if (actualType.isArray()) {
                            if (elementType == null) {
                                elementType = actualType.getComponentType();
                            }
                            int length = ArrayReflection.getLength(value);
                            writeArrayStart();
                            for (i = 0; i < length; i++) {
                                writeValue(ArrayReflection.get(value, i), elementType, null);
                            }
                            writeArrayEnd();
                            return;
                        } else if (value instanceof ObjectMap) {
                            if (knownType == null) {
                                knownType = ObjectMap.class;
                            }
                            writeObjectStart(actualType, knownType);
                            i$ = ((ObjectMap) value).entries().iterator();
                            while (i$.hasNext()) {
                                Entry entry = (Entry) i$.next();
                                this.writer.name(convertToString(entry.key));
                                writeValue(entry.value, elementType, null);
                            }
                            writeObjectEnd();
                            return;
                        } else if (value instanceof ArrayMap) {
                            if (knownType == null) {
                                knownType = ArrayMap.class;
                            }
                            writeObjectStart(actualType, knownType);
                            ArrayMap map = (ArrayMap) value;
                            n = map.size;
                            for (i = 0; i < n; i++) {
                                this.writer.name(convertToString(map.keys[i]));
                                writeValue(map.values[i], elementType, null);
                            }
                            writeObjectEnd();
                            return;
                        } else if (value instanceof Map) {
                            if (knownType == null) {
                                knownType = HashMap.class;
                            }
                            writeObjectStart(actualType, knownType);
                            for (Map.Entry entry2 : ((Map) value).entrySet()) {
                                this.writer.name(convertToString(entry2.getKey()));
                                writeValue(entry2.getValue(), elementType, null);
                            }
                            writeObjectEnd();
                            return;
                        } else if (ClassReflection.isAssignableFrom(Enum.class, actualType)) {
                            if (this.typeName == null || (knownType != null && knownType == actualType)) {
                                this.writer.value(convertToString((Enum) value));
                            } else {
                                if (actualType.getEnumConstants() == null) {
                                    actualType = actualType.getSuperclass();
                                }
                                writeObjectStart(actualType, null);
                                this.writer.name(FirebaseAnalytics$Param.VALUE);
                                this.writer.value(convertToString((Enum) value));
                                writeObjectEnd();
                            }
                            return;
                        } else {
                            writeObjectStart(actualType, knownType);
                            writeFields(value);
                            writeObjectEnd();
                            return;
                        }
                    }
                }
                writeObjectStart(actualType, null);
                writeValue(FirebaseAnalytics$Param.VALUE, value);
                writeObjectEnd();
                return;
            }
        }
        this.writer.value(value);
    }

    public void writeObjectStart(String name) {
        try {
            this.writer.name(name);
            writeObjectStart();
        } catch (Throwable ex) {
            throw new SerializationException(ex);
        }
    }

    public void writeObjectStart(String name, Class actualType, Class knownType) {
        try {
            this.writer.name(name);
            writeObjectStart(actualType, knownType);
        } catch (Throwable ex) {
            throw new SerializationException(ex);
        }
    }

    public void writeObjectStart() {
        try {
            this.writer.object();
        } catch (Throwable ex) {
            throw new SerializationException(ex);
        }
    }

    public void writeObjectStart(Class actualType, Class knownType) {
        try {
            this.writer.object();
            if (knownType == null || knownType != actualType) {
                writeType(actualType);
            }
        } catch (Throwable ex) {
            throw new SerializationException(ex);
        }
    }

    public void writeObjectEnd() {
        try {
            this.writer.pop();
        } catch (Throwable ex) {
            throw new SerializationException(ex);
        }
    }

    public void writeArrayStart(String name) {
        try {
            this.writer.name(name);
            this.writer.array();
        } catch (Throwable ex) {
            throw new SerializationException(ex);
        }
    }

    public void writeArrayStart() {
        try {
            this.writer.array();
        } catch (Throwable ex) {
            throw new SerializationException(ex);
        }
    }

    public void writeArrayEnd() {
        try {
            this.writer.pop();
        } catch (Throwable ex) {
            throw new SerializationException(ex);
        }
    }

    public void writeType(Class type) {
        if (this.typeName != null) {
            String className = getTag(type);
            if (className == null) {
                className = type.getName();
            }
            try {
                this.writer.set(this.typeName, className);
            } catch (Throwable ex) {
                throw new SerializationException(ex);
            }
        }
    }

    public <T> T fromJson(Class<T> type, Reader reader) {
        return readValue((Class) type, null, new JsonReader().parse(reader));
    }

    public <T> T fromJson(Class<T> type, Class elementType, Reader reader) {
        return readValue((Class) type, elementType, new JsonReader().parse(reader));
    }

    public <T> T fromJson(Class<T> type, InputStream input) {
        return readValue((Class) type, null, new JsonReader().parse(input));
    }

    public <T> T fromJson(Class<T> type, Class elementType, InputStream input) {
        return readValue((Class) type, elementType, new JsonReader().parse(input));
    }

    public <T> T fromJson(Class<T> type, FileHandle file) {
        try {
            return readValue((Class) type, null, new JsonReader().parse(file));
        } catch (Exception ex) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Error reading file: ");
            stringBuilder.append(file);
            throw new SerializationException(stringBuilder.toString(), ex);
        }
    }

    public <T> T fromJson(Class<T> type, Class elementType, FileHandle file) {
        try {
            return readValue((Class) type, elementType, new JsonReader().parse(file));
        } catch (Exception ex) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Error reading file: ");
            stringBuilder.append(file);
            throw new SerializationException(stringBuilder.toString(), ex);
        }
    }

    public <T> T fromJson(Class<T> type, char[] data, int offset, int length) {
        return readValue((Class) type, null, new JsonReader().parse(data, offset, length));
    }

    public <T> T fromJson(Class<T> type, Class elementType, char[] data, int offset, int length) {
        return readValue((Class) type, elementType, new JsonReader().parse(data, offset, length));
    }

    public <T> T fromJson(Class<T> type, String json) {
        return readValue((Class) type, null, new JsonReader().parse(json));
    }

    public <T> T fromJson(Class<T> type, Class elementType, String json) {
        return readValue((Class) type, elementType, new JsonReader().parse(json));
    }

    public void readField(Object object, String name, JsonValue jsonData) {
        readField(object, name, name, null, jsonData);
    }

    public void readField(Object object, String name, Class elementType, JsonValue jsonData) {
        readField(object, name, name, elementType, jsonData);
    }

    public void readField(Object object, String fieldName, String jsonName, JsonValue jsonData) {
        readField(object, fieldName, jsonName, null, jsonData);
    }

    public void readField(Object object, String fieldName, String jsonName, Class elementType, JsonValue jsonMap) {
        String str = fieldName;
        Class type = object.getClass();
        FieldMetadata metadata = (FieldMetadata) getFields(type).get(str);
        if (metadata == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Field not found: ");
            stringBuilder.append(str);
            stringBuilder.append(" (");
            stringBuilder.append(type.getName());
            stringBuilder.append(")");
            throw new SerializationException(stringBuilder.toString());
        }
        Class elementType2;
        Field field = metadata.field;
        if (elementType == null) {
            elementType2 = metadata.elementType;
        } else {
            elementType2 = elementType;
        }
        readField(object, field, jsonName, elementType2, jsonMap);
    }

    public void readField(Object object, Field field, String jsonName, Class elementType, JsonValue jsonMap) {
        StringBuilder stringBuilder;
        JsonValue jsonValue = jsonMap.get(jsonName);
        if (jsonValue != null) {
            try {
                field.set(object, readValue(field.getType(), elementType, jsonValue));
            } catch (ReflectionException ex) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("Error accessing field: ");
                stringBuilder.append(field.getName());
                stringBuilder.append(" (");
                stringBuilder.append(field.getDeclaringClass().getName());
                stringBuilder.append(")");
                throw new SerializationException(stringBuilder.toString(), ex);
            } catch (SerializationException ex2) {
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append(field.getName());
                stringBuilder2.append(" (");
                stringBuilder2.append(field.getDeclaringClass().getName());
                stringBuilder2.append(")");
                ex2.addTrace(stringBuilder2.toString());
                throw ex2;
            } catch (Throwable runtimeEx) {
                SerializationException ex3 = new SerializationException(runtimeEx);
                stringBuilder = new StringBuilder();
                stringBuilder.append(field.getName());
                stringBuilder.append(" (");
                stringBuilder.append(field.getDeclaringClass().getName());
                stringBuilder.append(")");
                ex3.addTrace(stringBuilder.toString());
                throw ex3;
            }
        }
    }

    public void readFields(Object object, JsonValue jsonMap) {
        StringBuilder stringBuilder;
        Class type = object.getClass();
        ObjectMap<String, FieldMetadata> fields = getFields(type);
        for (JsonValue child = jsonMap.child; child != null; child = child.next) {
            FieldMetadata metadata = (FieldMetadata) fields.get(child.name());
            if (metadata != null) {
                Field field = metadata.field;
                try {
                    field.set(object, readValue(field.getType(), metadata.elementType, child));
                } catch (ReflectionException ex) {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("Error accessing field: ");
                    stringBuilder.append(field.getName());
                    stringBuilder.append(" (");
                    stringBuilder.append(type.getName());
                    stringBuilder.append(")");
                    throw new SerializationException(stringBuilder.toString(), ex);
                } catch (SerializationException ex2) {
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append(field.getName());
                    stringBuilder2.append(" (");
                    stringBuilder2.append(type.getName());
                    stringBuilder2.append(")");
                    ex2.addTrace(stringBuilder2.toString());
                    throw ex2;
                } catch (Throwable runtimeEx) {
                    SerializationException ex3 = new SerializationException(runtimeEx);
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(field.getName());
                    stringBuilder.append(" (");
                    stringBuilder.append(type.getName());
                    stringBuilder.append(")");
                    ex3.addTrace(stringBuilder.toString());
                    throw ex3;
                }
            } else if (!this.ignoreUnknownFields) {
                StringBuilder stringBuilder3 = new StringBuilder();
                stringBuilder3.append("Field not found: ");
                stringBuilder3.append(child.name());
                stringBuilder3.append(" (");
                stringBuilder3.append(type.getName());
                stringBuilder3.append(")");
                throw new SerializationException(stringBuilder3.toString());
            }
        }
    }

    public <T> T readValue(String name, Class<T> type, JsonValue jsonMap) {
        return readValue((Class) type, null, jsonMap.get(name));
    }

    public <T> T readValue(String name, Class<T> type, T defaultValue, JsonValue jsonMap) {
        JsonValue jsonValue = jsonMap.get(name);
        if (jsonValue == null) {
            return defaultValue;
        }
        return readValue((Class) type, null, jsonValue);
    }

    public <T> T readValue(String name, Class<T> type, Class elementType, JsonValue jsonMap) {
        return readValue((Class) type, elementType, jsonMap.get(name));
    }

    public <T> T readValue(String name, Class<T> type, Class elementType, T defaultValue, JsonValue jsonMap) {
        JsonValue jsonValue = jsonMap.get(name);
        if (jsonValue == null) {
            return defaultValue;
        }
        return readValue((Class) type, elementType, jsonValue);
    }

    public <T> T readValue(Class<T> type, Class elementType, T t, JsonValue jsonData) {
        return readValue((Class) type, elementType, jsonData);
    }

    public <T> T readValue(Class<T> type, JsonValue jsonData) {
        return readValue((Class) type, null, jsonData);
    }

    public <T> T readValue(Class<T> type, Class elementType, JsonValue jsonData) {
        if (jsonData == null) {
            return null;
        }
        if (jsonData.isObject()) {
            String className = this.typeName == null ? null : jsonData.getString(this.typeName, null);
            if (className != null) {
                jsonData.remove(this.typeName);
                type = getClass(className);
                if (type == null) {
                    try {
                        type = ClassReflection.forName(className);
                    } catch (Throwable ex) {
                        throw new SerializationException(ex);
                    }
                }
            }
            if (type != null) {
                if (!(type == String.class || type == Integer.class || type == Boolean.class || type == Float.class || type == Long.class || type == Double.class || type == Short.class || type == Byte.class || type == Character.class)) {
                    if (!ClassReflection.isAssignableFrom(Enum.class, type)) {
                        if (this.typeName == null || !ClassReflection.isAssignableFrom(Collection.class, type)) {
                            Serializer serializer = (Serializer) this.classToSerializer.get(type);
                            if (serializer != null) {
                                return serializer.read(this, jsonData, type);
                            }
                            ObjectMap object = newInstance(type);
                            if (object instanceof Serializable) {
                                ((Serializable) object).read(this, jsonData);
                                return object;
                            } else if (object instanceof ObjectMap) {
                                ObjectMap result = object;
                                for (child = jsonData.child; child != null; child = child.next) {
                                    result.put(child.name(), readValue(elementType, null, child));
                                }
                                return result;
                            } else if (object instanceof ArrayMap) {
                                ArrayMap result2 = (ArrayMap) object;
                                for (child = jsonData.child; child != null; child = child.next) {
                                    result2.put(child.name(), readValue(elementType, null, child));
                                }
                                return result2;
                            } else if (object instanceof Map) {
                                Map result3 = (Map) object;
                                for (child = jsonData.child; child != null; child = child.next) {
                                    result3.put(child.name(), readValue(elementType, null, child));
                                }
                                return result3;
                            } else {
                                readFields(object, jsonData);
                                return object;
                            }
                        }
                        jsonData = jsonData.get("items");
                    }
                }
                return readValue(FirebaseAnalytics$Param.VALUE, (Class) type, jsonData);
            } else if (this.defaultSerializer != null) {
                return this.defaultSerializer.read(this, jsonData, type);
            } else {
                return jsonData;
            }
        }
        if (type != null) {
            Serializer serializer2 = (Serializer) this.classToSerializer.get(type);
            if (serializer2 != null) {
                return serializer2.read(this, jsonData, type);
            }
        }
        int i;
        if (jsonData.isArray()) {
            if (type == null || type == Object.class) {
                type = Array.class;
            }
            JsonValue child;
            if (ClassReflection.isAssignableFrom(Array.class, type)) {
                Array result4 = type == Array.class ? new Array() : (Array) newInstance(type);
                for (child = jsonData.child; child != null; child = child.next) {
                    result4.add(readValue(elementType, null, child));
                }
                return result4;
            } else if (ClassReflection.isAssignableFrom(Collection.class, type)) {
                Collection result5 = type.isInterface() ? new ArrayList() : (Collection) newInstance(type);
                for (child = jsonData.child; child != null; child = child.next) {
                    result5.add(readValue(elementType, null, child));
                }
                return result5;
            } else if (type.isArray()) {
                Class componentType = type.getComponentType();
                if (elementType == null) {
                    elementType = componentType;
                }
                Object result6 = ArrayReflection.newInstance(componentType, jsonData.size);
                i = 0;
                JsonValue child2 = jsonData.child;
                while (child2 != null) {
                    int i2 = i + 1;
                    ArrayReflection.set(result6, i, readValue(elementType, null, child2));
                    child2 = child2.next;
                    i = i2;
                }
                return result6;
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unable to convert value to required type: ");
                stringBuilder.append(jsonData);
                stringBuilder.append(" (");
                stringBuilder.append(type.getName());
                stringBuilder.append(")");
                throw new SerializationException(stringBuilder.toString());
            }
        }
        if (jsonData.isNumber()) {
            if (type != null) {
                try {
                    if (type != Float.TYPE) {
                        if (type != Float.class) {
                            if (type != Integer.TYPE) {
                                if (type != Integer.class) {
                                    if (type != Long.TYPE) {
                                        if (type != Long.class) {
                                            if (type != Double.TYPE) {
                                                if (type != Double.class) {
                                                    if (type == String.class) {
                                                        return jsonData.asString();
                                                    }
                                                    if (type != Short.TYPE) {
                                                        if (type != Short.class) {
                                                            if (type != Byte.TYPE) {
                                                                if (type != Byte.class) {
                                                                    jsonData = new JsonValue(jsonData.asString());
                                                                }
                                                            }
                                                            return Byte.valueOf(jsonData.asByte());
                                                        }
                                                    }
                                                    return Short.valueOf(jsonData.asShort());
                                                }
                                            }
                                            return Double.valueOf(jsonData.asDouble());
                                        }
                                    }
                                    return Long.valueOf(jsonData.asLong());
                                }
                            }
                            return Integer.valueOf(jsonData.asInt());
                        }
                    }
                } catch (NumberFormatException e) {
                }
            }
            return Float.valueOf(jsonData.asFloat());
        }
        if (jsonData.isBoolean()) {
            if (type != null) {
                try {
                    if (type != Boolean.TYPE) {
                        if (type != Boolean.class) {
                            jsonData = new JsonValue(jsonData.asString());
                        }
                    }
                } catch (NumberFormatException e2) {
                }
            }
            return Boolean.valueOf(jsonData.asBoolean());
        }
        if (!jsonData.isString()) {
            return null;
        }
        String string = jsonData.asString();
        if (type != null) {
            if (type != String.class) {
                try {
                    if (type != Integer.TYPE) {
                        if (type != Integer.class) {
                            if (type != Float.TYPE) {
                                if (type != Float.class) {
                                    if (type != Long.TYPE) {
                                        if (type != Long.class) {
                                            if (type != Double.TYPE) {
                                                if (type != Double.class) {
                                                    if (type != Short.TYPE) {
                                                        if (type != Short.class) {
                                                            if (type != Byte.TYPE) {
                                                                if (type != Byte.class) {
                                                                    if (type != Boolean.TYPE) {
                                                                        if (type != Boolean.class) {
                                                                            if (type != Character.TYPE) {
                                                                                if (type != Character.class) {
                                                                                    if (ClassReflection.isAssignableFrom(Enum.class, type)) {
                                                                                        for (Enum e3 : (Enum[]) type.getEnumConstants()) {
                                                                                            if (string.equals(convertToString(e3))) {
                                                                                                return e3;
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                    if (type == CharSequence.class) {
                                                                                        return string;
                                                                                    }
                                                                                    StringBuilder stringBuilder2 = new StringBuilder();
                                                                                    stringBuilder2.append("Unable to convert value to required type: ");
                                                                                    stringBuilder2.append(jsonData);
                                                                                    stringBuilder2.append(" (");
                                                                                    stringBuilder2.append(type.getName());
                                                                                    stringBuilder2.append(")");
                                                                                    throw new SerializationException(stringBuilder2.toString());
                                                                                }
                                                                            }
                                                                            return Character.valueOf(string.charAt(0));
                                                                        }
                                                                    }
                                                                    return Boolean.valueOf(string);
                                                                }
                                                            }
                                                            return Byte.valueOf(string);
                                                        }
                                                    }
                                                    return Short.valueOf(string);
                                                }
                                            }
                                            return Double.valueOf(string);
                                        }
                                    }
                                    return Long.valueOf(string);
                                }
                            }
                            return Float.valueOf(string);
                        }
                    }
                    return Integer.valueOf(string);
                } catch (NumberFormatException e4) {
                }
            }
        }
        return string;
    }

    private String convertToString(Enum e) {
        return this.enumNames ? e.name() : e.toString();
    }

    private String convertToString(Object object) {
        if (object instanceof Enum) {
            return convertToString((Enum) object);
        }
        if (object instanceof Class) {
            return ((Class) object).getName();
        }
        return String.valueOf(object);
    }

    protected Object newInstance(Class type) {
        StringBuilder stringBuilder;
        try {
            return ClassReflection.newInstance(type);
        } catch (Exception e) {
            Exception ex = e;
            try {
                Constructor constructor = ClassReflection.getDeclaredConstructor(type, new Class[0]);
                constructor.setAccessible(true);
                return constructor.newInstance(new Object[0]);
            } catch (SecurityException e2) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("Error constructing instance of class: ");
                stringBuilder.append(type.getName());
                throw new SerializationException(stringBuilder.toString(), ex);
            } catch (ReflectionException e3) {
                if (ClassReflection.isAssignableFrom(Enum.class, type)) {
                    if (type.getEnumConstants() == null) {
                        type = type.getSuperclass();
                    }
                    return type.getEnumConstants()[0];
                } else if (type.isArray()) {
                    r3 = new StringBuilder();
                    r3.append("Encountered JSON object when expected array of type: ");
                    r3.append(type.getName());
                    throw new SerializationException(r3.toString(), ex);
                } else if (!ClassReflection.isMemberClass(type) || ClassReflection.isStaticClass(type)) {
                    r3 = new StringBuilder();
                    r3.append("Class cannot be created (missing no-arg constructor): ");
                    r3.append(type.getName());
                    throw new SerializationException(r3.toString(), ex);
                } else {
                    r3 = new StringBuilder();
                    r3.append("Class cannot be created (non-static member class): ");
                    r3.append(type.getName());
                    throw new SerializationException(r3.toString(), ex);
                }
            } catch (Exception privateConstructorException) {
                ex = privateConstructorException;
                stringBuilder = new StringBuilder();
                stringBuilder.append("Error constructing instance of class: ");
                stringBuilder.append(type.getName());
                throw new SerializationException(stringBuilder.toString(), ex);
            }
        }
    }

    public String prettyPrint(Object object) {
        return prettyPrint(object, 0);
    }

    public String prettyPrint(String json) {
        return prettyPrint(json, 0);
    }

    public String prettyPrint(Object object, int singleLineColumns) {
        return prettyPrint(toJson(object), singleLineColumns);
    }

    public String prettyPrint(String json, int singleLineColumns) {
        return new JsonReader().parse(json).prettyPrint(this.outputType, singleLineColumns);
    }

    public String prettyPrint(Object object, JsonValue$PrettyPrintSettings settings) {
        return prettyPrint(toJson(object), settings);
    }

    public String prettyPrint(String json, JsonValue$PrettyPrintSettings settings) {
        return new JsonReader().parse(json).prettyPrint(settings);
    }
}
