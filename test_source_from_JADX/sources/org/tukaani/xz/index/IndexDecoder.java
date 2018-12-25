package org.tukaani.xz.index;

import android.support.v4.media.session.PlaybackStateCompat;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;
import org.tukaani.xz.CorruptedInputException;
import org.tukaani.xz.MemoryLimitException;
import org.tukaani.xz.SeekableInputStream;
import org.tukaani.xz.UnsupportedOptionsException;
import org.tukaani.xz.common.DecoderUtil;
import org.tukaani.xz.common.StreamFlags;

public class IndexDecoder extends IndexBase {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static /* synthetic */ Class class$org$tukaani$xz$index$IndexDecoder = class$("org.tukaani.xz.index.IndexDecoder");
    private long compressedOffset = 0;
    private long largestBlockSize = 0;
    private final int memoryUsage;
    private int recordOffset = 0;
    private final StreamFlags streamFlags;
    private final long streamPadding;
    private final long[] uncompressed;
    private long uncompressedOffset = 0;
    private final long[] unpadded;

    static {
        if (class$org$tukaani$xz$index$IndexDecoder == null) {
        } else {
            Class cls = class$org$tukaani$xz$index$IndexDecoder;
        }
    }

    public IndexDecoder(SeekableInputStream seekableInputStream, StreamFlags streamFlags, long j, int i) throws IOException {
        StreamFlags streamFlags2 = streamFlags;
        int i2 = i;
        super(new CorruptedInputException("XZ Index is corrupt"));
        this.streamFlags = streamFlags2;
        this.streamPadding = j;
        long position = (seekableInputStream.position() + streamFlags2.backwardSize) - 4;
        Object crc32 = new CRC32();
        InputStream checkedInputStream = new CheckedInputStream(seekableInputStream, crc32);
        if (checkedInputStream.read() != 0) {
            throw new CorruptedInputException("XZ Index is corrupt");
        }
        try {
            long decodeVLI = DecoderUtil.decodeVLI(checkedInputStream);
            if (decodeVLI >= streamFlags2.backwardSize / 2) {
                throw new CorruptedInputException("XZ Index is corrupt");
            } else if (decodeVLI > 2147483647L) {
                throw new UnsupportedOptionsException("XZ Index has over 2147483647 Records");
            } else {
                r1.memoryUsage = ((int) (((16 * decodeVLI) + 1023) / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID)) + 1;
                if (i2 < 0 || r1.memoryUsage <= i2) {
                    long j2;
                    int i3 = (int) decodeVLI;
                    r1.unpadded = new long[i3];
                    r1.uncompressed = new long[i3];
                    i2 = 0;
                    while (i3 > 0) {
                        decodeVLI = DecoderUtil.decodeVLI(checkedInputStream);
                        long decodeVLI2 = DecoderUtil.decodeVLI(checkedInputStream);
                        if (seekableInputStream.position() > position) {
                            throw new CorruptedInputException("XZ Index is corrupt");
                        }
                        j2 = position;
                        r1.unpadded[i2] = r1.blocksSum + decodeVLI;
                        r1.uncompressed[i2] = r1.uncompressedSum + decodeVLI2;
                        i2++;
                        super.add(decodeVLI, decodeVLI2);
                        if (r1.largestBlockSize < decodeVLI2) {
                            r1.largestBlockSize = decodeVLI2;
                        }
                        i3--;
                        position = j2;
                    }
                    j2 = position;
                    i3 = getIndexPaddingSize();
                    if (seekableInputStream.position() + ((long) i3) != j2) {
                        throw new CorruptedInputException("XZ Index is corrupt");
                    }
                    while (true) {
                        i2 = i3 - 1;
                        if (i3 <= 0) {
                            break;
                        } else if (checkedInputStream.read() != 0) {
                            throw new CorruptedInputException("XZ Index is corrupt");
                        } else {
                            i3 = i2;
                        }
                    }
                    long value = crc32.getValue();
                    for (int i4 = 0; i4 < 4; i4++) {
                        if (((value >>> (i4 * 8)) & 255) != ((long) seekableInputStream.read())) {
                            throw new CorruptedInputException("XZ Index is corrupt");
                        }
                    }
                    return;
                }
                throw new MemoryLimitException(r1.memoryUsage, i2);
            }
        } catch (EOFException e) {
            throw new CorruptedInputException("XZ Index is corrupt");
        }
    }

    static /* synthetic */ Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (Throwable e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    public long getLargestBlockSize() {
        return this.largestBlockSize;
    }

    public int getMemoryUsage() {
        return this.memoryUsage;
    }

    public int getRecordCount() {
        return (int) this.recordCount;
    }

    public StreamFlags getStreamFlags() {
        return this.streamFlags;
    }

    public long getUncompressedSize() {
        return this.uncompressedSum;
    }

    public boolean hasRecord(int i) {
        return i >= this.recordOffset && ((long) i) < ((long) this.recordOffset) + this.recordCount;
    }

    public boolean hasUncompressedOffset(long j) {
        return j >= this.uncompressedOffset && j < this.uncompressedOffset + this.uncompressedSum;
    }

    public void locateBlock(BlockInfo blockInfo, long j) {
        long j2 = j - this.uncompressedOffset;
        int i = 0;
        int length = this.unpadded.length - 1;
        while (i < length) {
            int i2 = ((length - i) / 2) + i;
            if (this.uncompressed[i2] <= j2) {
                i = i2 + 1;
            } else {
                length = i2;
            }
        }
        setBlockInfo(blockInfo, this.recordOffset + i);
    }

    public void setBlockInfo(BlockInfo blockInfo, int i) {
        blockInfo.index = this;
        blockInfo.blockNumber = i;
        i -= this.recordOffset;
        if (i == 0) {
            blockInfo.compressedOffset = 0;
            blockInfo.uncompressedOffset = 0;
        } else {
            int i2 = i - 1;
            blockInfo.compressedOffset = (this.unpadded[i2] + 3) & -4;
            blockInfo.uncompressedOffset = this.uncompressed[i2];
        }
        blockInfo.unpaddedSize = this.unpadded[i] - blockInfo.compressedOffset;
        blockInfo.uncompressedSize = this.uncompressed[i] - blockInfo.uncompressedOffset;
        blockInfo.compressedOffset += this.compressedOffset + 12;
        blockInfo.uncompressedOffset += this.uncompressedOffset;
    }

    public void setOffsets(IndexDecoder indexDecoder) {
        this.recordOffset = indexDecoder.recordOffset + ((int) indexDecoder.recordCount);
        this.compressedOffset = (indexDecoder.compressedOffset + indexDecoder.getStreamSize()) + indexDecoder.streamPadding;
        this.uncompressedOffset = indexDecoder.uncompressedOffset + indexDecoder.uncompressedSum;
    }
}
