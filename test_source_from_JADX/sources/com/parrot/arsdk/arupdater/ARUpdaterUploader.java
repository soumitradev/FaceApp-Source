package com.parrot.arsdk.arupdater;

import com.parrot.arsdk.ardiscovery.ARDISCOVERY_PRODUCT_ENUM;
import com.parrot.arsdk.arsal.ARSALMd5Manager;
import com.parrot.arsdk.arsal.ARSALPrint;
import com.parrot.arsdk.arutils.ARUtilsManager;
import com.parrot.mux.Mux;
import com.parrot.mux.Mux.Ref;

public class ARUpdaterUploader {
    private static final String TAG = "ARUpdaterUploader";
    private boolean isInit = false;
    private long nativeManager = 0;
    private Runnable uploaderRunnable = null;

    /* renamed from: com.parrot.arsdk.arupdater.ARUpdaterUploader$1 */
    class C16251 implements Runnable {
        C16251() {
        }

        public void run() {
            ARUpdaterUploader.this.nativeThreadRun(ARUpdaterUploader.this.nativeManager);
        }
    }

    private native int nativeCancelThread(long j);

    private native int nativeDelete(long j);

    private native int nativeNew(long j, String str, long j2, long j3, long j4, int i, int i2, ARUpdaterPlfUploadProgressListener aRUpdaterPlfUploadProgressListener, Object obj, ARUpdaterPlfUploadCompletionListener aRUpdaterPlfUploadCompletionListener, Object obj2);

    private static native void nativeStaticInit();

    private native void nativeThreadRun(long j);

    static {
        nativeStaticInit();
    }

    protected ARUpdaterUploader(long _nativeManager) {
        this.nativeManager = _nativeManager;
        this.uploaderRunnable = new C16251();
    }

    public void createUpdaterUploader(String rootFolder, Mux mux, ARUtilsManager utilsManager, ARSALMd5Manager md5Manager, boolean isAndroidApp, ARDISCOVERY_PRODUCT_ENUM product, ARUpdaterPlfUploadProgressListener plfUploadProgressListener, Object progressArgs, ARUpdaterPlfUploadCompletionListener plfUploadCompletionListener, Object completionArgs) throws ARUpdaterException {
        int result;
        ARUpdaterUploader aRUpdaterUploader = this;
        boolean isAndroidAppInt = isAndroidApp;
        if (mux != null) {
            Ref muxRef = mux.newMuxRef();
            Ref muxRef2 = muxRef;
            int result2 = nativeNew(aRUpdaterUploader.nativeManager, rootFolder, muxRef.getCPtr(), utilsManager.getManager(), md5Manager.getNativeManager(), isAndroidAppInt, product.getValue(), plfUploadProgressListener, progressArgs, plfUploadCompletionListener, completionArgs);
            muxRef2.release();
            result = result2;
            ARUpdaterUploader aRUpdaterUploader2 = this;
        } else {
            result = nativeNew(this.nativeManager, rootFolder, 0, utilsManager.getManager(), md5Manager.getNativeManager(), isAndroidAppInt, product.getValue(), plfUploadProgressListener, progressArgs, plfUploadCompletionListener, completionArgs);
            long j = 0;
        }
        if (ARUPDATER_ERROR_ENUM.getFromValue(result) != ARUPDATER_ERROR_ENUM.ARUPDATER_OK) {
            throw new ARUpdaterException();
        }
        aRUpdaterUploader2.isInit = true;
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

    public Runnable getUploaderRunnable() {
        if (this.isInit) {
            return this.uploaderRunnable;
        }
        return null;
    }
}
