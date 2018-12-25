package org.apache.commons.compress.archivers.sevenz;

import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.BitSet;
import java.util.LinkedList;
import java.util.zip.CRC32;
import org.apache.commons.compress.utils.BoundedInputStream;
import org.apache.commons.compress.utils.CRC32VerifyingInputStream;
import org.apache.commons.compress.utils.CharsetNames;
import org.apache.commons.compress.utils.IOUtils;
import org.billthefarmer.mididriver.GeneralMidiConstants;

public class SevenZFile implements Closeable {
    static final int SIGNATURE_HEADER_SIZE = 32;
    static final byte[] sevenZSignature = new byte[]{(byte) 55, GeneralMidiConstants.SEASHORE, (byte) -68, (byte) -81, GeneralMidiConstants.SYNTH_BASS_1, (byte) 28};
    private final Archive archive;
    private int currentEntryIndex;
    private InputStream currentEntryInputStream;
    private int currentFolderIndex;
    private InputStream currentFolderInputStream;
    private RandomAccessFile file;
    private byte[] password;

    public SevenZFile(File filename, byte[] password) throws IOException {
        this.currentEntryIndex = -1;
        this.currentFolderIndex = -1;
        boolean z = null;
        this.currentFolderInputStream = null;
        this.currentEntryInputStream = null;
        boolean succeeded = false;
        this.file = new RandomAccessFile(filename, "r");
        try {
            this.archive = readHeaders(password);
            if (password != null) {
                this.password = new byte[password.length];
                System.arraycopy(password, 0, this.password, 0, password.length);
            } else {
                this.password = null;
            }
            z = true;
        } finally {
            succeeded = 
/*
Method generation error in method: org.apache.commons.compress.archivers.sevenz.SevenZFile.<init>(java.io.File, byte[]):void, dex: classes3.dex
jadx.core.utils.exceptions.CodegenException: Error generate insn: ?: MERGE  (r1_1 'succeeded' boolean) = (r1_0 'succeeded' boolean), (r0_6 'z' boolean) in method: org.apache.commons.compress.archivers.sevenz.SevenZFile.<init>(java.io.File, byte[]):void, dex: classes3.dex
	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:226)
	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:203)
	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:100)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:50)
	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:87)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:53)
	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:93)
	at jadx.core.codegen.RegionGen.makeTryCatch(RegionGen.java:299)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:63)
	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:87)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:53)
	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:187)
	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:320)
	at jadx.core.codegen.ClassGen.addMethods(ClassGen.java:257)
	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:220)
	at jadx.core.codegen.ClassGen.addClassCode(ClassGen.java:110)
	at jadx.core.codegen.ClassGen.makeClass(ClassGen.java:75)
	at jadx.core.codegen.CodeGen.visit(CodeGen.java:12)
	at jadx.core.ProcessClass.process(ProcessClass.java:40)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:282)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:200)
	at jadx.api.JadxDecompiler$$Lambda$8/1115170891.run(Unknown Source)
Caused by: jadx.core.utils.exceptions.CodegenException: MERGE can be used only in fallback mode
	at jadx.core.codegen.InsnGen.fallbackOnlyInsn(InsnGen.java:537)
	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:509)
	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:220)
	... 22 more

*/

            public SevenZFile(File filename) throws IOException {
                this(filename, null);
            }

            public void close() throws IOException {
                if (this.file != null) {
                    try {
                        this.file.close();
                    } finally {
                        this.file = null;
                        if (this.password != null) {
                            Arrays.fill(this.password, (byte) 0);
                        }
                        this.password = null;
                    }
                }
            }

            public SevenZArchiveEntry getNextEntry() throws IOException {
                if (this.currentEntryIndex >= this.archive.files.length - 1) {
                    return null;
                }
                this.currentEntryIndex++;
                SevenZArchiveEntry entry = this.archive.files[this.currentEntryIndex];
                buildDecodingStream();
                return entry;
            }

            private Archive readHeaders(byte[] password) throws IOException {
                byte[] signature = new byte[6];
                this.file.readFully(signature);
                if (Arrays.equals(signature, sevenZSignature)) {
                    byte archiveVersionMajor = r0.file.readByte();
                    byte archiveVersionMinor = r0.file.readByte();
                    if (archiveVersionMajor != (byte) 0) {
                        throw new IOException(String.format("Unsupported 7z version (%d,%d)", new Object[]{Byte.valueOf(archiveVersionMajor), Byte.valueOf(archiveVersionMinor)}));
                    }
                    StartHeader startHeader = readStartHeader(((long) Integer.reverseBytes(r0.file.readInt())) & 4294967295L);
                    int nextHeaderSizeInt = (int) startHeader.nextHeaderSize;
                    if (((long) nextHeaderSizeInt) != startHeader.nextHeaderSize) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("cannot handle nextHeaderSize ");
                        stringBuilder.append(startHeader.nextHeaderSize);
                        throw new IOException(stringBuilder.toString());
                    }
                    StartHeader startHeader2 = startHeader;
                    r0.file.seek(startHeader.nextHeaderOffset + 32);
                    byte[] nextHeader = new byte[nextHeaderSizeInt];
                    r0.file.readFully(nextHeader);
                    CRC32 crc = new CRC32();
                    crc.update(nextHeader);
                    if (startHeader2.nextHeaderCrc != crc.getValue()) {
                        throw new IOException("NextHeader CRC mismatch");
                    }
                    DataInputStream nextHeaderInputStream = new DataInputStream(new ByteArrayInputStream(nextHeader));
                    Archive archive = new Archive();
                    int nid = nextHeaderInputStream.readUnsignedByte();
                    if (nid == 23) {
                        nextHeaderInputStream = readEncodedHeader(nextHeaderInputStream, archive, password);
                        archive = new Archive();
                        nid = nextHeaderInputStream.readUnsignedByte();
                    } else {
                        byte[] bArr = password;
                    }
                    if (nid == 1) {
                        readHeader(nextHeaderInputStream, archive);
                        nextHeaderInputStream.close();
                        return archive;
                    }
                    throw new IOException("Broken or unsupported archive: no Header");
                }
                throw new IOException("Bad 7z signature");
            }

            private StartHeader readStartHeader(long startHeaderCrc) throws IOException {
                StartHeader startHeader = new StartHeader();
                DataInputStream dataInputStream = null;
                try {
                    dataInputStream = new DataInputStream(new CRC32VerifyingInputStream(new BoundedRandomAccessFileInputStream(this.file, 20), 20, startHeaderCrc));
                    startHeader.nextHeaderOffset = Long.reverseBytes(dataInputStream.readLong());
                    startHeader.nextHeaderSize = Long.reverseBytes(dataInputStream.readLong());
                    startHeader.nextHeaderCrc = ((long) Integer.reverseBytes(dataInputStream.readInt())) & 4294967295L;
                    return startHeader;
                } finally {
                    if (dataInputStream != null) {
                        dataInputStream.close();
                    }
                }
            }

            private void readHeader(DataInput header, Archive archive) throws IOException {
                int nid = header.readUnsignedByte();
                if (nid == 2) {
                    readArchiveProperties(header);
                    nid = header.readUnsignedByte();
                }
                if (nid == 3) {
                    throw new IOException("Additional streams unsupported");
                }
                if (nid == 4) {
                    readStreamsInfo(header, archive);
                    nid = header.readUnsignedByte();
                }
                if (nid == 5) {
                    readFilesInfo(header, archive);
                    nid = header.readUnsignedByte();
                }
                if (nid != 0) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Badly terminated header, found ");
                    stringBuilder.append(nid);
                    throw new IOException(stringBuilder.toString());
                }
            }

            private void readArchiveProperties(DataInput input) throws IOException {
                int nid = input.readUnsignedByte();
                while (nid != 0) {
                    input.readFully(new byte[((int) readUint64(input))]);
                    nid = input.readUnsignedByte();
                }
            }

            private DataInputStream readEncodedHeader(DataInputStream header, Archive archive, byte[] password) throws IOException {
                byte[] bArr;
                Archive archive2 = archive;
                readStreamsInfo(header, archive);
                Folder folder = archive2.folders[0];
                this.file.seek((archive2.packPos + 32) + 0);
                InputStream inputStreamStack = new BoundedRandomAccessFileInputStream(this.file, archive2.packSizes[0]);
                for (Coder coder : folder.getOrderedCoders()) {
                    if (coder.numInStreams == 1) {
                        if (coder.numOutStreams == 1) {
                            inputStreamStack = Coders.addDecoder(inputStreamStack, folder.getUnpackSizeForCoder(coder), coder, password);
                        }
                    }
                    bArr = password;
                    throw new IOException("Multi input/output stream coders are not yet supported");
                }
                bArr = password;
                if (folder.hasCrc) {
                    inputStreamStack = new CRC32VerifyingInputStream(inputStreamStack, folder.getUnpackSize(), folder.crc);
                }
                byte[] nextHeader = new byte[((int) folder.getUnpackSize())];
                DataInputStream nextHeaderInputStream = new DataInputStream(inputStreamStack);
                try {
                    nextHeaderInputStream.readFully(nextHeader);
                    return new DataInputStream(new ByteArrayInputStream(nextHeader));
                } finally {
                    nextHeaderInputStream.close();
                }
            }

            private void readStreamsInfo(DataInput header, Archive archive) throws IOException {
                int nid = header.readUnsignedByte();
                if (nid == 6) {
                    readPackInfo(header, archive);
                    nid = header.readUnsignedByte();
                }
                if (nid == 7) {
                    readUnpackInfo(header, archive);
                    nid = header.readUnsignedByte();
                } else {
                    archive.folders = new Folder[0];
                }
                if (nid == 8) {
                    readSubStreamsInfo(header, archive);
                    nid = header.readUnsignedByte();
                }
                if (nid != 0) {
                    throw new IOException("Badly terminated StreamsInfo");
                }
            }

            private void readPackInfo(DataInput header, Archive archive) throws IOException {
                archive.packPos = readUint64(header);
                long numPackStreams = readUint64(header);
                int nid = header.readUnsignedByte();
                if (nid == 9) {
                    archive.packSizes = new long[((int) numPackStreams)];
                    for (int i = 0; i < archive.packSizes.length; i++) {
                        archive.packSizes[i] = readUint64(header);
                    }
                    nid = header.readUnsignedByte();
                }
                if (nid == 10) {
                    archive.packCrcsDefined = readAllOrBits(header, (int) numPackStreams);
                    archive.packCrcs = new long[((int) numPackStreams)];
                    for (int i2 = 0; i2 < ((int) numPackStreams); i2++) {
                        if (archive.packCrcsDefined.get(i2)) {
                            archive.packCrcs[i2] = ((long) Integer.reverseBytes(header.readInt())) & 4294967295L;
                        }
                    }
                    nid = header.readUnsignedByte();
                }
                if (nid != 0) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Badly terminated PackInfo (");
                    stringBuilder.append(nid);
                    stringBuilder.append(")");
                    throw new IOException(stringBuilder.toString());
                }
            }

            private void readUnpackInfo(DataInput header, Archive archive) throws IOException {
                int nid = header.readUnsignedByte();
                if (nid != 11) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Expected kFolder, got ");
                    stringBuilder.append(nid);
                    throw new IOException(stringBuilder.toString());
                }
                long numFolders = readUint64(header);
                Folder[] folders = new Folder[((int) numFolders)];
                archive.folders = folders;
                if (header.readUnsignedByte() != 0) {
                    throw new IOException("External unsupported");
                }
                for (int i = 0; i < ((int) numFolders); i++) {
                    folders[i] = readFolder(header);
                }
                nid = header.readUnsignedByte();
                if (nid != 12) {
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("Expected kCodersUnpackSize, got ");
                    stringBuilder2.append(nid);
                    throw new IOException(stringBuilder2.toString());
                }
                for (Folder folder : folders) {
                    folder.unpackSizes = new long[((int) folder.totalOutputStreams)];
                    for (int i2 = 0; ((long) i2) < folder.totalOutputStreams; i2++) {
                        folder.unpackSizes[i2] = readUint64(header);
                    }
                }
                nid = header.readUnsignedByte();
                if (nid == 10) {
                    BitSet crcsDefined = readAllOrBits(header, (int) numFolders);
                    int i3 = 0;
                    while (i3 < ((int) numFolders)) {
                        BitSet crcsDefined2;
                        if (crcsDefined.get(i3)) {
                            folders[i3].hasCrc = true;
                            crcsDefined2 = crcsDefined;
                            folders[i3].crc = ((long) Integer.reverseBytes(header.readInt())) & 4294967295L;
                        } else {
                            crcsDefined2 = crcsDefined;
                            folders[i3].hasCrc = false;
                        }
                        i3++;
                        crcsDefined = crcsDefined2;
                    }
                    nid = header.readUnsignedByte();
                } else {
                    SevenZFile sevenZFile = this;
                    DataInput dataInput = header;
                }
                if (nid != 0) {
                    throw new IOException("Badly terminated UnpackInfo");
                }
            }

            private void readSubStreamsInfo(DataInput header, Archive archive) throws IOException {
                int totalUnpackStreams;
                Archive archive2 = archive;
                for (Folder folder : archive2.folders) {
                    folder.numUnpackSubStreams = 1;
                }
                int totalUnpackStreams2 = archive2.folders.length;
                int len$ = header.readUnsignedByte();
                if (len$ == 13) {
                    totalUnpackStreams = 0;
                    for (Folder folder2 : archive2.folders) {
                        long numStreams = readUint64(header);
                        folder2.numUnpackSubStreams = (int) numStreams;
                        totalUnpackStreams = (int) (((long) totalUnpackStreams) + numStreams);
                    }
                    len$ = header.readUnsignedByte();
                    totalUnpackStreams2 = totalUnpackStreams;
                }
                SubStreamsInfo subStreamsInfo = new SubStreamsInfo();
                subStreamsInfo.unpackSizes = new long[totalUnpackStreams2];
                subStreamsInfo.hasCrc = new BitSet(totalUnpackStreams2);
                subStreamsInfo.crcs = new long[totalUnpackStreams2];
                int nextUnpackStream = 0;
                for (Folder folder3 : archive2.folders) {
                    if (folder3.numUnpackSubStreams != 0) {
                        int nextUnpackStream2;
                        long sum = 0;
                        if (len$ == 9) {
                            nextUnpackStream2 = nextUnpackStream;
                            nextUnpackStream = 0;
                            while (nextUnpackStream < folder3.numUnpackSubStreams - 1) {
                                long size = readUint64(header);
                                int nextUnpackStream3 = nextUnpackStream2 + 1;
                                subStreamsInfo.unpackSizes[nextUnpackStream2] = size;
                                nextUnpackStream++;
                                nextUnpackStream2 = nextUnpackStream3;
                                sum += size;
                            }
                            nextUnpackStream = nextUnpackStream2;
                        }
                        nextUnpackStream2 = nextUnpackStream + 1;
                        subStreamsInfo.unpackSizes[nextUnpackStream] = folder3.getUnpackSize() - sum;
                        nextUnpackStream = nextUnpackStream2;
                    }
                }
                if (len$ == 9) {
                    len$ = header.readUnsignedByte();
                }
                int len$2 = 0;
                for (Folder folder4 : archive2.folders) {
                    if (folder4.numUnpackSubStreams != 1 || !folder4.hasCrc) {
                        len$2 += folder4.numUnpackSubStreams;
                    }
                }
                int totalUnpackStreams3;
                int nid;
                if (len$ == 10) {
                    int i;
                    BitSet hasMissingCrc = readAllOrBits(header, len$2);
                    long[] missingCrcs = new long[len$2];
                    for (i = 0; i < len$2; i++) {
                        if (hasMissingCrc.get(i)) {
                            missingCrcs[i] = ((long) Integer.reverseBytes(header.readInt())) & 4294967295L;
                        }
                    }
                    Folder[] arr$ = archive2.folders;
                    int len$3 = arr$.length;
                    int nextMissingCrc = 0;
                    int nextCrc = 0;
                    i = 0;
                    while (i < len$3) {
                        Folder folder5 = arr$[i];
                        totalUnpackStreams3 = totalUnpackStreams2;
                        nid = len$;
                        if (folder5.numUnpackSubStreams != 1 || folder5.hasCrc == 0) {
                            for (totalUnpackStreams2 = 0; totalUnpackStreams2 < folder5.numUnpackSubStreams; totalUnpackStreams2++) {
                                subStreamsInfo.hasCrc.set(nextCrc, hasMissingCrc.get(nextMissingCrc));
                                subStreamsInfo.crcs[nextCrc] = missingCrcs[nextMissingCrc];
                                nextCrc++;
                                nextMissingCrc++;
                            }
                        } else {
                            subStreamsInfo.hasCrc.set(nextCrc, true);
                            subStreamsInfo.crcs[nextCrc] = folder5.crc;
                            nextCrc++;
                        }
                        i++;
                        totalUnpackStreams2 = totalUnpackStreams3;
                        len$ = nid;
                        SevenZFile sevenZFile = this;
                    }
                    nid = len$;
                    len$ = header.readUnsignedByte();
                } else {
                    DataInput dataInput = header;
                    totalUnpackStreams3 = totalUnpackStreams2;
                    nid = len$;
                }
                if (len$ != 0) {
                    throw new IOException("Badly terminated SubStreamsInfo");
                }
                archive2.subStreamsInfo = subStreamsInfo;
            }

            private Folder readFolder(DataInput header) throws IOException {
                DataInput dataInput = header;
                Folder folder = new Folder();
                long numCoders = readUint64(header);
                Coder[] coders = new Coder[((int) numCoders)];
                long totalOutStreams = 0;
                long totalInStreams = 0;
                int i = 0;
                while (i < coders.length) {
                    long numCoders2;
                    coders[i] = new Coder();
                    int bits = header.readUnsignedByte();
                    int idSize = bits & 15;
                    boolean z = true;
                    boolean isSimple = (bits & 16) == 0;
                    boolean hasAttributes = (bits & 32) != 0;
                    if ((bits & 128) == 0) {
                        z = false;
                    }
                    boolean moreAlternativeMethods = z;
                    coders[i].decompressionMethodId = new byte[idSize];
                    dataInput.readFully(coders[i].decompressionMethodId);
                    if (isSimple) {
                        numCoders2 = numCoders;
                        coders[i].numInStreams = 1;
                        coders[i].numOutStreams = 1;
                    } else {
                        numCoders2 = numCoders;
                        coders[i].numInStreams = readUint64(header);
                        coders[i].numOutStreams = readUint64(header);
                    }
                    long totalInStreams2 = totalInStreams + coders[i].numInStreams;
                    totalInStreams = totalOutStreams + coders[i].numOutStreams;
                    if (hasAttributes) {
                        coders[i].properties = new byte[((int) readUint64(header))];
                        dataInput.readFully(coders[i].properties);
                    }
                    if (moreAlternativeMethods) {
                        throw new IOException("Alternative methods are unsupported, please report. The reference implementation doesn't support them either.");
                    }
                    i++;
                    totalOutStreams = totalInStreams;
                    totalInStreams = totalInStreams2;
                    numCoders = numCoders2;
                }
                folder.coders = coders;
                folder.totalInputStreams = totalInStreams;
                folder.totalOutputStreams = totalOutStreams;
                if (totalOutStreams == 0) {
                    throw new IOException("Total output streams can't be 0");
                }
                long numBindPairs = totalOutStreams - 1;
                BindPair[] bindPairs = new BindPair[((int) numBindPairs)];
                for (int i2 = 0; i2 < bindPairs.length; i2++) {
                    bindPairs[i2] = new BindPair();
                    bindPairs[i2].inIndex = readUint64(header);
                    bindPairs[i2].outIndex = readUint64(header);
                }
                folder.bindPairs = bindPairs;
                if (totalInStreams < numBindPairs) {
                    throw new IOException("Total input streams can't be less than the number of bind pairs");
                }
                totalInStreams2 = totalInStreams - numBindPairs;
                long[] packedStreams = new long[((int) totalInStreams2)];
                if (totalInStreams2 != 1) {
                    int i3 = 0;
                    while (true) {
                        i = i3;
                        if (i >= ((int) totalInStreams2)) {
                            break;
                        }
                        packedStreams[i] = readUint64(header);
                        i3 = i + 1;
                    }
                } else {
                    i = 0;
                    while (i < ((int) totalInStreams)) {
                        if (folder.findBindPairForInStream(i) < 0) {
                            break;
                        }
                        i++;
                    }
                    if (i == ((int) totalInStreams)) {
                        throw new IOException("Couldn't find stream's bind pair index");
                    }
                    packedStreams[0] = (long) i;
                }
                folder.packedStreams = packedStreams;
                return folder;
            }

            private BitSet readAllOrBits(DataInput header, int size) throws IOException {
                if (header.readUnsignedByte() == 0) {
                    return readBits(header, size);
                }
                BitSet bits = new BitSet(size);
                for (int i = 0; i < size; i++) {
                    bits.set(i, true);
                }
                return bits;
            }

            private BitSet readBits(DataInput header, int size) throws IOException {
                BitSet bits = new BitSet(size);
                int cache = 0;
                int mask = 0;
                for (int i = 0; i < size; i++) {
                    if (mask == 0) {
                        mask = 128;
                        cache = header.readUnsignedByte();
                    }
                    bits.set(i, (cache & mask) != 0);
                    mask >>>= 1;
                }
                return bits;
            }

            private void readFilesInfo(DataInput header, Archive archive) throws IOException {
                int i;
                SevenZFile sevenZFile = this;
                DataInput dataInput = header;
                Archive archive2 = archive;
                long numFiles = readUint64(header);
                SevenZArchiveEntry[] files = new SevenZArchiveEntry[((int) numFiles)];
                boolean z = false;
                for (i = 0; i < files.length; i++) {
                    files[i] = new SevenZArchiveEntry();
                }
                BitSet isEmptyStream = null;
                BitSet isEmptyFile = null;
                BitSet isAnti = null;
                while (true) {
                    int propertyType = header.readUnsignedByte();
                    int emptyFileCounter;
                    int nonEmptyFileCounter;
                    BitSet isEmptyStream2;
                    if (propertyType == 0) {
                        emptyFileCounter = 0;
                        nonEmptyFileCounter = 0;
                        propertyType = 0;
                        while (propertyType < files.length) {
                            boolean z2;
                            SevenZArchiveEntry sevenZArchiveEntry;
                            boolean z3;
                            SevenZArchiveEntry sevenZArchiveEntry2 = files[propertyType];
                            if (isEmptyStream != null) {
                                if (isEmptyStream.get(propertyType)) {
                                    z2 = false;
                                    sevenZArchiveEntry2.setHasStream(z2);
                                    if (files[propertyType].hasStream()) {
                                        isEmptyStream2 = isEmptyStream;
                                        sevenZArchiveEntry = files[propertyType];
                                        if (isEmptyFile != null) {
                                            if (!isEmptyFile.get(emptyFileCounter)) {
                                                z3 = false;
                                                sevenZArchiveEntry.setDirectory(z3);
                                                files[propertyType].setAntiItem(isAnti != null ? false : isAnti.get(emptyFileCounter));
                                                files[propertyType].setHasCrc(false);
                                                files[propertyType].setSize(0);
                                                emptyFileCounter++;
                                            }
                                        }
                                        z3 = true;
                                        sevenZArchiveEntry.setDirectory(z3);
                                        if (isAnti != null) {
                                        }
                                        files[propertyType].setAntiItem(isAnti != null ? false : isAnti.get(emptyFileCounter));
                                        files[propertyType].setHasCrc(false);
                                        files[propertyType].setSize(0);
                                        emptyFileCounter++;
                                    } else {
                                        files[propertyType].setDirectory(z);
                                        files[propertyType].setAntiItem(z);
                                        files[propertyType].setHasCrc(archive2.subStreamsInfo.hasCrc.get(nonEmptyFileCounter));
                                        isEmptyStream2 = isEmptyStream;
                                        files[propertyType].setCrcValue(archive2.subStreamsInfo.crcs[nonEmptyFileCounter]);
                                        files[propertyType].setSize(archive2.subStreamsInfo.unpackSizes[nonEmptyFileCounter]);
                                        nonEmptyFileCounter++;
                                    }
                                    propertyType++;
                                    isEmptyStream = isEmptyStream2;
                                    z = false;
                                }
                            }
                            z2 = true;
                            sevenZArchiveEntry2.setHasStream(z2);
                            if (files[propertyType].hasStream()) {
                                isEmptyStream2 = isEmptyStream;
                                sevenZArchiveEntry = files[propertyType];
                                if (isEmptyFile != null) {
                                    if (!isEmptyFile.get(emptyFileCounter)) {
                                        z3 = false;
                                        sevenZArchiveEntry.setDirectory(z3);
                                        if (isAnti != null) {
                                        }
                                        files[propertyType].setAntiItem(isAnti != null ? false : isAnti.get(emptyFileCounter));
                                        files[propertyType].setHasCrc(false);
                                        files[propertyType].setSize(0);
                                        emptyFileCounter++;
                                    }
                                }
                                z3 = true;
                                sevenZArchiveEntry.setDirectory(z3);
                                if (isAnti != null) {
                                }
                                files[propertyType].setAntiItem(isAnti != null ? false : isAnti.get(emptyFileCounter));
                                files[propertyType].setHasCrc(false);
                                files[propertyType].setSize(0);
                                emptyFileCounter++;
                            } else {
                                files[propertyType].setDirectory(z);
                                files[propertyType].setAntiItem(z);
                                files[propertyType].setHasCrc(archive2.subStreamsInfo.hasCrc.get(nonEmptyFileCounter));
                                isEmptyStream2 = isEmptyStream;
                                files[propertyType].setCrcValue(archive2.subStreamsInfo.crcs[nonEmptyFileCounter]);
                                files[propertyType].setSize(archive2.subStreamsInfo.unpackSizes[nonEmptyFileCounter]);
                                nonEmptyFileCounter++;
                            }
                            propertyType++;
                            isEmptyStream = isEmptyStream2;
                            z = false;
                        }
                        archive2.files = files;
                        calculateStreamMap(archive2);
                        return;
                    }
                    long numFiles2;
                    isEmptyStream2 = isEmptyStream;
                    long size = readUint64(header);
                    int i2;
                    BitSet timesDefined;
                    BitSet timesDefined2;
                    switch (propertyType) {
                        case 14:
                            numFiles2 = numFiles;
                            isEmptyStream = isEmptyStream2;
                            isEmptyStream = readBits(dataInput, files.length);
                            continue;
                        case 15:
                            numFiles2 = numFiles;
                            isEmptyStream = isEmptyStream2;
                            if (isEmptyStream == null) {
                                throw new IOException("Header format error: kEmptyStream must appear before kEmptyFile");
                            }
                            isEmptyFile = readBits(dataInput, isEmptyStream.cardinality());
                            continue;
                        case 16:
                            numFiles2 = numFiles;
                            if (isEmptyStream2 == null) {
                                throw new IOException("Header format error: kEmptyStream must appear before kAnti");
                            }
                            isEmptyStream = isEmptyStream2;
                            isAnti = readBits(dataInput, isEmptyStream.cardinality());
                            continue;
                        case 17:
                            numFiles2 = numFiles;
                            int external = header.readUnsignedByte();
                            if (external != 0) {
                                throw new IOException("Not implemented");
                            } else if (((size - 1) & 1) != 0) {
                                throw new IOException("File names length invalid");
                            } else {
                                numFiles = new byte[((int) (size - 1))];
                                dataInput.readFully(numFiles);
                                int nextName = 0;
                                i = 0;
                                i2 = 0;
                                while (i2 < numFiles.length) {
                                    int external2;
                                    if (numFiles[i2] == (byte) 0 && numFiles[i2 + 1] == (byte) 0) {
                                        nonEmptyFileCounter = i + 1;
                                        external2 = external;
                                        files[i].setName(new String(numFiles, nextName, i2 - nextName, CharsetNames.UTF_16LE));
                                        nextName = i2 + 2;
                                        i = nonEmptyFileCounter;
                                    } else {
                                        external2 = external;
                                    }
                                    i2 += 2;
                                    external = external2;
                                }
                                if (nextName == numFiles.length) {
                                    if (i == files.length) {
                                        break;
                                    }
                                }
                                throw new IOException("Error parsing file names");
                            }
                        case 18:
                            numFiles2 = numFiles;
                            timesDefined = readAllOrBits(dataInput, files.length);
                            if (header.readUnsignedByte() == null) {
                                for (i2 = 0; i2 < files.length; i2++) {
                                    files[i2].setHasCreationDate(timesDefined.get(i2));
                                    if (files[i2].getHasCreationDate()) {
                                        files[i2].setCreationDate(Long.reverseBytes(header.readLong()));
                                    }
                                }
                                break;
                            }
                            throw new IOException("Unimplemented");
                        case 19:
                            numFiles2 = numFiles;
                            timesDefined = readAllOrBits(dataInput, files.length);
                            if (header.readUnsignedByte() == null) {
                                for (i2 = 0; i2 < files.length; i2++) {
                                    files[i2].setHasAccessDate(timesDefined.get(i2));
                                    if (files[i2].getHasAccessDate()) {
                                        files[i2].setAccessDate(Long.reverseBytes(header.readLong()));
                                    }
                                }
                                break;
                            }
                            throw new IOException("Unimplemented");
                        case 20:
                            timesDefined2 = readAllOrBits(dataInput, files.length);
                            if (header.readUnsignedByte() == 0) {
                                nonEmptyFileCounter = 0;
                                while (nonEmptyFileCounter < files.length) {
                                    files[nonEmptyFileCounter].setHasLastModifiedDate(timesDefined2.get(nonEmptyFileCounter));
                                    if (files[nonEmptyFileCounter].getHasLastModifiedDate()) {
                                        numFiles2 = numFiles;
                                        files[nonEmptyFileCounter].setLastModifiedDate(Long.reverseBytes(header.readLong()));
                                    } else {
                                        numFiles2 = numFiles;
                                    }
                                    nonEmptyFileCounter++;
                                    numFiles = numFiles2;
                                    archive2 = archive;
                                }
                                numFiles2 = numFiles;
                                break;
                            }
                            throw new IOException("Unimplemented");
                        case 21:
                            timesDefined2 = readAllOrBits(dataInput, files.length);
                            if (header.readUnsignedByte() == 0) {
                                for (emptyFileCounter = 0; emptyFileCounter < files.length; emptyFileCounter++) {
                                    files[emptyFileCounter].setHasWindowsAttributes(timesDefined2.get(emptyFileCounter));
                                    if (files[emptyFileCounter].getHasWindowsAttributes()) {
                                        files[emptyFileCounter].setWindowsAttributes(Integer.reverseBytes(header.readInt()));
                                    }
                                }
                                break;
                            }
                            throw new IOException("Unimplemented");
                        case 24:
                            throw new IOException("kStartPos is unsupported, please report");
                        case 25:
                            if (skipBytesFully(dataInput, size) < size) {
                                throw new IOException("Incomplete kDummy property");
                            }
                            break;
                        default:
                            numFiles2 = numFiles;
                            isEmptyStream = isEmptyStream2;
                            if (skipBytesFully(dataInput, size) < size) {
                                numFiles = new StringBuilder();
                                numFiles.append("Incomplete property of type ");
                                numFiles.append(propertyType);
                                throw new IOException(numFiles.toString());
                            }
                            continue;
                    }
                    numFiles2 = numFiles;
                    isEmptyStream = isEmptyStream2;
                    numFiles = numFiles2;
                    archive2 = archive;
                    z = false;
                }
            }

            private void calculateStreamMap(Archive archive) throws IOException {
                int i;
                StreamMap streamMap = new StreamMap();
                int numFolders = archive.folders != null ? archive.folders.length : 0;
                streamMap.folderFirstPackStreamIndex = new int[numFolders];
                int nextFolderPackStreamIndex = 0;
                for (i = 0; i < numFolders; i++) {
                    streamMap.folderFirstPackStreamIndex[i] = nextFolderPackStreamIndex;
                    nextFolderPackStreamIndex += archive.folders[i].packedStreams.length;
                }
                i = archive.packSizes != null ? archive.packSizes.length : 0;
                streamMap.packStreamOffsets = new long[i];
                long nextPackStreamOffset = 0;
                int i2 = 0;
                while (i2 < i) {
                    streamMap.packStreamOffsets[i2] = nextPackStreamOffset;
                    i2++;
                    nextPackStreamOffset += archive.packSizes[i2];
                }
                streamMap.folderFirstFileIndex = new int[numFolders];
                streamMap.fileFolderIndex = new int[archive.files.length];
                i2 = 0;
                int nextFolderUnpackStreamIndex = 0;
                for (int i3 = 0; i3 < archive.files.length; i3++) {
                    if (archive.files[i3].hasStream() || nextFolderUnpackStreamIndex != 0) {
                        if (nextFolderUnpackStreamIndex == 0) {
                            while (i2 < archive.folders.length) {
                                streamMap.folderFirstFileIndex[i2] = i3;
                                if (archive.folders[i2].numUnpackSubStreams > 0) {
                                    break;
                                }
                                i2++;
                            }
                            if (i2 >= archive.folders.length) {
                                throw new IOException("Too few folders in archive");
                            }
                        }
                        streamMap.fileFolderIndex[i3] = i2;
                        if (archive.files[i3].hasStream()) {
                            nextFolderUnpackStreamIndex++;
                            if (nextFolderUnpackStreamIndex >= archive.folders[i2].numUnpackSubStreams) {
                                i2++;
                                nextFolderUnpackStreamIndex = 0;
                            }
                        }
                    } else {
                        streamMap.fileFolderIndex[i3] = -1;
                    }
                }
                archive.streamMap = streamMap;
            }

            private void buildDecodingStream() throws IOException {
                int folderIndex = this.archive.streamMap.fileFolderIndex[this.currentEntryIndex];
                if (folderIndex < 0) {
                    this.currentEntryInputStream = new BoundedInputStream(new ByteArrayInputStream(new byte[0]), 0);
                    return;
                }
                SevenZArchiveEntry file = this.archive.files[this.currentEntryIndex];
                if (this.currentFolderIndex == folderIndex) {
                    drainPreviousEntry();
                    file.setContentMethods(this.archive.files[this.currentEntryIndex - 1].getContentMethods());
                } else {
                    this.currentFolderIndex = folderIndex;
                    if (this.currentFolderInputStream != null) {
                        this.currentFolderInputStream.close();
                        this.currentFolderInputStream = null;
                    }
                    Folder folder = this.archive.folders[folderIndex];
                    int firstPackStreamIndex = this.archive.streamMap.folderFirstPackStreamIndex[folderIndex];
                    this.currentFolderInputStream = buildDecoderStack(folder, (this.archive.packPos + 32) + this.archive.streamMap.packStreamOffsets[firstPackStreamIndex], firstPackStreamIndex, file);
                }
                InputStream fileStream = new BoundedInputStream(this.currentFolderInputStream, file.getSize());
                if (file.getHasCrc()) {
                    this.currentEntryInputStream = new CRC32VerifyingInputStream(fileStream, file.getSize(), file.getCrcValue());
                } else {
                    this.currentEntryInputStream = fileStream;
                }
            }

            private void drainPreviousEntry() throws IOException {
                if (this.currentEntryInputStream != null) {
                    IOUtils.skip(this.currentEntryInputStream, Long.MAX_VALUE);
                    this.currentEntryInputStream.close();
                    this.currentEntryInputStream = null;
                }
            }

            private InputStream buildDecoderStack(Folder folder, long folderOffset, int firstPackStreamIndex, SevenZArchiveEntry entry) throws IOException {
                this.file.seek(folderOffset);
                InputStream inputStreamStack = new BoundedRandomAccessFileInputStream(this.file, this.archive.packSizes[firstPackStreamIndex]);
                LinkedList<SevenZMethodConfiguration> methods = new LinkedList();
                for (Coder coder : folder.getOrderedCoders()) {
                    if (coder.numInStreams == 1) {
                        if (coder.numOutStreams == 1) {
                            SevenZMethod method = SevenZMethod.byId(coder.decompressionMethodId);
                            inputStreamStack = Coders.addDecoder(inputStreamStack, folder.getUnpackSizeForCoder(coder), coder, this.password);
                            methods.addFirst(new SevenZMethodConfiguration(method, Coders.findByMethod(method).getOptionsFromCoder(coder, inputStreamStack)));
                        }
                    }
                    throw new IOException("Multi input/output stream coders are not yet supported");
                }
                entry.setContentMethods(methods);
                if (!folder.hasCrc) {
                    return inputStreamStack;
                }
                return new CRC32VerifyingInputStream(inputStreamStack, folder.getUnpackSize(), folder.crc);
            }

            public int read() throws IOException {
                if (this.currentEntryInputStream != null) {
                    return this.currentEntryInputStream.read();
                }
                throw new IllegalStateException("No current 7z entry");
            }

            public int read(byte[] b) throws IOException {
                return read(b, 0, b.length);
            }

            public int read(byte[] b, int off, int len) throws IOException {
                if (this.currentEntryInputStream != null) {
                    return this.currentEntryInputStream.read(b, off, len);
                }
                throw new IllegalStateException("No current 7z entry");
            }

            private static long readUint64(DataInput in) throws IOException {
                long firstByte = (long) in.readUnsignedByte();
                int mask = 128;
                long value = 0;
                int i = 0;
                while (i < 8) {
                    if ((firstByte & ((long) mask)) == 0) {
                        return value | ((firstByte & ((long) (mask - 1))) << (i * 8));
                    }
                    mask >>>= 1;
                    i++;
                    value |= ((long) in.readUnsignedByte()) << (i * 8);
                }
                return value;
            }

            public static boolean matches(byte[] signature, int length) {
                if (length < sevenZSignature.length) {
                    return false;
                }
                for (int i = 0; i < sevenZSignature.length; i++) {
                    if (signature[i] != sevenZSignature[i]) {
                        return false;
                    }
                }
                return true;
            }

            private static long skipBytesFully(DataInput input, long bytesToSkip) throws IOException {
                if (bytesToSkip < 1) {
                    return 0;
                }
                long bytesToSkip2 = bytesToSkip;
                bytesToSkip = 0;
                while (bytesToSkip2 > 2147483647L) {
                    long skippedNow = skipBytesFully(input, 2147483647L);
                    if (skippedNow == 0) {
                        return bytesToSkip;
                    }
                    bytesToSkip2 -= skippedNow;
                    bytesToSkip += skippedNow;
                }
                while (bytesToSkip2 > 0) {
                    int skippedNow2 = input.skipBytes((int) bytesToSkip2);
                    if (skippedNow2 == 0) {
                        return bytesToSkip;
                    }
                    bytesToSkip2 -= (long) skippedNow2;
                    bytesToSkip += (long) skippedNow2;
                }
                return bytesToSkip;
            }
        }
