package com.google.common.hash;

import com.google.common.base.Preconditions;
import java.io.OutputStream;

class Funnels$SinkAsStream extends OutputStream {
    final PrimitiveSink sink;

    Funnels$SinkAsStream(PrimitiveSink sink) {
        this.sink = (PrimitiveSink) Preconditions.checkNotNull(sink);
    }

    public void write(int b) {
        this.sink.putByte((byte) b);
    }

    public void write(byte[] bytes) {
        this.sink.putBytes(bytes);
    }

    public void write(byte[] bytes, int off, int len) {
        this.sink.putBytes(bytes, off, len);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Funnels.asOutputStream(");
        stringBuilder.append(this.sink);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}
