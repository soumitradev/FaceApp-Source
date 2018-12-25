package org.rauschig.jarchivelib;

import java.io.File;
import java.io.IOException;
import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.compressors.CompressorException;

class ArchiverCompressorDecorator implements Archiver {
    private CommonsArchiver archiver;
    private CommonsCompressor compressor;

    ArchiverCompressorDecorator(CommonsArchiver archiver, CommonsCompressor compressor) {
        this.archiver = archiver;
        this.compressor = compressor;
    }

    public File create(String archive, File destination, File... sources) throws IOException {
        IOUtils.requireDirectory(destination);
        File temp = File.createTempFile(destination.getName(), this.archiver.getFilenameExtension(), destination);
        File destinationArchive = null;
        try {
            temp = this.archiver.create(temp.getName(), temp.getParentFile(), sources);
            destinationArchive = new File(destination, getArchiveFileName(archive));
            this.compressor.compress(temp, destinationArchive);
            return destinationArchive;
        } finally {
            temp.delete();
        }
    }

    public void extract(File archive, File destination) throws IOException {
        IOUtils.requireDirectory(destination);
        File temp = File.createTempFile(archive.getName(), this.archiver.getFilenameExtension(), destination);
        try {
            this.compressor.decompress(archive, temp);
            this.archiver.extract(temp, destination);
        } finally {
            temp.delete();
        }
    }

    public ArchiveStream stream(File archive) throws IOException {
        try {
            return new CommonsArchiveStream(CommonsStreamFactory.createArchiveInputStream(this.archiver, CommonsStreamFactory.createCompressorInputStream(archive)));
        } catch (ArchiveException e) {
            throw new IOException(e);
        } catch (CompressorException e2) {
            throw new IOException(e2);
        }
    }

    public String getFilenameExtension() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.archiver.getFilenameExtension());
        stringBuilder.append(this.compressor.getFilenameExtension());
        return stringBuilder.toString();
    }

    private String getArchiveFileName(String archive) {
        String fileExtension = getFilenameExtension();
        if (archive.endsWith(fileExtension)) {
            return archive;
        }
        if (archive.endsWith(this.archiver.getFilenameExtension())) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(archive);
            stringBuilder.append(this.compressor.getFilenameExtension());
            return stringBuilder.toString();
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append(archive);
        stringBuilder.append(fileExtension);
        return stringBuilder.toString();
    }
}
