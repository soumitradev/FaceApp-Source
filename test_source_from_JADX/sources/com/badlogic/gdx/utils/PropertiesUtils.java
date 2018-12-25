package com.badlogic.gdx.utils;

import com.badlogic.gdx.utils.ObjectMap.Entry;
import com.google.common.base.Ascii;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Date;
import java.util.Iterator;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.physics.content.bricks.CollisionReceiverBrick;

public final class PropertiesUtils {
    private static final int CONTINUE = 3;
    private static final int IGNORE = 5;
    private static final int KEY_DONE = 4;
    private static final String LINE_SEPARATOR = "\n";
    private static final int NONE = 0;
    private static final int SLASH = 1;
    private static final int UNICODE = 2;

    private PropertiesUtils() {
    }

    public static void load(ObjectMap<String, String> properties, Reader reader) throws IOException {
        ObjectMap<String, String> objectMap = properties;
        Reader reader2 = reader;
        if (objectMap == null) {
            throw new NullPointerException("ObjectMap cannot be null");
        } else if (reader2 == null) {
            throw new NullPointerException("Reader cannot be null");
        } else {
            int mode = 0;
            int unicode = 0;
            int count = 0;
            char[] buf = new char[40];
            int offset = 0;
            int keyLength = -1;
            boolean firstChar = true;
            BufferedReader br = new BufferedReader(reader2);
            while (true) {
                int intVal = br.read();
                String temp;
                if (intVal != -1) {
                    int digit;
                    char nextChar = (char) intVal;
                    if (offset == buf.length) {
                        char[] newBuf = new char[(buf.length * 2)];
                        System.arraycopy(buf, 0, newBuf, 0, offset);
                        buf = newBuf;
                    }
                    if (mode == 2) {
                        digit = Character.digit(nextChar, 16);
                        if (digit >= 0) {
                            unicode = (unicode << 4) + digit;
                            count++;
                            if (count < 4) {
                            }
                        } else if (count <= 4) {
                            throw new IllegalArgumentException("Invalid Unicode sequence: illegal character");
                        }
                        mode = 0;
                        int offset2 = offset + 1;
                        buf[offset] = (char) unicode;
                        if (nextChar != '\n') {
                            offset = offset2;
                        } else {
                            offset = offset2;
                        }
                    }
                    if (mode == 1) {
                        mode = 0;
                        if (nextChar == '\n') {
                            mode = 5;
                        } else if (nextChar == '\r') {
                            mode = 3;
                        } else if (nextChar == 'b') {
                            nextChar = '\b';
                        } else if (nextChar == 'f') {
                            nextChar = '\f';
                        } else if (nextChar == 'n') {
                            nextChar = '\n';
                        } else if (nextChar != 'r') {
                            switch (nextChar) {
                                case 't':
                                    nextChar = '\t';
                                    break;
                                case 'u':
                                    mode = 2;
                                    count = 0;
                                    unicode = 0;
                                    continue;
                                default:
                                    break;
                            }
                        } else {
                            nextChar = '\r';
                        }
                    } else {
                        if (nextChar != '\n') {
                            if (nextChar != '\r') {
                                if (nextChar == '!' || nextChar == '#') {
                                    if (firstChar) {
                                        do {
                                            intVal = br.read();
                                            if (intVal != -1) {
                                                nextChar = (char) intVal;
                                                if (nextChar != '\r') {
                                                }
                                            }
                                        } while (nextChar != '\n');
                                    }
                                } else if (nextChar == ':' || nextChar == '=') {
                                    if (keyLength == -1) {
                                        mode = 0;
                                        keyLength = offset;
                                    }
                                } else if (nextChar == '\\') {
                                    if (mode == 4) {
                                        keyLength = offset;
                                    }
                                    mode = 1;
                                }
                                if (Character.isSpace(nextChar)) {
                                    if (mode == 3) {
                                        mode = 5;
                                    }
                                    if (!(offset == 0 || offset == keyLength)) {
                                        if (mode != 5) {
                                            if (keyLength == -1) {
                                                mode = 4;
                                            }
                                        }
                                    }
                                }
                                if (mode == 5 || mode == 3) {
                                    mode = 0;
                                }
                            }
                        } else if (mode == 3) {
                            mode = 5;
                        }
                        mode = 0;
                        firstChar = true;
                        if (offset > 0 || (offset == 0 && keyLength == 0)) {
                            if (keyLength == -1) {
                                keyLength = offset;
                            }
                            temp = new String(buf, 0, offset);
                            objectMap.put(temp.substring(0, keyLength), temp.substring(keyLength));
                        }
                        keyLength = -1;
                        offset = 0;
                    }
                    firstChar = false;
                    if (mode == 4) {
                        keyLength = offset;
                        mode = 0;
                    }
                    digit = offset + 1;
                    buf[offset] = nextChar;
                    offset = digit;
                } else if (mode != 2 || count > 4) {
                    if (keyLength == -1 && offset > 0) {
                        keyLength = offset;
                    }
                    if (keyLength >= 0) {
                        temp = new String(buf, 0, offset);
                        String key = temp.substring(0, keyLength);
                        String value = temp.substring(keyLength);
                        if (mode == 1) {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append(value);
                            stringBuilder.append(CollisionReceiverBrick.ANYTHING_ESCAPE_CHAR);
                            value = stringBuilder.toString();
                        }
                        objectMap.put(key, value);
                        return;
                    }
                    return;
                } else {
                    throw new IllegalArgumentException("Invalid Unicode sequence: expected format \\uxxxx");
                }
            }
        }
    }

    public static void store(ObjectMap<String, String> properties, Writer writer, String comment) throws IOException {
        storeImpl(properties, writer, comment, false);
    }

    private static void storeImpl(ObjectMap<String, String> properties, Writer writer, String comment, boolean escapeUnicode) throws IOException {
        if (comment != null) {
            writeComment(writer, comment);
        }
        writer.write(Constants.ACTION_SPRITE_SEPARATOR);
        writer.write(new Date().toString());
        writer.write(LINE_SEPARATOR);
        StringBuilder sb = new StringBuilder(200);
        Iterator i$ = properties.entries().iterator();
        while (i$.hasNext()) {
            Entry<String, String> entry = (Entry) i$.next();
            dumpString(sb, (String) entry.key, true, escapeUnicode);
            sb.append('=');
            dumpString(sb, (String) entry.value, false, escapeUnicode);
            writer.write(LINE_SEPARATOR);
            writer.write(sb.toString());
            sb.setLength(0);
        }
        writer.flush();
    }

    private static void dumpString(StringBuilder outBuffer, String string, boolean escapeSpace, boolean escapeUnicode) {
        int len = string.length();
        for (int i = 0; i < len; i++) {
            char ch = string.charAt(i);
            if (ch <= '=' || ch >= Ascii.MAX) {
                switch (ch) {
                    case '\t':
                        outBuffer.append("\\t");
                        break;
                    case '\n':
                        outBuffer.append("\\n");
                        break;
                    case '\f':
                        outBuffer.append("\\f");
                        break;
                    case '\r':
                        outBuffer.append("\\r");
                        break;
                    case ' ':
                        if (i != 0 && !escapeSpace) {
                            break;
                        }
                        outBuffer.append("\\ ");
                        break;
                    case '!':
                    case '#':
                    case ':':
                    case '=':
                        outBuffer.append('\\').append(ch);
                        break;
                    default:
                        int i2;
                        String hex;
                        int j;
                        if (ch >= ' ') {
                            if (ch <= '~') {
                                i2 = 0;
                                if ((i2 & escapeUnicode) != 0) {
                                    outBuffer.append(ch);
                                    break;
                                }
                                hex = Integer.toHexString(ch);
                                outBuffer.append("\\u");
                                for (j = 0; j < 4 - hex.length(); j++) {
                                    outBuffer.append('0');
                                }
                                outBuffer.append(hex);
                                break;
                            }
                        }
                        i2 = 1;
                        if ((i2 & escapeUnicode) != 0) {
                            outBuffer.append(ch);
                        } else {
                            hex = Integer.toHexString(ch);
                            outBuffer.append("\\u");
                            for (j = 0; j < 4 - hex.length(); j++) {
                                outBuffer.append('0');
                            }
                            outBuffer.append(hex);
                        }
                }
            } else {
                outBuffer.append(ch == '\\' ? "\\\\" : Character.valueOf(ch));
            }
        }
    }

    private static void writeComment(Writer writer, String comment) throws IOException {
        writer.write(Constants.ACTION_SPRITE_SEPARATOR);
        int len = comment.length();
        int curIndex = 0;
        int lastIndex = 0;
        while (curIndex < len) {
            char c = comment.charAt(curIndex);
            if (c > 'ÿ' || c == '\n' || c == '\r') {
                if (lastIndex != curIndex) {
                    writer.write(comment.substring(lastIndex, curIndex));
                }
                if (c > 'ÿ') {
                    String hex = Integer.toHexString(c);
                    writer.write("\\u");
                    for (int j = 0; j < 4 - hex.length(); j++) {
                        writer.write(48);
                    }
                    writer.write(hex);
                } else {
                    writer.write(LINE_SEPARATOR);
                    if (c == '\r' && curIndex != len - 1 && comment.charAt(curIndex + 1) == '\n') {
                        curIndex++;
                    }
                    if (curIndex == len - 1 || !(comment.charAt(curIndex + 1) == '#' || comment.charAt(curIndex + 1) == '!')) {
                        writer.write(Constants.ACTION_SPRITE_SEPARATOR);
                    }
                }
                lastIndex = curIndex + 1;
            }
            curIndex++;
        }
        if (lastIndex != curIndex) {
            writer.write(comment.substring(lastIndex, curIndex));
        }
        writer.write(LINE_SEPARATOR);
    }
}
