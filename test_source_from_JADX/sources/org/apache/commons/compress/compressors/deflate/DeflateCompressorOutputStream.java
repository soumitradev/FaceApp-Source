package org.apache.commons.compress.compressors.deflate;

import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import org.apache.commons.compress.compressors.CompressorOutputStream;

public class DeflateCompressorOutputStream extends CompressorOutputStream {
    private final DeflaterOutputStream out;

    public DeflateCompressorOutputStream(OutputStream outputStream) throws IOException {
        this(outputStream, new DeflateParameters());
    }

    public DeflateCompressorOutputStream(OutputStream outputStream, DeflateParameters parameters) throws IOException {
        this.out = new DeflaterOutputStream(outputStream, new Deflater(parameters.getCompressionLevel(), parameters.withZlibHeader() ^ 1));
    }

    public void write(int b) throws IOException {
        this.out.write(b);
    }

    public void write(byte[] buf, int off, int len) throws IOException {
        this.out.write(buf, off, len);
    }

    public void flush() throws IOException {
        this.out.flush();
    }

    public void finish() throws IOException {
        this.out.finish();
    }

    public void close() throws IOException {
        this.out.close();
    }
}
