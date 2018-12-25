package org.rauschig.jarchivelib;

import java.io.File;
import java.io.IOException;

public interface Compressor {
    void compress(File file, File file2) throws IllegalArgumentException, IOException;

    void decompress(File file, File file2) throws IllegalArgumentException, IOException;

    String getFilenameExtension();
}
