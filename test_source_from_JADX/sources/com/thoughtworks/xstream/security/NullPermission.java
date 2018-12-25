package com.thoughtworks.xstream.security;

import com.thoughtworks.xstream.mapper.Mapper.Null;

public class NullPermission implements TypePermission {
    public static final TypePermission NULL = new NullPermission();

    public boolean allows(Class type) {
        if (type != null) {
            if (type != Null.class) {
                return false;
            }
        }
        return true;
    }
}
