package com.koushikdutta.async;

public class FilteredDataSink extends BufferedDataSink {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    public FilteredDataSink(DataSink sink) {
        super(sink);
        setMaxBuffer(0);
    }

    public ByteBufferList filter(ByteBufferList bb) {
        return bb;
    }

    public final void write(ByteBufferList bb) {
        if (!isBuffering() || getMaxBuffer() == Integer.MAX_VALUE) {
            super.write(filter(bb), true);
            if (bb != null) {
                bb.recycle();
            }
        }
    }
}
