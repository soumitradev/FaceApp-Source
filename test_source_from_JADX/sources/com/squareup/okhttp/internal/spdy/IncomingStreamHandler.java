package com.squareup.okhttp.internal.spdy;

import java.io.IOException;

public interface IncomingStreamHandler {
    public static final IncomingStreamHandler REFUSE_INCOMING_STREAMS = new C20381();

    /* renamed from: com.squareup.okhttp.internal.spdy.IncomingStreamHandler$1 */
    static class C20381 implements IncomingStreamHandler {
        C20381() {
        }

        public void receive(SpdyStream stream) throws IOException {
            stream.close(ErrorCode.REFUSED_STREAM);
        }
    }

    void receive(SpdyStream spdyStream) throws IOException;
}
