package org.tukaani.xz.index;

import org.tukaani.xz.XZIOException;
import org.tukaani.xz.common.Util;

abstract class IndexBase {
    long blocksSum = 0;
    long indexListSize = 0;
    private final XZIOException invalidIndexException;
    long recordCount = 0;
    long uncompressedSum = 0;

    IndexBase(XZIOException xZIOException) {
        this.invalidIndexException = xZIOException;
    }

    private long getUnpaddedIndexSize() {
        return (((long) (Util.getVLISize(this.recordCount) + 1)) + this.indexListSize) + 4;
    }

    void add(long j, long j2) throws XZIOException {
        this.blocksSum += (j + 3) & -4;
        this.uncompressedSum += j2;
        this.indexListSize += (long) (Util.getVLISize(j) + Util.getVLISize(j2));
        this.recordCount++;
        if (this.blocksSum >= 0 && this.uncompressedSum >= 0 && getIndexSize() <= Util.BACKWARD_SIZE_MAX) {
            if (getStreamSize() >= 0) {
                return;
            }
        }
        throw this.invalidIndexException;
    }

    int getIndexPaddingSize() {
        return (int) ((4 - getUnpaddedIndexSize()) & 3);
    }

    public long getIndexSize() {
        return (getUnpaddedIndexSize() + 3) & -4;
    }

    public long getStreamSize() {
        return ((this.blocksSum + 12) + getIndexSize()) + 12;
    }
}
