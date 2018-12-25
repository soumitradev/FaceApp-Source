package com.badlogic.gdx.utils;

import com.badlogic.gdx.utils.JsonWriter.OutputType;
import com.facebook.internal.ServerProtocol;
import org.catrobat.catroid.common.BrickValues;
import org.catrobat.catroid.common.Constants;

public class JsonValue implements Iterable<JsonValue> {
    public JsonValue child;
    private double doubleValue;
    private long longValue;
    public String name;
    public JsonValue next;
    public JsonValue prev;
    public int size;
    private String stringValue;
    private JsonValue$ValueType type;

    public JsonValue(JsonValue$ValueType type) {
        this.type = type;
    }

    public JsonValue(String value) {
        set(value);
    }

    public JsonValue(double value) {
        set(value, null);
    }

    public JsonValue(long value) {
        set(value, null);
    }

    public JsonValue(double value, String stringValue) {
        set(value, stringValue);
    }

    public JsonValue(long value, String stringValue) {
        set(value, stringValue);
    }

    public JsonValue(boolean value) {
        set(value);
    }

    public JsonValue get(int index) {
        JsonValue current = this.child;
        while (current != null && index > 0) {
            index--;
            current = current.next;
        }
        return current;
    }

    public JsonValue get(String name) {
        JsonValue current = this.child;
        while (current != null && !current.name.equalsIgnoreCase(name)) {
            current = current.next;
        }
        return current;
    }

    public boolean has(String name) {
        return get(name) != null;
    }

    public JsonValue require(int index) {
        JsonValue current = this.child;
        while (current != null && index > 0) {
            index--;
            current = current.next;
        }
        if (current != null) {
            return current;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Child not found with index: ");
        stringBuilder.append(index);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public JsonValue require(String name) {
        JsonValue current = this.child;
        while (current != null && !current.name.equalsIgnoreCase(name)) {
            current = current.next;
        }
        if (current != null) {
            return current;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Child not found with name: ");
        stringBuilder.append(name);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public JsonValue remove(int index) {
        JsonValue child = get(index);
        if (child == null) {
            return null;
        }
        if (child.prev == null) {
            this.child = child.next;
            if (this.child != null) {
                this.child.prev = null;
            }
        } else {
            child.prev.next = child.next;
            if (child.next != null) {
                child.next.prev = child.prev;
            }
        }
        this.size--;
        return child;
    }

    public JsonValue remove(String name) {
        JsonValue child = get(name);
        if (child == null) {
            return null;
        }
        if (child.prev == null) {
            this.child = child.next;
            if (this.child != null) {
                this.child.prev = null;
            }
        } else {
            child.prev.next = child.next;
            if (child.next != null) {
                child.next.prev = child.prev;
            }
        }
        this.size--;
        return child;
    }

    @Deprecated
    public int size() {
        return this.size;
    }

    public String asString() {
        switch (JsonValue$1.$SwitchMap$com$badlogic$gdx$utils$JsonValue$ValueType[this.type.ordinal()]) {
            case 1:
                return this.stringValue;
            case 2:
                return this.stringValue != null ? this.stringValue : Double.toString(this.doubleValue);
            case 3:
                return this.stringValue != null ? this.stringValue : Long.toString(this.longValue);
            case 4:
                return this.longValue != 0 ? ServerProtocol.DIALOG_RETURN_SCOPES_TRUE : "false";
            case 5:
                return null;
            default:
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Value cannot be converted to string: ");
                stringBuilder.append(this.type);
                throw new IllegalStateException(stringBuilder.toString());
        }
    }

    public float asFloat() {
        switch (JsonValue$1.$SwitchMap$com$badlogic$gdx$utils$JsonValue$ValueType[this.type.ordinal()]) {
            case 1:
                return Float.parseFloat(this.stringValue);
            case 2:
                return (float) this.doubleValue;
            case 3:
                return (float) this.longValue;
            case 4:
                return this.longValue != 0 ? 1.0f : 0.0f;
            default:
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Value cannot be converted to float: ");
                stringBuilder.append(this.type);
                throw new IllegalStateException(stringBuilder.toString());
        }
    }

    public double asDouble() {
        switch (JsonValue$1.$SwitchMap$com$badlogic$gdx$utils$JsonValue$ValueType[this.type.ordinal()]) {
            case 1:
                return Double.parseDouble(this.stringValue);
            case 2:
                return this.doubleValue;
            case 3:
                return (double) this.longValue;
            case 4:
                return this.longValue != 0 ? 1.0d : BrickValues.SET_COLOR_TO;
            default:
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Value cannot be converted to double: ");
                stringBuilder.append(this.type);
                throw new IllegalStateException(stringBuilder.toString());
        }
    }

    public long asLong() {
        switch (JsonValue$1.$SwitchMap$com$badlogic$gdx$utils$JsonValue$ValueType[this.type.ordinal()]) {
            case 1:
                return Long.parseLong(this.stringValue);
            case 2:
                return (long) this.doubleValue;
            case 3:
                return this.longValue;
            case 4:
                long j = 0;
                if (this.longValue != 0) {
                    j = 1;
                }
                return j;
            default:
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Value cannot be converted to long: ");
                stringBuilder.append(this.type);
                throw new IllegalStateException(stringBuilder.toString());
        }
    }

    public int asInt() {
        switch (JsonValue$1.$SwitchMap$com$badlogic$gdx$utils$JsonValue$ValueType[this.type.ordinal()]) {
            case 1:
                return Integer.parseInt(this.stringValue);
            case 2:
                return (int) this.doubleValue;
            case 3:
                return (int) this.longValue;
            case 4:
                return this.longValue != 0 ? 1 : 0;
            default:
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Value cannot be converted to int: ");
                stringBuilder.append(this.type);
                throw new IllegalStateException(stringBuilder.toString());
        }
    }

    public boolean asBoolean() {
        boolean z = false;
        switch (JsonValue$1.$SwitchMap$com$badlogic$gdx$utils$JsonValue$ValueType[this.type.ordinal()]) {
            case 1:
                return this.stringValue.equalsIgnoreCase(ServerProtocol.DIALOG_RETURN_SCOPES_TRUE);
            case 2:
                if (this.doubleValue != BrickValues.SET_COLOR_TO) {
                    z = true;
                }
                return z;
            case 3:
                if (this.longValue != 0) {
                    z = true;
                }
                return z;
            case 4:
                if (this.longValue != 0) {
                    z = true;
                }
                return z;
            default:
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Value cannot be converted to boolean: ");
                stringBuilder.append(this.type);
                throw new IllegalStateException(stringBuilder.toString());
        }
    }

    public byte asByte() {
        switch (JsonValue$1.$SwitchMap$com$badlogic$gdx$utils$JsonValue$ValueType[this.type.ordinal()]) {
            case 1:
                return Byte.parseByte(this.stringValue);
            case 2:
                return (byte) ((int) this.doubleValue);
            case 3:
                return (byte) ((int) this.longValue);
            case 4:
                return this.longValue != 0 ? (byte) 1 : (byte) 0;
            default:
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Value cannot be converted to byte: ");
                stringBuilder.append(this.type);
                throw new IllegalStateException(stringBuilder.toString());
        }
    }

    public short asShort() {
        switch (JsonValue$1.$SwitchMap$com$badlogic$gdx$utils$JsonValue$ValueType[this.type.ordinal()]) {
            case 1:
                return Short.parseShort(this.stringValue);
            case 2:
                return (short) ((int) this.doubleValue);
            case 3:
                return (short) ((int) this.longValue);
            case 4:
                return this.longValue != 0 ? (short) 1 : (short) 0;
            default:
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Value cannot be converted to short: ");
                stringBuilder.append(this.type);
                throw new IllegalStateException(stringBuilder.toString());
        }
    }

    public char asChar() {
        char c = '\u0000';
        switch (JsonValue$1.$SwitchMap$com$badlogic$gdx$utils$JsonValue$ValueType[this.type.ordinal()]) {
            case 1:
                if (this.stringValue.length() != 0) {
                    c = this.stringValue.charAt(0);
                }
                return c;
            case 2:
                return (char) ((int) this.doubleValue);
            case 3:
                return (char) ((int) this.longValue);
            case 4:
                if (this.longValue != 0) {
                    c = '\u0001';
                }
                return c;
            default:
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Value cannot be converted to char: ");
                stringBuilder.append(this.type);
                throw new IllegalStateException(stringBuilder.toString());
        }
    }

    public String[] asStringArray() {
        if (this.type != JsonValue$ValueType.array) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Value is not an array: ");
            stringBuilder.append(this.type);
            throw new IllegalStateException(stringBuilder.toString());
        }
        String[] array = new String[this.size];
        int i = 0;
        JsonValue value = this.child;
        while (value != null) {
            String v;
            switch (JsonValue$1.$SwitchMap$com$badlogic$gdx$utils$JsonValue$ValueType[value.type.ordinal()]) {
                case 1:
                    v = value.stringValue;
                    break;
                case 2:
                    v = this.stringValue != null ? this.stringValue : Double.toString(value.doubleValue);
                    break;
                case 3:
                    v = this.stringValue != null ? this.stringValue : Long.toString(value.longValue);
                    break;
                case 4:
                    v = value.longValue != 0 ? ServerProtocol.DIALOG_RETURN_SCOPES_TRUE : "false";
                    break;
                case 5:
                    v = null;
                    break;
                default:
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("Value cannot be converted to string: ");
                    stringBuilder2.append(value.type);
                    throw new IllegalStateException(stringBuilder2.toString());
            }
            array[i] = v;
            value = value.next;
            i++;
        }
        return array;
    }

    public float[] asFloatArray() {
        if (this.type != JsonValue$ValueType.array) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Value is not an array: ");
            stringBuilder.append(this.type);
            throw new IllegalStateException(stringBuilder.toString());
        }
        float[] array = new float[this.size];
        int i = 0;
        JsonValue value = this.child;
        while (value != null) {
            float v;
            switch (JsonValue$1.$SwitchMap$com$badlogic$gdx$utils$JsonValue$ValueType[value.type.ordinal()]) {
                case 1:
                    v = Float.parseFloat(value.stringValue);
                    break;
                case 2:
                    v = (float) value.doubleValue;
                    break;
                case 3:
                    v = (float) value.longValue;
                    break;
                case 4:
                    v = value.longValue != 0 ? 1.0f : 0.0f;
                    break;
                default:
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("Value cannot be converted to float: ");
                    stringBuilder2.append(value.type);
                    throw new IllegalStateException(stringBuilder2.toString());
            }
            array[i] = v;
            value = value.next;
            i++;
        }
        return array;
    }

    public double[] asDoubleArray() {
        if (this.type != JsonValue$ValueType.array) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Value is not an array: ");
            stringBuilder.append(this.type);
            throw new IllegalStateException(stringBuilder.toString());
        }
        double[] array = new double[this.size];
        int i = 0;
        JsonValue value = this.child;
        double v = BrickValues.SET_COLOR_TO;
        while (value != null) {
            switch (JsonValue$1.$SwitchMap$com$badlogic$gdx$utils$JsonValue$ValueType[value.type.ordinal()]) {
                case 1:
                    v = Double.parseDouble(value.stringValue);
                    break;
                case 2:
                    v = value.doubleValue;
                    break;
                case 3:
                    v = (double) value.longValue;
                    break;
                case 4:
                    v = value.longValue != 0 ? 1.0d : 0.0d;
                    break;
                default:
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("Value cannot be converted to double: ");
                    stringBuilder2.append(value.type);
                    throw new IllegalStateException(stringBuilder2.toString());
            }
            array[i] = v;
            value = value.next;
            i++;
        }
        return array;
    }

    public long[] asLongArray() {
        if (this.type != JsonValue$ValueType.array) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Value is not an array: ");
            stringBuilder.append(this.type);
            throw new IllegalStateException(stringBuilder.toString());
        }
        long[] array = new long[this.size];
        int i = 0;
        JsonValue value = this.child;
        long v = 0;
        while (value != null) {
            switch (JsonValue$1.$SwitchMap$com$badlogic$gdx$utils$JsonValue$ValueType[value.type.ordinal()]) {
                case 1:
                    v = Long.parseLong(value.stringValue);
                    break;
                case 2:
                    v = (long) value.doubleValue;
                    break;
                case 3:
                    v = value.longValue;
                    break;
                case 4:
                    v = value.longValue != 0 ? 1 : 0;
                    break;
                default:
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("Value cannot be converted to long: ");
                    stringBuilder2.append(value.type);
                    throw new IllegalStateException(stringBuilder2.toString());
            }
            array[i] = v;
            value = value.next;
            i++;
        }
        return array;
    }

    public int[] asIntArray() {
        if (this.type != JsonValue$ValueType.array) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Value is not an array: ");
            stringBuilder.append(this.type);
            throw new IllegalStateException(stringBuilder.toString());
        }
        int[] array = new int[this.size];
        int i = 0;
        JsonValue value = this.child;
        while (value != null) {
            int v;
            switch (JsonValue$1.$SwitchMap$com$badlogic$gdx$utils$JsonValue$ValueType[value.type.ordinal()]) {
                case 1:
                    v = Integer.parseInt(value.stringValue);
                    break;
                case 2:
                    v = (int) value.doubleValue;
                    break;
                case 3:
                    v = (int) value.longValue;
                    break;
                case 4:
                    v = value.longValue != 0 ? 1 : 0;
                    break;
                default:
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("Value cannot be converted to int: ");
                    stringBuilder2.append(value.type);
                    throw new IllegalStateException(stringBuilder2.toString());
            }
            array[i] = v;
            value = value.next;
            i++;
        }
        return array;
    }

    public boolean[] asBooleanArray() {
        if (this.type != JsonValue$ValueType.array) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Value is not an array: ");
            stringBuilder.append(this.type);
            throw new IllegalStateException(stringBuilder.toString());
        }
        boolean[] array = new boolean[this.size];
        int i = 0;
        JsonValue value = this.child;
        while (value != null) {
            boolean v;
            boolean z = true;
            switch (JsonValue$1.$SwitchMap$com$badlogic$gdx$utils$JsonValue$ValueType[value.type.ordinal()]) {
                case 1:
                    v = Boolean.parseBoolean(value.stringValue);
                    break;
                case 2:
                    if (value.doubleValue != BrickValues.SET_COLOR_TO) {
                        z = false;
                    }
                    v = z;
                    break;
                case 3:
                    if (value.longValue != 0) {
                        z = false;
                    }
                    v = z;
                    break;
                case 4:
                    if (value.longValue == 0) {
                        z = false;
                    }
                    v = z;
                    break;
                default:
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("Value cannot be converted to boolean: ");
                    stringBuilder2.append(value.type);
                    throw new IllegalStateException(stringBuilder2.toString());
            }
            array[i] = v;
            value = value.next;
            i++;
        }
        return array;
    }

    public byte[] asByteArray() {
        if (this.type != JsonValue$ValueType.array) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Value is not an array: ");
            stringBuilder.append(this.type);
            throw new IllegalStateException(stringBuilder.toString());
        }
        byte[] array = new byte[this.size];
        int i = 0;
        JsonValue value = this.child;
        while (value != null) {
            byte v;
            switch (JsonValue$1.$SwitchMap$com$badlogic$gdx$utils$JsonValue$ValueType[value.type.ordinal()]) {
                case 1:
                    v = Byte.parseByte(value.stringValue);
                    break;
                case 2:
                    v = (byte) ((int) value.doubleValue);
                    break;
                case 3:
                    v = (byte) ((int) value.longValue);
                    break;
                case 4:
                    v = value.longValue != 0 ? (byte) 1 : (byte) 0;
                    break;
                default:
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("Value cannot be converted to byte: ");
                    stringBuilder2.append(value.type);
                    throw new IllegalStateException(stringBuilder2.toString());
            }
            array[i] = v;
            value = value.next;
            i++;
        }
        return array;
    }

    public short[] asShortArray() {
        if (this.type != JsonValue$ValueType.array) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Value is not an array: ");
            stringBuilder.append(this.type);
            throw new IllegalStateException(stringBuilder.toString());
        }
        short[] array = new short[this.size];
        int i = 0;
        JsonValue value = this.child;
        while (value != null) {
            short v;
            switch (JsonValue$1.$SwitchMap$com$badlogic$gdx$utils$JsonValue$ValueType[value.type.ordinal()]) {
                case 1:
                    v = Short.parseShort(value.stringValue);
                    break;
                case 2:
                    v = (short) ((int) value.doubleValue);
                    break;
                case 3:
                    v = (short) ((int) value.longValue);
                    break;
                case 4:
                    v = value.longValue != 0 ? (short) 1 : (short) 0;
                    break;
                default:
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("Value cannot be converted to short: ");
                    stringBuilder2.append(value.type);
                    throw new IllegalStateException(stringBuilder2.toString());
            }
            array[i] = v;
            value = value.next;
            i++;
        }
        return array;
    }

    public char[] asCharArray() {
        if (this.type != JsonValue$ValueType.array) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Value is not an array: ");
            stringBuilder.append(this.type);
            throw new IllegalStateException(stringBuilder.toString());
        }
        char[] array = new char[this.size];
        int i = 0;
        JsonValue value = this.child;
        while (value != null) {
            char v;
            switch (JsonValue$1.$SwitchMap$com$badlogic$gdx$utils$JsonValue$ValueType[value.type.ordinal()]) {
                case 1:
                    v = value.stringValue.length() == 0 ? '\u0000' : value.stringValue.charAt(0);
                    break;
                case 2:
                    v = (char) ((int) value.doubleValue);
                    break;
                case 3:
                    v = (char) ((int) value.longValue);
                    break;
                case 4:
                    v = value.longValue != 0 ? '\u0001' : '\u0000';
                    break;
                default:
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("Value cannot be converted to char: ");
                    stringBuilder2.append(value.type);
                    throw new IllegalStateException(stringBuilder2.toString());
            }
            array[i] = v;
            value = value.next;
            i++;
        }
        return array;
    }

    public boolean hasChild(String name) {
        return getChild(name) != null;
    }

    public JsonValue getChild(String name) {
        JsonValue child = get(name);
        return child == null ? null : child.child;
    }

    public String getString(String name, String defaultValue) {
        JsonValue child = get(name);
        if (child != null && child.isValue()) {
            if (!child.isNull()) {
                return child.asString();
            }
        }
        return defaultValue;
    }

    public float getFloat(String name, float defaultValue) {
        JsonValue child = get(name);
        if (child != null) {
            if (child.isValue()) {
                return child.asFloat();
            }
        }
        return defaultValue;
    }

    public double getDouble(String name, double defaultValue) {
        JsonValue child = get(name);
        if (child != null) {
            if (child.isValue()) {
                return child.asDouble();
            }
        }
        return defaultValue;
    }

    public long getLong(String name, long defaultValue) {
        JsonValue child = get(name);
        if (child != null) {
            if (child.isValue()) {
                return child.asLong();
            }
        }
        return defaultValue;
    }

    public int getInt(String name, int defaultValue) {
        JsonValue child = get(name);
        if (child != null) {
            if (child.isValue()) {
                return child.asInt();
            }
        }
        return defaultValue;
    }

    public boolean getBoolean(String name, boolean defaultValue) {
        JsonValue child = get(name);
        if (child != null) {
            if (child.isValue()) {
                return child.asBoolean();
            }
        }
        return defaultValue;
    }

    public byte getByte(String name, byte defaultValue) {
        JsonValue child = get(name);
        if (child != null) {
            if (child.isValue()) {
                return child.asByte();
            }
        }
        return defaultValue;
    }

    public short getShort(String name, short defaultValue) {
        JsonValue child = get(name);
        if (child != null) {
            if (child.isValue()) {
                return child.asShort();
            }
        }
        return defaultValue;
    }

    public char getChar(String name, char defaultValue) {
        JsonValue child = get(name);
        if (child != null) {
            if (child.isValue()) {
                return child.asChar();
            }
        }
        return defaultValue;
    }

    public String getString(String name) {
        JsonValue child = get(name);
        if (child != null) {
            return child.asString();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Named value not found: ");
        stringBuilder.append(name);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public float getFloat(String name) {
        JsonValue child = get(name);
        if (child != null) {
            return child.asFloat();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Named value not found: ");
        stringBuilder.append(name);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public double getDouble(String name) {
        JsonValue child = get(name);
        if (child != null) {
            return child.asDouble();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Named value not found: ");
        stringBuilder.append(name);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public long getLong(String name) {
        JsonValue child = get(name);
        if (child != null) {
            return child.asLong();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Named value not found: ");
        stringBuilder.append(name);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public int getInt(String name) {
        JsonValue child = get(name);
        if (child != null) {
            return child.asInt();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Named value not found: ");
        stringBuilder.append(name);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public boolean getBoolean(String name) {
        JsonValue child = get(name);
        if (child != null) {
            return child.asBoolean();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Named value not found: ");
        stringBuilder.append(name);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public byte getByte(String name) {
        JsonValue child = get(name);
        if (child != null) {
            return child.asByte();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Named value not found: ");
        stringBuilder.append(name);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public short getShort(String name) {
        JsonValue child = get(name);
        if (child != null) {
            return child.asShort();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Named value not found: ");
        stringBuilder.append(name);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public char getChar(String name) {
        JsonValue child = get(name);
        if (child != null) {
            return child.asChar();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Named value not found: ");
        stringBuilder.append(name);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public String getString(int index) {
        JsonValue child = get(index);
        if (child != null) {
            return child.asString();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Indexed value not found: ");
        stringBuilder.append(this.name);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public float getFloat(int index) {
        JsonValue child = get(index);
        if (child != null) {
            return child.asFloat();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Indexed value not found: ");
        stringBuilder.append(this.name);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public double getDouble(int index) {
        JsonValue child = get(index);
        if (child != null) {
            return child.asDouble();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Indexed value not found: ");
        stringBuilder.append(this.name);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public long getLong(int index) {
        JsonValue child = get(index);
        if (child != null) {
            return child.asLong();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Indexed value not found: ");
        stringBuilder.append(this.name);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public int getInt(int index) {
        JsonValue child = get(index);
        if (child != null) {
            return child.asInt();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Indexed value not found: ");
        stringBuilder.append(this.name);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public boolean getBoolean(int index) {
        JsonValue child = get(index);
        if (child != null) {
            return child.asBoolean();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Indexed value not found: ");
        stringBuilder.append(this.name);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public byte getByte(int index) {
        JsonValue child = get(index);
        if (child != null) {
            return child.asByte();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Indexed value not found: ");
        stringBuilder.append(this.name);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public short getShort(int index) {
        JsonValue child = get(index);
        if (child != null) {
            return child.asShort();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Indexed value not found: ");
        stringBuilder.append(this.name);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public char getChar(int index) {
        JsonValue child = get(index);
        if (child != null) {
            return child.asChar();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Indexed value not found: ");
        stringBuilder.append(this.name);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public JsonValue$ValueType type() {
        return this.type;
    }

    public void setType(JsonValue$ValueType type) {
        if (type == null) {
            throw new IllegalArgumentException("type cannot be null.");
        }
        this.type = type;
    }

    public boolean isArray() {
        return this.type == JsonValue$ValueType.array;
    }

    public boolean isObject() {
        return this.type == JsonValue$ValueType.object;
    }

    public boolean isString() {
        return this.type == JsonValue$ValueType.stringValue;
    }

    public boolean isNumber() {
        if (this.type != JsonValue$ValueType.doubleValue) {
            if (this.type != JsonValue$ValueType.longValue) {
                return false;
            }
        }
        return true;
    }

    public boolean isDouble() {
        return this.type == JsonValue$ValueType.doubleValue;
    }

    public boolean isLong() {
        return this.type == JsonValue$ValueType.longValue;
    }

    public boolean isBoolean() {
        return this.type == JsonValue$ValueType.booleanValue;
    }

    public boolean isNull() {
        return this.type == JsonValue$ValueType.nullValue;
    }

    public boolean isValue() {
        switch (JsonValue$1.$SwitchMap$com$badlogic$gdx$utils$JsonValue$ValueType[this.type.ordinal()]) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                return true;
            default:
                return false;
        }
    }

    public String name() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JsonValue child() {
        return this.child;
    }

    public JsonValue next() {
        return this.next;
    }

    public void setNext(JsonValue next) {
        this.next = next;
    }

    public JsonValue prev() {
        return this.prev;
    }

    public void setPrev(JsonValue prev) {
        this.prev = prev;
    }

    public void set(String value) {
        this.stringValue = value;
        this.type = value == null ? JsonValue$ValueType.nullValue : JsonValue$ValueType.stringValue;
    }

    public void set(double value, String stringValue) {
        this.doubleValue = value;
        this.longValue = (long) value;
        this.stringValue = stringValue;
        this.type = JsonValue$ValueType.doubleValue;
    }

    public void set(long value, String stringValue) {
        this.longValue = value;
        this.doubleValue = (double) value;
        this.stringValue = stringValue;
        this.type = JsonValue$ValueType.longValue;
    }

    public void set(boolean value) {
        this.longValue = value ? 1 : 0;
        this.type = JsonValue$ValueType.booleanValue;
    }

    public String toString() {
        if (isValue()) {
            String asString;
            if (this.name == null) {
                asString = asString();
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(this.name);
                stringBuilder.append(": ");
                stringBuilder.append(asString());
                asString = stringBuilder.toString();
            }
            return asString;
        }
        String str;
        stringBuilder = new StringBuilder();
        if (this.name == null) {
            str = "";
        } else {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append(this.name);
            stringBuilder2.append(": ");
            str = stringBuilder2.toString();
        }
        stringBuilder.append(str);
        stringBuilder.append(prettyPrint(OutputType.minimal, 0));
        return stringBuilder.toString();
    }

    public String prettyPrint(OutputType outputType, int singleLineColumns) {
        JsonValue$PrettyPrintSettings settings = new JsonValue$PrettyPrintSettings();
        settings.outputType = outputType;
        settings.singleLineColumns = singleLineColumns;
        return prettyPrint(settings);
    }

    public String prettyPrint(JsonValue$PrettyPrintSettings settings) {
        StringBuilder buffer = new StringBuilder(512);
        prettyPrint(this, buffer, 0, settings);
        return buffer.toString();
    }

    private void prettyPrint(JsonValue object, StringBuilder buffer, int indent, JsonValue$PrettyPrintSettings settings) {
        OutputType outputType = settings.outputType;
        boolean wrap = true;
        boolean newLines;
        JsonValue child;
        if (object.isObject()) {
            if (object.child == null) {
                buffer.append("{}");
                return;
            }
            newLines = isFlat(object) ^ true;
            int start = buffer.length();
            loop0:
            while (true) {
                buffer.append(newLines ? "{\n" : "{ ");
                child = object.child;
                while (child != null) {
                    if (newLines) {
                        indent(indent, buffer);
                    }
                    buffer.append(outputType.quoteName(child.name));
                    buffer.append(": ");
                    prettyPrint(child, buffer, indent + 1, settings);
                    if (!((newLines && outputType == OutputType.minimal) || child.next == null)) {
                        buffer.append(Constants.REMIX_URL_SEPARATOR);
                    }
                    buffer.append(newLines ? '\n' : ' ');
                    if (newLines || buffer.length() - start <= settings.singleLineColumns) {
                        child = child.next;
                    } else {
                        buffer.setLength(start);
                        newLines = true;
                    }
                }
                break loop0;
            }
            if (newLines) {
                indent(indent - 1, buffer);
            }
            buffer.append('}');
        } else if (object.isArray()) {
            if (object.child == null) {
                buffer.append("[]");
                return;
            }
            newLines = isFlat(object) ^ true;
            if (!settings.wrapNumericArrays) {
                if (isNumeric(object)) {
                    wrap = false;
                }
            }
            int start2 = buffer.length();
            loop2:
            while (true) {
                buffer.append(newLines ? "[\n" : "[ ");
                child = object.child;
                while (child != null) {
                    if (newLines) {
                        indent(indent, buffer);
                    }
                    prettyPrint(child, buffer, indent + 1, settings);
                    if (!((newLines && outputType == OutputType.minimal) || child.next == null)) {
                        buffer.append(Constants.REMIX_URL_SEPARATOR);
                    }
                    buffer.append(newLines ? '\n' : ' ');
                    if (!wrap || newLines || buffer.length() - start2 <= settings.singleLineColumns) {
                        child = child.next;
                    } else {
                        buffer.setLength(start2);
                        newLines = true;
                    }
                }
                break loop2;
            }
            if (newLines) {
                indent(indent - 1, buffer);
            }
            buffer.append(Constants.REMIX_URL_SUFIX_INDICATOR);
        } else if (object.isString()) {
            buffer.append(outputType.quoteValue(object.asString()));
        } else if (object.isDouble()) {
            double doubleValue = object.asDouble();
            long longValue = object.asLong();
            buffer.append(doubleValue == ((double) longValue) ? (double) longValue : doubleValue);
        } else if (object.isLong()) {
            buffer.append(object.asLong());
        } else if (object.isBoolean()) {
            buffer.append(object.asBoolean());
        } else if (object.isNull()) {
            buffer.append("null");
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unknown object type: ");
            stringBuilder.append(object);
            throw new SerializationException(stringBuilder.toString());
        }
    }

    private static boolean isFlat(JsonValue object) {
        JsonValue child = object.child;
        while (child != null) {
            if (!child.isObject()) {
                if (!child.isArray()) {
                    child = child.next;
                }
            }
            return false;
        }
        return true;
    }

    private static boolean isNumeric(JsonValue object) {
        for (JsonValue child = object.child; child != null; child = child.next) {
            if (!child.isNumber()) {
                return false;
            }
        }
        return true;
    }

    private static void indent(int count, StringBuilder buffer) {
        for (int i = 0; i < count; i++) {
            buffer.append('\t');
        }
    }

    public JsonValue$JsonIterator iterator() {
        return new JsonValue$JsonIterator(this);
    }
}
