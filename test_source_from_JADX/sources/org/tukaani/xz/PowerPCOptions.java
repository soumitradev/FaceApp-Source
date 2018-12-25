package org.tukaani.xz;

import java.io.InputStream;
import org.tukaani.xz.simple.PowerPC;

public class PowerPCOptions extends BCJOptions {
    private static final int ALIGNMENT = 4;

    public PowerPCOptions() {
        super(4);
    }

    FilterEncoder getFilterEncoder() {
        return new BCJEncoder(this, 5);
    }

    public InputStream getInputStream(InputStream inputStream) {
        return new SimpleInputStream(inputStream, new PowerPC(false, this.startOffset));
    }

    public FinishableOutputStream getOutputStream(FinishableOutputStream finishableOutputStream) {
        return new SimpleOutputStream(finishableOutputStream, new PowerPC(true, this.startOffset));
    }
}
