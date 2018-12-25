package com.thoughtworks.xstream.io.xml;

import com.thoughtworks.xstream.XStream;
import java.util.ArrayList;
import java.util.List;
import javax.xml.transform.sax.SAXSource;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLFilter;
import org.xml.sax.XMLReader;

public class TraxSource extends SAXSource {
    public static final String XSTREAM_FEATURE = "http://com.thoughtworks.xstream/XStreamSource/feature";
    private List source = null;
    private XMLReader xmlReader = null;
    private XStream xstream = null;

    public TraxSource() {
        super(new InputSource());
    }

    public TraxSource(Object source) {
        super(new InputSource());
        setSource(source);
    }

    public TraxSource(Object source, XStream xstream) {
        super(new InputSource());
        setSource(source);
        setXStream(xstream);
    }

    public TraxSource(List source) {
        super(new InputSource());
        setSourceAsList(source);
    }

    public TraxSource(List source, XStream xstream) {
        super(new InputSource());
        setSourceAsList(source);
        setXStream(xstream);
    }

    public void setInputSource(InputSource inputSource) {
        throw new UnsupportedOperationException();
    }

    public void setXMLReader(XMLReader reader) {
        createXMLReader(reader);
    }

    public XMLReader getXMLReader() {
        if (this.xmlReader == null) {
            createXMLReader(null);
        }
        return this.xmlReader;
    }

    public void setXStream(XStream xstream) {
        if (xstream == null) {
            throw new IllegalArgumentException("xstream");
        }
        this.xstream = xstream;
        configureXMLReader();
    }

    public void setSource(Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("obj");
        }
        List list = new ArrayList(1);
        list.add(obj);
        setSourceAsList(list);
    }

    public void setSourceAsList(List list) {
        if (list != null) {
            if (!list.isEmpty()) {
                this.source = list;
                configureXMLReader();
                return;
            }
        }
        throw new IllegalArgumentException("list");
    }

    private void createXMLReader(XMLReader filterChain) {
        if (filterChain == null) {
            this.xmlReader = new SaxWriter();
        } else if (filterChain instanceof XMLFilter) {
            XMLFilter filter = (XMLFilter) filterChain;
            while (filter.getParent() instanceof XMLFilter) {
                filter = (XMLFilter) filter.getParent();
            }
            if (!(filter.getParent() instanceof SaxWriter)) {
                filter.setParent(new SaxWriter());
            }
            this.xmlReader = filterChain;
        } else {
            throw new UnsupportedOperationException();
        }
        configureXMLReader();
    }

    private void configureXMLReader() {
        if (this.xmlReader != null) {
            try {
                if (this.xstream != null) {
                    this.xmlReader.setProperty(SaxWriter.CONFIGURED_XSTREAM_PROPERTY, this.xstream);
                }
                if (this.source != null) {
                    this.xmlReader.setProperty(SaxWriter.SOURCE_OBJECT_LIST_PROPERTY, this.source);
                }
            } catch (SAXException e) {
                throw new IllegalArgumentException(e.getMessage());
            }
        }
    }
}
