package com.thoughtworks.xstream.converters.reflection;

import com.google.firebase.analytics.FirebaseAnalytics$Param;
import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.reflection.ReflectionProvider.Visitor;
import com.thoughtworks.xstream.core.ClassLoaderReference;
import com.thoughtworks.xstream.io.ExtendedHierarchicalStreamWriterHelper;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.CGLIBMapper.Marker;
import com.thoughtworks.xstream.mapper.Mapper;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.Factory;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.NoOp;
import org.catrobat.catroid.common.BrickValues;

public class CGLIBEnhancedConverter extends SerializableConverter {
    private static String CALLBACK_MARKER = "CGLIB$CALLBACK_";
    private static String DEFAULT_NAMING_MARKER = "$$EnhancerByCGLIB$$";
    private transient Map fieldCache;

    private static class ReverseEngineeredCallbackFilter implements CallbackFilter {
        private final Map callbackIndexMap;

        public ReverseEngineeredCallbackFilter(Map callbackIndexMap) {
            this.callbackIndexMap = callbackIndexMap;
        }

        public int accept(Method method) {
            if (this.callbackIndexMap.containsKey(method)) {
                return ((Integer) this.callbackIndexMap.get(method)).intValue();
            }
            ConversionException exception = new ConversionException("CGLIB callback not detected in reverse engineering");
            exception.add("CGLIB callback", method.toString());
            throw exception;
        }
    }

    private static final class ReverseEngineeringInvocationHandler implements InvocationHandler {
        private final Integer index;
        private final Map indexMap;

        public ReverseEngineeringInvocationHandler(int index, Map indexMap) {
            this.indexMap = indexMap;
            this.index = new Integer(index);
        }

        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            this.indexMap.put(this.indexMap.get(null), this.index);
            return null;
        }
    }

    private static class CGLIBFilteringReflectionProvider extends ReflectionProviderWrapper {
        public CGLIBFilteringReflectionProvider(ReflectionProvider reflectionProvider) {
            super(reflectionProvider);
        }

        public void visitSerializableFields(Object object, final Visitor visitor) {
            this.wrapped.visitSerializableFields(object, new Visitor() {
                public void visit(String name, Class type, Class definedIn, Object value) {
                    if (!name.startsWith("CGLIB$")) {
                        visitor.visit(name, type, definedIn, value);
                    }
                }
            });
        }
    }

    public CGLIBEnhancedConverter(Mapper mapper, ReflectionProvider reflectionProvider, ClassLoaderReference classLoaderReference) {
        super(mapper, new CGLIBFilteringReflectionProvider(reflectionProvider), classLoaderReference);
        this.fieldCache = new HashMap();
    }

    public CGLIBEnhancedConverter(Mapper mapper, ReflectionProvider reflectionProvider, ClassLoader classLoader) {
        super(mapper, new CGLIBFilteringReflectionProvider(reflectionProvider), classLoader);
        this.fieldCache = new HashMap();
    }

    public CGLIBEnhancedConverter(Mapper mapper, ReflectionProvider reflectionProvider) {
        this(mapper, new CGLIBFilteringReflectionProvider(reflectionProvider), CGLIBEnhancedConverter.class.getClassLoader());
    }

    public boolean canConvert(Class type) {
        return (Enhancer.isEnhanced(type) && type.getName().indexOf(DEFAULT_NAMING_MARKER) > 0) || type == Marker.class;
    }

    public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
        Class type = source.getClass();
        boolean hasFactory = Factory.class.isAssignableFrom(type);
        ExtendedHierarchicalStreamWriterHelper.startNode(writer, "type", type);
        context.convertAnother(type.getSuperclass());
        writer.endNode();
        writer.startNode("interfaces");
        Class[] interfaces = type.getInterfaces();
        for (int i = 0; i < interfaces.length; i++) {
            if (interfaces[i] != Factory.class) {
                ExtendedHierarchicalStreamWriterHelper.startNode(writer, this.mapper.serializedClass(interfaces[i].getClass()), interfaces[i].getClass());
                context.convertAnother(interfaces[i]);
                writer.endNode();
            }
        }
        writer.endNode();
        writer.startNode("hasFactory");
        writer.setValue(String.valueOf(hasFactory));
        writer.endNode();
        Callback[] callbacks = hasFactory ? ((Factory) source).getCallbacks() : getCallbacks(source);
        if (callbacks.length > 1) {
            if (hasFactory) {
                Map callbackIndexMap = createCallbackIndexMap((Factory) source);
                writer.startNode("callbacks");
                writer.startNode("mapping");
                context.convertAnother(callbackIndexMap);
                writer.endNode();
            } else {
                ConversionException exception = new ConversionException("Cannot handle CGLIB enhanced proxies without factory that have multiple callbacks");
                exception.add("proxy superclass", type.getSuperclass().getName());
                exception.add("number of callbacks", String.valueOf(callbacks.length));
                throw exception;
            }
        }
        boolean hasInterceptor = false;
        for (Callback callback : callbacks) {
            if (callback == null) {
                writer.startNode(this.mapper.serializedClass(null));
                writer.endNode();
            } else {
                boolean z;
                if (!hasInterceptor) {
                    if (!MethodInterceptor.class.isAssignableFrom(callback.getClass())) {
                        z = false;
                        hasInterceptor = z;
                        ExtendedHierarchicalStreamWriterHelper.startNode(writer, this.mapper.serializedClass(callback.getClass()), callback.getClass());
                        context.convertAnother(callback);
                        writer.endNode();
                    }
                }
                z = true;
                hasInterceptor = z;
                ExtendedHierarchicalStreamWriterHelper.startNode(writer, this.mapper.serializedClass(callback.getClass()), callback.getClass());
                context.convertAnother(callback);
                writer.endNode();
            }
        }
        if (callbacks.length > 1) {
            writer.endNode();
        }
        try {
            Field field = type.getDeclaredField("serialVersionUID");
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            long serialVersionUID = field.getLong(null);
            ExtendedHierarchicalStreamWriterHelper.startNode(writer, "serialVersionUID", String.class);
            writer.setValue(String.valueOf(serialVersionUID));
            writer.endNode();
        } catch (NoSuchFieldException e) {
        } catch (IllegalAccessException e2) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Access to serialVersionUID of ");
            stringBuilder.append(type.getName());
            stringBuilder.append(" not allowed");
            throw new ObjectAccessException(stringBuilder.toString(), e2);
        }
        if (hasInterceptor) {
            writer.startNode("instance");
            super.doMarshalConditionally(source, writer, context);
            writer.endNode();
        }
    }

    private Callback[] getCallbacks(Object source) {
        Class type = source.getClass();
        List fields = (List) this.fieldCache.get(type.getName());
        int i = 0;
        if (fields == null) {
            fields = new ArrayList();
            this.fieldCache.put(type.getName(), fields);
            int i2 = 0;
            while (true) {
                try {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(CALLBACK_MARKER);
                    stringBuilder.append(i2);
                    Field field = type.getDeclaredField(stringBuilder.toString());
                    if (!field.isAccessible()) {
                        field.setAccessible(true);
                    }
                    fields.add(field);
                    i2++;
                } catch (NoSuchFieldException e) {
                }
            }
        }
        List list = new ArrayList();
        while (i < fields.size()) {
            try {
                list.add(((Field) fields.get(i)).get(source));
                i++;
            } catch (IllegalAccessException e2) {
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("Access to ");
                stringBuilder2.append(type.getName());
                stringBuilder2.append(".");
                stringBuilder2.append(CALLBACK_MARKER);
                stringBuilder2.append(i);
                stringBuilder2.append(" not allowed");
                throw new ObjectAccessException(stringBuilder2.toString(), e2);
            }
        }
        return (Callback[]) list.toArray(new Callback[list.size()]);
    }

    private Map createCallbackIndexMap(Factory source) {
        CGLIBEnhancedConverter cGLIBEnhancedConverter = this;
        Factory factory = source;
        Callback[] originalCallbacks = source.getCallbacks();
        Callback[] reverseEngineeringCallbacks = new Callback[originalCallbacks.length];
        Map callbackIndexMap = new HashMap();
        int idxNoOp = -1;
        for (int i = 0; i < originalCallbacks.length; i++) {
            Callback callback = originalCallbacks[i];
            if (callback == null) {
                reverseEngineeringCallbacks[i] = null;
            } else if (NoOp.class.isAssignableFrom(callback.getClass())) {
                reverseEngineeringCallbacks[i] = NoOp.INSTANCE;
                idxNoOp = i;
            } else {
                reverseEngineeringCallbacks[i] = createReverseEngineeredCallbackOfProperType(callback, i, callbackIndexMap);
            }
        }
        Throwable th;
        Callback[] callbackArr;
        try {
            Method method;
            Method calledMethod;
            IllegalAccessException e;
            factory.setCallbacks(reverseEngineeringCallbacks);
            Set<Class> interfaces = new HashSet();
            Set<Object> methods = new HashSet();
            Class type = source.getClass();
            while (true) {
                methods.addAll(Arrays.asList(type.getDeclaredMethods()));
                methods.addAll(Arrays.asList(type.getMethods()));
                interfaces.addAll(Arrays.asList(type.getInterfaces()));
                type = type.getSuperclass();
                if (type == null) {
                    break;
                }
                cGLIBEnhancedConverter = this;
            }
            for (Class type2 : interfaces) {
                try {
                    methods.addAll(Arrays.asList(type2.getDeclaredMethods()));
                } catch (Throwable th2) {
                    th = th2;
                    callbackArr = reverseEngineeringCallbacks;
                }
            }
            Iterator iterator = methods.iterator();
            while (iterator.hasNext()) {
                method = (Method) iterator.next();
                if (!method.isAccessible()) {
                    method.setAccessible(true);
                }
                if (Factory.class.isAssignableFrom(method.getDeclaringClass())) {
                    callbackArr = reverseEngineeringCallbacks;
                } else if ((method.getModifiers() & 24) > 0) {
                    callbackArr = reverseEngineeringCallbacks;
                } else {
                    Class[] parameterTypes = method.getParameterTypes();
                    calledMethod = method;
                    try {
                        if ((method.getModifiers() & 1024) > 0) {
                            callbackArr = reverseEngineeringCallbacks;
                            try {
                                calledMethod = source.getClass().getMethod(method.getName(), method.getParameterTypes());
                            } catch (IllegalAccessException e2) {
                                e = e2;
                            } catch (InvocationTargetException e3) {
                            } catch (NoSuchMethodException e4) {
                                NoSuchMethodException noSuchMethodException = e4;
                            }
                        } else {
                            callbackArr = reverseEngineeringCallbacks;
                        }
                        callbackIndexMap.put(null, method);
                        calledMethod.invoke(factory, parameterTypes == null ? (Object[]) null : cGLIBEnhancedConverter.createNullArguments(parameterTypes));
                    } catch (IllegalAccessException e22) {
                        callbackArr = reverseEngineeringCallbacks;
                        e = e22;
                    } catch (InvocationTargetException e5) {
                        callbackArr = reverseEngineeringCallbacks;
                    } catch (NoSuchMethodException e42) {
                        callbackArr = reverseEngineeringCallbacks;
                        reverseEngineeringCallbacks = e42;
                    } catch (Throwable th22) {
                        th = th22;
                    }
                    if (callbackIndexMap.containsKey(method)) {
                        iterator.remove();
                    }
                    reverseEngineeringCallbacks = callbackArr;
                    cGLIBEnhancedConverter = this;
                }
                iterator.remove();
                reverseEngineeringCallbacks = callbackArr;
                cGLIBEnhancedConverter = this;
            }
            if (idxNoOp >= 0) {
                Integer idx = new Integer(idxNoOp);
                for (Object put : methods) {
                    callbackIndexMap.put(put, idx);
                }
            }
            factory.setCallbacks(originalCallbacks);
            callbackIndexMap.remove(null);
            return callbackIndexMap;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Access to ");
            stringBuilder.append(calledMethod);
            stringBuilder.append(" not allowed");
            throw new ObjectAccessException(stringBuilder.toString(), e);
            ConversionException exception = new ConversionException("CGLIB enhanced proxies wit abstract nethod that has not been implemented");
            exception.add("proxy superclass", type2.getSuperclass().getName());
            exception.add(FirebaseAnalytics$Param.METHOD, method.toString());
            throw exception;
            factory.setCallbacks(originalCallbacks);
            throw th;
        } catch (Throwable th222) {
            callbackArr = reverseEngineeringCallbacks;
            th = th222;
        }
    }

    private Object[] createNullArguments(Class[] parameterTypes) {
        Object[] arguments = new Object[parameterTypes.length];
        for (int i = 0; i < arguments.length; i++) {
            Class type = parameterTypes[i];
            if (type.isPrimitive()) {
                if (type == Byte.TYPE) {
                    arguments[i] = new Byte((byte) 0);
                } else if (type == Short.TYPE) {
                    arguments[i] = new Short((short) 0);
                } else if (type == Integer.TYPE) {
                    arguments[i] = new Integer(0);
                } else if (type == Long.TYPE) {
                    arguments[i] = new Long(0);
                } else if (type == Float.TYPE) {
                    arguments[i] = new Float(0.0f);
                } else if (type == Double.TYPE) {
                    arguments[i] = new Double(BrickValues.SET_COLOR_TO);
                } else if (type == Character.TYPE) {
                    arguments[i] = new Character('\u0000');
                } else {
                    arguments[i] = Boolean.FALSE;
                }
            }
        }
        return arguments;
    }

    private Callback createReverseEngineeredCallbackOfProperType(Callback callback, int index, Map callbackIndexMap) {
        Class[] interfaces = callback.getClass().getInterfaces();
        Class iface = null;
        int i = 0;
        while (i < interfaces.length) {
            if (Callback.class.isAssignableFrom(interfaces[i])) {
                iface = interfaces[i];
                if (iface == Callback.class) {
                    ConversionException exception = new ConversionException("Cannot handle CGLIB callback");
                    exception.add("CGLIB callback type", callback.getClass().getName());
                    throw exception;
                }
                interfaces = iface.getInterfaces();
                if (Arrays.asList(interfaces).contains(Callback.class)) {
                    break;
                }
                i = -1;
            }
            i++;
        }
        return (Callback) Proxy.newProxyInstance(iface.getClassLoader(), new Class[]{iface}, new ReverseEngineeringInvocationHandler(index, callbackIndexMap));
    }

    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        Enhancer enhancer = new Enhancer();
        reader.moveDown();
        Object result = null;
        enhancer.setSuperclass((Class) context.convertAnother(null, Class.class));
        reader.moveUp();
        reader.moveDown();
        List interfaces = new ArrayList();
        while (reader.hasMoreChildren()) {
            reader.moveDown();
            interfaces.add(context.convertAnother(null, this.mapper.realClass(reader.getNodeName())));
            reader.moveUp();
        }
        enhancer.setInterfaces((Class[]) interfaces.toArray(new Class[interfaces.size()]));
        reader.moveUp();
        reader.moveDown();
        boolean useFactory = Boolean.valueOf(reader.getValue()).booleanValue();
        enhancer.setUseFactory(useFactory);
        reader.moveUp();
        List callbacksToEnhance = new ArrayList();
        List callbacks = new ArrayList();
        Map callbackIndexMap = null;
        reader.moveDown();
        if ("callbacks".equals(reader.getNodeName())) {
            reader.moveDown();
            callbackIndexMap = (Map) context.convertAnother(null, HashMap.class);
            reader.moveUp();
            while (reader.hasMoreChildren()) {
                reader.moveDown();
                readCallback(reader, context, callbacksToEnhance, callbacks);
                reader.moveUp();
            }
        } else {
            readCallback(reader, context, callbacksToEnhance, callbacks);
        }
        enhancer.setCallbacks((Callback[]) callbacksToEnhance.toArray(new Callback[callbacksToEnhance.size()]));
        if (callbackIndexMap != null) {
            enhancer.setCallbackFilter(new ReverseEngineeredCallbackFilter(callbackIndexMap));
        }
        reader.moveUp();
        while (reader.hasMoreChildren()) {
            reader.moveDown();
            if (reader.getNodeName().equals("serialVersionUID")) {
                enhancer.setSerialVersionUID(Long.valueOf(reader.getValue()));
            } else if (reader.getNodeName().equals("instance")) {
                result = create(enhancer, callbacks, useFactory);
                super.doUnmarshalConditionally(result, reader, context);
            }
            reader.moveUp();
        }
        if (result == null) {
            result = create(enhancer, callbacks, useFactory);
        }
        return this.serializationMethodInvoker.callReadResolve(result);
    }

    private void readCallback(HierarchicalStreamReader reader, UnmarshallingContext context, List callbacksToEnhance, List callbacks) {
        Callback callback = (Callback) context.convertAnother(null, this.mapper.realClass(reader.getNodeName()));
        callbacks.add(callback);
        if (callback == null) {
            callbacksToEnhance.add(NoOp.INSTANCE);
        } else {
            callbacksToEnhance.add(callback);
        }
    }

    private Object create(Enhancer enhancer, List callbacks, boolean useFactory) {
        Object result = enhancer.create();
        if (useFactory) {
            ((Factory) result).setCallbacks((Callback[]) callbacks.toArray(new Callback[callbacks.size()]));
        }
        return result;
    }

    protected List hierarchyFor(Class type) {
        List typeHierarchy = super.hierarchyFor(type);
        typeHierarchy.remove(typeHierarchy.size() - 1);
        return typeHierarchy;
    }

    private Object readResolve() {
        this.fieldCache = new HashMap();
        return this;
    }
}
