package org.tukaani.xz;

import java.io.InputStream;
import org.tukaani.xz.simple.IA64;

public class IA64Options extends BCJOptions {
    private static final int ALIGNMENT = 16;

    public IA64Options() {
        super(16);
    }

    FilterEncoder getFilterEncoder() {
        return new BCJEncoder(this, 6);
    }

    public InputStream getInputStream(InputStream inputStream) {
        return new SimpleInputStream(inputStream, new IA64(false, this.startOffset));
    }

    public FinishableOutputStream getOutputStream(FinishableOutputStream finishableOutputStream) {
        return new SimpleOutputStream(finishableOutputStream, new IA64(true, this.startOffset));
    }
}
