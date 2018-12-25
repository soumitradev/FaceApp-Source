package org.apache.commons.compress.archivers.zip;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.zip.ZipException;

public class X7875_NewUnix implements ZipExtraField, Cloneable, Serializable {
    private static final ZipShort HEADER_ID = new ZipShort(30837);
    private static final BigInteger ONE_THOUSAND = BigInteger.valueOf(1000);
    private static final long serialVersionUID = 1;
    private BigInteger gid;
    private BigInteger uid;
    private int version = 1;

    public X7875_NewUnix() {
        reset();
    }

    public ZipShort getHeaderId() {
        return HEADER_ID;
    }

    public long getUID() {
        return ZipUtil.bigToLong(this.uid);
    }

    public long getGID() {
        return ZipUtil.bigToLong(this.gid);
    }

    public void setUID(long l) {
        this.uid = ZipUtil.longToBig(l);
    }

    public void setGID(long l) {
        this.gid = ZipUtil.longToBig(l);
    }

    public ZipShort getLocalFileDataLength() {
        return new ZipShort((trimLeadingZeroesForceMinLength(this.uid.toByteArray()).length + 3) + trimLeadingZeroesForceMinLength(this.gid.toByteArray()).length);
    }

    public ZipShort getCentralDirectoryLength() {
        return getLocalFileDataLength();
    }

    public byte[] getLocalFileDataData() {
        byte[] uidBytes = this.uid.toByteArray();
        byte[] gidBytes = this.gid.toByteArray();
        uidBytes = trimLeadingZeroesForceMinLength(uidBytes);
        gidBytes = trimLeadingZeroesForceMinLength(gidBytes);
        byte[] data = new byte[((uidBytes.length + 3) + gidBytes.length)];
        ZipUtil.reverse(uidBytes);
        ZipUtil.reverse(gidBytes);
        int pos = 0 + 1;
        data[0] = ZipUtil.unsignedIntToSignedByte(this.version);
        int pos2 = pos + 1;
        data[pos] = ZipUtil.unsignedIntToSignedByte(uidBytes.length);
        System.arraycopy(uidBytes, 0, data, pos2, uidBytes.length);
        pos2 += uidBytes.length;
        pos = pos2 + 1;
        data[pos2] = ZipUtil.unsignedIntToSignedByte(gidBytes.length);
        System.arraycopy(gidBytes, 0, data, pos, gidBytes.length);
        return data;
    }

    public byte[] getCentralDirectoryData() {
        return getLocalFileDataData();
    }

    public void parseFromLocalFileData(byte[] data, int offset, int length) throws ZipException {
        reset();
        int offset2 = offset + 1;
        this.version = ZipUtil.signedByteToUnsignedInt(data[offset]);
        offset = offset2 + 1;
        offset2 = ZipUtil.signedByteToUnsignedInt(data[offset2]);
        byte[] uidBytes = new byte[offset2];
        System.arraycopy(data, offset, uidBytes, 0, offset2);
        offset += offset2;
        this.uid = new BigInteger(1, ZipUtil.reverse(uidBytes));
        int offset3 = offset + 1;
        offset = ZipUtil.signedByteToUnsignedInt(data[offset]);
        byte[] gidBytes = new byte[offset];
        System.arraycopy(data, offset3, gidBytes, 0, offset);
        this.gid = new BigInteger(1, ZipUtil.reverse(gidBytes));
    }

    public void parseFromCentralDirectoryData(byte[] buffer, int offset, int length) throws ZipException {
        reset();
        parseFromLocalFileData(buffer, offset, length);
    }

    private void reset() {
        this.uid = ONE_THOUSAND;
        this.gid = ONE_THOUSAND;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0x7875 Zip Extra Field: UID=");
        stringBuilder.append(this.uid);
        stringBuilder.append(" GID=");
        stringBuilder.append(this.gid);
        return stringBuilder.toString();
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public boolean equals(Object o) {
        boolean z = false;
        if (!(o instanceof X7875_NewUnix)) {
            return false;
        }
        X7875_NewUnix xf = (X7875_NewUnix) o;
        if (this.version == xf.version && this.uid.equals(xf.uid) && this.gid.equals(xf.gid)) {
            z = true;
        }
        return z;
    }

    public int hashCode() {
        return ((this.version * -1234567) ^ Integer.rotateLeft(this.uid.hashCode(), 16)) ^ this.gid.hashCode();
    }

    static byte[] trimLeadingZeroesForceMinLength(byte[] array) {
        if (array == null) {
            return array;
        }
        int pos = 0;
        byte[] arr$ = array;
        int len$ = arr$.length;
        int i$ = 0;
        while (i$ < len$ && arr$[i$] == (byte) 0) {
            pos++;
            i$++;
        }
        byte[] trimmedArray = new byte[Math.max(1, array.length - pos)];
        i$ = trimmedArray.length - (array.length - pos);
        System.arraycopy(array, pos, trimmedArray, i$, trimmedArray.length - i$);
        return trimmedArray;
    }
}
