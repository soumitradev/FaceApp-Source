package org.rauschig.jarchivelib;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.compress.compressors.CompressorException;
import org.apache.commons.compress.compressors.CompressorInputStream;
import org.apache.commons.compress.compressors.CompressorOutputStream;

class CommonsCompressor implements Compressor {
    private final CompressionType compressionType;

    CommonsCompressor(CompressionType type) {
        this.compressionType = type;
    }

    public CompressionType getCompressionType() {
        return this.compressionType;
    }

    public void compress(File source, File destination) throws IllegalArgumentException, IOException {
        assertSource(source);
        assertDestination(destination);
        if (destination.isDirectory()) {
            destination = new File(destination, getCompressedFilename(source));
        }
        CompressorOutputStream compressed = null;
        BufferedInputStream input = null;
        try {
            input = new BufferedInputStream(new FileInputStream(source));
            compressed = CommonsStreamFactory.createCompressorOutputStream(this, destination);
            IOUtils.copy((InputStream) input, (OutputStream) compressed);
            IOUtils.closeQuietly(compressed);
            IOUtils.closeQuietly(input);
        } catch (CompressorException e) {
            throw new IOException(e);
        } catch (Throwable th) {
            IOUtils.closeQuietly(compressed);
            IOUtils.closeQuietly(input);
        }
    }

    public void decompress(File source, File destination) throws IOException {
        assertSource(source);
        assertDestination(destination);
        if (destination.isDirectory()) {
            destination = new File(destination, getDecompressedFilename(source));
        }
        CompressorInputStream compressed = null;
        FileOutputStream output = null;
        try {
            compressed = CommonsStreamFactory.createCompressorInputStream(getCompressionType(), source);
            output = new FileOutputStream(destination);
            IOUtils.copy((InputStream) compressed, (OutputStream) output);
            IOUtils.closeQuietly(compressed);
            IOUtils.closeQuietly(output);
        } catch (CompressorException e) {
            throw new IOException(e);
        } catch (Throwable th) {
            IOUtils.closeQuietly(compressed);
            IOUtils.closeQuietly(output);
        }
    }

    public String getFilenameExtension() {
        return getCompressionType().getDefaultFileExtension();
    }

    private String getCompressedFilename(File source) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(source.getName());
        stringBuilder.append(getFilenameExtension());
        return stringBuilder.toString();
    }

    private String getDecompressedFilename(File source) {
        FileType fileType = FileType.get(source);
        if (this.compressionType == fileType.getCompressionType()) {
            return source.getName().substring(0, source.getName().length() - fileType.getSuffix().length());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(source);
        stringBuilder.append(" is not of type ");
        stringBuilder.append(this.compressionType);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private void assertSource(File source) throws IllegalArgumentException, FileNotFoundException {
        if (source == null) {
            throw new IllegalArgumentException("Source is null");
        } else if (source.isDirectory()) {
            r1 = new StringBuilder();
            r1.append("Source ");
            r1.append(source);
            r1.append(" is a directory.");
            throw new IllegalArgumentException(r1.toString());
        } else if (!source.exists()) {
            throw new FileNotFoundException(source.getName());
        } else if (!source.canRead()) {
            r1 = new StringBuilder();
            r1.append("Can not read from source ");
            r1.append(source);
            throw new IllegalArgumentException(r1.toString());
        }
    }

    private void assertDestination(File destination) {
        if (destination == null) {
            throw new IllegalArgumentException("Destination is null");
        } else if (destination.isDirectory()) {
            if (!destination.canWrite()) {
                r1 = new StringBuilder();
                r1.append("Can not write to destination ");
                r1.append(destination);
                throw new IllegalArgumentException(r1.toString());
            }
        } else if (destination.exists() && !destination.canWrite()) {
            r1 = new StringBuilder();
            r1.append("Can not write to destination ");
            r1.append(destination);
            throw new IllegalArgumentException(r1.toString());
        }
    }
}
