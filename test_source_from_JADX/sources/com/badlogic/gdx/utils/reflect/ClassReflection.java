package com.badlogic.gdx.utils.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public final class ClassReflection {
    public static Class forName(String name) throws ReflectionException {
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException e) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Class not found: ");
            stringBuilder.append(name);
            throw new ReflectionException(stringBuilder.toString(), e);
        }
    }

    public static String getSimpleName(Class c) {
        return c.getSimpleName();
    }

    public static boolean isInstance(Class c, Object obj) {
        return c.isInstance(obj);
    }

    public static boolean isAssignableFrom(Class c1, Class c2) {
        return c1.isAssignableFrom(c2);
    }

    public static boolean isMemberClass(Class c) {
        return c.isMemberClass();
    }

    public static boolean isStaticClass(Class c) {
        return Modifier.isStatic(c.getModifiers());
    }

    public static boolean isArray(Class c) {
        return c.isArray();
    }

    public static <T> T newInstance(Class<T> c) throws ReflectionException {
        StringBuilder stringBuilder;
        try {
            return c.newInstance();
        } catch (InstantiationException e) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Could not instantiate instance of class: ");
            stringBuilder.append(c.getName());
            throw new ReflectionException(stringBuilder.toString(), e);
        } catch (IllegalAccessException e2) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Could not instantiate instance of class: ");
            stringBuilder.append(c.getName());
            throw new ReflectionException(stringBuilder.toString(), e2);
        }
    }

    public static Constructor[] getConstructors(Class c) {
        Constructor[] constructors = c.getConstructors();
        Constructor[] result = new Constructor[constructors.length];
        int j = constructors.length;
        for (int i = 0; i < j; i++) {
            result[i] = new Constructor(constructors[i]);
        }
        return result;
    }

    public static Constructor getConstructor(Class c, Class... parameterTypes) throws ReflectionException {
        StringBuilder stringBuilder;
        try {
            return new Constructor(c.getConstructor(parameterTypes));
        } catch (SecurityException e) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Security violation occurred while getting constructor for class: '");
            stringBuilder.append(c.getName());
            stringBuilder.append("'.");
            throw new ReflectionException(stringBuilder.toString(), e);
        } catch (NoSuchMethodException e2) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Constructor not found for class: ");
            stringBuilder.append(c.getName());
            throw new ReflectionException(stringBuilder.toString(), e2);
        }
    }

    public static Constructor getDeclaredConstructor(Class c, Class... parameterTypes) throws ReflectionException {
        StringBuilder stringBuilder;
        try {
            return new Constructor(c.getDeclaredConstructor(parameterTypes));
        } catch (SecurityException e) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Security violation while getting constructor for class: ");
            stringBuilder.append(c.getName());
            throw new ReflectionException(stringBuilder.toString(), e);
        } catch (NoSuchMethodException e2) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Constructor not found for class: ");
            stringBuilder.append(c.getName());
            throw new ReflectionException(stringBuilder.toString(), e2);
        }
    }

    public static Method[] getMethods(Class c) {
        Method[] methods = c.getMethods();
        Method[] result = new Method[methods.length];
        int j = methods.length;
        for (int i = 0; i < j; i++) {
            result[i] = new Method(methods[i]);
        }
        return result;
    }

    public static Method getMethod(Class c, String name, Class... parameterTypes) throws ReflectionException {
        StringBuilder stringBuilder;
        try {
            return new Method(c.getMethod(name, parameterTypes));
        } catch (SecurityException e) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Security violation while getting method: ");
            stringBuilder.append(name);
            stringBuilder.append(", for class: ");
            stringBuilder.append(c.getName());
            throw new ReflectionException(stringBuilder.toString(), e);
        } catch (NoSuchMethodException e2) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Method not found: ");
            stringBuilder.append(name);
            stringBuilder.append(", for class: ");
            stringBuilder.append(c.getName());
            throw new ReflectionException(stringBuilder.toString(), e2);
        }
    }

    public static Method[] getDeclaredMethods(Class c) {
        Method[] methods = c.getDeclaredMethods();
        Method[] result = new Method[methods.length];
        int j = methods.length;
        for (int i = 0; i < j; i++) {
            result[i] = new Method(methods[i]);
        }
        return result;
    }

    public static Method getDeclaredMethod(Class c, String name, Class... parameterTypes) throws ReflectionException {
        StringBuilder stringBuilder;
        try {
            return new Method(c.getDeclaredMethod(name, parameterTypes));
        } catch (SecurityException e) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Security violation while getting method: ");
            stringBuilder.append(name);
            stringBuilder.append(", for class: ");
            stringBuilder.append(c.getName());
            throw new ReflectionException(stringBuilder.toString(), e);
        } catch (NoSuchMethodException e2) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Method not found: ");
            stringBuilder.append(name);
            stringBuilder.append(", for class: ");
            stringBuilder.append(c.getName());
            throw new ReflectionException(stringBuilder.toString(), e2);
        }
    }

    public static Field[] getFields(Class c) {
        Field[] fields = c.getFields();
        Field[] result = new Field[fields.length];
        int j = fields.length;
        for (int i = 0; i < j; i++) {
            result[i] = new Field(fields[i]);
        }
        return result;
    }

    public static Field getField(Class c, String name) throws ReflectionException {
        StringBuilder stringBuilder;
        try {
            return new Field(c.getField(name));
        } catch (SecurityException e) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Security violation while getting field: ");
            stringBuilder.append(name);
            stringBuilder.append(", for class: ");
            stringBuilder.append(c.getName());
            throw new ReflectionException(stringBuilder.toString(), e);
        } catch (NoSuchFieldException e2) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Field not found: ");
            stringBuilder.append(name);
            stringBuilder.append(", for class: ");
            stringBuilder.append(c.getName());
            throw new ReflectionException(stringBuilder.toString(), e2);
        }
    }

    public static Field[] getDeclaredFields(Class c) {
        Field[] fields = c.getDeclaredFields();
        Field[] result = new Field[fields.length];
        int j = fields.length;
        for (int i = 0; i < j; i++) {
            result[i] = new Field(fields[i]);
        }
        return result;
    }

    public static Field getDeclaredField(Class c, String name) throws ReflectionException {
        StringBuilder stringBuilder;
        try {
            return new Field(c.getDeclaredField(name));
        } catch (SecurityException e) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Security violation while getting field: ");
            stringBuilder.append(name);
            stringBuilder.append(", for class: ");
            stringBuilder.append(c.getName());
            throw new ReflectionException(stringBuilder.toString(), e);
        } catch (NoSuchFieldException e2) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Field not found: ");
            stringBuilder.append(name);
            stringBuilder.append(", for class: ");
            stringBuilder.append(c.getName());
            throw new ReflectionException(stringBuilder.toString(), e2);
        }
    }

    public static boolean isAnnotationPresent(Class c, Class<? extends Annotation> annotationType) {
        return c.isAnnotationPresent(annotationType);
    }

    public static Annotation[] getDeclaredAnnotations(Class c) {
        Annotation[] annotations = c.getDeclaredAnnotations();
        Annotation[] result = new Annotation[annotations.length];
        for (int i = 0; i < annotations.length; i++) {
            result[i] = new Annotation(annotations[i]);
        }
        return result;
    }

    public static Annotation getDeclaredAnnotation(Class c, Class<? extends Annotation> annotationType) {
        for (Annotation annotation : c.getDeclaredAnnotations()) {
            if (annotation.annotationType().equals(annotationType)) {
                return new Annotation(annotation);
            }
        }
        return null;
    }
}
