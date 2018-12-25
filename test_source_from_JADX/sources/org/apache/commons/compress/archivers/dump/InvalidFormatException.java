package org.apache.commons.compress.archivers.dump;

public class InvalidFormatException extends DumpArchiveException {
    private static final long serialVersionUID = 1;
    protected long offset;

    public InvalidFormatException() {
        super("there was an error decoding a tape segment");
    }

    public InvalidFormatException(long offset) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("there was an error decoding a tape segment header at offset ");
        stringBuilder.append(offset);
        stringBuilder.append(".");
        super(stringBuilder.toString());
        this.offset = offset;
    }

    public long getOffset() {
        return this.offset;
    }
}
