package com.thoughtworks.xstream.core.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PushbackInputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import kotlin.text.Typography;

public final class XmlHeaderAwareReader extends Reader {
    private static final String KEY_ENCODING = "encoding";
    private static final String KEY_VERSION = "version";
    private static final int STATE_ATTR_NAME = 3;
    private static final int STATE_ATTR_VALUE = 4;
    private static final int STATE_AWAIT_XML_HEADER = 2;
    private static final int STATE_BOM = 0;
    private static final int STATE_START = 1;
    private static final String XML_TOKEN = "?xml";
    private final InputStreamReader reader;
    private final double version;

    public XmlHeaderAwareReader(InputStream in) throws UnsupportedEncodingException, IOException {
        PushbackInputStream[] pin = new PushbackInputStream[1];
        pin[0] = in instanceof PushbackInputStream ? (PushbackInputStream) in : new PushbackInputStream(in, 64);
        Map header = getHeader(pin);
        this.version = Double.parseDouble((String) header.get("version"));
        this.reader = new InputStreamReader(pin[0], (String) header.get(KEY_ENCODING));
    }

    private Map getHeader(PushbackInputStream[] in) throws IOException {
        byte[] pushbackData;
        Map header = new HashMap();
        header.put(KEY_ENCODING, "utf-8");
        header.put("version", "1.0");
        ByteArrayOutputStream out = new ByteArrayOutputStream(64);
        int i = 0;
        StringBuffer name = new StringBuffer();
        StringBuffer value = new StringBuffer();
        char valueEnd = '\u0000';
        int state = 0;
        boolean escape = false;
        while (i != -1) {
            int read = in[0].read();
            i = read;
            if (read != -1) {
                out.write(i);
                char ch = (char) i;
                switch (state) {
                    case 0:
                        if ((ch == 'ï' && out.size() == 1) || ((ch == Typography.rightGuillemete && out.size() == 2) || (ch == '¿' && out.size() == 3))) {
                            if (ch != '¿') {
                                break;
                            }
                            out.reset();
                            state = 1;
                            break;
                        } else if (out.size() > 1) {
                            i = -1;
                            break;
                        } else {
                            state = 1;
                        }
                        break;
                    case 1:
                        if (!Character.isWhitespace(ch)) {
                            if (ch != Typography.less) {
                                i = -1;
                                break;
                            }
                            state = 2;
                            break;
                        }
                        break;
                    case 2:
                        if (Character.isWhitespace(ch)) {
                            if (!name.toString().equals(XML_TOKEN)) {
                                i = -1;
                                break;
                            }
                            state = 3;
                            name.setLength(0);
                            break;
                        }
                        name.append(Character.toLowerCase(ch));
                        if (!XML_TOKEN.startsWith(name.substring(0))) {
                            i = -1;
                            break;
                        }
                        break;
                    case 3:
                        if (!Character.isWhitespace(ch)) {
                            if (ch != '=') {
                                ch = Character.toLowerCase(ch);
                                if (!Character.isLetter(ch)) {
                                    i = -1;
                                    break;
                                }
                                name.append(ch);
                                break;
                            }
                            state = 4;
                            break;
                        } else if (name.length() <= 0) {
                            break;
                        } else {
                            i = -1;
                            break;
                        }
                    case 4:
                        if (valueEnd != '\u0000') {
                            if (ch != '\\' || escape) {
                                if (ch == valueEnd && !escape) {
                                    valueEnd = '\u0000';
                                    state = 3;
                                    header.put(name.toString(), value.toString());
                                    name.setLength(0);
                                    value.setLength(0);
                                    break;
                                }
                                escape = false;
                                if (ch == '\n') {
                                    i = -1;
                                    break;
                                }
                                value.append(ch);
                                break;
                            }
                            escape = true;
                            break;
                        }
                        if (ch != Typography.quote) {
                            if (ch != '\'') {
                                i = -1;
                                break;
                            }
                        }
                        valueEnd = ch;
                        break;
                        break;
                    default:
                        break;
                }
            }
            pushbackData = out.toByteArray();
            i = pushbackData.length;
            while (true) {
                boolean escape2;
                read = i - 1;
                if (i <= 0) {
                    try {
                        in[0].unread(pushbackData[read]);
                        escape2 = escape;
                    } catch (IOException e) {
                        IOException ex = e;
                        escape2 = escape;
                        read++;
                        in[0] = new PushbackInputStream(in[0], read);
                    }
                    i = read;
                    escape = escape2;
                } else {
                    return header;
                }
            }
        }
        pushbackData = out.toByteArray();
        i = pushbackData.length;
        while (true) {
            read = i - 1;
            if (i <= 0) {
                return header;
            }
            in[0].unread(pushbackData[read]);
            escape2 = escape;
            i = read;
            escape = escape2;
        }
    }

    public String getEncoding() {
        return this.reader.getEncoding();
    }

    public double getVersion() {
        return this.version;
    }

    public void mark(int readAheadLimit) throws IOException {
        this.reader.mark(readAheadLimit);
    }

    public boolean markSupported() {
        return this.reader.markSupported();
    }

    public int read() throws IOException {
        return this.reader.read();
    }

    public int read(char[] cbuf, int offset, int length) throws IOException {
        return this.reader.read(cbuf, offset, length);
    }

    public int read(char[] cbuf) throws IOException {
        return this.reader.read(cbuf);
    }

    public boolean ready() throws IOException {
        return this.reader.ready();
    }

    public void reset() throws IOException {
        this.reader.reset();
    }

    public long skip(long n) throws IOException {
        return this.reader.skip(n);
    }

    public void close() throws IOException {
        this.reader.close();
    }

    public boolean equals(Object obj) {
        return this.reader.equals(obj);
    }

    public int hashCode() {
        return this.reader.hashCode();
    }

    public String toString() {
        return this.reader.toString();
    }
}
