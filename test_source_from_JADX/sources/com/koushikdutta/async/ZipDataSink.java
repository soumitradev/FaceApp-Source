package com.koushikdutta.async;

import com.koushikdutta.async.callback.CompletedCallback;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipDataSink extends FilteredDataSink {
    ByteArrayOutputStream bout = new ByteArrayOutputStream();
    ZipOutputStream zop = new ZipOutputStream(this.bout);

    public ZipDataSink(DataSink sink) {
        super(sink);
    }

    public void putNextEntry(ZipEntry ze) throws IOException {
        this.zop.putNextEntry(ze);
    }

    public void closeEntry() throws IOException {
        this.zop.closeEntry();
    }

    protected void report(Exception e) {
        CompletedCallback closed = getClosedCallback();
        if (closed != null) {
            closed.onCompleted(e);
        }
    }

    public void end() {
        try {
            this.zop.close();
            setMaxBuffer(Integer.MAX_VALUE);
            write(new ByteBufferList());
            super.end();
        } catch (IOException e) {
            report(e);
        }
    }

    public ByteBufferList filter(ByteBufferList bb) {
        if (bb != null) {
            while (bb.size() > 0) {
                try {
                    ByteBuffer b = bb.remove();
                    ByteBufferList byteBufferList = this.zop;
                    ByteBufferList.writeOutputStream(byteBufferList, b);
                    ByteBufferList.reclaim(b);
                } catch (IOException e) {
                    report(e);
                    byteBufferList = null;
                    return byteBufferList;
                } finally {
                    if (bb != null) {
                        bb.recycle();
                    }
                }
            }
        }
        IOException e2 = new ByteBufferList(this.bout.toByteArray());
        this.bout.reset();
        if (bb != null) {
            bb.recycle();
        }
        return e2;
    }
}
