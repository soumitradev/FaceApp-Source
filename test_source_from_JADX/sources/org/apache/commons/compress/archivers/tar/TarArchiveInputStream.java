package org.apache.commons.compress.archivers.tar;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipEncoding;
import org.apache.commons.compress.archivers.zip.ZipEncodingHelper;
import org.apache.commons.compress.utils.ArchiveUtils;
import org.apache.commons.compress.utils.IOUtils;
import org.catrobat.catroid.transfers.MediaDownloadService;

public class TarArchiveInputStream extends ArchiveInputStream {
    private static final int SMALL_BUFFER_SIZE = 256;
    private final byte[] SMALL_BUF;
    private final int blockSize;
    private TarArchiveEntry currEntry;
    private final ZipEncoding encoding;
    private long entryOffset;
    private long entrySize;
    private boolean hasHitEOF;
    private final InputStream is;
    private final int recordSize;

    public TarArchiveInputStream(InputStream is) {
        this(is, 10240, 512);
    }

    public TarArchiveInputStream(InputStream is, String encoding) {
        this(is, 10240, 512, encoding);
    }

    public TarArchiveInputStream(InputStream is, int blockSize) {
        this(is, blockSize, 512);
    }

    public TarArchiveInputStream(InputStream is, int blockSize, String encoding) {
        this(is, blockSize, 512, encoding);
    }

    public TarArchiveInputStream(InputStream is, int blockSize, int recordSize) {
        this(is, blockSize, recordSize, null);
    }

    public TarArchiveInputStream(InputStream is, int blockSize, int recordSize, String encoding) {
        this.SMALL_BUF = new byte[256];
        this.is = is;
        this.hasHitEOF = false;
        this.encoding = ZipEncodingHelper.getZipEncoding(encoding);
        this.recordSize = recordSize;
        this.blockSize = blockSize;
    }

    public void close() throws IOException {
        this.is.close();
    }

    public int getRecordSize() {
        return this.recordSize;
    }

    public int available() throws IOException {
        if (this.entrySize - this.entryOffset > 2147483647L) {
            return Integer.MAX_VALUE;
        }
        return (int) (this.entrySize - this.entryOffset);
    }

    public long skip(long n) throws IOException {
        if (n <= 0) {
            return 0;
        }
        long skipped = this.is.skip(Math.min(n, this.entrySize - this.entryOffset));
        count(skipped);
        this.entryOffset += skipped;
        return skipped;
    }

    public boolean markSupported() {
        return false;
    }

    public void mark(int markLimit) {
    }

    public synchronized void reset() {
    }

    public TarArchiveEntry getNextTarEntry() throws IOException {
        if (this.hasHitEOF) {
            return null;
        }
        if (this.currEntry != null) {
            IOUtils.skip(this, Long.MAX_VALUE);
            skipRecordPadding();
        }
        byte[] headerBuf = getRecord();
        if (headerBuf == null) {
            this.currEntry = null;
            return null;
        }
        try {
            byte[] longLinkData;
            this.currEntry = new TarArchiveEntry(headerBuf, this.encoding);
            this.entryOffset = 0;
            this.entrySize = this.currEntry.getSize();
            if (this.currEntry.isGNULongLinkEntry()) {
                longLinkData = getLongNameData();
                if (longLinkData == null) {
                    return null;
                }
                this.currEntry.setLinkName(this.encoding.decode(longLinkData));
            }
            if (this.currEntry.isGNULongNameEntry()) {
                longLinkData = getLongNameData();
                if (longLinkData == null) {
                    return null;
                }
                this.currEntry.setName(this.encoding.decode(longLinkData));
            }
            if (this.currEntry.isPaxHeader()) {
                paxHeaders();
            }
            if (this.currEntry.isGNUSparse()) {
                readGNUSparse();
            }
            this.entrySize = this.currEntry.getSize();
            return this.currEntry;
        } catch (IllegalArgumentException e) {
            IOException ioe = new IOException("Error detected parsing the header");
            ioe.initCause(e);
            throw ioe;
        }
    }

    private void skipRecordPadding() throws IOException {
        if (this.entrySize > 0 && this.entrySize % ((long) this.recordSize) != 0) {
            count(IOUtils.skip(this.is, (((long) this.recordSize) * ((this.entrySize / ((long) this.recordSize)) + 1)) - this.entrySize));
        }
    }

    protected byte[] getLongNameData() throws IOException {
        ByteArrayOutputStream longName = new ByteArrayOutputStream();
        while (true) {
            int read = read(this.SMALL_BUF);
            int length = read;
            if (read < 0) {
                break;
            }
            longName.write(this.SMALL_BUF, 0, length);
        }
        getNextEntry();
        if (this.currEntry == null) {
            return null;
        }
        byte[] longNameData = longName.toByteArray();
        length = longNameData.length;
        while (length > 0 && longNameData[length - 1] == (byte) 0) {
            length--;
        }
        if (length != longNameData.length) {
            byte[] l = new byte[length];
            System.arraycopy(longNameData, 0, l, 0, length);
            longNameData = l;
        }
        return longNameData;
    }

    private byte[] getRecord() throws IOException {
        byte[] headerBuf = readRecord();
        this.hasHitEOF = isEOFRecord(headerBuf);
        if (!this.hasHitEOF || headerBuf == null) {
            return headerBuf;
        }
        tryToConsumeSecondEOFRecord();
        consumeRemainderOfLastBlock();
        return null;
    }

    protected boolean isEOFRecord(byte[] record) {
        if (record != null) {
            if (!ArchiveUtils.isArrayZero(record, this.recordSize)) {
                return false;
            }
        }
        return true;
    }

    protected byte[] readRecord() throws IOException {
        byte[] record = new byte[this.recordSize];
        int readNow = IOUtils.readFully(this.is, record);
        count(readNow);
        if (readNow != this.recordSize) {
            return null;
        }
        return record;
    }

    private void paxHeaders() throws IOException {
        Map<String, String> headers = parsePaxHeaders(this);
        getNextEntry();
        applyPaxHeadersToCurrentEntry(headers);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    java.util.Map<java.lang.String, java.lang.String> parsePaxHeaders(java.io.InputStream r15) throws java.io.IOException {
        /*
        r14 = this;
        r0 = new java.util.HashMap;
        r0.<init>();
    L_0x0005:
        r1 = 0;
        r2 = 0;
        r3 = r1;
        r1 = 0;
    L_0x0009:
        r4 = r15.read();
        r5 = r4;
        r6 = -1;
        if (r4 == r6) goto L_0x0071;
    L_0x0011:
        r1 = r1 + 1;
        r4 = 32;
        if (r5 != r4) goto L_0x006b;
    L_0x0017:
        r4 = new java.io.ByteArrayOutputStream;
        r4.<init>();
    L_0x001c:
        r7 = r15.read();
        r5 = r7;
        if (r7 == r6) goto L_0x0071;
    L_0x0023:
        r1 = r1 + 1;
        r7 = 61;
        if (r5 != r7) goto L_0x0066;
    L_0x0029:
        r7 = "UTF-8";
        r7 = r4.toString(r7);
        r8 = r3 - r1;
        r9 = new byte[r8];
        r10 = org.apache.commons.compress.utils.IOUtils.readFully(r15, r9);
        if (r10 == r8) goto L_0x0058;
    L_0x0039:
        r2 = new java.io.IOException;
        r6 = new java.lang.StringBuilder;
        r6.<init>();
        r11 = "Failed to read Paxheader. Expected ";
        r6.append(r11);
        r6.append(r8);
        r11 = " bytes, read ";
        r6.append(r11);
        r6.append(r10);
        r6 = r6.toString();
        r2.<init>(r6);
        throw r2;
    L_0x0058:
        r11 = new java.lang.String;
        r12 = r8 + -1;
        r13 = "UTF-8";
        r11.<init>(r9, r2, r12, r13);
        r2 = r11;
        r0.put(r7, r2);
        goto L_0x0071;
    L_0x0066:
        r7 = (byte) r5;
        r4.write(r7);
        goto L_0x001c;
    L_0x006b:
        r3 = r3 * 10;
        r4 = r5 + -48;
        r3 = r3 + r4;
        goto L_0x0009;
    L_0x0071:
        if (r5 != r6) goto L_0x0075;
        return r0;
    L_0x0075:
        goto L_0x0005;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.compress.archivers.tar.TarArchiveInputStream.parsePaxHeaders(java.io.InputStream):java.util.Map<java.lang.String, java.lang.String>");
    }

    private void applyPaxHeadersToCurrentEntry(Map<String, String> headers) {
        for (Entry<String, String> ent : headers.entrySet()) {
            String key = (String) ent.getKey();
            String val = (String) ent.getValue();
            if (MediaDownloadService.MEDIA_FILE_PATH.equals(key)) {
                this.currEntry.setName(val);
            } else if ("linkpath".equals(key)) {
                this.currEntry.setLinkName(val);
            } else if ("gid".equals(key)) {
                this.currEntry.setGroupId(Integer.parseInt(val));
            } else if ("gname".equals(key)) {
                this.currEntry.setGroupName(val);
            } else if ("uid".equals(key)) {
                this.currEntry.setUserId(Integer.parseInt(val));
            } else if ("uname".equals(key)) {
                this.currEntry.setUserName(val);
            } else if ("size".equals(key)) {
                this.currEntry.setSize(Long.parseLong(val));
            } else if ("mtime".equals(key)) {
                this.currEntry.setModTime((long) (Double.parseDouble(val) * 1000.0d));
            } else if ("SCHILY.devminor".equals(key)) {
                this.currEntry.setDevMinor(Integer.parseInt(val));
            } else if ("SCHILY.devmajor".equals(key)) {
                this.currEntry.setDevMajor(Integer.parseInt(val));
            }
        }
    }

    private void readGNUSparse() throws IOException {
        if (this.currEntry.isExtended()) {
            byte[] headerBuf;
            do {
                headerBuf = getRecord();
                if (headerBuf == null) {
                    this.currEntry = null;
                    return;
                }
            } while (new TarArchiveSparseEntry(headerBuf).isExtended());
        }
    }

    public ArchiveEntry getNextEntry() throws IOException {
        return getNextTarEntry();
    }

    private void tryToConsumeSecondEOFRecord() throws IOException {
        boolean shouldReset = true;
        boolean marked = this.is.markSupported();
        if (marked) {
            this.is.mark(this.recordSize);
        }
        try {
            shouldReset = isEOFRecord(readRecord()) ^ 1;
        } finally {
            if (shouldReset && marked) {
                pushedBackBytes((long) this.recordSize);
                this.is.reset();
            }
        }
    }

    public int read(byte[] buf, int offset, int numToRead) throws IOException {
        if (!this.hasHitEOF) {
            if (this.entryOffset < this.entrySize) {
                if (this.currEntry == null) {
                    throw new IllegalStateException("No current tar entry");
                }
                numToRead = Math.min(numToRead, available());
                int totalRead = this.is.read(buf, offset, numToRead);
                if (totalRead != -1) {
                    count(totalRead);
                    this.entryOffset += (long) totalRead;
                } else if (numToRead > 0) {
                    throw new IOException("Truncated TAR archive");
                } else {
                    this.hasHitEOF = true;
                }
                return totalRead;
            }
        }
        return -1;
    }

    public boolean canReadEntryData(ArchiveEntry ae) {
        if (ae instanceof TarArchiveEntry) {
            return ((TarArchiveEntry) ae).isGNUSparse() ^ 1;
        }
        return false;
    }

    public TarArchiveEntry getCurrentEntry() {
        return this.currEntry;
    }

    protected final void setCurrentEntry(TarArchiveEntry e) {
        this.currEntry = e;
    }

    protected final boolean isAtEOF() {
        return this.hasHitEOF;
    }

    protected final void setAtEOF(boolean b) {
        this.hasHitEOF = b;
    }

    private void consumeRemainderOfLastBlock() throws IOException {
        long bytesReadOfLastBlock = getBytesRead() % ((long) this.blockSize);
        if (bytesReadOfLastBlock > 0) {
            count(IOUtils.skip(this.is, ((long) this.blockSize) - bytesReadOfLastBlock));
        }
    }

    public static boolean matches(byte[] signature, int length) {
        if (length < 265) {
            return false;
        }
        if (ArchiveUtils.matchAsciiBuffer("ustar\u0000", signature, 257, 6) && ArchiveUtils.matchAsciiBuffer(TarConstants.VERSION_POSIX, signature, 263, 2)) {
            return true;
        }
        if (ArchiveUtils.matchAsciiBuffer(TarConstants.MAGIC_GNU, signature, 257, 6) && (ArchiveUtils.matchAsciiBuffer(TarConstants.VERSION_GNU_SPACE, signature, 263, 2) || ArchiveUtils.matchAsciiBuffer(TarConstants.VERSION_GNU_ZERO, signature, 263, 2))) {
            return true;
        }
        if (ArchiveUtils.matchAsciiBuffer("ustar\u0000", signature, 257, 6) && ArchiveUtils.matchAsciiBuffer(TarConstants.VERSION_ANT, signature, 263, 2)) {
            return true;
        }
        return false;
    }
}
