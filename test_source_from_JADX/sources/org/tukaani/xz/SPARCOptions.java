package org.tukaani.xz;

import java.io.InputStream;
import org.tukaani.xz.simple.SPARC;

public class SPARCOptions extends BCJOptions {
    private static final int ALIGNMENT = 4;

    public SPARCOptions() {
        super(4);
    }

    FilterEncoder getFilterEncoder() {
        return new BCJEncoder(this, 9);
    }

    public InputStream getInputStream(InputStream inputStream) {
        return new SimpleInputStream(inputStream, new SPARC(false, this.startOffset));
    }

    public FinishableOutputStream getOutputStream(FinishableOutputStream finishableOutputStream) {
        return new SimpleOutputStream(finishableOutputStream, new SPARC(true, this.startOffset));
    }
}
