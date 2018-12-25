package com.koushikdutta.async.stream;

import com.koushikdutta.async.AsyncServer;
import com.koushikdutta.async.ByteBufferList;
import com.koushikdutta.async.DataSink;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.callback.WritableCallback;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

public class OutputStreamDataSink implements DataSink {
    Exception closeException;
    boolean closeReported;
    CompletedCallback mClosedCallback;
    OutputStream mStream;
    WritableCallback mWritable;
    WritableCallback outputStreamCallback;
    AsyncServer server;

    public OutputStreamDataSink(AsyncServer server) {
        this(server, null);
    }

    public void end() {
        try {
            if (this.mStream != null) {
                this.mStream.close();
            }
            reportClose(null);
        } catch (IOException e) {
            reportClose(e);
        }
    }

    public OutputStreamDataSink(AsyncServer server, OutputStream stream) {
        this.server = server;
        setOutputStream(stream);
    }

    public void setOutputStream(OutputStream stream) {
        this.mStream = stream;
    }

    public OutputStream getOutputStream() throws IOException {
        return this.mStream;
    }

    public void write(ByteBufferList bb) {
        while (bb.size() > 0) {
            try {
                ByteBuffer b = bb.remove();
                getOutputStream().write(b.array(), b.arrayOffset() + b.position(), b.remaining());
                ByteBufferList.reclaim(b);
            } catch (IOException e) {
                reportClose(e);
            } catch (Throwable th) {
                bb.recycle();
            }
        }
        bb.recycle();
    }

    public void setWriteableCallback(WritableCallback handler) {
        this.mWritable = handler;
    }

    public WritableCallback getWriteableCallback() {
        return this.mWritable;
    }

    public boolean isOpen() {
        return this.closeReported;
    }

    public void reportClose(Exception ex) {
        if (!this.closeReported) {
            this.closeReported = true;
            this.closeException = ex;
            if (this.mClosedCallback != null) {
                this.mClosedCallback.onCompleted(this.closeException);
            }
        }
    }

    public void setClosedCallback(CompletedCallback handler) {
        this.mClosedCallback = handler;
    }

    public CompletedCallback getClosedCallback() {
        return this.mClosedCallback;
    }

    public AsyncServer getServer() {
        return this.server;
    }

    public void setOutputStreamWritableCallback(WritableCallback outputStreamCallback) {
        this.outputStreamCallback = outputStreamCallback;
    }
}
