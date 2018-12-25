package com.parrot.arsdk.ardatatransfer;

import com.parrot.arsdk.arsal.ARSALPrint;
import com.parrot.arsdk.arutils.ARUtilsManager;

public class ARDataTransferDataDownloader {
    private static final String TAG = ARDataTransferDataDownloader.class.getSimpleName();
    private Runnable downloaderRunnable = null;
    private boolean isInit = false;
    private long nativeManager = 0;

    /* renamed from: com.parrot.arsdk.ardatatransfer.ARDataTransferDataDownloader$1 */
    class C15671 implements Runnable {
        C15671() {
        }

        public void run() {
            ARDataTransferDataDownloader.this.nativeThreadRun(ARDataTransferDataDownloader.this.nativeManager);
        }
    }

    private native int nativeCancelAvailableFiles(long j);

    private native int nativeCancelThread(long j);

    private native int nativeDelete(long j);

    private native long nativeGetAvailableFiles(long j) throws ARDataTransferException;

    private native int nativeNew(long j, long j2, long j3, String str, String str2, ARDataTransferDataDownloaderFileCompletionListener aRDataTransferDataDownloaderFileCompletionListener, Object obj);

    private static native boolean nativeStaticInit();

    private native void nativeThreadRun(long j);

    static {
        nativeStaticInit();
    }

    protected ARDataTransferDataDownloader(long _nativeManager) {
        this.nativeManager = _nativeManager;
        this.downloaderRunnable = new C15671();
    }

    public void createDataDownloader(ARUtilsManager utilsListManager, ARUtilsManager utilsDataManager, String remoteDirectory, String localDirectory, ARDataTransferDataDownloaderFileCompletionListener fileCompletionListener, Object fileCompletionArg) throws ARDataTransferException {
        ARDATATRANSFER_ERROR_ENUM error = ARDATATRANSFER_ERROR_ENUM.getFromValue(nativeNew(this.nativeManager, utilsListManager.getManager(), utilsDataManager.getManager(), remoteDirectory, localDirectory, fileCompletionListener, fileCompletionArg));
        if (error != ARDATATRANSFER_ERROR_ENUM.ARDATATRANSFER_OK) {
            throw new ARDataTransferException(error);
        }
        r11.isInit = true;
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

    public long getAvailableFiles() throws ARDataTransferException {
        return nativeGetAvailableFiles(this.nativeManager);
    }

    public ARDATATRANSFER_ERROR_ENUM cancelAvailableFiles() {
        return ARDATATRANSFER_ERROR_ENUM.getFromValue(nativeCancelAvailableFiles(this.nativeManager));
    }

    public Runnable getDownloaderRunnable() {
        if (this.isInit) {
            return this.downloaderRunnable;
        }
        return null;
    }

    public ARDATATRANSFER_ERROR_ENUM cancelThread() {
        return ARDATATRANSFER_ERROR_ENUM.getFromValue(nativeCancelThread(this.nativeManager));
    }
}
