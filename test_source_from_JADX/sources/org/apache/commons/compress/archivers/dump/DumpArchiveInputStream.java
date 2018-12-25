package org.apache.commons.compress.archivers.dump;

import android.support.v4.media.session.PlaybackStateCompat;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;
import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.dump.DumpArchiveConstants.SEGMENT_TYPE;
import org.apache.commons.compress.archivers.zip.ZipEncoding;
import org.apache.commons.compress.archivers.zip.ZipEncodingHelper;

public class DumpArchiveInputStream extends ArchiveInputStream {
    private DumpArchiveEntry active;
    private byte[] blockBuffer;
    private final ZipEncoding encoding;
    private long entryOffset;
    private long entrySize;
    private long filepos;
    private boolean hasHitEOF;
    private boolean isClosed;
    private final Map<Integer, Dirent> names;
    private final Map<Integer, DumpArchiveEntry> pending;
    private Queue<DumpArchiveEntry> queue;
    protected TapeInputStream raw;
    private final byte[] readBuf;
    private int readIdx;
    private int recordOffset;
    private DumpArchiveSummary summary;

    public DumpArchiveInputStream(InputStream is) throws ArchiveException {
        this(is, null);
    }

    public DumpArchiveInputStream(InputStream is, String encoding) throws ArchiveException {
        this.readBuf = new byte[1024];
        this.names = new HashMap();
        this.pending = new HashMap();
        this.raw = new TapeInputStream(is);
        this.hasHitEOF = false;
        this.encoding = ZipEncodingHelper.getZipEncoding(encoding);
        try {
            byte[] headerBytes = this.raw.readRecord();
            if (DumpArchiveUtil.verify(headerBytes)) {
                this.summary = new DumpArchiveSummary(headerBytes, this.encoding);
                this.raw.resetBlockSize(this.summary.getNTRec(), this.summary.isCompressed());
                this.blockBuffer = new byte[4096];
                readCLRI();
                readBITS();
                this.names.put(Integer.valueOf(2), new Dirent(2, 2, 4, "."));
                this.queue = new PriorityQueue(10, new DumpArchiveInputStream$1(this));
                return;
            }
            throw new UnrecognizedFormatException();
        } catch (IOException ex) {
            throw new ArchiveException(ex.getMessage(), ex);
        }
    }

    @Deprecated
    public int getCount() {
        return (int) getBytesRead();
    }

    public long getBytesRead() {
        return this.raw.getBytesRead();
    }

    public DumpArchiveSummary getSummary() {
        return this.summary;
    }

    private void readCLRI() throws IOException {
        byte[] buffer = this.raw.readRecord();
        if (DumpArchiveUtil.verify(buffer)) {
            this.active = DumpArchiveEntry.parse(buffer);
            if (SEGMENT_TYPE.CLRI != this.active.getHeaderType()) {
                throw new InvalidFormatException();
            } else if (this.raw.skip((long) (this.active.getHeaderCount() * 1024)) == -1) {
                throw new EOFException();
            } else {
                this.readIdx = this.active.getHeaderCount();
                return;
            }
        }
        throw new InvalidFormatException();
    }

    private void readBITS() throws IOException {
        byte[] buffer = this.raw.readRecord();
        if (DumpArchiveUtil.verify(buffer)) {
            this.active = DumpArchiveEntry.parse(buffer);
            if (SEGMENT_TYPE.BITS != this.active.getHeaderType()) {
                throw new InvalidFormatException();
            } else if (this.raw.skip((long) (this.active.getHeaderCount() * 1024)) == -1) {
                throw new EOFException();
            } else {
                this.readIdx = this.active.getHeaderCount();
                return;
            }
        }
        throw new InvalidFormatException();
    }

    public DumpArchiveEntry getNextDumpEntry() throws IOException {
        return getNextEntry();
    }

    public DumpArchiveEntry getNextEntry() throws IOException {
        DumpArchiveEntry entry = null;
        String path = null;
        if (!this.queue.isEmpty()) {
            return (DumpArchiveEntry) this.queue.remove();
        }
        while (entry == null) {
            if (this.hasHitEOF) {
                return null;
            }
            while (this.readIdx < this.active.getHeaderCount()) {
                DumpArchiveEntry dumpArchiveEntry = this.active;
                int i = this.readIdx;
                this.readIdx = i + 1;
                if (!dumpArchiveEntry.isSparseRecord(i) && this.raw.skip(PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) == -1) {
                    throw new EOFException();
                }
            }
            this.readIdx = 0;
            this.filepos = this.raw.getBytesRead();
            byte[] headerBytes = this.raw.readRecord();
            if (DumpArchiveUtil.verify(headerBytes)) {
                this.active = DumpArchiveEntry.parse(headerBytes);
                while (SEGMENT_TYPE.ADDR == this.active.getHeaderType()) {
                    if (this.raw.skip((long) ((this.active.getHeaderCount() - this.active.getHeaderHoles()) * 1024)) == -1) {
                        throw new EOFException();
                    }
                    this.filepos = this.raw.getBytesRead();
                    headerBytes = this.raw.readRecord();
                    if (DumpArchiveUtil.verify(headerBytes)) {
                        this.active = DumpArchiveEntry.parse(headerBytes);
                    } else {
                        throw new InvalidFormatException();
                    }
                }
                if (SEGMENT_TYPE.END == this.active.getHeaderType()) {
                    this.hasHitEOF = true;
                    return null;
                }
                entry = this.active;
                if (entry.isDirectory()) {
                    readDirectoryEntry(this.active);
                    this.entryOffset = 0;
                    this.entrySize = 0;
                    this.readIdx = this.active.getHeaderCount();
                } else {
                    this.entryOffset = 0;
                    this.entrySize = this.active.getEntrySize();
                    this.readIdx = 0;
                }
                this.recordOffset = this.readBuf.length;
                path = getPath(entry);
                if (path == null) {
                    entry = null;
                }
            } else {
                throw new InvalidFormatException();
            }
        }
        entry.setName(path);
        entry.setSimpleName(((Dirent) this.names.get(Integer.valueOf(entry.getIno()))).getName());
        entry.setOffset(this.filepos);
        return entry;
    }

    private void readDirectoryEntry(DumpArchiveEntry entry) throws IOException {
        DumpArchiveInputStream dumpArchiveInputStream = this;
        boolean first = true;
        long size = entry.getEntrySize();
        DumpArchiveEntry entry2 = entry;
        while (true) {
            boolean first2 = first;
            if (!first2) {
                if (SEGMENT_TYPE.ADDR != entry2.getHeaderType()) {
                    return;
                }
            }
            if (!first2) {
                dumpArchiveInputStream.raw.readRecord();
            }
            if (!dumpArchiveInputStream.names.containsKey(Integer.valueOf(entry2.getIno())) && SEGMENT_TYPE.INODE == entry2.getHeaderType()) {
                dumpArchiveInputStream.pending.put(Integer.valueOf(entry2.getIno()), entry2);
            }
            int datalen = entry2.getHeaderCount() * 1024;
            if (dumpArchiveInputStream.blockBuffer.length < datalen) {
                dumpArchiveInputStream.blockBuffer = new byte[datalen];
            }
            int i = 0;
            if (dumpArchiveInputStream.raw.read(dumpArchiveInputStream.blockBuffer, 0, datalen) != datalen) {
                throw new EOFException();
            }
            boolean z;
            byte[] peekBytes;
            while (true) {
                int i2 = i;
                if (i2 >= datalen - 8 || ((long) i2) >= size - 8) {
                    z = first2;
                    peekBytes = dumpArchiveInputStream.raw.peek();
                } else {
                    DumpArchiveEntry entry3;
                    i = DumpArchiveUtil.convert32(dumpArchiveInputStream.blockBuffer, i2);
                    int reclen = DumpArchiveUtil.convert16(dumpArchiveInputStream.blockBuffer, i2 + 4);
                    byte type = dumpArchiveInputStream.blockBuffer[i2 + 6];
                    String name = DumpArchiveUtil.decode(dumpArchiveInputStream.encoding, dumpArchiveInputStream.blockBuffer, i2 + 8, dumpArchiveInputStream.blockBuffer[i2 + 7]);
                    if (".".equals(name)) {
                        entry3 = entry2;
                        z = first2;
                    } else if ("..".equals(name)) {
                        entry3 = entry2;
                        z = first2;
                    } else {
                        dumpArchiveInputStream.names.put(Integer.valueOf(i), new Dirent(i, entry2.getIno(), type, name));
                        for (Entry<Integer, DumpArchiveEntry> e : dumpArchiveInputStream.pending.entrySet()) {
                            String path = getPath((DumpArchiveEntry) e.getValue());
                            if (path != null) {
                                ((DumpArchiveEntry) e.getValue()).setName(path);
                                entry3 = entry2;
                                z = first2;
                                ((DumpArchiveEntry) e.getValue()).setSimpleName(((Dirent) dumpArchiveInputStream.names.get(e.getKey())).getName());
                                dumpArchiveInputStream.queue.add(e.getValue());
                            } else {
                                entry3 = entry2;
                                z = first2;
                            }
                            entry2 = entry3;
                            first2 = z;
                        }
                        entry3 = entry2;
                        z = first2;
                        for (DumpArchiveEntry first3 : dumpArchiveInputStream.queue) {
                            dumpArchiveInputStream.pending.remove(Integer.valueOf(first3.getIno()));
                        }
                    }
                    i = i2 + reclen;
                    entry2 = entry3;
                    first2 = z;
                }
            }
            z = first2;
            peekBytes = dumpArchiveInputStream.raw.peek();
            if (DumpArchiveUtil.verify(peekBytes)) {
                entry2 = DumpArchiveEntry.parse(peekBytes);
                first = false;
                size -= PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID;
            } else {
                throw new InvalidFormatException();
            }
        }
    }

    private String getPath(DumpArchiveEntry entry) {
        Stack<String> elements = new Stack();
        int i = entry.getIno();
        while (this.names.containsKey(Integer.valueOf(i))) {
            Dirent dirent = (Dirent) this.names.get(Integer.valueOf(i));
            elements.push(dirent.getName());
            if (dirent.getIno() == dirent.getParentIno()) {
                break;
            }
            i = dirent.getParentIno();
        }
        elements.clear();
        if (elements.isEmpty()) {
            this.pending.put(Integer.valueOf(entry.getIno()), entry);
            return null;
        }
        StringBuilder sb = new StringBuilder((String) elements.pop());
        while (!elements.isEmpty()) {
            sb.append('/');
            sb.append((String) elements.pop());
        }
        return sb.toString();
    }

    public int read(byte[] buf, int off, int len) throws IOException {
        int totalRead = 0;
        if (!(this.hasHitEOF || this.isClosed)) {
            if (this.entryOffset < this.entrySize) {
                if (this.active == null) {
                    throw new IllegalStateException("No current dump entry");
                }
                if (((long) len) + this.entryOffset > this.entrySize) {
                    len = (int) (this.entrySize - this.entryOffset);
                }
                while (len > 0) {
                    int sz = len > this.readBuf.length - this.recordOffset ? this.readBuf.length - this.recordOffset : len;
                    if (this.recordOffset + sz <= this.readBuf.length) {
                        System.arraycopy(this.readBuf, this.recordOffset, buf, off, sz);
                        totalRead += sz;
                        this.recordOffset += sz;
                        len -= sz;
                        off += sz;
                    }
                    if (len > 0) {
                        if (this.readIdx >= 512) {
                            byte[] headerBytes = this.raw.readRecord();
                            if (DumpArchiveUtil.verify(headerBytes)) {
                                this.active = DumpArchiveEntry.parse(headerBytes);
                                this.readIdx = 0;
                            } else {
                                throw new InvalidFormatException();
                            }
                        }
                        DumpArchiveEntry dumpArchiveEntry = this.active;
                        int i = this.readIdx;
                        this.readIdx = i + 1;
                        if (dumpArchiveEntry.isSparseRecord(i)) {
                            Arrays.fill(this.readBuf, (byte) 0);
                        } else if (this.raw.read(this.readBuf, 0, this.readBuf.length) != this.readBuf.length) {
                            throw new EOFException();
                        }
                        this.recordOffset = 0;
                    }
                }
                this.entryOffset += (long) totalRead;
                return totalRead;
            }
        }
        return -1;
    }

    public void close() throws IOException {
        if (!this.isClosed) {
            this.isClosed = true;
            this.raw.close();
        }
    }

    public static boolean matches(byte[] buffer, int length) {
        boolean z = false;
        if (length < 32) {
            return false;
        }
        if (length >= 1024) {
            return DumpArchiveUtil.verify(buffer);
        }
        if (DumpArchiveConstants.NFS_MAGIC == DumpArchiveUtil.convert32(buffer, 24)) {
            z = true;
        }
        return z;
    }
}
