package com.badlogic.gdx.utils;

import com.facebook.internal.ServerProtocol;
import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.regex.Pattern;
import kotlin.text.Typography;
import org.catrobat.catroid.physics.PhysicsCollision;

public class JsonWriter extends Writer {
    private JsonObject current;
    private boolean named;
    private OutputType outputType = OutputType.json;
    private boolean quoteLongValues = false;
    private final Array<JsonObject> stack = new Array();
    final Writer writer;

    private class JsonObject {
        final boolean array;
        boolean needsComma;

        JsonObject(boolean array) throws IOException {
            this.array = array;
            JsonWriter.this.writer.write(array ? 91 : 123);
        }

        void close() throws IOException {
            JsonWriter.this.writer.write(this.array ? 93 : 125);
        }
    }

    public enum OutputType {
        json,
        javascript,
        minimal;
        
        private static Pattern javascriptPattern;
        private static Pattern minimalNamePattern;
        private static Pattern minimalValuePattern;

        static {
            javascriptPattern = Pattern.compile("^[a-zA-Z_$][a-zA-Z_$0-9]*$");
            minimalNamePattern = Pattern.compile("^[^\":,}/ ][^:]*$");
            minimalValuePattern = Pattern.compile("^[^\":,{\\[\\]/ ][^}\\],]*$");
        }

        public String quoteValue(Object value) {
            if (value == null) {
                return "null";
            }
            String string = value.toString();
            if (!(value instanceof Number)) {
                if (!(value instanceof Boolean)) {
                    string = string.replace("\\", "\\\\").replace("\r", "\\r").replace("\n", "\\n").replace(PhysicsCollision.COLLISION_MESSAGE_ESCAPE_CHAR, "\\t");
                    if (!(this != minimal || string.equals(ServerProtocol.DIALOG_RETURN_SCOPES_TRUE) || string.equals("false") || string.equals("null") || string.contains("//") || string.contains("/*"))) {
                        int length = string.length();
                        if (length > 0 && string.charAt(length - 1) != ' ' && minimalValuePattern.matcher(string).matches()) {
                            return string;
                        }
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(Typography.quote);
                    stringBuilder.append(string.replace("\"", "\\\""));
                    stringBuilder.append(Typography.quote);
                    return stringBuilder.toString();
                }
            }
            return string;
        }

        public String quoteName(String value) {
            value = value.replace("\\", "\\\\").replace("\r", "\\r").replace("\n", "\\n").replace(PhysicsCollision.COLLISION_MESSAGE_ESCAPE_CHAR, "\\t");
            switch (this) {
                case minimal:
                    if (!(value.contains("//") || value.contains("/*") || !minimalNamePattern.matcher(value).matches())) {
                        return value;
                    }
                case javascript:
                    break;
                default:
                    break;
            }
            if (javascriptPattern.matcher(value).matches()) {
                return value;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(Typography.quote);
            stringBuilder.append(value.replace("\"", "\\\""));
            stringBuilder.append(Typography.quote);
            return stringBuilder.toString();
        }
    }

    public JsonWriter(Writer writer) {
        this.writer = writer;
    }

    public Writer getWriter() {
        return this.writer;
    }

    public void setOutputType(OutputType outputType) {
        this.outputType = outputType;
    }

    public void setQuoteLongValues(boolean quoteLongValues) {
        this.quoteLongValues = quoteLongValues;
    }

    public JsonWriter name(String name) throws IOException {
        if (this.current != null) {
            if (!this.current.array) {
                if (this.current.needsComma) {
                    this.writer.write(44);
                } else {
                    this.current.needsComma = true;
                }
                this.writer.write(this.outputType.quoteName(name));
                this.writer.write(58);
                this.named = true;
                return this;
            }
        }
        throw new IllegalStateException("Current item must be an object.");
    }

    public JsonWriter object() throws IOException {
        requireCommaOrName();
        Array array = this.stack;
        JsonObject jsonObject = new JsonObject(false);
        this.current = jsonObject;
        array.add(jsonObject);
        return this;
    }

    public JsonWriter array() throws IOException {
        requireCommaOrName();
        Array array = this.stack;
        JsonObject jsonObject = new JsonObject(true);
        this.current = jsonObject;
        array.add(jsonObject);
        return this;
    }

    public JsonWriter value(Object value) throws IOException {
        if (this.quoteLongValues && ((value instanceof Long) || (value instanceof Double) || (value instanceof BigDecimal) || (value instanceof BigInteger))) {
            value = value.toString();
        } else if (value instanceof Number) {
            Number number = (Number) value;
            long longValue = number.longValue();
            if (number.doubleValue() == ((double) longValue)) {
                value = Long.valueOf(longValue);
            }
        }
        requireCommaOrName();
        this.writer.write(this.outputType.quoteValue(value));
        return this;
    }

    public JsonWriter json(String json) throws IOException {
        requireCommaOrName();
        this.writer.write(json);
        return this;
    }

    private void requireCommaOrName() throws IOException {
        if (this.current != null) {
            if (this.current.array) {
                if (this.current.needsComma) {
                    this.writer.write(44);
                } else {
                    this.current.needsComma = true;
                }
            } else if (this.named) {
                this.named = false;
            } else {
                throw new IllegalStateException("Name must be set.");
            }
        }
    }

    public JsonWriter object(String name) throws IOException {
        return name(name).object();
    }

    public JsonWriter array(String name) throws IOException {
        return name(name).array();
    }

    public JsonWriter set(String name, Object value) throws IOException {
        return name(name).value(value);
    }

    public JsonWriter json(String name, String json) throws IOException {
        return name(name).json(json);
    }

    public JsonWriter pop() throws IOException {
        if (this.named) {
            throw new IllegalStateException("Expected an object, array, or value since a name was set.");
        }
        ((JsonObject) this.stack.pop()).close();
        this.current = this.stack.size == 0 ? null : (JsonObject) this.stack.peek();
        return this;
    }

    public void write(char[] cbuf, int off, int len) throws IOException {
        this.writer.write(cbuf, off, len);
    }

    public void flush() throws IOException {
        this.writer.flush();
    }

    public void close() throws IOException {
        while (this.stack.size > 0) {
            pop();
        }
        this.writer.close();
    }
}
