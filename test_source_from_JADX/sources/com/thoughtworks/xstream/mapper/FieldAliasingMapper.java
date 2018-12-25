package com.thoughtworks.xstream.mapper;

import com.thoughtworks.xstream.core.util.FastField;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public class FieldAliasingMapper extends MapperWrapper {
    protected final Map aliasToFieldMap = new HashMap();
    protected final Map fieldToAliasMap = new HashMap();
    protected final Set fieldsToOmit = new HashSet();
    protected final Set unknownFieldsToIgnore = new LinkedHashSet();

    public FieldAliasingMapper(Mapper wrapped) {
        super(wrapped);
    }

    public void addFieldAlias(String alias, Class type, String fieldName) {
        this.fieldToAliasMap.put(key(type, fieldName), alias);
        this.aliasToFieldMap.put(key(type, alias), fieldName);
    }

    public void addFieldsToIgnore(Pattern pattern) {
        this.unknownFieldsToIgnore.add(pattern);
    }

    private Object key(Class type, String name) {
        return new FastField(type, name);
    }

    public String serializedMember(Class type, String memberName) {
        String alias = getMember(type, memberName, this.fieldToAliasMap);
        if (alias == null) {
            return super.serializedMember(type, memberName);
        }
        return alias;
    }

    public String realMember(Class type, String serialized) {
        String real = getMember(type, serialized, this.aliasToFieldMap);
        if (real == null) {
            return super.realMember(type, serialized);
        }
        return real;
    }

    private String getMember(Class type, String name, Map map) {
        String member = null;
        Class declaringType = type;
        while (member == null && declaringType != Object.class && declaringType != null) {
            member = (String) map.get(key(declaringType, name));
            declaringType = declaringType.getSuperclass();
        }
        return member;
    }

    public boolean shouldSerializeMember(Class definedIn, String fieldName) {
        if (this.fieldsToOmit.contains(key(definedIn, fieldName))) {
            return false;
        }
        if (definedIn == Object.class && !this.unknownFieldsToIgnore.isEmpty()) {
            for (Pattern pattern : this.unknownFieldsToIgnore) {
                if (pattern.matcher(fieldName).matches()) {
                    return false;
                }
            }
        }
        return true;
    }

    public void omitField(Class definedIn, String fieldName) {
        this.fieldsToOmit.add(key(definedIn, fieldName));
    }
}
