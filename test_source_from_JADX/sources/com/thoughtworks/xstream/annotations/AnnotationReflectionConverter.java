package com.thoughtworks.xstream.annotations;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.ConverterMatcher;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.SingleValueConverter;
import com.thoughtworks.xstream.converters.SingleValueConverterWrapper;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.reflection.ObjectAccessException;
import com.thoughtworks.xstream.converters.reflection.ReflectionConverter;
import com.thoughtworks.xstream.converters.reflection.ReflectionProvider;
import com.thoughtworks.xstream.mapper.Mapper;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

@Deprecated
public class AnnotationReflectionConverter extends ReflectionConverter {
    private final AnnotationProvider annotationProvider;
    private final Map<Class<? extends ConverterMatcher>, Converter> cachedConverters = new HashMap();

    @Deprecated
    public AnnotationReflectionConverter(Mapper mapper, ReflectionProvider reflectionProvider, AnnotationProvider annotationProvider) {
        super(mapper, reflectionProvider);
        this.annotationProvider = annotationProvider;
    }

    protected void marshallField(MarshallingContext context, Object newObj, Field field) {
        XStreamConverter annotation = (XStreamConverter) this.annotationProvider.getAnnotation(field, XStreamConverter.class);
        if (annotation != null) {
            Class<? extends ConverterMatcher> type = annotation.value();
            ensureCache(type);
            context.convertAnother(newObj, (Converter) this.cachedConverters.get(type));
            return;
        }
        context.convertAnother(newObj);
    }

    private void ensureCache(Class<? extends ConverterMatcher> type) {
        if (!this.cachedConverters.containsKey(type)) {
            this.cachedConverters.put(type, newInstance(type));
        }
    }

    protected Object unmarshallField(UnmarshallingContext context, Object result, Class type, Field field) {
        XStreamConverter annotation = (XStreamConverter) this.annotationProvider.getAnnotation(field, XStreamConverter.class);
        if (annotation == null) {
            return context.convertAnother(result, type);
        }
        Class<? extends Converter> converterType = annotation.value();
        ensureCache(converterType);
        return context.convertAnother(result, type, (Converter) this.cachedConverters.get(converterType));
    }

    private Converter newInstance(Class<? extends ConverterMatcher> type) {
        StringBuilder stringBuilder;
        try {
            Converter converter;
            if (SingleValueConverter.class.isAssignableFrom(type)) {
                converter = new SingleValueConverterWrapper((SingleValueConverter) type.getConstructor(new Class[0]).newInstance(new Object[0]));
            } else {
                converter = (Converter) type.getConstructor(new Class[0]).newInstance(new Object[0]);
            }
            return converter;
        } catch (InvocationTargetException e) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Cannot construct ");
            stringBuilder.append(type.getName());
            throw new ObjectAccessException(stringBuilder.toString(), e.getCause());
        } catch (InstantiationException e2) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Cannot construct ");
            stringBuilder.append(type.getName());
            throw new ObjectAccessException(stringBuilder.toString(), e2);
        } catch (IllegalAccessException e3) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Cannot construct ");
            stringBuilder.append(type.getName());
            throw new ObjectAccessException(stringBuilder.toString(), e3);
        } catch (NoSuchMethodException e4) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Cannot construct ");
            stringBuilder.append(type.getName());
            throw new ObjectAccessException(stringBuilder.toString(), e4);
        }
    }
}
