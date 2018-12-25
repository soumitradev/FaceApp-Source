package org.apache.commons.compress.archivers.zip;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.zip.ExtraFieldUtils.UnparseableExtraField;

public class ZipArchiveEntry extends ZipEntry implements ArchiveEntry {
    private static final byte[] EMPTY = new byte[0];
    public static final int PLATFORM_FAT = 0;
    public static final int PLATFORM_UNIX = 3;
    private static final int SHORT_MASK = 65535;
    private static final int SHORT_SHIFT = 16;
    private long externalAttributes;
    private LinkedHashMap<ZipShort, ZipExtraField> extraFields;
    private GeneralPurposeBit gpb;
    private int internalAttributes;
    private int method;
    private String name;
    private int platform;
    private byte[] rawName;
    private long size;
    private UnparseableExtraFieldData unparseableExtra;

    public ZipArchiveEntry(String name) {
        super(name);
        this.method = -1;
        this.size = -1;
        this.internalAttributes = 0;
        this.platform = 0;
        this.externalAttributes = 0;
        this.extraFields = null;
        this.unparseableExtra = null;
        this.name = null;
        this.rawName = null;
        this.gpb = new GeneralPurposeBit();
        setName(name);
    }

    public ZipArchiveEntry(ZipEntry entry) throws ZipException {
        super(entry);
        this.method = -1;
        this.size = -1;
        this.internalAttributes = 0;
        this.platform = 0;
        this.externalAttributes = 0;
        this.extraFields = null;
        this.unparseableExtra = null;
        this.name = null;
        this.rawName = null;
        this.gpb = new GeneralPurposeBit();
        setName(entry.getName());
        byte[] extra = entry.getExtra();
        if (extra != null) {
            setExtraFields(ExtraFieldUtils.parse(extra, true, UnparseableExtraField.READ));
        } else {
            setExtra();
        }
        setMethod(entry.getMethod());
        this.size = entry.getSize();
    }

    public ZipArchiveEntry(ZipArchiveEntry entry) throws ZipException {
        this((ZipEntry) entry);
        setInternalAttributes(entry.getInternalAttributes());
        setExternalAttributes(entry.getExternalAttributes());
        setExtraFields(entry.getExtraFields(true));
    }

    protected ZipArchiveEntry() {
        this("");
    }

    public ZipArchiveEntry(File inputFile, String entryName) {
        String str;
        if (!inputFile.isDirectory() || entryName.endsWith("/")) {
            str = entryName;
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(entryName);
            stringBuilder.append("/");
            str = stringBuilder.toString();
        }
        this(str);
        if (inputFile.isFile()) {
            setSize(inputFile.length());
        }
        setTime(inputFile.lastModified());
    }

    public Object clone() {
        ZipArchiveEntry e = (ZipArchiveEntry) super.clone();
        e.setInternalAttributes(getInternalAttributes());
        e.setExternalAttributes(getExternalAttributes());
        e.setExtraFields(getExtraFields(true));
        return e;
    }

    public int getMethod() {
        return this.method;
    }

    public void setMethod(int method) {
        if (method < 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("ZIP compression method can not be negative: ");
            stringBuilder.append(method);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        this.method = method;
    }

    public int getInternalAttributes() {
        return this.internalAttributes;
    }

    public void setInternalAttributes(int value) {
        this.internalAttributes = value;
    }

    public long getExternalAttributes() {
        return this.externalAttributes;
    }

    public void setExternalAttributes(long value) {
        this.externalAttributes = value;
    }

    public void setUnixMode(int mode) {
        int i = 0;
        int i2 = (mode << 16) | ((mode & 128) == 0 ? 1 : 0);
        if (isDirectory()) {
            i = 16;
        }
        setExternalAttributes((long) (i2 | i));
        this.platform = 3;
    }

    public int getUnixMode() {
        return this.platform != 3 ? 0 : (int) ((getExternalAttributes() >> 16) & 65535);
    }

    public boolean isUnixSymlink() {
        return (getUnixMode() & 40960) == 40960;
    }

    public int getPlatform() {
        return this.platform;
    }

    protected void setPlatform(int platform) {
        this.platform = platform;
    }

    public void setExtraFields(ZipExtraField[] fields) {
        this.extraFields = new LinkedHashMap();
        for (ZipExtraField field : fields) {
            if (field instanceof UnparseableExtraFieldData) {
                this.unparseableExtra = (UnparseableExtraFieldData) field;
            } else {
                this.extraFields.put(field.getHeaderId(), field);
            }
        }
        setExtra();
    }

    public ZipExtraField[] getExtraFields() {
        return getExtraFields(false);
    }

    public ZipExtraField[] getExtraFields(boolean includeUnparseable) {
        if (this.extraFields == null) {
            ZipExtraField[] zipExtraFieldArr;
            if (includeUnparseable) {
                if (this.unparseableExtra != null) {
                    zipExtraFieldArr = new ZipExtraField[]{this.unparseableExtra};
                    return zipExtraFieldArr;
                }
            }
            zipExtraFieldArr = new ZipExtraField[0];
            return zipExtraFieldArr;
        }
        List<ZipExtraField> result = new ArrayList(this.extraFields.values());
        if (includeUnparseable && this.unparseableExtra != null) {
            result.add(this.unparseableExtra);
        }
        return (ZipExtraField[]) result.toArray(new ZipExtraField[0]);
    }

    public void addExtraField(ZipExtraField ze) {
        if (ze instanceof UnparseableExtraFieldData) {
            this.unparseableExtra = (UnparseableExtraFieldData) ze;
        } else {
            if (this.extraFields == null) {
                this.extraFields = new LinkedHashMap();
            }
            this.extraFields.put(ze.getHeaderId(), ze);
        }
        setExtra();
    }

    public void addAsFirstExtraField(ZipExtraField ze) {
        if (ze instanceof UnparseableExtraFieldData) {
            this.unparseableExtra = (UnparseableExtraFieldData) ze;
        } else {
            LinkedHashMap<ZipShort, ZipExtraField> copy = this.extraFields;
            this.extraFields = new LinkedHashMap();
            this.extraFields.put(ze.getHeaderId(), ze);
            if (copy != null) {
                copy.remove(ze.getHeaderId());
                this.extraFields.putAll(copy);
            }
        }
        setExtra();
    }

    public void removeExtraField(ZipShort type) {
        if (this.extraFields == null) {
            throw new NoSuchElementException();
        } else if (this.extraFields.remove(type) == null) {
            throw new NoSuchElementException();
        } else {
            setExtra();
        }
    }

    public void removeUnparseableExtraFieldData() {
        if (this.unparseableExtra == null) {
            throw new NoSuchElementException();
        }
        this.unparseableExtra = null;
        setExtra();
    }

    public ZipExtraField getExtraField(ZipShort type) {
        if (this.extraFields != null) {
            return (ZipExtraField) this.extraFields.get(type);
        }
        return null;
    }

    public UnparseableExtraFieldData getUnparseableExtraFieldData() {
        return this.unparseableExtra;
    }

    public void setExtra(byte[] extra) throws RuntimeException {
        try {
            mergeExtraFields(ExtraFieldUtils.parse(extra, true, UnparseableExtraField.READ), true);
        } catch (ZipException e) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Error parsing extra fields for entry: ");
            stringBuilder.append(getName());
            stringBuilder.append(" - ");
            stringBuilder.append(e.getMessage());
            throw new RuntimeException(stringBuilder.toString(), e);
        }
    }

    protected void setExtra() {
        super.setExtra(ExtraFieldUtils.mergeLocalFileDataData(getExtraFields(true)));
    }

    public void setCentralDirectoryExtra(byte[] b) {
        try {
            mergeExtraFields(ExtraFieldUtils.parse(b, false, UnparseableExtraField.READ), false);
        } catch (ZipException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public byte[] getLocalFileDataExtra() {
        byte[] extra = getExtra();
        return extra != null ? extra : EMPTY;
    }

    public byte[] getCentralDirectoryExtra() {
        return ExtraFieldUtils.mergeCentralDirectoryData(getExtraFields(true));
    }

    public String getName() {
        return this.name == null ? super.getName() : this.name;
    }

    public boolean isDirectory() {
        return getName().endsWith("/");
    }

    protected void setName(String name) {
        if (name != null && getPlatform() == 0 && name.indexOf("/") == -1) {
            name = name.replace('\\', '/');
        }
        this.name = name;
    }

    public long getSize() {
        return this.size;
    }

    public void setSize(long size) {
        if (size < 0) {
            throw new IllegalArgumentException("invalid entry size");
        }
        this.size = size;
    }

    protected void setName(String name, byte[] rawName) {
        setName(name);
        this.rawName = rawName;
    }

    public byte[] getRawName() {
        if (this.rawName == null) {
            return null;
        }
        byte[] b = new byte[this.rawName.length];
        System.arraycopy(this.rawName, 0, b, 0, this.rawName.length);
        return b;
    }

    public int hashCode() {
        return getName().hashCode();
    }

    public GeneralPurposeBit getGeneralPurposeBit() {
        return this.gpb;
    }

    public void setGeneralPurposeBit(GeneralPurposeBit b) {
        this.gpb = b;
    }

    private void mergeExtraFields(ZipExtraField[] f, boolean local) throws ZipException {
        if (this.extraFields == null) {
            setExtraFields(f);
            return;
        }
        for (ZipExtraField element : f) {
            ZipExtraField existing;
            if (element instanceof UnparseableExtraFieldData) {
                existing = this.unparseableExtra;
            } else {
                existing = getExtraField(element.getHeaderId());
            }
            if (existing == null) {
                addExtraField(element);
            } else if (local) {
                b = element.getLocalFileDataData();
                existing.parseFromLocalFileData(b, 0, b.length);
            } else {
                b = element.getCentralDirectoryData();
                existing.parseFromCentralDirectoryData(b, 0, b.length);
            }
        }
        setExtra();
    }

    public Date getLastModifiedDate() {
        return new Date(getTime());
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (this == obj) {
            return true;
        }
        if (obj != null) {
            if (getClass() == obj.getClass()) {
                ZipArchiveEntry other = (ZipArchiveEntry) obj;
                String myName = getName();
                String otherName = other.getName();
                if (myName == null) {
                    if (otherName != null) {
                        return false;
                    }
                } else if (!myName.equals(otherName)) {
                    return false;
                }
                String myComment = getComment();
                String otherComment = other.getComment();
                if (myComment == null) {
                    myComment = "";
                }
                if (otherComment == null) {
                    otherComment = "";
                }
                if (getTime() != other.getTime() || !myComment.equals(otherComment) || getInternalAttributes() != other.getInternalAttributes() || getPlatform() != other.getPlatform() || getExternalAttributes() != other.getExternalAttributes() || getMethod() != other.getMethod() || getSize() != other.getSize() || getCrc() != other.getCrc() || getCompressedSize() != other.getCompressedSize() || !Arrays.equals(getCentralDirectoryExtra(), other.getCentralDirectoryExtra()) || !Arrays.equals(getLocalFileDataExtra(), other.getLocalFileDataExtra()) || !this.gpb.equals(other.gpb)) {
                    z = false;
                }
                return z;
            }
        }
        return false;
    }
}
