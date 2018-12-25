package org.catrobat.catroid.utils;

import java.io.InputStream;

public interface DiskCacheSnapshotInterface {
    void close();

    InputStream getInputStream(int i);
}
