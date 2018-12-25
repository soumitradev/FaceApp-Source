package com.parrot.arsdk.ardatatransfer;

public interface ARDataTransferUploaderCompletionListener {
    void didUploadComplete(Object obj, ARDATATRANSFER_ERROR_ENUM ardatatransfer_error_enum);
}
