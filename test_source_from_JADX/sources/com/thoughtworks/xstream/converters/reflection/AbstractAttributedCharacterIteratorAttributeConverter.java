package com.thoughtworks.xstream.converters.reflection;

import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter;
import com.thoughtworks.xstream.core.util.Fields;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.AttributedCharacterIterator.Attribute;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class AbstractAttributedCharacterIteratorAttributeConverter extends AbstractSingleValueConverter {
    private static final Method getName;
    private static final Map instanceMaps = new HashMap();
    private transient Map attributeMap;
    private final Class type;

    static {
        Method method = null;
        try {
            method = Attribute.class.getDeclaredMethod("getName", (Class[]) null);
            if (!method.isAccessible()) {
                method.setAccessible(true);
            }
        } catch (SecurityException e) {
        } catch (NoSuchMethodException e2) {
        }
        getName = method;
    }

    public AbstractAttributedCharacterIteratorAttributeConverter(Class type) {
        if (Attribute.class.isAssignableFrom(type)) {
            this.type = type;
            readResolve();
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(type.getName());
        stringBuilder.append(" is not a ");
        stringBuilder.append(Attribute.class.getName());
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public boolean canConvert(Class type) {
        return type == this.type && !this.attributeMap.isEmpty();
    }

    public String toString(Object source) {
        return getName((Attribute) source);
    }

    private String getName(Attribute attribute) {
        Exception ex = null;
        if (getName != null) {
            try {
                return (String) getName.invoke(attribute, (Object[]) null);
            } catch (Exception e) {
                ex = e;
            } catch (Exception e2) {
                ex = e2;
            }
        }
        String s = attribute.toString();
        String className = attribute.getClass().getName();
        if (s.startsWith(className)) {
            return s.substring(className.length() + 1, s.length() - 1);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Cannot find name of attribute of type ");
        stringBuilder.append(className);
        throw new ConversionException(stringBuilder.toString(), ex);
    }

    public Object fromString(String str) {
        if (this.attributeMap.containsKey(str)) {
            return this.attributeMap.get(str);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Cannot find attribute of type ");
        stringBuilder.append(this.type.getName());
        stringBuilder.append(" with name ");
        stringBuilder.append(str);
        throw new ConversionException(stringBuilder.toString());
    }

    private Object readResolve() {
        this.attributeMap = (Map) instanceMaps.get(this.type.getName());
        if (this.attributeMap == null) {
            this.attributeMap = new HashMap();
            Field instanceMap = Fields.locate(this.type, Map.class, true);
            if (instanceMap != null) {
                try {
                    Map map = (Map) Fields.read(instanceMap, null);
                    if (map != null) {
                        boolean valid = true;
                        Iterator iter = map.entrySet().iterator();
                        while (valid && iter.hasNext()) {
                            Entry entry = (Entry) iter.next();
                            boolean z = entry.getKey().getClass() == String.class && entry.getValue().getClass() == this.type;
                            valid = z;
                        }
                        if (valid) {
                            this.attributeMap.putAll(map);
                        }
                    }
                } catch (ObjectAccessException e) {
                }
            }
            if (this.attributeMap.isEmpty()) {
                try {
                    Field[] fields = this.type.getDeclaredFields();
                    for (int i = 0; i < fields.length; i++) {
                        if ((fields[i].getType() == this.type) == Modifier.isStatic(fields[i].getModifiers())) {
                            Attribute attribute = (Attribute) Fields.read(fields[i], null);
                            this.attributeMap.put(toString(attribute), attribute);
                        }
                    }
                } catch (SecurityException e2) {
                    this.attributeMap.clear();
                } catch (ObjectAccessException e3) {
                    this.attributeMap.clear();
                } catch (NoClassDefFoundError e4) {
                    this.attributeMap.clear();
                }
            }
            instanceMaps.put(this.type.getName(), this.attributeMap);
        }
        return this;
    }
}
