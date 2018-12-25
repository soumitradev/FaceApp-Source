package com.parrot.arsdk.arstream;

import com.parrot.arsdk.arsal.ARNativeData;

public interface ARStreamReaderListener {
    ARNativeData didUpdateFrameStatus(ARSTREAM_READER_CAUSE_ENUM arstream_reader_cause_enum, ARNativeData aRNativeData, boolean z, int i, int i2);
}
