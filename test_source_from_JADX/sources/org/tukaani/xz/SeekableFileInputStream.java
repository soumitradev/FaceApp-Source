package org.tukaani.xz;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class SeekableFileInputStream extends SeekableInputStream {
    protected RandomAccessFile randomAccessFile;

    public SeekableFileInputStream(File file) throws FileNotFoundException {
        this.randomAccessFile = new RandomAccessFile(file, "r");
    }

    public SeekableFileInputStream(RandomAccessFile randomAccessFile) {
        this.randomAccessFile = randomAccessFile;
    }

    public SeekableFileInputStream(String str) throws FileNotFoundException {
        this.randomAccessFile = new RandomAccessFile(str, "r");
    }

    public void close() throws IOException {
        this.randomAccessFile.close();
    }

    public long length() throws IOException {
        return this.randomAccessFile.length();
    }

    public long position() throws IOException {
        return this.randomAccessFile.getFilePointer();
    }

    public int read() throws IOException {
        return this.randomAccessFile.read();
    }

    public int read(byte[] bArr) throws IOException {
        return this.randomAccessFile.read(bArr);
    }

    public int read(byte[] bArr, int i, int i2) throws IOException {
        return this.randomAccessFile.read(bArr, i, i2);
    }

    public void seek(long j) throws IOException {
        this.randomAccessFile.seek(j);
    }
}
