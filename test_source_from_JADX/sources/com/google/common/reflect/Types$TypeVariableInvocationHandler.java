package com.google.common.reflect;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap$Builder;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessControlException;

final class Types$TypeVariableInvocationHandler implements InvocationHandler {
    private static final ImmutableMap<String, Method> typeVariableMethods;
    private final Types$TypeVariableImpl<?> typeVariableImpl;

    static {
        ImmutableMap$Builder<String, Method> builder = ImmutableMap.builder();
        for (Method method : Types$TypeVariableImpl.class.getMethods()) {
            if (method.getDeclaringClass().equals(Types$TypeVariableImpl.class)) {
                try {
                    method.setAccessible(true);
                } catch (AccessControlException e) {
                }
                builder.put(method.getName(), method);
            }
        }
        typeVariableMethods = builder.build();
    }

    Types$TypeVariableInvocationHandler(Types$TypeVariableImpl<?> typeVariableImpl) {
        this.typeVariableImpl = typeVariableImpl;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String methodName = method.getName();
        Method typeVariableMethod = (Method) typeVariableMethods.get(methodName);
        if (typeVariableMethod == null) {
            throw new UnsupportedOperationException(methodName);
        }
        try {
            return typeVariableMethod.invoke(this.typeVariableImpl, args);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }
}
