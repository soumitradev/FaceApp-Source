package com.thoughtworks.xstream.io.json;

import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.AbstractJsonWriter.Type;
import com.thoughtworks.xstream.io.naming.NameCoder;
import com.thoughtworks.xstream.io.naming.NoNameCoder;
import java.io.Writer;
import kotlin.text.Typography;

public class JsonWriter extends AbstractJsonWriter {
    private int depth;
    protected final Format format;
    private boolean newLineProposed;
    protected final QuickWriter writer;

    public static class Format {
        public static int COMPACT_EMPTY_ELEMENT = 2;
        public static int SPACE_AFTER_LABEL = 1;
        private char[] lineIndenter;
        private final int mode;
        private final NameCoder nameCoder;
        private char[] newLine;

        public Format() {
            this(new char[]{' ', ' '}, new char[]{'\n'}, SPACE_AFTER_LABEL | COMPACT_EMPTY_ELEMENT);
        }

        public Format(char[] lineIndenter, char[] newLine, int mode) {
            this(lineIndenter, newLine, mode, new NoNameCoder());
        }

        public Format(char[] lineIndenter, char[] newLine, int mode, NameCoder nameCoder) {
            this.lineIndenter = lineIndenter;
            this.newLine = newLine;
            this.mode = mode;
            this.nameCoder = nameCoder;
        }

        public char[] getLineIndenter() {
            return this.lineIndenter;
        }

        public char[] getNewLine() {
            return this.newLine;
        }

        public int mode() {
            return this.mode;
        }

        public NameCoder getNameCoder() {
            return this.nameCoder;
        }
    }

    public JsonWriter(Writer writer, char[] lineIndenter, String newLine) {
        this(writer, 0, new Format(lineIndenter, newLine.toCharArray(), Format.SPACE_AFTER_LABEL | Format.COMPACT_EMPTY_ELEMENT));
    }

    public JsonWriter(Writer writer, char[] lineIndenter) {
        this(writer, 0, new Format(lineIndenter, new char[]{'\n'}, Format.SPACE_AFTER_LABEL | Format.COMPACT_EMPTY_ELEMENT));
    }

    public JsonWriter(Writer writer, String lineIndenter, String newLine) {
        this(writer, 0, new Format(lineIndenter.toCharArray(), newLine.toCharArray(), Format.SPACE_AFTER_LABEL | Format.COMPACT_EMPTY_ELEMENT));
    }

    public JsonWriter(Writer writer, String lineIndenter) {
        this(writer, 0, new Format(lineIndenter.toCharArray(), new char[]{'\n'}, Format.SPACE_AFTER_LABEL | Format.COMPACT_EMPTY_ELEMENT));
    }

    public JsonWriter(Writer writer) {
        this(writer, 0, new Format(new char[]{' ', ' '}, new char[]{'\n'}, Format.SPACE_AFTER_LABEL | Format.COMPACT_EMPTY_ELEMENT));
    }

    public JsonWriter(Writer writer, char[] lineIndenter, String newLine, int mode) {
        this(writer, mode, new Format(lineIndenter, newLine.toCharArray(), Format.SPACE_AFTER_LABEL | Format.COMPACT_EMPTY_ELEMENT));
    }

    public JsonWriter(Writer writer, int mode) {
        this(writer, mode, new Format());
    }

    public JsonWriter(Writer writer, Format format) {
        this(writer, 0, format);
    }

    public JsonWriter(Writer writer, int mode, Format format) {
        this(writer, mode, format, 1024);
    }

    public JsonWriter(Writer writer, int mode, Format format, int bufferSize) {
        super(mode, format.getNameCoder());
        this.writer = new QuickWriter(writer, bufferSize);
        this.format = format;
        this.depth = (mode & 1) == 0 ? -1 : 0;
    }

    public void flush() {
        this.writer.flush();
    }

    public void close() {
        this.writer.close();
    }

    public HierarchicalStreamWriter underlyingWriter() {
        return this;
    }

    protected void startObject() {
        if (this.newLineProposed) {
            writeNewLine();
        }
        this.writer.write('{');
        startNewLine();
    }

    protected void addLabel(String name) {
        if (this.newLineProposed) {
            writeNewLine();
        }
        this.writer.write((char) Typography.quote);
        writeText(name);
        this.writer.write("\":");
        if ((this.format.mode() & Format.SPACE_AFTER_LABEL) != 0) {
            this.writer.write(' ');
        }
    }

    protected void addValue(String value, Type type) {
        if (this.newLineProposed) {
            writeNewLine();
        }
        if (type == Type.STRING) {
            this.writer.write((char) Typography.quote);
        }
        writeText(value);
        if (type == Type.STRING) {
            this.writer.write((char) Typography.quote);
        }
    }

    protected void startArray() {
        if (this.newLineProposed) {
            writeNewLine();
        }
        this.writer.write("[");
        startNewLine();
    }

    protected void nextElement() {
        this.writer.write(",");
        writeNewLine();
    }

    protected void endArray() {
        endNewLine();
        this.writer.write("]");
    }

    protected void endObject() {
        endNewLine();
        this.writer.write("}");
    }

    private void startNewLine() {
        int i = this.depth + 1;
        this.depth = i;
        if (i > 0) {
            this.newLineProposed = true;
        }
    }

    private void endNewLine() {
        int i = this.depth;
        this.depth = i - 1;
        if (i <= 0) {
            return;
        }
        if ((this.format.mode() & Format.COMPACT_EMPTY_ELEMENT) == 0 || !this.newLineProposed) {
            writeNewLine();
        } else {
            this.newLineProposed = false;
        }
    }

    private void writeNewLine() {
        int depth = this.depth;
        this.writer.write(this.format.getNewLine());
        while (true) {
            int depth2 = depth - 1;
            if (depth > 0) {
                this.writer.write(this.format.getLineIndenter());
                depth = depth2;
            } else {
                this.newLineProposed = false;
                return;
            }
        }
    }

    private void writeText(String text) {
        int length = text.length();
        for (int i = 0; i < length; i++) {
            char c = text.charAt(i);
            if (c == Typography.quote) {
                this.writer.write("\\\"");
            } else if (c != '\\') {
                switch (c) {
                    case '\b':
                        this.writer.write("\\b");
                        break;
                    case '\t':
                        this.writer.write("\\t");
                        break;
                    case '\n':
                        this.writer.write("\\n");
                        break;
                    default:
                        switch (c) {
                            case '\f':
                                this.writer.write("\\f");
                                break;
                            case '\r':
                                this.writer.write("\\r");
                                break;
                            default:
                                if (c <= '\u001f') {
                                    this.writer.write("\\u");
                                    String hex = new StringBuilder();
                                    hex.append("000");
                                    hex.append(Integer.toHexString(c));
                                    hex = hex.toString();
                                    this.writer.write(hex.substring(hex.length() - 4));
                                    break;
                                }
                                this.writer.write(c);
                                break;
                        }
                }
            } else {
                this.writer.write("\\\\");
            }
        }
    }
}
