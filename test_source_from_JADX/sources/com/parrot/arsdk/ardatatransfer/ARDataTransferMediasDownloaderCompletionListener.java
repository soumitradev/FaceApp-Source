package com.parrot.arsdk.ardatatransfer;

public interface ARDataTransferMediasDownloaderCompletionListener {
    void didMediaComplete(Object obj, ARDataTransferMedia aRDataTransferMedia, ARDATATRANSFER_ERROR_ENUM ardatatransfer_error_enum);
}
