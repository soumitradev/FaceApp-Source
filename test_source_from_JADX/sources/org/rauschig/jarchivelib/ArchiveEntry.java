package org.rauschig.jarchivelib;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public interface ArchiveEntry {
    public static final long UNKNOWN_SIZE = -1;

    File extract(File file) throws IOException, IllegalStateException, IllegalArgumentException;

    Date getLastModifiedDate();

    String getName();

    long getSize();

    boolean isDirectory();
}
