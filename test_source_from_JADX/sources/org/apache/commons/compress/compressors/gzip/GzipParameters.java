package org.apache.commons.compress.compressors.gzip;

public class GzipParameters {
    private String comment;
    private int compressionLevel = -1;
    private String filename;
    private long modificationTime;
    private int operatingSystem = 255;

    public int getCompressionLevel() {
        return this.compressionLevel;
    }

    public void setCompressionLevel(int compressionLevel) {
        if (compressionLevel >= -1) {
            if (compressionLevel <= 9) {
                this.compressionLevel = compressionLevel;
                return;
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid gzip compression level: ");
        stringBuilder.append(compressionLevel);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public long getModificationTime() {
        return this.modificationTime;
    }

    public void setModificationTime(long modificationTime) {
        this.modificationTime = modificationTime;
    }

    public String getFilename() {
        return this.filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getOperatingSystem() {
        return this.operatingSystem;
    }

    public void setOperatingSystem(int operatingSystem) {
        this.operatingSystem = operatingSystem;
    }
}
