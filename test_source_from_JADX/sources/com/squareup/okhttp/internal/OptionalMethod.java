package com.squareup.okhttp.internal;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class OptionalMethod<T> {
    private final String methodName;
    private final Class[] methodParams;
    private final Class<?> returnType;

    public OptionalMethod(Class<?> returnType, String methodName, Class... methodParams) {
        this.returnType = returnType;
        this.methodName = methodName;
        this.methodParams = methodParams;
    }

    public boolean isSupported(T target) {
        return getMethod(target.getClass()) != null;
    }

    public Object invokeOptional(T target, Object... args) throws InvocationTargetException {
        Method m = getMethod(target.getClass());
        if (m == null) {
            return null;
        }
        try {
            return m.invoke(target, args);
        } catch (IllegalAccessException e) {
            return null;
        }
    }

    public Object invokeOptionalWithoutCheckedException(T target, Object... args) {
        try {
            return invokeOptional(target, args);
        } catch (InvocationTargetException e) {
            Throwable targetException = e.getTargetException();
            if (targetException instanceof RuntimeException) {
                throw ((RuntimeException) targetException);
            }
            AssertionError error = new AssertionError("Unexpected exception");
            error.initCause(targetException);
            throw error;
        }
    }

    public Object invoke(T target, Object... args) throws InvocationTargetException {
        Method m = getMethod(target.getClass());
        if (m == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Method ");
            stringBuilder.append(this.methodName);
            stringBuilder.append(" not supported for object ");
            stringBuilder.append(target);
            throw new AssertionError(stringBuilder.toString());
        }
        try {
            return m.invoke(target, args);
        } catch (IllegalAccessException e) {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("Unexpectedly could not call: ");
            stringBuilder2.append(m);
            AssertionError error = new AssertionError(stringBuilder2.toString());
            error.initCause(e);
            throw error;
        }
    }

    public Object invokeWithoutCheckedException(T target, Object... args) {
        try {
            return invoke(target, args);
        } catch (InvocationTargetException e) {
            Throwable targetException = e.getTargetException();
            if (targetException instanceof RuntimeException) {
                throw ((RuntimeException) targetException);
            }
            AssertionError error = new AssertionError("Unexpected exception");
            error.initCause(targetException);
            throw error;
        }
    }

    private Method getMethod(Class<?> clazz) {
        if (this.methodName == null) {
            return null;
        }
        Method method = getPublicMethod(clazz, this.methodName, this.methodParams);
        if (method == null || this.returnType == null || this.returnType.isAssignableFrom(method.getReturnType())) {
            return method;
        }
        return null;
    }

    private static Method getPublicMethod(Class<?> clazz, String methodName, Class[] parameterTypes) {
        Method method = null;
        try {
            method = clazz.getMethod(methodName, parameterTypes);
            if ((method.getModifiers() & 1) == 0) {
                method = null;
            }
        } catch (NoSuchMethodException e) {
        }
        return method;
    }
}
