package com.koushikdutta.async;

import com.koushikdutta.async.callback.DataCallback;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class LineEmitter implements DataCallback {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    Charset charset;
    ByteBufferList data;
    StringCallback mLineCallback;

    public interface StringCallback {
        void onStringAvailable(String str);
    }

    public LineEmitter() {
        this(null);
    }

    public LineEmitter(Charset charset) {
        this.data = new ByteBufferList();
        this.charset = charset;
    }

    public void setLineCallback(StringCallback callback) {
        this.mLineCallback = callback;
    }

    public StringCallback getLineCallback() {
        return this.mLineCallback;
    }

    public void onDataAvailable(DataEmitter emitter, ByteBufferList bb) {
        ByteBuffer buffer = ByteBuffer.allocate(bb.remaining());
        while (bb.remaining() > 0) {
            byte b = bb.get();
            if (b == (byte) 10) {
                buffer.flip();
                this.data.add(buffer);
                this.mLineCallback.onStringAvailable(this.data.readString(this.charset));
                this.data = new ByteBufferList();
                return;
            }
            buffer.put(b);
        }
        buffer.flip();
        this.data.add(buffer);
    }
}
