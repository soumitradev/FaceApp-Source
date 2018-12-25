package com.thoughtworks.xstream.mapper;

import com.thoughtworks.xstream.InitializationException;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAliasType;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.annotations.XStreamConverters;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.annotations.XStreamImplicitCollection;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.ConverterLookup;
import com.thoughtworks.xstream.converters.ConverterMatcher;
import com.thoughtworks.xstream.converters.ConverterRegistry;
import com.thoughtworks.xstream.converters.SingleValueConverter;
import com.thoughtworks.xstream.converters.SingleValueConverterWrapper;
import com.thoughtworks.xstream.converters.reflection.ReflectionProvider;
import com.thoughtworks.xstream.core.ClassLoaderReference;
import com.thoughtworks.xstream.core.JVM;
import com.thoughtworks.xstream.core.util.DependencyInjectionFactory;
import com.thoughtworks.xstream.core.util.TypedNull;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AnnotationMapper extends MapperWrapper implements AnnotationConfiguration {
    private final Set<Class<?>> annotatedTypes;
    private transient Object[] arguments;
    private transient AttributeMapper attributeMapper;
    private transient ClassAliasingMapper classAliasingMapper;
    private final Map<Class<?>, Map<List<Object>, Converter>> converterCache;
    private final ConverterRegistry converterRegistry;
    private transient DefaultImplementationsMapper defaultImplementationsMapper;
    private transient FieldAliasingMapper fieldAliasingMapper;
    private transient ImplicitCollectionMapper implicitCollectionMapper;
    private transient LocalConversionMapper localConversionMapper;
    private boolean locked;

    public AnnotationMapper(Mapper wrapped, ConverterRegistry converterRegistry, ConverterLookup converterLookup, ClassLoaderReference classLoaderReference, ReflectionProvider reflectionProvider) {
        super(wrapped);
        this.converterCache = new HashMap();
        this.annotatedTypes = Collections.synchronizedSet(new HashSet());
        this.converterRegistry = converterRegistry;
        this.annotatedTypes.add(Object.class);
        setupMappers();
        this.locked = true;
        ClassLoader classLoader = classLoaderReference.getReference();
        Object[] objArr = new Object[6];
        objArr[0] = this;
        objArr[1] = classLoaderReference;
        objArr[2] = reflectionProvider;
        objArr[3] = converterLookup;
        objArr[4] = new JVM();
        objArr[5] = classLoader != null ? classLoader : new TypedNull(ClassLoader.class);
        this.arguments = objArr;
    }

    public AnnotationMapper(Mapper wrapped, ConverterRegistry converterRegistry, ConverterLookup converterLookup, ClassLoader classLoader, ReflectionProvider reflectionProvider, JVM jvm) {
        this(wrapped, converterRegistry, converterLookup, new ClassLoaderReference(classLoader), reflectionProvider);
    }

    public String realMember(Class type, String serialized) {
        if (!this.locked) {
            processAnnotations(type);
        }
        return super.realMember(type, serialized);
    }

    public String serializedClass(Class type) {
        if (!this.locked) {
            processAnnotations(type);
        }
        return super.serializedClass(type);
    }

    public Class defaultImplementationOf(Class type) {
        if (!this.locked) {
            processAnnotations(type);
        }
        Class defaultImplementation = super.defaultImplementationOf(type);
        if (!this.locked) {
            processAnnotations(defaultImplementation);
        }
        return defaultImplementation;
    }

    public Converter getLocalConverter(Class definedIn, String fieldName) {
        if (!this.locked) {
            processAnnotations(definedIn);
        }
        return super.getLocalConverter(definedIn, fieldName);
    }

    public void autodetectAnnotations(boolean mode) {
        this.locked = mode ^ 1;
    }

    public void processAnnotations(Class[] initialTypes) {
        if (initialTypes != null) {
            if (initialTypes.length != 0) {
                this.locked = true;
                Set<Class<?>> types = new AnnotationMapper$UnprocessedTypesSet(this, null);
                for (Class initialType : initialTypes) {
                    types.add(initialType);
                }
                processTypes(types);
            }
        }
    }

    private void processAnnotations(Class initialType) {
        if (initialType != null) {
            Set<Class<?>> types = new AnnotationMapper$UnprocessedTypesSet(this, null);
            types.add(initialType);
            processTypes(types);
        }
    }

    private void processTypes(Set<Class<?>> types) {
        while (!types.isEmpty()) {
            Iterator<Class<?>> iter = types.iterator();
            Class<?> type = (Class) iter.next();
            iter.remove();
            synchronized (type) {
                if (this.annotatedTypes.contains(type)) {
                } else {
                    if (!type.isPrimitive()) {
                        addParametrizedTypes(type, types);
                        processConverterAnnotations(type);
                        processAliasAnnotation(type, types);
                        processAliasTypeAnnotation(type);
                        if (type.isInterface()) {
                            this.annotatedTypes.add(type);
                        } else {
                            try {
                                processImplicitCollectionAnnotation(type);
                                Field[] fields = type.getDeclaredFields();
                                for (Field field : fields) {
                                    if (!field.isEnumConstant()) {
                                        if ((field.getModifiers() & 136) <= 0) {
                                            addParametrizedTypes(field.getGenericType(), types);
                                            if (!field.isSynthetic()) {
                                                processFieldAliasAnnotation(field);
                                                processAsAttributeAnnotation(field);
                                                processImplicitAnnotation(field);
                                                processOmitFieldAnnotation(field);
                                                processLocalConverterAnnotation(field);
                                            }
                                        }
                                    }
                                }
                                this.annotatedTypes.add(type);
                            } finally {
                                this.annotatedTypes.add(type);
                            }
                        }
                    }
                }
            }
        }
    }

    private void addParametrizedTypes(Type type, Set<Class<?>> types) {
        Set<Type> processedTypes = new HashSet();
        Set<Type> localTypes = new AnnotationMapper$1(this, types, processedTypes);
        while (type != null) {
            processedTypes.add(type);
            int i$ = 0;
            int len$;
            Type[] arr$;
            if (type instanceof Class) {
                Class<?> clazz = (Class) type;
                types.add(clazz);
                if (!clazz.isPrimitive()) {
                    for (TypeVariable<?> typeVariable : clazz.getTypeParameters()) {
                        localTypes.add(typeVariable);
                    }
                    localTypes.add(clazz.getGenericSuperclass());
                    arr$ = clazz.getGenericInterfaces();
                    len$ = arr$.length;
                    while (i$ < len$) {
                        localTypes.add(arr$[i$]);
                        i$++;
                    }
                }
            } else if (type instanceof TypeVariable) {
                arr$ = ((TypeVariable) type).getBounds();
                len$ = arr$.length;
                while (i$ < len$) {
                    localTypes.add(arr$[i$]);
                    i$++;
                }
            } else if (type instanceof ParameterizedType) {
                ParameterizedType parametrizedType = (ParameterizedType) type;
                localTypes.add(parametrizedType.getRawType());
                arr$ = parametrizedType.getActualTypeArguments();
                len$ = arr$.length;
                while (i$ < len$) {
                    localTypes.add(arr$[i$]);
                    i$++;
                }
            } else if (type instanceof GenericArrayType) {
                localTypes.add(((GenericArrayType) type).getGenericComponentType());
            }
            if (localTypes.isEmpty()) {
                type = null;
            } else {
                Iterator<Type> iter = localTypes.iterator();
                type = (Type) iter.next();
                iter.remove();
            }
        }
    }

    private void processConverterAnnotations(Class<?> type) {
        if (this.converterRegistry != null) {
            XStreamConverters convertersAnnotation = (XStreamConverters) type.getAnnotation(XStreamConverters.class);
            XStreamConverter converterAnnotation = (XStreamConverter) type.getAnnotation(XStreamConverter.class);
            List<XStreamConverter> annotations = convertersAnnotation != null ? new ArrayList(Arrays.asList(convertersAnnotation.value())) : new ArrayList();
            if (converterAnnotation != null) {
                annotations.add(converterAnnotation);
            }
            for (XStreamConverter annotation : annotations) {
                Converter converter = cacheConverter(annotation, converterAnnotation != null ? type : null);
                if (converter != null) {
                    if (converterAnnotation == null) {
                        if (!converter.canConvert(type)) {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("Converter ");
                            stringBuilder.append(annotation.value().getName());
                            stringBuilder.append(" cannot handle annotated class ");
                            stringBuilder.append(type.getName());
                            throw new InitializationException(stringBuilder.toString());
                        }
                    }
                    this.converterRegistry.registerConverter(converter, annotation.priority());
                }
            }
        }
    }

    private void processAliasAnnotation(Class<?> type, Set<Class<?>> types) {
        XStreamAlias aliasAnnotation = (XStreamAlias) type.getAnnotation(XStreamAlias.class);
        if (aliasAnnotation == null) {
            return;
        }
        if (this.classAliasingMapper == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("No ");
            stringBuilder.append(ClassAliasingMapper.class.getName());
            stringBuilder.append(" available");
            throw new InitializationException(stringBuilder.toString());
        }
        this.classAliasingMapper.addClassAlias(aliasAnnotation.value(), type);
        if (aliasAnnotation.impl() != Void.class) {
            this.defaultImplementationsMapper.addDefaultImplementation(aliasAnnotation.impl(), type);
            if (type.isInterface()) {
                types.add(aliasAnnotation.impl());
            }
        }
    }

    private void processAliasTypeAnnotation(Class<?> type) {
        XStreamAliasType aliasAnnotation = (XStreamAliasType) type.getAnnotation(XStreamAliasType.class);
        if (aliasAnnotation == null) {
            return;
        }
        if (this.classAliasingMapper == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("No ");
            stringBuilder.append(ClassAliasingMapper.class.getName());
            stringBuilder.append(" available");
            throw new InitializationException(stringBuilder.toString());
        }
        this.classAliasingMapper.addTypeAlias(aliasAnnotation.value(), type);
    }

    @Deprecated
    private void processImplicitCollectionAnnotation(Class<?> type) {
        XStreamImplicitCollection implicitColAnnotation = (XStreamImplicitCollection) type.getAnnotation(XStreamImplicitCollection.class);
        if (implicitColAnnotation == null) {
            return;
        }
        if (this.implicitCollectionMapper == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("No ");
            stringBuilder.append(ImplicitCollectionMapper.class.getName());
            stringBuilder.append(" available");
            throw new InitializationException(stringBuilder.toString());
        }
        String fieldName = implicitColAnnotation.value();
        String itemFieldName = implicitColAnnotation.item();
        try {
            Class itemType = null;
            Type genericType = type.getDeclaredField(fieldName).getGenericType();
            if (genericType instanceof ParameterizedType) {
                itemType = getClass(((ParameterizedType) genericType).getActualTypeArguments()[0]);
            }
            if (itemType == null) {
                this.implicitCollectionMapper.add(type, fieldName, null, Object.class);
            } else if (itemFieldName.equals("")) {
                this.implicitCollectionMapper.add(type, fieldName, null, itemType);
            } else {
                this.implicitCollectionMapper.add(type, fieldName, itemFieldName, itemType);
            }
        } catch (NoSuchFieldException e) {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append(type.getName());
            stringBuilder2.append(" does not have a field named '");
            stringBuilder2.append(fieldName);
            stringBuilder2.append("' as required by ");
            stringBuilder2.append(XStreamImplicitCollection.class.getName());
            throw new InitializationException(stringBuilder2.toString());
        }
    }

    private void processFieldAliasAnnotation(Field field) {
        XStreamAlias aliasAnnotation = (XStreamAlias) field.getAnnotation(XStreamAlias.class);
        if (aliasAnnotation == null) {
            return;
        }
        if (this.fieldAliasingMapper == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("No ");
            stringBuilder.append(FieldAliasingMapper.class.getName());
            stringBuilder.append(" available");
            throw new InitializationException(stringBuilder.toString());
        }
        this.fieldAliasingMapper.addFieldAlias(aliasAnnotation.value(), field.getDeclaringClass(), field.getName());
    }

    private void processAsAttributeAnnotation(Field field) {
        if (((XStreamAsAttribute) field.getAnnotation(XStreamAsAttribute.class)) == null) {
            return;
        }
        if (this.attributeMapper == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("No ");
            stringBuilder.append(AttributeMapper.class.getName());
            stringBuilder.append(" available");
            throw new InitializationException(stringBuilder.toString());
        }
        this.attributeMapper.addAttributeFor(field);
    }

    private void processImplicitAnnotation(Field field) {
        XStreamImplicit implicitAnnotation = (XStreamImplicit) field.getAnnotation(XStreamImplicit.class);
        if (implicitAnnotation == null) {
            return;
        }
        if (this.implicitCollectionMapper == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("No ");
            stringBuilder.append(ImplicitCollectionMapper.class.getName());
            stringBuilder.append(" available");
            throw new InitializationException(stringBuilder.toString());
        }
        String fieldName = field.getName();
        String itemFieldName = implicitAnnotation.itemFieldName();
        String keyFieldName = implicitAnnotation.keyFieldName();
        boolean isMap = Map.class.isAssignableFrom(field.getType());
        Class itemType = null;
        if (!field.getType().isArray()) {
            Type genericType = field.getGenericType();
            if (genericType instanceof ParameterizedType) {
                itemType = getClass(((ParameterizedType) genericType).getActualTypeArguments()[isMap]);
            }
        }
        Class itemType2 = itemType;
        if (isMap) {
            ImplicitCollectionMapper implicitCollectionMapper = this.implicitCollectionMapper;
            Class declaringClass = field.getDeclaringClass();
            String str = (itemFieldName == null || "".equals(itemFieldName)) ? null : itemFieldName;
            String str2 = (keyFieldName == null || "".equals(keyFieldName)) ? null : keyFieldName;
            implicitCollectionMapper.add(declaringClass, fieldName, str, itemType2, str2);
        } else if (itemFieldName == null || "".equals(itemFieldName)) {
            this.implicitCollectionMapper.add(field.getDeclaringClass(), fieldName, itemType2);
        } else {
            this.implicitCollectionMapper.add(field.getDeclaringClass(), fieldName, itemFieldName, itemType2);
        }
    }

    private void processOmitFieldAnnotation(Field field) {
        if (((XStreamOmitField) field.getAnnotation(XStreamOmitField.class)) == null) {
            return;
        }
        if (this.fieldAliasingMapper == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("No ");
            stringBuilder.append(FieldAliasingMapper.class.getName());
            stringBuilder.append(" available");
            throw new InitializationException(stringBuilder.toString());
        }
        this.fieldAliasingMapper.omitField(field.getDeclaringClass(), field.getName());
    }

    private void processLocalConverterAnnotation(Field field) {
        XStreamConverter annotation = (XStreamConverter) field.getAnnotation(XStreamConverter.class);
        if (annotation != null) {
            Converter converter = cacheConverter(annotation, field.getType());
            if (converter == null) {
                return;
            }
            if (this.localConversionMapper == null) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("No ");
                stringBuilder.append(LocalConversionMapper.class.getName());
                stringBuilder.append(" available");
                throw new InitializationException(stringBuilder.toString());
            }
            this.localConversionMapper.registerLocalConverter(field.getDeclaringClass(), field.getName(), converter);
        }
    }

    private Converter cacheConverter(XStreamConverter annotation, Class targetType) {
        Converter result = null;
        List<Object> parameter = new ArrayList();
        if (targetType != null && annotation.useImplicitType()) {
            parameter.add(targetType);
        }
        List<Object> arrays = new ArrayList();
        arrays.add(annotation.booleans());
        arrays.add(annotation.bytes());
        arrays.add(annotation.chars());
        arrays.add(annotation.doubles());
        arrays.add(annotation.floats());
        arrays.add(annotation.ints());
        arrays.add(annotation.longs());
        arrays.add(annotation.shorts());
        arrays.add(annotation.strings());
        arrays.add(annotation.types());
        Iterator i$ = arrays.iterator();
        while (true) {
            int i = 0;
            if (!i$.hasNext()) {
                break;
            }
            int length;
            Object array = i$.next();
            if (array != null) {
                length = Array.getLength(array);
                while (i < length) {
                    Object object = Array.get(array, i);
                    if (!parameter.contains(object)) {
                        parameter.add(object);
                    }
                    i++;
                }
            }
        }
        Class<? extends ConverterMatcher> converterType = annotation.value();
        Map<List<Object>, Converter> converterMapping = (Map) this.converterCache.get(converterType);
        if (converterMapping != null) {
            result = (Converter) converterMapping.get(parameter);
        }
        if (result != null) {
            return result;
        }
        Object[] args;
        length = parameter.size();
        if (length > 0) {
            args = new Object[(this.arguments.length + length)];
            System.arraycopy(this.arguments, 0, args, length, this.arguments.length);
            System.arraycopy(parameter.toArray(new Object[length]), 0, args, 0, length);
        } else {
            args = this.arguments;
        }
        Object[] args2 = args;
        try {
            Converter converter;
            if (!SingleValueConverter.class.isAssignableFrom(converterType) || Converter.class.isAssignableFrom(converterType)) {
                converter = (Converter) DependencyInjectionFactory.newInstance(converterType, args2);
            } else {
                converter = new SingleValueConverterWrapper((SingleValueConverter) DependencyInjectionFactory.newInstance(converterType, args2));
            }
            if (converterMapping == null) {
                converterMapping = new HashMap();
                this.converterCache.put(converterType, converterMapping);
            }
            converterMapping.put(parameter, converter);
            return converter;
        } catch (Exception e) {
            String stringBuilder;
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("Cannot instantiate converter ");
            stringBuilder2.append(converterType.getName());
            if (targetType != null) {
                StringBuilder stringBuilder3 = new StringBuilder();
                stringBuilder3.append(" for type ");
                stringBuilder3.append(targetType.getName());
                stringBuilder = stringBuilder3.toString();
            } else {
                stringBuilder = "";
            }
            stringBuilder2.append(stringBuilder);
            throw new InitializationException(stringBuilder2.toString(), e);
        }
    }

    private Class<?> getClass(Type typeArgument) {
        if (typeArgument instanceof ParameterizedType) {
            return (Class) ((ParameterizedType) typeArgument).getRawType();
        }
        if (typeArgument instanceof Class) {
            return (Class) typeArgument;
        }
        return null;
    }

    private void setupMappers() {
        this.classAliasingMapper = (ClassAliasingMapper) lookupMapperOfType(ClassAliasingMapper.class);
        this.defaultImplementationsMapper = (DefaultImplementationsMapper) lookupMapperOfType(DefaultImplementationsMapper.class);
        this.implicitCollectionMapper = (ImplicitCollectionMapper) lookupMapperOfType(ImplicitCollectionMapper.class);
        this.fieldAliasingMapper = (FieldAliasingMapper) lookupMapperOfType(FieldAliasingMapper.class);
        this.attributeMapper = (AttributeMapper) lookupMapperOfType(AttributeMapper.class);
        this.localConversionMapper = (LocalConversionMapper) lookupMapperOfType(LocalConversionMapper.class);
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        int max = this.arguments.length - 2;
        out.writeInt(max);
        for (int i = 0; i < max; i++) {
            out.writeObject(this.arguments[i]);
        }
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        setupMappers();
        int max = in.readInt();
        this.arguments = new Object[(max + 2)];
        for (int i = 0; i < max; i++) {
            this.arguments[i] = in.readObject();
            if (this.arguments[i] instanceof ClassLoaderReference) {
                this.arguments[max + 1] = ((ClassLoaderReference) this.arguments[i]).getReference();
            }
        }
        this.arguments[max] = new JVM();
    }
}
