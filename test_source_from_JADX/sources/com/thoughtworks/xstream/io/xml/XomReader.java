package com.thoughtworks.xstream.io.xml;

import com.thoughtworks.xstream.io.naming.NameCoder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.Node;
import nu.xom.Text;

public class XomReader extends AbstractDocumentReader {
    private Element currentElement;

    public XomReader(Element rootElement) {
        super(rootElement);
    }

    public XomReader(Document document) {
        super(document.getRootElement());
    }

    public XomReader(Element rootElement, NameCoder nameCoder) {
        super((Object) rootElement, nameCoder);
    }

    public XomReader(Document document, NameCoder nameCoder) {
        super(document.getRootElement(), nameCoder);
    }

    public XomReader(Element rootElement, XmlFriendlyReplacer replacer) {
        this(rootElement, (NameCoder) replacer);
    }

    public XomReader(Document document, XmlFriendlyReplacer replacer) {
        this(document.getRootElement(), (NameCoder) replacer);
    }

    public String getNodeName() {
        return decodeNode(this.currentElement.getLocalName());
    }

    public String getValue() {
        StringBuffer result = new StringBuffer();
        int childCount = this.currentElement.getChildCount();
        for (int i = 0; i < childCount; i++) {
            Node child = this.currentElement.getChild(i);
            if (child instanceof Text) {
                result.append(((Text) child).getValue());
            }
        }
        return result.toString();
    }

    public String getAttribute(String name) {
        return this.currentElement.getAttributeValue(encodeAttribute(name));
    }

    public String getAttribute(int index) {
        return this.currentElement.getAttribute(index).getValue();
    }

    public int getAttributeCount() {
        return this.currentElement.getAttributeCount();
    }

    public String getAttributeName(int index) {
        return decodeAttribute(this.currentElement.getAttribute(index).getQualifiedName());
    }

    protected int getChildCount() {
        return this.currentElement.getChildElements().size();
    }

    protected Object getParent() {
        return this.currentElement.getParent();
    }

    protected Object getChild(int index) {
        return this.currentElement.getChildElements().get(index);
    }

    protected void reassignCurrentElement(Object current) {
        this.currentElement = (Element) current;
    }

    public String peekNextChild() {
        Elements children = this.currentElement.getChildElements();
        if (children != null) {
            if (children.size() != 0) {
                return decodeNode(children.get(0).getLocalName());
            }
        }
        return null;
    }
}
