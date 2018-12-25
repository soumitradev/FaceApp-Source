package com.parrot.arsdk.arsal;

public class ARSALMd5Manager {
    private boolean m_initOk = false;
    private long m_managerPtr = nativeNew();

    private native int nativeCheck(long j, String str, String str2);

    private native int nativeClose(long j);

    private native byte[] nativeCompute(long j, String str) throws ARSALException;

    private native void nativeDelete(long j);

    private native int nativeInit(long j, ARSALMd5 aRSALMd5);

    private native long nativeNew() throws ARSALException;

    private static native boolean nativeStaticInit();

    static {
        nativeStaticInit();
    }

    public ARSALMd5Manager() throws ARSALException {
        if (this.m_managerPtr != 0) {
            this.m_initOk = true;
        }
    }

    public long getNativeManager() {
        return this.m_managerPtr;
    }

    public void init() throws ARSALException {
        ARSAL_ERROR_ENUM result = ARSAL_ERROR_ENUM.getFromValue(nativeInit(this.m_managerPtr, new ARSALMd5()));
        if (result != ARSAL_ERROR_ENUM.ARSAL_OK) {
            throw new ARSALException(result);
        }
    }

    public ARSAL_ERROR_ENUM close() {
        return ARSAL_ERROR_ENUM.getFromValue(nativeClose(this.m_managerPtr));
    }

    public void dispose() {
        if (this.m_initOk) {
            nativeDelete(this.m_managerPtr);
            this.m_managerPtr = 0;
            this.m_initOk = false;
        }
    }

    public ARSAL_ERROR_ENUM check(String filePath, String md5Txt) {
        return ARSAL_ERROR_ENUM.getFromValue(nativeCheck(this.m_managerPtr, filePath, md5Txt));
    }

    public byte[] compute(String filePath) throws ARSALException {
        return nativeCompute(this.m_managerPtr, filePath);
    }
}
