package com.thoughtworks.xstream.converters.basic;

import com.facebook.internal.ServerProtocol;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.SingleValueConverter;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class CharConverter implements Converter, SingleValueConverter {
    public boolean canConvert(Class type) {
        if (!type.equals(Character.TYPE)) {
            if (!type.equals(Character.class)) {
                return false;
            }
        }
        return true;
    }

    public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
        writer.setValue(toString(source));
    }

    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        String nullAttribute = reader.getAttribute("null");
        if (nullAttribute == null || !nullAttribute.equals(ServerProtocol.DIALOG_RETURN_SCOPES_TRUE)) {
            return fromString(reader.getValue());
        }
        return new Character('\u0000');
    }

    public Object fromString(String str) {
        if (str.length() == 0) {
            return new Character('\u0000');
        }
        return new Character(str.charAt(0));
    }

    public String toString(Object obj) {
        return ((Character) obj).charValue() == '\u0000' ? "" : obj.toString();
    }
}
