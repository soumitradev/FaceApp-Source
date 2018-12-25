package org.tukaani.xz;

import java.io.InputStream;
import org.tukaani.xz.simple.ARM;

public class ARMOptions extends BCJOptions {
    private static final int ALIGNMENT = 4;

    public ARMOptions() {
        super(4);
    }

    FilterEncoder getFilterEncoder() {
        return new BCJEncoder(this, 7);
    }

    public InputStream getInputStream(InputStream inputStream) {
        return new SimpleInputStream(inputStream, new ARM(false, this.startOffset));
    }

    public FinishableOutputStream getOutputStream(FinishableOutputStream finishableOutputStream) {
        return new SimpleOutputStream(finishableOutputStream, new ARM(true, this.startOffset));
    }
}
