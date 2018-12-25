package org.apache.commons.compress.archivers.sevenz;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.tukaani.xz.DeltaOptions;
import org.tukaani.xz.FinishableWrapperOutputStream;
import org.tukaani.xz.UnsupportedOptionsException;

class DeltaDecoder extends CoderBase {
    DeltaDecoder() {
        super(Number.class);
    }

    InputStream decode(InputStream in, long uncompressedLength, Coder coder, byte[] password) throws IOException {
        return new DeltaOptions(getOptionsFromCoder(coder)).getInputStream(in);
    }

    OutputStream encode(OutputStream out, Object options) throws IOException {
        try {
            return new DeltaOptions(CoderBase.numberOptionOrDefault(options, 1)).getOutputStream(new FinishableWrapperOutputStream(out));
        } catch (UnsupportedOptionsException ex) {
            throw new IOException(ex.getMessage());
        }
    }

    byte[] getOptionsAsProperties(Object options) {
        return new byte[]{(byte) (CoderBase.numberOptionOrDefault(options, 1) - 1)};
    }

    Object getOptionsFromCoder(Coder coder, InputStream in) {
        return Integer.valueOf(getOptionsFromCoder(coder));
    }

    private int getOptionsFromCoder(Coder coder) {
        if (coder.properties != null) {
            if (coder.properties.length != 0) {
                return (coder.properties[0] & 255) + 1;
            }
        }
        return 1;
    }
}
