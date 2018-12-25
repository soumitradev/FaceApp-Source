package com.parrot.arsdk.arupdater;

import com.parrot.arsdk.ardiscovery.ARDISCOVERY_PRODUCT_ENUM;
import com.parrot.arsdk.arsal.ARSALPrint;

public class ARUpdaterManager {
    private static final String TAG = "ARUpdaterManager";
    ARUpdaterDownloader downloader;
    private boolean isInit = false;
    private String localVersion = null;
    private long nativeManager = 0;
    ARUpdaterUploader uploader;

    private static native int nativeComparePlfVersions(String str, String str2);

    private native int nativeDelete(long j);

    private static native int nativeExtractUnixFileFromPlf(String str, String str2, String str3);

    private native long nativeNew() throws ARUpdaterException;

    private native boolean nativePlfVersionIsBlacklisted(int i, int i2, int i3, int i4);

    private native boolean nativePlfVersionIsUpToDate(long j, int i, String str, String str2) throws ARUpdaterException;

    private static native String nativeReadPlfVersion(String str);

    private static native void nativeStaticInit();

    static {
        nativeStaticInit();
    }

    public ARUpdaterManager() throws ARUpdaterException {
        if (!this.isInit) {
            this.nativeManager = nativeNew();
        }
        if (this.nativeManager != 0) {
            this.isInit = true;
        }
    }

    public void dispose() {
        if (this.nativeManager != 0) {
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

    public ARUpdaterDownloader getARUpdaterDownloader() {
        if (!this.isInit) {
            return null;
        }
        if (this.downloader == null) {
            this.downloader = new ARUpdaterDownloader(this.nativeManager);
        }
        return this.downloader;
    }

    public ARUpdaterUploader getARUpdaterUploader() {
        if (!this.isInit) {
            return null;
        }
        if (this.uploader == null) {
            this.uploader = new ARUpdaterUploader(this.nativeManager);
        }
        return this.uploader;
    }

    public ARUpdaterUploadPlfVersionInfo isPlfVersionUpToDate(ARDISCOVERY_PRODUCT_ENUM product, String remoteVersion, String rootFolder) throws ARUpdaterException {
        return new ARUpdaterUploadPlfVersionInfo(nativePlfVersionIsUpToDate(this.nativeManager, product.getValue(), remoteVersion, rootFolder), this.localVersion);
    }

    public boolean isPlfVersionBlacklisted(ARDISCOVERY_PRODUCT_ENUM product, int version, int edition, int extension) {
        return nativePlfVersionIsBlacklisted(product.getValue(), version, edition, extension);
    }

    public static String readPlfVersion(String plfPath) {
        return nativeReadPlfVersion(plfPath);
    }

    public static int comparePlfVersions(String version1, String version2) {
        return nativeComparePlfVersions(version1, version2);
    }

    public static ARUPDATER_ERROR_ENUM extractUnixFileFromPlf(String plfFileName, String outFolder, String unixFileName) {
        return ARUPDATER_ERROR_ENUM.getFromValue(nativeExtractUnixFileFromPlf(plfFileName, outFolder, unixFileName));
    }
}
