package com.thoughtworks.xstream.persistence;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.SingleValueConverter;
import com.thoughtworks.xstream.io.StreamException;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.io.File;

public class FilePersistenceStrategy extends AbstractFilePersistenceStrategy {
    private final String illegalChars;

    public FilePersistenceStrategy(File baseDirectory) {
        this(baseDirectory, new XStream(new DomDriver()));
    }

    public FilePersistenceStrategy(File baseDirectory, XStream xstream) {
        this(baseDirectory, xstream, "utf-8", "<>?:/\\\"|*%");
    }

    public FilePersistenceStrategy(File baseDirectory, XStream xstream, String encoding, String illegalChars) {
        super(baseDirectory, xstream, encoding);
        this.illegalChars = illegalChars;
    }

    protected boolean isValid(File dir, String name) {
        return super.isValid(dir, name) && name.indexOf(64) > 0;
    }

    protected Object extractKey(String name) {
        String key = unescape(name.substring(0, name.length() - 4));
        if ("null@null".equals(key)) {
            return null;
        }
        int idx = key.indexOf(64);
        if (idx < 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Not a valid key: ");
            stringBuilder.append(key);
            throw new StreamException(stringBuilder.toString());
        }
        Class type = getMapper().realClass(key.substring(0, idx));
        Converter converter = getConverterLookup().lookupConverterForType(type);
        if (converter instanceof SingleValueConverter) {
            return ((SingleValueConverter) converter).fromString(key.substring(idx + 1));
        }
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("No SingleValueConverter for type ");
        stringBuilder2.append(type.getName());
        stringBuilder2.append(" available");
        throw new StreamException(stringBuilder2.toString());
    }

    protected String unescape(String name) {
        StringBuffer buffer = new StringBuffer();
        while (true) {
            int idx = name.indexOf(37);
            if (idx >= 0) {
                buffer.append(name.substring(0, idx));
                buffer.append((char) Integer.parseInt(name.substring(idx + 1, idx + 3), 16));
                name = name.substring(idx + 3);
            } else {
                buffer.append(name);
                return buffer.toString();
            }
        }
    }

    protected String getName(Object key) {
        if (key == null) {
            return "null@null.xml";
        }
        Class type = key.getClass();
        Converter converter = getConverterLookup().lookupConverterForType(type);
        if (converter instanceof SingleValueConverter) {
            SingleValueConverter svConverter = (SingleValueConverter) converter;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(getMapper().serializedClass(type));
            stringBuilder.append('@');
            stringBuilder.append(escape(svConverter.toString(key)));
            stringBuilder.append(".xml");
            return stringBuilder.toString();
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append("No SingleValueConverter for type ");
        stringBuilder.append(type.getName());
        stringBuilder.append(" available");
        throw new StreamException(stringBuilder.toString());
    }

    protected String escape(String key) {
        StringBuffer buffer = new StringBuffer();
        char[] array = key.toCharArray();
        for (char c : array) {
            if (c < ' ' || this.illegalChars.indexOf(c) >= 0) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("%");
                stringBuilder.append(Integer.toHexString(c).toUpperCase());
                buffer.append(stringBuilder.toString());
            } else {
                buffer.append(c);
            }
        }
        return buffer.toString();
    }
}
