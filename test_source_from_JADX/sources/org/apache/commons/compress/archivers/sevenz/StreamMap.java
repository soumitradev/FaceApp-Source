package org.apache.commons.compress.archivers.sevenz;

class StreamMap {
    int[] fileFolderIndex;
    int[] folderFirstFileIndex;
    int[] folderFirstPackStreamIndex;
    long[] packStreamOffsets;

    StreamMap() {
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("StreamMap with indices of ");
        stringBuilder.append(this.folderFirstPackStreamIndex.length);
        stringBuilder.append(" folders, offsets of ");
        stringBuilder.append(this.packStreamOffsets.length);
        stringBuilder.append(" packed streams,");
        stringBuilder.append(" first files of ");
        stringBuilder.append(this.folderFirstFileIndex.length);
        stringBuilder.append(" folders and");
        stringBuilder.append(" folder indices for ");
        stringBuilder.append(this.fileFolderIndex.length);
        stringBuilder.append(" files");
        return stringBuilder.toString();
    }
}
