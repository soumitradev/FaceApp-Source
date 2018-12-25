package org.apache.commons.compress.compressors;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream;
import org.apache.commons.compress.compressors.deflate.DeflateCompressorInputStream;
import org.apache.commons.compress.compressors.deflate.DeflateCompressorOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;
import org.apache.commons.compress.compressors.lzma.LZMACompressorInputStream;
import org.apache.commons.compress.compressors.p000z.ZCompressorInputStream;
import org.apache.commons.compress.compressors.pack200.Pack200CompressorInputStream;
import org.apache.commons.compress.compressors.pack200.Pack200CompressorOutputStream;
import org.apache.commons.compress.compressors.snappy.FramedSnappyCompressorInputStream;
import org.apache.commons.compress.compressors.snappy.SnappyCompressorInputStream;
import org.apache.commons.compress.compressors.xz.XZCompressorInputStream;
import org.apache.commons.compress.compressors.xz.XZCompressorOutputStream;
import org.apache.commons.compress.compressors.xz.XZUtils;
import org.apache.commons.compress.utils.IOUtils;

public class CompressorStreamFactory {
    public static final String BZIP2 = "bzip2";
    public static final String DEFLATE = "deflate";
    public static final String GZIP = "gz";
    public static final String LZMA = "lzma";
    public static final String PACK200 = "pack200";
    public static final String SNAPPY_FRAMED = "snappy-framed";
    public static final String SNAPPY_RAW = "snappy-raw";
    public static final String XZ = "xz";
    /* renamed from: Z */
    public static final String f1737Z = "z";
    private boolean decompressConcatenated = false;

    public void setDecompressConcatenated(boolean decompressConcatenated) {
        this.decompressConcatenated = decompressConcatenated;
    }

    public CompressorInputStream createCompressorInputStream(InputStream in) throws CompressorException {
        if (in == null) {
            throw new IllegalArgumentException("Stream must not be null.");
        } else if (in.markSupported()) {
            byte[] signature = new byte[12];
            in.mark(signature.length);
            try {
                int signatureLength = IOUtils.readFully(in, signature);
                in.reset();
                if (BZip2CompressorInputStream.matches(signature, signatureLength)) {
                    return new BZip2CompressorInputStream(in, this.decompressConcatenated);
                }
                if (GzipCompressorInputStream.matches(signature, signatureLength)) {
                    return new GzipCompressorInputStream(in, this.decompressConcatenated);
                }
                if (Pack200CompressorInputStream.matches(signature, signatureLength)) {
                    return new Pack200CompressorInputStream(in);
                }
                if (FramedSnappyCompressorInputStream.matches(signature, signatureLength)) {
                    return new FramedSnappyCompressorInputStream(in);
                }
                if (ZCompressorInputStream.matches(signature, signatureLength)) {
                    return new ZCompressorInputStream(in);
                }
                if (XZUtils.matches(signature, signatureLength) && XZUtils.isXZCompressionAvailable()) {
                    return new XZCompressorInputStream(in, this.decompressConcatenated);
                }
                throw new CompressorException("No Compressor found for the stream signature.");
            } catch (IOException e) {
                throw new CompressorException("Failed to detect Compressor from InputStream.", e);
            }
        } else {
            throw new IllegalArgumentException("Mark is not supported.");
        }
    }

    public CompressorInputStream createCompressorInputStream(String name, InputStream in) throws CompressorException {
        if (name != null) {
            if (in != null) {
                try {
                    if (GZIP.equalsIgnoreCase(name)) {
                        return new GzipCompressorInputStream(in, this.decompressConcatenated);
                    }
                    if (BZIP2.equalsIgnoreCase(name)) {
                        return new BZip2CompressorInputStream(in, this.decompressConcatenated);
                    }
                    if (XZ.equalsIgnoreCase(name)) {
                        return new XZCompressorInputStream(in, this.decompressConcatenated);
                    }
                    if (LZMA.equalsIgnoreCase(name)) {
                        return new LZMACompressorInputStream(in);
                    }
                    if (PACK200.equalsIgnoreCase(name)) {
                        return new Pack200CompressorInputStream(in);
                    }
                    if (SNAPPY_RAW.equalsIgnoreCase(name)) {
                        return new SnappyCompressorInputStream(in);
                    }
                    if (SNAPPY_FRAMED.equalsIgnoreCase(name)) {
                        return new FramedSnappyCompressorInputStream(in);
                    }
                    if (f1737Z.equalsIgnoreCase(name)) {
                        return new ZCompressorInputStream(in);
                    }
                    if (DEFLATE.equalsIgnoreCase(name)) {
                        return new DeflateCompressorInputStream(in);
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Compressor: ");
                    stringBuilder.append(name);
                    stringBuilder.append(" not found.");
                    throw new CompressorException(stringBuilder.toString());
                } catch (IOException e) {
                    throw new CompressorException("Could not create CompressorInputStream.", e);
                }
            }
        }
        throw new IllegalArgumentException("Compressor name and stream must not be null.");
    }

    public CompressorOutputStream createCompressorOutputStream(String name, OutputStream out) throws CompressorException {
        if (name != null) {
            if (out != null) {
                try {
                    if (GZIP.equalsIgnoreCase(name)) {
                        return new GzipCompressorOutputStream(out);
                    }
                    if (BZIP2.equalsIgnoreCase(name)) {
                        return new BZip2CompressorOutputStream(out);
                    }
                    if (XZ.equalsIgnoreCase(name)) {
                        return new XZCompressorOutputStream(out);
                    }
                    if (PACK200.equalsIgnoreCase(name)) {
                        return new Pack200CompressorOutputStream(out);
                    }
                    if (DEFLATE.equalsIgnoreCase(name)) {
                        return new DeflateCompressorOutputStream(out);
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Compressor: ");
                    stringBuilder.append(name);
                    stringBuilder.append(" not found.");
                    throw new CompressorException(stringBuilder.toString());
                } catch (IOException e) {
                    throw new CompressorException("Could not create CompressorOutputStream", e);
                }
            }
        }
        throw new IllegalArgumentException("Compressor name and stream must not be null.");
    }
}
