package org.rauschig.jarchivelib;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

public abstract class ArchiveStream extends InputStream implements Closeable {
    private boolean closed;
    private ArchiveEntry currentEntry;

    protected abstract ArchiveEntry createNextEntry() throws IOException;

    public ArchiveEntry getCurrentEntry() {
        return this.currentEntry;
    }

    public ArchiveEntry getNextEntry() throws IOException {
        this.currentEntry = createNextEntry();
        return this.currentEntry;
    }

    public void close() throws IOException {
        this.closed = true;
    }

    public boolean isClosed() {
        return this.closed;
    }
}
