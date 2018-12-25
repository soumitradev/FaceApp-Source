package com.thoughtworks.xstream.core.util;

import com.thoughtworks.xstream.converters.reflection.ObjectAccessException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class Fields {
    public static Field locate(Class definedIn, Class fieldType, boolean isStatic) {
        Field field = null;
        try {
            Field[] fields = definedIn.getDeclaredFields();
            int i = 0;
            while (i < fields.length) {
                if (Modifier.isStatic(fields[i].getModifiers()) == isStatic && fieldType.isAssignableFrom(fields[i].getType())) {
                    field = fields[i];
                }
                i++;
            }
            if (!(field == null || field.isAccessible())) {
                field.setAccessible(true);
            }
        } catch (SecurityException e) {
        } catch (NoClassDefFoundError e2) {
        }
        return field;
    }

    public static Field find(Class type, String name) {
        StringBuilder stringBuilder;
        try {
            Field result = type.getDeclaredField(name);
            if (!result.isAccessible()) {
                result.setAccessible(true);
            }
            return result;
        } catch (NoSuchFieldException e) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Could not access ");
            stringBuilder.append(type.getName());
            stringBuilder.append(".");
            stringBuilder.append(name);
            stringBuilder.append(" field: ");
            stringBuilder.append(e.getMessage());
            throw new IllegalArgumentException(stringBuilder.toString());
        } catch (NoClassDefFoundError e2) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Could not access ");
            stringBuilder.append(type.getName());
            stringBuilder.append(".");
            stringBuilder.append(name);
            stringBuilder.append(" field: ");
            stringBuilder.append(e2.getMessage());
            throw new ObjectAccessException(stringBuilder.toString());
        }
    }

    public static void write(Field field, Object instance, Object value) {
        StringBuilder stringBuilder;
        try {
            field.set(instance, value);
        } catch (IllegalAccessException e) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Could not write ");
            stringBuilder.append(field.getType().getName());
            stringBuilder.append(".");
            stringBuilder.append(field.getName());
            stringBuilder.append(" field");
            throw new ObjectAccessException(stringBuilder.toString(), e);
        } catch (NoClassDefFoundError e2) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Could not write ");
            stringBuilder.append(field.getType().getName());
            stringBuilder.append(".");
            stringBuilder.append(field.getName());
            stringBuilder.append(" field");
            throw new ObjectAccessException(stringBuilder.toString(), e2);
        }
    }

    public static Object read(Field field, Object instance) {
        StringBuilder stringBuilder;
        try {
            return field.get(instance);
        } catch (IllegalAccessException e) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Could not read ");
            stringBuilder.append(field.getType().getName());
            stringBuilder.append(".");
            stringBuilder.append(field.getName());
            stringBuilder.append(" field");
            throw new ObjectAccessException(stringBuilder.toString(), e);
        } catch (NoClassDefFoundError e2) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Could not read ");
            stringBuilder.append(field.getType().getName());
            stringBuilder.append(".");
            stringBuilder.append(field.getName());
            stringBuilder.append(" field");
            throw new ObjectAccessException(stringBuilder.toString(), e2);
        }
    }
}
