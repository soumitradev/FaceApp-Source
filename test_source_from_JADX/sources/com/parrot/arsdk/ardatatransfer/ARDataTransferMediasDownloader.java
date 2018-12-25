package com.parrot.arsdk.ardatatransfer;

import com.parrot.arsdk.arsal.ARSALPrint;
import com.parrot.arsdk.arutils.ARUtilsManager;

public class ARDataTransferMediasDownloader {
    private static final String TAG = ARDataTransferMediasDownloader.class.getSimpleName();
    private Runnable downloaderRunnable = null;
    private boolean isInit = false;
    private long nativeManager = 0;

    /* renamed from: com.parrot.arsdk.ardatatransfer.ARDataTransferMediasDownloader$1 */
    class C15701 implements Runnable {
        C15701() {
        }

        public void run() {
            ARDataTransferMediasDownloader.this.nativeQueueThreadRun(ARDataTransferMediasDownloader.this.nativeManager);
        }
    }

    private native int nativeAddMediaToQueue(long j, ARDataTransferMedia aRDataTransferMedia, ARDataTransferMediasDownloaderProgressListener aRDataTransferMediasDownloaderProgressListener, Object obj, ARDataTransferMediasDownloaderCompletionListener aRDataTransferMediasDownloaderCompletionListener, Object obj2);

    private native int nativeCancelGetAvailableMedias(long j);

    private native int nativeCancelQueueThread(long j);

    private native int nativeDelete(long j);

    private native int nativeDeleteMedia(long j, ARDataTransferMedia aRDataTransferMedia);

    private native ARDataTransferMedia nativeGetAvailableMediaAtIndex(long j, int i);

    private native int nativeGetAvailableMediasAsync(long j, ARDataTransferMediasDownloaderAvailableMediaListener aRDataTransferMediasDownloaderAvailableMediaListener, Object obj);

    private native int nativeGetAvailableMediasSync(long j, boolean z);

    private native byte[] nativeGetMediaThumbnail(long j, ARDataTransferMedia aRDataTransferMedia);

    private native int nativeNew(long j, long j2, long j3, String str, String str2);

    private native void nativeQueueThreadRun(long j);

    private static native boolean nativeStaticInit();

    static {
        nativeStaticInit();
    }

    protected ARDataTransferMediasDownloader(long _nativeManager) {
        this.nativeManager = _nativeManager;
        this.downloaderRunnable = new C15701();
    }

    public void createMediasDownloader(ARUtilsManager utilsListManager, ARUtilsManager utilsQueueManager, String remoteDirectory, String localDirectory) throws ARDataTransferException {
        ARDATATRANSFER_ERROR_ENUM error = ARDATATRANSFER_ERROR_ENUM.getFromValue(nativeNew(this.nativeManager, utilsListManager.getManager(), utilsQueueManager.getManager(), remoteDirectory, localDirectory));
        if (error != ARDATATRANSFER_ERROR_ENUM.ARDATATRANSFER_OK) {
            throw new ARDataTransferException(error);
        }
        this.isInit = true;
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

    public int getAvailableMediasSync(boolean withThumbnail) throws ARDataTransferException {
        return nativeGetAvailableMediasSync(this.nativeManager, withThumbnail);
    }

    public ARDataTransferMedia getAvailableMediaAtIndex(int index) throws ARDataTransferException {
        return nativeGetAvailableMediaAtIndex(this.nativeManager, index);
    }

    public void getAvailableMediasAsync(ARDataTransferMediasDownloaderAvailableMediaListener availableMediaListener, Object availableMediaArg) throws ARDataTransferException {
        ARDATATRANSFER_ERROR_ENUM error = ARDATATRANSFER_ERROR_ENUM.getFromValue(nativeGetAvailableMediasAsync(this.nativeManager, availableMediaListener, availableMediaArg));
        if (error != ARDATATRANSFER_ERROR_ENUM.ARDATATRANSFER_OK) {
            throw new ARDataTransferException(error);
        }
    }

    public ARDATATRANSFER_ERROR_ENUM cancelGetAvailableMedias() {
        return ARDATATRANSFER_ERROR_ENUM.getFromValue(nativeCancelGetAvailableMedias(this.nativeManager));
    }

    public ARDATATRANSFER_ERROR_ENUM deleteMedia(ARDataTransferMedia media) {
        return ARDATATRANSFER_ERROR_ENUM.getFromValue(nativeDeleteMedia(this.nativeManager, media));
    }

    public byte[] getMediaThumbnail(ARDataTransferMedia media) {
        byte[] result = nativeGetMediaThumbnail(this.nativeManager, media);
        if (result != null) {
            media.setThumbail(result);
        }
        return media.getThumbnail();
    }

    public void addMediaToQueue(ARDataTransferMedia media, ARDataTransferMediasDownloaderProgressListener progressListener, Object progressArg, ARDataTransferMediasDownloaderCompletionListener completionListener, Object completionArg) throws ARDataTransferException {
        ARDATATRANSFER_ERROR_ENUM error = ARDATATRANSFER_ERROR_ENUM.getFromValue(nativeAddMediaToQueue(this.nativeManager, media, progressListener, progressArg, completionListener, completionArg));
        if (error != ARDATATRANSFER_ERROR_ENUM.ARDATATRANSFER_OK) {
            throw new ARDataTransferException(error);
        }
    }

    public Runnable getDownloaderQueueRunnable() {
        if (this.isInit) {
            return this.downloaderRunnable;
        }
        return null;
    }

    public ARDATATRANSFER_ERROR_ENUM cancelQueueThread() {
        return ARDATATRANSFER_ERROR_ENUM.getFromValue(nativeCancelQueueThread(this.nativeManager));
    }
}
