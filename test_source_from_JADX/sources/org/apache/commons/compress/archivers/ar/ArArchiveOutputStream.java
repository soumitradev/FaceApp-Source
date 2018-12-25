package org.apache.commons.compress.archivers.ar;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import name.antonsmirnov.firmata.FormatHelper;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.utils.ArchiveUtils;

public class ArArchiveOutputStream extends ArchiveOutputStream {
    public static final int LONGFILE_BSD = 1;
    public static final int LONGFILE_ERROR = 0;
    private long entryOffset = 0;
    private boolean finished = false;
    private boolean haveUnclosedEntry = false;
    private int longFileMode = 0;
    private final OutputStream out;
    private ArArchiveEntry prevEntry;

    public ArArchiveOutputStream(OutputStream pOut) {
        this.out = pOut;
    }

    public void setLongFileMode(int longFileMode) {
        this.longFileMode = longFileMode;
    }

    private long writeArchiveHeader() throws IOException {
        byte[] header = ArchiveUtils.toAsciiBytes(ArArchiveEntry.HEADER);
        this.out.write(header);
        return (long) header.length;
    }

    public void closeArchiveEntry() throws IOException {
        if (this.finished) {
            throw new IOException("Stream has already been finished");
        }
        if (this.prevEntry != null) {
            if (this.haveUnclosedEntry) {
                if (this.entryOffset % 2 != 0) {
                    this.out.write(10);
                }
                this.haveUnclosedEntry = false;
                return;
            }
        }
        throw new IOException("No current entry to close");
    }

    public void putArchiveEntry(ArchiveEntry pEntry) throws IOException {
        if (this.finished) {
            throw new IOException("Stream has already been finished");
        }
        ArArchiveEntry pArEntry = (ArArchiveEntry) pEntry;
        if (this.prevEntry == null) {
            writeArchiveHeader();
        } else if (this.prevEntry.getLength() != this.entryOffset) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("length does not match entry (");
            stringBuilder.append(this.prevEntry.getLength());
            stringBuilder.append(" != ");
            stringBuilder.append(this.entryOffset);
            throw new IOException(stringBuilder.toString());
        } else if (this.haveUnclosedEntry) {
            closeArchiveEntry();
        }
        this.prevEntry = pArEntry;
        writeEntryHeader(pArEntry);
        this.entryOffset = 0;
        this.haveUnclosedEntry = true;
    }

    private long fill(long pOffset, long pNewOffset, char pFill) throws IOException {
        long diff = pNewOffset - pOffset;
        if (diff > 0) {
            for (int i = 0; ((long) i) < diff; i++) {
                write(pFill);
            }
        }
        return pNewOffset;
    }

    private long write(String data) throws IOException {
        byte[] bytes = data.getBytes("ascii");
        write(bytes);
        return (long) bytes.length;
    }

    private long writeEntryHeader(ArArchiveEntry pEntry) throws IOException {
        boolean mustAppendName = false;
        String n = pEntry.getName();
        if (this.longFileMode != 0 || n.length() <= 16) {
            long offset;
            if (1 != r6.longFileMode || (n.length() <= 16 && n.indexOf(FormatHelper.SPACE) <= -1)) {
                offset = 0 + write(n);
            } else {
                mustAppendName = true;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("#1/");
                stringBuilder.append(String.valueOf(n.length()));
                offset = 0 + write(stringBuilder.toString());
            }
            boolean mustAppendName2 = mustAppendName;
            long offset2 = fill(offset, 16, ' ');
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("");
            stringBuilder2.append(pEntry.getLastModified());
            String m = stringBuilder2.toString();
            if (m.length() > 12) {
                throw new IOException("modified too long");
            }
            offset2 = fill(offset2 + write(m), 28, ' ');
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append("");
            stringBuilder2.append(pEntry.getUserId());
            String u = stringBuilder2.toString();
            if (u.length() > 6) {
                throw new IOException("userid too long");
            }
            offset2 = fill(offset2 + write(u), 34, ' ');
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append("");
            stringBuilder2.append(pEntry.getGroupId());
            String g = stringBuilder2.toString();
            if (g.length() > 6) {
                throw new IOException("groupid too long");
            }
            offset2 = fill(offset2 + write(g), 40, ' ');
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append("");
            stringBuilder2.append(Integer.toString(pEntry.getMode(), 8));
            String fm = stringBuilder2.toString();
            if (fm.length() > 8) {
                throw new IOException("filemode too long");
            }
            offset2 = fill(offset2 + write(fm), 48, ' ');
            String s = String.valueOf(pEntry.getLength() + ((long) (mustAppendName2 ? n.length() : 0)));
            if (s.length() > 10) {
                throw new IOException("size too long");
            }
            long offset3 = fill(offset2 + write(s), 58, ' ') + write(ArArchiveEntry.TRAILER);
            if (mustAppendName2) {
                return offset3 + write(n);
            }
            return offset3;
        }
        StringBuilder stringBuilder3 = new StringBuilder();
        stringBuilder3.append("filename too long, > 16 chars: ");
        stringBuilder3.append(n);
        throw new IOException(stringBuilder3.toString());
    }

    public void write(byte[] b, int off, int len) throws IOException {
        this.out.write(b, off, len);
        count(len);
        this.entryOffset += (long) len;
    }

    public void close() throws IOException {
        if (!this.finished) {
            finish();
        }
        this.out.close();
        this.prevEntry = null;
    }

    public ArchiveEntry createArchiveEntry(File inputFile, String entryName) throws IOException {
        if (!this.finished) {
            return new ArArchiveEntry(inputFile, entryName);
        }
        throw new IOException("Stream has already been finished");
    }

    public void finish() throws IOException {
        if (this.haveUnclosedEntry) {
            throw new IOException("This archive contains unclosed entries.");
        } else if (this.finished) {
            throw new IOException("This archive has already been finished");
        } else {
            this.finished = true;
        }
    }
}
