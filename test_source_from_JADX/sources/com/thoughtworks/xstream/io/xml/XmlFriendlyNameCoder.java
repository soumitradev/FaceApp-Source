package com.thoughtworks.xstream.io.xml;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.facebook.appevents.AppEventsConstants;
import com.google.common.base.Ascii;
import com.thoughtworks.xstream.converters.reflection.ObjectAccessException;
import com.thoughtworks.xstream.io.naming.NameCoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import kotlin.text.Typography;
import name.antonsmirnov.firmata.message.SamplingIntervalMessage;
import name.antonsmirnov.firmata.writer.ReportAnalogPinMessageWriter;
import org.apache.commons.compress.archivers.tar.TarConstants;

public class XmlFriendlyNameCoder implements NameCoder, Cloneable {
    private static final IntPair[] XML_NAME_CHAR_EXTRA_BOUNDS;
    private static final IntPair[] XML_NAME_START_CHAR_BOUNDS;
    private final String dollarReplacement;
    private transient Map escapeCache;
    private final String escapeCharReplacement;
    private final String hexPrefix;
    private transient Map unescapeCache;

    private static class IntPair {
        int max;
        int min;

        public IntPair(int min, int max) {
            this.min = min;
            this.max = max;
        }
    }

    static {
        AnonymousClass1IntPairList list = new ArrayList() {
            void add(int min, int max) {
                super.add(new IntPair(min, max));
            }

            void add(char cp) {
                super.add(new IntPair(cp, cp));
            }
        };
        list.add(':');
        list.add(65, 90);
        list.add(97, SamplingIntervalMessage.COMMAND);
        list.add('_');
        list.add(ReportAnalogPinMessageWriter.COMMAND, 214);
        list.add(216, Keys.F3);
        list.add(Keys.F5, 767);
        list.add(880, 893);
        list.add(895, 8191);
        list.add(8204, 8205);
        list.add(8304, 8591);
        list.add(11264, 12271);
        list.add(12289, 55295);
        list.add(63744, 64975);
        list.add(65008, 65533);
        list.add(65536, 983039);
        XML_NAME_START_CHAR_BOUNDS = (IntPair[]) list.toArray(new IntPair[list.size()]);
        list.clear();
        list.add('-');
        list.add('.');
        list.add(48, 57);
        list.add(Typography.middleDot);
        list.add(GL20.GL_SRC_COLOR, 879);
        list.add(8255, 8256);
        XML_NAME_CHAR_EXTRA_BOUNDS = (IntPair[]) list.toArray(new IntPair[list.size()]);
    }

    public XmlFriendlyNameCoder() {
        this("_-", "__");
    }

    public XmlFriendlyNameCoder(String dollarReplacement, String escapeCharReplacement) {
        this(dollarReplacement, escapeCharReplacement, "_.");
    }

    public XmlFriendlyNameCoder(String dollarReplacement, String escapeCharReplacement, String hexPrefix) {
        this.dollarReplacement = dollarReplacement;
        this.escapeCharReplacement = escapeCharReplacement;
        this.hexPrefix = hexPrefix;
        readResolve();
    }

    public String decodeAttribute(String attributeName) {
        return decodeName(attributeName);
    }

    public String decodeNode(String elementName) {
        return decodeName(elementName);
    }

    public String encodeAttribute(String name) {
        return encodeName(name);
    }

    public String encodeNode(String name) {
        return encodeName(name);
    }

    private String encodeName(String name) {
        String s = (String) this.escapeCache.get(name);
        if (s == null) {
            int length = name.length();
            int i = 0;
            while (i < length) {
                char c = name.charAt(i);
                if (c == Typography.dollar || c == '_' || c <= '\u001b') {
                    break;
                } else if (c >= Ascii.MAX) {
                    break;
                } else {
                    i++;
                }
            }
            if (i == length) {
                return name;
            }
            StringBuffer result = new StringBuffer(length + 8);
            if (i > 0) {
                result.append(name.substring(0, i));
            }
            while (i < length) {
                char c2 = name.charAt(i);
                if (c2 == Typography.dollar) {
                    result.append(this.dollarReplacement);
                } else if (c2 == '_') {
                    result.append(this.escapeCharReplacement);
                } else if ((i != 0 || isXmlNameStartChar(c2)) && (i <= 0 || isXmlNameChar(c2))) {
                    result.append(c2);
                } else {
                    result.append(this.hexPrefix);
                    if (c2 < '\u0010') {
                        result.append("000");
                    } else if (c2 < 'Ā') {
                        result.append(TarConstants.VERSION_POSIX);
                    } else if (c2 < 'က') {
                        result.append(AppEventsConstants.EVENT_PARAM_VALUE_NO);
                    }
                    result.append(Integer.toHexString(c2));
                }
                i++;
            }
            s = result.toString();
            this.escapeCache.put(name, s);
        }
        return s;
    }

    private String decodeName(String name) {
        String s = (String) this.unescapeCache.get(name);
        if (s == null) {
            char dollarReplacementFirstChar = this.dollarReplacement.charAt(0);
            char escapeReplacementFirstChar = this.escapeCharReplacement.charAt(0);
            char hexPrefixFirstChar = this.hexPrefix.charAt(0);
            int length = name.length();
            int i = 0;
            while (i < length) {
                char c = name.charAt(i);
                if (c == dollarReplacementFirstChar || c == escapeReplacementFirstChar) {
                    break;
                } else if (c == hexPrefixFirstChar) {
                    break;
                } else {
                    i++;
                }
            }
            if (i == length) {
                return name;
            }
            StringBuffer result = new StringBuffer(length + 8);
            if (i > 0) {
                result.append(name.substring(0, i));
            }
            while (i < length) {
                char c2 = name.charAt(i);
                if (c2 == dollarReplacementFirstChar && name.startsWith(this.dollarReplacement, i)) {
                    i += this.dollarReplacement.length() - 1;
                    result.append(Typography.dollar);
                } else if (c2 == hexPrefixFirstChar && name.startsWith(this.hexPrefix, i)) {
                    i += this.hexPrefix.length();
                    i += 3;
                    result.append((char) Integer.parseInt(name.substring(i, i + 4), 16));
                } else if (c2 == escapeReplacementFirstChar && name.startsWith(this.escapeCharReplacement, i)) {
                    i += this.escapeCharReplacement.length() - 1;
                    result.append('_');
                } else {
                    result.append(c2);
                }
                i++;
            }
            s = result.toString();
            this.unescapeCache.put(name, s);
        }
        return s;
    }

    public Object clone() {
        try {
            XmlFriendlyNameCoder coder = (XmlFriendlyNameCoder) super.clone();
            coder.readResolve();
            return coder;
        } catch (CloneNotSupportedException e) {
            throw new ObjectAccessException("Cannot clone XmlFriendlyNameCoder", e);
        }
    }

    private Object readResolve() {
        this.escapeCache = createCacheMap();
        this.unescapeCache = createCacheMap();
        return this;
    }

    protected Map createCacheMap() {
        return new HashMap();
    }

    private static boolean isXmlNameStartChar(int cp) {
        return isInNameCharBounds(cp, XML_NAME_START_CHAR_BOUNDS);
    }

    private static boolean isXmlNameChar(int cp) {
        if (isXmlNameStartChar(cp)) {
            return true;
        }
        return isInNameCharBounds(cp, XML_NAME_CHAR_EXTRA_BOUNDS);
    }

    private static boolean isInNameCharBounds(int cp, IntPair[] nameCharBounds) {
        for (IntPair p : nameCharBounds) {
            if (cp >= p.min && cp <= p.max) {
                return true;
            }
        }
        return false;
    }
}
