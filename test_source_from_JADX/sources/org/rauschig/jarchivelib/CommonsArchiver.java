package org.rauschig.jarchivelib;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveOutputStream;

class CommonsArchiver implements Archiver {
    private final ArchiveFormat archiveFormat;

    CommonsArchiver(ArchiveFormat archiveFormat) {
        this.archiveFormat = archiveFormat;
    }

    public ArchiveFormat getArchiveFormat() {
        return this.archiveFormat;
    }

    public File create(String archive, File destination, File... sources) throws IOException {
        IOUtils.requireDirectory(destination);
        File archiveFile = createNewArchiveFile(archive, getFilenameExtension(), destination);
        ArchiveOutputStream outputStream = null;
        try {
            outputStream = createArchiveOutputStream(archiveFile);
            writeToArchive(sources, outputStream);
            outputStream.flush();
            return archiveFile;
        } finally {
            IOUtils.closeQuietly(outputStream);
        }
    }

    public void extract(File archive, File destination) throws IOException {
        assertExtractSource(archive);
        IOUtils.requireDirectory(destination);
        ArchiveInputStream input = null;
        try {
            input = createArchiveInputStream(archive);
            while (true) {
                ArchiveEntry nextEntry = input.getNextEntry();
                ArchiveEntry entry = nextEntry;
                if (nextEntry == null) {
                    break;
                }
                File file = new File(destination, entry.getName());
                if (entry.isDirectory()) {
                    file.mkdirs();
                } else {
                    file.getParentFile().mkdirs();
                    IOUtils.copy((InputStream) input, file);
                }
                FileModeMapper.map(entry, file);
            }
        } finally {
            IOUtils.closeQuietly(input);
        }
    }

    public ArchiveStream stream(File archive) throws IOException {
        return new CommonsArchiveStream(createArchiveInputStream(archive));
    }

    public String getFilenameExtension() {
        return getArchiveFormat().getDefaultFileExtension();
    }

    protected ArchiveInputStream createArchiveInputStream(File archive) throws IOException {
        try {
            return CommonsStreamFactory.createArchiveInputStream(archive);
        } catch (ArchiveException e) {
            throw new IOException(e);
        }
    }

    protected ArchiveOutputStream createArchiveOutputStream(File archiveFile) throws IOException {
        try {
            return CommonsStreamFactory.createArchiveOutputStream(this, archiveFile);
        } catch (ArchiveException e) {
            throw new IOException(e);
        }
    }

    protected void assertExtractSource(File archive) throws FileNotFoundException, IllegalArgumentException {
        StringBuilder stringBuilder;
        if (archive.isDirectory()) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Can not extract ");
            stringBuilder.append(archive);
            stringBuilder.append(". Source is a directory.");
            throw new IllegalArgumentException(stringBuilder.toString());
        } else if (!archive.exists()) {
            throw new FileNotFoundException(archive.getPath());
        } else if (!archive.canRead()) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Can not extract ");
            stringBuilder.append(archive);
            stringBuilder.append(". Can not read from source.");
            throw new IllegalArgumentException(stringBuilder.toString());
        }
    }

    protected File createNewArchiveFile(String archive, String extension, File destination) throws IOException {
        if (!archive.endsWith(extension)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(archive);
            stringBuilder.append(extension);
            archive = stringBuilder.toString();
        }
        File file = new File(destination, archive);
        file.createNewFile();
        return file;
    }

    protected void writeToArchive(File[] sources, ArchiveOutputStream archive) throws IOException {
        File[] arr$ = sources;
        int len$ = arr$.length;
        int i$ = 0;
        while (i$ < len$) {
            File source = arr$[i$];
            if (!source.exists()) {
                throw new FileNotFoundException(source.getPath());
            } else if (source.canRead()) {
                if (source.isFile()) {
                    writeToArchive(source.getParentFile(), new File[]{source}, archive);
                } else {
                    writeToArchive(source, source.listFiles(), archive);
                }
                i$++;
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(source.getPath());
                stringBuilder.append(" (Permission denied)");
                throw new FileNotFoundException(stringBuilder.toString());
            }
        }
    }

    protected void writeToArchive(File parent, File[] sources, ArchiveOutputStream archive) throws IOException {
        for (File source : sources) {
            createArchiveEntry(source, IOUtils.relativePath(parent, source), archive);
            if (source.isDirectory()) {
                writeToArchive(parent, source.listFiles(), archive);
            }
        }
    }

    protected void createArchiveEntry(File file, String entryName, ArchiveOutputStream archive) throws IOException {
        ArchiveEntry entry = archive.createArchiveEntry(file, entryName);
        archive.putArchiveEntry(entry);
        if (!entry.isDirectory()) {
            FileInputStream input = null;
            try {
                input = new FileInputStream(file);
                IOUtils.copy((InputStream) input, (OutputStream) archive);
            } finally {
                IOUtils.closeQuietly(input);
            }
        }
        archive.closeArchiveEntry();
    }
}
