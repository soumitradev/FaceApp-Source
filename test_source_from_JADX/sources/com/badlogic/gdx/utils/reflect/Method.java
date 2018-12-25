package com.badlogic.gdx.utils.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

public final class Method {
    private final java.lang.reflect.Method method;

    Method(java.lang.reflect.Method method) {
        this.method = method;
    }

    public String getName() {
        return this.method.getName();
    }

    public Class getReturnType() {
        return this.method.getReturnType();
    }

    public Class[] getParameterTypes() {
        return this.method.getParameterTypes();
    }

    public Class getDeclaringClass() {
        return this.method.getDeclaringClass();
    }

    public boolean isAccessible() {
        return this.method.isAccessible();
    }

    public void setAccessible(boolean accessible) {
        this.method.setAccessible(accessible);
    }

    public boolean isAbstract() {
        return Modifier.isAbstract(this.method.getModifiers());
    }

    public boolean isDefaultAccess() {
        return (isPrivate() || isProtected() || isPublic()) ? false : true;
    }

    public boolean isFinal() {
        return Modifier.isFinal(this.method.getModifiers());
    }

    public boolean isPrivate() {
        return Modifier.isPrivate(this.method.getModifiers());
    }

    public boolean isProtected() {
        return Modifier.isProtected(this.method.getModifiers());
    }

    public boolean isPublic() {
        return Modifier.isPublic(this.method.getModifiers());
    }

    public boolean isNative() {
        return Modifier.isNative(this.method.getModifiers());
    }

    public boolean isStatic() {
        return Modifier.isStatic(this.method.getModifiers());
    }

    public boolean isVarArgs() {
        return this.method.isVarArgs();
    }

    public Object invoke(Object obj, Object... args) throws ReflectionException {
        StringBuilder stringBuilder;
        try {
            return this.method.invoke(obj, args);
        } catch (IllegalArgumentException e) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Illegal argument(s) supplied to method: ");
            stringBuilder.append(getName());
            throw new ReflectionException(stringBuilder.toString(), e);
        } catch (IllegalAccessException e2) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Illegal access to method: ");
            stringBuilder.append(getName());
            throw new ReflectionException(stringBuilder.toString(), e2);
        } catch (InvocationTargetException e3) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Exception occurred in method: ");
            stringBuilder.append(getName());
            throw new ReflectionException(stringBuilder.toString(), e3);
        }
    }

    public boolean isAnnotationPresent(Class<? extends Annotation> annotationType) {
        return this.method.isAnnotationPresent(annotationType);
    }

    public Annotation[] getDeclaredAnnotations() {
        Annotation[] annotations = this.method.getDeclaredAnnotations();
        Annotation[] result = new Annotation[annotations.length];
        for (int i = 0; i < annotations.length; i++) {
            result[i] = new Annotation(annotations[i]);
        }
        return result;
    }

    public Annotation getDeclaredAnnotation(Class<? extends Annotation> annotationType) {
        Annotation[] annotations = this.method.getDeclaredAnnotations();
        if (annotations == null) {
            return null;
        }
        for (Annotation annotation : annotations) {
            if (annotation.annotationType().equals(annotationType)) {
                return new Annotation(annotation);
            }
        }
        return null;
    }
}
