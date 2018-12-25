package com.thoughtworks.xstream.converters.extended;

import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.ConverterLookup;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.SingleValueConverter;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.collections.MapConverter;
import com.thoughtworks.xstream.core.JVM;
import com.thoughtworks.xstream.core.util.HierarchicalStreams;
import com.thoughtworks.xstream.io.ExtendedHierarchicalStreamWriterHelper;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;
import com.thoughtworks.xstream.mapper.Mapper.Null;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class NamedMapConverter extends MapConverter {
    private final String entryName;
    private final Mapper enumMapper;
    private final boolean keyAsAttribute;
    private final String keyName;
    private final Class keyType;
    private final ConverterLookup lookup;
    private final boolean valueAsAttribute;
    private final String valueName;
    private final Class valueType;

    public NamedMapConverter(Mapper mapper, String entryName, String keyName, Class keyType, String valueName, Class valueType) {
        this(mapper, entryName, keyName, keyType, valueName, valueType, false, false, null);
    }

    public NamedMapConverter(Class type, Mapper mapper, String entryName, String keyName, Class keyType, String valueName, Class valueType) {
        this(type, mapper, entryName, keyName, keyType, valueName, valueType, false, false, null);
    }

    public NamedMapConverter(Mapper mapper, String entryName, String keyName, Class keyType, String valueName, Class valueType, boolean keyAsAttribute, boolean valueAsAttribute, ConverterLookup lookup) {
        this(null, mapper, entryName, keyName, keyType, valueName, valueType, keyAsAttribute, valueAsAttribute, lookup);
    }

    public NamedMapConverter(Class type, Mapper mapper, String entryName, String keyName, Class keyType, String valueName, Class valueType, boolean keyAsAttribute, boolean valueAsAttribute, ConverterLookup lookup) {
        super(mapper, type);
        Mapper mapper2 = null;
        String str = (entryName == null || entryName.length() != 0) ? entryName : null;
        this.entryName = str;
        str = (keyName == null || keyName.length() != 0) ? keyName : null;
        this.keyName = str;
        this.keyType = keyType;
        str = (valueName == null || valueName.length() != 0) ? valueName : null;
        this.valueName = str;
        this.valueType = valueType;
        this.keyAsAttribute = keyAsAttribute;
        this.valueAsAttribute = valueAsAttribute;
        this.lookup = lookup;
        if (JVM.is15()) {
            mapper2 = UseAttributeForEnumMapper.createEnumMapper(mapper);
        }
        this.enumMapper = mapper2;
        if (keyType != null) {
            if (valueType != null) {
                if (entryName == null) {
                    if (!keyAsAttribute) {
                        if (!valueAsAttribute) {
                            if (valueName == null) {
                                throw new IllegalArgumentException("Cannot write value as text of entry, if entry must be omitted");
                            }
                        }
                    }
                    throw new IllegalArgumentException("Cannot write attributes to map entry, if map entry must be omitted");
                }
                if (keyName == null) {
                    throw new IllegalArgumentException("Cannot write key without name");
                }
                if (valueName == null) {
                    if (valueAsAttribute) {
                        throw new IllegalArgumentException("Cannot write value as attribute without name");
                    } else if (!keyAsAttribute) {
                        throw new IllegalArgumentException("Cannot write value as text of entry, if key is also child element");
                    }
                }
                if (keyAsAttribute && valueAsAttribute && keyName.equals(valueName)) {
                    throw new IllegalArgumentException("Cannot write key and value with same attribute name");
                }
                return;
            }
        }
        throw new IllegalArgumentException("Class types of key and value are mandatory");
    }

    public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
        SingleValueConverter valueConverter;
        Iterator iterator;
        Iterator iterator2;
        Entry entry;
        Object key;
        Object value;
        HierarchicalStreamWriter hierarchicalStreamWriter = writer;
        Map map = (Map) source;
        SingleValueConverter keyConverter = null;
        SingleValueConverter valueConverter2 = null;
        if (this.keyAsAttribute) {
            keyConverter = getSingleValueConverter(r6.keyType);
        }
        SingleValueConverter keyConverter2 = keyConverter;
        if (!r6.valueAsAttribute) {
            if (r6.valueName == null) {
            }
            valueConverter = valueConverter2;
            iterator = map.entrySet().iterator();
            while (true) {
                iterator2 = iterator;
                if (iterator2.hasNext()) {
                    entry = (Entry) iterator2.next();
                    key = entry.getKey();
                    value = entry.getValue();
                    if (r6.entryName != null) {
                        ExtendedHierarchicalStreamWriterHelper.startNode(hierarchicalStreamWriter, r6.entryName, entry.getClass());
                        if (!(keyConverter2 == null || key == null)) {
                            hierarchicalStreamWriter.addAttribute(r6.keyName, keyConverter2.toString(key));
                        }
                        if (!(r6.valueName == null || valueConverter == null || value == null)) {
                            hierarchicalStreamWriter.addAttribute(r6.valueName, valueConverter.toString(value));
                        }
                    }
                    if (keyConverter2 == null) {
                        writeItem(r6.keyName, r6.keyType, key, context, hierarchicalStreamWriter);
                    }
                    if (valueConverter == null) {
                        writeItem(r6.valueName, r6.valueType, value, context, hierarchicalStreamWriter);
                    } else if (r6.valueName == null) {
                        hierarchicalStreamWriter.setValue(valueConverter.toString(value));
                    }
                    if (r6.entryName != null) {
                        writer.endNode();
                    }
                    iterator = iterator2;
                } else {
                    return;
                }
            }
        }
        valueConverter2 = getSingleValueConverter(r6.valueType);
        valueConverter = valueConverter2;
        iterator = map.entrySet().iterator();
        while (true) {
            iterator2 = iterator;
            if (iterator2.hasNext()) {
                entry = (Entry) iterator2.next();
                key = entry.getKey();
                value = entry.getValue();
                if (r6.entryName != null) {
                    ExtendedHierarchicalStreamWriterHelper.startNode(hierarchicalStreamWriter, r6.entryName, entry.getClass());
                    hierarchicalStreamWriter.addAttribute(r6.keyName, keyConverter2.toString(key));
                    hierarchicalStreamWriter.addAttribute(r6.valueName, valueConverter.toString(value));
                }
                if (keyConverter2 == null) {
                    writeItem(r6.keyName, r6.keyType, key, context, hierarchicalStreamWriter);
                }
                if (valueConverter == null) {
                    writeItem(r6.valueName, r6.valueType, value, context, hierarchicalStreamWriter);
                } else if (r6.valueName == null) {
                    hierarchicalStreamWriter.setValue(valueConverter.toString(value));
                }
                if (r6.entryName != null) {
                    writer.endNode();
                }
                iterator = iterator2;
            } else {
                return;
            }
        }
    }

    protected void populateMap(HierarchicalStreamReader reader, UnmarshallingContext context, Map map, Map target) {
        SingleValueConverter keyConverter = null;
        SingleValueConverter valueConverter = null;
        if (this.keyAsAttribute) {
            keyConverter = getSingleValueConverter(this.keyType);
        }
        if (this.valueAsAttribute || this.valueName == null) {
            valueConverter = getSingleValueConverter(this.valueType);
        }
        while (reader.hasMoreChildren()) {
            Object key = null;
            Object value = null;
            if (this.entryName != null) {
                String attribute;
                reader.moveDown();
                if (keyConverter != null) {
                    attribute = reader.getAttribute(this.keyName);
                    if (attribute != null) {
                        key = keyConverter.fromString(attribute);
                    }
                }
                if (this.valueAsAttribute && valueConverter != null) {
                    attribute = reader.getAttribute(this.valueName);
                    if (attribute != null) {
                        value = valueConverter.fromString(attribute);
                    }
                }
            }
            if (keyConverter == null) {
                reader.moveDown();
                if (valueConverter == null && !this.keyName.equals(this.valueName) && reader.getNodeName().equals(this.valueName)) {
                    value = readItem(this.valueType, reader, context, map);
                } else {
                    key = readItem(this.keyType, reader, context, map);
                }
                reader.moveUp();
            }
            if (valueConverter == null) {
                reader.moveDown();
                if (keyConverter == null && key == null && value != null) {
                    key = readItem(this.keyType, reader, context, map);
                } else {
                    value = readItem(this.valueType, reader, context, map);
                }
                reader.moveUp();
            } else if (!this.valueAsAttribute) {
                value = reader.getValue();
            }
            target.put(key, value);
            if (this.entryName != null) {
                reader.moveUp();
            }
        }
    }

    private SingleValueConverter getSingleValueConverter(Class type) {
        SingleValueConverter conv = (UseAttributeForEnumMapper.isEnum(type) ? this.enumMapper : mapper()).getConverterFromItemType(null, type, null);
        if (conv != null) {
            return conv;
        }
        Converter converter = this.lookup.lookupConverterForType(type);
        if (converter instanceof SingleValueConverter) {
            return (SingleValueConverter) converter;
        }
        throw new ConversionException("No SingleValueConverter for key available");
    }

    protected void writeItem(String name, Class type, Object item, MarshallingContext context, HierarchicalStreamWriter writer) {
        Class itemType = item == null ? Null.class : item.getClass();
        ExtendedHierarchicalStreamWriterHelper.startNode(writer, name, itemType);
        if (!itemType.equals(type)) {
            String attributeName = mapper().aliasForSystemAttribute("class");
            if (attributeName != null) {
                writer.addAttribute(attributeName, mapper().serializedClass(itemType));
            }
        }
        if (item != null) {
            context.convertAnother(item);
        }
        writer.endNode();
    }

    protected Object readItem(Class type, HierarchicalStreamReader reader, UnmarshallingContext context, Object current) {
        String className = HierarchicalStreams.readClassAttribute(reader, mapper());
        Class itemType = className == null ? type : mapper().realClass(className);
        if (Null.class.equals(itemType)) {
            return null;
        }
        return context.convertAnother(current, itemType);
    }
}
