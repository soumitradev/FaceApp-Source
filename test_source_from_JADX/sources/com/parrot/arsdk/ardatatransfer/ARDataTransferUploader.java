package com.parrot.arsdk.ardatatransfer;

import com.parrot.arsdk.arsal.ARSALPrint;
import com.parrot.arsdk.arutils.ARUtilsManager;

public class ARDataTransferUploader {
    private static final String TAG = ARDataTransferMediasDownloader.class.getSimpleName();
    private boolean isInit = false;
    private long nativeManager = 0;
    private Runnable uploaderRunnable = null;

    /* renamed from: com.parrot.arsdk.ardatatransfer.ARDataTransferUploader$1 */
    class C15711 implements Runnable {
        C15711() {
        }

        public void run() {
            ARDataTransferUploader.this.nativeThreadRun(ARDataTransferUploader.this.nativeManager);
        }
    }

    private native int nativeCancelThread(long j);

    private native int nativeDelete(long j);

    private native int nativeNew(long j, long j2, String str, String str2, ARDataTransferUploaderProgressListener aRDataTransferUploaderProgressListener, Object obj, ARDataTransferUploaderCompletionListener aRDataTransferUploaderCompletionListener, Object obj2, int i);

    private static native boolean nativeStaticInit();

    private native void nativeThreadRun(long j);

    static {
        nativeStaticInit();
    }

    protected ARDataTransferUploader(long _nativeManager) {
        this.nativeManager = _nativeManager;
        this.uploaderRunnable = new C15711();
    }

    public void createUploader(ARUtilsManager utilsManager, String remotePath, String localPath, ARDataTransferUploaderProgressListener progressListener, Object progressArg, ARDataTransferUploaderCompletionListener completionListener, Object completionArg, ARDATATRANSFER_UPLOADER_RESUME_ENUM resume) throws ARDataTransferException {
        ARDATATRANSFER_ERROR_ENUM error = ARDATATRANSFER_ERROR_ENUM.getFromValue(nativeNew(this.nativeManager, utilsManager.getManager(), remotePath, localPath, progressListener, progressArg, completionListener, completionArg, resume.getValue()));
        if (error != ARDATATRANSFER_ERROR_ENUM.ARDATATRANSFER_OK) {
            throw new ARDataTransferException(error);
        }
        r12.isInit = true;
    }

    public ARDATATRANSFER_ERROR_ENUM dispose() {
        ARDATATRANSFER_ERROR_ENUM error = ARDATATRANSFER_ERROR_ENUM.ARDATATRANSFER_OK;
        if (this.isInit) {
            error = ARDATATRANSFER_ERROR_ENUM.getFromValue(nativeDelete(this.nativeManager));
            if (error == ARDATATRANSFER_ERROR_ENUM.ARDATATRANSFER_OK) {
                this.isInit = false;
            }
        }
        return error;
    }

    protected void finalize() throws Throwable {
        try {
            if (this.isInit) {
                String str = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Object ");
                stringBuilder.append(this);
                stringBuilder.append(" was not disposed !");
                ARSALPrint.m532e(str, stringBuilder.toString());
                if (dispose() != ARDATATRANSFER_ERROR_ENUM.ARDATATRANSFER_OK) {
                    String str2 = TAG;
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("Unable to dispose object ");
                    stringBuilder2.append(this);
                    stringBuilder2.append(" ... leaking memory !");
                    ARSALPrint.m532e(str2, stringBuilder2.toString());
                }
            }
            super.finalize();
        } catch (Throwable th) {
            super.finalize();
        }
    }

    public Runnable getUploaderRunnable() {
        if (this.isInit) {
            return this.uploaderRunnable;
        }
        return null;
    }

    public ARDATATRANSFER_ERROR_ENUM cancelThread() {
        return ARDATATRANSFER_ERROR_ENUM.getFromValue(nativeCancelThread(this.nativeManager));
    }
}
