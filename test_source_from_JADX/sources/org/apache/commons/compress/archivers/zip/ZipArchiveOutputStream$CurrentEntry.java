package org.apache.commons.compress.archivers.zip;

final class ZipArchiveOutputStream$CurrentEntry {
    private long bytesRead;
    private boolean causedUseOfZip64;
    private long dataStart;
    private final ZipArchiveEntry entry;
    private boolean hasWritten;
    private long localDataStart;

    static /* synthetic */ long access$314(ZipArchiveOutputStream$CurrentEntry x0, long x1) {
        long j = x0.bytesRead + x1;
        x0.bytesRead = j;
        return j;
    }

    private ZipArchiveOutputStream$CurrentEntry(ZipArchiveEntry entry) {
        this.localDataStart = 0;
        this.dataStart = 0;
        this.bytesRead = 0;
        this.causedUseOfZip64 = false;
        this.entry = entry;
    }
}
