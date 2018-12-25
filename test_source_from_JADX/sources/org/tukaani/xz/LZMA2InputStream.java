package org.tukaani.xz;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import name.antonsmirnov.firmata.writer.AnalogMessageWriter;
import name.antonsmirnov.firmata.writer.ReportAnalogPinMessageWriter;
import org.tukaani.xz.lz.LZDecoder;
import org.tukaani.xz.lzma.LZMADecoder;
import org.tukaani.xz.rangecoder.RangeDecoderFromBuffer;

public class LZMA2InputStream extends InputStream {
    private static final int COMPRESSED_SIZE_MAX = 65536;
    public static final int DICT_SIZE_MAX = 2147483632;
    public static final int DICT_SIZE_MIN = 4096;
    private boolean endReached;
    private IOException exception;
    private DataInputStream in;
    private boolean isLZMAChunk;
    private final LZDecoder lz;
    private LZMADecoder lzma;
    private boolean needDictReset;
    private boolean needProps;
    private final RangeDecoderFromBuffer rc;
    private final byte[] tempBuf;
    private int uncompressedSize;

    public LZMA2InputStream(InputStream inputStream, int i) {
        this(inputStream, i, null);
    }

    public LZMA2InputStream(InputStream inputStream, int i, byte[] bArr) {
        this.rc = new RangeDecoderFromBuffer(65536);
        this.uncompressedSize = 0;
        this.needDictReset = true;
        this.needProps = true;
        this.endReached = false;
        this.exception = null;
        this.tempBuf = new byte[1];
        if (inputStream == null) {
            throw new NullPointerException();
        }
        this.in = new DataInputStream(inputStream);
        this.lz = new LZDecoder(getDictSize(i), bArr);
        if (bArr != null && bArr.length > 0) {
            this.needDictReset = false;
        }
    }

    private void decodeChunkHeader() throws IOException {
        int readUnsignedByte = this.in.readUnsignedByte();
        if (readUnsignedByte == 0) {
            this.endReached = true;
            return;
        }
        if (readUnsignedByte < AnalogMessageWriter.COMMAND) {
            if (readUnsignedByte != 1) {
                if (this.needDictReset) {
                    throw new CorruptedInputException();
                }
                if (readUnsignedByte >= 128) {
                    this.isLZMAChunk = true;
                    this.uncompressedSize = (readUnsignedByte & 31) << 16;
                    this.uncompressedSize += this.in.readUnsignedShort() + 1;
                    int readUnsignedShort = this.in.readUnsignedShort() + 1;
                    if (readUnsignedByte >= ReportAnalogPinMessageWriter.COMMAND) {
                        this.needProps = false;
                        decodeProps();
                    } else if (this.needProps) {
                        throw new CorruptedInputException();
                    } else if (readUnsignedByte >= 160) {
                        this.lzma.reset();
                    }
                    this.rc.prepareInputBuffer(this.in, readUnsignedShort);
                } else if (readUnsignedByte <= 2) {
                    throw new CorruptedInputException();
                } else {
                    this.isLZMAChunk = false;
                    this.uncompressedSize = this.in.readUnsignedShort() + 1;
                }
            }
        }
        this.needProps = true;
        this.needDictReset = false;
        this.lz.reset();
        if (readUnsignedByte >= 128) {
            this.isLZMAChunk = true;
            this.uncompressedSize = (readUnsignedByte & 31) << 16;
            this.uncompressedSize += this.in.readUnsignedShort() + 1;
            int readUnsignedShort2 = this.in.readUnsignedShort() + 1;
            if (readUnsignedByte >= ReportAnalogPinMessageWriter.COMMAND) {
                this.needProps = false;
                decodeProps();
            } else if (this.needProps) {
                throw new CorruptedInputException();
            } else if (readUnsignedByte >= 160) {
                this.lzma.reset();
            }
            this.rc.prepareInputBuffer(this.in, readUnsignedShort2);
        } else if (readUnsignedByte <= 2) {
            this.isLZMAChunk = false;
            this.uncompressedSize = this.in.readUnsignedShort() + 1;
        } else {
            throw new CorruptedInputException();
        }
    }

    private void decodeProps() throws IOException {
        int readUnsignedByte = this.in.readUnsignedByte();
        if (readUnsignedByte > AnalogMessageWriter.COMMAND) {
            throw new CorruptedInputException();
        }
        int i = readUnsignedByte / 45;
        readUnsignedByte -= (i * 9) * 5;
        int i2 = readUnsignedByte / 9;
        int i3 = readUnsignedByte - (i2 * 9);
        if (i3 + i2 > 4) {
            throw new CorruptedInputException();
        }
        this.lzma = new LZMADecoder(this.lz, this.rc, i3, i2, i);
    }

    private static int getDictSize(int i) {
        if (i >= 4096) {
            if (i <= 2147483632) {
                return (i + 15) & -16;
            }
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Unsupported dictionary size ");
        stringBuffer.append(i);
        throw new IllegalArgumentException(stringBuffer.toString());
    }

    public static int getMemoryUsage(int i) {
        return (getDictSize(i) / 1024) + 104;
    }

    public int available() throws IOException {
        if (this.in == null) {
            throw new XZIOException("Stream closed");
        } else if (this.exception == null) {
            return this.uncompressedSize;
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
                                if (this.uncompressedSize == 0) {
                                    decodeChunkHeader();
                                    if (this.endReached) {
                                        if (i3 == 0) {
                                            i3 = -1;
                                        }
                                        return i3;
                                    }
                                }
                                int min = Math.min(this.uncompressedSize, i2);
                                if (this.isLZMAChunk) {
                                    this.lz.setLimit(min);
                                    this.lzma.decode();
                                    if (!this.rc.isInBufferOK()) {
                                        throw new CorruptedInputException();
                                    }
                                }
                                this.lz.copyUncompressed(this.in, min);
                                min = this.lz.flush(bArr, i);
                                i += min;
                                i2 -= min;
                                i3 += min;
                                this.uncompressedSize -= min;
                                if (this.uncompressedSize == 0 && (!this.rc.isFinished() || this.lz.hasPending())) {
                                    throw new CorruptedInputException();
                                }
                            } catch (IOException e) {
                                this.exception = e;
                                throw e;
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
