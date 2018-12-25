package org.apache.commons.compress.compressors.pack200;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

abstract class StreamBridge extends FilterOutputStream {
    private final Object INPUT_LOCK;
    private InputStream input;

    abstract InputStream getInputView() throws IOException;

    protected StreamBridge(OutputStream out) {
        super(out);
        this.INPUT_LOCK = new Object();
    }

    protected StreamBridge() {
        this(null);
    }

    InputStream getInput() throws IOException {
        synchronized (this.INPUT_LOCK) {
            if (this.input == null) {
                this.input = getInputView();
            }
        }
        return this.input;
    }

    void stop() throws IOException {
        close();
        synchronized (this.INPUT_LOCK) {
            if (this.input != null) {
                this.input.close();
                this.input = null;
            }
        }
    }
}
