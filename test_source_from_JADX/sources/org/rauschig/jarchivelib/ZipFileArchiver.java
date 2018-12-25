package org.rauschig.jarchivelib;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;

class ZipFileArchiver extends CommonsArchiver {

    static class ZipFileArchiveInputStream extends ArchiveInputStream {
        private ZipArchiveEntry currentEntry;
        private InputStream currentEntryStream;
        private Enumeration<ZipArchiveEntry> entries;
        private ZipFile file;

        public ZipFileArchiveInputStream(ZipFile file) {
            this.file = file;
        }

        public ZipArchiveEntry getNextEntry() throws IOException {
            Enumeration<ZipArchiveEntry> entries = getEntries();
            closeCurrentEntryStream();
            InputStream inputStream = null;
            this.currentEntry = entries.hasMoreElements() ? (ZipArchiveEntry) entries.nextElement() : null;
            if (this.currentEntry != null) {
                inputStream = this.file.getInputStream(this.currentEntry);
            }
            this.currentEntryStream = inputStream;
            return this.currentEntry;
        }

        public int read(byte[] b, int off, int len) throws IOException {
            int read = getCurrentEntryStream().read(b, off, len);
            if (read == -1) {
                IOUtils.closeQuietly(getCurrentEntryStream());
            }
            count(read);
            return read;
        }

        public boolean canReadEntryData(ArchiveEntry archiveEntry) {
            return archiveEntry == getCurrentEntry();
        }

        public ZipArchiveEntry getCurrentEntry() {
            return this.currentEntry;
        }

        public InputStream getCurrentEntryStream() {
            return this.currentEntryStream;
        }

        private Enumeration<ZipArchiveEntry> getEntries() {
            if (this.entries == null) {
                this.entries = this.file.getEntriesInPhysicalOrder();
            }
            return this.entries;
        }

        private void closeCurrentEntryStream() {
            IOUtils.closeQuietly(getCurrentEntryStream());
            this.currentEntryStream = null;
        }

        private void closeFile() {
            try {
                this.file.close();
            } catch (IOException e) {
            }
        }

        public void close() throws IOException {
            closeCurrentEntryStream();
            closeFile();
            super.close();
        }
    }

    ZipFileArchiver() {
        super(ArchiveFormat.ZIP);
    }

    protected ArchiveInputStream createArchiveInputStream(File archive) throws IOException {
        return new ZipFileArchiveInputStream(new ZipFile(archive));
    }
}
