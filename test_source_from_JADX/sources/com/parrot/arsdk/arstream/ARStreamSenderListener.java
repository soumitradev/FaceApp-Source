package com.parrot.arsdk.arstream;

import com.parrot.arsdk.arsal.ARNativeData;

public interface ARStreamSenderListener {
    void didUpdateFrameStatus(ARSTREAM_SENDER_STATUS_ENUM arstream_sender_status_enum, ARNativeData aRNativeData);
}
