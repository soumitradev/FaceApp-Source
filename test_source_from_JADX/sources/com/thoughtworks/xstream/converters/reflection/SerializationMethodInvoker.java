package com.thoughtworks.xstream.converters.reflection;

import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.core.Caching;
import com.thoughtworks.xstream.core.util.FastField;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SerializationMethodInvoker implements Caching {
    private static final Object[] EMPTY_ARGS = new Object[0];
    private static final Method NO_METHOD = new C16781().getClass().getDeclaredMethods()[0];
    private static final FastField[] OBJECT_TYPE_FIELDS = new FastField[]{new FastField(Object.class, "readResolve"), new FastField(Object.class, "writeReplace"), new FastField(Object.class, "readObject"), new FastField(Object.class, "writeObject")};
    private Map cache = Collections.synchronizedMap(new HashMap());

    /* renamed from: com.thoughtworks.xstream.converters.reflection.SerializationMethodInvoker$1 */
    static class C16781 {
        C16781() {
        }

        private void noMethod() {
        }
    }

    public SerializationMethodInvoker() {
        for (Object put : OBJECT_TYPE_FIELDS) {
            this.cache.put(put, NO_METHOD);
        }
    }

    public Object callReadResolve(Object result) {
        StringBuilder stringBuilder;
        if (result == null) {
            return null;
        }
        Method readResolveMethod = getMethod(result.getClass(), "readResolve", null, true);
        if (readResolveMethod == null) {
            return result;
        }
        try {
            return readResolveMethod.invoke(result, EMPTY_ARGS);
        } catch (IllegalAccessException e) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Could not call ");
            stringBuilder.append(result.getClass().getName());
            stringBuilder.append(".readResolve()");
            throw new ObjectAccessException(stringBuilder.toString(), e);
        } catch (InvocationTargetException e2) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Could not call ");
            stringBuilder.append(result.getClass().getName());
            stringBuilder.append(".readResolve()");
            throw new ObjectAccessException(stringBuilder.toString(), e2.getTargetException());
        }
    }

    public Object callWriteReplace(Object object) {
        StringBuilder stringBuilder;
        if (object == null) {
            return null;
        }
        Method writeReplaceMethod = getMethod(object.getClass(), "writeReplace", null, true);
        if (writeReplaceMethod == null) {
            return object;
        }
        try {
            return writeReplaceMethod.invoke(object, EMPTY_ARGS);
        } catch (IllegalAccessException e) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Could not call ");
            stringBuilder.append(object.getClass().getName());
            stringBuilder.append(".writeReplace()");
            throw new ObjectAccessException(stringBuilder.toString(), e);
        } catch (InvocationTargetException e2) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Could not call ");
            stringBuilder.append(object.getClass().getName());
            stringBuilder.append(".writeReplace()");
            throw new ObjectAccessException(stringBuilder.toString(), e2.getTargetException());
        }
    }

    public boolean supportsReadObject(Class type, boolean includeBaseClasses) {
        return getMethod(type, "readObject", new Class[]{ObjectInputStream.class}, includeBaseClasses) != null;
    }

    public void callReadObject(Class type, Object object, ObjectInputStream stream) {
        StringBuilder stringBuilder;
        try {
            getMethod(type, "readObject", new Class[]{ObjectInputStream.class}, false).invoke(object, new Object[]{stream});
        } catch (IllegalAccessException e) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Could not call ");
            stringBuilder.append(object.getClass().getName());
            stringBuilder.append(".readObject()");
            throw new ConversionException(stringBuilder.toString(), e);
        } catch (InvocationTargetException e2) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Could not call ");
            stringBuilder.append(object.getClass().getName());
            stringBuilder.append(".readObject()");
            throw new ConversionException(stringBuilder.toString(), e2.getTargetException());
        }
    }

    public boolean supportsWriteObject(Class type, boolean includeBaseClasses) {
        return getMethod(type, "writeObject", new Class[]{ObjectOutputStream.class}, includeBaseClasses) != null;
    }

    public void callWriteObject(Class type, Object instance, ObjectOutputStream stream) {
        StringBuilder stringBuilder;
        try {
            getMethod(type, "writeObject", new Class[]{ObjectOutputStream.class}, false).invoke(instance, new Object[]{stream});
        } catch (IllegalAccessException e) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Could not call ");
            stringBuilder.append(instance.getClass().getName());
            stringBuilder.append(".writeObject()");
            throw new ConversionException(stringBuilder.toString(), e);
        } catch (InvocationTargetException e2) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Could not call ");
            stringBuilder.append(instance.getClass().getName());
            stringBuilder.append(".writeObject()");
            throw new ConversionException(stringBuilder.toString(), e2.getTargetException());
        }
    }

    private Method getMethod(Class type, String name, Class[] parameterTypes, boolean includeBaseclasses) {
        Method method = getMethod(type, name, parameterTypes);
        if (method != NO_METHOD) {
            if (includeBaseclasses || method.getDeclaringClass().equals(type)) {
                return method;
            }
        }
        return null;
    }

    private Method getMethod(Class type, String name, Class[] parameterTypes) {
        if (type == null) {
            return null;
        }
        FastField method = new FastField(type, name);
        Method result = (Method) this.cache.get(method);
        if (result == null) {
            try {
                result = type.getDeclaredMethod(name, parameterTypes);
                if (!result.isAccessible()) {
                    result.setAccessible(true);
                }
            } catch (NoSuchMethodException e) {
                result = getMethod(type.getSuperclass(), name, parameterTypes);
            }
            this.cache.put(method, result);
        }
        return result;
    }

    public void flushCache() {
        this.cache.keySet().retainAll(Arrays.asList(OBJECT_TYPE_FIELDS));
    }
}
