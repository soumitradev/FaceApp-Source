package org.tukaani.xz;

import java.io.InputStream;
import org.tukaani.xz.simple.ARMThumb;

public class ARMThumbOptions extends BCJOptions {
    private static final int ALIGNMENT = 2;

    public ARMThumbOptions() {
        super(2);
    }

    FilterEncoder getFilterEncoder() {
        return new BCJEncoder(this, 8);
    }

    public InputStream getInputStream(InputStream inputStream) {
        return new SimpleInputStream(inputStream, new ARMThumb(false, this.startOffset));
    }

    public FinishableOutputStream getOutputStream(FinishableOutputStream finishableOutputStream) {
        return new SimpleOutputStream(finishableOutputStream, new ARMThumb(true, this.startOffset));
    }
}
