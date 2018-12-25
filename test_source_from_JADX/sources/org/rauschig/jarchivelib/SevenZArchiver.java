package org.rauschig.jarchivelib;

import java.io.File;
import java.io.IOException;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;
import org.apache.commons.compress.archivers.sevenz.SevenZOutputFile;

class SevenZArchiver extends CommonsArchiver {

    static class SevenZInputStream extends ArchiveInputStream {
        private SevenZFile file;

        public SevenZInputStream(SevenZFile file) {
            this.file = file;
        }

        public int read(byte[] b, int off, int len) throws IOException {
            return this.file.read(b, off, len);
        }

        public ArchiveEntry getNextEntry() throws IOException {
            return this.file.getNextEntry();
        }

        public void close() throws IOException {
            this.file.close();
        }
    }

    static class SevenZOutputStream extends ArchiveOutputStream {
        private SevenZOutputFile file;

        public SevenZOutputStream(SevenZOutputFile file) {
            this.file = file;
        }

        public void putArchiveEntry(ArchiveEntry entry) throws IOException {
            this.file.putArchiveEntry(entry);
        }

        public void closeArchiveEntry() throws IOException {
            this.file.closeArchiveEntry();
        }

        public void finish() throws IOException {
            this.file.finish();
        }

        public ArchiveEntry createArchiveEntry(File inputFile, String entryName) throws IOException {
            return this.file.createArchiveEntry(inputFile, entryName);
        }

        public void write(int b) throws IOException {
            this.file.write(b);
        }

        public void write(byte[] b) throws IOException {
            this.file.write(b);
        }

        public void write(byte[] b, int off, int len) throws IOException {
            this.file.write(b, off, len);
        }

        public void close() throws IOException {
            this.file.close();
        }

        public SevenZOutputFile getSevenZOutputFile() {
            return this.file;
        }
    }

    public SevenZArchiver() {
        super(ArchiveFormat.SEVEN_Z);
    }

    protected ArchiveOutputStream createArchiveOutputStream(File archive) throws IOException {
        return new SevenZOutputStream(new SevenZOutputFile(archive));
    }

    protected ArchiveInputStream createArchiveInputStream(File archive) throws IOException {
        return new SevenZInputStream(new SevenZFile(archive));
    }
}
