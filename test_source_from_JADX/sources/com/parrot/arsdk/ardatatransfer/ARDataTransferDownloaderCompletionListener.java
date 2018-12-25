package com.parrot.arsdk.ardatatransfer;

public interface ARDataTransferDownloaderCompletionListener {
    void didDownloadComplete(Object obj, ARDATATRANSFER_ERROR_ENUM ardatatransfer_error_enum);
}
