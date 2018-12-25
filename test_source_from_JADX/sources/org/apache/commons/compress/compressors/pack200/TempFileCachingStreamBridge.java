package org.apache.commons.compress.compressors.pack200;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

class TempFileCachingStreamBridge extends StreamBridge {
    /* renamed from: f */
    private final File f1777f = File.createTempFile("commons-compress", "packtemp");

    TempFileCachingStreamBridge() throws IOException {
        this.f1777f.deleteOnExit();
        this.out = new FileOutputStream(this.f1777f);
    }

    InputStream getInputView() throws IOException {
        this.out.close();
        return new FileInputStream(this.f1777f) {
            public void close() throws IOException {
                super.close();
                TempFileCachingStreamBridge.this.f1777f.delete();
            }
        };
    }
}
