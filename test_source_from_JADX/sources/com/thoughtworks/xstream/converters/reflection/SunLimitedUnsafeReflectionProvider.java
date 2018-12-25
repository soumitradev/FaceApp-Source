package com.thoughtworks.xstream.converters.reflection;

import java.lang.reflect.Field;
import sun.misc.Unsafe;

public class SunLimitedUnsafeReflectionProvider extends PureJavaReflectionProvider {
    protected static final Exception exception;
    protected static final Unsafe unsafe;

    static {
        Unsafe u = null;
        Exception ex = null;
        try {
            Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
            unsafeField.setAccessible(true);
            u = (Unsafe) unsafeField.get(null);
        } catch (Exception e) {
            ex = e;
        } catch (Exception e2) {
            ex = e2;
        } catch (Exception e22) {
            ex = e22;
        } catch (Exception e222) {
            ex = e222;
        }
        exception = ex;
        unsafe = u;
    }

    public SunLimitedUnsafeReflectionProvider(FieldDictionary fieldDictionary) {
        super(fieldDictionary);
    }

    public Object newInstance(Class type) {
        StringBuilder stringBuilder;
        if (exception != null) {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("Cannot construct ");
            stringBuilder2.append(type.getName());
            throw new ObjectAccessException(stringBuilder2.toString(), exception);
        }
        try {
            return unsafe.allocateInstance(type);
        } catch (SecurityException e) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Cannot construct ");
            stringBuilder.append(type.getName());
            throw new ObjectAccessException(stringBuilder.toString(), e);
        } catch (InstantiationException e2) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Cannot construct ");
            stringBuilder.append(type.getName());
            throw new ObjectAccessException(stringBuilder.toString(), e2);
        } catch (IllegalArgumentException e3) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Cannot construct ");
            stringBuilder.append(type.getName());
            throw new ObjectAccessException(stringBuilder.toString(), e3);
        }
    }

    protected void validateFieldAccess(Field field) {
    }

    private Object readResolve() {
        init();
        return this;
    }
}
