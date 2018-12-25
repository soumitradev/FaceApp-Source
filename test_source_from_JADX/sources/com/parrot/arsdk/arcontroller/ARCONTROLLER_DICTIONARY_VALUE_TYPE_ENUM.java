package com.parrot.arsdk.arcontroller;

import java.util.HashMap;

public enum ARCONTROLLER_DICTIONARY_VALUE_TYPE_ENUM {
    eARCONTROLLER_DICTIONARY_VALUE_TYPE_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCONTROLLER_DICTIONARY_VALUE_TYPE_U8(0),
    ARCONTROLLER_DICTIONARY_VALUE_TYPE_I8(1),
    ARCONTROLLER_DICTIONARY_VALUE_TYPE_U16(2),
    ARCONTROLLER_DICTIONARY_VALUE_TYPE_I16(3),
    ARCONTROLLER_DICTIONARY_VALUE_TYPE_U32(4),
    ARCONTROLLER_DICTIONARY_VALUE_TYPE_I32(5),
    ARCONTROLLER_DICTIONARY_VALUE_TYPE_U64(6),
    ARCONTROLLER_DICTIONARY_VALUE_TYPE_I64(7),
    ARCONTROLLER_DICTIONARY_VALUE_TYPE_FLOAT(8),
    ARCONTROLLER_DICTIONARY_VALUE_TYPE_DOUBLE(9),
    ARCONTROLLER_DICTIONARY_VALUE_TYPE_STRING(10),
    ARCONTROLLER_DICTIONARY_VALUE_TYPE_ENUM(11, "enumeration relative to the commands. must be read as I32 type."),
    ARCONTROLLER_DICTIONARY_VALUE_TYPE_MAX(12, "Max of the enumeration");
    
    static HashMap<Integer, ARCONTROLLER_DICTIONARY_VALUE_TYPE_ENUM> valuesList;
    private final String comment;
    private final int value;

    private ARCONTROLLER_DICTIONARY_VALUE_TYPE_ENUM(int value) {
        this.value = value;
        this.comment = null;
    }

    private ARCONTROLLER_DICTIONARY_VALUE_TYPE_ENUM(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static ARCONTROLLER_DICTIONARY_VALUE_TYPE_ENUM getFromValue(int value) {
        ARCONTROLLER_DICTIONARY_VALUE_TYPE_ENUM arcontroller_dictionary_value_type_enum;
        if (valuesList == null) {
            arcontroller_dictionary_value_type_enum = ARCONTROLLER_DICTIONARY_VALUE_TYPE_ENUM;
            ARCONTROLLER_DICTIONARY_VALUE_TYPE_ENUM[] valuesArray = values();
            valuesList = new HashMap(valuesArray.length);
            for (ARCONTROLLER_DICTIONARY_VALUE_TYPE_ENUM entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        arcontroller_dictionary_value_type_enum = (ARCONTROLLER_DICTIONARY_VALUE_TYPE_ENUM) valuesList.get(Integer.valueOf(value));
        if (arcontroller_dictionary_value_type_enum == null) {
            return eARCONTROLLER_DICTIONARY_VALUE_TYPE_UNKNOWN_ENUM_VALUE;
        }
        return arcontroller_dictionary_value_type_enum;
    }

    public String toString() {
        if (this.comment != null) {
            return this.comment;
        }
        return super.toString();
    }
}
