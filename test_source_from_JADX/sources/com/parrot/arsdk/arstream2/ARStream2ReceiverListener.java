package com.parrot.arsdk.arstream2;

import java.nio.ByteBuffer;

public interface ARStream2ReceiverListener {
    int getFreeBuffer();

    void onBufferReady(int i, long j, int i2, long j2, long j3, long j4, ARSTREAM2_STREAM_RECEIVER_AU_SYNC_TYPE_ENUM arstream2_stream_receiver_au_sync_type_enum);

    ByteBuffer[] onSpsPpsReady(ByteBuffer byteBuffer, ByteBuffer byteBuffer2);
}
