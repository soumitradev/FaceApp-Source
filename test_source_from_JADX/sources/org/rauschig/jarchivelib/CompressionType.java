package org.rauschig.jarchivelib;

import org.apache.commons.compress.compressors.CompressorStreamFactory;

public enum CompressionType {
    BZIP2(CompressorStreamFactory.BZIP2, ".bz2"),
    GZIP(CompressorStreamFactory.GZIP, ".gz"),
    XZ(CompressorStreamFactory.XZ, ".xz"),
    PACK200(CompressorStreamFactory.PACK200, ".pack");
    
    private final String defaultFileExtension;
    private final String name;

    private CompressionType(String name, String defaultFileExtension) {
        this.name = name;
        this.defaultFileExtension = defaultFileExtension;
    }

    public String getName() {
        return this.name;
    }

    public String getDefaultFileExtension() {
        return this.defaultFileExtension;
    }

    public static boolean isValidCompressionType(String compression) {
        for (CompressionType type : values()) {
            if (compression.equalsIgnoreCase(type.getName())) {
                return true;
            }
        }
        return false;
    }

    public static CompressionType fromString(String compression) {
        for (CompressionType type : values()) {
            if (compression.equalsIgnoreCase(type.getName())) {
                return type;
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unknown compression type ");
        stringBuilder.append(compression);
        throw new IllegalArgumentException(stringBuilder.toString());
    }
}
