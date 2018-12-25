package org.apache.commons.compress.archivers.zip;

import java.util.zip.ZipException;

public class Zip64ExtendedInformationExtraField implements ZipExtraField {
    private static final byte[] EMPTY = new byte[0];
    static final ZipShort HEADER_ID = new ZipShort(1);
    private static final String LFH_MUST_HAVE_BOTH_SIZES_MSG = "Zip64 extended information must contain both size values in the local file header.";
    private ZipEightByteInteger compressedSize;
    private ZipLong diskStart;
    private byte[] rawCentralDirectoryData;
    private ZipEightByteInteger relativeHeaderOffset;
    private ZipEightByteInteger size;

    public Zip64ExtendedInformationExtraField(ZipEightByteInteger size, ZipEightByteInteger compressedSize) {
        this(size, compressedSize, null, null);
    }

    public Zip64ExtendedInformationExtraField(ZipEightByteInteger size, ZipEightByteInteger compressedSize, ZipEightByteInteger relativeHeaderOffset, ZipLong diskStart) {
        this.size = size;
        this.compressedSize = compressedSize;
        this.relativeHeaderOffset = relativeHeaderOffset;
        this.diskStart = diskStart;
    }

    public ZipShort getHeaderId() {
        return HEADER_ID;
    }

    public ZipShort getLocalFileDataLength() {
        return new ZipShort(this.size != null ? 16 : 0);
    }

    public ZipShort getCentralDirectoryLength() {
        int i = 8;
        int i2 = 0;
        int i3 = (this.size != null ? 8 : 0) + (this.compressedSize != null ? 8 : 0);
        if (this.relativeHeaderOffset == null) {
            i = 0;
        }
        i3 += i;
        if (this.diskStart != null) {
            i2 = 4;
        }
        return new ZipShort(i3 + i2);
    }

    public byte[] getLocalFileDataData() {
        if (this.size == null) {
            if (this.compressedSize == null) {
                return EMPTY;
            }
        }
        if (this.size != null) {
            if (this.compressedSize != null) {
                byte[] data = new byte[16];
                addSizes(data);
                return data;
            }
        }
        throw new IllegalArgumentException(LFH_MUST_HAVE_BOTH_SIZES_MSG);
    }

    public byte[] getCentralDirectoryData() {
        byte[] data = new byte[getCentralDirectoryLength().getValue()];
        int off = addSizes(data);
        if (this.relativeHeaderOffset != null) {
            System.arraycopy(this.relativeHeaderOffset.getBytes(), 0, data, off, 8);
            off += 8;
        }
        if (this.diskStart != null) {
            System.arraycopy(this.diskStart.getBytes(), 0, data, off, 4);
            off += 4;
        }
        return data;
    }

    public void parseFromLocalFileData(byte[] buffer, int offset, int length) throws ZipException {
        if (length != 0) {
            if (length < 16) {
                throw new ZipException(LFH_MUST_HAVE_BOTH_SIZES_MSG);
            }
            this.size = new ZipEightByteInteger(buffer, offset);
            offset += 8;
            this.compressedSize = new ZipEightByteInteger(buffer, offset);
            offset += 8;
            int remaining = length - 16;
            if (remaining >= 8) {
                this.relativeHeaderOffset = new ZipEightByteInteger(buffer, offset);
                offset += 8;
                remaining -= 8;
            }
            if (remaining >= 4) {
                this.diskStart = new ZipLong(buffer, offset);
                offset += 4;
                remaining -= 4;
            }
        }
    }

    public void parseFromCentralDirectoryData(byte[] buffer, int offset, int length) throws ZipException {
        this.rawCentralDirectoryData = new byte[length];
        System.arraycopy(buffer, offset, this.rawCentralDirectoryData, 0, length);
        if (length >= 28) {
            parseFromLocalFileData(buffer, offset, length);
        } else if (length == 24) {
            this.size = new ZipEightByteInteger(buffer, offset);
            offset += 8;
            this.compressedSize = new ZipEightByteInteger(buffer, offset);
            this.relativeHeaderOffset = new ZipEightByteInteger(buffer, offset + 8);
        } else if (length % 8 == 4) {
            this.diskStart = new ZipLong(buffer, (offset + length) - 4);
        }
    }

    public void reparseCentralDirectoryData(boolean hasUncompressedSize, boolean hasCompressedSize, boolean hasRelativeHeaderOffset, boolean hasDiskStart) throws ZipException {
        if (this.rawCentralDirectoryData != null) {
            int i = 0;
            int i2 = 8;
            int i3 = (hasUncompressedSize ? 8 : 0) + (hasCompressedSize ? 8 : 0);
            if (!hasRelativeHeaderOffset) {
                i2 = 0;
            }
            i3 += i2;
            if (hasDiskStart) {
                i = 4;
            }
            i3 += i;
            if (this.rawCentralDirectoryData.length < i3) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("central directory zip64 extended information extra field's length doesn't match central directory data.  Expected length ");
                stringBuilder.append(i3);
                stringBuilder.append(" but is ");
                stringBuilder.append(this.rawCentralDirectoryData.length);
                throw new ZipException(stringBuilder.toString());
            }
            i = 0;
            if (hasUncompressedSize) {
                this.size = new ZipEightByteInteger(this.rawCentralDirectoryData, 0);
                i = 0 + 8;
            }
            if (hasCompressedSize) {
                this.compressedSize = new ZipEightByteInteger(this.rawCentralDirectoryData, i);
                i += 8;
            }
            if (hasRelativeHeaderOffset) {
                this.relativeHeaderOffset = new ZipEightByteInteger(this.rawCentralDirectoryData, i);
                i += 8;
            }
            if (hasDiskStart) {
                this.diskStart = new ZipLong(this.rawCentralDirectoryData, i);
            }
        }
    }

    public ZipEightByteInteger getSize() {
        return this.size;
    }

    public void setSize(ZipEightByteInteger size) {
        this.size = size;
    }

    public ZipEightByteInteger getCompressedSize() {
        return this.compressedSize;
    }

    public void setCompressedSize(ZipEightByteInteger compressedSize) {
        this.compressedSize = compressedSize;
    }

    public ZipEightByteInteger getRelativeHeaderOffset() {
        return this.relativeHeaderOffset;
    }

    public void setRelativeHeaderOffset(ZipEightByteInteger rho) {
        this.relativeHeaderOffset = rho;
    }

    public ZipLong getDiskStartNumber() {
        return this.diskStart;
    }

    public void setDiskStartNumber(ZipLong ds) {
        this.diskStart = ds;
    }

    private int addSizes(byte[] data) {
        int off = 0;
        if (this.size != null) {
            System.arraycopy(this.size.getBytes(), 0, data, 0, 8);
            off = 0 + 8;
        }
        if (this.compressedSize == null) {
            return off;
        }
        System.arraycopy(this.compressedSize.getBytes(), 0, data, off, 8);
        return off + 8;
    }
}
