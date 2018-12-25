package org.catrobat.catroid.utils;

import java.io.IOException;
import java.io.OutputStream;

public interface DiskCacheEntryEditorInterface {
    void abort() throws IOException;

    void commit() throws IOException;

    OutputStream newOutputStream(int i) throws IOException;
}
