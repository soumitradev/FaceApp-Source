package org.tukaani.xz;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class XZInputStream extends InputStream {
    private boolean endReached;
    private IOException exception;
    private InputStream in;
    private final int memoryLimit;
    private final byte[] tempBuf;
    private SingleXZInputStream xzIn;

    public XZInputStream(InputStream inputStream) throws IOException {
        this(inputStream, -1);
    }

    public XZInputStream(InputStream inputStream, int i) throws IOException {
        this.endReached = false;
        this.exception = null;
        this.tempBuf = new byte[1];
        this.in = inputStream;
        this.memoryLimit = i;
        this.xzIn = new SingleXZInputStream(inputStream, i);
    }

    private void prepareNextStream() throws IOException {
        DataInputStream dataInputStream = new DataInputStream(this.in);
        byte[] bArr = new byte[12];
        while (dataInputStream.read(bArr, 0, 1) != -1) {
            dataInputStream.readFully(bArr, 1, 3);
            if (bArr[0] != (byte) 0 || bArr[1] != (byte) 0 || bArr[2] != (byte) 0) {
                dataInputStream.readFully(bArr, 4, 8);
                try {
                    this.xzIn = new SingleXZInputStream(this.in, this.memoryLimit, bArr);
                    return;
                } catch (XZFormatException e) {
                    throw new CorruptedInputException("Garbage after a valid XZ Stream");
                }
            } else if (bArr[3] != (byte) 0) {
                dataInputStream.readFully(bArr, 4, 8);
                this.xzIn = new SingleXZInputStream(this.in, this.memoryLimit, bArr);
                return;
            }
        }
        this.endReached = true;
    }

    public int available() throws IOException {
        if (this.in == null) {
            throw new XZIOException("Stream closed");
        } else if (this.exception == null) {
            return this.xzIn == null ? 0 : this.xzIn.available();
        } else {
            throw this.exception;
        }
    }

    public void close() throws IOException {
        if (this.in != null) {
            try {
                this.in.close();
            } finally {
                this.in = null;
            }
        }
    }

    public int read() throws IOException {
        return read(this.tempBuf, 0, 1) == -1 ? -1 : this.tempBuf[0] & 255;
    }

    public int read(byte[] bArr, int i, int i2) throws IOException {
        if (i >= 0 && i2 >= 0) {
            int i3 = i + i2;
            if (i3 >= 0) {
                if (i3 <= bArr.length) {
                    i3 = 0;
                    if (i2 == 0) {
                        return 0;
                    }
                    if (this.in == null) {
                        throw new XZIOException("Stream closed");
                    } else if (this.exception != null) {
                        throw this.exception;
                    } else if (this.endReached) {
                        return -1;
                    } else {
                        while (i2 > 0) {
                            try {
                                if (this.xzIn == null) {
                                    prepareNextStream();
                                    if (this.endReached) {
                                        if (i3 == 0) {
                                            i3 = -1;
                                        }
                                        return i3;
                                    }
                                }
                                int read = this.xzIn.read(bArr, i, i2);
                                if (read > 0) {
                                    i3 += read;
                                    i += read;
                                    i2 -= read;
                                } else if (read == -1) {
                                    this.xzIn = null;
                                }
                            } catch (IOException e) {
                                this.exception = e;
                                if (i3 == 0) {
                                    throw e;
                                }
                            }
                        }
                        return i3;
                    }
                }
            }
        }
        throw new IndexOutOfBoundsException();
    }
}
