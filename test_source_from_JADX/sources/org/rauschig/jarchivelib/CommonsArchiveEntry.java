package org.rauschig.jarchivelib;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import org.apache.commons.compress.archivers.ArchiveEntry;

class CommonsArchiveEntry implements ArchiveEntry {
    private ArchiveEntry entry;
    private ArchiveStream stream;

    CommonsArchiveEntry(ArchiveStream stream, ArchiveEntry entry) {
        this.stream = stream;
        this.entry = entry;
    }

    public String getName() {
        assertState();
        return this.entry.getName();
    }

    public long getSize() {
        assertState();
        return this.entry.getSize();
    }

    public Date getLastModifiedDate() {
        assertState();
        return this.entry.getLastModifiedDate();
    }

    public boolean isDirectory() {
        assertState();
        return this.entry.isDirectory();
    }

    public File extract(File destination) throws IOException, IllegalStateException, IllegalArgumentException {
        assertState();
        IOUtils.requireDirectory(destination);
        File file = new File(destination, this.entry.getName());
        FileModeMapper.map(this.entry, file);
        if (this.entry.isDirectory()) {
            file.mkdirs();
        } else {
            file.getParentFile().mkdirs();
            IOUtils.copy(this.stream, file);
        }
        return file;
    }

    private void assertState() {
        if (this.stream.isClosed()) {
            throw new IllegalStateException("Stream has already been closed");
        } else if (this != this.stream.getCurrentEntry()) {
            throw new IllegalStateException("Illegal stream pointer");
        }
    }
}
