package com.thoughtworks.xstream.io.xml;

import com.thoughtworks.xstream.core.util.FastStack;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.naming.NameCoder;
import java.io.Writer;
import kotlin.text.Typography;

public class PrettyPrintWriter extends AbstractXmlWriter {
    private static final char[] AMP = "&amp;".toCharArray();
    private static final char[] APOS = "&apos;".toCharArray();
    private static final char[] CLOSE = "</".toCharArray();
    private static final char[] CR = "&#xd;".toCharArray();
    private static final char[] GT = "&gt;".toCharArray();
    private static final char[] LT = "&lt;".toCharArray();
    private static final char[] NULL = "&#x0;".toCharArray();
    private static final char[] QUOT = "&quot;".toCharArray();
    public static int XML_1_0 = 0;
    public static int XML_1_1 = 1;
    public static int XML_QUIRKS = -1;
    protected int depth;
    private final FastStack elementStack;
    private final char[] lineIndenter;
    private final int mode;
    private String newLine;
    private boolean readyForNewLine;
    private boolean tagInProgress;
    private boolean tagIsEmpty;
    private final QuickWriter writer;

    private PrettyPrintWriter(Writer writer, int mode, char[] lineIndenter, NameCoder nameCoder, String newLine) {
        super(nameCoder);
        this.elementStack = new FastStack(16);
        this.writer = new QuickWriter(writer);
        this.lineIndenter = lineIndenter;
        this.newLine = newLine;
        this.mode = mode;
        if (mode >= XML_QUIRKS) {
            if (mode <= XML_1_1) {
                return;
            }
        }
        throw new IllegalArgumentException("Not a valid XML mode");
    }

    public PrettyPrintWriter(Writer writer, char[] lineIndenter, String newLine, XmlFriendlyReplacer replacer) {
        this(writer, XML_QUIRKS, lineIndenter, replacer, newLine);
    }

    public PrettyPrintWriter(Writer writer, int mode, char[] lineIndenter, NameCoder nameCoder) {
        this(writer, mode, lineIndenter, nameCoder, "\n");
    }

    public PrettyPrintWriter(Writer writer, int mode, char[] lineIndenter, XmlFriendlyReplacer replacer) {
        this(writer, mode, lineIndenter, replacer, "\n");
    }

    public PrettyPrintWriter(Writer writer, char[] lineIndenter, String newLine) {
        this(writer, lineIndenter, newLine, new XmlFriendlyReplacer());
    }

    public PrettyPrintWriter(Writer writer, int mode, char[] lineIndenter) {
        this(writer, mode, lineIndenter, new XmlFriendlyNameCoder());
    }

    public PrettyPrintWriter(Writer writer, char[] lineIndenter) {
        this(writer, XML_QUIRKS, lineIndenter);
    }

    public PrettyPrintWriter(Writer writer, String lineIndenter, String newLine) {
        this(writer, lineIndenter.toCharArray(), newLine);
    }

    public PrettyPrintWriter(Writer writer, int mode, String lineIndenter) {
        this(writer, mode, lineIndenter.toCharArray());
    }

    public PrettyPrintWriter(Writer writer, String lineIndenter) {
        this(writer, lineIndenter.toCharArray());
    }

    public PrettyPrintWriter(Writer writer, int mode, NameCoder nameCoder) {
        this(writer, mode, new char[]{' ', ' '}, nameCoder);
    }

    public PrettyPrintWriter(Writer writer, int mode, XmlFriendlyReplacer replacer) {
        this(writer, mode, new char[]{' ', ' '}, replacer);
    }

    public PrettyPrintWriter(Writer writer, NameCoder nameCoder) {
        this(writer, XML_QUIRKS, new char[]{' ', ' '}, nameCoder, "\n");
    }

    public PrettyPrintWriter(Writer writer, XmlFriendlyReplacer replacer) {
        this(writer, new char[]{' ', ' '}, "\n", replacer);
    }

    public PrettyPrintWriter(Writer writer, int mode) {
        this(writer, mode, new char[]{' ', ' '});
    }

    public PrettyPrintWriter(Writer writer) {
        this(writer, new char[]{' ', ' '});
    }

    public void startNode(String name) {
        String escapedName = encodeNode(name);
        this.tagIsEmpty = false;
        finishTag();
        this.writer.write((char) Typography.less);
        this.writer.write(escapedName);
        this.elementStack.push(escapedName);
        this.tagInProgress = true;
        this.depth++;
        this.readyForNewLine = true;
        this.tagIsEmpty = true;
    }

    public void startNode(String name, Class clazz) {
        startNode(name);
    }

    public void setValue(String text) {
        this.readyForNewLine = false;
        this.tagIsEmpty = false;
        finishTag();
        writeText(this.writer, text);
    }

    public void addAttribute(String key, String value) {
        this.writer.write(' ');
        this.writer.write(encodeAttribute(key));
        this.writer.write('=');
        this.writer.write((char) Typography.quote);
        writeAttributeValue(this.writer, value);
        this.writer.write((char) Typography.quote);
    }

    protected void writeAttributeValue(QuickWriter writer, String text) {
        writeText(text, true);
    }

    protected void writeText(QuickWriter writer, String text) {
        writeText(text, false);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void writeText(java.lang.String r7, boolean r8) {
        /*
        r6 = this;
        r0 = r7.length();
        r1 = 0;
    L_0x0005:
        if (r1 >= r0) goto L_0x0141;
    L_0x0007:
        r2 = r7.charAt(r1);
        if (r2 == 0) goto L_0x0128;
    L_0x000d:
        r3 = 13;
        if (r2 == r3) goto L_0x0120;
    L_0x0011:
        r3 = 34;
        if (r2 == r3) goto L_0x0118;
    L_0x0015:
        r3 = 60;
        if (r2 == r3) goto L_0x0110;
    L_0x0019:
        r3 = 62;
        if (r2 == r3) goto L_0x0108;
    L_0x001d:
        switch(r2) {
            case 9: goto L_0x0036;
            case 10: goto L_0x0036;
            default: goto L_0x0020;
        };
    L_0x0020:
        switch(r2) {
            case 38: goto L_0x002d;
            case 39: goto L_0x0024;
            default: goto L_0x0023;
        };
    L_0x0023:
        goto L_0x003f;
    L_0x0024:
        r3 = r6.writer;
        r4 = APOS;
        r3.write(r4);
        goto L_0x0135;
    L_0x002d:
        r3 = r6.writer;
        r4 = AMP;
        r3.write(r4);
        goto L_0x0135;
    L_0x0036:
        if (r8 != 0) goto L_0x003f;
    L_0x0038:
        r3 = r6.writer;
        r3.write(r2);
        goto L_0x0135;
    L_0x003f:
        r3 = java.lang.Character.isDefined(r2);
        if (r3 == 0) goto L_0x0082;
    L_0x0045:
        r3 = java.lang.Character.isISOControl(r2);
        if (r3 != 0) goto L_0x0082;
    L_0x004b:
        r3 = r6.mode;
        r4 = XML_QUIRKS;
        if (r3 == r4) goto L_0x007b;
    L_0x0051:
        r3 = 55295; // 0xd7ff float:7.7485E-41 double:2.73194E-319;
        if (r2 <= r3) goto L_0x007b;
    L_0x0056:
        r3 = 57344; // 0xe000 float:8.0356E-41 double:2.83317E-319;
        if (r2 >= r3) goto L_0x007b;
    L_0x005b:
        r3 = new com.thoughtworks.xstream.io.StreamException;
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r5 = "Invalid character 0x";
        r4.append(r5);
        r5 = java.lang.Integer.toHexString(r2);
        r4.append(r5);
        r5 = " in XML stream";
        r4.append(r5);
        r4 = r4.toString();
        r3.<init>(r4);
        throw r3;
    L_0x007b:
        r3 = r6.writer;
        r3.write(r2);
        goto L_0x0135;
    L_0x0082:
        r3 = r6.mode;
        r4 = XML_1_0;
        if (r3 != r4) goto L_0x00c0;
    L_0x0088:
        r3 = 9;
        if (r2 < r3) goto L_0x00a0;
    L_0x008c:
        r3 = 11;
        if (r2 == r3) goto L_0x00a0;
    L_0x0090:
        r3 = 12;
        if (r2 == r3) goto L_0x00a0;
    L_0x0094:
        r3 = 14;
        if (r2 == r3) goto L_0x00a0;
    L_0x0098:
        r3 = 15;
        if (r2 < r3) goto L_0x00c0;
    L_0x009c:
        r3 = 31;
        if (r2 > r3) goto L_0x00c0;
    L_0x00a0:
        r3 = new com.thoughtworks.xstream.io.StreamException;
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r5 = "Invalid character 0x";
        r4.append(r5);
        r5 = java.lang.Integer.toHexString(r2);
        r4.append(r5);
        r5 = " in XML 1.0 stream";
        r4.append(r5);
        r4 = r4.toString();
        r3.<init>(r4);
        throw r3;
    L_0x00c0:
        r3 = r6.mode;
        r4 = XML_QUIRKS;
        if (r3 == r4) goto L_0x00f0;
    L_0x00c6:
        r3 = 65534; // 0xfffe float:9.1833E-41 double:3.2378E-319;
        if (r2 == r3) goto L_0x00d0;
    L_0x00cb:
        r3 = 65535; // 0xffff float:9.1834E-41 double:3.23786E-319;
        if (r2 != r3) goto L_0x00f0;
    L_0x00d0:
        r3 = new com.thoughtworks.xstream.io.StreamException;
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r5 = "Invalid character 0x";
        r4.append(r5);
        r5 = java.lang.Integer.toHexString(r2);
        r4.append(r5);
        r5 = " in XML stream";
        r4.append(r5);
        r4 = r4.toString();
        r3.<init>(r4);
        throw r3;
    L_0x00f0:
        r3 = r6.writer;
        r4 = "&#x";
        r3.write(r4);
        r3 = r6.writer;
        r4 = java.lang.Integer.toHexString(r2);
        r3.write(r4);
        r3 = r6.writer;
        r4 = 59;
        r3.write(r4);
        goto L_0x0135;
    L_0x0108:
        r3 = r6.writer;
        r4 = GT;
        r3.write(r4);
        goto L_0x0135;
    L_0x0110:
        r3 = r6.writer;
        r4 = LT;
        r3.write(r4);
        goto L_0x0135;
    L_0x0118:
        r3 = r6.writer;
        r4 = QUOT;
        r3.write(r4);
        goto L_0x0135;
    L_0x0120:
        r3 = r6.writer;
        r4 = CR;
        r3.write(r4);
        goto L_0x0135;
    L_0x0128:
        r3 = r6.mode;
        r4 = XML_QUIRKS;
        if (r3 != r4) goto L_0x0139;
    L_0x012e:
        r3 = r6.writer;
        r4 = NULL;
        r3.write(r4);
    L_0x0135:
        r1 = r1 + 1;
        goto L_0x0005;
    L_0x0139:
        r3 = new com.thoughtworks.xstream.io.StreamException;
        r4 = "Invalid character 0x0 in XML stream";
        r3.<init>(r4);
        throw r3;
    L_0x0141:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.thoughtworks.xstream.io.xml.PrettyPrintWriter.writeText(java.lang.String, boolean):void");
    }

    public void endNode() {
        this.depth--;
        if (this.tagIsEmpty) {
            this.writer.write('/');
            this.readyForNewLine = false;
            finishTag();
            this.elementStack.popSilently();
        } else {
            finishTag();
            this.writer.write(CLOSE);
            this.writer.write((String) this.elementStack.pop());
            this.writer.write((char) Typography.greater);
        }
        this.readyForNewLine = true;
        if (this.depth == 0) {
            this.writer.flush();
        }
    }

    private void finishTag() {
        if (this.tagInProgress) {
            this.writer.write((char) Typography.greater);
        }
        this.tagInProgress = false;
        if (this.readyForNewLine) {
            endOfLine();
        }
        this.readyForNewLine = false;
        this.tagIsEmpty = false;
    }

    protected void endOfLine() {
        this.writer.write(getNewLine());
        for (int i = 0; i < this.depth; i++) {
            this.writer.write(this.lineIndenter);
        }
    }

    public void flush() {
        this.writer.flush();
    }

    public void close() {
        this.writer.close();
    }

    protected String getNewLine() {
        return this.newLine;
    }
}
