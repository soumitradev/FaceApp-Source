package com.parrot.arsdk.arsal;

public class ARNativeData {
    public static final int DEFAULT_SIZE = 128;
    private static final String TAG = ARNativeData.class.getSimpleName();
    protected int capacity;
    private Throwable constructorCallStack;
    protected long pointer;
    protected int used;
    protected boolean valid;

    private native long allocateData(int i);

    private native boolean copyData(long j, int i, long j2, int i2);

    private native boolean copyJavaData(long j, int i, byte[] bArr, int i2);

    private native void freeData(long j);

    private native byte[] generateByteArray(long j, int i, int i2);

    private native long reallocateData(long j, int i);

    public ARNativeData(int size) {
        this.pointer = allocateData(size);
        this.capacity = 0;
        this.valid = false;
        if (this.pointer != 0) {
            this.capacity = size;
            this.valid = true;
        }
        this.used = 0;
        this.constructorCallStack = new Throwable();
    }

    public ARNativeData() {
        this(128);
    }

    public ARNativeData(ARNativeData data) {
        this(data.getDataSize());
        if (!copyData(this.pointer, this.capacity, data.getData(), data.getDataSize())) {
            dispose();
        }
    }

    public ARNativeData(long data, int dataSize) {
        this(dataSize);
        if (!copyData(this.pointer, this.capacity, data, dataSize)) {
            dispose();
        }
    }

    public ARNativeData(ARNativeData data, int capacity) {
        int totalSize = data.getDataSize();
        if (capacity > totalSize) {
            totalSize = capacity;
        }
        this.pointer = allocateData(totalSize);
        this.capacity = 0;
        this.valid = false;
        if (this.pointer != 0) {
            this.capacity = totalSize;
            this.valid = true;
        }
        this.used = 0;
        this.constructorCallStack = new Throwable();
        if (!copyData(this.pointer, this.capacity, data.getData(), data.getDataSize())) {
            dispose();
        }
    }

    protected void finalize() throws Throwable {
        try {
            if (this.valid) {
                String str = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(this);
                stringBuilder.append(": Finalize error -> dispose () was not called !");
                ARSALPrint.m539w(str, stringBuilder.toString(), this.constructorCallStack);
                dispose();
            }
            super.finalize();
        } catch (Throwable th) {
            super.finalize();
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(getClass().getSimpleName());
        stringBuilder.append(" { Valid : ");
        stringBuilder.append(this.valid);
        stringBuilder.append(" | ");
        stringBuilder.append("Used/Capacity (bytes) : ");
        stringBuilder.append(this.used);
        stringBuilder.append("/");
        stringBuilder.append(this.capacity);
        stringBuilder.append(" | ");
        stringBuilder.append("C Pointer : ");
        stringBuilder.append(this.pointer);
        stringBuilder.append(" }");
        return stringBuilder.toString();
    }

    public boolean isValid() {
        return this.valid;
    }

    public boolean setUsedSize(int size) {
        if (this.valid) {
            if (size <= this.capacity) {
                this.used = size;
                return true;
            }
        }
        return false;
    }

    public int getCapacity() {
        if (this.valid) {
            return this.capacity;
        }
        return 0;
    }

    public long getData() {
        if (this.valid) {
            return this.pointer;
        }
        return 0;
    }

    public int getDataSize() {
        if (this.valid) {
            return this.used;
        }
        return 0;
    }

    public byte[] getByteData() {
        if (this.valid) {
            return generateByteArray(this.pointer, this.capacity, this.used);
        }
        return null;
    }

    public boolean copyByteData(byte[] src, int dataSize) {
        if (!this.valid || !copyJavaData(this.pointer, this.capacity, src, dataSize)) {
            return false;
        }
        setUsedSize(dataSize);
        return true;
    }

    public void dispose() {
        if (this.valid) {
            freeData(this.pointer);
        }
        this.valid = false;
        this.pointer = 0;
        this.capacity = 0;
        this.used = 0;
    }

    public boolean ensureCapacityIsAtLeast(int minimumCapacity) {
        if (this.capacity >= minimumCapacity) {
            return true;
        }
        long newPointer = reallocateData(this.pointer, minimumCapacity);
        if (newPointer == 0) {
            return false;
        }
        this.capacity = minimumCapacity;
        this.pointer = newPointer;
        return true;
    }
}
