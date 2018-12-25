package com.thoughtworks.xstream.converters.reflection;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.WeakHashMap;

public class SunUnsafeReflectionProvider extends SunLimitedUnsafeReflectionProvider {
    private transient Map fieldOffsetCache;

    public SunUnsafeReflectionProvider(FieldDictionary dic) {
        super(dic);
    }

    public void writeField(Object object, String fieldName, Object value, Class definedIn) {
        write(this.fieldDictionary.field(object.getClass(), fieldName, definedIn), object, value);
    }

    private void write(Field field, Object object, Object value) {
        if (exception != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Could not set field ");
            stringBuilder.append(object.getClass());
            stringBuilder.append(".");
            stringBuilder.append(field.getName());
            throw new ObjectAccessException(stringBuilder.toString(), exception);
        }
        try {
            long offset = getFieldOffset(field);
            Class type = field.getType();
            if (!type.isPrimitive()) {
                unsafe.putObject(object, offset, value);
            } else if (type.equals(Integer.TYPE)) {
                unsafe.putInt(object, offset, ((Integer) value).intValue());
            } else if (type.equals(Long.TYPE)) {
                unsafe.putLong(object, offset, ((Long) value).longValue());
            } else if (type.equals(Short.TYPE)) {
                unsafe.putShort(object, offset, ((Short) value).shortValue());
            } else if (type.equals(Character.TYPE)) {
                unsafe.putChar(object, offset, ((Character) value).charValue());
            } else if (type.equals(Byte.TYPE)) {
                unsafe.putByte(object, offset, ((Byte) value).byteValue());
            } else if (type.equals(Float.TYPE)) {
                unsafe.putFloat(object, offset, ((Float) value).floatValue());
            } else if (type.equals(Double.TYPE)) {
                unsafe.putDouble(object, offset, ((Double) value).doubleValue());
            } else if (type.equals(Boolean.TYPE)) {
                unsafe.putBoolean(object, offset, ((Boolean) value).booleanValue());
            } else {
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("Could not set field ");
                stringBuilder2.append(object.getClass());
                stringBuilder2.append(".");
                stringBuilder2.append(field.getName());
                stringBuilder2.append(": Unknown type ");
                stringBuilder2.append(type);
                throw new ObjectAccessException(stringBuilder2.toString());
            }
        } catch (IllegalArgumentException e) {
            StringBuilder stringBuilder3 = new StringBuilder();
            stringBuilder3.append("Could not set field ");
            stringBuilder3.append(object.getClass());
            stringBuilder3.append(".");
            stringBuilder3.append(field.getName());
            throw new ObjectAccessException(stringBuilder3.toString(), e);
        }
    }

    private synchronized long getFieldOffset(Field f) {
        Long l;
        l = (Long) this.fieldOffsetCache.get(f);
        if (l == null) {
            l = new Long(unsafe.objectFieldOffset(f));
            this.fieldOffsetCache.put(f, l);
        }
        return l.longValue();
    }

    private Object readResolve() {
        init();
        return this;
    }

    protected void init() {
        super.init();
        this.fieldOffsetCache = new WeakHashMap();
    }
}
