package org.apache.commons.compress.archivers.tar;

import com.pdrogfer.mididroid.event.meta.MetaEvent;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import name.antonsmirnov.firmata.FormatHelper;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.zip.ZipEncoding;
import org.apache.commons.compress.archivers.zip.ZipEncodingHelper;
import org.apache.commons.compress.utils.CountingOutputStream;
import org.catrobat.catroid.transfers.MediaDownloadService;

public class TarArchiveOutputStream extends ArchiveOutputStream {
    private static final ZipEncoding ASCII = ZipEncodingHelper.getZipEncoding("ASCII");
    public static final int BIGNUMBER_ERROR = 0;
    public static final int BIGNUMBER_POSIX = 2;
    public static final int BIGNUMBER_STAR = 1;
    public static final int LONGFILE_ERROR = 0;
    public static final int LONGFILE_GNU = 2;
    public static final int LONGFILE_POSIX = 3;
    public static final int LONGFILE_TRUNCATE = 1;
    private boolean addPaxHeadersForNonAsciiNames;
    private final byte[] assemBuf;
    private int assemLen;
    private int bigNumberMode;
    private boolean closed;
    private long currBytes;
    private String currName;
    private long currSize;
    private final ZipEncoding encoding;
    private boolean finished;
    private boolean haveUnclosedEntry;
    private int longFileMode;
    private final OutputStream out;
    private final byte[] recordBuf;
    private final int recordSize;
    private final int recordsPerBlock;
    private int recordsWritten;

    public TarArchiveOutputStream(OutputStream os) {
        this(os, 10240, 512);
    }

    public TarArchiveOutputStream(OutputStream os, String encoding) {
        this(os, 10240, 512, encoding);
    }

    public TarArchiveOutputStream(OutputStream os, int blockSize) {
        this(os, blockSize, 512);
    }

    public TarArchiveOutputStream(OutputStream os, int blockSize, String encoding) {
        this(os, blockSize, 512, encoding);
    }

    public TarArchiveOutputStream(OutputStream os, int blockSize, int recordSize) {
        this(os, blockSize, recordSize, null);
    }

    public TarArchiveOutputStream(OutputStream os, int blockSize, int recordSize, String encoding) {
        this.longFileMode = 0;
        this.bigNumberMode = 0;
        this.closed = false;
        this.haveUnclosedEntry = false;
        this.finished = false;
        this.addPaxHeadersForNonAsciiNames = false;
        this.out = new CountingOutputStream(os);
        this.encoding = ZipEncodingHelper.getZipEncoding(encoding);
        this.assemLen = 0;
        this.assemBuf = new byte[recordSize];
        this.recordBuf = new byte[recordSize];
        this.recordSize = recordSize;
        this.recordsPerBlock = blockSize / recordSize;
    }

    public void setLongFileMode(int longFileMode) {
        this.longFileMode = longFileMode;
    }

    public void setBigNumberMode(int bigNumberMode) {
        this.bigNumberMode = bigNumberMode;
    }

    public void setAddPaxHeadersForNonAsciiNames(boolean b) {
        this.addPaxHeadersForNonAsciiNames = b;
    }

    @Deprecated
    public int getCount() {
        return (int) getBytesWritten();
    }

    public long getBytesWritten() {
        return ((CountingOutputStream) this.out).getBytesWritten();
    }

    public void finish() throws IOException {
        if (this.finished) {
            throw new IOException("This archive has already been finished");
        } else if (this.haveUnclosedEntry) {
            throw new IOException("This archives contains unclosed entries.");
        } else {
            writeEOFRecord();
            writeEOFRecord();
            padAsNeeded();
            this.out.flush();
            this.finished = true;
        }
    }

    public void close() throws IOException {
        if (!this.finished) {
            finish();
        }
        if (!this.closed) {
            this.out.close();
            this.closed = true;
        }
    }

    public int getRecordSize() {
        return this.recordSize;
    }

    public void putArchiveEntry(ArchiveEntry archiveEntry) throws IOException {
        if (this.finished) {
            throw new IOException("Stream has already been finished");
        }
        boolean paxHeaderContainsLinkPath;
        byte[] bArr;
        ZipEncoding zipEncoding;
        TarArchiveEntry entry = (TarArchiveEntry) archiveEntry;
        Map<String, String> paxHeaders = new HashMap();
        String entryName = entry.getName();
        boolean paxHeaderContainsPath = handleLongName(entry, entryName, paxHeaders, MediaDownloadService.MEDIA_FILE_PATH, (byte) 76, "file name");
        String linkName = entry.getLinkName();
        boolean z = false;
        if (linkName != null && linkName.length() > 0) {
            if (handleLongName(entry, linkName, paxHeaders, "linkpath", (byte) 75, "link name")) {
                paxHeaderContainsLinkPath = true;
                if (this.bigNumberMode == 2) {
                    addPaxHeadersForBigNumbers(paxHeaders, entry);
                } else if (this.bigNumberMode != 1) {
                    failForBigNumbers(entry);
                }
                if (!(!this.addPaxHeadersForNonAsciiNames || paxHeaderContainsPath || ASCII.canEncode(entryName))) {
                    paxHeaders.put(MediaDownloadService.MEDIA_FILE_PATH, entryName);
                }
                if (this.addPaxHeadersForNonAsciiNames && !paxHeaderContainsLinkPath && ((entry.isLink() || entry.isSymbolicLink()) && !ASCII.canEncode(linkName))) {
                    paxHeaders.put("linkpath", linkName);
                }
                if (paxHeaders.size() > 0) {
                    writePaxHeaders(entry, entryName, paxHeaders);
                }
                bArr = this.recordBuf;
                zipEncoding = this.encoding;
                if (this.bigNumberMode == 1) {
                    z = true;
                }
                entry.writeEntryHeader(bArr, zipEncoding, z);
                writeRecord(this.recordBuf);
                this.currBytes = 0;
                if (entry.isDirectory()) {
                    this.currSize = entry.getSize();
                } else {
                    this.currSize = 0;
                }
                this.currName = entryName;
                this.haveUnclosedEntry = true;
            }
        }
        paxHeaderContainsLinkPath = false;
        if (this.bigNumberMode == 2) {
            addPaxHeadersForBigNumbers(paxHeaders, entry);
        } else if (this.bigNumberMode != 1) {
            failForBigNumbers(entry);
        }
        paxHeaders.put(MediaDownloadService.MEDIA_FILE_PATH, entryName);
        paxHeaders.put("linkpath", linkName);
        if (paxHeaders.size() > 0) {
            writePaxHeaders(entry, entryName, paxHeaders);
        }
        bArr = this.recordBuf;
        zipEncoding = this.encoding;
        if (this.bigNumberMode == 1) {
            z = true;
        }
        entry.writeEntryHeader(bArr, zipEncoding, z);
        writeRecord(this.recordBuf);
        this.currBytes = 0;
        if (entry.isDirectory()) {
            this.currSize = entry.getSize();
        } else {
            this.currSize = 0;
        }
        this.currName = entryName;
        this.haveUnclosedEntry = true;
    }

    public void closeArchiveEntry() throws IOException {
        if (this.finished) {
            throw new IOException("Stream has already been finished");
        } else if (this.haveUnclosedEntry) {
            if (this.assemLen > 0) {
                for (int i = this.assemLen; i < this.assemBuf.length; i++) {
                    this.assemBuf[i] = (byte) 0;
                }
                writeRecord(this.assemBuf);
                this.currBytes += (long) this.assemLen;
                this.assemLen = 0;
            }
            if (this.currBytes < this.currSize) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("entry '");
                stringBuilder.append(this.currName);
                stringBuilder.append("' closed at '");
                stringBuilder.append(this.currBytes);
                stringBuilder.append("' before the '");
                stringBuilder.append(this.currSize);
                stringBuilder.append("' bytes specified in the header were written");
                throw new IOException(stringBuilder.toString());
            }
            this.haveUnclosedEntry = false;
        } else {
            throw new IOException("No current entry to close");
        }
    }

    public void write(byte[] wBuf, int wOffset, int numToWrite) throws IOException {
        if (!this.haveUnclosedEntry) {
            throw new IllegalStateException("No current tar entry");
        } else if (this.currBytes + ((long) numToWrite) > this.currSize) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("request to write '");
            stringBuilder.append(numToWrite);
            stringBuilder.append("' bytes exceeds size in header of '");
            stringBuilder.append(this.currSize);
            stringBuilder.append("' bytes for entry '");
            stringBuilder.append(this.currName);
            stringBuilder.append(FormatHelper.QUOTE);
            throw new IOException(stringBuilder.toString());
        } else {
            int aLen;
            if (this.assemLen > 0) {
                if (this.assemLen + numToWrite >= this.recordBuf.length) {
                    aLen = this.recordBuf.length - this.assemLen;
                    System.arraycopy(this.assemBuf, 0, this.recordBuf, 0, this.assemLen);
                    System.arraycopy(wBuf, wOffset, this.recordBuf, this.assemLen, aLen);
                    writeRecord(this.recordBuf);
                    this.currBytes += (long) this.recordBuf.length;
                    wOffset += aLen;
                    numToWrite -= aLen;
                    this.assemLen = 0;
                } else {
                    System.arraycopy(wBuf, wOffset, this.assemBuf, this.assemLen, numToWrite);
                    wOffset += numToWrite;
                    this.assemLen += numToWrite;
                    numToWrite = 0;
                }
            }
            while (numToWrite > 0) {
                if (numToWrite < this.recordBuf.length) {
                    System.arraycopy(wBuf, wOffset, this.assemBuf, this.assemLen, numToWrite);
                    this.assemLen += numToWrite;
                    return;
                }
                writeRecord(wBuf, wOffset);
                aLen = this.recordBuf.length;
                this.currBytes += (long) aLen;
                numToWrite -= aLen;
                wOffset += aLen;
            }
        }
    }

    void writePaxHeaders(TarArchiveEntry entry, String entryName, Map<String, String> headers) throws IOException {
        String name = new StringBuilder();
        name.append("./PaxHeaders.X/");
        name.append(stripTo7Bits(entryName));
        name = name.toString();
        if (name.length() >= 100) {
            name = name.substring(0, 99);
        }
        TarArchiveEntry pex = new TarArchiveEntry(name, (byte) 120);
        transferModTime(entry, pex);
        StringWriter w = new StringWriter();
        for (Entry<String, String> h : headers.entrySet()) {
            String key = (String) h.getKey();
            String value = (String) h.getValue();
            int len = ((key.length() + value.length()) + 3) + 2;
            String line = new StringBuilder();
            line.append(len);
            line.append(FormatHelper.SPACE);
            line.append(key);
            line.append("=");
            line.append(value);
            line.append("\n");
            line = line.toString();
            int actualLength = line.getBytes("UTF-8").length;
            while (len != actualLength) {
                len = actualLength;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(len);
                stringBuilder.append(FormatHelper.SPACE);
                stringBuilder.append(key);
                stringBuilder.append("=");
                stringBuilder.append(value);
                stringBuilder.append("\n");
                line = stringBuilder.toString();
                actualLength = line.getBytes("UTF-8").length;
            }
            w.write(line);
        }
        byte[] data = w.toString().getBytes("UTF-8");
        pex.setSize((long) data.length);
        putArchiveEntry(pex);
        write(data);
        closeArchiveEntry();
    }

    private String stripTo7Bits(String name) {
        int length = name.length();
        StringBuilder result = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            char stripped = (char) (name.charAt(i) & MetaEvent.SEQUENCER_SPECIFIC);
            if (shouldBeReplaced(stripped)) {
                result.append(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
            } else {
                result.append(stripped);
            }
        }
        return result.toString();
    }

    private boolean shouldBeReplaced(char c) {
        if (!(c == '\u0000' || c == '/')) {
            if (c != '\\') {
                return false;
            }
        }
        return true;
    }

    private void writeEOFRecord() throws IOException {
        Arrays.fill(this.recordBuf, (byte) 0);
        writeRecord(this.recordBuf);
    }

    public void flush() throws IOException {
        this.out.flush();
    }

    public ArchiveEntry createArchiveEntry(File inputFile, String entryName) throws IOException {
        if (!this.finished) {
            return new TarArchiveEntry(inputFile, entryName);
        }
        throw new IOException("Stream has already been finished");
    }

    private void writeRecord(byte[] record) throws IOException {
        if (record.length != this.recordSize) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("record to write has length '");
            stringBuilder.append(record.length);
            stringBuilder.append("' which is not the record size of '");
            stringBuilder.append(this.recordSize);
            stringBuilder.append(FormatHelper.QUOTE);
            throw new IOException(stringBuilder.toString());
        }
        this.out.write(record);
        this.recordsWritten++;
    }

    private void writeRecord(byte[] buf, int offset) throws IOException {
        if (this.recordSize + offset > buf.length) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("record has length '");
            stringBuilder.append(buf.length);
            stringBuilder.append("' with offset '");
            stringBuilder.append(offset);
            stringBuilder.append("' which is less than the record size of '");
            stringBuilder.append(this.recordSize);
            stringBuilder.append(FormatHelper.QUOTE);
            throw new IOException(stringBuilder.toString());
        }
        this.out.write(buf, offset, this.recordSize);
        this.recordsWritten++;
    }

    private void padAsNeeded() throws IOException {
        int start = this.recordsWritten % this.recordsPerBlock;
        if (start != 0) {
            for (int i = start; i < this.recordsPerBlock; i++) {
                writeEOFRecord();
            }
        }
    }

    private void addPaxHeadersForBigNumbers(Map<String, String> paxHeaders, TarArchiveEntry entry) {
        addPaxHeaderForBigNumber(paxHeaders, "size", entry.getSize(), TarConstants.MAXSIZE);
        Map<String, String> map = paxHeaders;
        addPaxHeaderForBigNumber(map, "gid", (long) entry.getGroupId(), TarConstants.MAXID);
        Map<String, String> map2 = paxHeaders;
        addPaxHeaderForBigNumber(map2, "mtime", entry.getModTime().getTime() / 1000, TarConstants.MAXSIZE);
        addPaxHeaderForBigNumber(map, "uid", (long) entry.getUserId(), TarConstants.MAXID);
        addPaxHeaderForBigNumber(map2, "SCHILY.devmajor", (long) entry.getDevMajor(), TarConstants.MAXID);
        addPaxHeaderForBigNumber(map, "SCHILY.devminor", (long) entry.getDevMinor(), TarConstants.MAXID);
        failForBigNumber("mode", (long) entry.getMode(), TarConstants.MAXID);
    }

    private void addPaxHeaderForBigNumber(Map<String, String> paxHeaders, String header, long value, long maxValue) {
        if (value < 0 || value > maxValue) {
            paxHeaders.put(header, String.valueOf(value));
        }
    }

    private void failForBigNumbers(TarArchiveEntry entry) {
        failForBigNumber("entry size", entry.getSize(), TarConstants.MAXSIZE);
        failForBigNumber("group id", (long) entry.getGroupId(), TarConstants.MAXID);
        failForBigNumber("last modification time", entry.getModTime().getTime() / 1000, TarConstants.MAXSIZE);
        failForBigNumber("user id", (long) entry.getUserId(), TarConstants.MAXID);
        failForBigNumber("mode", (long) entry.getMode(), TarConstants.MAXID);
        failForBigNumber("major device number", (long) entry.getDevMajor(), TarConstants.MAXID);
        failForBigNumber("minor device number", (long) entry.getDevMinor(), TarConstants.MAXID);
    }

    private void failForBigNumber(String field, long value, long maxValue) {
        if (value >= 0) {
            if (value <= maxValue) {
                return;
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(field);
        stringBuilder.append(" '");
        stringBuilder.append(value);
        stringBuilder.append("' is too big ( > ");
        stringBuilder.append(maxValue);
        stringBuilder.append(" )");
        throw new RuntimeException(stringBuilder.toString());
    }

    private boolean handleLongName(TarArchiveEntry entry, String name, Map<String, String> paxHeaders, String paxHeaderName, byte linkType, String fieldName) throws IOException {
        ByteBuffer encodedName = this.encoding.encode(name);
        int len = encodedName.limit() - encodedName.position();
        if (len >= 100) {
            if (this.longFileMode == 3) {
                paxHeaders.put(paxHeaderName, name);
                return true;
            } else if (this.longFileMode == 2) {
                TarArchiveEntry longLinkEntry = new TarArchiveEntry(TarConstants.GNU_LONGLINK, linkType);
                longLinkEntry.setSize((long) (len + 1));
                transferModTime(entry, longLinkEntry);
                putArchiveEntry(longLinkEntry);
                write(encodedName.array(), encodedName.arrayOffset(), len);
                write(0);
                closeArchiveEntry();
            } else if (this.longFileMode != 1) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(fieldName);
                stringBuilder.append(" '");
                stringBuilder.append(name);
                stringBuilder.append("' is too long ( > ");
                stringBuilder.append(100);
                stringBuilder.append(" bytes)");
                throw new RuntimeException(stringBuilder.toString());
            }
        }
        return false;
    }

    private void transferModTime(TarArchiveEntry from, TarArchiveEntry to) {
        Date fromModTime = from.getModTime();
        long fromModTimeSeconds = fromModTime.getTime() / 1000;
        if (fromModTimeSeconds < 0 || fromModTimeSeconds > TarConstants.MAXSIZE) {
            fromModTime = new Date(0);
        }
        to.setModTime(fromModTime);
    }
}
