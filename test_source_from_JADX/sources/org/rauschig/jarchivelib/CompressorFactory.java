package org.rauschig.jarchivelib;

import java.io.File;

public final class CompressorFactory {
    private CompressorFactory() {
    }

    public static Compressor createCompressor(File file) throws IllegalArgumentException {
        FileType fileType = FileType.get(file);
        if (fileType != FileType.UNKNOWN) {
            return createCompressor(fileType);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unknown file extension ");
        stringBuilder.append(file.getName());
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public static Compressor createCompressor(FileType fileType) throws IllegalArgumentException {
        if (fileType == FileType.UNKNOWN) {
            throw new IllegalArgumentException("Unknown file type");
        } else if (fileType.isCompressed()) {
            return createCompressor(fileType.getCompressionType());
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unknown compressed file type ");
            stringBuilder.append(fileType);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
    }

    public static Compressor createCompressor(String compression) throws IllegalArgumentException {
        if (CompressionType.isValidCompressionType(compression)) {
            return createCompressor(CompressionType.fromString(compression));
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unkonwn compression type ");
        stringBuilder.append(compression);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public static Compressor createCompressor(CompressionType compression) {
        return new CommonsCompressor(compression);
    }
}
