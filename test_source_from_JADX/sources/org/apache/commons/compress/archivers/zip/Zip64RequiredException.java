package org.apache.commons.compress.archivers.zip;

import java.util.zip.ZipException;

public class Zip64RequiredException extends ZipException {
    static final String ARCHIVE_TOO_BIG_MESSAGE = "archive's size exceeds the limit of 4GByte.";
    static final String TOO_MANY_ENTRIES_MESSAGE = "archive contains more than 65535 entries.";
    private static final long serialVersionUID = 20110809;

    static String getEntryTooBigMessage(ZipArchiveEntry ze) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(ze.getName());
        stringBuilder.append("'s size exceeds the limit of 4GByte.");
        return stringBuilder.toString();
    }

    public Zip64RequiredException(String reason) {
        super(reason);
    }
}
