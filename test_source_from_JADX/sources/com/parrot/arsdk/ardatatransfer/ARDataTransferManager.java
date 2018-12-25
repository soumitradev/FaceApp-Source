package com.parrot.arsdk.ardatatransfer;

import com.parrot.arsdk.arsal.ARSALPrint;

public class ARDataTransferManager {
    private static final String TAG = ARDataTransferManager.class.getSimpleName();
    ARDataTransferDataDownloader dataDownloader = null;
    ARDataTransferDownloader downloader = null;
    private boolean isInit = false;
    ARDataTransferMediasDownloader mediasDownloader = null;
    private long nativeManager = 0;
    ARDataTransferUploader uploader = null;

    private native void nativeDelete(long j);

    private native long nativeNew() throws ARDataTransferException;

    private static native boolean nativeStaticInit();

    static {
        nativeStaticInit();
    }

    public ARDataTransferManager() throws ARDataTransferException {
        if (!this.isInit) {
            this.nativeManager = nativeNew();
        }
        if (this.nativeManager != 0) {
            this.isInit = true;
        }
    }

    public void dispose() {
        if (this.nativeManager != 0) {
            if (this.dataDownloader != null) {
                this.dataDownloader.dispose();
            }
            if (this.mediasDownloader != null) {
                this.mediasDownloader.dispose();
            }
            if (this.downloader != null) {
                this.downloader.dispose();
            }
            if (this.uploader != null) {
                this.uploader.dispose();
            }
            nativeDelete(this.nativeManager);
            this.nativeManager = 0;
            this.isInit = false;
        }
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
                dispose();
            }
            super.finalize();
        } catch (Throwable th) {
            super.finalize();
        }
    }

    public boolean isInitialized() {
        return this.isInit;
    }

    public ARDataTransferDataDownloader getARDataTransferDataDownloader() {
        if (!this.isInit) {
            return null;
        }
        if (this.dataDownloader == null) {
            this.dataDownloader = new ARDataTransferDataDownloader(this.nativeManager);
        }
        return this.dataDownloader;
    }

    public ARDataTransferMediasDownloader getARDataTransferMediasDownloader() {
        if (!this.isInit) {
            return null;
        }
        if (this.mediasDownloader == null) {
            this.mediasDownloader = new ARDataTransferMediasDownloader(this.nativeManager);
        }
        return this.mediasDownloader;
    }

    public ARDataTransferDownloader getARDataTransferDownloader() {
        if (!this.isInit) {
            return null;
        }
        if (this.downloader == null) {
            this.downloader = new ARDataTransferDownloader(this.nativeManager);
        }
        return this.downloader;
    }

    public ARDataTransferUploader getARDataTransferUploader() {
        if (!this.isInit) {
            return null;
        }
        if (this.uploader == null) {
            this.uploader = new ARDataTransferUploader(this.nativeManager);
        }
        return this.uploader;
    }
}
