package org.rauschig.jarchivelib;

import java.io.IOException;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;

class CommonsArchiveStream extends ArchiveStream {
    private ArchiveInputStream stream;

    CommonsArchiveStream(ArchiveInputStream stream) {
        this.stream = stream;
    }

    protected ArchiveEntry createNextEntry() throws IOException {
        ArchiveEntry next = this.stream.getNextEntry();
        return next == null ? null : new CommonsArchiveEntry(this, next);
    }

    public int read() throws IOException {
        return this.stream.read();
    }

    public int read(byte[] b) throws IOException {
        return this.stream.read(b);
    }

    public int read(byte[] b, int off, int len) throws IOException {
        return this.stream.read(b, off, len);
    }

    public void close() throws IOException {
        super.close();
        this.stream.close();
    }
}
