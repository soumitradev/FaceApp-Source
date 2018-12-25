package com.badlogic.gdx.utils;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.ObjectMap.Entry;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Iterator;
import kotlin.text.Typography;
import name.antonsmirnov.firmata.FormatHelper;
import org.billthefarmer.mididriver.GeneralMidiConstants;

public class XmlReader {
    private static final byte[] _xml_actions = init__xml_actions_0();
    private static final short[] _xml_index_offsets = init__xml_index_offsets_0();
    private static final byte[] _xml_indicies = init__xml_indicies_0();
    private static final byte[] _xml_key_offsets = init__xml_key_offsets_0();
    private static final byte[] _xml_range_lengths = init__xml_range_lengths_0();
    private static final byte[] _xml_single_lengths = init__xml_single_lengths_0();
    private static final byte[] _xml_trans_actions = init__xml_trans_actions_0();
    private static final char[] _xml_trans_keys = init__xml_trans_keys_0();
    private static final byte[] _xml_trans_targs = init__xml_trans_targs_0();
    static final int xml_en_elementBody = 15;
    static final int xml_en_main = 1;
    static final int xml_error = 0;
    static final int xml_first_final = 34;
    static final int xml_start = 1;
    private Element current;
    private final Array<Element> elements = new Array(8);
    private Element root;
    private final StringBuilder textBuffer = new StringBuilder(64);

    public static class Element {
        private ObjectMap<String, String> attributes;
        private Array<Element> children;
        private final String name;
        private Element parent;
        private String text;

        public Element(String name, Element parent) {
            this.name = name;
            this.parent = parent;
        }

        public String getName() {
            return this.name;
        }

        public ObjectMap<String, String> getAttributes() {
            return this.attributes;
        }

        public String getAttribute(String name) {
            if (this.attributes == null) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Element ");
                stringBuilder.append(name);
                stringBuilder.append(" doesn't have attribute: ");
                stringBuilder.append(name);
                throw new GdxRuntimeException(stringBuilder.toString());
            }
            String value = (String) this.attributes.get(name);
            if (value != null) {
                return value;
            }
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("Element ");
            stringBuilder2.append(name);
            stringBuilder2.append(" doesn't have attribute: ");
            stringBuilder2.append(name);
            throw new GdxRuntimeException(stringBuilder2.toString());
        }

        public String getAttribute(String name, String defaultValue) {
            if (this.attributes == null) {
                return defaultValue;
            }
            String value = (String) this.attributes.get(name);
            if (value == null) {
                return defaultValue;
            }
            return value;
        }

        public void setAttribute(String name, String value) {
            if (this.attributes == null) {
                this.attributes = new ObjectMap(8);
            }
            this.attributes.put(name, value);
        }

        public int getChildCount() {
            if (this.children == null) {
                return 0;
            }
            return this.children.size;
        }

        public Element getChild(int index) {
            if (this.children != null) {
                return (Element) this.children.get(index);
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Element has no children: ");
            stringBuilder.append(this.name);
            throw new GdxRuntimeException(stringBuilder.toString());
        }

        public void addChild(Element element) {
            if (this.children == null) {
                this.children = new Array(8);
            }
            this.children.add(element);
        }

        public String getText() {
            return this.text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public void removeChild(int index) {
            if (this.children != null) {
                this.children.removeIndex(index);
            }
        }

        public void removeChild(Element child) {
            if (this.children != null) {
                this.children.removeValue(child, true);
            }
        }

        public void remove() {
            this.parent.removeChild(this);
        }

        public Element getParent() {
            return this.parent;
        }

        public String toString() {
            return toString("");
        }

        public String toString(String indent) {
            StringBuilder buffer = new StringBuilder(128);
            buffer.append(indent);
            buffer.append((char) Typography.less);
            buffer.append(this.name);
            if (this.attributes != null) {
                Iterator i$ = this.attributes.entries().iterator();
                while (i$.hasNext()) {
                    Entry<String, String> entry = (Entry) i$.next();
                    buffer.append(' ');
                    buffer.append((String) entry.key);
                    buffer.append("=\"");
                    buffer.append((String) entry.value);
                    buffer.append((char) Typography.quote);
                }
            }
            if (this.children == null && (this.text == null || this.text.length() == 0)) {
                buffer.append("/>");
            } else {
                buffer.append(">\n");
                String childIndent = new StringBuilder();
                childIndent.append(indent);
                childIndent.append('\t');
                childIndent = childIndent.toString();
                if (this.text != null && this.text.length() > 0) {
                    buffer.append(childIndent);
                    buffer.append(this.text);
                    buffer.append('\n');
                }
                if (this.children != null) {
                    Iterator i$2 = this.children.iterator();
                    while (i$2.hasNext()) {
                        buffer.append(((Element) i$2.next()).toString(childIndent));
                        buffer.append('\n');
                    }
                }
                buffer.append(indent);
                buffer.append("</");
                buffer.append(this.name);
                buffer.append((char) Typography.greater);
            }
            return buffer.toString();
        }

        public Element getChildByName(String name) {
            if (this.children == null) {
                return null;
            }
            for (int i = 0; i < this.children.size; i++) {
                Element element = (Element) this.children.get(i);
                if (element.name.equals(name)) {
                    return element;
                }
            }
            return null;
        }

        public Element getChildByNameRecursive(String name) {
            if (this.children == null) {
                return null;
            }
            for (int i = 0; i < this.children.size; i++) {
                Element element = (Element) this.children.get(i);
                if (element.name.equals(name)) {
                    return element;
                }
                Element found = element.getChildByNameRecursive(name);
                if (found != null) {
                    return found;
                }
            }
            return null;
        }

        public Array<Element> getChildrenByName(String name) {
            Array<Element> result = new Array();
            if (this.children == null) {
                return result;
            }
            for (int i = 0; i < this.children.size; i++) {
                Element child = (Element) this.children.get(i);
                if (child.name.equals(name)) {
                    result.add(child);
                }
            }
            return result;
        }

        public Array<Element> getChildrenByNameRecursively(String name) {
            Array<Element> result = new Array();
            getChildrenByNameRecursively(name, result);
            return result;
        }

        private void getChildrenByNameRecursively(String name, Array<Element> result) {
            if (this.children != null) {
                for (int i = 0; i < this.children.size; i++) {
                    Element child = (Element) this.children.get(i);
                    if (child.name.equals(name)) {
                        result.add(child);
                    }
                    child.getChildrenByNameRecursively(name, result);
                }
            }
        }

        public float getFloatAttribute(String name) {
            return Float.parseFloat(getAttribute(name));
        }

        public float getFloatAttribute(String name, float defaultValue) {
            String value = getAttribute(name, null);
            if (value == null) {
                return defaultValue;
            }
            return Float.parseFloat(value);
        }

        public int getIntAttribute(String name) {
            return Integer.parseInt(getAttribute(name));
        }

        public int getIntAttribute(String name, int defaultValue) {
            String value = getAttribute(name, null);
            if (value == null) {
                return defaultValue;
            }
            return Integer.parseInt(value);
        }

        public boolean getBooleanAttribute(String name) {
            return Boolean.parseBoolean(getAttribute(name));
        }

        public boolean getBooleanAttribute(String name, boolean defaultValue) {
            String value = getAttribute(name, null);
            if (value == null) {
                return defaultValue;
            }
            return Boolean.parseBoolean(value);
        }

        public String get(String name) {
            String value = get(name, null);
            if (value != null) {
                return value;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Element ");
            stringBuilder.append(this.name);
            stringBuilder.append(" doesn't have attribute or child: ");
            stringBuilder.append(name);
            throw new GdxRuntimeException(stringBuilder.toString());
        }

        public String get(String name, String defaultValue) {
            if (this.attributes != null) {
                String value = (String) this.attributes.get(name);
                if (value != null) {
                    return value;
                }
            }
            Element child = getChildByName(name);
            if (child == null) {
                return defaultValue;
            }
            String value2 = child.getText();
            if (value2 == null) {
                return defaultValue;
            }
            return value2;
        }

        public int getInt(String name) {
            String value = get(name, null);
            if (value != null) {
                return Integer.parseInt(value);
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Element ");
            stringBuilder.append(this.name);
            stringBuilder.append(" doesn't have attribute or child: ");
            stringBuilder.append(name);
            throw new GdxRuntimeException(stringBuilder.toString());
        }

        public int getInt(String name, int defaultValue) {
            String value = get(name, null);
            if (value == null) {
                return defaultValue;
            }
            return Integer.parseInt(value);
        }

        public float getFloat(String name) {
            String value = get(name, null);
            if (value != null) {
                return Float.parseFloat(value);
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Element ");
            stringBuilder.append(this.name);
            stringBuilder.append(" doesn't have attribute or child: ");
            stringBuilder.append(name);
            throw new GdxRuntimeException(stringBuilder.toString());
        }

        public float getFloat(String name, float defaultValue) {
            String value = get(name, null);
            if (value == null) {
                return defaultValue;
            }
            return Float.parseFloat(value);
        }

        public boolean getBoolean(String name) {
            String value = get(name, null);
            if (value != null) {
                return Boolean.parseBoolean(value);
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Element ");
            stringBuilder.append(this.name);
            stringBuilder.append(" doesn't have attribute or child: ");
            stringBuilder.append(name);
            throw new GdxRuntimeException(stringBuilder.toString());
        }

        public boolean getBoolean(String name, boolean defaultValue) {
            String value = get(name, null);
            if (value == null) {
                return defaultValue;
            }
            return Boolean.parseBoolean(value);
        }
    }

    public Element parse(String xml) {
        char[] data = xml.toCharArray();
        return parse(data, 0, data.length);
    }

    public Element parse(Reader reader) throws IOException {
        try {
            char[] data = new char[1024];
            int offset = 0;
            while (true) {
                int length = reader.read(data, offset, data.length - offset);
                if (length == -1) {
                    Element parse = parse(data, 0, offset);
                    StreamUtils.closeQuietly(reader);
                    return parse;
                } else if (length == 0) {
                    char[] newData = new char[(data.length * 2)];
                    System.arraycopy(data, 0, newData, 0, data.length);
                    data = newData;
                } else {
                    offset += length;
                }
            }
        } catch (Throwable ex) {
            throw new SerializationException(ex);
        } catch (Throwable th) {
            StreamUtils.closeQuietly(reader);
        }
    }

    public Element parse(InputStream input) throws IOException {
        try {
            Element parse = parse(new InputStreamReader(input, "UTF-8"));
            StreamUtils.closeQuietly(input);
            return parse;
        } catch (Throwable ex) {
            throw new SerializationException(ex);
        } catch (Throwable th) {
            StreamUtils.closeQuietly(input);
        }
    }

    public Element parse(FileHandle file) throws IOException {
        try {
            return parse(file.reader("UTF-8"));
        } catch (Exception ex) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Error parsing file: ");
            stringBuilder.append(file);
            throw new SerializationException(stringBuilder.toString(), ex);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.badlogic.gdx.utils.XmlReader.Element parse(char[] r25, int r26, int r27) {
        /*
        r24 = this;
        r0 = r24;
        r1 = r25;
        r2 = r26;
        r3 = r27;
        r4 = 0;
        r5 = 0;
        r6 = 0;
        r7 = 1;
        r8 = 0;
        r11 = r4;
        r10 = r5;
        r12 = r6;
        r5 = 0;
        r6 = 0;
        r4 = r2;
        r2 = 0;
    L_0x0014:
        switch(r2) {
            case 0: goto L_0x0020;
            case 1: goto L_0x0028;
            case 2: goto L_0x001c;
            default: goto L_0x0017;
        };
    L_0x0017:
        r18 = r2;
        r2 = 0;
        goto L_0x0294;
    L_0x001c:
        r18 = r2;
        goto L_0x0287;
    L_0x0020:
        if (r4 != r3) goto L_0x0024;
    L_0x0022:
        r2 = 4;
        goto L_0x0014;
    L_0x0024:
        if (r7 != 0) goto L_0x0028;
    L_0x0026:
        r2 = 5;
        goto L_0x0014;
    L_0x0028:
        r5 = _xml_key_offsets;
        r5 = r5[r7];
        r6 = _xml_index_offsets;
        r6 = r6[r7];
        r8 = _xml_single_lengths;
        r8 = r8[r7];
        if (r8 <= 0) goto L_0x0065;
    L_0x0036:
        r14 = r5;
        r15 = r5 + r8;
        r15 = r15 + -1;
    L_0x003b:
        if (r15 >= r14) goto L_0x0040;
    L_0x003d:
        r5 = r5 + r8;
        r6 = r6 + r8;
        goto L_0x0065;
    L_0x0040:
        r16 = r15 - r14;
        r16 = r16 >> 1;
        r16 = r14 + r16;
        r9 = r1[r4];
        r17 = _xml_trans_keys;
        r13 = r17[r16];
        if (r9 >= r13) goto L_0x0051;
    L_0x004e:
        r15 = r16 + -1;
        goto L_0x003b;
    L_0x0051:
        r9 = r1[r4];
        r13 = _xml_trans_keys;
        r13 = r13[r16];
        if (r9 <= r13) goto L_0x005c;
    L_0x0059:
        r14 = r16 + 1;
        goto L_0x003b;
    L_0x005c:
        r9 = r16 - r5;
        r6 = r6 + r9;
    L_0x0060:
        r18 = r2;
    L_0x0062:
        r2 = r5;
        r5 = r8;
        goto L_0x00a2;
    L_0x0065:
        r9 = _xml_range_lengths;
        r8 = r9[r7];
        if (r8 <= 0) goto L_0x009e;
    L_0x006b:
        r9 = r5;
        r13 = r8 << 1;
        r13 = r13 + r5;
        r13 = r13 + -2;
    L_0x0071:
        if (r13 >= r9) goto L_0x0075;
    L_0x0073:
        r6 = r6 + r8;
        goto L_0x0060;
    L_0x0075:
        r14 = r13 - r9;
        r14 = r14 >> 1;
        r14 = r14 & -2;
        r14 = r14 + r9;
        r15 = r1[r4];
        r16 = _xml_trans_keys;
        r18 = r2;
        r2 = r16[r14];
        if (r15 >= r2) goto L_0x008b;
    L_0x0086:
        r13 = r14 + -2;
    L_0x0088:
        r2 = r18;
        goto L_0x0071;
    L_0x008b:
        r2 = r1[r4];
        r15 = _xml_trans_keys;
        r16 = r14 + 1;
        r15 = r15[r16];
        if (r2 <= r15) goto L_0x0098;
    L_0x0095:
        r9 = r14 + 2;
        goto L_0x0088;
    L_0x0098:
        r2 = r14 - r5;
        r2 = r2 >> 1;
        r6 = r6 + r2;
        goto L_0x0062;
    L_0x009e:
        r18 = r2;
        r2 = r5;
        r5 = r8;
    L_0x00a2:
        r8 = _xml_indicies;
        r8 = r8[r6];
        r6 = _xml_trans_targs;
        r6 = r6[r8];
        r7 = _xml_trans_actions;
        r7 = r7[r8];
        if (r7 == 0) goto L_0x0280;
    L_0x00b0:
        r7 = _xml_trans_actions;
        r7 = r7[r8];
        r9 = _xml_actions;
        r13 = r7 + 1;
        r7 = r9[r7];
    L_0x00ba:
        r9 = r7 + -1;
        if (r7 <= 0) goto L_0x0280;
    L_0x00be:
        r7 = _xml_actions;
        r14 = r13 + 1;
        r7 = r7[r13];
        switch(r7) {
            case 0: goto L_0x0271;
            case 1: goto L_0x01ba;
            case 2: goto L_0x01aa;
            case 3: goto L_0x019f;
            case 4: goto L_0x018f;
            case 5: goto L_0x0181;
            case 6: goto L_0x0171;
            case 7: goto L_0x00cd;
            default: goto L_0x00c7;
        };
    L_0x00c7:
        r19 = r2;
        r20 = r5;
        goto L_0x0277;
    L_0x00cd:
        r7 = r4;
    L_0x00ce:
        if (r7 == r11) goto L_0x00e3;
    L_0x00d0:
        r13 = r7 + -1;
        r13 = r1[r13];
        r15 = 13;
        if (r13 == r15) goto L_0x00e0;
    L_0x00d8:
        r15 = 32;
        if (r13 == r15) goto L_0x00e0;
    L_0x00dc:
        switch(r13) {
            case 9: goto L_0x00e0;
            case 10: goto L_0x00e0;
            default: goto L_0x00df;
        };
    L_0x00df:
        goto L_0x00e3;
    L_0x00e0:
        r7 = r7 + -1;
        goto L_0x00ce;
    L_0x00e3:
        r13 = r11;
        r15 = r11;
        r11 = 0;
    L_0x00e6:
        if (r13 == r7) goto L_0x0144;
    L_0x00e8:
        r16 = r13 + 1;
        r13 = r1[r13];
        r19 = r2;
        r2 = 38;
        if (r13 == r2) goto L_0x00f7;
    L_0x00f2:
        r13 = r16;
        r2 = r19;
        goto L_0x00e6;
    L_0x00f7:
        r2 = r16;
    L_0x00f9:
        r13 = r16;
        if (r2 == r7) goto L_0x013c;
    L_0x00fd:
        r16 = r2 + 1;
        r2 = r1[r2];
        r20 = r5;
        r5 = 59;
        if (r2 == r5) goto L_0x010e;
    L_0x0107:
        r2 = r16;
        r5 = r20;
        r16 = r13;
        goto L_0x00f9;
    L_0x010e:
        r2 = r0.textBuffer;
        r5 = r13 - r15;
        r5 = r5 + -1;
        r2.append(r1, r15, r5);
        r2 = new java.lang.String;
        r5 = r16 - r13;
        r5 = r5 + -1;
        r2.<init>(r1, r13, r5);
        r5 = r0.entity(r2);
        r21 = r2;
        r2 = r0.textBuffer;
        if (r5 == 0) goto L_0x012d;
    L_0x012a:
        r22 = r5;
        goto L_0x0131;
    L_0x012d:
        r22 = r5;
        r5 = r21;
    L_0x0131:
        r2.append(r5);
        r15 = r16;
        r2 = 1;
        r11 = r2;
        r13 = r16;
        goto L_0x013f;
    L_0x013c:
        r20 = r5;
        r13 = r2;
    L_0x013f:
        r2 = r19;
        r5 = r20;
        goto L_0x00e6;
    L_0x0144:
        r19 = r2;
        r20 = r5;
        if (r11 == 0) goto L_0x0163;
    L_0x014a:
        if (r15 >= r7) goto L_0x0153;
    L_0x014c:
        r2 = r0.textBuffer;
        r5 = r7 - r15;
        r2.append(r1, r15, r5);
    L_0x0153:
        r2 = r0.textBuffer;
        r2 = r2.toString();
        r0.text(r2);
        r2 = r0.textBuffer;
        r5 = 0;
        r2.setLength(r5);
        goto L_0x016e;
    L_0x0163:
        r5 = 0;
        r2 = new java.lang.String;
        r5 = r7 - r15;
        r2.<init>(r1, r15, r5);
        r0.text(r2);
    L_0x016e:
        r11 = r15;
        goto L_0x0277;
    L_0x0171:
        r19 = r2;
        r20 = r5;
        r2 = new java.lang.String;
        r5 = r4 - r11;
        r2.<init>(r1, r11, r5);
        r0.attribute(r10, r2);
        goto L_0x0277;
    L_0x0181:
        r19 = r2;
        r20 = r5;
        r2 = new java.lang.String;
        r5 = r4 - r11;
        r2.<init>(r1, r11, r5);
        r10 = r2;
        goto L_0x0277;
    L_0x018f:
        r19 = r2;
        r20 = r5;
        if (r12 == 0) goto L_0x0277;
    L_0x0195:
        r7 = 15;
        r2 = 2;
    L_0x0199:
        r6 = r19;
        r5 = r20;
        goto L_0x0014;
    L_0x019f:
        r19 = r2;
        r20 = r5;
        r24.close();
        r7 = 15;
        r2 = 2;
        goto L_0x01b6;
    L_0x01aa:
        r19 = r2;
        r20 = r5;
        r12 = 0;
        r24.close();
        r7 = 15;
        r2 = 2;
    L_0x01b6:
        r6 = r19;
        goto L_0x0014;
    L_0x01ba:
        r19 = r2;
        r20 = r5;
        r2 = r1[r11];
        r5 = 63;
        r7 = 33;
        if (r2 == r5) goto L_0x01d6;
    L_0x01c6:
        if (r2 != r7) goto L_0x01c9;
    L_0x01c8:
        goto L_0x01d6;
    L_0x01c9:
        r12 = 1;
        r5 = new java.lang.String;
        r7 = r4 - r11;
        r5.<init>(r1, r11, r7);
        r0.open(r5);
        goto L_0x0277;
    L_0x01d6:
        r5 = r11 + 1;
        r5 = r1[r5];
        r13 = 91;
        if (r5 != r13) goto L_0x0234;
    L_0x01de:
        r5 = r11 + 2;
        r5 = r1[r5];
        r7 = 67;
        if (r5 != r7) goto L_0x0234;
    L_0x01e6:
        r5 = r11 + 3;
        r5 = r1[r5];
        r7 = 68;
        if (r5 != r7) goto L_0x0234;
    L_0x01ee:
        r5 = r11 + 4;
        r5 = r1[r5];
        r7 = 65;
        if (r5 != r7) goto L_0x0234;
    L_0x01f6:
        r5 = r11 + 5;
        r5 = r1[r5];
        r15 = 84;
        if (r5 != r15) goto L_0x0234;
    L_0x01fe:
        r5 = r11 + 6;
        r5 = r1[r5];
        if (r5 != r7) goto L_0x0234;
    L_0x0204:
        r5 = r11 + 7;
        r5 = r1[r5];
        if (r5 != r13) goto L_0x0234;
    L_0x020a:
        r5 = r11 + 8;
        r4 = r5 + 2;
    L_0x020e:
        r7 = r4 + -2;
        r7 = r1[r7];
        r11 = 93;
        if (r7 != r11) goto L_0x0231;
    L_0x0216:
        r7 = r4 + -1;
        r7 = r1[r7];
        if (r7 != r11) goto L_0x0231;
    L_0x021c:
        r7 = r1[r4];
        r11 = 62;
        if (r7 == r11) goto L_0x0223;
    L_0x0222:
        goto L_0x0231;
    L_0x0223:
        r7 = new java.lang.String;
        r11 = r4 - r5;
        r11 = r11 + -2;
        r7.<init>(r1, r5, r11);
        r0.text(r7);
        r11 = r5;
        goto L_0x026a;
    L_0x0231:
        r4 = r4 + 1;
        goto L_0x020e;
    L_0x0234:
        r5 = 33;
        if (r2 != r5) goto L_0x0261;
    L_0x0238:
        r5 = r11 + 1;
        r5 = r1[r5];
        r7 = 45;
        if (r5 != r7) goto L_0x0261;
    L_0x0240:
        r5 = r11 + 2;
        r5 = r1[r5];
        if (r5 != r7) goto L_0x0261;
    L_0x0246:
        r4 = r11 + 3;
    L_0x0248:
        r5 = r1[r4];
        if (r5 != r7) goto L_0x025e;
    L_0x024c:
        r5 = r4 + 1;
        r5 = r1[r5];
        if (r5 != r7) goto L_0x025e;
    L_0x0252:
        r5 = r4 + 2;
        r5 = r1[r5];
        r13 = 62;
        if (r5 == r13) goto L_0x025b;
    L_0x025a:
        goto L_0x025e;
    L_0x025b:
        r4 = r4 + 2;
        goto L_0x026a;
    L_0x025e:
        r4 = r4 + 1;
        goto L_0x0248;
    L_0x0261:
        r5 = r1[r4];
        r7 = 62;
        if (r5 == r7) goto L_0x026a;
    L_0x0267:
        r4 = r4 + 1;
        goto L_0x0261;
    L_0x026a:
        r7 = 15;
        r5 = 2;
        r2 = r5;
        goto L_0x0199;
    L_0x0271:
        r19 = r2;
        r20 = r5;
        r11 = r4;
        r7 = r9;
        r13 = r14;
        r2 = r19;
        r5 = r20;
        goto L_0x00ba;
    L_0x0280:
        r19 = r2;
        r20 = r5;
        r7 = r6;
        r6 = r19;
    L_0x0287:
        if (r7 != 0) goto L_0x028c;
    L_0x0289:
        r2 = 5;
        goto L_0x0014;
    L_0x028c:
        r4 = r4 + 1;
        if (r4 == r3) goto L_0x0293;
    L_0x0290:
        r2 = 1;
        goto L_0x0014;
    L_0x0293:
        r2 = 0;
    L_0x0294:
        if (r4 >= r3) goto L_0x02d1;
    L_0x0296:
        r5 = 1;
    L_0x0298:
        if (r2 >= r4) goto L_0x02a5;
    L_0x029a:
        r6 = r1[r2];
        r8 = 10;
        if (r6 != r8) goto L_0x02a2;
    L_0x02a0:
        r5 = r5 + 1;
    L_0x02a2:
        r2 = r2 + 1;
        goto L_0x0298;
    L_0x02a5:
        r2 = new com.badlogic.gdx.utils.SerializationException;
        r6 = new java.lang.StringBuilder;
        r6.<init>();
        r8 = "Error parsing XML on line ";
        r6.append(r8);
        r6.append(r5);
        r8 = " near: ";
        r6.append(r8);
        r8 = new java.lang.String;
        r9 = r3 - r4;
        r13 = 32;
        r9 = java.lang.Math.min(r13, r9);
        r8.<init>(r1, r4, r9);
        r6.append(r8);
        r6 = r6.toString();
        r2.<init>(r6);
        throw r2;
    L_0x02d1:
        r2 = r0.elements;
        r2 = r2.size;
        if (r2 == 0) goto L_0x02ff;
    L_0x02d7:
        r2 = r0.elements;
        r2 = r2.peek();
        r2 = (com.badlogic.gdx.utils.XmlReader.Element) r2;
        r5 = r0.elements;
        r5.clear();
        r5 = new com.badlogic.gdx.utils.SerializationException;
        r6 = new java.lang.StringBuilder;
        r6.<init>();
        r8 = "Error parsing XML, unclosed element: ";
        r6.append(r8);
        r8 = r2.getName();
        r6.append(r8);
        r6 = r6.toString();
        r5.<init>(r6);
        throw r5;
    L_0x02ff:
        r2 = r0.root;
        r5 = 0;
        r0.root = r5;
        return r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.badlogic.gdx.utils.XmlReader.parse(char[], int, int):com.badlogic.gdx.utils.XmlReader$Element");
    }

    private static byte[] init__xml_actions_0() {
        return new byte[]{(byte) 0, (byte) 1, (byte) 0, (byte) 1, (byte) 1, (byte) 1, (byte) 2, (byte) 1, (byte) 3, (byte) 1, (byte) 4, (byte) 1, (byte) 5, (byte) 1, (byte) 6, (byte) 1, (byte) 7, (byte) 2, (byte) 0, (byte) 6, (byte) 2, (byte) 1, (byte) 4, (byte) 2, (byte) 2, (byte) 4};
    }

    private static byte[] init__xml_key_offsets_0() {
        return new byte[]{(byte) 0, (byte) 0, (byte) 4, (byte) 9, (byte) 14, (byte) 20, (byte) 26, (byte) 30, GeneralMidiConstants.FRETLESS_BASS, GeneralMidiConstants.SLAP_BASS_0, GeneralMidiConstants.SLAP_BASS_1, GeneralMidiConstants.CELLO, GeneralMidiConstants.ORCHESTRAL_HARP, (byte) 50, (byte) 51, (byte) 52, GeneralMidiConstants.TRUMPET, GeneralMidiConstants.TROMBONE, GeneralMidiConstants.SYNTHBRASS_0, GeneralMidiConstants.BARITONE_SAX, GeneralMidiConstants.FLUTE, GeneralMidiConstants.OCARINA, (byte) 83, (byte) 88, GeneralMidiConstants.PAD_1_WARM, GeneralMidiConstants.PAD_2_POLYSYNTH, GeneralMidiConstants.PAD_7_SWEEP, GeneralMidiConstants.FX_3_ATMOSPHERE, (byte) 103, GeneralMidiConstants.SIT_R, GeneralMidiConstants.KALIMBA, GeneralMidiConstants.BAG_PIPE, GeneralMidiConstants.FIDDLE, GeneralMidiConstants.SHANAI, GeneralMidiConstants.TINKLE_BELL, GeneralMidiConstants.WOODBLOCK};
    }

    private static char[] init__xml_trans_keys_0() {
        return new char[]{' ', Typography.less, '\t', '\r', ' ', '/', Typography.greater, '\t', '\r', ' ', '/', Typography.greater, '\t', '\r', ' ', '/', '=', Typography.greater, '\t', '\r', ' ', '/', '=', Typography.greater, '\t', '\r', ' ', '=', '\t', '\r', ' ', Typography.quote, '\'', '\t', '\r', Typography.quote, Typography.quote, ' ', '/', Typography.greater, '\t', '\r', ' ', Typography.greater, '\t', '\r', ' ', Typography.greater, '\t', '\r', '\'', '\'', ' ', Typography.less, '\t', '\r', Typography.less, ' ', '/', Typography.greater, '\t', '\r', ' ', '/', Typography.greater, '\t', '\r', ' ', '/', '=', Typography.greater, '\t', '\r', ' ', '/', '=', Typography.greater, '\t', '\r', ' ', '=', '\t', '\r', ' ', Typography.quote, '\'', '\t', '\r', Typography.quote, Typography.quote, ' ', '/', Typography.greater, '\t', '\r', ' ', Typography.greater, '\t', '\r', ' ', Typography.greater, '\t', '\r', Typography.less, ' ', '/', '\t', '\r', Typography.greater, Typography.greater, '\'', '\'', ' ', '\t', '\r', '\u0000'};
    }

    private static byte[] init__xml_single_lengths_0() {
        return new byte[]{(byte) 0, (byte) 2, (byte) 3, (byte) 3, (byte) 4, (byte) 4, (byte) 2, (byte) 3, (byte) 1, (byte) 1, (byte) 3, (byte) 2, (byte) 2, (byte) 1, (byte) 1, (byte) 2, (byte) 1, (byte) 3, (byte) 3, (byte) 4, (byte) 4, (byte) 2, (byte) 3, (byte) 1, (byte) 1, (byte) 3, (byte) 2, (byte) 2, (byte) 1, (byte) 2, (byte) 1, (byte) 1, (byte) 1, (byte) 1, (byte) 1, (byte) 0};
    }

    private static byte[] init__xml_range_lengths_0() {
        return new byte[]{(byte) 0, (byte) 1, (byte) 1, (byte) 1, (byte) 1, (byte) 1, (byte) 1, (byte) 1, (byte) 0, (byte) 0, (byte) 1, (byte) 1, (byte) 1, (byte) 0, (byte) 0, (byte) 1, (byte) 0, (byte) 1, (byte) 1, (byte) 1, (byte) 1, (byte) 1, (byte) 1, (byte) 0, (byte) 0, (byte) 1, (byte) 1, (byte) 1, (byte) 0, (byte) 1, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 1, (byte) 0};
    }

    private static short[] init__xml_index_offsets_0() {
        return new short[]{(short) 0, (short) 0, (short) 4, (short) 9, (short) 14, (short) 20, (short) 26, (short) 30, (short) 35, (short) 37, (short) 39, (short) 44, (short) 48, (short) 52, (short) 54, (short) 56, (short) 60, (short) 62, (short) 67, (short) 72, (short) 78, (short) 84, (short) 88, (short) 93, (short) 95, (short) 97, (short) 102, (short) 106, (short) 110, (short) 112, (short) 116, (short) 118, (short) 120, (short) 122, (short) 124, (short) 127};
    }

    private static byte[] init__xml_indicies_0() {
        return new byte[]{(byte) 0, (byte) 2, (byte) 0, (byte) 1, (byte) 2, (byte) 1, (byte) 1, (byte) 2, (byte) 3, (byte) 5, (byte) 6, (byte) 7, (byte) 5, (byte) 4, (byte) 9, (byte) 10, (byte) 1, (byte) 11, (byte) 9, (byte) 8, (byte) 13, (byte) 1, (byte) 14, (byte) 1, (byte) 13, (byte) 12, (byte) 15, (byte) 16, (byte) 15, (byte) 1, (byte) 16, (byte) 17, (byte) 18, (byte) 16, (byte) 1, (byte) 20, (byte) 19, (byte) 22, (byte) 21, (byte) 9, (byte) 10, (byte) 11, (byte) 9, (byte) 1, (byte) 23, (byte) 24, (byte) 23, (byte) 1, (byte) 25, (byte) 11, (byte) 25, (byte) 1, (byte) 20, (byte) 26, (byte) 22, (byte) 27, (byte) 29, (byte) 30, (byte) 29, (byte) 28, (byte) 32, (byte) 31, (byte) 30, GeneralMidiConstants.ELECTRIC_BASS_PICK, (byte) 1, (byte) 30, GeneralMidiConstants.ELECTRIC_BASS_FINGER, GeneralMidiConstants.SLAP_BASS_0, GeneralMidiConstants.SLAP_BASS_1, GeneralMidiConstants.SYNTH_BASS_0, GeneralMidiConstants.SLAP_BASS_0, GeneralMidiConstants.FRETLESS_BASS, GeneralMidiConstants.VIOLIN, GeneralMidiConstants.VIOLA, (byte) 1, GeneralMidiConstants.CELLO, GeneralMidiConstants.VIOLIN, GeneralMidiConstants.SYNTH_BASS_1, GeneralMidiConstants.TREMOLO_STRINGS, (byte) 1, GeneralMidiConstants.PIZZICATO_STRINGS, (byte) 1, GeneralMidiConstants.TREMOLO_STRINGS, GeneralMidiConstants.CONTRABASS, GeneralMidiConstants.ORCHESTRAL_HARP, GeneralMidiConstants.TIMPANI, GeneralMidiConstants.ORCHESTRAL_HARP, (byte) 1, GeneralMidiConstants.TIMPANI, (byte) 48, (byte) 49, GeneralMidiConstants.TIMPANI, (byte) 1, (byte) 51, (byte) 50, (byte) 53, (byte) 52, GeneralMidiConstants.VIOLIN, GeneralMidiConstants.VIOLA, GeneralMidiConstants.CELLO, GeneralMidiConstants.VIOLIN, (byte) 1, (byte) 54, (byte) 55, (byte) 54, (byte) 1, GeneralMidiConstants.TRUMPET, GeneralMidiConstants.CELLO, GeneralMidiConstants.TRUMPET, (byte) 1, GeneralMidiConstants.TROMBONE, (byte) 1, GeneralMidiConstants.TROMBONE, GeneralMidiConstants.ELECTRIC_BASS_PICK, GeneralMidiConstants.TROMBONE, (byte) 1, (byte) 1, GeneralMidiConstants.TUBA, GeneralMidiConstants.MUTED_TRUMPET, GeneralMidiConstants.TUBA, (byte) 51, GeneralMidiConstants.FRENCH_HORN, (byte) 53, GeneralMidiConstants.BRASS_SECTION, GeneralMidiConstants.SYNTHBRASS_0, GeneralMidiConstants.SYNTHBRASS_0, (byte) 1, (byte) 1, (byte) 0};
    }

    private static byte[] init__xml_trans_targs_0() {
        return new byte[]{(byte) 1, (byte) 0, (byte) 2, (byte) 3, (byte) 3, (byte) 4, (byte) 11, GeneralMidiConstants.ELECTRIC_BASS_PICK, (byte) 5, (byte) 4, (byte) 11, GeneralMidiConstants.ELECTRIC_BASS_PICK, (byte) 5, (byte) 6, (byte) 7, (byte) 6, (byte) 7, (byte) 8, (byte) 13, (byte) 9, (byte) 10, (byte) 9, (byte) 10, (byte) 12, GeneralMidiConstants.ELECTRIC_BASS_PICK, (byte) 12, (byte) 14, (byte) 14, (byte) 16, (byte) 15, (byte) 17, (byte) 16, (byte) 17, (byte) 18, (byte) 30, (byte) 18, (byte) 19, (byte) 26, (byte) 28, (byte) 20, (byte) 19, (byte) 26, (byte) 28, (byte) 20, (byte) 21, (byte) 22, (byte) 21, (byte) 22, (byte) 23, (byte) 32, (byte) 24, (byte) 25, (byte) 24, (byte) 25, (byte) 27, (byte) 28, (byte) 27, (byte) 29, (byte) 31, GeneralMidiConstants.FRETLESS_BASS, GeneralMidiConstants.ELECTRIC_BASS_FINGER, GeneralMidiConstants.ELECTRIC_BASS_FINGER, GeneralMidiConstants.ELECTRIC_BASS_PICK};
    }

    private static byte[] init__xml_trans_actions_0() {
        return new byte[]{(byte) 0, (byte) 0, (byte) 0, (byte) 1, (byte) 0, (byte) 3, (byte) 3, (byte) 20, (byte) 1, (byte) 0, (byte) 0, (byte) 9, (byte) 0, (byte) 11, (byte) 11, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 1, (byte) 17, (byte) 0, (byte) 13, (byte) 5, (byte) 23, (byte) 0, (byte) 1, (byte) 0, (byte) 1, (byte) 0, (byte) 0, (byte) 0, (byte) 15, (byte) 1, (byte) 0, (byte) 0, (byte) 3, (byte) 3, (byte) 20, (byte) 1, (byte) 0, (byte) 0, (byte) 9, (byte) 0, (byte) 11, (byte) 11, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 1, (byte) 17, (byte) 0, (byte) 13, (byte) 5, (byte) 23, (byte) 0, (byte) 0, (byte) 0, (byte) 7, (byte) 1, (byte) 0, (byte) 0};
    }

    protected void open(String name) {
        Element child = new Element(name, this.current);
        Element parent = this.current;
        if (parent != null) {
            parent.addChild(child);
        }
        this.elements.add(child);
        this.current = child;
    }

    protected void attribute(String name, String value) {
        this.current.setAttribute(name, value);
    }

    protected String entity(String name) {
        if (name.equals("lt")) {
            return "<";
        }
        if (name.equals("gt")) {
            return ">";
        }
        if (name.equals("amp")) {
            return "&";
        }
        if (name.equals("apos")) {
            return FormatHelper.QUOTE;
        }
        if (name.equals("quot")) {
            return "\"";
        }
        if (name.startsWith("#x")) {
            return Character.toString((char) Integer.parseInt(name.substring(2), 16));
        }
        return null;
    }

    protected void text(String text) {
        String stringBuilder;
        String existing = this.current.getText();
        Element element = this.current;
        if (existing != null) {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append(existing);
            stringBuilder2.append(text);
            stringBuilder = stringBuilder2.toString();
        } else {
            stringBuilder = text;
        }
        element.setText(stringBuilder);
    }

    protected void close() {
        this.root = (Element) this.elements.pop();
        this.current = this.elements.size > 0 ? (Element) this.elements.peek() : null;
    }
}
