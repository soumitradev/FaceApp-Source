package org.apache.commons.compress.archivers.tar;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Locale;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipEncoding;
import org.apache.commons.compress.utils.ArchiveUtils;

public class TarArchiveEntry implements TarConstants, ArchiveEntry {
    public static final int DEFAULT_DIR_MODE = 16877;
    public static final int DEFAULT_FILE_MODE = 33188;
    public static final int MAX_NAMELEN = 31;
    public static final int MILLIS_PER_SECOND = 1000;
    private boolean checkSumOK;
    private int devMajor;
    private int devMinor;
    private final File file;
    private int groupId;
    private String groupName;
    private boolean isExtended;
    private byte linkFlag;
    private String linkName;
    private String magic;
    private long modTime;
    private int mode;
    private String name;
    private long realSize;
    private long size;
    private int userId;
    private String userName;
    private String version;

    private TarArchiveEntry() {
        this.name = "";
        this.userId = 0;
        this.groupId = 0;
        this.size = 0;
        this.linkName = "";
        this.magic = "ustar\u0000";
        this.version = TarConstants.VERSION_POSIX;
        this.groupName = "";
        this.devMajor = 0;
        this.devMinor = 0;
        String user = System.getProperty("user.name", "");
        if (user.length() > 31) {
            user = user.substring(0, 31);
        }
        this.userName = user;
        this.file = null;
    }

    public TarArchiveEntry(String name) {
        this(name, false);
    }

    public TarArchiveEntry(String name, boolean preserveLeadingSlashes) {
        this();
        name = normalizeFileName(name, preserveLeadingSlashes);
        boolean isDir = name.endsWith("/");
        this.name = name;
        this.mode = isDir ? DEFAULT_DIR_MODE : DEFAULT_FILE_MODE;
        this.linkFlag = isDir ? (byte) 53 : (byte) 48;
        this.modTime = new Date().getTime() / 1000;
        this.userName = "";
    }

    public TarArchiveEntry(String name, byte linkFlag) {
        this(name, linkFlag, false);
    }

    public TarArchiveEntry(String name, byte linkFlag, boolean preserveLeadingSlashes) {
        this(name, preserveLeadingSlashes);
        this.linkFlag = linkFlag;
        if (linkFlag == (byte) 76) {
            this.magic = TarConstants.MAGIC_GNU;
            this.version = TarConstants.VERSION_GNU_SPACE;
        }
    }

    public TarArchiveEntry(File file) {
        this(file, normalizeFileName(file.getPath(), false));
    }

    public TarArchiveEntry(File file, String fileName) {
        this.name = "";
        this.userId = 0;
        this.groupId = 0;
        this.size = 0;
        this.linkName = "";
        this.magic = "ustar\u0000";
        this.version = TarConstants.VERSION_POSIX;
        this.groupName = "";
        this.devMajor = 0;
        this.devMinor = 0;
        this.file = file;
        if (file.isDirectory()) {
            this.mode = DEFAULT_DIR_MODE;
            this.linkFlag = (byte) 53;
            int nameLength = fileName.length();
            if (nameLength != 0) {
                if (fileName.charAt(nameLength - 1) == '/') {
                    this.name = fileName;
                }
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(fileName);
            stringBuilder.append("/");
            this.name = stringBuilder.toString();
        } else {
            this.mode = DEFAULT_FILE_MODE;
            this.linkFlag = (byte) 48;
            this.size = file.length();
            this.name = fileName;
        }
        this.modTime = file.lastModified() / 1000;
        this.userName = "";
    }

    public TarArchiveEntry(byte[] headerBuf) {
        this();
        parseTarHeader(headerBuf);
    }

    public TarArchiveEntry(byte[] headerBuf, ZipEncoding encoding) throws IOException {
        this();
        parseTarHeader(headerBuf, encoding);
    }

    public boolean equals(TarArchiveEntry it) {
        return getName().equals(it.getName());
    }

    public boolean equals(Object it) {
        if (it != null) {
            if (getClass() == it.getClass()) {
                return equals((TarArchiveEntry) it);
            }
        }
        return false;
    }

    public int hashCode() {
        return getName().hashCode();
    }

    public boolean isDescendent(TarArchiveEntry desc) {
        return desc.getName().startsWith(getName());
    }

    public String getName() {
        return this.name.toString();
    }

    public void setName(String name) {
        this.name = normalizeFileName(name, false);
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public String getLinkName() {
        return this.linkName.toString();
    }

    public void setLinkName(String link) {
        this.linkName = link;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getGroupId() {
        return this.groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getUserName() {
        return this.userName.toString();
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGroupName() {
        return this.groupName.toString();
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setIds(int userId, int groupId) {
        setUserId(userId);
        setGroupId(groupId);
    }

    public void setNames(String userName, String groupName) {
        setUserName(userName);
        setGroupName(groupName);
    }

    public void setModTime(long time) {
        this.modTime = time / 1000;
    }

    public void setModTime(Date time) {
        this.modTime = time.getTime() / 1000;
    }

    public Date getModTime() {
        return new Date(this.modTime * 1000);
    }

    public Date getLastModifiedDate() {
        return getModTime();
    }

    public boolean isCheckSumOK() {
        return this.checkSumOK;
    }

    public File getFile() {
        return this.file;
    }

    public int getMode() {
        return this.mode;
    }

    public long getSize() {
        return this.size;
    }

    public void setSize(long size) {
        if (size < 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Size is out of range: ");
            stringBuilder.append(size);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        this.size = size;
    }

    public int getDevMajor() {
        return this.devMajor;
    }

    public void setDevMajor(int devNo) {
        if (devNo < 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Major device number is out of range: ");
            stringBuilder.append(devNo);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        this.devMajor = devNo;
    }

    public int getDevMinor() {
        return this.devMinor;
    }

    public void setDevMinor(int devNo) {
        if (devNo < 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Minor device number is out of range: ");
            stringBuilder.append(devNo);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        this.devMinor = devNo;
    }

    public boolean isExtended() {
        return this.isExtended;
    }

    public long getRealSize() {
        return this.realSize;
    }

    public boolean isGNUSparse() {
        return this.linkFlag == (byte) 83;
    }

    public boolean isGNULongLinkEntry() {
        return this.linkFlag == (byte) 75 && this.name.equals(TarConstants.GNU_LONGLINK);
    }

    public boolean isGNULongNameEntry() {
        return this.linkFlag == (byte) 76 && this.name.equals(TarConstants.GNU_LONGLINK);
    }

    public boolean isPaxHeader() {
        if (this.linkFlag != (byte) 120) {
            if (this.linkFlag != (byte) 88) {
                return false;
            }
        }
        return true;
    }

    public boolean isGlobalPaxHeader() {
        return this.linkFlag == (byte) 103;
    }

    public boolean isDirectory() {
        if (this.file != null) {
            return this.file.isDirectory();
        }
        if (this.linkFlag == (byte) 53 || getName().endsWith("/")) {
            return true;
        }
        return false;
    }

    public boolean isFile() {
        if (this.file != null) {
            return this.file.isFile();
        }
        if (this.linkFlag != (byte) 0) {
            if (this.linkFlag != (byte) 48) {
                return getName().endsWith("/") ^ true;
            }
        }
        return true;
    }

    public boolean isSymbolicLink() {
        return this.linkFlag == (byte) 50;
    }

    public boolean isLink() {
        return this.linkFlag == (byte) 49;
    }

    public boolean isCharacterDevice() {
        return this.linkFlag == (byte) 51;
    }

    public boolean isBlockDevice() {
        return this.linkFlag == (byte) 52;
    }

    public boolean isFIFO() {
        return this.linkFlag == (byte) 54;
    }

    public TarArchiveEntry[] getDirectoryEntries() {
        int i = 0;
        if (this.file != null) {
            if (this.file.isDirectory()) {
                String[] list = this.file.list();
                TarArchiveEntry[] result = new TarArchiveEntry[list.length];
                while (i < list.length) {
                    result[i] = new TarArchiveEntry(new File(this.file, list[i]));
                    i++;
                }
                return result;
            }
        }
        return new TarArchiveEntry[0];
    }

    public void writeEntryHeader(byte[] outbuf) {
        try {
            writeEntryHeader(outbuf, TarUtils.DEFAULT_ENCODING, false);
        } catch (IOException e) {
            try {
                writeEntryHeader(outbuf, TarUtils.FALLBACK_ENCODING, false);
            } catch (IOException ex2) {
                throw new RuntimeException(ex2);
            }
        }
    }

    public void writeEntryHeader(byte[] outbuf, ZipEncoding encoding, boolean starMode) throws IOException {
        byte[] bArr = outbuf;
        ZipEncoding zipEncoding = encoding;
        long j = (long) this.mode;
        byte[] bArr2 = bArr;
        boolean z = starMode;
        int offset = writeEntryHeaderField(j, bArr2, TarUtils.formatNameBytes(this.name, bArr, 0, 100, zipEncoding), 8, z);
        offset = writeEntryHeaderField((long) this.userId, bArr2, offset, 8, z);
        int offset2 = writeEntryHeaderField(this.modTime, bArr2, writeEntryHeaderField(this.size, bArr2, writeEntryHeaderField((long) this.groupId, bArr2, offset, 8, z), 12, z), 12, z);
        offset = offset2;
        int offset3 = offset2;
        offset2 = 0;
        while (offset2 < 8) {
            int offset4 = offset3 + 1;
            bArr[offset3] = (byte) 32;
            offset2++;
            offset3 = offset4;
        }
        offset2 = offset3 + 1;
        bArr[offset3] = r7.linkFlag;
        j = (long) r7.devMajor;
        bArr2 = bArr;
        z = starMode;
        int offset5 = writeEntryHeaderField(j, bArr2, TarUtils.formatNameBytes(r7.groupName, bArr, TarUtils.formatNameBytes(r7.userName, bArr, TarUtils.formatNameBytes(r7.version, bArr, TarUtils.formatNameBytes(r7.magic, bArr, TarUtils.formatNameBytes(r7.linkName, bArr, offset2, 100, zipEncoding), 6), 2), 32, zipEncoding), 32, zipEncoding), 8, z);
        offset2 = writeEntryHeaderField((long) r7.devMinor, bArr2, offset5, 8, z);
        while (offset2 < bArr.length) {
            offset3 = offset2 + 1;
            bArr[offset2] = (byte) 0;
            offset2 = offset3;
        }
        TarUtils.formatCheckSumOctalBytes(TarUtils.computeCheckSum(bArr), bArr, offset, 8);
    }

    private int writeEntryHeaderField(long value, byte[] outbuf, int offset, int length, boolean starMode) {
        if (starMode || (value >= 0 && value < (1 << ((length - 1) * 3)))) {
            return TarUtils.formatLongOctalOrBinaryBytes(value, outbuf, offset, length);
        }
        return TarUtils.formatLongOctalBytes(0, outbuf, offset, length);
    }

    public void parseTarHeader(byte[] header) {
        try {
            parseTarHeader(header, TarUtils.DEFAULT_ENCODING);
        } catch (IOException e) {
            try {
                parseTarHeader(header, TarUtils.DEFAULT_ENCODING, true);
            } catch (IOException ex2) {
                throw new RuntimeException(ex2);
            }
        }
    }

    public void parseTarHeader(byte[] header, ZipEncoding encoding) throws IOException {
        parseTarHeader(header, encoding, false);
    }

    private void parseTarHeader(byte[] header, ZipEncoding encoding, boolean oldStyle) throws IOException {
        this.name = oldStyle ? TarUtils.parseName(header, 0, 100) : TarUtils.parseName(header, 0, 100, encoding);
        int offset = 0 + 100;
        this.mode = (int) TarUtils.parseOctalOrBinary(header, offset, 8);
        offset += 8;
        this.userId = (int) TarUtils.parseOctalOrBinary(header, offset, 8);
        offset += 8;
        this.groupId = (int) TarUtils.parseOctalOrBinary(header, offset, 8);
        offset += 8;
        this.size = TarUtils.parseOctalOrBinary(header, offset, 12);
        offset += 12;
        this.modTime = TarUtils.parseOctalOrBinary(header, offset, 12);
        offset += 12;
        this.checkSumOK = TarUtils.verifyCheckSum(header);
        offset += 8;
        int offset2 = offset + 1;
        this.linkFlag = header[offset];
        this.linkName = oldStyle ? TarUtils.parseName(header, offset2, 100) : TarUtils.parseName(header, offset2, 100, encoding);
        offset2 += 100;
        this.magic = TarUtils.parseName(header, offset2, 6);
        offset2 += 6;
        this.version = TarUtils.parseName(header, offset2, 2);
        offset2 += 2;
        this.userName = oldStyle ? TarUtils.parseName(header, offset2, 32) : TarUtils.parseName(header, offset2, 32, encoding);
        offset2 += 32;
        this.groupName = oldStyle ? TarUtils.parseName(header, offset2, 32) : TarUtils.parseName(header, offset2, 32, encoding);
        offset2 += 32;
        this.devMajor = (int) TarUtils.parseOctalOrBinary(header, offset2, 8);
        offset2 += 8;
        this.devMinor = (int) TarUtils.parseOctalOrBinary(header, offset2, 8);
        offset2 += 8;
        if (evaluateType(header) != 2) {
            StringBuilder stringBuilder;
            String prefix = oldStyle ? TarUtils.parseName(header, offset2, TarConstants.PREFIXLEN) : TarUtils.parseName(header, offset2, TarConstants.PREFIXLEN, encoding);
            if (isDirectory() && !this.name.endsWith("/")) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(this.name);
                stringBuilder.append("/");
                this.name = stringBuilder.toString();
            }
            if (prefix.length() > 0) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(prefix);
                stringBuilder.append("/");
                stringBuilder.append(this.name);
                this.name = stringBuilder.toString();
                return;
            }
            return;
        }
        offset2 = (((((offset2 + 12) + 12) + 12) + 4) + 1) + 96;
        this.isExtended = TarUtils.parseBoolean(header, offset2);
        offset2++;
        this.realSize = TarUtils.parseOctal(header, offset2, 12);
        offset2 += 12;
    }

    private static String normalizeFileName(String fileName, boolean preserveLeadingSlashes) {
        String osname = System.getProperty("os.name").toLowerCase(Locale.ENGLISH);
        if (osname != null) {
            if (osname.startsWith("windows")) {
                if (fileName.length() > 2) {
                    char ch1 = fileName.charAt('\u0000');
                    if (fileName.charAt(1) == ':' && ((ch1 >= 'a' && ch1 <= 'z') || (ch1 >= 'A' && ch1 <= 'Z'))) {
                        fileName = fileName.substring(2);
                    }
                }
            } else if (osname.indexOf("netware") > -1) {
                int colon = fileName.indexOf(58);
                if (colon != -1) {
                    fileName = fileName.substring(colon + 1);
                }
            }
        }
        fileName = fileName.replace(File.separatorChar, '/');
        while (!preserveLeadingSlashes && fileName.startsWith("/")) {
            fileName = fileName.substring(1);
        }
        return fileName;
    }

    private int evaluateType(byte[] header) {
        if (ArchiveUtils.matchAsciiBuffer(TarConstants.MAGIC_GNU, header, 257, 6)) {
            return 2;
        }
        if (ArchiveUtils.matchAsciiBuffer("ustar\u0000", header, 257, 6)) {
            return 3;
        }
        return 0;
    }
}
