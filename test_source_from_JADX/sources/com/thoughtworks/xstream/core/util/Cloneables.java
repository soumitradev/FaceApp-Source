package com.thoughtworks.xstream.core.util;

import com.thoughtworks.xstream.converters.reflection.ObjectAccessException;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;

public class Cloneables {
    public static Object clone(Object o) {
        if (!(o instanceof Cloneable)) {
            return null;
        }
        if (o.getClass().isArray()) {
            Class componentType = o.getClass().getComponentType();
            if (!componentType.isPrimitive()) {
                return ((Object[]) o).clone();
            }
            int length = Array.getLength(o);
            Object clone = Array.newInstance(componentType, length);
            while (true) {
                int length2 = length - 1;
                if (length <= 0) {
                    return clone;
                }
                Array.set(clone, length2, Array.get(o, length2));
                length = length2;
            }
        } else {
            try {
                return o.getClass().getMethod("clone", (Class[]) null).invoke(o, (Object[]) null);
            } catch (NoSuchMethodException e) {
                throw new ObjectAccessException("Cloneable type has no clone method", e);
            } catch (IllegalAccessException e2) {
                throw new ObjectAccessException("Cannot clone Cloneable type", e2);
            } catch (InvocationTargetException e3) {
                throw new ObjectAccessException("Exception cloning Cloneable type", e3.getCause());
            }
        }
    }

    public static Object cloneIfPossible(Object o) {
        Object clone = clone(o);
        return clone == null ? o : clone;
    }
}
