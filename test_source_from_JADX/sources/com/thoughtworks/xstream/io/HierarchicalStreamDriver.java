package com.thoughtworks.xstream.io;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;

public interface HierarchicalStreamDriver {
    HierarchicalStreamReader createReader(File file);

    HierarchicalStreamReader createReader(InputStream inputStream);

    HierarchicalStreamReader createReader(Reader reader);

    HierarchicalStreamReader createReader(URL url);

    HierarchicalStreamWriter createWriter(OutputStream outputStream);

    HierarchicalStreamWriter createWriter(Writer writer);
}
