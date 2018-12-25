package org.apache.commons.compress.archivers.zip;

import java.io.Serializable;
import java.util.Date;
import java.util.zip.ZipException;
import name.antonsmirnov.firmata.FormatHelper;

public class X5455_ExtendedTimestamp implements ZipExtraField, Cloneable, Serializable {
    public static final byte ACCESS_TIME_BIT = (byte) 2;
    public static final byte CREATE_TIME_BIT = (byte) 4;
    private static final ZipShort HEADER_ID = new ZipShort(21589);
    public static final byte MODIFY_TIME_BIT = (byte) 1;
    private static final long serialVersionUID = 1;
    private ZipLong accessTime;
    private boolean bit0_modifyTimePresent;
    private boolean bit1_accessTimePresent;
    private boolean bit2_createTimePresent;
    private ZipLong createTime;
    private byte flags;
    private ZipLong modifyTime;

    public ZipShort getHeaderId() {
        return HEADER_ID;
    }

    public ZipShort getLocalFileDataLength() {
        int i = 0;
        int i2 = (this.bit0_modifyTimePresent ? 4 : 0) + 1;
        int i3 = (!this.bit1_accessTimePresent || this.accessTime == null) ? 0 : 4;
        i2 += i3;
        if (this.bit2_createTimePresent && this.createTime != null) {
            i = 4;
        }
        return new ZipShort(i2 + i);
    }

    public ZipShort getCentralDirectoryLength() {
        return new ZipShort((this.bit0_modifyTimePresent ? 4 : 0) + 1);
    }

    public byte[] getLocalFileDataData() {
        byte[] data = new byte[getLocalFileDataLength().getValue()];
        int pos = 0 + 1;
        data[0] = (byte) 0;
        if (this.bit0_modifyTimePresent) {
            data[0] = (byte) (data[0] | 1);
            System.arraycopy(this.modifyTime.getBytes(), 0, data, pos, 4);
            pos += 4;
        }
        if (this.bit1_accessTimePresent && this.accessTime != null) {
            data[0] = (byte) (data[0] | 2);
            System.arraycopy(this.accessTime.getBytes(), 0, data, pos, 4);
            pos += 4;
        }
        if (this.bit2_createTimePresent && this.createTime != null) {
            data[0] = (byte) (data[0] | 4);
            System.arraycopy(this.createTime.getBytes(), 0, data, pos, 4);
            pos += 4;
        }
        return data;
    }

    public byte[] getCentralDirectoryData() {
        byte[] centralData = new byte[getCentralDirectoryLength().getValue()];
        System.arraycopy(getLocalFileDataData(), 0, centralData, 0, centralData.length);
        return centralData;
    }

    public void parseFromLocalFileData(byte[] data, int offset, int length) throws ZipException {
        reset();
        int len = offset + length;
        int offset2 = offset + 1;
        setFlags(data[offset]);
        if (this.bit0_modifyTimePresent) {
            this.modifyTime = new ZipLong(data, offset2);
            offset2 += 4;
        }
        if (this.bit1_accessTimePresent && offset2 + 4 <= len) {
            this.accessTime = new ZipLong(data, offset2);
            offset2 += 4;
        }
        if (this.bit2_createTimePresent && offset2 + 4 <= len) {
            this.createTime = new ZipLong(data, offset2);
            offset2 += 4;
        }
    }

    public void parseFromCentralDirectoryData(byte[] buffer, int offset, int length) throws ZipException {
        reset();
        parseFromLocalFileData(buffer, offset, length);
    }

    private void reset() {
        setFlags((byte) 0);
        this.modifyTime = null;
        this.accessTime = null;
        this.createTime = null;
    }

    public void setFlags(byte flags) {
        this.flags = flags;
        boolean z = false;
        this.bit0_modifyTimePresent = (flags & 1) == 1;
        this.bit1_accessTimePresent = (flags & 2) == 2;
        if ((flags & 4) == 4) {
            z = true;
        }
        this.bit2_createTimePresent = z;
    }

    public byte getFlags() {
        return this.flags;
    }

    public boolean isBit0_modifyTimePresent() {
        return this.bit0_modifyTimePresent;
    }

    public boolean isBit1_accessTimePresent() {
        return this.bit1_accessTimePresent;
    }

    public boolean isBit2_createTimePresent() {
        return this.bit2_createTimePresent;
    }

    public ZipLong getModifyTime() {
        return this.modifyTime;
    }

    public ZipLong getAccessTime() {
        return this.accessTime;
    }

    public ZipLong getCreateTime() {
        return this.createTime;
    }

    public Date getModifyJavaTime() {
        return this.modifyTime != null ? new Date(this.modifyTime.getValue() * 1000) : null;
    }

    public Date getAccessJavaTime() {
        return this.accessTime != null ? new Date(this.accessTime.getValue() * 1000) : null;
    }

    public Date getCreateJavaTime() {
        return this.createTime != null ? new Date(this.createTime.getValue() * 1000) : null;
    }

    public void setModifyTime(ZipLong l) {
        this.bit0_modifyTimePresent = l != null;
        this.flags = (byte) (l != null ? 1 | this.flags : this.flags & -2);
        this.modifyTime = l;
    }

    public void setAccessTime(ZipLong l) {
        this.bit1_accessTimePresent = l != null;
        this.flags = (byte) (l != null ? this.flags | 2 : this.flags & -3);
        this.accessTime = l;
    }

    public void setCreateTime(ZipLong l) {
        this.bit2_createTimePresent = l != null;
        this.flags = (byte) (l != null ? this.flags | 4 : this.flags & -5);
        this.createTime = l;
    }

    public void setModifyJavaTime(Date d) {
        setModifyTime(dateToZipLong(d));
    }

    public void setAccessJavaTime(Date d) {
        setAccessTime(dateToZipLong(d));
    }

    public void setCreateJavaTime(Date d) {
        setCreateTime(dateToZipLong(d));
    }

    private static ZipLong dateToZipLong(Date d) {
        if (d == null) {
            return null;
        }
        long l = d.getTime() / 1000;
        if (l < 4294967296L) {
            return new ZipLong(l);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Cannot set an X5455 timestamp larger than 2^32: ");
        stringBuilder.append(l);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("0x5455 Zip Extra Field: Flags=");
        buf.append(Integer.toBinaryString(ZipUtil.unsignedIntToSignedByte(this.flags)));
        buf.append(FormatHelper.SPACE);
        if (this.bit0_modifyTimePresent && this.modifyTime != null) {
            Date m = getModifyJavaTime();
            buf.append(" Modify:[");
            buf.append(m);
            buf.append("] ");
        }
        if (this.bit1_accessTimePresent && this.accessTime != null) {
            m = getAccessJavaTime();
            buf.append(" Access:[");
            buf.append(m);
            buf.append("] ");
        }
        if (this.bit2_createTimePresent && this.createTime != null) {
            m = getCreateJavaTime();
            buf.append(" Create:[");
            buf.append(m);
            buf.append("] ");
        }
        return buf.toString();
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public boolean equals(Object o) {
        boolean z = false;
        if (!(o instanceof X5455_ExtendedTimestamp)) {
            return false;
        }
        X5455_ExtendedTimestamp xf = (X5455_ExtendedTimestamp) o;
        if ((this.flags & 7) == (xf.flags & 7) && ((this.modifyTime == xf.modifyTime || (this.modifyTime != null && this.modifyTime.equals(xf.modifyTime))) && ((this.accessTime == xf.accessTime || (this.accessTime != null && this.accessTime.equals(xf.accessTime))) && (this.createTime == xf.createTime || (this.createTime != null && this.createTime.equals(xf.createTime)))))) {
            z = true;
        }
        return z;
    }

    public int hashCode() {
        int hc = (this.flags & 7) * -123;
        if (this.modifyTime != null) {
            hc ^= this.modifyTime.hashCode();
        }
        if (this.accessTime != null) {
            hc ^= Integer.rotateLeft(this.accessTime.hashCode(), 11);
        }
        if (this.createTime != null) {
            return hc ^ Integer.rotateLeft(this.createTime.hashCode(), 22);
        }
        return hc;
    }
}
