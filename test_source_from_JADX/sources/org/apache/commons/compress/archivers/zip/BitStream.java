package org.apache.commons.compress.archivers.zip;

import com.pdrogfer.mididroid.event.meta.MetaEvent;
import java.io.IOException;
import java.io.InputStream;

class BitStream {
    private static final int[] MASKS = new int[]{0, 1, 3, 7, 15, 31, 63, MetaEvent.SEQUENCER_SPECIFIC, 255};
    private long bitCache;
    private int bitCacheSize;
    private final InputStream in;

    BitStream(InputStream in) {
        this.in = in;
    }

    private boolean fillCache() throws IOException {
        boolean filled = false;
        while (this.bitCacheSize <= 56) {
            long nextByte = (long) this.in.read();
            if (nextByte == -1) {
                break;
            }
            filled = true;
            this.bitCache |= nextByte << this.bitCacheSize;
            this.bitCacheSize += 8;
        }
        return filled;
    }

    int nextBit() throws IOException {
        if (this.bitCacheSize == 0 && !fillCache()) {
            return -1;
        }
        int bit = (int) (this.bitCache & 1);
        this.bitCache >>>= 1;
        this.bitCacheSize--;
        return bit;
    }

    int nextBits(int n) throws IOException {
        if (this.bitCacheSize < n && !fillCache()) {
            return -1;
        }
        int bits = (int) (this.bitCache & ((long) MASKS[n]));
        this.bitCache >>>= n;
        this.bitCacheSize -= n;
        return bits;
    }

    int nextByte() throws IOException {
        return nextBits(8);
    }
}
