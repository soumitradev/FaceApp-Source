package org.rauschig.jarchivelib;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.compressors.CompressorException;
import org.apache.commons.compress.compressors.CompressorInputStream;
import org.apache.commons.compress.compressors.CompressorOutputStream;
import org.apache.commons.compress.compressors.CompressorStreamFactory;

final class CommonsStreamFactory {
    private static ArchiveStreamFactory archiveStreamFactory = new ArchiveStreamFactory();
    private static CompressorStreamFactory compressorStreamFactory = new CompressorStreamFactory();

    private CommonsStreamFactory() {
    }

    static ArchiveInputStream createArchiveInputStream(String archiverName, InputStream in) throws ArchiveException {
        return archiveStreamFactory.createArchiveInputStream(archiverName, in);
    }

    static ArchiveInputStream createArchiveInputStream(ArchiveFormat archiveFormat, InputStream in) throws ArchiveException {
        return createArchiveInputStream(archiveFormat.getName(), in);
    }

    static ArchiveInputStream createArchiveInputStream(CommonsArchiver archiver, InputStream in) throws ArchiveException {
        return createArchiveInputStream(archiver.getArchiveFormat(), in);
    }

    static ArchiveInputStream createArchiveInputStream(InputStream in) throws ArchiveException {
        return archiveStreamFactory.createArchiveInputStream(in);
    }

    static ArchiveInputStream createArchiveInputStream(File archive) throws IOException, ArchiveException {
        return createArchiveInputStream(new BufferedInputStream(new FileInputStream(archive)));
    }

    static ArchiveOutputStream createArchiveOutputStream(String archiverName, OutputStream out) throws ArchiveException {
        return archiveStreamFactory.createArchiveOutputStream(archiverName, out);
    }

    static ArchiveOutputStream createArchiveOutputStream(ArchiveFormat format, File archive) throws IOException, ArchiveException {
        return createArchiveOutputStream(format.getName(), new FileOutputStream(archive));
    }

    static ArchiveOutputStream createArchiveOutputStream(CommonsArchiver archiver, File archive) throws IOException, ArchiveException {
        return createArchiveOutputStream(archiver.getArchiveFormat(), archive);
    }

    static CompressorInputStream createCompressorInputStream(File source) throws IOException, CompressorException {
        return createCompressorInputStream(new BufferedInputStream(new FileInputStream(source)));
    }

    static CompressorInputStream createCompressorInputStream(CompressionType type, File source) throws IOException, CompressorException {
        return createCompressorInputStream(type.getName(), new BufferedInputStream(new FileInputStream(source)));
    }

    private static CompressorInputStream createCompressorInputStream(String compressionType, InputStream in) throws CompressorException {
        return compressorStreamFactory.createCompressorInputStream(compressionType, in);
    }

    static CompressorInputStream createCompressorInputStream(InputStream in) throws CompressorException {
        return compressorStreamFactory.createCompressorInputStream(in);
    }

    static CompressorOutputStream createCompressorOutputStream(CompressionType compressionType, File destination) throws IOException, CompressorException {
        return createCompressorOutputStream(compressionType.getName(), new FileOutputStream(destination));
    }

    static CompressorOutputStream createCompressorOutputStream(CommonsCompressor compressor, File destination) throws IOException, CompressorException {
        return createCompressorOutputStream(compressor.getCompressionType(), destination);
    }

    static CompressorOutputStream createCompressorOutputStream(String compressorName, OutputStream out) throws CompressorException {
        return compressorStreamFactory.createCompressorOutputStream(compressorName, out);
    }
}
