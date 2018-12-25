package org.apache.commons.compress.compressors.gzip;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.CRC32;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;
import org.apache.commons.compress.compressors.CompressorInputStream;
import org.apache.commons.compress.utils.CharsetNames;

public class GzipCompressorInputStream extends CompressorInputStream {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int FCOMMENT = 16;
    private static final int FEXTRA = 4;
    private static final int FHCRC = 2;
    private static final int FNAME = 8;
    private static final int FRESERVED = 224;
    private final byte[] buf;
    private int bufUsed;
    private final CRC32 crc;
    private final boolean decompressConcatenated;
    private boolean endReached;
    private final InputStream in;
    private Inflater inf;
    private final byte[] oneByte;
    private final GzipParameters parameters;

    public GzipCompressorInputStream(InputStream inputStream) throws IOException {
        this(inputStream, false);
    }

    public GzipCompressorInputStream(InputStream inputStream, boolean decompressConcatenated) throws IOException {
        this.buf = new byte[8192];
        this.bufUsed = 0;
        this.inf = new Inflater(true);
        this.crc = new CRC32();
        this.endReached = false;
        this.oneByte = new byte[1];
        this.parameters = new GzipParameters();
        if (inputStream.markSupported()) {
            this.in = inputStream;
        } else {
            this.in = new BufferedInputStream(inputStream);
        }
        this.decompressConcatenated = decompressConcatenated;
        init(true);
    }

    public GzipParameters getMetaData() {
        return this.parameters;
    }

    private boolean init(boolean isFirstMember) throws IOException {
        int magic0 = this.in.read();
        int magic1 = this.in.read();
        if (magic0 == -1 && !isFirstMember) {
            return false;
        }
        if (magic0 == 31) {
            if (magic1 == 139) {
                DataInputStream inData = new DataInputStream(this.in);
                int method = inData.readUnsignedByte();
                if (method != 8) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Unsupported compression method ");
                    stringBuilder.append(method);
                    stringBuilder.append(" in the .gz header");
                    throw new IOException(stringBuilder.toString());
                }
                int flg = inData.readUnsignedByte();
                if ((flg & 224) != 0) {
                    throw new IOException("Reserved flags are set in the .gz header");
                }
                this.parameters.setModificationTime(readLittleEndianInt(inData) * 1000);
                int readUnsignedByte = inData.readUnsignedByte();
                if (readUnsignedByte == 2) {
                    this.parameters.setCompressionLevel(9);
                } else if (readUnsignedByte == 4) {
                    this.parameters.setCompressionLevel(1);
                }
                this.parameters.setOperatingSystem(inData.readUnsignedByte());
                if ((flg & 4) != 0) {
                    int xlen = (inData.readUnsignedByte() << 8) | inData.readUnsignedByte();
                    while (true) {
                        readUnsignedByte = xlen - 1;
                        if (xlen <= 0) {
                            break;
                        }
                        inData.readUnsignedByte();
                        xlen = readUnsignedByte;
                    }
                }
                if ((flg & 8) != 0) {
                    this.parameters.setFilename(new String(readToNull(inData), CharsetNames.ISO_8859_1));
                }
                if ((flg & 16) != 0) {
                    this.parameters.setComment(new String(readToNull(inData), CharsetNames.ISO_8859_1));
                }
                if ((flg & 2) != 0) {
                    inData.readShort();
                }
                this.inf.reset();
                this.crc.reset();
                return true;
            }
        }
        throw new IOException(isFirstMember ? "Input is not in the .gz format" : "Garbage after a valid .gz stream");
    }

    private byte[] readToNull(DataInputStream inData) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while (true) {
            int readUnsignedByte = inData.readUnsignedByte();
            int b = readUnsignedByte;
            if (readUnsignedByte == 0) {
                return bos.toByteArray();
            }
            bos.write(b);
        }
    }

    private long readLittleEndianInt(DataInputStream inData) throws IOException {
        return ((long) ((inData.readUnsignedByte() | (inData.readUnsignedByte() << 8)) | (inData.readUnsignedByte() << 16))) | (((long) inData.readUnsignedByte()) << 24);
    }

    public int read() throws IOException {
        return read(this.oneByte, 0, 1) == -1 ? -1 : this.oneByte[0] & 255;
    }

    public int read(byte[] b, int off, int len) throws IOException {
        byte[] bArr = b;
        int i = -1;
        if (this.endReached) {
            return -1;
        }
        int off2 = off;
        int len2 = len;
        int size = 0;
        while (len2 > 0) {
            if (r1.inf.needsInput()) {
                r1.in.mark(r1.buf.length);
                r1.bufUsed = r1.in.read(r1.buf);
                if (r1.bufUsed == -1) {
                    throw new EOFException();
                }
                r1.inf.setInput(r1.buf, 0, r1.bufUsed);
            }
            try {
                int ret = r1.inf.inflate(bArr, off2, len2);
                r1.crc.update(bArr, off2, ret);
                off2 += ret;
                len2 -= ret;
                size += ret;
                count(ret);
                if (r1.inf.finished()) {
                    r1.in.reset();
                    int skipAmount = r1.bufUsed - r1.inf.getRemaining();
                    if (r1.in.skip((long) skipAmount) != ((long) skipAmount)) {
                        throw new IOException();
                    }
                    r1.bufUsed = 0;
                    DataInputStream inData = new DataInputStream(r1.in);
                    if (readLittleEndianInt(inData) != r1.crc.getValue()) {
                        throw new IOException("Gzip-compressed data is corrupt (CRC32 error)");
                    } else if (readLittleEndianInt(inData) != (r1.inf.getBytesWritten() & 4294967295L)) {
                        throw new IOException("Gzip-compressed data is corrupt(uncompressed size mismatch)");
                    } else if (!r1.decompressConcatenated || !init(false)) {
                        r1.inf.end();
                        r1.inf = null;
                        r1.endReached = true;
                        if (size != 0) {
                            i = size;
                        }
                        return i;
                    }
                }
            } catch (DataFormatException e) {
                throw new IOException("Gzip-compressed data is corrupt");
            }
        }
        return size;
    }

    public static boolean matches(byte[] signature, int length) {
        if (length >= 2 && signature[0] == (byte) 31 && signature[1] == (byte) -117) {
            return true;
        }
        return false;
    }

    public void close() throws IOException {
        if (this.inf != null) {
            this.inf.end();
            this.inf = null;
        }
        if (this.in != System.in) {
            this.in.close();
        }
    }
}
