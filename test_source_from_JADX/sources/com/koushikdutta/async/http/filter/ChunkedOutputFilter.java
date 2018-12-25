package com.koushikdutta.async.http.filter;

import com.koushikdutta.async.ByteBufferList;
import com.koushikdutta.async.DataSink;
import com.koushikdutta.async.FilteredDataSink;
import java.nio.ByteBuffer;

public class ChunkedOutputFilter extends FilteredDataSink {
    public ChunkedOutputFilter(DataSink sink) {
        super(sink);
    }

    public ByteBufferList filter(ByteBufferList bb) {
        String chunkLen = new StringBuilder();
        chunkLen.append(Integer.toString(bb.remaining(), 16));
        chunkLen.append("\r\n");
        bb.addFirst(ByteBuffer.wrap(chunkLen.toString().getBytes()));
        bb.add(ByteBuffer.wrap("\r\n".getBytes()));
        return bb;
    }

    public void end() {
        setMaxBuffer(Integer.MAX_VALUE);
        write(new ByteBufferList());
        setMaxBuffer(0);
    }
}
