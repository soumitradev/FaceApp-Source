package com.parrot.arsdk.arcontroller;

import com.parrot.arsdk.arsal.ARNativeData;

public class ARFrame extends ARNativeData {
    private static final int ARFRAME_DEFAULT_CAPACITY = 60000;
    private boolean available;
    private boolean isIFrame;
    private ARNativeData metadata;
    private int missed;
    private long timestamp;

    public ARFrame() {
        super((int) ARFRAME_DEFAULT_CAPACITY);
        initialize();
    }

    public ARFrame(int defaultCapacity) {
        super(defaultCapacity);
        initialize();
    }

    public ARFrame(long data, int dataCapacity, int dataSize, boolean isIFrame, int missed, long timestamp, long metadata, int metadataSize) {
        super(data, dataCapacity);
        initialize();
        setUsedSize(dataSize);
        this.missed = missed;
        this.isIFrame = isIFrame;
        this.timestamp = timestamp;
        if (metadata != 0) {
            this.metadata = new ARNativeData(metadata, metadataSize);
            this.metadata.setUsedSize(metadataSize);
        }
    }

    private void initialize() {
        this.missed = 0;
        this.isIFrame = false;
        this.available = true;
    }

    public void dispose() {
        if (this.metadata != null) {
            this.metadata.dispose();
        }
        super.dispose();
    }

    public int getMissed() {
        return this.missed;
    }

    public void setMissed(int missed) {
        this.missed = missed;
    }

    public boolean isIFrame() {
        return this.isIFrame;
    }

    public void setIFrame(boolean isIFrame) {
        this.isIFrame = isIFrame;
    }

    public boolean isAvailable() {
        return this.available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public ARNativeData getMetadata() {
        return this.metadata;
    }
}
