package org.rauschig.jarchivelib;

import org.apache.commons.compress.archivers.ArchiveStreamFactory;

public enum ArchiveFormat {
    AR(ArchiveStreamFactory.AR, ".ar"),
    CPIO(ArchiveStreamFactory.CPIO, ".cpio"),
    DUMP(ArchiveStreamFactory.DUMP, ".dump"),
    JAR(ArchiveStreamFactory.JAR, ".jar"),
    SEVEN_Z(ArchiveStreamFactory.SEVEN_Z, ".7z"),
    TAR(ArchiveStreamFactory.TAR, ".tar"),
    ZIP(ArchiveStreamFactory.ZIP, ".zip");
    
    private final String defaultFileExtension;
    private final String name;

    private ArchiveFormat(String name, String defaultFileExtension) {
        this.name = name;
        this.defaultFileExtension = defaultFileExtension;
    }

    public String getName() {
        return this.name;
    }

    public String getDefaultFileExtension() {
        return this.defaultFileExtension;
    }

    public static boolean isValidArchiveFormat(String archiveFormat) {
        for (ArchiveFormat format : values()) {
            if (archiveFormat.trim().equalsIgnoreCase(format.getName())) {
                return true;
            }
        }
        return false;
    }

    public static ArchiveFormat fromString(String archiveFormat) {
        for (ArchiveFormat format : values()) {
            if (archiveFormat.trim().equalsIgnoreCase(format.getName())) {
                return format;
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unknown archive format ");
        stringBuilder.append(archiveFormat);
        throw new IllegalArgumentException(stringBuilder.toString());
    }
}
