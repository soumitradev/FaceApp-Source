package org.apache.commons.compress.archivers.zip;

import android.support.v4.internal.view.SupportMenu;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.zip.CRC32;
import java.util.zip.Deflater;
import java.util.zip.ZipException;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;

public class ZipArchiveOutputStream extends ArchiveOutputStream {
    static final int BUFFER_SIZE = 512;
    static final byte[] CFH_SIG = ZipLong.CFH_SIG.getBytes();
    static final byte[] DD_SIG = ZipLong.DD_SIG.getBytes();
    public static final int DEFAULT_COMPRESSION = -1;
    static final String DEFAULT_ENCODING = "UTF8";
    public static final int DEFLATED = 8;
    private static final int DEFLATER_BLOCK_SIZE = 8192;
    @Deprecated
    public static final int EFS_FLAG = 2048;
    private static final byte[] EMPTY = new byte[0];
    static final byte[] EOCD_SIG = ZipLong.getBytes(101010256);
    static final byte[] LFH_SIG = ZipLong.LFH_SIG.getBytes();
    private static final byte[] LZERO = new byte[]{(byte) 0, (byte) 0, (byte) 0, (byte) 0};
    private static final byte[] ONE = ZipLong.getBytes(1);
    public static final int STORED = 0;
    private static final byte[] ZERO = new byte[]{(byte) 0, (byte) 0};
    static final byte[] ZIP64_EOCD_LOC_SIG = ZipLong.getBytes(117853008);
    static final byte[] ZIP64_EOCD_SIG = ZipLong.getBytes(101075792);
    private final byte[] buf;
    private long cdLength;
    private long cdOffset;
    private String comment;
    private final CRC32 crc;
    private ZipArchiveOutputStream$UnicodeExtraFieldPolicy createUnicodeExtraFields;
    protected final Deflater def;
    private String encoding;
    private final List<ZipArchiveEntry> entries;
    private ZipArchiveOutputStream$CurrentEntry entry;
    private boolean fallbackToUTF8;
    protected boolean finished;
    private boolean hasCompressionLevelChanged;
    private boolean hasUsedZip64;
    private int level;
    private int method;
    private final Map<ZipArchiveEntry, Long> offsets;
    private final OutputStream out;
    private final RandomAccessFile raf;
    private boolean useUTF8Flag;
    private long written;
    private Zip64Mode zip64Mode;
    private ZipEncoding zipEncoding;

    public ZipArchiveOutputStream(OutputStream out) {
        this.finished = false;
        this.comment = "";
        this.level = -1;
        this.hasCompressionLevelChanged = false;
        this.method = 8;
        this.entries = new LinkedList();
        this.crc = new CRC32();
        this.written = 0;
        this.cdOffset = 0;
        this.cdLength = 0;
        this.offsets = new HashMap();
        this.encoding = "UTF8";
        this.zipEncoding = ZipEncodingHelper.getZipEncoding("UTF8");
        this.def = new Deflater(this.level, true);
        this.buf = new byte[512];
        this.useUTF8Flag = true;
        this.fallbackToUTF8 = false;
        this.createUnicodeExtraFields = ZipArchiveOutputStream$UnicodeExtraFieldPolicy.NEVER;
        this.hasUsedZip64 = false;
        this.zip64Mode = Zip64Mode.AsNeeded;
        this.out = out;
        this.raf = null;
    }

    public ZipArchiveOutputStream(File file) throws IOException {
        this.finished = false;
        this.comment = "";
        this.level = -1;
        this.hasCompressionLevelChanged = false;
        this.method = 8;
        this.entries = new LinkedList();
        this.crc = new CRC32();
        this.written = 0;
        this.cdOffset = 0;
        this.cdLength = 0;
        this.offsets = new HashMap();
        this.encoding = "UTF8";
        this.zipEncoding = ZipEncodingHelper.getZipEncoding("UTF8");
        this.def = new Deflater(this.level, true);
        this.buf = new byte[512];
        this.useUTF8Flag = true;
        this.fallbackToUTF8 = false;
        this.createUnicodeExtraFields = ZipArchiveOutputStream$UnicodeExtraFieldPolicy.NEVER;
        this.hasUsedZip64 = false;
        this.zip64Mode = Zip64Mode.AsNeeded;
        OutputStream o = null;
        RandomAccessFile _raf = null;
        try {
            _raf = new RandomAccessFile(file, "rw");
            _raf.setLength(0);
        } catch (IOException e) {
            IOUtils.closeQuietly(_raf);
            _raf = null;
            o = new FileOutputStream(file);
        }
        this.out = o;
        this.raf = _raf;
    }

    public boolean isSeekable() {
        return this.raf != null;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
        this.zipEncoding = ZipEncodingHelper.getZipEncoding(encoding);
        if (this.useUTF8Flag && !ZipEncodingHelper.isUTF8(encoding)) {
            this.useUTF8Flag = false;
        }
    }

    public String getEncoding() {
        return this.encoding;
    }

    public void setUseLanguageEncodingFlag(boolean b) {
        boolean z = b && ZipEncodingHelper.isUTF8(this.encoding);
        this.useUTF8Flag = z;
    }

    public void setCreateUnicodeExtraFields(ZipArchiveOutputStream$UnicodeExtraFieldPolicy b) {
        this.createUnicodeExtraFields = b;
    }

    public void setFallbackToUTF8(boolean b) {
        this.fallbackToUTF8 = b;
    }

    public void setUseZip64(Zip64Mode mode) {
        this.zip64Mode = mode;
    }

    public void finish() throws IOException {
        if (this.finished) {
            throw new IOException("This archive has already been finished");
        } else if (this.entry != null) {
            throw new IOException("This archive contains unclosed entries.");
        } else {
            this.cdOffset = this.written;
            for (ZipArchiveEntry ze : this.entries) {
                writeCentralFileHeader(ze);
            }
            this.cdLength = this.written - this.cdOffset;
            writeZip64CentralDirectory();
            writeCentralDirectoryEnd();
            this.offsets.clear();
            this.entries.clear();
            this.def.end();
            this.finished = true;
        }
    }

    public void closeArchiveEntry() throws IOException {
        if (this.finished) {
            throw new IOException("Stream has already been finished");
        } else if (this.entry == null) {
            throw new IOException("No current entry to close");
        } else {
            if (!ZipArchiveOutputStream$CurrentEntry.access$000(this.entry)) {
                write(EMPTY, 0, 0);
            }
            flushDeflater();
            Zip64Mode effectiveMode = getEffectiveZip64Mode(ZipArchiveOutputStream$CurrentEntry.access$100(this.entry));
            long bytesWritten = this.written - ZipArchiveOutputStream$CurrentEntry.access$200(this.entry);
            long realCrc = this.crc.getValue();
            this.crc.reset();
            boolean actuallyNeedsZip64 = handleSizesAndCrc(bytesWritten, realCrc, effectiveMode);
            if (this.raf != null) {
                rewriteSizesAndCrc(actuallyNeedsZip64);
            }
            writeDataDescriptor(ZipArchiveOutputStream$CurrentEntry.access$100(this.entry));
            this.entry = null;
        }
    }

    private void flushDeflater() throws IOException {
        if (ZipArchiveOutputStream$CurrentEntry.access$100(this.entry).getMethod() == 8) {
            this.def.finish();
            while (!this.def.finished()) {
                deflate();
            }
        }
    }

    private boolean handleSizesAndCrc(long bytesWritten, long crc, Zip64Mode effectiveMode) throws ZipException {
        boolean actuallyNeedsZip64;
        if (ZipArchiveOutputStream$CurrentEntry.access$100(this.entry).getMethod() == 8) {
            ZipArchiveOutputStream$CurrentEntry.access$100(this.entry).setSize(ZipArchiveOutputStream$CurrentEntry.access$300(this.entry));
            ZipArchiveOutputStream$CurrentEntry.access$100(this.entry).setCompressedSize(bytesWritten);
            ZipArchiveOutputStream$CurrentEntry.access$100(this.entry).setCrc(crc);
            this.def.reset();
        } else if (this.raf != null) {
            ZipArchiveOutputStream$CurrentEntry.access$100(this.entry).setSize(bytesWritten);
            ZipArchiveOutputStream$CurrentEntry.access$100(this.entry).setCompressedSize(bytesWritten);
            ZipArchiveOutputStream$CurrentEntry.access$100(this.entry).setCrc(crc);
        } else if (ZipArchiveOutputStream$CurrentEntry.access$100(this.entry).getCrc() != crc) {
            r1 = new StringBuilder();
            r1.append("bad CRC checksum for entry ");
            r1.append(ZipArchiveOutputStream$CurrentEntry.access$100(this.entry).getName());
            r1.append(": ");
            r1.append(Long.toHexString(ZipArchiveOutputStream$CurrentEntry.access$100(this.entry).getCrc()));
            r1.append(" instead of ");
            r1.append(Long.toHexString(crc));
            throw new ZipException(r1.toString());
        } else if (ZipArchiveOutputStream$CurrentEntry.access$100(this.entry).getSize() != bytesWritten) {
            r1 = new StringBuilder();
            r1.append("bad size for entry ");
            r1.append(ZipArchiveOutputStream$CurrentEntry.access$100(this.entry).getName());
            r1.append(": ");
            r1.append(ZipArchiveOutputStream$CurrentEntry.access$100(this.entry).getSize());
            r1.append(" instead of ");
            r1.append(bytesWritten);
            throw new ZipException(r1.toString());
        }
        if (effectiveMode != Zip64Mode.Always && ZipArchiveOutputStream$CurrentEntry.access$100(this.entry).getSize() < 4294967295L) {
            if (ZipArchiveOutputStream$CurrentEntry.access$100(this.entry).getCompressedSize() < 4294967295L) {
                actuallyNeedsZip64 = false;
                if (actuallyNeedsZip64 || effectiveMode != Zip64Mode.Never) {
                    return actuallyNeedsZip64;
                }
                throw new Zip64RequiredException(Zip64RequiredException.getEntryTooBigMessage(ZipArchiveOutputStream$CurrentEntry.access$100(this.entry)));
            }
        }
        actuallyNeedsZip64 = true;
        if (actuallyNeedsZip64) {
        }
        return actuallyNeedsZip64;
    }

    private void rewriteSizesAndCrc(boolean actuallyNeedsZip64) throws IOException {
        long save = this.raf.getFilePointer();
        this.raf.seek(ZipArchiveOutputStream$CurrentEntry.access$400(this.entry));
        writeOut(ZipLong.getBytes(ZipArchiveOutputStream$CurrentEntry.access$100(this.entry).getCrc()));
        if (hasZip64Extra(ZipArchiveOutputStream$CurrentEntry.access$100(this.entry))) {
            if (actuallyNeedsZip64) {
                writeOut(ZipLong.ZIP64_MAGIC.getBytes());
                writeOut(ZipLong.ZIP64_MAGIC.getBytes());
                if (hasZip64Extra(ZipArchiveOutputStream$CurrentEntry.access$100(this.entry))) {
                    this.raf.seek((((ZipArchiveOutputStream$CurrentEntry.access$400(this.entry) + 12) + 4) + ((long) getName(ZipArchiveOutputStream$CurrentEntry.access$100(this.entry)).limit())) + 4);
                    writeOut(ZipEightByteInteger.getBytes(ZipArchiveOutputStream$CurrentEntry.access$100(this.entry).getSize()));
                    writeOut(ZipEightByteInteger.getBytes(ZipArchiveOutputStream$CurrentEntry.access$100(this.entry).getCompressedSize()));
                    if (!actuallyNeedsZip64) {
                        this.raf.seek(ZipArchiveOutputStream$CurrentEntry.access$400(this.entry) - 10);
                        writeOut(ZipShort.getBytes(10));
                        ZipArchiveOutputStream$CurrentEntry.access$100(this.entry).removeExtraField(Zip64ExtendedInformationExtraField.HEADER_ID);
                        ZipArchiveOutputStream$CurrentEntry.access$100(this.entry).setExtra();
                        if (ZipArchiveOutputStream$CurrentEntry.access$500(this.entry)) {
                            this.hasUsedZip64 = false;
                        }
                    }
                }
                this.raf.seek(save);
            }
        }
        writeOut(ZipLong.getBytes(ZipArchiveOutputStream$CurrentEntry.access$100(this.entry).getCompressedSize()));
        writeOut(ZipLong.getBytes(ZipArchiveOutputStream$CurrentEntry.access$100(this.entry).getSize()));
        if (hasZip64Extra(ZipArchiveOutputStream$CurrentEntry.access$100(this.entry))) {
            this.raf.seek((((ZipArchiveOutputStream$CurrentEntry.access$400(this.entry) + 12) + 4) + ((long) getName(ZipArchiveOutputStream$CurrentEntry.access$100(this.entry)).limit())) + 4);
            writeOut(ZipEightByteInteger.getBytes(ZipArchiveOutputStream$CurrentEntry.access$100(this.entry).getSize()));
            writeOut(ZipEightByteInteger.getBytes(ZipArchiveOutputStream$CurrentEntry.access$100(this.entry).getCompressedSize()));
            if (actuallyNeedsZip64) {
                this.raf.seek(ZipArchiveOutputStream$CurrentEntry.access$400(this.entry) - 10);
                writeOut(ZipShort.getBytes(10));
                ZipArchiveOutputStream$CurrentEntry.access$100(this.entry).removeExtraField(Zip64ExtendedInformationExtraField.HEADER_ID);
                ZipArchiveOutputStream$CurrentEntry.access$100(this.entry).setExtra();
                if (ZipArchiveOutputStream$CurrentEntry.access$500(this.entry)) {
                    this.hasUsedZip64 = false;
                }
            }
        }
        this.raf.seek(save);
    }

    public void putArchiveEntry(ArchiveEntry archiveEntry) throws IOException {
        if (this.finished) {
            throw new IOException("Stream has already been finished");
        }
        if (this.entry != null) {
            closeArchiveEntry();
        }
        this.entry = new ZipArchiveOutputStream$CurrentEntry((ZipArchiveEntry) archiveEntry, null);
        this.entries.add(ZipArchiveOutputStream$CurrentEntry.access$100(this.entry));
        setDefaults(ZipArchiveOutputStream$CurrentEntry.access$100(this.entry));
        Zip64Mode effectiveMode = getEffectiveZip64Mode(ZipArchiveOutputStream$CurrentEntry.access$100(this.entry));
        validateSizeInformation(effectiveMode);
        if (shouldAddZip64Extra(ZipArchiveOutputStream$CurrentEntry.access$100(this.entry), effectiveMode)) {
            Zip64ExtendedInformationExtraField z64 = getZip64Extra(ZipArchiveOutputStream$CurrentEntry.access$100(this.entry));
            ZipEightByteInteger size = ZipEightByteInteger.ZERO;
            if (ZipArchiveOutputStream$CurrentEntry.access$100(this.entry).getMethod() == 0 && ZipArchiveOutputStream$CurrentEntry.access$100(this.entry).getSize() != -1) {
                size = new ZipEightByteInteger(ZipArchiveOutputStream$CurrentEntry.access$100(this.entry).getSize());
            }
            z64.setSize(size);
            z64.setCompressedSize(size);
            ZipArchiveOutputStream$CurrentEntry.access$100(this.entry).setExtra();
        }
        if (ZipArchiveOutputStream$CurrentEntry.access$100(this.entry).getMethod() == 8 && this.hasCompressionLevelChanged) {
            this.def.setLevel(this.level);
            this.hasCompressionLevelChanged = false;
        }
        writeLocalFileHeader(ZipArchiveOutputStream$CurrentEntry.access$100(this.entry));
    }

    private void setDefaults(ZipArchiveEntry entry) {
        if (entry.getMethod() == -1) {
            entry.setMethod(this.method);
        }
        if (entry.getTime() == -1) {
            entry.setTime(System.currentTimeMillis());
        }
    }

    private void validateSizeInformation(Zip64Mode effectiveMode) throws ZipException {
        if (ZipArchiveOutputStream$CurrentEntry.access$100(this.entry).getMethod() == 0 && this.raf == null) {
            if (ZipArchiveOutputStream$CurrentEntry.access$100(this.entry).getSize() == -1) {
                throw new ZipException("uncompressed size is required for STORED method when not writing to a file");
            } else if (ZipArchiveOutputStream$CurrentEntry.access$100(this.entry).getCrc() == -1) {
                throw new ZipException("crc checksum is required for STORED method when not writing to a file");
            } else {
                ZipArchiveOutputStream$CurrentEntry.access$100(this.entry).setCompressedSize(ZipArchiveOutputStream$CurrentEntry.access$100(this.entry).getSize());
            }
        }
        if ((ZipArchiveOutputStream$CurrentEntry.access$100(this.entry).getSize() >= 4294967295L || ZipArchiveOutputStream$CurrentEntry.access$100(this.entry).getCompressedSize() >= 4294967295L) && effectiveMode == Zip64Mode.Never) {
            throw new Zip64RequiredException(Zip64RequiredException.getEntryTooBigMessage(ZipArchiveOutputStream$CurrentEntry.access$100(this.entry)));
        }
    }

    private boolean shouldAddZip64Extra(ZipArchiveEntry entry, Zip64Mode mode) {
        if (mode != Zip64Mode.Always && entry.getSize() < 4294967295L && entry.getCompressedSize() < 4294967295L) {
            if (entry.getSize() != -1 || this.raf == null || mode == Zip64Mode.Never) {
                return false;
            }
        }
        return true;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setLevel(int level) {
        if (level >= -1) {
            if (level <= 9) {
                this.hasCompressionLevelChanged = this.level != level;
                this.level = level;
                return;
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid compression level: ");
        stringBuilder.append(level);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public void setMethod(int method) {
        this.method = method;
    }

    public boolean canWriteEntryData(ArchiveEntry ae) {
        boolean z = false;
        if (!(ae instanceof ZipArchiveEntry)) {
            return false;
        }
        ZipArchiveEntry zae = (ZipArchiveEntry) ae;
        if (!(zae.getMethod() == ZipMethod.IMPLODING.getCode() || zae.getMethod() == ZipMethod.UNSHRINKING.getCode() || !ZipUtil.canHandleEntryData(zae))) {
            z = true;
        }
        return z;
    }

    public void write(byte[] b, int offset, int length) throws IOException {
        if (this.entry == null) {
            throw new IllegalStateException("No current entry");
        }
        ZipUtil.checkRequestedFeatures(ZipArchiveOutputStream$CurrentEntry.access$100(this.entry));
        ZipArchiveOutputStream$CurrentEntry.access$002(this.entry, true);
        if (ZipArchiveOutputStream$CurrentEntry.access$100(this.entry).getMethod() == 8) {
            writeDeflated(b, offset, length);
        } else {
            writeOut(b, offset, length);
            this.written += (long) length;
        }
        this.crc.update(b, offset, length);
        count(length);
    }

    private void writeDeflated(byte[] b, int offset, int length) throws IOException {
        if (length > 0 && !this.def.finished()) {
            ZipArchiveOutputStream$CurrentEntry.access$314(this.entry, (long) length);
            if (length <= 8192) {
                this.def.setInput(b, offset, length);
                deflateUntilInputIsNeeded();
                return;
            }
            int fullblocks = length / 8192;
            for (int i = 0; i < fullblocks; i++) {
                this.def.setInput(b, (i * 8192) + offset, 8192);
                deflateUntilInputIsNeeded();
            }
            int done = fullblocks * 8192;
            if (done < length) {
                this.def.setInput(b, offset + done, length - done);
                deflateUntilInputIsNeeded();
            }
        }
    }

    public void close() throws IOException {
        if (!this.finished) {
            finish();
        }
        destroy();
    }

    public void flush() throws IOException {
        if (this.out != null) {
            this.out.flush();
        }
    }

    protected final void deflate() throws IOException {
        int len = this.def.deflate(this.buf, 0, this.buf.length);
        if (len > 0) {
            writeOut(this.buf, 0, len);
            this.written += (long) len;
        }
    }

    protected void writeLocalFileHeader(ZipArchiveEntry ze) throws IOException {
        byte[] size;
        boolean encodable = this.zipEncoding.canEncode(ze.getName());
        ByteBuffer name = getName(ze);
        if (this.createUnicodeExtraFields != ZipArchiveOutputStream$UnicodeExtraFieldPolicy.NEVER) {
            addUnicodeExtraFields(ze, encodable, name);
        }
        this.offsets.put(ze, Long.valueOf(this.written));
        writeOut(LFH_SIG);
        this.written += 4;
        int zipMethod = ze.getMethod();
        boolean z = !encodable && this.fallbackToUTF8;
        writeVersionNeededToExtractAndGeneralPurposeBits(zipMethod, z, hasZip64Extra(ze));
        this.written += 4;
        writeOut(ZipShort.getBytes(zipMethod));
        this.written += 2;
        writeOut(ZipUtil.toDosTime(ze.getTime()));
        this.written += 4;
        ZipArchiveOutputStream$CurrentEntry.access$402(this.entry, this.written);
        if (zipMethod != 8) {
            if (this.raf == null) {
                writeOut(ZipLong.getBytes(ze.getCrc()));
                size = ZipLong.ZIP64_MAGIC.getBytes();
                if (!hasZip64Extra(ze)) {
                    size = ZipLong.getBytes(ze.getSize());
                }
                writeOut(size);
                writeOut(size);
                this.written += 12;
                writeOut(ZipShort.getBytes(name.limit()));
                this.written += 2;
                size = ze.getLocalFileDataExtra();
                writeOut(ZipShort.getBytes(size.length));
                this.written += 2;
                writeOut(name.array(), name.arrayOffset(), name.limit() - name.position());
                this.written += (long) name.limit();
                writeOut(size);
                this.written += (long) size.length;
                ZipArchiveOutputStream$CurrentEntry.access$202(this.entry, this.written);
            }
        }
        writeOut(LZERO);
        if (hasZip64Extra(ZipArchiveOutputStream$CurrentEntry.access$100(this.entry))) {
            writeOut(ZipLong.ZIP64_MAGIC.getBytes());
            writeOut(ZipLong.ZIP64_MAGIC.getBytes());
        } else {
            writeOut(LZERO);
            writeOut(LZERO);
        }
        this.written += 12;
        writeOut(ZipShort.getBytes(name.limit()));
        this.written += 2;
        size = ze.getLocalFileDataExtra();
        writeOut(ZipShort.getBytes(size.length));
        this.written += 2;
        writeOut(name.array(), name.arrayOffset(), name.limit() - name.position());
        this.written += (long) name.limit();
        writeOut(size);
        this.written += (long) size.length;
        ZipArchiveOutputStream$CurrentEntry.access$202(this.entry, this.written);
    }

    private void addUnicodeExtraFields(ZipArchiveEntry ze, boolean encodable, ByteBuffer name) throws IOException {
        if (this.createUnicodeExtraFields == ZipArchiveOutputStream$UnicodeExtraFieldPolicy.ALWAYS || !encodable) {
            ze.addExtraField(new UnicodePathExtraField(ze.getName(), name.array(), name.arrayOffset(), name.limit() - name.position()));
        }
        String comm = ze.getComment();
        if (comm != null && !"".equals(comm)) {
            boolean commentEncodable = this.zipEncoding.canEncode(comm);
            if (this.createUnicodeExtraFields == ZipArchiveOutputStream$UnicodeExtraFieldPolicy.ALWAYS || !commentEncodable) {
                ByteBuffer commentB = getEntryEncoding(ze).encode(comm);
                ze.addExtraField(new UnicodeCommentExtraField(comm, commentB.array(), commentB.arrayOffset(), commentB.limit() - commentB.position()));
            }
        }
    }

    protected void writeDataDescriptor(ZipArchiveEntry ze) throws IOException {
        if (ze.getMethod() == 8) {
            if (this.raf == null) {
                writeOut(DD_SIG);
                writeOut(ZipLong.getBytes(ze.getCrc()));
                int sizeFieldSize = 4;
                if (hasZip64Extra(ze)) {
                    sizeFieldSize = 8;
                    writeOut(ZipEightByteInteger.getBytes(ze.getCompressedSize()));
                    writeOut(ZipEightByteInteger.getBytes(ze.getSize()));
                } else {
                    writeOut(ZipLong.getBytes(ze.getCompressedSize()));
                    writeOut(ZipLong.getBytes(ze.getSize()));
                }
                this.written += (long) ((sizeFieldSize * 2) + 8);
            }
        }
    }

    protected void writeCentralFileHeader(ZipArchiveEntry ze) throws IOException {
        boolean needsZip64Extra;
        int zipMethod;
        boolean z;
        ByteBuffer name;
        byte[] extra;
        String comm;
        ByteBuffer commentB;
        ZipArchiveEntry zipArchiveEntry = ze;
        writeOut(CFH_SIG);
        this.written += 4;
        long lfhOffset = ((Long) this.offsets.get(zipArchiveEntry)).longValue();
        if (!hasZip64Extra(ze) && ze.getCompressedSize() < 4294967295L && ze.getSize() < 4294967295L) {
            if (lfhOffset < 4294967295L) {
                needsZip64Extra = false;
                if (needsZip64Extra || r0.zip64Mode != Zip64Mode.Never) {
                    handleZip64Extra(zipArchiveEntry, lfhOffset, needsZip64Extra);
                    writeOut(ZipShort.getBytes((ze.getPlatform() << 8) | (r0.hasUsedZip64 ? 20 : 45)));
                    r0.written += 2;
                    zipMethod = ze.getMethod();
                    z = r0.zipEncoding.canEncode(ze.getName()) && r0.fallbackToUTF8;
                    writeVersionNeededToExtractAndGeneralPurposeBits(zipMethod, z, needsZip64Extra);
                    r0.written += 4;
                    writeOut(ZipShort.getBytes(zipMethod));
                    r0.written += 2;
                    writeOut(ZipUtil.toDosTime(ze.getTime()));
                    r0.written += 4;
                    writeOut(ZipLong.getBytes(ze.getCrc()));
                    if (ze.getCompressedSize() < 4294967295L) {
                        if (ze.getSize() >= 4294967295L) {
                            writeOut(ZipLong.getBytes(ze.getCompressedSize()));
                            writeOut(ZipLong.getBytes(ze.getSize()));
                            r0.written += 12;
                            name = getName(ze);
                            writeOut(ZipShort.getBytes(name.limit()));
                            r0.written += 2;
                            extra = ze.getCentralDirectoryExtra();
                            writeOut(ZipShort.getBytes(extra.length));
                            r0.written += 2;
                            comm = ze.getComment();
                            if (comm == null) {
                                comm = "";
                            }
                            commentB = getEntryEncoding(ze).encode(comm);
                            writeOut(ZipShort.getBytes(commentB.limit()));
                            r0.written += true;
                            writeOut(ZERO);
                            r0.written += 2;
                            writeOut(ZipShort.getBytes(ze.getInternalAttributes()));
                            r0.written += 2;
                            writeOut(ZipLong.getBytes(ze.getExternalAttributes()));
                            r0.written += 4;
                            writeOut(ZipLong.getBytes(Math.min(lfhOffset, 4294967295L)));
                            r0.written += 4;
                            writeOut(name.array(), name.arrayOffset(), name.limit() - name.position());
                            r0.written += (long) name.limit();
                            writeOut(extra);
                            r0.written += (long) extra.length;
                            writeOut(commentB.array(), commentB.arrayOffset(), commentB.limit() - commentB.position());
                            r0.written += (long) commentB.limit();
                        }
                    }
                    writeOut(ZipLong.ZIP64_MAGIC.getBytes());
                    writeOut(ZipLong.ZIP64_MAGIC.getBytes());
                    r0.written += 12;
                    name = getName(ze);
                    writeOut(ZipShort.getBytes(name.limit()));
                    r0.written += 2;
                    extra = ze.getCentralDirectoryExtra();
                    writeOut(ZipShort.getBytes(extra.length));
                    r0.written += 2;
                    comm = ze.getComment();
                    if (comm == null) {
                        comm = "";
                    }
                    commentB = getEntryEncoding(ze).encode(comm);
                    writeOut(ZipShort.getBytes(commentB.limit()));
                    r0.written += true;
                    writeOut(ZERO);
                    r0.written += 2;
                    writeOut(ZipShort.getBytes(ze.getInternalAttributes()));
                    r0.written += 2;
                    writeOut(ZipLong.getBytes(ze.getExternalAttributes()));
                    r0.written += 4;
                    writeOut(ZipLong.getBytes(Math.min(lfhOffset, 4294967295L)));
                    r0.written += 4;
                    writeOut(name.array(), name.arrayOffset(), name.limit() - name.position());
                    r0.written += (long) name.limit();
                    writeOut(extra);
                    r0.written += (long) extra.length;
                    writeOut(commentB.array(), commentB.arrayOffset(), commentB.limit() - commentB.position());
                    r0.written += (long) commentB.limit();
                }
                throw new Zip64RequiredException("archive's size exceeds the limit of 4GByte.");
            }
        }
        needsZip64Extra = true;
        if (needsZip64Extra) {
        }
        handleZip64Extra(zipArchiveEntry, lfhOffset, needsZip64Extra);
        if (r0.hasUsedZip64) {
        }
        writeOut(ZipShort.getBytes((ze.getPlatform() << 8) | (r0.hasUsedZip64 ? 20 : 45)));
        r0.written += 2;
        zipMethod = ze.getMethod();
        if (r0.zipEncoding.canEncode(ze.getName())) {
        }
        writeVersionNeededToExtractAndGeneralPurposeBits(zipMethod, z, needsZip64Extra);
        r0.written += 4;
        writeOut(ZipShort.getBytes(zipMethod));
        r0.written += 2;
        writeOut(ZipUtil.toDosTime(ze.getTime()));
        r0.written += 4;
        writeOut(ZipLong.getBytes(ze.getCrc()));
        if (ze.getCompressedSize() < 4294967295L) {
            if (ze.getSize() >= 4294967295L) {
                writeOut(ZipLong.getBytes(ze.getCompressedSize()));
                writeOut(ZipLong.getBytes(ze.getSize()));
                r0.written += 12;
                name = getName(ze);
                writeOut(ZipShort.getBytes(name.limit()));
                r0.written += 2;
                extra = ze.getCentralDirectoryExtra();
                writeOut(ZipShort.getBytes(extra.length));
                r0.written += 2;
                comm = ze.getComment();
                if (comm == null) {
                    comm = "";
                }
                commentB = getEntryEncoding(ze).encode(comm);
                writeOut(ZipShort.getBytes(commentB.limit()));
                r0.written += true;
                writeOut(ZERO);
                r0.written += 2;
                writeOut(ZipShort.getBytes(ze.getInternalAttributes()));
                r0.written += 2;
                writeOut(ZipLong.getBytes(ze.getExternalAttributes()));
                r0.written += 4;
                writeOut(ZipLong.getBytes(Math.min(lfhOffset, 4294967295L)));
                r0.written += 4;
                writeOut(name.array(), name.arrayOffset(), name.limit() - name.position());
                r0.written += (long) name.limit();
                writeOut(extra);
                r0.written += (long) extra.length;
                writeOut(commentB.array(), commentB.arrayOffset(), commentB.limit() - commentB.position());
                r0.written += (long) commentB.limit();
            }
        }
        writeOut(ZipLong.ZIP64_MAGIC.getBytes());
        writeOut(ZipLong.ZIP64_MAGIC.getBytes());
        r0.written += 12;
        name = getName(ze);
        writeOut(ZipShort.getBytes(name.limit()));
        r0.written += 2;
        extra = ze.getCentralDirectoryExtra();
        writeOut(ZipShort.getBytes(extra.length));
        r0.written += 2;
        comm = ze.getComment();
        if (comm == null) {
            comm = "";
        }
        commentB = getEntryEncoding(ze).encode(comm);
        writeOut(ZipShort.getBytes(commentB.limit()));
        r0.written += true;
        writeOut(ZERO);
        r0.written += 2;
        writeOut(ZipShort.getBytes(ze.getInternalAttributes()));
        r0.written += 2;
        writeOut(ZipLong.getBytes(ze.getExternalAttributes()));
        r0.written += 4;
        writeOut(ZipLong.getBytes(Math.min(lfhOffset, 4294967295L)));
        r0.written += 4;
        writeOut(name.array(), name.arrayOffset(), name.limit() - name.position());
        r0.written += (long) name.limit();
        writeOut(extra);
        r0.written += (long) extra.length;
        writeOut(commentB.array(), commentB.arrayOffset(), commentB.limit() - commentB.position());
        r0.written += (long) commentB.limit();
    }

    private void handleZip64Extra(ZipArchiveEntry ze, long lfhOffset, boolean needsZip64Extra) {
        if (needsZip64Extra) {
            Zip64ExtendedInformationExtraField z64 = getZip64Extra(ze);
            if (ze.getCompressedSize() < 4294967295L) {
                if (ze.getSize() < 4294967295L) {
                    z64.setCompressedSize(null);
                    z64.setSize(null);
                    if (lfhOffset >= 4294967295L) {
                        z64.setRelativeHeaderOffset(new ZipEightByteInteger(lfhOffset));
                    }
                    ze.setExtra();
                }
            }
            z64.setCompressedSize(new ZipEightByteInteger(ze.getCompressedSize()));
            z64.setSize(new ZipEightByteInteger(ze.getSize()));
            if (lfhOffset >= 4294967295L) {
                z64.setRelativeHeaderOffset(new ZipEightByteInteger(lfhOffset));
            }
            ze.setExtra();
        }
    }

    protected void writeCentralDirectoryEnd() throws IOException {
        writeOut(EOCD_SIG);
        writeOut(ZERO);
        writeOut(ZERO);
        int numberOfEntries = this.entries.size();
        if (numberOfEntries > SupportMenu.USER_MASK && this.zip64Mode == Zip64Mode.Never) {
            throw new Zip64RequiredException("archive contains more than 65535 entries.");
        } else if (this.cdOffset <= 4294967295L || this.zip64Mode != Zip64Mode.Never) {
            byte[] num = ZipShort.getBytes(Math.min(numberOfEntries, SupportMenu.USER_MASK));
            writeOut(num);
            writeOut(num);
            writeOut(ZipLong.getBytes(Math.min(this.cdLength, 4294967295L)));
            writeOut(ZipLong.getBytes(Math.min(this.cdOffset, 4294967295L)));
            ByteBuffer data = this.zipEncoding.encode(this.comment);
            writeOut(ZipShort.getBytes(data.limit()));
            writeOut(data.array(), data.arrayOffset(), data.limit() - data.position());
        } else {
            throw new Zip64RequiredException("archive's size exceeds the limit of 4GByte.");
        }
    }

    protected void writeZip64CentralDirectory() throws IOException {
        if (this.zip64Mode != Zip64Mode.Never) {
            if (!this.hasUsedZip64 && (this.cdOffset >= 4294967295L || this.cdLength >= 4294967295L || this.entries.size() >= SupportMenu.USER_MASK)) {
                this.hasUsedZip64 = true;
            }
            if (this.hasUsedZip64) {
                long offset = this.written;
                writeOut(ZIP64_EOCD_SIG);
                writeOut(ZipEightByteInteger.getBytes(44));
                writeOut(ZipShort.getBytes(45));
                writeOut(ZipShort.getBytes(45));
                writeOut(LZERO);
                writeOut(LZERO);
                byte[] num = ZipEightByteInteger.getBytes((long) this.entries.size());
                writeOut(num);
                writeOut(num);
                writeOut(ZipEightByteInteger.getBytes(this.cdLength));
                writeOut(ZipEightByteInteger.getBytes(this.cdOffset));
                writeOut(ZIP64_EOCD_LOC_SIG);
                writeOut(LZERO);
                writeOut(ZipEightByteInteger.getBytes(offset));
                writeOut(ONE);
            }
        }
    }

    protected final void writeOut(byte[] data) throws IOException {
        writeOut(data, 0, data.length);
    }

    protected final void writeOut(byte[] data, int offset, int length) throws IOException {
        if (this.raf != null) {
            this.raf.write(data, offset, length);
        } else {
            this.out.write(data, offset, length);
        }
    }

    private void deflateUntilInputIsNeeded() throws IOException {
        while (!this.def.needsInput()) {
            deflate();
        }
    }

    private void writeVersionNeededToExtractAndGeneralPurposeBits(int zipMethod, boolean utfFallback, boolean zip64) throws IOException {
        boolean z;
        int versionNeededToExtract = 10;
        GeneralPurposeBit b = new GeneralPurposeBit();
        if (!this.useUTF8Flag) {
            if (!utfFallback) {
                z = false;
                b.useUTF8ForNames(z);
                if (zipMethod == 8 && this.raf == null) {
                    versionNeededToExtract = 20;
                    b.useDataDescriptor(true);
                }
                if (zip64) {
                    versionNeededToExtract = 45;
                }
                writeOut(ZipShort.getBytes(versionNeededToExtract));
                writeOut(b.encode());
            }
        }
        z = true;
        b.useUTF8ForNames(z);
        versionNeededToExtract = 20;
        b.useDataDescriptor(true);
        if (zip64) {
            versionNeededToExtract = 45;
        }
        writeOut(ZipShort.getBytes(versionNeededToExtract));
        writeOut(b.encode());
    }

    public ArchiveEntry createArchiveEntry(File inputFile, String entryName) throws IOException {
        if (!this.finished) {
            return new ZipArchiveEntry(inputFile, entryName);
        }
        throw new IOException("Stream has already been finished");
    }

    private Zip64ExtendedInformationExtraField getZip64Extra(ZipArchiveEntry ze) {
        if (this.entry != null) {
            ZipArchiveOutputStream$CurrentEntry.access$502(this.entry, this.hasUsedZip64 ^ true);
        }
        this.hasUsedZip64 = true;
        Zip64ExtendedInformationExtraField z64 = (Zip64ExtendedInformationExtraField) ze.getExtraField(Zip64ExtendedInformationExtraField.HEADER_ID);
        if (z64 == null) {
            z64 = new Zip64ExtendedInformationExtraField();
        }
        ze.addAsFirstExtraField(z64);
        return z64;
    }

    private boolean hasZip64Extra(ZipArchiveEntry ze) {
        return ze.getExtraField(Zip64ExtendedInformationExtraField.HEADER_ID) != null;
    }

    private Zip64Mode getEffectiveZip64Mode(ZipArchiveEntry ze) {
        if (this.zip64Mode == Zip64Mode.AsNeeded && this.raf == null && ze.getMethod() == 8) {
            if (ze.getSize() == -1) {
                return Zip64Mode.Never;
            }
        }
        return this.zip64Mode;
    }

    private ZipEncoding getEntryEncoding(ZipArchiveEntry ze) {
        return (this.zipEncoding.canEncode(ze.getName()) || !this.fallbackToUTF8) ? this.zipEncoding : ZipEncodingHelper.UTF8_ZIP_ENCODING;
    }

    private ByteBuffer getName(ZipArchiveEntry ze) throws IOException {
        return getEntryEncoding(ze).encode(ze.getName());
    }

    void destroy() throws IOException {
        if (this.raf != null) {
            this.raf.close();
        }
        if (this.out != null) {
            this.out.close();
        }
    }
}
