package com.google.common.base;

import com.google.common.annotations.Beta;
import com.google.common.annotations.VisibleForTesting;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;

public final class Throwables {
    private static final String JAVA_LANG_ACCESS_CLASSNAME = "sun.misc.JavaLangAccess";
    @VisibleForTesting
    static final String SHARED_SECRETS_CLASSNAME = "sun.misc.SharedSecrets";
    @Nullable
    private static final Method getStackTraceDepthMethod;
    @Nullable
    private static final Method getStackTraceElementMethod = (jla == null ? null : getGetMethod());
    @Nullable
    private static final Object jla = getJLA();

    private Throwables() {
    }

    public static <X extends Throwable> void propagateIfInstanceOf(@Nullable Throwable throwable, Class<X> declaredType) throws Throwable {
        if (throwable != null && declaredType.isInstance(throwable)) {
            throw ((Throwable) declaredType.cast(throwable));
        }
    }

    public static void propagateIfPossible(@Nullable Throwable throwable) {
        propagateIfInstanceOf(throwable, Error.class);
        propagateIfInstanceOf(throwable, RuntimeException.class);
    }

    public static <X extends Throwable> void propagateIfPossible(@Nullable Throwable throwable, Class<X> declaredType) throws Throwable {
        propagateIfInstanceOf(throwable, declaredType);
        propagateIfPossible(throwable);
    }

    public static <X1 extends Throwable, X2 extends Throwable> void propagateIfPossible(@Nullable Throwable throwable, Class<X1> declaredType1, Class<X2> declaredType2) throws Throwable, Throwable {
        Preconditions.checkNotNull(declaredType2);
        propagateIfInstanceOf(throwable, declaredType1);
        propagateIfPossible(throwable, declaredType2);
    }

    public static RuntimeException propagate(Throwable throwable) {
        propagateIfPossible((Throwable) Preconditions.checkNotNull(throwable));
        throw new RuntimeException(throwable);
    }

    @CheckReturnValue
    public static Throwable getRootCause(Throwable throwable) {
        while (true) {
            Throwable cause = throwable.getCause();
            Throwable cause2 = cause;
            if (cause == null) {
                return throwable;
            }
            throwable = cause2;
        }
    }

    @CheckReturnValue
    @Beta
    public static List<Throwable> getCausalChain(Throwable throwable) {
        Preconditions.checkNotNull(throwable);
        List<Throwable> causes = new ArrayList(4);
        while (throwable != null) {
            causes.add(throwable);
            throwable = throwable.getCause();
        }
        return Collections.unmodifiableList(causes);
    }

    @CheckReturnValue
    public static String getStackTraceAsString(Throwable throwable) {
        StringWriter stringWriter = new StringWriter();
        throwable.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    @CheckReturnValue
    @Beta
    public static List<StackTraceElement> lazyStackTrace(Throwable throwable) {
        return lazyStackTraceIsLazy() ? jlaStackTrace(throwable) : Collections.unmodifiableList(Arrays.asList(throwable.getStackTrace()));
    }

    @CheckReturnValue
    @Beta
    public static boolean lazyStackTraceIsLazy() {
        int i = 0;
        int i2 = getStackTraceElementMethod != null ? 1 : 0;
        if (getStackTraceDepthMethod != null) {
            i = 1;
        }
        return i2 & i;
    }

    private static List<StackTraceElement> jlaStackTrace(Throwable t) {
        Preconditions.checkNotNull(t);
        return new Throwables$1(t);
    }

    private static Object invokeAccessibleNonThrowingMethod(Method method, Object receiver, Object... params) {
        try {
            return method.invoke(receiver, params);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e2) {
            throw propagate(e2.getCause());
        }
    }

    static {
        Method method = null;
        if (jla != null) {
            method = getSizeMethod();
        }
        getStackTraceDepthMethod = method;
    }

    @Nullable
    private static Object getJLA() {
        try {
            return Class.forName(SHARED_SECRETS_CLASSNAME, false, null).getMethod("getJavaLangAccess", new Class[0]).invoke(null, new Object[0]);
        } catch (ThreadDeath death) {
            throw death;
        } catch (Throwable th) {
            return null;
        }
    }

    @Nullable
    private static Method getGetMethod() {
        return getJlaMethod("getStackTraceElement", Throwable.class, Integer.TYPE);
    }

    @Nullable
    private static Method getSizeMethod() {
        return getJlaMethod("getStackTraceDepth", Throwable.class);
    }

    @Nullable
    private static Method getJlaMethod(String name, Class<?>... parameterTypes) throws ThreadDeath {
        try {
            return Class.forName(JAVA_LANG_ACCESS_CLASSNAME, false, null).getMethod(name, parameterTypes);
        } catch (ThreadDeath death) {
            throw death;
        } catch (Throwable th) {
            return null;
        }
    }
}
