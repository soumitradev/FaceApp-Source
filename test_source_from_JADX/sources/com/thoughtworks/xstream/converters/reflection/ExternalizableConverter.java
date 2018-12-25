package com.thoughtworks.xstream.converters.reflection;

import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.DataHolder;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.core.ClassLoaderReference;
import com.thoughtworks.xstream.core.JVM;
import com.thoughtworks.xstream.core.util.CustomObjectInputStream;
import com.thoughtworks.xstream.core.util.CustomObjectOutputStream;
import com.thoughtworks.xstream.core.util.CustomObjectOutputStream.StreamCallback;
import com.thoughtworks.xstream.core.util.HierarchicalStreams;
import com.thoughtworks.xstream.io.ExtendedHierarchicalStreamWriterHelper;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;
import java.io.Externalizable;
import java.io.IOException;
import java.io.NotActiveException;
import java.io.ObjectInputValidation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class ExternalizableConverter implements Converter {
    private final ClassLoaderReference classLoaderReference;
    private Mapper mapper;

    public ExternalizableConverter(Mapper mapper, ClassLoaderReference classLoaderReference) {
        this.mapper = mapper;
        this.classLoaderReference = classLoaderReference;
    }

    public ExternalizableConverter(Mapper mapper, ClassLoader classLoader) {
        this(mapper, new ClassLoaderReference(classLoader));
    }

    public ExternalizableConverter(Mapper mapper) {
        this(mapper, ExternalizableConverter.class.getClassLoader());
    }

    public boolean canConvert(Class type) {
        return JVM.canCreateDerivedObjectOutputStream() && Externalizable.class.isAssignableFrom(type);
    }

    public void marshal(Object source, final HierarchicalStreamWriter writer, final MarshallingContext context) {
        try {
            Externalizable externalizable = (Externalizable) source;
            CustomObjectOutputStream objectOutput = CustomObjectOutputStream.getInstance(context, new StreamCallback() {
                public void writeToStream(Object object) {
                    if (object == null) {
                        writer.startNode("null");
                        writer.endNode();
                        return;
                    }
                    ExtendedHierarchicalStreamWriterHelper.startNode(writer, ExternalizableConverter.this.mapper.serializedClass(object.getClass()), object.getClass());
                    context.convertAnother(object);
                    writer.endNode();
                }

                public void writeFieldsToStream(Map fields) {
                    throw new UnsupportedOperationException();
                }

                public void defaultWriteObject() {
                    throw new UnsupportedOperationException();
                }

                public void flush() {
                    writer.flush();
                }

                public void close() {
                    throw new UnsupportedOperationException("Objects are not allowed to call ObjectOutput.close() from writeExternal()");
                }
            });
            externalizable.writeExternal(objectOutput);
            objectOutput.popCallback();
        } catch (IOException e) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Cannot serialize ");
            stringBuilder.append(source.getClass().getName());
            stringBuilder.append(" using Externalization");
            throw new ConversionException(stringBuilder.toString(), e);
        }
    }

    public Object unmarshal(final HierarchicalStreamReader reader, final UnmarshallingContext context) {
        NoSuchMethodException e;
        StringBuilder stringBuilder;
        InvocationTargetException e2;
        InstantiationException e3;
        IllegalAccessException e4;
        IOException e5;
        ClassNotFoundException e6;
        Object obj;
        Class type = context.getRequiredType();
        try {
            Constructor defaultConstructor = type.getDeclaredConstructor((Class[]) null);
            try {
                if (!defaultConstructor.isAccessible()) {
                    defaultConstructor.setAccessible(true);
                }
                final Externalizable externalizable = (Externalizable) defaultConstructor.newInstance((Object[]) null);
                CustomObjectInputStream objectInput = CustomObjectInputStream.getInstance((DataHolder) context, new CustomObjectInputStream.StreamCallback() {
                    public Object readFromStream() {
                        reader.moveDown();
                        Object streamItem = context.convertAnother(externalizable, HierarchicalStreams.readClassType(reader, ExternalizableConverter.this.mapper));
                        reader.moveUp();
                        return streamItem;
                    }

                    public Map readFieldsFromStream() {
                        throw new UnsupportedOperationException();
                    }

                    public void defaultReadObject() {
                        throw new UnsupportedOperationException();
                    }

                    public void registerValidation(ObjectInputValidation validation, int priority) throws NotActiveException {
                        throw new NotActiveException("stream inactive");
                    }

                    public void close() {
                        throw new UnsupportedOperationException("Objects are not allowed to call ObjectInput.close() from readExternal()");
                    }
                }, this.classLoaderReference);
                externalizable.readExternal(objectInput);
                objectInput.popCallback();
                return externalizable;
            } catch (NoSuchMethodException e7) {
                e = e7;
                stringBuilder = new StringBuilder();
                stringBuilder.append("Cannot construct ");
                stringBuilder.append(type.getClass());
                stringBuilder.append(", missing default constructor");
                throw new ConversionException(stringBuilder.toString(), e);
            } catch (InvocationTargetException e8) {
                e2 = e8;
                stringBuilder = new StringBuilder();
                stringBuilder.append("Cannot construct ");
                stringBuilder.append(type.getClass());
                throw new ConversionException(stringBuilder.toString(), e2);
            } catch (InstantiationException e9) {
                e3 = e9;
                stringBuilder = new StringBuilder();
                stringBuilder.append("Cannot construct ");
                stringBuilder.append(type.getClass());
                throw new ConversionException(stringBuilder.toString(), e3);
            } catch (IllegalAccessException e10) {
                e4 = e10;
                stringBuilder = new StringBuilder();
                stringBuilder.append("Cannot construct ");
                stringBuilder.append(type.getClass());
                throw new ConversionException(stringBuilder.toString(), e4);
            } catch (IOException e11) {
                e5 = e11;
                stringBuilder = new StringBuilder();
                stringBuilder.append("Cannot externalize ");
                stringBuilder.append(type.getClass());
                throw new ConversionException(stringBuilder.toString(), e5);
            } catch (ClassNotFoundException e12) {
                e6 = e12;
                stringBuilder = new StringBuilder();
                stringBuilder.append("Cannot externalize ");
                stringBuilder.append(type.getClass());
                throw new ConversionException(stringBuilder.toString(), e6);
            }
        } catch (NoSuchMethodException e13) {
            NoSuchMethodException noSuchMethodException = e13;
            obj = null;
            e = noSuchMethodException;
            stringBuilder = new StringBuilder();
            stringBuilder.append("Cannot construct ");
            stringBuilder.append(type.getClass());
            stringBuilder.append(", missing default constructor");
            throw new ConversionException(stringBuilder.toString(), e);
        } catch (InvocationTargetException e14) {
            InvocationTargetException invocationTargetException = e14;
            obj = null;
            e2 = invocationTargetException;
            stringBuilder = new StringBuilder();
            stringBuilder.append("Cannot construct ");
            stringBuilder.append(type.getClass());
            throw new ConversionException(stringBuilder.toString(), e2);
        } catch (InstantiationException e15) {
            InstantiationException instantiationException = e15;
            obj = null;
            e3 = instantiationException;
            stringBuilder = new StringBuilder();
            stringBuilder.append("Cannot construct ");
            stringBuilder.append(type.getClass());
            throw new ConversionException(stringBuilder.toString(), e3);
        } catch (IllegalAccessException e16) {
            IllegalAccessException illegalAccessException = e16;
            obj = null;
            e4 = illegalAccessException;
            stringBuilder = new StringBuilder();
            stringBuilder.append("Cannot construct ");
            stringBuilder.append(type.getClass());
            throw new ConversionException(stringBuilder.toString(), e4);
        } catch (IOException e17) {
            IOException iOException = e17;
            obj = null;
            e5 = iOException;
            stringBuilder = new StringBuilder();
            stringBuilder.append("Cannot externalize ");
            stringBuilder.append(type.getClass());
            throw new ConversionException(stringBuilder.toString(), e5);
        } catch (ClassNotFoundException e18) {
            ClassNotFoundException classNotFoundException = e18;
            obj = null;
            e6 = classNotFoundException;
            stringBuilder = new StringBuilder();
            stringBuilder.append("Cannot externalize ");
            stringBuilder.append(type.getClass());
            throw new ConversionException(stringBuilder.toString(), e6);
        }
    }
}
