package org.apache.commons.compress.archivers.zip;

import android.support.v4.internal.view.SupportMenu;
import io.fabric.sdk.android.services.network.UrlUtils;
import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;
import java.util.zip.ZipException;
import org.apache.commons.compress.utils.IOUtils;

public class ZipFile implements Closeable {
    static final int BYTE_SHIFT = 8;
    private static final int CFD_LOCATOR_OFFSET = 16;
    private static final int CFH_LEN = 42;
    private static final long CFH_SIG = ZipLong.getValue(ZipArchiveOutputStream.CFH_SIG);
    private static final int HASH_SIZE = 509;
    private static final long LFH_OFFSET_FOR_FILENAME_LENGTH = 26;
    private static final int MAX_EOCD_SIZE = 65557;
    static final int MIN_EOCD_SIZE = 22;
    static final int NIBLET_MASK = 15;
    private static final int POS_0 = 0;
    private static final int POS_1 = 1;
    private static final int POS_2 = 2;
    private static final int POS_3 = 3;
    private static final int ZIP64_EOCDL_LENGTH = 20;
    private static final int ZIP64_EOCDL_LOCATOR_OFFSET = 8;
    private static final int ZIP64_EOCD_CFD_LOCATOR_OFFSET = 48;
    private final byte[] CFH_BUF;
    private final byte[] DWORD_BUF;
    private final Comparator<ZipArchiveEntry> OFFSET_COMPARATOR;
    private final byte[] SHORT_BUF;
    private final byte[] WORD_BUF;
    private final RandomAccessFile archive;
    private final String archiveName;
    private boolean closed;
    private final String encoding;
    private final List<ZipArchiveEntry> entries;
    private final Map<String, LinkedList<ZipArchiveEntry>> nameMap;
    private final boolean useUnicodeExtraFields;
    private final ZipEncoding zipEncoding;

    /* renamed from: org.apache.commons.compress.archivers.zip.ZipFile$2 */
    class C17372 implements Comparator<ZipArchiveEntry> {
        C17372() {
        }

        public int compare(ZipArchiveEntry e1, ZipArchiveEntry e2) {
            int i = 0;
            if (e1 == e2) {
                return 0;
            }
            Entry ent2 = null;
            Entry ent1 = e1 instanceof Entry ? (Entry) e1 : null;
            if (e2 instanceof Entry) {
                ent2 = (Entry) e2;
            }
            if (ent1 == null) {
                return 1;
            }
            if (ent2 == null) {
                return -1;
            }
            long val = ent1.getOffsetEntry().headerOffset - ent2.getOffsetEntry().headerOffset;
            if (val != 0) {
                i = val < 0 ? -1 : 1;
            }
            return i;
        }
    }

    private class BoundedInputStream extends InputStream {
        private boolean addDummyByte = null;
        private long loc;
        private long remaining;

        BoundedInputStream(long start, long remaining) {
            this.remaining = remaining;
            this.loc = start;
        }

        public int read() throws IOException {
            long j = this.remaining;
            this.remaining = j - 1;
            if (j > 0) {
                int read;
                synchronized (ZipFile.this.archive) {
                    RandomAccessFile access$600 = ZipFile.this.archive;
                    long j2 = this.loc;
                    this.loc = j2 + 1;
                    access$600.seek(j2);
                    read = ZipFile.this.archive.read();
                }
                return read;
            } else if (!this.addDummyByte) {
                return -1;
            } else {
                this.addDummyByte = false;
                return 0;
            }
        }

        public int read(byte[] b, int off, int len) throws IOException {
            if (this.remaining <= 0) {
                if (!this.addDummyByte) {
                    return -1;
                }
                this.addDummyByte = false;
                b[off] = (byte) 0;
                return 1;
            } else if (len <= 0) {
                return 0;
            } else {
                int ret;
                if (((long) len) > this.remaining) {
                    len = (int) this.remaining;
                }
                synchronized (ZipFile.this.archive) {
                    ZipFile.this.archive.seek(this.loc);
                    ret = ZipFile.this.archive.read(b, off, len);
                }
                if (ret > 0) {
                    this.loc += (long) ret;
                    this.remaining -= (long) ret;
                }
                return ret;
            }
        }

        void addDummy() {
            this.addDummyByte = true;
        }
    }

    private static final class NameAndComment {
        private final byte[] comment;
        private final byte[] name;

        private NameAndComment(byte[] name, byte[] comment) {
            this.name = name;
            this.comment = comment;
        }
    }

    private static final class OffsetEntry {
        private long dataOffset;
        private long headerOffset;

        private OffsetEntry() {
            this.headerOffset = -1;
            this.dataOffset = -1;
        }
    }

    private static class Entry extends ZipArchiveEntry {
        private final OffsetEntry offsetEntry;

        Entry(OffsetEntry offset) {
            this.offsetEntry = offset;
        }

        OffsetEntry getOffsetEntry() {
            return this.offsetEntry;
        }

        public int hashCode() {
            return (super.hashCode() * 3) + ((int) (this.offsetEntry.headerOffset % 2147483647L));
        }

        public boolean equals(Object other) {
            boolean z = false;
            if (!super.equals(other)) {
                return false;
            }
            Entry otherEntry = (Entry) other;
            if (this.offsetEntry.headerOffset == otherEntry.offsetEntry.headerOffset && this.offsetEntry.dataOffset == otherEntry.offsetEntry.dataOffset) {
                z = true;
            }
            return z;
        }
    }

    public ZipFile(File f) throws IOException {
        this(f, UrlUtils.UTF8);
    }

    public ZipFile(String name) throws IOException {
        this(new File(name), UrlUtils.UTF8);
    }

    public ZipFile(String name, String encoding) throws IOException {
        this(new File(name), encoding, true);
    }

    public ZipFile(File f, String encoding) throws IOException {
        this(f, encoding, true);
    }

    public ZipFile(File f, String encoding, boolean useUnicodeExtraFields) throws IOException {
        this.entries = new LinkedList();
        this.nameMap = new HashMap(HASH_SIZE);
        this.DWORD_BUF = new byte[8];
        this.WORD_BUF = new byte[4];
        this.CFH_BUF = new byte[42];
        this.SHORT_BUF = new byte[2];
        this.OFFSET_COMPARATOR = new C17372();
        this.archiveName = f.getAbsolutePath();
        this.encoding = encoding;
        this.zipEncoding = ZipEncodingHelper.getZipEncoding(encoding);
        this.useUnicodeExtraFields = useUnicodeExtraFields;
        this.archive = new RandomAccessFile(f, "r");
        boolean success = false;
        try {
            resolveLocalFileHeaderData(populateFromCentralDirectory());
            success = true;
        } finally {
            if (!success) {
                this.closed = true;
                IOUtils.closeQuietly(this.archive);
            }
        }
    }

    public String getEncoding() {
        return this.encoding;
    }

    public void close() throws IOException {
        this.closed = true;
        this.archive.close();
    }

    public static void closeQuietly(ZipFile zipfile) {
        IOUtils.closeQuietly(zipfile);
    }

    public Enumeration<ZipArchiveEntry> getEntries() {
        return Collections.enumeration(this.entries);
    }

    public Enumeration<ZipArchiveEntry> getEntriesInPhysicalOrder() {
        ZipArchiveEntry[] allEntries = (ZipArchiveEntry[]) this.entries.toArray(new ZipArchiveEntry[0]);
        Arrays.sort(allEntries, this.OFFSET_COMPARATOR);
        return Collections.enumeration(Arrays.asList(allEntries));
    }

    public ZipArchiveEntry getEntry(String name) {
        LinkedList<ZipArchiveEntry> entriesOfThatName = (LinkedList) this.nameMap.get(name);
        return entriesOfThatName != null ? (ZipArchiveEntry) entriesOfThatName.getFirst() : null;
    }

    public Iterable<ZipArchiveEntry> getEntries(String name) {
        List<ZipArchiveEntry> entriesOfThatName = (List) this.nameMap.get(name);
        return entriesOfThatName != null ? entriesOfThatName : Collections.emptyList();
    }

    public Iterable<ZipArchiveEntry> getEntriesInPhysicalOrder(String name) {
        ZipArchiveEntry[] entriesOfThatName = new ZipArchiveEntry[null];
        if (this.nameMap.containsKey(name)) {
            entriesOfThatName = (ZipArchiveEntry[]) ((LinkedList) this.nameMap.get(name)).toArray(entriesOfThatName);
            Arrays.sort(entriesOfThatName, this.OFFSET_COMPARATOR);
        }
        return Arrays.asList(entriesOfThatName);
    }

    public boolean canReadEntryData(ZipArchiveEntry ze) {
        return ZipUtil.canHandleEntryData(ze);
    }

    public InputStream getInputStream(ZipArchiveEntry ze) throws IOException, ZipException {
        if (!(ze instanceof Entry)) {
            return null;
        }
        OffsetEntry offsetEntry = ((Entry) ze).getOffsetEntry();
        ZipUtil.checkRequestedFeatures(ze);
        BoundedInputStream bis = new BoundedInputStream(offsetEntry.dataOffset, ze.getCompressedSize());
        switch (ZipMethod.getMethodByCode(ze.getMethod())) {
            case STORED:
                return bis;
            case UNSHRINKING:
                return new UnshrinkingInputStream(bis);
            case IMPLODING:
                return new ExplodingInputStream(ze.getGeneralPurposeBit().getSlidingDictionarySize(), ze.getGeneralPurposeBit().getNumberOfShannonFanoTrees(), new BufferedInputStream(bis));
            case DEFLATED:
                bis.addDummy();
                final Inflater inflater = new Inflater(true);
                return new InflaterInputStream(bis, inflater) {
                    public void close() throws IOException {
                        super.close();
                        inflater.end();
                    }
                };
            default:
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Found unsupported compression method ");
                stringBuilder.append(ze.getMethod());
                throw new ZipException(stringBuilder.toString());
        }
    }

    public String getUnixSymlink(ZipArchiveEntry entry) throws IOException {
        InputStream in = null;
        if (entry == null || !entry.isUnixSymlink()) {
            return null;
        }
        try {
            in = getInputStream(entry);
            String decode = this.zipEncoding.decode(IOUtils.toByteArray(in));
            return decode;
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }

    protected void finalize() throws Throwable {
        try {
            if (!this.closed) {
                PrintStream printStream = System.err;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Cleaning up unclosed ZipFile for archive ");
                stringBuilder.append(this.archiveName);
                printStream.println(stringBuilder.toString());
                close();
            }
            super.finalize();
        } catch (Throwable th) {
            super.finalize();
        }
    }

    private Map<ZipArchiveEntry, NameAndComment> populateFromCentralDirectory() throws IOException {
        HashMap<ZipArchiveEntry, NameAndComment> noUTF8Flag = new HashMap();
        positionAtCentralDirectory();
        this.archive.readFully(this.WORD_BUF);
        long sig = ZipLong.getValue(this.WORD_BUF);
        if (sig == CFH_SIG || !startsWithLocalFileHeader()) {
            while (sig == CFH_SIG) {
                readCentralDirectoryEntry(noUTF8Flag);
                this.archive.readFully(this.WORD_BUF);
                sig = ZipLong.getValue(this.WORD_BUF);
            }
            return noUTF8Flag;
        }
        throw new IOException("central directory is empty, can't expand corrupt archive.");
    }

    private void readCentralDirectoryEntry(Map<ZipArchiveEntry, NameAndComment> noUTF8Flag) throws IOException {
        this.archive.readFully(this.CFH_BUF);
        OffsetEntry offset = new OffsetEntry();
        Entry ze = new Entry(offset);
        int versionMadeBy = ZipShort.getValue(this.CFH_BUF, 0);
        int off = 0 + 2;
        ze.setPlatform((versionMadeBy >> 8) & 15);
        off += 2;
        GeneralPurposeBit gpFlag = GeneralPurposeBit.parse(this.CFH_BUF, off);
        boolean hasUTF8Flag = gpFlag.usesUTF8ForNames();
        ZipEncoding entryEncoding = hasUTF8Flag ? ZipEncodingHelper.UTF8_ZIP_ENCODING : r0.zipEncoding;
        ze.setGeneralPurposeBit(gpFlag);
        off += 2;
        ze.setMethod(ZipShort.getValue(r0.CFH_BUF, off));
        off += 2;
        ze.setTime(ZipUtil.dosToJavaTime(ZipLong.getValue(r0.CFH_BUF, off)));
        off += 4;
        ze.setCrc(ZipLong.getValue(r0.CFH_BUF, off));
        off += 4;
        ze.setCompressedSize(ZipLong.getValue(r0.CFH_BUF, off));
        off += 4;
        ze.setSize(ZipLong.getValue(r0.CFH_BUF, off));
        off += 4;
        int fileNameLen = ZipShort.getValue(r0.CFH_BUF, off);
        off += 2;
        int extraLen = ZipShort.getValue(r0.CFH_BUF, off);
        off += 2;
        int commentLen = ZipShort.getValue(r0.CFH_BUF, off);
        off += 2;
        int diskStart = ZipShort.getValue(r0.CFH_BUF, off);
        off += 2;
        ze.setInternalAttributes(ZipShort.getValue(r0.CFH_BUF, off));
        off += 2;
        ze.setExternalAttributes(ZipLong.getValue(r0.CFH_BUF, off));
        off += 4;
        byte[] fileName = new byte[fileNameLen];
        r0.archive.readFully(fileName);
        ze.setName(entryEncoding.decode(fileName), fileName);
        Entry ze2 = ze;
        offset.headerOffset = ZipLong.getValue(r0.CFH_BUF, off);
        ze = ze2;
        r0.entries.add(ze);
        byte[] cdExtraData = new byte[extraLen];
        r0.archive.readFully(cdExtraData);
        ze.setCentralDirectoryExtra(cdExtraData);
        setSizesAndOffsetFromZip64Extra(ze, offset, diskStart);
        byte[] comment = new byte[commentLen];
        r0.archive.readFully(comment);
        ze.setComment(entryEncoding.decode(comment));
        if (hasUTF8Flag || !r0.useUnicodeExtraFields) {
            Map<ZipArchiveEntry, NameAndComment> map = noUTF8Flag;
        } else {
            noUTF8Flag.put(ze, new NameAndComment(fileName, comment));
        }
    }

    private void setSizesAndOffsetFromZip64Extra(ZipArchiveEntry ze, OffsetEntry offset, int diskStart) throws IOException {
        Zip64ExtendedInformationExtraField z64 = (Zip64ExtendedInformationExtraField) ze.getExtraField(Zip64ExtendedInformationExtraField.HEADER_ID);
        if (z64 != null) {
            boolean z = false;
            boolean hasUncompressedSize = ze.getSize() == 4294967295L;
            boolean hasCompressedSize = ze.getCompressedSize() == 4294967295L;
            boolean hasRelativeHeaderOffset = offset.headerOffset == 4294967295L;
            if (diskStart == SupportMenu.USER_MASK) {
                z = true;
            }
            z64.reparseCentralDirectoryData(hasUncompressedSize, hasCompressedSize, hasRelativeHeaderOffset, z);
            if (hasUncompressedSize) {
                ze.setSize(z64.getSize().getLongValue());
            } else if (hasCompressedSize) {
                z64.setSize(new ZipEightByteInteger(ze.getSize()));
            }
            if (hasCompressedSize) {
                ze.setCompressedSize(z64.getCompressedSize().getLongValue());
            } else if (hasUncompressedSize) {
                z64.setCompressedSize(new ZipEightByteInteger(ze.getCompressedSize()));
            }
            if (hasRelativeHeaderOffset) {
                offset.headerOffset = z64.getRelativeHeaderOffset().getLongValue();
            }
        }
    }

    private void positionAtCentralDirectory() throws IOException {
        positionAtEndOfCentralDirectoryRecord();
        boolean found = false;
        boolean searchedForZip64EOCD = this.archive.getFilePointer() > 20;
        if (searchedForZip64EOCD) {
            this.archive.seek(this.archive.getFilePointer() - 20);
            this.archive.readFully(this.WORD_BUF);
            found = Arrays.equals(ZipArchiveOutputStream.ZIP64_EOCD_LOC_SIG, this.WORD_BUF);
        }
        if (found) {
            positionAtCentralDirectory64();
            return;
        }
        if (searchedForZip64EOCD) {
            skipBytes(16);
        }
        positionAtCentralDirectory32();
    }

    private void positionAtCentralDirectory64() throws IOException {
        skipBytes(4);
        this.archive.readFully(this.DWORD_BUF);
        this.archive.seek(ZipEightByteInteger.getLongValue(this.DWORD_BUF));
        this.archive.readFully(this.WORD_BUF);
        if (Arrays.equals(this.WORD_BUF, ZipArchiveOutputStream.ZIP64_EOCD_SIG)) {
            skipBytes(44);
            this.archive.readFully(this.DWORD_BUF);
            this.archive.seek(ZipEightByteInteger.getLongValue(this.DWORD_BUF));
            return;
        }
        throw new ZipException("archive's ZIP64 end of central directory locator is corrupt.");
    }

    private void positionAtCentralDirectory32() throws IOException {
        skipBytes(16);
        this.archive.readFully(this.WORD_BUF);
        this.archive.seek(ZipLong.getValue(this.WORD_BUF));
    }

    private void positionAtEndOfCentralDirectoryRecord() throws IOException {
        if (!tryToLocateSignature(22, 65557, ZipArchiveOutputStream.EOCD_SIG)) {
            throw new ZipException("archive is not a ZIP archive");
        }
    }

    private boolean tryToLocateSignature(long minDistanceFromEnd, long maxDistanceFromEnd, byte[] sig) throws IOException {
        boolean found = false;
        long off = this.archive.length() - minDistanceFromEnd;
        long stopSearching = Math.max(0, this.archive.length() - maxDistanceFromEnd);
        if (off >= 0) {
            while (off >= stopSearching) {
                this.archive.seek(off);
                byte curr = this.archive.read();
                if (curr != (byte) -1) {
                    if (curr == sig[0] && this.archive.read() == sig[1] && this.archive.read() == sig[2] && this.archive.read() == sig[3]) {
                        found = true;
                        break;
                    }
                    off--;
                } else {
                    break;
                }
            }
        }
        if (found) {
            this.archive.seek(off);
        }
        return found;
    }

    private void skipBytes(int count) throws IOException {
        int totalSkipped = 0;
        while (totalSkipped < count) {
            int skippedNow = this.archive.skipBytes(count - totalSkipped);
            if (skippedNow <= 0) {
                throw new EOFException();
            }
            totalSkipped += skippedNow;
        }
    }

    private void resolveLocalFileHeaderData(Map<ZipArchiveEntry, NameAndComment> entriesWithoutUTF8Flag) throws IOException {
        Map<ZipArchiveEntry, NameAndComment> map = entriesWithoutUTF8Flag;
        for (ZipArchiveEntry zipArchiveEntry : this.entries) {
            Entry ze = (Entry) zipArchiveEntry;
            OffsetEntry offsetEntry = ze.getOffsetEntry();
            long offset = offsetEntry.headerOffset;
            r0.archive.seek(offset + LFH_OFFSET_FOR_FILENAME_LENGTH);
            r0.archive.readFully(r0.SHORT_BUF);
            int fileNameLen = ZipShort.getValue(r0.SHORT_BUF);
            r0.archive.readFully(r0.SHORT_BUF);
            int extraFieldLen = ZipShort.getValue(r0.SHORT_BUF);
            int lenToSkip = fileNameLen;
            while (lenToSkip > 0) {
                int skipped = r0.archive.skipBytes(lenToSkip);
                if (skipped <= 0) {
                    throw new IOException("failed to skip file name in local file header");
                }
                lenToSkip -= skipped;
            }
            byte[] localExtraData = new byte[extraFieldLen];
            r0.archive.readFully(localExtraData);
            ze.setExtra(localExtraData);
            offsetEntry.dataOffset = ((((offset + LFH_OFFSET_FOR_FILENAME_LENGTH) + 2) + 2) + ((long) fileNameLen)) + ((long) extraFieldLen);
            if (map.containsKey(ze)) {
                NameAndComment nc = (NameAndComment) map.get(ze);
                ZipUtil.setNameAndCommentFromExtraFields(ze, nc.name, nc.comment);
            }
            String name = ze.getName();
            LinkedList<ZipArchiveEntry> entriesOfThatName = (LinkedList) r0.nameMap.get(name);
            if (entriesOfThatName == null) {
                entriesOfThatName = new LinkedList();
                r0.nameMap.put(name, entriesOfThatName);
            }
            entriesOfThatName.addLast(ze);
        }
    }

    private boolean startsWithLocalFileHeader() throws IOException {
        this.archive.seek(0);
        this.archive.readFully(this.WORD_BUF);
        return Arrays.equals(this.WORD_BUF, ZipArchiveOutputStream.LFH_SIG);
    }
}
