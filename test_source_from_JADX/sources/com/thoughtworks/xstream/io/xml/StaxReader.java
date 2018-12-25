package com.thoughtworks.xstream.io.xml;

import com.thoughtworks.xstream.converters.ErrorWriter;
import com.thoughtworks.xstream.io.StreamException;
import com.thoughtworks.xstream.io.naming.NameCoder;
import javax.xml.stream.XMLStreamReader;

public class StaxReader extends AbstractPullReader {
    private final XMLStreamReader in;
    private final QNameMap qnameMap;

    public StaxReader(QNameMap qnameMap, XMLStreamReader in) {
        this(qnameMap, in, new XmlFriendlyNameCoder());
    }

    public StaxReader(QNameMap qnameMap, XMLStreamReader in, NameCoder replacer) {
        super(replacer);
        this.qnameMap = qnameMap;
        this.in = in;
        moveDown();
    }

    public StaxReader(QNameMap qnameMap, XMLStreamReader in, XmlFriendlyReplacer replacer) {
        this(qnameMap, in, (NameCoder) replacer);
    }

    protected int pullNextEvent() {
        try {
            switch (this.in.next()) {
                case 1:
                case 7:
                    return 1;
                case 2:
                case 8:
                    return 2;
                case 4:
                    return 3;
                case 5:
                    return 4;
                default:
                    return 0;
            }
        } catch (Throwable e) {
            throw new StreamException(e);
        }
    }

    protected String pullElementName() {
        return this.qnameMap.getJavaClassName(this.in.getName());
    }

    protected String pullText() {
        return this.in.getText();
    }

    public String getAttribute(String name) {
        return this.in.getAttributeValue(null, encodeAttribute(name));
    }

    public String getAttribute(int index) {
        return this.in.getAttributeValue(index);
    }

    public int getAttributeCount() {
        return this.in.getAttributeCount();
    }

    public String getAttributeName(int index) {
        return decodeAttribute(this.in.getAttributeLocalName(index));
    }

    public void appendErrors(ErrorWriter errorWriter) {
        errorWriter.add("line number", String.valueOf(this.in.getLocation().getLineNumber()));
    }

    public void close() {
        try {
            this.in.close();
        } catch (Throwable e) {
            throw new StreamException(e);
        }
    }
}
