package com.google.common.io;

import com.google.common.annotations.Beta;
import com.google.common.annotations.VisibleForTesting;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Beta
public final class FileBackedOutputStream extends OutputStream {
    private File file;
    private final int fileThreshold;
    private MemoryOutput memory;
    private OutputStream out;
    private final boolean resetOnFinalize;
    private final ByteSource source;

    private static class MemoryOutput extends ByteArrayOutputStream {
        private MemoryOutput() {
        }

        byte[] getBuffer() {
            return this.buf;
        }

        int getCount() {
            return this.count;
        }
    }

    /* renamed from: com.google.common.io.FileBackedOutputStream$1 */
    class C09901 extends ByteSource {
        C09901() {
        }

        public InputStream openStream() throws IOException {
            return FileBackedOutputStream.this.openInputStream();
        }

        protected void finalize() {
            try {
                FileBackedOutputStream.this.reset();
            } catch (Throwable t) {
                t.printStackTrace(System.err);
            }
        }
    }

    /* renamed from: com.google.common.io.FileBackedOutputStream$2 */
    class C09912 extends ByteSource {
        C09912() {
        }

        public InputStream openStream() throws IOException {
            return FileBackedOutputStream.this.openInputStream();
        }
    }

    @VisibleForTesting
    synchronized File getFile() {
        return this.file;
    }

    public FileBackedOutputStream(int fileThreshold) {
        this(fileThreshold, false);
    }

    public FileBackedOutputStream(int fileThreshold, boolean resetOnFinalize) {
        this.fileThreshold = fileThreshold;
        this.resetOnFinalize = resetOnFinalize;
        this.memory = new MemoryOutput();
        this.out = this.memory;
        if (resetOnFinalize) {
            this.source = new C09901();
        } else {
            this.source = new C09912();
        }
    }

    public ByteSource asByteSource() {
        return this.source;
    }

    private synchronized InputStream openInputStream() throws IOException {
        if (this.file != null) {
            return new FileInputStream(this.file);
        }
        return new ByteArrayInputStream(this.memory.getBuffer(), 0, this.memory.getCount());
    }

    public synchronized void reset() throws IOException {
        IOException iOException;
        try {
            close();
            if (this.memory == null) {
                this.memory = new MemoryOutput();
            } else {
                this.memory.reset();
            }
            this.out = this.memory;
            if (this.file != null) {
                File deleteMe = this.file;
                this.file = null;
                if (!deleteMe.delete()) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Could not delete: ");
                    stringBuilder.append(deleteMe);
                    iOException = new IOException(stringBuilder.toString());
                    throw iOException;
                }
            }
        } catch (Throwable th) {
            if (this.memory == null) {
                this.memory = new MemoryOutput();
            } else {
                this.memory.reset();
            }
            this.out = this.memory;
            if (this.file != null) {
                File deleteMe2 = this.file;
                this.file = null;
                if (!deleteMe2.delete()) {
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("Could not delete: ");
                    stringBuilder2.append(deleteMe2);
                    iOException = new IOException(stringBuilder2.toString());
                }
            }
        }
    }

    public synchronized void write(int b) throws IOException {
        update(1);
        this.out.write(b);
    }

    public synchronized void write(byte[] b) throws IOException {
        write(b, 0, b.length);
    }

    public synchronized void write(byte[] b, int off, int len) throws IOException {
        update(len);
        this.out.write(b, off, len);
    }

    public synchronized void close() throws IOException {
        this.out.close();
    }

    public synchronized void flush() throws IOException {
        this.out.flush();
    }

    private void update(int len) throws IOException {
        if (this.file == null && this.memory.getCount() + len > this.fileThreshold) {
            File temp = File.createTempFile("FileBackedOutputStream", null);
            if (this.resetOnFinalize) {
                temp.deleteOnExit();
            }
            FileOutputStream transfer = new FileOutputStream(temp);
            transfer.write(this.memory.getBuffer(), 0, this.memory.getCount());
            transfer.flush();
            this.out = transfer;
            this.file = temp;
            this.memory = null;
        }
    }
}
