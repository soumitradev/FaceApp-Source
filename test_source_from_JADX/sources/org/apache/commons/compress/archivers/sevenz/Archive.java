package org.apache.commons.compress.archivers.sevenz;

import java.util.BitSet;

class Archive {
    SevenZArchiveEntry[] files;
    Folder[] folders;
    long[] packCrcs;
    BitSet packCrcsDefined;
    long packPos;
    long[] packSizes;
    StreamMap streamMap;
    SubStreamsInfo subStreamsInfo;

    Archive() {
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Archive with packed streams starting at offset ");
        stringBuilder.append(this.packPos);
        stringBuilder.append(", ");
        stringBuilder.append(lengthOf(this.packSizes));
        stringBuilder.append(" pack sizes, ");
        stringBuilder.append(lengthOf(this.packCrcs));
        stringBuilder.append(" CRCs, ");
        stringBuilder.append(lengthOf(this.folders));
        stringBuilder.append(" folders, ");
        stringBuilder.append(lengthOf(this.files));
        stringBuilder.append(" files and ");
        stringBuilder.append(this.streamMap);
        return stringBuilder.toString();
    }

    private static String lengthOf(long[] a) {
        return a == null ? "(null)" : String.valueOf(a.length);
    }

    private static String lengthOf(Object[] a) {
        return a == null ? "(null)" : String.valueOf(a.length);
    }
}
