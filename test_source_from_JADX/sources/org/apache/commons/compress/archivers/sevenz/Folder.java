package org.apache.commons.compress.archivers.sevenz;

import java.util.LinkedList;

class Folder {
    BindPair[] bindPairs;
    Coder[] coders;
    long crc;
    boolean hasCrc;
    int numUnpackSubStreams;
    long[] packedStreams;
    long totalInputStreams;
    long totalOutputStreams;
    long[] unpackSizes;

    Folder() {
    }

    Iterable<Coder> getOrderedCoders() {
        LinkedList<Coder> l = new LinkedList();
        int current = (int) this.packedStreams[0];
        while (true) {
            int i = -1;
            if (current == -1) {
                return l;
            }
            l.addLast(this.coders[current]);
            int pair = findBindPairForOutStream(current);
            if (pair != -1) {
                i = (int) this.bindPairs[pair].inIndex;
            }
            current = i;
        }
    }

    int findBindPairForInStream(int index) {
        for (int i = 0; i < this.bindPairs.length; i++) {
            if (this.bindPairs[i].inIndex == ((long) index)) {
                return i;
            }
        }
        return -1;
    }

    int findBindPairForOutStream(int index) {
        for (int i = 0; i < this.bindPairs.length; i++) {
            if (this.bindPairs[i].outIndex == ((long) index)) {
                return i;
            }
        }
        return -1;
    }

    long getUnpackSize() {
        if (this.totalOutputStreams == 0) {
            return 0;
        }
        for (int i = ((int) this.totalOutputStreams) - 1; i >= 0; i--) {
            if (findBindPairForOutStream(i) < 0) {
                return this.unpackSizes[i];
            }
        }
        return 0;
    }

    long getUnpackSizeForCoder(Coder coder) {
        if (this.coders != null) {
            for (int i = 0; i < this.coders.length; i++) {
                if (this.coders[i] == coder) {
                    return this.unpackSizes[i];
                }
            }
        }
        return 0;
    }

    public String toString() {
        String stringBuilder;
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("Folder with ");
        stringBuilder2.append(this.coders.length);
        stringBuilder2.append(" coders, ");
        stringBuilder2.append(this.totalInputStreams);
        stringBuilder2.append(" input streams, ");
        stringBuilder2.append(this.totalOutputStreams);
        stringBuilder2.append(" output streams, ");
        stringBuilder2.append(this.bindPairs.length);
        stringBuilder2.append(" bind pairs, ");
        stringBuilder2.append(this.packedStreams.length);
        stringBuilder2.append(" packed streams, ");
        stringBuilder2.append(this.unpackSizes.length);
        stringBuilder2.append(" unpack sizes, ");
        if (this.hasCrc) {
            StringBuilder stringBuilder3 = new StringBuilder();
            stringBuilder3.append("with CRC ");
            stringBuilder3.append(this.crc);
            stringBuilder = stringBuilder3.toString();
        } else {
            stringBuilder = "without CRC";
        }
        stringBuilder2.append(stringBuilder);
        stringBuilder2.append(" and ");
        stringBuilder2.append(this.numUnpackSubStreams);
        stringBuilder2.append(" unpack streams");
        return stringBuilder2.toString();
    }
}
