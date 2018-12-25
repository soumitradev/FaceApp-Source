package org.apache.commons.compress.archivers;

public class StreamingNotSupportedException extends ArchiveException {
    private static final long serialVersionUID = 1;
    private final String format;

    public StreamingNotSupportedException(String format) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("The ");
        stringBuilder.append(format);
        stringBuilder.append(" doesn't support streaming.");
        super(stringBuilder.toString());
        this.format = format;
    }

    public String getFormat() {
        return this.format;
    }
}
