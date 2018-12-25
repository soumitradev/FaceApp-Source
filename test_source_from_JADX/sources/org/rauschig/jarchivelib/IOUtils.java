package org.rauschig.jarchivelib;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public final class IOUtils {
    private static final int DEFAULT_BUFFER_SIZE = 8024;

    private IOUtils() {
    }

    public static void copy(InputStream source, File destination) throws IOException {
        OutputStream output = null;
        try {
            output = new FileOutputStream(destination);
            copy(source, output);
        } finally {
            closeQuietly(output);
        }
    }

    public static long copy(InputStream input, OutputStream output) throws IOException {
        return copy(input, output, DEFAULT_BUFFER_SIZE);
    }

    public static long copy(InputStream input, OutputStream output, int buffersize) throws IOException {
        byte[] buffer = new byte[buffersize];
        long count = 0;
        while (true) {
            int read = input.read(buffer);
            int n = read;
            if (-1 == read) {
                return count;
            }
            output.write(buffer, 0, n);
            count += (long) n;
        }
    }

    public static String relativePath(File root, File node) throws IOException {
        return node.getCanonicalPath().substring(root.getCanonicalPath().length() + 1);
    }

    public static void requireDirectory(File destination) throws IOException, IllegalArgumentException {
        if (destination.isFile()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(destination);
            stringBuilder.append(" exists and is a file, directory or path expected.");
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        if (!destination.exists()) {
            destination.mkdirs();
        }
        if (!destination.canWrite()) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Can not write to destination ");
            stringBuilder.append(destination);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
    }

    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
            }
        }
    }
}
