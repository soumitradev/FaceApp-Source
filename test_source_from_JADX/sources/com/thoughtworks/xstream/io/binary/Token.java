package com.thoughtworks.xstream.io.binary;

import com.thoughtworks.xstream.io.StreamException;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public abstract class Token {
    private static final byte ID_EIGHT_BYTES = (byte) 32;
    private static final byte ID_FOUR_BYTES = (byte) 24;
    private static final byte ID_MASK = (byte) 56;
    private static final byte ID_ONE_BYTE = (byte) 8;
    private static final String ID_SPLITTED = "\u0000‡\u0000";
    private static final byte ID_TWO_BYTES = (byte) 16;
    private static final int MAX_UTF8_LENGTH = 65535;
    public static final byte TYPE_ATTRIBUTE = (byte) 5;
    public static final byte TYPE_END_NODE = (byte) 4;
    public static final byte TYPE_MAP_ID_TO_VALUE = (byte) 2;
    private static final byte TYPE_MASK = (byte) 7;
    public static final byte TYPE_START_NODE = (byte) 3;
    public static final byte TYPE_VALUE = (byte) 6;
    public static final byte TYPE_VERSION = (byte) 1;
    protected long id = -1;
    private final byte type;
    protected String value;

    public static class Formatter {
        public void write(DataOutput out, Token token) throws IOException {
            byte idType;
            long id = token.getId();
            if (id <= 255) {
                idType = (byte) 8;
            } else if (id <= 65535) {
                idType = (byte) 16;
            } else if (id <= 4294967295L) {
                idType = (byte) 24;
            } else {
                idType = (byte) 32;
                out.write(token.getType() + idType);
                token.writeTo(out, idType);
            }
            out.write(token.getType() + idType);
            token.writeTo(out, idType);
        }

        public Token read(DataInput in) throws IOException {
            byte nextByte = in.readByte();
            byte idType = (byte) (nextByte & 56);
            Token token = contructToken((byte) (nextByte & 7));
            token.readFrom(in, idType);
            return token;
        }

        private Token contructToken(byte type) {
            switch (type) {
                case (byte) 2:
                    return new MapIdToValue();
                case (byte) 3:
                    return new StartNode();
                case (byte) 4:
                    return new EndNode();
                case (byte) 5:
                    return new Attribute();
                case (byte) 6:
                    return new Value();
                default:
                    throw new StreamException("Unknown token type");
            }
        }
    }

    public static class Attribute extends Token {
        public Attribute(long id, String value) {
            super((byte) 5);
            this.id = id;
            this.value = value;
        }

        public Attribute() {
            super((byte) 5);
        }

        public void writeTo(DataOutput out, byte idType) throws IOException {
            writeId(out, this.id, idType);
            writeString(out, this.value);
        }

        public void readFrom(DataInput in, byte idType) throws IOException {
            this.id = readId(in, idType);
            this.value = readString(in);
        }
    }

    public static class EndNode extends Token {
        public EndNode() {
            super((byte) 4);
        }

        public void writeTo(DataOutput out, byte idType) {
        }

        public void readFrom(DataInput in, byte idType) {
        }
    }

    public static class MapIdToValue extends Token {
        public MapIdToValue(long id, String value) {
            super((byte) 2);
            this.id = id;
            this.value = value;
        }

        public MapIdToValue() {
            super((byte) 2);
        }

        public void writeTo(DataOutput out, byte idType) throws IOException {
            writeId(out, this.id, idType);
            writeString(out, this.value);
        }

        public void readFrom(DataInput in, byte idType) throws IOException {
            this.id = readId(in, idType);
            this.value = readString(in);
        }
    }

    public static class StartNode extends Token {
        public StartNode(long id) {
            super((byte) 3);
            this.id = id;
        }

        public StartNode() {
            super((byte) 3);
        }

        public void writeTo(DataOutput out, byte idType) throws IOException {
            writeId(out, this.id, idType);
        }

        public void readFrom(DataInput in, byte idType) throws IOException {
            this.id = readId(in, idType);
        }
    }

    public static class Value extends Token {
        public Value(String value) {
            super((byte) 6);
            this.value = value;
        }

        public Value() {
            super((byte) 6);
        }

        public void writeTo(DataOutput out, byte idType) throws IOException {
            writeString(out, this.value);
        }

        public void readFrom(DataInput in, byte idType) throws IOException {
            this.value = readString(in);
        }
    }

    public abstract void readFrom(DataInput dataInput, byte b) throws IOException;

    public abstract void writeTo(DataOutput dataOutput, byte b) throws IOException;

    public Token(byte type) {
        this.type = type;
    }

    public byte getType() {
        return this.type;
    }

    public long getId() {
        return this.id;
    }

    public String getValue() {
        return this.value;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getClass().getName());
        stringBuilder.append(" [id=");
        stringBuilder.append(this.id);
        stringBuilder.append(", value='");
        stringBuilder.append(this.value);
        stringBuilder.append("']");
        return stringBuilder.toString();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean equals(java.lang.Object r9) {
        /*
        r8 = this;
        r0 = 1;
        if (r8 != r9) goto L_0x0004;
    L_0x0003:
        return r0;
    L_0x0004:
        r1 = 0;
        if (r9 == 0) goto L_0x003b;
    L_0x0007:
        r2 = r8.getClass();
        r3 = r9.getClass();
        if (r2 == r3) goto L_0x0012;
    L_0x0011:
        goto L_0x003b;
    L_0x0012:
        r2 = r9;
        r2 = (com.thoughtworks.xstream.io.binary.Token) r2;
        r3 = r8.id;
        r5 = r2.id;
        r7 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1));
        if (r7 == 0) goto L_0x001e;
    L_0x001d:
        return r1;
    L_0x001e:
        r3 = r8.type;
        r4 = r2.type;
        if (r3 == r4) goto L_0x0025;
    L_0x0024:
        return r1;
    L_0x0025:
        r3 = r8.value;
        if (r3 == 0) goto L_0x0034;
    L_0x0029:
        r3 = r8.value;
        r4 = r2.value;
        r3 = r3.equals(r4);
        if (r3 != 0) goto L_0x0038;
    L_0x0033:
        goto L_0x0039;
    L_0x0034:
        r3 = r2.value;
        if (r3 != 0) goto L_0x0039;
    L_0x0038:
        goto L_0x003a;
    L_0x0039:
        r0 = 0;
    L_0x003a:
        return r0;
    L_0x003b:
        return r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.thoughtworks.xstream.io.binary.Token.equals(java.lang.Object):boolean");
    }

    public int hashCode() {
        return (((this.type * 29) + ((int) (this.id ^ (this.id >>> 32)))) * 29) + (this.value != null ? this.value.hashCode() : 0);
    }

    protected void writeId(DataOutput out, long id, byte idType) throws IOException {
        StringBuilder stringBuilder;
        if (id < 0) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("id must not be negative ");
            stringBuilder.append(id);
            throw new IOException(stringBuilder.toString());
        } else if (idType == (byte) 8) {
            out.writeByte(((byte) ((int) id)) - 128);
        } else if (idType == (byte) 16) {
            out.writeShort(((short) ((int) id)) - 32768);
        } else if (idType == (byte) 24) {
            out.writeInt(((int) id) - Integer.MIN_VALUE);
        } else if (idType != (byte) 32) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Unknown idType ");
            stringBuilder.append(idType);
            throw new Error(stringBuilder.toString());
        } else {
            out.writeLong(id - Long.MIN_VALUE);
        }
    }

    protected void writeString(DataOutput out, String string) throws IOException {
        byte[] bytes = string.length() > 16383 ? string.getBytes("utf-8") : new byte[0];
        if (bytes.length <= 65535) {
            out.writeUTF(string);
            return;
        }
        out.writeUTF(ID_SPLITTED);
        out.writeInt(bytes.length);
        out.write(bytes);
    }

    protected long readId(DataInput in, byte idType) throws IOException {
        if (idType == (byte) 8) {
            return (long) (in.readByte() + 128);
        }
        if (idType == (byte) 16) {
            return (long) (in.readShort() - -32768);
        }
        if (idType == (byte) 24) {
            return (long) (in.readInt() - Integer.MIN_VALUE);
        }
        if (idType == (byte) 32) {
            return in.readLong() - Long.MIN_VALUE;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unknown idType ");
        stringBuilder.append(idType);
        throw new Error(stringBuilder.toString());
    }

    protected String readString(DataInput in) throws IOException {
        String string = in.readUTF();
        if (!ID_SPLITTED.equals(string)) {
            return string;
        }
        byte[] bytes = new byte[in.readInt()];
        in.readFully(bytes);
        return new String(bytes, "utf-8");
    }
}
