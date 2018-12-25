package org.apache.commons.compress.compressors.snappy;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.util.Arrays;
import org.apache.commons.compress.compressors.CompressorInputStream;
import org.apache.commons.compress.utils.BoundedInputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.billthefarmer.mididriver.GeneralMidiConstants;

public class FramedSnappyCompressorInputStream extends CompressorInputStream {
    private static final int COMPRESSED_CHUNK_TYPE = 0;
    static final long MASK_OFFSET = 2726488792L;
    private static final int MAX_SKIPPABLE_TYPE = 253;
    private static final int MAX_UNSKIPPABLE_TYPE = 127;
    private static final int MIN_UNSKIPPABLE_TYPE = 2;
    private static final int PADDING_CHUNK_TYPE = 254;
    private static final int STREAM_IDENTIFIER_TYPE = 255;
    private static final byte[] SZ_SIGNATURE = new byte[]{(byte) -1, (byte) 6, (byte) 0, (byte) 0, GeneralMidiConstants.WOODBLOCK, GeneralMidiConstants.WHISTLE, GeneralMidiConstants.FX_1_SOUNDTRACK, GeneralMidiConstants.LEAD_0_SQUARE, GeneralMidiConstants.TINKLE_BELL, GeneralMidiConstants.PAD_1_WARM};
    private static final int UNCOMPRESSED_CHUNK_TYPE = 1;
    private final PureJavaCrc32C checksum = new PureJavaCrc32C();
    private SnappyCompressorInputStream currentCompressedChunk;
    private boolean endReached;
    private long expectedChecksum = -1;
    private final PushbackInputStream in;
    private boolean inUncompressedChunk;
    private final byte[] oneByte = new byte[1];
    private int uncompressedBytesRemaining;

    public FramedSnappyCompressorInputStream(InputStream in) throws IOException {
        this.in = new PushbackInputStream(in, 1);
        readStreamIdentifier();
    }

    public int read() throws IOException {
        return read(this.oneByte, 0, 1) == -1 ? -1 : this.oneByte[0] & 255;
    }

    public void close() throws IOException {
        if (this.currentCompressedChunk != null) {
            this.currentCompressedChunk.close();
            this.currentCompressedChunk = null;
        }
        this.in.close();
    }

    public int read(byte[] b, int off, int len) throws IOException {
        int read = readOnce(b, off, len);
        if (read == -1) {
            readNextBlock();
            if (this.endReached) {
                return -1;
            }
            read = readOnce(b, off, len);
        }
        return read;
    }

    public int available() throws IOException {
        if (this.inUncompressedChunk) {
            return Math.min(this.uncompressedBytesRemaining, this.in.available());
        }
        if (this.currentCompressedChunk != null) {
            return this.currentCompressedChunk.available();
        }
        return 0;
    }

    private int readOnce(byte[] b, int off, int len) throws IOException {
        int read = -1;
        if (this.inUncompressedChunk) {
            int amount = Math.min(this.uncompressedBytesRemaining, len);
            if (amount == 0) {
                return -1;
            }
            read = this.in.read(b, off, amount);
            if (read != -1) {
                this.uncompressedBytesRemaining -= read;
                count(read);
            }
        } else if (this.currentCompressedChunk != null) {
            long before = this.currentCompressedChunk.getBytesRead();
            read = this.currentCompressedChunk.read(b, off, len);
            if (read == -1) {
                this.currentCompressedChunk.close();
                this.currentCompressedChunk = null;
            } else {
                count(this.currentCompressedChunk.getBytesRead() - before);
            }
        }
        if (read > 0) {
            this.checksum.update(b, off, read);
        }
        return read;
    }

    private void readNextBlock() throws IOException {
        verifyLastChecksumAndReset();
        this.inUncompressedChunk = false;
        int type = readOneByte();
        if (type == -1) {
            this.endReached = true;
        } else if (type == 255) {
            this.in.unread(type);
            pushedBackBytes(1);
            readStreamIdentifier();
            readNextBlock();
        } else {
            if (type != 254) {
                if (type <= 127 || type > 253) {
                    StringBuilder stringBuilder;
                    if (type >= 2 && type <= 127) {
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("unskippable chunk with type ");
                        stringBuilder.append(type);
                        stringBuilder.append(" (hex ");
                        stringBuilder.append(Integer.toHexString(type));
                        stringBuilder.append(")");
                        stringBuilder.append(" detected.");
                        throw new IOException(stringBuilder.toString());
                    } else if (type == 1) {
                        this.inUncompressedChunk = true;
                        this.uncompressedBytesRemaining = readSize() - 4;
                        this.expectedChecksum = unmask(readCrc());
                        return;
                    } else if (type == 0) {
                        long size = (long) (readSize() - 4);
                        this.expectedChecksum = unmask(readCrc());
                        this.currentCompressedChunk = new SnappyCompressorInputStream(new BoundedInputStream(this.in, size));
                        count(this.currentCompressedChunk.getBytesRead());
                        return;
                    } else {
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("unknown chunk type ");
                        stringBuilder.append(type);
                        stringBuilder.append(" detected.");
                        throw new IOException(stringBuilder.toString());
                    }
                }
            }
            skipBlock();
            readNextBlock();
        }
    }

    private long readCrc() throws IOException {
        byte[] b = new byte[4];
        int read = IOUtils.readFully(this.in, b);
        count(read);
        if (read != 4) {
            throw new IOException("premature end of stream");
        }
        long crc = 0;
        int i = 0;
        while (i < 4) {
            i++;
            crc |= (((long) b[i]) & 255) << (i * 8);
        }
        return crc;
    }

    static long unmask(long x) {
        long x2 = (x - MASK_OFFSET) & 4294967295L;
        return ((x2 >> 17) | (x2 << 15)) & 4294967295L;
    }

    private int readSize() throws IOException {
        int sz = 0;
        for (int i = 0; i < 3; i++) {
            int b = readOneByte();
            if (b == -1) {
                throw new IOException("premature end of stream");
            }
            sz |= b << (i * 8);
        }
        return sz;
    }

    private void skipBlock() throws IOException {
        int size = readSize();
        long read = IOUtils.skip(this.in, (long) size);
        count(read);
        if (read != ((long) size)) {
            throw new IOException("premature end of stream");
        }
    }

    private void readStreamIdentifier() throws IOException {
        byte[] b = new byte[10];
        int read = IOUtils.readFully(this.in, b);
        count(read);
        if (10 == read) {
            if (matches(b, 10)) {
                return;
            }
        }
        throw new IOException("Not a framed Snappy stream");
    }

    private int readOneByte() throws IOException {
        int b = this.in.read();
        if (b == -1) {
            return -1;
        }
        count(1);
        return b & 255;
    }

    private void verifyLastChecksumAndReset() throws IOException {
        if (this.expectedChecksum < 0 || this.expectedChecksum == this.checksum.getValue()) {
            this.expectedChecksum = -1;
            this.checksum.reset();
            return;
        }
        throw new IOException("Checksum verification failed");
    }

    public static boolean matches(byte[] signature, int length) {
        if (length < SZ_SIGNATURE.length) {
            return false;
        }
        byte[] shortenedSig = signature;
        if (signature.length > SZ_SIGNATURE.length) {
            shortenedSig = new byte[SZ_SIGNATURE.length];
            System.arraycopy(signature, 0, shortenedSig, 0, SZ_SIGNATURE.length);
        }
        return Arrays.equals(shortenedSig, SZ_SIGNATURE);
    }
}
