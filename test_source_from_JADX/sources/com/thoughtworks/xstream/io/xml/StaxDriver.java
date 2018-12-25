package com.thoughtworks.xstream.io.xml;

import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.ReaderWrapper;
import com.thoughtworks.xstream.io.StreamException;
import com.thoughtworks.xstream.io.naming.NameCoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

public class StaxDriver extends AbstractXmlDriver {
    private XMLInputFactory inputFactory;
    private XMLOutputFactory outputFactory;
    private QNameMap qnameMap;

    public StaxDriver() {
        this(new QNameMap());
    }

    public StaxDriver(QNameMap qnameMap) {
        this(qnameMap, new XmlFriendlyNameCoder());
    }

    public StaxDriver(QNameMap qnameMap, NameCoder nameCoder) {
        super(nameCoder);
        this.qnameMap = qnameMap;
    }

    public StaxDriver(NameCoder nameCoder) {
        this(new QNameMap(), nameCoder);
    }

    public StaxDriver(QNameMap qnameMap, XmlFriendlyReplacer replacer) {
        this(qnameMap, (NameCoder) replacer);
    }

    public StaxDriver(XmlFriendlyReplacer replacer) {
        this(new QNameMap(), (NameCoder) replacer);
    }

    public HierarchicalStreamReader createReader(Reader xml) {
        try {
            return createStaxReader(createParser(xml));
        } catch (Throwable e) {
            throw new StreamException(e);
        }
    }

    public HierarchicalStreamReader createReader(InputStream in) {
        try {
            return createStaxReader(createParser(in));
        } catch (Throwable e) {
            throw new StreamException(e);
        }
    }

    public HierarchicalStreamReader createReader(URL in) {
        try {
            final InputStream stream = in.openStream();
            return new ReaderWrapper(createStaxReader(createParser(new StreamSource(stream, in.toExternalForm())))) {
                public void close() {
                    super.close();
                    try {
                        stream.close();
                    } catch (IOException e) {
                    }
                }
            };
        } catch (Throwable e) {
            throw new StreamException(e);
        } catch (Throwable e2) {
            throw new StreamException(e2);
        }
    }

    public HierarchicalStreamReader createReader(File in) {
        try {
            final InputStream stream = new FileInputStream(in);
            return new ReaderWrapper(createStaxReader(createParser(new StreamSource(stream, in.toURI().toASCIIString())))) {
                public void close() {
                    super.close();
                    try {
                        stream.close();
                    } catch (IOException e) {
                    }
                }
            };
        } catch (Throwable e) {
            throw new StreamException(e);
        } catch (Throwable e2) {
            throw new StreamException(e2);
        }
    }

    public HierarchicalStreamWriter createWriter(Writer out) {
        try {
            return createStaxWriter(getOutputFactory().createXMLStreamWriter(out));
        } catch (Throwable e) {
            throw new StreamException(e);
        }
    }

    public HierarchicalStreamWriter createWriter(OutputStream out) {
        try {
            return createStaxWriter(getOutputFactory().createXMLStreamWriter(out));
        } catch (Throwable e) {
            throw new StreamException(e);
        }
    }

    public AbstractPullReader createStaxReader(XMLStreamReader in) {
        return new StaxReader(this.qnameMap, in, getNameCoder());
    }

    public StaxWriter createStaxWriter(XMLStreamWriter out, boolean writeStartEndDocument) throws XMLStreamException {
        return new StaxWriter(this.qnameMap, out, writeStartEndDocument, isRepairingNamespace(), getNameCoder());
    }

    public StaxWriter createStaxWriter(XMLStreamWriter out) throws XMLStreamException {
        return createStaxWriter(out, true);
    }

    public QNameMap getQnameMap() {
        return this.qnameMap;
    }

    public void setQnameMap(QNameMap qnameMap) {
        this.qnameMap = qnameMap;
    }

    public XMLInputFactory getInputFactory() {
        if (this.inputFactory == null) {
            this.inputFactory = createInputFactory();
        }
        return this.inputFactory;
    }

    public XMLOutputFactory getOutputFactory() {
        if (this.outputFactory == null) {
            this.outputFactory = createOutputFactory();
        }
        return this.outputFactory;
    }

    public boolean isRepairingNamespace() {
        return Boolean.TRUE.equals(getOutputFactory().getProperty("javax.xml.stream.isRepairingNamespaces"));
    }

    public void setRepairingNamespace(boolean repairing) {
        getOutputFactory().setProperty("javax.xml.stream.isRepairingNamespaces", repairing ? Boolean.TRUE : Boolean.FALSE);
    }

    protected XMLStreamReader createParser(Reader xml) throws XMLStreamException {
        return getInputFactory().createXMLStreamReader(xml);
    }

    protected XMLStreamReader createParser(InputStream xml) throws XMLStreamException {
        return getInputFactory().createXMLStreamReader(xml);
    }

    protected XMLStreamReader createParser(Source source) throws XMLStreamException {
        return getInputFactory().createXMLStreamReader(source);
    }

    protected XMLInputFactory createInputFactory() {
        return XMLInputFactory.newInstance();
    }

    protected XMLOutputFactory createOutputFactory() {
        return XMLOutputFactory.newInstance();
    }
}
