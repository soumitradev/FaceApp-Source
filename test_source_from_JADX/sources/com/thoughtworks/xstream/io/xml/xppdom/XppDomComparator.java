package com.thoughtworks.xstream.io.xml.xppdom;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import org.catrobat.catroid.common.Constants;

public class XppDomComparator implements Comparator {
    private final ThreadLocal xpath;

    public XppDomComparator() {
        this(null);
    }

    public XppDomComparator(ThreadLocal xpath) {
        this.xpath = xpath;
    }

    public int compare(Object dom1, Object dom2) {
        StringBuffer xpath = new StringBuffer("/");
        int s = compareInternal((XppDom) dom1, (XppDom) dom2, xpath, -1);
        if (this.xpath != null) {
            if (s != 0) {
                this.xpath.set(xpath.toString());
            } else {
                this.xpath.set(null);
            }
        }
        return s;
    }

    private int compareInternal(XppDom dom1, XppDom dom2, StringBuffer xpath, int count) {
        XppDom xppDom = dom1;
        XppDom xppDom2 = dom2;
        StringBuffer stringBuffer = xpath;
        int pathlen = xpath.length();
        String name = dom1.getName();
        int s = name.compareTo(dom2.getName());
        stringBuffer.append(name);
        if (count >= 0) {
            stringBuffer.append(Constants.REMIX_URL_PREFIX_INDICATOR);
            xpath.append(count);
            stringBuffer.append(Constants.REMIX_URL_SUFIX_INDICATOR);
        }
        if (s != 0) {
            stringBuffer.append('?');
            return s;
        }
        String[] attributes = dom1.getAttributeNames();
        String[] attributes2 = dom2.getAttributeNames();
        int len = attributes.length;
        int s2 = attributes2.length - len;
        if (s2 != 0) {
            stringBuffer.append("::count(@*)");
            return s2 < 0 ? 1 : -1;
        }
        Arrays.sort(attributes);
        Arrays.sort(attributes2);
        for (s2 = 0; s2 < len; s2++) {
            String attribute = attributes[s2];
            s = attribute.compareTo(attributes2[s2]);
            if (s != 0) {
                stringBuffer.append("[@");
                stringBuffer.append(attribute);
                stringBuffer.append("?]");
                return s;
            }
            int s3 = xppDom.getAttribute(attribute).compareTo(xppDom2.getAttribute(attribute));
            if (s3 != 0) {
                stringBuffer.append("[@");
                stringBuffer.append(attribute);
                stringBuffer.append(Constants.REMIX_URL_SUFIX_INDICATOR);
                return s3;
            }
        }
        s = dom1.getChildCount();
        int s4 = dom2.getChildCount() - s;
        if (s4 != 0) {
            stringBuffer.append("::count(*)");
            return s4 < 0 ? 1 : -1;
        }
        int s5;
        int i;
        if (s > 0) {
            if (dom1.getValue() != null) {
                i = s;
                s = this;
            } else if (dom2.getValue() != null) {
                r17 = name;
                i = s;
                s = this;
            } else {
                stringBuffer.append('/');
                Map names = new HashMap();
                s5 = s4;
                s4 = 0;
                while (s4 < s) {
                    XppDom child1 = xppDom.getChild(s4);
                    XppDom child2 = xppDom2.getChild(s4);
                    String child = child1.getName();
                    if (names.containsKey(child)) {
                        r17 = name;
                        i = s;
                    } else {
                        r17 = name;
                        i = s;
                        names.put(child, new int[1]);
                    }
                    int[] iArr = (int[]) names.get(child);
                    int i2 = iArr[0];
                    iArr[0] = i2 + 1;
                    s5 = compareInternal(child1, child2, stringBuffer, i2);
                    if (s5 != 0) {
                        return s5;
                    }
                    s4++;
                    name = r17;
                    s = i;
                }
                i = s;
                s = this;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("XppDom cannot handle mixed mode at ");
            stringBuilder.append(stringBuffer);
            stringBuilder.append("::text()");
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        i = s;
        s = this;
        name = dom2.getValue();
        String value1 = dom1.getValue();
        if (value1 == null) {
            s4 = name == null ? 0 : -1;
        } else {
            s4 = name == null ? 1 : value1.compareTo(name);
        }
        s5 = s4;
        if (s5 != 0) {
            stringBuffer.append("::text()");
            return s5;
        }
        stringBuffer.setLength(pathlen);
        return s5;
    }
}
