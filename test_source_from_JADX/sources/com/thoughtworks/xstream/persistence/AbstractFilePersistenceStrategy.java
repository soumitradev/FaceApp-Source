package com.thoughtworks.xstream.persistence;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.ConverterLookup;
import com.thoughtworks.xstream.io.StreamException;
import com.thoughtworks.xstream.mapper.Mapper;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.Iterator;
import java.util.Map.Entry;

public abstract class AbstractFilePersistenceStrategy implements PersistenceStrategy {
    private final File baseDirectory;
    private final String encoding;
    private final FilenameFilter filter = new ValidFilenameFilter();
    private final transient XStream xstream;

    protected class ValidFilenameFilter implements FilenameFilter {
        protected ValidFilenameFilter() {
        }

        public boolean accept(File dir, String name) {
            return new File(dir, name).isFile() && AbstractFilePersistenceStrategy.this.isValid(dir, name);
        }
    }

    protected class XmlMapEntriesIterator implements Iterator {
        private File current = null;
        private final File[] files = AbstractFilePersistenceStrategy.this.baseDirectory.listFiles(AbstractFilePersistenceStrategy.this.filter);
        private int position = -1;

        /* renamed from: com.thoughtworks.xstream.persistence.AbstractFilePersistenceStrategy$XmlMapEntriesIterator$1 */
        class C16941 implements Entry {
            private final File file = XmlMapEntriesIterator.this.current = XmlMapEntriesIterator.this.files[XmlMapEntriesIterator.access$404(XmlMapEntriesIterator.this)];
            private final Object key = AbstractFilePersistenceStrategy.this.extractKey(this.file.getName());

            C16941() {
            }

            public Object getKey() {
                return this.key;
            }

            public Object getValue() {
                return AbstractFilePersistenceStrategy.this.readFile(this.file);
            }

            public Object setValue(Object value) {
                return AbstractFilePersistenceStrategy.this.put(this.key, value);
            }

            /* JADX WARNING: inconsistent code. */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public boolean equals(java.lang.Object r8) {
                /*
                r7 = this;
                r0 = r8 instanceof java.util.Map.Entry;
                r1 = 0;
                if (r0 != 0) goto L_0x0006;
            L_0x0005:
                return r1;
            L_0x0006:
                r0 = r7.getValue();
                r2 = r8;
                r2 = (java.util.Map.Entry) r2;
                r3 = r2.getKey();
                r4 = r2.getValue();
                r5 = r7.key;
                if (r5 != 0) goto L_0x001c;
            L_0x0019:
                if (r3 != 0) goto L_0x0039;
            L_0x001b:
                goto L_0x0024;
            L_0x001c:
                r5 = r7.key;
                r5 = r5.equals(r3);
                if (r5 == 0) goto L_0x0039;
            L_0x0024:
                if (r0 != 0) goto L_0x0029;
            L_0x0026:
                if (r4 != 0) goto L_0x0039;
            L_0x0028:
                goto L_0x0037;
            L_0x0029:
                r5 = r7.getValue();
                r6 = r2.getValue();
                r5 = r5.equals(r6);
                if (r5 == 0) goto L_0x0039;
            L_0x0037:
                r1 = 1;
            L_0x0039:
                return r1;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.thoughtworks.xstream.persistence.AbstractFilePersistenceStrategy.XmlMapEntriesIterator.1.equals(java.lang.Object):boolean");
            }
        }

        protected XmlMapEntriesIterator() {
        }

        static /* synthetic */ int access$404(XmlMapEntriesIterator x0) {
            int i = x0.position + 1;
            x0.position = i;
            return i;
        }

        public boolean hasNext() {
            return this.position + 1 < this.files.length;
        }

        public void remove() {
            if (this.current == null) {
                throw new IllegalStateException();
            }
            this.current.delete();
        }

        public Object next() {
            return new C16941();
        }
    }

    protected abstract Object extractKey(String str);

    protected abstract String getName(Object obj);

    public AbstractFilePersistenceStrategy(File baseDirectory, XStream xstream, String encoding) {
        this.baseDirectory = baseDirectory;
        this.xstream = xstream;
        this.encoding = encoding;
    }

    protected ConverterLookup getConverterLookup() {
        return this.xstream.getConverterLookup();
    }

    protected Mapper getMapper() {
        return this.xstream.getMapper();
    }

    protected boolean isValid(File dir, String name) {
        return name.endsWith(".xml");
    }

    private void writeFile(File file, Object value) {
        Writer writer;
        try {
            FileOutputStream out = new FileOutputStream(file);
            writer = this.encoding != null ? new OutputStreamWriter(out, this.encoding) : new OutputStreamWriter(out);
            this.xstream.toXML(value, writer);
            writer.close();
        } catch (Throwable e) {
            throw new StreamException(e);
        } catch (Throwable th) {
            writer.close();
        }
    }

    private File getFile(String filename) {
        return new File(this.baseDirectory, filename);
    }

    private Object readFile(File file) {
        Reader reader;
        try {
            FileInputStream in = new FileInputStream(file);
            reader = this.encoding != null ? new InputStreamReader(in, this.encoding) : new InputStreamReader(in);
            Object fromXML = this.xstream.fromXML(reader);
            reader.close();
            return fromXML;
        } catch (FileNotFoundException e) {
            return null;
        } catch (Throwable e2) {
            throw new StreamException(e2);
        } catch (Throwable th) {
            reader.close();
        }
    }

    public Object put(Object key, Object value) {
        Object oldValue = get(key);
        writeFile(new File(this.baseDirectory, getName(key)), value);
        return oldValue;
    }

    public Iterator iterator() {
        return new XmlMapEntriesIterator();
    }

    public int size() {
        return this.baseDirectory.list(this.filter).length;
    }

    public boolean containsKey(Object key) {
        return getFile(getName(key)).isFile();
    }

    public Object get(Object key) {
        return readFile(getFile(getName(key)));
    }

    public Object remove(Object key) {
        File file = getFile(getName(key));
        if (!file.isFile()) {
            return null;
        }
        Object value = readFile(file);
        file.delete();
        return value;
    }
}
