package org.catrobat.catroid.utils;

import java.io.File;
import java.io.IOException;

public interface DiskCacheBackendInterface {
    void delete() throws IOException;

    DiskCacheEntryEditorInterface edit(String str) throws IOException;

    DiskCacheSnapshotInterface get(String str) throws IOException;

    File getDirectory();

    long maxSize();

    boolean remove(String str) throws IOException;
}
