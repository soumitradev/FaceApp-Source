package com.koushikdutta.async.http.filter;

import com.koushikdutta.async.ByteBufferList;
import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.FilteredDataEmitter;
import com.koushikdutta.async.Util;
import java.nio.ByteBuffer;
import java.util.zip.Inflater;

public class InflaterInputFilter extends FilteredDataEmitter {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private Inflater mInflater;
    ByteBufferList transformed;

    protected void report(Exception e) {
        this.mInflater.end();
        if (e != null && this.mInflater.getRemaining() > 0) {
            e = new DataRemainingException("data still remaining in inflater", e);
        }
        super.report(e);
    }

    public void onDataAvailable(DataEmitter emitter, ByteBufferList bb) {
        try {
            ByteBuffer output = ByteBufferList.obtain(bb.remaining() * 2);
            while (bb.size() > 0) {
                ByteBuffer b = bb.remove();
                if (b.hasRemaining()) {
                    int totalRead = b.remaining();
                    this.mInflater.setInput(b.array(), b.arrayOffset() + b.position(), b.remaining());
                    do {
                        output.position(output.position() + this.mInflater.inflate(output.array(), output.arrayOffset() + output.position(), output.remaining()));
                        if (!output.hasRemaining()) {
                            output.flip();
                            this.transformed.add(output);
                            output = ByteBufferList.obtain(output.capacity() * 2);
                        }
                        if (this.mInflater.needsInput()) {
                            break;
                        }
                    } while (!this.mInflater.finished());
                }
                ByteBufferList.reclaim(b);
            }
            output.flip();
            this.transformed.add(output);
            Util.emitAllData(this, this.transformed);
        } catch (Exception ex) {
            report(ex);
        }
    }

    public InflaterInputFilter() {
        this(new Inflater());
    }

    public InflaterInputFilter(Inflater inflater) {
        this.transformed = new ByteBufferList();
        this.mInflater = inflater;
    }
}
