package com.thoughtworks.xstream.converters.extended;

import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter;
import com.thoughtworks.xstream.core.JVM;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StackTraceElementConverter extends AbstractSingleValueConverter {
    private static final StackTraceElementFactory FACTORY;
    private static final Pattern PATTERN = Pattern.compile("^(.+)\\.([^\\(]+)\\(([^:]*)(:(\\d+))?\\)$");

    static {
        StackTraceElementFactory factory = null;
        if (JVM.is15()) {
            try {
                factory = (StackTraceElementFactory) JVM.loadClassForName("com.thoughtworks.xstream.converters.extended.StackTraceElementFactory15", false).newInstance();
            } catch (Exception e) {
            } catch (LinkageError e2) {
            }
        }
        if (factory == null) {
            factory = new StackTraceElementFactory();
        }
        try {
            factory.unknownSourceElement("a", "b");
        } catch (Exception e3) {
            factory = null;
        } catch (NoClassDefFoundError e4) {
            factory = null;
        }
        FACTORY = factory;
    }

    public boolean canConvert(Class type) {
        return StackTraceElement.class.equals(type) && FACTORY != null;
    }

    public String toString(Object obj) {
        return super.toString(obj).replaceFirst(":\\?\\?\\?", "");
    }

    public Object fromString(String str) {
        Matcher matcher = PATTERN.matcher(str);
        if (matcher.matches()) {
            String declaringClass = matcher.group(1);
            String methodName = matcher.group(2);
            String fileName = matcher.group(3);
            if (fileName.equals("Unknown Source")) {
                return FACTORY.unknownSourceElement(declaringClass, methodName);
            }
            if (fileName.equals("Native Method")) {
                return FACTORY.nativeMethodElement(declaringClass, methodName);
            }
            if (matcher.group(4) == null) {
                return FACTORY.element(declaringClass, methodName, fileName);
            }
            return FACTORY.element(declaringClass, methodName, fileName, Integer.parseInt(matcher.group(5)));
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Could not parse StackTraceElement : ");
        stringBuilder.append(str);
        throw new ConversionException(stringBuilder.toString());
    }
}
