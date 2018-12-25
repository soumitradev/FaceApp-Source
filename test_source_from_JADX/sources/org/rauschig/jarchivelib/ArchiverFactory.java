package org.rauschig.jarchivelib;

import java.io.File;

public final class ArchiverFactory {
    private ArchiverFactory() {
    }

    public static Archiver createArchiver(File archive) throws IllegalArgumentException {
        FileType fileType = FileType.get(archive);
        if (fileType != FileType.UNKNOWN) {
            return createArchiver(fileType);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unknown file extension ");
        stringBuilder.append(archive.getName());
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public static Archiver createArchiver(FileType fileType) {
        if (fileType == FileType.UNKNOWN) {
            throw new IllegalArgumentException("Unknown file type");
        } else if (fileType.isArchive() && fileType.isCompressed()) {
            return createArchiver(fileType.getArchiveFormat(), fileType.getCompressionType());
        } else {
            if (fileType.isArchive()) {
                return createArchiver(fileType.getArchiveFormat());
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unknown archive file extension ");
            stringBuilder.append(fileType);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
    }

    public static Archiver createArchiver(String archiveFormat, String compression) throws IllegalArgumentException {
        StringBuilder stringBuilder;
        if (!ArchiveFormat.isValidArchiveFormat(archiveFormat)) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Unknown archive format ");
            stringBuilder.append(archiveFormat);
            throw new IllegalArgumentException(stringBuilder.toString());
        } else if (CompressionType.isValidCompressionType(compression)) {
            return createArchiver(ArchiveFormat.fromString(archiveFormat), CompressionType.fromString(compression));
        } else {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Unknown compression type ");
            stringBuilder.append(compression);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
    }

    public static Archiver createArchiver(ArchiveFormat archiveFormat, CompressionType compression) {
        return new ArchiverCompressorDecorator(new CommonsArchiver(archiveFormat), new CommonsCompressor(compression));
    }

    public static Archiver createArchiver(String archiveFormat) throws IllegalArgumentException {
        if (ArchiveFormat.isValidArchiveFormat(archiveFormat)) {
            return createArchiver(ArchiveFormat.fromString(archiveFormat));
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unknown archive format ");
        stringBuilder.append(archiveFormat);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public static Archiver createArchiver(ArchiveFormat archiveFormat) {
        if (archiveFormat == ArchiveFormat.SEVEN_Z) {
            return new SevenZArchiver();
        }
        if (archiveFormat == ArchiveFormat.ZIP) {
            return new ZipFileArchiver();
        }
        return new CommonsArchiver(archiveFormat);
    }
}
