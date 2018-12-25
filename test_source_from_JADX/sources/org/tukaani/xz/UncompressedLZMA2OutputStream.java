package org.tukaani.xz;

import java.io.DataOutputStream;
import java.io.IOException;

class UncompressedLZMA2OutputStream extends FinishableOutputStream {
    private boolean dictResetNeeded = true;
    private IOException exception = null;
    private boolean finished = false;
    private FinishableOutputStream out;
    private final DataOutputStream outData;
    private final byte[] tempBuf = new byte[1];
    private final byte[] uncompBuf = new byte[65536];
    private int uncompPos = 0;

    UncompressedLZMA2OutputStream(FinishableOutputStream finishableOutputStream) {
        if (finishableOutputStream == null) {
            throw new NullPointerException();
        }
        this.out = finishableOutputStream;
        this.outData = new DataOutputStream(finishableOutputStream);
    }

    static int getMemoryUsage() {
        return 70;
    }

    private void writeChunk() throws IOException {
        this.outData.writeByte(this.dictResetNeeded ? 1 : 2);
        this.outData.writeShort(this.uncompPos - 1);
        this.outData.write(this.uncompBuf, 0, this.uncompPos);
        this.uncompPos = 0;
        this.dictResetNeeded = false;
    }

    private void writeEndMarker() throws IOException {
        if (this.exception != null) {
            throw this.exception;
        } else if (this.finished) {
            throw new XZIOException("Stream finished or closed");
        } else {
            try {
                if (this.uncompPos > 0) {
                    writeChunk();
                }
                this.out.write(0);
            } catch (IOException e) {
                this.exception = e;
                throw e;
            }
        }
    }

    public void close() throws IOException {
        if (this.out != null) {
            if (!this.finished) {
                try {
                    writeEndMarker();
                } catch (IOException e) {
                }
            }
            try {
                this.out.close();
            } catch (IOException e2) {
                if (this.exception == null) {
                    this.exception = e2;
                }
            }
            this.out = null;
        }
        if (this.exception != null) {
            throw this.exception;
        }
    }

    public void finish() throws IOException {
        if (!this.finished) {
            writeEndMarker();
            try {
                this.out.finish();
                this.finished = true;
            } catch (IOException e) {
                this.exception = e;
                throw e;
            }
        }
    }

    public void flush() throws IOException {
        if (this.exception != null) {
            throw this.exception;
        } else if (this.finished) {
            throw new XZIOException("Stream finished or closed");
        } else {
            try {
                if (this.uncompPos > 0) {
                    writeChunk();
                }
                this.out.flush();
            } catch (IOException e) {
                this.exception = e;
                throw e;
            }
        }
    }

    public void write(int i) throws IOException {
        this.tempBuf[0] = (byte) i;
        write(this.tempBuf, 0, 1);
    }

    public void write(byte[] bArr, int i, int i2) throws IOException {
        if (i >= 0 && i2 >= 0) {
            int i3 = i + i2;
            if (i3 >= 0) {
                if (i3 <= bArr.length) {
                    if (this.exception != null) {
                        throw this.exception;
                    } else if (this.finished) {
                        throw new XZIOException("Stream finished or closed");
                    } else {
                        while (i2 > 0) {
                            try {
                                i3 = Math.min(this.uncompBuf.length - this.uncompPos, i2);
                                System.arraycopy(bArr, i, this.uncompBuf, this.uncompPos, i3);
                                i2 -= i3;
                                this.uncompPos += i3;
                                if (this.uncompPos == this.uncompBuf.length) {
                                    writeChunk();
                                }
                            } catch (IOException e) {
                                this.exception = e;
                                throw e;
                            }
                        }
                        return;
                    }
                }
            }
        }
        throw new IndexOutOfBoundsException();
    }
}
