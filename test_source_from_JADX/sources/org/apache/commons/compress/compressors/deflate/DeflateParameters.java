package org.apache.commons.compress.compressors.deflate;

public class DeflateParameters {
    private int compressionLevel = -1;
    private boolean zlibHeader = true;

    public boolean withZlibHeader() {
        return this.zlibHeader;
    }

    public void setWithZlibHeader(boolean zlibHeader) {
        this.zlibHeader = zlibHeader;
    }

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
        stringBuilder.append("Invalid Deflate compression level: ");
        stringBuilder.append(compressionLevel);
        throw new IllegalArgumentException(stringBuilder.toString());
    }
}
