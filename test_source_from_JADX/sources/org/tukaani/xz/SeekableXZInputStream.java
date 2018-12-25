package org.tukaani.xz;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import org.tukaani.xz.check.Check;
import org.tukaani.xz.common.DecoderUtil;
import org.tukaani.xz.common.StreamFlags;
import org.tukaani.xz.index.BlockInfo;
import org.tukaani.xz.index.IndexDecoder;

public class SeekableXZInputStream extends SeekableInputStream {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static /* synthetic */ Class class$org$tukaani$xz$SeekableXZInputStream = class$("org.tukaani.xz.SeekableXZInputStream");
    private int blockCount;
    private BlockInputStream blockDecoder;
    private Check check;
    private int checkTypes;
    private final BlockInfo curBlockInfo;
    private long curPos;
    private boolean endReached;
    private IOException exception;
    private SeekableInputStream in;
    private int indexMemoryUsage;
    private long largestBlockSize;
    private final int memoryLimit;
    private final BlockInfo queriedBlockInfo;
    private boolean seekNeeded;
    private long seekPos;
    private final ArrayList streams;
    private final byte[] tempBuf;
    private long uncompressedSize;

    static {
        if (class$org$tukaani$xz$SeekableXZInputStream == null) {
        } else {
            Class cls = class$org$tukaani$xz$SeekableXZInputStream;
        }
    }

    public SeekableXZInputStream(SeekableInputStream seekableInputStream) throws IOException {
        this(seekableInputStream, -1);
    }

    public SeekableXZInputStream(SeekableInputStream seekableInputStream, int i) throws IOException {
        InputStream inputStream = seekableInputStream;
        this.indexMemoryUsage = 0;
        this.streams = new ArrayList();
        this.checkTypes = 0;
        long j = 0;
        this.uncompressedSize = 0;
        this.largestBlockSize = 0;
        this.blockCount = 0;
        this.blockDecoder = null;
        this.curPos = 0;
        this.seekNeeded = false;
        this.endReached = false;
        this.exception = null;
        int i2 = 1;
        this.tempBuf = new byte[1];
        this.in = inputStream;
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        inputStream.seek(0);
        byte[] bArr = new byte[XZ.HEADER_MAGIC.length];
        dataInputStream.readFully(bArr);
        if (Arrays.equals(bArr, XZ.HEADER_MAGIC)) {
            long length = seekableInputStream.length();
            if ((length & 3) != 0) {
                throw new CorruptedInputException("XZ file size is not a multiple of 4 bytes");
            }
            IndexDecoder indexDecoder;
            byte[] bArr2 = new byte[12];
            int i3 = i;
            long j2 = 0;
            while (length > j) {
                if (length < 12) {
                    throw new CorruptedInputException();
                }
                j = length - 12;
                inputStream.seek(j);
                dataInputStream.readFully(bArr2);
                if (bArr2[8] == (byte) 0 && bArr2[9] == (byte) 0 && bArr2[10] == (byte) 0 && bArr2[11] == (byte) 0) {
                    length -= 4;
                    j2 += 4;
                    j = 0;
                } else {
                    StreamFlags decodeStreamFooter = DecoderUtil.decodeStreamFooter(bArr2);
                    if (decodeStreamFooter.backwardSize >= j) {
                        throw new CorruptedInputException("Backward Size in XZ Stream Footer is too big");
                    }
                    r1.check = Check.getInstance(decodeStreamFooter.checkType);
                    r1.checkTypes |= i2 << decodeStreamFooter.checkType;
                    DataInputStream dataInputStream2 = dataInputStream;
                    inputStream.seek(j - decodeStreamFooter.backwardSize);
                    try {
                        StreamFlags streamFlags = decodeStreamFooter;
                        indexDecoder = new IndexDecoder(inputStream, decodeStreamFooter, j2, i3);
                        r1.indexMemoryUsage += indexDecoder.getMemoryUsage();
                        if (i3 >= 0) {
                            i3 -= indexDecoder.getMemoryUsage();
                        }
                        if (r1.largestBlockSize < indexDecoder.getLargestBlockSize()) {
                            r1.largestBlockSize = indexDecoder.getLargestBlockSize();
                        }
                        long streamSize = indexDecoder.getStreamSize() - 12;
                        if (j < streamSize) {
                            throw new CorruptedInputException("XZ Index indicates too big compressed size for the XZ Stream");
                        }
                        length = j - streamSize;
                        inputStream.seek(length);
                        DataInputStream dataInputStream3 = dataInputStream2;
                        dataInputStream3.readFully(bArr2);
                        if (DecoderUtil.areStreamFlagsEqual(DecoderUtil.decodeStreamHeader(bArr2), streamFlags)) {
                            long j3 = length;
                            r1.uncompressedSize += indexDecoder.getUncompressedSize();
                            j2 = 0;
                            if (r1.uncompressedSize < 0) {
                                throw new UnsupportedOptionsException("XZ file is too big");
                            }
                            r1.blockCount += indexDecoder.getRecordCount();
                            if (r1.blockCount < 0) {
                                throw new UnsupportedOptionsException("XZ file has over 2147483647 Blocks");
                            }
                            r1.streams.add(indexDecoder);
                            dataInputStream = dataInputStream3;
                            j = 0;
                            length = j3;
                            i2 = 1;
                        } else {
                            throw new CorruptedInputException("XZ Stream Footer does not match Stream Header");
                        }
                    } catch (MemoryLimitException e) {
                        throw new MemoryLimitException(e.getMemoryNeeded() + r1.indexMemoryUsage, i3 + r1.indexMemoryUsage);
                    }
                }
            }
            r1.memoryLimit = i3;
            indexDecoder = (IndexDecoder) r1.streams.get(r1.streams.size() - 1);
            int size = r1.streams.size() - 2;
            while (size >= 0) {
                IndexDecoder indexDecoder2 = (IndexDecoder) r1.streams.get(size);
                indexDecoder2.setOffsets(indexDecoder);
                size--;
                indexDecoder = indexDecoder2;
            }
            indexDecoder = (IndexDecoder) r1.streams.get(r1.streams.size() - 1);
            r1.curBlockInfo = new BlockInfo(indexDecoder);
            r1.queriedBlockInfo = new BlockInfo(indexDecoder);
            return;
        }
        throw new XZFormatException();
    }

    static /* synthetic */ Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (Throwable e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    private void initBlockDecoder() throws IOException {
        try {
            this.blockDecoder = null;
            this.blockDecoder = new BlockInputStream(this.in, this.check, this.memoryLimit, this.curBlockInfo.unpaddedSize, this.curBlockInfo.uncompressedSize);
        } catch (MemoryLimitException e) {
            throw new MemoryLimitException(e.getMemoryNeeded() + this.indexMemoryUsage, this.memoryLimit + this.indexMemoryUsage);
        } catch (IndexIndicatorException e2) {
            throw new CorruptedInputException();
        }
    }

    private void locateBlockByNumber(BlockInfo blockInfo, int i) {
        if (i >= 0) {
            if (i < this.blockCount) {
                if (blockInfo.blockNumber != i) {
                    int i2 = 0;
                    while (true) {
                        IndexDecoder indexDecoder = (IndexDecoder) this.streams.get(i2);
                        if (indexDecoder.hasRecord(i)) {
                            indexDecoder.setBlockInfo(blockInfo, i);
                            return;
                        }
                        i2++;
                    }
                } else {
                    return;
                }
            }
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Invalid XZ Block number: ");
        stringBuffer.append(i);
        throw new IndexOutOfBoundsException(stringBuffer.toString());
    }

    private void locateBlockByPos(BlockInfo blockInfo, long j) {
        if (j >= 0) {
            if (j < this.uncompressedSize) {
                int i = 0;
                while (true) {
                    IndexDecoder indexDecoder = (IndexDecoder) this.streams.get(i);
                    if (indexDecoder.hasUncompressedOffset(j)) {
                        indexDecoder.locateBlock(blockInfo, j);
                        return;
                    }
                    i++;
                }
            }
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Invalid uncompressed position: ");
        stringBuffer.append(j);
        throw new IndexOutOfBoundsException(stringBuffer.toString());
    }

    private void seek() throws IOException {
        if (!this.seekNeeded) {
            if (this.curBlockInfo.hasNext()) {
                this.curBlockInfo.setNext();
                initBlockDecoder();
                return;
            }
            this.seekPos = this.curPos;
        }
        this.seekNeeded = false;
        if (this.seekPos >= this.uncompressedSize) {
            this.curPos = this.seekPos;
            this.blockDecoder = null;
            this.endReached = true;
            return;
        }
        this.endReached = false;
        locateBlockByPos(this.curBlockInfo, this.seekPos);
        if (this.curPos <= this.curBlockInfo.uncompressedOffset || this.curPos > this.seekPos) {
            this.in.seek(this.curBlockInfo.compressedOffset);
            this.check = Check.getInstance(this.curBlockInfo.getCheckType());
            initBlockDecoder();
            this.curPos = this.curBlockInfo.uncompressedOffset;
        }
        if (this.seekPos > this.curPos) {
            long j = this.seekPos - this.curPos;
            if (this.blockDecoder.skip(j) != j) {
                throw new CorruptedInputException();
            }
            this.curPos = this.seekPos;
        }
    }

    public int available() throws IOException {
        if (this.in == null) {
            throw new XZIOException("Stream closed");
        } else if (this.exception != null) {
            throw this.exception;
        } else {
            if (!(this.endReached || this.seekNeeded)) {
                if (this.blockDecoder != null) {
                    return this.blockDecoder.available();
                }
            }
            return 0;
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

    public int getBlockCheckType(int i) {
        locateBlockByNumber(this.queriedBlockInfo, i);
        return this.queriedBlockInfo.getCheckType();
    }

    public long getBlockCompPos(int i) {
        locateBlockByNumber(this.queriedBlockInfo, i);
        return this.queriedBlockInfo.compressedOffset;
    }

    public long getBlockCompSize(int i) {
        locateBlockByNumber(this.queriedBlockInfo, i);
        return (this.queriedBlockInfo.unpaddedSize + 3) & -4;
    }

    public int getBlockCount() {
        return this.blockCount;
    }

    public int getBlockNumber(long j) {
        locateBlockByPos(this.queriedBlockInfo, j);
        return this.queriedBlockInfo.blockNumber;
    }

    public long getBlockPos(int i) {
        locateBlockByNumber(this.queriedBlockInfo, i);
        return this.queriedBlockInfo.uncompressedOffset;
    }

    public long getBlockSize(int i) {
        locateBlockByNumber(this.queriedBlockInfo, i);
        return this.queriedBlockInfo.uncompressedSize;
    }

    public int getCheckTypes() {
        return this.checkTypes;
    }

    public int getIndexMemoryUsage() {
        return this.indexMemoryUsage;
    }

    public long getLargestBlockSize() {
        return this.largestBlockSize;
    }

    public int getStreamCount() {
        return this.streams.size();
    }

    public long length() {
        return this.uncompressedSize;
    }

    public long position() throws IOException {
        if (this.in != null) {
            return this.seekNeeded ? this.seekPos : this.curPos;
        } else {
            throw new XZIOException("Stream closed");
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
                    } else {
                        try {
                            if (this.seekNeeded) {
                                seek();
                            }
                            if (this.endReached) {
                                return -1;
                            }
                            while (i2 > 0) {
                                if (this.blockDecoder == null) {
                                    seek();
                                    if (this.endReached) {
                                        return i3;
                                    }
                                }
                                int read = this.blockDecoder.read(bArr, i, i2);
                                if (read > 0) {
                                    this.curPos += (long) read;
                                    i3 += read;
                                    i += read;
                                    i2 -= read;
                                } else if (read == -1) {
                                    this.blockDecoder = null;
                                }
                            }
                            return i3;
                        } catch (IOException e) {
                            IOException e2 = e;
                            if (e2 instanceof EOFException) {
                                e2 = new CorruptedInputException();
                            }
                            this.exception = e2;
                            if (null == null) {
                                throw e2;
                            }
                        }
                    }
                }
            }
        }
        throw new IndexOutOfBoundsException();
    }

    public void seek(long j) throws IOException {
        if (this.in == null) {
            throw new XZIOException("Stream closed");
        } else if (j < 0) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Negative seek position: ");
            stringBuffer.append(j);
            throw new XZIOException(stringBuffer.toString());
        } else {
            this.seekPos = j;
            this.seekNeeded = true;
        }
    }

    public void seekToBlock(int i) throws IOException {
        if (this.in == null) {
            throw new XZIOException("Stream closed");
        }
        if (i >= 0) {
            if (i < this.blockCount) {
                this.seekPos = getBlockPos(i);
                this.seekNeeded = true;
                return;
            }
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Invalid XZ Block number: ");
        stringBuffer.append(i);
        throw new XZIOException(stringBuffer.toString());
    }
}
