package com.parrot.arsdk.arupdater;

import com.parrot.arsdk.ardiscovery.ARDISCOVERY_PRODUCT_ENUM;
import com.parrot.arsdk.arsal.ARSALMd5Manager;
import com.parrot.arsdk.arsal.ARSALPrint;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class ARUpdaterDownloader {
    private static final String TAG = "ARUpdaterDownloader";
    private Runnable downloaderRunnable = null;
    private boolean isInit = false;
    private long nativeManager = 0;

    /* renamed from: com.parrot.arsdk.arupdater.ARUpdaterDownloader$1 */
    class C16241 implements Runnable {
        C16241() {
        }

        public void run() {
            ARUpdaterDownloader.this.nativeThreadRun(ARUpdaterDownloader.this.nativeManager);
        }
    }

    private native int nativeCancelThread(long j);

    private native int nativeCheckUpdatesAsync(long j);

    private native int nativeCheckUpdatesSync(long j) throws ARUpdaterException;

    private native int nativeDelete(long j);

    private native int nativeGetBlacklistedFirmwareVersionsSync(long j, int i, int[] iArr, Object[] objArr) throws ARUpdaterException;

    private native ARUpdaterDownloadInfo[] nativeGetUpdatesInfoSync(long j) throws ARUpdaterException;

    private native int nativeNew(long j, String str, long j2, int i, String str2, ARUpdaterShouldDownloadPlfListener aRUpdaterShouldDownloadPlfListener, Object obj, ARUpdaterWillDownloadPlfListener aRUpdaterWillDownloadPlfListener, Object obj2, ARUpdaterPlfDownloadProgressListener aRUpdaterPlfDownloadProgressListener, Object obj3, ARUpdaterPlfDownloadCompletionListener aRUpdaterPlfDownloadCompletionListener, Object obj4);

    private native int nativeSetUpdatesProductList(long j, int[] iArr);

    private static native void nativeStaticInit();

    private native void nativeThreadRun(long j);

    static {
        nativeStaticInit();
    }

    protected ARUpdaterDownloader(long _nativeManager) {
        this.nativeManager = _nativeManager;
        this.downloaderRunnable = new C16241();
    }

    public void createUpdaterDownloader(String rootFolder, ARSALMd5Manager md5Manager, String appVersion, ARUpdaterShouldDownloadPlfListener shouldDownloadPlfListener, Object downloadArgs, ARUpdaterWillDownloadPlfListener willDownloadPlfListener, Object willDownloadPlfArgs, ARUpdaterPlfDownloadProgressListener plfDownloadProgressListener, Object progressArgs, ARUpdaterPlfDownloadCompletionListener plfDownloadCompletionListener, Object completionArgs) throws ARUpdaterException {
        if (ARUPDATER_ERROR_ENUM.getFromValue(nativeNew(this.nativeManager, rootFolder, md5Manager.getNativeManager(), ARUPDATER_Downloader_Platforms_ENUM.ARUPDATER_DOWNLOADER_ANDROID_PLATFORM.getValue(), appVersion, shouldDownloadPlfListener, downloadArgs, willDownloadPlfListener, willDownloadPlfArgs, plfDownloadProgressListener, progressArgs, plfDownloadCompletionListener, completionArgs)) != ARUPDATER_ERROR_ENUM.ARUPDATER_OK) {
            throw new ARUpdaterException();
        }
        this.isInit = true;
    }

    public ARUPDATER_ERROR_ENUM dispose() {
        ARUPDATER_ERROR_ENUM error = ARUPDATER_ERROR_ENUM.ARUPDATER_OK;
        if (this.isInit) {
            error = ARUPDATER_ERROR_ENUM.getFromValue(nativeDelete(this.nativeManager));
            if (error == ARUPDATER_ERROR_ENUM.ARUPDATER_OK) {
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
                if (dispose() != ARUPDATER_ERROR_ENUM.ARUPDATER_OK) {
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

    public ARUPDATER_ERROR_ENUM cancel() {
        return ARUPDATER_ERROR_ENUM.getFromValue(nativeCancelThread(this.nativeManager));
    }

    public Runnable getDownloaderRunnable() {
        if (this.isInit) {
            return this.downloaderRunnable;
        }
        return null;
    }

    public ARUPDATER_ERROR_ENUM setUpdatesProductList(ARDISCOVERY_PRODUCT_ENUM[] productEnumArray) {
        int[] productArray = new int[productEnumArray.length];
        for (int i = 0; i < productEnumArray.length; i++) {
            productArray[i] = productEnumArray[i].getValue();
        }
        return ARUPDATER_ERROR_ENUM.getFromValue(nativeSetUpdatesProductList(this.nativeManager, productArray));
    }

    public ARUPDATER_ERROR_ENUM checkUpdatesAsync() {
        return ARUPDATER_ERROR_ENUM.getFromValue(nativeCheckUpdatesAsync(this.nativeManager));
    }

    public int checkUpdatesSync() throws ARUpdaterException {
        return nativeCheckUpdatesSync(this.nativeManager);
    }

    public ARUpdaterDownloadInfo[] getUpdatesInfoSync() throws ARUpdaterException {
        return nativeGetUpdatesInfoSync(this.nativeManager);
    }

    public HashMap<ARDISCOVERY_PRODUCT_ENUM, Set<String>> getBlacklistedVersionSync(boolean alsoCheckRemote) throws ARUpdaterException {
        HashMap<ARDISCOVERY_PRODUCT_ENUM, Set<String>> blacklistDict = null;
        int[] productArray = new int[ARDISCOVERY_PRODUCT_ENUM.ARDISCOVERY_PRODUCT_MAX.getValue()];
        Object[] blacklistedVersionArray = new Object[ARDISCOVERY_PRODUCT_ENUM.ARDISCOVERY_PRODUCT_MAX.getValue()];
        if (ARUPDATER_ERROR_ENUM.getFromValue(nativeGetBlacklistedFirmwareVersionsSync(this.nativeManager, alsoCheckRemote, productArray, blacklistedVersionArray)) == ARUPDATER_ERROR_ENUM.ARUPDATER_OK) {
            blacklistDict = new HashMap();
            for (int i = 0; i < ARDISCOVERY_PRODUCT_ENUM.ARDISCOVERY_PRODUCT_MAX.getValue(); i++) {
                ARDISCOVERY_PRODUCT_ENUM product = ARDISCOVERY_PRODUCT_ENUM.getFromValue(productArray[i]);
                String[] blacklistedVersionsForThisProduct = blacklistedVersionArray[i];
                if (blacklistedVersionsForThisProduct != null) {
                    Set<String> blacklistedStringArr = new HashSet();
                    Collections.addAll(blacklistedStringArr, blacklistedVersionsForThisProduct);
                    blacklistDict.put(product, blacklistedStringArr);
                }
            }
        }
        return blacklistDict;
    }
}
