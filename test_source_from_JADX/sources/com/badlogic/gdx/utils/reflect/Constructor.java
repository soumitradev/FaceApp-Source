package com.badlogic.gdx.utils.reflect;

import java.lang.reflect.InvocationTargetException;

public final class Constructor {
    private final java.lang.reflect.Constructor constructor;

    Constructor(java.lang.reflect.Constructor constructor) {
        this.constructor = constructor;
    }

    public Class[] getParameterTypes() {
        return this.constructor.getParameterTypes();
    }

    public Class getDeclaringClass() {
        return this.constructor.getDeclaringClass();
    }

    public boolean isAccessible() {
        return this.constructor.isAccessible();
    }

    public void setAccessible(boolean accessible) {
        this.constructor.setAccessible(accessible);
    }

    public Object newInstance(Object... args) throws ReflectionException {
        StringBuilder stringBuilder;
        try {
            return this.constructor.newInstance(args);
        } catch (IllegalArgumentException e) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Illegal argument(s) supplied to constructor for class: ");
            stringBuilder.append(getDeclaringClass().getName());
            throw new ReflectionException(stringBuilder.toString(), e);
        } catch (InstantiationException e2) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Could not instantiate instance of class: ");
            stringBuilder.append(getDeclaringClass().getName());
            throw new ReflectionException(stringBuilder.toString(), e2);
        } catch (IllegalAccessException e3) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Could not instantiate instance of class: ");
            stringBuilder.append(getDeclaringClass().getName());
            throw new ReflectionException(stringBuilder.toString(), e3);
        } catch (InvocationTargetException e4) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Exception occurred in constructor for class: ");
            stringBuilder.append(getDeclaringClass().getName());
            throw new ReflectionException(stringBuilder.toString(), e4);
        }
    }
}
