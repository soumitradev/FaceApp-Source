package com.badlogic.gdx.files;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.StreamUtils;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

public class FileHandle {
    protected File file;
    protected FileType type;

    protected FileHandle() {
    }

    public FileHandle(String fileName) {
        this.file = new File(fileName);
        this.type = FileType.Absolute;
    }

    public FileHandle(File file) {
        this.file = file;
        this.type = FileType.Absolute;
    }

    protected FileHandle(String fileName, FileType type) {
        this.type = type;
        this.file = new File(fileName);
    }

    protected FileHandle(File file, FileType type) {
        this.file = file;
        this.type = type;
    }

    public String path() {
        return this.file.getPath().replace('\\', '/');
    }

    public String name() {
        return this.file.getName();
    }

    public String extension() {
        String name = this.file.getName();
        int dotIndex = name.lastIndexOf(46);
        if (dotIndex == -1) {
            return "";
        }
        return name.substring(dotIndex + 1);
    }

    public String nameWithoutExtension() {
        String name = this.file.getName();
        int dotIndex = name.lastIndexOf(46);
        if (dotIndex == -1) {
            return name;
        }
        return name.substring(0, dotIndex);
    }

    public String pathWithoutExtension() {
        String path = this.file.getPath().replace('\\', '/');
        int dotIndex = path.lastIndexOf(46);
        if (dotIndex == -1) {
            return path;
        }
        return path.substring(0, dotIndex);
    }

    public FileType type() {
        return this.type;
    }

    public File file() {
        if (this.type == FileType.External) {
            return new File(Gdx.files.getExternalStoragePath(), this.file.getPath());
        }
        return this.file;
    }

    public InputStream read() {
        StringBuilder stringBuilder;
        if (this.type != FileType.Classpath && (this.type != FileType.Internal || file().exists())) {
            if (this.type != FileType.Local || file().exists()) {
                try {
                    return new FileInputStream(file());
                } catch (Exception ex) {
                    if (file().isDirectory()) {
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("Cannot open a stream to a directory: ");
                        stringBuilder.append(this.file);
                        stringBuilder.append(" (");
                        stringBuilder.append(this.type);
                        stringBuilder.append(")");
                        throw new GdxRuntimeException(stringBuilder.toString(), ex);
                    }
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("Error reading file: ");
                    stringBuilder.append(this.file);
                    stringBuilder.append(" (");
                    stringBuilder.append(this.type);
                    stringBuilder.append(")");
                    throw new GdxRuntimeException(stringBuilder.toString(), ex);
                }
            }
        }
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("/");
        stringBuilder2.append(this.file.getPath().replace('\\', '/'));
        InputStream input = FileHandle.class.getResourceAsStream(stringBuilder2.toString());
        if (input != null) {
            return input;
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append("File not found: ");
        stringBuilder.append(this.file);
        stringBuilder.append(" (");
        stringBuilder.append(this.type);
        stringBuilder.append(")");
        throw new GdxRuntimeException(stringBuilder.toString());
    }

    public BufferedInputStream read(int bufferSize) {
        return new BufferedInputStream(read(), bufferSize);
    }

    public Reader reader() {
        return new InputStreamReader(read());
    }

    public Reader reader(String charset) {
        InputStream stream = read();
        try {
            return new InputStreamReader(stream, charset);
        } catch (UnsupportedEncodingException ex) {
            StreamUtils.closeQuietly(stream);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Error reading file: ");
            stringBuilder.append(this);
            throw new GdxRuntimeException(stringBuilder.toString(), ex);
        }
    }

    public BufferedReader reader(int bufferSize) {
        return new BufferedReader(new InputStreamReader(read()), bufferSize);
    }

    public BufferedReader reader(int bufferSize, String charset) {
        try {
            return new BufferedReader(new InputStreamReader(read(), charset), bufferSize);
        } catch (UnsupportedEncodingException ex) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Error reading file: ");
            stringBuilder.append(this);
            throw new GdxRuntimeException(stringBuilder.toString(), ex);
        }
    }

    public String readString() {
        return readString(null);
    }

    public String readString(String charset) {
        StringBuilder output = new StringBuilder(estimateLength());
        InputStreamReader reader = null;
        if (charset == null) {
            try {
                reader = new InputStreamReader(read());
            } catch (IOException ex) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Error reading layout file: ");
                stringBuilder.append(this);
                throw new GdxRuntimeException(stringBuilder.toString(), ex);
            } catch (Throwable th) {
                StreamUtils.closeQuietly(reader);
            }
        } else {
            reader = new InputStreamReader(read(), charset);
        }
        IOException ex2 = new char[256];
        while (true) {
            int length = reader.read(ex2);
            if (length == -1) {
                StreamUtils.closeQuietly(reader);
                return output.toString();
            }
            output.append(ex2, 0, length);
        }
    }

    public byte[] readBytes() {
        InputStream input = read();
        try {
            byte[] copyStreamToByteArray = StreamUtils.copyStreamToByteArray(input, estimateLength());
            StreamUtils.closeQuietly(input);
            return copyStreamToByteArray;
        } catch (IOException ex) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Error reading file: ");
            stringBuilder.append(this);
            throw new GdxRuntimeException(stringBuilder.toString(), ex);
        } catch (Throwable th) {
            StreamUtils.closeQuietly(input);
        }
    }

    private int estimateLength() {
        int length = (int) length();
        return length != 0 ? length : 512;
    }

    public int readBytes(byte[] bytes, int offset, int size) {
        InputStream input = read();
        int position = 0;
        while (true) {
            try {
                int count = input.read(bytes, offset + position, size - position);
                if (count <= 0) {
                    StreamUtils.closeQuietly(input);
                    return position - offset;
                }
                position += count;
            } catch (IOException ex) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Error reading file: ");
                stringBuilder.append(this);
                throw new GdxRuntimeException(stringBuilder.toString(), ex);
            } catch (Throwable th) {
                StreamUtils.closeQuietly(input);
            }
        }
    }

    public OutputStream write(boolean append) {
        StringBuilder stringBuilder;
        if (this.type == FileType.Classpath) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Cannot write to a classpath file: ");
            stringBuilder.append(this.file);
            throw new GdxRuntimeException(stringBuilder.toString());
        } else if (this.type == FileType.Internal) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Cannot write to an internal file: ");
            stringBuilder.append(this.file);
            throw new GdxRuntimeException(stringBuilder.toString());
        } else {
            parent().mkdirs();
            try {
                return new FileOutputStream(file(), append);
            } catch (Exception ex) {
                StringBuilder stringBuilder2;
                if (file().isDirectory()) {
                    stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("Cannot open a stream to a directory: ");
                    stringBuilder2.append(this.file);
                    stringBuilder2.append(" (");
                    stringBuilder2.append(this.type);
                    stringBuilder2.append(")");
                    throw new GdxRuntimeException(stringBuilder2.toString(), ex);
                }
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append("Error writing file: ");
                stringBuilder2.append(this.file);
                stringBuilder2.append(" (");
                stringBuilder2.append(this.type);
                stringBuilder2.append(")");
                throw new GdxRuntimeException(stringBuilder2.toString(), ex);
            }
        }
    }

    public OutputStream write(boolean append, int bufferSize) {
        return new BufferedOutputStream(write(append), bufferSize);
    }

    public void write(InputStream input, boolean append) {
        OutputStream output = null;
        try {
            output = write(append);
            StreamUtils.copyStream(input, output);
            StreamUtils.closeQuietly(input);
            StreamUtils.closeQuietly(output);
        } catch (Exception ex) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Error stream writing to file: ");
            stringBuilder.append(this.file);
            stringBuilder.append(" (");
            stringBuilder.append(this.type);
            stringBuilder.append(")");
            throw new GdxRuntimeException(stringBuilder.toString(), ex);
        } catch (Throwable th) {
            StreamUtils.closeQuietly(input);
            StreamUtils.closeQuietly(output);
        }
    }

    public Writer writer(boolean append) {
        return writer(append, null);
    }

    public Writer writer(boolean append, String charset) {
        StringBuilder stringBuilder;
        if (this.type == FileType.Classpath) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Cannot write to a classpath file: ");
            stringBuilder.append(this.file);
            throw new GdxRuntimeException(stringBuilder.toString());
        } else if (this.type == FileType.Internal) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Cannot write to an internal file: ");
            stringBuilder.append(this.file);
            throw new GdxRuntimeException(stringBuilder.toString());
        } else {
            parent().mkdirs();
            try {
                FileOutputStream output = new FileOutputStream(file(), append);
                if (charset == null) {
                    return new OutputStreamWriter(output);
                }
                return new OutputStreamWriter(output, charset);
            } catch (IOException ex) {
                StringBuilder stringBuilder2;
                if (file().isDirectory()) {
                    stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("Cannot open a stream to a directory: ");
                    stringBuilder2.append(this.file);
                    stringBuilder2.append(" (");
                    stringBuilder2.append(this.type);
                    stringBuilder2.append(")");
                    throw new GdxRuntimeException(stringBuilder2.toString(), ex);
                }
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append("Error writing file: ");
                stringBuilder2.append(this.file);
                stringBuilder2.append(" (");
                stringBuilder2.append(this.type);
                stringBuilder2.append(")");
                throw new GdxRuntimeException(stringBuilder2.toString(), ex);
            }
        }
    }

    public void writeString(String string, boolean append) {
        writeString(string, append, null);
    }

    public void writeString(String string, boolean append, String charset) {
        Writer writer = null;
        try {
            writer = writer(append, charset);
            writer.write(string);
            StreamUtils.closeQuietly(writer);
        } catch (Exception ex) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Error writing file: ");
            stringBuilder.append(this.file);
            stringBuilder.append(" (");
            stringBuilder.append(this.type);
            stringBuilder.append(")");
            throw new GdxRuntimeException(stringBuilder.toString(), ex);
        } catch (Throwable th) {
            StreamUtils.closeQuietly(writer);
        }
    }

    public void writeBytes(byte[] bytes, boolean append) {
        OutputStream output = write(append);
        try {
            output.write(bytes);
            StreamUtils.closeQuietly(output);
        } catch (IOException ex) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Error writing file: ");
            stringBuilder.append(this.file);
            stringBuilder.append(" (");
            stringBuilder.append(this.type);
            stringBuilder.append(")");
            throw new GdxRuntimeException(stringBuilder.toString(), ex);
        } catch (Throwable th) {
            StreamUtils.closeQuietly(output);
        }
    }

    public void writeBytes(byte[] bytes, int offset, int length, boolean append) {
        OutputStream output = write(append);
        try {
            output.write(bytes, offset, length);
            StreamUtils.closeQuietly(output);
        } catch (IOException ex) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Error writing file: ");
            stringBuilder.append(this.file);
            stringBuilder.append(" (");
            stringBuilder.append(this.type);
            stringBuilder.append(")");
            throw new GdxRuntimeException(stringBuilder.toString(), ex);
        } catch (Throwable th) {
            StreamUtils.closeQuietly(output);
        }
    }

    public FileHandle[] list() {
        if (this.type == FileType.Classpath) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Cannot list a classpath directory: ");
            stringBuilder.append(this.file);
            throw new GdxRuntimeException(stringBuilder.toString());
        }
        String[] relativePaths = file().list();
        if (relativePaths == null) {
            return new FileHandle[0];
        }
        FileHandle[] handles = new FileHandle[relativePaths.length];
        int n = relativePaths.length;
        for (int i = 0; i < n; i++) {
            handles[i] = child(relativePaths[i]);
        }
        return handles;
    }

    public FileHandle[] list(FileFilter filter) {
        if (this.type == FileType.Classpath) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Cannot list a classpath directory: ");
            stringBuilder.append(this.file);
            throw new GdxRuntimeException(stringBuilder.toString());
        }
        String[] relativePaths = file().list();
        if (relativePaths == null) {
            return new FileHandle[0];
        }
        FileHandle[] handles = new FileHandle[relativePaths.length];
        int count = 0;
        for (String path : relativePaths) {
            FileHandle child = child(path);
            if (filter.accept(child.file())) {
                handles[count] = child;
                count++;
            }
        }
        if (count < relativePaths.length) {
            FileHandle[] newHandles = new FileHandle[count];
            System.arraycopy(handles, 0, newHandles, 0, count);
            handles = newHandles;
        }
        return handles;
    }

    public FileHandle[] list(FilenameFilter filter) {
        if (this.type == FileType.Classpath) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Cannot list a classpath directory: ");
            stringBuilder.append(this.file);
            throw new GdxRuntimeException(stringBuilder.toString());
        }
        File file = file();
        String[] relativePaths = file.list();
        if (relativePaths == null) {
            return new FileHandle[0];
        }
        FileHandle[] handles = new FileHandle[relativePaths.length];
        int count = 0;
        for (String path : relativePaths) {
            if (filter.accept(file, path)) {
                handles[count] = child(path);
                count++;
            }
        }
        if (count < relativePaths.length) {
            FileHandle[] newHandles = new FileHandle[count];
            System.arraycopy(handles, 0, newHandles, 0, count);
            handles = newHandles;
        }
        return handles;
    }

    public FileHandle[] list(String suffix) {
        if (this.type == FileType.Classpath) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Cannot list a classpath directory: ");
            stringBuilder.append(this.file);
            throw new GdxRuntimeException(stringBuilder.toString());
        }
        String[] relativePaths = file().list();
        if (relativePaths == null) {
            return new FileHandle[0];
        }
        FileHandle[] handles = new FileHandle[relativePaths.length];
        int count = 0;
        for (String path : relativePaths) {
            if (path.endsWith(suffix)) {
                handles[count] = child(path);
                count++;
            }
        }
        if (count < relativePaths.length) {
            FileHandle[] newHandles = new FileHandle[count];
            System.arraycopy(handles, 0, newHandles, 0, count);
            handles = newHandles;
        }
        return handles;
    }

    public boolean isDirectory() {
        if (this.type == FileType.Classpath) {
            return false;
        }
        return file().isDirectory();
    }

    public FileHandle child(String name) {
        if (this.file.getPath().length() == 0) {
            return new FileHandle(new File(name), this.type);
        }
        return new FileHandle(new File(this.file, name), this.type);
    }

    public FileHandle sibling(String name) {
        if (this.file.getPath().length() != 0) {
            return new FileHandle(new File(this.file.getParent(), name), this.type);
        }
        throw new GdxRuntimeException("Cannot get the sibling of the root.");
    }

    public FileHandle parent() {
        File parent = this.file.getParentFile();
        if (parent == null) {
            if (this.type == FileType.Absolute) {
                parent = new File("/");
            } else {
                parent = new File("");
            }
        }
        return new FileHandle(parent, this.type);
    }

    public void mkdirs() {
        StringBuilder stringBuilder;
        if (this.type == FileType.Classpath) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Cannot mkdirs with a classpath file: ");
            stringBuilder.append(this.file);
            throw new GdxRuntimeException(stringBuilder.toString());
        } else if (this.type == FileType.Internal) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Cannot mkdirs with an internal file: ");
            stringBuilder.append(this.file);
            throw new GdxRuntimeException(stringBuilder.toString());
        } else {
            file().mkdirs();
        }
    }

    public boolean exists() {
        boolean z = true;
        switch (this.type) {
            case Internal:
                if (file().exists()) {
                    return true;
                }
                break;
            case Classpath:
                break;
            default:
                return file().exists();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("/");
        stringBuilder.append(this.file.getPath().replace('\\', '/'));
        if (FileHandle.class.getResource(stringBuilder.toString()) == null) {
            z = false;
        }
        return z;
    }

    public boolean delete() {
        StringBuilder stringBuilder;
        if (this.type == FileType.Classpath) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Cannot delete a classpath file: ");
            stringBuilder.append(this.file);
            throw new GdxRuntimeException(stringBuilder.toString());
        } else if (this.type != FileType.Internal) {
            return file().delete();
        } else {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Cannot delete an internal file: ");
            stringBuilder.append(this.file);
            throw new GdxRuntimeException(stringBuilder.toString());
        }
    }

    public boolean deleteDirectory() {
        StringBuilder stringBuilder;
        if (this.type == FileType.Classpath) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Cannot delete a classpath file: ");
            stringBuilder.append(this.file);
            throw new GdxRuntimeException(stringBuilder.toString());
        } else if (this.type != FileType.Internal) {
            return deleteDirectory(file());
        } else {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Cannot delete an internal file: ");
            stringBuilder.append(this.file);
            throw new GdxRuntimeException(stringBuilder.toString());
        }
    }

    public void emptyDirectory() {
        emptyDirectory(false);
    }

    public void emptyDirectory(boolean preserveTree) {
        StringBuilder stringBuilder;
        if (this.type == FileType.Classpath) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Cannot delete a classpath file: ");
            stringBuilder.append(this.file);
            throw new GdxRuntimeException(stringBuilder.toString());
        } else if (this.type == FileType.Internal) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Cannot delete an internal file: ");
            stringBuilder.append(this.file);
            throw new GdxRuntimeException(stringBuilder.toString());
        } else {
            emptyDirectory(file(), preserveTree);
        }
    }

    public void copyTo(FileHandle dest) {
        boolean sourceDir = isDirectory();
        if (sourceDir) {
            StringBuilder stringBuilder;
            if (!dest.exists()) {
                dest.mkdirs();
                if (!dest.isDirectory()) {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("Destination directory cannot be created: ");
                    stringBuilder.append(dest);
                    throw new GdxRuntimeException(stringBuilder.toString());
                }
            } else if (!dest.isDirectory()) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("Destination exists but is not a directory: ");
                stringBuilder.append(dest);
                throw new GdxRuntimeException(stringBuilder.toString());
            }
            if (!sourceDir) {
                dest = dest.child(name());
            }
            copyDirectory(this, dest);
            return;
        }
        if (dest.isDirectory()) {
            dest = dest.child(name());
        }
        copyFile(this, dest);
    }

    public void moveTo(FileHandle dest) {
        StringBuilder stringBuilder;
        if (this.type == FileType.Classpath) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Cannot move a classpath file: ");
            stringBuilder.append(this.file);
            throw new GdxRuntimeException(stringBuilder.toString());
        } else if (this.type == FileType.Internal) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Cannot move an internal file: ");
            stringBuilder.append(this.file);
            throw new GdxRuntimeException(stringBuilder.toString());
        } else {
            copyTo(dest);
            delete();
            if (exists() && isDirectory()) {
                deleteDirectory();
            }
        }
    }

    public long length() {
        if (this.type != FileType.Classpath) {
            if (this.type != FileType.Internal || this.file.exists()) {
                return file().length();
            }
        }
        InputStream input = read();
        long available;
        try {
            available = (long) input.available();
            return available;
        } catch (Exception e) {
            available = e;
            return 0;
        } finally {
            StreamUtils.closeQuietly(input);
        }
    }

    public long lastModified() {
        return file().lastModified();
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (!(obj instanceof FileHandle)) {
            return false;
        }
        FileHandle other = (FileHandle) obj;
        if (this.type == other.type && path().equals(other.path())) {
            z = true;
        }
        return z;
    }

    public int hashCode() {
        return (((1 * 37) + this.type.hashCode()) * 67) + path().hashCode();
    }

    public String toString() {
        return this.file.getPath().replace('\\', '/');
    }

    public static FileHandle tempFile(String prefix) {
        try {
            return new FileHandle(File.createTempFile(prefix, null));
        } catch (IOException ex) {
            throw new GdxRuntimeException("Unable to create temp file.", ex);
        }
    }

    public static FileHandle tempDirectory(String prefix) {
        try {
            File file = File.createTempFile(prefix, null);
            StringBuilder stringBuilder;
            if (!file.delete()) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("Unable to delete temp file: ");
                stringBuilder.append(file);
                throw new IOException(stringBuilder.toString());
            } else if (file.mkdir()) {
                return new FileHandle(file);
            } else {
                stringBuilder = new StringBuilder();
                stringBuilder.append("Unable to create temp directory: ");
                stringBuilder.append(file);
                throw new IOException(stringBuilder.toString());
            }
        } catch (IOException ex) {
            throw new GdxRuntimeException("Unable to create temp file.", ex);
        }
    }

    private static void emptyDirectory(File file, boolean preserveTree) {
        if (file.exists()) {
            File[] files = file.listFiles();
            if (files != null) {
                int n = files.length;
                for (int i = 0; i < n; i++) {
                    if (!files[i].isDirectory()) {
                        files[i].delete();
                    } else if (preserveTree) {
                        emptyDirectory(files[i], true);
                    } else {
                        deleteDirectory(files[i]);
                    }
                }
            }
        }
    }

    private static boolean deleteDirectory(File file) {
        emptyDirectory(file, false);
        return file.delete();
    }

    private static void copyFile(FileHandle source, FileHandle dest) {
        try {
            dest.write(source.read(), false);
        } catch (Exception ex) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Error copying source file: ");
            stringBuilder.append(source.file);
            stringBuilder.append(" (");
            stringBuilder.append(source.type);
            stringBuilder.append(")\n");
            stringBuilder.append("To destination: ");
            stringBuilder.append(dest.file);
            stringBuilder.append(" (");
            stringBuilder.append(dest.type);
            stringBuilder.append(")");
            throw new GdxRuntimeException(stringBuilder.toString(), ex);
        }
    }

    private static void copyDirectory(FileHandle sourceDir, FileHandle destDir) {
        destDir.mkdirs();
        for (FileHandle srcFile : sourceDir.list()) {
            FileHandle destFile = destDir.child(srcFile.name());
            if (srcFile.isDirectory()) {
                copyDirectory(srcFile, destFile);
            } else {
                copyFile(srcFile, destFile);
            }
        }
    }
}
