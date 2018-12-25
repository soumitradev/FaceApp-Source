package com.thoughtworks.xstream.mapper;

import com.thoughtworks.xstream.InitializationException;
import com.thoughtworks.xstream.core.util.Primitives;
import com.thoughtworks.xstream.mapper.Mapper.ImplicitCollectionMapping;
import com.thoughtworks.xstream.mapper.Mapper.Null;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class ImplicitCollectionMapper extends MapperWrapper {
    private final Map classNameToMapper = new HashMap();

    private class ImplicitCollectionMapperForClass {
        private Class definedIn;
        private Map fieldNameToDef = new HashMap();
        private Map itemFieldNameToDef = new HashMap();
        private Map namedItemTypeToDef = new HashMap();

        ImplicitCollectionMapperForClass(Class definedIn) {
            this.definedIn = definedIn;
        }

        public String getFieldNameForItemTypeAndName(Class itemType, String itemFieldName) {
            ImplicitCollectionMappingImpl unnamed = null;
            for (NamedItemType itemTypeForFieldName : this.namedItemTypeToDef.keySet()) {
                ImplicitCollectionMappingImpl def = (ImplicitCollectionMappingImpl) this.namedItemTypeToDef.get(itemTypeForFieldName);
                if (itemType == Null.class) {
                    unnamed = def;
                    break;
                } else if (itemTypeForFieldName.itemType.isAssignableFrom(itemType)) {
                    if (def.getItemFieldName() != null) {
                        if (def.getItemFieldName().equals(itemFieldName)) {
                            return def.getFieldName();
                        }
                    } else if (unnamed == null || unnamed.getItemType() == null || (def.getItemType() != null && unnamed.getItemType().isAssignableFrom(def.getItemType()))) {
                        unnamed = def;
                    }
                }
            }
            if (unnamed != null) {
                return unnamed.getFieldName();
            }
            ImplicitCollectionMapperForClass mapper = ImplicitCollectionMapper.this.getMapper(this.definedIn.getSuperclass());
            return mapper != null ? mapper.getFieldNameForItemTypeAndName(itemType, itemFieldName) : null;
        }

        public Class getItemTypeForItemFieldName(String itemFieldName) {
            ImplicitCollectionMappingImpl def = getImplicitCollectionDefByItemFieldName(itemFieldName);
            if (def != null) {
                return def.getItemType();
            }
            ImplicitCollectionMapperForClass mapper = ImplicitCollectionMapper.this.getMapper(this.definedIn.getSuperclass());
            return mapper != null ? mapper.getItemTypeForItemFieldName(itemFieldName) : null;
        }

        private ImplicitCollectionMappingImpl getImplicitCollectionDefByItemFieldName(String itemFieldName) {
            ImplicitCollectionMappingImpl implicitCollectionMappingImpl = null;
            if (itemFieldName == null) {
                return null;
            }
            ImplicitCollectionMappingImpl mapping = (ImplicitCollectionMappingImpl) this.itemFieldNameToDef.get(itemFieldName);
            if (mapping != null) {
                return mapping;
            }
            ImplicitCollectionMapperForClass mapper = ImplicitCollectionMapper.this.getMapper(this.definedIn.getSuperclass());
            if (mapper != null) {
                implicitCollectionMappingImpl = mapper.getImplicitCollectionDefByItemFieldName(itemFieldName);
            }
            return implicitCollectionMappingImpl;
        }

        public ImplicitCollectionMapping getImplicitCollectionDefForFieldName(String fieldName) {
            ImplicitCollectionMapping mapping = (ImplicitCollectionMapping) this.fieldNameToDef.get(fieldName);
            if (mapping != null) {
                return mapping;
            }
            ImplicitCollectionMapperForClass mapper = ImplicitCollectionMapper.this.getMapper(this.definedIn.getSuperclass());
            return mapper != null ? mapper.getImplicitCollectionDefForFieldName(fieldName) : null;
        }

        public void add(ImplicitCollectionMappingImpl def) {
            this.fieldNameToDef.put(def.getFieldName(), def);
            this.namedItemTypeToDef.put(def.createNamedItemType(), def);
            if (def.getItemFieldName() != null) {
                this.itemFieldNameToDef.put(def.getItemFieldName(), def);
            }
        }
    }

    private static class NamedItemType {
        String itemFieldName;
        Class itemType;

        NamedItemType(Class itemType, String itemFieldName) {
            this.itemType = itemType == null ? Object.class : itemType;
            this.itemFieldName = itemFieldName;
        }

        public boolean equals(Object obj) {
            boolean z = false;
            if (!(obj instanceof NamedItemType)) {
                return false;
            }
            NamedItemType b = (NamedItemType) obj;
            if (this.itemType.equals(b.itemType) && isEquals(this.itemFieldName, b.itemFieldName)) {
                z = true;
            }
            return z;
        }

        private static boolean isEquals(Object a, Object b) {
            if (a != null) {
                return a.equals(b);
            }
            return b == null;
        }

        public int hashCode() {
            int hash = this.itemType.hashCode() << 7;
            if (this.itemFieldName != null) {
                return hash + this.itemFieldName.hashCode();
            }
            return hash;
        }
    }

    private static class ImplicitCollectionMappingImpl implements ImplicitCollectionMapping {
        private final String fieldName;
        private final String itemFieldName;
        private final Class itemType;
        private final String keyFieldName;

        ImplicitCollectionMappingImpl(String fieldName, Class itemType, String itemFieldName, String keyFieldName) {
            this.fieldName = fieldName;
            this.itemFieldName = itemFieldName;
            this.itemType = itemType;
            this.keyFieldName = keyFieldName;
        }

        public NamedItemType createNamedItemType() {
            return new NamedItemType(this.itemType, this.itemFieldName);
        }

        public String getFieldName() {
            return this.fieldName;
        }

        public String getItemFieldName() {
            return this.itemFieldName;
        }

        public Class getItemType() {
            return this.itemType;
        }

        public String getKeyFieldName() {
            return this.keyFieldName;
        }
    }

    public ImplicitCollectionMapper(Mapper wrapped) {
        super(wrapped);
    }

    private ImplicitCollectionMapperForClass getMapper(Class definedIn) {
        while (definedIn != null) {
            ImplicitCollectionMapperForClass mapper = (ImplicitCollectionMapperForClass) this.classNameToMapper.get(definedIn);
            if (mapper != null) {
                return mapper;
            }
            definedIn = definedIn.getSuperclass();
        }
        return null;
    }

    private ImplicitCollectionMapperForClass getOrCreateMapper(Class definedIn) {
        ImplicitCollectionMapperForClass mapper = (ImplicitCollectionMapperForClass) this.classNameToMapper.get(definedIn);
        if (mapper != null) {
            return mapper;
        }
        mapper = new ImplicitCollectionMapperForClass(definedIn);
        this.classNameToMapper.put(definedIn, mapper);
        return mapper;
    }

    public String getFieldNameForItemTypeAndName(Class definedIn, Class itemType, String itemFieldName) {
        ImplicitCollectionMapperForClass mapper = getMapper(definedIn);
        if (mapper != null) {
            return mapper.getFieldNameForItemTypeAndName(itemType, itemFieldName);
        }
        return null;
    }

    public Class getItemTypeForItemFieldName(Class definedIn, String itemFieldName) {
        ImplicitCollectionMapperForClass mapper = getMapper(definedIn);
        if (mapper != null) {
            return mapper.getItemTypeForItemFieldName(itemFieldName);
        }
        return null;
    }

    public ImplicitCollectionMapping getImplicitCollectionDefForFieldName(Class itemType, String fieldName) {
        ImplicitCollectionMapperForClass mapper = getMapper(itemType);
        if (mapper != null) {
            return mapper.getImplicitCollectionDefForFieldName(fieldName);
        }
        return null;
    }

    public void add(Class definedIn, String fieldName, Class itemType) {
        add(definedIn, fieldName, null, itemType);
    }

    public void add(Class definedIn, String fieldName, String itemFieldName, Class itemType) {
        add(definedIn, fieldName, itemFieldName, itemType, null);
    }

    public void add(Class definedIn, String fieldName, String itemFieldName, Class itemType, String keyFieldName) {
        Field field = null;
        Class declaredIn = definedIn;
        while (declaredIn != Object.class && definedIn != null) {
            try {
                field = declaredIn.getDeclaredField(fieldName);
                break;
            } catch (SecurityException e) {
                throw new InitializationException("Access denied for field with implicit collection", e);
            } catch (NoSuchFieldException e2) {
                declaredIn = declaredIn.getSuperclass();
            }
        }
        if (field == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("No field \"");
            stringBuilder.append(fieldName);
            stringBuilder.append("\" for implicit collection");
            throw new InitializationException(stringBuilder.toString());
        }
        if (Map.class.isAssignableFrom(field.getType())) {
            if (itemFieldName == null && keyFieldName == null) {
                itemType = Entry.class;
            }
        } else if (!Collection.class.isAssignableFrom(field.getType())) {
            Class fieldType = field.getType();
            if (fieldType.isArray()) {
                Class componentType = fieldType.getComponentType();
                componentType = componentType.isPrimitive() ? Primitives.box(componentType) : componentType;
                if (itemType == null) {
                    itemType = componentType;
                } else {
                    itemType = itemType.isPrimitive() ? Primitives.box(itemType) : itemType;
                    if (!componentType.isAssignableFrom(itemType)) {
                        StringBuilder stringBuilder2 = new StringBuilder();
                        stringBuilder2.append("Field \"");
                        stringBuilder2.append(fieldName);
                        stringBuilder2.append("\" declares an array, but the array type is not compatible with ");
                        stringBuilder2.append(itemType.getName());
                        throw new InitializationException(stringBuilder2.toString());
                    }
                }
            }
            StringBuilder stringBuilder3 = new StringBuilder();
            stringBuilder3.append("Field \"");
            stringBuilder3.append(fieldName);
            stringBuilder3.append("\" declares no collection or array");
            throw new InitializationException(stringBuilder3.toString());
        }
        getOrCreateMapper(definedIn).add(new ImplicitCollectionMappingImpl(fieldName, itemType, itemFieldName, keyFieldName));
    }
}
