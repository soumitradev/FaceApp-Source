package com.thoughtworks.xstream.converters.javabean;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.UndeclaredThrowableException;

public class BeanProperty {
    private static final Object[] EMPTY_ARGS = new Object[0];
    protected Method getter;
    private Class memberClass;
    private String propertyName;
    private Method setter;
    private Class type;

    public BeanProperty(Class memberClass, String propertyName, Class propertyType) {
        this.memberClass = memberClass;
        this.propertyName = propertyName;
        this.type = propertyType;
    }

    public Class getBeanClass() {
        return this.memberClass;
    }

    public Class getType() {
        return this.type;
    }

    public String getName() {
        return this.propertyName;
    }

    public boolean isReadable() {
        return this.getter != null;
    }

    public boolean isWritable() {
        return this.setter != null;
    }

    public Object get(Object member) throws IllegalArgumentException, IllegalAccessException {
        if (isReadable()) {
            try {
                return this.getter.invoke(member, EMPTY_ARGS);
            } catch (InvocationTargetException e) {
                throw new UndeclaredThrowableException(e.getTargetException());
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Property ");
        stringBuilder.append(this.propertyName);
        stringBuilder.append(" of ");
        stringBuilder.append(this.memberClass);
        stringBuilder.append(" not readable");
        throw new IllegalStateException(stringBuilder.toString());
    }

    public Object set(Object member, Object newValue) throws IllegalArgumentException, IllegalAccessException {
        if (isWritable()) {
            try {
                return this.setter.invoke(member, new Object[]{newValue});
            } catch (InvocationTargetException e) {
                throw new UndeclaredThrowableException(e.getTargetException());
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Property ");
        stringBuilder.append(this.propertyName);
        stringBuilder.append(" of ");
        stringBuilder.append(this.memberClass);
        stringBuilder.append(" not writable");
        throw new IllegalStateException(stringBuilder.toString());
    }

    public void setGetterMethod(Method method) {
        this.getter = method;
    }

    public void setSetterMethod(Method method) {
        this.setter = method;
    }
}
