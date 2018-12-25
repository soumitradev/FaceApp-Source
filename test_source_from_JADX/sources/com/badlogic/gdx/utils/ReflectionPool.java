package com.badlogic.gdx.utils;

import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.Constructor;
import com.badlogic.gdx.utils.reflect.ReflectionException;

public class ReflectionPool<T> extends Pool<T> {
    private final Constructor constructor;

    public ReflectionPool(Class<T> type) {
        this(type, 16, Integer.MAX_VALUE);
    }

    public ReflectionPool(Class<T> type, int initialCapacity) {
        this(type, initialCapacity, Integer.MAX_VALUE);
    }

    public ReflectionPool(Class<T> type, int initialCapacity, int max) {
        super(initialCapacity, max);
        this.constructor = findConstructor(type);
        if (this.constructor == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Class cannot be created (missing no-arg constructor): ");
            stringBuilder.append(type.getName());
            throw new RuntimeException(stringBuilder.toString());
        }
    }

    private Constructor findConstructor(Class<T> type) {
        try {
            return ClassReflection.getConstructor(type, (Class[]) null);
        } catch (Exception e) {
            try {
                Constructor constructor = ClassReflection.getDeclaredConstructor(type, (Class[]) null);
                constructor.setAccessible(true);
                return constructor;
            } catch (ReflectionException e2) {
                return null;
            }
        }
    }

    protected T newObject() {
        try {
            return this.constructor.newInstance((Object[]) null);
        } catch (Exception ex) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unable to create new instance: ");
            stringBuilder.append(this.constructor.getDeclaringClass().getName());
            throw new GdxRuntimeException(stringBuilder.toString(), ex);
        }
    }
}
