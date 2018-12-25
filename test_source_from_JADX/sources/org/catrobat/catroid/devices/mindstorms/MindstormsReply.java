package org.catrobat.catroid.devices.mindstorms;

public abstract class MindstormsReply {
    protected byte[] data;

    public abstract byte getCommandByte();

    public abstract byte getStatusByte();

    public abstract boolean hasError();

    public MindstormsReply(byte[] data) {
        this.data = (byte[]) data.clone();
    }

    public int getLength() {
        return this.data.length;
    }

    public byte[] getData() {
        return (byte[]) this.data.clone();
    }

    public byte[] getData(int offset, int length) {
        byte[] a = null;
        if (offset <= this.data.length - length) {
            a = new byte[length];
            for (int i = 0; i < length; i++) {
                a[i] = this.data[i + offset];
            }
        }
        return a;
    }

    public byte getByte(int number) {
        return this.data[number];
    }

    public int getShort(int offset) {
        return (short) ((this.data[offset] & 255) | ((this.data[offset + 1] & 255) << 8));
    }

    public int getInt(int offset) {
        return (((this.data[offset] & 255) | ((this.data[offset + 1] & 255) << 8)) | ((this.data[offset + 2] & 255) << 16)) | ((this.data[offset + 3] & 255) << 24);
    }
}
