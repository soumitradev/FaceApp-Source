package org.rauschig.jarchivelib;

import java.io.File;
import java.io.IOException;

public interface Archiver {
    File create(String str, File file, File... fileArr) throws IOException;

    void extract(File file, File file2) throws IOException;

    String getFilenameExtension();

    ArchiveStream stream(File file) throws IOException;
}
