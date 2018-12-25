package org.apache.commons.compress.archivers.zip;

import java.io.Serializable;

public final class ZipLong implements Cloneable, Serializable {
    public static final ZipLong AED_SIG = new ZipLong(134630224);
    private static final int BYTE_1 = 1;
    private static final int BYTE_1_MASK = 65280;
    private static final int BYTE_1_SHIFT = 8;
    private static final int BYTE_2 = 2;
    private static final int BYTE_2_MASK = 16711680;
    private static final int BYTE_2_SHIFT = 16;
    private static final int BYTE_3 = 3;
    private static final long BYTE_3_MASK = 4278190080L;
    private static final int BYTE_3_SHIFT = 24;
    public static final ZipLong CFH_SIG = new ZipLong(33639248);
    public static final ZipLong DD_SIG = new ZipLong(134695760);
    public static final ZipLong LFH_SIG = new ZipLong(67324752);
    public static final ZipLong SINGLE_SEGMENT_SPLIT_MARKER = new ZipLong(808471376);
    static final ZipLong ZIP64_MAGIC = new ZipLong(4294967295L);
    private static final long serialVersionUID = 1;
    private final long value;

    public ZipLong(long value) {
        this.value = value;
    }

    public ZipLong(byte[] bytes) {
        this(bytes, 0);
    }

    public ZipLong(byte[] bytes, int offset) {
        this.value = getValue(bytes, offset);
    }

    public byte[] getBytes() {
        return getBytes(this.value);
    }

    public long getValue() {
        return this.value;
    }

    public static byte[] getBytes(long value) {
        return new byte[]{(byte) ((int) (value & 255)), (byte) ((int) ((value & 65280) >> 8)), (byte) ((int) ((value & 16711680) >> 16)), (byte) ((int) ((value & BYTE_3_MASK) >> 24))};
    }

    public static long getValue(byte[] bytes, int offset) {
        return (((((long) (bytes[offset + 3] << 24)) & BYTE_3_MASK) + ((long) ((bytes[offset + 2] << 16) & BYTE_2_MASK))) + ((long) ((bytes[offset + 1] << 8) & 65280))) + ((long) (bytes[offset] & 255));
    }

    public static long getValue(byte[] bytes) {
        return getValue(bytes, 0);
    }

    public boolean equals(Object o) {
        boolean z = false;
        if (o != null) {
            if (o instanceof ZipLong) {
                if (this.value == ((ZipLong) o).getValue()) {
                    z = true;
                }
                return z;
            }
        }
        return false;
    }

    public int hashCode() {
        return (int) this.value;
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException cnfe) {
            throw new RuntimeException(cnfe);
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ZipLong value: ");
        stringBuilder.append(this.value);
        return stringBuilder.toString();
    }
}
