package com.koushikdutta.async.http.spdy;

import com.koushikdutta.async.BufferedDataSink;
import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.http.Protocol;
import com.koushikdutta.async.http.spdy.FrameReader.Handler;

interface Variant {
    Protocol getProtocol();

    int maxFrameSize();

    FrameReader newReader(DataEmitter dataEmitter, Handler handler, boolean z);

    FrameWriter newWriter(BufferedDataSink bufferedDataSink, boolean z);
}
