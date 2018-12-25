package com.koushikdutta.async.http.filter;

import com.koushikdutta.async.ByteBufferList;
import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.FilteredDataEmitter;

public class ContentLengthFilter extends FilteredDataEmitter {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    long contentLength;
    long totalRead;
    ByteBufferList transformed = new ByteBufferList();

    public ContentLengthFilter(long contentLength) {
        this.contentLength = contentLength;
    }

    protected void report(Exception e) {
        if (e == null && this.totalRead != this.contentLength) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("End of data reached before content length was read: ");
            stringBuilder.append(this.totalRead);
            stringBuilder.append("/");
            stringBuilder.append(this.contentLength);
            stringBuilder.append(" Paused: ");
            stringBuilder.append(isPaused());
            e = new PrematureDataEndException(stringBuilder.toString());
        }
        super.report(e);
    }

    public void onDataAvailable(DataEmitter emitter, ByteBufferList bb) {
        bb.get(this.transformed, (int) Math.min(this.contentLength - this.totalRead, (long) bb.remaining()));
        int beforeRead = this.transformed.remaining();
        super.onDataAvailable(emitter, this.transformed);
        this.totalRead += (long) (beforeRead - this.transformed.remaining());
        this.transformed.get(bb);
        if (this.totalRead == this.contentLength) {
            report(null);
        }
    }
}
