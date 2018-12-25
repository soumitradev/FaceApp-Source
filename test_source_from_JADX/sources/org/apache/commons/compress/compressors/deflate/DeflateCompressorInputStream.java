package org.apache.commons.compress.compressors.deflate;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;
import org.apache.commons.compress.compressors.CompressorInputStream;

public class DeflateCompressorInputStream extends CompressorInputStream {
    private final InputStream in;

    public DeflateCompressorInputStream(InputStream inputStream) {
        this(inputStream, new DeflateParameters());
    }

    public DeflateCompressorInputStream(InputStream inputStream, DeflateParameters parameters) {
        this.in = new InflaterInputStream(inputStream, new Inflater(parameters.withZlibHeader() ^ 1));
    }

    public int read() throws IOException {
        int ret = this.in.read();
        count(ret == -1 ? 0 : 1);
        return ret;
    }

    public int read(byte[] buf, int off, int len) throws IOException {
        int ret = this.in.read(buf, off, len);
        count(ret);
        return ret;
    }

    public long skip(long n) throws IOException {
        return this.in.skip(n);
    }

    public int available() throws IOException {
        return this.in.available();
    }

    public void close() throws IOException {
        this.in.close();
    }
}
