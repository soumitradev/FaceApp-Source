package com.koushikdutta.async;

import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.callback.WritableCallback;

public class BufferedDataSink implements DataSink {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    boolean endPending;
    boolean forceBuffering;
    DataSink mDataSink;
    int mMaxBuffer = Integer.MAX_VALUE;
    ByteBufferList mPendingWrites = new ByteBufferList();
    WritableCallback mWritable;

    /* renamed from: com.koushikdutta.async.BufferedDataSink$3 */
    class C06403 implements Runnable {
        C06403() {
        }

        public void run() {
            BufferedDataSink.this.end();
        }
    }

    /* renamed from: com.koushikdutta.async.BufferedDataSink$1 */
    class C10891 implements WritableCallback {
        C10891() {
        }

        public void onWriteable() {
            BufferedDataSink.this.writePending();
        }
    }

    public BufferedDataSink(DataSink datasink) {
        setDataSink(datasink);
    }

    public boolean isBuffering() {
        if (!this.mPendingWrites.hasRemaining()) {
            if (!this.forceBuffering) {
                return false;
            }
        }
        return true;
    }

    public DataSink getDataSink() {
        return this.mDataSink;
    }

    public void forceBuffering(boolean forceBuffering) {
        this.forceBuffering = forceBuffering;
        if (!forceBuffering) {
            writePending();
        }
    }

    public void setDataSink(DataSink datasink) {
        this.mDataSink = datasink;
        this.mDataSink.setWriteableCallback(new C10891());
    }

    private void writePending() {
        if (!this.forceBuffering) {
            if (this.mPendingWrites.hasRemaining()) {
                this.mDataSink.write(this.mPendingWrites);
                if (this.mPendingWrites.remaining() == 0 && this.endPending) {
                    this.mDataSink.end();
                }
            }
            if (!(this.mPendingWrites.hasRemaining() || this.mWritable == null)) {
                this.mWritable.onWriteable();
            }
        }
    }

    public void write(ByteBufferList bb) {
        write(bb, false);
    }

    protected void write(final ByteBufferList bb, final boolean ignoreBuffer) {
        if (getServer().getAffinity() != Thread.currentThread()) {
            getServer().run(new Runnable() {
                public void run() {
                    BufferedDataSink.this.write(bb, ignoreBuffer);
                }
            });
            return;
        }
        if (!isBuffering()) {
            this.mDataSink.write(bb);
        }
        if (bb.remaining() > 0) {
            int toRead = Math.min(bb.remaining(), this.mMaxBuffer);
            if (ignoreBuffer) {
                toRead = bb.remaining();
            }
            if (toRead > 0) {
                bb.get(this.mPendingWrites, toRead);
            }
        }
    }

    public void setWriteableCallback(WritableCallback handler) {
        this.mWritable = handler;
    }

    public WritableCallback getWriteableCallback() {
        return this.mWritable;
    }

    public int remaining() {
        return this.mPendingWrites.remaining();
    }

    public int getMaxBuffer() {
        return this.mMaxBuffer;
    }

    public void setMaxBuffer(int maxBuffer) {
        this.mMaxBuffer = maxBuffer;
    }

    public boolean isOpen() {
        return this.mDataSink.isOpen();
    }

    public void end() {
        if (getServer().getAffinity() != Thread.currentThread()) {
            getServer().run(new C06403());
        } else if (this.mPendingWrites.hasRemaining()) {
            this.endPending = true;
        } else {
            this.mDataSink.end();
        }
    }

    public void setClosedCallback(CompletedCallback handler) {
        this.mDataSink.setClosedCallback(handler);
    }

    public CompletedCallback getClosedCallback() {
        return this.mDataSink.getClosedCallback();
    }

    public AsyncServer getServer() {
        return this.mDataSink.getServer();
    }
}
